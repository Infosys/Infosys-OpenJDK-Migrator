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

package com.infy.openjdk.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonGetter;

public class JsonRulesJDK {	
	private List<JDK8> listJDK8;
	private List<JDK11> listJDK11;

	/**
	 * @return listJDK8
	 */
	@JsonGetter("JDK8")
	public List<JDK8> getJDK8() {
		return listJDK8;
	}
	/**
	 * @param jDK8
	 */
	public void setJDK8(List<JDK8> jDK8) {
		listJDK8 = jDK8;
	}

	/**
	 * @return listJDK11
	 */
	@JsonGetter("JDK11")
	public List<JDK11> getJDK11() {
		return listJDK11;
	}
	/**
	 * @param jDK11
	 */
	public void setJDK11(List<JDK11> jDK11) {
		listJDK11 = jDK11;
	}

}
