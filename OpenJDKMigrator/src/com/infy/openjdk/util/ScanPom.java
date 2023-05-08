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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.infy.openjdk.ui.View;

public class ScanPom {
	HashMap<String,String> getFrameVersion = new HashMap<>();
	/**
	 * @param path
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public void findThirdParty(String path) throws  IOException, XmlPullParserException {
    	MavenXpp3Reader reader = new MavenXpp3Reader();
    	Model model = null;
    
	try {
		File filePath = new File(path);		
		File[] files = filePath.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				getBuildVersion(file);
			} 
			else {
				if (file.getName().equalsIgnoreCase("pom.xml")) {
				    model = reader.read(
					//new InputStreamReader(
					//Application.class.getResourceAsStream(
					//"/META-INF/maven/de.scrum-master.stackoverflow/aspectj-introduce-method/pom.xml"
					//)				        		  
					//new FileInputStream(
					//"D://openjdk//git_test//Java-8-master_updated//pom.xml"
					//)
					new FileInputStream(
				        file.getCanonicalPath()
				        )
				    );
					break;
				}
			}
		}
		
	} 
	catch (NullPointerException |SecurityException | IOException e) {
		View.logger.error(e.getMessage());
	}

    if(model != null) {
    	getDependencies(model);
    }
  }

	/**
	 * @param model
	 */
	private void getDependencies(Model model) {
		List<Dependency> dependencyList = model.getDependencies();
		List<Plugin> plugin = model.getBuild().getPlugins();
		plugin.get(0).getConfiguration();
		
		getFrameVersion = new HashMap<>();
		
		for (Dependency d : dependencyList) {			    
			if(d.getGroupId().equalsIgnoreCase("org.springframework")) {
				getFrameVersion.put("spring", d.getVersion());
			}
			if(d.getGroupId().equalsIgnoreCase("org.apache.struts")) {
			    getFrameVersion.put("struts", d.getVersion());
			}
			if(d.getGroupId().equalsIgnoreCase("com.mydomain")) {
				getFrameVersion.put("ejb", d.getVersion());
			}			    
		}
	}

	/**
	 * @param sourcePath
	 * @return version
	 */
	public String getBuildVersion(File sourcePath) {
		String version = "";
		try {
			File[] files = sourcePath.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					getBuildVersion(file);
				} 
				else {
					if (file.getName().equalsIgnoreCase("pom.xml")) {
						version= findVersion(file);
						break;
					}
				}
			}			
		} 
		catch (SecurityException| ParserConfigurationException| IOException e) {
			View.logger.error(e.getMessage());
		}
		return version;
	}

	/**
	 * @param file
	 * @return strVersion
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private String findVersion(File file) throws  IOException, ParserConfigurationException {
		String strVersion = "";
        File filePom = file;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // Compliant
        dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); // compliant
        dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl",true);
        DocumentBuilder dBuilder;
        try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(filePom);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("plugin");
            for (int i = 0; i < nodeList.getLength(); i++) {
            	Node node = nodeList.item(i);
            	if(node.getNodeType() == Node.ELEMENT_NODE) {
            		Element element = (Element) node;

            		if(element.getElementsByTagName("artifactId").item(0).getTextContent().equalsIgnoreCase("maven-compiler-plugin")) {
            			Node node1 = element.getElementsByTagName("configuration").item(0);
            			Element element1 = (Element) node1;
            			strVersion = element1.getElementsByTagName("source").item(0).getTextContent();
            		}
            	}
        	}
            if(strVersion.equals("")) {
                NodeList nodeListprop = doc.getElementsByTagName("properties");
                for (int i = 0; i < nodeListprop.getLength(); i++) {
                	Node node = nodeListprop.item(i);
                	if(node.getNodeType() == Node.ELEMENT_NODE) {
                		Element element = (Element) node;

                		if(element.getElementsByTagName("java.version") != null) {
                			strVersion = element.getElementsByTagName("java.version").item(0).getTextContent();
                		}
                	}
                }
            }

		} catch (ParserConfigurationException | SAXException e) {
			View.logger.error(e.getMessage());
		}
		return strVersion;
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ScanPom s = new ScanPom();
		try {
			s.findThirdParty("D:\\DemoJDK\\path");
		} 
		catch (IOException | XmlPullParserException e) {
			View.logger.error(e.getMessage());
		}
	}	
}