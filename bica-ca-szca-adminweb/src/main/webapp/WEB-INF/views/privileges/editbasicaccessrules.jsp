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
 
							 <form:form method="post" enctype="multipart/form-data" name="editBasicAccessRulesFrom"  id="editBasicAccessRulesFrom" action="${pageContext.request.contextPath}/privileges/saveAccessRules.html" modelAttribute="editBasicAccessRulesFrom"  class="form-horizontal"> 
                              <b id="hiddenAttr"></b>
   								  <div class="control-group">
										<label class="control-label">角色模板</label>
										<div class="controls">
											<select class="span6 m-wrap" name="selectrole"  id="selectrole" data-placeholder="Choose a Category" onchange="roleupdated()" tabindex="1">
											    <c:forEach var="roles" items="${rolesMap}" varStatus="status">
												<option value="${roles.value}" >${roles.key}</option>
												  </c:forEach>
											</select>
										</div>

									</div>
   									
   									
   									  <div class="control-group">
										<label class="control-label">编辑终端实体模板</label>
										<div class="controls">
											<select class="span6 m-wrap" name="selectcas" id="selectcas" data-placeholder="Choose a Category"  multiple="multiple" tabindex="1">
											    <c:forEach var="ca" items="${availableCaMap}" varStatus="status">
												<option value="${ca.value}" >${ca.key}</option>
												  </c:forEach>
											</select>
										</div>
 									</div>
 									
 								   <div class="control-group">
										<label class="control-label">终端实体规则</label>
										<div class="controls">
											<select class="span6 m-wrap" name="selectendentityrules"  id="selectendentityrules" data-placeholder="Choose a Category"  multiple="multiple" tabindex="1">
											    <c:forEach var="endentityrules" items="${availableendentityrulesMap}" varStatus="status">
												<option value="${endentityrules.value}" >${endentityrules.key}</option>
												  </c:forEach>
											</select>
										</div>
 									</div>
 									
 									   <div class="control-group">
										<label class="control-label">编辑终端实体模板</label>
										<div class="controls">
											<select class="span6 m-wrap" name="selectendentityprofiles" id="selectendentityprofiles" data-placeholder="Choose a Category"  multiple="multiple" tabindex="1">
											    <c:forEach var="endentityprofiles" items="${availableendentityprofilesMap}" varStatus="status">
												<option value="${endentityprofiles.value}" >${endentityprofiles.key}</option>
												  </c:forEach>
											</select>
										</div>
 									</div> 
 									
 											
 								<div class="control-group">
										<label class="control-label">INTERNALKEYBINDINGRULES</label>
										<div class="controls">
											<select class="span6 m-wrap" name="selectinternalkeybindingrules" id="selectinternalkeybindingrules"  multiple="multiple" id="selectendentityprofile" data-placeholder="Choose a Category"   tabindex="1">
											    <c:forEach var="availableInternalKeybindingRules" items="${availableInternalKeybindingRulesMap}" varStatus="status">
												<option value="${availableInternalKeybindingRules.value}" >${availableInternalKeybindingRules.key}</option>
												  </c:forEach>
											</select>
										</div>
 									</div>
 									
 									
 										<div class="control-group">
										<label class="control-label">其他规则</label>
										<div class="controls">
											<select class="span6 m-wrap" name="selectother" id="selectother" data-placeholder="Choose a Category"   multiple="multiple" tabindex="1">
											    <c:forEach var="availableotherrules" items="${availableotherrulesMap}" varStatus="status">
												<option value="${availableotherrules.value}" >${availableotherrules.key}</option>
												  </c:forEach>
											</select>
										</div>
 									</div>
 								  <a class=" btn green" style="display: none" data-toggle="modal" id="stack2A" href="#stack2"></a>
  		                      	<div class="form-actions">
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
<script type="text/javascript">
  
$(function () { 
	 roleupdated();
 });
 
/**
 * Modify selectable fields according to the currently selected role.
 */
function roleupdated() {
	var selectcas = document.getElementById('selectcas');
 	var selectrole = document.getElementById('selectrole');
	//alert(selectrole);
	var selectendentityrules = document.getElementById('selectendentityrules');
	var selectendentityprofiles = document.getElementById('selectendentityprofiles');
	var selectother = document.getElementById('selectother');
	var selectinternalkeybindingrules = document.getElementById('selectinternalkeybindingrules');
	
	var currentrole = selectrole.options[selectrole.options.selectedIndex].value;
	if (currentrole === '${customName}' || currentrole === '${superadministrator}' ) {
		selectAll(selectcas, true, false);
		selectAll(selectendentityrules, true, false);
		selectAll(selectendentityprofiles, true, false);
		selectAll(selectinternalkeybindingrules, true, false);
		selectAll(selectother, true, false);
	} else if (currentrole === '${caadministrator}') {
		selectcas.disabled = false;
		selectAll(selectendentityrules, true, false);
		selectAll(selectendentityprofiles, true, false);
		selectAll(selectinternalkeybindingrules, false, true);
		selectSome(selectother, [ '${other_viewlog}' ], true);
	} else if (currentrole === '${raadministrator}') {
		selectcas.disabled = false;
		selectendentityprofiles.disabled = false;
		selectSome(selectendentityrules, [
			'${endentity_view}',
			'${endentity_viewhistory}',
			'${endentity_create}',
			'${endentity_edit}',
			'${endentity_delete}',
			'${endentity_revoke}'
		], true);
		selectAll(selectinternalkeybindingrules, true, false);
		selectSome(selectother, [ '${other_viewlog}' ], true);
	} else if(currentrole === '${supervisor}') {
		selectcas.disabled = false;
		selectendentityprofiles.disabled = false;
		selectSome(selectendentityrules, [
			'${endentity_view}',
			'${endentity_viewhistory}',
			'${endentity_viewhardtokens}'
		], true);
		selectAll(selectinternalkeybindingrules, true, false);
		selectSome(selectother, [ '${other_viewlog}' ], true);
	} else if(currentrole === '${auditor}') {
		selectcas.disabled = false;
		selectendentityprofiles.disabled = false;
		selectAll(selectendentityrules, true, false);
		selectSome(selectendentityrules, [
		                      			'${endentity_view}',
		                      			'${endentity_viewhistory}',
		                      		], true);
		selectSome(selectinternalkeybindingrules, [
		    '${resource}'
			], true);
		selectSome(selectother, [ '${other_viewlog}' ], true);
	}
}

function reload(){
	location.reload();
}
 
function save(){
 	var caArray = new Array();
 	$("#hiddenAttr").html();
 	caArray.push(' <input  type="hidden" id="roleName" name="roleName" value="${roleName}"/>');
    caArray.push('<input  type="hidden" id="roleTemplate" name="roleTemplate" value="'+$("#selectrole").val()+'"/>');
	var selectcas = $("#selectcas").val();
		if (null != selectcas) {
			for (j = 0; j < selectcas.length; j++) {
				caArray.push('<input  type="hidden" id="currentCAs" name="currentCAs" value="'+selectcas[j]+'"/>');
			}
		}

	 var  selectendentityprofiles=$("#selectendentityprofiles").val();
	  if(null!=selectendentityprofiles){	
	 for(j = 0; j < selectendentityprofiles.length; j++) {
		  caArray.push( '<input  type="hidden" id="currentEndEntityProfiles" name="currentEndEntityProfiles" value="'+selectendentityprofiles[j]+'"/>');
    } }
	 var  selectother=$("#selectother").val();
	 if(null!=selectother){	
	 for(j = 0; j < selectother.length; j++) {
		  caArray.push( '<input  type="hidden" id="currentOtherRules" name="currentOtherRules" value="'+selectother[j]+'"/>');
    }}
	 var  selectendentityrules=$("#selectendentityrules").val();
	 if(null!=selectendentityrules){	
	 for(j = 0; j < selectendentityrules.length; j++) {
		  caArray.push( '<input  type="hidden" id="currentEndEntityRules" name="currentEndEntityRules" value="'+selectendentityrules[j]+'"/>');
    }   
	}
	 var  selectinternalkeybindingrules=$("#selectinternalkeybindingrules").val();
	  if(null!=selectinternalkeybindingrules){	
	 for(j = 0; j < selectinternalkeybindingrules.length; j++) {
		  caArray.push( '<input  type="hidden" id="currentInternalKeybindingRules" name="currentInternalKeybindingRules"  value="'+selectinternalkeybindingrules[j]+'"/>');
       }  }
             $("#hiddenAttr").html(caArray.join(""));
 		       $("#editBasicAccessRulesFrom").ajaxSubmit({
					url : "${pageContext.request.contextPath}/privileges/saveAccessRules.html",      
         			iframe: true,    
          			success: function(data) { 
        				   $("#stack2A").click();
          				 if(data.success){
           				   $("#modalMsg").html("操作成功！");
           					}else{
            				$("#modalMsg").html(data.msg);
         				}
          		            },
          		       error: function(arg1, arg2, ex) { 
          		    	  $("#stack2A").click();
          		    	   $("#modalMsg").html("操作失败，请稍后重试！");
           			    },        
         	   dataType: 'json'});
 	}

	function selectAll(selectElement, selectDisabled, selectedValue) {
		var length = selectElement.length;
		for (var i = 0; i < length; i++) {
			selectElement.options[i].disabled = false;
			selectElement.options[i].selected = selectedValue;
		}
		selectElement.disabled = selectDisabled;
	}

	function selectSome(selectElement, optionValues, disableUnselected) {
		selectElement.disabled = false;
		var selectLength = selectElement.length;
		for (var i = 0; i < selectLength; i++) {
			var found = false;
			for (var j = 0; j < optionValues.length; j++) {
				if (selectElement.options[i].value === optionValues[j]) {
					found = true;
					break;
				}
			}
			if (found) {
				selectElement.options[i].selected = true;
				selectElement.options[i].disabled = false;
			} else {
				selectElement.options[i].selected = false;
				selectElement.options[i].disabled = disableUnselected;
			}
		}
	}
	function checkallfields() {
		var selectrole = document.getElementById('selectrole');
		var currentrole = selectrole.options[selectrole.options.selectedIndex].value;
		if (currentrole === '${customName}') {
			alert("${selectanotherrole}");
			return false;
		}
		return true;
	}
</script>

</html>