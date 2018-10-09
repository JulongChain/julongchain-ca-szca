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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringEscapeUtils;

public class PagerTag extends SimpleTagSupport {
	private String rowsCount;
	private String pageCount;
	private String currentPage;
	private String rowsPerPage;
	private String id;
	private String name;
	private String gotoJsFunction;

	public String getRowsCount() {
		return rowsCount;
	}

	public void setRowsCount(String rowCount) {
		this.rowsCount = rowCount;
	}

	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(String rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGotoJsFunction() {
		return gotoJsFunction;
	}

	public void setGotoJsFunction(String gotoJsFunction) {
		this.gotoJsFunction = gotoJsFunction;
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter writer = getJspContext().getOut();
		//writer.print("======================");

		int rCount = 0, pCount = 0, rPerPage = Constants.PAGE_ROW_NUM, cPage = 1;
		try {
			rCount = Integer.parseInt(this.rowsCount);
		} catch (Exception e) {
		}
		if (rCount < 0)
			rCount = 0;

		try {
			cPage = Integer.parseInt(this.currentPage);
		} catch (Exception e) {
		}
		if (cPage < 0)
			cPage = 1;

		try {
			rPerPage = Integer.parseInt(this.rowsPerPage);
		} catch (Exception e) {
		}
		if (rPerPage < 1)
			rPerPage = Constants.PAGE_ROW_NUM;

		if(gotoJsFunction==null || "".equals(gotoJsFunction))
			this.gotoJsFunction = "goto_page";
		 
		if(id==null || "".equals(id))
			this.id = "pager";
		
		if(name==null || "".equals(name))
			this.name = id;
		
		String barHtml = this.pagerBar(rCount, cPage, rPerPage, gotoJsFunction, this.id, this.name);
		writer.print(barHtml);
	}

	 

	private String pagerBar(int rowsCount, int currPage, int rowsPerPage, String gotoJsFunction, String id,
			String name) {
		// int rows = 0, rowsPP = Constants.PAGE_ROW_NUM, currPage = 1, pagesCount = 0;
		// <div class="dataTables_paginate paging_bootstrap pagination">
		// <ul>
		// <li class="prev"><a href="#"><span class="hidden-480"> << </span></a></li>
		// <li class="prev"><a href="#"><span class="hidden-480"> < </span></a></li>
		// <li><a href="#">1</a></li>
		// <li><a href="#">2</a></li>
		// <li><a href="#">3</a></li>
		// <li><a href="#">4</a></li>
		// <li class="active"><a href="#">5</a></li>
		// <li class="next disabled"><a href="#"><span
		// class="hidden-480">>></span></a></li>
		// <li class="next disabled"><a href="#"><span
		// class="hidden-480">></span></a></li>
		// </ul>
		// </div>
		 

		if (rowsCount <= 0)
			return "共0条记录";
		int pagesCount = rowsCount / rowsPerPage + (rowsCount % rowsPerPage >= 1 ? 1 : 0);
		currPage = currPage <= 0 ? 1 : currPage;
		currPage = currPage > pagesCount ? pagesCount : currPage;

		StringBuffer buff = new StringBuffer();
		
		/*
		if (pagesCount > 0) {
			buff.append(String.format(
					"<input type='hidden' name='%s.currentPage' id='%s.currentPage' value='%d'>", name,id,currPage));
			buff.append(String.format(
					"<input type='hidden' name='%s.rowsPerPage' id='%s.rowsPerPage' value='%s'>", name,id,rowsPerPage));
			 
		}
		 */
		if (pagesCount > 1)
			buff.append(String.format("<span style='vertical-align:middle;padding-bottom:20px;display:inline-block;'>"
					+ " 共<b>%s</b>条记录, 第<b>%d</b>页, 共<b>%d</b>页.  </span>", rowsCount, currPage, pagesCount));
		else
			buff.append(String.format(" 共<b>%d</b>条记录.  ", rowsCount));

		if (pagesCount > 1)
			buff.append("<ul>");
		String href = null;

		if (pagesCount > 1 && pagesCount <= 10)
			for (int i = 1; i <= pagesCount; i++) {
				if (i == currPage)
					href = "<li class=\"active\"><a href='#' onclick='%s(%d)'><b>%d</b></a></li>";
				else
					href = "<li><a href='#' onclick='%s(%d)'>%d</a></li>";
				buff.append(String.format(href, gotoJsFunction, i, i));
			}

		if (pagesCount > 10) {
			// page 1~4
			for (int i = 1; i <= 4; i++) {
				if (i == currPage)
					href = "<li class=\"active\"><a href='#' title='第%d页' onclick='%s(%d)'><b>%d</b></a></li>";
				else
					href = "<li><a href='#' title='第%d页' onclick='%s(%d)'>%d</a></li>";
				buff.append(String.format(href, i, gotoJsFunction, i, i));
			}
			// middle page
			// buff.append("......");

			//
			// first page
			// buff.append(String.format("</ul><ul>"));
			buff.append(String.format("<li><a href='#' title='首页' onclick='%s(1)'> %s </a></li>", gotoJsFunction,
					escapeHtml("<<")));
			// pre page
			// if(currPage>1)
			buff.append(String.format("<li><a href='#' title='上一页'  onclick='%s(%d)'> %s </a></li>", gotoJsFunction,
					(currPage - 1) > 1 ? (currPage - 1) : 1, escapeHtml("<")));

			// goto page
			// buff.append(String.format("<li>第<input type='text' size='4' length='4'
			// placeholder=\"small\" class=\"m-wrap small\" name='gotoPageNum' value='%d'
			// onblur='%s(this.value)'>页</li>", currPage, gotoJsFunction));
			buff.append(String.format(
					"<li><span>第"
					+ "<input type='text' size='4' length='4' placeholder=\"small\" name='gotoPageNum' value='%d' "
					+ "onblur='%s(this.value)' style='width:30px;text-align:center;line-height:15px;margin:0 2px;height:10px;'>"
					+ "页</span></li>",
					currPage, gotoJsFunction));

			// next page
			// if(currPage<pagesCount)
			buff.append(String.format("<li><a href='#' title='下一页' onclick='%s(%d)'> %s </a></li>", gotoJsFunction,
					(currPage + 1) < pagesCount ? (currPage + 1) : pagesCount, escapeHtml(">")));
			// last page
			buff.append(String.format("<li><a href='#' title='末页' onclick='%s(%d)'> %s </a></li>", gotoJsFunction,
					pagesCount, escapeHtml(">>")));

			// buff.append(String.format("</ul><ul>"));

			// last 4 pages
			for (int i = pagesCount - 3; i <= pagesCount; i++) {
				if (i == currPage)
					href = "<li class=\"active\"><a href='#' title='第%d页' onclick='%s(%d)'><b>%d</b></a></li>";
				else
					href = "<li><a href='#' title='第%d页' onclick='%s(%d)'>%d</a></li>";
				buff.append(String.format(href, i, gotoJsFunction, i, i));
			}

		}
		if (pagesCount > 1)
			buff.append("</ul>");

	//	if (pagesCount > 10)
			// goto page
	//		buff.append(String.format(
	//				"跳到第<input type='text' size='4' length='4' placeholder=\"small\" class=\"m-wrap small\" name='gotoPageNum' value='%d' onblur='%s(this.value)'>页,<input type='button' name='btnGo' value='btnGo' onClick='%s(this.value)'>",
	//				currPage, gotoJsFunction, currPage, gotoJsFunction));
		return buff.toString();
	}

	public String escapeHtml(java.lang.String src) {
		if (src == null || "".equals(src))
			return "";
		return StringEscapeUtils.escapeHtml4(src);
	}
}
