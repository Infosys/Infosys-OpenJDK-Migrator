package com.infy.openjdk.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.infy.openjdk.controller.AppScannerJson;
import com.infy.openjdk.mapper.RemediationParsingJson;
import com.infy.openjdk.pojo.JDK11;
import com.infy.openjdk.pojo.JDK11ThirdParty;
import com.infy.openjdk.pojo.JDK8;
import com.infy.openjdk.pojo.JDK8ThirdParty;
import com.infy.openjdk.pojo.JsonRulesJDK;
import com.infy.openjdk.pojo.JsonRulesJDKThirdParty;
import com.infy.openjdk.ui.View;
import com.infy.openjdk.util.Progress;

public class TestAppScannerJson {
	List<JDK8ThirdParty> dataListJDK8thirdparty = null;
	List<JDK11ThirdParty> dataListJDK11thirdparty = null;
	List<JDK8> dataListJDK8 = null;
	List<JDK11> dataListJDK11 = null;
	
	@Test
	public void testParseFiles() {
		AppScannerJson appScJs = new AppScannerJson();		
			Progress progress = null;
			try {
				appScJs.parseFiles();
				appScJs.getAllFiles("d", "JDK8", "JDK11", progress );
			} 
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			appScJs.getAssistedFiles();
			appScJs.getAutomatedFiles();
			appScJs.getColumnExecutedRuleHeader();
			appScJs.getColumnHeaders6();
			appScJs.getColumnHeadersAssisted();
			appScJs.getColumnHeadersLibrary();
			appScJs.getColumnIncFeatHeader();
			appScJs.getColumnpackageHeader();
			appScJs.getComEffortRequired();
			appScJs.getDataMap();
			appScJs.getDataMapAssisted();
			appScJs.getDataMapAssistedMand();
			appScJs.getDataMapLibrary();
			appScJs.getDataMapMultiBrowse();
			appScJs.getDataMapPackages();
			appScJs.getDataMapPackagesIncfeat();
			appScJs.getDataMapPackagesNew();
			appScJs.getEffortRequired();
			appScJs.getDataMapAssisted();
			appScJs.getDataMapLibrary();
			appScJs.getDataMapMultiBrowse();
			appScJs.getDataMapPackages();
			appScJs.getDataMapPackagesIncfeat();
			appScJs.getDataMapPackagesNew();
			appScJs.getEffortRequired();
			appScJs.getEffortSaved();
			appScJs.getInfoEffortRequired();
			appScJs.getJDKInstance();
			appScJs.getListassistedAll();
			appScJs.getListGidlibrary();
			appScJs.getListGrid13();
			appScJs.getListGridAssisted();
			appScJs.getListGridAssistedMand();
			appScJs.getListGridMulti();
			appScJs.getListGridPackages();
			appScJs.getListOfExecutedRules();
			appScJs.getListOfIncompatibleFeatures();
			appScJs.getMandArchEffortRequired();
			appScJs.getMandComEffortRequired();
			appScJs.getMandEffortRequired();
			appScJs.getMandInfoEffortRequired();
			appScJs.getMandMedEffortRequired();
			appScJs.getMandredEffortRequired();
			appScJs.getMandSimEffortRequired();
			appScJs.getMedEffortRequired();
			appScJs.getMultiBrowseColHeader();
			appScJs.getNoOfAssistedLines();
			appScJs.getNoOfAutomatedLines();
			appScJs.getNoOfComplex();
			appScJs.getNoOfInfo();
			appScJs.getNoOfMandArch();
			appScJs.getNoOfMandatory();
			appScJs.getNoOfMandComplex();
			appScJs.getNoOfMandInfo();
			appScJs.getNoOfMandMedium();
			appScJs.getNoOfMandRedesign();
			appScJs.getNoOfMandSimple();
			appScJs.getNoOfMedium();
			appScJs.getNoOfOptional();
			appScJs.getNoOfSimple();
			appScJs.getSimEffortRequired();
			appScJs.getSyntax();
			appScJs.getTargetJDKInstance();
			appScJs.getToJdkVersion();
			appScJs.getTotalFilesScanned();
			appScJs.getUtility();
			List<String> pathList = new ArrayList<String>();
			pathList.add("");
			appScJs.multiBrowseAnalysis(pathList );
			List<String[]> pathList1 = new ArrayList<String[]>();
			String[] str = {""};
			pathList1.add(str) ;
			appScannerPath(appScJs, pathList, pathList1);
	}

	/**
	 * @param appScJs
	 * @param pathList
	 * @param pathList1
	 */
	private void appScannerPath(AppScannerJson appScJs, List<String> pathList, List<String[]> pathList1) {
		appScJs.multiBrowsePaths(pathList1 );
		appScJs.setAssistedFiles(null);
		appScJs.setAutomatedFiles(null);
		appScJs.setColumnExecutedRuleHeader(pathList);
		appScJs.setColumnHeaders6(pathList);
		appScJs.setColumnHeadersAssisted(pathList);
		appScJs.setColumnHeadersLibrary(pathList);
		appScJs.setColumnIncFeatHeader(pathList);
		appScJs.setColumnpackageHeader(pathList);
		appScJs.setComEffortRequired(null);
		appScJs.setDataMap(null);
		appScJs.setDataMapAssisted(null);
		appScJs.setDataMapAssistedMand(null);
		appScJs.setDataMapLibrary(null);
		appScJs.setDataMapMultiBrowse(null);
		appScJs.setDataMapPackages(null);
		appScJs.setDataMapPackagesNew(null);
		appScJs.setEffortRequired(null);
		appScJs.setDataMapAssisted(null);
		appScJs.setDataMapLibrary(null);
		appScJs.setDataMapMultiBrowse(null);
		appScJs.setDataMapPackages(null);
		appScJs.setDataMapPackagesIncfeat(null);
		appScJs.setDataMapPackagesNew(null);
		appScJs.setEffortRequired(null);
		appScJs.setEffortSaved(null);
		appScJs.setInfoEffortRequired(null);
		appScJs.setListassistedAll(null);
		appScJs.setListGidlibrary(null);
		appScJs.setListGrid13(null);
		appScJs.setListGridAssisted(null);
		appScJs.setListGridAssistedMand(null);
		appScJs.setListGridMulti(null);
		appScJs.setListGridPackages(null);
		appScJs.setListOfExecutedRules(null);
		appScJs.setListOfIncompatibleFeatures(null);
		appScJs.setMandArchEffortRequired(null);
		appScJs.setMandComEffortRequired(null);
		appScJs.setMandEffortRequired(null);
		appScJs.setMandInfoEffortRequired(null);
		appScJs.setMandMedEffortRequired(null);
		appScJs.setMandredEffortRequired(null);
		appScJs.setMandSimEffortRequired(null);
		appScJs.setMedEffortRequired(null);
		appScJs.setMultiBrowseColHeader(null);
		appScJs.setNoOfAssistedLines(0);
		appScJs.setNoOfAutomatedLines(1);
		appScJs.setNoOfComplex(1);
		appScJs.setNoOfInfo(1);
		appScJs.setNoOfMandArch(1);
		appScJs.setNoOfMandatory(1);
		appScJs.setNoOfMandComplex(1);
		appScJs.setNoOfMandInfo(1);
		appScJs.setNoOfMandMedium(1);
		appScJs.setNoOfMandRedesign(1);
		appScJs.setNoOfMandSimple(1);
		appScJs.setNoOfMedium(1);
		appScJs.setNoOfOptional(1);
		appScJs.setNoOfSimple(1);
		appScJs.setSimEffortRequired(null);
		appScJs.setSyntax(null);
		appScJs.setToJdkVersion(null);
		appScJs.setTotalFilesScanned(1);
		appScJs.setUtility(null);
	}
		
}
