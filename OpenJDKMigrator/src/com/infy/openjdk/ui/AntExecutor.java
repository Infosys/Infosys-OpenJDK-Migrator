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
import java.io.IOException;
import org.apache.logging.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

/**
 * @author Ekta_Khippal
 *
 */
public class AntExecutor {
	/**
	 * To execute the default target specified in the Ant build.xml file
	 *
	 * @param buildXmlFileFullPath
	 */
	static Logger logger1 = null;

	public static boolean executeAntTask1(String buildXmlFileFullPath1) {
		return executeAntTask1(buildXmlFileFullPath1, null);
	}

	/**
	 * To execute a target specified in the Ant build.xml file
	 *
	 * @param buildXmlFileFullPath
	 * @param target
	 */
	@SuppressWarnings("finally")
	public static boolean executeAntTask1(String buildXmlFileFullPath1, String target1) {
		boolean success1 = false;

		// Prepare Ant project
		Project project1 = new Project();
		File buildFile1 = new File(buildXmlFileFullPath1);
		project1.setUserProperty("ant.file..", buildFile1.getAbsolutePath());
		// Capture event for Ant script build start / stop / failure
		try {
			project1.fireBuildStarted();
			project1.init();
			ProjectHelper projectHelper1 = ProjectHelper.getProjectHelper();
			project1.addReference("ant.projectHelper", projectHelper1);
			projectHelper1.parse(project1, buildFile1);
			// If no target specified then default target will be executed.
			String targetToExecute1 = (target1 != null && target1.trim().length() > 0) ? target1.trim()
					: project1.getDefaultTarget();
			project1.executeTarget(targetToExecute1);
			project1.fireBuildFinished(null);
			success1 = true;
			return success1;
		} 
		catch (BuildException buildException1) {
			project1.fireBuildFinished(buildException1);
			throw new RuntimeException("!!! Unable to restart the  App !!!", buildException1);
		} 
		finally {
			//commented below line to resolve the sonar issue

		}

	}

	/**
	 * Main method to test code
	 *
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args)  {
		// Running default target of ant script
		executeAntTask1("build.xml");		
			logger1.info("BUILD SUCCESSFUL");		
		// Running specified target of ant script
	}

}