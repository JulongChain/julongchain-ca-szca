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
		<jsp:param value="menu_csr" name="currMenu" />
	</jsp:include>

	<!-- END nav -->


	<section class="probootstrap-cover overflow-hidden relative" style="background-image: url('assets/images/bg_1.jpg');" data-stellar-background-ratio="0.5"
		id="section-home">
		<div class="overlay"></div>
		<div class="container">
			<div class="row align-items-center">
				<div class="col-md">
					<h2 class="heading mb-2 display-4 font-light probootstrap-animate">解析证书请求</h2>
					<%-- 
					<p class="lead mb-5 probootstrap-animate"></p>
					<a href="onepage.html" role="button" class="btn btn-primary p-3 mr-3 pl-5 pr-5 text-uppercase d-lg-inline d-md-inline d-sm-block d-block mb-3">See
						OnePage Verion</a>
					</p> --%>
				</div>
				 
				<div class="col-md-right">
					<form name="decodeCsrForm" id="decodeCsrForm" action="decodeCsr.html" method="post"  class="probootstrap-form">
					
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
								<div class="tab-pane active" id="tab1">
									<h3 class="block">CSR详细信息</h3>
									
								</div>
								<table class="table table-striped table-bordered table-hover table-full-width ">
									<tr>
										<td style="color: black; width: 30%">通用名称(CN)：</td>
										<td style="color: black; width: 70%">${csrInfo.cn }</td>
									</tr>
									<tr>
										<td style="color: black">单位名称(O)：</td>
										<td style="color: black">${csrInfo.o }</td>
									</tr>
									<tr>
										<td style="color: black">部门(OU)：</td>
										<td style="color: black">${csrInfo.ou }</td>
									</tr>
									<tr>
										<td style="color: black">城市(L)：</td>
										<td style="color: black">${csrInfo.l }</td>
									</tr>
									<tr>
										<td style="color: black">省份(ST)：</td>
										<td style="color: black">${csrInfo.s }</td>
									</tr>
									<tr>
										<td style="color: black">国家(C)：</td>
										<td style="color: black">${csrInfo.c }</td>
									</tr>
									<tr>
										<td style="color: black">密钥长度(keySize)：</td>
										<td style="color: black">${csrInfo.keySize }</td>
									</tr>
									<tr>
										<td style="color: black">加密算法：</td>
										<td style="color: black">${csrInfo.signAlg }</td>
									</tr>
								</table>
								</div>
								
							</div>
						</div>
					</form>

				</div> 
			</div>
		</div>

	</section>
	<!-- END section -->
 

	<!-- START Footer -->
	<%@include file="common/footer.jsp" %>
	<!-- END Footer -->
</body>
<script>
function downloadEECert(){
	//downloadEndEntityCertForm.target="downloadEndEntityCertFrame";
	downloadEndEntityCertForm.action="genEndEntityCert.html";	
	downloadEndEntityCertForm.submit();
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