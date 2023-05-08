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

public class JsonRulesJDKThirdParty {		
	private List<JDK8ThirdParty> jDK8thirdparty;
	private List<JDK11ThirdParty> jDK11thirdparty;
	
	/**
	 * @return jDK11thirdparty
	 */
	@JsonGetter("JDK11")
	public List<JDK11ThirdParty> getJDK11thirdparty() {
		return jDK11thirdparty;
	}

	/**
	 * @param jDK11thirdparty
	 */
	public void setJDK11thirdparty(List<JDK11ThirdParty> jDK11thirdparty) {
		this.jDK11thirdparty = jDK11thirdparty;
	}

	/**
	 * @return jDK8thirdparty
	 */
	@JsonGetter("JDK8")
	public List<JDK8ThirdParty> getJDK8thirdparty() {
		return jDK8thirdparty;
	}
	/**
	 * @param jDK8thirdparty
	 */
	public void setJDK8thirdparty(List<JDK8ThirdParty> jDK8thirdparty) {
		this.jDK8thirdparty = jDK8thirdparty;
	}
	
}
