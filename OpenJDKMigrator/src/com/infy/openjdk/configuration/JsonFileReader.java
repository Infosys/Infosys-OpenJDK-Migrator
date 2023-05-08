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

/*********************************************************************************
 * 1.Reads Json Rule
 *
 * 
 ***********************************************************************************/

package com.infy.openjdk.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.infy.openjdk.pojo.JDK11;
import com.infy.openjdk.pojo.JDK11ThirdParty;
import com.infy.openjdk.pojo.JDK8;
import com.infy.openjdk.pojo.JDK8ThirdParty;
import com.infy.openjdk.pojo.JsonRulesJDK;
import com.infy.openjdk.pojo.JsonRulesJDKThirdParty;
import com.infy.openjdk.ui.View;

public class JsonFileReader {
	List<JDK8ThirdParty> dataListJDK8thirdparty = null;
	List<JDK11ThirdParty> dataListJDK11thirdparty = null;
	List<JDK8> dataListJDK8 = null;
	List<JDK11> dataListJDK11 = null;

	/**
	 * @return datalistJdk8thirdparty
	 */
	public List<JDK8ThirdParty> getDataListJDK8thirdparty() {
		return dataListJDK8thirdparty;
	}

	/**
	 * @param dataListJDK8thirdparty
	 */
	public void setDataListJDK8thirdparty(List<JDK8ThirdParty> dataListJDK8thirdparty) {
		this.dataListJDK8thirdparty = dataListJDK8thirdparty;
	}

	/**
	 * @return dataListJDK11thirdparty
	 */
	public List<JDK11ThirdParty> getDataListJDK11thirdparty() {
		return dataListJDK11thirdparty;
	}

	/**
	 * @param dataListJDK11thirdparty
	 */
	public void setDataListJDK11thirdparty(List<JDK11ThirdParty> dataListJDK11thirdparty) {
		this.dataListJDK11thirdparty = dataListJDK11thirdparty;
	}

	/**
	 * @return dataListJDK8
	 */
	public List<JDK8> getDataListJDK8() {
		return dataListJDK8;
	}

	/**
	 * @param dataListJDK8
	 */
	public void setDataListJDK8(List<JDK8> dataListJDK8) {
		this.dataListJDK8 = dataListJDK8;
	}

	/**
	 * @return dataListJDK11
	 */
	public List<JDK11> getDataListJDK11() {
		return dataListJDK11;
	}

	/**
	 * @param dataListJDK11
	 */
	public void setDataListJDK11(List<JDK11> dataListJDK11) {
		this.dataListJDK11 = dataListJDK11;
	}

	/**
	 * @param toVersion
	 * @throws IOException
	 */
	public void loadPattern(String toVersion) throws IOException {	
		View.logger.info("Reading json"); 
		String path = System.getProperty("user.dir") + File.separator +"properties";
		URL urlThirdPartyJarsJson = new URL("platform:/plugin/OpenJDKMigrator/com/infy/openjdk/configuration/thirdpartyjars.json");
		InputStream jsonDataThirdPartyJars = urlThirdPartyJarsJson.openConnection().getInputStream();
		URL urlJDKJson = new URL("platform:/plugin/OpenJDKMigrator/com/infy/openjdk/configuration/JDK.json");
		InputStream jsonDataJDK = urlJDKJson.openConnection().getInputStream();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);		
		JsonRulesJDKThirdParty dataList3 = objectMapper.readValue(jsonDataThirdPartyJars, JsonRulesJDKThirdParty.class);
		JsonRulesJDK dataList4 = objectMapper.readValue(jsonDataJDK, JsonRulesJDK.class);

		if(toVersion.equals("JDK8")) {
			dataListJDK8thirdparty=dataList3.getJDK8thirdparty();
			dataListJDK8 = dataList4.getJDK8();			
		}
		else if(toVersion.equals("JDK11")) {
			dataListJDK11thirdparty=dataList3.getJDK11thirdparty();
			dataListJDK11 = dataList4.getJDK11();
		}
				
	}

	/**
	 * @param toVersion
	 * @throws IOException
	 */
	public void loadPatternTest(String toVersion) throws IOException {		
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
