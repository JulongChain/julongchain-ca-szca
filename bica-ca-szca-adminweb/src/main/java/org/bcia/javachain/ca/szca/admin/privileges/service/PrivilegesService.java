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

package org.bcia.javachain.ca.szca.admin.privileges.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.bcia.javachain.ca.result.Result;
import org.bcia.javachain.ca.szca.admin.privileges.vo.EditBasicAccessRulesFrom;
import org.cesecore.roles.RoleData;
import org.springframework.web.servlet.ModelAndView;
 
public interface PrivilegesService {
	/**
	* Description:  
	* @param request
	* @return a List of all roles, excepting ones that refer to CA's which the current role doesn't have access to
	* Date: 2018年5月23日 下午2:07:34
	* Author:power
	* Version: 1.0
	*/
	List<RoleData>  getRoles(HttpServletRequest request);
	
	
	
    /**
    * Description:  Adds a new role 
    * @param request
    * @param roleName
    * @return
    * Date: 2018年5月23日 下午3:30:54
    * Author:power
    * Version: 1.0
    */
    Result addRole(HttpServletRequest request,String  roleName) ;
    
    
    
    /**
    * Description: Method to remove a role.
    * @param request
    * @param roleName
    * @return
    * Date: 2018年5月23日 下午6:41:40
    * Author:power
    * Version: 1.0
    */
    Result removeRole(HttpServletRequest request,String  roleName) ;
    
    /**
    * Description: Method to rename a role.
    * @param request
    * @param oldname
    * @param newname
    * @return
    * Date: 2018年5月23日 下午6:31:40
    * Author:power
    * Version: 1.0
    */
    Result renameRole(HttpServletRequest request,String oldname, String newname);
    
    
    
    /**
    * Description:
    * @param request
    * @param roleName
    * Date: 2018年5月24日 下午3:56:24
    * Author:power
    * Version: 1.0
    */
     void editbasicaccessrules(HttpServletRequest request,String roleName,ModelAndView view);

    
     
     /**
     * @param request
     * @param roleName
     * @param view
     * @return
     */
    Result  editadminentities(HttpServletRequest request, String roleName,ModelAndView view);
    /**
    * Description:
    * @param request
    * @param roleTemplate
    * @param currentCAs
    * @param currentEndEntityProfiles
    * @param currentOtherRules
    * @param currentEndEntityRules
    * @param currentInternalKeybindingRules
    * @return
    * Date: 2018年5月24日 下午3:52:24
    * Author:power
    * Version: 1.0
    */
    Result saveAccessRules(HttpServletRequest request,String roleName,String roleTemplate, EditBasicAccessRulesFrom editBasicAccessRulesFrom) ;
    
    /**
    * Description:
    * @param request
    * @param primaryKeye
    * Date: 2018年5月24日 下午3:56:44
    * Author:power
    * Version: 1.0
    */
    Result deleteAdmin(HttpServletRequest request, String roleName, String primaryKeye);
    
    
    /**
    * Description:
    * @param request
    * @param matchWith
    * @param matchType
    * @param matchValue
    * @param matchCaId
    * @param roleName
    * @return
    * Date: 2018年5月24日 下午3:56:54
    * Author:power
    * Version: 1.0
    */
    Result addAdmin(HttpServletRequest request,String matchWith, String matchType,String matchValue,String matchCaId,String roleName) ;
}
