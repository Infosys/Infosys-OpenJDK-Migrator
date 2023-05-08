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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.infy.openjdk.ui.View;

public class CloneDir {
	/**
	 * @param sourceLocation
	 * @param targetLocation
	 * @throws IOException
	 */
	public void copy(File sourceLocation, File targetLocation) throws IOException {
		if (sourceLocation.isDirectory()) {
			copyDirectory(sourceLocation, targetLocation);
		} 
		else {
			copyFile(sourceLocation, targetLocation);
		}

	}

	/**
	 * @param source
	 * @param target
	 * @throws IOException
	 */
	private void copyDirectory(File source, File target) throws IOException {
		if (!target.exists()) {
			target.mkdir();
		}

		for (String f : source.list()) {
			copy(new File(source, f), new File(target, f));
		}

	}

	/**
	 * @param source
	 * @param target
	 * @throws IOException
	 */
	private void copyFile(File source, File target) throws IOException {
		try (InputStream in = new FileInputStream(source); OutputStream out = new FileOutputStream(target)) {
			byte[] buf = new byte[1024];
			int length;
			while ((length = in.read(buf)) > 0) {
				out.write(buf, 0, length);
			}

		}
	}

	/**
	 * @param directory
	 */
	public void deleteFolder(File directory) {
	    if(directory.exists()){
	        File[] files = directory.listFiles();
	        if(null!=files){
	            for(int i=0; i<files.length; i++) {
	            	
	                if(files[i].isDirectory()) {
	                	View.logger.info("deleting file>>"+files[i].getName());
	                	deleteFolder(files[i]);
	                }
	                else {						
	                	View.logger.info("deleting file>>"+files[i].getName());
	                    boolean bool = files[i].delete();
	                    if(!bool) {
	                    	View.logger.info("not able to delete file"+files[i]);	                    
						}
	                }
	            }
	        }
	    }
	    View.logger.info("deleting last file "+directory.getName());
	    
	    boolean bool = directory.delete();
        if(!bool) {
        	View.logger.info("not able to delete directory"+directory);
        }
	}
	
}