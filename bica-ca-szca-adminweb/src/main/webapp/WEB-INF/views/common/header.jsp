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

<!-- BEGIN HEADER -->
<div class="header navbar navbar-inverse navbar-fixed-top">
	<!-- BEGIN TOP NAVIGATION BAR -->
	<div class="navbar-inner">
		<div class="container-fluid">
			<!-- BEGIN LOGO -->
			<a class="brand" href="index.html"> <img src="${pageContext.request.contextPath}/media/image/logo.png" alt="logo" />
			</a>
			<!-- END LOGO -->
			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
			<a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse"> <img src="${pageContext.request.contextPath}/media/image/menu-toggler.png" alt="" />
			</a>
			<!-- END RESPONSIVE MENU TOGGLER -->
			<!-- BEGIN TOP NAVIGATION MENU -->
			<ul class="nav pull-right">
				<!-- BEGIN NOTIFICATION DROPDOWN -->
				<%--
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
					</ul></li> --%>
				<!-- END NOTIFICATION DROPDOWN -->
				<!-- BEGIN INBOX DROPDOWN -->
				<%--
				<li class="dropdown" id="header_inbox_bar"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i class="icon-envelope"></i> <span class="badge">5</span>
				</a>
					<ul class="dropdown-menu extended inbox">
						<li>
							<p>You have 12 new messages</p>
						</li>
						<li><a href="inbox.html?a=view"> <span class="photo"><img src="${pageContext.request.contextPath}/media/image/avatar2.jpg" alt="" /></span> <span class="subject"> <span class="from">Lisa Wong</span> <span
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
					</ul></li> --%>
				<!-- END INBOX DROPDOWN -->
				<!-- BEGIN TODO DROPDOWN -->
				<%--
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
					</ul></li> --%>
				<!-- END TODO DROPDOWN -->
				<!-- BEGIN USER LOGIN DROPDOWN -->
				<c:if test="${!empty LOGIN_USER_KEY_IN_SESSION}">
				<li class="dropdown user"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> <img alt="" src="${pageContext.request.contextPath}/media/image/avatar1_small.jpg" /> <span class="username">${LOGIN_USER_KEY_IN_SESSION.username}</span> <i
						class="icon-angle-down"></i>
				</a>
					<ul class="dropdown-menu">
					<li><i class="icon-user"></i>${LOGIN_USER_KEY_IN_SESSION.fullName }</li>
						<li><i class="icon-tasks"></i>${LOGIN_USER_KEY_IN_SESSION.dn }</li>
						<li><i class="icon-tasks"></i>${LOGIN_USER_KEY_IN_SESSION.issuerDn }</li>
						<li><i class="icon-tasks"></i>${LOGIN_USER_KEY_IN_SESSION.sn }</li>
					<%--
						<li><a href="extra_profile.html"><i class="icon-user"></i> My Profile</a></li>
						<li><a href="page_calendar.html"><i class="icon-calendar"></i> My Calendar</a></li>
						<li><a href="inbox.html"><i class="icon-envelope"></i> My Inbox(3)</a></li>
						<li><a href="#"><i class="icon-tasks"></i> My Tasks</a></li>
						<li class="divider"></li>
						<li><a href="extra_lock.html"><i class="icon-lock"></i> Lock Screen</a></li>
						<li><a href="login.html"><i class="icon-key"></i> Log Out</a></li>
						 --%>
					</ul>										 
					</li></c:if>
				<!-- END USER LOGIN DROPDOWN -->
			</ul>
			<!-- END TOP NAVIGATION MENU -->
		</div>
	</div>
	<!-- END TOP NAVIGATION BAR -->
</div>
<!-- END HEADER -->