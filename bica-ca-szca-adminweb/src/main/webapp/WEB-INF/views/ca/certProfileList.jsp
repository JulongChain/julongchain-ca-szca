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
			<jsp:param name="menuLevel1" value="ca" />
			<jsp:param name="menuLevel2" value="certProfileList" />
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
							证书模板列表<small></small>
						</h3>
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="#">证书模板</a> <i class="icon-angle-right"></i></li>
							<li><a href="#">证书模板列表</a></li>
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
				<!-- BEGIN PAGE CONTAINER-->
				<!-- BEGIN PAGE CONTENT===搜索条件===-->
				<%--
				<div class="row-fluid">
					<div class="span8">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box green">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-globe"></i>日志搜索条件
								</div>
							</div>
							<div class="portlet-body form">
								<!-- BEGIN FORM-->
								<form name="searchForm" action="#" class="form-horizontal">
									<!--  -->
									<input type='hidden' name='currentPage' id='currentPage' value='${currentPage}'> <input type='hidden' name='rowsPerPage' id='rowsPerPage' value='${rowsPerPage}'>
									<div class="control-group">
										<label class="control-label">Small Input</label>
										<div class="controls">
											<input type="text" placeholder="small" class="m-wrap small" /> <span class="help-inline">Some hint here</span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">Meduam Input</label>
										<div class="controls">
											<input type="text" placeholder="medium" class="m-wrap medium" /> <span class="help-inline">Some hint here</span>
										</div>
									</div>
									<div class="form-actions">
										<button type="submit" class="btn blue">
											<i class="icon-ok"></i> Save
										</button>
										<button type="button" class="btn">Cancel</button>
									</div>
								</form>
								<!-- END FORM-->
							</div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
				</div> --%>
				<!-- END PAGE CONTENT===搜索条件===-->
				<!-- BEGIN PAGE CONTENT===搜索结果===-->
				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box blue">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-reorder"></i> <span class="hidden-480">当前证书模板列表</span>
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
								<!-- <div class="span12">
									<div class="dataTables_paginate paging_bootstrap pagination">
										<form name="listForm" action="#">
											<input type="hidden" name="currentPage" id="currentPage" value="${currentPage}" />
										</form>
										<szca:pager rowsCount="${totalRowsCount}" id="cryptPagerBar" currentPage="${currentPage}" rowsPerPage="${ rowsPerPage}" />
									</div>
								</div> -->
								<table class="table table-striped table-bordered table-hover table-full-width ">
									<tr>
										<th>ID</th>
										<th>模板名称</th>
										<th class="hidden-280" colspan="5" >操作</th>
									</tr>
									<c:forEach items="${defaultCertificateProfileMap }" var="defaultProfile">
										<tr>
											<td>${defaultProfile.key}</td>
											<td>
												${defaultProfile.value}
												<input type="hidden" name="profileNames" value="${defaultProfile.value}"/>
											</td>
											<td colspan="5">
												<input type="button" class="btn white" disabled="disabled" value="查看" onclick=""/>
												<input type="button" class="btn white" disabled="disabled" value="编辑" onclick=""/>
												<input type="button" class="btn white" disabled="disabled" value="删除" onclick=""/>
												<input type="button" class="btn white" disabled="disabled" value="重命名" onclick=""/>
												<input type="button" class="btn blue"  value="复制" onclick="addCertProfile(${defaultProfile.key })"/>
											</td>
										</tr> 
									</c:forEach>
									<c:forEach items="${certificateProfileDatas }" var="definedProfile">
										<tr>
											<td>${definedProfile.id}</td>
											<td>
												${definedProfile.certificateProfileName}
												<input type="hidden" name="profileNames" value="${definedProfile.certificateProfileName}"/>
											</td>
											<td colspan="5">
												<input type="button" name="updateBtn1" class="btn blue" value="查看" onclick="viewCertProfile('${definedProfile.certificateProfileName }')"> 
												<input type="button" name="updateBtn2" class="btn blue" value="编辑" onclick="editCertProfile('${definedProfile.certificateProfileName }')">
												<input type="button" name="updateBtn3" class="btn blue" value="删除" onclick="removeCertProfile('${definedProfile.certificateProfileName }')">
												<input type="button" name="updateBtn4" class="btn blue" value="重命名" onclick="renameCertProfile('${definedProfile.certificateProfileName }')">
												<input type="button" name="updateBtn5" class="btn blue" value="复制" onclick="cloneCertProfile('${definedProfile.certificateProfileName }')">
											</td>
										</tr>
									</c:forEach>
									<tr>
											<td colspan="2"> 
												<input type="text" name="profileName" id="profileName" style="width: 99%" id="profileName"/>
											</td>
											<td colspan="5">
												<input type="button" class="btn blue" value="新建模板" onclick="addCertProfile(1)"/>
											</td>
										</tr>
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

function addCertProfile(type){
	var profileName = document.getElementById("profileName").value;
	if(profileName == ''){
		alert('请指定模板名称！');
		return false;
	}
	if(checkProfileName(profileName)){
		alert('模板名称已存在!');
		return false;
	}
	window.location.href ="${pageContext.request.contextPath}/ca/addCertProfile.html?profileType=" + type + "&profileName=" + profileName;
} 

function cloneCertProfile(orgname){
	var profileName = document.getElementById("profileName").value;
	if(profileName == ''){
		alert('请指定模板名称！');
		return false;
	}
	if(checkProfileName(profileName)){
		alert('模板名称已存在!');
		return false;
	}
	window.location.href ="${pageContext.request.contextPath}/ca/cloneCertProfile.html?orgname=" + orgname + "&profileName=" + profileName;
} 
function removeCertProfile(name){
	if(window.confirm('确定删除证书模板？')){
		window.location.href ="${pageContext.request.contextPath}/ca/removeCertProfile.html?profileName=" + name;
    }
	
} 

function editCertProfile(n){
	window.location.href ="${pageContext.request.contextPath}/ca/editCertProfile.html?profileName=" + n;
} 

function viewCertProfile(n){
	window.location.href ="${pageContext.request.contextPath}/ca/viewCertProfile.html?profileName=" + n;
} 

function renameCertProfile(oldName){
	var newName = document.getElementById("profileName").value;
	if(newName == ''){
		alert('请输入新的模板名称！');
		return false;
	}
	if(checkProfileName(newName)){
		alert('模板名称已存在!');
		return false;
	}
	var profiles = document.getElementsByName("profileNames");
	for(var i=0;i<profiles.length;i++){
		var name = profiles[i].value;
		if(name == newName){
			alert('模板名称已存在!');
			return false;
		}
	}
	if(window.confirm('确定重命名证书模板？')){
		window.location.href ="${pageContext.request.contextPath}/ca/renameCertProfile.html?oldName=" + oldName + "&newName=" + newName;
	}
} 

function checkProfileName(newName){
	var profiles = document.getElementsByName("profileNames");
	for(var i=0;i<profiles.length;i++){
		var name = profiles[i].value;
		if(name == newName){
			return true;
		}
	}
}
</script>
<!-- END BODY -->
<%@include file="../common/corePlugin.jsp"%>
</html>