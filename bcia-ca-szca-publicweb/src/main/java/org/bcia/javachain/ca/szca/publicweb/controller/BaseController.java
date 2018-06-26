/*
 *
 * Copyright © 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 * Copyright © 2018  SZCA. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.bcia.javachain.ca.szca.publicweb.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.security.auth.x500.X500Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bcia.javachain.ca.szca.publicweb.api.ApiCaInfo;
import org.bcia.javachain.ca.szca.publicweb.api.ApiError;
import org.bcia.javachain.ca.szca.publicweb.api.ApiMessage;
import org.bcia.javachain.ca.szca.publicweb.api.ApiResult;
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


public class BaseController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	ISzcaApiService apiService;
  
	@Autowired
	CertificateStoreSession certificateStoreSessionBean;

	
	@Autowired
	WebAuthenticationProviderSessionLocal authenticationSession;
	
	protected AuthenticationToken getAuthenticationToken(HttpServletRequest req)	{
	AuthenticationToken authenticationToken = null;

	final X509Certificate[] certificates = (X509Certificate[]) req
			.getAttribute("javax.servlet.request.X509Certificate");
	if (certificates == null || certificates.length == 0) {
		logger.error("Client certificate required.");
		// throw new Exception("无法获得客户端证，请指定客户端登录证书。");
//		// AuthenticationToken admin =new AuthenticationToken();
//		// 暂时构造一个
//		Collection<CertificateWrapper> superCerts = certificateStoreSessionBean
//				.findCertificatesByUsername("defaultpublic");
//		CertificateWrapper[] sc = (CertificateWrapper[]) superCerts.toArray(new CertificateWrapper[0]);
//		// AlwaysAllowLocalAuthenticationToken admin = new
//		// AlwaysAllowLocalAuthenticationToken();
//		final Set<X500Principal> principals = new HashSet<X500Principal>();
//		principals.add(((X509Certificate) (sc[0].getCertificate())).getSubjectX500Principal());
//		final Set<X509Certificate> credentials = new HashSet<X509Certificate>();
//		credentials.add((X509Certificate) (sc[0].getCertificate()));
//		authenticationToken = new X509CertificateAuthenticationToken(principals, credentials);
		
		authenticationToken = new AlwaysAllowLocalAuthenticationToken(
				new UsernamePrincipal(req.getRemoteAddr() + ":" + req.getRequestURI()));
		
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
	
	protected JsonObject getPostParam(HttpServletRequest req) {
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
			obj = new JsonParser().parse(buff.toString().trim()).getAsJsonObject();
		} catch (Exception e) {
			logger.warn("无法获取POST参数。");
		}
		return obj;

	}
	
	protected String getMessage(HttpServletRequest request, String key) { 
 	     Locale currentLocale = RequestContextUtils.getLocale(request); 
		 ResourceBundle bundle = ResourceBundle.getBundle("messages_zh", currentLocale);
		 return bundle.getString(key);
	}
}
