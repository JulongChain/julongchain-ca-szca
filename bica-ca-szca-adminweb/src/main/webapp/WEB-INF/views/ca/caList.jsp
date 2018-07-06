<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
						CA操作日志 <small></small>
					</h3>
					<ul class="breadcrumb">
						<li><i class="icon-home"></i> <a href="index.html">CA审核</a> <i class="icon-angle-right"></i></li>
						<li><a href="#">操作日志</a></li>
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
								<i class="icon-reorder"></i> <span class="hidden-480">已授权的CA列表</span>
							</div>
							<div></div>
						</div>
						<div class="portlet-body">
							<div class="span12">
								<div class="dataTables_paginate paging_bootstrap pagination">
									<form name="caForm" action="#">
										<a id="open_form_modal3" class="btn hide" href="#form_import_modal" data-toggle="modal"></a>
										<a id="open_form_modal4" class="btn hide" href="wizardCA.html"></a>
										<input type="button" name="importCACrypto" value="导入CA密钥"
											   onclick="javascript:open_form_modal3.click()">
										<input type="button" name="startWizardCA" value="创建CA向导"
											   onclick="javascript:window.location.href='wizardCA.html'">
									</form>
									<!-- ==modal panel for import START== -->
									<div id="form_import_modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="form_import_modal_Label" aria-hidden="true">
										<div class="modal-header">

										</div>
										<div class="modal-body">
											<form name="importP12Form" action="importP12.html" class="form-horizontal" enctype="multipart/form-data" method="post">
												<div class="control-group">
													<label class="control-label" id="testResultMsg">CA名称</label>
													<div class="controls">
														<input type="text" name="name" class="colorpicker-default m-wrap" value="" />
													</div>
												</div>
												<div class="control-group">
													<label class="control-label" id="testResultMsg">包含CA密钥的PKCS#12文件</label>
													<div class="controls">
														<input type="file" name="p12file" class="colorpicker-default m-wrap" value="" />
													</div>
												</div>
												<div class="control-group">
													<label class="control-label" id="testResultMsg">密钥库密码</label>
													<div class="controls">
														<input type="password" name="passwd" class="colorpicker-default m-wrap" value="" />
													</div>
												</div>
												<div class="control-group">
													<label class="control-label" id="testResultMsg">签名密钥别名</label>
													<div class="controls">
														<input type="text" name="signKey" class="colorpicker-default m-wrap" value="" />
													</div>
												</div>
												<div class="control-group">
													<label class="control-label" id="testResultMsg">加密密钥别名</label>
													<div class="controls">
														<input type="text" name="encKey" class="colorpicker-default m-wrap" value="" />
													</div>
												</div>
											</form>
										</div>
										<div class="modal-footer">
											<button class="btn blue btn-primary" data-dismiss="modal" aria-hidden="true" onclick="importP12Form.submit()">导入</button>
											<button class="btn " data-dismiss="modal" aria-hidden="true" onclick="importP12Form.reset()">取消</button>
										</div>
									</div>
									<!-- ==modal panel for import END== -->
								</div>
							</div>
							<div class="span12">
								<div class="dataTables_paginate paging_bootstrap pagination">
									<form name="listForm" action="#">
										<input type="hidden" name="currentPage" id="currentPage" value="${currentPage}" />
									</form>
									<szca:pager rowsCount="${totalRowsCount}" id="cryptPagerBar" currentPage="${currentPage}" rowsPerPage="${ rowsPerPage}" />
								</div>
							</div>
							<table class="table table-striped table-bordered table-hover table-full-width ">
								<tr>
									<th>ID</th>
									<th>名称</th>
									<th class="hidden-480">状态</th>
									<th class="hidden-480">主题</th>
									<th class="hidden-480">有效期</th>
									<%--	<th class="hidden-480">创建时间</th> --%>
									<th class="hidden-480"></th>
								</tr>
								<c:forEach items="${resultList }" var="ca">
									<tr>
										<td>${ca.CAId}</td>
										<td>${ca.name}</td>
										<td>
											<c:if test="${ca.status eq 1 }">正常</c:if>
											<c:if test="${ca.status eq 2 }">等待证书响应</c:if>
											<c:if test="${ca.status eq 3 }">已过期</c:if>
											<c:if test="${ca.status eq 4 }">已撤销</c:if>
											<c:if test="${ca.status eq 5 }">离线</c:if>
											<c:if test="${ca.status eq 6 }">外部CA</c:if>
											<c:if test="${ca.status eq 6 }">等待初始化</c:if>

										</td>
										<td>${ca.subjectDN}</td>
										<td>${szca:formatDate(ca.expireTime)}</td>
										<td><input type="button" name="updateBtn" value="详情" onclick="viewCA(${ca.CAId})"></td>
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

    function caWizard(){
        window.location.href ="${pageContext.request.contextPath}/ca/caWizard.html";
    }
    function viewCA(id){
        window.location.href ="${pageContext.request.contextPath}/ca/caInfo.html?caid="+id;
    }
</script>
<!-- END BODY -->
<%@include file="../common/corePlugin.jsp"%>
</html>