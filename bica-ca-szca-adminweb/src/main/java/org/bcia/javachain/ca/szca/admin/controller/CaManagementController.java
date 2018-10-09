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

package org.bcia.javachain.ca.szca.admin.controller;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.bcia.javachain.ca.szca.admin.ca.CADataHandlerBean;
import org.bcia.javachain.ca.szca.admin.ca.CaManagementBean;
import org.bcia.javachain.ca.szca.admin.ca.service.CaManagementService;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.certificates.ca.CAData;
import org.cesecore.certificates.ca.CAInfo;
import org.cesecore.certificates.ca.X509CAInfo;
import org.cesecore.certificates.ca.catoken.CATokenConstants;
import org.cesecore.certificates.certificateprofile.CertificateProfileData;
import org.cesecore.keys.token.CryptoTokenData;
import org.cesecore.util.SimpleTime;
import org.cesecore.util.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.szca.common.LoginUser;
import com.szca.wfs.common.WebUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
public class CaManagementController {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());


	@Autowired
	CaManagementBean caBean ;
	@Autowired
	CaManagementService caManagementService;
	@Autowired
	CADataHandlerBean caDataHandlerBean;
	@PersistenceContext//(unitName = CesecoreConfiguration.PERSISTENCE_UNIT)
	private EntityManager entityManager;

	@RequestMapping("/ca/caList")
	public ModelAndView caList(HttpServletRequest request,String currentPage) {
		logger.info("==========caList");
		String msg = "";
		List<CAInfo> caList = null;
		try {

			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if (auth == null)
				throw new Exception("没有权限导入CA证书。");

			caList = caBean.getCaList(auth);
		} catch (Exception e) {
			msg = String.format("caList    failed: " + e.getMessage());
		}
		ModelAndView view = new ModelAndView("/ca/caList");

		view.addObject("totalRowsCount", caList!=null?caList.size():0);
		view.addObject("currentPage", currentPage);
		view.addObject("resultList", WebUtils.getPageList(caList, currentPage));
		return view;

	}

	@RequestMapping("/ca/caInfo")
	public ModelAndView caInfo(HttpServletRequest request,int caid) {
		logger.info("=========caInfo");

		X509CAInfo cainfo = null;
		ModelAndView view= new ModelAndView("/ca/caInfo");

		try {
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			cainfo = (X509CAInfo)caBean.getCaById(auth,caid);//importCAFromKeyStore(auth, name, p12file.getBytes(), passwd, signKey, encKey);
			caManagementService.loadCaInfo(auth, cainfo, view);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//view.addObject("dump", dump);


		return view;

	}

	@RequestMapping("/ca/renameCA")
	public ModelAndView renameCA(HttpServletRequest request,int caid,String name) {
		logger.info("=========renameCA");

		try {
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			caDataHandlerBean.renameCA(auth,caid, name);

			//cainfo = caBean.updateCaName(auth,caid,name);//importCAFromKeyStore(auth, name, p12file.getBytes(), passwd, signKey, encKey);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/ca/caList.html");
	}

	@RequestMapping("/ca/updateCA")
	public ModelAndView updateCA(HttpServletRequest request,int caid) {
		logger.info("=========updateCrlPeriod");

		try {
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			X509CAInfo cainfo = (X509CAInfo) caBean.getCaById(auth,caid);
			caManagementService.setCaInfo(auth, request, cainfo);
			caDataHandlerBean.editCA(auth, cainfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/ca/caList.html");
	}


	@RequestMapping("/ca/renewCA")
	public ModelAndView renewCA(HttpServletRequest request,int caid) {
		logger.info("=========renewCA");

		try {
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			X509CAInfo cainfo = (X509CAInfo) caBean.getCaById(auth,caid);
			long validity = Long.parseLong(request.getParameter("validity"));
			Date now = new Date();
			cainfo.setValidity(now.getTime() + validity * SimpleTime.MILLISECONDS_PER_DAY);
			caDataHandlerBean.editCA(auth, cainfo);
			caDataHandlerBean.renewCA(auth, caid,now, cainfo.getCAToken().getAliasFromPurpose(CATokenConstants.CAKEYPURPOSE_CERTSIGN), false);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/ca/caList.html");
	}

	@Transactional
	@RequestMapping("/ca/removeCA")
	public ModelAndView removeCA(HttpServletRequest request,int caid) {
		logger.info("=========removeCA:" + caid);

		try {
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			String result = caDataHandlerBean.checkCaIdExist(auth, caid);
			if(result == null || result.length() < 1)
				caDataHandlerBean.removeCA(auth,caid);
			else {
				ModelAndView view = new ModelAndView("/ca/showResult");
				view.addObject("showResult", result);
				return view;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/ca/caList.html");
	}

	@Transactional
	@RequestMapping("/ca/revokeCA")
	public ModelAndView revokeCA(HttpServletRequest request,int caid, int reason) {
		logger.info("=========revokeCA:" + caid);

		try {
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			caDataHandlerBean.revokeCA(auth, caid, reason);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/ca/caInfo.html?caid=" + caid);
	}

	@RequestMapping("/ca/openCaCert")
	public void openCaCert(HttpServletRequest request,HttpServletResponse response ,int caid ) {
		logger.info("=========openCaCert");
		try {

			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			//cryptoTokenBean.removeKeyPair(auth, cryptoTokenId,alias);
//			final PublicKey publicKey = cryptoTokenBean.getPublicKey(auth, cryptoTokenId, alias);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", " attachment; filename=\"" + StringTools.stripFilename(caid + ".pem") + "\"");
			response.getOutputStream().write(caid);
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return  new ModelAndView("redirect:/cryptoToken/viewCryptoToken.html?cryptoTokenId="+ cryptoTokenId);

	}


	@RequestMapping("/ca/wizardCA")
	public ModelAndView wizardCA(HttpServletRequest request, String caType) {
		logger.info("=========renameCA");
		List<CryptoTokenData> keyList =null;
		List<CAData> caList =null;
		String certProfiles = null;
		try {
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			//caDataHandlerBean.renameCA(auth,caid, name);

			//cainfo = caBean.updateCaName(auth,caid,name);//importCAFromKeyStore(auth, name, p12file.getBytes(), passwd, signKey, encKey);
			keyList = caBean.getCryptoTokenList();
			caList = caBean.getCaList();
			List<CertificateProfileData> cpds = CertificateProfileData.findAll(entityManager);
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			for(int i=0;i<cpds.size();i++) {
				CertificateProfileData cpd = cpds.get(i);
				jsonObject.put("type", cpd.getCertificateProfile().getType());
				jsonObject.put("profileId", cpd.getId());
				jsonObject.put("profileName", cpd.getCertificateProfileName());
				jsonArray.add(jsonObject);
			}

			certProfiles = jsonArray.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ModelAndView view =  new ModelAndView("/ca/caWizard");
		view.addObject("keyList", keyList);
		view.addObject("caList", caList);
		view.addObject("certProfiles", certProfiles);
		return view;
	}

	@RequestMapping("/ca/createNewCA")
	public ModelAndView createNewCA(HttpServletRequest request, CreateNewCaForm createNewCaForm) {
		logger.info("=========createNewCA");
		List<CryptoTokenData> keyList =null;
		List<CAData> caList =null;
		try {
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			//caDataHandlerBean.renameCA(auth,caid, name);
			//
			// certificateProfileId: 2=(SubCAs) SUBCA, 3=(RootCAs) ROOTCA
			//<!-- signedBy: 1=Self Signed, 2=External CA -->
			//if("1".equals(createNewCaForm.getSignedBy()))
			//	createNewCaForm.setCertificateProfileId("3");
			//else
			//	createNewCaForm.setCertificateProfileId("2");
			//cainfo = caBean.updateCaName(auth,caid,name);//importCAFromKeyStore(auth, name, p12file.getBytes(), passwd, signKey, encKey);
//			keyList = caBean.getCryptoTokenList();
//			caList = caBean.getCaList();
			caBean.actionCreateCaMakeRequest(auth,createNewCaForm);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/ca/caList.html");
	}


	@RequestMapping("/ca/importP12")
	public ModelAndView importP12(HttpServletRequest request,MultipartFile p12file,String name,String passwd,String signKey,String encKey) {
		logger.info("=========importP12");


		//CertAndRequestDumpBean dump = null;
		try {
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();

			if (p12file != null && p12file.getBytes().length > 0) {
				//logger.info(new String(p12file.getBytes()));
				//dump = service.inspectReqFile(reqfile.getBytes());
				caBean.importCAFromKeyStore(auth, name, p12file.getBytes(), passwd, signKey, encKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//view.addObject("dump", dump);
		return new ModelAndView("redirect:/ca/caList.html");

	}
}
