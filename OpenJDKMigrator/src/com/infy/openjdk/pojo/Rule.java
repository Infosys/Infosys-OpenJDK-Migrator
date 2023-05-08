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

public class Rule {
	private String name;
	private String value;
private boolean isMatches;
	/**
	 * @return isMatches
	 */
	public boolean isMatches() {
	return isMatches;
}

/**
 * @param isMatches
 */
public void setMatches(boolean isMatches) {
	this.isMatches = isMatches;
}
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

}