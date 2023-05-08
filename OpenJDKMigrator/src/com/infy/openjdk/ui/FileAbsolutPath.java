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

package com.infy.openjdk.ui;

import java.io.File;
import java.util.ArrayList;

public class FileAbsolutPath {
	//testing purpose
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileAbsolutPath d = new FileAbsolutPath();
		String filename = "web.xml";
		ArrayList<File> files = new ArrayList<File>();
		String h = d.listf("C:\\Users\\prateechee_mishra.ITLINFOSYS\\Desktop\\restful\\restful", files, filename);
		System.out.println(h);

		
	}

	/**
	 * @param directoryName
	 * @param files
	 * @param filename
	 * @return
	 */
	public String listf(String directoryName, ArrayList<File> files, String filename) {
		File directory = new File(directoryName);
		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				if (file.toString().contains(filename)) {
					files.add(file);
				}

			} else if (file.isDirectory()) {
				listf(file.getAbsolutePath(), files, filename);
			}
		}
		
		return files.toString();
	}

}