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
package com.infy.openjdk.business;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.infy.openjdk.configuration.ConfigReader;
import com.infy.openjdk.ui.View;

/**
 * @author Silambarasan_a
 *
 */
public class CustomException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param a
	 */
	public static void main(String[] a) {

		//left blank for testing
				
	}

	/**
	 * @param errorId
	 */
	public CustomException(String errorId) {
		View.logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + ":"
				+ Thread.currentThread().getStackTrace()[1].getLineNumber() + ":"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
	}
}
