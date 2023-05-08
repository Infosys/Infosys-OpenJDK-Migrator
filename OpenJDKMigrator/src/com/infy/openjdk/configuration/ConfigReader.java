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
package com.infy.openjdk.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.eclipse.swt.widgets.Shell;

import com.infy.openjdk.ui.View;


public class ConfigReader {
	static Shell shell = new Shell();
	Properties prop = new Properties();
	private static ConfigReader instance = null;
	
	/**
	 * @return
	 */
	public Properties getProp() {
		return prop;
	}

	/**
	 * @param prop
	 */
	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public ConfigReader() {
		try {
			
			URL jdk = new URL("platform:/plugin/OpenJDKMigrator/Migration.product/repository/");						
			InputStream propInputstream = jdk.openConnection().getInputStream();
			prop.load(propInputstream);					
		} 
		catch (MalformedURLException e3) {
			View.logger.error(e3.getMessage(),e3);
		} 
		catch (IOException e) {
			View.logger.error(e.getMessage(),e);
		}
	}

	/**
	 * This method allows only one instance of this class to be created
	 *
	 * @return ConfigReader
	 * @throws BaseException
	 */
	public static ConfigReader getInstance()  {
		if (null == instance) {
			try {
				instance = new ConfigReader();
			} 
			catch (Exception exp) {
				View.logger.error(exp.getMessage(),exp);
			}
		}
		return instance;
	}

	/**
	 * This method is for getting the value for the given key from properties file
	 *
	 * @param name - Name of the key
	 * @return String
	 */
	public String getProperty(String name) {
		return prop.getProperty(name);
	}

}
