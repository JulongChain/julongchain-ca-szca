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
import java.io.InputStreamReader;
import java.security.Principal;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bcia.javachain.ca.szca.publicweb.api.ApiCaInfo;
import org.bcia.javachain.ca.szca.publicweb.api.ApiError;
import org.bcia.javachain.ca.szca.publicweb.api.ApiMessage;
import org.bcia.javachain.ca.szca.publicweb.api.ApiResult;
import org.bcia.javachain.ca.szca.publicweb.api.CallLogData;
import org.bcia.javachain.ca.szca.publicweb.api.service.ISzcaApiService;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.certificates.ca.CAData;
import org.cesecore.certificates.endentity.EndEntityInformation;
import org.cesecore.certificates.endentity.EndEntityType;
import org.cesecore.certificates.endentity.EndEntityTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
public class RegisterController extends BaseApiController {
	// Logger log = LoggerFactory.getLogger(this.getClass());
	// @Autowired
	// ISzcaApiService apiService;

	@RequestMapping("/register")
	public void register(HttpServletRequest req, HttpServletResponse res) {
		CallLogData callLog = this.getCallLog(req);
		// Client发送:
		// {"id":"admin2","type":"peer","max_enrollments":-1,"affiliation":"bcia.szca","attrs":[{"name":"","value":""}]}
		// Server返回:
		// {"success":true,"result":{"secret":"mbtPBMDSIlFd"},"errors":[],"messages":[]}
		// System.out.println(">>>>>>>>>>.Authorization="+Authorization+";authorization>>>>>>>>"+authorization);
		String caName, format, type, affiliation, userId, max_enrollments;
		JsonElement attrs;
		JsonObject json =callLog.receivedAsJson();// this.getPostParam(req);
		if (json.get("caname") != null)
			caName = json.get("caname").getAsString();
		else
			caName = req.getParameter("caname");

		if (json.get("format") != null)
			format = json.get("format").getAsString();
		else
			format = req.getParameter("format");

		if (caName == null || "".equals(caName.trim()))
			caName = apiService.getDefaultCaName();

		ApiResult apiResult = new ApiResult();
		ApiCaInfo apiCaInfo = new ApiCaInfo();
		apiCaInfo.setCaname(caName);
		ApiMessage apiMessage = new ApiMessage();
		ApiError apiError = new ApiError();
		boolean hasCallRight = this.checkApiCallRight(callLog);

		if (hasCallRight) {
			try {
				//
				userId = json.get("id").getAsString();
				type = json.get("type").getAsString();
				affiliation = json.get("affiliation").getAsString();
				attrs = json.get("attrs");
				max_enrollments = json.get("max_enrollments").getAsString();

				// ISzcaApiService apiService = this.getBean(req, ISzcaApiService.class);
				// apiCaInfo.setCachain(apiService.getCertificateChain4Base64(caName, format));
				// String passwd = apiService.randomPwd(12);
				CAData caData = apiService.getCaByName(caName);
				AuthenticationToken admin = this.getAuthenticationToken(req);
				EndEntityInformation entity = new EndEntityInformation();
				entity.setEndEntityProfileId(1);
				entity.setCertificateProfileId(1);
				entity.setAdministrator(false);
				entity.setDN("CN=" + userId + ", O=BCIA, OU=" + affiliation);
				// entity.setPassword(passwd);
				entity.setCAId(caData.getCaId());
				entity.setUsername(userId);
				entity.setTokenType(2);
				entity.setType(new EndEntityType(EndEntityTypes.ENDUSER));
				String passwd = apiService.addEndEntity(admin, entity);
				apiResult.setSuccess(true);
				apiResult.setSecret(passwd);
			} catch (Exception e) {
				e.printStackTrace();
				apiResult.setSuccess(false);
				apiError.setCode(-1);
				apiError.setMessage(e.getMessage());
			}
		} else {
			apiResult.setSuccess(false);
			apiMessage.setMessage(String.format("此终端[%s]没有访问此接口[%s]的权限", callLog.getIp(), callLog.getUri()));
		}
		apiResult.setResult(apiCaInfo.toJsonObject());

		// 参考Fabric-CA，成功时没有Errors与Messages
		if (!apiResult.isSuccess()) {
			apiResult.addMessage(apiMessage.toJsonObject());
			apiResult.addError(apiError.toJsonObject());
		}
		try {

			callLog.setReceived(json.toString());
			callLog.setResult(apiResult.toString());
			apiService.apiCallLog(callLog);// req.getRemoteAddr(), req.getRequestURI(),
											// json.toString(),this.getAuthorization(req),apiResult.toString());
		} catch (Exception logExp) {
			logger.error("Logging call message failed:" + logExp.getMessage());
		}

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

	// private JsonObject getPostParam(HttpServletRequest req) {}
}
