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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileSystemView;

public class MultipleFileUploadUI {
	String getBuildPath1 = null;
	String getBuildPath2 = null;
	String getBuildPath3 = null;
	String getBuildPath4 = null;
	String getBuildPath5 = null;
	String fromVersion1=null;
	String fromVersion2=null;
	String fromVersion3=null;
	String fromVersion4=null;
	String fromVersion5=null;
	String toVersion1=null;
	String toVersion2=null;
	String toVersion3=null;
	String toVersion4=null;
	String toVersion5=null;
	List<String> pathList= new ArrayList<>();
	LinkedHashMap<String, String> batchMap = new LinkedHashMap<>();
	List<LinkedHashMap<String, String>> batchList = new ArrayList<>();
	String getAllPathLocation="";
	boolean setValue=false;

	public MultipleFileUploadUI() {
		setValue=false;
		JRadioButton rbmanual;
		JRadioButton rbConfig;    
		rbmanual=new JRadioButton("Select Manually");    
		rbmanual.setBounds(100,5,150,30);
		rbmanual.setSelected(true);
		rbConfig=new JRadioButton("Upload List");    
		rbConfig.setBounds(260,5,150,30);    
		ButtonGroup bg=new ButtonGroup();    
		bg.add(rbmanual);bg.add(rbConfig);    
		JLabel fromToLabel = new JLabel();
		fromToLabel.setText("From:                            To:");
		fromToLabel.setBounds(500, 20, 340, 10);		
		JFrame f = new JFrame("Select Applications");
		// upload1
		JButton button1 = new JButton("Browse");
		button1.setBounds(380, 50, 90, 25);
		// enter name label
		JLabel label = new JLabel();
		label.setText("Select Application :");
		label.setBounds(10, 10, 120, 100);
		// empty label which will show event after button clicked
		JLabel label1 = new JLabel();
		label1.setBounds(10, 110, 300, 150);
		// textfield to enter name
		JTextField textfield = new JTextField();
		textfield.setBounds(150, 50, 200, 30);		
		String[] src = { "JDK6", "JDK7", "JDK8", "JDK9", "JDK10" };
		String[] dest = { "JDK8", "JDK11" };
		JComboBox srcTextJDK1 = new JComboBox(src);
		srcTextJDK1.setBounds(500, 50, 100, 30);
		JComboBox destTextJDK1 = new JComboBox(dest);
		destTextJDK1.setBounds(610, 50, 100, 30);
		// upload2
		JLabel label2 = new JLabel();
		label2.setText("Select Application :");
		label2.setBounds(10, 50, 120, 100);
		JTextField textfield2 = new JTextField();
		textfield2.setBounds(150, 90, 200, 30);
		JButton button2 = new JButton("Browse");
		button2.setBounds(380, 90, 90, 25);		
		JComboBox srcTextJDK2 = new JComboBox(src);
		srcTextJDK2.setBounds(500, 90, 100, 30);
		JComboBox destTextJDK2 = new JComboBox(dest);
		destTextJDK2.setBounds(610, 90, 100, 30);
		// upload3
		JLabel label3 = new JLabel();
		label3.setText("Select Application :");
		label3.setBounds(10, 90, 120, 100);
		JTextField textfield3 = new JTextField();
		textfield3.setBounds(150, 130, 200, 30);
		JButton button3 = new JButton("Browse");
		button3.setBounds(380, 130, 90, 25);
		JComboBox srcTextJDK3 = new JComboBox(src);
		srcTextJDK3.setBounds(500, 130, 100, 30);
		JComboBox destTextJDK3 = new JComboBox(dest);
		destTextJDK3.setBounds(610, 130, 100, 30);
		// upload4
		JLabel label4 = new JLabel();
		label4.setText("Select Application :");
		label4.setBounds(10, 130, 120, 100);
		JTextField textfield4 = new JTextField();
		textfield4.setBounds(150, 170, 200, 30);
		JButton button4 = new JButton("Browse");
		button4.setBounds(380, 170, 90, 25);
		JComboBox srcTextJDK4 = new JComboBox(src);
		srcTextJDK4.setBounds(500, 170, 100, 30);
		JComboBox destTextJDK4 = new JComboBox(dest);
		destTextJDK4.setBounds(610, 170, 100, 30);
		// upload5
		JLabel label5 = new JLabel();
		label5.setText("Select Application :");
		label5.setBounds(10, 170, 120, 100);
		JTextField textfield5 = new JTextField();
		textfield5.setBounds(150, 210, 200, 30);
		JButton button5 = new JButton("Browse");
		button5.setBounds(380, 210, 90, 25);
		JComboBox srcTextJDK5 = new JComboBox(src);
		srcTextJDK5.setBounds(500, 210, 100, 30);
		JComboBox destTextJDK5 = new JComboBox(dest);
		destTextJDK5.setBounds(610, 210, 100, 30);
		//Upload
		JButton uploadButton = new JButton("Done");
		uploadButton.setBounds(110, 300, 140, 25);
		uploadButton.setEnabled(true);
		// add to frame
		addJFrames(rbmanual, rbConfig, fromToLabel, f, button1, label, label1, textfield, srcTextJDK1, destTextJDK1,
				label2, textfield2, button2, srcTextJDK2, destTextJDK2, label3, textfield3, button3, srcTextJDK3,
				destTextJDK3, label4, textfield4, button4, srcTextJDK4, destTextJDK4, label5, textfield5, button5,
				srcTextJDK5, destTextJDK5, uploadButton);	
		//radio buttons Manual
		rbmanual.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		//radio buttons Config
		rbConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(rbConfig.isSelected()){
					String file = openFileDirectory("");
					try(FileReader reader = new FileReader(file);
						BufferedReader bufferedReader = new BufferedReader(reader,8192);) {
					    String line;
                        int i=1;
					    while ((line = bufferedReader.readLine()) != null) {
					     View.logger.info(line);
					     if(i==1)
					     {
					    	 getBuildPath1=line; 
					    	 textfield.setText(getBuildPath1);
					     }
					     if(i==2)
					     {
					    	 getBuildPath2=line; 
					    	 textfield2.setText(getBuildPath2);
					     }
					     if(i==3)
					     {
					    	 getBuildPath3=line; 
					    	 textfield3.setText(getBuildPath3);
					     }
					     if(i==4)
					     {
					    	 getBuildPath4=line; 
					    	 textfield4.setText(getBuildPath4);
					     }
					     if(i==5)
					     {
					    	 getBuildPath5=line; 
					    	 textfield5.setText(getBuildPath5);
					     }
					     i++;
					    }
					    

					   } catch (IOException e) {
						   View.logger.error(e.getMessage());
					   }					
				}
			}
		});
		// action listener
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getBuildPath1 = "";
				getBuildPath1 = openDialogDirectory(getBuildPath1);
				textfield.setText(getBuildPath1);
			}
		});
		// action listener
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getBuildPath2 = "";
				getBuildPath2 = openDialogDirectory(getBuildPath2);
				textfield2.setText(getBuildPath2);
			}
		});
		//action listener
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getBuildPath3 = "";
				getBuildPath3 = openDialogDirectory(getBuildPath3);
				textfield3.setText(getBuildPath3);
			}
		});
		//action listener
		button4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getBuildPath4 = "";
				getBuildPath4 = openDialogDirectory(getBuildPath4);
				textfield4.setText(getBuildPath4);
			}
		});
		//action listener
		button5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getBuildPath5 = "";
				getBuildPath5 = openDialogDirectory(getBuildPath5);
				textfield5.setText(getBuildPath5);
			}
		});

		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				fromVersion1= (String) srcTextJDK1.getItemAt(srcTextJDK1.getSelectedIndex()); 
				toVersion1=(String) destTextJDK1.getItemAt(destTextJDK1.getSelectedIndex()); 
				
				fromVersion2= (String) srcTextJDK2.getItemAt(srcTextJDK2.getSelectedIndex()); 
				toVersion2=(String) destTextJDK2.getItemAt(destTextJDK2.getSelectedIndex()); 
				
				fromVersion3= (String) srcTextJDK3.getItemAt(srcTextJDK3.getSelectedIndex()); 
				toVersion3=(String) destTextJDK3.getItemAt(destTextJDK3.getSelectedIndex()); 
				
				fromVersion4= (String) srcTextJDK4.getItemAt(srcTextJDK4.getSelectedIndex()); 
				toVersion4=(String) destTextJDK4.getItemAt(destTextJDK4.getSelectedIndex()); 
				
				fromVersion5= (String) srcTextJDK5.getItemAt(srcTextJDK5.getSelectedIndex()); 
				toVersion5=(String) destTextJDK5.getItemAt(destTextJDK5.getSelectedIndex()); 
				
				pathList= new ArrayList<>();
				pathList.add(getBuildPath1);
				pathList.add(getBuildPath2);
				pathList.add(getBuildPath3);
				pathList.add(getBuildPath4);
				pathList.add(getBuildPath5);

				getActionListener();
				if(getBuildPath2!= null) {
					batchMap= new LinkedHashMap<>();
					batchMap.put("Build Path", getBuildPath2);
					batchMap.put("From Version",fromVersion2);
					batchMap.put("To Version",toVersion2);
					batchList.add(batchMap);
				}
				if(getBuildPath3!= null) {
					batchMap= new LinkedHashMap<>();
					batchMap.put("Build Path", getBuildPath3);
					batchMap.put("From Version",fromVersion3);
					batchMap.put("To Version",toVersion3);
					batchList.add(batchMap);
				}
				if(getBuildPath4!= null) {
					batchMap= new LinkedHashMap<>();
					batchMap.put("Build Path", getBuildPath4);
					batchMap.put("From Version",fromVersion4);
					batchMap.put("To Version",toVersion4);
					batchList.add(batchMap);
				}
				if(getBuildPath5!= null) {
					batchMap= new LinkedHashMap<>();
					batchMap.put("Build Path", getBuildPath5);
					batchMap.put("From Version",fromVersion5);
					batchMap.put("To Version",toVersion5);
					batchList.add(batchMap);
				}
				
				setValue=true;
				String strMultiAppPath =  "";
				strMultiAppPath = getMultiAppPath(strMultiAppPath);
				getAllPathLocation=strMultiAppPath;
				f.dispose();
				label1.setText("Build Success: Please check the Logs");
			}

			private String getMultiAppPath(String strMultiAppPath) {
				if(getBuildPath1 != null && !getBuildPath1.equals("")) {
					strMultiAppPath = strMultiAppPath.concat(getBuildPath1);
				}
				if(getBuildPath2 != null && !getBuildPath2.equals("")) {
					strMultiAppPath = strMultiAppPath.concat(getBuildPath2);
				}
				if(getBuildPath3 != null && !getBuildPath3.equals("")) {
					strMultiAppPath = strMultiAppPath.concat(getBuildPath3);
				}
				if(getBuildPath4 != null && !getBuildPath4.equals("")) {
					strMultiAppPath = strMultiAppPath.concat(getBuildPath4);
				}
				if(getBuildPath5 != null && !getBuildPath5.equals("")) {
					strMultiAppPath = strMultiAppPath.concat(getBuildPath5);
				}
				return strMultiAppPath;
			}

			private void getActionListener() {
				if(getBuildPath1!= null) {
					batchMap= new LinkedHashMap<>();
					batchMap.put("Build Path", getBuildPath1);
					batchMap.put("From Version",fromVersion1);
					batchMap.put("To Version",toVersion1);
					batchList.add(batchMap);
				}
			}
		});
	}

	private void addJFrames(JRadioButton rbmanual, JRadioButton rbConfig, JLabel fromToLabel, JFrame f, JButton button1,
			JLabel label, JLabel label1, JTextField textfield, JComboBox srcTextJDK1, JComboBox destTextJDK1,
			JLabel label2, JTextField textfield2, JButton button2, JComboBox srcTextJDK2, JComboBox destTextJDK2,
			JLabel label3, JTextField textfield3, JButton button3, JComboBox srcTextJDK3, JComboBox destTextJDK3,
			JLabel label4, JTextField textfield4, JButton button4, JComboBox srcTextJDK4, JComboBox destTextJDK4,
			JLabel label5, JTextField textfield5, JButton button5, JComboBox srcTextJDK5, JComboBox destTextJDK5,
			JButton uploadButton) {
		f.add(rbmanual);
		f.add(rbConfig);
		f.add(fromToLabel);
		f.add(label1);
		f.add(textfield);
		f.add(label);
		f.add(srcTextJDK1);
		f.add(destTextJDK1);
		f.add(button1);
		f.add(uploadButton);
		f.add(button2);
		f.add(textfield2);
		f.add(label2);
		f.add(srcTextJDK2);
		f.add(destTextJDK2);
		f.add(button3);
		f.add(textfield3);
		f.add(label3);
		f.add(srcTextJDK3);
		f.add(destTextJDK3);
		f.add(button4);
		f.add(textfield4);
		f.add(label4);
		f.add(srcTextJDK4);
		f.add(destTextJDK4);
		f.add(button5);
		f.add(textfield5);
		f.add(label5);
		f.add(srcTextJDK5);
		f.add(destTextJDK5);
		f.setSize(750, 400);
		f.setResizable(true);
		f.setLocationRelativeTo(null);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	public void getPath()
	{	
		String strMultiAppPath =  "";
		if(getBuildPath1 != null && !getBuildPath1.equals("")) {
			strMultiAppPath = strMultiAppPath.concat(getBuildPath1);
		}
		if(getBuildPath2 != null && !getBuildPath2.equals("")) {
			strMultiAppPath = strMultiAppPath.concat(getBuildPath2);
		}
		if(getBuildPath3 != null && !getBuildPath3.equals("")) {
			strMultiAppPath = strMultiAppPath.concat(getBuildPath3);
		}
		if(getBuildPath4 != null && !getBuildPath4.equals("")) {
			strMultiAppPath = strMultiAppPath.concat(getBuildPath4);
		}
		if(getBuildPath5 != null && !getBuildPath5.equals("")) {
			strMultiAppPath = strMultiAppPath.concat(getBuildPath5);
		}
		getAllPathLocation=strMultiAppPath;
	}

	/**
	 * @param getFilePath
	 * @return getFilePath
	 */
	private String openDialogDirectory(String getFilePath) {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView());
		jfc.setDialogTitle("Choose a directory: ");
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnValue = jfc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION && jfc.getSelectedFile().isDirectory()) {
				getFilePath = jfc.getSelectedFile().getAbsolutePath();
		}
		return getFilePath;

	}
	
	/**
	 * @param getFilePath
	 * @return
	 */
	private String openFileDirectory(String getFilePath) {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView());
		jfc.setDialogTitle("Choose a directory: ");
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int returnValue = jfc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION && jfc.getSelectedFile().isFile()) {
				getFilePath = jfc.getSelectedFile().getAbsolutePath();
		}
		return getFilePath;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MultipleFileUploadUI();
	}
}