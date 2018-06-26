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

package com.szca.wfs.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;

public class SzcaElFunction {

	static Logger logger = org.slf4j.LoggerFactory.getLogger(SzcaElFunction.class);

	private final static String defaultPattern = "yyyy-MM-dd HH:mm:ss";
	private final static SimpleDateFormat dateDefaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * @param src
	 * @param length
	 * @return
	 * 
	 */
	public static String trimStr(java.lang.String src, java.lang.String length) {
		if (src == null || "".equals(src))
			return "";
		int srcLen = src.length();
		int endIndex = srcLen;
		try {
			endIndex = Integer.parseInt(length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		endIndex = endIndex > srcLen ? srcLen : endIndex;

		String result = src;
		if (endIndex < srcLen)
			result = src.substring(0, endIndex) + "...";

		// return result;
		return StringEscapeUtils.escapeHtml4(result);
	}

	/**
	 * @param src
	 * @param length
	 * @return
	 */
	public static String trimStrTip(java.lang.String src, java.lang.String length) {
		if (src == null || "".equals(src))
			return "";
		int srcLen = src.length();
		int endIndex = srcLen;
		try {
			endIndex = Integer.parseInt(length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		endIndex = endIndex > srcLen ? srcLen : endIndex;

		String result = "";
		if (endIndex < srcLen)
			result = src;
		else
			result = "";
		// return result;
		return StringEscapeUtils.escapeHtml4(result);
	}

	public static String formatDate(java.lang.Object obj) {
		return formatDate(obj, defaultPattern);
	}

	public static String formatDate(java.lang.Object obj, String pattern) {
		// System.out.println("---pattern:"+pattern +"--"+obj);
		if (obj == null)
			return null;
		SimpleDateFormat sdf = null;
		try {
			sdf = new SimpleDateFormat(pattern);
		} catch (java.lang.IllegalArgumentException exp) {
			logger.equals(String.format("pattern[%s]错误.", pattern));
			exp.printStackTrace();

			sdf = dateDefaultFormat;
		}
		long time = 0;
		if (obj instanceof java.sql.Timestamp) {
			time = ((Timestamp) obj).getTime();
		} else if (obj instanceof java.sql.Date) {
			time = ((java.sql.Date) obj).getTime();
		} else if (obj instanceof java.util.Date) {
			time = ((java.util.Date) obj).getTime();
		} else if (obj instanceof java.lang.Long) {
			time = ((java.lang.Long) obj).longValue();
		} else if (obj instanceof java.lang.String) {
			Date date = new Date();
			// 注意format的格式要与日期String的格式相匹配
			DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				date = sdf.parse((String) obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			time = date.getTime();
		} else {
			return obj.toString();
		}
		return sdf.format(new Date(time));
	}

	public static String escapeHtml(java.lang.String src) {
		if (src == null || "".equals(src))
			return "";
		return StringEscapeUtils.escapeHtml4(src);
	}

	

	public static java.lang.String testRequest(Object obj) {
		System.out.println("===========" + obj);
		if (obj != null) {
			if (obj instanceof HttpServletRequest) {
				HttpServletRequest req = (HttpServletRequest) obj;
				Object pageNo = req.getAttribute(Constants.EL_PAGEBAR_CURR_PAGE_NUM);
				Object rowCounts = req.getAttribute(Constants.EL_PAGEBAR_TOTAL_ROW_COUNT);
				Object rowsPerPage = req.getAttribute(Constants.EL_PAGEBAR_ROWS_PER_PAGE);
				System.out.println("===========" + pageNo + "||||||" + rowCounts + "||||||" + rowsPerPage);
			}
			return obj.toString();
		} else
			return "===";
	}

	public static java.lang.Integer indexOf(java.lang.String src, java.lang.String str) {
		if (src == null || str == null)
			return -1;
		else
			return src.indexOf(str);
	}

}
