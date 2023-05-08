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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class TabbedPane extends JFrame {
    JTabbedPane tabs;
    CoursePanel cource;
    SelectCoursePanel selectCourse;

    TabbedPane() {
        super("Tabbed Pane Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        // Setting the JTabbedPane Position and Layout as Wrap
        tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        cource = new CoursePanel();
        selectCourse = new SelectCoursePanel();
        // Adding user defined pannels to JTabbedPane
        tabs.addTab("Cources", cource);
        tabs.addTab("Select Course", selectCourse);        
        // Adding JPanels to JTabbedPane
        tabs.addTab("Listing", new JPanel());
        tabs.addTab("Comment", new JTextArea(10, 40));
        tabs.addTab("Register", new JPanel());
        tabs.addTab("Contact Us", new JPanel());
        tabs.addTab("More..", new JPanel());
        getContentPane().add(tabs);
    }
}

/*Creating CoursePanel by extending JPanel*/
class CoursePanel extends JPanel {
    JButton addCourse, clear;
    CoursePanel() {
        addCourse = new JButton("Add Course");
        clear = new JButton("Clear");
        setLayout(new FlowLayout());
        add(addCourse);
        add(clear);
    }
}
/*Creating SelectCoursePanel by extending JPanel*/
class SelectCoursePanel extends JPanel {
    JCheckBox java, swing, hibernate;
    SelectCoursePanel() {
        java = new JCheckBox("Java");
        swing = new JCheckBox("Spring");
        hibernate = new JCheckBox("Hibernate");
        setLayout(new FlowLayout());
        add(java);
        add(swing);
        add(hibernate);
    }
}

class JTabbedPaneDemo {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        TabbedPane frame = new TabbedPane();
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}