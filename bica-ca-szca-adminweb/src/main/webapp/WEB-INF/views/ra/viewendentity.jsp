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
		<jsp:param name="menuLevel1" value="ra" />
		<jsp:param name="menuLevel2" value="viewendentity" />
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
 							查看终端实体
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

							<li><a href="#">查看终端实体</a></li>

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

								<div class="caption"><i class="icon-reorder"></i>查看终端实体</div>

 
							</div>

							<div class="portlet-body form">

								<!-- BEGIN FORM-->

								<form action="#" class="form-horizontal">
 									 
 								  <c:choose>
  							     <c:when test="${!isSuccess}">
  										<div class="control-group" style="height: 50px;"> 
   										  ${msg}
  										  </div>
  										</c:when>
  								<c:otherwise> 
								   <div class="control-group">
										<label class="control-label">${USERNAME_TITLE}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${USERNAME}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
									
                                   
                                   <div class="control-group">
										<label class="control-label">${STATUST_ITLE}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${STATUST}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>

						         <div class="control-group">
										<label class="control-label">${CREATED_TITLE}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${CREATED}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
 									
 									  <div class="control-group">
										<label class="control-label">${MODIFIED_TITLE}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${MODIFIED}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
 									
 									 <div class="control-group">
										<label class="control-label">${ENDENTITYPROFILE_TITLE}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${ENDENTITYPROFILE}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
 									
 									
 									 <c:if test="${USE_CLEARTEXTPASSWORD}"> 
 									 <div class="control-group">
										<label class="control-label">${USEINBATCH_ABBR_TITLE}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${CLEARTEXTPASSWORD}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
 									</c:if>
 									
 									<c:if test="${USE_EMAIL}"> 
 									 <div class="control-group">
										<label class="control-label">${EMAIL_TITLE}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${EMAIL}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
 									</c:if>
 									<hr/>
 									
 											<div class="control-group">
										<label class="control-label">${CERT_SUBJECTDN_TITLE} </label>
										<div class="controls">
										</div>
 									</div>
 							          <c:forEach var="subject" items="${subjectMap}" varStatus="status">
  											<div class="control-group">
										<label class="control-label">${subject.key}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${subject.value}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
									   </c:forEach>
									   
									  <c:if test="${subjectAltNameFieldOrderLength>0||subjectDirAttrFieldOrderLength>0}"> 
									  		<div class="control-group">
										<label class="control-label">${OTHERSUBJECTATTR_TITLE} </label>
										<div class="controls">
										</div>
 									</div>
									   </c:if>
									   
									   
									      
									  <c:if test="${subjectAltNameFieldOrderLength>0}"> 
									  		<div class="control-group">
										<label class="control-label">${EXT_ABBR_SUBJECTALTNAME_TITLE} </label>
										<div class="controls">
										</div>
 									</div>
									 </c:if>
									   
									  <c:forEach var="subjectAltNameFieldsInOrder" items="${subjectAltNameFieldsInOrderMap}" varStatus="status">
  											<div class="control-group">
										<label class="control-label">${subjectAltNameFieldsInOrder.key}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${subjectAltNameFieldsInOrder.value}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
									 </c:forEach>
									 
									  <c:if test="${subjectDirAttrFieldOrderLength>0}"> 
									  		<div class="control-group">
										<label class="control-label">${EXT_ABBR_SUBJECTDIRATTRS_TITLE} </label>
										<div class="controls">
										</div>
 									</div>
									 </c:if>
 									  <c:forEach var="subjectDirAttrFieldOrder" items="${subjectDirAttrFieldOrderMap}" varStatus="status">
  											<div class="control-group">
										<label class="control-label">${subjectDirAttrFieldOrder.key}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${subjectDirAttrFieldOrder.value}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
									 </c:forEach>
									 
									 
									 
								    <div class="control-group">
										<label class="control-label">${MAINCERTIFICATEDATA} </label>
										<div class="controls">
										</div>
 									</div>
									 
									 
  									 <div class="control-group">
										<label class="control-label">${CERTIFICATEPROFILE_TITLE}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${CERTIFICATEPROFILE}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
 									 
									 			 <div class="control-group">
										<label class="control-label">${CA_TITLE}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${CA}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
									 
									 
									 
									 			 <div class="control-group">
										<label class="control-label">${TOKEN_TITLE}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${TOKEN}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
 									
 										<c:if test="${issueHardwareTokens}"> 
 									 <div class="control-group">
										<label class="control-label">${HARDTOKENISSUER_TITLE}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${HARDTOKENISSUER}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
 									</c:if>
  									<hr/>
   								 <c:if test="${enableKeyRecovery}"> 
 									 <div class="control-group">
										<label class="control-label">${KEYRECOVERABLE_TITLE}</label>
										<div class="controls">
												<input type="text" name="textfieldusername" value="${KEYRECOVERABLE}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
 										</div>
 									</div>
 									</c:if>
  									<hr/>
										<div class="form-actions">
 										<button type="button" class="btn blue" onclick="javascript:history.back(-1);">返回</button>
   									</div>
   										</c:otherwise>
  								 </c:choose>
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
<%@include file="../common/corePlugin.jsp"%>
</html>