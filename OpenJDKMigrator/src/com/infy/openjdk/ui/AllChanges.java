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

package com.infy.openjdk.ui;

public class AllChanges {	
	AllChanges() {
		//to be implemented
	}

	// rest
	static String rest1 = "Adding jboss-deployment-structure.xml";
	static String rest2 = "Adding Parameters to web.xml";
	static String rest3 = "Adding jars listed below in WEB-INF folder";

	// jndi
	static String jndi1 = "Connector jars need to be copied in the JBOSS Modules folder";
	static String jndi2 = "Adding Datasource Entry in Standalone-full.xml";
	static String jndi3 = "Appending \"java:/\" to the datasource name";

	// Sagar added for Tomcat
	static String jndi1Tomcat = "Connector jars need to be copied in the Tomcat lib folder";
	static String jndi2Tomcat = "Adding Datasource Entry in Server.xml";

	// orm
	static String orm1 = "Adding Version Specific Jars";
	static String ormTomcat = "No additional jars are required to add for Tomcat.";
	// mdb

	static String mdb1 = "Adding JMS Queue/Topic Entry in Standalone.xml";
	static String mdb2 = "Adding JMS connectionfactories in standalone.xml";
	static String mdb3 = "Adding messaging subsystem in standalone.xml";
	static String mdb4 = "Adding mdb tag under urn:jboss:domain:ejb3:1.2 subsystem";
	static String mdb5 = "Adding security domain authentication under urn:jboss:domain:security:1.2 subsystem";
	static String mdb6 = "Adding socket-bindings of messaging and messaging-throughput";

	// ejb

	static String ejb1 = "Upadating JBOSS Specific EJB Configuration (.xml) File in Application";
}
