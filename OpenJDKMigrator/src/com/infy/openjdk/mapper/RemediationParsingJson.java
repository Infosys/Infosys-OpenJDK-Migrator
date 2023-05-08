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

package com.infy.openjdk.mapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.maven.artifact.versioning.ComparableVersion;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import com.infy.openjdk.pojo.JDK;
import com.infy.openjdk.pojo.JDK11;
import com.infy.openjdk.pojo.JDK11ThirdParty;
import com.infy.openjdk.pojo.JDK8;
import com.infy.openjdk.pojo.JDK8ThirdParty;
import com.infy.openjdk.pojo.JDKThirdParty;
import com.infy.openjdk.pojo.Match;
import com.infy.openjdk.ui.View;
import com.infy.openjdk.util.GradleBuild;
import com.infy.openjdk.util.Progress;
import com.infy.openjdk.util.ReadGradleBuildFile;
import com.infy.openjdk.util.Utilities;

/*********************************************************************************
 * 1.Scans list of files from the project Source file
 * 
 *
 * 2.Adds File Name, Line Number, Incompatibility, Possible Replacement
 *
 * Description: pass the path argument in method displayDirectoryContents(File
 * dir)
 *
 ***********************************************************************************/

public class RemediationParsingJson {

	List<JDK8> patternListJDK8;
	List<JDK11> patternListJDK11;
	int totalFilesCount=0;
	Match match;
	List<Match> list = new ArrayList<>();
	List<Match> jarlist = new ArrayList<>();
	Map<String, LinkedList<Match>> hmPackJava = new HashMap<>();
	Utilities utility = new Utilities();
	//hmObjectClasses contains classes and their object as key value pair
	//which created in single line
	Map<String, String> hmObjectClasses = new HashMap<>();
	//hmObjectClasses contains classes and their object as key value pair
	//which created in different lines
	Map<String, String> hmObjectClasses1 = new HashMap<>();
	//hashmap contaning the details of javapackages and their rules
	Map<String, Map<String, Integer[]>> hmJavaPackageDetails = null;
	Map<String, Integer> hmIncomaptFeatureDetails = null;
	
	// Scans list of files from the uploaded project
	
	/**
	 * @return totalFilesCount
	 */
	public int getTotalFilesCount() {
		return totalFilesCount;
	}
	/**
	 * @param totalFilesCount
	 */
	public void setTotalFilesCount(int totalFilesCount) {
		this.totalFilesCount = totalFilesCount;
	}
	/**
	 * @return jarlist
	 */
	public List<Match> getJarlist() {
		return jarlist;
	}
	/**
	 * @param jarlist
	 */
	public void setJarlist(List<Match> jarlist) {
		this.jarlist = jarlist;
	}
	/**
	 * @return hmPackJava
	 */
	public Map<String, LinkedList<Match>> getHmPackJava() {
		return hmPackJava;
	}
	/**
	 * @param hmPackJava
	 */
	public void setHmPackJava(Map<String, LinkedList<Match>> hmPackJava) {
		this.hmPackJava = hmPackJava;
	}
	/**
	 * @return hmJavaPackageDetails
	 */
	public Map<String, Map<String, Integer[]>> getHmJavaPackageDetails() {
		return hmJavaPackageDetails;
	}
	/**
	 * @param hmJavaPackageDetails
	 */
	public void setHmJavaPackageDetails(Map<String, Map<String, Integer[]>> hmJavaPackageDetails) {
		this.hmJavaPackageDetails = hmJavaPackageDetails;
	}
	/**
	 * @return hmIncomaptFeatureDetails
	 */
	public Map<String, Integer> getHmIncomaptFeatureDetails() {
		return hmIncomaptFeatureDetails;
	}
	/**
	 * @param hmIncomaptFeatureDetails
	 */
	public void setHmIncomaptFeatureDetails(Map<String, Integer> hmIncomaptFeatureDetails) {
		this.hmIncomaptFeatureDetails = hmIncomaptFeatureDetails;
	}	
	/**
	 * @param dir
	 * @param dataListJDK8
	 * @param dataListJDK11
	 * @param dataListJDK8thirdparty
	 * @param dataListJDK11thirdparty
	 * @param toVersion
	 * @param progress
	 */
	public void displayDirectoryContents(File dir, List<JDK8> dataListJDK8,List<JDK11> dataListJDK11, List<JDK8ThirdParty> dataListJDK8thirdparty, List<JDK11ThirdParty> dataListJDK11thirdparty, String toVersion, Progress progress) {
		try {			
			File[] files = dir.listFiles();

			for (File file : files) {
				if (file.isDirectory()) {
					displayDirectoryContents(file, dataListJDK8,dataListJDK11,dataListJDK8thirdparty,dataListJDK11thirdparty,toVersion, progress);
				} 
				else {
					if (file.getName().endsWith(".java")) {
						progress.getB().setValue(totalFilesCount);
						totalFilesCount++;
						String scanLine = "";					
						//below code is to get the list of methods and their class
						getMethodClassMapping(file);							
						//Below code is to match the regex with line
						try(Scanner scanner = new Scanner(new File(file.getCanonicalPath()));) {
							int i = 1;
							populateMatchingpattern(dataListJDK8, dataListJDK11, toVersion, file, scanner, i);
						}
						catch(NullPointerException |SecurityException |IllegalStateException | NoSuchElementException| IndexOutOfBoundsException| IOException  e) {
								View.logger.error(e.getMessage(),e);
						}
												
					}
					else if(file.getName().endsWith(".jar")) {						
						//code to be added to store the name of the jars
						//View.logger.info("Jar file>>>"+file.getCanonicalPath());
						match = new Match();
						match.setFileName(file.getName());						
						jarlist.add(match);						
					}
					else if(file.getName().endsWith("pom.xml")) {						
						//code to be added to store the name of the jars					
						//View.logger.info("pom file>>>"+file.getCanonicalPath());
					    MavenXpp3Reader reader = new MavenXpp3Reader();
					    Model model =  reader.read(new FileInputStream(file.getCanonicalPath()));
					    String pomPath = file.getCanonicalPath().substring(file.getCanonicalPath().indexOf(dir.getName()),file.getCanonicalPath().length());
						//View.logger.info("pomPath>>>"+pomPath);
					    if(toVersion.equals("JDK11")) {
							for(JDK11ThirdParty jdkThirdParty : dataListJDK11thirdparty)
							{					
								populateThirdPartyJars(model, pomPath, jdkThirdParty);
							}
					    } 
						else if(toVersion.equals("JDK8")) {
							for(JDK8ThirdParty jdkThirdParty : dataListJDK8thirdparty)
							{
								populateThirdPartyJars(model, pomPath, jdkThirdParty);
							}						    
					    }
					}
					else if(file.getName().endsWith("build.gradle")) {
						//View.logger.info("Skipped file>>>"+file.getCanonicalPath());
						ReadGradleBuildFile readgradleBuild = new ReadGradleBuildFile();
						GradleBuild gb= readgradleBuild.readGradleBuildFile(file);
						List<com.infy.openjdk.util.Dependency> dependencies = gb.getDependencies();
						if(toVersion.equals("JDK11")) {
							for(JDK11ThirdParty jdkThirdParty : dataListJDK11thirdparty)
							{					
								populateGradleThirdParty(file, dependencies, jdkThirdParty);
							}
					    } 
						else if(toVersion.equals("JDK8")) {
							for(JDK8ThirdParty jdkThirdParty : dataListJDK8thirdparty)
							{
								populateGradleThirdParty(file, dependencies, jdkThirdParty);
							}
						    
					    }						
					}				
				}
			}
		}
		catch (IOException |SecurityException| IllegalArgumentException | ClassCastException 
				|UnsupportedOperationException|IndexOutOfBoundsException |NullPointerException  e) {
			//View.logger.info("Error occured in Remediation parsing>>>"+match.getFileName()+">>"+match.getImpactLine()+">>"+match.getSupportVersion());
			View.logger.error(e.getMessage(),e);
		} 
		catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			View.logger.error(e.getMessage(),e);
		}

	}
	/**
	 * @param dataListJDK8
	 * @param dataListJDK11
	 * @param toVersion
	 * @param file
	 * @param scanner
	 * @param i
	 * @throws IOException
	 */
	private void populateMatchingpattern(List<JDK8> dataListJDK8, List<JDK11> dataListJDK11, String toVersion,
			File file, Scanner scanner, int i) throws IOException {
		String scanLine;
		while (scanner.hasNextLine()) {
			scanLine = scanner.nextLine();
			if(!scanLine.trim().startsWith("//") && !scanLine.trim().startsWith("/*") && !scanLine.trim().startsWith("*") && !scanLine.trim().startsWith("String") && !scanLine.trim().endsWith("*/") ) {
				patternMatch(scanLine, toVersion, dataListJDK8, dataListJDK11);
				if(patternListJDK8 != null) {								
					for(int j=0; j<patternListJDK8.size(); j++) {									
						String strScanLinecopy = scanLine;
						JDK8 jdk= new JDK8();
						jdk = patternListJDK8.get(j);
						populateMatchingPattern(file, scanLine, i, strScanLinecopy, jdk);	
					}
				}
				if(patternListJDK11 != null) {								
					for(int j=0; j<patternListJDK11.size(); j++) {										
						String strScanLinecopy = scanLine;
						JDK11 jdk= new JDK11();
						jdk = patternListJDK11.get(j);
						populateMatchingPattern(file, scanLine, i, strScanLinecopy, jdk);
					}
				}
			}
			i++;
		}
	}
	/**
	 * @param file
	 * @param dependencies
	 * @param jdkThirdParty
	 */
	private void populateGradleThirdParty(File file, List<com.infy.openjdk.util.Dependency> dependencies,
			JDKThirdParty jdkThirdParty) {
		for(com.infy.openjdk.util.Dependency d:dependencies) {
			if(d.getGroupId().equalsIgnoreCase(jdkThirdParty.getGroupId()) && !jdkThirdParty.getArtifactId().equals("") && jdkThirdParty.getArtifactId().equalsIgnoreCase(d.getArtifactId())&& d.getVersion() != null && !d.getVersion().startsWith("${") && compareVersion(d.getVersion(),jdkThirdParty.getSupportedVersion())) {
				match = new Match();
				match.setSyntaxId(jdkThirdParty.getRuleId());
				match.setImpactLine(d.getVersion());
				match.setFileName(file.getAbsolutePath());
				match.setLineNumber(0);
				match.setComplexity(jdkThirdParty.getComplexity());
				match.setSupportVersion(jdkThirdParty.getSuggestion());
				match.setSourcePattern(jdkThirdParty.getJavaPackage());
				match.setReference(jdkThirdParty.getReference());
				match.setIsAutomated(jdkThirdParty.getIsAutomated());
				match.setMandOpt(jdkThirdParty.getMandOpt());
				list.add(match);
				if(hmPackJava.get(match.getSourcePattern()) == null) {
					LinkedList<Match> list2 = new LinkedList<>();
					list2.add(match);
					hmPackJava.put(match.getSourcePattern(), list2);
				}
				else {
					LinkedList<Match> list2 = hmPackJava.get(match.getSourcePattern());
					list2.add(match);
					hmPackJava.put(match.getSourcePattern(), list2);
				}
				if(jdkThirdParty.getMandOpt().equalsIgnoreCase("MANDATORY")) {
					if(hmIncomaptFeatureDetails == null) {
						hmIncomaptFeatureDetails = new HashMap<>();
					}
					if(hmIncomaptFeatureDetails.containsKey(jdkThirdParty.getJavaPackage())) {
						Integer hmincmfetinst = hmIncomaptFeatureDetails.get(jdkThirdParty.getJavaPackage());
						hmIncomaptFeatureDetails.put(jdkThirdParty.getJavaPackage(), hmincmfetinst+1);
					}
					else {
						hmIncomaptFeatureDetails.put(jdkThirdParty.getJavaPackage(), 1);	
					}
				}								
			}
		}
	}

	/**
	 * @param model
	 * @param pomPath
	 * @param jdkThirdParty
	 */
	private void populateThirdPartyJars(Model model, String pomPath, JDKThirdParty jdkThirdParty) {
		List<Dependency> dependencyList = model.getDependencies();		
		Parent parent = model.getParent();
		for (Dependency d : dependencyList) {			    
			if(d.getGroupId().equalsIgnoreCase(jdkThirdParty.getGroupId()) && !jdkThirdParty.getArtifactId().equals("") && jdkThirdParty.getArtifactId().equalsIgnoreCase(d.getArtifactId())&& d.getVersion() != null && !d.getVersion().startsWith("${") && compareVersion(d.getVersion(),jdkThirdParty.getSupportedVersion())) {
				match = new Match();
				match.setFileName(pomPath);
				match.setSyntaxId(jdkThirdParty.getRuleId());
				match.setImpactLine(d.getVersion());
				match.setLineNumber(0);
				match.setComplexity(jdkThirdParty.getComplexity());
				match.setSupportVersion(jdkThirdParty.getSuggestion());
				match.setSourcePattern(jdkThirdParty.getJavaPackage());
				match.setReference(jdkThirdParty.getReference());
				match.setMandOpt(jdkThirdParty.getMandOpt());
				match.setIsAutomated(jdkThirdParty.getIsAutomated());
				list.add(match);
				if(hmPackJava.get(match.getSourcePattern()) == null) {
					LinkedList<Match> list3 = new LinkedList<>();
					list3.add(match);
					hmPackJava.put(match.getSourcePattern(), list3);
				}
				else {
					LinkedList<Match> list4 = hmPackJava.get(match.getSourcePattern());
					list4.add(match);
					hmPackJava.put(match.getSourcePattern(), list4);
				}
						
				if(("MANDATORY").equalsIgnoreCase(jdkThirdParty.getMandOpt())) {
					if(hmIncomaptFeatureDetails == null) {
						hmIncomaptFeatureDetails = new HashMap<>();
					}
					if(hmIncomaptFeatureDetails.containsKey(jdkThirdParty.getJavaPackage())) {
						Integer hmincfetinst = hmIncomaptFeatureDetails.get(jdkThirdParty.getJavaPackage());
						hmIncomaptFeatureDetails.put(jdkThirdParty.getJavaPackage(), hmincfetinst+1);
					}
					else {
						hmIncomaptFeatureDetails.put(jdkThirdParty.getJavaPackage(), 1);	
					}
				}
											
			}			
		}
		
		populateJdkThirdParty(model, pomPath, jdkThirdParty);
		
		//Spring boot configuration
    	if(parent != null && parent.getVersion() != null && parent.getArtifactId().equalsIgnoreCase(jdkThirdParty.getArtifactId()) && compareVersion(parent.getVersion(),jdkThirdParty.getSupportedVersion())) {
    		match = new Match();
			match.setSyntaxId(jdkThirdParty.getRuleId());
			match.setFileName(pomPath);
			match.setImpactLine(parent.getVersion());
			match.setLineNumber(0);
			match.setSupportVersion(jdkThirdParty.getSuggestion());
			match.setComplexity(jdkThirdParty.getComplexity());
			match.setReference(jdkThirdParty.getReference());
			match.setSourcePattern(jdkThirdParty.getJavaPackage());
			match.setMandOpt(jdkThirdParty.getMandOpt());
			match.setIsAutomated(jdkThirdParty.getIsAutomated());
			list.add(match);
			if(hmPackJava.get(match.getSourcePattern()) == null) {
				LinkedList<Match> list1 = new LinkedList<>();
				list1.add(match);
				hmPackJava.put(match.getSourcePattern(), list1);
			}
			else {
				LinkedList<Match> list1 = hmPackJava.get(match.getSourcePattern());
				list1.add(match);
				hmPackJava.put(match.getSourcePattern(), list1);
			}
			if(jdkThirdParty.getMandOpt().equalsIgnoreCase("MANDATORY")) {
				if(hmIncomaptFeatureDetails == null) {
					hmIncomaptFeatureDetails = new HashMap<>();
				}
				
				if(hmIncomaptFeatureDetails.containsKey(jdkThirdParty.getJavaPackage())) {
				Integer hmincmfetinst = hmIncomaptFeatureDetails.get(jdkThirdParty.getJavaPackage());
				hmIncomaptFeatureDetails.put(jdkThirdParty.getJavaPackage(), hmincmfetinst+1);
				}
				else {
					hmIncomaptFeatureDetails.put(jdkThirdParty.getJavaPackage(), 1);	
				}
			}
    	}
		
	}
	/**
	 * @param model
	 * @param pomPath
	 * @param jdkThirdParty
	 */
	private void populateJdkThirdParty(Model model, String pomPath, JDKThirdParty jdkThirdParty) {
		if(model.getBuild()!= null) {
			List<Plugin> plugin = model.getBuild().getPlugins();
			for(Plugin p:plugin) {
				if(p.getArtifactId().equalsIgnoreCase(jdkThirdParty.getArtifactId())){					
					//View.logger.info("maven-compiler-plugin>>"+p.getVersion());
			    	if(p.getVersion() != null && !p.getVersion().startsWith("${") && compareVersion(p.getVersion(),jdkThirdParty.getSupportedVersion())) {
			    		match = new Match();
						match.setSyntaxId(jdkThirdParty.getRuleId());
						match.setFileName(pomPath);
						match.setImpactLine(p.getVersion());
						match.setLineNumber(0);
						match.setSupportVersion(jdkThirdParty.getSuggestion());
						match.setReference(jdkThirdParty.getReference());
						match.setComplexity(jdkThirdParty.getComplexity());
						match.setSourcePattern(jdkThirdParty.getJavaPackage());
						match.setMandOpt(jdkThirdParty.getMandOpt());
						match.setIsAutomated(jdkThirdParty.getIsAutomated());
						list.add(match);
						if(hmPackJava.get(match.getSourcePattern()) == null) {
							LinkedList<Match> list5 = new LinkedList<>();
							list5.add(match);
							hmPackJava.put(match.getSourcePattern(), list5);
						}
						else {
							LinkedList<Match> list7 = hmPackJava.get(match.getSourcePattern());
							list7.add(match);
							hmPackJava.put(match.getSourcePattern(), list7);
						}
						if(jdkThirdParty.getMandOpt().equalsIgnoreCase("MANDATORY")) {							
							if(hmIncomaptFeatureDetails == null) {
								hmIncomaptFeatureDetails = new HashMap<>();
							}
							if(hmIncomaptFeatureDetails.containsKey(jdkThirdParty.getJavaPackage())) {
								Integer hmincmfetins = hmIncomaptFeatureDetails.get(jdkThirdParty.getJavaPackage());
								hmIncomaptFeatureDetails.put(jdkThirdParty.getJavaPackage(), hmincmfetins+1);
							}
							else {
								hmIncomaptFeatureDetails.put(jdkThirdParty.getJavaPackage(), 1);	
							}
						}
			    	}
				}
			}
		}
	}

	/**
	 * @param file
	 * @param scanLine
	 * @param i
	 * @param strScanLinecopy
	 * @param jdk
	 * @throws IOException
	 */
	private void populateMatchingPattern(File file, String scanLine, int i, String strScanLinecopy, JDK jdk) throws IOException {
		match = new Match();
		match.setFileName(file.getCanonicalPath());
		match.setImpactLine(scanLine);
		match.setLineNumber(i);
		match.setSourcePattern(jdk.getJavaPackage());
		match.setSyntaxId(jdk.getRuleId());
		String strSupportVersion = jdk.getSuggestion();
		if(jdk.getValue().equalsIgnoreCase("")) {
		jdk.setValue(jdk.getPattern().replaceAll("\\.|\\*|\\(|\\)|\\\\", ""));
		}
		if(!strSupportVersion.equalsIgnoreCase("Not Applicable")) {
		match.setComplexity(jdk.getComplexity());
		match.setMandOpt(jdk.getMandOpt());
		strSupportVersion =  strSupportVersion.trim();
		if(jdk.getType().equals("METHOD")) {
			int index =strScanLinecopy.indexOf(jdk.getValue());
			strScanLinecopy=strScanLinecopy.substring(0,index-1);
			int lastIndex =strScanLinecopy.lastIndexOf('	');
			if(lastIndex<strScanLinecopy.lastIndexOf(' ')) {
				lastIndex = strScanLinecopy.lastIndexOf(' ');
			}
			if(lastIndex<strScanLinecopy.lastIndexOf(',')) {
				lastIndex = strScanLinecopy.lastIndexOf(',');
			}
			if(lastIndex<strScanLinecopy.lastIndexOf('(')) {
				lastIndex = strScanLinecopy.lastIndexOf('(');
			}
			strScanLinecopy=strScanLinecopy.substring(lastIndex+1,strScanLinecopy.length());
			if(strScanLinecopy != null && hmObjectClasses.get(strScanLinecopy) != null && (hmObjectClasses.get(strScanLinecopy).equalsIgnoreCase(jdk.getClassObject())) )  {
				if(jdk.getIsAutomated().equals("Y")) {
					strScanLinecopy = scanLine.replaceAll(jdk.getValue(),strSupportVersion);
				}
				else {
					strScanLinecopy = strSupportVersion;
				}
			}
			else {
				return;
			}			
		}
		else if(jdk.getIsAutomated().equals("Y")){
			strScanLinecopy = strScanLinecopy.replaceAll(jdk.getValue(), strSupportVersion);		
		}
		else{

			strScanLinecopy = strSupportVersion.trim();
		}
		match.setSupportVersion(strScanLinecopy);
		match.setIsAutomated(jdk.getIsAutomated());
		match.setReference(jdk.getReference());
		list.add(match);
		if(hmPackJava.get(match.getSourcePattern()) == null) {
			LinkedList<Match> list1 = new LinkedList<>();
			list1.add(match);
			hmPackJava.put(match.getSourcePattern(), list1);
		}
		else {
			LinkedList<Match> list1 = hmPackJava.get(match.getSourcePattern());
			list1.add(match);
			hmPackJava.put(match.getSourcePattern(), list1);
		}
		Map<String, Integer[]> hmTypeRuleCount = hmJavaPackageDetails.get(jdk.getJavaPackage());		
		Integer counts[] = hmTypeRuleCount.get(jdk.getType());
		counts[1] = counts[1]+1;
		hmTypeRuleCount.put(jdk.getType(), counts);		
		hmJavaPackageDetails.put(jdk.getJavaPackage(), hmTypeRuleCount);		
		if(jdk.getMandOpt().equalsIgnoreCase("MANDATORY")) {
			Integer hmincmfetinst = hmIncomaptFeatureDetails.get(jdk.getJavaPackage());
			hmIncomaptFeatureDetails.put(jdk.getJavaPackage(), hmincmfetinst+1);
		}	
			hmJavaPackageDetails.put(jdk.getJavaPackage(), hmTypeRuleCount);					
		}
	}

	/**
	 * @param file
	 * @throws IOException
	 */
	private void getMethodClassMapping(File file) throws IOException {
		//View.logger.info("Java file>>"+file.getCanonicalPath());		
		try(Scanner scannermethods = new Scanner(new File(file.getCanonicalPath()));) {			
			String object = null;
			String classname = null;
			while(scannermethods.hasNextLine()) {
				String line = scannermethods.nextLine();
				String[] str2 =line.trim().split(" ");
				if(str2.length == 2 && !str2[0].matches("import|package")) {
					hmObjectClasses1.put(str2[1].replace(";", ""), str2[0]);
				}
				line=line.trim();
				if(line.startsWith("//") ||line.contains("class") || line.contains("interface") || line.startsWith("if") || line.startsWith("for")  || line.startsWith("while")) {
					continue;
				}
				if(line.contains("new ")) {
					line=line.trim();
					//View.logger.info(line);
					String[] str1 = line.split("=");
					str1[0]=str1[0].trim();
					if(str1[0].contains(" ")) {
						object = str1[0].substring(str1[0].indexOf(' '),str1[0].length()).trim();
						classname = str1[0].substring(0,str1[0].indexOf(' ')).trim();
						hmObjectClasses.put(object,classname);
					}
					else{
						object=str1[0].trim();
						classname = hmObjectClasses1.get(str1[0].trim());
						hmObjectClasses.put(object,classname);
					}
				}
				else if((line.startsWith("public") || line.startsWith("private") || line.startsWith("void")) && ( line.endsWith("{")  && !line.endsWith(";"))) {
					//View.logger.info("method line1 >>"+line);
					String str = line.substring(line.indexOf('(')+1,line.lastIndexOf(')'));
					//View.logger.info("method line2 >>"+str);
					if(!str.equals("")) {
						if(str.contains(",")) {
							String[] arguments = str.split(",");
							//View.logger.info("arguments3 "+arguments.length);
							for(int i = 0; i<arguments.length;i++) {
								String[] arguments1 = arguments[i].split(" ");
								if(arguments1.length>2) {
									object=arguments1[2];
									classname=arguments1[1];
								}
								else {
									object=arguments1[1];
									classname=arguments1[0];
								}
								//View.logger.info("classname1>>"+classname+" "+"object1>>"+object);
								hmObjectClasses.put(object,classname);
							}
						}
						else {
							//View.logger.info("argument1 "+str);
							String[] arguments1 = str.split(" ");						
							if(arguments1.length>2) {
								object=arguments1[2];
								classname=arguments1[1];
							}
							else {
								object=arguments1[1];
								classname=arguments1[0];
							}
							//View.logger.info("classname2>>"+classname+" "+"object2>>"+object);
							hmObjectClasses.put(object,classname);
						}
					}
				} 
				else if((line.startsWith("public") || line.startsWith("private") || line.startsWith("void")) && ( line.endsWith(","))) {
					//View.logger.info("method line3 >>"+line);
					String str = line.substring(line.indexOf('(')+1,line.lastIndexOf(','));
					//View.logger.info("method line4 >>"+str);
					if(!str.equals("")) {
						if(str.contains(",")) {
							String[] arguments = str.split(",");
							View.logger.info("arguments1 "+arguments.length);
							for(int i = 0; i<arguments.length;i++) {
								String[] arguments1 = arguments[i].split(" ");
								if(arguments1.length>2) {
									object=arguments1[2];
									classname=arguments1[1];
								}
								else {
									object=arguments1[1];
									classname=arguments1[0];
								}
								//View.logger.info("classname3>>"+classname+" "+"object3>>"+object);
								hmObjectClasses.put(object,classname);
							}
						}
						else {
							//View.logger.info("argument2 "+str);
							String[] arguments1 = str.split(" ");						
							if(arguments1.length>2) {
								object=arguments1[2];
								classname=arguments1[1];
							}
							else {
								object=arguments1[1];
								classname=arguments1[0];
							}
							//View.logger.info("classname4>>"+classname+" "+"object4>>"+object);
							hmObjectClasses.put(object,classname);
						}
					}
				}
				else if( line.endsWith("{") ) {
					//View.logger.info("method line5 >>"+line);
					if(line.contains(")")){				
						String str = line.substring(0,line.indexOf((")")));
						//View.logger.info("method line6 >>"+str);
						if(!str.equals("")) {
							if(str.contains(",")) {
								String[] arguments = str.split(",");						
								//View.logger.info("arguments2 "+arguments.length);
								for(int i = 0; i<arguments.length;i++) {
									String[] arguments1 = arguments[i].split(" ");							
									if(arguments1.length>2) {
										object=arguments1[2];
										classname=arguments1[1];
									}
									else {
										object=arguments1[1];
										classname=arguments1[0];
									}
									//View.logger.info("classname5>>"+classname+" "+"object5>>"+object);							
									hmObjectClasses.put(object,classname);
								}
							}
							else {
								//View.logger.info("argument3 "+str);
								String[] arguments1 = str.split(" ");						
								if(arguments1.length>2) {
									object=arguments1[2];
									classname=arguments1[1];
								}
								else if(arguments1.length>1) {
									object=arguments1[1];
									classname=arguments1[0];
								}
								//View.logger.info("classname6>>"+classname+" "+"object6>>"+object);
								hmObjectClasses.put(object,classname);
							}
						}
					}
				}
			}
		}
		catch(NullPointerException | IOException |SecurityException |IllegalStateException e) {
			View.logger.error(e.getMessage(),e);
		}
		
		catch(NoSuchElementException| IndexOutOfBoundsException |IllegalArgumentException | ClassCastException |UnsupportedOperationException e) {
			View.logger.error(e.getMessage(),e);
		}	
	}

	/**
	 * @param scanValue
	 * @param toVersion
	 * @param dataListJDK8
	 * @param dataListJDK11
	 */
	public void patternMatch(String scanValue, String toVersion, List<JDK8> dataListJDK8, List<JDK11> dataListJDK11) {

		boolean isMatches;
		String strPattern;

		if(toVersion.equalsIgnoreCase("JDK8")) {
			if(hmJavaPackageDetails == null ) {
				hmJavaPackageDetails = new HashMap<>();
				if(hmIncomaptFeatureDetails == null) {
				hmIncomaptFeatureDetails = new HashMap<>();
				}
				for(JDK8 jdk:dataListJDK8) {
					if(!hmJavaPackageDetails.containsKey(jdk.getJavaPackage())) {
						Integer counts[] = {1,0};
						Map<String, Integer[]> hmTypeRuleCount = new HashMap<>();
						hmTypeRuleCount.put(jdk.getType(), counts);
						hmJavaPackageDetails.put(jdk.getJavaPackage(), hmTypeRuleCount);
					}
					else {
						Map<String, Integer[]> hmTypeRuleCount = hmJavaPackageDetails.get(jdk.getJavaPackage());
						if(!hmTypeRuleCount.containsKey(jdk.getType())) {
							Integer counts[] = {1,0};
							hmTypeRuleCount.put(jdk.getType(), counts);
						}
						else {
							Integer counts[] = hmTypeRuleCount.get(jdk.getType());
							counts[0] = counts[0]+1;
							hmTypeRuleCount.put(jdk.getType(), counts);
						}
						hmJavaPackageDetails.put(jdk.getJavaPackage(), hmTypeRuleCount);
					}
					if(jdk.getMandOpt().equalsIgnoreCase("MANDATORY") && !hmIncomaptFeatureDetails.containsKey(jdk.getJavaPackage())) {
						hmIncomaptFeatureDetails.put(jdk.getJavaPackage(), 0);
					}
					
				}
			}			
			patternListJDK8 = new ArrayList<>();
			for(JDK8 jdk:dataListJDK8) {
				strPattern = jdk.getPattern();
				Pattern patternJdk = Pattern.compile(strPattern);
				Matcher matcherJdk = patternJdk.matcher(scanValue.trim());
				isMatches = matcherJdk.matches();	

				if (isMatches) {
					//View.logger.info(" Matched line>>"+scanValue+">>Syntax>>"+jdk.getRuleId());
					patternListJDK8.add(jdk);
				}
			}
		}
		else if(toVersion.equalsIgnoreCase("JDK11")) {
			if(hmJavaPackageDetails == null) {
				hmJavaPackageDetails = new HashMap<>();
				if(hmIncomaptFeatureDetails == null) {
					hmIncomaptFeatureDetails = new HashMap<>();
				}
				for(JDK11 jdk:dataListJDK11) {
					if(!hmJavaPackageDetails.containsKey(jdk.getJavaPackage())) {
						Integer counts[] = {1,0};
						Map<String, Integer[]> hmTypeRuleCount = new HashMap<>();
						hmTypeRuleCount.put(jdk.getType(), counts);
						hmJavaPackageDetails.put(jdk.getJavaPackage(), hmTypeRuleCount);
					}
					else {
						Map<String, Integer[]> hmTypeRuleCount = hmJavaPackageDetails.get(jdk.getJavaPackage());
						if(!hmTypeRuleCount.containsKey(jdk.getType())) {
							Integer counts[] = {1,0};
							hmTypeRuleCount.put(jdk.getType(), counts);
						}
						else {
							Integer counts[] = hmTypeRuleCount.get(jdk.getType());
							counts[0] = counts[0]+1;
							hmTypeRuleCount.put(jdk.getType(), counts);
						}
						hmJavaPackageDetails.put(jdk.getJavaPackage(), hmTypeRuleCount);
					}
					if(jdk.getMandOpt().equalsIgnoreCase("MANDATORY") && !hmIncomaptFeatureDetails.containsKey(jdk.getJavaPackage())) {
						hmIncomaptFeatureDetails.put(jdk.getJavaPackage(), 0);
					}
				}
			}
			patternListJDK11 = new ArrayList<>();
			for(JDK11 jdk:dataListJDK11) {
				strPattern = jdk.getPattern();
				Pattern patternJdk = Pattern.compile(strPattern);
				Matcher matcherJdk = patternJdk.matcher(scanValue.trim());
				isMatches = matcherJdk.matches();	

				if (isMatches) {
					//View.logger.info(" Matched line>>"+scanValue+">>Syntax>>"+jdk.getRuleId());
					patternListJDK11.add(jdk);
				}
			}
			
		}

	}
	
    /**
     * @param foundVersion
     * @param neededVersion
     * @return
     */
    private boolean compareVersion(String foundVersion, String neededVersion) {     	
        ComparableVersion foundVersion1 = new ComparableVersion(foundVersion); 
        ComparableVersion neededVersion1 = new ComparableVersion(neededVersion);
        //return true of current version is loewr than needed version
        return foundVersion1.compareTo(neededVersion1) < 0; 
    }
	
}