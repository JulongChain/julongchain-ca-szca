/*
 * **
 *  *
 *  * Copyright © 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 *  * Copyright © 2018  SZCA. All Rights Reserved.
 *  * <p>
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * <p>
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  * <p>
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 *
 */

package org.bcia.javachain.ca.szca.publicweb.api.constroller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.security.auth.x500.X500Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.bcia.javachain.ca.szca.publicweb.api.ApiAdminUserData;
import org.bcia.javachain.ca.szca.publicweb.api.ApiCaInfo;
import org.bcia.javachain.ca.szca.publicweb.api.ApiError;
import org.bcia.javachain.ca.szca.publicweb.api.ApiMessage;
import org.bcia.javachain.ca.szca.publicweb.api.ApiResult;
import org.bcia.javachain.ca.szca.publicweb.api.CallConfigData;
import org.bcia.javachain.ca.szca.publicweb.api.CallLogData;
import org.bcia.javachain.ca.szca.publicweb.api.service.ISzcaApiService;
import org.cesecore.authentication.tokens.AlwaysAllowLocalAuthenticationToken;
import org.cesecore.authentication.tokens.AuthenticationSubject;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authentication.tokens.UsernamePrincipal;
import org.cesecore.authentication.tokens.X509CertificateAuthenticationToken;
import org.cesecore.certificates.certificate.CertificateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cn.net.bcia.bcca.core.ejb.authentication.web.WebAuthenticationProviderSessionLocal;
import cn.net.bcia.cesecore.certificates.certificate.CertificateStoreSession;

public class BaseApiController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	ISzcaApiService apiService;

	@Autowired
	CertificateStoreSession certificateStoreSessionBean;

	@Autowired
	WebAuthenticationProviderSessionLocal authenticationSession;

	protected boolean checkApiCallRight(CallLogData callLog) {
		String ip = callLog.getIp(); String uri=callLog.getUri(); String username=callLog.getUsername(); String passwd=callLog.getPassword();
		// boolean checkApiCallRight(HttpServletRequest req) {

		// String uri = req.getRequestURI() ;
		String uriUpper = uri.toUpperCase();
		// getcacert不用权限
		if (uriUpper.indexOf("CAINFO") >= 0 || uriUpper.indexOf("GETCACERT") >= 0)
			return true;

		// 全局IP配置
		CallConfigData confAllIp = apiService.getCallConfigByIP("*");
		if (confAllIp != null && ("*".equals(confAllIp.getUri()) || confAllIp.getUri().indexOf(uri) >= 0))
			return true;

		// String ip = req.getRemoteAddr();
		CallConfigData conf = apiService.getCallConfigByIP(ip);
		
		if (conf == null) {
			logger.warn(String.format("尚未授权IP[%s]访问系统接口[%s]", ip, uri));
			return false;
		}
		ApiAdminUserData admin = apiService.getApiAdminUserData(conf.getUsername());
		if (username.equals(conf.getUsername()) && passwd.equals(admin.getPassword()))
			return "*".equals(conf.getUri()) || conf.getUri().indexOf(uri) >= 0;
		else
			return false;

	}

	protected AuthenticationToken getAuthenticationToken(HttpServletRequest req) {
		AuthenticationToken authenticationToken = null;

		final X509Certificate[] certificates = (X509Certificate[]) req
				.getAttribute("javax.servlet.request.X509Certificate");

		if (certificates == null || certificates.length == 0) {
			logger.error("Client certificate required.");

			String auth = this.getAuthorization(req);
			X509Certificate x509cert = null;
			if (auth != null) {
				try {
					ByteArrayInputStream bais = new ByteArrayInputStream(
							org.apache.commons.codec.binary.Base64.decodeBase64(auth));
					CertificateFactory cf = CertificateFactory.getInstance("X.509");
					Certificate cert = cf.generateCertificate(bais);
					if (cert instanceof X509Certificate)
						x509cert = (X509Certificate) cert;
				} catch (Exception ae) {

				}
			}
			final Set<X509Certificate> credentials = new HashSet<X509Certificate>();
			if (x509cert != null) {
				// TODO: 在此增加根据验证证书和根据证书验证权限的代码
				credentials.add(x509cert);
				final AuthenticationSubject subject = new AuthenticationSubject(null, credentials);
				// TODO: 在启用权限控制前，先不根据证书来确定操作权限
				// authenticationToken = authenticationSession.authenticate(subject);
				// TODO: 在启用权限控制前，暂时允许所有用户
				authenticationToken = new AlwaysAllowLocalAuthenticationToken(
						new UsernamePrincipal(req.getRemoteAddr() + ":" + req.getRequestURI() + "="
								+ x509cert.getSubjectDN() + ";" + x509cert.getSerialNumber().toString(61)));

			} else {

				// throw new Exception("无法获得客户端证，请指定客户端登录证书。");
				// AuthenticationToken admin =new AuthenticationToken();
				// 暂时构造一个
				// Collection<CertificateWrapper> superCerts = certificateStoreSessionBean
				// .findCertificatesByUsername("superadmin");
				// CertificateWrapper[] sc = (CertificateWrapper[]) superCerts.toArray(new
				// CertificateWrapper[0]);
				// AlwaysAllowLocalAuthenticationToken admin = new
				// AlwaysAllowLocalAuthenticationToken();
				// final Set<X500Principal> principals = new HashSet<X500Principal>();
				// principals.add(((X509Certificate)
				// (sc[0].getCertificate())).getSubjectX500Principal());
				// final Set<X509Certificate> credentials = new HashSet<X509Certificate>();
				// credentials.add((X509Certificate) (sc[0].getCertificate()));
				// authenticationToken = new X509CertificateAuthenticationToken(principals,
				// credentials);
				authenticationToken = new AlwaysAllowLocalAuthenticationToken(
						new UsernamePrincipal(req.getRemoteAddr() + ":" + req.getRequestURI()));
			}
		} else {

			final Set<X509Certificate> credentials = new HashSet<X509Certificate>();
			// TODO: 在此增加根据验证证书和根据证书验证权限的代码
			credentials.add(certificates[0]);
			final AuthenticationSubject subject = new AuthenticationSubject(null, credentials);
			authenticationToken = authenticationSession.authenticate(subject);

		}
		return authenticationToken;
	}

	protected String getParam(HttpServletRequest req, JsonObject postParamJson, String paramName) {
		if (postParamJson == null)
			postParamJson = this.getPostParam(req);

		String pv = "";
		if (postParamJson.get(paramName) != null)
			pv = postParamJson.get(paramName).getAsString();
		else
			pv = req.getParameter(paramName);
		return pv;
	}

	private JsonObject getPostParam(HttpServletRequest req) {
		//
		JsonObject obj = new JsonObject();
		try {
			// byte[] buff = new byte[1024];
			// InputStream is = req.getInputStream();
			// is.read(buff);
			// InputStreamReader is
			StringBuffer buff = new StringBuffer();
			BufferedReader read = new BufferedReader(new InputStreamReader(req.getInputStream()));
			String str = null;
			while ((str = read.readLine()) != null) {
				buff.append(str);
			}
			String reqMsg = buff.toString().trim();
			logger.debug(">>>>" + reqMsg);
			// log call info
			try {
				// apiService.apiCallLog(req.getRemoteAddr(),req.getRequestURI(),reqMsg,"");
			} catch (Exception logExp) {
				logger.error("Logging API call info failed:" + logExp.getMessage());
			}
			obj = new JsonParser().parse(buff.toString().trim()).getAsJsonObject();
		} catch (Exception e) {
			logger.warn("无法获取POST参数。");
		}
		return obj;

	}

	protected String getMessage(HttpServletRequest request, String key) {
//		public static String getMessage(HttpServletRequest request, String key){
 	      Locale currentLocale = RequestContextUtils.getLocale(request);
//	        String lang = currentLocale.getLanguage();
//	        ResourceBundle bundle = ResourceBundle.getBundle("messages_"+lang, currentLocale);
 
		 ResourceBundle bundle = ResourceBundle.getBundle("messages_zh", currentLocale);
		 return bundle.getString(key);
	}
	protected String getAuthorization(HttpServletRequest req) {
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>register=");
		// String Authorization = req.getParameter("Authorization");
		// String authorization = req.getParameter("authorization");
		//
		// Principal principal = req.getUserPrincipal();
		// if(principal!=null) {
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>principal="+principal.toString());
		// }
		//
		// Enumeration<String> attrs = req.getAttributeNames();
		// while(attrs.hasMoreElements()) {
		// String name = attrs.nextElement();
		// Object pv = req.getAttribute(name) ;
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>name="+name+"; value="+pv);
		// }
		//
		// Enumeration<String> params = req.getParameterNames();
		// while(params.hasMoreElements()) {
		// String name = params.nextElement();
		// String[] pv = req.getParameterValues(name) ;
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>name="+name+"; value="+pv);
		// }
		// Enumeration<String> headers = req.getHeaderNames();
		// while(headers.hasMoreElements()) {
		// String headerName = headers.nextElement();
		// String header = req.getHeader(headerName);
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>headerName="+headerName+";
		// header="+header);
		// }
		String authorization = req.getHeader("authorization");
		return authorization;
	}

	protected CallLogData getCallLog(HttpServletRequest req) {
		String authorization = this.getAuthorization(req);
		String password = null, username = null;
		/**
		 * 参照Fabric-CA的写法
		 * 其中Authorization: Basic bHVvbGY6WUp5aE56UWFEa0xY是密码luolf:YJyhNzQaDkLX的Base64格式,即：
		 * bHVvbGY6WUp5aE56UWFEa0xY =》（base64 decode） luolf:YJyhNzQaDkLX
		 * */
		if (authorization!=null && authorization.startsWith("Basic ")) {
			String tmp = authorization.substring(6);
			tmp = new String(Base64.decodeBase64(tmp));
			if (tmp.indexOf(":") > 0) {
				String[] ids = tmp.split(":");
				username = ids[0];
				password = ids[1];
			}
		}
		JsonObject postParamJson = this.getPostParam(req);
		if(username==null) {
			 
			// String user = this.getParam(req, postParamJson, "id");
			username = this.getParam(req, postParamJson, "username");
			if(username==null)
				 username = this.getParam(req, postParamJson, "user");
			password = this.getParam(req, postParamJson, "password");
			if(password==null)
				password = this.getParam(req, postParamJson, "passwd");
			if(password==null)
				password = this.getParam(req, postParamJson, "secret");
			 
		}
		
		CallLogData callLog = new CallLogData();
		callLog.setReceived(postParamJson.toString());
		callLog.setAuthorization(this.getAuthorization(req));
		callLog.setCallTime(System.currentTimeMillis());
		callLog.setIp(req.getRemoteAddr());
		callLog.setUri(req.getRequestURI());
		callLog.setUsername(username);
		callLog.setPassword(password);
		return callLog;
	}

}
