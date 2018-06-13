<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
		<jsp:param value="menu_crl" name="currMenu" />
	</jsp:include>

	<!-- END nav -->


	<section class="probootstrap-cover overflow-hidden relative" style="background-image: url('assets/images/bg_1.jpg');" data-stellar-background-ratio="0.5"
		id="section-home">
		<div class="overlay"></div>
		<div class="container">
			<div class="row align-items-center">
				<div class="col-md">
					<h2 class="heading mb-5 display-4 font-light probootstrap-animate">获取证书撤销列表</h2>
					 
					<p class="lead mb-5 probootstrap-animate">下载CA的证书证书撤销列表，可以是der格式，也可以是pem格式</p>
					 
				</div>
				 
				<div class="col-md-listRight">
					<c:forEach var="retCa" items="${availableCAs}" varStatus="idx">
		<c:set var="ca" value="${retCa.ca}" />
		<c:set var="ca_id" value="${retCa.caid}" />


		<c:set var="caName" value="${ca.name}" />
		<c:set var="caDN" value="${ca.subjectDN}" />

		<c:url var="der" value="/webdist/certdist">
			<c:param name="cmd" value="crl" />
			<c:param name="issuer" value="${caDN}" />
		</c:url>
		<c:url var="pem" value="/webdist/certdist">
			<c:param name="cmd" value="crl" />
			<c:param name="format" value="PEM" />
			<c:param name="issuer" value="${caDN}" />
		</c:url>

		<c:url var="derdelta" value="/webdist/certdist">
			<c:param name="cmd" value="deltacrl" />
			<c:param name="issuer" value="${caDN}" />
		</c:url>
		<c:url var="pemdelta" value="/webdist/certdist">
			<c:param name="cmd" value="deltacrl" />
			<c:param name="format" value="PEM" />
			<c:param name="issuer" value="${caDN}" />
		</c:url>
		<c:if test="${ idx.index % 2 ==0}">
			<c:set var="order1" value="1" />
			<c:set var="order2" value="2" />
		</c:if>
		<c:if test="${ idx.index % 2 ==1}">
			<c:set var="order1" value="2" />
			<c:set var="order2" value="1" />
		</c:if>
		<section class="probootstrap-section-half d-md-flex" id="section-about">
			<%-- <div class="probootstrap-image order-${order1} probootstrap-animate" data-animate-effect="fadeIn" style="background-image: url(assets/images/img_3.jpg)"></div> --%>
			<div class="probootstrap-text order-${order2} maxWidth">
				<div class="probootstrap-text maxWidth">
					<div class="content-right probootstrap-inner probootstrap-animate" data-animate-effect="fadeInRight">
						<h4 class="mb-4">CA名称: ${caName}</h4>
					<!-- <h3>有两种证书注销列表</h3> -->	

					 	
						<h4 class="mb-4">CA主题: ${caDN}</h4>
						<h5>下载CRL文件</h5> 
						<ul>
							<li><a href="${der}">DER 格式</a></li>
							<li><a href="${pem}">PEM 格式</a></li>
						</ul>
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