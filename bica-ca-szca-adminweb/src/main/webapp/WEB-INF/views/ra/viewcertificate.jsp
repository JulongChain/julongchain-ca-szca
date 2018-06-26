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
<link href="${pageContext.request.contextPath}/media/css/loadingLayer.css" rel="stylesheet" type="text/css" />
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
		<jsp:param name="menuLevel1" value="ra" />
		<jsp:param name="menuLevel2" value="viewcertificate" />
		<jsp:param name="menuLevel3" value="" />
		</jsp:include>
		<!-- END SIDEBAR -->
		 
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
 							查看证书
 						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								<a href="index.html">首页</a> 

								<span class="icon-angle-right"></span>

							</li>

							<li>

								<a href="#">RA功能</a>

								<span class="icon-angle-right"></span>

							</li>

							<li><a href="#">查看证书</a></li>

						</ul>

					</div>

				</div>

				<!-- END PAGE HEADER-->

				<!-- BEGIN PAGE CONTENT-->

				<div class="row-fluid">

					<div class="span12">

						<!-- BEGIN SAMPLE FORM PORTLET-->   

						<div class="portlet box blue">

							<div class="portlet-title">

								<div class="caption"><i class="icon-reorder"></i>查看证书</div>

 
							</div>

							<div class="portlet-body form">

								<!-- BEGIN FORM-->
                                 
   										
								 <form action="#" class="form-horizontal">
								  <c:choose>
  										<c:when test="${empty viewcertificateVo}">
  										<div class="control-group" style="height: 50px;"> 
   										  ${msg}
  										  </div>
  										</c:when>
  										<c:otherwise> 
 								 <input type="hidden" name="username" id="username" value="${viewcertificateVo.usernameVal}" />
 								   <div class="control-group">
										<label class="control-label">${viewcertificateVo.username}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.usernameVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
									
                                   
                                   <div class="control-group">
										<label class="control-label">${viewcertificateVo.certificatenr}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.certificatenrVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>

						         <div class="control-group">
										<label class="control-label">${viewcertificateVo.cert_typeversion}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.cert_typeversionVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
 									
 									  <div class="control-group">
										<label class="control-label">${viewcertificateVo.cert_serialnumber}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.cert_serialnumberVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
 									
 									 <div class="control-group">
										<label class="control-label">${viewcertificateVo.cert_issuerdn}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.cert_issuerdnVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
 									
   									 <div class="control-group">
										<label class="control-label">${viewcertificateVo.cert_validfrom}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.cert_validfromVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
  									
  									 <div class="control-group">
										<label class="control-label">${viewcertificateVo.cert_validto}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.cert_validtoVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
  									
  									 <div class="control-group">
										<label class="control-label">${viewcertificateVo.cert_subjectdn}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.cert_subjectdnVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
  									
  									
 									  <c:if test="${type}"> 
 									  
 									  
  										 <div class="control-group">
										<label class="control-label">${viewcertificateVo.ext_abbr_subjectaltname}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.ext_abbr_subjectaltnameVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
 									
 									
 									
  										 <div class="control-group">
										<label class="control-label">${viewcertificateVo.ext_abbr_subjectdirattrs}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.ext_abbr_subjectdirattrsVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
 									
 								
 									  
 									  </c:if>
 									
 										
  										 <div class="control-group">
										<label class="control-label">${viewcertificateVo.cert_publickey}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.cert_publickeyVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
  									
  										
  										 <div class="control-group">
										<label class="control-label">${viewcertificateVo.ext_abbr_basicconstraints}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.ext_abbr_basicconstraintsVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
  									
  									  <c:if test="${type}"> 
  									  
   									  	 <div class="control-group">
										<label class="control-label">${viewcertificateVo.ext_abbr_keyusage}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.ext_abbr_keyusageVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
   									  </c:if>
  										  	 <div class="control-group">
										<label class="control-label">${viewcertificateVo.ext_abbr_extendedkeyusage}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.ext_abbr_extendedkeyusageVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
  									
  									  	 <div class="control-group">
										<label class="control-label">${viewcertificateVo.ext_abbr_nameconstraints}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.ext_abbr_nameconstraintsVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
  									
  									  	 <div class="control-group">
										<label class="control-label">${viewcertificateVo.ext_abbr_qcstatements}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.ext_abbr_qcstatementsVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
  									
  									
  									
  									 	 <div class="control-group">
										<label class="control-label">${viewcertificateVo.ext_certificate_transparency_scts}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.ext_certificate_transparency_sctsVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
  									
  									
  									 <div class="control-group">
										<label class="control-label">${viewcertificateVo.signaturealgorithm}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.signaturealgorithmVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
  									
  									
  									
  									 <div class="control-group">
										<label class="control-label">${viewcertificateVo.fingerprint_sha256}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.fingerprint_sha256Val}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
  										 <div class="control-group">
										<label class="control-label">${viewcertificateVo.fingerprint_sha1}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${viewcertificateVo.fingerprint_sha1Val}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
  									
  									 <div class="control-group">
										<label class="control-label">${viewcertificateVo.revoked}</label>
 										<div class="controls">
 												<input type="text" name="textfieldusername" value="${viewcertificateVo.revokedVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
  										</div>
 									</div>
 									
 									 <c:if test="${isRevoked}"> 
 										  <div class="control-group">
										<label class="control-label">${viewcertificateVo.revocationdate}</label>
 										<div class="controls">
 												<input type="text" name="textfieldusername" value="${viewcertificateVo.revocationdateVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
  										</div>
 								     	</div>
 								     	
  								     	 <div class="control-group">
										<label class="control-label">${viewcertificateVo.revocationreasons}</label>
 										<div class="controls">
 												<input type="text" name="textfieldusername" value="${viewcertificateVo.revocationreasonsVal}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
  										</div>
 									       </div>
 										 </c:if>
   							       <c:if test="${not empty revokeMap}"> 
   											<div class="control-group">
										<label class="control-label"></label>
										<div class="controls">
										  <select name="selectrevocationreason" id="selectrevocationreason">  
										    <c:forEach var="revoke" items="${revokeMap}" varStatus="status">
 										    <option value='${revoke.value}'>${revoke.key}</option>
										      </c:forEach>
										  </select>
										  	<button type="button" class="btn blue" onclick="revoke()">${viewcertificateVo.revoke}</button>
  										</div>
 									</div>
									 
									 </c:if>
								 
								 <hr/>
								 </c:otherwise>
  								 </c:choose>
 							 	<div class="form-actions">
 										<button type="button" class="btn blue" onclick="javascript:history.back(-1);">返回</button>
   									</div>
  								</form>

								<!-- END FORM-->       

							</div>

						</div>

						<!-- END SAMPLE FORM PORTLET-->

					</div>
					</div>
					

				</div>

 
				<!-- END PAGE CONTENT-->         

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
<script src="${pageContext.request.contextPath}/media/js/jquery-1.10.1.min.js"></script>
<script src="${pageContext.request.contextPath}/media/js/loadingLayer.js"></script>
<%@include file="../common/corePlugin.jsp"%>
  <script type="text/javascript">
function revoke(){
	 showMask();
 	  $.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/ra/revoke.html",
			async:true,
			dataType:'json',
			data:{username:$("#username").val(),reason:$("#selectrevocationreason").val()},
 			success : function(data) {
				if(data.success){
					confirmFn("操作成功",function(){window.location.reload();});
 				}else{
 					alertFn(data.msg);
 				}
				hideMask();
			},
			error : function(responseData) {
				hideMask();
			}
		});
	
}
  
  </script>
</html>