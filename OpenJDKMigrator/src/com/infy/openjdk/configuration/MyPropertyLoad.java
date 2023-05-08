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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.Properties;
import java.util.regex.PatternSyntaxException;

import com.infy.openjdk.ui.View;

public class MyPropertyLoad {
	Properties prop = new Properties();

	/**
	 * @return prop
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

	/**
	 * @param version
	 */
	public void loadRule(String version) {
		InputStream is = null;
		String path = null;
		URL url;
		try {
			prop = new Properties();
			path = System.getProperty("user.dir") + File.separator + "properties" + File.separator + version.trim()
					+ ".properties";

			url = new URL("platform:/plugin/OpenJDKMigrator/com/infy/openjdk/configuration/" + version.trim()
					+ ".properties");
			is = url.openConnection().getInputStream();
			prop.load(is);

		} 
		catch (FileNotFoundException | UnknownServiceException e) {
			View.logger.error(e.getMessage(), e);
		} 
		catch (IOException e) {
			try {
				String filterUnwantedChar = cleanString(path);
					is = new FileInputStream(new File(filterUnwantedChar));
					prop.load(is);
			} 
			catch (IOException | PatternSyntaxException e1) {
				View.logger.error(e.getMessage(), e);
			}
		}

	}

	/**
	 * @param workingDir
	 * @return cleanString
	 */
	public String cleanString(String workingDir) {
		if (workingDir == null)
			return null;
		String cleanString = "";
		for (int i = 0; i < workingDir.length(); ++i) {
			cleanString += cleanChar(workingDir.charAt(i));
		}
		return cleanString;
	}

	/**
	 * @param aChar
	 * @return char
	 */
	private char cleanChar(char aChar) {
		// 0 - 9
		for (int i = 48; i < 58; ++i) {
			if (aChar == i)
				return (char) i;
		}

		// 'A' - 'Z'
		for (int i = 65; i < 91; ++i) {
			if (aChar == i)
				return (char) i;
		}

		// 'a' - 'z'
		for (int i = 97; i < 123; ++i) {
			if (aChar == i)
				return (char) i;
		}

		// other valid characters
		switch (aChar) {
		case '/':
			return '/';
		case '\\':
			return '\\';
		case '.':
			return '.';
		case '-':
			return '-';
		case '_':
			return '_';
		case ' ':
			return ' ';
		case ':':
			return ':';
		}

		return aChar;
	}
}