<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="common/metaHead.jsp"%>
</head>
<body>
	<!-- START nav -->
	<jsp:include page="common/topNav.jsp" flush="true">
		<jsp:param value="menu_home" name="currMenu" />
	</jsp:include>

	<!-- END nav -->


	<section class="probootstrap-cover overflow-hidden relative" style="background-image: url('assets/images/bg_1.jpg');" data-stellar-background-ratio="0.5"
		id="section-home">
		<div class="overlay"></div>
		<div class="container">
			<div class="row align-items-center">
				<div class="col-md">
					<h2 class="heading mb-2 display-4 font-light probootstrap-animate">系统错误:${errMsg}</h2>
					 
				</div>
				 
			</div>
		</div>

	</section>
	<!-- END section -->
	<div class="chs">
	 
	</div>


	<section class="probootstrap_section" id="section-feature-testimonial">
		<div class="container">
			<div class="row justify-content-center mb-5">
				<div class="col-md-12 text-center mb-5 probootstrap-animate">
					<h2 class="display-4 border-bottom probootstrap-section-heading">来源:${exception} </h2>
					<!-- <h3 class="display-4 border-bottom probootstrap-section-heading">信息:${errMsg}</h3> -->
					<blockquote class="">
						<p class="lead mb-4">
							<em>${ste}</em>
					 
						</p>
						 
					</blockquote>

				</div>
			</div>

		</div>
	</section>
	<!-- END section -->
 

	<!-- END section -->

	 
	<!-- END section -->
 
	<!-- END section -->
	<!-- START Footer -->
	<%@include file="common/footer.jsp" %>
	<!-- END Footer -->
</body>
</html>