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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bcia.javachain.ca.szca.publicweb.api.ApiCaInfo;
import org.bcia.javachain.ca.szca.publicweb.api.ApiError;
import org.bcia.javachain.ca.szca.publicweb.api.ApiMessage;
import org.bcia.javachain.ca.szca.publicweb.api.ApiResult;
import org.bcia.javachain.ca.szca.publicweb.api.CallLogData;
import org.bcia.javachain.ca.szca.publicweb.api.service.ISzcaApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
public class CaInfoController extends BaseApiController{
	Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	ISzcaApiService apiService;

	@RequestMapping("/helloApi")
	public void helloApi(HttpServletRequest req, HttpServletResponse res) {
		try {
			res.getWriter().print("Hello from BCIA");
		} catch (Exception e) {

		}
	}

	@RequestMapping("/getcacert")
	public void getcacert(HttpServletRequest req, HttpServletResponse res) {
		this.cainfo(req, res);
	}
	@RequestMapping("/getCaCert")
	public void getCaCert(HttpServletRequest req, HttpServletResponse res) {
		this.cainfo(req, res);
	}
	@RequestMapping("/caInfo")
	public void caInfo(HttpServletRequest req, HttpServletResponse res) {
		this.cainfo(req, res);
	}
	@RequestMapping("/cainfo")
	public void cainfo(HttpServletRequest req, HttpServletResponse res) {
	 
		if(false&& !"POST".equalsIgnoreCase(req.getMethod())) {
		//{"success":false,"result":null,"errors":[{"code":405,"message":"Method is not allowed:\"GET\""}],"messages":[]}
			JsonObject obj = new JsonParser().parse("{\"success\":false,\"result\":null,\"errors\":[{\"code\":405,\"message\":\"Method is not allowed:\\\""+req.getMethod()+"\"\"}],\"messages\":[]}").getAsJsonObject();
			try {
				res.getOutputStream().write(obj.toString().getBytes());
				log.debug("Method is not allowed: " + req.getMethod());
			} catch (Exception e) {
				log.error("响应cainfo接口失败:"+e.getMessage());
			}
		}
		CallLogData callLog = this.getCallLog(req);
		//成功信息，类似：
		//{"success":true,"result":{"CAName":"","CAChain":"LS0tLS1......EUtLS0tLQo="},"errors":[],"messages":[]}
		String caName, format;
		JsonObject json = callLog.receivedAsJson();//  this.getPostParam(req);
		if (json.get("caname") != null)
			caName = json.get("caname").getAsString();
		else
			caName = req.getParameter("caname");

		if (json.get("format") != null)
			format = json.get("format").getAsString();
		else
			format = req.getParameter("format");

		if(caName==null || "".equals(caName.trim()))
			caName  = apiService.getDefaultCaName();
		
		
		
		ApiResult apiResult = new ApiResult();
		ApiCaInfo apiCaInfo = new ApiCaInfo();
		apiCaInfo.setCaname(caName);
		ApiMessage apiMessage = new ApiMessage();
		ApiError apiError = new ApiError();
		try {
			// ISzcaApiService apiService = this.getBean(req, ISzcaApiService.class);	
			
			apiCaInfo.setCachain(apiService.getCertificateChain4Base64(caName, format));
			//apiCaInfo.setCachain("LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUNIakNDQWNXZ0F3SUJBZ0lVSThjMExaSXl5MitUa20rUTE3SUlEbjJvWENvd0NnWUlLb1pJemowRUF3SXcKYkRFTE1Ba0dBMVVFQmhNQ1EwNHhFakFRQmdOVkJBZ1RDVWQxWVc1blpHOXVaekVSTUE4R0ExVUVCeE1JVTJobApibnBvWlc0eERUQUxCZ05WQkFvVEJFSkRTVUV4RURBT0JnTlZCQXNUQjBKRFNVRWdRMEV4RlRBVEJnTlZCQU1UCkRFSkRTVUVnVWs5UFZDQkRRVEFlRncweE9EQXpNVE13TkRJNE1EQmFGdzB6TXpBek1Ea3dOREk0TURCYU1Hd3gKQ3pBSkJnTlZCQVlUQWtOT01SSXdFQVlEVlFRSUV3bEhkV0Z1WjJSdmJtY3hFVEFQQmdOVkJBY1RDRk5vWlc1NgphR1Z1TVEwd0N3WURWUVFLRXdSQ1EwbEJNUkF3RGdZRFZRUUxFd2RDUTBsQklFTkJNUlV3RXdZRFZRUURFd3hDClEwbEJJRkpQVDFRZ1EwRXdXVEFUQmdjcWhrak9QUUlCQmdncWhrak9QUU1CQndOQ0FBUWNKWG9UL21xYWJQaVoKTGdubFo1VFB4anlQeHpUaVBZZzBock1acnY0NW5Sd29CQ290UmtXK2drZ3ZHWnNEYi9IbGc5ekhNMDNyaTk5TQpLbnBZSExXWm8wVXdRekFPQmdOVkhROEJBZjhFQkFNQ0FRWXdFZ1lEVlIwVEFRSC9CQWd3QmdFQi93SUJBVEFkCkJnTlZIUTRFRmdRVXB3ZGJONWVhTHFvSTVya1JLK1VEV3ZhN1Bja3dDZ1lJS29aSXpqMEVBd0lEUndBd1JBSWcKYkhwYkpVR0NWenhWWWkvUzZWbWE4aVRIeWZpNkF4SFRhY2xIN0hONkcwY0NJRUlMenlNU2JjU09OMzFia3ZDWQpPVEtlZzdEeWxrVGRIRS8zME5VZmtuSGsKLS0tLS1FTkQgQ0VSVElGSUNBVEUtLS0tLQo=");
			apiResult.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			apiResult.setSuccess(false);
			apiError.setCode(-1);
			apiError.setMessage(e.getMessage());
		}
		apiResult.setResult(apiCaInfo.toJsonObject());
		
		//参考Fabric-CA，成功时没有Errors与Messages
		if(!apiResult.isSuccess()) {
			apiResult.addMessage(apiMessage.toJsonObject());
			apiResult.addError(apiError.toJsonObject());
		}
		
		try {
			 
			callLog.setResult(json.toString());
			callLog.setResult(apiResult.toString());
			apiService.apiCallLog(callLog);//req.getRemoteAddr(), req.getRequestURI(), json.toString(),this.getAuthorization(req),apiResult.toString());
		}catch(Exception logExp) {
			logger.error("Logging call message failed:"+logExp.getMessage());
		}
		
		
		byte[] ret = apiResult.toString().getBytes();
		res.setCharacterEncoding("utf-8");
		res.setContentType("application/json");
		res.setContentLength(ret.length);
		try {
			res.getOutputStream().write(ret);
			log.debug("Sent CA certificate chain to client, len=" + ret.length + ".");
		} catch (Exception e) {
			log.error("响应cainfo接口失败:"+e.getMessage());
		}
	}

//	private JsonObject getPostParam(HttpServletRequest req) {
//		JsonObject obj = new JsonObject();
//		try {
//			// byte[] buff = new byte[1024];
//			// InputStream is = req.getInputStream();
//			// is.read(buff);
//			// InputStreamReader is
//			StringBuffer buff = new StringBuffer();
//			BufferedReader read = new BufferedReader(new InputStreamReader(req.getInputStream()));
//			String str = null;
//			while ((str = read.readLine()) != null) {
//				buff.append(str);
//			}
//			obj = new JsonParser().parse(buff.toString().trim()).getAsJsonObject();
//		} catch (Exception e) {
//			log.warn("无法获取POST参数。");
//		}
//		return obj;
//
//	}
	
}
