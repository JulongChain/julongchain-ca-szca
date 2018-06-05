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

package org.bcia.javachain.ca.szca.admin.ra.servcie;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ejbca.core.model.ra.raadmin.EndEntityProfile;

public interface EndEntityProfileService {
	//加载终端实体模板配置
	public Map<String, String> loadProfileMap();
	//加载默认证书模板
	public Map<Integer, String> loadDefaultCertProfile();
	//转换终端实体模板配置为JSON字符串
	public String loadProfileAutoJson(LinkedHashMap<String, Object> tranProfileMap);
	//编辑终端实体
	public void handleProfileChange(HttpServletRequest request, EndEntityProfile eep);
}
