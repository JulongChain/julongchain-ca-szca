<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../common/metaHead.jsp"%>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<%@include file="../common/header.jsp"%>
	<!-- END HEADER -->
	<!-- BEGIN CONTAINER -->
	<div class="page-container row-fluid">
		<!-- BEGIN SIDEBAR -->
		<%-- 使用jsp:include动态包含，可以向sideBar传递参数，确保对应菜单状态为设置为active  --%>
		<jsp:include page="../common/sideBar.jsp" flush="true">
			<jsp:param name="menuLevel1" value="ra" />
			<jsp:param name="menuLevel2" value="endEntityProfileList" />
			<jsp:param name="menuLevel3" value="" />
		</jsp:include>
		<!-- END SIDEBAR -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<div id="portlet-config" class="modal hide">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button"></button>
					<h3>portlet Settings</h3>
				</div>
				<div class="modal-body">
					<p>Here will be a configuration form</p>
				</div>
			</div>
			<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<!-- BEGIN PAGE CONTAINER-->
			<div class="container-fluid">
				<!-- BEGIN PAGE HEADER-->
				<div class="row-fluid">
					<div class="span12">
						<h3 class="page-title">
							编辑终端实体模板 <small></small>
						</h3>
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="index.html">终端实体模板</a> <span class="icon-angle-right"></span></li>
							<li><a href="#">编辑终端实体模板</a> <span class="icon-angle-right"></span></li>
							<li>
								<div id="dashboard-report-range" class="dashboard-date-range tooltips no-tooltip-on-touch-device responsive" data-tablet="" data-desktop="tooltips" data-placement="top"
									data-original-title="Change dashboard date range">
									<i class="icon-calendar"></i> <span></span> <i class="icon-angle-down"></i>
								</div>
							</li>
						</ul>
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->
				<div class="row-fluid">
					<div class="span12">
						<div class="portlet box blue" id="form_wizard_1">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-reorder"></i> 编辑终端实体模板 
								</div>
								<div class="tools hidden-phone">
									<a href="javascript:;" class="collapse"></a> <a href="#portlet-config" data-toggle="modal" class="config"></a> <a href="javascript:;" class="reload"></a> <a href="javascript:;" class="remove"></a>
								</div>
							</div>
							<div class="portlet-body form">
								<form action="handleEndEntityProfile.html" class="form-horizontal" id="submit_form" name="submit_form" method="post">
									<!-- caType: 1=X509,2=CVC -->
									<input type="hidden" name="profileId" value="${profileId }">									
									<input type="hidden" name="profileName" value="${profileName }">
									<div class="form-wizard">
										<div class="tab-content">
											<div class="alert alert-error hide">
												<button class="close" data-dismiss="alert"></button>
												某些输入有误， 请检查.
											</div>
											<div class="alert alert-success hide">
												<button class="close" data-dismiss="alert"></button>
												Your form validation is successful!
											</div>
											<div class="tab-pane active" id="tab1">
												<h4 class="block">实体基本信息</h4>
												<div class="control-group">
													<label class="control-label">模板名称：</label>
													<div class="controls">
														${profileName } 
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">模板Id：</label>
													<div class="controls">
														${profileId }
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label">用户名：</label>
													<div class="controls">
														<input type="checkbox" name="USERNAME" id="USERNAME" value="true"/>自动产生
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">密码：</label>
													<div class="controls">
														<input type="text" name="PASSWORD" size="1" id="PASSWORD" value="${profileMap['1'] }"/>
														&nbsp;&nbsp;<input type="checkbox" name="isRequiredPwd" id="isRequiredPwd" value="true"/>必需的
														&nbsp;&nbsp;<input type="checkbox" name="isUsePwd" id="isUsePwd" value="true" onchange="isUsePwdChange()"/>自动产生
													</div>
													<br/>   
													<div class="controls">
														<select name="selectautopasswordtype" id="selectautopasswordtype" size="1">
												            <option value="PWGEN_DIGIT">仅数字</option>
												            <option value="PWGEN_LETTERDIGIT">英文字母和数字</option>
												            <option value="PWGEN_ALLPRINTABLE">所有可打印的英文字符</option>
												            <option value="PWGEN_NOLOOKALIKELD">英文字母和数字除了“O0lI1”</option>
												            <option value="PWGEN_NOSOUNDALIKEENLD">英文字母和数字除了“ajeg”</option>
												            <option value="PWGEN_NOLOSALIKEENLD">英文字母和数字除了“O0lI1ajeg”</option>
												        </select>
												        &nbsp;&nbsp;长度：
												        <select name="selectautopasswordlength" id="selectautopasswordlength" size="1">
												            <option value="4">4 </option>
												            <option value="5">5</option>
												            <option value="6">6</option>
												            <option value="7">7</option>
												            <option value="8">8</option>
												            <option value="9">9</option>
												            <option value="10">10</option>
												            <option value="11">11</option>
												            <option value="12">12</option>
												            <option value="13">13</option>
												            <option value="14">14</option>
												            <option value="15">15</option>
												            <option value="16">16</option>
												        </select>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">最小密码长度：</label>
													<div class="controls">
														<input type="text" name="minpwdstrength" id="minpwdstrength" value="${profileMap['90'] }"/>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">最大失败登录次数：</label>
													<div class="controls">
														<input type="checkbox" name="usemaxfailedlogins" id="usemaxfailedlogins" value="true" onchange="maxFialedLoginsChange()"/>
														 &nbsp;&nbsp; &nbsp;&nbsp;<input type="radio" style="width: 20px" name="radiomaxfailedlogins" value="specified" checked="checked" onclick="maxFialedLoginsRadioChange(1)" id="radiomaxfailedloginsSpecail"/>
											             &nbsp;&nbsp; 默认 = <input type="text" name="maxfailedlogins" id="maxfailedlogins" value=""/>
											             &nbsp;&nbsp; &nbsp;&nbsp;<input type="radio" name="radiomaxfailedlogins" value="unlimited" onclick="maxFialedLoginsRadioChange(0)" id="radiomaxfailedloginsunlimited"/>无限制
														<input type="checkbox" name="modifyablemaxfailedlogins" value="true" id="modifyablemaxfailedlogins"/>可修改
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">允许批量签署证书(密码以明文方式保存)：</label>
													<div class="controls">
														<input type="checkbox" name="usecleartextpassword" id="usecleartextpassword" value="true" onclick="cleartextpasswordChange()"/>
											            <!-- &nbsp;&nbsp; 默认 = <input type="checkbox" name="cleartextpassword" value="true" id="cleartextpassword"/>  -->
														<input type="checkbox" name="requiredcleartextpassword" value="true" id="requiredcleartextpassword"/>必需的
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">邮箱地址：</label>
													<div class="controls">
												 		<input type="checkbox" name="useemail" id="useemail" value="true" onchange="useEmailChange()"/>使用
														<br/>
														<input type="text" name="email" id="email" value="${profileMap['26'] }" />
														<br/>
														<input type="checkbox" name="requiredemail" id="requiredemail" value="true" />必需的
														<input type="checkbox" name="modifyableemail" id="modifyableemail" value="true" />可修改
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label">反相主题/主题别名选项：</label>
													<div class="controls">
														<input type="checkbox" name="reversefieldchecks" id="reversefieldchecks" value="true"/>使用
													</div>
												</div> 
												
												<div class="control-group">
													<label class="control-label">允许合并DN接口：</label>
													<div class="controls">
														<input type="checkbox" name="allowmergednwebservices" id="allowmergednwebservices" value="true"/>使用
													</div>
												</div> 
												<h4 class="block">主题DN</h4>
												<input type="hidden" id="SUBJECTDNFIELDORDER" name="SUBJECTDNFIELDORDER" value="${profileMap['SUBJECTDNFIELDORDER'] }"/>
												<input type="hidden" id="NUMBERARRAY" name="NUMBERARRAY" value="${profileMap['NUMBERARRAY'] }"/>
												<input type="hidden" id="SUBJECTALTNAMEFIELDORDER" name="SUBJECTALTNAMEFIELDORDER" value="${profileMap['SUBJECTALTNAMEFIELDORDER'] }"/>
												<input type="hidden" id="SUBJECTDIRATTRFIELDORDER" name="SUBJECTDIRATTRFIELDORDER" value="${profileMap['SUBJECTDIRATTRFIELDORDER'] }"/>
												<div class="control-group">
													<label class="control-label">主题(DN)字段：</label>
													<div class="controls">
														<select name="selectaddsubjectdn" id="selectaddsubjectdn" size="1">
												            <c:forEach items="${dnMap }" var="dn">
																<option value="${dn.key }">${dn.value }</option>
															</c:forEach>
												        </select>
												        &nbsp;
												        <input type="button" name="buttonaddsubjectdn" onclick="addSubject(1)" value="添加">   
													</div>
												</div> 
												<div id="dnDiv"></div>
												
												<h4 class="block">主题别名SAN</h4>
												<div class="control-group">
													<label class="control-label">主题别名(SAN)字段：</label>
													<div class="controls">
														<select name="selectaddsubjectaltname" id="selectaddsubjectaltname" size="1">
												            <c:forEach items="${altMap }" var="san">
																<option value="${san.key }">${san.value }</option>
															</c:forEach>
												        </select>
												        &nbsp;
												        <input type="button" name="buttonaddsubjectdn" onclick="addSubject(2)" value="添加">   
													</div>
												</div> 
												<div id="sanDiv">													
												</div>
												
												<h4 class="block">主题目录属性</h4>
												<div class="control-group">
													<label class="control-label">主题目录属性：</label>
													<div class="controls">
														<select name="selectaddsubjectdirattr" id="selectaddsubjectdirattr" size="1">
												            <c:forEach items="${dirMap }" var="dir">
																<option value="${dir.key }">${dir.value }</option>
															</c:forEach>
												        </select>
												        &nbsp;
												        <input type="button" name="buttonaddsubjectdn" onclick="addSubject(3)" value="添加">   
													</div>
												</div> 
												<div id="dirDiv">													
												</div>
												
												<h4 class="block">主要证书信息</h4>
												<div class="control-group">
													<label class="control-label">默认证书模板</label>
													<div class="controls">
														<select id="selectdefaultcertprofile" name="selectdefaultcertprofile" >	
															<c:forEach items="${certificateProfileDatas }" var="aprofile">
																<option value="${aprofile.id }">${aprofile.certificateProfileName }</option>
															</c:forEach>
															<c:forEach items="${defaultCertificateProfileMap }" var="dprofile">
																<option value="${dprofile.key }">${dprofile.value }</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">可选证书模板</label>
													<div class="controls">
														<select id="selectavailablecertprofiles" name="selectavailablecertprofiles" multiple="multiple" size="5">	
															<c:forEach items="${certificateProfileDatas }" var="aprofile">
																<option value="${aprofile.id }">${aprofile.certificateProfileName }</option>
															</c:forEach>
															<c:forEach items="${defaultCertificateProfileMap }" var="dprofile">
																<option value="${dprofile.key }">${dprofile.value }</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">默认CA</label>
													<div class="controls">
														<select id="selectdefaultca" name="selectdefaultca" >	
															<c:forEach items="${cas }" var="ca">
																<option value="${ca.caId }">${ca.name }</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">可用CA</label>
													<div class="controls">
														<select id="selectavailablecas" name="selectavailablecas" multiple="multiple" size="5">	
															<option value="-1">任何CA</option>
															<c:forEach items="${cas }" var="ca">
																<option value="${ca.caId }">${ca.name }</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">默认密钥方式</label>
													<div class="controls">
														<select id="selectdefaulttokentype" name="selectdefaulttokentype" >	
															<option value="1">用户自己产生</option>
												           	<option value="2">P12文件</option> 
												           	<option value="3">JKS文件</option> 
												          	<option value="4">PEM文件</option>
														</select>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">可用密钥方式</label>
													<div class="controls">
														<select id="selectavailabletokentypes" name="selectavailabletokentypes" multiple="multiple">	
															<option value="1">用户自己产生</option>
												           	<option value="2">P12文件</option> 
												           	<option value="3">JKS文件</option> 
												          	<option value="4">PEM文件</option>
														</select>
													</div>
												</div>
												
												<h4 class="block">其他自定义配置</h4>
												<div class="control-group">
													<label class="control-label">自定义证书序列号</label>
													<div class="controls">
														<input type="checkbox" name="checkboxusecertserialonr" id="checkboxusecertserialonr" value="true"/>使用
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">自定义证书扩展项</label>
												 	<div class="controls">
														<input type="checkbox" name="checkboxuseextensiondata" id="checkboxuseextensiondata" value="true"/>使用
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">证书有效开始时间</label>
													<div class="controls">
														<input type="checkbox" name="checkboxsusestarttime" id="checkboxsusestarttime" value="true" onchange="changeStartTime()"/>使用:Value
														<input type="text" name="textfieldstarttime" id="textfieldstarttime" value="${profileMap['98']}"/>
														<input type="checkbox" name="checkboxmodifyablestarttime" id="checkboxmodifyablestarttime" value="true"/>可修改
														<BR/>
														(ISO 8601 date: [yyyy-MM-dd HH:mm:ssZZ]: '2018-05-28 22:44:57-07:00' or 天:小时:分钟)
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">证书有效结束时间</label>
													<div class="controls">
														<input type="checkbox" name="checkboxsuseendtime" id="checkboxsuseendtime" value="true" onchange="changeEndTime()"/>使用:Value
														<input type="text" name="textfieldendtime" id="textfieldendtime" value="${profileMap['99']}"/>
														<input type="checkbox" name="checkboxmodifyableendtime" id="checkboxmodifyableendtime" value="true"/>可修改
														<BR/>
														(ISO 8601 date: [yyyy-MM-dd HH:mm:ssZZ]: '2018-05-28 22:44:57-07:00' or 天:小时:分钟)
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">介质编号</label>
													<div class="controls">
														<input type="checkbox" name="checkboxusecardnumber" id="checkboxusecardnumber" value="true" onchange="changeUse('checkboxusecardnumber','checkboxrequiredcardnumber')"/>使用
														<input type="checkbox" name="checkboxrequiredcardnumber" id="checkboxrequiredcardnumber" value="true"/>必需的
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">名称限制-允许</label>
													<div class="controls">
														<input type="checkbox" name="checkboxusencpermitted" id="checkboxusencpermitted" value="true" onchange="changeUse('checkboxusencpermitted','checkboxrequiredncpermitted')"/>使用
														<input type="checkbox" name="checkboxrequiredncpermitted" id="checkboxrequiredncpermitted" value="true"/>必需的
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">名称限制-拒绝</label>
													<div class="controls">
														<input type="checkbox" name="checkboxusencexcluded" id="checkboxusencexcluded" value="true" onchange="changeUse('checkboxusencexcluded','checkboxrequiredncexcluded')"/>使用
														<input type="checkbox" name="checkboxrequiredncexcluded" id="checkboxrequiredncexcluded" value="true"/>必需的
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">允许请求数</label>
													<div class="controls">
														<input type="checkbox" name="checkboxuseallowedrequests" id="checkboxuseallowedrequests" value="true"/>使用：默认 = 
														<select name="selectallowedrequests" id="selectallowedrequests" size="1">
													        <option value="1">1</option>
													        <option value="2">2</option>
													        <option value="3">3</option>
													        <option value="4">4</option>
													        <option value="5">5</option>
												        </select>
													</div>
												</div>
											</div>
										</div>
										<div class="form-actions clearfix">
											<input type="button" class="btn blue" onclick="validateAll();submit_form.submit()" value="提交"/>
											<input type="button" class="btn blue" onclick="history.go(-1);" value="返回"/>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				<!-- END PAGE CONTENT-->
			</div>
			<!-- END PAGE CONTAINER-->
		</div>
		<!-- END PAGE -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@include file="../common/footer.jsp"%>
	<!-- END FOOTER -->
</body> 

<script src="${pageContext.request.contextPath}/media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script>  

	 
	function validateAll(){
		
	}
	
	function checkBoxInit(flag, id){
		if(flag == 'true'){
			$('#' + id).prop("checked", true);
		}
	} 
	
	function multipleSelected(vals,selectId){
		var arr = vals.split(";");
		var sel = document.getElementById(selectId);
		var len=sel.options.length;
		for(var i=0;i<arr.length;i++){
			for(var j=0;j<len;j++){
		        if(sel.options[j].value == arr[i].trim()){
		            sel.options[j].setAttribute("selected",true);
		            break;
		        }
		    }
		} 
	}
	 
	function isUsePwdChange(){
		if($('#isUsePwd').prop('checked') == true){
			$('#PASSWORD').prop("value","");
			$('#PASSWORD').attr("disabled",true);
			$('#isRequiredPwd').attr("disabled",false);
			$('#selectautopasswordtype').attr("disabled",false);
			$('#selectautopasswordlength').attr("disabled",false);
		}else{
			$('#PASSWORD').attr("disabled",false);
			$('#isRequiredPwd').attr("disabled", false);
			$('#selectautopasswordtype').attr("disabled", true);
			$('#selectautopasswordlength').attr("disabled", true);
		}
	}
	
	function maxFialedLoginsChange(){
		if($('#usemaxfailedlogins').prop('checked') == true){
			$('#maxfailedlogins').attr("disabled",false);
			$('#radiomaxfailedloginsSpecail').attr("disabled",false);
			$('#radiomaxfailedloginsunlimited').attr("disabled",false);
			$('#modifyablemaxfailedlogins').attr("disabled",false);
		}else{
			$('#maxfailedlogins').prop("value","");
			$('#maxfailedlogins').attr("disabled",true);
			$('#radiomaxfailedloginsSpecail').attr("disabled",true);
			$('#modifyablemaxfailedlogins').prop("checked",false);
			$('#radiomaxfailedloginsunlimited').attr("disabled",true);
			$('#modifyablemaxfailedlogins').attr("disabled",true);
		}
	}
	
	function maxFialedLoginsRadioChange(type){
		if(type == 0){
			$('#maxfailedlogins').prop("value","");
			$('#maxfailedlogins').attr("disabled",true);
		}else{
			$('#maxfailedlogins').attr("disabled",false);
		}
	}
	
	function cleartextpasswordChange(){ 
		if($('#usecleartextpassword').prop('checked') == true){
			//$('#cleartextpassword').attr("disabled",false); 
			$('#requiredcleartextpassword').attr("disabled",false);
		}else{
			//$('#cleartextpassword').prop("checked",false); 
			$('#requiredcleartextpassword').prop("checked",false);
			//$('#cleartextpassword').attr("disabled",true); 
			$('#requiredcleartextpassword').attr("disabled",true);
 			
		}  
	} 
	
	function useEmailChange(){
		if($('#useemail').prop('checked') == true){
			$('#email').attr("disabled",false);
			$('#requiredemail').attr("disabled",false);
			$('#modifyableemail').attr("disabled",false);
		}else{
			$('#email').prop("value","");
			$('#requiredemail').attr("checked",false);
			$('#modifyableemail').attr("checked",false);
			$('#email').attr("disabled",true);
			$('#requiredemail').attr("disabled",true);
			$('#modifyableemail').attr("disabled",true);
		} 
	}
	
	function loadSubject(typeName,selectId, divId, orderId){
		var opts = $('#' + selectId + ' option');
		var m = new Map();
		for(var i=0;i<opts.length;i++){
			var opt = opts[i];
			m.set(opt.value, opt.text); 
		}
 		
		var dnDiv = $('#' + divId);
		var dnOrder = $('#' + orderId).val();
		dnOrder = dnOrder.replace('[','');
		dnOrder = dnOrder.replace(']','');
		var dnarr = dnOrder.split(','); 
		var jsonObj =  eval('(${subjectOrderValue })');
		for(var i=0;i<dnarr.length;i++){
			if(dnarr[i].trim() == '')
				continue;
			var dnId = parseInt(parseInt(dnarr[i].trim()) / 100);
			var insetDiv = document.createElement("div");
			var controlGroup = document.createAttribute("class");  
			controlGroup.value = "control-group"; 
		    insetDiv.setAttributeNode(controlGroup);
		    var labelEle = document.createElement("label");
		    var controlLabel = document.createAttribute("class");  
		    controlLabel.value = "control-label"; 
		    labelEle.setAttributeNode(controlLabel);
		    labelEle.innerHTML = m.get('' + dnId) + '：';
		    insetDiv.appendChild(labelEle);
		    var controlsDiv = document.createElement("div");
		    var controls = document.createAttribute("class");   
		    controls.value = "controls"; 
		    controlsDiv.setAttributeNode(controls);  
			if(typeName == 'subjectdn' && dnId == 3){
			    var checkboxrequiredemail = document.createElement("input");
			    checkboxrequiredemail.type="checkbox";
			    checkboxrequiredemail.name="checkboxrequired" + typeName + i;
			    checkboxrequiredemail.id="checkboxrequired" + typeName + i;
			    checkboxrequiredemail.value="true";
			    if(jsonObj['checkboxrequired' + dnarr[i].trim()] == true){
			    	checkboxrequiredemail.checked = true;  
			    }
			    controlsDiv.appendChild(checkboxrequiredemail);
			    controlsDiv.appendChild(document.createTextNode('必需的      ')); 
			    var removeDnBtn = document.createElement("input");
			    removeDnBtn.type="button"; 
			    removeDnBtn.value="删除"; 
			    removeDnBtn.name=dnarr[i].trim();
			    removeDnBtn.onclick=function (){ 
			    	removeSubject(this.name);
			    }; 
			    controlsDiv.appendChild(removeDnBtn); 
			    insetDiv.appendChild(controlsDiv);  
			    dnDiv.append(insetDiv); 
			    continue;
			}
			var changeLine = document.createElement("br"); 
			var isSanEmailUser = true;
			if(typeName == 'subjectaltname' && dnId == 17){
			    var userEntityEmail = document.createElement("input");
			    userEntityEmail.type="checkbox";
			    userEntityEmail.name="checkboxuse" + typeName + i;
			    userEntityEmail.id="checkboxuse" + typeName + i;
			    userEntityEmail.value="true";
			    userEntityEmail.onchange = function (){ 
			    	changeSanEmailUse(this.name);
			    }; 
			    if(jsonObj['checkboxuse' + dnarr[i].trim()] == true){
			    	userEntityEmail.checked = true;  
			    	isSanEmailUser = false;
			    }
			    controlsDiv.appendChild(userEntityEmail);
			    controlsDiv.appendChild(document.createTextNode('使用实体邮箱地址      '));
			    controlsDiv.appendChild(changeLine); 
			}
		    var textfieldsubjectdn = document.createElement("input");
		    textfieldsubjectdn.type="text";
		    textfieldsubjectdn.name="textfield" + typeName + i;
		    textfieldsubjectdn.id="textfield" + typeName + i;
		    textfieldsubjectdn.value=jsonObj['textfield' + dnarr[i].trim()];
		    if(dnId == 17 && !isSanEmailUser){
		    	textfieldsubjectdn.value = '';
		    	textfieldsubjectdn.disabled = 'disabled';
		    }
		    controlsDiv.appendChild(textfieldsubjectdn); 
		    var removeDnBtn = document.createElement("input");
		    removeDnBtn.type="button"; 
		    removeDnBtn.value="删除"; 
		    removeDnBtn.name=dnarr[i].trim();
		    removeDnBtn.onclick=function (){ 
		    	removeSubject(this.name);
		    }; 
		    controlsDiv.appendChild(removeDnBtn); 
		    
		    var changeLine1 = document.createElement("br"); 
		    controlsDiv.appendChild(changeLine1); 
		    //必需的 
		    var checkboxrequiredsubjectdn = document.createElement("input");
		    checkboxrequiredsubjectdn.type="checkbox";
		    checkboxrequiredsubjectdn.name="checkboxrequired" + typeName + i;
		    checkboxrequiredsubjectdn.id="checkboxrequired" + typeName + i;
		    checkboxrequiredsubjectdn.value="true";
		    if(jsonObj['checkboxrequired' + dnarr[i].trim()] == true){
		    	checkboxrequiredsubjectdn.checked = true;  
		    }
		    controlsDiv.appendChild(checkboxrequiredsubjectdn);
		    controlsDiv.appendChild(document.createTextNode('必需的      ')); 
		    //if(typeName != 'subjectdirattr'){
		    	//可修改的
		    	var modifyId = typeName + i;
			    var checkboxmodifyablesubjectdn = document.createElement("input");
			    checkboxmodifyablesubjectdn.type="checkbox";
			    checkboxmodifyablesubjectdn.name="checkboxmodifyable" + typeName + i;
			    checkboxmodifyablesubjectdn.id="checkboxmodifyable" + typeName + i;
			    checkboxmodifyablesubjectdn.value="true";
			    /* checkboxmodifyablesubjectdn.onchange = function (){ 
			    	changeSubjectModifyable(this.name);
			    };  */
			    var isModify = false;
			    if(dnId == 17 && !isSanEmailUser){
			    	checkboxmodifyablesubjectdn.checked = false;  
			    	checkboxmodifyablesubjectdn.disabled = 'disabled';
			    }else if(jsonObj['checkboxmodifyable' + dnarr[i].trim()] == true){
			    	checkboxmodifyablesubjectdn.checked = true;
			    	isModify = true;
			    }
			    controlsDiv.appendChild(checkboxmodifyablesubjectdn);
			    controlsDiv.appendChild(document.createTextNode('可修改      ')); 
			  //验证规则
			   /*  var checkboxvalidationsubjectdn = document.createElement("input");
			    checkboxvalidationsubjectdn.type="checkbox";
			    checkboxvalidationsubjectdn.name="checkboxvalidation" + typeName + i;
			    checkboxvalidationsubjectdn.id="checkboxvalidation" + typeName + i;
			    checkboxvalidationsubjectdn.value="true";
			    if(dnId == 17 && !isSanEmailUser){
			    	checkboxvalidationsubjectdn.checked = false;  
			    	checkboxvalidationsubjectdn.disabled = 'disabled';
			    }
			    if(!isModify){
			    	checkboxvalidationsubjectdn.checked = false;
			    	checkboxvalidationsubjectdn.disabled = 'disabled';  	
			    }else if(jsonObj['checkboxvalidation' + dnarr[i].trim()] == true)
			    	checkboxvalidationsubjectdn.checked = true; 
	
			    
			    controlsDiv.appendChild(checkboxvalidationsubjectdn);
			    controlsDiv.appendChild(document.createTextNode('验证      '));   
			    var textfieldsubjectdnvalidation = document.createElement("input");
			    textfieldsubjectdnvalidation.type="text";
			    textfieldsubjectdnvalidation.name="textfield" + typeName + "validation" + i;
			    textfieldsubjectdnvalidation.id="textfield" + typeName + "validation" + i;
			    textfieldsubjectdnvalidation.value=jsonObj['textfield' + dnarr[i].trim() + "validation"];
			    if(dnId == 17 && !isSanEmailUser){
			    	textfieldsubjectdnvalidation.value = '';  
			    	textfieldsubjectdnvalidation.disabled = 'disabled';
			    }
			    if(!isModify){
			    	textfieldsubjectdnvalidation.value = '';
			    	checkboxvalidationsubjectdn.disabled = 'disabled';  	
			    }
			    controlsDiv.appendChild(textfieldsubjectdnvalidation);  */
		   /*  }else{
		    	//可修改的
			    var checkboxmodifyablesubjectdn = document.createElement("input");
			    checkboxmodifyablesubjectdn.type="checkbox";
			    checkboxmodifyablesubjectdn.name="checkboxmodifyable" + typeName + i;
			    checkboxmodifyablesubjectdn.id="checkboxmodifyable" + typeName + i;
			    checkboxmodifyablesubjectdn.value="true";
			    if(jsonObj['checkboxmodifyable' + dnarr[i].trim()] == true){
			    	checkboxmodifyablesubjectdn.checked = true;  
			    }
			    controlsDiv.appendChild(checkboxmodifyablesubjectdn);
			    controlsDiv.appendChild(document.createTextNode('可修改      ')); 
		    } */
		    insetDiv.appendChild(controlsDiv);  
		    dnDiv.append(insetDiv);  
		} 
	} 
	
	
	
	function removeSubject(dnOrder){
		window.location.href ="${pageContext.request.contextPath}/ra/removeSubject.html?dnOrder=" + dnOrder + "&profileName=${profileName }";
	}
	
	function addSubject(type){
		var id = '';
		if(type == 1) id = $('#selectaddsubjectdn').val();
		if(type == 2) id = $('#selectaddsubjectaltname').val();
		if(type == 3) id = $('#selectaddsubjectdirattr').val();
		window.location.href ="${pageContext.request.contextPath}/ra/addSubject.html?dnOrder=" + id + "&profileName=${profileName } ";
	}
	
	/* function changeSubjectModifyable(typeName){
		if(typeName.indexOf('subjectdn') != -1){
			var i = typeName.replace('checkboxmodifyablesubjectdn', '');
			if($('#' + typeName).prop('checked') == true){
				$('#checkboxvalidationsubjectdn' + i).attr("disabled",false); 
				$('#textfieldsubjectdnvalidation' + i).attr("disabled",false); 
			}else{
				$('#checkboxvalidationsubjectdn' + i).prop("checked",false); 
				$('#textfieldsubjectdnvalidation' + i).prop("value",'');
				$('#checkboxvalidationsubjectdn' + i).attr("disabled",true); 
				$('#textfieldsubjectdnvalidation' + i).attr("disabled",true);  
			}
		}
		if(typeName.indexOf('subjectaltname') != -1){
			var i = typeName.replace('checkboxmodifyablesubjectaltname', '');
			if($('#' + typeName).prop('checked') == true){
				$('#checkboxvalidationsubjectaltname' + i).attr("disabled",false); 
				$('#textfieldsubjectaltnamevalidation' + i).attr("disabled",false); 
			}else{
				$('#checkboxvalidationsubjectaltname' + i).prop("checked",false); 
				$('#textfieldsubjectaltnamevalidation' + i).prop("value",'');
				$('#checkboxvalidationsubjectaltname' + i).attr("disabled",true); 
				$('#textfieldsubjectaltnamevalidation' + i).attr("disabled",true);  
			}
		}
	} */
	
	function changeSanEmailUse(typeName){
		var i = typeName.replace('checkboxusesubjectaltname', '');
		if($('#' + typeName).prop('checked') == true){
			$('#textfieldsubjectaltname' + i).prop("value",'');
			$('#textfieldsubjectaltname' + i).attr("disabled",true);
			$('#checkboxmodifyablesubjectaltname' + i).prop("checked",false); 
			$('#checkboxmodifyablesubjectaltname' + i).attr("disabled",true); 
			$('#checkboxvalidationsubjectaltname' + i).prop("checked",false);
			$('#checkboxvalidationsubjectaltname' + i).attr("disabled",true);
			$('#textfieldsubjectaltnamevalidation' + i).prop("value",''); 
			$('#textfieldsubjectaltnamevalidation' + i).attr("disabled",true); 
		}else{
			$('#textfieldsubjectaltname' + i).attr("disabled",false); 
			$('#checkboxmodifyablesubjectaltname' + i).attr("disabled",false); 
			if($('#checkboxmodifyablesubjectaltname' + i).prop('checked') == true){
				$('#checkboxvalidationsubjectaltname' + i).attr("disabled",false);
				$('#textfieldsubjectaltnamevalidation' + i).attr("disabled",false);  
			}
		}
	}
	
	function changeStartTime(){
		if($('#checkboxsusestarttime').prop('checked') == true){
			$('#textfieldstarttime').attr("disabled",false);
			$('#checkboxmodifyablestarttime').attr("disabled",false);
		}else{
			$('#textfieldstarttime').prop("value",'');
			$('#checkboxmodifyablestarttime').prop("checked",false);
			$('#textfieldstarttime').attr("disabled",true);
			$('#checkboxmodifyablestarttime').attr("disabled",true);
		}
	}
	
	function changeEndTime(){
		if($('#checkboxsuseendtime').prop('checked') == true){
			$('#textfieldendtime').attr("disabled",false);
			$('#checkboxmodifyableendtime').attr("disabled",false);
		}else{
			$('#textfieldendtime').prop("value",'');
			$('#checkboxmodifyableendtime').prop("checked",false);
			$('#textfieldendtime').attr("disabled",true);
			$('#checkboxmodifyableendtime').attr("disabled",true);
		}
	}
	
	function changeUse(useId, modifyId){
		if($('#' + useId).prop('checked') == true){
			$('#' + modifyId).attr("disabled",false);
		}else{
			$('#' + modifyId).prop("checked",false);
			$('#' + modifyId).attr("disabled",true);
		}
	}
	 
	//用户名
	var isUserNameModifyable = "${profileMap['30000']}";  
	if(isUserNameModifyable == 'false')
		checkBoxInit('true', 'USERNAME'); 
	//密码
	var isRequiredPwd = "${profileMap['20001']}"; 
	checkBoxInit(isRequiredPwd, 'isRequiredPwd'); 
	var isUsePwd = "${profileMap['30001']}"; 
	if(isUsePwd == 'false')
		checkBoxInit('true', 'isUsePwd'); 
	isUsePwdChange();
	$('#selectautopasswordtype').val("${profileMap['95']}");
	$('#selectautopasswordlength').val("${profileMap['96']}");
	//最大登陆失败次数
	var usemaxfailedlogins = "${profileMap['10093']}";
	checkBoxInit(usemaxfailedlogins, 'usemaxfailedlogins');
	if(usemaxfailedlogins == 'true'){
		var modifyablemaxfailedlogins = "${profileMap['30093']}";
		checkBoxInit(modifyablemaxfailedlogins, 'modifyablemaxfailedlogins');
		var maxfailedlogins = "${profileMap['93']}"
		if(maxfailedlogins == -1){
			$('#maxfailedlogins').val('');
			$("input[type=radio][name=radiomaxfailedlogins][value=unlimited]").attr("checked",'checked');
			maxFialedLoginsRadioChange(0); 
		}else{
			$('#maxfailedlogins').val(maxfailedlogins);
		}
	}
	maxFialedLoginsChange();
	//批量签署
	var usecleartextpassword = "${profileMap['10002']}";
	var requiredcleartextpassword = "${profileMap['20002']}";
	checkBoxInit(usecleartextpassword, 'usecleartextpassword');
	checkBoxInit(requiredcleartextpassword, 'requiredcleartextpassword');
	cleartextpasswordChange();
	 
	//邮箱
	var useemail = "${profileMap['10026']}";
	checkBoxInit(useemail, 'useemail');
	var requiredemail = "${profileMap['20026']}"; 
	checkBoxInit(requiredemail, 'requiredemail');
	var modifyableemail = "${profileMap['30026']}";
	checkBoxInit(modifyableemail, 'modifyableemail');
	useEmailChange();
	
	//反相主题及主题别名选项
	var reversefieldchecks = "${profileMap['REVERSEFFIELDCHECKS']}";
	checkBoxInit(reversefieldchecks, 'reversefieldchecks');
	//允许合并DN接口
	var allowmergednwebservices = "${profileMap['ALLOW_MERGEDN_WEBSERVICES']}";
	checkBoxInit(allowmergednwebservices, 'allowmergednwebservices');
	loadSubject("subjectdn","selectaddsubjectdn","dnDiv","SUBJECTDNFIELDORDER"); 
	loadSubject("subjectaltname","selectaddsubjectaltname","sanDiv","SUBJECTALTNAMEFIELDORDER"); 
	loadSubject("subjectdirattr","selectaddsubjectdirattr","dirDiv","SUBJECTDIRATTRFIELDORDER");
	
	//证书模板
	var defaultProfile = "${profileMap['29']}";
	$('#selectdefaultcertprofile').prop('value',defaultProfile); 
	var availProfiles = "${profileMap['30']}";
	multipleSelected(availProfiles, 'selectavailablecertprofiles');
	
	//CA
	var defaultca = "${profileMap['37']}";
	$('#selectdefaultca').prop('value',defaultca); 
	var availcas = "${profileMap['38']}";
	multipleSelected(availcas, 'selectavailablecas');
	//密钥方式
	var defaulttokentype = "${profileMap['31']}";
	$('#selectdefaulttokentype').prop('value',defaulttokentype); 
	var availtokentypes = "${profileMap['32']}";
	multipleSelected(availtokentypes, 'selectavailabletokentypes');
	
	//自定义证书序列号
	var certserialno = "${profileMap['10092']}";
	checkBoxInit(certserialno, 'checkboxusecertserialonr');
	//证书有效开始/结束时间
	var usestarttime = "${profileMap['10098']}";
	checkBoxInit(usestarttime, 'checkboxsusestarttime');
	var useendtime = "${profileMap['10099']}";
	checkBoxInit(useendtime, 'checkboxsuseendtime');
	$('#textfieldstarttime').val("${profileMap['98']}");
	$('#textfieldendtime').val("${profileMap['99']}");
	var moditystarttime = "${profileMap['30098']}";
	checkBoxInit(moditystarttime, 'checkboxmodifyablestarttime');
	var modifyendtime = "${profileMap['30099']}";
	checkBoxInit(modifyendtime, 'checkboxmodifyableendtime');
	changeStartTime();
	changeEndTime();
	//介质编号
	var usecardnum = "${profileMap['10091']}";
	checkBoxInit(usecardnum, 'checkboxusecardnumber');
	var requiredcardnum = "${profileMap['20091']}";
	checkBoxInit(requiredcardnum, 'checkboxrequiredcardnumber');
	changeUse('checkboxusecardnumber','checkboxrequiredcardnumber');
	//名称限制，允许
	var usencpermitted = "${profileMap['10089']}";
	checkBoxInit(usencpermitted, 'checkboxusencpermitted');
	var requiredncpermitted = "${profileMap['20089']}";
	checkBoxInit(requiredncpermitted, 'checkboxrequiredncpermitted');
	changeUse('checkboxusencpermitted','checkboxrequiredncpermitted');
	//名称限制，拒绝
	var usencexcluded = "${profileMap['10088']}";
	checkBoxInit(usencexcluded, 'checkboxusencexcluded');
	var requiredncexcluded = "${profileMap['20088']}";
	checkBoxInit(requiredncexcluded, 'checkboxrequiredncexcluded');
	changeUse('checkboxusencexcluded','checkboxrequiredncexcluded');
	//自定义证书扩展项
	var useextensiondata = "${profileMap['USEEXTENSIONDATA']}";
	checkBoxInit(useextensiondata, 'checkboxuseextensiondata');
	//允许请求数
	var useallowedrequests = "${profileMap['10097']}";
	checkBoxInit(useallowedrequests, 'checkboxuseallowedrequests');
	var allowedrequests = "${profileMap['97']}";
	$('#selectallowedrequests').val(allowedrequests);
	
</script>

</html>