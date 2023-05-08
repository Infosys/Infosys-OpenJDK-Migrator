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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

public class OpenViewAction extends Action {
	private final IWorkbenchWindow window;
	private int instanceNumber  = 0;
	private final String viewId1;

	/**
	 * @param window
	 * @param label
	 * @param viewId
	 */
	public OpenViewAction(IWorkbenchWindow window, String label, String viewId) {
		this.window = window;
		this.viewId1 = viewId;
		setText(label);
		// The id is used to refer to the action in a menu or toolbar
		setId(ICommandIds.CMD_OPEN);
		// Associate the action with a pre-defined command, to allow key bindings.
		setActionDefinitionId(ICommandIds.CMD_OPEN);
		setImageDescriptor(com.infy.openjdk.ui.Activator.getImageDescriptor("/icons/JDKTool.png"));
	}

	@Override
	public void run() {
		if (window != null) {
			try {
				window.getActivePage().showView(viewId1, Integer.toString(instanceNumber++), IWorkbenchPage.VIEW_ACTIVATE);
			} catch (PartInitException e) {
				MessageDialog.openError(window.getShell(), "Error", "Error opening view:" + e.getMessage());
			}
		}
	}
}