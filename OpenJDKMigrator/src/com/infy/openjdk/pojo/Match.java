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

package com.infy.openjdk.pojo;

public class Match {
	private int lineNumber;
	private String fileName;
	private String impactLine;
	private String supportVersion;
	private String depracatedMethod;
	private String metadataFolderName;
	private String complexity;
	private String sourcePattern;
	private String mandOpt;
	private String syntaxId;
	private String reference;
	private String isAutomated;

/**
 * @return reference
 */
public String getReference() {
		return reference;
	}

	/**
	 * @param reference
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

/**
 * @return syntaxId
 */
public String getSyntaxId() {
		return syntaxId;
	}

	/**
	 * @param syntaxId
	 */
	public void setSyntaxId(String syntaxId) {
		this.syntaxId = syntaxId;
	}

/**
 * @return mandOpt
 */
public String getMandOpt() {
		return mandOpt;
	}

	/**
	 * @param mandOpt
	 */
	public void setMandOpt(String mandOpt) {
		this.mandOpt = mandOpt;
	}

/**
 * @return sourcePattern
 */
public String getSourcePattern() {
		return sourcePattern;
	}

	/**
	 * @param sourcePattern
	 */
	public void setSourcePattern(String sourcePattern) {
		this.sourcePattern = sourcePattern;
	}

	//private String
	/**
	 * @return depracatedMethod
	 */
	public String getDepracatedMethod() {
		return depracatedMethod;
	}

	/**
	 * @return complexity
	 */
	public String getComplexity() {
		return complexity;
	}

	/**
	 * @param complexity
	 */
	public void setComplexity(String complexity) {
		this.complexity = complexity;
	}

	/**
	 * @return metadataFolderName
	 */
	public String getMetadataFolderName() {
		return metadataFolderName;
	}

	/**
	 * @param metadataFolderName
	 */
	public void setMetadataFolderName(String metadataFolderName) {
		this.metadataFolderName = metadataFolderName;
	}


	/**
	 * @param depracatedMethod
	 */
	public void setDepracatedMethod(String depracatedMethod) {
		this.depracatedMethod = depracatedMethod;
	}

	/**
	 * @return lineNumber
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * @param lineNumber
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return
	 */
	public String getImpactLine() {
		return impactLine;
	}

	/**
	 * @param impactLine
	 */
	public void setImpactLine(String impactLine) {
		this.impactLine = impactLine;
	}

	/**
	 * @return
	 */
	public String getSupportVersion() {
		return supportVersion;
	}

	/**
	 * @param supportVersion
	 */
	public void setSupportVersion(String supportVersion) {
		this.supportVersion = supportVersion;
	}

	/**
	 * @return isAutomated
	 */
	public String getIsAutomated() {
		return isAutomated;
	}

	/**
	 * @param isAutomated
	 */
	public void setIsAutomated(String isAutomated) {
		this.isAutomated = isAutomated;
	}

}