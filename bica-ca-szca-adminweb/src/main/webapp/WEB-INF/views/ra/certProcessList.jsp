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

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
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
		<!-- BEGIN SIDEBAR -->
		<%-- 使用jsp:include动态包含，可以向sideBar传递参数，确保对应菜单状态为设置为active  --%>
		<%--@include file="common/sideBar.jsp"--%>
		<jsp:include page="../common/sideBar.jsp" flush="true">
			<jsp:param name="menuLevel1" value="ra" />
			<jsp:param name="menuLevel2" value="certProcessList" />
			<jsp:param name="menuLevel3" value="" />
		</jsp:include>
		<!-- END SIDEBAR -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN PAGE HEADER-->
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN STYLE CUSTOMIZER -->
						<%--@include file="common/theme.jsp" --%>
						<!-- END BEGIN STYLE CUSTOMIZER -->
						<!-- BEGIN PAGE TITLE & BREADCRUMB-->
						<h3 class="page-title">
							实体证书流程列表<small></small>
						</h3>
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="#">实体证书流程配置</a> <i class="icon-angle-right"></i></li>
							<li><a href="#">实体证书流程列表</a></li>
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
				<!-- BEGIN PAGE CONTENT===搜索结果===-->
				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box blue">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-reorder"></i> <span class="hidden-480">当前实体证书流程列表</span>
								</div>
								<div></div>
							</div>
							<div class="portlet-body">
								<div class="span12">
									<div class="dataTables_paginate paging_bootstrap pagination">
										<form name="caForm" action="#">
											<a id="open_form_modal3" class="btn hide" href="#form_import_modal" data-toggle="modal"></a> 
											<a id="open_form_modal4" class="btn hide" href="wizardCA.html"></a> 	
										</form>

									</div>
								</div>
								<table class="table table-striped table-bordered table-hover table-full-width ">
									<tr>
										<td colspan="5">
											<input type="button" class="btn blue" value="新建流程" onclick="addCertProcess()"/>
										</td>
									</tr>
									<tr>
										<th>流程ID</th>
										<th>证书流程名称</th>
										<th>证书模板名称</th>
										<th>终端实体模板名称</th>
										<th>CA名称</th>
										<th>创建时间</th>
										<th>状态</th>
										<th>说明</th>
									</tr>
									<c:forEach items="${certProcessDatas }" var="certProcessData">
										<tr>
											<td>${certProcessData.processId}</td>
											<td><a href="javascript:void(0)" onclick="editCertProcess('${certProcessData.processId}')">${certProcessData.processName}</a></td>
											<td>${certProcessData.endEntityProfileName}</td>
											<td>${certProcessData.certProfileName}</td>
											<td>${certProcessData.caName}</td>
											<td>${certProcessData.createTime}</td>
											<td>
												<c:if test="${certProcessData.status eq 1}">
													正常
												</c:if>
												<c:if test="${certProcessData.status eq -1}">
													停用
												</c:if>
											</td>
											<td>${certProcessData.memo}</td>
										</tr>
									</c:forEach>
									
								</table> 
							</div> 
							<!-- END EXAMPLE TABLE PORTLET-->
						</div>
					</div>
				</div>
				<!-- END PAGE CONTENT===搜索结果===-->
			</div>
			<!-- END PAGE CONTAINER-->
		</div>
	</div>
	<!-- END PAGE -->
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@include file="../common/footer.jsp"%>
	<!-- END FOOTER -->
</body>
<script>

function addCertProcess(){
	window.location.href ="${pageContext.request.contextPath}/ra/addCertProcess.html";
} 

function editCertProcess(id){
	window.location.href ="${pageContext.request.contextPath}/ra/editCertProcess.html?id=" + id;
}

</script>
<!-- END BODY -->
<%@include file="../common/corePlugin.jsp"%>
</html>