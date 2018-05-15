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

import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.security.auth.x500.X500Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bcia.javachain.ca.szca.publicweb.api.ApiCaInfo;
import org.bcia.javachain.ca.szca.publicweb.api.ApiError;
import org.bcia.javachain.ca.szca.publicweb.api.ApiMessage;
import org.bcia.javachain.ca.szca.publicweb.api.ApiResult;
import org.bcia.javachain.ca.szca.publicweb.api.CallLogData;
import org.bcia.javachain.ca.szca.publicweb.api.service.ISzcaApiService;
import org.cesecore.authentication.tokens.AuthenticationSubject;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authentication.tokens.X509CertificateAuthenticationToken;
import org.cesecore.certificates.certificate.CertificateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonObject;

import cn.net.bcia.bcca.core.ejb.authentication.web.WebAuthenticationProviderSessionLocal;
import cn.net.bcia.cesecore.certificates.certificate.CertificateStoreSession;

@Controller
public class TCertController extends BaseApiController {

	// @Autowired
	// CertificateStoreSession certificateStoreSessionBean;

	@RequestMapping("/tcert")
	public void enroll(HttpServletRequest req, HttpServletResponse res) {
		CallLogData callLog = getCallLog(req);
		JsonObject postParamJson = callLog.receivedAsJson();//this.getPostParam(req);

		String caName = this.getParam(req, postParamJson, "caname");
		String count = this.getParam(req, postParamJson, "count");
		String attr_names = this.getParam(req, postParamJson, "attr_names");
		String validity_period = this.getParam(req, postParamJson, "validity_period");
		String encrypt_attrs = this.getParam(req, postParamJson, "encrypt_attrs");

		if (caName == null || "".equals(caName.trim()))
			caName = apiService.getDefaultCaName();
		boolean isEncrypt   = false;
		try {
			isEncrypt = Boolean.parseBoolean(encrypt_attrs);
		} catch (Exception e) {

		}
		
		int certCount = 0;
		try {
			certCount = Integer.parseInt(count);
		} catch (Exception e) {

		}
		int validityPeriod = 0;
		try {
			validityPeriod = Integer.parseInt(validity_period);
		} catch (Exception e) {

		}
		 
		ApiResult apiResult = new ApiResult();
		ApiCaInfo apiCaInfo = new ApiCaInfo();
		apiCaInfo.setCaname(caName);
		ApiMessage apiMessage = new ApiMessage();
		ApiError apiError = new ApiError();
		boolean hasCallRight = this.checkApiCallRight(callLog);
		if (hasCallRight) {
			try {
				AuthenticationToken authenticationToken = this.getAuthenticationToken(req);
				// ISzcaApiService apiService = this.getBean(req, ISzcaApiService.class);
				apiService.findTCert(authenticationToken, caName, certCount, validityPeriod, isEncrypt, attr_names);
				apiResult.setSuccess(true);

			} catch (Exception e) {
				e.printStackTrace();
				apiResult.setSuccess(false);
				apiError.setCode(-1);
				apiError.setMessage(e.getMessage());
			}
		} else {

		}
		apiResult.setResult(apiCaInfo.toJsonObject());
		apiResult.addMessage(apiMessage.toJsonObject());
		apiResult.addError(apiError.toJsonObject());

		byte[] ret = apiResult.toString().getBytes();
		res.setCharacterEncoding("utf-8");
		res.setContentType("application/json");
		res.setContentLength(ret.length);
		try {
			res.getOutputStream().write(ret);
			logger.debug("Sent CA certificate chain to client, len=" + ret.length + ".");
		} catch (Exception e) {
			logger.error("响应cainfo接口失败:" + e.getMessage());
		}
	}

}
