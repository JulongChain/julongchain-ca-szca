<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<div class="page-container row-fluid">
		<!-- BEGIN SIDEBAR -->
		<%-- 使用jsp:include动态包含，可以向sideBar传递参数，确保对应菜单状态为设置为active  --%>
		<jsp:include page="../common/sideBar.jsp" flush="true">
			<jsp:param name="menuLevel1" value="ra" />
			<jsp:param name="menuLevel2" value="certProcessList" />
			<jsp:param name="menuLevel3" value="" />
		</jsp:include>
		<!-- END SIDEBAR -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<div id="portlet-config" class="modal hide">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button"></button>
					<h3>portlet Settings</h3>
				</div>
				<div class="modal-body">
					<p>Here will be a configuration form</p>
				</div>
			</div>
			<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<!-- BEGIN PAGE CONTAINER-->
			<div class="container-fluid">
				<!-- BEGIN PAGE HEADER-->
				<div class="row-fluid">
					<div class="span12">
						<h3 class="page-title">
							实体证书流程配置 <small></small>
						</h3>
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="certProcessList.html">实体证书流程配置</a> <span class="icon-angle-right"></span></li>
							<li><a href="#">添加实体证书流程</a> <span class="icon-angle-right"></span></li>
							<li>
								<div id="dashboard-report-range" class="dashboard-date-range tooltips no-tooltip-on-touch-device responsive" data-tablet="" data-desktop="tooltips" data-placement="top"
									data-original-title="Change dashboard date range">
									<i class="icon-calendar"></i> <span></span> <i class="icon-angle-down"></i>
								</div>
							</li>
						</ul>
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->
				<div class="row-fluid">
					<div class="span12">
						<div class="portlet box blue" id="form_wizard_1">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-reorder"></i> 添加实体证书流程
								</div>
								<div class="tools hidden-phone">
									<a href="javascript:;" class="collapse"></a> <a href="#portlet-config" data-toggle="modal" class="config"></a> <a href="javascript:;" class="reload"></a> <a href="javascript:;" class="remove"></a>
								</div>
							</div>
							<div class="portlet-body form">
								<form action="handleCertProcess.html" class="form-horizontal" id="submit_form" name="submit_form" method="post">
									<!-- caType: 1=X509,2=CVC -->
									<div class="form-wizard">
										<div class="tab-content">
											<div class="alert alert-error hide">
												<button class="close" data-dismiss="alert"></button>
												某些输入有误， 请检查.
											</div>
											<div class="alert alert-success hide">
												<button class="close" data-dismiss="alert"></button>
												Your form validation is successful!
											</div>
											<div class="tab-pane active" id="tab1">
												<div class="control-group">
													<label class="control-label">实体流程名称：<span class="required">*</span></label>
													<div class="controls">
														<input type="text" onchange="processNameChange()" name="processName" id="processName" /> 
													</div> 
												</div>
												
												<div class="control-group">
													<label class="control-label">证书模板：</label>
													<div class="controls">
														<select id="certProfileName" name="certProfileName" >	
															<c:forEach items="${certificateProfileDatas }" var="certProfile">
																<option value="${certProfile.certificateProfileName }">${certProfile.certificateProfileName }</option>
															</c:forEach>
														</select>
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label">终端实体模板：</label>
													<div class="controls">
														<select id="endEntityProfileName" name="endEntityProfileName" >	
															<c:forEach items="${endEntityProfileDatas }" var="endEntityProfile">
																<option value="${endEntityProfile.profileName }">${endEntityProfile.profileName }</option>
															</c:forEach>
														</select>
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label">CA名称：</label>
													<div class="controls">
														<select id="caName" name="caName" >	
															<c:forEach items="${cas }" var="ca">
																<option value="${ca.name }">${ca.name }</option>
															</c:forEach>
														</select>
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label">状态：</label>
													<div class="controls">
														<select id="status" name="status" >	
															<option value="1">正常</option>
															<option value="-1">停用</option>
														</select>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">说明：</label>
													<div class="controls">
														<textarea rows="4" cols="6" name="memo"></textarea>
													</div>
												</div>
												
											</div>
										</div>
										<div class="form-actions clearfix">
											<input type="button" class="btn blue" id="submitBtn" disabled="disabled" onclick="submit_form.submit()" value="提交"/>
											<input type="button" class="btn blue" onclick="history.go(-1);" value="返回"/>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				<!-- END PAGE CONTENT-->
			</div>
			<!-- END PAGE CONTAINER-->
		</div>
		<!-- END PAGE -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@include file="../common/footer.jsp"%>
	<!-- END FOOTER -->
</body> 

<script src="${pageContext.request.contextPath}/media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script>  
	
	function validateAll(){
		if($('#processName').val() == null || $('#processName').val() == ''){
			alert('请输入实体流程名称!');
			return false;
		}
	}
	
	function processNameChange(){
		if($('#processName').val() == null || $('#processName').val() == ''){
			$('#submitBtn').attr("disabled",true);
		}else{
			$('#submitBtn').attr("disabled",false);
		}
	}
	
	
</script>

</html>