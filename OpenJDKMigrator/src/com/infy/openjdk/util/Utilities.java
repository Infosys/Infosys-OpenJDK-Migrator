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

package com.infy.openjdk.util;


import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.infy.openjdk.configuration.ConfigReader;
import com.infy.openjdk.configuration.MyPropertyLoad;
import com.infy.openjdk.pojo.Rule;
import com.infy.openjdk.ui.View;

/*********************************************************************************
 * 1.Utilities - Handles getDirectoryPath, GetRules, PatternMatches 
 * 
 *
 *
 * 
 ***********************************************************************************/

public class Utilities {
	List<Rule> jdkList = new ArrayList<>();
	List<Rule> jdkToVersion = new ArrayList<>();
	Rule rule;
	boolean isMatches;
	List<Boolean> l = new ArrayList<>();
	List<String> modules = new ArrayList<>();
	String ruleName;
	List<Map<String, String>> lstmap = null;
	String getPatternValue="";
	
	public List<Rule> getJdkToVersion() {
		return jdkToVersion;
	}

	public void setJdkToVersion(List<Rule> jdkToVersion) {
		this.jdkToVersion = jdkToVersion;
	}


	/**
	 * @param line
	 * @return
	 */
	public List<Boolean> patternCompiler(String line)
	{
		ConfigReader confReader = new ConfigReader();
		for (Enumeration<?> e = confReader.getProp().propertyNames(); e.hasMoreElements();) {
			
			rule = new Rule();
			ruleName="";
			String name = (String) e.nextElement();

			String value = confReader.getProp().getProperty(name);
			if (name.startsWith("PATTERN_")) {
				Pattern patternREST = Pattern.compile(Pattern.quote(value),
						Pattern.CASE_INSENSITIVE);
				Matcher matcherREST = patternREST.matcher(line);
				isMatches = matcherREST.matches();	
				if(isMatches)
				{
					
					ruleName=name;
					ruleName=ruleName.substring(ruleName.lastIndexOf('_') + 1);
					l.add(isMatches);
					rule.setName(ruleName);
					rule.setMatches(isMatches);
					modules.add(ruleName);
					
					
				}
			}

		}		
		return l;
	}
	
	/**
	 * @param fromversion
	 * @return
	 */
	public List<Rule> rules(String fromversion) {
		MyPropertyLoad load = new MyPropertyLoad();
		load.loadRule(fromversion);
		for (Enumeration<?> e = load.getProp().propertyNames(); e.hasMoreElements();) {
			rule = new Rule();
			String name = (String) e.nextElement();
			String value = load.getProp().getProperty(name);	
			rule.setName(name);
			rule.setValue(value);
			jdkList.add(rule);				
		}
		return jdkList;
	}
	/**
	 * @param toVersion
	 * @return jdkToVersion
	 */
	public List<Rule> rulesToVersion(String toVersion)  {
		MyPropertyLoad load = new MyPropertyLoad();
		load.loadRule("Target"+toVersion);
		for (Enumeration<?> e = load.getProp().propertyNames(); e.hasMoreElements();) {
			rule = new Rule();
			String name = (String) e.nextElement();
			String value = load.getProp().getProperty(name);		
			rule.setName(name);
			rule.setValue(value);
			jdkToVersion.add(rule);				
		}
		return jdkToVersion;
	}

	//get Pattern Value from encrypted Rules
}