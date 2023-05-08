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

import org.eclipse.core.commands.AbstractHandler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.QuickMenuCreator;
import org.eclipse.ui.handlers.HandlerUtil;

public class IHandlerImpl extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub				
		Application Application = new Application();
		
		try {
		    HandlerUtil.getActiveWorkbenchWindow(event)
			.getActivePage()
			.showView("OpenJDKMigrator.view");
		} 
		catch (PartInitException e) {
		    throw new ExecutionException("Error while opening view", e);
		}
		return null;
	}

}