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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.infy.openjdk.ui.View;
 
public class MyZipFileList {
String getProjectName=null; 

/**
 * @return
 */
public String getGetProjectName() {
	return getProjectName;
}

/**
 * @param getProjectName
 */
public void setGetProjectName(String getProjectName) {
	this.getProjectName = getProjectName;
}

/**
 * @param filePath
 * @throws IOException
 */
public void printFileList(String filePath) throws IOException{         
        ZipEntry zEntry = null;
        try(FileInputStream  fis = new FileInputStream(filePath);
        	ZipInputStream zipIs = new ZipInputStream(new BufferedInputStream(fis));) {
                int i=0;
                while((zEntry = zipIs.getNextEntry()) != null && i==0){
            	    getProjectName=zEntry.getName();
                    i++;
                }
        } 
        catch (IOException e) {
           View.logger.error(e.getMessage());
        } 
    }
 
    /**
     * @param a
     */
    public static void main(String[] a){         
        MyZipFileList mfe = new MyZipFileList();
        try {
			mfe.printFileList("D:\\ADMCOE\\InfosysModernizationPlatform\\UITemplateSourceCode.zip");
		} 
        catch (IOException e) {
			View.logger.error(e.getMessage());
		}       
    }
}