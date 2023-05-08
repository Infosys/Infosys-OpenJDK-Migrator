package com.infy.openjdk.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.infy.openjdk.business.MergeChanges;
import com.infy.openjdk.report.JTableHeaderCheckBox;

public class TestMergeChanges {
	
	static MergeChanges mergeCh;
	@BeforeClass
	public static void initializeOjects() {
		mergeCh = new MergeChanges();
	}
	
	@Test
	public void testDisplayDireContents(){
		File file = new File("D:\\Demo\\test");
		Map<String, String> mp = new HashMap<>();
		mp.put("line1", "line1");
		mp.put("line2", "line2");
		List<Map<String, String>> list2 = new ArrayList<>();
		list2.add(mp);
		List<String> list = new ArrayList<>();
		list.add("line3");
		list.add("line4");
		list.add("line5");
		list.add("line6");
		JTableHeaderCheckBox jtable = new JTableHeaderCheckBox();
		Set<String> selectedFiles = new HashSet<>();
		selectedFiles.add("ALL");
		jtable.setSelectedFiles(selectedFiles);
		Set<String> removedFiles = new HashSet<>();
		jtable.setRemovedFiles(removedFiles);
		mergeCh.displayDestDirContents(file, list2, list, jtable);
	}
	
	@AfterClass
	public static void releaseObjects() {
		mergeCh = null;
	}
}