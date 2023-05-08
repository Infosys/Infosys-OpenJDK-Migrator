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

//Java Program to create a 
//simple progress bar 
import java.awt.Color;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import com.infy.openjdk.ui.View;

public class Progress {
	// create a frame 
	JFrame f; 
	static Integer value =0;
	JProgressBar b;    
    static Integer i = 0;
    static Integer k = 0;    
    boolean status = false; 
    /**
     * @return
     */
    public JProgressBar getB() {
		return b;
	}

	/**
	 * @param b
	 */
	public void setB(JProgressBar b) {
		this.b = b;
	}

	/**
	 * @return
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(Integer value) {
		this.value = value;
	}
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} 
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException| UnsupportedLookAndFeelException e) {
			View.logger.error(e.getMessage());
		}
		Progress progress = new Progress();
		String uri ="D:\\DemoJDK\\ApplicationDemo";
		File dir = new File(uri);
		progress.displayDirectoryContents(dir);
		progress.progressbar(i);
	}
	
	/**
	 * @return
	 */
	public JFrame getF() {
		return f;
	}

	/**
	 * @param f
	 */
	public void setF(JFrame f) {
		this.f = f;
	}

	/**
	 * @param totalCount
	 */
	public  void progressbar(Integer totalCount) {
		// create a frame 
        f = new JFrame("Analysis in progress..."); 
        i=totalCount;
        // create a panel 
        f.setBackground(new Color(255,255,255));
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBounds(30, 30, 420, 30);
        p.setBackground(new Color(255,255,255));
        // create a progressbar 
        b = new JProgressBar(0,totalCount); 
        b.setBounds(30, 30, 420, 30);
        // set initial value 
        b.setValue(value); 
        b.setStringPainted(true); 
        b.setBackground(new Color(255,255,255));
        b.setForeground(new Color(134, 38, 195));
        // add progressbar 
        p.add(b);   
        // add panel 
        f.add(p); 		  
        // set the size of the frame 
        f.setSize(480, 150); 
        f.setVisible(true);
        f.setLocationRelativeTo(null);		
	}
	
	// function to increase progress 
    public  void fill() 
    { 
        int j = 0; 
        try { 
            while (j <= i) {         		
                // fill the menu bar 
                b.setValue(j);   
                // delay the thread 
                Thread.sleep(500); 
                j += 1; 
            }
        } 
        catch (IllegalArgumentException | InterruptedException e) {
        	View.logger.error(e.getMessage());
        }
		finally {
        	f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
    } 

	/**
	 * @param dir
	 */
	public void displayDirectoryContents(File dir) {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					displayDirectoryContents(file);
				} 
				else {
					i++;
				}				
			}
		}
		catch (SecurityException | NullPointerException e) {
			View.logger.error(e.getMessage());
		}
	}    		
}