<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
					<h2 class="heading mb-5 display-4 font-light probootstrap-animate">生成CSR信息</h2>
					 
					<p class="lead mb-5 probootstrap-animate">请拷贝并保存好私钥和CSR信息</p>
					 
				</div>
				 
				<div class="col-md-listRight">
					
		<section class="probootstrap-section-half d-md-flex" id="section-about">
			<%-- <div class="probootstrap-image order-${order1} probootstrap-animate" data-animate-effect="fadeIn" style="background-image: url(assets/images/img_3.jpg)"></div> --%>
			<div class="probootstrap-text order-1 maxWidth">
				<div class="probootstrap-text maxWidth">
					<div class="content-right probootstrap-inner probootstrap-animate" data-animate-effect="fadeInRight">
						<h4 class="mb-4">私钥: </h4>
					<!-- <h3>有两种证书注销列表</h3> -->	

					 	<pre style="font-size:10px;overflow: hidden;">${priKey }
                      </pre>
						<h4 class="mb-4">证书请求（CSR）: </h4>
						<pre style="font-size:10px;overflow: hidden;">${csr }
	
					<%--
						<h5>Delta CRL(增量)</h5>

						<ul>
							<li><a href="${derdelta}">DER 格式</a></li>
							<li><a href="${pemdelta}">PEM 格式</a></li>
						</ul>
 					--%>

					</div>
				</div>
			</div>
		</section>
				</div> 
			</div>
		</div>

	</section>
	<!-- END section -->
	 

	 
	<!-- END section -->

  


  
	<!-- END section -->
 
	<!-- END section -->
 
	<!-- END section -->
 
	<!-- END section -->
 
	<!-- END section -->
 
	<!-- END section -->

	<!-- START Footer -->
	<%@include file="common/footer.jsp" %>
	<!-- END Footer -->
</body>
</html>