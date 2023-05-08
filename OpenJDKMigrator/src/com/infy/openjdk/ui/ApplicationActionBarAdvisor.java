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

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.infy.openjdk.ui.OpenViewAction;


public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	

	private OpenViewAction openViewAction1;
	public ApplicationActionBarAdvisor(IActionBarConfigurer actionBarConfigurer1) {
		super(actionBarConfigurer1);
	}

	@Override
	protected void makeActions(final IWorkbenchWindow workbenchWindow1) {
		// Creates the actions and registers them.
		// Registering is needed to ensure that key bindings work.
		// The corresponding commands keybindings are defined in the plugin.xml file.
		// Registering also provides automatic disposal of the actions when
		// the window is closed.

		openViewAction1 = new OpenViewAction(workbenchWindow1, "Home page", View.ID);
		register(openViewAction1);

	}

	@Override
	protected void fillMenuBar(IMenuManager menuManager) {
		MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
		menuManager.add(fileMenu);
		// Add a group marker indicating where action set menus will appear.
		menuManager.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		// File
		fileMenu.add(openViewAction1);
		// Help

	}

	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
		coolBar.add(new ToolBarContributionItem(toolbar, "main"));
		toolbar.add(openViewAction1);

	}
}