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
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class Browse {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Choose a directory: ");
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnValue = jfc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			if (jfc.getSelectedFile().isDirectory()) {
				System.out.println("You selected the directory: " + jfc.getSelectedFile());
			}
		}

	}

}