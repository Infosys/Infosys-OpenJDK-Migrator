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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class MainView {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		Display disp = Display.getDefault();
		Shell shell = new Shell(disp);
		shell.setLayout(new FillLayout());
		Composite composite = new Composite(shell, SWT.NONE);
		View view = new View();
		System.out.println("Before the call");
		view.createPartControl(composite);
		System.out.println("After the call");
		composite.setVisible(true);
		shell.pack();
		shell.open();
		shell.setEnabled(true);
		while (!shell.isDisposed()) {
			if (!disp.readAndDispatch())
				disp.sleep();
		}
		disp.dispose();
	}
	
}