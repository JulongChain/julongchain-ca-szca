package org.bcia.javachain.ca.szca.publicweb.api.constroller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.Certificate;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.bcia.javachain.ca.szca.publicweb.api.ApiCaInfo;
import org.bcia.javachain.ca.szca.publicweb.api.ApiError;
import org.bcia.javachain.ca.szca.publicweb.api.ApiMessage;
import org.bcia.javachain.ca.szca.publicweb.api.ApiResult;
import org.bcia.javachain.ca.szca.publicweb.api.CallLogData;
import org.bcia.javachain.ca.szca.publicweb.api.service.BciaRequestInstance;
import org.bcia.javachain.ca.szca.publicweb.api.service.BciaRequestResult;
import org.bcia.javachain.ca.szca.publicweb.api.service.ISzcaApiService;
import org.bcia.javachain.ca.szca.publicweb.controller.EnrollCertForm;
import org.cesecore.certificates.ca.CAData;
import org.cesecore.util.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
public class EnrollController extends BaseApiController {

	@Autowired
	BciaRequestInstance requestInstance;

 
	@RequestMapping(value="/reenroll",produces="text/html;charset=UTF-8")
	public void enRoll(HttpServletRequest req, HttpServletResponse res) {
		this.enroll(req, res);
	}

	// @RequestMapping("/downloadEntityCert")
	// public void downloadUserCert(HttpServletRequest req, HttpServletResponse res)
	// throws Exception{
	// EnrollCertForm form = new EnrollCertForm();
	//
	// //API
	//
	// form.setTokenType(1);
	// BciaRequestResult result = requestInstance.doPost(req, form);
	// }
	@RequestMapping(value="/enroll",produces="text/html;charset=UTF-8")
	public void enroll(HttpServletRequest req, HttpServletResponse res) {
		CallLogData callLog = getCallLog(req);
		/**
		 * //从request的header中得到密码 == 密码的base64 //Authorization: Basic
		 * bHVvbGY6WUp5aE56UWFEa0xY == 密码的base64 //从POST中获得参数：
		 * //{"hosts":["bc"],"certificate_request":"-----BEGIN CERTIFICATE
		 * REQUEST-----\nMIIBODCB3wIBADBdMQswCQYDVQQGEwJVUzEXMBUGA1UECBMOTm9ydGggQ2Fyb2xp\nbmExFDASBgNVBAoTC0h5cGVybGVkZ2VyMQ8wDQYDVQQLEwZGYWJyaWMxDjAMBgNV\nBAMTBWx1b2xmMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEHKJF7dM4qyV1/eMI\nTNMLhlJgCfZKFC9+qjWw3h/0EhNzYabkTh83sHm5D3UQOnke0jJf1qUSm4KacHaw\nF1VeGaAgMB4GCSqGSIb3DQEJDjERMA8wDQYDVR0RBAYwBIICYmMwCgYIKoZIzj0E\nAwIDSAAwRQIhAIOMVBkN7Um8a2RTamissy932w7Y/LJqmV4pwMsxk7hgAiBPG3yK\nkB4LcruSrZ7PDKsWHbLZIa5QSbIw+yiahJPD5Q==\n-----END
		 * CERTIFICATE
		 * REQUEST-----\n","profile":"","crl_override":"","label":"","CAName":""}
		 * //{"hosts":["bc"], "certificate_request":"-----BEGIN CERTIFICATE
		 * REQUEST-----\nMIIBODCB3wIBADBdMQswCQYDVQQGEwJVUzEXMBUGA1UECBMOTm9ydGggQ2Fyb2xp\n
		 * bmExFDASBgNVBAoTC0h5cGVybGVkZ2VyMQ8wDQYDVQQLEwZGYWJyaWMxDjAMBgNV\nBAMTBWx1b2xmMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEHKJF7dM4qyV1/eMI\n
		 * TNMLhlJgCfZKFC9+qjWw3h/0EhNzYabkTh83sHm5D3UQOnke0jJf1qUSm4KacHaw\nF1VeGaAgMB4GCSqGSIb3DQEJDjERMA8wDQYDVR0RBAYwBIICYmMwCgYIKoZIzj0E\n
		 * AwIDSAAwRQIhAIOMVBkN7Um8a2RTamissy932w7Y/LJqmV4pwMsxk7hgAiBPG3yK\nkB4LcruSrZ7PDKsWHbLZIa5QSbIw+yiahJPD5Q==\n
		 * -----END CERTIFICATE REQUEST-----\n",
		 * "profile":"","crl_override":"","label":"","CAName":""}
		 */

		/**
		 * 回复内容与格式：
		 * {"success":true,"result":{"Cert":"LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUNPakNDQWVHZ0F3SUJBZ0lVVEc4UFFZOFFHQW5OUmcyVURMMStsdCtNMUxRd0NnWUlLb1pJemowRUF3SXcKYkRFTE1Ba0dBMVVFQmhNQ1EwNHhFakFRQmdOVkJBZ1RDVWQxWVc1blpHOXVaekVSTUE4R0ExVUVCeE1JVTJobApibnBvWlc0eERUQUxCZ05WQkFvVEJFSkRTVUV4RURBT0JnTlZCQXNUQjBKRFNVRWdRMEV4RlRBVEJnTlZCQU1UCkRFSkRTVUVnVWs5UFZDQkRRVEFlRncweE9EQTFNRGd3TlRRNU1EQmFGdzB4T1RBMU1EZ3dOVFE1TURCYU1GNHgKQ3pBSkJnTlZCQVlUQWxWVE1SY3dGUVlEVlFRSUV3NU9iM0owYUNCRFlYSnZiR2x1WVRFVU1CSUdBMVVFQ2hNTApTSGx3WlhKc1pXUm5aWEl4RHpBTkJnTlZCQXNUQmtaaFluSnBZekVQTUEwR0ExVUVBeE1HYkhWdmJHWTFNRmt3CkV3WUhLb1pJemowQ0FRWUlLb1pJemowREFRY0RRZ0FFR3c4akdIY0dKdFJjSlo2MEJrQ28yVmJuMVpLOGFGa0UKUElPN2FwNTdaODdYVHFwTFNTT2I3SFhkY1BvYWJsYnVlSUx4bmp0QXhMVjQ0WkVzU2JRNVk2TnZNRzB3RGdZRApWUjBQQVFIL0JBUURBZ2VBTUF3R0ExVWRFd0VCL3dRQ01BQXdIUVlEVlIwT0JCWUVGQkg3VWRVWk56RFpUK2VuCjlxVGVHc2RGRHBlRU1COEdBMVVkSXdRWU1CYUFGQ2JkVURqM1hsb3dsM2w4Ym5tMzJKWnc1NCtsTUEwR0ExVWQKRVFRR01BU0NBbUpqTUFvR0NDcUdTTTQ5QkFNQ0EwY0FNRVFDSUgvR1FFckZTbk1ybVR4Z3U4N3QxenpydFErZQptWkVaVHFKcC9QR05zUlYwQWlCaHJJbjJJRUp1MHpWUzhINytydFRwOStuRW5pSlNKbHE0NEliYzZxNVI0Zz09Ci0tLS0tRU5EIENFUlRJRklDQVRFLS0tLS0K","ServerInfo":{"CAName":"","CAChain":"LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUNIekNDQWNXZ0F3SUJBZ0lVV2NoUkZVZGtsdTFPalBsbkZUc2hIc0xSNGpnd0NnWUlLb1pJemowRUF3SXcKYkRFTE1Ba0dBMVVFQmhNQ1EwNHhFakFRQmdOVkJBZ1RDVWQxWVc1blpHOXVaekVSTUE4R0ExVUVCeE1JVTJobApibnBvWlc0eERUQUxCZ05WQkFvVEJFSkRTVUV4RURBT0JnTlZCQXNUQjBKRFNVRWdRMEV4RlRBVEJnTlZCQU1UCkRFSkRTVUVnVWs5UFZDQkRRVEFlRncweE9EQTFNRGd3TXpVeU1EQmFGdzB6TXpBMU1EUXdNelV5TURCYU1Hd3gKQ3pBSkJnTlZCQVlUQWtOT01SSXdFQVlEVlFRSUV3bEhkV0Z1WjJSdmJtY3hFVEFQQmdOVkJBY1RDRk5vWlc1NgphR1Z1TVEwd0N3WURWUVFLRXdSQ1EwbEJNUkF3RGdZRFZRUUxFd2RDUTBsQklFTkJNUlV3RXdZRFZRUURFd3hDClEwbEJJRkpQVDFRZ1EwRXdXVEFUQmdjcWhrak9QUUlCQmdncWhrak9QUU1CQndOQ0FBUWdmR2luV0JNNmpySDMKcUpnVDFNQXkvUy9ieElWSGk4RTZOSy9Tc2ZUdE1OWlVybGhUMVRSNVh6bFhCdjJRSUlRczdoR2NvMkdoVXhNLwpXdVVQbjhZN28wVXdRekFPQmdOVkhROEJBZjhFQkFNQ0FRWXdFZ1lEVlIwVEFRSC9CQWd3QmdFQi93SUJBVEFkCkJnTlZIUTRFRmdRVUp0MVFPUGRlV2pDWGVYeHVlYmZZbG5Ebmo2VXdDZ1lJS29aSXpqMEVBd0lEU0FBd1JRSWgKQUxkTWRQU3FpTjVPdzBQYVZvTmtFbnFIcEJuZ1pLNHVuUEZPbmRnblJVdjlBaUJpNWdoQ1AxbXBLdEt6QjRzaApmR1M1a3RRUmthdGdJMnNHQXkvM2dWWERPdz09Ci0tLS0tRU5EIENFUlRJRklDQVRFLS0tLS0K"}},"errors":[],"messages":[]}
		 *
		 * {"success":true,"result":{"Cert":"LS0t。。。。。。S0K","ServerInfo":{"CAName":"","CAChain":"LS0tL。。。。。。LS0K"}},"errors":[],"messages":[]}
		 * 
		 */
		// String caName="";
		JsonObject postParamJson =callLog.receivedAsJson();// this.getPostParam(req);
		// String user = this.getParam(req, postParamJson, "id");
		String certificate_request = this.getParam(req, postParamJson, "certificate_request");
		String hosts = this.getParam(req, postParamJson, "hosts");
		String profile = this.getParam(req, postParamJson, "profile");
		String crl_override = this.getParam(req, postParamJson, "crl_override");
		String label = this.getParam(req, postParamJson, "label");
		// String caName = this.getParam(req, postParamJson, "CAName");
		String passwd = callLog.getPassword();
		String user = callLog.getUsername();

		EnrollCertForm form = new EnrollCertForm();
		// Certificate=1,PKCS12=2,JKS=3
		form.setTokenType(1);
		form.setUsername(user);
		form.setPassword(passwd);
		form.setCsr(certificate_request);

		ApiResult apiResult = new ApiResult();
		ApiCaInfo apiCaInfo = new ApiCaInfo();

		ApiMessage apiMessage = new ApiMessage();
		ApiError apiError = new ApiError();

		boolean hasCallRight = this.checkApiCallRight(callLog);

		if (hasCallRight) {
			try {
				CAData caData = apiService.getCaByUser(user);
				BciaRequestResult result = requestInstance.doPost(req, form);
				// String cachain = apiService.getCertificateChain4Base64(caName, null);
				apiResult.setSuccess(result.isSuccess());
				apiMessage.setMessage(result.getMessage());
				apiCaInfo.setCert(new String(result.getResultData()));

				apiCaInfo.setCaname(caData.getName());

				// Collection<java.security.cert.Certificate> chain =
				// caData.getCA().getCertificateChain();
				java.security.cert.Certificate caCert = caData.getCA().getCACertificate();// CertificateChain();
				// apiCaInfo.setCachain(new String(Base64.decodeBase64(caCert.getEncoded())));
				// Fabric-CA-Client 在Fabric-CA中，要encode两次
				apiCaInfo.setCachain(new String(Base64.encodeBase64(caCert.getEncoded())));

				// ServerInfo for Fabric-CA-Client,在Fabric-CA中，要encode两次
				// "ServerInfo":{"CAName":"","CAChain":"LS0tL。。。。。。LS0K"}
				JsonObject serverInfo = new JsonObject();
				serverInfo.addProperty("CAName", caData.getName());
				serverInfo.addProperty("CAChain", new String(Base64.encodeBase64(caCert.getEncoded())));
				apiCaInfo.setServerInfo(serverInfo);
			} catch (Exception e) {
				e.printStackTrace();
				apiResult.setSuccess(false);
				apiError.setCode(-1);
				apiError.setMessage(e.getMessage());
			}
		} else {
			//此终端[%s]没有访问此接口[%s]的权限", callLog.getIp(), callLog.getUri();			 
			String message = String.format(this.getMessage(req, "api.message.noright"), callLog.getIp(), callLog.getUri());
			
			apiError.setCode(-1);
			apiError.setMessage(message);
			apiResult.setSuccess(false);
			apiMessage.setMessage(message);
		}

		apiResult.setResult(apiCaInfo.toJsonObject());

		if (!apiResult.isSuccess()) {
			apiResult.addMessage(apiMessage.toJsonObject());
			apiResult.addError(apiError.toJsonObject());
		}
		System.out.println("===ENROLL====" + apiResult.toString());

		try {
			byte[] ret = apiResult.toString().getBytes();
//			res.setCharacterEncoding("utf-8");
//			res.setContentType("application/json");

			res.setContentLength(ret.length);
			res.getOutputStream().write(ret);
			res.flushBuffer();
		} catch (Exception e) {

		}
		//
		// if(apiResult.isSuccess()) {
		//
		// byte[] ret = apiResult.toString().getBytes();
		// }
	}

}
