<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.szca.com/jsp/jstl/szca" prefix="szca"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
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
.form-horizontal .control-label{
float:left;
width:440px;
padding-top:5px;
text-align:left;
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
		<jsp:param name="menuLevel1" value="sys" />
		<jsp:param name="menuLevel2" value="privileges" />
		<jsp:param name="menuLevel3" value="editbasicaccessrules" />
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
 							编辑访问控制规则
 						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								<a href="index.html">首页</a> 

								<span class="icon-angle-right"></span>

							</li>

							<li>

								<a href="#">系统功能</a>

								<span class="icon-angle-right"></span>

							</li>

							<li><a href="#">编辑访问控制规则</a></li>

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

								<div class="caption"><i class="icon-reorder"></i>编辑访问控制规则</div>

 
							</div>

							<div class="portlet-body form">

								<!-- BEGIN FORM-->
                          <c:set var="index" value="0" />
							 <form:form method="post" enctype="multipart/form-data" name="accessRuleFrom"  id="accessRuleFrom" action="${pageContext.request.contextPath}/privileges/saveAdvancedAccessRules.html" modelAttribute="accessRuleFrom"  class="form-horizontal"> 
   					              <input  type="hidden" id="roleName" name="roleName" value="${roleName}"/>
  					     	 <c:forEach var="accessRuleVo" items="${accessRuleVoList}" varStatus="status">
									 <hr/>
	                                 <div class="control-group" style="background-color:#f5f5f5">
										<label class="control-label" style="font-size: 14;font-weight: bold">${accessRuleVo.name}</label>
										<div class="controls">
										</div>
 									</div>
										 <div class="control-group">
  										<label class="control-label"  style="font-size: 12;font-weight: bold">资源</label>
  										<div class="controls">
   										 <label class="control-label"  style="font-size: 12;font-weight: bold">规则</label>
										  <b style="font-size: 12;font-weight: bold">权限继承</b>
    									</div>
  									</div>
                                     <c:forEach var="accessRuleDataVo" items="${accessRuleVo.collection}" varStatus="accessRuleDataVoStatus">
 									 <div class="control-group">
  										<label class="control-label">${accessRuleDataVo.parsedAccessRule}</label>
                                         <c:set var="index" value="${index+1}" />
 										<div class="controls">
 										
 									   <input  type="hidden" id="" name="accessRuleDataList[${index}].parsedAccessRule" value="${accessRuleDataVo.parsedAccessRule}"/>
  									   <input  type="hidden" id="" name="accessRuleDataList[${index}].primaryKey" value="${accessRuleDataVo.primaryKey}"/>
   									   <input  type="hidden" id="accessRuleDataListRecursive_id${index}" name="accessRuleDataList[${index}].recursive" value='${accessRuleDataVo.recursive ?"true":"false"}'/>
    											<select class="span6 m-wrap" name="accessRuleDataList[${index}].state" id="selectusername" data-placeholder="Choose a Category" tabindex="1">
											    <c:forEach var="accessRuleStates" items="${accessRuleStatesMap}" varStatus="status">
												<option value="${accessRuleStates.value}" ${accessRuleDataVo.state eq accessRuleStates.value?"selected='selected'":""} >${accessRuleStates.key}</option>
												  </c:forEach>
											</select>
     						                 &nbsp;&nbsp;&nbsp;
											 <input type="checkbox" onclick="changCheckFn(this)"  attrHiddenId="accessRuleDataListRecursive_id${index}" value=""  ${accessRuleDataVo.recursive ?"checked='checked'":""}   name=""  tabindex="" />
    									</div>
  									</div></c:forEach>
									</c:forEach>

									   	<div class="form-actions">
									   <a class=" btn green" style="display: none" data-toggle="modal" id="stack2A" href="#stack2"></a>
  										<button type="button" class="btn blue" onclick="save()">提交</button>
   									</div>
 							</form:form>

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
	 
				<div id="stack2" class="modal hide fade in" tabindex="-1" data-focus-on="input:first">

									<div class="modal-header">

										<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
										<h3>操作提示</h3>

									</div>

									<div class="modal-body">
									   <p></p>
										&nbsp;&nbsp;<b id="modalMsg"></b>
									</div>

									<div class="modal-footer">
	                                    <button type="button" class="btn blue" id="buttonRem" attrRoleName="" onclick="reload(this)" >确定</button>
										<button type="button" data-dismiss="modal" id="dismissRemove" class="btn">关闭</button>
									</div>

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

<script type="text/javascript">
  
 
function changCheckFn(par){
 if($(par).is(':checked')){
 	 $("#"+$(par).attr("attrHiddenId")).val(true);
}else{
	 $("#"+$(par).attr("attrHiddenId")).val(false);
}	
}
 
function reload(){
	location.reload();
}
 
 
function save(){
	  showMask();
 	$("#accessRuleFrom").ajaxSubmit({
		     url : "${pageContext.request.contextPath}/privileges/saveAdvancedAccessRules.html",
							iframe : true,
							success : function(data) {
								   $("#stack2A").click();
									 if(data.success){
				           				   $("#modalMsg").html("操作成功！");
				           					}else{
				            				$("#modalMsg").html(data.msg);
				         				}
								hideMask();
							},
							error : function(arg1, arg2, ex) {
								 $("#stack2A").click();
		          		    	 $("#modalMsg").html("操作失败，请稍后重试！");
 								hideMask();
							},
							dataType : 'json'
	        });
	}
</script>

</html>