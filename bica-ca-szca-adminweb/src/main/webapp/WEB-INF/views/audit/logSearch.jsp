<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.szca.com/jsp/jstl/szca" prefix="szca"%>
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
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../common/metaHead.jsp"%>
<link href="${pageContext.request.contextPath}/media/css/datetimepicker.css" rel="stylesheet" type="text/css" />
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
			<jsp:param name="menuLevel1" value="audit" />
			<jsp:param name="menuLevel2" value="logSearch" />
			<jsp:param name="menuLevel3" value="" />
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
							CA操作日志 <small></small>
						</h3>
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="index.html">CA审核</a> <i class="icon-angle-right"></i></li>
							<li><a href="#">操作日志</a></li>
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
				<div class="row-fluid">
					<div class="span8">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box green">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-globe"></i>日志搜索条件
								</div>
							</div>
							<div class="portlet-body form">
								<!-- BEGIN FORM-->
								<form name="searchForm"
								 method="post" id="logsear_searchForm" action="${pageContext.request.contextPath}/audit/logSearch.html" class="form-horizontal">
								<!--  -->
								<input type='hidden' name='currentPage' id='currentPage' value='${currentPage}'>
								<input type='hidden' name='rowsPerPage' id='rowsPerPage' value='${rowsPerPage}'>
							 
									
									<div class="control-group">
										<label class="control-label">时间</label>
										<div class="controls">
										<div class="input-append date form_datetime" id="logsear_datetimepicker">
											<input name="filter_timeStampBegin" size="16" style="width:150px;" type="text"  value="${filter_timeStampBegin }" readonly class="m-wrap">
											<span class="add-on"><i class="icon-calendar"></i></span>
										</div>
										-
										<div class="input-append date form_datetime" id="logsear_datetimepicker">
											<input name="filter_timeStampEng" size="16" style="width:150px;" type="text"  value="${filter_timeStampEng }" readonly class="m-wrap">
											<span class="add-on"><i class="icon-calendar"></i></span>
										</div>
										</div>
										
									</div>
									<div class="control-group">
										<label class="control-label">模块</label>
										<div class="controls">
											<input name="filter_authToken" value="${filter_authToken }" type="text" placeholder="请输入模块" class="m-wrap medium" />
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">Browser</label>
										<div class="controls">
											<input name="filter_eventTypeValue" value="${filter_eventTypeValue }" type="text" placeholder="请输入Browser" class="m-wrap medium" />
										</div>
									</div>
									
									<div class="form-actions">
										<button type="submit" class="btn blue">
											<i class="icon-ok"></i> 查询
										</button>
										<button type="button" class="btn" onclick="resetSearch('logsear')">重置</button>
									</div>
								</form>
								<!-- END FORM-->
							</div>
						</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
				</div>
				<!-- END PAGE CONTENT===搜索条件===-->
				<!-- BEGIN PAGE CONTENT===搜索结果===-->
				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box green">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-reorder"></i> <span class="hidden-480">日志搜索结果</span>
								</div>
							</div>
							<div class="portlet-body">
								
								<div class="span12">
									<div class="dataTables_paginate paging_bootstrap pagination">
									 
									     <szca:pager rowsCount="${totalRowsCount}" id="logPagerBar" currentPage="${currentPage}" rowsPerPage="${ rowsPerPage}"/>
										<%--
											<ul>
												<li class="prev"><a href="#"><span class="hidden-480"> << </span></a></li>
												<li class="prev"><a href="#"><span class="hidden-480"> < </span></a></li>
												<li><a href="#">1</a></li>
												<li><a href="#">2</a></li>
												<li><a href="#">3</a></li>
												<li><a href="#">4</a></li>
												<li class="active"><a href="#">5</a></li>
												<li class="next disabled"><a href="#"><span class="hidden-480">>></span></a></li>
												<li class="next disabled"><a href="#"><span class="hidden-480">></span></a></li> 
											</ul> --%>
									</div>
								</div>
								<table class="table table-striped table-bordered table-hover table-full-width ">
									<tr>
										<th>时间</th>
										<th>Browser</th>
										<th class="hidden-480">模块</th>
										<th class="hidden-480">管理员</th>
										<th class="hidden-480">实体ID</th>
									</tr>
									<c:forEach items="${resultList }" var="log">
										<tr>
											<td>${szca:formatDate(log.timeStamp)}</td>
											<td>${log.eventTypeValue}</td>
											<td>${log.authToken}</td>
											<td>${log.customId}</td>
											<td>${log.moduleTypeValue}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<!-- END EXAMPLE TABLE PORTLET-->
						</div>
					</div>
				</div>
				<!-- END PAGE CONTENT===搜索结果===-->
			</div>
			<!-- END PAGE CONTAINER-->
		</div>
	</div>
	<!-- END PAGE -->
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@include file="../common/footer.jsp"%>
	<!-- END FOOTER -->
</body>
<script>
	function goto_page(pageNo) {
		//searchForm.currentPage.value=pageNo;
		document.getElementById("currentPage").value = pageNo;
		searchForm.action = "${pageContext.request.contextPath}/audit/logSearch.html";
		searchForm.submit();
	}
</script>
<!-- END BODY -->
<%@include file="../common/corePlugin.jsp"%>

<script type="text/javascript">
function resetSearch(tableID) {
    var searchForm=$("#"+tableID+"_searchForm");
    if(searchForm.size()>0){
        searchForm.find("input").each(function(){
            if (this.type == "checkbox") {
                $(this).prop("checked",false);
            } else if(this.type == "button"
            		|| this.name=="currentPage"
            		|| this.name=="rowsPerPage"){
                //do nothing
            } else {
                $(this).val(null);
            }
        });
        searchForm.find("select").each(function(){
            $(this).val(null);
        });
    }
};

$(document).ready(function() {   
	$.fn.datetimepicker.dates['cn'] = {   //切换为中文显示  
		    days: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],  
		            daysShort: ["日", "一", "二", "三", "四", "五", "六", "日"],  
		            daysMin: ["日", "一", "二", "三", "四", "五", "六", "日"],  
		            months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],  
		            monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
		            meridiem: ["上午", "下午"],
		            today: "今天" 
		    }; 
	
	
	   $(".form_datetime").datetimepicker({
		 format: "yyyy-mm-dd  hh:ii:ss",
		 language: 'cn',          
         pickerPosition: "bottom-right"
    });
	});
</script>
<script type="text/javascript" charset="UTF-8"
	src="${pageContext.request.contextPath}/media/js/bootstrap-datetimepicker.js"></script>
</html>




