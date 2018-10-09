
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

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="no-js">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- BEGIN HEADER -->
<meta charset="utf-8" />
<title>BCIA - CA系统</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="media/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="media/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
<link href="media/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="media/css/style-metro.css" rel="stylesheet" type="text/css" />
<link href="media/css/style.css" rel="stylesheet" type="text/css" />
<link href="media/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="media/css/default.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="media/css/uniform.default.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="media/css/jquery.gritter.css" rel="stylesheet" type="text/css" />
<link href="media/css/daterangepicker.css" rel="stylesheet" type="text/css" />
<link href="media/css/fullcalendar.css" rel="stylesheet" type="text/css" />
<link href="media/css/jqvmap.css" rel="stylesheet" type="text/css" media="screen" />
<link href="media/css/jquery.easy-pie-chart.css" rel="stylesheet" type="text/css" media="screen" />
<!-- END PAGE LEVEL STYLES -->
<link rel="shortcut icon" href="media/image/favicon.ico" />
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<!-- BEGIN HEADER -->
	<div class="header navbar navbar-inverse navbar-fixed-top">
		<!-- BEGIN TOP NAVIGATION BAR -->
		<div class="navbar-inner">
			<div class="container-fluid">
				<!-- BEGIN LOGO -->
				<!--<a class="brand" href="index.html"> <img src="media/image/logo.png" alt="logo" />
				</a>-->
				<!-- END LOGO -->
				<!-- BEGIN RESPONSIVE MENU TOGGLER -->
				<a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse"> <img src="media/image/menu-toggler.png" alt="" />
				</a>
				<!-- END RESPONSIVE MENU TOGGLER -->
				<!-- BEGIN TOP NAVIGATION MENU -->
				<ul class="nav pull-right">
					<!-- BEGIN NOTIFICATION DROPDOWN -->
					<li class="dropdown" id="header_notification_bar"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i class="icon-warning-sign"></i> <span class="badge">6</span>
					</a>
						<ul class="dropdown-menu extended notification">
							<li>
								<p>You have 14 new notifications</p>
							</li>
							<li><a href="#"> <span class="label label-success"><i class="icon-plus"></i></span> New user registered. <span class="time">Just now</span>
							</a></li>
							<li><a href="#"> <span class="label label-important"><i class="icon-bolt"></i></span> Server #12 overloaded. <span class="time">15 mins</span>
							</a></li>
							<li><a href="#"> <span class="label label-warning"><i class="icon-bell"></i></span> Server #2 not respoding. <span class="time">22 mins</span>
							</a></li>
							<li><a href="#"> <span class="label label-info"><i class="icon-bullhorn"></i></span> Application error. <span class="time">40 mins</span>
							</a></li>
							<li><a href="#"> <span class="label label-important"><i class="icon-bolt"></i></span> Database overloaded 68%. <span class="time">2 hrs</span>
							</a></li>
							<li><a href="#"> <span class="label label-important"><i class="icon-bolt"></i></span> 2 user IP blocked. <span class="time">5 hrs</span>
							</a></li>
							<li class="external"><a href="#">See all notifications <i class="m-icon-swapright"></i></a></li>
						</ul></li>
					<!-- END NOTIFICATION DROPDOWN -->
					<!-- BEGIN INBOX DROPDOWN -->
					<li class="dropdown" id="header_inbox_bar"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i class="icon-envelope"></i> <span class="badge">5</span>
					</a>
						<ul class="dropdown-menu extended inbox">
							<li>
								<p>You have 12 new messages</p>
							</li>
							<li><a href="inbox.html?a=view"> <span class="photo"><img src="media/image/avatar2.jpg" alt="" /></span> <span class="subject"> <span class="from">Lisa Wong</span> <span
										class="time">Just Now</span>
								</span> <span class="message"> Vivamus sed auctor nibh congue nibh. auctor nibh auctor nibh... </span>
							</a></li>
							<li><a href="inbox.html?a=view"> <span class="photo"><img src="./media/image/avatar3.jpg" alt="" /></span> <span class="subject"> <span class="from">Richard Doe</span> <span
										class="time">16 mins</span>
								</span> <span class="message"> Vivamus sed congue nibh auctor nibh congue nibh. auctor nibh auctor nibh... </span>
							</a></li>
							<li><a href="inbox.html?a=view"> <span class="photo"><img src="./media/image/avatar1.jpg" alt="" /></span> <span class="subject"> <span class="from">Bob Nilson</span> <span
										class="time">2 hrs</span>
								</span> <span class="message"> Vivamus sed nibh auctor nibh congue nibh. auctor nibh auctor nibh... </span>
							</a></li>
							<li class="external"><a href="inbox.html">See all messages <i class="m-icon-swapright"></i>
							</a></li>
						</ul></li>
					<!-- END INBOX DROPDOWN -->
					<!-- BEGIN TODO DROPDOWN -->
					<li class="dropdown" id="header_task_bar"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i class="icon-tasks"></i> <span class="badge">5</span>
					</a>
						<ul class="dropdown-menu extended tasks">
							<li>
								<p>You have 12 pending tasks</p>
							</li>
							<li><a href="#"> <span class="task"> <span class="desc">New release v1.2</span> <span class="percent">30%</span>
								</span> <span class="progress progress-success "> <span style="width: 30%;" class="bar"></span>
								</span>
							</a></li>
							<li><a href="#"> <span class="task"> <span class="desc">Application deployment</span> <span class="percent">65%</span>
								</span> <span class="progress progress-danger progress-striped active"> <span style="width: 65%;" class="bar"></span>
								</span>
							</a></li>
							<li><a href="#"> <span class="task"> <span class="desc">Mobile app release</span> <span class="percent">98%</span>
								</span> <span class="progress progress-success"> <span style="width: 98%;" class="bar"></span>
								</span>
							</a></li>
							<li><a href="#"> <span class="task"> <span class="desc">Database migration</span> <span class="percent">10%</span>
								</span> <span class="progress progress-warning progress-striped"> <span style="width: 10%;" class="bar"></span>
								</span>
							</a></li>
							<li><a href="#"> <span class="task"> <span class="desc">Web server upgrade</span> <span class="percent">58%</span>
								</span> <span class="progress progress-info"> <span style="width: 58%;" class="bar"></span>
								</span>
							</a></li>
							<li><a href="#"> <span class="task"> <span class="desc">Mobile development</span> <span class="percent">85%</span>
								</span> <span class="progress progress-success"> <span style="width: 85%;" class="bar"></span>
								</span>
							</a></li>
							<li class="external"><a href="#">See all tasks <i class="m-icon-swapright"></i></a></li>
						</ul></li>
					<!-- END TODO DROPDOWN -->
					<!-- BEGIN USER LOGIN DROPDOWN -->
					<li class="dropdown user"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> <img alt="" src="media/image/avatar1_small.jpg" /> <span class="username">Bob Nilson</span> <i
							class="icon-angle-down"></i>
					</a>
						<ul class="dropdown-menu">
							<li><a href="extra_profile.html"><i class="icon-user"></i> My Profile</a></li>
							<li><a href="page_calendar.html"><i class="icon-calendar"></i> My Calendar</a></li>
							<li><a href="inbox.html"><i class="icon-envelope"></i> My Inbox(3)</a></li>
							<li><a href="#"><i class="icon-tasks"></i> My Tasks</a></li>
							<li class="divider"></li>
							<li><a href="extra_lock.html"><i class="icon-lock"></i> Lock Screen</a></li>
							<li><a href="login.html"><i class="icon-key"></i> Log Out</a></li>
						</ul></li>
					<!-- END USER LOGIN DROPDOWN -->
				</ul>
				<!-- END TOP NAVIGATION MENU -->
			</div>
		</div>
		<!-- END TOP NAVIGATION BAR -->
	</div>
	<!-- END HEADER -->
	<!-- END HEADER -->
	<div class="copyrights">
		Collect from <a href="http://www.cssmoban.com/">网页模板</a>
	</div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- START SIDEBAR -->
		<!-- BEGIN SIDEBAR -->
		<div class="page-sidebar nav-collapse collapse">
			<!-- BEGIN SIDEBAR MENU -->
			<ul class="page-sidebar-menu">
				<li>
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
					<div class="sidebar-toggler hidden-phone"></div> <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
				</li>
				<li>
					<!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
					<form class="sidebar-search">
						<div class="input-box">
							<a href="javascript:;" class="remove"></a> <input type="text" placeholder="Search..." /> <input type="button" class="submit" value=" " />
						</div>
					</form> <!-- END RESPONSIVE QUICK SEARCH FORM -->
				</li>
				<li class="start active "><a href="index.html"> <i class="icon-home"></i> <span class="title">首页</span> <span class="selected"></span>
				</a></li>
				<li class=""><a href="javascript:;"> <i class="icon-cogs"></i> <span class="title">CA功能</span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="layout_horizontal_sidebar_menu.html"> CA启动激活</a></li>
						<li><a href="layout_horizontal_menu1.html">CA基本功能</a></li>
						<li><a href="layout_horizontal_menu2.html"> 证书模板</a></li>
						<li><a href="layout_promo.html">CA配置管理</a></li>
						<li><a href="layout_email.html">秘钥配置管理</a></li>
						<li><a href="layout_ajax.html"> 发布器配置管理</a></li>
						<li><a href="layout_sidebar_closed.html"> Sidebar Closed Page</a></li>
						<li><a href="layout_blank_page.html"> Blank Page</a></li>
						<li><a href="layout_boxed_page.html"> Boxed Page</a></li>
						<li><a href="layout_boxed_not_responsive.html"> Non-Responsive Boxed Layout</a></li>
					</ul></li>
				<li class=""><a href="javascript:;"> <i class="icon-bookmark-empty"></i> <span class="title">RA 功能</span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="ui_general.html"> 添加终端实体</a></li>
						<li><a href="ui_buttons.html">终端实体模板配置管理</a></li>
						<li><a href="ui_modals.html"> 终端实体管理</a></li>
						<li><a href="ui_tabs_accordions.html"> 用户数据源配置管理</a></li>
						<li><a href="ui_jqueryui.html"> jQuery UI Components</a></li>
						<li><a href="ui_sliders.html"> Sliders</a></li>
						<li><a href="ui_tiles.html"> Tiles</a></li>
						<li><a href="ui_typography.html"> Typography</a></li>
						<li><a href="ui_tree.html"> Tree View</a></li>
						<li><a href="ui_nestable.html"> Nestable List</a></li>
					</ul></li>
				<li class=""><a href="javascript:;"> <i class="icon-table"></i> <span class="title">监察员功能</span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="form_layout.html"> 操作审核</a></li>
						<li><a href="form_samples.html">日志</a></li>
						<li><a href="form_component.html"> Form Components</a></li>
						<li><a href="form_wizard.html"> Form Wizard</a></li>
						<li><a href="form_validation.html"> Form Validation</a></li>
						<li><a href="form_fileupload.html"> Multiple File Upload</a></li>
						<li><a href="form_dropzone.html"> Dropzone File Upload</a></li>
					</ul></li>
				<li class=""><a href="javascript:;"> <i class="icon-briefcase"></i> <span class="title">系统功能</span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="page_timeline.html"> <i class="icon-time"></i> 管理员权限配置管理
						</a></li>
						<li><a href="page_coming_soon.html"> <i class="icon-cogs"></i> 秘钥绑定配置管理
						</a></li>
						<li><a href="page_blog.html"> <i class="icon-comments"></i>服务配置管理
						</a></li>
						<li><a href="page_blog_item.html"> <i class="icon-font"></i> Blog Post
						</a></li>
						<li><a href="page_news.html"> <i class="icon-coffee"></i> News
						</a></li>
						<li><a href="page_news_item.html"> <i class="icon-bell"></i> News View
						</a></li>
						<li><a href="page_about.html"> <i class="icon-group"></i> About Us
						</a></li>
						<li><a href="page_contact.html"> <i class="icon-envelope-alt"></i> Contact Us
						</a></li>
						<li><a href="page_calendar.html"> <i class="icon-calendar"></i> Calendar
						</a></li>
					</ul></li>
				<li class=""><a href="javascript:;"> <i class="icon-gift"></i> <span class="title">系统配置</span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="extra_profile.html">CMP配置管理</a></li>
						<li><a href="extra_lock.html"> SCEP配置管理</a></li>
						<li><a href="extra_faq.html"> 系统配置管理</a></li>
						<li><a href="inbox.html"> Inbox</a></li>
						<li><a href="extra_search.html"> Search Results</a></li>
						<li><a href="extra_invoice.html"> Invoice</a></li>
						<li><a href="extra_pricing_table.html"> Pricing Tables</a></li>
						<li><a href="extra_image_manager.html"> Image Manager</a></li>
						<li><a href="extra_404_option1.html"> 404 Page Option 1</a></li>
						<li><a href="extra_404_option2.html"> 404 Page Option 2</a></li>
						<li><a href="extra_404_option3.html"> 404 Page Option 3</a></li>
						<li><a href="extra_500_option1.html"> 500 Page Option 1</a></li>
						<li><a href="extra_500_option2.html"> 500 Page Option 2</a></li>
					</ul></li>
				<li><a class="active" href="javascript:;"> <i class="icon-sitemap"></i> <span class="title">3 Level Menu</span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="javascript:;"> Item 1 <span class="arrow"></span>
						</a>
							<ul class="sub-menu">
								<li><a href="#">Sample Link 1</a></li>
								<li><a href="#">Sample Link 2</a></li>
								<li><a href="#">Sample Link 3</a></li>
							</ul></li>
						<li><a href="javascript:;"> Item 1 <span class="arrow"></span>
						</a>
							<ul class="sub-menu">
								<li><a href="#">Sample Link 1</a></li>
								<li><a href="#">Sample Link 1</a></li>
								<li><a href="#">Sample Link 1</a></li>
							</ul></li>
						<li><a href="#"> Item 3 </a></li>
					</ul></li>
				<li><a href="javascript:;"> <i class="icon-folder-open"></i> <span class="title">4 Level Menu</span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="javascript:;"> <i class="icon-cogs"></i> Item 1 <span class="arrow"></span>
						</a>
							<ul class="sub-menu">
								<li><a href="javascript:;"> <i class="icon-user"></i> Sample Link 1 <span class="arrow"></span>
								</a>
									<ul class="sub-menu">
										<li><a href="#"><i class="icon-remove"></i> Sample Link 1</a></li>
										<li><a href="#"><i class="icon-pencil"></i> Sample Link 1</a></li>
										<li><a href="#"><i class="icon-edit"></i> Sample Link 1</a></li>
									</ul></li>
								<li><a href="#"><i class="icon-user"></i> Sample Link 1</a></li>
								<li><a href="#"><i class="icon-external-link"></i> Sample Link 2</a></li>
								<li><a href="#"><i class="icon-bell"></i> Sample Link 3</a></li>
							</ul></li>
						<li><a href="javascript:;"> <i class="icon-globe"></i> Item 2 <span class="arrow"></span>
						</a>
							<ul class="sub-menu">
								<li><a href="#"><i class="icon-user"></i> Sample Link 1</a></li>
								<li><a href="#"><i class="icon-external-link"></i> Sample Link 1</a></li>
								<li><a href="#"><i class="icon-bell"></i> Sample Link 1</a></li>
							</ul></li>
						<li><a href="#"> <i class="icon-folder-open"></i> Item 3
						</a></li>
					</ul></li>
				<li class=""><a href="javascript:;"> <i class="icon-user"></i> <span class="title">Login Options</span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="login.html"> Login Form 1</a></li>
						<li><a href="login_soft.html"> Login Form 2</a></li>
					</ul></li>
				<li class=""><a href="javascript:;"> <i class="icon-th"></i> <span class="title">数据表格</span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="table_basic.html"> Basic Tables</a></li>
						<li><a href="table_responsive.html"> Responsive Tables</a></li>
						<li><a href="table_managed.html"> Managed Tables</a></li>
						<li><a href="table_editable.html"> Editable Tables</a></li>
						<li><a href="table_advanced.html"> Advanced Tables</a></li>
					</ul></li>
				<li class=""><a href="javascript:;"> <i class="icon-file-text"></i> <span class="title">Portlets</span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="portlet_general.html"> General Portlets</a></li>
						<li><a href="portlet_draggable.html"> Draggable Portlets</a></li>
					</ul></li>
				<li class=""><a href="javascript:;"> <i class="icon-map-marker"></i> <span class="title">Maps</span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="maps_google.html"> Google Maps!!!!!!!!</a></li>
						<li><a href="maps_vector.html"> Vector Maps</a></li>
					</ul></li>
				<li class="last "><a href="charts.html"> <i class="icon-bar-chart"></i> <span class="title">管理员首选项</span>
				</a></li>
				<li class="last "><a href="charts.html"> <i class="icon-bar-chart"></i> <span class="title">公共前端网店</span>
				</a></li>
				<li class="last "><a href="charts.html"> <i class="icon-bar-chart"></i> <span class="title">文档</span>
				</a></li>
				<li class="last "><a href="charts.html"> <i class="icon-bar-chart"></i> <span class="title">登出</span>
				</a></li>
			</ul>
			<!-- END SIDEBAR MENU -->
		</div>
		<!-- END SIDEBAR -->
		<!-- END SIDEBAR -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<div id="portlet-config" class="modal hide">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button"></button>
					<h3>Widget Settings</h3>
				</div>
				<div class="modal-body">Widget settings form goes here</div>
			</div>
			<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<!-- BEGIN PAGE CONTAINER-->
			<div class="container-fluid">
				<!-- BEGIN PAGE HEADER-->
				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN STYLE CUSTOMIZER -->
						<!-- END BEGIN STYLE CUSTOMIZER -->
						<!-- BEGIN PAGE TITLE & BREADCRUMB-->
						<h3 class="page-title">
							Dashboard <small>statistics and more</small>
						</h3>
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="index.html">Home</a> <i class="icon-angle-right"></i></li>
							<li><a href="#">Dashboard</a></li>
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
				<!-- END DASHBOARD STATS -->
				<div class="clearfix"></div>
				<div class="row-fluid">
					<div class="span6">sdf</div>
					<div class="span6">gasd</div>
				</div>
			</div>
		</div>
		<!-- END PAGE CONTAINER-->
	</div>
	<!-- END PAGE -->
 
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<!-- BEGIN FOOTER -->
	<div class="footer">
		<div class="footer-inner">
			2013 &copy; Metronic by keenthemes.Collect from <a href="http://www.cssmoban.com/" title="网站模板" target="_blank">网站模板</a> - More Templates <a href="http://www.cssmoban.com/" target="_blank"
				title="模板之家">模板之家</a>
		</div>
		<div class="footer-tools">
			<span class="go-top"> <i class="icon-angle-up"></i>
			</span>
		</div>
	</div>
	<!-- END FOOTER -->
	<!-- END FOOTER -->
</body>
<!-- END BODY -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<script src="media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="media/js/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="media/js/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
<script src="media/js/bootstrap.min.js" type="text/javascript"></script>
<!--[if lt IE 9]>

	<script src="media/js/excanvas.min.js"></script>

	<script src="media/js/respond.min.js"></script>  

	<![endif]-->
<script src="media/js/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="media/js/jquery.blockui.min.js" type="text/javascript"></script>
<script src="media/js/jquery.cookie.min.js" type="text/javascript"></script>
<script src="media/js/jquery.uniform.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="media/js/jquery.vmap.js" type="text/javascript"></script>
<script src="media/js/jquery.vmap.russia.js" type="text/javascript"></script>
<script src="media/js/jquery.vmap.world.js" type="text/javascript"></script>
<script src="media/js/jquery.vmap.europe.js" type="text/javascript"></script>
<script src="media/js/jquery.vmap.germany.js" type="text/javascript"></script>
<script src="media/js/jquery.vmap.usa.js" type="text/javascript"></script>
<script src="media/js/jquery.vmap.sampledata.js" type="text/javascript"></script>
<script src="media/js/jquery.flot.js" type="text/javascript"></script>
<script src="media/js/jquery.flot.resize.js" type="text/javascript"></script>
<script src="media/js/jquery.pulsate.min.js" type="text/javascript"></script>
<script src="media/js/date.js" type="text/javascript"></script>
<script src="media/js/daterangepicker.js" type="text/javascript"></script>
<script src="media/js/jquery.gritter.js" type="text/javascript"></script>
<script src="media/js/fullcalendar.min.js" type="text/javascript"></script>
<script src="media/js/jquery.easy-pie-chart.js" type="text/javascript"></script>
<script src="media/js/jquery.sparkline.min.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="media/js/app.js" type="text/javascript"></script>
<script src="media/js/index.js" type="text/javascript"></script>
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
</html>