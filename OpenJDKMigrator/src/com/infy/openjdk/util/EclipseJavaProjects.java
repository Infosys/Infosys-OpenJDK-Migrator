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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

import com.infy.openjdk.ui.View;

public class EclipseJavaProjects {
	/**
	 * @return projectList
	 */
	public static List<IJavaProject> getJavaProjects() {
		List<IJavaProject> projectList = new LinkedList<IJavaProject>();
		try {

			IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
			IProject[] projects = workspaceRoot.getProjects();
			for (int i = 0; i < projects.length; i++) {
				IProject project = projects[i];
				if (project.isOpen() && project.hasNature(JavaCore.NATURE_ID)) {
					projectList.add(JavaCore.create(project));
				}
			}
		} catch (CoreException ce) {
			ce.printStackTrace();
			// TODO: handle exception
		}
		return projectList;
	}

	/**
	 * @return projects
	 */
	public static IProject[] getProjects() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		return projects;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		IProject[] projects = EclipseJavaProjects.getProjects();
		System.out.println(Arrays.toString(projects));
	}

}