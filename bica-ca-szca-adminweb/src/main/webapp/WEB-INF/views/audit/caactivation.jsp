<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.szca.com/jsp/jstl/szca" prefix="szca"%>
<%--
  ~
  ~ Copyright © 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
  ~ Copyright © 2018  SZCA. All Rights Reserved.
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
  ~
  --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../common/metaHead.jsp"%>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<%@include file="../common/header.jsp"%>
	<!-- END HEADER -->
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- START SIDEBAR -->
		<%-- 使用jsp:include动态包含，可以向sideBar传递参数，确保对应菜单状态为设置为active  --%>
		<%--@include file="common/sideBar.jsp"--%>
		<jsp:include page="../common/sideBar.jsp" flush="true">
			<jsp:param name="menuLevel1" value="ca" />
			<jsp:param name="menuLevel2" value="caactivation" />
			<jsp:param name="menuLevel3" value="" />
		</jsp:include>
		<!-- END SIDEBAR -->
		<div class="page-content">
			<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<%--
			<div id="portlet-config" class="modal hide">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button"></button>
					<h3>Widget Settings</h3>
				</div>
				<div class="modal-body">Widget settings form goes here</div>
			</div>
			 --%>
			<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<!-- BEGIN PAGE CONTAINER-->
			<div class="container-fluid">
				<!-- BEGIN PAGE HEADER-->
				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN STYLE CUSTOMIZER -->
						<%--@include file="common/theme.jsp" --%>
						<!-- END BEGIN STYLE CUSTOMIZER -->
						<!-- BEGIN PAGE TITLE & BREADCRUMB-->
						<h3 class="page-title">
							CA运行状态 <small>启动或停止CA服务</small>
						</h3>
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="index.html">CA功能</a> <i class="icon-angle-right"></i></li>
							<li><a href="#">CA服务</a></li>
							<li class="pull-right no-text-shadow">
								<div id="dashboard-report-range" class="dashboard-date-range tooltips no-tooltip-on-touch-device responsive" data-tablet="" data-desktop="tooltips" data-placement="top"
									data-original-title="Change dashboard date range">
									<i class="icon-calendar"></i> <span></span> <i class="icon-angle-down"></i>
								</div>
							</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- END DASHBOARD STATS -->
				<%--
				<c:forEach items="${cas}" var="ca" varStatus="idx">
					<c:set var="caInfo" value="${ca.caInfo}" />
					<c:set var="crlInfo" value="${ca.crlInfo}" />
				 
				 
				<div class="clearfix"></div>
					<div class="span12">
						<!-- BEGIN PORTLET-->
						<div class="portlet paddingless">
							<div class="portlet-title line">
								<div class="caption">
									<i class="icon-bell"></i> ${caInfo.name}  
								</div>
								 
							</div>
							<div class="portlet-body">
								<!--BEGIN TABS-->
								<div class="tabbable tabbable-custom">
									<ul class="nav nav-tabs">
										<li class="active"><a href="#tab_1_1" data-toggle="tab">${caInfo.subjectDN}</a></li>
									</ul>
									<div class="tab-content">
										<div class="tab-pane active" id="tab_1_1">
											<div class="scroller" data-height="290px" data-always-visible="1" data-rail-visible="0">
											<h2>密钥服务状态：<input type="button" name="cryptoBtn" value="启动"></h2>
											<h2>CA服务状态：<input type="button" name="caBtn" value="启动"></h2>
											<h2>CA监控状态：<input type="button" name="monitorBtn" value="启动"></h2>
												<!-- 
												<ul class="feeds">
													<li>
														<div class="col1">
															<div class="cont">
																<div class="cont-col1">
																	<div class="label label-success">
																		<i class="icon-bell"></i>
																	</div>
																</div>
																<div class="cont-col2">
																	<div class="desc">
																		You have 4 pending tasks. <span class="label label-important label-mini"> Take action <i class="icon-share-alt"></i>
																		</span>
																	</div>
																</div>
															</div>
														</div>
														<div class="col2">
															<div class="date">Just now</div>
														</div>
													</li>
												</ul> -->
											</div>
										</div>
									</div>
								</div>
								<!--END TABS-->
							</div>
						</div>
						<!-- END PORTLET-->
					</div>
				</c:forEach>
				 --%>
				<c:forEach items="${aalist}" var="tokenAndCa" varStatus="idx">
					<c:set var="caInfo" value="${ca.caInfo}" />
					<c:set var="crlInfo" value="${ca.crlInfo}" />
					<div class="clearfix"></div>
					<div class="span12">
						<!-- BEGIN PORTLET-->
						<div class="portlet paddingless">
							<%--
							<div class="portlet-title line">
								<div class="caption">
									<i class="icon-bell"></i> ${tokenAndCa.ca.name} 
								</div>
							 
							</div>
							 --%>
							<div class="portlet-body">
								<!--BEGIN TABS-->
								<div class="tabbable tabbable-custom">
									<ul class="nav nav-tabs">
										<li class="active"><a href="#tab_1_1" data-toggle="tab">${tokenAndCa.ca.name}</a></li>
									</ul>
									<div class="tab-content">
										<div class="tab-pane active" id="tab_1_1">
											<div class="scroller" data-height="160px" data-always-visible="1" data-rail-visible="0">
												
													<table>
													<!--  -->
														<tr>
															<th>CA名称</th>
															<td>${tokenAndCa.ca.name}</td>
															<td></td>
														</tr>
														<tr>
															<th>CA服务状态</th>
															<td><c:if test="${tokenAndCa.ca.active}">已经启动</c:if>
																<c:if test="${!tokenAndCa.ca.active}">已经停止</c:if></td>
																<td>
																<form name="caForm" action="activation.html">
																<input type="hidden" name="caid" value="${tokenAndCa.ca.caId}"/>
																<input type="hidden" name="cryptoTokenName" value="${tokenAndCa.cryptoToken.cryptoTokenName}"/>
																<input type="hidden" name="caNewState" value="${!tokenAndCa.ca.active}"/>
																<input type="hidden" name="monitoredNewState" value="${tokenAndCa.ca.monitoredNewState}"/>
																<input type="hidden" name="cryptoTokenNewState" value="${tokenAndCa.cryptoToken.cryptoTokenActive}"/>
																<c:if test="${tokenAndCa.ca.active}"><input type="submit" name="btnStart" value="停止CA服务"/></c:if>
																<c:if test="${!tokenAndCa.ca.active}"><input type="submit" name="btnStart" value="启动CA服务"/></c:if>
																</form>
																</td>
														</tr>
														<!-- 
											<tr>
											<th>tokenAndCa.cryptoToken.existing</th><td>${tokenAndCa.cryptoToken.existing}</td>
											</tr>
											 -->
														<tr>
															<th>密钥名称</th>
															<td>${tokenAndCa.cryptoToken.cryptoTokenName}</td>
														</tr>
														<tr>
															<th>密钥服务状态</th>
															<td><c:if test="${tokenAndCa.cryptoToken.cryptoTokenActive}">已经启动</c:if>
																<c:if test="${!tokenAndCa.cryptoToken.cryptoTokenActive}">已经停止</c:if></td>
																<td>
																<form  name="ctForm" action="activation.html">
																<input type="hidden" name="caid" value="${tokenAndCa.ca.caId}"/>
																<input type="hidden" name="cryptoTokenName" value="${tokenAndCa.cryptoToken.cryptoTokenName}"/>
																<input type="hidden" name="caNewState" value="${tokenAndCa.ca.active}"/>
																<input type="hidden" name="monitoredNewState" value="${tokenAndCa.ca.monitoredNewState}"/>
																<input type="hidden" name="cryptoTokenNewState" value="${!tokenAndCa.cryptoToken.cryptoTokenActive}"/>
																
															<c:if test="${tokenAndCa.cryptoToken.cryptoTokenActive}"><input type="submit" name="btnStart" value="停止秘钥服务"/></c:if>
																<c:if test="${!tokenAndCa.cryptoToken.cryptoTokenActive}">授权码:<input type="password" name="authCode" value=""/> <input type="submit" name="btnStart" value="启动秘钥服务"/></c:if>
																</form>
																</td>
														</tr>
														<!-- 
														<tr>
															<th>CA监控状态</th>
															<td>${tokenAndCa.ca.monitoredNewState}</td>
														</tr>
														 -->
													</table>
													<!-- 
											<h2>密钥服务状态：${tokenAndCa.cryptoToken.cryptoTokenName}<input type="button" name="cryptoBtn" value="启动"></h2>
											<div>tokenAndCa.cryptoToken.existing：${tokenAndCa.cryptoToken.existing}</div>
											<div>tokenAndCa.cryptoToken.cryptoTokenActive：${tokenAndCa.cryptoToken.cryptoTokenActive} <input type="checkbox" name="cryptoTokenNewState" checked="${tokenAndCa.cryptoToken.cryptoTokenActive }"></div>
											<div>tokenAndCa.cryptoToken.cryptoTokenName：${tokenAndCa.cryptoToken.cryptoTokenName}</div>
											
											<h2>CA服务状态：${tokenAndCa.ca.name}  <input type="button" name="caBtn" value="启动"></h2>
											<div>tokenAndCa.ca.active：${tokenAndCa.ca.active}</div>
											
											
											<h2>CA监控状态：${tokenAndCa.ca.name}<input type="button" name="monitorBtn" value="启动"></h2>
											<div>tokenAndCa.ca.monitoredNewState：${tokenAndCa.ca.monitoredNewState}</div>	
											CA Id<input type="text" name="caid" value="${tokenAndCa.ca.caId}"> 
											Auth Code<input type="text" name="authCode" value="Foo123">
											<input type="submit" name="apply" value="启动">
											 -->
												</form>
											</div>
										</div>
									</div>
								</div>
								<!--END TABS-->
							</div>
						</div>
						<!-- END PORTLET-->
					</div>
				</c:forEach>
			</div>
		</div>
		<!-- END PAGE CONTAINER-->
	</div>
	<!-- E====END PAGE==== -->
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@include file="../common/footer.jsp"%>
	<!-- END FOOTER -->
</body>
<!-- END BODY -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<%@include file="../common/corePlugin.jsp"%>
</html>