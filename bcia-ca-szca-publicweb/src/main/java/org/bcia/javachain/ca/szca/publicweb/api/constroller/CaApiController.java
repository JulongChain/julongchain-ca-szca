/*
 * Copyright ? 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 * Copyright ? 2018  SZCA. All Rights Reserved.
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
 */

package org.bcia.javachain.ca.szca.publicweb.api.constroller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bcia.javachain.ca.szca.publicweb.api.service.BciaRequestInstance;
import org.bcia.javachain.ca.szca.publicweb.api.service.BciaRequestResult;
import org.bcia.javachain.ca.szca.publicweb.controller.EnrollCertForm;
import org.bcia.javachain.ca.szca.publicweb.entity.EndEntityApprovalData;
import org.bouncycastle.util.encoders.Base64;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.certificates.endentity.EndEntityInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
public class CaApiController extends BaseApiController {
	@Autowired
	BciaRequestInstance requestInstance;
	Logger logger = LoggerFactory.getLogger(getClass());
	@RequestMapping(value="/enrollOrUpdate",method = RequestMethod.POST)
	public void enrollOrUpdate(HttpServletRequest req,@RequestBody  String applyJson, HttpServletResponse res) {	
			logger.info("==API enrollOrUpdate");
			AuthenticationToken admin = this.getAuthenticationToken(req);
			JsonObject jsonObject = new JsonParser().parse(applyJson.trim()).getAsJsonObject();
			JsonObject resultObject  = apiService.checkEndEntityInfo(jsonObject);
			if(resultObject == null) {
				resultObject = new JsonObject();
				resultObject.addProperty("resultCode", BciaRequestResult.RESULT_CODE_SYSTEM_ERROR);
				resultObject.addProperty("errorMsg", "checkEndEntityInfo error!");
				writeResult(resultObject, res);
				return;
			}
			if(resultObject.get("resultCode") != null && !BciaRequestResult.RESULT_CODE_SUCCESS.equals(resultObject.get("resultCode"))) {
				writeResult(resultObject, res);
				return;
			}
			EndEntityApprovalData endEntityApprovalData = new EndEntityApprovalData();
			EnrollCertForm form = new EnrollCertForm();
			try {
				EndEntityInformation ei = apiService.createEndEntityInformation(jsonObject, form, endEntityApprovalData);
				if(jsonObject.get("reqType").getAsInt() == BciaRequestResult.REQ_TYPE_NEW)
					apiService.addEndEntityWithPassword(admin, endEntityApprovalData, ei);
				else if(jsonObject.get("reqType").getAsInt() == BciaRequestResult.REQ_TYPE_UPDATE)
					apiService.updateEndEntityWithPassword(admin, endEntityApprovalData, ei);
				else 
					throw new Exception("ReqType error!");
			}catch (Exception e) {
				e.printStackTrace();
				resultObject.addProperty("resultCode", BciaRequestResult.RESULT_CODE_USER_REGISTER_ERROR);
				resultObject.addProperty("errorMsg", e.getMessage());
				writeResult(resultObject, res);
				return;
			}
			try {
				BciaRequestResult result = requestInstance.doPost(req, form);
				if(!result.isSuccess()) {
					resultObject.addProperty("resultCode", BciaRequestResult.RESULT_CODE_CERT_ISSUE_ERROR);
					resultObject.addProperty("errorMsg", result.getMessage());
					writeResult(resultObject, res);
					return;
				}else {
					resultObject.addProperty("resultCode", BciaRequestResult.RESULT_CODE_SUCCESS);
					resultObject.addProperty("cert", new String(Base64.encode(result.getResultData())));
					writeResult(resultObject, res);
					return;
				}
			}catch (Exception e) {
				e.printStackTrace();
				resultObject.addProperty("resultCode", BciaRequestResult.RESULT_CODE_CERT_ISSUE_ERROR);
				resultObject.addProperty("errorMsg", e.getMessage());
				writeResult(resultObject, res);
				return;
			}
	}
	@RequestMapping(value="/revoke",method = RequestMethod.POST)
	public void revoke(HttpServletRequest req,@RequestBody  String revokeJson, HttpServletResponse res) {	
		logger.info("==API revoke");
		AuthenticationToken admin = this.getAuthenticationToken(req);
		JsonObject jsonObject = new JsonParser().parse(revokeJson.trim()).getAsJsonObject();
		JsonObject resultObject  = new JsonObject();
		if(jsonObject == null) {
			resultObject.addProperty("resultCode", BciaRequestResult.RESULT_CODE_REVOKE_PARAM_ERROR);
			resultObject.addProperty("errorMsg", "Request params [null] error!");
			writeResult(resultObject, res);
			return;
		}
		if(jsonObject.get("reqType") == null || jsonObject.get("reqType").getAsInt() == 0) {
			resultObject.addProperty("resultCode", BciaRequestResult.RESULT_CODE_REVOKE_PARAM_ERROR);
			resultObject.addProperty("errorMsg", "Request params [reqType] error!");
			writeResult(resultObject, res);
			return;
		}
		int reqType = jsonObject.get("reqType").getAsInt();
		BciaRequestResult result = null;
		if(BciaRequestResult.REQ_TYPE_REVOKE_CERT == reqType) {
			result = apiService.revokeCert(admin, jsonObject);
		}else if(BciaRequestResult.REQ_TYPE_REVOKE_USER == reqType) {
			result = apiService.revokeUser(admin, jsonObject);
		}else {
			resultObject.addProperty("resultCode", BciaRequestResult.RESULT_CODE_REVOKE_PARAM_ERROR);
			resultObject.addProperty("errorMsg", "Request params [reqType] error!");
			writeResult(resultObject, res);
			return;
		}
		if(result == null) {
			resultObject.addProperty("resultCode", BciaRequestResult.RESULT_CODE_REVOKE_FAILURE);
		}else if(!result.isSuccess()) {
			resultObject.addProperty("resultCode", result.getErrorCode());
			resultObject.addProperty("errorMsg", result.getMessage());
		}else {
			resultObject.addProperty("resultCode", BciaRequestResult.RESULT_CODE_SUCCESS);
		}
		writeResult(resultObject, res);
	}
	private void writeResult(JsonObject resultObject, HttpServletResponse res) {
		try {
			res.setContentLength(resultObject.toString().getBytes().length);
			res.getOutputStream().write(resultObject.toString().getBytes());
			res.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
