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
import java.security.PublicKey;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bcia.javachain.ca.szca.admin.ca.CryptoTokenBean;
import org.bcia.javachain.ca.szca.admin.ca.CryptoTokenGuiInfo;
import org.bcia.javachain.ca.szca.admin.ca.KeyPairGuiInfo;
import org.bcia.javachain.ca.szca.common.cesecore.keys.token.CryptoTokenManagementSessionBean;
import org.bcia.javachain.ca.szca.common.cesecore.keys.token.CryptoTokenSessionLocal;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.keys.token.BaseCryptoToken;
import org.cesecore.keys.token.CryptoToken;
import org.cesecore.keys.util.KeyTools;
import org.cesecore.util.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.szca.common.AjaxActionResult;
import com.szca.common.LoginUser;
import com.szca.wfs.common.WebUtils;



@Controller
public class CryptoTokenController {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());


	@Autowired
	CryptoTokenBean cryptoTokenBean;
	@Autowired
	CryptoTokenSessionLocal cryptoTokenSession;
	@Autowired
	CryptoTokenManagementSessionBean cryptoTokenManagementSessionBean;
	@PersistenceContext//(unitName = CesecoreConfiguration.PERSISTENCE_UNIT)
	private EntityManager entityManager;

	@RequestMapping("/cryptoToken/cryptoTokenList")
	public ModelAndView cryptoTokenList(HttpServletRequest request,String currentPage) {
		logger.info("==========cryptoTokenList");
		String msg = "";
		List<CryptoTokenGuiInfo> ctList = null;
		try {

			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if (auth == null)
				throw new Exception("没有权限导入CA证书。");

			ctList = cryptoTokenBean.getCryptoTokenGuiList(auth);
		} catch (Exception e) {
			msg = String.format("cryptoTokenList    failed: " + e.getMessage());
		}
		ModelAndView view = new ModelAndView("/cryptoToken/cryptoTokenList");

		view.addObject("totalRowsCount", ctList!=null?ctList.size():0);
		view.addObject("currentPage", currentPage);
		//view.addObject("rowsPerPage", form.getRowsPerPage());
		view.addObject("resultList", WebUtils.getPageList(ctList, currentPage));
		return view;

	}
	@RequestMapping("/cryptoToken/cryptoToken")
	public ModelAndView cryptoTokenEdit(HttpServletRequest request,String name,String type,String authCode,String authCode2,boolean autoActivation,boolean expKey) {
		logger.info("=========cryptoTokenImport");


		//CertAndRequestDumpBean dump = null;
		try {} catch (Exception e) {
			e.printStackTrace();
		}

		//view.addObject("dump", dump);
		return new ModelAndView("/cryptoToken/cryptoToken");

	}

	@Transactional
	@RequestMapping("/cryptoToken/addCryptoToken")
	public ModelAndView addCryptoToken(HttpServletRequest request,int cryptoTokenId,String name,String type,String authCode,String authCode2,boolean autoActivate,boolean expKey) {
		logger.info("=========cryptoTokenImport");

		//if(authCode==null || !authCode.equals(authCode2))
		//	System.out.println("授权码......");//throw new Exception("");

		//CertAndRequestDumpBean dump = null;
		try {

			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if(cryptoTokenId == 0) {
				cryptoTokenBean.saveCurrentCryptoToken(auth, name, authCode, expKey, autoActivate);
			}else {
				CryptoToken cryptoToken = cryptoTokenSession.getCryptoToken(cryptoTokenId);
				cryptoToken.setTokenName(name);
				if(autoActivate && !cryptoToken.isAutoActivationPinPresent()) {
					Properties prop = cryptoToken.getProperties();
					BaseCryptoToken.setAutoActivatePin(prop, authCode, true);
					cryptoToken.activate(authCode.toCharArray());
				}else if(!autoActivate && cryptoToken.isAutoActivationPinPresent()) {
					Properties prop = cryptoToken.getProperties();
					prop.remove(CryptoToken.AUTOACTIVATE_PIN_PROPERTY);
					cryptoToken.setProperties(prop);
				}
				cryptoTokenSession.mergeCryptoToken(cryptoToken);

			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		//view.addObject("dump", dump);
		return new ModelAndView("redirect:/cryptoToken/cryptoTokenList.html");

	}

	@Transactional
	@RequestMapping("/cryptoToken/activeCryptoToken")
	public ModelAndView activeCryptoToken(HttpServletRequest request,int cryptoTokenId,String authCode) {
		logger.info("=========activeCryptoToken");
		try {

			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			cryptoTokenManagementSessionBean.activate(auth, cryptoTokenId, authCode.toCharArray());
		} catch (Exception e) {
			e.printStackTrace();
		}

		//view.addObject("dump", dump);
		return new ModelAndView("redirect:/cryptoToken/cryptoTokenList.html");

	}

	@Transactional
	@RequestMapping("/cryptoToken/deleteCryptoToken")
	public ModelAndView deleteCryptoToken(HttpServletRequest request,int cryptoTokenId) {
		logger.info("=========deleteCryptoToken");
		try {

			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			cryptoTokenManagementSessionBean.deleteCryptoToken(auth, cryptoTokenId);

		} catch (Exception e) {
			e.printStackTrace();
		}

		//view.addObject("dump", dump);
		return new ModelAndView("redirect:/cryptoToken/cryptoTokenList.html");

	}

	@RequestMapping("/cryptoToken/viewCryptoToken")
	public ModelAndView viewCryptoToken(HttpServletRequest request,int cryptoTokenId) {
		logger.info("=========viewCryptoToken");

		CryptoTokenGuiInfo cryptoTokenGuiInfo=null;
		List<KeyPairGuiInfo>  kpList = null;
		//CertAndRequestDumpBean dump = null;
		try {

			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			cryptoTokenGuiInfo = cryptoTokenBean.getCryptoTokenById(auth, cryptoTokenId);
			kpList = cryptoTokenBean.getKeyPairGuiList(auth, cryptoTokenGuiInfo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView view =  new ModelAndView("/cryptoToken/cryptoToken");
		view.addObject("cryptoTokenGuiInfo", cryptoTokenGuiInfo);
		view.addObject("kpList", kpList);
		return view;

	}

	@RequestMapping("/cryptoToken/genNewKeyPair")
	public ModelAndView genNewKeyPair(HttpServletRequest request,int cryptoTokenId, String alias,String algorithm) {
		logger.info("=========genNewKeyPair");
		try {
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			cryptoTokenBean.generateNewKeyPair(auth, cryptoTokenId,alias,algorithm);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return  new ModelAndView("redirect:/cryptoToken/viewCryptoToken.html?cryptoTokenId="+ cryptoTokenId);


	}
	@RequestMapping("/cryptoToken/testCryptoToken")
	public void testCryptoToken(HttpServletRequest request ,HttpServletResponse response ,int cryptoTokenId,String alias) {
		logger.info("=========testCryptoToken");
		AjaxActionResult result = new AjaxActionResult();

		String testMsg = "";
		try {

			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			cryptoTokenBean.testKeyPair(auth, cryptoTokenId,alias);
			testMsg = "测试通过。";
			result.setResultCode(0);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			testMsg = "测试失败："+ e.getMessage();
			result.setResultCode(-1);
			result.setSuccess(false);
		}
		result.setMessage(testMsg);

		try {
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(result.getJSONString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	@RequestMapping("/cryptoToken/removeCryptoToken")
	public ModelAndView removeCryptoToken(HttpServletRequest request,int cryptoTokenId,String alias) {
		logger.info("=========removeCryptoToken");


		try {

			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			cryptoTokenBean.removeKeyPair(auth, cryptoTokenId,alias);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return  new ModelAndView("redirect:/cryptoToken/viewCryptoToken.html?cryptoTokenId="+ cryptoTokenId);

	}

	@RequestMapping("/cryptoToken/downloadCryptoToken")
	public void downloadCryptoToken(HttpServletRequest request,HttpServletResponse response ,int cryptoTokenId,String alias) {
		logger.info("=========downloadCryptoToken");


		try {

			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			//cryptoTokenBean.removeKeyPair(auth, cryptoTokenId,alias);
			final PublicKey publicKey = cryptoTokenBean.getPublicKey(auth, cryptoTokenId, alias);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", " attachment; filename=\"" + StringTools.stripFilename(alias + ".pem") + "\"");
			response.getOutputStream().write(KeyTools.getAsPem(publicKey).getBytes());
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return  new ModelAndView("redirect:/cryptoToken/viewCryptoToken.html?cryptoTokenId="+ cryptoTokenId);

	}





}
