package com.infy.openjdk.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.infy.openjdk.business.ProjectAnalyze;

public class TestProjectAnalyze {

	static ProjectAnalyze pr;
	@BeforeClass
	public static void initializeVariables(){
		pr = new ProjectAnalyze();
	}

	@Test
	public void testGetMigrationSummary() {		
		pr.getMigrationSummary(100, 50, 5, 10, "pre", 46, 76);				
	}
	
	@Test
	public void testGetMigrationSummaryPost() {		
		pr.getMigrationSummary(100, 50, 5, 10, "post", 46, 76);				
	}

	@Test
	public void testGetMigrationSummarBatch() {		
		pr.getMigrationSummaryBatch(100, 20, 5, 15, "pre", 25, 67, "Sample");
		pr.getMigrationSummaryBatch(100, 20, 5, 15, "post", 25, 67, "Sample");				
	}
	
	@Test
	public void testGetMigrationPercent() {		
		pr.getMigrationPercent(120, 45, 12, 34);
	}

	@Test
	public void testEffortMethods() {
		pr.getEffortBreakDown("Sample app", 5,10, 12, 3, 0.0,20.0, 34.0, 45.0, 4,12, 2, 0, 0.0, 2.0, 4.0, 6.0, 0, 1, 8.0, 0.0);
		pr.getEffortReport("Sample app", 10.0, 20.0);
		pr.getEffortReportBatch(10.0, "Sample app", 10, 5, 3);
	}

	@AfterClass
	public static void removeObjects() {
		pr = null;
	}
}