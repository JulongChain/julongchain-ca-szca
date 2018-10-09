<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
		<jsp:param value="ca_certs" name="currMenu" />
	</jsp:include>

	<!-- END nav -->


	<section class="probootstrap-cover overflow-hidden relative" style="background-image: url('assets/images/bg_1.jpg');" data-stellar-background-ratio="0.5"
		id="section-home">
		<div class="overlay"></div>
		<div class="container">
			<div class="row align-items-center">
				<div class="col-md">
					<h2 class="heading mb-5 display-4 font-light probootstrap-animate">获取CA证书</h2>
					 
					<p class="lead mb-5 probootstrap-animate">下载CA的根证书，或者下载CA的证书链。可以选择PEM格式，Firefox格式，IE格式等。若下载证书链，还可以选择JKS格式，此时JKS的密码是changeit</p>
					 
				</div>
				 
				<div class="col-md-listRight">
					<c:forEach var="retCa" items="${availableCAs}"  varStatus="idx">
		 <c:set var="ca" value="${retCa.ca}" /> 
		 <c:set var="ca_id" value="${retCa.caid}" />  
	 <section class="probootstrap-section-half d-md-flex" id="section-about">
	 
	 <%--
      <div class="probootstrap-image probootstrap-animate" data-animate-effect="fadeIn" style="background-image: url(assets/images/img_2.jpg)"></div>
      <div class="probootstrap-text">
       --%>
       <c:if test="${ idx.index % 2 ==0}">
       <c:set var="order1"  value="1" />
       <c:set var="order2"  value="2" />
       </c:if>
       <c:if test="${ idx.index % 2 ==1}">
       <c:set var="order1"  value="2" />
       <c:set var="order2"  value="1" />
       </c:if>
      <%-- <div class="probootstrap-image order-${order1} probootstrap-animate" data-animate-effect="fadeIn" style="background-image: url(assets/images/img_3.jpg)"></div> --%>
      <div class="probootstrap-text order-${order2} maxWidth">
      
      
        <div class="content-right" data-animate-effect="fadeInRight">
          <h4 class="mb-4">  CA名称: ${ca.name}  </h4>
		<c:set var="chain" value="${retCa.chain}" />
		<c:set var="chainsize" value="${fn:length(chain)}" />
	
		<c:choose>
			<c:when test="${chainsize == 0}">
			<h5 class="heading mb-4">  CA尚未初始化，不存在CA证书。  </h5>
			 
			</c:when>
			<c:otherwise>
				<c:set var="issuercert" value="${chain[chainsize - 1]}" />
				<c:set var="issuerdn" value="${issuercert.subjectDN}" />

				<div>
				<c:forEach var="cert" items="${chain}" varStatus="status">
			 
					<div style="padding-left: ${status.index}0px ; margin-left: ${status.index}0px ;">
				<h5 class="wow zoomIn animated" data-wow-delay=".5s">CA主题: 
					<c:if test="${status.last}"><b></c:if>
						<i>${cert.subjectDN}</i>
					<c:if test="${status.last}"></b></c:if></h5>
					<div>
					CA证书:
					<c:url var="pem" value="/webdist/certdist" >
						<c:param name="cmd" value="cacert" />
						<c:param name="issuer" value="${issuerdn}" />
						<c:param name="level" value="${chainsize - status.count}" />
					</c:url>
					<a href="${pem}">下载PEM格式</a>,
					<c:url var="ns" value="/webdist/certdist" >
						<c:param name="cmd" value="nscacert" />
						<c:param name="issuer" value="${issuerdn}" />
						<c:param name="level" value="${chainsize - status.count}" />
					</c:url>
					<a href="${ns}">下载Firefox格式</a>,
					<c:url var="ie" value="/webdist/certdist" >
						<c:param name="cmd" value="iecacert" />
						<c:param name="issuer" value="${issuerdn}" />
						<c:param name="level" value="${chainsize - status.count}" />
					</c:url>
					<a href="${ie}">下载IE格式 </a>
					</div>
					</div>
				</c:forEach>
				<div>
			 
				CA证书链:
				<c:url var="pemchain" value="/webdist/certdist" >
					<c:param name="cmd" value="cachain" />
					<c:param name="caid" value="${ca_id}" />
					<c:param name="format" value="pem" />
				</c:url>					
				<a href="${pemchain}">下载PEM证书链</a>, 
				<c:url var="jkschain" value="/webdist/certdist" >
					<c:param name="cmd" value="cachain" />
					<c:param name="caid" value="${ca_id}" />
					<c:param name="format" value="jks" />
				</c:url>					
				<a href="${jkschain}">下载JKS truststore</a> (密码: changeit)
				 
				</div>				
				</div>
			</c:otherwise>
		</c:choose>
		</div>
      </div>
    </section>
	</c:forEach>
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