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
import java.io.IOException;

import com.infy.openjdk.ui.View;

public class ExtractZipContents {
	/**
	 * @param unzipPath
	 * @throws IOException
	 */
	public void getFilesFromZip(String unzipPath) throws IOException {
		/*String path = validateFilenameInDir("UITemplateSourceCode.zip","D:\\Project\\WorkBench\\WorkSpaceADMCOE\\OpenJDKMigrator\\" );		
		try(ZipFile zipFile = new ZipFile(path)) {
			
			Enumeration<?> enu = zipFile.entries();
			while (enu.hasMoreElements()) {
				ZipEntry zipEntry = (ZipEntry) enu.nextElement();

				String name = zipEntry.getName();


				File file = new File(name);
				if (name.endsWith("/")) {
					file.mkdirs();
					continue;
				}


				File parent = file.getParentFile();
				if (parent != null) {
					parent.mkdirs();
				}

				InputStream is = zipFile.getInputStream(zipEntry);
				try(FileOutputStream fos = new FileOutputStream(file)){
				byte[] bytes = new byte[1024];
				int length;
				while ((length = is.read(bytes)) >= 0) {
					fos.write(bytes, 0, length);
				}
				}
				is.close();
				

			}
			
		
		} catch (IOException e) {
			View.logger.error(e.getMessage());
		}*/
	}
	
    /**
     * @param a
     */
    public static void main(String[] a){        
    	ExtractZipContents mfe = new ExtractZipContents();
        try {
			mfe.getFilesFromZip("D:\\Project\\WorkBench\\WorkSpaceADMCOE\\OpenJDKMigrator\\UITemplateSourceCode.zip");
		} 
		catch (IOException e) {
			View.logger.error(e.getMessage());
		}
       
    }
	       
}