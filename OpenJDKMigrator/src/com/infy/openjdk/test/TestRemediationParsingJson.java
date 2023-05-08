package com.infy.openjdk.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.infy.openjdk.configuration.JsonFileReader;
import com.infy.openjdk.mapper.RemediationParsingJson;
import com.infy.openjdk.pojo.JDK11;
import com.infy.openjdk.pojo.JDK11ThirdParty;
import com.infy.openjdk.pojo.JDK8;
import com.infy.openjdk.pojo.JDK8ThirdParty;
import com.infy.openjdk.pojo.JsonRulesJDK;
import com.infy.openjdk.pojo.JsonRulesJDKThirdParty;
import com.infy.openjdk.ui.View;
import com.infy.openjdk.util.Progress;
import com.infy.openjdk.util.Utilities;

public class TestRemediationParsingJson {
	
	List<JDK8ThirdParty> dataListJDK8thirdparty = null;
	List<JDK11ThirdParty> dataListJDK11thirdparty = null;
	List<JDK8> dataListJDK8 = null;
	List<JDK11> dataListJDK11 = null;

	/**
	 * @throws XmlPullParserException
	 */
	@Test
	public void testdisplayDirectoryContents() throws XmlPullParserException {
		RemediationParsingJson object = new RemediationParsingJson();
		try {
			loadPattern("JDK8");
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		Progress progress = new Progress();
		progress.progressbar(2);
		object.displayDirectoryContents(new File("D:\\DemoJDK\\maven-hello-world-master\\my-app"),dataListJDK8,null,dataListJDK8thirdparty,null, "JDK8", progress);
	}
	
	/**
	 * @throws XmlPullParserException
	 */
	@Test
	public void testdisplayDirectoryContentsJDK11() throws XmlPullParserException {
		RemediationParsingJson object1 = new RemediationParsingJson();
		try {
				loadPattern("JDK11");
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
		Progress progress1 = new Progress();
		progress1.progressbar(2);
		object1.displayDirectoryContents(new File("D:\\DemoJDK\\maven-hello-world-master\\my-app"),null,dataListJDK11,null,dataListJDK11thirdparty, "JDK11", progress1);
	}
	
	/**
	 * @throws XmlPullParserException
	 */
	@Test
	public void testdisplayDirectoryContentsGradle() throws XmlPullParserException {
		RemediationParsingJson object1 = new RemediationParsingJson();
		try {
				loadPattern("JDK11");
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		Progress progress1 = new Progress();
		progress1.progressbar(2);
		object1.displayDirectoryContents(new File("D:\\DemoJDK\\gradledemo"),null,dataListJDK11,null,dataListJDK11thirdparty, "JDK11", progress1);
	}
	
	/**
	 * @throws XmlPullParserException
	 */
	@Test
	public void testdisplayDirectoryContentsSpringBoot() throws XmlPullParserException {
		RemediationParsingJson object1 = new RemediationParsingJson();
		try {
				loadPattern("JDK11");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			Progress progress1 = new Progress();
			progress1.progressbar(2);
		object1.displayDirectoryContents(new File("D:\\DemoJDK\\InfyGoBoot_SpringMVC"),null,dataListJDK11,null,dataListJDK11thirdparty, "JDK11", progress1);
	}
	
	/**
	 * @param toVersion
	 * @throws IOException
	 */
	public void loadPattern(String toVersion) throws IOException {		
		View.logger.info("Reading json"); 
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);				
		JsonRulesJDKThirdParty dataList3 = objectMapper.readValue(new File("D:\\Project\\WorkBench\\WorkSpaceADMCOE\\OpenJDKMigrator\\src\\com\\infy\\openjdk\\configuration\\thirdpartyjars.json"), JsonRulesJDKThirdParty.class);
		JsonRulesJDK dataList4 = objectMapper.readValue(new File("D:\\Project\\WorkBench\\WorkSpaceADMCOE\\OpenJDKMigrator\\src\\com\\infy\\openjdk\\configuration\\JDK.json"), JsonRulesJDK.class);

		if(toVersion.equals("JDK8")) {
		dataListJDK8thirdparty=dataList3.getJDK8thirdparty();
		dataListJDK8 = dataList4.getJDK8();			
		}
		else if(toVersion.equals("JDK11")) {
		dataListJDK11thirdparty=dataList3.getJDK11thirdparty();
		dataListJDK11 = dataList4.getJDK11();
		}				
	}
}