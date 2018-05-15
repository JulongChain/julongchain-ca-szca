<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

<!DOCTYPE html>
<html>
<head>
<%@include file="common/metaHead.jsp"%>
<link rel="stylesheet" type="text/css" href="assets/css/wang.css" />
</head>
<body>
	<!-- START nav -->
	<jsp:include page="common/topNav.jsp" flush="true">
		<jsp:param value="menu_revoke_cert" name="currMenu" />
	</jsp:include>

	<!-- END nav -->


	<section class="probootstrap-cover overflow-hidden relative" style="background-image: url('assets/images/bg_1.jpg');" data-stellar-background-ratio="0.5"
		id="section-home">
		<div class="overlay"></div>
		<div class="container">
			<div class="row align-items-center">
				 
				 <div class="col-md">
					<h2 class="heading mb-2 display-4 font-light probootstrap-animate">撤销证书</h2>
					<p class="lead mb-5 probootstrap-animate">根据实体名称、证书序列号等，撤销指定的实体证书。若只指定实体名称，不指定证书序号，则会撤销此实体下所有有效证书;若指定了证书序号，则只撤销一个证书。其中管理员名称与管理员密码是指有撤销实体证书权限的CA管理员，由CA后台管理系统配置。</p>
				 
				</div>
				<div class="col-md-right">
					<form action="revokeEECert.html" id="downloadEndEntityCertForm" name="downloadEndEntityCertForm" method="post" class="probootstrap-form">
				 
							<div class="row mb-3">
								<div class="col-md">
									<div class="form-group">
										<span id="id_testResultMsg" style="color: red"></span>										 
									</div>
								</div>							 
							</div>
							
						<div class="form-group">
						 
						 <div class="row mb-3">
								
								<div class="col-md">
									<div class="form-group">
									<label>管理员名称</label> 
										<input type="text" name="adminName" value="">	
										 
									</div>
								</div>
								
							</div>
							<!-- END row -->
							
							<div class="row mb-3">
								
								<div class="col-md">
									<div class="form-group">
									<label>管理员密码</label> 
										<input type="password" name="adminPasswd" value="">	
										 
									</div>
								</div>
								
							</div>
							<!-- END row -->
						 
							<div class="row mb-3">
								<div class="col-md">
									<div class="form-group">
										<label for="id_label_single2">CA名称</label> 
										<select name="issuerDn" class="js-example-basic-single js-states form-control" id="id_label_single2" style="width: 100%;">
											 <c:forEach items="${caList }" var="ca">
											 <option value="${ca.name }">${ca.subjectDN }</option>
											 </c:forEach>
													 
													 												 
											</select>
										
									</div>
								</div>
								
							</div>
							<div class="row mb-3">
								
								<div class="col-md">
									<div class="form-group">
									<label>实体名称</label> 
										<input type="text" name="user" value="">	
										 
									</div>
								</div>
								
							</div>
							<!-- END row -->
							<div class="row mb-3">
								<div class="col-md">
									<div class="form-group">
											<label>证书序号</label> 
										<input type="text" name="serialNum">
										
									</div>
								</div>
							</div>
							<!-- 不提供subjectDn，因为相同subjectDn可能会有多个证书
							<div class="row mb-3">
								<div class="col-md">
									<div class="form-group">
										<label>证书主题</label> 
										<input type="text" name="subjectDn" value="">
										 
									</div>
								</div>
							</div>
							 -->
							<!-- END row -->
							<div class="row mb-3">
								<div class="col-md">
									<div class="form-group">
										<label for="id_label_single2">撤销原因</label> 
										<select name="reason" class="js-example-basic-single js-states form-control" id="id_label_single3" style="width: 100%;">
											<!-- 
											 <c:forEach items="${revokeReason }" var="rsn">
											 <option value="${issuerDn }">${issuerDn }</option>
											 </c:forEach>
											 -->
											 <option value="0">Unknown</option>		 
													 												 
											</select>
										
									</div>
								</div>
								
							</div> 
							
								<!-- END row -->
							<div class="row">
								<!-- <div class="col-md">
									
								</div> -->
								<div class="col-md">
								 	<input type="button" value="撤销证书" class="btn btn-primary btn-block" onclick="revokeEECert()">
								</div>
							</div>
						 
							
						</div>
					</form>
				</div> 
			</div>
		</div>

	</section>
	
	
	<!-- START Footer -->
	<%@include file="common/footer.jsp" %>
	<!-- END Footer -->
</body>
<script>
function revokeEECert(){
	 $.ajax({
					type : "POST",
					dataType : "json",
					url : "${pageContext.request.contextPath}/revokeEECert.html",
					data : $('#downloadEndEntityCertForm').serialize(),
					success : function(resp) {	 
						//alert(resp.message);						 
						$("#id_testResultMsg").html(resp.message);	
						 
					},
					error : function(data) {					 
						//btnobj.setAttribute("disabled", false);
					}
				});
}
</script>
<script type="text/javascript">
$(document).ready(function(){
	$(".js-example-basic-single").select2({
        minimumResultsForSearch: -1
	});
});
</script>
</html>