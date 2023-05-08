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
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.infy.openjdk.configuration.MyPropertyLoad;
import com.infy.openjdk.ui.View;

public class URLReader {
	public MyPropertyLoad obj = new MyPropertyLoad();
		/**
		 * @param url
		 * @param file
		 * @throws IOException
		 */
		public static void copyURLToFile(URL url, File file) throws IOException {		
			try(InputStream input = url.openStream();) {				
			if (file.exists()) {
				if (file.isDirectory())
					throw new IOException("File '" + file + "' is a directory");					
					if (!file.canWrite())
						throw new IOException("File '" + file + "' cannot be written");
			} 
			else {
				File parent = file.getParentFile();
				if ((parent != null) && (!parent.exists()) && (!parent.mkdirs())) {
					throw new IOException("File '" + file + "' could not be created");
				}
			}

			try(FileOutputStream output = new FileOutputStream(file)){
				byte[] buffer = new byte[4096];
				int n = 0;
				while (-1 != (n = input.read(buffer))) {
					output.write(buffer, 0, n);
				}
			}			
		View.logger.info("File '" + file + "' downloaded successfully!");
		}
		catch(IOException ioEx) {
			View.logger.error(ioEx.getMessage());
		}
	}
		
	/**
	 * @param sURL
	 * @param filePath
	 * @throws IOException
	 */
	public void getCode(String sURL, String filePath) throws IOException{
		URL url = new URL(sURL);			
		//File where to be downloaded
		File file = new File(filePath);			
		URLReader.copyURLToFile(url, file);
	}

	/**
	 * @param repoUrl
	 * @param cloneDirectoryPath
	 * @param username
	 * @param password
	 * @throws GitAPIException
	 */
	public void connectgit(String repoUrl, String cloneDirectoryPath, String username, String password) throws GitAPIException{
 		String filterUnwantedChar = obj.cleanString(cloneDirectoryPath);
		File file = new File(filterUnwantedChar);
		if(file.exists()) {
			deleteCloneFolder(file);
		}
			  
		CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(username,password);
		if(cloneDirectoryPath.length()>0 ||cloneDirectoryPath !=null ||!cloneDirectoryPath.matches("[*:?<>|\"]")) {
			Git.cloneRepository()
			.setURI(repoUrl)
			.setCredentialsProvider(credentialsProvider )
			.setDirectory(Paths.get(filterUnwantedChar).toFile())
			.call();
		    System.out.println("Completed Cloning");
		}
	}
		
	/**
	 * @param clonedirectory
	 */
	public void deleteCloneFolder(File clonedirectory) {
		if(clonedirectory.exists()){
		    File[] filesindir = clonedirectory.listFiles();
		    if(null!=filesindir){
		        for(int i=0; i<filesindir.length; i++) {		            	
		            if(filesindir[i].isDirectory()) {
		                View.logger.info("deleting file>>"+filesindir[i].getName());
		                deleteCloneFolder(filesindir[i]);
		            }
		            else {
		                View.logger.info("deleting file>>"+filesindir[i].getName());
		                boolean bool = filesindir[i].delete();
		                if(!bool) {
		                    View.logger.info("not able to delete file"+filesindir[i]);	                    
						}
		            }
		        }
		    }
		}
		View.logger.info("deleting last file "+clonedirectory.getName());		    
		boolean bool = clonedirectory.delete();
	    if(!bool) {
	        View.logger.info("not able to delete directory"+clonedirectory);
	    }
	}				
}