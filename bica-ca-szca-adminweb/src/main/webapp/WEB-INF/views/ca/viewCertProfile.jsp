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
			<jsp:param name="menuLevel2" value="certProfileList" />
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
							编辑建证书模板 <small></small>
						</h3>
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="index.html">证书模板</a> <span class="icon-angle-right"></span></li>
							<li><a href="#">编辑证书模板</a> <span class="icon-angle-right"></span></li>
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
									<i class="icon-reorder"></i> 编辑证书模板 
								</div>
								<div class="tools hidden-phone">
									<a href="javascript:;" class="collapse"></a> <a href="#portlet-config" data-toggle="modal" class="config"></a> <a href="javascript:;" class="reload"></a> <a href="javascript:;" class="remove"></a>
								</div>
							</div>
							<div class="portlet-body form">
								<form action="handleCertProfile.html" class="form-horizontal" id="submit_form" name="submit_form" method="post">
									<!-- caType: 1=X509,2=CVC -->
									<input type="hidden" name="certProfileId" value="${profileId }">									
									
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
												<h3 class="block">基本信息</h3>
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
													<label class="control-label">模板类型：</label>
													<div class="controls"> 
														<c:choose>
															<c:when test="${profileMap['type'] eq '2' }">子CA</c:when>
															<c:when test="${profileMap['type'] eq '8' }">根CA</c:when>
															<c:otherwise>终端实体</c:otherwise>
														</c:choose>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">可用密钥算法</label>
													<div class="controls">
														<select name="availablekeyalgorithms" id="availablekeyalgorithms" multiple="multiple" disabled="disabled">
															 <option value="DSA">DSA</option>
															 <option value="ECDSA">ECDSA</option>
															 <option value="RSA">RSA</option>
														</select>	
													</div>
												</div>
											 	<div class="control-group">
													<label class="control-label">可用ECDSA曲线</label>
													<div class="controls">
														<select name="availableeccurves" id="availableeccurves" multiple="multiple"  disabled="disabled">
															<option value="ANY_EC_CURVE">允许所有曲线</option>
															<option value="prime239v1">prime239v1</option>
															<option value="prime239v2">prime239v2</option>
															<option value="prime239v3">prime239v3</option>
															<option value="prime256v1">prime256v1 / secp256r1 / P-256</option>
															<option value="secp224k1">secp224k1</option>
															<option value="secp224r1">secp224r1 / P-224</option>
															<option value="secp256k1">secp256k1</option>
															<option value="secp384r1">secp384r1 / P-384</option>
															<option value="secp521r1">secp521r1 / P-521</option>
															<option value="sect233k1">sect233k1 / K-233</option>
															<option value="sect233r1">sect233r1 / B-233</option>
															<option value="sect239k1">sect239k1</option>
															<option value="sect283k1">sect283k1 / K-283</option>
															<option value="sect283r1">sect283r1 / B-283</option>
															<option value="sect409k1">sect409k1 / K-409</option>
															<option value="sect409r1">sect409r1 / B-409</option>
															<option value="sect571k1">sect571k1 / K-571</option>
															<option value="sect571r1">sect571r1 / B-571</option>
															<option value="sm2p256v1">sm2p256v1</option>
														</select>	
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">可用密钥长度</label>
													<div class="controls">
														<select id="availablebitlengths" name="availablebitlengths" multiple="multiple" size="5" disabled="disabled">	
															<option value="0">0 位</option>
															<option value="256">256 位</option>
															<option value="384">384 位</option>
															<option value="512">512 位</option>
															<option value="521">521 位</option>
															<option value="1024">1024 位</option>
															<option value="2048">2048 位</option>
															<option value="4096">4096 位</option>
														</select>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">签名算法</label>
													<div class="controls">
														<select id="signaturealgorithm" name="signaturealgorithm" disabled="disabled">	
															<option value="" selected="selected">继承签发CA</option>
															<option value="SHA1WithRSA">SHA1WithRSA</option>
															<option value="SHA256WithRSA">SHA256WithRSA</option>
															<option value="SHA384WithRSA">SHA384WithRSA</option>
															<option value="SHA1withECDSA">SHA1withECDSA</option>
															<option value="SHA256withECDSA">SHA256withECDSA</option>
															<option value="SHA384withECDSA">SHA384withECDSA</option>
															<option value="SHA1WithDSA">SHA1WithDSA</option>
															<option value="SM3WithSM2">SM3WithSM2</option>
														</select>
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label">有效期</label>
													<div class="controls">
														<input type="text" name="validity" required="required" value="${profileMap['validity']}" data-title="validity"  disabled="disabled"/>天
														<span class="help-inline"></span>
													</div>
												</div>
												
												<h3 class="block">权限控制</h3>
												<div class="control-group">
													<label class="control-label">允许有效期重置</label>
													<div class="controls">
														<input type="checkbox" name="allowvalidityoverride" id="allowvalidityoverride" value="true"  disabled="disabled"/>允许
														<span class="help-inline"></span>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">允许扩展项重置</label>
													<div class="controls">
														<input type="checkbox" name="allowextensionoverride" id="allowextensionoverride" value="true" disabled="disabled"/>允许
														<span class="help-inline"></span>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">允许DN被CSR重置</label>
													<div class="controls">
														<input type="checkbox" name="allowdnoverride" id="allowdnoverride" value="true" disabled="disabled"/>允许
														<span class="help-inline"></span>
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label">允许DN被终端实体重置</label>
													<div class="controls"> 
														<input type="checkbox" name="allowdnoverridebyeei" id="allowdnoverridebyeei" value="true" disabled="disabled"/>允许
														<span class="help-inline"></span>
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label">允许密钥用途重置</label>
													<div class="controls"> 
														<input type="checkbox" name="allowkeyusageoverride" id="allowkeyusageoverride" value="true" disabled="disabled"/>允许
														<span class="help-inline"></span>
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label">允许追溯吊销</label>
													<div class="controls"> 
														<input type="checkbox" name="allowbackdatedrevokation" id="allowbackdatedrevokation" value="true" disabled="disabled"/>允许
														<span class="help-inline"></span>
													</div>
												</div>
												
												<div class="control-group" id="usecertificatestorageDiv" style="display: none;">
													<label class="control-label">使用证书存储</label>
													<div class="controls"> 
														<input type="checkbox" name="usecertificatestorage" id="usecertificatestorage" value="true" disabled="disabled"/>使用
														<span class="help-inline"></span>
													</div>
												</div>
												
												<div class="control-group" id="storecertificatedataDiv" style="display: none;">
													<label class="control-label">存储证书信息</label>
													<div class="controls"> 
														<input type="checkbox" name="storecertificatedata" id="storecertificatedata" value="true" disabled="disabled"/>使用
														<span class="help-inline"></span>
													</div>
												</div>
												
												<h3 class="block">X509 扩展</h3>
												<h4 class="block">基本扩展</h4>
												<div class="control-group">
													<label class="control-label">基本约束</label>
													<div class="controls"> 
														<input type="checkbox" name="usebasicconstrants" id="usebasicconstrants" value="true" disabled="disabled"/>使用
														<input type="checkbox" name="basicconstraintscritical" id="basicconstraintscritical" value="true" disabled="disabled"/>关键
														<span class="help-inline"></span>
													</div>
												</div>
												<div class="control-group" id="pathLen" style="display: none;">
													<label class="control-label">路径长度限制</label>
													<div class="controls"> 
														<input type="checkbox" name="usepathlengthconstraint" id="usepathlengthconstraint" value="true" onchange="pathLenChange()" disabled="disabled"/>使用
														&nbsp;&nbsp;&nbsp;长度值 ：<input type="text" name="pathlengthconstraint" style="width: 50px" id="pathlengthconstraint" value="${profileMap['pathlengthconstraint']}" disabled="disabled"/>
														<span class="help-inline"></span>
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label">CA密钥标识符</label>
													<div class="controls"> 
														<input type="checkbox" name="useauthoritykeyidentifier" id="useauthoritykeyidentifier" value="true" disabled="disabled"/>使用
														<span class="help-inline"></span>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">主题密钥标识符</label>
													<div class="controls"> 
														<input type="checkbox" name="usesubjectkeyidentifier" id="usesubjectkeyidentifier" value="true" disabled="disabled"/>使用
														<span class="help-inline"></span>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">LDAN DN 顺序</label>
													<div class="controls"> 
														<input type="checkbox" name="useldapdnorder" id="useldapdnorder" value="true" disabled="disabled"/>使用
														<span class="help-inline"></span>
													</div>
												</div>
												<h4 class="block">用法(Usages)</h4>
												<div class="control-group">
													<label class="control-label">密钥用法</label>
													<div class="controls"> 
														<input type="checkbox" name="usekeyusage" id="usekeyusage" value="true" disabled="disabled"/>使用
														<input type="checkbox" name="keyusagecritical" id="keyusagecritical" value="true" disabled="disabled"/>关键
														<span class="help-inline"></span>
													</div>
													<div class="controls"> 
														密钥用法: 
													</div>
													<div class="controls"></div>
													<div class="controls"> 
														<input type="checkbox" name="keyusage" id="keyusage0" value="0" disabled="disabled"/>数字签名
														<input type="checkbox" name="keyusage" id="keyusage1" value="1" disabled="disabled"/>不可抵赖  
														<input type="checkbox" name="keyusage" id="keyusage2" value="2" disabled="disabled"/>密钥加密
													</div>
													<div class="controls">  
														<input type="checkbox" name="keyusage" id="keyusage3" value="3" disabled="disabled"/>数据加密       
														<input type="checkbox" name="keyusage" id="keyusage4" value="4" disabled="disabled"/>密钥协议
														<input type="checkbox" name="keyusage" id="keyusage5" value="5" disabled="disabled"/>密钥证书签名
													</div>
													<div class="controls"> 
														<input type="checkbox" name="keyusage" id="keyusage6" value="6" disabled="disabled"/>CRL签名  
														<input type="checkbox" name="keyusage" id="keyusage7" value="7" disabled="disabled"/>只用于加密
														<input type="checkbox" name="keyusage" id="keyusage8" value="8" disabled="disabled"/>只用于解密
														<span class="help-inline"></span>
													</div>
												</div>


												
												<div class="control-group">
													<label class="control-label">增强密钥用法</label>
													<div class="controls"> 
														<input type="checkbox" name="useextendedkeyusage" id="useextendedkeyusage" value="true" disabled="disabled"/>使用
														<input type="checkbox" name="extendedkeyusagecritical" id="extendedkeyusagecritical" value="true" disabled="disabled"/>关键
														<span class="help-inline"></span>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label"></label>
													<div class="controls"> 
														<select id="extendedkeyusage" name="extendedkeyusage"　id="extendedkeyusage" multiple="multiple" disabled="disabled">	
															<option value="2.5.29.37.0">任意扩展的密钥用途</option>
															<option value="1.2.203.7064.1.1.369791.1">CSN 369791 TLS client</option>
															<option value="1.2.203.7064.1.1.369791.2">CSN 369791 TLS server</option>
															<option value="1.3.6.1.5.5.7.3.14">EAP over LAN (EAPOL)</option>
															<option value="1.3.6.1.5.5.7.3.13">EAP over PPP</option>
															<option value="0.4.0.2231.3.0">ETSI TSL Signing</option>
															<option value="2.23.136.1.1.3">ICAO Master List Signing</option>
															<option value="2.16.840.1.113741.1.2.3">Intel AMT management</option>
															<option value="1.3.6.1.5.5.7.3.17">Internet Key Exchange for IPsec</option>
															<option value="1.3.6.1.5.2.3.4">Kerberos Client Authentication</option>
															<option value="1.3.6.1.5.2.3.5">Kerberos KDC (Key Distribution Center)</option>
															<option value="1.3.6.1.4.1.311.2.1.22">MS Commercial Code Signing</option>
															<option value="1.3.6.1.4.1.311.10.3.12">MS Document Signing</option>
															<option value="1.3.6.1.4.1.311.10.3.4.1">MS EFS Recovery</option>
															<option value="1.3.6.1.4.1.311.10.3.4">MS Encrypted File System (EFS)</option>
															<option value="1.3.6.1.4.1.311.2.1.21">MS Individual Code Signing</option>
															<option value="1.3.6.1.4.1.311.20.2.2">MS智能卡登录</option>
															<option value="1.3.6.1.5.5.7.3.9">OCSP签发者</option>
															<option value="1.2.840.113583.1.1.5">PDF Signing</option>
															<option value="2.16.840.1.101.3.6.8">PIV Card Authentication</option>
															<option value="1.3.6.1.5.5.7.3.16">SCVP Client</option>
															<option value="1.3.6.1.5.5.7.3.15">SCVP Server</option>
															<option value="1.3.6.1.5.5.7.3.20">SIP Domain</option>
															<option value="1.3.6.1.5.5.7.3.21">SSH Client</option>
															<option value="1.3.6.1.5.5.7.3.22">SSH Server</option>
															<option value="1.3.6.1.5.5.7.3.3">代码签名</option>
															<option value="1.3.6.1.5.5.7.3.4">安全Email</option>
															<option value="1.3.6.1.5.5.7.3.2">客户身份验证</option>
															<option value="1.3.6.1.5.5.7.3.8">时间戳</option>
															<option value="1.3.6.1.5.5.7.3.1">服务器验证</option>
														</select>
													</div>
												</div>
												
												<div class="control-group" >
													<label class="control-label">证书策略</label>
													<div class="controls"> 
														<input type="checkbox" name="usecertificatepolicies" id="usecertificatepolicies" value="true" onchange="cpChange()" disabled="disabled"/>使用
														<input type="checkbox" name="certificatepoliciescritical" id="certificatepoliciescritical" value="true" disabled="disabled"/>关键
														<span class="help-inline"></span>
													</div>
												</div>
												<input type="hidden" name="existCPVal" id="existCPVal" value="${existCP }">
												<div class="control-group" id="cpDiv" style="display: none;">
													<label class="control-label"></label>
													<div class="controls" id="existCP"> 
														
													</div>
													<div class="controls"> 
														证书策略ID：<input type="text" name="cpId" id="cpId"  disabled="disabled"/><input type="button" onclick="addCP()" value="添加"  disabled="disabled" />												
													</div>
													<div class="controls"> 
														<input type="radio" name="qualifier" checked="checked" value="0" onchange="qualifierChange()" disabled="disabled"/>无策略限定符											
														<input type="radio" name="qualifier" value="1" onchange="qualifierChange()" disabled="disabled"/>用户通知
														<input type="radio" name="qualifier" value="2" onchange="qualifierChange()" disabled="disabled"/>CPS
													</div>
													<div class="controls" id="qualifierDiv" style="display: none;"> 
														<input type="text" name="qualifierVal" id="qualifierVal" disabled="disabled"/>								
													</div>
												</div>
												<h4 class="block">名称(Names)</h4>
												<div class="control-group">
													<label class="control-label">主题别名</label>
													<div class="controls"> 
														<input type="checkbox" name="usesubjectalternativename" id="usesubjectalternativename" value="true" disabled="disabled"/>使用
														<input type="checkbox" name="subjectalternativenamecritical" id="subjectalternativenamecritical" value="true" disabled="disabled"/>关键
														<span class="help-inline"></span>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">颁发者别名</label>
													<div class="controls"> 
														<input type="checkbox" name="useissueralternativename" id="useissueralternativename" value="true" disabled="disabled"/>使用
														<input type="checkbox" name="issueralternativenamecritical" id="issueralternativenamecritical" value="true" disabled="disabled"/>关键
														<span class="help-inline"></span>
													</div>
												</div>
												<h4 class="block">验证信息(VA)</h4>
												<div class="control-group">
													<label class="control-label">CRL发布点</label>
													<div class="controls"> 
														<input type="checkbox" name="usecrldistributionpoint" id="usecrldistributionpoint" value="true" onchange="crlChange()" disabled="disabled"/>使用
														<input type="checkbox" name="crldistributionpointcritical" id="crldistributionpointcritical" value="true" disabled="disabled"/>关键
														<span class="help-inline"></span>
													</div>
												</div>
												<div class="control-group" id="defaultCrlDiv" style="display: none;">
													<label class="control-label">使用CA定义的CRL发布点</label>
													<div class="controls"> 
														<input type="checkbox" name="usedefaultcrldistributionpoint" id="usedefaultcrldistributionpoint" value="true" onchange="defaultCrlChange()" disabled="disabled"/>使用
													</div>
												</div>
												<div class="control-group" id="crlDiv" style="display: none;">
													<label class="control-label">CRL发布点URI</label>
													<div class="controls"> 
														<input type="text" name="crldistributionpointuri" id="crldistributionpointuri" value="${profileMap['crldistributionpointuri']}"  disabled="disabled"/>
													</div>
												</div>
												<div class="control-group" id="crlIssuerDiv" style="display: none;">
													<label class="control-label">CRL发布点者</label>
													<div class="controls"> 
														<input type="text" name="crlissuer" id="crlissuer" value="${profileMap['crlissuer']}"  disabled="disabled"/>
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label">授权信息访问(AIA)</label>
													<div class="controls"> 
														<input type="checkbox" name="useauthorityinformationaccess" id="useauthorityinformationaccess" value="true" onchange="aiaChange()" disabled="disabled"/>使用
														<span class="help-inline"></span>
													</div>
												</div>
												<div class="control-group" id="defaultOCSPDiv" style="display: none;">
													<label class="control-label">使用CA定义的OCSP</label>
													<div class="controls"> 
														<input type="checkbox" name="usedefaultocspservicelocator" id="usedefaultocspservicelocator" value="true" onchange="defaultOCSPChange()" disabled="disabled"/>使用
													</div>
												</div>
												<div class="control-group" id="ocspDiv" style="display: none;">
													<label class="control-label">OCSP URI</label>
													<div class="controls"> 
														<input type="text" name="ocspservicelocatoruri" id="ocspservicelocatoruri" value="${profileMap['ocspservicelocatoruri']}"  disabled="disabled"/>
													</div>
												</div>
												<!-- 
												<div class="control-group" id="defaultIssuerDiv" style="display: none;">
													<label class="control-label">使用CA定义的CA 颁发者</label>
													<div class="controls"> 
														<input type="checkbox" name="usecaissuersuri" id="usecaissuersuri" value="true" onchange="defaultIssuerChange()"/>使用
													</div>
												</div>
												 -->
												<input type="hidden" name="existIssuers" id="existIssuers" value="${profileMap['caissuers'] }">
												<div class="control-group" id="issuerDiv" style="display: none;">
													<label class="control-label">CA颁发者URI</label>
													<div class="controls" id="ocspIssuer"> 
														
													</div>
													<div class="controls"> 
														<input type="text" name="issueruri" id="issueruri" value=""  disabled="disabled"/>
														<input type="button" id="addIssuerButton" value="添加" onclick="addIssuer()" disabled="disabled"/>
													</div>
												</div>
												<div class="control-group">
													<label class="control-label">OCSP不撤销</label>
													<div class="controls"> 
														<input type="checkbox" name="useocspnocheck" id="useocspnocheck" value="true" disabled="disabled"/>使用
													</div>
												</div>
												<h4 class="block">CA配置</h4>
												<div class="control-group">
													<label class="control-label">可用CA</label>
													<div class="controls">
														<select id="availablecas" name="availablecas" multiple="multiple" disabled="disabled">	
															<option value="-1">任何CA</option>
															<c:forEach items="${cas }" var="ca">
																<option value="${ca.caId }">${ca.name }</option>
															</c:forEach>
														</select>
													</div>
												</div>
												
											</div>
										</div>
										<div class="form-actions clearfix">
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

	function multipleSelected(vals,selectId){
		vals = vals.replace("[","");
		vals = vals.replace("]","");
		var arr = vals.split(",");
		
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
	
	function kuCheckBox(vals,checkBoxId){
		vals = vals.replace("[","");
		vals = vals.replace("]","");
		var arr = vals.split(",");
		for(var i=0;i<arr.length;i++){
			if(arr[i].trim() == 'true'){
				document.getElementById(checkBoxId + i).checked = 'checked';
			}
		}
		
	}
	
	function signleSelected(val,selectId){
		var sel = document.getElementById(selectId);
		var len=sel.options.length;
		for(var j=0;j<len;j++){
		    if(sel.options[j].value == val.trim()){
		        sel.options[j].setAttribute("selected",true);
		        break;
		    }
		}
	}
	
	function multipleSelectedAll(selectId){
		var sel = document.getElementById(selectId);
		var len=sel.options.length;
		for(var j=0;j<len;j++){
		    sel.options[j].setAttribute("selected",true);
		}
	}
	
	function checkBoxInit(flag, id){
		if(flag == 'true'){
			document.getElementById(id).checked = 'checked';
		}
	} 
	
	function cpChange(){
		if(document.getElementById('usecertificatepolicies').checked == true){
			document.getElementById('cpDiv').style.display = "block";
		}else{
			document.getElementById('cpDiv').style.display = "none";
		}
	}
	
	function qualifierChange(){
		var radio = document.getElementsByName("qualifier");  
	    for (i=0; i<radio.length; i++) {  
	        if (radio[i].checked == true) {
	            if(radio[i].value == 0){
	            	document.getElementById('qualifierDiv').style.display = "none";
	            }else{
	            	document.getElementById('qualifierDiv').style.display = "block";
	            }  
	        }  
	    }  
		
	}
	
	function loadCP(){
		var cpVals = document.getElementById('existCPVal').value;
		var div = document.getElementById("existCP"); 
		var table = document.createElement("table");//创建table 
		table.id = "existCPTable";
		if(cpVals != null){
			var cpVal = cpVals.split(',');
			for(var i=0;i<cpVal.length;i++){
				var cpArr = cpVal[i].split('|');
				if(cpArr.length == 3){
					var row1 = table.insertRow();//创建一行 
					row1.id = cpArr[0] + i;
					var cell1 = row1.insertCell();
					cell1.align = 'right';
					cell1.innerHTML="证书策略ID:"; 
					var cell2 = row1.insertCell();
					cell2.innerHTML = cpArr[0];
					var cell3 = row1.insertCell();
					var cpValDeal = cpVal[i].replace(new RegExp(' ',"gm"),'&nbsp;');
					var str = '<input type="button" disabled="disabled" value="删除" onclick=removeCP("' + cpArr[0] + i + '","' + cpValDeal + '")>';
					cell3.innerHTML = str;
					if(cpArr[1] != null && cpArr[1] != 'null' && cpArr[1] != ''){
						var row2 = table.insertRow();//创建二行 
						row2.id = cpArr[0] + i + 'val';
						var cell4 = row2.insertCell();
						cell4.align = 'right';
						if(cpArr[1] == '1.3.6.1.5.5.7.2.1'){
							cell4.innerHTML = "CPS:"; 
						}else if(cpArr[1] == '1.3.6.1.5.5.7.2.2'){
							cell4.innerHTML = "用户通告:"; 
						}
						
						var cell5 = row2.insertCell();
						if(cpArr[2] != null && cpArr[2] != 'null' && cpArr[2] != ''){
							cell5.innerHTML = cpArr[2];
						}else{
							cell5.innerHTML = '';
						}
					}
				}
				
			}
		}
		div.appendChild(table); 
	}
	
	function removeRow(tableId, rowId){
		var cpTable = document.getElementById(tableId);
		var row = document.getElementById(rowId);
		var index = row.rowIndex;
		cpTable.deleteRow(index);
	}
	
	function removeCP(rowId, cp){
		removeRow("existCPTable", rowId);
		if(document.getElementById(rowId + 'val') != null){
			removeRow("existCPTable", rowId + 'val');
		}
		var newCpVals = '';
		var cpVals = document.getElementById('existCPVal').value.split(',');
		for(var i=0;i<cpVals.length;i++){
			if(cp != cpVals[i]){
				newCpVals += cpVals[i] + ',';
			}
		}
		if(newCpVals.length > 1){
			newCpVals = newCpVals.substring(0, newCpVals.length - 1);
		}
		document.getElementById('existCPVal').value = newCpVals;
	}
	
	function addCP(){
		var cpId = document.getElementById('cpId').value;
		var radio = document.getElementsByName("qualifier");  
		var qualifierVal = document.getElementById("qualifierVal").value;
		var cpType = '';
	    for (i=0; i<radio.length; i++) {  
	        if (radio[i].checked == true) {
	        	cpType = radio[i].value;
	        }  
	    }
	    
		var cpVals = document.getElementById('existCPVal').value;
		var newCp = '';
		if(cpVals.length >1){
			cpVals += ',';
		}
	    if(cpType == 0){
	    	newCp = cpId + '|null|null';
	    }
	    if(cpType == 1){
	    	newCp = cpId + '|1.3.6.1.5.5.7.2.2|' + qualifierVal;
	    }
	    if(cpType == 2){
	    	newCp = cpId + '|1.3.6.1.5.5.7.2.1|' + qualifierVal;
	    }
		if(cpVals.indexOf(newCp) == -1){
			document.getElementById('existCPVal').value = cpVals + newCp;
		    var div = document.getElementById("existCP"); 
		    div.removeChild(div.lastChild);  
		    loadCP();
		}else{
			alert('重复的策略!');
		}
	}
	
	function crlChange(){
		var checkbox = document.getElementById('usecrldistributionpoint');
		if(checkbox.checked == true){
			document.getElementById('defaultCrlDiv').style.display = 'block';
			document.getElementById('crlDiv').style.display = 'block';
			document.getElementById('crlIssuerDiv').style.display = 'block';
		}else{
			document.getElementById('defaultCrlDiv').style.display = 'none';
			document.getElementById('crlDiv').style.display = 'none';
			document.getElementById('crlIssuerDiv').style.display = 'none';
		}
	}
	
	function defaultCrlChange(){
		var checkbox = document.getElementById('usedefaultcrldistributionpoint');
		if(checkbox.checked == true){
			document.getElementById('crldistributionpointuri').disabled="disabled";
			document.getElementById('crlissuer').disabled="disabled";
		}else{
			document.getElementById('crldistributionpointuri').disabled="";
			document.getElementById('crlissuer').disabled="";
		}
	}
	
	function changeProfileType(t){
		document.getElementById('type').value = t;
		if(t == '1'){
			document.getElementById('profiletype1').disabled = 'disabled';
			document.getElementById('profiletype2').disabled = '';
			document.getElementById('profiletype8').disabled = '';
			document.getElementById('pathLen').style.display = 'none';
			document.getElementById('usecertificatestorageDiv').style.display = 'block'
			document.getElementById('storecertificatedataDiv').style.display = 'block'	
		}
		if(t == '2'){
			document.getElementById('profiletype1').disabled = '';
			document.getElementById('profiletype2').disabled = 'disabled';
			document.getElementById('profiletype8').disabled = '';
			document.getElementById('pathLen').style.display = 'block';
			document.getElementById('usecertificatestorageDiv').style.display = 'none'
			document.getElementById('storecertificatedataDiv').style.display = 'none'
		}
		if(t == '8'){
			document.getElementById('profiletype1').disabled = '';
			document.getElementById('profiletype2').disabled = '';
			document.getElementById('profiletype8').disabled = 'disabled';
			document.getElementById('pathLen').style.display = 'block';
			document.getElementById('usecertificatestorageDiv').style.display = 'none'
			document.getElementById('storecertificatedataDiv').style.display = 'none'
		}
	}
	
	function pathLenChange(){
		var checkbox = document.getElementById('usepathlengthconstraint');
		if(checkbox.checked == true){
			document.getElementById('pathlengthconstraint').disabled="";
		}else{
			document.getElementById('pathlengthconstraint').disabled="disabled";
		}
	}
	
	function loadOCSPIssuser(){
		var issuerVals = document.getElementById('existIssuers').value;
		issuerVals = issuerVals.replace("[","");
		issuerVals = issuerVals.replace("]","");
		var div = document.getElementById("ocspIssuer"); 
		var table = document.createElement("table");//创建table 
		table.id = "existIssuerTable";
		if(issuerVals != null){
			var issuerVal = issuerVals.split(',');
			for(var i=0;i<issuerVal.length;i++){
				if(issuerVal[i] != null && issuerVal[i].length > 0){
					var row = table.insertRow();//创建一行 
					row.id = "issuer" + i;
					var cell1 = row.insertCell();
					cell1.align = 'left';
					cell1.innerHTML = issuerVal[i].trim();
					var cell2 = row.insertCell();
					var str = '<input type="button" disabled="disabled" name="issuerRemoveBtn" value="删除" onclick=removeIssuer("issuer'+ i + '","' + issuerVal[i].trim() + '")>';
					cell2.innerHTML = str;
				}	
			}
		}
		div.appendChild(table); 
		
	}
	
	function removeIssuer(rowId, removeIssuer){
		removeRow('existIssuerTable', rowId); 
		var newIssuerVals = '[';
		var issuerVals = document.getElementById('existIssuers').value;
		issuerVals = issuerVals.replace("[","");
		issuerVals = issuerVals.replace("]","");
		var issuerVal = issuerVals.split(',');
		for(var i=0;i<issuerVal.length;i++){
			if(removeIssuer != issuerVal[i].trim()){
				newIssuerVals += issuerVal[i].trim() + ',';
			}
		}
		if(newIssuerVals.length > 1){
			newIssuerVals = newIssuerVals.substring(0, newIssuerVals.length - 1) + ']';
		}
		document.getElementById('existIssuers').value = newIssuerVals;
	}
	
	function addIssuer(){
		var issuer = document.getElementById('issueruri').value;
		var issuerVals = document.getElementById('existIssuers').value;
	    if(issuerVals.indexOf(issuer) == -1){
	    	issuerVals = issuerVals.replace("]","");
			if(issuerVals.length >1){
				issuerVals += ',';
			}
			issuerVals += issuer;
			issuerVals += ']';
			document.getElementById('existIssuers').value = issuerVals;
		    var div = document.getElementById("ocspIssuer"); 
		    div.removeChild(div.lastChild);  
		    loadOCSPIssuser();
		}else{
			alert('重复的颁发者URI!');
		}
	}
	
	function aiaChange(){
		var checkbox = document.getElementById('useauthorityinformationaccess');
		if(checkbox.checked == true){
			document.getElementById('defaultOCSPDiv').style.display = 'block';
			document.getElementById('ocspDiv').style.display = 'block';
			//document.getElementById('defaultIssuerDiv').style.display = 'block';
			document.getElementById('issuerDiv').style.display = 'block';
		}else{
			document.getElementById('defaultOCSPDiv').style.display = 'none';
			document.getElementById('ocspDiv').style.display = 'none';
			//document.getElementById('defaultIssuerDiv').style.display = 'none';
			document.getElementById('issuerDiv').style.display = 'none';
		}
	}
	
	function defaultOCSPChange(){
		var checkbox = document.getElementById('usedefaultocspservicelocator');
		if(checkbox.checked == true){
			document.getElementById('ocspservicelocatoruri').disabled="disabled";
		}else{
			document.getElementById('ocspservicelocatoruri').disabled="";
		}
	}
	
	function defaultIssuerChange(){
		var checkbox = document.getElementById('usecaissuersuri');
		if(checkbox.checked == true){
			document.getElementById('issueruri').disabled="disabled";
			document.getElementById('addIssuerButton').disabled="disabled";
			var btns = document.getElementsByName('issuerRemoveBtn');
			for(var i=0;i<btns.length;i++){
				btns[i].disabled="disabled";
			}
		}else{
			document.getElementById('issueruri').disabled="";
			document.getElementById('addIssuerButton').disabled="";
			var btns = document.getElementsByName('issuerRemoveBtn');
			for(var i=0;i<btns.length;i++){
				btns[i].disabled="";
			}
		}
	}
	
	 

	
	//密钥算法
	var keyAlgs = "${profileMap['availablekeyalgorithms']}";
	multipleSelected(keyAlgs,'availablekeyalgorithms');
	//ECC曲线
	var eccurves = "${profileMap['availableeccurves']}";
	multipleSelected(eccurves,'availableeccurves');
	
	//密钥长度
	var bitlen = "${profileMap['availablebitlengths']}";
	multipleSelected(bitlen,'availablebitlengths');
	
	//算名算法
	var sigAlg = "${profileMap['signaturealgorithm']}";
	signleSelected(sigAlg,'signaturealgorithm');
	
	//允许有效期覆盖
	var isAllowValidatyOverride = "${profileMap['allowvalidityoverride']}";
	checkBoxInit(isAllowValidatyOverride,'allowvalidityoverride');
	
	//允许DN重置
	var isAllowDNOverriderByCsr = "${profileMap['allowdnoverride']}";
	var isAllowDNOverriderByEE = "${profileMap['allowdnoverridebyeei']}";
	checkBoxInit(isAllowDNOverriderByCsr,'allowdnoverride');
	checkBoxInit(isAllowDNOverriderByEE,'allowdnoverridebyeei');
	
	//允许密钥用途重置
	var isAllowKUOverride = "${profileMap['allowkeyusageoverride']}";
	checkBoxInit(isAllowKUOverride,'allowkeyusageoverride');
	
	//允许追溯吊销
	var isBackDateRevoke = "${profileMap['allowbackdatedrevokation']}";
	checkBoxInit(isBackDateRevoke,'allowbackdatedrevokation');
	
	
	//使用证书存储
	var isUserCertStore = "${profileMap['usecertificatestorage']}";
	checkBoxInit(isUserCertStore,'usecertificatestorage');
	
	//存储证书信息
	var isStoreCertData = "${profileMap['storecertificatedata']}";
	checkBoxInit(isStoreCertData,'storecertificatedata');
	
	//基本约束
	var isUseBasicConstrants = "${profileMap['usebasicconstrants']}";
	checkBoxInit(isUseBasicConstrants,'usebasicconstrants');
	var isBasicConstrantsCritical = "${profileMap['basicconstraintscritical']}";
	checkBoxInit(isBasicConstrantsCritical,'basicconstraintscritical');
	
	//路径长度限制
	var isUsePathLen = "${profileMap['usepathlengthconstraint']}";
	checkBoxInit(isUsePathLen,'usepathlengthconstraint');
	if("${profileMap['type'] eq '2' || profileMap['type'] eq '8'}" == "true"){
		pathLenChange(); 
	}

	//CA密钥标识符和主题密钥标识符
	var isUseAKI = "${profileMap['useauthoritykeyidentifier']}";
	checkBoxInit(isUseAKI,'useauthoritykeyidentifier');
	var isUseSKI = "${profileMap['usesubjectkeyidentifier']}";
	checkBoxInit(isUseSKI,'usesubjectkeyidentifier');
	
	//SubjectDN顺序
	var isUseLdapOrder = "${profileMap['useldapdnorder']}";
	checkBoxInit(isUseLdapOrder,'useldapdnorder');
	
	//密钥用途
	var isUseKU = "${profileMap['usekeyusage']}";
	checkBoxInit(isUseKU,'usekeyusage');
	var isKUCritical = "${profileMap['keyusagecritical']}";
	checkBoxInit(isKUCritical,'keyusagecritical');
	var kuArr = "${profileMap['keyusage']}";
	kuCheckBox(kuArr, 'keyusage');
	
	//增强密钥用法
	var isUseEKU = "${profileMap['useextendedkeyusage']}";
	checkBoxInit(isUseEKU,'useextendedkeyusage');
	var isEKUCritical = "${profileMap['extendedkeyusagecritical']}";
	checkBoxInit(isEKUCritical,'extendedkeyusagecritical');
	var ekuArr = "${profileMap['extendedkeyusage']}";
	multipleSelected(ekuArr,'extendedkeyusage');
	
	//证书策略
	var isUserCP = "${profileMap['usecertificatepolicies']}";
	checkBoxInit(isUserCP,'usecertificatepolicies');
	var isCPCritical = "${profileMap['certificatepoliciescritical']}";
	checkBoxInit(isCPCritical,'certificatepoliciescritical');
	cpChange();
	loadCP(); 
	
	//主题别名SAN
	var isUseSAN = "${profileMap['usesubjectalternativename']}";
	checkBoxInit(isUseSAN,'usesubjectalternativename');
	var isSANCritical = "${profileMap['subjectalternativenamecritical']}";
	checkBoxInit(isSANCritical,'subjectalternativenamecritical');
	
	//颁发者别名
	var isUseIAN = "${profileMap['useissueralternativename']}";
	checkBoxInit(isUseIAN,'useissueralternativename');
	var isIANCritical = "${profileMap['issueralternativenamecritical']}";
	checkBoxInit(isIANCritical,'issueralternativenamecritical');
	
	//名称约束
	var isUseNC = "${profileMap['usenameconstraints']}";
	checkBoxInit(isUseNC,'usenameconstraints');
	var isNCCritical = "${profileMap['nameconstraintscritical']}";
	checkBoxInit(isNCCritical,'nameconstraintscritical');
	
	//CRL发布点
	var isUseCDP = "${profileMap['usecrldistributionpoint']}";
	checkBoxInit(isUseCDP,'usecrldistributionpoint');
	var isCDPCritical = "${profileMap['crldistributionpointcritical']}";
	checkBoxInit(isCDPCritical,'crldistributionpointcritical');
	var isUseDefaultCDP = "${profileMap['usedefaultcrldistributionpoint']}";
	checkBoxInit(isUseDefaultCDP,'usedefaultcrldistributionpoint');
	crlChange();
	defaultCrlChange();
	
	//授权信息访问
	var isUseAIA = "${profileMap['useauthorityinformationaccess']}";
	checkBoxInit(isUseAIA,'useauthorityinformationaccess');
	var isDefaultOCSP = "${profileMap['usedefaultocspservicelocator']}";
	checkBoxInit(isDefaultOCSP,'usedefaultocspservicelocator');
	var isDefaultIssuer = "${profileMap['usecaissuersuri']}";
	checkBoxInit(isDefaultIssuer,'usecaissuersuri');
	aiaChange();
	defaultOCSPChange();
	//defaultIssuerChange();
	loadOCSPIssuser();
	var isUseOCSPNoCheck = "${profileMap['useocspnocheck']}";//OCSP不撤销
	checkBoxInit(isUseOCSPNoCheck,'useocspnocheck');
	
	//可用CA
	var caArr = "${profileMap['availablecas']}";
	multipleSelected(caArr, 'availablecas');
</script>
<!-- END JAVASCRIPTS -->
</html>