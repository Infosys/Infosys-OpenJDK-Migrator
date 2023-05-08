/*
*
* @ 2019-2020 Infosys Limited, Chandigarh, India. All rights reserved.
*
* Version: 1.0.0
*
* Except for any open source components embedded in this Infosys proprietary software program
* ("Program"), this Program is protected by copyright laws, international treaties and other pending or 
* existing intellectual property rights in India, the United States and other countries.
* Except as expressly permitted, any unauthorized reproduction , storage, transmission in any form or by
* any means (including without limitation electronic, mechanical, printing, photocopying, recording or 
* otherwise), or any distribution of this Program, or any portion of it, may result in severe civil and 
* criminal penalties, and will be prosecuted to the maximum extent possible under the law
* 
*/

/*
*
* Date: 01-Aug-2020
* @author Ajay_Singh10, ADMCOE, Infosys Ltd
* @version 1.0.0
* Description: 
*
*/

package com.infy.openjdk.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestMergeChanges.class,TestProjectAnalyze.class,TestBuildCode.class,TestJsonFileReader.class,TestAppScannerJson.class, TestRemediationParsingJson.class, TestMultipleFileUpload.class,TestJasperMigrationReports.class, TestJTableHeaderCheckBox.class, TestView.class})
public class TestSuite {

}
