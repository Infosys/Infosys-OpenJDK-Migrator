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

import java.awt.*;
import java.io.File;

import javax.swing.JFileChooser;


import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.*;

import javax.swing.table.*;

public class SimpleTableTest extends JFrame {
  private JPanel topPanel ;
  private JTable table;
  private JScrollPane scrollPane;
  private String[] columnNames= new String[3];
  private String[][] dataValues=new String[3][3] ;

  public SimpleTableTest() {
    setTitle("JTable Cell Click");
    setSize(300,300);
    topPanel= new JPanel();
    topPanel.setLayout(new BorderLayout());
    getContentPane().add(topPanel);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    columnNames=new String[] {"Column 1" , "Column 2" , "Column 3"};
    dataValues = new String[][] {
      {"1","2","3"},
      {"4","5","6"},
      {"7","8","9"}
    };

    TableModel model=new myTableModel();
    table =new JTable( );
    table.setRowHeight(50);
    table.setModel(model);
    scrollPane=new JScrollPane(table);
    topPanel.add(scrollPane,BorderLayout.CENTER);   

    table.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent e) {
        int row=table.rowAtPoint(e.getPoint());
        int col= table.columnAtPoint(e.getPoint());
        JOptionPane.showMessageDialog(null," Value in the cell clicked :"+ " "  +table.getValueAt(row,col).toString());
      }
    }
    );
  }

  class myTableModel extends DefaultTableModel {
    myTableModel( ){
      super(dataValues,columnNames);
      System.out.println("Inside myTableModel");
    }
    public boolean isCellEditable(int row,int cols) {
      return false;                                                                                        
    }
  }         

  // public static void main(String args[]) throws IOException
  public void multipload() {            		
    JFileChooser chooser = new JFileChooser();
    chooser.setMultiSelectionEnabled(true);            	    
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.showOpenDialog(null);
    int returnValue = chooser.showSaveDialog(null);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      if (chooser.getSelectedFile().isDirectory()) {    				
        File[] files = chooser.getSelectedFiles();
        for(File file:files) {
          System.out.println("Path--->"+file.getAbsolutePath());
        }            	  
      }
    }
  }         
}