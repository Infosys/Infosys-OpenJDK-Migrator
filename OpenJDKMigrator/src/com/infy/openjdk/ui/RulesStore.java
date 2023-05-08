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
* Date: 06-May-2019
* @author Ajay_Singh10, ADMCOE, Infosys Ltd
* @version 1.0.0
* Description: 
*
*/
package com.infy.openjdk.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.infy.openjdk.configuration.MyPropertyLoad;
import com.infy.openjdk.util.Main;

public class RulesStore {
String fromVersion="";
String toVersion="";
String updateSrcText="";
String updateDstText="";
public MyPropertyLoad obj = new MyPropertyLoad();
	RulesStore(){    
		JFrame f=new JFrame("Add Rules"); 
		//submit button				
		String[] src = { "JDK6", "JDK7", "JDK8", "JDK9", "JDK10" };
		String[] dest = { "JDK8", "JDK11" };

		JComboBox srcTextJDK1 = new JComboBox(src);
		srcTextJDK1.setBounds(110, 50, 130, 30);
		JTextField textfield= new JTextField();
		textfield.setBounds(110, 100, 130, 30);
		JButton b=new JButton("Submit");    
		b.setBounds(260,50,100, 30);
		
		JComboBox destTextJDK1 = new JComboBox(dest);
		destTextJDK1.setBounds(270, 50, 130, 30);
		JTextField textfield1= new JTextField();
		textfield1.setBounds(270, 100, 130, 30);
		JButton targetSubmit=new JButton("Submit");    
		targetSubmit.setBounds(200,150,130, 30);
		
		//enter name label
		JLabel label = new JLabel();		
		label.setText("Select Version :");
		label.setBounds(10, 10, 100, 100);
		//empty label which will show event after button clicked
		JLabel label1 = new JLabel();
		label1.setBounds(10, 110, 600, 150);
		//textfield to enter name		
		//add to frame
	    f.add(srcTextJDK1);
	    f.add(destTextJDK1);
	    f.add(textfield1);
		f.add(label1);
		f.add(textfield);
		f.add(label);
		f.add(targetSubmit);
		f.setSize(650, 400);
		f.setResizable(true);
		f.setLocationRelativeTo(null);			  
		f.setLayout(null);    
		f.setVisible(true);    		
		//action listener
		targetSubmit.addActionListener(new ActionListener() {
	        
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.callStatus("Wait...");
				fromVersion= (String) srcTextJDK1.getItemAt(srcTextJDK1.getSelectedIndex()); 
				toVersion=(String) destTextJDK1.getItemAt(destTextJDK1.getSelectedIndex()); 
				updateSrcText=textfield.getText();
				updateDstText=textfield1.getText();
				String currentDir = System.getProperty("user.dir");
				String targetDir=currentDir+"\\src\\com\\infy\\openjdk\\configuration\\"+toVersion+".properties";
				currentDir=currentDir+"\\src\\com\\infy\\openjdk\\configuration\\"+fromVersion+".properties";
				String filterUnwantedChar = obj.cleanString(currentDir);
				File src = new File(filterUnwantedChar);
				try(BufferedReader br = new BufferedReader(new FileReader(filterUnwantedChar),8192);) {					    
					String sCurrentLine;
					String lastLine = "";
					while ((sCurrentLine = br.readLine()) != null) {					       
					    lastLine = sCurrentLine;
					}
					String syntaxCount = lastLine.substring( 0, lastLine.indexOf('='));					    
					syntaxCount = syntaxCount.substring(syntaxCount.indexOf("syntax") , syntaxCount.length());
					String countIncrmt= syntaxCount.replace("syntax", "").trim();
					int appendCount= Integer.parseInt(countIncrmt);
					appendCount=appendCount+1;					
					PrintWriter out = new PrintWriter(new FileWriter(src, true));
					out.append("\nsyntax"+appendCount+"="+updateSrcText);
					out.close();
				}
				catch(IOException e){					        
					View.logger.error(e.getMessage());
				}
				filterUnwantedChar = obj.cleanString(targetDir);
				File dest = new File(filterUnwantedChar);
				try (BufferedReader br1 = new BufferedReader(new FileReader(filterUnwantedChar),2048);) {
					String sCurrentLine;
					String lastLine = "";
					while ((sCurrentLine = br1.readLine()) != null) 
					{
						lastLine = sCurrentLine;
					}
					String syntaxCount = lastLine.substring( 0, lastLine.indexOf('='));
					syntaxCount = syntaxCount.substring(syntaxCount.indexOf("syntax") , syntaxCount.length());
					String countIncrmt= syntaxCount.replace("syntax", "");
					int appendCount= Integer.parseInt(countIncrmt);
					appendCount=appendCount+1;
							
					PrintWriter out = new PrintWriter(new FileWriter(dest, true));
					out.append("\nsyntax"+appendCount+"="+updateDstText);
					out.close();
				}
				catch(IOException e){
					View.logger.error(e.getMessage());
				}				
			}          
	    });				
	}         

	/**
	 * @param args
	 */
	public static void main(String[] args) {    
		new RulesStore();   
	}    
}