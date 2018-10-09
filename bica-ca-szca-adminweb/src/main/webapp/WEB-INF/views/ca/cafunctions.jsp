<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.szca.com/jsp/jstl/szca" prefix="szca"%>
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
			<jsp:param name="menuLevel2" value="cafunctions" />
			<jsp:param name="menuLevel3" value="" />
		</jsp:include>
		<!-- END SIDEBAR -->
		<div class="page-content">
			<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<div id="portlet-config" class="modal hide">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button"></button>
					<h3>Widget Settings</h3>
				</div>
				<div class="modal-body">Widget settings form goes here</div>
			</div>
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
							CA结构与CRLs状态 <small>statistics and more</small>
						</h3>
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="caList.html">CA功能</a>
								<i class="icon-angle-right"></i></li>
							<li><a href="#">CA结构与CRLs状态</a></li>
							<li class="pull-right no-text-shadow">
								<div id="dashboard-report-range"
									class="dashboard-date-range tooltips no-tooltip-on-touch-device responsive"
									data-tablet="" data-desktop="tooltips" data-placement="top"
									data-original-title="Change dashboard date range">
									<i class="icon-calendar"></i> <span></span> <i
										class="icon-angle-down"></i>
								</div>
							</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- END DASHBOARD STATS -->

				<!-- ====================== -->
				<c:forEach var="ca" items="${caList}" varStatus="idx">
					<c:set var="caInfo" value="${ca.caInfo}" />
					<c:set var="crlInfo" value="${ca.crlInfo}" />

					<div class="clearfix"></div>
					<div class="span12">
						<!-- BEGIN PORTLET-->
						<div class="portlet paddingless">
							 
							<div class="portlet-title line">
								<div class="caption" style="color:red"  id="id_testResultMsg${ca.caid}"> 
								</div>
							 
							</div>
							 
							<div class="portlet-body">
								<!--BEGIN TABS-->
								<div class="tabbable tabbable-custom">
									<ul class="nav nav-tabs">
										<li class="active"><a href="#tab_1_1" data-toggle="tab">${caInfo.name}</a></li>
									</ul>
									<div class="tab-content">
										<div class="tab-pane active" id="tab_1_1">
											<div class="scroller" data-height="330px"
												data-always-visible="1" data-rail-visible="0">

												<table cellpadding="3">
													<!--  -->
													<tr>
														<th width="120px" align="right">CA序号</th>
														<td>${ca.caid}</td>
														<td></td>
													</tr>
													<tr>
														<th align="right">CA主题</th>
														<td>${caInfo.subjectDN}</td>
														<td></td>
													</tr>

													<tr>
														<th align="right">当前CRL序号</th>
														<td>${crlInfo.lastCRLNumber}</td>
														<td></td>
													</tr>
													<tr>
														<th align="right">当前CRL创建时间</th>
														<td>${szca:formatDate(crlInfo.createDate)}</td>
														<td></td>
													</tr>
													<tr>
														<th align="right">当前CRL过期时间</th>
														<td>${szca:formatDate(crlInfo.expireDate)}</td>
														<td><form
																action="${pageContext.request.contextPath}/ca/createCrl.html"
																method="post">
																<input type="hidden" name="caId" value="${ca.caid}">
																<input type="submit" name="updateCrl" value="更新CRL">
															</form></td>
													</tr>
													 
<tr>
														<th colspan="3"><hr></th>
													 
													</tr>
													<tr>
													<td>
													<c:set var="cronExpression" value="" />
													<c:set var="configed" value="false" />
													<c:set var="runningLabel" value="" />
													<c:set var="runningStatus" value="" />
													<c:forEach items="${crlStrategyList }" var="stg">
													<c:if test="${stg.caid==ca.caid}">
													<c:set var="cronExpression" value="${stg.cronExpr }" />
													<c:set var="configed" value="true" />
													<c:set var="runningStatus" value="尚未启动策略" />
													<c:set var="runningLabel" value="启动策略" />
													<c:if test="${stg.running}">
													<c:set var="runningStatus" value="已经启动策略" />
													<c:set var="runningLabel" value="停止策略" />
													</c:if>
													
													</c:if>
													</c:forEach>
													</td>
													</tr>
													<form id="updateCrlStrategyForm${ca.caid}"  method="post">
													<tr>
														<th align="right">CRL生成策略</th>
														<td><input type="text" name="cronExpression" value="${cronExpression}" placeholder="秒 分 时 日 月 星期" title="Cron Expression定时表达式：秒(0-59) 分(0-59) 时(0-23) 日(1-31) 月(1-12) 星期(1-7)"></td>
														<td>
																<input type="hidden" name="caId" value="${ca.caid}">
																
																<input type="button" name="updateCrlStrategy" value="更新策略" onclick="updateCrlStr(${ca.caid})">
															</td>
													</tr>
													</form>
													<c:if test="${configed }">
													<tr>
														<th align="right">CRL策略状态</th>
														<td id="switchCrlStrategyTD${ca.caid}">${runningStatus}</td>
														<td><form id="switchCrlStrategyForm${ca.caid}" method="post">
																<input type="hidden" name="caId" value="${ca.caid}">
																<input type="button" id="switchCrlStrategyBtn${ca.caid}" name="switchCrlStrategy" value="${runningLabel }" onclick="switchCrlStr(${ca.caid})">
															</form></td>
													</tr>
													 </c:if>
												</table>


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
				<!-- ====================== -->
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

<script>
 
function updateCrlStr(caid){
	 $.ajax({
					type : "POST",
					dataType : "json",
					url : "${pageContext.request.contextPath}/ca/updateCrlStrategy.html",
					data : $('#updateCrlStrategyForm'+caid).serialize(),
					success : function(resp) {	 
						//showMask();						 
						$("#id_testResultMsg"+caid).html(resp.message);	
						alert(resp.message); 
						 
						var url ="${pageContext.request.contextPath}/ca/cafunctions.html";
						setTimeout("window.location.href  = '" + url + "'",1500);//1500毫秒后跳转
					},
					error : function(data) {					 
						//btnobj.setAttribute("disabled", false);
					}
				});
}
function switchCrlStr(caid){
	 
	 $.ajax({
					type : "POST",
					dataType : "json",
					url : "${pageContext.request.contextPath}/ca/switchCrlStrategy.html",
					data : $('#switchCrlStrategyForm'+caid).serialize(),
					success : function(resp) {	 
						//alert(resp.message);						 
						$("#id_testResultMsg"+caid).html(resp.message);
						if(resp.success){
							if($("#switchCrlStrategyBtn"+caid).val()=="启动策略"){
								$("#switchCrlStrategyTD"+caid).html("已经启动策略");
								$("#switchCrlStrategyBtn"+caid).val("停止策略");
							}else{
								$("#switchCrlStrategyTD"+caid).html("已经停止策略");
								$("#switchCrlStrategyBtn"+caid).val("启动策略");
							}
						}
					},
					error : function(data) {					 
						//btnobj.setAttribute("disabled", false);
					}
				});
}
</script>
<!-- END BODY -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<%@include file="../common/corePlugin.jsp"%>
</html>