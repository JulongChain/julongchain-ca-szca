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

import java.util.ArrayList;
import java.util.List;

public class WebUtils {

	// public static <T> List<T> getPageList(List<T> list, Pager form) {
	//
	// return getPageList(list,form.getCurrentPage(),form.getRowsPerPage());
	// }

	public static <T> List<T> getPageList(List<T> list) {
		return getPageList(list, 1, Constants.PAGE_ROW_NUM);
	}

	public static <T> List<T> getPageList(List<T> list, String currentPage) {
		int curr = 1;
		try {
			curr = Integer.parseInt(currentPage);
		} catch (Exception e) {
		}
		if (curr < 1)
			curr = 1;
		return getPageList(list, curr, Constants.PAGE_ROW_NUM);
	}

	public static <T> List<T> getPageList(List<T> list, int currentPage) {
		return getPageList(list, currentPage, Constants.PAGE_ROW_NUM);
	}

	public static <T> List<T> getPageList(List<T> list, int currentPage, int rowsPerPage) {
		int rc = 0;
		if (list != null && !list.isEmpty())
			rc = (int) list.size();

		int rowsPP = rowsPerPage;
		if (rowsPP < 1)
			rowsPP = Constants.PAGE_ROW_NUM;

		int pageCount = rc / rowsPP + ((rc % rowsPP) >= 1 ? 1 : 0);

		int currPage = currentPage;
		if (currPage > pageCount)
			currPage = pageCount;
		else if (currPage < 1)
			currPage = 1;

		int start = (currPage - 1) * rowsPP;
		int end = currPage * rowsPP - 1;

		List<T> pagerList = new ArrayList<T>();

		if (list != null && !list.isEmpty()) {
			int i = 0;
			for (T row : list) {
				if (i >= start && i <= end)
					pagerList.add(row);
				i++;
			}
		}
		return pagerList;
	}

	// public static List<WfsBaseCode> getKVList(List<ResultRow> dataList, String
	// valueKey, String labelKey) {
	// List<WfsBaseCode> list = new ArrayList<WfsBaseCode>();
	// if (dataList != null && !dataList.isEmpty()) {
	// Iterator<ResultRow> it = dataList.iterator();
	// try {
	//
	// while (it.hasNext()) {
	// ResultRow row = it.next();
	// WfsBaseCode bc = new WfsBaseCode();
	// bc.setCode(row.getString(valueKey));
	// bc.setDesc(row.getString(labelKey));
	// list.add(bc);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// return list;
	// }

	// public static List<String> getKeyArray(List<ResultRow> dataList, String key)
	// {
	// List<String> list = new ArrayList<String>();
	// if (dataList != null && !dataList.isEmpty()) {
	// Iterator<ResultRow> it = dataList.iterator();
	// try {
	//
	// while (it.hasNext()) {
	// ResultRow row = it.next();
	//
	// list.add(row.getString(key));
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// return list;
	// }

}
