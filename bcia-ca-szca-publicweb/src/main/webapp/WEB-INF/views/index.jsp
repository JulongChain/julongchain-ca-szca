<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<html>
<head>
<%@include file="common/metaHead.jsp"%>
</head>
<body>
	<!-- START nav -->
	<jsp:include page="common/topNav.jsp" flush="true">
		<jsp:param value="menu_home" name="currMenu" />
	</jsp:include>

	<!-- END nav -->


	<section class="probootstrap-cover overflow-hidden relative" style="background-image: url('assets/images/bg_1.jpg');" data-stellar-background-ratio="0.5"
		id="section-home">
		<div class="overlay"></div>
		<div class="container">
			<div class="row align-items-center">
				<div class="col-md">
					<h2 class="heading mb-2 display-4 font-light probootstrap-animate">区块链密码创新联盟</h2>
					<p class="lead mb-5 probootstrap-animate"> 基于Java的区块链的国产密码应用</p>
					
				 
					<%--
					<p class="lead mb-5 probootstrap-animate"></p>
					<a href="onepage.html" role="button" class="btn btn-primary p-3 mr-3 pl-5 pr-5 text-uppercase d-lg-inline d-md-inline d-sm-block d-block mb-3">See
						OnePage Verion</a>
					</p> --%>
				</div>
				<%--
				<div class="col-md probootstrap-animate">
					<form action="#" class="probootstrap-form">
						<div class="form-group">
							<div class="row mb-3">
								<div class="col-md">
									<div class="form-group">
										<label for="id_label_single">From</label> <label for="id_label_single" style="width: 100%;"> <select
											class="js-example-basic-single js-states form-control" id="id_label_single" style="width: 100%;">
												<option value="Australia">Australia</option>
												<option value="Japan">Japan</option>
												<option value="United States">United States</option>
												<option value="Brazil">Brazil</option>
												<option value="China">China</option>
												<option value="Israel">Israel</option>
												<option value="Philippines">Philippines</option>
												<option value="Malaysia">Malaysia</option>
												<option value="Canada">Canada</option>
												<option value="Chile">Chile</option>
												<option value="Chile">Zimbabwe</option>
										</select>
										</label>


									</div>
								</div>
								<div class="col-md">
									<div class="form-group">
										<label for="id_label_single2">To</label>
										<div class="probootstrap_select-wrap">
											<label for="id_label_single2" style="width: 100%;"> <select class="js-example-basic-single js-states form-control" id="id_label_single2"
												style="width: 100%;">
													<option value="Australia">Australia</option>
													<option value="Japan">Japan</option>
													<option value="United States">United States</option>
													<option value="Brazil">Brazil</option>
													<option value="China">China</option>
													<option value="Israel">Israel</option>
													<option value="Philippines">Philippines</option>
													<option value="Malaysia">Malaysia</option>
													<option value="Canada">Canada</option>
													<option value="Chile">Chile</option>
													<option value="Chile">Zimbabwe</option>
											</select>
											</label>
										</div>
									</div>
								</div>
							</div>
							<!-- END row -->
							<div class="row mb-5">
								<div class="col-md">
									<div class="form-group">
										<label for="probootstrap-date-departure">Departure</label>
										<div class="probootstrap-date-wrap">
											<span class="icon ion-calendar"></span> <input type="text" id="probootstrap-date-departure" class="form-control" placeholder="">
										</div>
									</div>
								</div>
								<div class="col-md">
									<div class="form-group">
										<label for="probootstrap-date-arrival">Arrival</label>
										<div class="probootstrap-date-wrap">
											<span class="icon ion-calendar"></span> <input type="text" id="probootstrap-date-arrival" class="form-control" placeholder="">
										</div>
									</div>
								</div>
							</div>
							<!-- END row -->
							<div class="row">
								<div class="col-md">
									<label for="round" class="mr-5"><input type="radio" id="round" name="direction"> Round</label> <label for="oneway"><input type="radio"
										id="oneway" name="direction"> Oneway</label>
								</div>
								<div class="col-md">
									<input type="submit" value="Submit" class="btn btn-primary btn-block">
								</div>
							</div>
						</div>
					</form>
				</div> --%>
			</div>
		</div>

	</section>
	<!-- END section -->
	   

	<section class="probootstrap_section">
		<div class="container">
			<div class="row text-center mb-5 probootstrap-animate">
				<div class="col-md-12">
					<h2 class="display-4 border-bottom probootstrap-section-heading">BCIA JuLongChain</h2>
				</div>
			</div>

			<div class="row probootstrap-animate">
				<div class="col-md-12">
					<div class="owl-carousel js-owl-carousel-2">
						<div>
							<div class="media probootstrap-media d-block align-items-stretch mb-4 probootstrap-animate">
								<img src="assets/images/sq_entity.jpg" alt="Free Template by ProBootstrap" class="img-fluid" onclick="openUrl('registerEntity.html')">
								<div class="media-body">
									<h5 class="mb-3">注册终端实体</h5>
									<p>注册一个终端实体，返回注册密码，请牢记此密码，切莫向他人泄露此密码。给此实体注册证书时需要使用此密码。</p>
								</div>
							</div>
						</div>
						<!-- END slide item -->

						<div>
							<div class="media probootstrap-media d-block align-items-stretch mb-4 probootstrap-animate">
								<img src="assets/images/sq_cert.jpg" alt="Free Template by ProBootstrap" class="img-fluid" onclick="openUrl('enrollCert.html')">
								<div class="media-body">
									<h5 class="mb-3">生成实体证书</h5>
									<p>输入实体名称的实体密码(实体密码是在注册实体时返回),如果输出类型选择了证书(Certificate),则必须上传CSR文件或者输入BASE64编码格式的CSR内容。  
								</div>
							</div>
						</div>
						<!-- END slide item -->

						<div>
							<div class="media probootstrap-media d-block align-items-stretch mb-4 probootstrap-animate">
								<img src="assets/images/sq_chain.jpg" alt="Free Template by ProBootstrap" class="img-fluid" onclick="openUrl('ca_certs.html')">
								<div class="media-body">  
						 
									<h5 class="mb-3">获取CA证书和证书链</h5>
									<p>下载CA的根证书，或者下载CA的证书链。可以选择PEM格式，Firefox格式，IE格式等。若下载证书链，还可以选择JKS格式，此时JKS的密码是changeit</p>
								</div>
							</div>
						</div>
						<!-- END slide item -->

						<div>
							<div class="media probootstrap-media d-block align-items-stretch mb-4 probootstrap-animate">
								<img src="assets/images/sq_crls.jpg" alt="Free Template by ProBootstrap" class="img-fluid" onclick="openUrl('ca_crls.html')">
								<div class="media-body">
									<h5 class="mb-3">获取CA的证书撤销列表CRLs</h5>
									<p>下载CA的证书证书撤销列表，可以是der格式，也可以是pem格式</p>
								</div>
							</div>
						</div>
						<!-- END slide item -->

						<div>
							<div class="media probootstrap-media d-block align-items-stretch mb-4 probootstrap-animate">
								<img src="assets/images/sq_usercert.jpg" alt="Free Template by ProBootstrap" class="img-fluid" onclick="openUrl('user_cert.html')">
								<div class="media-body">
									<h5 class="mb-3">下载实体证书 </h5>
									<p>根据实体名称、证书主题、证书序列号等，下载指定的证书，可以指定一个或者多个条件。若有多个证书符合条件，则返回最新的一个证书。</p>
								</div>
							</div>
						</div>
						<!-- END slide item -->


							<div>
							<div class="media probootstrap-media d-block align-items-stretch mb-4 probootstrap-animate">
								<img src="assets/images/sq_csr.jpg" alt="Free Template by ProBootstrap" class="img-fluid" onclick="openUrl('miscellaneous.html')">
								<div class="media-body">
									<h5 class="mb-3">常用CSR、证书工具 </h5>
									<p>提供多种常用工具，包含生成RSA、SM2等算法的密钥、CSR，解析RSA、SM2等算法的CSR，检测证书密钥匹配功能的在线工具。</p>
								</div>
								
							 
								
								
							</div>
						</div>
						<!-- END slide item -->
						<%--
						<div>
							<div class="media probootstrap-media d-block align-items-stretch mb-4 probootstrap-animate">
								<img src="assets/images/sq_img_2.jpg" alt="Free Template by ProBootstrap" class="img-fluid">
								<div class="media-body">
									<h5 class="mb-3">02. Service Title Here</h5>
									<p>ccccccccccc</p>
								</div>
							</div>
						</div>
						<!-- END slide item -->

						<div>
							<div class="media probootstrap-media d-block align-items-stretch mb-4 probootstrap-animate">
								<img src="assets/images/sq_img_1.jpg" alt="Free Template by ProBootstrap" class="img-fluid">
								<div class="media-body">
									<h5 class="mb-3">02. Service Title Here</h5>
									<p>ddddd</p>
								</div>
							</div>
						</div>
						<!-- END slide item -->

						<div>
							<div class="media probootstrap-media d-block align-items-stretch mb-4 probootstrap-animate">
								<img src="assets/images/sq_img_3.jpg" alt="Free Template by ProBootstrap" class="img-fluid">
								<div class="media-body">
									<h5 class="mb-3">02. Service Title Here</h5>
									<p>月月月有</p>
								</div>
							</div>
						</div>
						<!-- END slide item -->

						<div>
							<div class="media probootstrap-media d-block align-items-stretch mb-4 probootstrap-animate">
								<img src="assets/images/sq_img_4.jpg" alt="Free Template by ProBootstrap" class="img-fluid">
								<div class="media-body">
									<h5 class="mb-3">02. Service Title Here</h5>
									<p>fffffffff</p>
								</div>
							</div>
						</div>
						<!-- END slide item -->

						<div>
							<div class="media probootstrap-media d-block align-items-stretch mb-4 probootstrap-animate">
								<img src="assets/images/sq_img_5.jpg" alt="Free Template by ProBootstrap" class="img-fluid">
								<div class="media-body">
									<h5 class="mb-3">02. Service Title Here</h5>
									<p>刚刚嘎嘎嘎</p>
								</div>
							</div>
						</div>
						<!-- END slide item -->
 					--%>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- END section -->
	<!-- START Footer -->
	<%@include file="common/footer.jsp" %>
	<!-- END Footer -->
</body>

<script type="text/javascript">
function openUrl(url){
	window.location.href=url;
}
</script>
</html>