<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
					<h2 class="heading mb-2 display-4 font-light probootstrap-animate">常用CSR、证书工具</h2>
					<p class="lead mb-5 probootstrap-animate">提供多种常用工具，包含生成RSA、SM2等算法的密钥、CSR功能的在线工具，解析RSA、SM2等算法的CSR功能的在线工具，还有检测证书密钥匹配功能的在线工具。</p>
					 
				</div>
				 
				<div class="col-md-right">
				<!-- <div >
				<iframe name="downloadEndEntityCertFrame" frameborder=0 width=0 height=0></iframe>
				  </div> -->
					<form name="genCsrForm" id="genCsrForm" action="genCsr.html" method="post"  class="probootstrap-form">
					
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
									<!-- 	<label for="id_label_single1"><a href="createCsr.html">生成CSR</a></label> 	 -->
										<input type="button" value="生成CSR" class="btn btn-primary btn-block" onclick="openUrl('createCsr.html.html')"/>							 
									</div>
								</div>
								
							</div>
							<div class="row mb-3">
								<div class="col-md">
									<div class="form-group">
									<!-- 
								 <label for="id_label_single1"><a href="readCsr.html">解析CSR</a></label>  -->	
									<input type="button" value="解析CSR" class="btn btn-primary btn-block" onclick="openUrl('readCsr.html')"/>	
											 
									</div>
								</div>
							 
							</div>
							
							<div class="row mb-3">
								<div class="col-md">
									<div class="form-group">
									<!-- 
								 <label for="id_label_single1"><a href="createValidate.html">证书密钥匹配检测</a></label> 	 -->
								 <input type="button" value="证书密钥匹配检测" class="btn btn-primary btn-block" onclick="openUrl('createValidate.html')"/>	
										 
									</div>
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
function openUrl(url){
	 window.location.href=url;
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