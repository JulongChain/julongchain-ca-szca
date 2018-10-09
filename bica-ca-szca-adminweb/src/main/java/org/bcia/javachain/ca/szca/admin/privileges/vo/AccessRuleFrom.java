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

import java.util.List;

public class AccessRuleFrom {

    private List<AccessRuleDataVo> accessRuleDataList;

//	public List<AccessRuleDataVo2> getAccessRuleDataList() {
//		return accessRuleDataList;
//	}
//
//	public void setAccessRuleDataList(List<AccessRuleDataVo2> accessRuleDataList) {
//		this.accessRuleDataList = accessRuleDataList;
//	}

	public List<AccessRuleDataVo> getAccessRuleDataList() {
		return accessRuleDataList;
	}

	public void setAccessRuleDataList(List<AccessRuleDataVo> accessRuleDataList) {
		this.accessRuleDataList = accessRuleDataList;
	}

    
    
}
