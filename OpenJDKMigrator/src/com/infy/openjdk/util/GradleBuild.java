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

import java.util.HashMap;
import java.util.List;

public class GradleBuild {
	private List<Dependency> dependencies;
	private List plugins;
	private HashMap def;
	private String sourceCompatibility;
	private String targetCompatibility;
	/**
	 * @return dependencies
	 */
	public List<Dependency> getDependencies() {
		return dependencies;
	}
	/**
	 * @param dependencies
	 */
	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}
	/**
	 * @return plugins
	 */
	public List getPlugins() {
		return plugins;
	}
	/**
	 * @param plugins
	 */
	public void setPlugins(List plugins) {
		this.plugins = plugins;
	}
	/**
	 * @return
	 */
	public HashMap getDef() {
		return def;
	}
	/**
	 * @param def
	 */
	public void setDef(HashMap def) {
		this.def = def;
	}
	/**
	 * @return
	 */
	public String getSourceCompatibility() {
		return sourceCompatibility;
	}
	/**
	 * @param sourceCompatibility
	 */
	public void setSourceCompatibility(String sourceCompatibility) {
		this.sourceCompatibility = sourceCompatibility;
	}
	/**
	 * @return targetCompatibility
	 */
	public String getTargetCompatibility() {
		return targetCompatibility;
	}
	/**
	 * @param targetCompatibility
	 */
	public void setTargetCompatibility(String targetCompatibility) {
		this.targetCompatibility = targetCompatibility;
	}

}