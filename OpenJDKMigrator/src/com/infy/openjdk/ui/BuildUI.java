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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import com.infy.openjdk.business.BuildCode;
import com.infy.openjdk.util.Main;
public class BuildUI {
	String getBuildPath=null;	
	BuildUI(){    
		buildApplication();
		}

	public void buildApplication() {
		JFrame f=new JFrame("Build Project"); 
		//submit button
		JButton b=new JButton("Browse");    
		b.setBounds(260,50,100, 30);
		JButton c=new JButton("Build");    
		c.setBounds(110,110,130, 30);  
		c.setEnabled(false);
		//enter name label
		JLabel label = new JLabel();		
		label.setText("Select Project :");
		label.setBounds(10, 10, 100, 100);
		//empty label which will show event after button clicked
		JLabel label1 = new JLabel();
		label1.setBounds(10, 110, 600, 150);
		//textfield to enter name
		JTextField textfield= new JTextField();
		textfield.setBounds(110, 50, 130, 30);
		//add to frame
		f.add(label1);
		f.add(textfield);
		f.add(label);
		f.add(b); 
		f.add(c);
		f.setSize(650, 400);
		f.setResizable(true);
		f.setLocationRelativeTo(null);				  
		f.setLayout(null);    
		f.setVisible(true);    		
		//action listener
		b.addActionListener(new ActionListener() {	        
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.callStatus("Wait...");
				getBuildPath="";
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Choose a directory: ");
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = jfc.showSaveDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION && jfc.getSelectedFile().isDirectory()) {
						View.logger.info("You selected the directory: " + jfc.getSelectedFile());
						getBuildPath= jfc.getSelectedFile().getAbsolutePath();
				}
					label1.setText(getBuildPath);	
					textfield.setText(getBuildPath);
					c.setEnabled(true);
			}          
	      });
				
		c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BuildCode build = new BuildCode();
				try {
					Main.callStatus("Wait...");
					build.buildMavenProject(getBuildPath);
					label1.setText("Build Success: Please check the Logs");	
				}
				catch (IOException e) {
					View.logger.error(e.getMessage());
				}
			}
		});
	}         
		
		/**
		 * @param args
		 */
		public static void main(String[] args) {    
		    new BuildUI();    
		}    
 }