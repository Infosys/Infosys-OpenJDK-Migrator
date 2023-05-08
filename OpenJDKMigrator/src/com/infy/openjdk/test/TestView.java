package com.infy.openjdk.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

import com.infy.openjdk.ui.View;

public class TestView {

	@Test
	public void testView() {
		Display disp = Display.getDefault();
		Shell shell = new Shell(disp);
		shell.setLayout(new FillLayout());
		Composite composite = new Composite(shell, SWT.NONE);
		View view = new View();
		view.createPartControl(composite);
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
