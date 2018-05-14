<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="common/metaHead.jsp"%>
</head>
<body>
	<body>
  
	<!-- START nav -->
	<jsp:include page="common/topNav.jsp" flush="true">
		<jsp:param value="menu_inspect" name="currMenu" />
	</jsp:include>

	<!-- END nav -->
 
    

    <section class="probootstrap-cover overflow-hidden relative"  style="background-image: url('assets/images/bg_1.jpg');" data-stellar-background-ratio="0.5" id="section-home">
      <div class="overlay"></div>
      <div class="container">
        <div class="row align-items-center text-center">
          <div class="col-md">
           <div class="col-md">
					<h2 class="heading mb-2 display-4 font-light probootstrap-animate">区块链密码创新联盟</h2>
					<p class="lead mb-5 probootstrap-animate"> 基于Java的区块链的国产密码应用</p>
					
				 
				</div>
          </div> 
        </div>
      </div>
    
    </section>
    <!-- END section -->
    
       
    <!-- END section -->

    <!-- START Footer -->
	<%@include file="common/footer.jsp" %>
	<!-- END Footer -->
	</body>
</html>