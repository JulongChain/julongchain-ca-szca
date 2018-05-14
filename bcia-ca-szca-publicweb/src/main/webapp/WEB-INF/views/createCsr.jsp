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
					<h2 class="heading mb-2 display-4 font-light probootstrap-animate">生成证书请求</h2>
					<p class="lead mb-5 probootstrap-animate">输入证书主体信息</p>
					<p class="lead mb-5 probootstrap-animate">选择密钥类型和长度</p>
					<p class="lead mb-5 probootstrap-animate">拷贝保存生成的私钥和证书请求信息 </p>
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
										<label for="id_label_single1">通用名（CN）</label> 
										<input type="text" name="cn" value="" placeholder="机构名称/姓名/通用名" required="required">
										 
									</div>
								</div>
								
							</div>
							<div class="row mb-3">
								
								<div class="col-md">
									<div class="form-group">
								 
										<label for="id_label_single1">机构名称（Organization）</label> 
										<input type="text" name="o" value="" placeholder="深圳CA">
										 
									</div>
								</div>
							 
							</div>
							<div class="row mb-3">
								
								<div class="col-md">
									<div class="form-group">
								 
										<label for="id_label_single1">部门（Organizational Unit）</label> 
										<input type="text" name="ou" value="" placeholder="技术中心">
										 
									</div>
								</div>
							 
							</div>
							<div class="row mb-3">
								
								<div class="col-md">
									<div class="form-group">
								 
										<label for="id_label_single1">城市（Locality）</label> 
										<input type="text" name="l" value="" placeholder="深圳市 ">
										 
									</div>
								</div>
							 
							</div>
							<div class="row mb-3">
								
								<div class="col-md">
									<div class="form-group">
								 
										<label for="id_label_single1">省份（State）</label> 
										<input type="text" name="s" value="" placeholder="广东省">
										 
									</div>
								</div>
							 
							</div>
							<div class="row mb-3">
								
								<div class="col-md">
									<div class="form-group">
								 
										<label for="id_label_single1">国家（Country）</label> 
										<input type="text" name="c" value="CN" readonly="readonly" >
										 
									</div>
								</div>
							 
							</div>
						
							<div class="row mb-3">
								<div class="col-md">
									<div class="form-group">
										<label for="probootstrap-date-arrival">密钥类型</label>
										<div class="probootstrap_select-wrap">
											<label for="id_label_single4" style="width: 100%;"> 										 
											<select name="keyType" class="js-example-basic-single js-states form-control" id="id_label_single4" style="width: 100%;">
													<option value="RSA1024">RSA1024</option>
													<option value="RSA2048">RSA2048</option>
													<option value="RSA4096">RSA4096</option>	
													<option value="EC" selected="selected">SM2</option>												 
											</select>
											</label>
										</div>
										
										
									</div>
								</div>
							</div>
							
							
							<!-- END row -->
							<div class="row">
								<!-- <div class="col-md">
									
								</div> -->
								<div class="col-md">
									<input type="submit" value="提交" class="btn btn-primary btn-block" />
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