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

public class AccessRuleDataVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8944461148806507232L;
	
	private int  state;
	private String parsedAccessRule;
	private boolean recursive;
    private int primaryKey;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getParsedAccessRule() {
		return parsedAccessRule;
	}

	public void setParsedAccessRule(String parsedAccessRule) {
		this.parsedAccessRule = parsedAccessRule;
	}

	public boolean isRecursive() {
		return recursive;
	}

	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}

	public int getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(int primaryKey) {
		this.primaryKey = primaryKey;
	}
 
	
	
}
