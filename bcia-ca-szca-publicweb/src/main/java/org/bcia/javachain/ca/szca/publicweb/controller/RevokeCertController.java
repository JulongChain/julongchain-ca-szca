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

import java.io.ByteArrayOutputStream;
import java.security.KeyStore;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bcia.javachain.ca.szca.publicweb.api.ApiAdminUserData;
import org.bcia.javachain.ca.szca.publicweb.api.service.BciaRequestInstance;
import org.bcia.javachain.ca.szca.publicweb.api.service.BciaRequestResult;
import org.bcia.javachain.ca.szca.publicweb.api.service.ISzcaApiService;
import org.bcia.javachain.ca.szca.publicweb.service.InspectService;
import org.cesecore.authentication.tokens.AlwaysAllowLocalAuthenticationToken;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authentication.tokens.UsernamePrincipal;
import org.cesecore.certificates.ca.CAData;
import org.cesecore.keys.util.KeyTools;
import org.cesecore.util.StringTools;
import org.ejbca.core.model.SecConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.szca.common.AjaxActionResult;

@Controller
public class RevokeCertController {
	Logger logger = LoggerFactory.getLogger(getClass());

// 	@Autowired
// 	InspectService inspectService;
	@Autowired
	ISzcaApiService apiService;
	
	@RequestMapping("/revokeCert")
	public ModelAndView enrollCert() {
		ModelAndView view = new ModelAndView("revokeCert");
		try {
			List<CAData> caList = apiService.getCaList();
		
			view.addObject("caList", caList);
		
		}catch(Exception e) {
			
		}
		return view;
	}

	@RequestMapping("/revokeEECert")
	public void downloadEndEntityCert(HttpServletRequest req, HttpServletResponse res, EntityCertForm form) {
//	public void downloadEndEntityCert(HttpServletRequest req, HttpServletResponse res, String issuerDn,
//			String subjectDn, String serialNum, String user) {
		logger.info("==revokeEECert");
		AjaxActionResult result = new AjaxActionResult();
		try {
			ApiAdminUserData adminUser = apiService.getApiAdminUserData(form.getAdminName());
			if(adminUser==null || !adminUser.getPassword().equals(form.getAdminPasswd()))
			{
				//validate failed -------
				result.setSuccess(false);
				result.setResultCode(-1);
				result.setMessage("管理员名称/密码不正确，撤销证书失败。");
			}else if(isEmpty(form.getSerialNum()) && isEmpty(form.getSubjectDn())&& isEmpty(form.getUser())){
				result.setSuccess(false);
				result.setResultCode(-1);
				result.setMessage("输入的证书查询条件不正确，撤销证书失败。");
			}else {
				
				AuthenticationToken admin =   new AlwaysAllowLocalAuthenticationToken(
						new UsernamePrincipal(adminUser.getUsername()+"->"+req.getRemoteAddr() + ":" + req.getRequestURI()));
				String aki=null;
				List<String> snList = apiService.revoke(admin, form.getIssuerDn(), form.getSerialNum(),form.getSubjectDn(), form.getReason(), form.getUser(), aki);
				 
				result.setSuccess(true);
				result.setResultCode(0);
				result.setMessage("撤销证书["+snList+"]成功。");
			} 
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultCode(-2);
			result.setMessage("撤销证书失败："+e.getMessage());
				e.printStackTrace();
			 
		}
		
		try {
			res.setContentType("text/json;charset=utf-8");
			res.getWriter().write(result.getJSONString());
			res.getWriter().flush();
			res.getWriter().close();
		} catch (Exception e) {
			String msg = String.format("撤销证书失败:%s。",e.getMessage());
			logger.error(msg);
			e.printStackTrace();
		}
	}
	
	private boolean isEmpty(String s) {
		return s==null || "".equals(s.trim());
	}

}
