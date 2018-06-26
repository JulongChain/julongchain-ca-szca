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
<style>
.bclass {
color: red;
}
</style>
<script src="${pageContext.request.contextPath}/media/js/jquery-1.10.1.min.js"></script>

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
		<jsp:param name="menuLevel2" value="addendentity" />
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

					<p></p>

				</div>

			</div>

			<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->

			<!-- BEGIN PAGE CONTAINER-->

			<div class="container-fluid">

				<!-- BEGIN PAGE HEADER-->   

				<div class="row-fluid">

					<div class="span12">
 
 						<h3 class="page-title">
 							添加终端实体
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

							<li><a href="#">添加终端实体</a></li>

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

								<div class="caption"><i class="icon-reorder"></i>添加终端实体</div>

 
							</div>

							<div class="portlet-body form">

								<!-- BEGIN FORM-->

								<form action="${pageContext.request.contextPath}/ra/saveEndentity.html"  method="post" id="formId"  class="form-horizontal">
								<c:choose>
  							     <c:when test="${!isSuccess}">
  										<div class="control-group" style="height: 50px;"> 
   										  ${msg}
  										  </div>
  										</c:when>
  								<c:otherwise> 
 								  <input type="hidden" name="username" id="username" value="" />
 								  <input type="hidden" name="subjectDN"  id="subjectDN" value="" />
 									<input type="hidden" name="caid" id="caid" value="" />
 									<input type="hidden" name="subjectAltName" id="subjectAltName" value="" />
 									<input type="hidden" name="subjectEmail" id="subjectEmail" value="" />
 									<input type="hidden" name="password" id="password" value="" />
 									<input type="hidden" name="cardNumber" id="cardNumber" value="" />
 									<input type="hidden" name="status" id="status" value="10" />
 									<input type="hidden" name="endentityprofileid" id="endentityprofileid" value="" />
 									<input type="hidden" name="certificateprofileid" id="certificateprofileid" value="" />
 									<input type="hidden" name="timecreated" id="timecreated" value="" />
 									<input type="hidden" name="timemodified" id="timemodified" value="" />
 									<input type="hidden" name="tokentype" id="tokentype" value="" /> 
  								    <input type="hidden" name="hardtokenissuerid" id="hardtokenissuerid" value="0" /> 
  								     <input type="hidden" name="cleartextpwd" id="cleartextpwd" value="" /> 
  								     <input type="hidden" name="keyrecoverable" id="keyrecoverable" value="" /> 
  								     <input type="hidden" name="maxLoginAttempts" id="maxLoginAttempts" value="" /> 
  								     <input type="hidden" name="remainingLoginAttempts" id="remainingLoginAttempts" value="" /> 
  								     
  								    
  								    
 								    <div class="control-group">
										<label class="control-label">模版名称</label>
										<div class="controls">
											<select class="span6 m-wrap" name="selectendentityprofile" id="selectendentityprofile" data-placeholder="Choose a Category" onchange="changeProfile(this)" tabindex="1">
											    <c:forEach var="profile" items="${profileNames}" varStatus="status">
												<option value="${profile.value}"  ${profile.value eq profileid?"selected='selected'":""}  >${profile.key}</option>
												  </c:forEach>
											</select>
										</div>

									</div>
									<hr />
                                     <c:if test="${useNameFields}"> 
  								       	<div class="control-group">
 										<label class="control-label">用户名</label>
 										<div class="controls">
 										<c:if test="${!isModifyableUsername}"> 
  											<select class="span6 m-wrap" name="selectusername" id="selectusername" data-placeholder="Choose a Category" tabindex="1">
											    <c:forEach var="nameoption" items="${nameoptions}" varStatus="status">
												<option value="${nameoption}">${nameoption}</option>
												  </c:forEach>
											</select>
  										</c:if>
 										<c:if test="${isModifyableUsername}"> 
  											<input type="text" name="textfieldusername" id="textfieldusername" value="${nameFieldsValue}" class="span6 m-wrap popovers" data-trigger="hover" data-content="" data-original-title="" />
  										</c:if>
  										${isRequiredUsername?"<span class='bclass'>*</span>":""}
   										</div>
  									</div>
                                     </c:if>    
                                     
                                     
                                        <c:if test="${usePasswordFields}"> 
  								       	<div class="control-group">
 										<label class="control-label">密码</label>
 										<div class="controls">
 										<c:if test="${!isModifyablePassword}"> 
  											<select class="span6 m-wrap" name="selectpassword" id="selectpassword" data-placeholder="Choose a Category" tabindex="1">
  											<c:if test="${ not empty passwordFieldsValue}"> 
 												<option value="${passwordFieldsValue}">${passwordFieldsValue}</option>
 												</c:if>
 											</select>
  										</c:if>
 										<c:if test="${isModifyablePassword}"> 
  											<input type="password" name="textfieldpassword" id="textfieldpassword" value="${passwordFieldsValue}" class="span6 m-wrap popovers" data-trigger="hover" data-content="" data-original-title="" />
  										</c:if>
  										${isRequiredPassword?"<b class='bclass'> *</b>":""}
   										</div>
  									</div>
                                     </c:if>   
                                     
                                        <c:if test="${usePasswordFields}"> 
  								       	<div class="control-group">
 										<label class="control-label">确认密码</label>
 										<div class="controls">
 										<c:if test="${!isModifyablePassword}"> 
  											<select class="span6 m-wrap" name="selectconfirmpassword" id="selectconfirmpassword" data-placeholder="Choose a Category" tabindex="1">
  											<c:if test="${ not empty passwordFieldsValue}"> 
 												<option value="${passwordFieldsValue}">${passwordFieldsValue}</option>
 												</c:if>
 											</select>
  										</c:if>
 										<c:if test="${isModifyablePassword}"> 
  											<input type="password" name="textfieldconfirmpassword" id="textfieldconfirmpassword" value="${passwordFieldsValue}" class="span6 m-wrap popovers" data-trigger="hover" data-content="" data-original-title="" />
  										</c:if>
  										${isRequiredPassword?"<b class='bclass'> *</b>":""}
   										</div>
  									</div>
                                     </c:if>   
                                     
                                     
                                     
                                       <c:if test="${useMaxFailedLogins}"> 
  								       	<div class="control-group">
 										<label class="control-label">MAXFAILEDLOGINATTEMPTS</label>
 										<div class="controls">
 										<input type="radio" name="radiomaxfailedlogins" value="specified" ${maxLoginAttempts!=-1?"checked='checked'":""} <c:if test="${!isModifyMaxLoginAttempts}">  readonly="readonly"  </c:if>    onclick="maxFailedLoginsSpecified()"/>
   										<input type="text"  name="textfieldmaxfailedlogins" size="5" maxlength="255" tabindex=""    value='${maxLoginAttempts!=-1?maxLoginAttempts:""}'  ${maxLoginAttempts==-1?"disabled='disabled'":""}  <c:if test="${!isModifyMaxLoginAttempts}">  readonly="readonly"  </c:if> title="">
  										<input type="radio" name="radiomaxfailedlogins" value="unlimited" onclick="maxFailedLoginsUnlimited()" ${maxLoginAttempts==-1?"checked='checked'":""} <c:if test="${!isModifyMaxLoginAttempts}">  readonly="readonly"  </c:if>  id="radiomaxfailedloginsunlimited">
 									    <label for="radiomaxfailedloginsunlimited">UNLIMITED</label>
     								 </div>
  									</div>
                                     </c:if>   
                                     
    
                                    <c:if test="${useCleartextPasswordFields}"> 
  								       	<div class="control-group">
 										<label class="control-label">允许批量签署证书(密码以明文方式保存)</label>
 										<div class="controls">
 									   <input type="checkbox" name="checkboxcleartextpassword" id="checkboxcleartextpassword" value="true" tabindex="" ${cleartextPasswordValue eq "true"?"checked='checked'":""}    ${isRequiredCleartextPassword?"disabled='disabled'":""}  id="checkboxcleartextpassword" />
 										使用
      								    </div>
  									    </div>
                                        </c:if>   
             
             
     
           
                                <c:if test="${useEmailFields}"> 
  								       	<div class="control-group">
 										<label class="control-label">邮箱</label>
 										<div class="controls">
 									   <input type="text" name="textfieldemail" id="textfieldemail"  class="span3 m-wrap popovers" data-trigger="hover"  maxlength="255" tabindex="" value=""> @
  										<c:if test="${!isModifyableEmail}"> 
  								      <c:choose>
  										<c:when test="${empty emailoptions }">
                                     	    <input type="hidden" name="selectemaildomain"  class="span3 m-wrap popovers" data-trigger="hover"   value="" />
                            	         </c:when>
                            	         <c:when test="${fn:length(emailoptions)==1}">
                                     <input type="hidden" name="selectemaildomain>"  class="span3 m-wrap popovers" data-trigger="hover"   value="${emailoptions[0]}" />
                                     <strong>${emailoptions[0]}</strong>
                                     </c:when>
                           	        <c:otherwise> 
                            	        	<select class="span6 m-wrap" name="selectemaildomain" id="selectemaildomain" data-placeholder="Choose a Category" tabindex="1">
											    <c:forEach var="emailoption" items="${emailoptions}" varStatus="status">
												<option value="${nameoption}">${nameoption}</option>
												  </c:forEach>
											</select>
                             	   </c:otherwise>
                                       </c:choose>
   										</c:if>
 										<c:if test="${isModifyableEmail}"> 
 										   <input type="text" name="textfieldemaildomain"   maxlength="255" tabindex=""  value="${emailValue}" class="span3 m-wrap popovers" data-trigger="hover" data-content="" data-original-title="" title=""/>
   										</c:if>
  										${isRequiredEmail?"<b class='bclass'> *</b>":""}
   										</div>
  									</div>
                                     </c:if>    
           <hr />
                      <!-- ---------- Subject DN  BEGIN------------------- -->
                                                 
									<div class="control-group">
										<label class="control-label">主题(DN)字段 </label>
										<div class="controls">
										</div>
 									</div>
                               <c:forEach var="subjectDn" items="${subjectDnList}" varStatus="status">
   								       	<div class="control-group">
 										<label class="control-label">${subjectDn.title}</label>
 										<div class="controls">
   							       <c:if test="${!subjectDn.fieldOfType}"> 
    							       <c:if test="${!subjectDn.modifyable}">
   							         <c:choose>
  										<c:when test="${empty subjectDn.options}">
                                     	    <input type="hidden" name="selectsubjectdn${status.index}"  class="span6 m-wrap popovers" data-trigger="hover" value="" />
                            	         </c:when>
                            	         <c:when test="${fn:length(subjectDn.options)==1}">
                                     <input type="hidden" name="selectsubjectdn${status.index}" value="${subjectDn.options[0]}" />
                                     <strong>${subjectDn.options[0]}</strong>
                                     </c:when>
                           	        <c:otherwise> 
                            	        	<select class="span6 m-wrap" name="selectsubjectdn${status.index}" data-placeholder="Choose a Category" tabindex="1">
											    <c:forEach var="sunbjectDnOption" items="${subjectDn.options}" varStatus="statusop">
												<option value="${sunbjectDnOption}">${sunbjectDnOption}</option>
												  </c:forEach>
											</select>
                             	   </c:otherwise>
                                       </c:choose>
   							       	</c:if> 
  								       <c:if test="${subjectDn.modifyable}">
  							       <input type="text"  name="selectsubjectdn${status.index}" size="40" maxlength="255" tabindex=""  class="span6 m-wrap popovers" data-trigger="hover"  value="${subjectDn.value}"
    							        <c:if test="${not empty subjectDn.regex}">pattern="${subjectDn.regex}" </c:if>
  							       title="Must match format specified in profile. / Technical detail - the regex is ${subjectDn.regex}"/>
  									  	</c:if>
  									 </c:if> 	
  									 <c:if test="${subjectDn.fieldOfType}">
  									 
  									   <input type="checkbox" name="textfieldsubjectdn${status.index}" value="true"  class="span6 m-wrap popovers" data-trigger="hover" tabindex="" 
  									    <c:if test="${subjectDn.required}"> 	
  									    checked="checked" disabled="disabled" 
  									    </c:if> id="checkboxsubjectdn${status.index}" />
    									   </c:if> 
   										${subjectDn.required?"<b class='bclass'> *</b>":""}
   										</div>
  									</div>
                                 </c:forEach>
 									<hr />						    
  					          <!-- ---------- Other subject attributes -------------------- -->
  					          	 <c:if test="${numberofsubjectaltnamefields>0||numberofsubjectdirattrfields>0}">
  					         	<div class="control-group">
										<label class="control-label">${OTHERSUBJECTATTR}</label>
										<div class="controls">
										</div>
 									</div>
  				             	</c:if>
  					          <c:if test="${numberofsubjectaltnamefields>0}">
  					         	<div class="control-group">
										<label class="control-label">${SUBJECTALTNAME}</label>
										<div class="controls">
										</div>
 									</div>
  				             	</c:if>
   					           <c:forEach var="otherSubjectVo" items="${otherSubjectVoList}" varStatus="status">
   					            <c:if test="${otherSubjectVo.fieldImplemented}"> 
    								       	<div class="control-group">
 										<label class="control-label">${otherSubjectVo.title}</label>
 										<div class="controls">
 										
   							 <c:if test="${otherSubjectVo.fieldOfTypeRFC822}"> 
   							         
    						   <c:if test="${otherSubjectVo.use}">
    							<input type="checkbox" name="checkboxsubjectaltname" value="true"  class="span6 m-wrap popovers" data-trigger="hover" tabindex=""
     							 <c:if test="${otherSubjectVo.required}"> 	
  									    checked="checked" disabled="disabled" 
  									    </c:if> id="checkboxsubjectaltname${status.index}" />
     							 
   							    </c:if> 
   							     <c:if test="${!otherSubjectVo.use}">
   							         <c:if test="${otherSubjectVo.rfc822NameStringc}">
   							         <input type="text" name="textfieldrfc822name${status.index}" size="20" maxlength="255"  class="span6 m-wrap popovers" data-trigger="hover" tabindex=">"
					            	value='${otherSubjectVo.rfc822NameArray[0]}' /> @
   							         </c:if>
   							     
   							     
   							       <c:if test="${otherSubjectVo.modifyable}">
 					            		<input type="text" name="textfieldsubjectaltname${status.index}" size="15" maxlength="255" tabindex=""  class="span6 m-wrap popovers" data-trigger="hover" title="" value='${otherSubjectVo.rfc822NameArray[1]}'  />
   							         </c:if>
   							     <c:if test="${!otherSubjectVo.modifyable}">  
   							     	  <c:choose>
  										<c:when test="${empty otherSubjectVo.rfc822NameOptions||fn:length(otherSubjectVo.rfc822NameOptions)<=0}">
                                     	    <input type="hidden" name="selectsubjectaltname${status.index}"  class="span6 m-wrap popovers" data-trigger="hover" value="" />
                             	         </c:when>
                            	         <c:when test="${fn:length(otherSubjectVo.rfc822NameOptions)==1}">
                                     <input type="hidden" name="selectsubjectaltname${status.index}"  class="span6 m-wrap popovers" data-trigger="hover" value="${otherSubjectVo.rfc822NameOptions[0]}" />
                                     <strong>${otherSubjectVo.rfc822NameOptions[0]}</strong>
                                     </c:when>
                           	        <c:otherwise> 
                            	        	<select class="span6 m-wrap" name="selectsubjectaltname${status.index}" data-placeholder="Choose a Category" tabindex="1">
											    <c:forEach var="rfc822NameOption" items="${otherSubjectVo.rfc822NameOptions}" varStatus="statusop">
												<option value="${rfc822NameOption}">${rfc822NameOption}</option>
												  </c:forEach>
											</select>
                             	   </c:otherwise>
                                       </c:choose>
   							     
   							      </c:if>
   							     
   							       </c:if>
  								     
  									 </c:if> 	
  									  
  									  <c:if test="${!otherSubjectVo.fieldOfTypeRFC822}"> 
  									   <c:choose>
  									  <c:when test="${!otherSubjectVo.modifyable}"> 
   									    <c:if test="${otherSubjectVo.fieldOfTypeUPN}">
  									    <input type="text" name="textfieldupnname" size="20" maxlength="255"  class="span6 m-wrap popovers" data-trigger="hover" tabindex="" /> @
  									     </c:if>
  									   <c:choose>
  										<c:when test="${empty otherSubjectVo.nonRFC822NAMEOptions||fn:length(otherSubjectVo.nonRFC822NAMEOptions)<=0}">
                                     	    <input type="hidden" name="selectsubjectaltname${status.index}" value=""  class="span6 m-wrap popovers" data-trigger="hover"/>
                             	         </c:when>
                            	        <c:otherwise> 
                           	         <c:choose>
                           	            <c:when test="${fn:length(otherSubjectVo.nonRFC822NAMEOptions)==1}">
                                     <input type="hidden" name="selectsubjectaltname${status.index}"  class="span6 m-wrap popovers" data-trigger="hover" value="${otherSubjectVo.nonRFC822NAMEOptions[0]}" />
                                     <strong>${otherSubjectVo.nonRFC822NAMEOptions[0]}</strong>
                                     </c:when>
                                       <c:otherwise>
                            	        	<select class="span6 m-wrap" name="selectsubjectaltname${status.index}" data-placeholder="Choose a Category" tabindex="1">
											    <c:forEach var="nonrfc822NameOption" items="${otherSubjectVo.nonRFC822NAMEOptions}" varStatus="statusop">
												<option value="${nonrfc822NameOption}">${nonrfc822NameOption}</option>
												  </c:forEach>
											</select>
                                            </c:otherwise>
											</c:choose>
                             	   </c:otherwise>
                                       </c:choose>
    						       </c:when>
     						     <c:otherwise>
   									 <c:if test="${otherSubjectVo.fieldOfTypeUPN}"> 
  									      	<input type="text" name="textfieldupnname${status.index}" size="20" maxlength="255"  class="span3 m-wrap popovers" data-trigger="hover" tabindex="" /> @
					                      	<input type="text" name="textfieldsubjectaltname${status.index}" size="15" maxlength="255"  class="span3 m-wrap popovers" data-trigger="hover" tabindex=""
					                      	 value='${otherSubjectVo.value}' title=""/>
   									      </c:if>
  									      <c:if test="${!otherSubjectVo.fieldOfTypeUPN}"> 
  									      
  									       <input type="text" name="textfieldsubjectaltname${status.index}" size="40" maxlength="255"  class="span6 m-wrap popovers" data-trigger="hover" tabindex=""
                               value="${otherSubjectVo.value}"  <c:if test="${not empty otherSubjectVo.regex}">pattern="${otherSubjectVo.regex}" </c:if>
  							       title="Must match format specified in profile. / Technical detail - the regex is ${otherSubjectVo.regex}"/>
  									  	</c:if>
    									      </c:otherwise>
   									      </c:choose>
  									      </c:if>
    										${otherSubjectVo.required?"*":""}
   										</div>
  									</div>
  									</c:if>
                                 </c:forEach>
  					
 						 
 						 
 						     <c:if test="${numberofsubjectdirattrfields>0}">
  					         	<div class="control-group">
										<label class="control-label">${SUBJECTDIRATTRS}</label>
										<div class="controls">
										</div>
 									</div>
  				             	</c:if>
 						     <c:forEach var="otherSubjectVoDirAttr" items="${dirAttrList}" varStatus="status">
  						     	 	<div class="control-group">
 										<label class="control-label">${otherSubjectVoDirAttr.title}</label>
 										<div class="controls">
     							    <c:if test="${!otherSubjectVoDirAttr.modifyable}">
   							         <c:choose>
  										<c:when test="${empty otherSubjectVoDirAttr.nonRFC822NAMEOptions}">
                                     	    <input type="hidden" name="selectsubjectaldirattr${status.index}"  class="span6 m-wrap popovers" data-trigger="hover" value="" />
                            	         </c:when>
                            	         <c:when test="${fn:length(subjectDn.options)==1}">
                                     <input type="hidden" name="selectsubjectaldirattr${status.index}"  class="span6 m-wrap popovers" data-trigger="hover" value="${otherSubjectVoDirAttr.nonRFC822NAMEOptions[0]}" />
                                     <strong>${otherSubjectVoDirAttr.nonRFC822NAMEOptions[0]}</strong>
                                     </c:when>
                           	        <c:otherwise> 
                            	        	<select class="span6 m-wrap" name="selectsubjectaldirattr${status.index}" data-placeholder="Choose a Category" tabindex="1">
											    <c:forEach var="otherSubjectVoDirAttrOption" items="${otherSubjectVoDirAttr.nonRFC822NAMEOptions}" varStatus="statusop">
												<option value="${otherSubjectVoDirAttrOption}">${otherSubjectVoDirAttrOption}</option>
												  </c:forEach>
											</select>
                             	   </c:otherwise>
                                       </c:choose>
   							       	</c:if> 
  								       <c:if test="${otherSubjectVoDirAttr.modifyable}">
  								         <input type="text" name="textfieldsubjectdirattr${status.index}"  class="span6 m-wrap popovers" data-trigger="hover" size="20" maxlength="255" tabindex="" value='${otherSubjectVoDirAttr.value}'/>
   									  	</c:if>
    										${subjectDn.required?"*":""}
   										</div>
  									</div>
  						     </c:forEach>
  						     
  						     
  						     
  						      <!-- ---------- Main certificate data -------------------- -->
  						      <hr />
  						      <div class="control-group">
										<label class="control-label">${maincertificaText} </label>
										<div class="controls">
										</div>
 									</div>
 									
 									
 									
 									<div class="control-group">
 										<label class="control-label">${certificateprofileText}</label>
 										<div class="controls">
   											<select class="span6 m-wrap" name="selectcertificateprofile" id="selectcertificateprofile" onchange="fillCAField()" data-placeholder="Choose a Category" tabindex="1">
											    <c:forEach var="profileMap" items="${profileMap}" varStatus="status">
												<option value="${profileMap.key}">${profileMap.value}</option>
												  </c:forEach>
											</select>
   											<b class="bclass"> *</b>
   										</div>
  									</div>
 

	
 									<div class="control-group">
 										<label class="control-label">${maincertificaCAText}</label>
 										<div class="controls">
   											<select class="span6 m-wrap" name="selectca" id="selectca" data-placeholder="Choose a Category" tabindex="1">
  											</select>
    											<b class="bclass"> *</b>
   										</div>
  									</div>
  									
  									
  									<div class="control-group">
 										<label class="control-label">${tokenText}</label>
 										<div class="controls">
   											<select class="span6 m-wrap" name="selecttoken" id="selecttoken" data-placeholder="Choose a Category" 
   											  <c:if test="${usehardtokenissuers}">
   											  onchange="setAvailableHardTokenIssuers()"
   										      </c:if>
   											  <c:if test="${usekeyrecovery}">
   											  onchange="isKeyRecoveryPossible()"
   										      </c:if>
    									    tabindex="1">
											    <c:forEach var="tokens" items="${tokensMap}" varStatus="status">
												<option value="${tokens.value}"  ${tokens.value eq lastselectedtoken?"selected='selected'":""}   >${tokens.key}</option>
												  </c:forEach>
											</select>
    										<b class="bclass"> *</b>
   										</div>
  									</div>
 									
 									  <hr />
 									
 									
 									   <c:if test="${usekeyrecovery}">
 											<div class="control-group">
 										<label class="control-label">${KEYRECOVERABLE}</label>
 										<div class="controls">
   									         <input type="checkbox" name="checkboxkeyrecoverable" id="checkboxkeyrecoverable"  value="true" tabindex=""   ${keyecoverableValue?"checked='checked'":""}   ${isRequiredUsekeyrecovery?"disabled='disabled'":""}  />
     										</div>
  									</div>
 									</c:if>
 									
 									
 									
  
 
 									
 									
 		                      	<div class="form-actions">
 										<button type="button" class="btn blue" onclick="saveData()">提交</button>

										<button type="button" class="btn" onclick="resetform()">重置</button>                            
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

 
				<!-- END PAGE CONTENT-->         

			</div></div>
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
<script src="${pageContext.request.contextPath}/media/js/jquery.form.js"></script>
<script src="${pageContext.request.contextPath}/media/js/loadingLayer.js"></script>
<script src="${pageContext.request.contextPath}/media/js/ra-common.js"></script>
<script type="text/javascript">
  
$(function () { 
    initRaJs('${pageContext.request.contextPath}');
	fillCAField($("#selectcertificateprofile").val(),'${caAndCertificateprofileRel}');
});

 </script>
</html>