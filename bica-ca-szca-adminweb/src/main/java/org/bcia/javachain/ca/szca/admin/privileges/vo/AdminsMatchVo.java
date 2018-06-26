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

public class AdminsMatchVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2965082277654159469L;

	
	private String issuingCA;
	private String adminsMatchWith;
	private String adminsMatchType;
	private String matchValue;
 	private int primaryKey;
	public String getIssuingCA() {
		return issuingCA;
	}
	public void setIssuingCA(String issuingCA) {
		this.issuingCA = issuingCA;
	}
	public String getAdminsMatchWith() {
		return adminsMatchWith;
	}
	public void setAdminsMatchWith(String adminsMatchWith) {
		this.adminsMatchWith = adminsMatchWith;
	}
	public String getAdminsMatchType() {
		return adminsMatchType;
	}
	public void setAdminsMatchType(String adminsMatchType) {
		this.adminsMatchType = adminsMatchType;
	}
	public int getPrimaryKey() {
		return primaryKey;
	}
	public String getMatchValue() {
		return matchValue;
	}
	public void setMatchValue(String matchValue) {
		this.matchValue = matchValue;
	}
	public void setPrimaryKey(int primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	
}
