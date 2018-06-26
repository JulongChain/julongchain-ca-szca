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

package org.bcia.javachain.ca.szca.admin.ra.vo;

import java.io.Serializable;

public class SubjectDnVo implements Serializable{

	 
	private static final long serialVersionUID = 4102371937169854656L;
	private String title;
	private boolean fieldOfType;
	private boolean modifyable;
	private String[] options;
	private String value;
	private String regex;
	private boolean required;
	private String subjectDNField;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isFieldOfType() {
		return fieldOfType;
	}
	public void setFieldOfType(boolean fieldOfType) {
		this.fieldOfType = fieldOfType;
	}
	public boolean isModifyable() {
		return modifyable;
	}
	public void setModifyable(boolean modifyable) {
		this.modifyable = modifyable;
	}
	public String[] getOptions() {
		return options;
	}
	public void setOptions(String[] options) {
		this.options = options;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRegex() {
		return regex;
	}
	public void setRegex(String regex) {
		this.regex = regex;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getSubjectDNField() {
		return subjectDNField;
	}
	public void setSubjectDNField(String subjectDNField) {
		this.subjectDNField = subjectDNField;
	}
  
}
