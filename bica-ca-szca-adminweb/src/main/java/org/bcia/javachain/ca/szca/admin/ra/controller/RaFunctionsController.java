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

package org.bcia.javachain.ca.szca.admin.ra.controller;

import javax.servlet.http.HttpServletRequest;

import org.bcia.javachain.ca.result.Result;
import org.bcia.javachain.ca.szca.admin.ra.servcie.RaFunctionsService;
import org.bcia.javachain.ca.szca.admin.ra.vo.EndEntityInformationVo;
import org.ejbca.util.query.IllegalQueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szca.wfs.common.BaseForm;

import net.sf.json.JSONObject;

@Controller
@RequestMapping({"ra"})
public class RaFunctionsController {

	Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired 
	RaFunctionsService  raFunctionsService;
	
 
 	@RequestMapping(value = "/addendentity", method = RequestMethod.GET)
	public ModelAndView addendentity(HttpServletRequest request,  Integer profileid) {
		ModelAndView view = new ModelAndView("/ra/addendentity");
		Result  result =	raFunctionsService.addendentity(request, profileid,view);
		view.addObject("isSuccess", result.isSuccess());
  		view.addObject("msg", result.getMsg());
		return view;
 	}
 	
 	
 	@RequestMapping(value = "/viewendentity", method = RequestMethod.GET)
	public ModelAndView viewendentity(HttpServletRequest request,  String  username) {
		ModelAndView view = new ModelAndView("/ra/viewendentity");
		Result  result =	raFunctionsService.viewendentity(request, username,view);
		view.addObject("isSuccess", result.isSuccess());
  		view.addObject("msg", result.getMsg());
 		return view;
 	}
 	
 	
 	
 	@RequestMapping(value = "/editendentity", method = RequestMethod.GET)
	public ModelAndView editendentity(HttpServletRequest request,  String  username) {
		ModelAndView view = new ModelAndView("/ra/editendentity");
		Result  result =	raFunctionsService.editendentity(request, username,view);
  		view.addObject("isSuccess", result.isSuccess());
  		view.addObject("msg", result.getMsg());
  		return view;
 	}
 	
 	
 	
 	
	 
	@RequestMapping(value = "/saveEndentity", method = {RequestMethod.POST })
	@ResponseBody
 	public String saveEndentity(HttpServletRequest request,EndEntityInformationVo endEntityInformationVo) {
  		   Result  result =raFunctionsService.saveEndentity(request, endEntityInformationVo);
 		   return JSONObject.fromObject(result).toString();
 	}
	
	
	@RequestMapping(value = "/viewcertificate", method = {RequestMethod.GET })
 	public ModelAndView viewcertificate(HttpServletRequest request,String  username) {
		   ModelAndView view = new ModelAndView("/ra/viewcertificate");
 		   Result  result =raFunctionsService.viewcertificate(request, username,view);
		   if(!result.isSuccess()) {
  				view.addObject("msg", result.getMsg());
			}
          return view;
	}
	
	
	
	
	@RequestMapping(value = "/revoke", method = {RequestMethod.POST })
	@ResponseBody
 	public String revoke(HttpServletRequest request,String  username,int reason) {
  		  Result  result =raFunctionsService.revoke(request, username,reason);
  		  return JSONObject.fromObject(result).toString();
	}
	
	
	
	@RequestMapping(value = "/changeUserData", method = {RequestMethod.POST })
	@ResponseBody
 	public String changeUserData(HttpServletRequest request,EndEntityInformationVo endEntityInformationVo,String primevalUsername) {
 			 Result result=new Result();
			try {
				result = raFunctionsService.changeUserData(request, endEntityInformationVo,primevalUsername);
			} catch (Exception e) {
 				e.printStackTrace();
 				result.setSuccess(false);
 				result.setMsg(e.getMessage());
			}
 			 return JSONObject.fromObject(result).toString();
  	}
	
	
	
	 
	
	
	
	@RequestMapping(value = "/listEndentity", method = {RequestMethod.GET,RequestMethod.POST })
 	public ModelAndView listEndentity(HttpServletRequest request,EndEntityInformationVo endEntityInformationVo,BaseForm baseForm) {
		   ModelAndView view = new ModelAndView("/ra/listEndentity");
  		   try {
			raFunctionsService.findAllUsers(request,baseForm,view,endEntityInformationVo);
		   } catch (IllegalQueryException e) {
 			e.printStackTrace();
 			view.addObject("error", true);
 			view.addObject("errorMsg", "Illegal Query");
		    }
           return view;
	}
}
