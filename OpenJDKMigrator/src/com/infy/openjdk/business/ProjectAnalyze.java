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

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.infy.openjdk.configuration.MyPropertyLoad;
import com.infy.openjdk.ui.View;

public class ProjectAnalyze {
	private static final String ESTIMATED_ASSISTED_REMEDIATION_EFFORT = "Estimated Assisted Remediation Effort";
	private static final String SIMPLE = "Simple";
	private static final String MEDIUM = "Medium";
	private static final String IMPACTED_FILES = "Impacted Files";
	private static final String FILES_SCANNED = "Files Scanned";
	private static final String EFFORT = "Effort";
	private static final String COMPLEXITY = "Complexity";
	private static final String COMPLEX = "Complex";
	private static final String CATEGORY = "Category";
	private static final String AUTOMATICALLY_REMEDIATED_FILES = "Automatically Remediated Files";
	private static final String AUTOMATED_REMEDIATION_INSTANCES = "Automated Remediation Instances";
	private static final String AUTOMATED_REMEDIATION_FILES = "Automated Remediation Files";
	private static final String ASSISTED_REMEDIATION_INSTANCES = "Assisted Remediation Instances";
	private static final String ASSISTED_REMEDIATION_FILES = "Assisted Remediation Files";
	private static final String ASSISTED_INSTANCES = "Assisted Instances";
	private static final String ASSISTED_FILES = "Assisted Files";
	private static final String APPLICATION = "Application";
	Map<String, Integer> dataMap = new LinkedHashMap<>();
	Map<String, String> dataMap1 = new LinkedHashMap<>();
	Map<String, String> dataMap2 = new LinkedHashMap<>();
	Integer totalCount = 0;
	List<String> summaryColumnHeaders = new ArrayList<>();
	List<String> summaryColumnHeaders1 = new ArrayList<>();
	List<String> migrationPercentHeaders = new ArrayList<>();
	List<String> effortHeaders = new ArrayList<>();
	List<String> effortHeaderspie = new ArrayList<>();
	List<String> effortBreakHeaders = new ArrayList<>();
	List<Map<String, String>> listGrid13 = new ArrayList<>();
	List<Map<String, Integer>> listGrid14 = new ArrayList<>();
	List<Map<String, String>> listGrid15 = new ArrayList<>();
	List<Map<String, String>> listGrid16 = new ArrayList<>();
	List<Map<String, String>> listGrid17 = new ArrayList<>();
	List<Map<String, String>> listGrid18 = new ArrayList<>();
	List<Map<String, String>> listGrid19 = new ArrayList<>();
	List<Map<String, String>> listGrid20 = new ArrayList<>();
	List<List<Map<String, Integer>>> list2 = new LinkedList<>();
	List<List<Map<String, String>>> list3 = new LinkedList<>();
	List<List<Map<String, String>>> list5 = new LinkedList<>();
	List<List<Map<String, String>>> list6 = new LinkedList<>();
	List<List<Map<String, String>>> list7 = new LinkedList<>();
	List<String> automatedHeaderBatch = new ArrayList<>(
			Arrays.asList(APPLICATION,FILES_SCANNED, IMPACTED_FILES, AUTOMATED_REMEDIATION_FILES, AUTOMATED_REMEDIATION_INSTANCES,ASSISTED_REMEDIATION_FILES,ASSISTED_REMEDIATION_INSTANCES));
	List<String> summaryColumnHeadersbatch =  new ArrayList<>(automatedHeaderBatch);
	List<String> summaryHeaderBatch = new ArrayList<>(
			Arrays.asList("Total Applications Scanned","Total Files Scanned", "Total Impacted Files", "Total Automated Remediation Files", "Total Assisted Remediation Files"));
	List<String> arrEffortHeadersBatch = new ArrayList<>(
			Arrays.asList(APPLICATION,SIMPLE,MEDIUM,COMPLEX,ASSISTED_REMEDIATION_INSTANCES,ESTIMATED_ASSISTED_REMEDIATION_EFFORT));
	List<String> automatedHeaderMigrationBatch = new ArrayList<>(
			Arrays.asList(APPLICATION, AUTOMATICALLY_REMEDIATED_FILES));
	List<String> summaryColumnHeadersMigrationbatch =  new ArrayList<>(automatedHeaderMigrationBatch);
	List<String> effortHeadersBatch = new ArrayList<>(arrEffortHeadersBatch);		

	/**
	 * @return
	 */
	public Map<String, Integer> getDataMap() {
		return dataMap;
	}
	/**
	 * @param dataMap
	 */
	public void setDataMap(Map<String, Integer> dataMap) {
		this.dataMap = dataMap;
	}
	/**
	 * @return
	 */
	public Map<String, String> getDataMap1() {
		return dataMap1;
	}
	/**
	 * @param dataMap1
	 */
	public void setDataMap1(Map<String, String> dataMap1) {
		this.dataMap1 = dataMap1;
	}
	/**
	 * @return
	 */
	public Map<String, String> getDataMap2() {
		return dataMap2;
	}
	/**
	 * @param dataMap2
	 */
	public void setDataMap2(Map<String, String> dataMap2) {
		this.dataMap2 = dataMap2;
	}
	/**
	 * @return
	 */
	public Integer getTotalCount() {
		return totalCount;
	}
	/**
	 * @param totalCount
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * @return
	 */
	public List<String> getSummaryColumnHeaders() {
		return summaryColumnHeaders;
	}
	/**
	 * @param summaryColumnHeaders
	 */
	public void setSummaryColumnHeaders(List<String> summaryColumnHeaders) {
		this.summaryColumnHeaders = summaryColumnHeaders;
	}
	/**
	 * @return
	 */
	public List<String> getSummaryColumnHeaders1() {
		return summaryColumnHeaders1;
	}
	/**
	 * @param summaryColumnHeaders1
	 */
	public void setSummaryColumnHeaders1(List<String> summaryColumnHeaders1) {
		this.summaryColumnHeaders1 = summaryColumnHeaders1;
	}
	/**
	 * @return
	 */
	public List<String> getMigrationPercentHeaders() {
		return migrationPercentHeaders;
	}
	/**
	 * @param migrationPercentHeaders
	 */
	public void setMigrationPercentHeaders(List<String> migrationPercentHeaders) {
		this.migrationPercentHeaders = migrationPercentHeaders;
	}
	/**
	 * @return
	 */
	public List<String> getEffortHeaders() {
		return effortHeaders;
	}
	/**
	 * @param effortHeaders
	 */
	public void setEffortHeaders(List<String> effortHeaders) {
		this.effortHeaders = effortHeaders;
	}
	/**
	 * @return
	 */
	public List<String> getEffortHeaderspie() {
		return effortHeaderspie;
	}
	/**
	 * @param effortHeaderspie
	 */
	public void setEffortHeaderspie(List<String> effortHeaderspie) {
		this.effortHeaderspie = effortHeaderspie;
	}
	/**
	 * @return
	 */
	public List<String> getEffortBreakHeaders() {
		return effortBreakHeaders;
	}
	/**
	 * @param effortBreakHeaders
	 */
	public void setEffortBreakHeaders(List<String> effortBreakHeaders) {
		this.effortBreakHeaders = effortBreakHeaders;
	}
	/**
	 * @return
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
	 * @return
	 */
	public List<Map<String, Integer>> getListGrid14() {
		return listGrid14;
	}
	/**
	 * @param listGrid14
	 */
	public void setListGrid14(List<Map<String, Integer>> listGrid14) {
		this.listGrid14 = listGrid14;
	}
	/**
	 * @return
	 */
	public List<Map<String, String>> getListGrid15() {
		return listGrid15;
	}
	/**
	 * @param listGrid15
	 */
	public void setListGrid15(List<Map<String, String>> listGrid15) {
		this.listGrid15 = listGrid15;
	}
	/**
	 * @return
	 */
	public List<Map<String, String>> getListGrid16() {
		return listGrid16;
	}
	/**
	 * @param listGrid16
	 */
	public void setListGrid16(List<Map<String, String>> listGrid16) {
		this.listGrid16 = listGrid16;
	}
	/**
	 * @return
	 */
	public List<Map<String, String>> getListGrid17() {
		return listGrid17;
	}
	/**
	 * @param listGrid17
	 */
	public void setListGrid17(List<Map<String, String>> listGrid17) {
		this.listGrid17 = listGrid17;
	}
	/**
	 * @return
	 */
	public List<Map<String, String>> getListGrid18() {
		return listGrid18;
	}
	/**
	 * @param listGrid18
	 */
	public void setListGrid18(List<Map<String, String>> listGrid18) {
		this.listGrid18 = listGrid18;
	}
	/**
	 * @return
	 */
	public List<Map<String, String>> getListGrid19() {
		return listGrid19;
	}
	/**
	 * @param listGrid19
	 */
	public void setListGrid19(List<Map<String, String>> listGrid19) {
		this.listGrid19 = listGrid19;
	}
	/**
	 * @return
	 */
	public List<Map<String, String>> getListGrid20() {
		return listGrid20;
	}
	/**
	 * @param listGrid20
	 */
	public void setListGrid20(List<Map<String, String>> listGrid20) {
		this.listGrid20 = listGrid20;
	}
	/**
	 * @return
	 */
	public List<List<Map<String, Integer>>> getList2() {
		return list2;
	}
	/**
	 * @param list2
	 */
	public void setList2(List<List<Map<String, Integer>>> list2) {
		this.list2 = list2;
	}
	/**
	 * @return
	 */
	public List<List<Map<String, String>>> getList3() {
		return list3;
	}
	/**
	 * @param list3
	 */
	public void setList3(List<List<Map<String, String>>> list3) {
		this.list3 = list3;
	}
	/**
	 * @return
	 */
	public List<List<Map<String, String>>> getList5() {
		return list5;
	}
	/**
	 * @param list5
	 */
	public void setList5(List<List<Map<String, String>>> list5) {
		this.list5 = list5;
	}
	/**
	 * @return
	 */
	public List<List<Map<String, String>>> getList6() {
		return list6;
	}
	/**
	 * @param list6
	 */
	public void setList6(List<List<Map<String, String>>> list6) {
		this.list6 = list6;
	}
	/**
	 * @return
	 */
	public List<List<Map<String, String>>> getList7() {
		return list7;
	}
	/**
	 * @param list7
	 */
	public void setList7(List<List<Map<String, String>>> list7) {
		this.list7 = list7;
	}
	/**
	 * @return
	 */
	public List<String> getAutomatedHeaderBatch() {
		return automatedHeaderBatch;
	}
	/**
	 * @param automatedHeaderBatch
	 */
	public void setAutomatedHeaderBatch(List<String> automatedHeaderBatch) {
		this.automatedHeaderBatch = automatedHeaderBatch;
	}
	/**
	 * @return
	 */
	public List<String> getSummaryColumnHeadersbatch() {
		return summaryColumnHeadersbatch;
	}
	/**
	 * @param summaryColumnHeadersbatch
	 */
	public void setSummaryColumnHeadersbatch(List<String> summaryColumnHeadersbatch) {
		this.summaryColumnHeadersbatch = summaryColumnHeadersbatch;
	}
	/**
	 * @return
	 */
	public List<String> getSummaryHeaderBatch() {
		return summaryHeaderBatch;
	}
	/**
	 * @param summaryHeaderBatch
	 */
	public void setSummaryHeaderBatch(List<String> summaryHeaderBatch) {
		this.summaryHeaderBatch = summaryHeaderBatch;
	}
	/**
	 * @return
	 */
	public List<String> getArrEffortHeadersBatch() {
		return arrEffortHeadersBatch;
	}
	/**
	 * @param arrEffortHeadersBatch
	 */
	public void setArrEffortHeadersBatch(List<String> arrEffortHeadersBatch) {
		this.arrEffortHeadersBatch = arrEffortHeadersBatch;
	}
	/**
	 * @return
	 */
	public List<String> getAutomatedHeaderMigrationBatch() {
		return automatedHeaderMigrationBatch;
	}
	/**
	 * @param automatedHeaderMigrationBatch
	 */
	public void setAutomatedHeaderMigrationBatch(List<String> automatedHeaderMigrationBatch) {
		this.automatedHeaderMigrationBatch = automatedHeaderMigrationBatch;
	}
	/**
	 * @return
	 */
	public List<String> getSummaryColumnHeadersMigrationbatch() {
		return summaryColumnHeadersMigrationbatch;
	}
	/**
	 * @param summaryColumnHeadersMigrationbatch
	 */
	public void setSummaryColumnHeadersMigrationbatch(List<String> summaryColumnHeadersMigrationbatch) {
		this.summaryColumnHeadersMigrationbatch = summaryColumnHeadersMigrationbatch;
	}
	/**
	 * @return
	 */
	public List<String> getEffortHeadersBatch() {
		return effortHeadersBatch;
	}
	/**
	 * @param effortHeadersBatch
	 */
	public void setEffortHeadersBatch(List<String> effortHeadersBatch) {
		this.effortHeadersBatch = effortHeadersBatch;
	}

	/**
	 * @param projectPath
	 * @param fromVersion
	 * @param toVersion
	 */
	public void analyze(String projectPath, String fromVersion, String toVersion) {

		//to be implemented
	
	}

	/**
	 * @param totalFilesCount
	 * @param totalAffectedFiles
	 * @param automatedFilesCount
	 * @param assistedFilesCount
	 * @param reportType
	 * @param noOfAutomatedLines
	 * @param noOfAssistedLines
	 */
	public void getMigrationSummary(int totalFilesCount, int totalAffectedFiles, int automatedFilesCount, int assistedFilesCount,String reportType, int noOfAutomatedLines, int noOfAssistedLines)
	{
		String filesScanned=Integer.toString(totalFilesCount);
		String impactFiles= Integer.toString(totalAffectedFiles);
		String autoFiles= Integer.toString(automatedFilesCount);
		String assistFiles= Integer.toString(assistedFilesCount);
		
		if(reportType.equals("pre")) {
			ArrayList<String> automatedHeader = new ArrayList<>(Arrays.asList("Applications",FILES_SCANNED, IMPACTED_FILES, AUTOMATED_REMEDIATION_FILES, AUTOMATED_REMEDIATION_INSTANCES,ASSISTED_REMEDIATION_FILES,ASSISTED_REMEDIATION_INSTANCES));
			listGrid19 = new ArrayList<>();
			summaryColumnHeaders.addAll(automatedHeader);
			dataMap1 = new LinkedHashMap<>();
			dataMap1.put("Applications","1" );
			dataMap1.put(FILES_SCANNED,filesScanned );
			dataMap1.put(IMPACTED_FILES, impactFiles);
			dataMap1.put(AUTOMATED_REMEDIATION_FILES, autoFiles);
			dataMap1.put(AUTOMATED_REMEDIATION_INSTANCES, String.valueOf(noOfAutomatedLines));
			dataMap1.put(ASSISTED_REMEDIATION_FILES, assistFiles);
			dataMap1.put(ASSISTED_REMEDIATION_INSTANCES, String.valueOf(noOfAssistedLines));	
			listGrid19.add(dataMap1);	
		}	

		else {
			double affectedDecimal =((double) totalAffectedFiles / totalFilesCount)*100;
			double migratedDecimal= ((double)automatedFilesCount/totalFilesCount)*100;
			double manualDecimal=((double)assistedFilesCount/totalFilesCount)*100;
			int affected = (int) Math.round(affectedDecimal);
			int migrated=(int)Math.round(migratedDecimal);
			int manual=(int)Math.round(manualDecimal);
			String impactPercent=Integer.toString(affected)+" %";
			String migratedPercent=Integer.toString(migrated)+" %";
			String manualPercent=Integer.toString(manual)+" %";
			
			ArrayList<String> automatedHeader = new ArrayList<>(Arrays.asList(FILES_SCANNED, IMPACTED_FILES,  AUTOMATICALLY_REMEDIATED_FILES,ASSISTED_FILES));
			listGrid19 = new ArrayList<>();
			summaryColumnHeaders.addAll(automatedHeader);

			dataMap1 = new LinkedHashMap<>();
			dataMap1.put(FILES_SCANNED,filesScanned );		
			dataMap1.put(IMPACTED_FILES, impactFiles);
			dataMap1.put(AUTOMATICALLY_REMEDIATED_FILES, autoFiles);
			dataMap1.put(ASSISTED_FILES, assistFiles);
			listGrid19.add(dataMap1);
			dataMap1 = new LinkedHashMap<>();
			dataMap1.put(FILES_SCANNED,"100 %" );		
			dataMap1.put(IMPACTED_FILES, impactPercent);
			dataMap1.put(AUTOMATICALLY_REMEDIATED_FILES, migratedPercent);
			dataMap1.put(ASSISTED_FILES, manualPercent);
			
			listGrid19.add(dataMap1);
			
			automatedHeader = new ArrayList<>(Arrays.asList("Automated Changes", "Successfully Done"));
			summaryColumnHeaders1.addAll(automatedHeader);
			dataMap1 = new LinkedHashMap<>();
			dataMap1.put("Automated Changes",String.valueOf(noOfAutomatedLines) );		
			dataMap1.put("Successfully Done", String.valueOf(noOfAutomatedLines));
			listGrid20.add(dataMap1);		
		}
	}

	/**
	 * @param totalFilesCount
	 * @param totalAffectedFiles
	 * @param automatedFilesCount
	 * @param assistedFilesCount
	 * @param reportType
	 * @param noOfAutomatedLines
	 * @param noOfAssistedLines
	 * @param applicationName
	 */
	public void getMigrationSummaryBatch(int totalFilesCount, int totalAffectedFiles, int automatedFilesCount, int assistedFilesCount,String reportType, int noOfAutomatedLines, int noOfAssistedLines, String applicationName)
	{
		String filesScanned=Integer.toString(totalFilesCount);
		String impactFiles= Integer.toString(totalAffectedFiles);
		String autoFiles= Integer.toString(automatedFilesCount);
		String assistFiles= Integer.toString(assistedFilesCount);
		
		
		if(reportType.equals("pre")) {
			dataMap1 = new LinkedHashMap<>();
			dataMap1.put(APPLICATION,applicationName );
			dataMap1.put(FILES_SCANNED,filesScanned );
			dataMap1.put(IMPACTED_FILES, impactFiles);
			dataMap1.put(AUTOMATED_REMEDIATION_FILES, autoFiles);
			dataMap1.put(AUTOMATED_REMEDIATION_INSTANCES, String.valueOf(noOfAutomatedLines));
			dataMap1.put(ASSISTED_REMEDIATION_FILES, assistFiles);
			dataMap1.put(ASSISTED_REMEDIATION_INSTANCES, String.valueOf(noOfAssistedLines));
		

			listGrid17.add(dataMap1);
		
			list6.add(listGrid17);
		}
		else {
			dataMap1 = new LinkedHashMap<>();
			dataMap1.put(APPLICATION,applicationName );		
			dataMap1.put(AUTOMATICALLY_REMEDIATED_FILES, autoFiles);
			listGrid17.add(dataMap1);		
			list6.add(listGrid17);
		}
	}

	/**
	 * @param totalFilesScanned
	 * @param totalAffectedFiles
	 * @param automatedFilesCount
	 * @param assistedFilesCount
	 */
	public void getMigrationPercent(int totalFilesScanned, int totalAffectedFiles, int automatedFilesCount, int assistedFilesCount)
	{
		ArrayList<String> migrationEffortHeaders = new ArrayList<>(
				Arrays.asList("Affected Files in %", "Migrated Files in %", "Manual Files in %"));
		double affectedDecimal =((double) totalAffectedFiles / totalFilesScanned)*100;
		double migratedDecimal= ((double)automatedFilesCount/totalFilesScanned)*100;
		double manualDecimal=((double)assistedFilesCount/totalFilesScanned)*100;
		int affected = (int) Math.round(affectedDecimal);
		int migrated=(int)Math.round(migratedDecimal);
		int manual=(int)Math.round(manualDecimal);	
		View.logger.info("summary-->"+affected+  " "+migrated+" "+manual);
		migrationPercentHeaders.addAll(migrationEffortHeaders);
		dataMap = new LinkedHashMap<>();
		listGrid14 = new ArrayList<>();
		list2 = new LinkedList<>();
		dataMap.put("Affected Files in %", affected);	
		dataMap.put("Migrated Files in %", migrated);
		dataMap.put("Manual Files in %", manual);
		listGrid14.add(dataMap);
		list2.add(listGrid14);
	}

	/**
	 * @param strapplicationName
	 * @param noOfInfo
	 * @param noOfSimple
	 * @param noOfMedium
	 * @param noOfComplex
	 * @param infoEffortRequired
	 * @param simEffortRequired
	 * @param medEffortRequired
	 * @param comEffortRequired
	 * @param noOfMandInfo
	 * @param noOfMandSimple
	 * @param noOfMandMedium
	 * @param noOfMandComplex
	 * @param mandInfoEffortRequired
	 * @param mandSimEffortRequired
	 * @param mandMedEffortRequired
	 * @param mandComEffortRequired
	 * @param noOfMandRedesign
	 * @param noOfMandArch
	 * @param mandredEffortRequired
	 * @param mandArchEffortRequired
	 */
	public void getEffortBreakDown(String strapplicationName, Integer noOfInfo, Integer noOfSimple, Integer noOfMedium, Integer noOfComplex, Double infoEffortRequired,Double simEffortRequired, Double medEffortRequired, Double comEffortRequired, Integer noOfMandInfo, Integer noOfMandSimple, Integer noOfMandMedium, Integer noOfMandComplex,Double mandInfoEffortRequired, Double mandSimEffortRequired, Double mandMedEffortRequired, Double mandComEffortRequired, Integer noOfMandRedesign, Integer noOfMandArch, Double mandredEffortRequired, Double mandArchEffortRequired)
	{

		ArrayList<String> headers = new ArrayList<>(Arrays.asList(APPLICATION,CATEGORY,COMPLEXITY,ASSISTED_INSTANCES,EFFORT));
		MyPropertyLoad load = new MyPropertyLoad();
		load.loadRule("effort");
		HashMap<String,Double> hmEffort = new HashMap<>();
		for (Enumeration<?> e = load.getProp().propertyNames(); e.hasMoreElements();) {
			String name = (String) e.nextElement();
			String value = load.getProp().getProperty(name);
			hmEffort.put(name,Double.valueOf(value));
		}
		
		effortBreakHeaders.addAll(headers);
		listGrid13 = new ArrayList<>();
		dataMap1 = new LinkedHashMap<>();
		dataMap1.put(APPLICATION, strapplicationName);
		dataMap1.put(CATEGORY, "Mandatory");
		dataMap1.put(COMPLEXITY, "Info");
		dataMap1.put(ASSISTED_INSTANCES, noOfMandInfo.toString());
		dataMap1.put(EFFORT, roundTwoDecimals(mandInfoEffortRequired).toString());
		listGrid13.add(dataMap1);
		dataMap1 = new LinkedHashMap<>();
		dataMap1.put(APPLICATION, "");
		dataMap1.put(CATEGORY, "");
		dataMap1.put(COMPLEXITY, SIMPLE);
		dataMap1.put(ASSISTED_INSTANCES, noOfMandSimple.toString());
		dataMap1.put(EFFORT, roundTwoDecimals(mandSimEffortRequired).toString());
		listGrid13.add(dataMap1);
		dataMap1 = new LinkedHashMap<>();
		dataMap1.put(APPLICATION, "");
		dataMap1.put(CATEGORY, "");
		dataMap1.put(COMPLEXITY, MEDIUM);
		dataMap1.put(ASSISTED_INSTANCES, noOfMandMedium.toString());
		dataMap1.put(EFFORT,  roundTwoDecimals(mandMedEffortRequired).toString());
		listGrid13.add(dataMap1);
		dataMap1 = new LinkedHashMap<>();
		dataMap1.put(APPLICATION, "");
		dataMap1.put(CATEGORY, "");
		dataMap1.put(COMPLEXITY, COMPLEX);
		dataMap1.put(ASSISTED_INSTANCES, noOfMandComplex.toString());
		dataMap1.put(EFFORT,  roundTwoDecimals(mandComEffortRequired).toString());
		listGrid13.add(dataMap1);
		dataMap1 = new LinkedHashMap<>();
		dataMap1.put(APPLICATION, "");
		dataMap1.put(CATEGORY, "");
		dataMap1.put(COMPLEXITY, "Redesign");
		dataMap1.put(ASSISTED_INSTANCES, noOfMandRedesign.toString());
		dataMap1.put(EFFORT,  roundTwoDecimals(mandredEffortRequired).toString());
		listGrid13.add(dataMap1);
		dataMap1 = new LinkedHashMap<>();
		dataMap1.put(APPLICATION, "");
		dataMap1.put(CATEGORY, "");
		dataMap1.put(COMPLEXITY, "Architecture");
		dataMap1.put(ASSISTED_INSTANCES, noOfMandArch.toString());
		dataMap1.put(EFFORT,  roundTwoDecimals(mandArchEffortRequired).toString());
		listGrid13.add(dataMap1);
		dataMap1 = new LinkedHashMap<>();
		dataMap1.put(APPLICATION, "");
		dataMap1.put(CATEGORY, "Optional");
		dataMap1.put(COMPLEXITY, "Info");
		dataMap1.put(ASSISTED_INSTANCES, noOfInfo.toString());
		dataMap1.put(EFFORT,  roundTwoDecimals(infoEffortRequired).toString());
		listGrid13.add(dataMap1);
		dataMap1 = new LinkedHashMap<>();
		dataMap1.put(APPLICATION, "");
		dataMap1.put(CATEGORY, "");
		dataMap1.put(COMPLEXITY, SIMPLE);
		dataMap1.put(ASSISTED_INSTANCES, noOfSimple.toString());
		dataMap1.put(EFFORT,  roundTwoDecimals(simEffortRequired).toString());
		listGrid13.add(dataMap1);
		dataMap1 = new LinkedHashMap<>();
		dataMap1.put(APPLICATION, "");
		dataMap1.put(CATEGORY, "");
		dataMap1.put(COMPLEXITY, MEDIUM);
		dataMap1.put(ASSISTED_INSTANCES, noOfMedium.toString());
		dataMap1.put(EFFORT,  roundTwoDecimals(medEffortRequired).toString());
		listGrid13.add(dataMap1);
		dataMap1 = new LinkedHashMap<>();
		dataMap1.put(APPLICATION, "");
		dataMap1.put(CATEGORY, "");
		dataMap1.put(COMPLEXITY, COMPLEX);
		dataMap1.put(ASSISTED_INSTANCES, noOfComplex.toString());
		dataMap1.put(EFFORT,  roundTwoDecimals(comEffortRequired).toString());
		listGrid13.add(dataMap1);
	}

	/**
	 * @param strapplicationName
	 * @param mandatory
	 * @param optional
	 */
	public void getEffortReport(String strapplicationName, Double mandatory, Double optional)
	{
		effortHeaders = new ArrayList<>(Arrays.asList(APPLICATION,CATEGORY,EFFORT));
		ArrayList<String> effortHeadersPie = new ArrayList<>(Arrays.asList(CATEGORY,EFFORT));

		effortHeaderspie.addAll(effortHeadersPie);
		listGrid15 = new ArrayList<>();
		list3 = new LinkedList<>();
		dataMap2 = new LinkedHashMap<>();
		dataMap2.put(APPLICATION, strapplicationName);
		dataMap2.put(CATEGORY, "Mandatory*");
		dataMap2.put(EFFORT, roundTwoDecimals(mandatory).toString());
		listGrid15.add(dataMap2);
		dataMap2 = new LinkedHashMap<>();
		dataMap2.put(APPLICATION, "");
		dataMap2.put(CATEGORY, "Optional**");
		dataMap2.put(EFFORT, roundTwoDecimals(optional).toString());
		listGrid15.add(dataMap2);
		list3.add(listGrid15);
		
		//Data for Pie chart
		listGrid18 = new ArrayList<>();
		list7 = new LinkedList<>();
		dataMap2 = new LinkedHashMap<>();
		dataMap2.put(CATEGORY, "Mandatory");
		dataMap2.put(EFFORT, mandatory.toString());
		listGrid18.add(dataMap2);
		dataMap2 = new LinkedHashMap<>();
		dataMap2.put(CATEGORY, "Optional");
		dataMap2.put(EFFORT, optional.toString());
		listGrid18.add(dataMap2);
		list7.add(listGrid18);
	}

	/**
	 * @param effortRequired
	 * @param applicationName
	 * @param noOfSimple
	 * @param noOfMedium
	 * @param noOfComplex
	 */
	public void getEffortReportBatch( double effortRequired, String applicationName, int noOfSimple, int noOfMedium, int noOfComplex)
	{
		LinkedHashMap<String, String> dataMap4 = new LinkedHashMap<>();

		dataMap4.put(APPLICATION, applicationName);
		dataMap4.put(SIMPLE, String.valueOf(noOfSimple));
		dataMap4.put(MEDIUM, String.valueOf(noOfMedium));
		dataMap4.put(COMPLEX, String.valueOf(noOfComplex));
		dataMap4.put(ASSISTED_REMEDIATION_INSTANCES, String.valueOf(noOfSimple+noOfMedium+noOfComplex));
		dataMap4.put(ESTIMATED_ASSISTED_REMEDIATION_EFFORT, String.valueOf(effortRequired));
		listGrid16.add(dataMap4);
		list5.add(listGrid16);
	}

	/**
	 * @param d
	 * @return
	 */
	public Double roundTwoDecimals(Double d) {
		DecimalFormat twoDForm = new DecimalFormat("##0");
		return Double.valueOf(twoDForm.format(d));
	//	return Double.parseDouble(twoDForm.format(d));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ProjectAnalyze analyze=new ProjectAnalyze();
		analyze.getMigrationPercent(110,43,15,19);
	}

	/**
	 * @param dir
	 */
	public void displayDirectoryContents(File dir) {
		try {

			File[] files = dir.listFiles();

			for (File file : files) {

				if (file.isDirectory()) {

					displayDirectoryContents(file);
				} 
				else {
					totalCount++;
				}			
			}
		}
		catch (SecurityException |NullPointerException  e) {
			View.logger.error(e.getMessage(),e);
		}
	}

}
