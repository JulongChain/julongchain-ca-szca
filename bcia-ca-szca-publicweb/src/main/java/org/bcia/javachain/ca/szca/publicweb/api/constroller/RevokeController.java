package org.bcia.javachain.ca.szca.publicweb.api.constroller;

import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
public class RevokeController extends BaseApiController {

	// @Autowired
	// CertificateStoreSession certificateStoreSessionBean;

	@RequestMapping("/revoke")
	public void enroll(HttpServletRequest req, HttpServletResponse res) {
		CallLogData callLog = getCallLog(req);
		//{"id":"admin","serial":"620231177374469240240067313696623580623426853450","aki":"5779569e8a81833814310e9136fcb74cdbb26317","reason":"1"}
		JsonObject postParamJson =callLog.receivedAsJson();// this.getPostParam(req);

		String caName = this.getParam(req, postParamJson, "caname");
		String serial = this.getParam(req, postParamJson, "serial");
		String reason = this.getParam(req, postParamJson, "reason");
		String name = this.getParam(req, postParamJson, "id");
		String aki = this.getParam(req, postParamJson, "aki");

		if (caName == null || "".equals(caName.trim()))
			caName = apiService.getDefaultCaName();
		int revokeReason = 0;
		try {
			revokeReason = Integer.parseInt(reason);
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
				List<String> snList = apiService.revoke(authenticationToken, caName, serial, revokeReason, name, aki);
				apiResult.setSuccess(true);
				apiMessage.setMessage(snList.toString());
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
		if(!apiResult.isSuccess()) {
			apiResult.addMessage(apiMessage.toJsonObject());
			apiResult.addError(apiError.toJsonObject());
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

}
