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
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import com.infy.openjdk.configuration.MyPropertyLoad;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

import com.infy.openjdk.ui.View;

public class LicenseGenerator {    /**
     * 1. Generate a plain text for encryption
     * 2. Get a secret key (printed in hexadecimal form). In actual use this must 
     * by encrypted and kept safe. The same key is required for decryption.
     * 3. 
     */
	public static MyPropertyLoad obj = new MyPropertyLoad();
    public static void main(String[] args) {
        try {
        String plainText = "#1.0#10-06-2019#31-02-2023#AJAY-SINGH";
        SecretKey secKey = getSecretEncryptionKey();
        String filterUnwantedChar = obj.cleanString(System.getProperty("user.dir"));
        String encodedKey = Base64.getEncoder().encodeToString(secKey.getEncoded());
        File filename = new File(filterUnwantedChar + File.separator +"license"+File.separator+ "key.txt");
        FileOutputStream file = new FileOutputStream(filename);
        try(ObjectOutputStream oout = new ObjectOutputStream(file)){
        	oout.writeObject(encodedKey);
        }				
		file.close();

        byte[] cipherText = encryptText(plainText, secKey);
        System.out.println("length"+cipherText.length);
        File fileLice = new File(System.getProperty("user.dir") + File.separator +"license"+File.separator+ "license.txt");
        try(FileOutputStream fileLic = new FileOutputStream(fileLice)){
            try(ObjectOutputStream objOutStr = new ObjectOutputStream(fileLic)){
            	objOutStr.writeObject(Base64.getEncoder().encodeToString(cipherText));
            }            
        }
            System.out.println("License generated successfully");
    	}
        catch (Exception e) {
			View.logger.error(e.getMessage());
		}
        
    }
     
    /**
     * gets the AES encryption key. In your actual programs, this should be safely
     * stored.
     * @return
     * @throws Exception 
     */
    public static SecretKey getSecretEncryptionKey() {
        KeyGenerator generator = null;
		try {
			generator = KeyGenerator.getInstance("AES");
		}
        catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // The AES key size in number of bits
        generator.init(128); 
        return generator.generateKey();
    }
         
    /**
    * Encrypts plainText in AES using the secret key
    * @param secKey
    * @param plainText
    * @return
    * @throws Exception 
    */
    public static byte[] encryptText(String plainText,SecretKey secKey) {    
        Cipher aesCipher;
        byte[] res = null;
	    try {
			aesCipher = Cipher.getInstance("AES/GCM/NoPadding");
            aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
            res= aesCipher.doFinal(plainText.getBytes());
		} 
        catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return res;
    }
     
    /**
    * Decrypts encrypted byte array using key used for encryption.
    * @param secKey
    * @param byteCipherText
    * @return
    * @throws Exception 
    */
    public static String decryptText(byte[] byteCipherText, SecretKey secKey) {    
        Cipher aesCipher;
        String res1 = null;
	    try {
		    aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.DECRYPT_MODE, secKey);
            byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
            res1= new String(bytePlainText);
		} 
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException  e) {
		    // TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return res1;
    }
     
    /**
     * Convert a binary byte array into readable hex form
     * @param hash
     * @return 
     */
    private static String  bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }
}