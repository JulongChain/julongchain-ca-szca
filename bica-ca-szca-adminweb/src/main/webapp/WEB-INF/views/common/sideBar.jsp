<%@page import="org.bcia.javachain.ca.szca.admin.common.AdminWebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  ~ Copyright ? 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
  ~ Copyright ? 2018  SZCA. All Rights Reserved.
  ~ <p>
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~ <p>
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~ <p>
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<%
//获得活动工菜单
	String plvl1 = request.getParameter("menuLevel1");
	String plvl2 = request.getParameter("menuLevel2");
	String plvl3 = request.getParameter("menuLevel3");
	String publicUrl = AdminWebConstants.PUBLIC_URL;
%>
<%!
String isActive(String menuId,String plvl1,String plvl2,String plvl3){
	if( menuId==null || "".equals(menuId)) return "";	
	return  (menuId.equalsIgnoreCase(plvl1) || menuId.equalsIgnoreCase(plvl2) || menuId.equalsIgnoreCase(plvl3))?"active":"";
}
String isSelect(String menuId,String plvl1,String plvl2,String plvl3){
	if( menuId==null || "".equals(menuId)) return "";	
	return  (menuId.equalsIgnoreCase(plvl1) || menuId.equalsIgnoreCase(plvl2) || menuId.equalsIgnoreCase(plvl3))?"<span class=\"selected\"></span>":"";
}
String isOpen(String menuId,String plvl1,String plvl2,String plvl3){
	//有子菜单才需要使用此方法
	if( menuId==null || "".equals(menuId)) return "";	
	return  (menuId.equalsIgnoreCase(plvl1) || menuId.equalsIgnoreCase(plvl2) || menuId.equalsIgnoreCase(plvl3))?"<span class=\"arrow open\"></span>":"<span class=\"arrow \"></span>";
}
%>
<!-- BEGIN SIDEBAR -->
<div class="page-sidebar nav-collapse collapse">
	<!-- BEGIN SIDEBAR MENU -->
	<ul class="page-sidebar-menu">
		<li>
			<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
			<div class="sidebar-toggler hidden-phone"></div> <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
		</li>
		<%--
		<li>
			<!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
			<form class="sidebar-search">
				<div class="input-box">
					<a href="javascript:;" class="remove"></a> <input type="text" placeholder="Search..." /> <input type="button" class="submit" value=" " />
				</div>
			</form> <!-- END RESPONSIVE QUICK SEARCH FORM -->
		</li> --%>
		<li class="start <%=isActive("home",plvl1,plvl2,plvl3)%>"><a href="${pageContext.request.contextPath}/index.html"> <i class="icon-home"></i> <span class="title">首页</span> <%=isSelect("home",plvl1,plvl2,plvl3)%>
		</a></li>
		<li class="<%=isActive("ca",plvl1,plvl2,plvl3)%>"><a href="javascript:;"> <i class="icon-cogs"></i> <span class="title">CA功能</span> <%=isSelect("ca",plvl1,plvl2,plvl3)%><%=isOpen("ca",plvl1,plvl2,plvl3)%>
		</a>
			<ul class="sub-menu">
				<li class="<%=isActive("caactivation",plvl1,plvl2,plvl3)%>"><a href="${pageContext.request.contextPath}/ca/caactivation.html"> CA启动激活</a></li>
				<li class="<%=isActive("cafunctions",plvl1,plvl2,plvl3)%>"><a href="${pageContext.request.contextPath}/ca/cafunctions.html">CA结构 & CRLs</a></li>
				<li class="<%=isActive("certProfileList",plvl1,plvl2,plvl3)%>"><a href="${pageContext.request.contextPath}/ca/certProfileList.html"> 证书模板</a></li>			 
				<li class="<%=isActive("caList",plvl1,plvl2,plvl3)%>"><a href="${pageContext.request.contextPath}/ca/caList.html">CA配置管理</a></li>
				<li class="<%=isActive("cryptoToken",plvl1,plvl2,plvl3)%>"><a href="${pageContext.request.contextPath}/cryptoToken/cryptoTokenList.html">秘钥配置管理</a></li>
			<!-- 	<li><a href="layout_ajax.html"> 发布器配置管理</a></li> -->
 			</ul></li>
		<li class="<%=isActive("ra",plvl1,plvl2,plvl3)%>"><a href="javascript:;"> <i class="icon-bookmark-empty"></i> <span class="title">RA 功能</span> <%=isSelect("ra",plvl1,plvl2,plvl3)%><%=isOpen("ra",plvl1,plvl2,plvl3)%>
		</a>
			<ul class="sub-menu">
				<li  class="<%=isActive("addendentity",plvl1,plvl2,plvl3)%>"><a href="${pageContext.request.contextPath}/ra/addendentity.html"> 添加终端实体</a></li>
				<li  class="<%=isActive("listEndentity",plvl1,plvl2,plvl3)%>"> <a href="${pageContext.request.contextPath}/ra/listEndentity.html">终端实体管理</a></li>
				<li  class="<%=isActive("endEntityProfileList",plvl1,plvl2,plvl3)%>"><a href="${pageContext.request.contextPath}/ra/endEntityProfileList.html">终端实体模板配置管理</a></li>
       			<li  class="<%=isActive("certProcessList",plvl1,plvl2,plvl3)%>"><a href="${pageContext.request.contextPath}/ra/certProcessList.html">实体证书流程配置</a></li>
       			</ul></li>
		<li class="<%=isActive("audit",plvl1,plvl2,plvl3)%>"><a href="javascript:;"> <i class="icon-table"></i> <span class="title">监察员功能</span> <%=isSelect("audit",plvl1,plvl2,plvl3)%><%=isOpen("audit",plvl1,plvl2,plvl3)%>
		</a>
			<ul class="sub-menu">
			<!-- 	<li><a href="form_layout.html"> 操作审核</a></li> -->
 				<li class="<%=isActive("logSearch",plvl1,plvl2,plvl3)%>"><a href="${pageContext.request.contextPath}/audit/logSearch.html">操作日志</a></li>
 			</ul></li>
		<li class="<%=isActive("sys",plvl1,plvl2,plvl3)%>"><a href="javascript:;"> <i class="icon-briefcase"></i> <span class="title">系统功能</span> <%=isSelect("sys",plvl1,plvl2,plvl3)%><%=isOpen("sys",plvl1,plvl2,plvl3)%> 
		</a>
			<ul class="sub-menu">
			   <li class="<%=isActive("administratorprivileges",plvl1,plvl2,plvl3)%>"><a href="${pageContext.request.contextPath}/privileges/administratorprivileges.html">管理员权限配置管理</a></li>
 			<!-- 	<li><a href="page_coming_soon.html"> <i class="icon-cogs"></i> 秘钥绑定配置管理
				</a></li>
				<li><a href="page_blog.html"> <i class="icon-comments"></i>服务配置管理
				</a></li> -->
 			</ul></li>
		<!--
		<li class=""><a href="javascript:;"> <i class="icon-gift"></i> <span class="title">系统配置</span> <span class="arrow "></span>
		</a>
			<ul class="sub-menu">
				<li><a href="extra_profile.html">CMP配置管理</a></li>
				<li><a href="extra_lock.html"> SCEP配置管理</a></li>
				<li><a href="extra_faq.html"> 系统配置管理</a></li>
 			</ul></li>-->
  	 <!-- 
		<li class="last "><a href="charts.html"> <i class="icon-bar-chart"></i> <span class="title">管理员首选项</span>
		</a></li> -->
		<li class="last "><a href="<%=publicUrl%>"> <i class="icon-bar-chart"></i> <span class="title">公共前端网站</span>
		</a></li>
		<!--
		<li class="last "><a href="charts.html"> <i class="icon-bar-chart"></i> <span class="title">文档</span>
		</a></li>
		<li class="last "><a href="charts.html"> <i class="icon-bar-chart"></i> <span class="title">登出</span>
		</a></li>
		 -->
	</ul>
	<!-- END SIDEBAR MENU -->
</div>
<!-- END SIDEBAR -->
