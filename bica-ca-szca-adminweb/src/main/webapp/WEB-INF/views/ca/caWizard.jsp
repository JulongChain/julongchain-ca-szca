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
			<jsp:param name="menuLevel1" value="ca" />
			<jsp:param name="menuLevel2" value="caWizard" />
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
							Form Wizard <small>form wizard sample</small>
						</h3>
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="index.html">Home</a> <span class="icon-angle-right"></span></li>
							<li><a href="#">Form Stuff</a> <span class="icon-angle-right"></span></li>
							<li><a href="#">Form Wizard</a></li>
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
									<i class="icon-reorder"></i> 创建CA - <span class="step-title">Step 1 of 4</span>
								</div>
								<div class="tools hidden-phone">
									<a href="javascript:;" class="collapse"></a> <a href="#portlet-config" data-toggle="modal" class="config"></a> <a href="javascript:;" class="reload"></a> <a href="javascript:;" class="remove"></a>
								</div>
							</div>
							<div class="portlet-body form">
								<form action="createNewCA.html" class="form-horizontal" id="submit_form" name="submit_form" method="post">
									<!-- caType: 1=X509,2=CVC -->
									<input type="hidden" name="caType" value="1">
									<input type="hidden" name="SignKeySpec" value="2048">
									
									 
                
									<div class="form-wizard">
										<div class="navbar steps">
											<div class="navbar-inner">
												<ul class="row-fluid">
													<li class="span3"><a href="#tab1" data-toggle="tab" class="step active"> <span class="number">1</span> <span class="desc"><i class="icon-ok"></i> 配置密钥</span>
													</a></li>
													<li class="span3"><a href="#tab2" data-toggle="tab" class="step"> <span class="number">2</span> <span class="desc"><i class="icon-ok"></i>配置CA证书</span>
													</a></li>
													<li class="span3"><a href="#tab3" data-toggle="tab" class="step"> <span class="number">3</span> <span class="desc"><i class="icon-ok"></i>配置CRL</span>
													</a></li>
													<li class="span3"><a href="#tab4" data-toggle="tab" class="step"> <span class="number">4</span> <span class="desc"><i class="icon-ok"></i> 确认配置</span>
													</a></li>
												</ul>
											</div>
										</div>
										<div id="bar" class="progress progress-success progress-striped">
											<div class="bar"></div>
										</div>
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
												<h3 class="block">配置密钥信息</h3>
												<div class="control-group">
													<label class="control-label">CA名称</label>
													<div class="controls">
														<input type="text" name="caName" value="" data-title="caName" />
														<span class="help-inline"></span>
													</div>
												</div>
												<!-- 
												<div class="control-group">
													<label class="control-label">CA类型<span class="required">*</span></label>
													<div class="controls">
														<label class="radio"> <input type="radio" name="caType" value="X500" data-title="X500" checked /> X500
														</label>
														<div class="clearfix"></div>
														<label class="radio"> <input type="radio" name="caType" value="SM2" data-title="SM2" /> SM2
														</label> <span class="help-inline"></span>
													</div>
												</div>
												 -->
												<div class="control-group">
													<label class="control-label">密钥签名算法<span class="required">*</span></label>
													<div class="controls">
													<%--
														<label class="radio"> <input type="radio" name="signatureAlgorithm" value="SHA1WithRSA" data-title="SHA1WithRSA111" /> SHA1WithRSA
														</label>
														<div class="clearfix"></div>
														<label class="radio"> <input type="radio" name="signatureAlgorithm" value="SHA256WithRSA" data-title="SHA256WithRSA222" checked /> SHA256WithRSA
														</label> <span class="help-inline"></span>
														 --%>
														<select name="signatureAlgorithm" id="signatureAlgorithm_list" class="span6">
															 <option value="SHA256WithRSA">SHA256WithRSA</option>
															 <option value="SHA1WithRSA">SHA1WithRSA</option>
															 <option value="SHA256WithRSA">SHA256WithECDSA</option>
															 <option value="SHA1WithRSA">SHA1WithECDSA</option>
															 <option value="SM3WithSM2">SM3WithSM2</option>
															</select>
															
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">密钥<span class="required">*</span></label>
													<div class="controls">
														<c:if test="${!empty keyList}">
															<select name="cryptoTokenId" id="cryptoTokenId_list" class="span6">
																<c:forEach items="${keyList}" var="key">
																	<option value="${key.id}">${key.id}-${key.tokenName}</option>
																</c:forEach>
															</select>
															<span class="help-inline"></span>
														</c:if>
														<c:if test="${empty  keyList}">
															<span class="help-inline">此系统尚未配置密钥，<a href="${pageContext.request.contextPath}/cryptoToken/cryptoTokenList.html">配置密钥</a></span>
														</c:if>
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label">默认密钥别名</label>
													<div class="controls">
														<input type="text" name="keyAliasDefaultKey" value="" data-title="keyAliasDefaultKey" />
														<span class="help-inline"></span>
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label">签名密钥别名 KeyAliasCertSignKey</label>
													<div class="controls">
														<input type="text" name="keyAliasCertSignKey" value="" data-title="keyAliasCertSignKey" />
														<span class="help-inline"></span>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">加密机密钥别名 KeyAliasHardTokenEncryptKey</label>
													<div class="controls">
														<input type="text" name="keyAliasHardTokenEncryptKey" value="" data-title="keyAliasHardTokenEncryptKey" />
														<span class="help-inline"></span>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">加密密钥别名 KeyAliasKeyEncryptKey</label>
													<div class="controls">
														<input type="text" name="keyAliasKeyEncryptKey" value="" data-title="keyAliasKeyEncryptKey" />
														<span class="help-inline"></span>
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label">测试密钥别名 KeyAliasKeyTestKey</label>
													<div class="controls">
														<input type="text" name="keyAliasKeyTestKey" value="" data-title="keyAliasKeyTestKey" />
														<span class="help-inline"></span>
													</div>
												</div>
											 
											</div>
											<div class="tab-pane" id="tab2">
												<h3 class="block">配置CA证书信息</h3>
												<div class="control-group">
													<label class="control-label">证书主题(Subject DN)<span class="required">*</span></label>
													<div class="controls">
														<input type="text" class="span6 m-wrap" name="subjectDn" /> <span class="help-inline"></span>
													</div>
												</div>
												 
									
									
												<div class="control-group">
													<label class="control-label">CA证书的颁发者<span class="required">*</span></label>
													<div class="controls">
													<!-- signedBy: 1=Self Signed, 2=External CA -->
													<!-- <input type="hidden" name="signedBy" value="1"> -->
														<select name="signedBy" id="issuerCaId_list" class="span6" onchange="loadCertProfile()">
															<option value="1">自签名CA证书</option>
															<c:forEach items="${caList}" var="ca">
																<option value="${ca.caId}">${ca.caId}-${ca.name}</option>
															</c:forEach>
														</select> <span class="help-inline"></span>
														<!-- certificateProfileId: 2=(SubCAs) SUBCA, 3=(RootCAs) ROOTCA 
														<input type="hidden" name="certificateProfileId" value="3">
														-->
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label">证书模板<span class="required">*</span></label>
													<div class="controls">
														<select name="certificateProfileId" id="certificateProfileId_list" class="span6">
															
														</select>
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label">有效期</label>
													<div class="controls">
														<input type="text" class="span6 m-wrap" name="validityString" value="3650"/> <span class="help-inline">输入yyyy-mm-dd格式的有效期结束日期,或者直接输入有效期天数</span>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">主题别名</label>
													<div class="controls">
														<input type="text" class="span6 m-wrap" name="subjectAltName" /> <span class="help-inline"></span>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">证书策略ID</label>
													<div class="controls">
														<input type="text" class="span6 m-wrap" name="policyId" /> <span class="help-inline"></span>
													</div>
												</div>
											</div>
											<div class="tab-pane" id="tab3">
												<h3 class="block">配置CRL信息</h3>
												<div class="control-group">
													<label class="control-label">CRL周期</label>
													<div class="controls">
														<input type="text" class="span6 m-wrap" name="crlPeriod" value="1"/> <span class="help-inline"></span>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">CA密钥标识</label>
													<div class="controls">
														<!-- 
														<input type="text" class="span6 m-wrap" name="akid" />
														 -->
														<label class="radio"> <input type="checkbox" name="useAuthorityKeyIdentifier" value="true" data-title="使用" /> 使用
														</label>
														<div class="clearfix"></div>
														<label class="radio"> <input type="checkbox" name="akid" value="critical" data-title="关键"  /> 关键
														</label> <span class="help-inline"></span>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">CRL序号</label>
													<div class="controls">
														<label class="radio"> <input type="checkbox" name="crlNum" value="use" data-title="使用" /> 使用
														</label>
														<div class="clearfix"></div>
														<label class="radio"> <input type="checkbox" name="crlNum" value="critical" data-title="关键"  /> 关键 <span class="help-inline"></span>
														</label>
													</div>
												</div>
											</div>
											<div class="tab-pane" id="tab4">
												<h3 class="block">确认CA配置信息</h3>
												<h4 class="form-section">密钥配置</h4>
												<div class="control-group">
													<label class="control-label">CA名称</label>
													<div class="controls">
														<span class="text display-value" data-display="caName"></span>
													</div>
												</div>
												<!-- 
												<div class="control-group">
													<label class="control-label">CA类型</label>
													<div class="controls">
														<span class="text display-value" data-display="caType"></span>
													</div>
												</div> -->
												<div class="control-group">
													<label class="control-label">密钥签名算法</label>
													<div class="controls">
														<span class="text display-value" data-display="signatureAlgorithm"></span>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">密钥</label>
													<div class="controls">
														<span class="text display-value" data-display="cryptoTokenId"></span>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">密钥别名</label>
													<div class="controls">
														<span class="text display-value" data-display="keyAliasDefaultKey"></span>
													</div>
												</div>
												<h4 class="form-section">CA证书配置</h4>
												<div class="control-group">
													<label class="control-label">CA证书主题</label>
													<div class="controls">
														<span class="text display-value" data-display="subjectDn"></span>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">CA证书颁发者</label>
													<div class="controls">
														<span class="text display-value" data-display="issuerCaId"></span>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">主题别名:</label>
													<div class="controls">
														<span class="text display-value" data-display="subjectAltName"></span>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">证书策略ID:</label>
													<div class="controls">
														<span class="text display-value" data-display="policyId"></span>
													</div>
												</div>
												<h4 class="form-section">CRL配置</h4>
												
												<div class="control-group">
													<label class="control-label">CRL周期</label>
													<div class="controls">
													<span class="text display-value" data-display="crlPeriod"></span>
													</div>
												</div>
												
												
												<div class="control-group">
													<label class="control-label">CA密钥标识:</label>
													<div class="controls">
														<span class="text display-value" data-display="akid"></span>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">CRL序号:</label>
													<div class="controls">
														<span class="text display-value" data-display="crlNum"></span>
													</div>
												</div>
												<!-- 
												<div class="control-group">
													<label class="control-label">Payment Options:</label>
													<div class="controls">
														<span class="text display-value" data-display="payment"></span>
													</div>
												</div>
												 -->
											</div>
										</div>
										<div class="form-actions clearfix">
											<a href="javascript:;" class="btn button-previous"> <i class="m-icon-swapleft"></i> 上一步
											</a> <a href="javascript:;" class="btn blue button-next"> 下一步 <i class="m-icon-swapright m-icon-white"></i>
											</a> <a href="#" class="btn green button-submit" onclick="submit_form.submit()"> 提交 <i class="m-icon-swapright m-icon-white"></i>
											</a>
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
<script>
function createNewCa(){
	
}

</script>
<!-- END BODY -->
<%----%>
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<script src="../media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="../media/js/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="../media/js/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
<script src="../media/js/bootstrap.min.js" type="text/javascript"></script>
<!--[if lt IE 9]>

	<script src="media/js/excanvas.min.js"></script>

	<script src="media/js/respond.min.js"></script>  

	<![endif]-->
<script src="../media/js/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="../media/js/jquery.blockui.min.js" type="text/javascript"></script>
<script src="../media/js/jquery.cookie.min.js" type="text/javascript"></script>
<script src="../media/js/jquery.uniform.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script type="text/javascript" src="../media/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="../media/js/additional-methods.min.js"></script>
<script type="text/javascript" src="../media/js/jquery.bootstrap.wizard.min.js"></script>
<script type="text/javascript" src="../media/js/chosen.jquery.min.js"></script>
<script type="text/javascript" src="../media/js/select2.min.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<%--@include file="../common/corePlugin.jsp"--%>
<script src="../media/js/app.js"></script>
<script src="../media/js/ca-form-wizard.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script>
	jQuery(document).ready(function() {

		// initiate layout and plugins

		App.init();

		FormWizard.init();

	});
	
	function loadCertProfile(){
		var issuerId = $('#issuerCaId_list').val();
		var sels = $("#certificateProfileId_list");
		var jsonObj =  eval('(${certProfiles })');
		sels.empty(); 
		if(issuerId == 1)
			sels.append("<option value='3'>ROOTCA</option>");//添加option
		else
			sels.append("<option value='2'>SUBCA</option>");//添加option
		for(var i=0;i<jsonObj.length;i++){
			if(issuerId == 1){
				if(jsonObj[i].type == 8)
					sels.append("<option value='" + jsonObj[i].profileId + "'>" + jsonObj[i].profileName + "</option>");//添加option
			}else{
				if(jsonObj[i].type == 2)
					sels.append("<option value='" + jsonObj[i].profileId + "'>" + jsonObj[i].profileName + "</option>");//添加option
			}
		}
		
	}
	loadCertProfile();
</script>
<!-- END JAVASCRIPTS -->
</html>