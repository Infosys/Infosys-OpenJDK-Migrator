/*
*
* @ 2019-2020 Infosys Limited, Chandigarh, India. All rights reserved.
*
* Version: 1.0.0
*
* Except for any open source components embedded in this Infosys proprietary software program
* ("Program"), this Program is protected by copyright laws, international treaties and other pending or 
* existing intellectual property rights in India, the United States and other countries.
* Except as expressly permitted, any unauthorized reproduction , storage, transmission in any form or by
* any means (including without limitation electronic, mechanical, printing, photocopying, recording or 
* otherwise), or any distribution of this Program, or any portion of it, may result in severe civil and 
* criminal penalties, and will be prosecuted to the maximum extent possible under the law
* 
*/

/*
*
* Date: 06-May-2019
* @author Ajay_Singh10, ADMCOE, Infosys Ltd
* @version 1.0.0
* Description: 
*
*/

package com.infy.openjdk.ui;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {
	
	@Override
	public void createInitialLayout(IPageLayout layout) {		
		layout.setEditorAreaVisible(true);		
		layout.setFixed(false);		
	}

}
