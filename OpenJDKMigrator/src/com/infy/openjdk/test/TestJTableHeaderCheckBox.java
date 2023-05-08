package com.infy.openjdk.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.infy.openjdk.report.JTableHeaderCheckBox;

public class TestJTableHeaderCheckBox {

	@Test
	public void testBuildGui() {		
		JTableHeaderCheckBox jhcb = new JTableHeaderCheckBox();
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
		jhcb.buildGUI(viewer2DataList, headerList);
		jhcb.buildGUIAutomatedReport();
		jhcb.assistedTable(viewer2DataList, headerList);
		jhcb.buildMultiBrowseUpload(viewer2DataList, headerList);
	}
}