package com.infy.openjdk.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.infy.openjdk.report.JasperMigrationReport;

public class TestJasperMigrationReports {

	@Test
	public void testcreateReport() {
		JasperMigrationReport jsp = new JasperMigrationReport();
		List<String> headerList = new ArrayList<>();
		headerList.add("column1");
		headerList.add("column2");
		headerList.add("column3");
		headerList.add("column4");
		headerList.add("column5");
		headerList.add("column6");
		headerList.add("column7");
		headerList.add("column8");
		Map<String, String> map= new HashMap<String, String>();
		map.put("column1", "1");
		map.put("column2", "2");
		map.put("column3", "3");
		map.put("column4", "4");
		map.put("column5", "5");
		map.put("column6", "6");
		map.put("column7", "7");
		map.put("column8", "8");
		List<Map<String, String>> viewer2DataList = new ArrayList<>();
		viewer2DataList.add(map);
		jsp.createReport10(viewer2DataList, headerList);
		jsp.createReport11(viewer2DataList, headerList);
		jsp.createReport12(viewer2DataList, headerList);
		jsp.createReport13(viewer2DataList, headerList);
		jsp.createReport15(viewer2DataList, headerList);
		jsp.createReport17(viewer2DataList, headerList);
		jsp.createReport18(viewer2DataList, headerList);
		jsp.createReport20(viewer2DataList, headerList);
		jsp.createExecutedRulesTable(viewer2DataList, headerList);
		jsp.createIncompatibleFeatureTable(viewer2DataList, headerList);
		jsp.createpackagereport(viewer2DataList, headerList);
		List<List<Map<String, String>>> list = new ArrayList<List<Map<String,String>>>();
		list.add(viewer2DataList);
		List<String> colsList = new ArrayList<String>();
		colsList.addAll(headerList);
		jsp.createPieChart(viewer2DataList, headerList );
		jsp.createReportEffortBatch(list, colsList);
		jsp.createReportMigrationSummaryBatch(list, colsList);
		jsp.createReportSummaryBreakDownBatch(list, colsList);
		jsp.createReportSummaryBatch(1, 2, 3, 4, colsList);
		jsp.addCodeSnippet();
	}
	
}