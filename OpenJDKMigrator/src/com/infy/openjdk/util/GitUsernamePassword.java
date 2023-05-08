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

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

public class GitUsernamePassword {
	String userName = null;
	String password = null;
	boolean close = false;		
	/**
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GitUsernamePassword git = new GitUsernamePassword();
		git.getUserNamePassword();		
	}
	/**
	 * @return 
	 */
	public GitUsernamePassword getUserNamePassword() {
		GitUsernamePassword git = new GitUsernamePassword();
		JFrame jf = new JFrame("Enter credentials");
		jf.setBackground(new Color(255,255,255));
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBounds(30, 100, 420, 100);
        p.setBackground(new Color(255,255,255));
        JLabel username = new JLabel();
        username.setText("Username:");
        username.setBounds(75, 50, 150, 25);
        username.setVisible(true);
		JTextField usertext = new JTextField(SWT.BORDER);
		usertext.setBounds(200, 50, 150, 25);
		usertext.setVisible(true);
        JLabel password = new JLabel();
        password.setText("Password:");
        password.setBounds(75, 100, 150, 25);
        password.setVisible(true);
		JPasswordField passwordtext = new JPasswordField(SWT.BORDER);
		passwordtext.setBounds(200, 100, 150, 25);
		passwordtext.setVisible(true);
		JButton donebtn = new JButton();
		donebtn.setText("DONE");
		donebtn.setBounds(200, 150, 75, 25);
		donebtn.setBackground(new Color(20, 132, 219));
		donebtn.setForeground(new Color(255, 255, 255));
		donebtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				git.setUserName(usertext.getText());
				git.setPassword(passwordtext.getText());
				close = true;
				jf.dispose();				
			}
		});
		p.add(username);
		p.add(password);
		p.add(usertext);
		p.add(passwordtext);
		p.add(donebtn);
		jf.add(p);
		jf.setSize(450, 250); 
		jf.setVisible(true);
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		//below loop to hold control till user pressess done button
		while(!close){
		}
        return git;
	}
	
}