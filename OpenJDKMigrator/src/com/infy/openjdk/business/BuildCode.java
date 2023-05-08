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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;
import org.apache.poi.util.BoundedInputStream;

import com.infy.openjdk.util.Main;

import net.sf.jasperreports.util.StringBuilderWriter;

public class BuildCode {
	/**
	 * @param projectPath
	 * @return
	 * @throws IOException
	 */
	public StringBuilder buildMavenProject(String projectPath) throws IOException {
		ProcessBuilder builder = new ProcessBuilder(
	            "cmd.exe", "/c", "cd \""+projectPath+"\" && mvn clean compile");
	        builder.redirectErrorStream(true);
	        Process p = builder.start();
	        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()),8192);
	        StringBuilder output = new StringBuilder();
	        StringBuilderWriter writer = new StringBuilderWriter(output);

	        IOUtils.copy(r, writer); // copies data from "reader" => "writer"
	        return output;

	}     

	/**
	 * @param args
	 * @throws IOException
	 */
		
	public static void main(String[] args) throws IOException {
		BuildCode code= new BuildCode();
		code.buildMavenProject("D:\\DemoJDK\\ApplicationSample");
	}
	
}
