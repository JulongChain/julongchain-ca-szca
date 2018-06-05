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
		<jsp:param name="menuLevel3" value="administratorprivileges" />
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

						<!-- BEGIN STYLE CUSTOMIZER -->

						<div class="color-panel hidden-phone">

 
						 

						<!-- END BEGIN STYLE CUSTOMIZER -->  

						<!-- BEGIN PAGE TITLE & BREADCRUMB-->

						<h3 class="page-title">
 							管理员权限配置管理
 						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								<a href="index.html">首页</a> 

								<i class="icon-angle-right"></i>

							</li>

							<li>

								<a href="#">系统功能</a>

								<i class="icon-angle-right"></i>

							</li>

							<li><a href="#">管理员权限配置管理</a></li>

						</ul>

						<!-- END PAGE TITLE & BREADCRUMB-->

					</div>

				</div>

				<!-- END PAGE HEADER-->

				<!-- BEGIN PAGE CONTENT-->          

 
				<div class="row-fluid">

					<div class="span12">

						<!-- BEGIN EXAMPLE TABLE PORTLET-->

						<div class="portlet box blue">

							<div class="portlet-title">

								<div class="caption"><i class="icon-edit">管理员权限配置管理</i></div>
							</div>

							<div class="portlet-body">

							<div class="clearfix">

									<div class="btn-group">
  										<a class=" btn green" data-toggle="modal" href="#stack1">新增 </a>
 									</div>

								</div>
 								 
								<table class="table table-striped table-hover table-bordered dataTable" id="sample_editable_1">

									<thead>

										<tr>
											<!-- <th style="width:8px;"><input type="checkbox" class="group-checkable" data-set="#sample_editable_1 .checkboxes" /></th> -->
 											<th>角色名称</th>
  											<th>操作</th>
 										</tr>
 									</thead>
 									<tbody>
 									   <c:forEach var="role" items="${roles}" varStatus="status">
  										<tr class="">
											<!-- <td><input type="checkbox" class="checkboxes" value="1" /></td>-->
										   <td>${role.roleName}</td>
 									 		 <td>
 												 		  <a class=" btn green"   attrRoleName="${role.roleName}"  onclick="editadminentities(this)" >编辑管理员</a>
 												 		 <a class=" btn green"   attrRoleName="${role.roleName}"  onclick="editadvancedaccessrules(this)" >编辑权限</a>
 												 		 <a class=" btn green" data-toggle="modal" attrRoleName="${role.roleName}"   onclick="renameRole(this)" href="#stack3">修改角色名</a>
 														 <a class=" btn green" data-toggle="modal" attrRoleName="${role.roleName}"   onclick="remove(this)" href="#stack2">删除</a>
      										 </td>
   										</tr>
  									  </c:forEach>
 									</tbody>

								</table>

							</div>

						</div>

						<!-- END EXAMPLE TABLE PORTLET-->

					</div>

				</div>


 


				<!-- END PAGE CONTENT-->

			</div>

			<!-- END PAGE CONTAINER-->

		</div></div></div>

				<div id="stack1" class="modal hide fade" tabindex="-1" data-focus-on="input:first">

									<div class="modal-header">

										<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
										<h3>新增角色</h3>

									</div>

									<div class="modal-body">
									   <p></p>
										&nbsp;&nbsp;角色名称：<input type="text" class="m-wrap" id="inputRoleName" data-tabindex="1"><b id="errorMsg"  style="color: red" ></b>
									</div>

									<div class="modal-footer">
	                                    <button type="button" class="btn blue" onclick="addRole()" >确定</button>
										<button type="button" data-dismiss="modal" id="dismiss" class="btn">关闭</button>
									</div>
 				 </div>
 				 
 				 
 				 
				<div id="stack2" class="modal hide fade in" tabindex="-1" data-focus-on="input:first">

									<div class="modal-header">

										<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
										<h3>删除角色</h3>

									</div>

									<div class="modal-body">
									   <p></p>
										&nbsp;&nbsp;确定删除“<b id="msgRole"  style="color: red" ></b>”角色？
									</div>

									<div class="modal-footer">
	                                    <button type="button" class="btn blue" id="buttonRem" attrRoleName="" onclick="doRemove(this)" >确定</button>
										<button type="button" data-dismiss="modal" id="dismissRemove" class="btn">关闭</button>
									</div>

								</div>
								
								
								
				<div id="stack3" class="modal hide fade" tabindex="-1" data-focus-on="input:first">

									<div class="modal-header">

										<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
										<h3>修改角色名</h3>
 									</div>

									<div class="modal-body">
									   <p></p>
										&nbsp;&nbsp;修改“<b id="msgRoleName"  style="color: red" ></b>”角色名称为：<input type="text" class="m-wrap" id="newRoleName" data-tabindex="1"><b id="newRoleNameerrorMsg"  style="color: red" ></b>
									</div>

									<div class="modal-footer">
	                                    <button type="button" class="btn blue" id="buttonRoleName" attrRoleName="" onclick="doRenameRole(this)" >确定</button>
										<button type="button" data-dismiss="modal" id="dismissRenameRole" class="btn">关闭</button>
									</div>
 				 </div>
 	<!-- E====END PAGE==== -->
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@include file="../common/footer.jsp"%>
	<!-- END FOOTER -->
</body>

<script src="${pageContext.request.contextPath}/media/js/jquery-1.10.1.min.js"></script>
 <script>

	
	function editadminentities(par) {
		window.location.href = "${pageContext.request.contextPath}/privileges/editadminentities.html?roleName="+ encodeURI($(par).attr("attrRoleName"));

	}

	function editadvancedaccessrules(par) {
		window.location.href = "${pageContext.request.contextPath}/privileges/editbasicaccessrules.html?roleName="+ encodeURI($(par).attr("attrRoleName"));
	}

	function  addRole(){
		var inputRoleName=$("#inputRoleName").val();
		if(""!=inputRoleName){
		 $.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/privileges/addRole.html",
			async:true,
			data:{roleName:inputRoleName},
			dataType:'json',
			success : function(data) { 				 
   				 if(data.success){
   					  $("#dismiss").click();
					  window.location.href="${pageContext.request.contextPath}/privileges/administratorprivileges.html";
   					}else{
  					 alert(data.msg);
 				}
   			},
			error : function(responseData) {
		 
 				}
		});}else{
				$("#errorMsg").html("请输入角色名称");
				$("#inputRoleName").focus();
 			}
		}

 
	function  remove(par){
		$("#msgRole").html($(par).attr("attrRoleName"));
		$("#buttonRem").attr("attrRoleName",$(par).attr("attrRoleName"));
  		}


	function  renameRole(par){
		$("#msgRoleName").html($(par).attr("attrRoleName"));
		$("#buttonRoleName").attr("attrRoleName",$(par).attr("attrRoleName"));
 		
  		}


	function doRenameRole(par){
		var newRoleName=$("#newRoleName").val();
		if(""!=newRoleName){
		 $.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/privileges/renameRole.html",
			async:true,
			data:{oldname:$(par).attr("attrRoleName"),newname:newRoleName},
			dataType:'json',
			success : function(data) { 				 
   				 if(data.success){
   					  $("#dismissRenameRole").click();
					  window.location.href="${pageContext.request.contextPath}/privileges/administratorprivileges.html";
   					}else{
  					 alert(data.msg);
 				}
   			},
			error : function(responseData) {
		 
 				}
		});}else{
			    $("#newRoleNameErrorMsg").html("请输入角色名称");
 				$("#newRoleName").focus();
 			}

		}
	
	
   function doRemove(par){
    $.ajax({
   			type : "POST",
   			url : "${pageContext.request.contextPath}/privileges/removeRole.html",
   			async:true,
   			data:{roleName:$(par).attr("attrRoleName")},
   			dataType:'json',
   			success : function(data) { 				 
      				 if(data.success){
      					 $("#dismissRemove").click();
   					    window.location.href="${pageContext.request.contextPath}/privileges/administratorprivileges.html";
      					}else{
     					 alert(data.msg);
    				}
      			},
   			error : function(responseData) { }
   	      	}); 
				
   }
     
</script>
<!-- END BODY -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<%@include file="../common/corePlugin.jsp"%>
</html>