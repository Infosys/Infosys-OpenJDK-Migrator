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
import javax.swing.*;
import com.infy.openjdk.ui.View;

public class Main {
	public static void callStatus(String status) {
		final JFrame frame = new JFrame(status);
		final JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		final JPanel contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 35));
		contentPane.setLayout(new BorderLayout());
		contentPane.add(new JLabel("In Progress..."), BorderLayout.NORTH);
		contentPane.add(progressBar, BorderLayout.CENTER);
		frame.setContentPane(contentPane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setState(Frame.MAXIMIZED_BOTH);

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				// do loading stuff in here
				// for now, simulate loading task with Thread.sleep(...)
				
				try {
					Thread.sleep(1000);
				} 
				catch (InterruptedException e) {
					View.logger.error(e.getMessage());
					Thread.currentThread().interrupt();
				}
				try {
					Thread.sleep(1000);
				} 
				catch (InterruptedException e) {
					View.logger.error(e.getMessage());
					Thread.currentThread().interrupt();
				}				
				// when loading is finished, make frame disappear
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						frame.setVisible(false);

					}
				});

			}
		};
		new Thread(runnable).start();
	}
}