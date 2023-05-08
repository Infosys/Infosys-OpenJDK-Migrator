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

public class Dependency {
	private String groupId;
	private String artifactId;
	private String version;
	
	/**
	 * @param groupId
	 * @param artifactId
	 * @param version
	 */
	public Dependency(String groupId,String artifactId,String version) {
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
	}
	
	/**
	 * @return groupId
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return artifactId
	 */
	public String getArtifactId() {
		return artifactId;
	}
	/**
	 * @param artifactId
	 */
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}
	/**
	 * @return
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
}
