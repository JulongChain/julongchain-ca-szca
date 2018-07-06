<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.szca.com/jsp/jstl/szca" prefix="szca"%>
<%--
  ~ Copyright ? 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
  ~ Copyright ? 2018  SZCA. All Rights Reserved.
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
  --%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
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
<div class="page-container">
	<!-- BEGIN SIDEBAR -->
	<%-- 使用jsp:include动态包含，可以向sideBar传递参数，确保对应菜单状态为设置为active  --%>
	<%--@include file="common/sideBar.jsp"--%>
	<jsp:include page="../common/sideBar.jsp" flush="true">
		<jsp:param name="menuLevel1" value="ca" />
		<jsp:param name="menuLevel2" value="caList" />
		<jsp:param name="menuLevel3" value="view" />
	</jsp:include>
	<!-- END SIDEBAR -->
	<!-- BEGIN PAGE -->
	<div class="page-content">
		<!-- BEGIN PAGE HEADER-->
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<!-- BEGIN STYLE CUSTOMIZER -->
					<%--@include file="common/theme.jsp" --%>
					<!-- END BEGIN STYLE CUSTOMIZER -->
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<h3 class="page-title">
						CA <small></small>
					</h3>
					<ul class="breadcrumb">
						<li><i class="icon-home"></i> <a href="index.html">CA功能</a> <i class="icon-angle-right"></i></li>
						<li><a href="#">CA列表</a><i class="icon-angle-right"></i></li>
						<li><a href="#">查看CA</a></li>
						<li class="pull-right no-text-shadow">
							<div id="dashboard-report-range" class="dashboard-date-range tooltips no-tooltip-on-touch-device responsive" data-tablet="" data-desktop="tooltips" data-placement="top"
								 data-original-title="Change dashboard date range">
								<i class="icon-calendar"></i> <span></span> <i class="icon-angle-down"></i>
							</div>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTAINER-->
			<!-- BEGIN PAGE CONTENT===搜索条件===-->
			<%----%>
			<div class="row-fluid">
				<!-- ===START===CA基本信息===== -->
				<div class="span12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption">
								<i class="icon-globe"></i>CA基本信息
							</div>
						</div>
						<div class="portlet-body form">
							<!-- BEGIN FORM-->
							<form name="renameCaForm" action="renameCA.html" class="form-horizontal">
								<!--  -->
								<c:set var="token" value="${cainfo.CAToken}" />
								<div class="control-group">
									<label class="control-label">CA ID</label>
									<div class="controls">
										<input type="hidden" name="caid" readOnly value="${cainfo.CAId}" />
										<span class="help-inline">
											${cainfo.CAId}
										</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">CA名称</label>
									<div class="controls">
										<input type="text" name="name" class="colorpicker-default m-wrap" value="${cainfo.name}" />
										<button class="btn blue btn-primary" onclick="renameCaForm.submit()">
											<i class="icon-ok"></i> 重命名
										</button>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">CA类型</label>
									<div class="controls">
											<span class="help-inline">
												<c:if test="${signedby eq 1 }">根CA</c:if>
												<c:if test="${signedby ne 1 }">子CA</c:if>
											</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">签名算法</label>
									<div class="controls">
										<span class="help-inline">${signAlg}</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">密钥库名称</label>
									<div class="controls">
										<span class="help-inline">${tokenName }</span>

									</div>
								</div>
								<c:forEach items="${tokenMap }" var="t">
									<div class="control-group">
										<label class="control-label">${t.key }</label>
										<div class="controls">
											<span class="help-inline">${t.value }</span>

										</div>
									</div>
								</c:forEach>
								<!--
                               <div class="form-actions">
                                   <button class="btn blue btn-primary" onclick="renameCaForm.submit()">
                                       <i class="icon-ok"></i>保存
                                   </button>
                                   <button class="btn " onclick="renameCaForm.reset()">取消</button>
                               </div>
                                -->
							</form>
							<!-- END FORM-->
						</div>
					</div>
				</div>
				<!-- END EXAMPLE TABLE PORTLET-->
				<!-- ===END===CA基本信息===== -->
				<!-- ===START===CA证书信息===== -->
				<form name="caForm1" action="#" class="form-horizontal">
					<div class="portlet box blue">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet-title">
							<div class="caption">
								<i class="icon-globe"></i>CA配置信息
							</div>
						</div>
						<div class="portlet-body form">
							<!-- BEGIN FORM-->
							<div class="portlet-body form">
								<!-- BEGIN FORM-->
								<c:set var="certList" value="${cainfo.certificateChain}" />

								<input type="hidden" name="caid" readOnly value="${cainfo.CAId}" />
								<div class="control-group">
									<label class="control-label">主题（DN）</label>
									<div class="controls">
										<span class="help-inline">${certList[0].subjectX500Principal}</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">颁发者(DN)</label>
									<div class="controls">
										<span class="help-inline">${certList[0].issuerX500Principal}</span>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label">证书模板</label>
									<div class="controls">
										<span class="help-inline">${certProfileName }</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">有效期</label>
									<div class="controls">
										<span class="help-inline">${szca:formatDate(certList[0].notBefore)} ~ ${szca:formatDate(certList[0].notAfter)}</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">更新有效期</label>
									<div class="controls">
												<span class="help-inline">
													<input type="text" name="validity" id="validity" value="${validity }" />天 (仅更新CA时用到)
													<c:if test="${!isRevoked}">
														<button type="button" class="btn blue" onclick="renewCA(${cainfo.CAId})">更新CA</button>
													</c:if>
												</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">强制唯一公钥</label>
									<div class="controls">
												<span class="help-inline">
													<input type="checkbox" name="isEnforceUniquePK" <c:if test="${isEnforceUniquePK }">checked="checked"</c:if>  value="true" />强制
												</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">强制唯一DN</label>
									<div class="controls">
												<span class="help-inline">
													<input type="checkbox" name="isEnforceUniqueDN" <c:if test="${isEnforceUniqueDN }">checked="checked"</c:if>  value="true" />强制
												</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">强制唯一DN序列号</label>
									<div class="controls">
												<span class="help-inline">
													<input type="checkbox" name="isEnforceUniqueSN" <c:if test="${isEnforceUniqueSN }">checked="checked"</c:if>  value="true" />强制
												</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">用户信息存储</label>
									<div class="controls">
												<span class="help-inline">
													<input type="checkbox" name="useUserStorage" <c:if test="${useUserStorage }">checked="checked"</c:if>  value="true" />使用
												</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">证书信息存储</label>
									<div class="controls">
												<span class="help-inline">
													<input type="checkbox" name="useCertStorage" <c:if test="${useCertStorage }">checked="checked"</c:if>  value="true" />使用
												</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">LDAP DN顺序</label>
									<div class="controls">
												<span class="help-inline">
													<input type="checkbox" name="isLdapDNOrder" <c:if test="${isLdapDNOrder }">checked="checked"</c:if>  value="true" />使用
												</span>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label">策略文本只使用UTF-8编码</label>
									<div class="controls">
												<span class="help-inline">
													<input type="checkbox" name="useUTF8PolicyText" <c:if test="${useUTF8PolicyText }">checked="checked"</c:if>  value="true" />使用
												</span>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label">DN使用Printable编码</label>
									<div class="controls">
												<span class="help-inline">
													<input type="checkbox" name="usePrintableDN" <c:if test="${usePrintableDN }">checked="checked"</c:if>  value="true" />使用
												</span>
									</div>
								</div>


								<!-- END FORM-->
							</div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
					<div class="portlet box blue">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet-title">
							<div class="caption">
								<i class="icon-globe"></i>证书VA信息
							</div>
						</div>
						<div class="portlet-body form">
							<!-- BEGIN FORM-->
							<div class="portlet-body form">
								<!-- BEGIN FORM-->
								<div class="control-group">
									<label class="control-label">CRL主题密钥标识符</label>
									<div class="controls">
												<span class="help-inline">
													<input type="checkbox" name="useAKI" <c:if test="${useAKI }">checked="checked"</c:if>  value="true" />使用
													<input type="checkbox" name="useAKICritical" <c:if test="${useAKICritical }">checked="checked"</c:if>  value="true" />关键
												</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">CRL序号</label>
									<div class="controls">
												<span class="help-inline">
													<input type="checkbox" name="useSN" <c:if test="${useSN }">checked="checked"</c:if> value="true" />使用
													<input type="checkbox" name="useSNCritical" <c:if test="${useSNCritical }">checked="checked"</c:if> value="true" />关键
												</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">CRL周期</label>
									<div class="controls">
												<span class="help-inline">
													<input type="text" name="crlPeriod"  value="${crlPeriod }" required="required"/>天
												</span>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label">默认CRL分发点URI</label>
									<div class="controls">
												<span class="help-inline">
													<input type="text" name="defaultCDP"  value="${defaultCDP }" />
												</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">默认CRL颁发者</label>
									<div class="controls">
												<span class="help-inline">
													<input type="text" name="defaultCrlIssuer"  value="${defaultCrlIssuer }" />
												</span>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label">默认OCSP URI</label>
									<div class="controls">
												<span class="help-inline">
													<input type="text" name="defaultOCSP"  value="${defaultOCSP }" />
												</span>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label">默认颁发者CA URI</label>
									<div class="controls">
												<span class="help-inline">
													<input type="text" name="defaultIssuerCA"  value="${defaultIssuerCA }" />
												</span>
									</div>
								</div>


								<div class="control-group">
									<label class="control-label">CA状态</label>
									<div class="controls">
												<span class="help-inline">
													<c:choose>
														<c:when test="${status eq 1 }">正常</c:when>
														<c:when test="${status eq 2 }">等待证书响应</c:when>
														<c:when test="${status eq 3 }">已过期</c:when>
														<c:when test="${status eq 4 }">已吊销</c:when>
														<c:when test="${status eq 5 }">离线</c:when>
														<c:when test="${status eq 6 }">外部CA</c:when>
														<c:when test="${status eq 7 }">未初始化</c:when>
														<c:otherwise>未知</c:otherwise>
													</c:choose>
												</span>
									</div>
								</div>
								<c:if test="${isRevoked}">
									<div class="control-group">
										<label class="control-label">吊销时间</label>
										<div class="controls">
											<input type="text" name="textfieldusername" value="${revokedDate}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
										</div>
									</div>

									<div class="control-group">
										<label class="control-label">吊销原因</label>
										<div class="controls">
											<input type="text" name="textfieldusername" value="${revokedReason}" readonly="readonly" class="span6 m-wrap popovers" data-trigger="hover"   />
										</div>
									</div>
								</c:if>
								<c:if test="${!isRevoked}">
									<c:if test="${not empty revokeMap}">
										<div class="control-group">
											<label class="control-label">撤销操作</label>
											<div class="controls">
												<select name="selectrevocationreason" id="selectrevocationreason">
													<c:forEach var="revoke" items="${revokeMap}" varStatus="status">
														<option value='${revoke.key}'>${revoke.value}</option>
													</c:forEach>
												</select>
											</div>
										</div>

									</c:if>
								</c:if>
								<div class="control-group">
									<label class="control-label"></label>
									<div class="controls">
										<button type="button" class="btn blue" onclick="saveCA()">保存CA</button>
										<c:if test="${!isRevoked}">
											<button type="button" class="btn blue" onclick="revokeCA(${cainfo.CAId})">撤销CA</button>
										</c:if>
										<button type="button" class="btn blue" onclick="removeCA(${cainfo.CAId})">删除CA</button>
										<button type="button" class="btn blue" onclick="history.go(-1)">返回</button>
									</div>
								</div>
								<!-- END FORM-->
							</div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
					<!-- ===END===CA证书信息===== -->
				</form>
				<!-- END PAGE CONTENT===搜索条件===-->
				<!-- BEGIN PAGE CONTENT===搜索结果===-->
				<!-- END PAGE CONTENT===搜索结果===-->
			</div>

		</div>
		<!-- END PAGE -->
		<!-- END CONTAINER -->
		<!-- BEGIN FOOTER -->
		<%@include file="../common/footer.jsp"%>
		<!-- END FOOTER -->
</body>
<script>
    function removeCA(id) {
        if(confirm("确定要删除此CA?")){
            document.location.href="removeCA.html?caid=" + id;
        }
    }

    function revokeCA(id) {
        if(confirm("确定要撤销此CA?")){
            if(confirm("撤销后CA签发的证书将无法正常使用，确定要撤销?")){
                var reason = $('#selectrevocationreason').val();
                document.location.href="revokeCA.html?caid=" + id + "&reason=" + reason;
            }
        }
    }

    function renewCA(id) {
        if(confirm("确定要更新此CA?")){
            if(confirm("确定要重新颁发CA?")){
                document.location.href="renewCA.html?caid=" + id + "&validity=" + $("#validity").val();
            }
        }
    }

    function exportCA(id) {
        caForm1.action="exportCA.html";
        caForm1.submit();

    }

    function saveCA() {
        caForm1.action="updateCA.html";
        caForm1.submit();

    }

    function updateCrlPeriod(caId) {
        caForm1.action="updateCrlPeriod.html";
        caForm1.submit();

    }


</script>
<!-- END BODY -->
<%@include file="../common/corePlugin.jsp"%>
</html>