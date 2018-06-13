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