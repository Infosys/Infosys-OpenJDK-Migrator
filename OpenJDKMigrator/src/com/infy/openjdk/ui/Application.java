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

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;


public class Application implements IApplication {

	@Override
	public Object start(IApplicationContext context1) {
	
		Display display1 = PlatformUI.createDisplay();
		try {
			int returnCode1 = PlatformUI.createAndRunWorkbench(display1, new ApplicationWorkbenchAdvisor());
			if (returnCode1 == PlatformUI.RETURN_RESTART) {
				return IApplication.EXIT_RESTART;
			}
			return IApplication.EXIT_OK;
		} 
		finally {
			display1.dispose();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	@Override
	public void stop() {
		if (!PlatformUI.isWorkbenchRunning())
			return;
		final IWorkbench workbench1 = PlatformUI.getWorkbench();
		final Display display1 = workbench1.getDisplay();
		display1.syncExec(new Runnable() {
			@Override
			public void run() {
				if (!display1.isDisposed())//
					workbench1.close();
			}
		});
	}
	
}
