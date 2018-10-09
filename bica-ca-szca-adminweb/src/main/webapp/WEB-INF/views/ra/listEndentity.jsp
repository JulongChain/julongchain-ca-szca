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
		<jsp:param name="menuLevel1" value="ra" />
		<jsp:param name="menuLevel2" value="listEndentity" />
		<jsp:param name="menuLevel3" value="" />
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
 							终端实体管理 
 						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								<a href="index.html">首页</a> 

								<i class="icon-angle-right"></i>

							</li>

							<li>

								<a href="#">RA功能</a>

								<i class="icon-angle-right"></i>

							</li>

							<li><a href="#">终端实体管理</a></li>

						</ul>

						<!-- END PAGE TITLE & BREADCRUMB-->

					</div>

				</div>

				<!-- END PAGE HEADER-->

				<!-- BEGIN PAGE CONTENT-->          
       <form name="listEndentityForm"  method="post" id="listEndentityForm" action="${pageContext.request.contextPath}/ra/listEndentity.html" class="form-horizontal">
  		 <input type='hidden' name='currentPage' id='currentPage' value='${currentPage}'>
		 <input type='hidden' name='rowsPerPage' id='rowsPerPage' value='${rowsPerPage}'>  
  		 <c:choose>
  						    <c:when test="${error}">
  										<div class="control-group" style="height: 50px;"> 
   										  ${errorMsg}
  										  </div>
  										</c:when>
  								<c:otherwise> 
				<div class="row-fluid">

					<div class="span12">

						<!-- BEGIN EXAMPLE TABLE PORTLET-->
 
						<div class="portlet box blue">

							<div class="portlet-title">

								<div class="caption"><i class="icon-edit">终端实体列表</i></div>
							</div>

							<div class="portlet-body">
 								 			<div class="portlet-body form">
								<!-- BEGIN FORM-->
					  <form name="listEndentityForm"  method="post" id="listEndentityForm" action="${pageContext.request.contextPath}/ra/listEndentity.html" class="form-horizontal"> 
   								<div class="control-group">
										<label class="control-label">用户名</label>
									 <div class="controls">
									 <input name="username" value="${endEntityInformationVo.username}" type="text" placeholder="请输入用户名" class="m-wrap medium" />	<button type="submit" class="btn blue">
											<i class="icon-ok"></i> 查询
										</button>
										</div>
									</div>
								</form>
								<!-- END FORM-->
							</div>
								<table class="table table-striped table-hover table-bordered dataTable" id="example">

									<thead>

										<tr>
											<!-- <th style="width:8px;"><input type="checkbox" class="group-checkable" data-set="#sample_editable_1 .checkboxes" /></th> -->
 											<th>用户名</th>
 											<th>CA</th>
 											<th>CN</th>
 											<th>OU</th>
 											<th>O (组织)</th>
 											<th>状态</th>
											<th>操作</th>
 										</tr>
 									</thead>
 									<tbody>
 									   <c:forEach var="endEntityInfoVo" items="${list}" varStatus="status">
  										<tr class="">
											<!-- <td><input type="checkbox" class="checkboxes" value="1" /></td>-->
										   <td>${endEntityInfoVo.username}</td>
 											<td>${endEntityInfoVo.caName}</td>
 											<td>${endEntityInfoVo.commonName}</td>
 											<td>${endEntityInfoVo.subjectDNFieldOU}</td>
 											<td class="center">${endEntityInfoVo.subjectDNFieldO}</td>
 											  <td class="center">${endEntityInfoVo.status}</td>
  											<td>
														<button type="button" class="btn blue" onclick="viewuser('${endEntityInfoVo.username}')">${VIEWENDENTITY}</button>
														<button type="button" class="btn blue" onclick="edituser('${endEntityInfoVo.username}')">${EDITENDENTITY}</button>
 														<button type="button" class="btn blue" onclick="viewcert('${endEntityInfoVo.username}')">${VIEWCERTIFICATES}</button>
   											<%------ 
  											   <c:if test="${viewendentity}"> 
  											   <A  style="cursor:pointer;" onclick='edituser()'>${EDITENDENTITY}</A>
  											   </c:if>
   											  <c:if test="${authorizedCaView_cert}"> 
  											      <A  style="cursor:pointer;" onclick='viewcert()'>${VIEWCERTIFICATES}  </A>
  											   </c:if>
  											    <c:if test="${authorizedHardtokenViewRights}"> 
  											      <A  style="cursor:pointer;" onclick='viewtoken()'>${EDITENDENTITYEDITENDENTITYEDITENDENTITYEDITENDENTITY}  </A>
  											   </c:if>
  											    <c:if test="${authorizedRaHistoryRights}"> 
  											      <A  style="cursor:pointer;" href="audit/search.html?username=${endEntityInfoVo.username}" >${VIEWHISTORY}  </A>
  											   </c:if>
  											   
  											   --%>
    											</td>
   										</tr>
  											  </c:forEach>
 									</tbody>

								</table>
                              <div class="span12">
									<div class="dataTables_paginate paging_bootstrap pagination">
 									     <szca:pager rowsCount="${totalRowsCount}" id="logPagerBar" currentPage="${currentPage}" rowsPerPage="${rowsPerPage}"/>
 									</div>
								</div>
							</div>
							</div>

						</div>

						<!-- END EXAMPLE TABLE PORTLET-->

					</div>
					
 						</c:otherwise>
  								 </c:choose>
 </form>
				</div>



				<!-- END PAGE CONTENT-->

			</div></div></div>

			<!-- END PAGE CONTAINER-->

		</div>
		 
	<!-- E====END PAGE==== -->
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@include file="../common/footer.jsp"%>
	<!-- END FOOTER -->
</body>

    

<script>

$(document).ready(function() {
   
} );

function goto_page(pageNo) {
	//searchForm.currentPage.value=pageNo;
	document.getElementById("currentPage").value = pageNo;
 	$("#listEndentityForm").submit();
}
		function viewuser(username){
 			window.location.href="${pageContext.request.contextPath}/ra/viewendentity.html?username="+encodeURI(username);
		}

		function edituser(username){
 			window.location.href="${pageContext.request.contextPath}/ra/editendentity.html?username="+encodeURI(username);
 		}

 

		function viewcert(username){
 			window.location.href="${pageContext.request.contextPath}/ra/viewcertificate.html?index=0&username="+encodeURI(username);

		}

 
		
	</script>

<!-- END BODY -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<%@include file="../common/corePlugin.jsp"%>
</html>