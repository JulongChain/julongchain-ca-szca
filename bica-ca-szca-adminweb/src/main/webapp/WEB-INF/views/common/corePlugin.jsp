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

<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<script src="${pageContext.request.contextPath}/media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="${pageContext.request.contextPath}/media/js/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/bootstrap.min.js" type="text/javascript"></script>
<!--[if lt IE 9]>

	<script src="${pageContext.request.contextPath}/media/js/excanvas.min.js"></script>

	<script src="${pageContext.request.contextPath}/media/js/respond.min.js"></script>  

	<![endif]-->
<script src="${pageContext.request.contextPath}/media/js/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/jquery.cookie.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/jquery.uniform.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${pageContext.request.contextPath}/media/js/jquery.vmap.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/jquery.vmap.russia.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/jquery.vmap.world.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/jquery.vmap.europe.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/jquery.vmap.germany.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/jquery.vmap.usa.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/jquery.vmap.sampledata.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/jquery.flot.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/jquery.flot.resize.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/jquery.pulsate.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/date.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/daterangepicker.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/jquery.gritter.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/fullcalendar.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/jquery.easy-pie-chart.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/jquery.sparkline.min.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="${pageContext.request.contextPath}/media/js/app.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/media/js/index.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script>
	jQuery(document).ready(function() {

		App.init(); // initlayout and core plugins

		//Index.init();

		//Index.initJQVMAP(); // init index page's custom scripts

		//Index.initCalendar(); // init index page's custom scripts

		//Index.initCharts(); // init index page's custom scripts

		//Index.initChat();

		//Index.initMiniCharts();

		//	Index.initDashboardDaterange();

		//Index.initIntro();

	});
</script>
<!-- END JAVASCRIPTS -->