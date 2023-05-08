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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.infy.openjdk.report.JTableHeaderCheckBox;
import com.infy.openjdk.ui.View;

public class MergeChanges {
	
	private static final String JAVA = ".java";
	Integer intNoOfFilesMerged = 0;

	/**
	 * @return
	 */
	public Integer getIntNoOfFilesMerged() {
		return intNoOfFilesMerged;
	}

	/**
	 * @param intNoOfFilesMerged
	 */
	public void setIntNoOfFilesMerged(Integer intNoOfFilesMerged) {
		this.intNoOfFilesMerged = intNoOfFilesMerged;
	}

	public void mergeFiles() {
		View.logger.info("inside mergeFiles");		
	}
	
	// Scans list of files from the uploaded project
	/**
	 * @param dir
	 * @param list2
	 * @param list
	 * @param jtable
	 */
	public void displayDestDirContents(File dir, List<Map<String, String>> list2, List<String> list, JTableHeaderCheckBox jtable) {
		try {

			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					displayDestDirContents(file, list2,  list, jtable);
				} 				

				else {
					if (file.getName().endsWith(JAVA)) {
						replaceCode(list2, file,list,jtable);
					}
				}
			}
		} 
		catch (IOException e) {
			View.logger.error(e.getMessage(),e);
		}

	}

	/**
	 * @param list2
	 * @param file
	 * @param headerList
	 * @param jtable
	 * @throws IOException
	 */
	private void replaceCode(List<Map<String, String>> list2,File file, List<String> headerList, JTableHeaderCheckBox jtable) throws IOException {
		
		Set<String> selectedFiles = jtable.getSelectedFiles();
        Set<String> removedFiles = jtable.getRemovedFiles();
		String fileName = headerList.get(0);
		String incompatibility = headerList.get(2);
		String possibleReplacement = headerList.get(3);
		View.logger.info("selectedFiles>>"+selectedFiles);
		View.logger.info("removedFiles>>"+removedFiles);
	    String oldFileName = file.getCanonicalPath();
	    File filetmp = file;
	    String tmpFileName = filetmp.getCanonicalPath().replace(JAVA, "_updated.java");
        boolean boolFileMatched = false;

		try (Scanner scanner = new Scanner(new File(file.getCanonicalPath()));
			BufferedWriter bw = new BufferedWriter(new FileWriter(tmpFileName));){
			List<Map<String, String>> hmImpactedFiles = list2;
			for(Map<String, String> lnkhmmap:hmImpactedFiles) {
				View.logger.info(lnkhmmap.get(fileName));
				if(file.getCanonicalPath().equalsIgnoreCase(lnkhmmap.get(fileName)) && (selectedFiles.contains("ALL") || selectedFiles.contains(file.getName()))) {
					View.logger.info("file Matched file.getName() >>"+lnkhmmap.get(fileName));
					boolFileMatched = true;
					while (scanner.hasNextLine()) {				        	 
				    	String line = scanner.nextLine();
						if(lnkhmmap.get(incompatibility).equalsIgnoreCase(line)) {
							line = lnkhmmap.get(possibleReplacement);
							bw.write(line+"\n");
							break;
						}
						else {
							bw.write(line+"\n");	
						}
						 
				    }
				         
				}
			}

			if(boolFileMatched) {
				intNoOfFilesMerged = intNoOfFilesMerged+1;
				while (scanner.hasNextLine()) {
					bw.write(scanner.nextLine()+"\n");
				}
				bw.close();
				scanner.close();
				  //deleting the old file
			      File oldFile = new File(oldFileName);
			      Files.delete(oldFile.toPath());

			      // And rename new file's name to old file name
			      File newFile = new File(tmpFileName);
			      
			      boolean bool1 = newFile.renameTo(oldFile);
			      if(!bool1) {
			    	  View.logger.info("file cannot be renamed");
			      }
			      
			}
			else {
				bw.close();
				scanner.close();
			    File tempFileName = new File(tmpFileName);
			    Files.delete(tempFileName.toPath());

			}			

		} 
		catch (FileNotFoundException e) {
			View.logger.info(e.getMessage(),e);
		} 		

	}
	
}
