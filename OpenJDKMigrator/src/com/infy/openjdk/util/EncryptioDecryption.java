/*
* Copyright 2018 Infosys Ltd.
* Version: 1.0.0
*Use of this source code is governed by MIT license that can be found in the LICENSE file or at https://opensource.org/licenses/MIT.
*/

/*
* Date: 06-May-2019
* @version 1.0.0
* Description: 
*/

package com.infy.openjdk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infy.openjdk.ui.View;

public class EncryptioDecryption {  
    static Logger logger = (Logger) LoggerFactory.getLogger(EncryptioDecryption.class);
    public static final int GCM_IV_LENGTH = 12;
    public static final int GCM_TAG_LENGTH = 16;
	/**
     * 1. Generate a plain text for encryption
     * 2. Get a secret key (printed in hexadecimal form). In actual use this must 
     * by encrypted and kept safe. The same key is required for decryption.
     * 3. 
	 * @throws Exception 
     */


    /**
     * @param filename
     * @param fileLice
     * @return
     * @throws Exception
     */
    public static String getLicenseString(File filename,File fileLice) throws Exception  {	
	    SecretKey originalKey = null;
	    try(FileInputStream fileInput = new FileInputStream(filename)) {		  
		    try(ObjectInputStream in = new ObjectInputStream(fileInput)){
				String secKeyObj = (String) in.readObject(); 
				byte[] decodedKey = Base64.getDecoder().decode(secKeyObj);
				 originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		    }
	    // Method for deserialization of object 

	    }
        catch (IOException  |SecurityException |NullPointerException |ClassNotFoundException e) {
		logger.error(e.getMessage());
	    }

	    String liceByte = null;
	    try(FileInputStream fileInputLic = new FileInputStream(fileLice);
		ObjectInputStream out = new ObjectInputStream(fileInputLic); ) {
	        // Method for deserialization of object 
	        liceByte = (String) out.readObject();
	    }
        catch (IOException  |SecurityException |NullPointerException | ClassNotFoundException e) {
		    logger.error(e.getMessage());
	    }  

	    String decryptedText = decryptText(Base64.getDecoder().decode(liceByte), originalKey);
	    Provider[] prov =  Security.getProviders();
	    prov[0].getName();
	    return decryptedText;
    }
     
    /**
     * gets the AES encryption key. In your actual programs, this should be safely
     * stored.
     * @return
     * @throws NoSuchAlgorithmException 
     * @throws Exception 
     */
    
    public static SecretKey getSecretEncryptionKey() throws NoSuchAlgorithmException{
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        SecureRandom secRandom = new SecureRandom();
        generator.init(secRandom); // The AES key size in number of bits
        return generator.generateKey();
    }
              
    /**
    * Decrypts encrypted byte array using the key used for encryption.
    * @param byteCipherText
    * @param secKey
    * @return
    * @throws NoSuchPaddingException 
    * @throws NoSuchAlgorithmException 
    * @throws BadPaddingException 
    * @throws IllegalBlockSizeException 
    * @throws InvalidKeyException 
    * @throws InvalidAlgorithmParameterException 
    * @throws Exception 
    */
    public static String decryptText(byte[] byteCipherText, SecretKey secKey) throws Exception {
        // AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, secKey);
        byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
        return new String(bytePlainText);
    }
       
}
