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
import java.io.FileNotFoundException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import com.infy.openjdk.configuration.MyPropertyLoad;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infy.openjdk.constants.Constants;
import com.infy.openjdk.ui.View;

public class LicenseValidator {
	public static MyPropertyLoad obj = new MyPropertyLoad();
    public static boolean ADMINLICENSE=false;
    static Logger logger = (Logger) LoggerFactory.getLogger(LicenseValidator.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {	
			LicenseValidator.validateLicense();
		} catch (Exception e) {
		
		}
	}

	/**
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws Exception
	 */
	public static boolean validateLicense() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, Exception {
		boolean isLicenseValidated = false;
		String filterUnwantedChar = obj.cleanString(System.getProperty("user.dir"));
			
		File license = new File(filterUnwantedChar + File.separator +"license");
		if(!license.exists()) {
			logger.info("License is not present. Please upload license.");
		}
		else {
			filterUnwantedChar = obj.cleanString(System.getProperty("user.dir"));
			File licenseGen = new File(filterUnwantedChar + File.separator +"license"+File.separator+ "license.txt"); 
			File keyGen = new File(filterUnwantedChar+ File.separator +"license"+File.separator+ "key.txt"); 
			if(!licenseGen.exists() ||  !keyGen.exists() ) {
				logger.info("License or Key is not present. Please upload the same.");
				System.out.println("License or Key is not present. Please upload the same.");
			}
			else {
				System.out.println("else if");
				Scanner scanLicense;
			try {
				filterUnwantedChar = obj.cleanString(System.getProperty("user.dir"));
				scanLicense = new Scanner(new File( filterUnwantedChar+ File.separator +"license"+File.separator+ "license.txt"));			
				ArrayList<String> licenseDetails = new ArrayList<>();
				while(scanLicense.hasNext()) {
					licenseDetails.add(scanLicense.nextLine());
				}
				scanLicense.close();
			 	filterUnwantedChar = obj.cleanString(System.getProperty("user.dir"));
				Scanner scanKey = new Scanner(new File(filterUnwantedChar + File.separator +"license"+File.separator+ "key.txt"));
				scanKey.close();
				String licenseString = EncryptioDecryption.getLicenseString(new File(filterUnwantedChar + File.separator +"license"+File.separator+ "key.txt"),new File(System.getProperty("user.dir") + File.separator +"license"+File.separator+ "license.txt"));
				String[] licenseArray = licenseString.split("#");
				String version = licenseArray[1];
				String version1 =licenseArray[1];
				String validUpto = licenseArray[3];

				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date validUptodate = sdf. parse(validUpto);
				Calendar calValidUpto = Calendar. getInstance();
				calValidUpto.setTime(validUptodate);
				
				if(version.equalsIgnoreCase(Constants.VERSION) && calValidUpto.compareTo(cal) > 0 ) {
					logger.info("Successfull authentication.");
					isLicenseValidated = true;	
				} 
				else if (version1.equalsIgnoreCase(Constants.VERSION1) && calValidUpto.compareTo(cal) > 0) {
					logger.info("Successfull Admin authentication.");
					ADMINLICENSE = true;
					isLicenseValidated = true;
				} 
				else {
					logger.info("Not Successfull Admin Authentication.");
				}

			} 
			catch (FileNotFoundException | ParseException | NullPointerException | IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	return isLicenseValidated;

}
   	
}
