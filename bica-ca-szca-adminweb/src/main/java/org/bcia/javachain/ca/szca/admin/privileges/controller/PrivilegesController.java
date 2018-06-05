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

package org.bcia.javachain.ca.szca.admin.privileges.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.bcia.javachain.ca.result.Result;
import org.bcia.javachain.ca.szca.admin.privileges.service.PrivilegesService;
import org.bcia.javachain.ca.szca.admin.privileges.vo.EditBasicAccessRulesFrom;
import org.cesecore.roles.RoleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

@Controller
@RequestMapping({ "privileges" })
public class PrivilegesController {
	@Autowired
	PrivilegesService privilegesService;

	@RequestMapping(value = "/administratorprivileges", method = RequestMethod.GET)
	public ModelAndView administratorprivileges(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/privileges/administratorprivileges");
		List<RoleData> roles = privilegesService.getRoles(request);
		view.addObject("roles", roles);
		return view;
	}

	@RequestMapping(value = "/addRole", method = { RequestMethod.POST })
	@ResponseBody
	public String addRole(HttpServletRequest request, String roleName) {
		Result result = privilegesService.addRole(request, roleName);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "/removeRole", method = { RequestMethod.POST })
	@ResponseBody
	public String removeRole(HttpServletRequest request, String roleName) {
		Result result = privilegesService.removeRole(request, roleName);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "/renameRole", method = { RequestMethod.POST })
	@ResponseBody
	public String renameRole(HttpServletRequest request, String oldname, String newname) {
		Result result = privilegesService.renameRole(request, oldname, newname);
		return JSONObject.fromObject(result).toString();
	}

	@RequestMapping(value = "/editbasicaccessrules", method = RequestMethod.GET)
	public ModelAndView editbasicaccessrules(HttpServletRequest request, String roleName) {
		ModelAndView view = new ModelAndView("/privileges/editbasicaccessrules");
		privilegesService.editbasicaccessrules(request, roleName, view);
 		return view;
	}

	@RequestMapping(value = "/saveAccessRules", method = { RequestMethod.POST })
	@ResponseBody
	public String saveAccessRules(HttpServletRequest request,String roleName,String roleTemplate,EditBasicAccessRulesFrom editBasicAccessRulesFrom ) {
   		Result result = privilegesService.saveAccessRules(request,roleName, roleTemplate, editBasicAccessRulesFrom);
		return JSONObject.fromObject(result).toString();
	}

	
	@RequestMapping(value = "/editadminentities", method = RequestMethod.GET)
	public ModelAndView editadminentities(HttpServletRequest request, String roleName) {
		ModelAndView view = new ModelAndView("/privileges/editadminentities");
		privilegesService.editadminentities(request, roleName, view);
 		return view;
	}
	
	
	@RequestMapping(value = "/deleteAdmin", method = { RequestMethod.POST })
	@ResponseBody
	public String deleteAdmin(HttpServletRequest request,String roleName, String primaryKeye) {
		Result result = privilegesService.deleteAdmin(request,  roleName, primaryKeye);
		return JSONObject.fromObject(result).toString();
	}

	
	@RequestMapping(value = "/addAdmin", method = { RequestMethod.POST })
	@ResponseBody
	public String addAdmin(HttpServletRequest request,String matchWith, String matchType,String matchValue,String matchCaId,String roleName) {
		Result result = privilegesService.addAdmin(request, matchWith, matchType, matchValue, matchCaId, roleName);
		return JSONObject.fromObject(result).toString();
	}

	
	
}
