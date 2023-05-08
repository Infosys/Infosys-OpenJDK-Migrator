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

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

import java.awt.BorderLayout;
import com.infy.openjdk.configuration.MyPropertyLoad;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import com.infy.openjdk.business.BuildCode;
import com.infy.openjdk.business.CloneDir;
import com.infy.openjdk.business.CustomException;
import com.infy.openjdk.business.MergeChanges;
import com.infy.openjdk.business.ProjectAnalyze;
import com.infy.openjdk.controller.AppScannerJson;
import com.infy.openjdk.report.JTableHeaderCheckBox;
import com.infy.openjdk.report.JasperMigrationReport;
import com.infy.openjdk.util.GitUsernamePassword;
import com.infy.openjdk.util.LicenseValidator;
import com.infy.openjdk.util.Main;
import com.infy.openjdk.util.Progress;
import com.infy.openjdk.util.ResourceManager;
import com.infy.openjdk.util.SWTResourceManager;
import com.infy.openjdk.util.ScanPom;
import com.infy.openjdk.util.URLReader;

import net.sf.dynamicreports.examples.Templates;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.swing.JRViewer;


/*********************************************************************************
 * 1.OpenJDK Migrator Tool UI
 * 
 *
 *@author Silambarasan_A
 *
 * 
 ***********************************************************************************/

public class View extends ViewPart   {

	public static final Logger logger = LoggerFactory.getLogger(View.class);
	public MyPropertyLoad obj = new MyPropertyLoad();
	static Shell shell = new Shell();
	public View() {
		logger.info("ConfigObj created- Config loaded");
		logger.info("Now console output will be in Sysout.txt");
		PrintStream fileStream;
		try {

			logger.info("First message from logger.");
			fileStream = new PrintStream("Sysout.txt");
			System.setOut(fileStream);


		} 
		catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
		}
	}

	public static final String ID = "OpenJDKMigrator.view";

	// Variable initialization
	String uploadedAppPath;
	String uploadedDestAppPath;
	String getBuildPath=null;
	Path path;
	String fileContent;
	String path1;

	boolean sourcePathGiven = false;
	List<Boolean> list;

	OutputStream outputStream;

	Main main;
	String projPath;
	String projSelected;
	String fromVersion;
	String toVersion;
	ProjectAnalyze projectAnalyze=new ProjectAnalyze();
    AppScannerJson scanner = null;
    CloneDir clonedir = null;
    MultipleFileUploadUI multiUI=null;
    boolean batchFlag=false;
    ArrayList<String[]> multiApplication = null;
    List<AppScannerJson> linkListAppScanner = new LinkedList<>();
    Progress progress = new Progress();
	//// Instantiating all the objects needed through-out the program
	// Added for recursiveFileDisplay
	// ""
	/**
	 * The content provider class is responsible for providing objects to the view.
	 * It can wrap existing objects in adapters or simply return objects as-is.
	 * These objects may be sensitive to the current input of the view, or ignore it
	 * and always show the same content (like Task List, for example).
	 */
	class ViewContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object parent) {
			if (parent instanceof Object[]) {
				return (Object[]) parent;
			}
			return new Object[0];
		}
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	@Override
	public void createPartControl(Composite parent) {				
		//License code starts
		try {
			if(!LicenseValidator.validateLicense()) {
				logger.info("Invalid License");
				parent.getShell().setBounds(100, 30, 600, 400);
				Display display = Display.getDefault();
				parent.setBackground(SWTResourceManager.getColour(SWT.COLOR_WHITE));
				parent.setFont(SWTResourceManager.getFonts("Segoe UI Emoji", 11, SWT.NORMAL));
				parent.setLocation(800, 384);
				shell.setVisible(false);
				shell.setFullScreen(false);
				
				parent.setLayout(null);
				parent.setVisible(true);
				Label lblThisApplicationWill = new Label(parent, SWT.NONE);
				lblThisApplicationWill.setBounds(0, 0, 1350, 120);

				lblThisApplicationWill.addFocusListener(new FocusAdapter() {				
				});

				lblThisApplicationWill.setFont(SWTResourceManager.getFonts("Arial", 20, SWT.BOLD));
				lblThisApplicationWill.setText("\n You are not authorized to run the tool. Please contact your administrator.");
				lblThisApplicationWill.setVisible(true);
				lblThisApplicationWill.setBackground(new org.eclipse.swt.graphics.Color(display,134, 38, 195));
				lblThisApplicationWill.setForeground(new org.eclipse.swt.graphics.Color(display,255,255,255));
				Label lblLicense = new Label(parent, SWT.NONE);
				lblLicense.setFont(SWTResourceManager.getFonts("Arial", 10, SWT.NONE));
				lblLicense.setForeground(SWTResourceManager.getColour(SWT.COLOR_BLACK));
				lblLicense.setBounds(30, 160, 200, 25);
				lblLicense.setText("Upload License File :");
				lblLicense.setVisible(true);
				Text txtLicensePath = new Text(parent, SWT.BORDER);
				txtLicensePath.setBounds(250, 160, 250, 25);
				txtLicensePath.setVisible(true);
				Button btnLicBrowse = new Button(parent, SWT.NONE);
				btnLicBrowse.setBounds(520, 160, 80, 25);
				btnLicBrowse.setText("Browse");
				btnLicBrowse.setVisible(true);
				btnLicBrowse.setBackground(new org.eclipse.swt.graphics.Color(display,134, 38, 195));
				btnLicBrowse.setForeground(new org.eclipse.swt.graphics.Color(display,255,255,255));
				btnLicBrowse.setToolTipText("click to select the license file");					
				
				btnLicBrowse.addListener(SWT.MouseEnter, new Listener() {
				    public void handleEvent(Event e) {				    	  
				    	btnLicBrowse.setBackground(new org.eclipse.swt.graphics.Color(display,134, 38, 195));
				    }
				});
				btnLicBrowse.addListener(SWT.MouseExit, new Listener() {
				    public void handleEvent(Event e) {				    	  
				    	btnLicBrowse.setBackground(SWTResourceManager.getColour(SWT.COLOR_BLACK));
				    }
				});
				
				btnLicBrowse.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent event) {
						GridData data = new GridData(GridData.FILL_HORIZONTAL);
						data.horizontalSpan = 4;
						// Clicking the button will allow the user
						// to select a directory
						FileDialog dlg = new FileDialog(shell);
						// Set the initial filter path according
						// to anything they've selected or typed in
						dlg.setFilterPath(txtLicensePath.getText());
						// Change the title bar text
						dlg.setText("Select license file");
						// Customizable message displayed in the dialog						
						String dir = dlg.open();
						if (dir != null) {
							// Set the text box to the new selection
							txtLicensePath.setText(dir);
						}
						
						File file = new File(dir);
						String filterUnwantedChar = obj.cleanString(System.getProperty("user.dir"));
						File dest= new File(filterUnwantedChar + File.separator +"licen	se");
						if(!dest.exists())
							dest.mkdir();
						 filterUnwantedChar = obj.cleanString(System.getProperty("user.dir"));
						dest= new File(filterUnwantedChar + File.separator +"license"+File.separator+"license.txt");
						clonedir=new CloneDir();
						try {
							clonedir.copy(file, dest);
						} 
						catch (IOException e) {
							logger.info("License File not copied.");
							logger.error(e.getMessage());
						}
						
					}
				});
				
				Label lblKey = new Label(parent, SWT.NONE);
				lblKey.setFont(SWTResourceManager.getFonts("Arial", 10, SWT.NONE));
				lblKey.setForeground(SWTResourceManager.getColour(SWT.COLOR_BLACK));
				lblKey.setBounds(30, 220, 200, 25);
				lblKey.setText("Upload Key File :");
				lblKey.setVisible(true);
				Text txtKeyPath = new Text(parent, SWT.BORDER);
				txtKeyPath.setBounds(250, 220, 250, 25);
				txtKeyPath.setVisible(true);
				Button btnKeyBrowse = new Button(parent, SWT.NONE);
				btnKeyBrowse.setBounds(520, 220, 80, 25);
				btnKeyBrowse.setText("Browse");
				btnKeyBrowse.setVisible(true);
				btnKeyBrowse.setBackground(new org.eclipse.swt.graphics.Color(display,134, 38, 195));
				btnKeyBrowse.setForeground(new org.eclipse.swt.graphics.Color(display,255,255,255));
				btnKeyBrowse.setToolTipText("click to select the key file");

				btnKeyBrowse.addListener(SWT.MouseEnter, new Listener() {
				    public void handleEvent(Event e) {				    	  
				    	btnKeyBrowse.setBackground(new org.eclipse.swt.graphics.Color(display,134, 38, 195));
				    }
				});
				
				btnKeyBrowse.addListener(SWT.MouseExit, new Listener() {
				    public void handleEvent(Event e) {					  
				    	btnKeyBrowse.setBackground(SWTResourceManager.getColour(SWT.COLOR_BLACK));
				    }
				});
				
				btnKeyBrowse.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent event) {
						GridData data = new GridData(GridData.FILL_HORIZONTAL);
						data.horizontalSpan = 4;
						// Clicking the button will allow the user
						// to select a directory
						FileDialog dlg = new FileDialog(shell);
						// Set the initial filter path according
						// to anything they've selected or typed in
						dlg.setFilterPath(txtLicensePath.getText());
						// Change the title bar text
						dlg.setText("Select key file");
						// Customizable message displayed in the dialog
						
						String dir = dlg.open();
						if (dir != null) {
							// Set the text box to the new selection
							txtKeyPath.setText(dir);
						}
						
						File file1 = new File(dir);
						File dest= new File(System.getProperty("user.dir") + File.separator +"license");
						if(!dest.exists())
							dest.mkdir();
						String filterUnwantedChar = obj.cleanString(System.getProperty("user.dir"));
						dest= new File(filterUnwantedChar + File.separator +"license"+File.separator+"key.txt");
						clonedir=new CloneDir();
						try {
							clonedir.copy(file1, dest);
						} 
						catch (IOException e) {
							logger.info("License File not copied.");
							logger.error(e.getMessage());
						}
						
					}
				});
				
				Button btnDone = new Button(parent, SWT.NONE);
				btnDone.setBounds(250, 300, 80, 25);
				btnDone.setText("Validate");
				btnDone.setVisible(true);
				btnDone.setBackground(new org.eclipse.swt.graphics.Color(display,134, 38, 195));
				btnDone.setForeground(new org.eclipse.swt.graphics.Color(display,255, 255, 255));
				btnDone.setToolTipText("click to validate the license");
				//btnBrowse.addSelectionListener(new SelectionAdapter() {					
				btnDone.addListener(SWT.MouseEnter, new Listener() {
				    public void handleEvent(Event e) {				    	  
				    	btnDone.setBackground(new org.eclipse.swt.graphics.Color(display,134, 38, 195));
				    }
				});
				btnDone.addListener(SWT.MouseExit, new Listener() {
				    public void handleEvent(Event e) {				    	  
				    	btnDone.setBackground(SWTResourceManager.getColour(SWT.COLOR_BLACK));
				    }
				});
				
				btnDone.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent event) {						
						try {
							if(LicenseValidator.validateLicense()) {
								JFrame licenseValidator = new JFrame();
								licenseValidator.setBackground(new Color(66,134,244));
								licenseValidator.setForeground(new Color(255,255,255));
								JOptionPane.showMessageDialog(licenseValidator, "License validated successfully. Please restart the tool.");

							}
							else {
								JFrame licenseValidator = new JFrame();
								licenseValidator.setBackground(new Color(66,134,244));
								licenseValidator.setForeground(new Color(255,255,255));
								JOptionPane.showMessageDialog(licenseValidator, "Invalid License File. Please upload the valid license file to proceed further");
							}
						} 
						catch (Exception e) {
							logger.info("Issue with license file validation.");
							logger.error(e.getMessage());
						}
					}
				});
				return;
			}			
		}
		catch (Exception e) {
			logger.info("Issue with license file validation.");
			logger.error(e.getMessage());
		}
			//License code ends
			
		try {
			licenceCode(parent);
	}
		catch (Exception e) {
			try {
					throw new CustomException("1001");
				} 
				catch (CustomException e1) {
					logger.error(e.getMessage(),e1);
				}
		}

	}

	/**
	 * @param parent
	 */
	private void licenceCode(Composite parent) {
		parent.addControlListener(new ControlAdapter() {
		});
		parent.addFocusListener(new FocusAdapter() {				
		});
		
		// setting up the main shell's size, location etc.
		//1160, 680
		parent.getShell().setBounds(97, 25, 1170, 690);
		parent.setForeground(SWTResourceManager.getColour(SWT.COLOR_WHITE));
		parent.setBackground(SWTResourceManager.getColour(SWT.COLOR_WHITE));
		Display display = Display.getDefault();
		parent.setFont(SWTResourceManager.getFonts("Segoe UI Emoji", 11, SWT.NORMAL));
		parent.setLocation(800, 384);
		shell.setVisible(false);
		parent.setLayout(null);

		Group group = new Group(parent, SWT.NONE);		
		group.setBounds(299, 72, 800, 349);
		group.setVisible(false);
		
		Group group1 = new Group(parent, SWT.BORDER_DASH);
		group1.setBounds(50, 110, 570, 300);
		group1.setVisible(false);
		
		Group group2 = new Group(parent, SWT.BORDER_DOT);
		group2.setBounds(0, 180, 1350, 810);
		group2.setVisible(false);
		
		Table table = new Table(parent, SWT.BORDER | SWT.NO_SCROLL);
		table.setBounds(362, 291, 600, 25);
		table.setVisible(false);
		
		Button[] radiosTechnology = new Button[2];
		radiosTechnology[0] = new Button(group2, SWT.RADIO);			
		radiosTechnology[0].setText("Single Application");
		radiosTechnology[0].setBounds(380,30, 270, 35);
		radiosTechnology[0].setSelection(true);
		radiosTechnology[0].setData("org.eclipse.e4.ui.css.CssClassName", "Label10px");
		
		radiosTechnology[1] = new Button(group2, SWT.RADIO);
		radiosTechnology[1].setText("Multiple Applications");
		radiosTechnology[1].setBounds(680, 30, 250, 35);
		radiosTechnology[1].setData("org.eclipse.e4.ui.css.CssClassName", "Label10px");
		
		Label lblSelAppPath = new Label(group2, SWT.NONE);
		lblSelAppPath.setBounds(100, 80, 280, 25);
		lblSelAppPath.setText("Application Source Code:");
		lblSelAppPath.setVisible(false);
		lblSelAppPath.setData("org.eclipse.e4.ui.css.CssClassName", "Label10px");
		
		Text txtSrcDirPath = new Text(group2, SWT.BORDER);
		txtSrcDirPath.setBounds(380, 150, 100, 23);
		txtSrcDirPath.setVisible(false);
		
		Text txtUploadedPath = new Text(group2, SWT.BORDER);
		txtUploadedPath.setBounds(380, 80, 290, 25);
		txtUploadedPath.setVisible(false);
		
		Label gitLabel = new Label(group2, SWT.NONE);
		gitLabel.setBounds(100, 120, 280, 25);
		gitLabel.setText("Git Clone Url:");
		gitLabel.setVisible(false);
		gitLabel.setData("org.eclipse.e4.ui.css.CssClassName", "Label10px");
		
		Text gitTxtBox = new Text(group2, SWT.BORDER);
		gitTxtBox.setBounds(380, 120, 290, 25);
		gitTxtBox.setVisible(false);
		
		Label lblSrcDirPath = new Label(group2, SWT.NONE);
		lblSrcDirPath.setBounds(100, 190, 230, 25);
		lblSrcDirPath.setText("Current Jdk Version:");
		lblSrcDirPath.setVisible(false);
		lblSrcDirPath.setData("org.eclipse.e4.ui.css.CssClassName", "Label10px");
		
		Label lblSelDestPath = new Label(group2, SWT.NONE);
		lblSelDestPath.setFont(SWTResourceManager.getFonts("Arial", 10, SWT.BOLD));
		lblSelDestPath.setForeground(SWTResourceManager.getColour(SWT.COLOR_BLACK));
		lblSelDestPath.setForeground(new org.eclipse.swt.graphics.Color(display,102, 102, 102));
		lblSelDestPath.setBounds(100, 140, 230, 25);
		lblSelDestPath.setText("Select Destination Path:");
		lblSelDestPath.setVisible(false);
					
		Label lblDestDirPath = new Label(group2, SWT.NONE);
		lblDestDirPath.setBounds(100, 240, 230, 25);
		//commented below code for PwC
		lblDestDirPath.setText("Target Jdk Version:");
		lblDestDirPath.setVisible(false);
		lblDestDirPath.setData("org.eclipse.e4.ui.css.CssClassName", "Label10px");
		
		Combo comboSrcServer = new Combo(group2, SWT.NONE);
		comboSrcServer.setItems(new String[] { "JDK5","JDK6", "JDK7", "JDK8","JDK9","JDK10","JDK11" });
		comboSrcServer.setBounds(380, 190, 100, 23);
		comboSrcServer.setVisible(false);

		Combo comboDestServer = new Combo(group2, SWT.NONE);
		comboDestServer.setBounds(380, 240, 100, 23);
		comboDestServer.setItems(new String[] {"JDK8", "JDK11" });
		comboDestServer.setVisible(false);

		Group group3 = new Group(parent, SWT.NONE);
		group3.setBounds(5, 520, 800, 60);
		group3.setVisible(false);

		Group group4 = new Group(group1, SWT.BORDER_DOT);
		group4.setBounds(50, 200, 450, 60);
		group4.setVisible(false);

		Group group5 = new Group(parent, SWT.NONE);
		group5.setBounds(0, 0, 1350, 75);
		group5.setVisible(true);
		
		org.eclipse.swt.graphics.Color colorBGLogo = new org.eclipse.swt.graphics.Color(display,134, 38, 195); //background
		group5.setBackground(colorBGLogo );
		
		Group group6 = new Group(parent, SWT.NONE);
		group6.setBounds(0, 0, 1350, 180);
		group6.setVisible(true);
		
		Label lblInfyLogo = new Label(group5, SWT.NONE);
		lblInfyLogo.setBackground(new org.eclipse.swt.graphics.Color(display,134, 38, 195));
		lblInfyLogo.setImage(ResourceManager.getPluginImage("OpenJDKMigrator", "icons/Infy_Logo.png"));
		lblInfyLogo.setBounds(20, 12, 125, 45);

		Label lblMigWiz = new Label(group5, SWT.NONE);
		lblMigWiz.setBounds(140, 20, 800, 45);
		lblMigWiz.setFont(SWTResourceManager.getFonts("Arial", 22, SWT.NORMAL));
		lblMigWiz.setForeground(new org.eclipse.swt.graphics.Color(display,255,255,255)); //JDK Migrator Text
		lblMigWiz.setText("JDK Migrator");
		
		Label lblCopyRightFooter = new Label(group2, SWT.NONE);
		lblCopyRightFooter.setFont(SWTResourceManager.getFonts("Arial", 10, SWT.BOLD));
		lblCopyRightFooter.setBackground(new org.eclipse.swt.graphics.Color(display,134, 38, 195));
		lblCopyRightFooter.setForeground(new org.eclipse.swt.graphics.Color(display,255,255,255));
		lblCopyRightFooter.setBounds(0, 450, 1350, 50);
		lblCopyRightFooter.setText("Copyright � 2022 Infosys Limited");
					
		Label lblVersion = new Label(group1, SWT.NONE);
		lblVersion.setBounds(875, 72, 50, 25);
		lblVersion.setFont(SWTResourceManager.getFonts("Segoe UI Emoji", 10, SWT.BOLD));
		lblVersion.setForeground(SWTResourceManager.getColour(SWT.COLOR_BLACK));
		lblVersion.setText("Ver 1.0");

		Label lblThisApplicationWill = new Label(group6, SWT.NONE);
		lblThisApplicationWill.setBounds(20, 100, 1350, 70);
		lblThisApplicationWill.addFocusListener(new FocusAdapter() {
		});
		
		lblThisApplicationWill.setText("This tool helps in migrating an application from Oracle JDK to Open JDK by scanning the application source code, identifying the deprecated APIs and \nunsupported features.The tool provides assessment report, auto remediation and the effort required for manual remediation.");
		lblThisApplicationWill.setBackground(new org.eclipse.swt.graphics.Color(display,255, 255, 255));
		lblThisApplicationWill.setForeground(new org.eclipse.swt.graphics.Color(display,0, 0, 0));
		lblThisApplicationWill.setForeground(new org.eclipse.swt.graphics.Color(display,0,0,0));
		lblThisApplicationWill.setFont(SWTResourceManager.getFonts("Verdana", 12, SWT.NONE));
		
		Button btnAnalyze = new Button(group2, SWT.NONE);
		btnAnalyze.setBounds(380, 300, 160, 30);
		btnAnalyze.setBackground(new org.eclipse.swt.graphics.Color(display,134, 38, 195));
		btnAnalyze.setForeground(new org.eclipse.swt.graphics.Color(display,255, 255, 255));
		btnAnalyze.setText("Analyze");
		btnAnalyze.setVisible(false);
		btnAnalyze.setToolTipText("click to analyze the source code");
		btnAnalyze.setData("org.eclipse.e4.ui.css.CssClassName", "Label10px");
		
		btnAnalyze.addListener(SWT.MouseEnter, new Listener() {
		    public void handleEvent(Event e) {			    	  
		    	btnAnalyze.setBackground(new org.eclipse.swt.graphics.Color(display,134, 38, 195));
		    }
		});
		
		btnAnalyze.addListener(SWT.MouseExit, new Listener() {
		    public void handleEvent(Event e) {			    	  
		    	btnAnalyze.setBackground(SWTResourceManager.getColour(SWT.COLOR_BLACK));
		      }
		});
					
		Button btnMultiAnalyze = new Button(group2, SWT.NONE);
		btnMultiAnalyze.setBounds(380, 300, 160, 30);
		btnMultiAnalyze.setBackground(new org.eclipse.swt.graphics.Color(display,134, 38, 195));
		btnMultiAnalyze.setForeground(new org.eclipse.swt.graphics.Color(display,255, 255, 255));
		btnMultiAnalyze.setText("Analyze");
		btnMultiAnalyze.setVisible(false);
		btnMultiAnalyze.setToolTipText("click to analyze the applications");
		btnMultiAnalyze.setData("org.eclipse.e4.ui.css.CssClassName", "Label10px");
		
		btnMultiAnalyze.addListener(SWT.MouseEnter, new Listener() {
		    public void handleEvent(Event e) {				  
		    	btnMultiAnalyze.setBackground(new org.eclipse.swt.graphics.Color(display,134, 38, 195));
		    }
		});
		
		btnMultiAnalyze.addListener(SWT.MouseExit, new Listener() {
		    public void handleEvent(Event e) {			    	  
		    	btnMultiAnalyze.setBackground(SWTResourceManager.getColour(SWT.COLOR_BLACK));
		    }
		});
		
		Button btnMultiAnalyzeMigrate = new Button(group2, SWT.NONE);
		btnMultiAnalyzeMigrate.setBounds(470, 300, 160, 25);
		btnMultiAnalyzeMigrate.setBackground(new org.eclipse.swt.graphics.Color(display,20, 132, 219));
		btnMultiAnalyzeMigrate.setForeground(new org.eclipse.swt.graphics.Color(display,255, 255, 255));
		btnMultiAnalyzeMigrate.setFont(SWTResourceManager.getFonts("verdana", 8, SWT.BOLD));
		btnMultiAnalyzeMigrate.setText("ANALYZE AND MIGRATE");
		btnMultiAnalyzeMigrate.setVisible(false);
		btnMultiAnalyzeMigrate.setToolTipText("click to analyze and migrate the applications");			
		Button btnBrowse = new Button(group2, SWT.NONE);
		btnBrowse.setBounds(680, 80, 80, 25);
		btnBrowse.setText("Browse");
		btnBrowse.setVisible(false);
		btnBrowse.setBackground(new org.eclipse.swt.graphics.Color(display,134, 38, 195));
		btnBrowse.setForeground(new org.eclipse.swt.graphics.Color(display,255,255,255));
		btnBrowse.setToolTipText("click to select the source code");
		btnBrowse.setData("org.eclipse.e4.ui.css.CssClassName", "Label8px");
		//btnBrowse.addSelectionListener(new SelectionAdapter() {				
		btnBrowse.addListener(SWT.MouseEnter, new Listener() {
		    public void handleEvent(Event e) {
		    	btnBrowse.setBackground(new org.eclipse.swt.graphics.Color(display,134, 38, 195));
		    }
		});
		
		btnBrowse.addListener(SWT.MouseExit, new Listener() {
		    public void handleEvent(Event e) {
		    	btnBrowse.setBackground(SWTResourceManager.getColour(SWT.COLOR_BLACK));
		    }
		});
			 
		Button btnGetCode = new Button(group2, SWT.NONE);
		btnGetCode.setBounds(680, 120, 80, 25);
		btnGetCode.setText("Confirm");
		btnGetCode.setVisible(false);
		btnGetCode.setBackground(new org.eclipse.swt.graphics.Color(display,134, 38, 195));
		btnGetCode.setForeground(new org.eclipse.swt.graphics.Color(display,255,255,255));
		btnGetCode.setData("org.eclipse.e4.ui.css.CssClassName", "Label8px");
		
		btnGetCode.addListener(SWT.MouseEnter, new Listener() {
		    public void handleEvent(Event e) {
		    	btnGetCode.setBackground(new org.eclipse.swt.graphics.Color(display,134, 38, 195));
		    }
		});
		
		btnGetCode.addListener(SWT.MouseExit, new Listener() {
		    public void handleEvent(Event e) {
		    	btnGetCode.setBackground(SWTResourceManager.getColour(SWT.COLOR_BLACK));
		    }
		});
		
		Label lblmultiBrowse = new Label(group2, SWT.NONE);
		lblmultiBrowse.setBounds(100, 120, 280, 25);
		lblmultiBrowse.setText("Application Inventory:");
		lblmultiBrowse.setVisible(false);
		lblmultiBrowse.setForeground(new org.eclipse.swt.graphics.Color(display,0,0,0));
		lblmultiBrowse.setData("org.eclipse.e4.ui.css.CssClassName", "Label10px");

		Text txtmultiUploadConfig = new Text(group2, SWT.BORDER);
		txtmultiUploadConfig.setBackground(SWTResourceManager.getColour(SWT.COLOR_WHITE));
		txtmultiUploadConfig.setBounds(380, 120, 290, 25);
		txtmultiUploadConfig.setVisible(false);
		
		Button multiBrowse = new Button(group2, SWT.NONE);
		multiBrowse.setBounds(680, 120, 80, 25);
		multiBrowse.setText("Browse");
		multiBrowse.setVisible(false);
		multiBrowse.setBackground(SWTResourceManager.getColour(SWT.COLOR_BLACK));
		multiBrowse.setForeground(new org.eclipse.swt.graphics.Color(display,255, 255, 255));
		multiBrowse.setToolTipText("click to select the config file");
		multiBrowse.setData("org.eclipse.e4.ui.css.CssClassName", "Label8px");

		multiBrowse.addListener(SWT.MouseEnter, new Listener() {
		    public void handleEvent(Event e) {				  
		    	multiBrowse.setBackground(new org.eclipse.swt.graphics.Color(display,134, 38, 195));
		    }
		});
		
		multiBrowse.addListener(SWT.MouseExit, new Listener() {
		    public void handleEvent(Event e) {
		    	multiBrowse.setBackground(SWTResourceManager.getColour(SWT.COLOR_BLACK));
		    }
		});
		
		Text txtmultiUpload = new Text(group2, SWT.NONE);
		txtmultiUpload.setBackground(SWTResourceManager.getColour(SWT.COLOR_WHITE));
		txtmultiUpload.setBounds(350, 130, 290, 50);
		txtmultiUpload.setVisible(false);
					
		Button upload = new Button(group2, SWT.NONE);
		upload.setBounds(4, 90, 60, 25);
		upload.setText("UPLOAD");
		upload.setVisible(false);
		
		Label lblDwnldConfig = new Label(group2, SWT.NONE);
		lblDwnldConfig.setBounds(100, 80, 280, 25);
		
		lblDwnldConfig.setText("Download Template:");
		lblDwnldConfig.setVisible(false);
		lblDwnldConfig.setData("org.eclipse.e4.ui.css.CssClassName", "Label10px");
					
		Button templateBtn = new Button(group2, SWT.NONE);
		templateBtn.setBounds(380, 80, 290, 25);
		templateBtn.setText("Here");
		templateBtn.setVisible(false);
		templateBtn.setBackground(SWTResourceManager.getColour(SWT.COLOR_WHITE));
		templateBtn.setForeground(new org.eclipse.swt.graphics.Color(display,0, 0, 0));
		templateBtn.setData("org.eclipse.e4.ui.css.CssClassName", "Label10px");
		
		Text txtUploadedDestPath = new Text(group2, SWT.BORDER);
		txtUploadedDestPath.setBounds(540, 140, 250, 23);
		txtUploadedDestPath.setVisible(false);

		Text lblUploadedSrcPath = new Text(group2, SWT.WRAP|SWT.MULTI);
		lblUploadedSrcPath.setFont(SWTResourceManager.getFonts("Segoe UI",8, SWT.NONE));
		lblUploadedSrcPath.setForeground(SWTResourceManager.getColour(SWT.COLOR_BLACK));
		lblUploadedSrcPath.setBackground(SWTResourceManager.getColour(SWT.COLOR_WHITE));
		lblUploadedSrcPath.setBounds(380, 120, 290, 25);
		lblUploadedSrcPath.setEditable(false);
		lblUploadedSrcPath.setVisible(false);
		
		Button btnDestBrowse = new Button(group2, SWT.NONE);
		btnDestBrowse.setBounds(800, 140, 50, 25);
		btnDestBrowse.setText("Browse");
		btnDestBrowse.setVisible(false);
		btnDestBrowse.setBackground(new org.eclipse.swt.graphics.Color(display,66,134,244));

		Label lblName = new Label(parent, SWT.NONE);
		lblName.setBounds(470, 115, 100, 25);
		lblName.setFont(SWTResourceManager.getFonts("Segoe UI", 10, SWT.BOLD));
		lblName.setForeground(SWTResourceManager.getColour(SWT.COLOR_DARK_CYAN));
		lblName.setText("Name:");
		lblName.setVisible(false);

		Label lblPublisher = new Label(parent, SWT.NONE);
		lblPublisher.setBounds(470, 152, 113, 25);
		lblPublisher.setForeground(SWTResourceManager.getColour(SWT.COLOR_BLACK));
		lblPublisher.setFont(SWTResourceManager.getFonts("Segoe UI", 10, SWT.BOLD));

		lblPublisher.setText("Publisher:");
		lblPublisher.setVisible(false);
		Label lblInfyLtd = new Label(parent, SWT.NONE);
		lblInfyLtd.setVisible(true);
		lblInfyLtd.setBounds(600, 152, 203, 25);
		lblInfyLtd.setFont(SWTResourceManager.getFonts("Segoe UI", 10, SWT.NORMAL));
		lblInfyLtd.setText("Infosys Limited");
		lblInfyLtd.setVisible(false);

		Button btnCheckButton = new Button(group1, SWT.CHECK);
		btnCheckButton.setForeground(SWTResourceManager.getColour(SWT.COLOR_BLUE));
		btnCheckButton.setFont(SWTResourceManager.getFonts("Segoe UI", 10, SWT.NORMAL));
		btnCheckButton.setBounds(250, 322, 462, 25);
		btnCheckButton.setText("Do not show this again for apps from the publisher above.");

		Button btnRun = new Button(parent, SWT.NONE);
		btnRun.setFont(SWTResourceManager.getFonts("Segoe UI", 12, SWT.BOLD));
		btnRun.setBounds(380, 450, 100, 25);
		btnRun.setBackground(new org.eclipse.swt.graphics.Color(display,66,134,244));
		btnRun.setForeground(new org.eclipse.swt.graphics.Color(display,255,255,255));
		
		/**
		* This event will be fired when user clicks on run button.
		*/

		group5.setVisible(true);
		logger.info("Run btton clicked!!");

		handleEventGroup(group2, radiosTechnology, lblSelAppPath, txtSrcDirPath, txtUploadedPath, gitLabel, gitTxtBox,
				comboSrcServer, comboDestServer, lblInfyLogo, lblMigWiz, lblCopyRightFooter, btnAnalyze,
				lblUploadedSrcPath, lblName, lblPublisher, lblInfyLtd, btnCheckButton, btnRun);

		logger.info("ComboDestServer Inside BTNRUN: " + comboDestServer.getText());
		/**
		* This event will be fired when user clicks on back button.
		*/

		btnBrowse.setVisible(true);
		btnGetCode.setVisible(true);
		btnAnalyze.setVisible(true);
		lblSelAppPath.setVisible(true);
		gitTxtBox.setVisible(true);
		gitLabel.setVisible(true);

		txtSrcDirPath.setVisible(false);
		comboSrcServer.setVisible(false);
		comboDestServer.setVisible(false);
		lblSrcDirPath.setVisible(false);
		lblDestDirPath.setVisible(false);
		group3.setVisible(false);
		group4.setVisible(true);
		group5.setVisible(true);
		fromVersion=comboSrcServer.getText();
		toVersion=comboDestServer.getText();
				
		//get git code					
		btnGetCode.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				URLReader gitReader=new URLReader();
				try {
					String currentDirectory = System.getProperty("user.dir");
					if(gitTxtBox.getText() != null && !gitTxtBox.getText().equals("") && gitTxtBox.getText().endsWith(".git")) {
							String projectName = gitTxtBox.getText().substring(gitTxtBox.getText().lastIndexOf("/")+1,gitTxtBox.getText().indexOf(".git"));
							GitUsernamePassword userpwd = new GitUsernamePassword();
							userpwd = userpwd.getUserNamePassword();
							if(!userpwd.getUserName().equals("") && !userpwd.getPassword().equals("")) {
								gitReader.connectgit(gitTxtBox.getText(), currentDirectory+"\\"+projectName,userpwd.getUserName(),userpwd.getPassword());
								txtUploadedPath.setText(currentDirectory+"\\"+projectName);
							}
							else {
								JOptionPane.showMessageDialog(null, "Please enter username and password","Error",JOptionPane.ERROR_MESSAGE);
							}
					}
					else {
						JOptionPane.showMessageDialog(null, "Please enter proper git clone URL","Error",JOptionPane.ERROR_MESSAGE);
					}								 								 
				} 
				catch (Exception e) {
					logger.error(e.getMessage(),e);
					JOptionPane.showMessageDialog(null, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
				
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				GridData data = new GridData(GridData.FILL_HORIZONTAL);
				data.horizontalSpan = 0;
				System.out.println("inside browse");
				txtUploadedPath.setLayoutData(data);
				// Clicking the button will allow the user
				// to select a directory
				DirectoryDialog dlg = new DirectoryDialog(shell);
				// Set the initial filter path according
				// to anything they've selected or typed in
				dlg.setFilterPath(txtUploadedPath.getText());
				// Change the title bar text
				dlg.setText("SWT's DirectoryDialog");
				// Customizable message displayed in the dialog
				dlg.setMessage("Select a directory");
				String dir = dlg.open();
				if (dir != null) {
					// Set the text box to the new selection
					txtUploadedPath.setText(dir);
				}
			}
		});
		ModifyListener listener = new ModifyListener() {
		/** {@inheritDoc} */
		public void modifyText(ModifyEvent e) {
			uploadedAppPath=txtUploadedPath.getText();
			logger.info("uploadedAppPath"+uploadedAppPath);
			if(uploadedAppPath != null && !uploadedAppPath.equals("")) {
				File sourcePath = new File(uploadedAppPath);							
				String strBuildVersion = new ScanPom().getBuildVersion(sourcePath);
				logger.info("version returned "+strBuildVersion);
				if(strBuildVersion != null) {
					switch(strBuildVersion){
						case "1.5":
						fromVersion="JDK5";
						break;
						
						case "1.6":
						fromVersion="JDK6";
						break;
							
						case "1.7":
						fromVersion="JDK7";
						break;
						
						case "1.8":
						fromVersion="JDK8";
						break;
								
						case "1.9":
						fromVersion="JDK9";
						break;
								
						case "10":
						fromVersion="JDK10";
						break;
						
						case "11":
						fromVersion="JDK11";
						break;
								
						default:
						fromVersion="JDK5";
						break;
					}
				}
				else {
						fromVersion="JDK5";
					}
				comboSrcServer.setVisible(true);
				comboDestServer.setVisible(true);
				lblSrcDirPath.setVisible(true);
				lblDestDirPath.setVisible(true);
				comboSrcServer.setText(fromVersion);
						
				lblUploadedSrcPath.setVisible(true);
				btnAnalyze.setVisible(true);
				}
			}
		};
		ModifyListener listener1 = new ModifyListener() {
		/** {@inheritDoc} */
			public void modifyText(ModifyEvent e) {
				fromVersion=comboSrcServer.getText();
				if(fromVersion.contains("JDK5") || fromVersion.contains("JDK6") || fromVersion.contains("JDK7") || fromVersion.contains("JDK8")) {
					comboDestServer.setItems(new String[] { "JDK8","JDK11"});
				}
				else if( fromVersion.contains("JDK9") || fromVersion.contains("JDK10")) {
					comboDestServer.setItems(new String[] { "JDK11"});
				}
					logger.info("fromVersion"+fromVersion);
			}
		};
					
		ModifyListener listener2 = new ModifyListener() {
			/** {@inheritDoc} */
			public void modifyText(ModifyEvent e) {
				toVersion=comboDestServer.getText();
				logger.info("toVersion"+toVersion);
			}
		};

		txtUploadedPath.addModifyListener(listener);
		comboSrcServer.addModifyListener(listener1);
		comboDestServer.addModifyListener(listener2);					
		//Select Destination Server					
		btnDestBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				GridData data = new GridData(GridData.FILL_HORIZONTAL);
				data.horizontalSpan = 4;
				txtUploadedDestPath.setLayoutData(data);
				// Clicking the button will allow the user
				// to select a directory
				DirectoryDialog dlg = new DirectoryDialog(shell);
				// Set the initial filter path according
				// to anything they've selected or typed in
				dlg.setFilterPath(txtUploadedDestPath.getText());
				// Change the title bar text
				dlg.setText("SWT's DirectoryDialog");
				// Customizable message displayed in the dialog
				dlg.setMessage("Select a directory");
				String dir = dlg.open();
				if (dir != null) {
					// Set the text box to the new selection
					txtUploadedDestPath.setText(dir);
				}
			}
		});
		
		ModifyListener destlistener = new ModifyListener() {
			/** {@inheritDoc} */
			public void modifyText(ModifyEvent e) {
				uploadedDestAppPath=txtUploadedDestPath.getText();
				logger.info("txtUploadedDestPath"+txtUploadedDestPath);
			}
		};
		
		txtUploadedDestPath.addModifyListener(destlistener);										
		//end					
				
		/**
		* This event will be fired when user clicks on Analyze button.
		*/

		//radio button listener for single upload
		SelectionListener single = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				logger.info("inside single");
				multiBrowse.setVisible(false);
				lblmultiBrowse.setVisible(false);
				txtmultiUploadConfig.setVisible(false);
				btnBrowse.setVisible(true);
							
				btnGetCode.setVisible(true);
				logger.info("Single Mode!!");
				radiosTechnology[0].setVisible(true);
				radiosTechnology[1].setVisible(true);
				btnAnalyze.setEnabled(true);
				txtSrcDirPath.clearSelection();
				txtSrcDirPath.setText("");
				lblName.setVisible(false);
				lblPublisher.setVisible(false);
				lblInfyLtd.setVisible(false);
				btnCheckButton.setVisible(false);
				btnRun.setVisible(false);
				lblInfyLogo.setVisible(true);
				lblCopyRightFooter.setVisible(true);
				lblMigWiz.setVisible(true);
				txtUploadedPath.setVisible(true);
				txtUploadedPath.setText("");
				txtUploadedPath.clearSelection();
				lblUploadedSrcPath.setVisible(true);
				lblUploadedSrcPath.setText("");
				lblSelAppPath.setVisible(true);
				gitTxtBox.setVisible(true);
				gitTxtBox.setText("");
				gitLabel.setVisible(true);

				comboSrcServer.setVisible(false);
				comboSrcServer.clearSelection();
				comboSrcServer.setText("");
				comboDestServer.setVisible(false);
				comboDestServer.setText("");
				comboDestServer.clearSelection();
				group2.setVisible(true);
				logger.info("ComboDestServer Inside BTNRUN: " + comboDestServer.getText());
				logger.info("Inside BTNRun");
				/**
				* This event will be fired when user clicks on back button.
				*/
				btnBrowse.setVisible(true);
				btnGetCode.setVisible(true);
				upload.setVisible(false);
				btnAnalyze.setVisible(true);
				lblSelAppPath.setVisible(true);
				gitTxtBox.setVisible(true);
				gitLabel.setVisible(true);
				txtSrcDirPath.setVisible(false);
				txtUploadedPath.setVisible(true);
				comboSrcServer.setVisible(false);
				comboDestServer.setVisible(false);
				lblSrcDirPath.setVisible(false);
				lblDestDirPath.setVisible(false);
				btnMultiAnalyze.setVisible(false);
				btnMultiAnalyzeMigrate.setVisible(false);
				templateBtn.setVisible(false);
				lblDwnldConfig.setVisible(false);
				group3.setVisible(false);
				group4.setVisible(true);
				group5.setVisible(true);
				fromVersion=comboSrcServer.getText();
				toVersion=comboDestServer.getText();
				txtmultiUpload.setVisible(false);							
			}
		};
		radiosTechnology[0].addSelectionListener(single);					
		//end
				
		//radio button listener for batch upload
		SelectionListener batch = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				logger.info("inside batch");
				multiBrowse.setVisible(true);
				lblmultiBrowse.setVisible(true);
				txtmultiUploadConfig.setVisible(true);
				templateBtn.setVisible(true);
				lblDwnldConfig.setVisible(true);
				btnBrowse.setVisible(false);
				btnAnalyze.setVisible(false);
				lblSelAppPath.setVisible(false);
				lblSrcDirPath.setVisible(false);
				lblDestDirPath.setVisible(false);
				comboSrcServer.setVisible(false);
				comboDestServer.setVisible(false);
				lblUploadedSrcPath.setVisible(false);
				btnMultiAnalyze.setVisible(true);
				txtUploadedPath.setVisible(false);
				txtmultiUpload.setVisible(false);
				gitLabel.setVisible(false);
				gitTxtBox.setVisible(false);
				btnGetCode.setVisible(false);
			}
		};

		radiosTechnology[1].addSelectionListener(batch);					
		//multi file upload button listener
		multiBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				logger.info("inside multibrowse");							
				try {
					String file = null;
					multiApplication = new ArrayList<>();
					GridData data = new GridData(GridData.FILL_HORIZONTAL);
					data.horizontalSpan = 4;
					txtUploadedPath.setLayoutData(data);
					// Clicking the button will allow the user
					// to select a directory
					FileDialog dlg = new FileDialog(shell);
					// Set the initial filter path according
					// to anything they've selected or typed in
					dlg.setFilterPath(txtUploadedPath.getText());
					// Change the title bar text
					dlg.setText("Select config file");
					// Customizable message displayed in the dialog
					String dir = dlg.open();
					if (dir != null) {
						// Set the text box to the new selection
						file = dir;
					}
					logger.info(file);
					try(FileReader reader = new FileReader(file);
						BufferedReader bufferedReader = new BufferedReader(reader,8192);) {							    
							String line;							   
							int i=1;
							while ((line = bufferedReader.readLine()) != null) {
								logger.info(line);									
								String[] strApplication = line.split("\\|");
								multiApplication.add(strApplication);
								i++;
							}
						}
					catch (IllegalArgumentException | IOException e) {
						logger.error(e.getMessage(),e);
					}
					txtmultiUpload.setText(file);
					txtmultiUploadConfig.setText(file);
					txtmultiUpload.setVisible(true);							    
					versionFinder();
				} 
				catch (Exception e) {
					logger.error(e.getMessage(),e);
				}																													
			}

			/**
			 * versionFinder
			 */
			private void versionFinder() {
				if(multiApplication.get(0)[1] == null || multiApplication.get(0)[1].equals("")) {
					String filterUnwantedChar = obj.cleanString(multiApplication.get(0)[0]);
					String strVersion = new ScanPom().getBuildVersion(new File(filterUnwantedChar));
					if(strVersion != null) {
						switch(strVersion){
							case "1.5":
							fromVersion="JDK5";
							break;
							case "1.7":
							fromVersion="JDK7";
							break;
							case "1.6":
							fromVersion="JDK6";
							break;
							case "1.9":
							fromVersion="JDK9";
							break;
							case "1.8":
							fromVersion="JDK8";
							break;
							case "1.10":
							fromVersion="JDK10";
							break;
							default:
							fromVersion="JDK5";
							break;
						}
					}
				}
			}
		});
				
		//download Template
		templateBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				logger.info("inside download");							
				File file = new File("./config.txt");
				logger.info("test-->" + file.getAbsolutePath());
				if (!Desktop.isDesktopSupported()) {
					logger.info("Desktop is not supported");
					return;
				}

				Desktop desktop = Desktop.getDesktop();
				if (file.exists())
				try {
					desktop.open(file);
				} 
				catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});
		
		upload.addSelectionListener(new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent event) {
			multiBrowse.setVisible(true);
			templateBtn.setVisible(true);
			String strMultiAppPath =  "";
			if(multiUI.getBuildPath1 != null && !multiUI.getBuildPath1.equals("")) {
				strMultiAppPath = strMultiAppPath.concat("\n"+multiUI.getBuildPath1);
			}
			if(multiUI.getBuildPath2 != null && !multiUI.getBuildPath2.equals("")) {
				strMultiAppPath = strMultiAppPath.concat("\n"+multiUI.getBuildPath2);
			}
			if(multiUI.getBuildPath3 != null && !multiUI.getBuildPath3.equals("")) {
				strMultiAppPath = strMultiAppPath.concat("\n"+multiUI.getBuildPath3);
			}
			if(multiUI.getBuildPath4 != null && !multiUI.getBuildPath4.equals("")) {
				strMultiAppPath = strMultiAppPath.concat("\n"+multiUI.getBuildPath4);
			}
			if(multiUI.getBuildPath5 != null && !multiUI.getBuildPath5.equals("")) {
				strMultiAppPath = strMultiAppPath.concat("\n"+multiUI.getBuildPath5);
			}

			lblSrcDirPath.setVisible(false);
			lblDestDirPath.setVisible(false);
		}
});			
				
btnAnalyze.addSelectionListener(new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {							
			// ...
			logger.info("Analysis Started>>");
			if(uploadedAppPath == null || fromVersion.equalsIgnoreCase("")|| toVersion.equalsIgnoreCase("")) {								
				JOptionPane.showMessageDialog(null, "Select Application Source Code, Current JDK Version and Target JDK Version to proceed.","Error",JOptionPane.ERROR_MESSAGE);								
			}
			else {
				logger.info("text>>"+uploadedAppPath+">>"+fromVersion+">>"+toVersion);
				try {
					projectAnalyze.displayDirectoryContents(new File(uploadedAppPath));								
					progress.progressbar(projectAnalyze.getTotalCount()+1);
					progress.getF().setTitle("Analysis in progress....");								
					scanner = new AppScannerJson();								
					scanner.getAllFiles(uploadedAppPath,fromVersion,toVersion,progress);
					scanner.parseFiles();
				} 
				catch (Exception e1) {
					logger.info("Error occured while Scanning the files");
					logger.error(e1.getMessage(),e1);
				}
				//Analysis code starts here														
				//Summary Begin							
				logger.info("Inside multi Report");
				projectAnalyze=new ProjectAnalyze();
				logger.info("Inside Report multi");
				String strapplicationName = uploadedAppPath.substring(uploadedAppPath.lastIndexOf('\\')+1,uploadedAppPath.length());
				int totalAffectedFiles =scanner.getAutomatedFiles().size()+scanner.getAssistedFiles().size();
				projectAnalyze.getMigrationSummary(scanner.getTotalFilesScanned(),totalAffectedFiles,scanner.getAutomatedFiles().size(),scanner.getAssistedFiles().size(),"pre",scanner.getNoOfAutomatedLines(),scanner.getNoOfAssistedLines());
				Double totalEffort = scanner.getMandEffortRequired() + scanner.getEffortRequired();
				projectAnalyze.getEffortReport(strapplicationName,scanner.getMandEffortRequired(), scanner.getEffortRequired());
				projectAnalyze.getEffortBreakDown(strapplicationName,scanner.getNoOfInfo(),scanner.getNoOfSimple(), scanner.getNoOfMedium(),scanner.getNoOfComplex(),scanner.getInfoEffortRequired(),scanner.getSimEffortRequired(),scanner.getMedEffortRequired(),scanner.getComEffortRequired(),scanner.getNoOfMandInfo(),scanner.getNoOfMandSimple(),scanner.getNoOfMandMedium(),scanner.getNoOfMandComplex(),scanner.getMandInfoEffortRequired(),scanner.getMandSimEffortRequired(),scanner.getMandMedEffortRequired(),scanner.getMandComEffortRequired(),scanner.getNoOfMandRedesign(),scanner.getNoOfMandArch(),scanner.getMandredEffortRequired(),scanner.getMandArchEffortRequired());
						
				logger.info("Inside multi Report2");
				JasperMigrationReport executionReport = new JasperMigrationReport();
				logger.info("Inside multi Report3");
				StyleBuilder titleStyle = stl.style(stl.style().bold())
					.setFontName(Font.SANS_SERIF).setFontSize(20);
				StyleBuilder titleStyle1 = stl.style(stl.style().bold())
					.setFontName(Font.SANS_SERIF).setFontSize(10);
				JasperReportBuilder[] jsReport = new JasperReportBuilder[2];
					logger.info("Inside multi Report4");
				jsReport[0] = report().setTemplate(Templates.reportTemplate).ignorePagination().setPageFormat(PageType.B4).title(cmp.text("Detailed Assessment Report - "+strapplicationName).setStyle(titleStyle).setHorizontalTextAlignment(
					HorizontalTextAlignment.CENTER), cmp.verticalGap(10), cmp.line(), cmp.verticalGap(5));
				logger.info("Inside multi Report5");
					jsReport[0].summary(cmp.text("Current Version: "+fromVersion+"     Target Version: "+toVersion).setStyle(titleStyle1),
				cmp.verticalGap(20),cmp.text("Summary"),cmp.subreport(executionReport.createReport15(projectAnalyze.getListGrid19(),projectAnalyze.getSummaryColumnHeaders())),
				cmp.verticalGap(20),cmp.text("Estimated Assisted Remediation Effort (person hours)"),cmp.subreport(executionReport.createReport17(projectAnalyze.getListGrid15(),projectAnalyze.getEffortHeaders())),
				cmp.text("Total Remediation Effort = "+Math.round(totalEffort)).setStyle(titleStyle1),
				cmp.text("*Optional changes are the deprecated java APIs which may work fine in current target version but may not work in higher java versions."),
				cmp.verticalGap(20),cmp.text("Effort Break Down"),cmp.subreport(executionReport.createReport18(projectAnalyze.getListGrid13(),projectAnalyze.getEffortBreakHeaders())),
				cmp.verticalGap(20),cmp.text("Changes by Java Package"),cmp.subreport(executionReport.createpackagereport(scanner.getListGridPackages(),scanner.getColumnpackageHeader())),
				cmp.verticalGap(20),cmp.text("Mandatory Changes").setStyle(titleStyle1),cmp.line(),
				cmp.verticalGap(20),cmp.text("Assisted Remediations"),cmp.subreport(executionReport.createReport11(scanner.getListGridAssistedMand(),scanner.getColumnHeadersAssisted())),
				cmp.verticalGap(20),cmp.text("Jars"),cmp.subreport(executionReport.createReport11(scanner.getListGidlibrary(),scanner.getColumnHeadersAssisted())),
				cmp.verticalGap(20),cmp.text("Optional Changes").setStyle(titleStyle1),cmp.line(),
				cmp.verticalGap(20),cmp.text("Automated Remediations"),cmp.subreport(executionReport.createReport10(scanner.getListGrid13(),scanner.getColumnHeaders6())),
				cmp.verticalGap(20),cmp.text("Assisted Remediation for Java code"),cmp.subreport(executionReport.createReport11(scanner.getListGridAssisted(),scanner.getColumnHeadersAssisted())));
				logger.info("Inside multi Report6");																			  
				logger.info("Assessment Summary");
				JFrame fReport = new JFrame("Detailed Assessment Report");
				fReport.setSize(1160, 680);
				fReport.setResizable(true);
				fReport.setLocationRelativeTo(null);
				JasperPrint jasperPrint;
				
				try {
					jasperPrint = jsReport[0].toJasperPrint();
					fReport.getContentPane().add(new JRViewer(jasperPrint));
				} 
				catch (DRException e3) {
					logger.error(e3.getMessage(),e3);
				}
				logger.info("Assessment Summary");					  
				strapplicationName = uploadedAppPath.substring(uploadedAppPath.lastIndexOf('\\')+1,uploadedAppPath.length());
				JFrame frameSummary = new JFrame("Assessment Summary");
				Container container = frameSummary.getContentPane();
				JLabel lblHeading = new JLabel();
				lblHeading.setBounds(0, 0, 1160, 60);
				lblHeading.setFont(new Font("Arial",Font.BOLD,24));
				lblHeading.setText(" "+strapplicationName);
				lblHeading.setBackground(new Color(134, 38, 195));
				lblHeading.setForeground(new Color(255,255,255));
				lblHeading.setOpaque(true);
				lblHeading.setHorizontalAlignment(SwingConstants.CENTER);
				lblHeading.setVerticalAlignment(SwingConstants.CENTER);
				JLabel lblVersions = new JLabel(" Current Version: "+fromVersion+"     Target Version: "+toVersion);
				lblVersions.setBounds(0, 60, 1160, 30);
				lblVersions.setFont(new Font("Arial",Font.TRUETYPE_FONT,20));
				lblVersions.setBackground(new Color(134, 38, 195));
				lblVersions.setForeground(new Color(255,255,255));
				lblVersions.setOpaque(true);
				lblVersions.setHorizontalAlignment(SwingConstants.CENTER);
				lblVersions.setVerticalAlignment(SwingConstants.CENTER);
						
				JLabel lblSummary = new JLabel("Assessment Summary");
				lblSummary.setBounds(10, 110, 1125, 30);
				lblSummary.setFont(new Font("Arial",Font.TRUETYPE_FONT,18));
				        
				List<String> headerList = projectAnalyze.getSummaryColumnHeaders();
						
				List<Map<String, String>> viewer2DataList = projectAnalyze.getListGrid19();
				String[] columns = {headerList.get(0),headerList.get(1), headerList.get(2), headerList.get(3), headerList.get(4),headerList.get(5),headerList.get(6)};
				Object[][] data = {};
				DefaultTableModel dtm = new DefaultTableModel(data, columns);
				for (int i = 0; i < viewer2DataList.size(); i++) {									
					dtm.addRow(new Object[] {
					viewer2DataList.get(i).get(headerList.get(0)),
					viewer2DataList.get(i).get(headerList.get(1)),
					viewer2DataList.get(i).get(headerList.get(2)),
					viewer2DataList.get(i).get(headerList.get(3)),
					viewer2DataList.get(i).get(headerList.get(4)),
					viewer2DataList.get(i).get(headerList.get(5)),
					viewer2DataList.get(i).get(headerList.get(6))});																
				}
				
				JTable tblSummary = new JTable(dtm);
				tblSummary.setEnabled(false);
				TableColumn tc = tblSummary.getColumnModel().getColumn(0);
				tc.setMaxWidth(100);
				JTableHeader tableHeader = tblSummary.getTableHeader();
				tableHeader.setDefaultRenderer(new HeaderRenderer(tblSummary));
				tableHeader.setBackground(new Color(134, 38, 195));
				tableHeader.setForeground(new Color(255,255,255));
				JScrollPane scrollPane = new JScrollPane(tblSummary);
				scrollPane.setBounds(10, 140, 1125, 39);
				//commented below code for PwC 
				JLabel lblEffortSummary = new JLabel("Estimated Assisted Remediation Effort (Person Hours)");
				lblEffortSummary.setFont(new Font("Arial",Font.TRUETYPE_FONT,18));
				lblEffortSummary.setBounds(10,210,600,30);
				headerList = projectAnalyze.getEffortHeaders();
				
				viewer2DataList = projectAnalyze.getListGrid15();
				String[] columnsEffort = {headerList.get(0),headerList.get(1), headerList.get(2)};
				Object[][] dataEffort = {};
				DefaultTableModel dtmEffort = new DefaultTableModel(dataEffort, columnsEffort);
				Float fTotalEffort = new Float(0);
				for (int i = 0; i < viewer2DataList.size(); i++) {								
					fTotalEffort = widGetViewer(headerList, viewer2DataList, dtmEffort, fTotalEffort, i);
				}
				dtmEffort.addRow(new Object[] {"Overall Effort","",fTotalEffort});
				JTable tblEffort = new JTable(dtmEffort);
				tblEffort.setEnabled(false);
				JTableHeader tableHeaderEffort = tblEffort.getTableHeader();
				tableHeaderEffort.setDefaultRenderer(new HeaderRenderer(tblEffort));
				tableHeaderEffort.setBackground(new Color(134, 38, 195));
				tableHeaderEffort.setForeground(new Color(255,255,255));
				JScrollPane scrollPaneEffort = new JScrollPane(tblEffort);
				scrollPaneEffort.setBounds(10, 240, 600, 71);
				JLabel lblEffortBreakDown = new JLabel("Effort Break Down");
				lblEffortBreakDown.setFont(new Font("Arial",Font.TRUETYPE_FONT,18));
				lblEffortBreakDown.setBounds(10,350,600,30);
				headerList = projectAnalyze.getEffortBreakHeaders();							
				viewer2DataList = projectAnalyze.getListGrid13();
				String[] columnsEffortBreak = {headerList.get(0),headerList.get(1), headerList.get(2),headerList.get(3),headerList.get(4)};
				Object[][] dataEffortBreak = {};
				DefaultTableModel dtmEffortBreak = new DefaultTableModel(dataEffortBreak, columnsEffortBreak);
				for (int i = 0; i < viewer2DataList.size(); i++) {									
					dtmEffortBreak.addRow(new Object[] {viewer2DataList.get(i).get(headerList.get(0)),
					viewer2DataList.get(i).get(headerList.get(1)),
					viewer2DataList.get(i).get(headerList.get(2)),
					viewer2DataList.get(i).get(headerList.get(3)),
					viewer2DataList.get(i).get(headerList.get(4))});																
				}

				JTable tblEffortBreak = new JTable(dtmEffortBreak);
				tblEffortBreak.setEnabled(false);
				JTableHeader tableHeaderEffortBreak = tblEffortBreak.getTableHeader();
				tableHeaderEffortBreak.setDefaultRenderer(new HeaderRenderer(tblEffortBreak));
				tableHeaderEffortBreak.setBackground(new Color(134, 38, 195));
				tableHeaderEffortBreak.setForeground(new Color(255,255,255));
				JScrollPane scrollPaneEffortBreak = new JScrollPane(tblEffortBreak);
				scrollPaneEffortBreak.setBounds(10, 380, 600, 183);
				JButton btnNext = new JButton("Next");
				btnNext.setBounds(1025,600,100,30);					        					        
				JLabel lblJavaPackage = new JLabel("Changes by Java Package");
				lblJavaPackage.setFont(new Font("Arial",Font.TRUETYPE_FONT,18));
				lblJavaPackage.setBounds(650,210,475,30);
						
				String[] columnsJavaPackage = {scanner.getColumnpackageHeader().get(0),scanner.getColumnpackageHeader().get(1)};
				Object[][] dataJavaPackage = {};
				DefaultTableModel dtmJavaPackage = new DefaultTableModel(dataJavaPackage, columnsJavaPackage);
				for (int i = 0; i < scanner.getListGridPackages().size(); i++) {									
					dtmJavaPackage.addRow(new Object[] {scanner.getListGridPackages().get(i).get(scanner.getColumnpackageHeader().get(0)),
					scanner.getListGridPackages().get(i).get(scanner.getColumnpackageHeader().get(1))});																
				}

				JTable tblJavaPackage = new JTable(dtmJavaPackage);
				tblJavaPackage.setEnabled(false);
				JTableHeader tableHeaderJavaPackage = tblJavaPackage.getTableHeader();
				tableHeaderJavaPackage.setDefaultRenderer(new HeaderRenderer(tblJavaPackage));
				tableHeaderJavaPackage.setBackground(new Color(134, 38, 195));
				tableHeaderJavaPackage.setForeground(new Color(255,255,255));
				JScrollPane scrollPaneJavaPackage = new JScrollPane(tblJavaPackage);
				scrollPaneJavaPackage.setBounds(650, 240, 475, 323);
				JLabel lblMandatoryMeaning = new JLabel("*Mandatory changes are must to do changes to proceed ahead with migration.");
				lblMandatoryMeaning.setFont(new Font("Arial",Font.TRUETYPE_FONT,12));
				lblMandatoryMeaning.setBounds(10,570,1125,24);
				JLabel lblOptionalMeaning = new JLabel("**Optional changes are good to have changes. These are deprecated java API/funtions which might be removed in future java releases.");
				lblOptionalMeaning.setFont(new Font("Arial",Font.TRUETYPE_FONT,12));
				lblOptionalMeaning.setBounds(10,595,1125,24);
				JButton reportSummary = new JButton("Assessment Report");
				JButton reportSummary1 = new JButton("Hover over this button");
				reportSummary.setOpaque(true);		
				reportSummary.addMouseListener(new MouseAdapter()  {
					public void mouseEntered(MouseEvent evt) {
						reportSummary.setBackground(Color.BLACK);
					}
					
					public void mouseExited(MouseEvent evt) {
					    reportSummary.setBackground(new Color(134, 38, 195));
					}
				});

				reportSummary.setForeground(new Color(255, 255, 255));
				reportSummary.setBackground(new Color(134, 38, 195));
				reportSummary.setBounds(960,570,175,30);
				reportSummary.setPreferredSize(new Dimension(20, 40));
				JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
				jTabbedPane.setBackground(new Color(255,255,255));					        
				JPanel jpSummary = new JPanel();
				jpSummary.setBackground(new Color(255,255,255));
				jpSummary.setLayout(null);
				jpSummary.setVisible(true);
				jpSummary.add(lblHeading);
				jpSummary.add(lblVersions);
				jpSummary.add(lblSummary);
				jpSummary.add(scrollPane);
				jpSummary.add(lblEffortSummary);
				jpSummary.add(scrollPaneEffort);
				jpSummary.add(lblEffortBreakDown);
				jpSummary.add(scrollPaneEffortBreak);
				jpSummary.add(scrollPaneJavaPackage);
				jpSummary.add(lblJavaPackage);
				jpSummary.add(lblMandatoryMeaning);
				jpSummary.add(lblOptionalMeaning);
				jpSummary.add(reportSummary);
				jTabbedPane.addTab("Assessment Summary", jpSummary);
				jTabbedPane.setVisible(true);
				container.add(jTabbedPane);
				frameSummary.pack();
				frameSummary.setSize(1160, 680);
				frameSummary.setResizable(false);
				progress.getF().dispose();
				btnAnalyze.setVisible(true);
				frameSummary.setVisible(true);
				frameSummary.setLocationRelativeTo(null);					    
				
				btnNext.addActionListener(new ActionListener() {								
					@Override
					public void actionPerformed(ActionEvent arg0) {
						//to be implemented
					}
				});					        							
				//Summary end
						
				//Analysis Report Start							
				JTableHeaderCheckBox jtable = new JTableHeaderCheckBox();
				JPanel visiblePanel = new JPanel(new FlowLayout());
				visiblePanel.setBorder(BorderFactory.createTitledBorder("Visible Panel"));							
				JLabel lblAutoRem = new JLabel("AUTOMATED REMEDIATION");							
				lblAutoRem.setFont(new Font("Arial",Font.TRUETYPE_FONT,20));
				lblAutoRem.setBounds(0, 0, 1160, 50);
				lblAutoRem.setOpaque(true);
				lblAutoRem.setBackground(new Color(134, 38, 195));
				lblAutoRem.setForeground(new Color(255,255,255));
				lblAutoRem.setHorizontalAlignment(SwingConstants.CENTER);
				lblAutoRem.setVerticalAlignment(SwingConstants.CENTER);
				JLabel lblAssistRem = new JLabel("ASSISTED REMEDIATION");
				lblAssistRem.setFont(new Font("Arial",Font.TRUETYPE_FONT,20));
				lblAssistRem.setBounds(0, 0, 1160, 50);
				lblAssistRem.setOpaque(true);
				lblAssistRem.setBackground(new Color(134, 38, 195));
				lblAssistRem.setForeground(new Color(255,255,255));
				lblAssistRem.setHorizontalAlignment(SwingConstants.CENTER);
				lblAssistRem.setVerticalAlignment(SwingConstants.CENTER);
				JButton btnMigrate = new JButton("Migrate");														
				
				btnMigrate.addMouseListener(new MouseAdapter()  {
					public void mouseEntered(MouseEvent evt) {
						btnMigrate.setBackground(Color.BLACK);
					}
					public void mouseExited(MouseEvent evt) {
					    btnMigrate.setBackground(new Color(134, 38, 195));
					}
				});
						
				btnMigrate.setForeground(new Color(255, 255, 255));
				btnMigrate.setBounds(770,570,175,30);
				btnMigrate.setPreferredSize(new Dimension(20, 40));
				btnMigrate.setForeground(new Color(255, 255, 255));
				btnMigrate.setBackground(new Color(134, 38, 195));														
				JButton postReport = new JButton("Migration Report");
				postReport.setBackground(new Color(134, 38, 195));
				postReport.setForeground(new Color(255, 255, 255));
				postReport.setBounds(960,570,175,30);
				postReport.setPreferredSize(new Dimension(20, 40));					
				postReport.addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent evt) {
					    postReport.setBackground(Color.BLACK);
					}
					public void mouseExited(MouseEvent evt) {
					    postReport.setBackground(new Color(134, 38, 195));
					}
				});
						
				JButton build = new JButton("BUILD");
				build.setPreferredSize(new Dimension(20, 40));
				build.setEnabled(false);
				build.setIcon(new ImageIcon(this.getClass().getResource("build.png")));
				build.setBounds(935,570,200,50);
						
				JPanel jPAutomated = new JPanel();
				jPAutomated.setBackground(new Color(255,255,255));
				jPAutomated.setLayout(null);							
				btnMigrate.setEnabled(true);
				JTable tblFiles;
				if(scanner.getListGrid13() != null && !scanner.getListGrid13().isEmpty()) {
					tblFiles = jtable.buildGUI(scanner.getListGrid13(),scanner.getColumnHeaders6());
				}else
				{
					tblFiles = new JTable();
					System.out.println("file"+tblFiles);
				}
				JScrollPane sp = new JScrollPane(tblFiles);
				sp.setBackground(new Color(134, 38, 195));
				sp.setBounds(10, 50, 1125, 515);
				jPAutomated.add(sp);
				jPAutomated.add(lblAutoRem);
				jPAutomated.add(btnMigrate);
				jPAutomated.add(postReport);
				jTabbedPane.addTab("Automated Remediation", jPAutomated);					        
				JPanel jPAssisted = new JPanel();
				jPAssisted.setBackground(new Color(255,255,255));
				jPAssisted.setLayout(null);
				JTable tblFilesAssisted;
				if(!scanner.getListassistedAll().isEmpty()) {
					tblFilesAssisted = jtable.assistedTable(scanner.getListassistedAll(),scanner.getColumnHeadersAssisted());
				}
				else {
					tblFilesAssisted = new JTable();
				}
				JScrollPane spAssisted = new JScrollPane(tblFilesAssisted);							
				JButton btnAssMigrate = new JButton("MIGRATE");
				btnAssMigrate.setBackground(new Color(20, 132, 219));
				btnAssMigrate.setForeground(new Color(255, 255, 255));
				btnAssMigrate.setBounds(770,570,175,30);
				btnAssMigrate.setPreferredSize(new Dimension(20, 40));
				btnAssMigrate.setEnabled(false);
				btnAssMigrate.setVisible(false);
				JButton btnreport = new JButton("ASSESSMENT REPORT");
				btnreport.setForeground(new Color(255, 255, 255));
				btnreport.setBackground(new Color(20, 132, 219));
				btnreport.setBounds(960,570,175,30);
				btnreport.setPreferredSize(new Dimension(20, 40));							
				spAssisted.setBounds(10, 50, 1125, 515);
				spAssisted.setBackground(new Color(255,255,255));
				jPAssisted.add(spAssisted);
				jPAssisted.add(btnAssMigrate);
				jPAssisted.add(lblAssistRem);
				jTabbedPane.addTab("Assisted Remediation", jPAssisted);														
				JPanel jPBuild = new JPanel();
				jPBuild.setBackground(new Color(255,255,255));
				jPBuild.setLayout(null);
				JLabel lblBuild = new JLabel("BUILD");
				lblBuild.setFont(new Font("Arial",Font.TRUETYPE_FONT,20));
				lblBuild.setBounds(0, 0, 1160, 50);
				lblBuild.setOpaque(true);
				lblBuild.setBackground(new Color(134, 38, 195));
				lblBuild.setForeground(new Color(255,255,255));
				lblBuild.setHorizontalAlignment(SwingConstants.CENTER);
				lblBuild.setVerticalAlignment(SwingConstants.CENTER);							
				JLabel lblBuildText = new JLabel("You can test maven build and check the integrity of your migrated source files here.");
				lblBuildText.setFont(new Font("Arial",Font.TRUETYPE_FONT,15));
				lblBuildText.setBounds(0, 50, 1160, 50);
				lblBuildText.setOpaque(true);
				lblBuildText.setBackground(new Color(134, 38, 195));
				lblBuildText.setForeground(new Color(255,255,255));
				lblBuildText.setHorizontalAlignment(SwingConstants.CENTER);
				lblBuildText.setVerticalAlignment(SwingConstants.CENTER);							
				JLabel label = new JLabel();		
				label.setText("Select Source Code:");
				label.setBounds(100, 200, 230, 25);							
				JTextField textfield= new JTextField();
				textfield.setBounds(100, 250, 300, 25);														
				JButton btnBrowse=new JButton();    
				btnBrowse.setBounds(420, 250, 120, 25);
				btnBrowse.setText("Browse");
				btnBrowse.setVisible(true);
				btnBrowse.setBackground(new Color(134, 38, 195));
				btnBrowse.setForeground(new Color(255, 255, 255));
				btnBrowse.setToolTipText("click to select the code");
				btnBrowse.addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent evt) {
					    btnBrowse.setBackground(Color.BLACK);
					}
					public void mouseExited(MouseEvent evt) {
					    btnBrowse.setBackground(new Color(134, 38, 195));
					}
				});							
				JLabel label1 = new JLabel();
				label1.setBounds(100, 300, 600, 50);							
				JButton btnBuild=new JButton();
				btnBuild.setBounds(100,400,130, 30);
				btnBuild.setText("Builds");
				btnBuild.setVisible(true);
				btnBuild.setForeground(new Color(255, 255, 255));
				btnBuild.setBackground(new Color(134, 38, 195));
				btnBuild.setToolTipText("click to build the code");
				btnBuild.addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent evt) {
					    btnBuild.setBackground(Color.BLACK);
					}
					public void mouseExited(MouseEvent evt) {
					    btnBuild.setBackground(new Color(134, 38, 195));
					}
				});							
				//enter name label
				//empty label which will show event after button clicked
				//textfield to enter name
				//add to frame
				jPBuild.add(label1);
				jPBuild.add(textfield);
				jPBuild.add(label);
				jPBuild.add(btnBrowse); 
				jPBuild.add(btnBuild);
				jPBuild.add(lblBuild);
				jPBuild.add(lblBuildText);
						   
				//action listener
				btnBrowse.addActionListener(new ActionListener() {						        
					@Override
					public void actionPerformed(ActionEvent arg0) {
						Main.callStatus("Wait...");
						JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
						jfc.setDialogTitle("Choose a directory: ");
						jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int returnValue = jfc.showSaveDialog(null);
						if (returnValue == JFileChooser.APPROVE_OPTION && jfc.getSelectedFile().isDirectory()) {
							logger.info("You selected the directory: " + jfc.getSelectedFile());
							getBuildPath= jfc.getSelectedFile().getAbsolutePath();
						}
						label1.setText(getBuildPath);	
						textfield.setText(getBuildPath);
						btnBuild.setEnabled(true);
					}          
				});
												
				btnBuild.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						BuildCode build = new BuildCode();
						try {
							Main.callStatus("Wait...");
							build.buildMavenProject(getBuildPath);
							label1.setText("Build Success: Please check the Logs");	
						} 
						catch (IOException e) {
							logger.error(e.getMessage(),e);
						}
					}
				});															
				
				jTabbedPane.addTab("Build", jPBuild);
				//btnBuildCode
				build.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Main.callStatus("Wait...");																									
					}
				});														
					
				btnMigrate.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {								
						// ...									
						String reportPath = null;
						// ...
						logger.info("Migrate clicked");
						clonedir = new CloneDir();
						if(!scanner.getListGrid13().isEmpty()) {
							File srcDir = new File(uploadedAppPath);
						    File destDir=null;
						    reportPath = uploadedAppPath.substring(0,uploadedAppPath.lastIndexOf('\\'));
						    logger.info("Path selected for migration--"+reportPath);							        
						    if(reportPath!=null && !reportPath.equals("")) {
						        try {
						        	if(reportPath.contains(uploadedAppPath)) {
						        		logger.info("The migration target path should not be inside the source path.");
						        		return;
						        	}
						        	Main.callStatus("Wait...");
						        	String strapplicationPath = uploadedAppPath.substring(uploadedAppPath.lastIndexOf('\\')+1,uploadedAppPath.length());
						        	destDir = new File(reportPath+File.separator+strapplicationPath + "_Original");							        								        
									clonedir.deleteFolder(destDir);									
									logger.info("cloning the source directory");
									clonedir.copy(srcDir, destDir);
								}
								catch (IndexOutOfBoundsException|IOException|NullPointerException e2) {
									logger.info("Error occured while cloning the source directory");
									logger.error(e2.getMessage(),e2);
								}

								MergeChanges mergerChanges = new MergeChanges();
								mergerChanges.displayDestDirContents(srcDir, scanner.getListGrid13(),scanner.getColumnHeaders6(),jtable);
								JFrame migrationSucess = new JFrame();
								JOptionPane.showMessageDialog(migrationSucess, "Migration done successfully. "+mergerChanges.getIntNoOfFilesMerged()+" files migrated.");
								postReport.setEnabled(true);
								build.setEnabled(true);
						    }
						}
					}	
				});								
					
				btnreport.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {								
						genAssessmentReport();								
					}	
				});
				reportSummary.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {								
						genAssessmentReport();								
					}	
				});							
					
				//Post-Report							
				postReport.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						logger.info("inside Post-Report");									
						Main.callStatus("Wait...");
						projectAnalyze=new ProjectAnalyze();
						int totalAffectedFilesMultiUpload =scanner.getAutomatedFiles().size()+scanner.getAssistedFiles().size();
						projectAnalyze.getMigrationSummary(scanner.getTotalFilesScanned(),totalAffectedFilesMultiUpload,scanner.getAutomatedFiles().size(),scanner.getAssistedFiles().size(),"post",scanner.getNoOfAutomatedLines(),scanner.getNoOfAssistedLines());
						String strapplicationName = uploadedAppPath.substring(uploadedAppPath.lastIndexOf('\\')+1,uploadedAppPath.length());
						JasperMigrationReport executionReport = new JasperMigrationReport();
						StyleBuilder titleStyle = stl.style(stl.style().bold().setForegroundColor(new Color(153, 0, 0)))
						.setFontName(Font.SANS_SERIF).setFontSize(20);
						StyleBuilder titleStyle1 = stl.style(stl.style().bold())
						.setFontName(Font.SANS_SERIF).setFontSize(10);
						JasperReportBuilder[] jsReport = new JasperReportBuilder[2];
						jsReport[0] = report().setTemplate(Templates.reportTemplate).setPageFormat(PageType.B4)
						.title(cmp.text("Migration Report - "+strapplicationName).setStyle(titleStyle).setHorizontalTextAlignment(
						HorizontalTextAlignment.CENTER), cmp.verticalGap(10), cmp.line(), cmp.verticalGap(5));
						//commented below code for PwC
						jsReport[0].summary(cmp.text("Current JDK Version:"+fromVersion+"     Target JDK Version:"+toVersion+"\n").setStyle(titleStyle1),cmp.verticalGap(10),
						//jsReport[0].summary(cmp.text("Current JDK Version:"+fromVersion+"     Azul JDK Version:"+toVersion+"\n").setStyle(titleStyle1),cmp.verticalGap(10),
						cmp.text("Summary"),cmp.subreport(executionReport.createReport12(projectAnalyze.getListGrid19(),projectAnalyze.getSummaryColumnHeaders())),cmp.verticalGap(20),
						cmp.subreport(executionReport.createReport20(projectAnalyze.getListGrid20(),projectAnalyze.getSummaryColumnHeaders1())),cmp.verticalGap(20),
						cmp.text("Automated Migration"),cmp.subreport(executionReport.createReport13(scanner.getListGrid13(),scanner.getColumnHeaders6())));									
						try {
							JasperPrint jasperPrint=jsReport[0].ignorePagination().toJasperPrint();
							JFrame f = new JFrame("Migration Report");
							f.setSize(1160, 680);
						  	f.setResizable(true);
						  	f.setLocationRelativeTo(null);
						  	f.getContentPane().add(new JRViewer(jasperPrint));
						  	f.setVisible(true);
						} 
						catch (DRException |NullPointerException e1) {
							logger.info("Error occured while Scanning the files");
							logger.error(e1.getMessage(),e1);
						}
					}
				});
				//Analysis Report End
			}
		}

		private Float widGetViewer(List<String> headerList, List<Map<String, String>> viewer2DataList,
				DefaultTableModel dtmEffort, Float fTotalEffort, int i) {
			dtmEffort.addRow(new Object[] {viewer2DataList.get(i).get(headerList.get(0)),
			viewer2DataList.get(i).get(headerList.get(1)),
			Float.valueOf(viewer2DataList.get(i).get(headerList.get(2)))});
			fTotalEffort = fTotalEffort+Float.valueOf(viewer2DataList.get(i).get(headerList.get(2)));
			return fTotalEffort;
		}
});	
																									
//MultiBtn Analyze
btnMultiAnalyze.addSelectionListener(new SelectionAdapter() {																	
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			//Multibrowse Upload
			batchFlag=false;
			if(!multiApplication.isEmpty()) {
				JTableHeaderCheckBox jtable = new JTableHeaderCheckBox();
				scanner = new AppScannerJson();
				scanner.multiBrowsePaths(multiApplication);
				for(int i=0;i<multiApplication.size();i++) {
					uploadedAppPath="";
					uploadedDestAppPath="";
					toVersion="";
					fromVersion="";
					uploadedAppPath=multiApplication.get(i)[0];
					fromVersion=multiApplication.get(i)[1];
					toVersion=multiApplication.get(i)[2];
					projectAnalyze.displayDirectoryContents(new File(uploadedAppPath));
					progress.progressbar(projectAnalyze.getTotalCount()+1);
					progress.getF().setTitle("Analysis in progress..");
					AppScannerJson scanner1 = new AppScannerJson();
					try {
						scanner1.getAllFiles(uploadedAppPath,fromVersion,toVersion, progress);
						scanner1.parseFiles();
					} 
					catch (IOException |NullPointerException | XmlPullParserException e1) {
						logger.info("Error occured while Scanning the files");
						logger.error(e1.getMessage(),e1);
					}
					progress.getF().dispose();
					linkListAppScanner.add(scanner1);
				}																
							
			JFrame success = new JFrame();
			JOptionPane.showMessageDialog(success, "Analysis complete. "+multiApplication.size()+ " applications analyzed.");
			JFrame frameBatchSummary = new JFrame("BATCH ANALYSIS REPORT");
			frameBatchSummary.setSize(1160, 680);
			frameBatchSummary.setResizable(true);
			frameBatchSummary.setLocationRelativeTo(null);
			frameBatchSummary.setVisible(true);
			JScrollPane sp ;
			sp = new JScrollPane(jtable.buildMultiBrowseUpload(scanner.getListGridMulti(),scanner.getMultiBrowseColHeader()));
			JButton btnMultiMigrate = new JButton("MIGRATE");
			btnMultiMigrate.setPreferredSize(new Dimension(20, 40));
			btnMultiMigrate.setBackground(new Color(20, 132, 219));
			btnMultiMigrate.setForeground(new Color(255, 255, 255));
			JButton btnPreReport = new JButton("ASSESSMENT REPORT");
			btnPreReport.setPreferredSize(new Dimension(20, 40));
			btnPreReport.setBackground(new Color(20, 132, 219));
			btnPreReport.setForeground(new Color(255, 255, 255));
			JButton btnpostReport = new JButton("MIGRATION REPORT");
			btnpostReport.setPreferredSize(new Dimension(20, 40));
			btnpostReport.setBackground(new Color(20, 132, 219));
			btnpostReport.setForeground(new Color(255, 255, 255));
			btnpostReport.setEnabled(false);								
			JPanel m = new JPanel(new GridLayout(1, 2,150,0));
			m.setBackground(new Color(255, 255, 255));
			m.add(btnpostReport);
			m.add(btnPreReport);
			m.add(btnMultiMigrate);
			frameBatchSummary.getContentPane().add(m, BorderLayout.SOUTH);								 
			frameBatchSummary.getContentPane().add(sp);
			frameBatchSummary.setVisible(true);								
			
			btnPreReport.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {										
					//Code to select the path of report to be saved
					String reportPath = openDialogDirectory("","Choose location to save assessment report");
					if(reportPath!= null && !reportPath.equals("")) {
						projectAnalyze=new ProjectAnalyze();				
						Main.callStatus("Wait...");
						JasperMigrationReport executionReport = new JasperMigrationReport();
						JasperReportBuilder[] jsReport = new JasperReportBuilder[2];
						List<JasperReportBuilder[]> jsReports = new LinkedList<>();
						List<String[]> jdkVersions = new LinkedList<>();
						int totalAutoMatedMulti = 0;
						int totalFilesScannedMulti = 0;											
						int totalAssistedMulti = 0;
						StyleBuilder titleStyle1 = stl.style(stl.style().bold())
						.setFontName(Font.SANS_SERIF).setFontSize(10);
						StyleBuilder titleStyle = stl.style(stl.style().bold().setForegroundColor(new Color(153, 0, 0)))
						.setFontName(Font.SANS_SERIF).setFontSize(20);											
						try { 
							for(int i=0;i<multiApplication.size();i++) {
								scanner = linkListAppScanner.get(i);
								logger.info("Inside Report");
								uploadedAppPath = multiApplication.get(i)[0];
								String strapplicationPathMulti = uploadedAppPath.substring(uploadedAppPath.lastIndexOf('\\')+1,uploadedAppPath.length());
								String[] jdks = {multiApplication.get(i)[1],multiApplication.get(i)[2],strapplicationPathMulti};
								jdkVersions.add(jdks);
								int totalAffectedFiles =scanner.getAutomatedFiles().size()+scanner.getAssistedFiles().size();
								totalFilesScannedMulti = totalFilesScannedMulti+scanner.getTotalFilesScanned();
								totalAutoMatedMulti = totalAutoMatedMulti + scanner.getAutomatedFiles().size();
								totalAssistedMulti = totalAssistedMulti + scanner.getAssistedFiles().size();
								projectAnalyze.getMigrationSummaryBatch(scanner.getTotalFilesScanned(),totalAffectedFiles,scanner.getAutomatedFiles().size(),scanner.getAssistedFiles().size(),"pre",scanner.getNoOfAutomatedLines(),scanner.getNoOfAssistedLines(),strapplicationPathMulti);
								projectAnalyze.getEffortReportBatch( scanner.getEffortRequired(),strapplicationPathMulti,scanner.getNoOfSimple(),scanner.getNoOfMedium(),scanner.getNoOfComplex());										
								JasperReportBuilder[] jsReportperApplicationMulti = new JasperReportBuilder[2];
								jsReportperApplicationMulti[0] = executionReport.createReport10(scanner.getListGrid13(),scanner.getColumnHeaders6());
								jsReportperApplicationMulti[1] = executionReport.createReport11(scanner.getListGridAssisted(),scanner.getColumnHeadersAssisted());
								jsReports.add(jsReportperApplicationMulti);
							}																																										
							jsReport[0] = report().setTemplate(Templates.reportTemplate).ignorePagination().setPageFormat(PageType.B4).title(cmp.text("Assessment Report").setStyle(titleStyle).setHorizontalTextAlignment(
										HorizontalTextAlignment.CENTER), cmp.verticalGap(10), cmp.line(), cmp.verticalGap(20),
										cmp.text("Summary"),cmp.subreport(executionReport.createReportSummaryBatch(multiApplication.size(),totalFilesScannedMulti,totalAutoMatedMulti,totalAssistedMulti,projectAnalyze.getSummaryHeaderBatch())),cmp.verticalGap(10),
										cmp.text("Summary Break Down"),cmp.subreport(executionReport.createReportSummaryBreakDownBatch(projectAnalyze.getList6(),projectAnalyze.getSummaryColumnHeadersbatch())),cmp.verticalGap(10),
										cmp.text("Effort (Man-hour)"),cmp.subreport(executionReport.createReportEffortBatch(projectAnalyze.getList5(),projectAnalyze.getEffortHeadersBatch())));
							for(int i=0;i<jsReports.size();i++) {
								jsReport[0].summary(cmp.verticalGap(20),cmp.text("Application - "+jdkVersions.get(i)[2]).setStyle(titleStyle1),cmp.verticalGap(10),cmp.text("Current JDK Version:"+jdkVersions.get(i)[0]+"     Target JDK Version:"+jdkVersions.get(i)[1]).setStyle(titleStyle1),
								cmp.text("Automated Remediation"),cmp.verticalGap(10),cmp.subreport(jsReports.get(i)[0]),
								cmp.text("Assisted Remediation"),cmp.verticalGap(10),cmp.subreport(jsReports.get(i)[1]));
							}
							JasperPrint jasperPrint=jsReport[0].toJasperPrint();								 
							JRXlsxExporter xlsxexporter = new JRXlsxExporter();
							xlsxexporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
							xlsxexporter.setParameter(JRExporterParameter.OUTPUT_FILE,new File(reportPath+"//AssessmentReport.xlsX"));
							xlsxexporter.exportReport();								
							JFrame f = new JFrame();
							f.setSize(1160, 660);
							f.setResizable(true);
							f.setLocationRelativeTo(null);
						} 
						catch (IndexOutOfBoundsException | NullPointerException |ClassCastException |UnsupportedOperationException| 
										IllegalArgumentException | JRException |DRException e1) {
							logger.info("Error occured while Ssanning the files");
							logger.error(e1.getMessage(),e1);
						}

						JFrame success = new JFrame();
						JOptionPane.showMessageDialog(success, "Report generated successfully.");
						}
					}
				});	

				btnMultiMigrate.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {										
						String reportPath = openDialogDirectory("","Choose location to save migrated project");
						if(reportPath!= null && !reportPath.equals("")) {
							Main.callStatus("Wait...");
							//Multithreading
							for(int i=0;i<multiApplication.size();i++) {
								batchFlag=true;	
								// ...
								scanner = linkListAppScanner.get(i);
								logger.info("Inside btnMigrate");
								clonedir = new CloneDir();
								if(!scanner.getListGrid13().isEmpty()) {
							    	File srcDir = new File(uploadedAppPath);
							        File destDir=null;
							        if(!reportPath.equals("")) {
							        	uploadedAppPath = multiApplication.get(i)[0];
							        	String strapplicationPath = uploadedAppPath.substring(uploadedAppPath.lastIndexOf('\\')+1,uploadedAppPath.length());
							        	destDir = new File(reportPath+File.separator+strapplicationPath + "_updated");
							        }
							        else {
							        	destDir = new File(uploadedAppPath + "_updated");
							        }
									clonedir.deleteFolder(destDir);
									try {
										clonedir.copy(srcDir, destDir);
									} 
									catch (IOException e2) {
										logger.info("Error occured while Ssanning the files");
										logger.error(e2.getMessage(),e2);
									}
									MergeChanges mergerChanges = new MergeChanges();
									mergerChanges.displayDestDirContents(destDir, scanner.getListGrid13(),scanner.getColumnHeaders6(),jtable);
								}									
							}
							JFrame success = new JFrame();
							JOptionPane.showMessageDialog(success, "Migration done successfully.");
							btnpostReport.setEnabled(true);
						}
					}
				});

				btnpostReport.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {																				
						String reportPath = openDialogDirectory("","Choose location to save migration report");
						if(reportPath!= null && !reportPath.equals("")) {
							Main.callStatus("Wait...");
							projectAnalyze=new ProjectAnalyze();
							JasperMigrationReport executionReport = new JasperMigrationReport();
							StyleBuilder titleStyle = stl.style(stl.style().bold().setForegroundColor(new Color(153, 0, 0)))
								.setFontName(Font.SANS_SERIF).setFontSize(20);
							StyleBuilder titleStyle1 = stl.style(stl.style().bold())
								.setFontName(Font.SANS_SERIF).setFontSize(10);
							JasperReportBuilder[] jsReport = new JasperReportBuilder[2];
							List<JasperReportBuilder> jsReports = new LinkedList<>();
							List<String[]> jdkVersions = new LinkedList<>();
							for(int i=0;i<multiApplication.size();i++) {
								logger.info("inside Post-Report");
								uploadedAppPath = multiApplication.get(i)[0];
								String strapplicationPath = uploadedAppPath.substring(uploadedAppPath.lastIndexOf('\\')+1,uploadedAppPath.length());
								String[] jdks = {multiApplication.get(i)[1],multiApplication.get(i)[2],strapplicationPath};
								jdkVersions.add(jdks);
								scanner = linkListAppScanner.get(i);
								int totalAffectedFiles =scanner.getAutomatedFiles().size()+scanner.getAssistedFiles().size();
								projectAnalyze.getMigrationSummaryBatch(scanner.getTotalFilesScanned(),totalAffectedFiles,scanner.getAutomatedFiles().size(),scanner.getAssistedFiles().size(),"post",scanner.getNoOfAutomatedLines(),scanner.getNoOfAssistedLines(),strapplicationPath);
								JasperReportBuilder jsReportperApplication = executionReport.createReport13(scanner.getListGrid13(),scanner.getColumnHeaders6());
								jsReports.add(jsReportperApplication);										
							}
							try {
								jsReport[0] = report().setTemplate(Templates.reportTemplate).setPageFormat(PageType.B4)
								.title(cmp.text("Migration Report").setStyle(titleStyle).setHorizontalTextAlignment(
								HorizontalTextAlignment.CENTER), cmp.verticalGap(10), cmp.line(), cmp.verticalGap(5),
								cmp.verticalGap(10),cmp.text("Summary"),cmp.subreport(executionReport.createReportMigrationSummaryBatch(projectAnalyze.getList6(),projectAnalyze.getSummaryColumnHeadersMigrationbatch())));
								for(int i=0;i<jsReports.size();i++) {
								jsReport[0].summary(
									cmp.verticalGap(20),cmp.text("Application - "+jdkVersions.get(i)[2]).setStyle(titleStyle1),cmp.verticalGap(10),cmp.text("Current JDK Version:"+jdkVersions.get(i)[0]+"     Target JDK Version:"+jdkVersions.get(i)[1]).setStyle(titleStyle1),
									cmp.text("Automated Remediation"),cmp.verticalGap(10),cmp.subreport(jsReports.get(i))
									);
								}

								JasperPrint jasperPrint=jsReport[0].ignorePagination().toJasperPrint();								 
								JRXlsxExporter xlsxexporter = new JRXlsxExporter();
								xlsxexporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
								xlsxexporter.setParameter(JRExporterParameter.OUTPUT_FILE,new File(reportPath+"//MigrationReport.xlsX"));
								xlsxexporter.exportReport();																  
							  	JFrame f = new JFrame("Migration Report");
							  	f.setSize(1160, 680);
							  	f.setResizable(true);
							  	f.setLocationRelativeTo(null);
							}
							catch (JRException | DRException | NullPointerException e1) {
								logger.info("Error occured while Ssanning the files");
								logger.error(e1.getMessage(),e1);
							}										
							
							JFrame success = new JFrame();
							JOptionPane.showMessageDialog(success, "Report generated successfully.");
						}
					}
				});																																
			}							
		}												
});
//end

btnMultiAnalyzeMigrate.addSelectionListener(new SelectionAdapter() {						
		@Override
		public void widgetSelected(SelectionEvent arg0) {							
			JTableHeaderCheckBox jtable = new JTableHeaderCheckBox();
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.horizontalSpan = 4;
			String destinationDirectory = null;
			txtUploadedPath.setLayoutData(data);
			// Clicking the button will allow the user
			// to select a directory
			DirectoryDialog dlg = new DirectoryDialog(shell);
			// Set the initial filter path according
			// to anything they've selected or typed in
			dlg.setFilterPath("");
			// Change the title bar text
			dlg.setText("Select destination directory");
			// Customizable message displayed in the dialog
			dlg.setMessage("Select a directory");
			String dir = dlg.open();
			if (dir != null) {
				// Set the text box to the new selection
				destinationDirectory = dir;
			}														
			if(!multiApplication.isEmpty() && destinationDirectory != null) {																							
				Main.callStatus("Wait...");
				projectAnalyze=new ProjectAnalyze();
				JasperMigrationReport executionReport = new JasperMigrationReport();
				scanner = new AppScannerJson();
				scanner.multiBrowsePaths(multiApplication);
				//default landing to automated page
				for(int i=0;i<multiApplication.size();i++) {
					uploadedDestAppPath="";
					uploadedAppPath="";
					fromVersion="";
					toVersion="";
					uploadedAppPath=multiApplication.get(i)[0];
					fromVersion=multiApplication.get(i)[1];
					toVersion=multiApplication.get(i)[2];
					AppScannerJson scanner1 = new AppScannerJson();
					try {
						scanner1.getAllFiles(uploadedAppPath,fromVersion,toVersion, progress);
						scanner1.parseFiles();
						} 
					catch (Exception e1) {
						logger.info("Error occured while Ssanning the files");
						logger.error(e1.getMessage(),e1);
					}
					linkListAppScanner.add(scanner1);
				}														
				//Creation of AssessmentReport start							
				JasperReportBuilder[] jsReport = new JasperReportBuilder[2];
				List<JasperReportBuilder[]> jsReports = new LinkedList<>();
				List<String[]> jdkVersions = new LinkedList<>();
				int totalFilesScanned = 0;
				int totalAutoMated = 0;
				int totalAssisted = 0;
				StyleBuilder titleStyle = stl.style(stl.style().bold().setForegroundColor(new Color(153, 0, 0)))
					.setFontName(Font.SANS_SERIF).setFontSize(20);
				StyleBuilder titleStyle1 = stl.style(stl.style().bold())
					.setFontName(Font.SANS_SERIF).setFontSize(10);
				try {
					destinationDirectory(destinationDirectory, executionReport, jsReport, jsReports, jdkVersions,
							totalFilesScanned, totalAutoMated, totalAssisted, titleStyle, titleStyle1);							
				} 
				catch (Exception e1) {
					logger.error(e1.getMessage(),e1);
				}
				//Creation of assessment report end														
				//Migration code starts							
				for(int i=0;i<multiApplication.size();i++) {
					batchFlag=true;															
					// ...
					scanner = linkListAppScanner.get(i);
					logger.info("Inside btnMigrate");
					clonedir = new CloneDir();
					if(!scanner.getListGrid13().isEmpty()) {
				    	File srcDir = new File(uploadedAppPath);
				    	File destDir=null;
				    	if(!destinationDirectory.equals("") && destinationDirectory!=null) {
					        uploadedAppPath = multiApplication.get(i)[0];
					        String strapplicationPath = uploadedAppPath.substring(uploadedAppPath.lastIndexOf('\\')+1,uploadedAppPath.length());
					        destDir = new File(destinationDirectory+File.separator+strapplicationPath + "_updated");
					    }
					    else {
				        	destDir = new File(uploadedAppPath + "_updated");
				    	}
						clonedir.deleteFolder(destDir);
						try {
							clonedir.copy(srcDir, destDir);
						} 
						catch (IOException e2) {
							logger.error(e2.getMessage(),e2);
						}
						MergeChanges mergerChanges = new MergeChanges();
						mergerChanges.displayDestDirContents(destDir, scanner.getListGrid13(),scanner.getColumnHeaders6(),jtable);
					}						
				}														
				//Migration code ends						
				//Migration report starts														
				if(destinationDirectory!= null && !destinationDirectory.equals("")) {
					projectAnalyze=new ProjectAnalyze();
					JasperReportBuilder[] jsReportMigrate = new JasperReportBuilder[2];
					List<JasperReportBuilder> jsReportsMigrate = new LinkedList<>();
					List<String[]> jdkVersionsMigrate = new LinkedList<>();
					for(int i=0;i<multiApplication.size();i++) {
						jasperMigration(executionReport, jsReportsMigrate, jdkVersionsMigrate, i);							
					}	
					try {
						jsReportMigrate[0] = report().setTemplate(Templates.reportTemplate).setPageFormat(PageType.B4)
							.title(cmp.text("Migration Report").setStyle(titleStyle).setHorizontalTextAlignment(
						HorizontalTextAlignment.CENTER), cmp.verticalGap(10), cmp.line(), cmp.verticalGap(5),
							cmp.verticalGap(10),cmp.text("Summary"),cmp.subreport(executionReport.createReportMigrationSummaryBatch(projectAnalyze.getList6(),projectAnalyze.getSummaryColumnHeadersMigrationbatch()))
						);
						for(int i=0;i<jsReports.size();i++) {
							jsReportMigrate[0].summary(
							cmp.verticalGap(20),cmp.text("Application - "+jdkVersionsMigrate.get(i)[2]).setStyle(titleStyle1),cmp.verticalGap(10),cmp.text("Current JDK Version:"+jdkVersionsMigrate.get(i)[0]+"     Target JDK Version:"+jdkVersionsMigrate.get(i)[1]).setStyle(titleStyle1),
							cmp.text("Automated Remediation"),cmp.verticalGap(10),cmp.subreport(jsReportsMigrate.get(i))
						);
						}
				  		JasperPrint jasperPrint=jsReportMigrate[0].ignorePagination().toJasperPrint();					 
						JRXlsxExporter xlsxexporter = new JRXlsxExporter();
						xlsxexporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
						xlsxexporter.setParameter(JRExporterParameter.OUTPUT_FILE,new File(destinationDirectory+"//MigrationReport.xlsX"));
						xlsxexporter.exportReport();										  
				  		JFrame f = new JFrame("Migration Report");
						f.setSize(1160, 680);
						f.setResizable(true);
				  		f.setLocationRelativeTo(null);
					} 
					catch (IndexOutOfBoundsException | NullPointerException |ClassCastException |UnsupportedOperationException| 
						IllegalArgumentException | JRException |DRException e1) {
						logger.error(e1.getMessage(),e1);
					}							
				}
					
				JFrame success = new JFrame();
				JOptionPane.showMessageDialog(success, "Migration completed. "+multiApplication.size()+ " applications migrated.");
				//Migration report ends							
			}
		}

		/**
		 * @param destinationDirectory
		 * @param executionReport
		 * @param jsReport
		 * @param jsReports
		 * @param jdkVersions
		 * @param totalFilesScanned
		 * @param totalAutoMated
		 * @param totalAssisted
		 * @param titleStyle
		 * @param titleStyle1
		 * @throws DRException
		 * @throws JRException
		 */
		private void destinationDirectory(String destinationDirectory, JasperMigrationReport executionReport,
				JasperReportBuilder[] jsReport, List<JasperReportBuilder[]> jsReports, List<String[]> jdkVersions,
				int totalFilesScanned, int totalAutoMated, int totalAssisted, StyleBuilder titleStyle,
				StyleBuilder titleStyle1) throws DRException, JRException {
			for(int i=0;i<multiApplication.size();i++) {
				scanner = linkListAppScanner.get(i);
				logger.info("Inside Report");
				uploadedAppPath = multiApplication.get(i)[0];
				String strapplicationPath = uploadedAppPath.substring(uploadedAppPath.lastIndexOf('\\')+1,uploadedAppPath.length());
				String[] jdks = {multiApplication.get(i)[1],multiApplication.get(i)[2],strapplicationPath};
				jdkVersions.add(jdks);
				int totalAffectedFiles =scanner.getAutomatedFiles().size()+scanner.getAssistedFiles().size();
				totalFilesScanned = totalFilesScanned+scanner.getTotalFilesScanned();
				totalAutoMated = totalAutoMated + scanner.getAutomatedFiles().size();
				totalAssisted = totalAssisted + scanner.getAssistedFiles().size();
				projectAnalyze.getMigrationSummaryBatch(scanner.getTotalFilesScanned(),totalAffectedFiles,scanner.getAutomatedFiles().size(),scanner.getAssistedFiles().size(),"pre",scanner.getNoOfAutomatedLines(),scanner.getNoOfAssistedLines(),strapplicationPath);
				projectAnalyze.getEffortReportBatch( scanner.getEffortRequired(),strapplicationPath,scanner.getNoOfSimple(),scanner.getNoOfMedium(),scanner.getNoOfComplex());						
				JasperReportBuilder[] jsReportperApplication = new JasperReportBuilder[2];
				jsReportperApplication[0] = executionReport.createReport10(scanner.getListGrid13(),scanner.getColumnHeaders6());
				jsReportperApplication[1] = executionReport.createReport11(scanner.getListGridAssisted(),scanner.getColumnHeadersAssisted());
				jsReports.add(jsReportperApplication);
			}
				
			jsReport[0] = report().setTemplate(Templates.reportTemplate).ignorePagination().setPageFormat(PageType.B4).title(cmp.text("Assessment Report").setStyle(titleStyle).setHorizontalTextAlignment(
			HorizontalTextAlignment.CENTER), cmp.verticalGap(10), cmp.line(), cmp.verticalGap(20),
			cmp.text("Summary"),cmp.subreport(executionReport.createReportSummaryBatch(multiApplication.size(),totalFilesScanned,totalAutoMated,totalAssisted,projectAnalyze.getSummaryHeaderBatch())),cmp.verticalGap(10),
			cmp.text("Summary Break Down"),cmp.subreport(executionReport.createReportSummaryBreakDownBatch(projectAnalyze.getList6(),projectAnalyze.getSummaryColumnHeadersbatch())),cmp.verticalGap(10),
			cmp.text("Effort (Man-hour)"),cmp.subreport(executionReport.createReportEffortBatch(projectAnalyze.getList5(),projectAnalyze.getEffortHeadersBatch()))							
			);
			for(int i=0;i<jsReports.size();i++) {
				jsReport[0].summary(cmp.verticalGap(20),cmp.text("Application - "+jdkVersions.get(i)[2]).setStyle(titleStyle1),cmp.verticalGap(10),cmp.text("Current JDK Version:"+jdkVersions.get(i)[0]+"     Target JDK Version:"+jdkVersions.get(i)[1]).setStyle(titleStyle1),
				cmp.text("Automated Remediation"),cmp.verticalGap(10),cmp.subreport(jsReports.get(i)[0]),
				cmp.text("Assisted Remediation"),cmp.verticalGap(10),cmp.subreport(jsReports.get(i)[1]));
			}
			JasperPrint jasperPrint=jsReport[0].toJasperPrint();				 
			JRXlsxExporter xlsxexporter = new JRXlsxExporter();
			xlsxexporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			xlsxexporter.setParameter(JRExporterParameter.OUTPUT_FILE,new File(destinationDirectory+"//AssessmentReport.xlsX"));
			xlsxexporter.exportReport();
		}

		/**
		 * @param executionReport
		 * @param jsReportsMigrate
		 * @param jdkVersionsMigrate
		 * @param i
		 */
		private void jasperMigration(JasperMigrationReport executionReport, List<JasperReportBuilder> jsReportsMigrate,
				List<String[]> jdkVersionsMigrate, int i) {
			logger.info("inside Post-Report");
			uploadedAppPath = multiApplication.get(i)[0];
			String strapplicationPath = uploadedAppPath.substring(uploadedAppPath.lastIndexOf('\\')+1,uploadedAppPath.length());
			String[] jdks = {multiApplication.get(i)[1],multiApplication.get(i)[2],strapplicationPath};
			jdkVersionsMigrate.add(jdks);
			scanner = linkListAppScanner.get(i);
			int totalAffectedFiles =scanner.getAutomatedFiles().size()+scanner.getAssistedFiles().size();
			projectAnalyze.getMigrationSummaryBatch(scanner.getTotalFilesScanned(),totalAffectedFiles,scanner.getAutomatedFiles().size(),scanner.getAssistedFiles().size(),"post",scanner.getNoOfAutomatedLines(),scanner.getNoOfAssistedLines(),strapplicationPath);
			JasperReportBuilder jsReportperApplication = executionReport.createReport13(scanner.getListGrid13(),scanner.getColumnHeaders6());
			jsReportsMigrate.add(jsReportperApplication);
		}
});				
btnRun.setText("Proceed");
	}

	/**
	 * @param group2
	 * @param radiosTechnology
	 * @param comboSrcServer
	 * @param comboDestServer
	 * @param lblInfyLogo
	 * @param lblMigWiz
	 * @param lblCopyRightFooter
	 * @param btnAnalyze
	 * @param lblUploadedSrcPath
	 */
	private void handleEventGroup(Group group2, Button[] radiosTechnology, Label lblSelAppPath, Text txtSrcDirPath,
			Text txtUploadedPath, Label gitLabel, Text gitTxtBox, Combo comboSrcServer, Combo comboDestServer,
			Label lblInfyLogo, Label lblMigWiz, Label lblCopyRightFooter, Button btnAnalyze, Text lblUploadedSrcPath,
			Label lblName, Label lblPublisher, Label lblInfyLtd, Button btnCheckButton, Button btnRun) {
		btnAnalyze.setEnabled(true);
		radiosTechnology[0].setVisible(true);
		radiosTechnology[1].setVisible(true);
		lblInfyLogo.setVisible(true);
		lblCopyRightFooter.setVisible(true);
		lblMigWiz.setVisible(true);
		txtSrcDirPath.clearSelection();
		txtSrcDirPath.setText("");
		lblName.setVisible(false);
		lblPublisher.setVisible(false);
		lblInfyLtd.setVisible(false);
		btnCheckButton.setVisible(false);
		btnRun.setVisible(false);
		txtUploadedPath.setVisible(true);
		txtUploadedPath.setText("");
		txtUploadedPath.clearSelection();
		lblUploadedSrcPath.setVisible(true);
		lblSelAppPath.setVisible(true);
		gitTxtBox.setVisible(true);
		gitLabel.setVisible(true);

		comboSrcServer.setVisible(true);
		comboSrcServer.clearSelection();
		comboSrcServer.setText("");
		comboDestServer.setVisible(true);
		comboDestServer.setText("");
		comboDestServer.clearSelection();
		group2.setVisible(true);
	}

	/**
	 * @param getFilePath
	 * @param title
	 * @return
	 */
	private String openDialogDirectory(String getFilePath,String title) {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView());
		jfc.setDialogTitle(title);
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnValue = jfc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION && jfc.getSelectedFile().isDirectory()) {			
				logger.info("You selected the directory: " + jfc.getSelectedFile());
				getFilePath = jfc.getSelectedFile().getAbsolutePath();
		}
		return getFilePath;

	}
	

	
	@Override
	public void setFocus() {
		//to be implemented
	}

	public void genAssessmentReport() {
		// ...
		try {
			logger.info("Inside Report");
			Main.callStatus("Wait...");
			projectAnalyze=new ProjectAnalyze();
			logger.info("Inside Report1");
			String strapplicationName = uploadedAppPath.substring(uploadedAppPath.lastIndexOf('\\')+1,uploadedAppPath.length());
			int totalAffectedFilesAuto =scanner.getAutomatedFiles().size()+scanner.getAssistedFiles().size();
			projectAnalyze.getMigrationSummary(scanner.getTotalFilesScanned(),totalAffectedFilesAuto,scanner.getAutomatedFiles().size(),scanner.getAssistedFiles().size(),"pre",scanner.getNoOfAutomatedLines(),scanner.getNoOfAssistedLines());
			Double totalEffortAuto = scanner.getMandEffortRequired() + scanner.getEffortRequired();
			projectAnalyze.getEffortReport(strapplicationName,scanner.getMandEffortRequired(), scanner.getEffortRequired());
			projectAnalyze.getEffortBreakDown(strapplicationName,scanner.getNoOfInfo(),scanner.getNoOfSimple(), scanner.getNoOfMedium(),scanner.getNoOfComplex(),scanner.getInfoEffortRequired(),scanner.getSimEffortRequired(),scanner.getMedEffortRequired(),scanner.getComEffortRequired(),scanner.getNoOfMandInfo(),scanner.getNoOfMandSimple(),scanner.getNoOfMandMedium(),scanner.getNoOfMandComplex(),scanner.getMandInfoEffortRequired(),scanner.getMandSimEffortRequired(),scanner.getMandMedEffortRequired(),scanner.getMandComEffortRequired(),scanner.getNoOfMandRedesign(),scanner.getNoOfMandArch(),scanner.getMandredEffortRequired(),scanner.getMandArchEffortRequired());
				
			logger.info("Inside Report2");
			JasperMigrationReport executionReport = new JasperMigrationReport();
			logger.info("Inside Report3");
			StyleBuilder titleStyle = stl.style(stl.style().bold().setForegroundColor(Color.BLACK))
				.setFontName(Font.SANS_SERIF).setFontSize(20);
			StyleBuilder titleStyle1 = stl.style(stl.style().bold())
				.setFontName(Font.SANS_SERIF).setFontSize(12);
			StyleBuilder titleStyle2 = stl.style(stl.style().bold().underline())
				.setFontName(Font.SANS_SERIF).setFontSize(15);
			JasperReportBuilder[] jsReport = new JasperReportBuilder[2];
			logger.info("Inside Report4");

			jsReport[0] = report().setTemplate(Templates.reportTemplate).setPageFormat(PageType.B4).title(cmp.text("Assessment Report - "+strapplicationName).setStyle(titleStyle).setHorizontalTextAlignment(
				HorizontalTextAlignment.CENTER), cmp.verticalGap(10), cmp.line(), cmp.verticalGap(5));
			logger.info("Inside Report5");
			//commented below code for PwC
			jsReport[0].summary(cmp.text("Current JDK Version:"+fromVersion+"     Target JDK Version:"+toVersion).setStyle(titleStyle1),
			//jsReport[0].summary(cmp.text("Current JDK Version:"+fromVersion+"     Azul JDK Version:"+toVersion).setStyle(titleStyle1),		
			cmp.verticalGap(20),cmp.text("Summary").setStyle(titleStyle1),cmp.verticalGap(10),cmp.subreport(executionReport.createReport15(projectAnalyze.getListGrid19(),projectAnalyze.getSummaryColumnHeaders())),
			//commented below code for PwC
			cmp.verticalGap(20),cmp.text("Estimated Assisted Remediation Effort (person hours)").setStyle(titleStyle1),cmp.verticalGap(10),cmp.subreport(executionReport.createReport17(projectAnalyze.getListGrid15(),projectAnalyze.getEffortHeaders())),
			//cmp.text("Total Remediation Effort = "+Math.round(totalEffortAuto)).setStyle(titleStyle1),
			//cmp.text("*Optional changes are the deprecated java APIs which may work fine in current target version but may not work in higher java versions."),
			cmp.verticalGap(20),cmp.text("Effort Break Down").setStyle(titleStyle1),cmp.verticalGap(10),cmp.subreport(executionReport.createReport18(projectAnalyze.getListGrid13(),projectAnalyze.getEffortBreakHeaders())),
			cmp.verticalGap(20),cmp.text("Changes detected by Java Package").setStyle(titleStyle1),cmp.verticalGap(10),cmp.subreport(executionReport.createpackagereport(scanner.getListGridPackages(),scanner.getColumnpackageHeader())),
			cmp.verticalGap(20),cmp.text("Mandatory Changes").setStyle(titleStyle2),cmp.verticalGap(10),
			//cmp.verticalGap(20),cmp.text("Automated Remediation"),cmp.subreport(executionReport.createReport10(scanner.listMandAuto,scanner.columnHeaders6)),
			cmp.verticalGap(20),cmp.text("Assisted Remediation").setStyle(titleStyle1),cmp.verticalGap(10),cmp.subreport(executionReport.createReport11(scanner.getListGridAssistedMand(),scanner.getColumnHeadersAssisted())),
			cmp.verticalGap(20),cmp.text("Jars").setStyle(titleStyle1),cmp.verticalGap(10),cmp.subreport(executionReport.createReport11(scanner.getListGidlibrary(),scanner.getColumnHeadersAssisted())),
			//cmp.verticalGap(10),cmp.text("Third Party Library"),cmp.subreport(executionReport.createReport19(scanner.listLibrary,scanner.columnHeadersLibrary)),
			cmp.verticalGap(20),cmp.text("Optional Changes").setStyle(titleStyle2),
			cmp.verticalGap(20),cmp.text("Automated Remediation").setStyle(titleStyle1),cmp.verticalGap(10),cmp.subreport(executionReport.createReport10(scanner.getListGrid13(),scanner.getColumnHeaders6())),
			cmp.verticalGap(20),cmp.text("Assisted Remediation").setStyle(titleStyle1),cmp.verticalGap(10),cmp.subreport(executionReport.createReport11(scanner.getListGridAssisted(),scanner.getColumnHeadersAssisted())),
			cmp.verticalGap(20),cmp.text("Incompatible Java Features Scanned").setStyle(titleStyle2),cmp.verticalGap(10),cmp.subreport(executionReport.createIncompatibleFeatureTable(scanner.getListOfIncompatibleFeatures(),scanner.getColumnIncFeatHeader())),
			cmp.verticalGap(20),cmp.text("Executed Rule Summary").setStyle(titleStyle2),cmp.verticalGap(10),cmp.subreport(executionReport.createExecutedRulesTable(scanner.getListOfExecutedRules(),scanner.getColumnExecutedRuleHeader())));
			logger.info("Inside Report6");			  
			logger.info("Inside Report7");
			JFrame f = new JFrame("Assessment Report");
			f.setSize(1160, 680);
			f.setResizable(true);
			f.setLocationRelativeTo(null);
			JasperPrint jasperPrint=jsReport[0].toJasperPrint();
			jasperPrint.setName(strapplicationName+"_Oracle"+fromVersion+"_Open"+toVersion+new Date());
			f.getContentPane().add(new JRViewer(jasperPrint));
			logger.info("Inside Report8");
			f.setVisible(true);  
		} catch (NullPointerException|DRException e1) {
			logger.info("Exception in Report");
			logger.error(e1.getMessage(),e1);
		}
	}

}