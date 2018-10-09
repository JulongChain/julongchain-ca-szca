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

package org.bcia.javachain.ca.szca.util;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;


public final class ServletUtils {
	public static final String PREFIX = "filter_";

	private ServletUtils() {

	}

	@SuppressWarnings("unchecked")
	public static Map<String,String> getParameters(HttpServletRequest request) {
		Enumeration<String> paramNames = request.getParameterNames();
		Map<String,String> paramMap = new HashMap<String,String>();

		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if (paramName.startsWith(PREFIX)) {
				String[] tmp = paramName.split("_");
				String paramValue = request.getParameterValues(paramName)[0].toString();
				if (StringUtils.isNotBlank(paramValue)) {
					if (tmp.length == 2) {
						paramMap.put(tmp[1], paramValue);
					}
				}
			}
		}
		return paramMap;
	}

	public static void putParam(ModelAndView view,Map<String,String> paramMap) {
		if(paramMap == null
				|| paramMap.isEmpty()) {
			return;
		}
		
		for(Map.Entry<String, String> entry : paramMap.entrySet()) {
			view.addObject(PREFIX + entry.getKey(), entry.getValue());
		}
	}
}




