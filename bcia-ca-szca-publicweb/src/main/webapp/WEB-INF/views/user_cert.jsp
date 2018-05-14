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
		<jsp:param value="menu_user_cert" name="currMenu" />
	</jsp:include>

	<!-- END nav -->


	<section class="probootstrap-cover overflow-hidden relative" style="background-image: url('assets/images/bg_1.jpg');" data-stellar-background-ratio="0.5"
		id="section-home">
		<div class="overlay"></div>
		<div class="container">
			<div class="row align-items-center">
				<div class="col-md">
					<h2 class="heading mb-2 display-4 font-light probootstrap-animate">下载实体证书</h2>
					<p class="lead mb-5 probootstrap-animate">根据实体名称、证书主题、证书序列号等，下载指定的实体证书。在实体名称、证书主题、证书序列号等三个条件中，可以指定一个或者多个条件，但是至少要指定一个条件。若有多个证书符合条件，则返回最新的一个证书。</p>
					<%-- 
					<p class="lead mb-5 probootstrap-animate"></p>
					<a href="onepage.html" role="button" class="btn btn-primary p-3 mr-3 pl-5 pr-5 text-uppercase d-lg-inline d-md-inline d-sm-block d-block mb-3">See
						OnePage Verion</a>
					</p> --%>
				</div>
				 
				<div class="col-md-right">
					<form action="downloadEndEntityCert.html" id="downloadEndEntityCertForm" name="downloadEndEntityCertForm" method="post" class="probootstrap-form">
				 
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
										<label for="id_label_single2">CA名称</label> 
										<select name="issuerDn" class="js-example-basic-single js-states form-control" id="id_label_single2" style="width: 100%;">
											 <c:forEach items="${caList }" var="issuerDn">
											 <option value="${issuerDn }">${issuerDn }</option>
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
							<div class="row mb-3">
								<div class="col-md">
									<div class="form-group">
										<label>证书主题</label> 
										<input type="text" name="subjectDn" value="">
										 
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
	
	
	<!-- START Footer -->
	<%@include file="common/footer.jsp" %>
	<!-- END Footer -->
</body>
	<script>
function downloadEECert(){
	downloadEndEntityCertForm.method="POST";
	downloadEndEntityCertForm.action="downloadEndEntityCert.html";	
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