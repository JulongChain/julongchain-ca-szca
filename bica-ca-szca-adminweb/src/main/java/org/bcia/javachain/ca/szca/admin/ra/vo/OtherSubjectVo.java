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

public class OtherSubjectVo {
 	private String title;
	private boolean fieldImplemented;
	private boolean rfc822NameStringc;
	private boolean fieldOfTypeRFC822;
	private boolean fieldOfTypeUPN;
	private boolean modifyable;
	private boolean use;
	private String[] rfc822NameOptions;
	private String[] nonRFC822NAMEOptions;
	private String value;
	private String regex;
	private boolean required;
	private String[] rfc822NameArray;
	private String subjectDNField;

	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isFieldImplemented() {
		return fieldImplemented;
	}
	public void setFieldImplemented(boolean fieldImplemented) {
		this.fieldImplemented = fieldImplemented;
	}
 
	public boolean isModifyable() {
		return modifyable;
	}
	public void setModifyable(boolean modifyable) {
		this.modifyable = modifyable;
	}
	public boolean isUse() {
		return use;
	}
	public void setUse(boolean use) {
		this.use = use;
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
	public boolean isFieldOfTypeRFC822() {
		return fieldOfTypeRFC822;
	}
	public void setFieldOfTypeRFC822(boolean fieldOfTypeRFC822) {
		this.fieldOfTypeRFC822 = fieldOfTypeRFC822;
	}
	public boolean isFieldOfTypeUPN() {
		return fieldOfTypeUPN;
	}
	public void setFieldOfTypeUPN(boolean fieldOfTypeUPN) {
		this.fieldOfTypeUPN = fieldOfTypeUPN;
	}
	public boolean isRfc822NameStringc() {
		return rfc822NameStringc;
	}
	public void setRfc822NameStringc(boolean rfc822NameStringc) {
		this.rfc822NameStringc = rfc822NameStringc;
	}
	public String[] getRfc822NameArray() {
		return rfc822NameArray;
	}
	public void setRfc822NameArray(String[] rfc822NameArray) {
		this.rfc822NameArray = rfc822NameArray;
	}
	public String[] getRfc822NameOptions() {
		return rfc822NameOptions;
	}
	public void setRfc822NameOptions(String[] rfc822NameOptions) {
		this.rfc822NameOptions = rfc822NameOptions;
	}
	public String[] getNonRFC822NAMEOptions() {
		return nonRFC822NAMEOptions;
	}
	public void setNonRFC822NAMEOptions(String[] nonRFC822NAMEOptions) {
		this.nonRFC822NAMEOptions = nonRFC822NAMEOptions;
	}
	public String getSubjectDNField() {
		return subjectDNField;
	}
	public void setSubjectDNField(String subjectDNField) {
		this.subjectDNField = subjectDNField;
	}

	
	
}
