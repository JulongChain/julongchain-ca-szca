<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
  ~ /***
  ~  *
  ~  * Copyright © 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
  ~  * Copyright © 2018  SZCA. All Rights Reserved.
  ~  * <p>
  ~  * Licensed under the Apache License, Version 2.0 (the "License");
  ~  * you may not use this file except in compliance with the License.
  ~  * You may obtain a copy of the License at
  ~  * <p>
  ~  * http://www.apache.org/licenses/LICENSE-2.0
  ~  * <p>
  ~  * Unless required by applicable law or agreed to in writing, software
  ~  * distributed under the License is distributed on an "AS IS" BASIS,
  ~  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~  * See the License for the specific language governing permissions and
  ~  * limitations under the License.
  ~  *
  ~  */
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
		<jsp:param value="menu_enroll" name="currMenu" />
	</jsp:include>

	<!-- END nav -->


	<section class="probootstrap-cover overflow-hidden relative" style="background-image: url('assets/images/bg_1.jpg');" data-stellar-background-ratio="0.5"
		id="section-home">
		<div class="overlay"></div>
		<div class="container">
			<div class="row align-items-center">
				<div class="col-md">
					<h2 class="heading mb-2 display-4 font-light probootstrap-animate">申请实体证书</h2>
					<p class="lead mb-5 probootstrap-animate">输入实体名称的实体密码(实体密码是在注册实体时返回)</p>
					<p class="lead mb-5 probootstrap-animate">如果输出类型选择了证书(Certificate),则必须上传CSR文件或者输入BASE64编码格式的CSR内容。</p>
					<p class="lead mb-5 probootstrap-animate">如果选择了CSR文件或者输入BASE64编码格式的CSR内容，则系统统一返回 Certificate </p>
					<%-- 
					<p class="lead mb-5 probootstrap-animate"></p>
					<a href="onepage.html" role="button" class="btn btn-primary p-3 mr-3 pl-5 pr-5 text-uppercase d-lg-inline d-md-inline d-sm-block d-block mb-3">See
						OnePage Verion</a>
					</p> --%>
				</div>
				 
				<div class="col-md-right">
				<!-- <div >
				<iframe name="downloadEndEntityCertFrame" frameborder=0 width=0 height=0></iframe>
				  </div> -->
					<form name="downloadEndEntityCertForm" id="downloadEndEntityCertForm" enctype="multipart/form-data" method="post"   class="probootstrap-form">
					<input type="hidden" name="keylength" value="2048"> 
					
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
										<label for="id_label_single1">实体名称</label> 
										<input type="text" name="username" value="" placeholder="username">
										 
									</div>
								</div>
								
							</div>
							<div class="row mb-3">
								
								<div class="col-md">
									<div class="form-group">
								 
										<label for="id_label_single1">密码</label> 
										<input type="password" name="password" value="" placeholder="注册实体时返回的密码">
										 
									</div>
								</div>
							 
							</div>
							<!-- END row -->
							<!-- 
							<div class="row mb-3">
								<div class="col-md">
									<div class="form-group">
										<label for="probootstrap-date-departure">密钥长度</label>
										<div class="probootstrap_select-wrap">
											<label for="id_label_single3" style="width: 100%;"> 										 
											<select name="keylength" class="js-example-basic-single js-states form-control" id="id_label_single3" style="width: 100%;">
													<option value="1024">1024</option>
													<option value="2048">2048</option>													 
											</select>
											</label>
										</div>
									</div>
								</div>
							</div>
							 -->
							<div class="row mb-3">
								<div class="col-md">
									<div class="form-group">
										<label for="probootstrap-date-arrival">证书模板</label>
										<div class="probootstrap_select-wrap">
											<label for="id_label_single4" style="width: 100%;"> 										 
											<select name="certProfile" class="js-example-basic-single js-states form-control" id="id_label_single4" style="width: 100%;">
													<option value="ENDUSER">ENDUSER</option>
													<option value="SERVER">SERVER</option>													 
											</select>
											</label>
										</div>
										
										
									</div>
								</div>
							</div>
							<!-- END row -->
							<div class="row mb-3">
								<div class="col-md">
									<div class="form-group">
										<label for="probootstrap-date-departure">输出类型</label>
										<div class="probootstrap_select-wrap">
											<label for="id_label_single5" style="width: 100%;"> 										 
											<select name="tokenType" class="js-example-basic-single js-states form-control" id="id_label_single5" style="width: 100%;">
												<!-- <option value="1">PKCS#7b</option> -->	
													<option value="1">Certificate</option>
													<option value="2">PKCS#12</option>
													<option value="3">JKS</option>
													
													 												 
											</select>
											</label>
										</div>
									</div>
								</div>
							</div>
							<!-- END row -->
							<div class="row mb-10">
								<div class="col-md">
									<div class="form-group">
										<label for="probootstrap-date-departure">上传CSR文件</label>
										 <input style="border:none" type="file" name="csrFile" >
										 
									</div>
								</div>
								</div>
								<div class="row mb-10">
								<div class="col-md">
									<div class="form-group">
										<label for="probootstrap-date-arrival">CSR内容</label>
										<textarea name=csr rows="4" style="resize:none;width:100%;" placeholder="CSR的内容"></textarea>
										
									</div>
								</div>
							</div>
							
							<!-- END row -->
							<div class="row">
								<!-- <div class="col-md">
									
								</div> -->
								<div class="col-md">
									<input type="button" value="提交" class="btn btn-primary btn-block" onclick="downloadEECert()">
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