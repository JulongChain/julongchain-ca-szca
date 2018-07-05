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
			<jsp:param name="menuLevel2" value="cryptoToken" />
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
							密钥库 <small></small>
						</h3>
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="index.html">CA功能</a> <i class="icon-angle-right"></i></li>
							<li><a href="#">密钥库详情</a></li>
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
				<div class="row-fluid">
					<div class="span8">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box green">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-globe"></i>
									<c:if test="${empty cryptoTokenGuiInfo}">创建密钥库容器</c:if>
									<c:if test="${!empty cryptoTokenGuiInfo}">修改密钥库容器</c:if>
								</div>
							</div>
							<div class="portlet-body form">
								<!-- BEGIN FORM-->
								<form name="cryptForm" action="addCryptoToken.html" class="form-horizontal">
									<c:if test="${!empty cryptoTokenGuiInfo}">
										<div class="control-group">
											<label class="control-label">序号 </label>
											<div class="controls">${cryptoTokenGuiInfo.cryptoTokenId}</div>
										</div>
									</c:if>
									<div class="control-group">
										<label class="control-label">名称 </label>
										<div class="controls">
											<input type="text" name="name" value="${cryptoTokenGuiInfo.tokenName}" size="45" title="Identifier, string formated" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">类型</label>
										<div class="controls">
											<select id="type" name="type" size="1">
												<option value="SoftCryptoToken" selected="selected">SOFT</option>
											</select>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">授权码</label>
										<div class="controls">
											<input id="authCode" type="password" name="authCode" autocomplete="off" value="" size="20" title="Authentication Code" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">重复授权码</label>
										<div class="controls">
											<input id="authCode2" type="password" name="authCode2" autocomplete="off" value="" size="20" title="Authentication Code" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">自动激活</label>
										<div class="controls">
											<input id="autoActivate" type="checkbox" name="autoActivate" checked="${cryptoTokenGuiInfo.autoActivation}" /><label class="help-inline" for="autoActivate">使用</label>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">允许导出密钥</label>
										<div class="controls">
											<input id="expKey" type="checkbox" name="expKey" /> <label class="help-inline" for="expKey">允许</label>
										</div>
									</div>
									<div class="form-actions">
										<button type="submit" class="btn blue">
											<i class="icon-ok"></i> Save
										</button>
										<button type="reset" class="btn" onclick="history.go(-1);">Cancel</button>
									</div>
								</form>
								<!-- END FORM-->
							</div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
				</div>
				<!-- END PAGE CONTENT===搜索条件===-->
				<!-- BEGIN PAGE CONTENT===创建密钥对===-->
				<c:if test="${!empty cryptoTokenGuiInfo }">
					<div class="row-fluid">
						<div class="span8">
							<!-- BEGIN EXAMPLE TABLE PORTLET-->
							<div class="portlet box green">
								<div class="portlet-title">
									<div class="caption">
										<i class="icon-globe"></i> 创建密钥对
									</div>
								</div>
								<div class="portlet-body form">
									<!-- BEGIN FORM-->
									<form name="cryptForm" action="genNewKeyPair.html" class="form-horizontal">
										<input type="hidden" name="cryptoTokenId" value="${cryptoTokenGuiInfo.cryptoTokenId}" />
										<div class="control-group">
											<label class="control-label">别名 </label>
											<div class="controls">
												<input type="text" name="alias" value="" size="45" title="Identifier, string formated" />
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">算法</label>
											<div class="controls">
												<select id="algorithm" name="algorithm" size="1">
													<option value="RSA1024">RSA 1024</option>
													<option value="RSA2048" selected="selected">RSA 2048</option>
													<option value="RSA4096">RSA 4096</option>
													<option value="sm2p256v1">SM2</option>
												</select>
											</div>
										</div>
										<div class="form-actions">
											<button type="submit" class="btn blue">
												<i class="icon-ok"></i> 生成
											</button>
											<button type="reset" class="btn" onclick="history.go(-1);">取消</button>
										</div>
									</form>
									<!-- END FORM-->
								</div>
							</div>
							<!-- END EXAMPLE TABLE PORTLET-->
						</div>
					</div>
				</c:if>
				<!-- END PAGE CONTENT===创建密钥对===-->
				<!-- BEGIN PAGE CONTENT===密钥对===-->
				<c:if test="${!empty kpList }">
					<div class="row-fluid">
						<div class="span8">
							<!-- BEGIN EXAMPLE TABLE PORTLET-->
							<div class="portlet box green">
								<div class="portlet-title">
									<div class="caption">
										<i class="icon-globe"></i>包含的密钥对
									</div>
								</div>
								<div class="portlet-body">
									<div class="span12">
										<div class="dataTables_paginate paging_bootstrap pagination">
											<form name="listForm" action="#">
												<input type="hidden" name="currentPage" id="currentPage" value="${currentPage}" />
											</form>
										</div>
									</div>
									<table class="table table-striped table-bordered table-hover table-full-width ">
										<tr>
											<th>别名</th>
											<th>算法</th>
											<th class="hidden-480">长度</th>
										 
											<th class="hidden-480">SubjectKeyID</th>
											<%--	<th class="hidden-480">创建时间</th> --%>
											<th class="hidden-480"></th>
										</tr>
										<c:forEach items="${kpList }" var="kp">
											<tr>
												<td>${kp.alias}</td>
												<td>${kp.keyAlgorithm}</td>
												<td>${kp.keySpecification}</td>
										 
												<td>${kp.subjectKeyID}</td>
												<%--	<td>${szca:formatDate(cto.crtTime)}</td> --%>
												<td>
												<a id="open_form_modal3" class="btn hide" href="#form_modal3" data-toggle="modal"></a>
												<input type="button" name="testBtn" value="测试" onclick="testCryptoToken(${cryptoTokenGuiInfo.cryptoTokenId},'${kp.alias}')" /> <input type="button" name="removeBtn" value="删除"
													onclick="removeCryptoToken(${cryptoTokenGuiInfo.cryptoTokenId},'${kp.alias}')" /> <input type="button" name="downloadBtn" value="下载公钥"
													onclick="downloadCryptoToken(${cryptoTokenGuiInfo.cryptoTokenId},'${kp.alias}')" /></td>
											</tr>
										</c:forEach>
									</table>
								</div>
								<!-- ==modal panel START== -->
								<div id="form_modal3" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
										<h3 id="myModalLabel3">测试结果</h3>
									</div>
									<div class="modal-body">
										<%-- <form action="#" class="form-horizontal"> --%>
											<div class="control-group">
												<label class="control-label"  id="testResultMsg">hello!!</label>
												<%--
												<div class="controls">
													<input type="text" class="colorpicker-default m-wrap" value="#8fff00" />
												</div>
												 --%>
											</div>
											 
										<%-- </form> --%>
									</div>
									<div class="modal-footer">
										<button class="btn green btn-primary" data-dismiss="modal" aria-hidden="true">关闭</button> 
									</div>
								</div>
								<!-- ==modal panel END== -->
								<!-- END FORM-->
							</div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
				</c:if>
				<!-- END PAGE CONTENT===密钥对===-->
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
function removeCryptoToken(cryptoTokenId, alias){
	window.location.href ="${pageContext.request.contextPath}/cryptoToken/removeCryptoToken.html?cryptoTokenId="+cryptoTokenId+"&alias="+alias;
}
function downloadCryptoToken(cryptoTokenId, alias){
	window.location.href ="${pageContext.request.contextPath}/cryptoToken/downloadCryptoToken.html?cryptoTokenId="+cryptoTokenId+"&alias="+alias;
}
function testCryptoToken(cryptoTokenId, alias){
	 $.ajax({
					type : "POST",
					dataType : "json",
					url : "${pageContext.request.contextPath}/cryptoToken/testCryptoToken.html?cryptoTokenId="+cryptoTokenId+"&alias="+alias,
					success : function(resp) {	 
						//alert(resp.message);						 
						$("#testResultMsg").html(resp.message);	
						$("#open_form_modal3").click();
					},
					error : function(data) {					 
						//btnobj.setAttribute("disabled", false);
					}
				});
}
</script>
<!-- END BODY -->
<%@include file="../common/corePlugin.jsp"%>
</html>