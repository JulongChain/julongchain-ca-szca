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

package org.bcia.javachain.ca.szca.admin.ra.servcie;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.FinderException;
import javax.servlet.http.HttpServletRequest;

import org.bcia.javachain.ca.result.Result;
import org.bcia.javachain.ca.szca.admin.ra.UserView;
import org.bcia.javachain.ca.szca.admin.ra.vo.EndEntityInformationVo;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authorization.AuthorizationDeniedException;
import org.cesecore.certificates.ca.CADoesntExistsException;
import org.ejbca.core.EjbcaException;
import org.ejbca.core.ejb.ra.EndEntityExistsException;
import org.ejbca.core.model.approval.WaitingForApprovalException;
import org.ejbca.core.model.ra.raadmin.UserDoesntFullfillEndEntityProfile;
import org.ejbca.util.query.IllegalQueryException;
import org.springframework.web.servlet.ModelAndView;

import com.szca.wfs.common.BaseForm;

/**
* Description:
* ClassName: RaFunctionsService
* Date:2018年5月11日 上午10:07:34
* @author power
* @version 1.0
*/
/**
* Description:
* ClassName: RaFunctionsService
* Date:2018年5月11日 上午10:07:35
* @author power
* @version 1.0
*/
public interface RaFunctionsService {

   /**
* Description: 新增实体
* @param userdata
* @param administrator
* @throws EndEntityExistsException
* @throws CADoesntExistsException
* @throws AuthorizationDeniedException
* @throws WaitingForApprovalException
* @throws EjbcaException
* @throws org.ejbca.core.model.ra.raadmin.UserDoesntFullfillEndEntityProfile
* @throws org.ejbca.core.model.approval.WaitingForApprovalException
* Date: 2018年5月7日 下午3:25:32
* Author:power
* Version: 1.0
*/
abstract public void addUser(UserView userdata,AuthenticationToken administrator) throws EndEntityExistsException, CADoesntExistsException, AuthorizationDeniedException, WaitingForApprovalException, EjbcaException, org.ejbca.core.model.ra.raadmin.UserDoesntFullfillEndEntityProfile, org.ejbca.core.model.approval.WaitingForApprovalException ;
   abstract public TreeMap<String, Integer> getAuthorizedEndEntityProfileNames(final String endentityAccessRule,AuthenticationToken administrator);
   
   abstract public Map<Integer, List<Integer>> getCasAvailableToEndEntity(int endentityprofileid, final String endentityAccessRule,AuthenticationToken administrator);
   
   public String[] getCertificateProfileNames(AuthenticationToken administrator);
   public String getCertificateProfileName(int certificateprofileid);
  /**
 * @param request
 * @param profileid
 * @return
 */
Result addendentity(HttpServletRequest request,  Integer profileid,ModelAndView view); 


/**
* Description:
* @param 保存终端数据
* @return
* Date: 2018年5月3日 下午4:36:14
* Author:power
* Version: 1.0
*/
public Result saveEndentity(HttpServletRequest request,EndEntityInformationVo endEntityInformationVo);

/**
* Description:显示所有实体信息
* @param request
* @param index
* @param size
* @return
* @throws FinderException
* Date: 2018年5月7日 下午3:25:13
* Author:power
* Version: 1.0
*/
public void findAllUsers(HttpServletRequest request,BaseForm baseForm,ModelAndView view ,EndEntityInformationVo endEntityInformationVo)throws IllegalQueryException;
 
/**
* Description:获取实体信息
* @param request
* @param username
* @param view
* @return
* Date: 2018年5月7日 下午3:24:52
* Author:power
* Version: 1.0
*/
public Result viewendentity(HttpServletRequest request, String  username,ModelAndView view);

 
/**
* Description:
* @param request
* @param username
* @param view
* @return
* Date: 2018年5月8日 下午5:50:35
* Author:power
* Version: 1.0
*/
public Result editendentity(HttpServletRequest request, String  username,ModelAndView view);



 /**
* Description:
* @param request
* @param endEntityInformationVo
* @param primevalUsername
* @return
* Date: 2018年5月11日 上午10:07:40
* Author:power
* Version: 1.0
*/
public Result changeUserData(HttpServletRequest request, EndEntityInformationVo endEntityInformationVo,String primevalUsername) throws AuthorizationDeniedException, EndEntityExistsException, CADoesntExistsException, UserDoesntFullfillEndEntityProfile, WaitingForApprovalException, EjbcaException ;



/**
* Description:
* @param request
* @param username
* @param view
* @return
* Date: 2018年5月11日 上午10:07:40
* Author:power
* Version: 1.0
*/
public Result viewcertificate(HttpServletRequest request, String username, int index, ModelAndView view);


    /**
     * Description:
     * @param request
     * @param username
     * @return
     * Date: 2018年5月11日 上午10:07:40
     * Author:power
     * Version: 1.0
     */
    public Result revoke(HttpServletRequest request,String username,int reason,String sha1Finger);

    /**
     * Description:
     * @param request
     * @param username
     * @return
     * Date: 2018年5月11日 上午10:07:40
     * Author:power
     * Version: 1.0
     */
    public Result revokeUser(HttpServletRequest request,String username,int reason);

}
