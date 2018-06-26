/*
 *
 * Copyright © 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 * Copyright © 2018  SZCA. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.bcia.javachain.ca.szca.admin.privileges.vo;

import java.io.Serializable;
import java.util.List;

public class EditBasicAccessRulesFrom implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6141031666408027157L;
 
	private List<Integer> currentCAs;
	private List<Integer> currentEndEntityProfiles;
	private List<Integer> currentOtherRules;
	private List<Integer> currentEndEntityRules;
	private List<String> currentInternalKeybindingRules;
	public List<Integer> getCurrentCAs() {
		return currentCAs;
	}
	public void setCurrentCAs(List<Integer> currentCAs) {
		this.currentCAs = currentCAs;
	}
	public List<Integer> getCurrentEndEntityProfiles() {
		return currentEndEntityProfiles;
	}
	public void setCurrentEndEntityProfiles(List<Integer> currentEndEntityProfiles) {
		this.currentEndEntityProfiles = currentEndEntityProfiles;
	}
	public List<Integer> getCurrentOtherRules() {
		return currentOtherRules;
	}
	public void setCurrentOtherRules(List<Integer> currentOtherRules) {
		this.currentOtherRules = currentOtherRules;
	}
	public List<Integer> getCurrentEndEntityRules() {
		return currentEndEntityRules;
	}
	public void setCurrentEndEntityRules(List<Integer> currentEndEntityRules) {
		this.currentEndEntityRules = currentEndEntityRules;
	}
	public List<String> getCurrentInternalKeybindingRules() {
		return currentInternalKeybindingRules;
	}
	public void setCurrentInternalKeybindingRules(List<String> currentInternalKeybindingRules) {
		this.currentInternalKeybindingRules = currentInternalKeybindingRules;
	}
 	
}
