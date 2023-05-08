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
package com.infy.openjdk.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import com.infy.openjdk.configuration.JsonFileReader;
import com.infy.openjdk.configuration.MyPropertyLoad;
import com.infy.openjdk.mapper.RemediationParsingJson;
import com.infy.openjdk.pojo.Match;
import com.infy.openjdk.pojo.Rule;
import com.infy.openjdk.ui.View;
import com.infy.openjdk.util.Progress;
import com.infy.openjdk.util.Utilities;
public class AppScannerJson {

	private static final String SIMPLE = "simple";
	private static final String REDESIGN = "redesign";
	private static final String MEDIUM = "medium";
	private static final String FRAMEWORK = "framework";
	private static final String COMPLEX = "complex";
	private static final String LINE_NO = "Line No.";
	private static final String INCOMPATIBILITY = "Incompatibility";
	private static final String FILE_NAME = "File Name";
	private static final String APPLICATION_PATH = "Application Path";
	RemediationParsingJson parsingJson = new RemediationParsingJson();
	Utilities utility = new Utilities();
	int totalFilesScanned=0;
	Map<String, String> toJdkVersion = new HashMap<>();
	List<String> columnHeaders6 = new ArrayList<>();
	List<String> columnHeadersAssisted = new ArrayList<>();
	List<String> columnpackageHeader = new ArrayList<>();
	List<String> columnExecutedRuleHeader = new ArrayList<>();
	List<String> columnIncFeatHeader = new ArrayList<>();
	List<String> multiBrowseColHeader = new ArrayList<>();
	List<String> columnHeadersLibrary = new ArrayList<>();
	Map<String, String> dataMap = new LinkedHashMap<>();
	Map<String, String> dataMapAssisted = new LinkedHashMap<>();
	Map<String, String> dataMapAssistedMand = new LinkedHashMap<>();
	Map<String, String> dataMapLibrary = new LinkedHashMap<>();
	Map<String, String> dataMapPackages = new LinkedHashMap<>();
	Map<String, String> dataMapPackagesNew = new LinkedHashMap<>();
	Map<String, String> dataMapPackagesIncfeat = new LinkedHashMap<>();
	Map<String, String> dataMapMultiBrowse = new LinkedHashMap<>();
	List<Map<String, String>> listGrid13 = new ArrayList<>();
	List<Map<String, String>> listGridAssisted = new ArrayList<>();
	List<Map<String, String>> listGridAssistedMand = new ArrayList<>();
	List<Map<String, String>> listGridPackages = new ArrayList<>();
	List<Map<String, String>> listOfExecutedRules = new ArrayList<>();
	List<Map<String, String>> listOfIncompatibleFeatures = new ArrayList<>();
	List<Map<String, String>> listGridMulti = new ArrayList<>();
	List<Map<String, String>> listGidlibrary= new ArrayList<>();
	List<Map<String, String>> listassistedAll = new ArrayList<>();
	Set<String> automatedFiles = new HashSet<>();
	Set<String> assistedFiles = new HashSet<>();
	
	/**
	 * @return
	 */
	public Map<String, String> getDataMap() {
		return dataMap;
	}
	/**
	 * @param dataMap
	 */
	public void setDataMap(Map<String, String> dataMap) {
		this.dataMap = dataMap;
	}
	/**
	 * @return dataMapAssisted
	 */
	public Map<String, String> getDataMapAssisted() {
		return dataMapAssisted;
	}
	/**
	 * @param dataMapAssisted
	 */
	public void setDataMapAssisted(Map<String, String> dataMapAssisted) {
		this.dataMapAssisted = dataMapAssisted;
	}
	/**
	 * @return dataMapAssistedMand
	 */
	public Map<String, String> getDataMapAssistedMand() {
		return dataMapAssistedMand;
	}
	/**
	 * @param dataMapAssistedMand
	 */
	public void setDataMapAssistedMand(Map<String, String> dataMapAssistedMand) {
		this.dataMapAssistedMand = dataMapAssistedMand;
	}
	/**
	 * @return dataMapLibrary
	 */
	public Map<String, String> getDataMapLibrary() {
		return dataMapLibrary;
	}
	/**
	 * @param dataMapLibrary
	 */
	public void setDataMapLibrary(Map<String, String> dataMapLibrary) {
		this.dataMapLibrary = dataMapLibrary;
	}
	/**
	 * @return dataMapPackages
	 */
	public Map<String, String> getDataMapPackages() {
		return dataMapPackages;
	}
	/**
	 * @param dataMapPackages
	 */
	public void setDataMapPackages(Map<String, String> dataMapPackages) {
		this.dataMapPackages = dataMapPackages;
	}
	/**
	 * @return dataMapPackagesNew
	 */
	public Map<String, String> getDataMapPackagesNew() {
		return dataMapPackagesNew;
	}
	/**
	 * @param dataMapPackagesNew
	 */
	public void setDataMapPackagesNew(Map<String, String> dataMapPackagesNew) {
		this.dataMapPackagesNew = dataMapPackagesNew;
	}
	
	/**
	 * @return dataMapPackagesIncfeat
	 */
	public Map<String, String> getDataMapPackagesIncfeat() {
		return dataMapPackagesIncfeat;
	}
	/**
	 * @param dataMapPackagesIncfeat
	 */
	public void setDataMapPackagesIncfeat(Map<String, String> dataMapPackagesIncfeat) {
		this.dataMapPackagesIncfeat = dataMapPackagesIncfeat;
	}
	/**
	 * @return dataMapMultiBrowse
	 */
	public Map<String, String> getDataMapMultiBrowse() {
		return dataMapMultiBrowse;
	}
	/**
	 * @param dataMapMultiBrowse
	 */
	public void setDataMapMultiBrowse(Map<String, String> dataMapMultiBrowse) {
		this.dataMapMultiBrowse = dataMapMultiBrowse;
	}
	/**
	 * @return listGrid13
	 */
	public List<Map<String, String>> getListGrid13() {
		return listGrid13;
	}
	/**
	 * @param listGrid13
	 */
	public void setListGrid13(List<Map<String, String>> listGrid13) {
		this.listGrid13 = listGrid13;
	}
	/**
	 * @return listGridAssisted
	 */
	public List<Map<String, String>> getListGridAssisted() {
		return listGridAssisted;
	}
	/**
	 * @param listGridAssisted
	 */
	public void setListGridAssisted(List<Map<String, String>> listGridAssisted) {
		this.listGridAssisted = listGridAssisted;
	}
	/**
	 * @return listGridAssistedMand
	 */
	public List<Map<String, String>> getListGridAssistedMand() {
		return listGridAssistedMand;
	}
	/**
	 * @param listGridAssistedMand
	 */
	public void setListGridAssistedMand(List<Map<String, String>> listGridAssistedMand) {
		this.listGridAssistedMand = listGridAssistedMand;
	}
	/**
	 * @return listGridPackages
	 */
	public List<Map<String, String>> getListGridPackages() {
		return listGridPackages;
	}
	/**
	 * @param listGridPackages
	 */
	public void setListGridPackages(List<Map<String, String>> listGridPackages) {
		this.listGridPackages = listGridPackages;
	}
	
	/**
	 * @return listOfExecutedRules
	 */
	public List<Map<String, String>> getListOfExecutedRules() {
		return listOfExecutedRules;
	}
	/**
	 * @param listOfExecutedRules
	 */
	public void setListOfExecutedRules(List<Map<String, String>> listOfExecutedRules) {
		this.listOfExecutedRules = listOfExecutedRules;
	}
	
	/**
	 * @return listOfIncompatibleFeatures 
	 */
	public List<Map<String, String>> getListOfIncompatibleFeatures() {
		return listOfIncompatibleFeatures;
	}
	/**
	 * @param listOfIncompatibleFeatures
	 */
	public void setListOfIncompatibleFeatures(List<Map<String, String>> listOfIncompatibleFeatures) {
		this.listOfIncompatibleFeatures = listOfIncompatibleFeatures;
	}
	/**
	 * @return listGridMulti
	 */
	public List<Map<String, String>> getListGridMulti() {
		return listGridMulti;
	}
	/**
	 * @param listGridMulti
	 */
	public void setListGridMulti(List<Map<String, String>> listGridMulti) {
		this.listGridMulti = listGridMulti;
	}
	/**
	 * @return listGidlibrary
	 */
	public List<Map<String, String>> getListGidlibrary() {
		return listGidlibrary;
	}
	/**
	 * @param listGidlibrary
	 */
	public void setListGidlibrary(List<Map<String, String>> listGidlibrary) {
		this.listGidlibrary = listGidlibrary;
	}
	/**
	 * @return listassistedAll
	 */
	public List<Map<String, String>> getListassistedAll() {
		return listassistedAll;
	}
	/**
	 * @param listassistedAll
	 */
	public void setListassistedAll(List<Map<String, String>> listassistedAll) {
		this.listassistedAll = listassistedAll;
	}
	/**
	 * @return columnHeaders6
	 */
	public List<String> getColumnHeaders6() {
		return columnHeaders6;
	}
	/**
	 * @param columnHeaders6
	 */
	public void setColumnHeaders6(List<String> columnHeaders6) {
		this.columnHeaders6 = columnHeaders6;
	}
	/**
	 * @return columnHeadersAssisted
	 */
	public List<String> getColumnHeadersAssisted() {
		return columnHeadersAssisted;
	}
	/**
	 * @param columnHeadersAssisted
	 */
	public void setColumnHeadersAssisted(List<String> columnHeadersAssisted) {
		this.columnHeadersAssisted = columnHeadersAssisted;
	}
	/**
	 * @return columnHeadersAssisted
	 */
	public List<String> getColumnpackageHeader() {
		return columnpackageHeader;
	}
	/**
	 * @param columnpackageHeader
	 */
	public void setColumnpackageHeader(List<String> columnpackageHeader) {
		this.columnpackageHeader = columnpackageHeader;
	}
	
	/**
	 * @return columnExecutedRuleHeader
	 */
	public List<String> getColumnExecutedRuleHeader() {
		return columnExecutedRuleHeader;
	}
	/**
	 * @param columnpackageHeadernew
	 */
	public void setColumnExecutedRuleHeader(List<String> columnpackageHeadernew) {
		this.columnExecutedRuleHeader = columnpackageHeadernew;
	}
	
	/**
	 * @return columnIncFeatHeader
	 */
	public List<String> getColumnIncFeatHeader() {
		return columnIncFeatHeader;
	}
	/**
	 * @param columnIncFeatHeader
	 */
	public void setColumnIncFeatHeader(List<String> columnIncFeatHeader) {
		this.columnIncFeatHeader = columnIncFeatHeader;
	}
	/**
	 * @return multiBrowseColHeader
	 */
	public List<String> getMultiBrowseColHeader() {
		return multiBrowseColHeader;
	}
	/**
	 * @param multiBrowseColHeader
	 */
	public void setMultiBrowseColHeader(List<String> multiBrowseColHeader) {
		this.multiBrowseColHeader = multiBrowseColHeader;
	}
	/**
	 * @return columnHeadersLibrary
	 */
	public List<String> getColumnHeadersLibrary() {
		return columnHeadersLibrary;
	}
	/**
	 * @param columnHeadersLibrary
	 */
	public void setColumnHeadersLibrary(List<String> columnHeadersLibrary) {
		this.columnHeadersLibrary = columnHeadersLibrary;
	}

	/**
	 * @return automatedFiles
	 */
	public Set<String> getAutomatedFiles() {
		return automatedFiles;
	}
	/**
	 * @param automatedFiles
	 */
	public void setAutomatedFiles(Set<String> automatedFiles) {
		this.automatedFiles = automatedFiles;
	}
	/**
	 * @return assistedFiles
	 */
	public Set<String> getAssistedFiles() {
		return assistedFiles;
	}
	/**
	 * @param assistedFiles
	 */
	public void setAssistedFiles(Set<String> assistedFiles) {
		this.assistedFiles = assistedFiles;
	}


	Set<String> syntax = new HashSet<>();
	Double effortSaved = 0.00;
	Double effortRequired = 0.00;
	Double mandEffortRequired = 0.00;
	int noOfAutomatedLines = 0;
	int noOfAssistedLines = 0;
	int noOfInfo = 0;
	Double infoEffortRequired = 0.00;
	int noOfSimple = 0;
	Double simEffortRequired = 0.00;
	int noOfMedium = 0;
	Double medEffortRequired = 0.00;
	int noOfComplex = 0;
	Double comEffortRequired = 0.00;
	int noOfMandInfo = 0;
	Double mandInfoEffortRequired = 0.00;
	int noOfMandSimple = 0;
	Double mandSimEffortRequired = 0.00;
	int noOfMandMedium = 0;
	Double mandMedEffortRequired = 0.00;
	int noOfMandComplex = 0;
	Double mandComEffortRequired = 0.00;
	int noOfMandRedesign = 0;
	Double mandredEffortRequired = 0.00;
	int noOfMandArch = 0;
	Double mandArchEffortRequired = 0.00;
	int noOfMandatory = 0;
	int noOfOptional = 0;
	

	/**
	 * @return utility
	 */
	public Utilities getUtility() {
		return utility;
	}
	/**
	 * @param utility
	 */
	public void setUtility(Utilities utility) {
		this.utility = utility;
	}
	/**
	 * @return totalFilesScanned
	 */
	public int getTotalFilesScanned() {
		return totalFilesScanned;
	}
	/**
	 * @param totalFilesScanned
	 */
	public void setTotalFilesScanned(int totalFilesScanned) {
		this.totalFilesScanned = totalFilesScanned;
	}
	/**
	 * @return toJdkVersion
	 */
	public Map<String, String> getToJdkVersion() {
		return toJdkVersion;
	}
	/**
	 * @param toJdkVersion
	 */
	public void setToJdkVersion(Map<String, String> toJdkVersion) {
		this.toJdkVersion = toJdkVersion;
	}
	/**
	 * @return syntax
	 */
	public Set<String> getSyntax() {
		return syntax;
	}
	/**
	 * @param syntax
	 */
	public void setSyntax(Set<String> syntax) {
		this.syntax = syntax;
	}
	/**
	 * @return effortSaved
	 */
	public Double getEffortSaved() {
		return effortSaved;
	}
	/**
	 * @param effortSaved
	 */
	public void setEffortSaved(Double effortSaved) {
		this.effortSaved = effortSaved;
	}
	/**
	 * @return effortRequired
	 */
	public Double getEffortRequired() {
		return effortRequired;
	}
	/**
	 * @param effortRequired
	 */
	public void setEffortRequired(Double effortRequired) {
		this.effortRequired = effortRequired;
	}
	/**
	 * @return mandEffortRequired
	 */
	public Double getMandEffortRequired() {
		return mandEffortRequired;
	}
	/**
	 * @param mandEffortRequired
	 */
	public void setMandEffortRequired(Double mandEffortRequired) {
		this.mandEffortRequired = mandEffortRequired;
	}
	/**
	 * @return noOfAutomatedLines
	 */
	public int getNoOfAutomatedLines() {
		return noOfAutomatedLines;
	}
	/**
	 * @param noOfAutomatedLines
	 */
	public void setNoOfAutomatedLines(int noOfAutomatedLines) {
		this.noOfAutomatedLines = noOfAutomatedLines;
	}
	/**
	 * @return noOfAssistedLines
	 */
	public int getNoOfAssistedLines() {
		return noOfAssistedLines;
	}
	/**
	 * @param noOfAssistedLines
	 */
	public void setNoOfAssistedLines(int noOfAssistedLines) {
		this.noOfAssistedLines = noOfAssistedLines;
	}
	/**
	 * @return noOfInfo
	 */
	public int getNoOfInfo() {
		return noOfInfo;
	}
	/**
	 * @param noOfInfo
	 */
	public void setNoOfInfo(int noOfInfo) {
		this.noOfInfo = noOfInfo;
	}
	/**
	 * @return infoEffortRequired
	 */
	public Double getInfoEffortRequired() {
		return infoEffortRequired;
	}
	/**
	 * @param infoEffortRequired
	 */
	public void setInfoEffortRequired(Double infoEffortRequired) {
		this.infoEffortRequired = infoEffortRequired;
	}
	/**
	 * @return noOfSimple
	 */
	public int getNoOfSimple() {
		return noOfSimple;
	}
	/**
	 * @param noOfSimple
	 */
	public void setNoOfSimple(int noOfSimple) {
		this.noOfSimple = noOfSimple;
	}
	/**
	 * @return simEffortRequired
	 */
	public Double getSimEffortRequired() {
		return simEffortRequired;
	}
	/**
	 * @param simEffortRequired
	 */
	public void setSimEffortRequired(Double simEffortRequired) {
		this.simEffortRequired = simEffortRequired;
	}
	/**
	 * @return noOfMedium
	 */
	public int getNoOfMedium() {
		return noOfMedium;
	}
	/**
	 * @param noOfMedium
	 */
	public void setNoOfMedium(int noOfMedium) {
		this.noOfMedium = noOfMedium;
	}
	/**
	 * @return medEffortRequired
	 */
	public Double getMedEffortRequired() {
		return medEffortRequired;
	}
	/**
	 * @param medEffortRequired
	 */
	public void setMedEffortRequired(Double medEffortRequired) {
		this.medEffortRequired = medEffortRequired;
	}
	/**
	 * @return noOfComplex
	 */
	public int getNoOfComplex() {
		return noOfComplex;
	}
	/**
	 * @param noOfComplex
	 */
	public void setNoOfComplex(int noOfComplex) {
		this.noOfComplex = noOfComplex;
	}
	/**
	 * @return comEffortRequired
	 */
	public Double getComEffortRequired() {
		return comEffortRequired;
	}
	/**
	 * @param comEffortRequired
	 */
	public void setComEffortRequired(Double comEffortRequired) {
		this.comEffortRequired = comEffortRequired;
	}
	
	/**
	 * @return noOfMandInfo
	 */
	public int getNoOfMandInfo() {
		return noOfMandInfo;
	}
	/**
	 * @param noOfMandInfo
	 */
	public void setNoOfMandInfo(int noOfMandInfo) {
		this.noOfMandInfo = noOfMandInfo;
	}
	/**
	 * @return mandInfoEffortRequired
	 */
	public Double getMandInfoEffortRequired() {
		return mandInfoEffortRequired;
	}
	/**
	 * @param mandInfoEffortRequired
	 */
	public void setMandInfoEffortRequired(Double mandInfoEffortRequired) {
		this.mandInfoEffortRequired = mandInfoEffortRequired;
	}
	/**
	 * @return noOfMandSimple
	 */
	public int getNoOfMandSimple() {
		return noOfMandSimple;
	}
	/**
	 * @param noOfMandSimple
	 */
	public void setNoOfMandSimple(int noOfMandSimple) {
		this.noOfMandSimple = noOfMandSimple;
	}
	/**
	 * @return mandSimEffortRequired
	 */
	public Double getMandSimEffortRequired() {
		return mandSimEffortRequired;
	}
	/**
	 * @param mandSimEffortRequired
	 */
	public void setMandSimEffortRequired(Double mandSimEffortRequired) {
		this.mandSimEffortRequired = mandSimEffortRequired;
	}
	/**
	 * @return noOfMandMedium
	 */
	public int getNoOfMandMedium() {
		return noOfMandMedium;
	}
	/**
	 * @param noOfMandMedium
	 */
	public void setNoOfMandMedium(int noOfMandMedium) {
		this.noOfMandMedium = noOfMandMedium;
	}
	/**
	 * @return mandMedEffortRequired
	 */
	public Double getMandMedEffortRequired() {
		return mandMedEffortRequired;
	}
	/**
	 * @param mandMedEffortRequired
	 */
	public void setMandMedEffortRequired(Double mandMedEffortRequired) {
		this.mandMedEffortRequired = mandMedEffortRequired;
	}
	/**
	 * @return noOfMandComplex
	 */
	public int getNoOfMandComplex() {
		return noOfMandComplex;
	}
	/**
	 * @param noOfMandComplex
	 */
	public void setNoOfMandComplex(int noOfMandComplex) {
		this.noOfMandComplex = noOfMandComplex;
	}
	/**
	 * @return mandComEffortRequired
	 */
	public Double getMandComEffortRequired() {
		return mandComEffortRequired;
	}
	/**
	 * @param mandComEffortRequired
	 */
	public void setMandComEffortRequired(Double mandComEffortRequired) {
		this.mandComEffortRequired = mandComEffortRequired;
	}
	/**
	 * @return noOfMandRedesign
	 */
	public int getNoOfMandRedesign() {
		return noOfMandRedesign;
	}
	/**
	 * @param noOfMandRedesign
	 */
	public void setNoOfMandRedesign(int noOfMandRedesign) {
		this.noOfMandRedesign = noOfMandRedesign;
	}
	/**
	 * @return mandredEffortRequired
	 */
	public Double getMandredEffortRequired() {
		return mandredEffortRequired;
	}
	/**
	 * @param mandredEffortRequired
	 */
	public void setMandredEffortRequired(Double mandredEffortRequired) {
		this.mandredEffortRequired = mandredEffortRequired;
	}
	/**
	 * @return noOfMandArch
	 */
	public int getNoOfMandArch() {
		return noOfMandArch;
	}
	/**
	 * @param noOfMandArch
	 */
	public void setNoOfMandArch(int noOfMandArch) {
		this.noOfMandArch = noOfMandArch;
	}
	/**
	 * @return mandArchEffortRequired
	 */
	public Double getMandArchEffortRequired() {
		return mandArchEffortRequired;
	}
	/**
	 * @param mandArchEffortRequired
	 */
	public void setMandArchEffortRequired(Double mandArchEffortRequired) {
		this.mandArchEffortRequired = mandArchEffortRequired;
	}
	/**
	 * @return noOfMandatory
	 */
	public int getNoOfMandatory() {
		return noOfMandatory;
	}
	/**
	 * @param noOfMandatory
	 */
	public void setNoOfMandatory(int noOfMandatory) {
		this.noOfMandatory = noOfMandatory;
	}
	/**
	 * @return
	 */
	public int getNoOfOptional() {
		return noOfOptional;
	}
	/**
	 * @param noOfOptional
	 */
	public void setNoOfOptional(int noOfOptional) {
		this.noOfOptional = noOfOptional;
	}
	
	/**
	 * @param uploadedAppPath
	 * @param fromVersion
	 * @param toVersion
	 * @param progress
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public synchronized void getAllFiles(String uploadedAppPath,String fromVersion,String toVersion, Progress progress) throws IOException, XmlPullParserException
	{
		utility.rules( fromVersion);
		utility.rulesToVersion(toVersion);
		File uploadfile= new File(uploadedAppPath);
		JsonFileReader jsonfileReader = new JsonFileReader();

		jsonfileReader.loadPattern(toVersion);
		for(Rule rule:utility.getJdkToVersion()) {
			toJdkVersion.put(rule.getName(), rule.getValue());
		}
		
		parsingJson.displayDirectoryContents(uploadfile,jsonfileReader.getDataListJDK8(),jsonfileReader.getDataListJDK11(),jsonfileReader.getDataListJDK8thirdparty(),jsonfileReader.getDataListJDK11thirdparty(), toVersion, progress);
		
		totalFilesScanned=parsingJson.getTotalFilesCount();
	}
	public void getJDKInstance()
	{
		//to find the uploaded project JDK version
	}
	public void getTargetJDKInstance()
	{
		
		//build the Target project after Modifications
	}
	
	
	/**
	 * @synchronized
	 */
	public synchronized void parseFiles()
	{
		
		//Header for Automated
		columnHeaders6.add(0, FILE_NAME);
		columnHeaders6.add(1, LINE_NO);
		columnHeaders6.add(2, INCOMPATIBILITY);
		columnHeaders6.add(3, "Replacement");
		columnHeaders6.add(4, "Migration Status");
	
		//Header for assissted
		columnHeadersAssisted.add(0, FILE_NAME);
		columnHeadersAssisted.add(1, LINE_NO);
		columnHeadersAssisted.add(2, INCOMPATIBILITY);
		columnHeadersAssisted.add(3, "Suggested Replacement");
		columnHeadersAssisted.add(4, "References");
		columnHeadersAssisted.add(5, "Complexity");
		columnHeadersAssisted.add(6, "Package");
		columnHeadersAssisted.add(7, "Category");
	
		//Header for java package
		columnpackageHeader.add(0, "Changes By Java Packages");
		columnpackageHeader.add(1, "Instances");
		
		//Header for java package
		columnExecutedRuleHeader.add(0, "Package");
		columnExecutedRuleHeader.add(1, "Type");
		columnExecutedRuleHeader.add(2, "Number of Rules");
		columnExecutedRuleHeader.add(3, "Matches Found");
		
		//Header for Incompatible features
		columnIncFeatHeader.add(0,"Feature");
		columnIncFeatHeader.add(1,"Matches Found");
		
		//Third party library
	
		
		//header for Third party jars
		columnHeadersLibrary.add(0, "Package");
		columnHeadersLibrary.add(1, FILE_NAME);
		columnHeadersLibrary.add(2, LINE_NO);
		columnHeadersLibrary.add(3, INCOMPATIBILITY);
		columnHeadersLibrary.add(4, "Suggested Replacement");
		columnHeadersLibrary.add(5, "References");
		columnHeadersLibrary.add(6, "Complexity");
		
		MyPropertyLoad load = new MyPropertyLoad();
		load.loadRule("effort");
		HashMap<String,Double> hmEffort = new HashMap<>();
		for (Enumeration<?> e = load.getProp().propertyNames(); e.hasMoreElements();) {
			String name = (String) e.nextElement();
			String value = load.getProp().getProperty(name);
			hmEffort.put(name,Double.valueOf(value));
		}
		Map<String,LinkedList<Match>> fileList = parsingJson.getHmPackJava();
		Iterator itr = fileList.keySet().iterator();
	
		int frameCount=0;
		String strpackage = null;
		Integer intpackage = null;
		while(itr.hasNext()) {
			frameCount = parseFilesCount(hmEffort, fileList, itr, frameCount);


		
		}
		//adding number of jars
		if(parsingJson.getJarlist() != null && !parsingJson.getJarlist().isEmpty()) {
			Integer noOfJars = parsingJson.getJarlist().size();
			dataMapPackages = new LinkedHashMap<>();
			dataMapPackages.put(columnpackageHeader.get(0), "Jars");
			dataMapPackages.put(columnpackageHeader.get(1), noOfJars.toString());
			listGridPackages.add(dataMapPackages);
		}

		//populating the package structure
		Map<String, Map<String, Integer[]>> hmJavaPackageDetails = parsingJson.getHmJavaPackageDetails();
		Iterator itrhm = hmJavaPackageDetails.keySet().iterator();
		String strPackage1 = null;
		while(itrhm.hasNext()){
			strPackage1 = itrhm.next().toString();
			Map<String, Integer[]> hmType = hmJavaPackageDetails.get(strPackage1);
			Iterator itrhmType = hmType.keySet().iterator();
			while(itrhmType.hasNext()) {
				String type = itrhmType.next().toString();
				Integer[] count = hmType.get(type);
				dataMapPackagesNew = new LinkedHashMap<>();
				dataMapPackagesNew.put(columnExecutedRuleHeader.get(0), strPackage1);
				dataMapPackagesNew.put(columnExecutedRuleHeader.get(1), type);
				dataMapPackagesNew.put(columnExecutedRuleHeader.get(2), count[0].toString());
				dataMapPackagesNew.put(columnExecutedRuleHeader.get(3), count[1].toString());
				listOfExecutedRules.add(dataMapPackagesNew);
			}

		}
		Map<String, Integer> hmIncomaptFeatureDetails = parsingJson.getHmIncomaptFeatureDetails();
		Iterator itrinfeat = hmIncomaptFeatureDetails.keySet().iterator();
		String strIncFeat = null;
		while(itrinfeat.hasNext()){
			strIncFeat = itrinfeat.next().toString();
			dataMapPackagesIncfeat = new LinkedHashMap<>();
			dataMapPackagesIncfeat.put(columnIncFeatHeader.get(0), strIncFeat);
			dataMapPackagesIncfeat.put(columnIncFeatHeader.get(1), hmIncomaptFeatureDetails.get(strIncFeat).toString());
				listOfIncompatibleFeatures.add(dataMapPackagesIncfeat);
			}

		
		//Combining the optional and mandatory
		//If there no chnages related to java, only framework changes are there
		if(frameCount==0 && frameCount<1)
		{
			frameWorkGrid(columnHeadersLibrary, frameCount);
		}
	}
	/**
	 * @param hmEffort
	 * @param fileList
	 * @param itr
	 * @param frameCount
	 * @return
	 */
	private int parseFilesCount(HashMap<String, Double> hmEffort, Map<String, LinkedList<Match>> fileList, Iterator itr,
			int frameCount) {
		String strpackage;
		Integer intpackage;
		strpackage = itr.next().toString();
		List<Match> remedylist = fileList.get(strpackage);
		intpackage = remedylist.size();
for (int i = 0; i < intpackage; i++) {
		
		
		//automated
		if(remedylist.get(i).getIsAutomated().equals("Y"))
		{
		dataMapRemedy(remedylist, i);
		}
		//assisted
		else
		{
			

			//validate third party libraries
			frameCount = frameWorkGrid(columnHeadersLibrary, frameCount);
			
			
			
			if(("Optional").equalsIgnoreCase(remedylist.get(i).getMandOpt())) {
				
				dataMapAssisted= new LinkedHashMap<>();
				dataMapAssisted.put(columnHeadersAssisted.get(0), remedylist.get(i).getFileName());
				String lineNumber = String.valueOf(remedylist.get(i).getLineNumber());
				dataMapAssisted.put(columnHeadersAssisted.get(1), lineNumber);
				dataMapAssisted.put(columnHeadersAssisted.get(2), remedylist.get(i).getImpactLine().trim());
				dataMapAssisted.put(columnHeadersAssisted.get(6), strpackage);
				dataMapAssisted.put(columnHeadersAssisted.get(7), remedylist.get(i).getMandOpt());
			if(remedylist.get(i).getSupportVersion().trim().equalsIgnoreCase("No Replacement")) {
				dataMapAssisted.put(columnHeadersAssisted.get(3), "This API is deprecated. No direct replacement is available in higher JDK versions. Please change the code manually as per the target JDK syntax.");
				dataMapAssisted.put(columnHeadersAssisted.get(4), "https://docs.oracle.com/en/java/javase/11/docs/api/deprecated-list.html");
			}else {
				
				

					dataMapAssisted.put(columnHeadersAssisted.get(3), remedylist.get(i).getSupportVersion());
					dataMapAssisted.put(columnHeadersAssisted.get(4), remedylist.get(i).getReference());
				
			}
			

			noOfOptional = noOfOptional+1;
			if(remedylist.get(i).getComplexity().equalsIgnoreCase("0")) {
				parseFilesMap(hmEffort, remedylist, i);
			}else if(remedylist.get(i).getComplexity().equalsIgnoreCase("1")) {
				if(syntax.add((remedylist.get(i).getSyntaxId()))){
				effortRequired =  effortRequired+ hmEffort.get(SIMPLE);
				simEffortRequired = simEffortRequired+ hmEffort.get(SIMPLE);
				}else {
				effortRequired =  effortRequired+ hmEffort.get(SIMPLE)*0.2;
				simEffortRequired = simEffortRequired+ hmEffort.get(SIMPLE)*0.2;
				}
				noOfSimple = noOfSimple+1;
				dataMapAssisted.put(columnHeadersAssisted.get(5), "Simple");
			}else if(remedylist.get(i).getComplexity().equalsIgnoreCase("2")) {
				if(syntax.add((remedylist.get(i).getSyntaxId()))){
				effortRequired =  effortRequired+ hmEffort.get(MEDIUM);
				medEffortRequired = medEffortRequired+ hmEffort.get(MEDIUM);
				}else {
				effortRequired =  effortRequired+ hmEffort.get(MEDIUM)*0.2;
				medEffortRequired = medEffortRequired+ hmEffort.get(MEDIUM)*0.2;
				}
				noOfMedium = noOfMedium+1;
				dataMapAssisted.put(columnHeadersAssisted.get(5), "Medium");
			}else if(remedylist.get(i).getComplexity().equalsIgnoreCase("3")){
				if(syntax.add((remedylist.get(i).getSyntaxId()))){
				effortRequired =  effortRequired+ hmEffort.get(COMPLEX);
				comEffortRequired = comEffortRequired+hmEffort.get(COMPLEX);
				}else {
				effortRequired =  effortRequired+ hmEffort.get(COMPLEX)*0.2;
				comEffortRequired = comEffortRequired+hmEffort.get(COMPLEX)*0.2;
				}
				noOfComplex = noOfComplex+1;
				dataMapAssisted.put(columnHeadersAssisted.get(5), "Complex");
			}
			listGridAssisted.add(dataMapAssisted);
			listassistedAll.add(dataMapAssisted);
			}else if(("Mandatory").equalsIgnoreCase(remedylist.get(i).getMandOpt())){
				dataMapAssistedMand = new LinkedHashMap<>();
				dataMapAssistedMand.put(columnHeadersAssisted.get(0), remedylist.get(i).getFileName());
				String lineNumber = String.valueOf(remedylist.get(i).getLineNumber());
				dataMapAssistedMand.put(columnHeadersAssisted.get(1), lineNumber);
				dataMapAssistedMand.put(columnHeadersAssisted.get(2), remedylist.get(i).getImpactLine().trim());
				dataMapAssistedMand.put(columnHeadersAssisted.get(6), strpackage);
				dataMapAssistedMand.put(columnHeadersAssisted.get(7), remedylist.get(i).getMandOpt());
				if(remedylist.get(i).getSupportVersion().trim().equalsIgnoreCase("No Replacement")) {
					dataMapAssistedMand.put(columnHeadersAssisted.get(3), "This API is deprecated. No direct replacement is available in higher JDK versions. Please change the code manually as per the target JDK syntax.");
					dataMapAssistedMand.put(columnHeadersAssisted.get(4), "https://docs.oracle.com/en/java/javase/11/docs/api/deprecated-list.html");
				}else {
					dataMapAssistedMand.put(columnHeadersAssisted.get(3), remedylist.get(i).getSupportVersion());
					dataMapAssistedMand.put(columnHeadersAssisted.get(4), remedylist.get(i).getReference());
					
				}
				
				//Effort Calculation
				

				noOfMandatory = noOfMandatory+1;
				if(remedylist.get(i).getComplexity().equalsIgnoreCase("0")) {
					if(syntax.add((remedylist.get(i).getSyntaxId()))){
						mandEffortRequired =  mandEffortRequired+ hmEffort.get("info");
						mandInfoEffortRequired= mandInfoEffortRequired+hmEffort.get("info");
					}else {
						mandEffortRequired =  mandEffortRequired+ hmEffort.get("info")*0.2;
						mandInfoEffortRequired= mandInfoEffortRequired+hmEffort.get("info")*0.2;
					}
					
					noOfMandInfo = noOfMandInfo+1;
					dataMapAssistedMand.put(columnHeadersAssisted.get(5), "Info");
				}else if(remedylist.get(i).getComplexity().equalsIgnoreCase("1")) {
					if(syntax.add((remedylist.get(i).getSyntaxId()))){
						mandEffortRequired =  mandEffortRequired+ hmEffort.get(SIMPLE);
						mandSimEffortRequired= mandSimEffortRequired+hmEffort.get(SIMPLE);
					}else {
						mandEffortRequired =  mandEffortRequired+ hmEffort.get(SIMPLE)*0.2;
						mandSimEffortRequired= mandSimEffortRequired+hmEffort.get(SIMPLE)*0.2;
					}
					
					noOfMandSimple = noOfMandSimple+1;
					dataMapAssistedMand.put(columnHeadersAssisted.get(5), "Simple");
				}else if(remedylist.get(i).getComplexity().equalsIgnoreCase("2")) {
					if(syntax.add((remedylist.get(i).getSyntaxId()))){
						mandEffortRequired =  mandEffortRequired+ hmEffort.get(MEDIUM);
						mandMedEffortRequired = mandMedEffortRequired+ hmEffort.get(MEDIUM);
					}else {
						mandEffortRequired =  mandEffortRequired+ hmEffort.get(MEDIUM)*0.2;
						mandMedEffortRequired = mandMedEffortRequired+ hmEffort.get(MEDIUM)*0.2;
					}
					noOfMandMedium = noOfMandMedium+1;
					dataMapAssistedMand.put(columnHeadersAssisted.get(5), "Medium");
				}else if(remedylist.get(i).getComplexity().equalsIgnoreCase("3")){
					if(syntax.add((remedylist.get(i).getSyntaxId()))){
						mandEffortRequired =  mandEffortRequired+ hmEffort.get(COMPLEX);
						mandComEffortRequired = mandComEffortRequired+ hmEffort.get(COMPLEX);
					}else {
						mandEffortRequired =  mandEffortRequired+ hmEffort.get(COMPLEX)*0.2;
						mandComEffortRequired = mandComEffortRequired+ hmEffort.get(COMPLEX)*0.2;
					}
					noOfMandComplex = noOfMandComplex+1;
					dataMapAssistedMand.put(columnHeadersAssisted.get(5), "Complex");
				}else if(remedylist.get(i).getComplexity().equalsIgnoreCase("4")){
					if(syntax.add((remedylist.get(i).getSyntaxId()))){
						mandEffortRequired =  mandEffortRequired+ hmEffort.get(REDESIGN);
						mandredEffortRequired = mandredEffortRequired+ hmEffort.get(REDESIGN);
					}else {
						mandEffortRequired =  mandEffortRequired+ hmEffort.get(REDESIGN)*0.2;
						mandredEffortRequired = mandredEffortRequired+ hmEffort.get(REDESIGN)*0.2;
					}
					noOfMandRedesign = noOfMandRedesign+1;
					dataMapAssistedMand.put(columnHeadersAssisted.get(5), "Redesign");
				}else if(remedylist.get(i).getComplexity().equalsIgnoreCase("5")){
					if(syntax.add((remedylist.get(i).getSyntaxId()))){
						mandEffortRequired =  mandEffortRequired+ hmEffort.get(FRAMEWORK);
						mandArchEffortRequired = mandArchEffortRequired+ hmEffort.get(FRAMEWORK);
					}else {
						mandEffortRequired =  mandEffortRequired+ hmEffort.get(FRAMEWORK)*0.2;
						mandArchEffortRequired = mandArchEffortRequired+ hmEffort.get(FRAMEWORK)*0.2;
					}
					noOfMandArch = noOfMandArch+1;
					dataMapAssistedMand.put(columnHeadersAssisted.get(5), "Architecture");
				}
			
				listGridAssistedMand.add(dataMapAssistedMand);
				listassistedAll.add(dataMapAssistedMand);
			}

			//keeping list of automated files
			assistedFiles.add(remedylist.get(i).getFileName());
			noOfAssistedLines = noOfAssistedLines+1;
		}
}

dataMapPackages = new LinkedHashMap<>();
dataMapPackages.put(columnpackageHeader.get(0), strpackage);
dataMapPackages.put(columnpackageHeader.get(1), intpackage.toString());
listGridPackages.add(dataMapPackages);
		return frameCount;
	}
	/**
	 * @param hmEffort
	 * @param remedylist
	 * @param i
	 */
	private void parseFilesMap(HashMap<String, Double> hmEffort, List<Match> remedylist, int i) {
		if(syntax.add((remedylist.get(i).getSyntaxId()))){
		effortRequired =  effortRequired+ hmEffort.get("info");
		infoEffortRequired = infoEffortRequired+ hmEffort.get("info");
		}else {
		effortRequired =  effortRequired+ hmEffort.get("info")*0.2;
		infoEffortRequired = infoEffortRequired+ hmEffort.get("info")*0.2;
		}
		noOfInfo = noOfInfo+1;
		dataMapAssisted.put(columnHeadersAssisted.get(5), "Info");
	}
	/**
	 * @param remedylist
	 * @param i
	 */
	private void dataMapRemedy(List<Match> remedylist, int i) {
		dataMap = new LinkedHashMap<>();
		dataMap.put(columnHeaders6.get(0), remedylist.get(i).getFileName());
		String lineNumber = String.valueOf(remedylist.get(i).getLineNumber());
		dataMap.put(columnHeaders6.get(1), lineNumber);
		dataMap.put(columnHeaders6.get(2), remedylist.get(i).getImpactLine());
		dataMap.put(columnHeaders6.get(3), remedylist.get(i).getSupportVersion());
		dataMap.put(columnHeaders6.get(4), "Success");
		
		//keeping list of automated files
		automatedFiles.add(remedylist.get(i).getFileName());
		noOfAutomatedLines= noOfAutomatedLines+1;
		listGrid13.add(dataMap);
	}
	
	
	/**
	 * @param columnHeadersLibrary
	 * @param frameCount
	 * @return
	 */
	private int frameWorkGrid(List<String> columnHeadersLibrary,  int frameCount) {
		if(frameCount==0 && frameCount<1)
		{
			frameCount++;
	
			for(Match match:parsingJson.getJarlist()) {
				dataMapLibrary= new LinkedHashMap<>();
				dataMapLibrary.put(columnHeadersLibrary.get(0), "jar");
				dataMapLibrary.put(columnHeadersLibrary.get(1), match.getFileName());
				dataMapLibrary.put(columnHeadersLibrary.get(2), "NA");
				dataMapLibrary.put(columnHeadersLibrary.get(3), "NA");
				dataMapLibrary.put(columnHeadersLibrary.get(4), "If this a third party jar, please validate it's compatibility with current target JDK version. Ignore if not a third party jar.");
				dataMapLibrary.put(columnHeadersLibrary.get(5), "https://mvnrepository.com/repos");
				dataMapLibrary.put(columnHeadersLibrary.get(6), "");
				listGidlibrary.add(dataMapLibrary);
			}
			
			
		}
		return frameCount;
	}
	
	/**
	 * @param pathList
	 */
	public void multiBrowseAnalysis(List<String> pathList)
	{
	
		multiBrowseColHeader.add(0, APPLICATION_PATH);
		
		for(int i=0;i<pathList.size();i++)
		{
			dataMapMultiBrowse= new LinkedHashMap<>();
				View.logger.info("pathList1-->"+pathList.get(i));
		if(pathList.get(i) != null && !pathList.get(i).equals(""))	{
		dataMapMultiBrowse.put(APPLICATION_PATH, pathList.get(i));
		listGridMulti.add(dataMapMultiBrowse);
		}
		}
		
		
	}
	
	/**
	 * @param pathList
	 */
	public void multiBrowsePaths(List<String[]> pathList)
	{
	
		multiBrowseColHeader.add(0, APPLICATION_PATH);
		
		for(int i=0;i<pathList.size();i++)
		{
			dataMapMultiBrowse= new LinkedHashMap<>();
				View.logger.info("pathList1-->"+pathList.get(i));
		if(pathList.get(i) != null && !pathList.get(i)[0].equals(""))	{
		dataMapMultiBrowse.put(APPLICATION_PATH, pathList.get(i)[0]);
		listGridMulti.add(dataMapMultiBrowse);
		}
		}
		
		
	}
	


}
