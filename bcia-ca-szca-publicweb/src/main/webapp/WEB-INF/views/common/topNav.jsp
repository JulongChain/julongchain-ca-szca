<%@page import="org.bcia.javachain.ca.szca.publicweb.PublicWebConstants"%>
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
	String currMenu = request.getParameter("currMenu");
	//boolean b = "menu_home".equalsIgnoreCase(currMenu)?"menu-item-current":"";
	String menu_home = "menu_home".equalsIgnoreCase(currMenu) ? "active" : "";
	String menu_enroll = "menu_enroll".equalsIgnoreCase(currMenu) ? "active" : "";
	String menu_register = "menu_register".equalsIgnoreCase(currMenu) ? "active" : "";
	String ca_certs = "ca_certs".equalsIgnoreCase(currMenu) ? "active" : "";
	String menu_crl = "menu_crl".equalsIgnoreCase(currMenu) ? "active" : "";
	String menu_user_cert = "menu_user_cert".equalsIgnoreCase(currMenu) ? "active" : "";
	String menu_revoke_cert = "menu_revoke_cert".equalsIgnoreCase(currMenu) ? "active" : "";
	String menu_inspect = "menu_inspect".equalsIgnoreCase(currMenu) ? "active" : "";

	String menu_miscellaneous = "menu_csr".equalsIgnoreCase(currMenu) ? "active" : "";
	String menu_resources = "menu_resources".equalsIgnoreCase(currMenu) ? "active" : "";
	String menu_login = "menu_login".equalsIgnoreCase(currMenu) ? "active" : "";

	String login_url = PublicWebConstants.ADMIN_URL;
%>

<nav class="navbar navbar-expand-lg navbar-dark probootstrap_navbar" id="probootstrap-navbar">
	<div class="container">
		<a class="navbar-brand" href="/">BCIA JuLongChain</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#probootstrap-menu" aria-controls="probootstrap-menu" aria-expanded="false"
			aria-label="Toggle navigation">
			<span><i class="ion-navicon"></i></span>
		</button>
		<div class="collapse navbar-collapse" id="probootstrap-menu">
			<ul class="navbar-nav ml-auto">
				<li id="menu_home" class="nav-item <%=menu_home%>"><a class="nav-link" href="index.html">首页</a></li>
				<li id="menu_register" class="nav-item <%=menu_register%>"><a class="nav-link" href="registerEntity.html">注册实体 </a></li>
				<li id="menu_enroll" class="nav-item <%=menu_enroll%>"><a class="nav-link" href="enrollCert.html">申请证书</a></li>
				<li id="ca_certs" class="nav-item <%=ca_certs%>"><a class="nav-link" href="ca_certs.html">CA证书</a></li>
				<li id="menu_crl" class="nav-item <%=menu_crl%>"><a class="nav-link" href="ca_crls.html">CRL列表</a></li>
				<li id="menu_user_cert" class="nav-item <%=menu_user_cert%>"><a class="nav-link" href="user_cert.html">实体证书</a></li>
				<!--<li id="menu_user_cert" class="nav-item <%=menu_revoke_cert%>"><a class="nav-link" href="revokeCert.html">撤销证书</a></li>-->
				
				 <!--  
				<li id="menu_inspect" class="nav-item <%=menu_inspect%>"><a class="nav-link" href="inspect.html">Inspect</a></li>
				 -->
				<li id="menu_miscellaneous" class="nav-item <%=menu_miscellaneous%>"><a class="nav-link" href="miscellaneous.html">CSR工具</a></li>
				
				<li id="menu_user_cert" class="nav-item <%=menu_resources%>"><a class="nav-link" href="resources.html">文档资源</a></li>
				<li id="menu_login" class="nav-item <%=menu_login%>"><a class="nav-link" href="<%=login_url%>">登录</a></li>
			</ul>
		</div>
	</div>
</nav>