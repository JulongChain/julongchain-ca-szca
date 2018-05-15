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

package org.bcia.javachain.ca.szca.publicweb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.certificates.ca.CAData;
import org.cesecore.certificates.endentity.EndEntityInformation;
import org.cesecore.certificates.endentity.EndEntityType;
import org.cesecore.certificates.endentity.EndEntityTypes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.szca.common.AjaxActionResult;

@Controller
public class RegisterEntityController extends BaseController{
 
	@RequestMapping("/registerEntity")
	public ModelAndView register(HttpServletRequest req, HttpServletResponse res) throws Exception{
		List<CAData> caList = apiService.getCaList();
		ModelAndView view= new ModelAndView("registerEntity");
		view.addObject("caList", caList);
		return view;
	}
  
	@RequestMapping("/addEntity")
	public void addEntity(HttpServletRequest req, HttpServletResponse res) {
		 
		String user = req.getParameter("user");
		String password = req.getParameter("password");
		String subjectDn = req.getParameter("subjectDn");
 		String subjectAltName = req.getParameter("subjectAltName");
 		String caId = req.getParameter("caId");
//		String keylength = req.getParameter("keylength");
//		String keyalg = req.getParameter("keyalg");
//		String tokenKeySpec = req.getParameter("tokenKeySpec");
//		String resulttype = req.getParameter("resulttype");
//		String csr = req.getParameter("csr");
 		password = "Bcia12";
 		AjaxActionResult result = new AjaxActionResult();
		try {
		 	
			AuthenticationToken admin =  this.getAuthenticationToken(req);
			EndEntityInformation entity = new EndEntityInformation();
			entity.setEndEntityProfileId(1);
			entity.setCertificateProfileId(1);
			entity.setAdministrator(false);
			entity.setDN(subjectDn);
			//entity.setPassword(password);
			//entity.setCAId(-1296285382);
			entity.setCAId(Integer.parseInt(caId));
			entity.setUsername(user);
			entity.setTokenType(2);
			entity.setSubjectAltName(subjectAltName);
			entity.setType(new EndEntityType(EndEntityTypes.ENDUSER));
			String newPwd = apiService.addEndEntity(admin, entity);
			 
			result.setResultCode(0);
			result.setSuccess(true);
			//"成功注册实体[%s],注册密码是%s请牢记此密码。"
			String msg=this.getMessage(req, "register.addentity.success");
			result.setMessage(String.format(msg,user,newPwd));
			
		} catch (Exception e) {
			//"注册实体[%s]失败:%s。"
			String msg = String.format(this.getMessage(req, "register.addentity.failed"),user,e.getMessage());
			logger.error(msg);
			result.setResultCode(-1);
			result.setSuccess(false);
			result.setMessage(msg);
			e.printStackTrace();
		}
		 
		try {
			res.setContentType("text/json;charset=utf-8");
			res.getWriter().write(result.getJSONString());
			res.getWriter().flush();
			res.getWriter().close();
		} catch (Exception e) {
			String msg = String.format("注册实体[%s]失败:%s。",user,e.getMessage());
			logger.error(msg);
			e.printStackTrace();
		}
	}
 
}
