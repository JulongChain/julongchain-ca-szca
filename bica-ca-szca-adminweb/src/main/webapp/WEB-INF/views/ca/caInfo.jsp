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
			<jsp:param name="menuLevel2" value="caList" />
			<jsp:param name="menuLevel3" value="view" />
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
							CA <small></small>
						</h3>
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="index.html">CA功能</a> <i class="icon-angle-right"></i></li>
							<li><a href="#">CA列表</a><i class="icon-angle-right"></i></li>
							<li><a href="#">查看CA</a></li>
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
				<%----%>
				<div class="row-fluid">
					<!-- ===START===CA基本信息===== -->
					<div class="span6">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box green">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-globe"></i>CA基本信息
								</div>
							</div>
							<div class="portlet-body form">
								<!-- BEGIN FORM-->
								<form name="renameCaForm" action="renameCA.html" class="form-horizontal">
									<!--  -->
									<c:set var="token" value="${cainfo.CAToken}" />
									<div class="control-group">
										<label class="control-label">CA序号</label>
										<div class="controls">
										<input type="text" name="caid" readOnly value="${cainfo.CAId}" />
											 
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">CA名称</label>
										<div class="controls">
											<input type="text" name="name" class="colorpicker-default m-wrap" value="${cainfo.name}" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">CA类型</label>
										<div class="controls">
											<span class="help-inline">${cainfo.CAType}</span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">签名算法</label>
										<div class="controls">
											<span class="help-inline">${cainfo.CAType}</span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">密钥库名称</label>
										<div class="controls">
											<span class="help-inline">${token.cryptoTokenId}</span>
										</div>
									</div>
									 
									<div class="form-actions">
										<button class="btn blue btn-primary" onclick="renameCaForm.submit()">
											<i class="icon-ok"></i>保存
										</button>
										<button class="btn " onclick="renameCaForm.reset()">取消</button>
									</div>
								</form>
								<!-- END FORM-->
							</div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
					<!-- ===END===CA基本信息===== -->
					<!-- ===START===CA证书信息===== -->
					<div class="span6">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box green">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-globe"></i>CA证书信息
								</div>
							</div>
							<div class="portlet-body form">
								<!-- BEGIN FORM-->
								<div class="portlet-body form">
									<!-- BEGIN FORM-->
									<c:set var="certList" value="${cainfo.certificateChain}" />
									<form name="caForm1" action="#" class="form-horizontal">
										<div class="control-group">
											<label class="control-label">主题（DN）</label>
											<div class="controls">
												<span class="help-inline">${certList[0].subjectDN}</span>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">发行者DN</label>
											<div class="controls">
												<span class="help-inline">${certList[0].issuerDN}</span>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">签发者</label>
											<div class="controls">
												<span class="help-inline"></span>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">证书模板</label>
											<div class="controls">
												<input type="text" name="name" class="colorpicker-default m-wrap" value="" />
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">有效期</label>
											<div class="controls">
												<span class="help-inline">${szca:formatDate(certList[0].notBefore)} ~ ${szca:formatDate(certList[0].notAfter)}</span>
											</div>
										</div>
										<div class="form-actions">
										 
											<button class="btn blue btn-primary" class="btn blue btn-primary" onclick="openCaCert(${cainfo.CAId})">查看CA证书详情</button>
										</div>
									</form>
									<!-- END FORM-->
								</div>
							</div>
							<!-- END EXAMPLE TABLE PORTLET-->
						</div>
						<!-- ===END===CA证书信息===== -->
					</div>
					<!-- END PAGE CONTENT===搜索条件===-->
					<!-- BEGIN PAGE CONTENT===搜索结果===-->
					<!-- END PAGE CONTENT===搜索结果===-->
				</div>
				<!-- 第二行=========================== -->
				<div class="row-fluid">
					<!-- ===START===撤销CA===== -->
					<div class="span6">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box green">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-globe"></i>撤销CA
								</div>
							</div>
							<div class="portlet-body form">
								<!-- BEGIN FORM-->
								<form name="caForm1" action="#" class="form-horizontal">
									<!--  -->
									<input type='hidden' name='caid' id='caid' value='${cainfo.CAId}'>
									<div class="control-group">
										<label class="control-label">状态</label>
										<div class="controls">有效/已撤销</div>
									</div>
									<div class="control-group">
										<label class="control-label">撤销原因</label>
										<div class="controls">
											<select name="revokereason">
												<option value='0'>没有指定</option>
												<option value='1'>密钥损坏</option>
												<option value='2'>CA损坏</option>
												<option value='3'>从属关系改变</option>
												<option value='4'>证书替换</option>
												<option value='5'>停止使用</option>
												<option value='6'>证书挂起</option>
												<option value='8'>从CRL中删除</option>
												<option value='9'>撤销权限</option>
												<option value='10'>AA泄密</option>
											</select>
										</div>
									</div>
									<div class="form-actions">
										<button class="btn blue btn-primary" onclick="importP12Form.submit()">
											<i class="icon-ok"></i>撤销
										</button>
										<button class="btn blue btn-primary" onclick="importP12Form.submit()">
											<i class="icon-ok"></i>重新发布CA证书
										</button>
										<button class="btn " onclick="importP12Form.reset()">取消</button>
									</div>
								</form>
								<!-- END FORM-->
							</div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
					<!-- ===END===撤销CA===== -->
					<!-- ===START===导出CA===== -->
					<div class="span6">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box green">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-globe"></i>导出CA/删除CA
								</div>
							</div>
							<div class="portlet-body form">
								<!-- BEGIN FORM-->
								<div class="portlet-body form">
									<!-- BEGIN FORM-->
									<form name="expCaForm" action="" class="form-horizontal">
									 <input type='hidden' name='caid' id='caid' value='${cainfo.CAId}'>
										<div class="control-group">
											<label class="control-label">主题（DN）</label>
											<div class="controls">
												<input type="text" name="caDnxxx" class="colorpicker-default m-wrap" value="" />
											</div>
										</div>
										<div class="form-actions">
											<button class="btn blue btn-primary" onclick="importP12Form.submit()">
												<i class="icon-ok"></i>导出
											</button>
											<button class="btn " onclick="removeCA()">
												删除
											</button>
										</div>
									</form>
									<!-- END FORM-->
								</div>
							</div>
							<!-- END EXAMPLE TABLE PORTLET-->
						</div>
						<!-- ===END===CA证书信息===== -->
					</div>
					<!-- END PAGE CONTENT===搜索条件===-->
					<!-- BEGIN PAGE CONTENT===搜索结果===-->
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
	function removeCA(id) {
		if(confirm("确定要删除此CA?")){
			expCaForm.action="removeCA.html";
			expCaForm.submit();
		}
	}	
	function exportCA(id) {
		expCaForm.action="exportCA.html";
		expCaForm.submit();
		
	}	
</script>
<!-- END BODY -->
<%@include file="../common/corePlugin.jsp"%>
</html>