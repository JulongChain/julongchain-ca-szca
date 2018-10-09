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
<link rel="stylesheet" type="text/css" href="assets/css/wang.css" />
</head>
<body>
	<!-- START nav -->
	<jsp:include page="common/topNav.jsp" flush="true">
		<jsp:param value="menu_register" name="currMenu" />
	</jsp:include>

	<!-- END nav -->


	<section class="probootstrap-cover overflow-hidden relative" style="background-image: url('assets/images/bg_1.jpg');" data-stellar-background-ratio="0.5"
		id="section-home">
		<div class="overlay"></div>
		<div class="container">
			<div class="row align-items-center">
				<div class="col-md">
					<h2 class="heading mb-2 display-4 font-light probootstrap-animate">注册终端实体</h2>
					<p class="lead mb-5 probootstrap-animate">注册一个终端实体，请牢记用户名和密码，切莫向他人泄露。给此实体注册证书时需要使用此密码。</p>
					<%-- 
					<p class="lead mb-5 probootstrap-animate"></p>
					<a href="onepage.html" role="button" class="btn btn-primary p-3 mr-3 pl-5 pr-5 text-uppercase d-lg-inline d-md-inline d-sm-block d-block mb-3">See
						OnePage Verion</a>
					</p> --%>
				</div>
				 
				<div class="col-md-right">
					<form action="handleEntity.html" id="endEntityForm" method="post" class="probootstrap-form">					
							<div class="row mb-3">
								<div class="col-md">
									<div class="form-group">
										<span id="id_testResultMsg" style="color: red"></span>										 
									</div>
								</div>							 
							</div>
							
							<input type="hidden" name="processId" value="${certProcessData.processId }"/>
						<div class="form-group">
						 
							<div class="row mb-3">
								<div class="col-md">
									<h5>用户信息</h5>
									<div class="form-group">
										<label for="id_label_single1">用户名：</label> 
										<input type="text" name="username" required="required" placeholder="username">  
									</div>
									<div class="form-group">
										<label for="id_label_single1">密    码：</label> 
										<input type="password" name="password" required="required" id="password" value="${tranProfileMap['1']}">
									</div>
									
								</div>
							</div>
							<div class="row mb-3">
								<div class="col-md" id="dnDiv">	
								</div>
							</div>
							
							<div class="row mb-3">
								<div class="col-md" id="altDiv">	
								</div>
							</div> 
							
							<div class="row mb-3">
								<div class="col-md" id="dirDiv">	
								</div>
							</div> 
							<c:if test="${tranProfileMap['USEEXTENSIONDATA']}">
							<div class="row mb-3">
								<div class="col-md">
									<h5>自定义扩展项</h5>	
									<c:forEach items="${certificateExtensions }" var="e">
										<div class="form-group">
											<label for="id_label_single1">${e.displayName }：</label> 
											<input type="text" name="extension${e.id }" required="required" id="extension${e.id }" /> 
										</div>
									</c:forEach>
								</div>
							</div>
							</c:if>
							<!-- END row -->
							<div class="row">
								<!-- <div class="col-md">
									
								</div> -->
								<div class="col-md">
									<input type="submit"  value="提交" class="btn btn-primary btn-block"/>
								</div>
							</div>
						</div>
					</form>
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
<script>
 

</script>
<script type="text/javascript">
$(document).ready(function(){
	$(".js-example-basic-single").select2({
        minimumResultsForSearch: -1
	});
});

/* function doSubmit(){
	if($('#password').val() != $('#repassword').val()){
		alert('密码与再次输入密码不一致!');
		return false;
	}
	$('#endEntityForm').submit();
} */

function getArray(l){
	l = l.replace('[','');
	l = l.replace(']','');
	if(l.indexOf(', ') == -1 && l.trim() == '')
		return null;
	arrs = l.trim().split(', ');
	return arrs;  
}
function loadDn(){
	var sdns = "${tranProfileMap['SUBJECTDNFIELDORDER']}";
	var sdnArrs = getArray(sdns);
	var sdnReqs = "${tranProfileMap['SUBJECTDNFIELDORDERREQUIRED']}";
	var sdnReqArrs = getArray(sdnReqs);
	var sdnMods = "${tranProfileMap['SUBJECTDNFIELDORDERMODIFY']}";
	var sdnModArrs = getArray(sdnMods);
	var sdnNames = "${tranProfileMap['SUBJECTDNFIELDORDERNAME']}";
	var sdnNameArrs = getArray(sdnNames);
	var dnDiv = $('#dnDiv');
	var h5 = document.createElement("h5");
	h5.innerHTML = "证书主题(Subject DN)";
	dnDiv.append(h5);
	appendDiv(sdnArrs,sdnReqArrs,sdnNameArrs, dnDiv);
} 

function loadAltName(){
	var sdns = "${tranProfileMap['SUBJECTALTNAMEFIELDORDER']}";
	var sdnArrs = getArray(sdns);
	if(sdnArrs != null){
		var sdnReqs = "${tranProfileMap['SUBJECTALTNAMEFIELDORDERREQUIRED']}";
		var sdnReqArrs = getArray(sdnReqs);
		var sdnMods = "${tranProfileMap['SUBJECTALTNAMEFIELDORDERMODIFY']}";
		var sdnModArrs = getArray(sdnMods);
		var sdnNames = "${tranProfileMap['SUBJECTALTNAMEFIELDORDERNAME']}";
		var sdnNameArrs = getArray(sdnNames);
		var dnDiv = $('#altDiv');
		var h5 = document.createElement("h5");
		h5.innerHTML = "使用者可选名称(SUBJECT ALT NAME)";
		dnDiv.append(h5); 
		appendDiv(sdnArrs,sdnReqArrs,sdnNameArrs, dnDiv);
	}
} 

function loadDirName(){
	var sdns = "${tranProfileMap['SUBJECTDIRATTRFIELDORDER']}";
	var sdnArrs = getArray(sdns);
	if(sdnArrs != null){
		var sdnReqs = "${tranProfileMap['SUBJECTDIRATTRFIELDORDERREQUIRED']}";
		var sdnReqArrs = getArray(sdnReqs);
		var sdnMods = "${tranProfileMap['SUBJECTDIRATTRFIELDORDERMODIFY']}";
		var sdnModArrs = getArray(sdnMods);
		var sdnNames = "${tranProfileMap['SUBJECTDIRATTRFIELDORDERNAME']}";
		var sdnNameArrs = getArray(sdnNames);
		var dnDiv = $('#dirDiv');
		var h5 = document.createElement("h5"); 
		h5.innerHTML = "主题目录属性";
		dnDiv.append(h5); 
		appendDiv(sdnArrs,sdnReqArrs,sdnNameArrs, dnDiv);
	}
} 

function appendDiv(sdnArrs,sdnReqArrs,sdnNameArrs, pDiv){
	for(var i=0;i<sdnArrs.length;i++){
		var dnId = parseInt(parseInt(sdnArrs[i].trim()) / 100);
		var insetDiv = document.createElement("div");
		var controlGroup = document.createAttribute("class");  
		controlGroup.value = "form-group"; 
	    insetDiv.setAttributeNode(controlGroup);
	    var labelEle = document.createElement("label"); 
	    var controlLabel = document.createAttribute("for");  
	    controlLabel.value = "id_label_single1"; 
	    labelEle.setAttributeNode(controlLabel);
	    labelEle.innerHTML = sdnNameArrs[i] + '：';
	    insetDiv.appendChild(labelEle);
	    var controlsInput = document.createElement("input");
	    controlsInput.type = "text";
	    controlsInput.name = sdnArrs[i].trim();
	    
	    if(sdnReqArrs[i] == 'true'){
	    	controlsInput.required = 'required';
	    }
	    insetDiv.appendChild(controlsInput);
	    pDiv.append(insetDiv); 
	}
}

loadDn();
loadAltName();
//loadDirName(); 

</script>
</html>