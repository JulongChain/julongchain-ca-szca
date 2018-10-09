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
		<jsp:param name="menuLevel3" value="editadminentities" />
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
 							编辑管理员
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

							<li><a href="#">编辑管理员</a></li>

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

								<div class="caption"><i class="icon-edit">编辑管理员：${roleName}</i></div>
							</div>

							<div class="portlet-body">
 					  <form:form method="post"  name="editadminentitiesFrom"  id="editadminentitiesFrom" action="${pageContext.request.contextPath}/privileges/addAdmin.html"   class="form-horizontal"> 
 								<table class="table table-bordered dataTable" id="sample_editable_1">
 									<thead>
 										<tr>
   											<th>CA</th>
 											<th>匹配</th>
 											<th>匹配类型</th>
 											<th>匹配值</th>
 											<th></th>
 										</tr>
 									</thead>
 									<tbody>
   										<tr class="">
												<td><select class="span6 m-wrap" name="matchCaId"  id="matchCaId" data-placeholder="Choose a Category"   tabindex="1">
 													   <c:forEach var="availablecasMap" items="${availablecasMap}" varStatus="status">
												<option value="${availablecasMap.value}" >${availablecasMap.key}</option>
												  </c:forEach>
 												</select></td>
												<td><select class="span6 m-wrap" name="matchWithItems" id="matchWithItems" data-placeholder="Choose a Category"  tabindex="1">
												 <c:forEach var="matchWithMap" items="${matchWithMap}" varStatus="status">
												<option value="${matchWithMap.value}" >${matchWithMap.key}</option>
												  </c:forEach>
												</select></td>
												<td><select class="span6 m-wrap" name="matchType" id="matchType" data-placeholder="Choose a Category"   tabindex="1">
													 <c:forEach var="accessMatchTypeMap" items="${accessMatchTypeMap}" varStatus="status">
												<option value="${accessMatchTypeMap.value}" >${accessMatchTypeMap.key}</option>
												  </c:forEach>
												</select></td>
												<td>
											<input type="text" name="matchValue" id="matchValue" value="" class="span6 m-wrap popovers" data-trigger="hover" data-content="" data-original-title="" />
                                                       </td>
												<td>
													<button type="button" class="btn blue" onclick="addAdmin()">添加</button>
												</td>
											</tr>
  									</tbody>

								</table>
								</form:form>
<hr/>


	<table class="table table-striped table-hover table-bordered dataTable" id="sample_editable_1">
  									<tbody>
  								 <c:forEach var="adminsMatchVo" items="${adminsMatchVoList}" varStatus="status">
  										<tr class="">
 										   <td>${adminsMatchVo.issuingCA}</td>
 											<td>${adminsMatchVo.adminsMatchWith}</td>
 											<td>${adminsMatchVo.adminsMatchType}</td>
 											<td>${adminsMatchVo.matchValue}</td>
  											<td>
 										   <a class=" btn green" data-toggle="modal"  attrPrimaryKeye="${adminsMatchVo.primaryKey}"   onclick="removeFunction(this)" href="#stack2" >删除</a>
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

	</div>	</div>
 


				<!-- END PAGE CONTENT-->

			</div>

			<!-- END PAGE CONTAINER-->

		</div>
		 
		 	 
				<div id="stack2" class="modal hide fade in" tabindex="-1" data-focus-on="input:first">

									<div class="modal-header">

										<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
										<h3>操作提示</h3>

									</div>

									<div class="modal-body">
									   <p></p>
										&nbsp;&nbsp;确定删除？
									</div>

									<div class="modal-footer">
	                                    <button type="button" class="btn blue" id="buttonRem" attrPrimaryKeye="" onclick="doRemove(this)" >确定</button>
										<button type="button" data-dismiss="modal" id="dismissRemove" class="btn">关闭</button>
									</div>
 </div>
								
	<!-- E====END PAGE==== -->
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@include file="../common/footer.jsp"%>
	<!-- END FOOTER -->
</body>
   <script>

 	function editadvancedaccessrules(par) {
		window.location.href = "${pageContext.request.contextPath}/privileges/editbasicaccessrules.html?roleName="+ encodeURI($(par).attr("attrRoleName"));
	}

	function  addAdmin(){
		// check data   todo
		   var matchCaId=$("#matchCaId").val();
		   var matchWithItems=$("#matchWithItems").val();
		   var matchValue=$("#matchValue").val();
		   var matchType=$("#matchType").val();
   	       $.ajax({
	   			type : "POST",
	   			url : "${pageContext.request.contextPath}/privileges/addAdmin.html",
	   			async:true,
	   			data:{matchCaId:matchCaId,matchWith:matchWithItems,matchValue:matchValue,matchType:matchType,roleName:"${roleName}"},
	   			dataType:'json',
	   			success : function(data) { 				 
	      				 if(data.success){
 							 	reloadFunction();
	       					}else{
	     					 alert(data.msg);
	    				}
	      			},
	   			error : function(responseData) { }
	   	      	}); 
		}

 
	function  removeFunction(par){
 		$("#buttonRem").attr("attrPrimaryKeye",$(par).attr("attrPrimaryKeye"));
   		}


 	function  reloadFunction(){
			window.location.href = "${pageContext.request.contextPath}/privileges/editadminentities.html?roleName="+ encodeURI("${roleName}");
    		}


 
   function doRemove(par){
    $.ajax({
   			type : "POST",
   			url : "${pageContext.request.contextPath}/privileges/deleteAdmin.html",
   			async:true,
   			data:{primaryKeye:$(par).attr("attrPrimaryKeye"),roleName:"${roleName}"},
   			dataType:'json',
   			success : function(data) { 				 
      				 if(data.success){
      					 $("#dismissRemove").click();
						 	reloadFunction();
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