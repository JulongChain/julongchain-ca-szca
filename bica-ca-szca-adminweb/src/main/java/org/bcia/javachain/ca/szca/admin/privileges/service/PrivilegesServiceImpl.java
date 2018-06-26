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

package org.bcia.javachain.ca.szca.admin.privileges.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.bcia.javachain.ca.core.model.authorization.BasicAccessRuleSet;
import org.bcia.javachain.ca.core.model.authorization.BasicAccessRuleSetDecoder;
import org.bcia.javachain.ca.result.Result;
import org.bcia.javachain.ca.szca.admin.common.WebLanguages;
import org.bcia.javachain.ca.szca.admin.privileges.vo.AccessRuleDataVo;
import org.bcia.javachain.ca.szca.admin.privileges.vo.AccessRuleFrom;
import org.bcia.javachain.ca.szca.admin.privileges.vo.AccessRuleVo;
import org.bcia.javachain.ca.szca.admin.privileges.vo.AdminsMatchVo;
import org.bcia.javachain.ca.szca.admin.privileges.vo.EditBasicAccessRulesFrom;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authorization.AuthorizationDeniedException;
import org.cesecore.authorization.control.CryptoTokenRules;
import org.cesecore.authorization.control.StandardRules;
import org.cesecore.authorization.rules.AccessRuleData;
import org.cesecore.authorization.rules.AccessRuleExistsException;
import org.cesecore.authorization.rules.AccessRuleState;
import org.cesecore.authorization.user.AccessMatchType;
import org.cesecore.authorization.user.AccessUserAspectData;
import org.cesecore.authorization.user.matchvalues.AccessMatchValue;
import org.cesecore.authorization.user.matchvalues.AccessMatchValueReverseLookupRegistry;
import org.cesecore.keys.token.CryptoTokenInfo;
import org.cesecore.roles.RoleData;
import org.cesecore.roles.RoleExistsException;
import org.cesecore.roles.RoleNotFoundException;
import org.ejbca.config.EjbcaConfiguration;
import org.ejbca.config.GlobalConfiguration;
import org.ejbca.core.model.authorization.AccessRuleTemplate;
import org.ejbca.core.model.authorization.AccessRulesConstants;
import org.ejbca.core.model.authorization.DefaultRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.szca.common.LoginUser;

import org.bcia.javachain.ca.szca.common.bcca.core.ejb.authorization.ComplexAccessControlSession;
import org.bcia.javachain.ca.szca.common.bcca.core.ejb.ra.raadmin.EndEntityProfileSession;
import org.bcia.javachain.ca.szca.common.bcca.core.ejb.ra.userdatasource.UserDataSourceSession;
import org.bcia.javachain.ca.szca.common.bcca.core.ejb.ra.userdatasource.UserDataSourceSessionLocal;
import org.bcia.javachain.ca.szca.common.bcca.core.model.InternalEjbcaResources;
import org.bcia.javachain.ca.szca.common.cesecore.authorization.control.AccessControlSessionLocal;
import org.bcia.javachain.ca.szca.common.cesecore.certificates.ca.CaSessionLocal;
import org.bcia.javachain.ca.szca.common.cesecore.configuration.GlobalConfigurationSessionLocal;
import org.bcia.javachain.ca.szca.common.cesecore.keybind.InternalKeyBindingRules;
import org.bcia.javachain.ca.szca.common.cesecore.keys.token.CryptoTokenManagementSessionLocal;
import org.bcia.javachain.ca.szca.common.cesecore.roles.access.RoleAccessSessionLocal;
import org.bcia.javachain.ca.szca.common.cesecore.roles.management.RoleManagementSession;

@Service
public class PrivilegesServiceImpl implements PrivilegesService{
	private static Logger log = Logger.getLogger(PrivilegesServiceImpl.class);

	@Autowired
	private CaSessionLocal casession;
	
	@Autowired
	private RoleAccessSessionLocal roleAccessSessionLocal;
	
	
	@Autowired
	private AccessControlSessionLocal authorizationsession;
	
	@Autowired
    private RoleManagementSession roleManagementSession;
	
	@Autowired
	ComplexAccessControlSession complexAccessControlSession;

	@Autowired
	UserDataSourceSession userdatasourcesession;
	
	@Autowired
	GlobalConfigurationSessionLocal globalConfigSession;
	
	@Autowired
	EndEntityProfileSession endEntityProfileSession;
	
	@Autowired
	UserDataSourceSessionLocal userDataSourceSessionLocal;
	
	@Autowired
	CryptoTokenManagementSessionLocal cryptoTokenManagementSessionLocal;
	
    private static final InternalEjbcaResources intres = InternalEjbcaResources.getInstance();
    private Set<Integer> availablecas = new HashSet<Integer>();
    private ArrayList<Integer> availableendentityrules = new ArrayList<Integer>();
    private Set<Integer> availableendentityprofiles = new HashSet<Integer>();
    private List<Integer> availableotherrules = new ArrayList<Integer>();
    private List<String> availableInternalKeybindingRules = new ArrayList<String>();
    private TreeMap<String, Integer> allcanames = null;

	@Override
	public List<RoleData>  getRoles(HttpServletRequest request) {
 		AuthenticationToken authenticationToken=getAuthenticationToken(request);
        List<RoleData> roles = new ArrayList<RoleData>();
        roleLoop: for(RoleData role : roleAccessSessionLocal.getAllRoles()) {
            // Firstly, make sure that authentication token authorized for all access user aspects in role, by checking against the CA that produced them.
            for (AccessUserAspectData accessUserAspect : role.getAccessUsers().values()) {
                if (!casession.authorizedToCANoLogging(authenticationToken, accessUserAspect.getCaId())) {
                    continue roleLoop;
                }
            }
            // Secondly, walk through all CAs and make sure that there are no differences. 
            for (Integer caId : casession.getAllCaIds()) {
                if(!casession.authorizedToCANoLogging(authenticationToken, caId) && role.hasAccessToRule(StandardRules.CAACCESS.resource() + caId)) {
                    continue roleLoop;
                }
            }
            roles.add(role);
        }
        Collections.sort(roles);
 		return roles;
	}
	
	
	
     
	private  AuthenticationToken getAuthenticationToken(HttpServletRequest request) {
	    LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
		AuthenticationToken administrator = user.getAuthenticationToken();
		return administrator;
    }
	
	
 
	@Override
	public Result addRole(HttpServletRequest request, String roleName) {
        // Authorized to edit administrative privileges
		Result  result=new Result();
		AuthenticationToken authenticationToken =getAuthenticationToken(request);
        if (!authorizationsession.isAuthorized(authenticationToken, StandardRules.EDITROLES.resource())) {
            final String msg = intres.getLocalizedMessage("authorization.notuathorizedtoresource",  StandardRules.EDITROLES.resource(), null);
            result.setMsg(msg);
            return  result;
        }
        try {
	      roleManagementSession.create(authenticationToken, roleName);
	      result.setSuccess(true);
		} catch (RoleExistsException e) {
  			result.setMsg("RoleExists-角色已经存在");
 		} catch (AuthorizationDeniedException e) {
  			result.setMsg("授权被拒绝");
		}
 		return result;
	}




	@Override
	public Result removeRole(HttpServletRequest request, String roleName) {
		Result  result=new Result();
		AuthenticationToken authenticationToken =getAuthenticationToken(request);
        try {
			roleManagementSession.remove(authenticationToken, roleName);
			result.setSuccess(true);
		} catch (RoleNotFoundException e) {
 			e.printStackTrace();
 			result.setMsg("RoleNotFound");
		} catch (AuthorizationDeniedException e) {
 			e.printStackTrace();
 			result.setMsg("AuthorizationDenied");
		}
 		return result;
	}




	@Override
	public Result renameRole(HttpServletRequest request, String oldname, String newname) {
		Result  result=new Result();
		AuthenticationToken authenticationToken =getAuthenticationToken(request);
        try {
			roleManagementSession.renameRole(authenticationToken, oldname, newname);
			result.setSuccess(true);
 		} catch (RoleExistsException e) {
 			e.printStackTrace();
 			result.setMsg("RoleExists");
 		} catch (AuthorizationDeniedException e) {
 			e.printStackTrace();
 			result.setMsg("AuthorizationDenied");
 		}
         return result;
	}


    public Set<String> getAuthorizedAccessRulesUncategorized(AuthenticationToken administrator,final String endentityAccessRule, GlobalConfiguration globalconfiguration) {
        Set<String> accessRulesSet = new HashSet<String>();
        for(Entry<String, Set<String>> entry : getAuthorizedAccessRules(administrator,endentityAccessRule,globalconfiguration).entrySet()) {
            accessRulesSet.addAll(entry.getValue());
        }
        return accessRulesSet;
    }
    
     /** 
     * @return a map containing the administrator's authorized available access rules, sorted by category
     */

    public Map<String, Set<String>> getAuthorizedAccessRules(AuthenticationToken administrator,final String endentityAccessRule, GlobalConfiguration globalconfiguration) {
     	return complexAccessControlSession.getAuthorizedAvailableAccessRules(administrator,
                    globalconfiguration.getEnableEndEntityProfileLimitations(), globalconfiguration.getIssueHardwareTokens(),
                    globalconfiguration.getEnableKeyRecovery(), endEntityProfileSession.getAuthorizedEndEntityProfileIds(administrator, endentityAccessRule),
                    userdatasourcesession.getAuthorizedUserDataSourceIds(administrator, true), EjbcaConfiguration.getCustomAvailableAccessRules());
     }
 
    
    
 	@Override
	public void editbasicaccessrules(HttpServletRequest request, String roleName,ModelAndView view) {
 		AuthenticationToken authenticationToken =getAuthenticationToken(request);
		RoleData  roleData =roleAccessSessionLocal.findRole(roleName);
	    GlobalConfiguration globalconfiguration= (GlobalConfiguration) globalConfigSession.getCachedConfiguration(GlobalConfiguration.GLOBAL_CONFIGURATION_ID);
  	    Set<String> availableaccessrules= getAuthorizedAccessRulesUncategorized(authenticationToken,AccessRulesConstants.CREATE_END_ENTITY,globalconfiguration);
	    HashSet<String> aar = new HashSet<String>();
	    aar.addAll(availableaccessrules);
	    for (AccessRuleData accessRule : roleData.getAccessRules().values()) {
	            aar.add(accessRule.getAccessRuleName());
	    }
 	    
	    Map<String,String> rolesMap=new HashMap<String,String>();
 		rolesMap.put(WebLanguages.getInstance().getText(DefaultRoles.CUSTOM.getName()), DefaultRoles.CUSTOM.getName());
 		rolesMap.put(WebLanguages.getInstance().getText(DefaultRoles.CAADMINISTRATOR.getName()), DefaultRoles.CAADMINISTRATOR.getName());
 		rolesMap.put(WebLanguages.getInstance().getText(DefaultRoles.RAADMINISTRATOR.getName()), DefaultRoles.RAADMINISTRATOR.getName());
 		rolesMap.put(WebLanguages.getInstance().getText(DefaultRoles.SUPERVISOR.getName()), DefaultRoles.SUPERVISOR.getName());
  		rolesMap.put(WebLanguages.getInstance().getText(DefaultRoles.AUDITOR.getName()), DefaultRoles.AUDITOR.getName());
          // Check if administrator can create superadministrators
        if (aar.contains(StandardRules.ROLE_ROOT.resource())) {
       		rolesMap.put(WebLanguages.getInstance().getText(DefaultRoles.SUPERADMINISTRATOR.getName()), DefaultRoles.SUPERADMINISTRATOR.getName());
         }
        view.addObject("rolesMap", rolesMap);
        boolean usehardtokens=globalconfiguration.getIssueHardwareTokens();
        boolean  usekeyrecovery=globalconfiguration.getEnableKeyRecovery();
        availableendentityrules.add(Integer.valueOf(BasicAccessRuleSet.ENDENTITY_VIEW));
        availableendentityrules.add(Integer.valueOf(BasicAccessRuleSet.ENDENTITY_VIEWHISTORY));
        if (usehardtokens) {
            availableendentityrules.add(Integer.valueOf(BasicAccessRuleSet.ENDENTITY_VIEWHARDTOKENS));
        }
        availableendentityrules.add(Integer.valueOf(BasicAccessRuleSet.ENDENTITY_CREATE));
        availableendentityrules.add(Integer.valueOf(BasicAccessRuleSet.ENDENTITY_EDIT));
        availableendentityrules.add(Integer.valueOf(BasicAccessRuleSet.ENDENTITY_DELETE));
        availableendentityrules.add(Integer.valueOf(BasicAccessRuleSet.ENDENTITY_REVOKE));
        availableendentityrules.add(Integer.valueOf(BasicAccessRuleSet.ENDENTITY_APPROVE));
        availableendentityrules.add(Integer.valueOf(BasicAccessRuleSet.ENDENTITY_VIEWPUK));
        if (usekeyrecovery) {
            availableendentityrules.add(Integer.valueOf(BasicAccessRuleSet.ENDENTITY_KEYRECOVER));
        }

        for(String nextrule : availableaccessrules) {          
            if (nextrule.equals(StandardRules.CAACCESSBASE.resource())) {
                this.availablecas.add(Integer.valueOf(BasicAccessRuleSet.CA_ALL));
            } else if (nextrule.startsWith(StandardRules.CAACCESS.resource())) {
                this.availablecas.add(Integer.valueOf(nextrule.substring(StandardRules.CAACCESS.resource().length())));
            } else if (nextrule.equals(AccessRulesConstants.ENDENTITYPROFILEBASE)) {
                this.availableendentityprofiles.add(Integer.valueOf(BasicAccessRuleSet.ENDENTITYPROFILE_ALL));
            } else if (nextrule.startsWith(AccessRulesConstants.ENDENTITYPROFILEPREFIX)) {
                if (nextrule.lastIndexOf('/') <= AccessRulesConstants.ENDENTITYPROFILEPREFIX.length()) {
                    this.availableendentityprofiles.add(Integer.valueOf(nextrule.substring(AccessRulesConstants.ENDENTITYPROFILEPREFIX.length())));
                } else {
                    String tmpString = nextrule.substring(AccessRulesConstants.ENDENTITYPROFILEPREFIX.length());
                    this.availableendentityprofiles.add(Integer.valueOf(tmpString.substring(0, tmpString.indexOf('/'))));
                }
            }
        }

        this.availableotherrules.add(Integer.valueOf(BasicAccessRuleSet.OTHER_VIEWLOG));
        if (usehardtokens) {
            this.availableotherrules.add(Integer.valueOf(BasicAccessRuleSet.OTHER_ISSUEHARDTOKENS));
        }
        availableInternalKeybindingRules.add(InternalKeyBindingRules.DELETE.resource());
        availableInternalKeybindingRules.add(InternalKeyBindingRules.MODIFY.resource());
        availableInternalKeybindingRules.add(InternalKeyBindingRules.VIEW.resource());
    
	    Map<String,String> availableCaMap=new HashMap<String,String>();
	    Map<String, Integer> cas =getAllCANames();
        for (String caName : cas.keySet()) {
            Integer caId = cas.get(caName);
            if (availablecas.contains(caId)) {
                 availableCaMap.put(caName, caId.toString());
            }
        }
        if (authorizationsession.isAuthorizedNoLogging(authenticationToken, StandardRules.CAACCESSBASE.resource())) {
            availableCaMap.put(WebLanguages.getInstance().getText("ALL"),String.valueOf(BasicAccessRuleSet.CA_ALL));
        }

        view.addObject("availableCaMap", availableCaMap);
        
	    Map<String,String> availableendentityrulesMap=new HashMap<String,String>();
         for (Integer currentRule :availableendentityrules) {
             availableendentityrulesMap.put(WebLanguages.getInstance().getText(BasicAccessRuleSet.getEndEntityRuleText(currentRule)), String.valueOf(currentRule));
        }
        view.addObject("availableendentityrulesMap", availableendentityrulesMap);
        
	    Map<String,String> availableendentityprofilesMap=new HashMap<String,String>();
        for (Integer currentProfile :availableendentityprofiles) {
            if (currentProfile == BasicAccessRuleSet.ENDENTITYPROFILE_ALL) {
            	availableendentityprofilesMap.put(WebLanguages.getInstance().getText("ALL"), String.valueOf(currentProfile));
             } else {
            	availableendentityprofilesMap.put(endEntityProfileSession.getEndEntityProfileName(currentProfile),String.valueOf(currentProfile ));
             }
        }
        view.addObject("availableendentityprofilesMap", availableendentityprofilesMap);

	    Map<String,String> availableInternalKeybindingRulesMap=new HashMap<String,String>();
         for(String rule :availableInternalKeybindingRules) {
      	    availableInternalKeybindingRulesMap.put(WebLanguages.getInstance().getText(InternalKeyBindingRules.getFromResource(rule).getReference()), rule);
          }
         view.addObject("availableInternalKeybindingRulesMap", availableInternalKeybindingRulesMap);
 	    
         Map<String,String> availableotherrulesMap=new HashMap<String,String>();
         for (Integer currentRule :availableotherrules) {
              availableotherrulesMap.put(WebLanguages.getInstance().getText(BasicAccessRuleSet.OTHERTEXTS[currentRule]), currentRule.toString());
          }
         view.addObject("availableotherrulesMap", availableotherrulesMap);
         view.addObject("customName", DefaultRoles.CUSTOM.getName());
         view.addObject("superadministrator", DefaultRoles.SUPERADMINISTRATOR.getName());
         view.addObject("caadministrator", DefaultRoles.CAADMINISTRATOR.getName());
         view.addObject("other_viewlog", BasicAccessRuleSet.OTHER_VIEWLOG);
         view.addObject("raadministrator", DefaultRoles.RAADMINISTRATOR.getName());
         view.addObject("supervisor",DefaultRoles.SUPERVISOR.getName());
         view.addObject("endentity_view", BasicAccessRuleSet.ENDENTITY_VIEW);
         view.addObject("endentity_viewhistory", BasicAccessRuleSet.ENDENTITY_VIEWHISTORY);
         view.addObject("endentity_viewhardtokens", BasicAccessRuleSet.ENDENTITY_VIEWHARDTOKENS);
         view.addObject("endentity_create", BasicAccessRuleSet.ENDENTITY_CREATE);
         view.addObject("endentity_edit", BasicAccessRuleSet.ENDENTITY_EDIT);
         view.addObject("endentity_delete", BasicAccessRuleSet.ENDENTITY_DELETE);
         view.addObject("auditor", DefaultRoles.AUDITOR.getName());
         view.addObject("resource", InternalKeyBindingRules.VIEW.resource());
         view.addObject("roleName", roleName);

         view.addObject("endentity_revoke", BasicAccessRuleSet.ENDENTITY_REVOKE);    
         view.addObject("selectanotherrole",WebLanguages.getInstance().getText("SELECTANOTHERROLE"));         
 	}
    public TreeMap<String, Integer> getAllCANames() {
        allcanames = new TreeMap<String, Integer>();
        HashMap<Integer, String> idtonamemap = casession.getCAIdToNameMap();
        for (Integer id : idtonamemap.keySet()) {
            allcanames.put(idtonamemap.get(id), id);
        }
        return allcanames;
    }




	@Override
	public Result saveAccessRules(HttpServletRequest request, String roleName,String roleTemplate,EditBasicAccessRulesFrom editBasicAccessRulesFrom) {
		 
        BasicAccessRuleSetDecoder barsd = new BasicAccessRuleSetDecoder(roleTemplate, editBasicAccessRulesFrom.getCurrentCAs(),   editBasicAccessRulesFrom.getCurrentEndEntityRules(),
        		  editBasicAccessRulesFrom.getCurrentEndEntityProfiles(), editBasicAccessRulesFrom.getCurrentInternalKeybindingRules(), editBasicAccessRulesFrom.getCurrentOtherRules());
		Result  result=new Result();
		AuthenticationToken authenticationToken =getAuthenticationToken(request);
         if(roleTemplate.equals(DefaultRoles.CUSTOM.getName())) {
            result.setMsg("Attempting to add rules to a rule using the Custom role template from basic mode is invalid");
            return result;
        }
        
        try {
            //Using a map in order to weed out duplicates. 
            Map<Integer, AccessRuleData> rulesToReplaceWith = new HashMap<Integer, AccessRuleData>();
            for (AccessRuleTemplate template : barsd.getCurrentAdvancedRuleSet()) {
                AccessRuleData rule = template.createAccessRuleData(roleName);
                if (!rulesToReplaceWith.containsKey(rule.getPrimaryKey())) {
                    rulesToReplaceWith.put(rule.getPrimaryKey(), rule);
                } else {
                    //Examine if we're trying to submit two rules which aren't the exact same.
                    if (!rule.equals(rulesToReplaceWith.get(rule.getPrimaryKey()))) {
                        throw new IllegalStateException("RolesManagedBean tried to save two overlapping rules (" + rule.getAccessRuleName()
                                + ") with different values.");
                    }
                }
            }
			roleManagementSession.replaceAccessRulesInRole(authenticationToken, roleAccessSessionLocal.findRole(roleName), rulesToReplaceWith.values());
			result.setSuccess(true);
		} catch (AuthorizationDeniedException e) {
			result.setMsg("AuthorizationDenied");
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(e.getMessage());

		}
        return  result;
      }




	@Override
	public Result editadminentities(HttpServletRequest request, String roleName, ModelAndView view) {
 		AuthenticationToken authenticationToken =getAuthenticationToken(request);
          List<String> tokenTypes = new ArrayList<String>(AccessMatchValueReverseLookupRegistry.INSTANCE.getAllTokenTypes());
        Collections.sort(tokenTypes);
        Map<String,String> matchWithMap=new HashMap<String,String>();
        for (String tokenType : tokenTypes) {             
            List<AccessMatchValue> accessMatchValues = new ArrayList<AccessMatchValue>(AccessMatchValueReverseLookupRegistry.INSTANCE
                    .getNameLookupRegistryForTokenType(tokenType).values());
            Set<AccessMatchValue> treeSet = new TreeSet<AccessMatchValue>();
            treeSet.addAll(accessMatchValues);
            for (AccessMatchValue current : treeSet) {
 
            	matchWithMap.put(tokenType+":"+WebLanguages.getInstance().getText(current.name()) ,tokenType+":"+ current.name());
             }
        }
        view.addObject("matchWithMap", matchWithMap);
	    GlobalConfiguration globalconfiguration= (GlobalConfiguration) globalConfigSession.getCachedConfiguration(GlobalConfiguration.GLOBAL_CONFIGURATION_ID);
   	    Set<String> availableaccessrules= getAuthorizedAccessRulesUncategorized(authenticationToken,AccessRulesConstants.CREATE_END_ENTITY,globalconfiguration);
        for(String nextrule : availableaccessrules) {          
            if (nextrule.equals(StandardRules.CAACCESSBASE.resource())) {
                this.availablecas.add(Integer.valueOf(BasicAccessRuleSet.CA_ALL));
            } else if (nextrule.startsWith(StandardRules.CAACCESS.resource())) {
                this.availablecas.add(Integer.valueOf(nextrule.substring(StandardRules.CAACCESS.resource().length())));
            }  
        }

        Map<String,String> availablecasMap=new HashMap<String,String>();
         Map<String, Integer> cas = getAllCANames();
        for (String caName : cas.keySet()) {
            Integer caId = cas.get(caName);
            if (availablecas.contains(caId)) {
                 availablecasMap.put(caName, caId.toString());
            }
        }
        view.addObject("availablecasMap", availablecasMap);
        Map<String,String> accessMatchTypeMap=new HashMap<String,String>();
       for (AccessMatchType current : AccessMatchType.values()) {
            accessMatchTypeMap.put(WebLanguages.getInstance().getText(current.toString()), current.toString());
        }
       view.addObject("accessMatchTypeMap", accessMatchTypeMap);
       
       view.addObject("roleName", roleName);
       
       List<AdminsMatchVo> adminsMatchVoList = new ArrayList<AdminsMatchVo>();
       Collection<AccessUserAspectData> list=roleAccessSessionLocal.findRole(roleName).getAccessUsers().values();
       for(AccessUserAspectData accessUserAspectData:list) {
    	   AdminsMatchVo adminsMatchVo=new AdminsMatchVo();
    	   adminsMatchVo.setIssuingCA(getIssuingCA(accessUserAspectData));
    	   adminsMatchVo.setAdminsMatchWith(getAdminsMatchWith(accessUserAspectData));
    	   adminsMatchVo.setAdminsMatchType(getAdminsTokenType(accessUserAspectData));
    	   adminsMatchVo.setMatchValue(accessUserAspectData.getMatchValue());
    	   adminsMatchVo.setPrimaryKey(accessUserAspectData.getPrimaryKey());
     	   adminsMatchVoList.add(adminsMatchVo);
       }
       view.addObject("adminsMatchVoList", adminsMatchVoList);
		return null;
	}
	
	
	
	 /** @return the name of the CA that has issued the certificate for the admin in the current row of the datatable */
    public String getIssuingCA(AccessUserAspectData accessUserAspectData) {
         String caName = (String) casession.getCAIdToNameMap().get(accessUserAspectData.getCaId());
        if (caName == null) {
            caName = "Unknown CA with hash " + accessUserAspectData.getCaId();
        }
        if (AccessMatchValueReverseLookupRegistry.INSTANCE.getDefaultValueForTokenType(accessUserAspectData.getTokenType()).isIssuedByCa()) {
            return caName;
        } else {
            return "";
        }
    }

    public String getAdminsTokenType(AccessUserAspectData accessUserAspectData) {
          return   WebLanguages.getInstance().getText(accessUserAspectData.getMatchTypeAsType().toString());

    }
    
    /** @return the 'match with'-text for the admin in the current row of the datatable */
    public String getAdminsMatchWith(AccessUserAspectData userAspect) {
        return userAspect.getTokenType() + ":"
                +  WebLanguages.getInstance().getText( AccessMatchValueReverseLookupRegistry.INSTANCE.performReverseLookup(userAspect.getTokenType(), userAspect.getMatchWith()).name());
    }




	@Override
	public Result deleteAdmin(HttpServletRequest request, String roleName, String primaryKeye) {
		  Result  result=new Result();
	 	  AuthenticationToken authenticationToken =getAuthenticationToken(request);
	 	  RoleData roleData= roleAccessSessionLocal.findRole(roleName);
	 	 if (StringUtils.isEmpty(primaryKeye)) {
	        	result.setMsg("ACCESSUSERASPECT_UNKNOWN");
	            return result;
	        }
		  final AccessUserAspectData adminEntity = roleData.getAccessUsers().get(Integer.parseInt(primaryKeye));
	      if (adminEntity==null) {
	        	result.setMsg("ACCESSUSERASPECT_UNKNOWN");
	            return result;
	        }
	        Collection<AccessUserAspectData> adminEntities = new ArrayList<AccessUserAspectData>();
	        adminEntities.add(adminEntity);
	        try {
 					roleManagementSession.removeSubjectsFromRole(authenticationToken, roleData, adminEntities);
				
 	            result.setSuccess(true);
 	        } catch (AuthorizationDeniedException e) {
 	            result.setMsg("AUTHORIZATIONDENIED");
 	            log.error(e);
 	        }catch (RoleNotFoundException e) {
  			   result.setMsg("Role Not Found");
  			    log.error(e);
			}
 		return result;
	}




	@Override
	public Result addAdmin(HttpServletRequest request, String matchWithItems, String matchType, String matchValue,
			String matchCaId, String roleName) {
		 Result  result=new Result();
	 	 AuthenticationToken authenticationToken =getAuthenticationToken(request);
	 	 RoleData roleData= roleAccessSessionLocal.findRole(roleName);
         String[] matchWithMenuItems =matchWithItems.split(":");
        AccessMatchValue matchWith = AccessMatchValueReverseLookupRegistry.INSTANCE.lookupMatchValueFromTokenTypeAndName(matchWithMenuItems[0],
                matchWithMenuItems[1]);
         if (matchValue == null || "".equals(matchValue)) {
            result.setMsg("MATCHVALUEREQUIRED");
            return result;
        }
        int caid = Integer.parseInt(matchCaId);
        AccessUserAspectData adminEntity = new AccessUserAspectData(roleName, caid, matchWith, AccessMatchType.matchFromName(matchType),  matchValue);
        // TODO: Check if adminentity exists and add a nice errormessage instead of being silent
        Collection<AccessUserAspectData> adminEntities = new ArrayList<AccessUserAspectData>();
        adminEntities.add(adminEntity);
        try {
 			roleManagementSession.addSubjectsToRole(authenticationToken, roleData, adminEntities);
 			result.setSuccess(true);
          } catch (AuthorizationDeniedException e) {
             result.setMsg("MATCHVALUEREQUIRED");
             log.error(e);
        } catch (RoleNotFoundException e) {
        	 log.error(e);
 			 result.setMsg("Role Not Found");
		}
         
        return result;
    }




	@Override
	public void editadvancedaccessrules(HttpServletRequest request, String roleName, ModelAndView view) {
         List<AccessRuleVo> accessRuleVoList = new ArrayList<AccessRuleVo>();
	 	 AuthenticationToken authenticationToken =getAuthenticationToken(request);
         RoleData  role =roleAccessSessionLocal.findRole(roleName);
        	AccessRuleDataVo accessRuleDataVo=null;
       	Map<String, List<AccessRuleData>> allRulesViewCache= getAccessRules(role, authenticationToken);
         for(Entry<String, List<AccessRuleData>> entry : allRulesViewCache.entrySet()) {
        	 List<AccessRuleData> list= entry.getValue();
        	 List<AccessRuleDataVo> collection=new ArrayList<AccessRuleDataVo>();
         	 for(AccessRuleData rccessRuleData:list) {
         		accessRuleDataVo=new AccessRuleDataVo();
        		String parsedAccessRule= getParsedAccessRule(rccessRuleData, authenticationToken);
        		accessRuleDataVo.setParsedAccessRule(parsedAccessRule);
        		accessRuleDataVo.setState(rccessRuleData.getState());
        		accessRuleDataVo.setRecursive(rccessRuleData.getRecursive());
        		accessRuleDataVo.setPrimaryKey(rccessRuleData.getPrimaryKey());
          		collection.add(accessRuleDataVo);
         	 }
         	 accessRuleVoList.add(new AccessRuleVo(WebLanguages.getInstance().getText(entry.getKey()), collection));
         }
         view.addObject("accessRuleVoList", accessRuleVoList);
         view.addObject("roleName", roleName);
         
         Map<String,String> accessRuleStatesMap=new HashMap<String,String>();
         accessRuleStatesMap.put(WebLanguages.getInstance().getText(AccessRuleState.RULE_NOTUSED.getName(), true), String.valueOf(AccessRuleState.RULE_NOTUSED.getDatabaseValue()));
         accessRuleStatesMap.put(WebLanguages.getInstance().getText(AccessRuleState.RULE_ACCEPT.getName(), true), String.valueOf(AccessRuleState.RULE_ACCEPT.getDatabaseValue()));
         accessRuleStatesMap.put(WebLanguages.getInstance().getText(AccessRuleState.RULE_DECLINE.getName(), true), String.valueOf(AccessRuleState.RULE_DECLINE.getDatabaseValue()));
         view.addObject("accessRuleStatesMap", accessRuleStatesMap);
         
	
	}

	
	
 
	
    /**
     * @return a parsed version of the accessrule for the current row in the datatable. CAs, End Entity Profiles and UserDataSources are given their
     *         cleartext name.
     */
    public String getParsedAccessRule(AccessRuleData accessRule,AuthenticationToken authenticationToken) {
         String resource = accessRule.getAccessRuleName();
        // Check if it is a profile rule, then replace profile id with profile name.
        Map<Integer, String> profileMap =  endEntityProfileSession.getEndEntityProfileIdToNameMap();
        if (resource.startsWith(AccessRulesConstants.ENDENTITYPROFILEPREFIX)) {
            if (resource.lastIndexOf('/') < AccessRulesConstants.ENDENTITYPROFILEPREFIX.length()) {
                return AccessRulesConstants.ENDENTITYPROFILEPREFIX 
                		+ profileMap.get(Integer.parseInt(resource.substring(AccessRulesConstants.ENDENTITYPROFILEPREFIX.length())));
            } else {
                String tmpString = resource.substring(AccessRulesConstants.ENDENTITYPROFILEPREFIX.length());
                return AccessRulesConstants.ENDENTITYPROFILEPREFIX
                        + profileMap.get(
                                Integer.parseInt(tmpString.substring(0, tmpString.indexOf('/')))) + tmpString.substring(tmpString.indexOf('/'));
            }
        }
        // Check if it is a CA rule, then replace CA id with CA name.
        if (resource.startsWith(StandardRules.CAACCESS.resource())) {
            Map<Integer, String> caIdToNameMap =casession.getCAIdToNameMap();
            if (resource.lastIndexOf('/') < StandardRules.CAACCESS.resource().length()) {
                return StandardRules.CAACCESS.resource() + caIdToNameMap.get(Integer.valueOf(resource.substring(StandardRules.CAACCESS.resource().length())));
            } else {
                return StandardRules.CAACCESS.resource()
                        + caIdToNameMap.get(Integer.valueOf(resource.substring(StandardRules.CAACCESS.resource().length(), resource.lastIndexOf('/'))))
                        + resource.substring(resource.lastIndexOf('/'));
            }
        }
        // Check if it is a User Data Source rule, then replace User Data Source id with User Data Source name.
        if (resource.startsWith(AccessRulesConstants.USERDATASOURCEPREFIX)) {
            if (resource.lastIndexOf('/') < AccessRulesConstants.USERDATASOURCEPREFIX.length()) {
                return AccessRulesConstants.USERDATASOURCEPREFIX
                        +  userDataSourceSessionLocal.getUserDataSourceName(authenticationToken,
                                Integer.parseInt(resource.substring(AccessRulesConstants.USERDATASOURCEPREFIX.length())));
            } else {
                return AccessRulesConstants.USERDATASOURCEPREFIX
                        + userDataSourceSessionLocal.getUserDataSourceName(authenticationToken,
                                Integer.parseInt(resource.substring(AccessRulesConstants.USERDATASOURCEPREFIX.length(), resource.lastIndexOf('/'))))
                        + resource.substring(resource.lastIndexOf('/'));
            }
        }
        // Check if it is a CryptoToken rule, then replace CryptoToken id with CryptoToken name.
        if (resource.startsWith(CryptoTokenRules.BASE.resource() + '/')) {
            final int lastIndexOfSlash = resource.lastIndexOf('/');
            try {
                final Integer cryptoTokenId = Integer.valueOf(resource.substring(lastIndexOfSlash+1));
                // Use local invocation without checking authorization, since we 
                final CryptoTokenInfo cryptoTokenInfo =  cryptoTokenManagementSessionLocal.getCryptoTokenInfo(cryptoTokenId);
                if (cryptoTokenInfo != null) {
                    return resource.substring(0, lastIndexOfSlash+1) + cryptoTokenInfo.getName();
                }
            } catch (NumberFormatException e) {
                // Ignore.. we only want to convert the ones where the last section is a number
            }
            return resource;
        }
        return resource;
    }

	
	
	
	
    /**
     * @return a map containing all available access rules, with unauthorized CAs, End Entity Profiles, Crypto Tokens and Certificate Profiles missing. 
     */

    public Map<String, Set<String>> getRedactedAccessRules(final String endentityAccessRule, GlobalConfiguration globalconfiguration, AuthenticationToken administrator) {
    	 Map<String, Set<String>> redactedAccessRules = complexAccessControlSession.getAllAccessRulesRedactUnauthorizedCas(administrator,
                    globalconfiguration.getEnableEndEntityProfileLimitations(), globalconfiguration.getIssueHardwareTokens(),
                    globalconfiguration.getEnableKeyRecovery(), endEntityProfileSession.getAuthorizedEndEntityProfileIds(administrator, endentityAccessRule),
                    userdatasourcesession.getAuthorizedUserDataSourceIds(administrator, true), EjbcaConfiguration.getCustomAvailableAccessRules());
        
        return redactedAccessRules;
    }
	 
    
    /**
     *  Takes a role and a set of rules, returning map (sorted by category) of all rules, with set states for those rules contained in the role
     * 
     * @param role a Role
     * @param redactedRules a list of all rules, barring unauthorized CAs, CPs, EEPs, CryptoTokens 
     * @return the sought map
     */
    private Map<String, List<AccessRuleData>> getCategorizedRuleSet(RoleData role, Map<String, Set<String>> redactedRules) {
        Map<String, List<AccessRuleData>> result = new LinkedHashMap<String, List<AccessRuleData>>();
        Map<Integer, AccessRuleData> knownRules = role.getAccessRules();
        if (redactedRules != null) {
            for (String category : redactedRules.keySet()) {
                List<AccessRuleData> subset = new ArrayList<AccessRuleData>();
                for (String rule : redactedRules.get(category)) {
                    Integer key = AccessRuleData.generatePrimaryKey(role.getRoleName(), rule);
                    if (!knownRules.containsKey(key)) {
                        // Access rule can not be found, create a new AccessRuleData that we can return
                        subset.add(new AccessRuleData(key.intValue(), rule, AccessRuleState.RULE_NOTUSED, false));
                    } else {
                        subset.add(knownRules.get(key));
                    }
                }
                result.put(category, subset);
            }
        }
        return result;
    }




	@Override
	public void saveAdvancedAccessRules(HttpServletRequest request, String roleName,AccessRuleFrom accessRuleFrom) throws AuthorizationDeniedException, RoleNotFoundException, AccessRuleExistsException {
        log.info("Trying to replace access rules..");
        RoleData  role =roleAccessSessionLocal.findRole(roleName);
	 	AuthenticationToken authenticationToken =getAuthenticationToken(request);
        Collection<AccessRuleData> allRules = new ArrayList<AccessRuleData>();
        Collection<AccessRuleData> toReplace = new ArrayList<AccessRuleData>();
        List<AccessRuleData> toRemove = new ArrayList<AccessRuleData>();
       	Map<String, List<AccessRuleData>> allRulesViewCache= getAccessRules(role, authenticationToken);
       	List<AccessRuleDataVo>   accessRuleDataVoList =accessRuleFrom.getAccessRuleDataList();
        for (Entry<String, List<AccessRuleData>> entry : allRulesViewCache.entrySet()) {
         	for(AccessRuleData accessRuleData:entry.getValue()) {
         	   	for(AccessRuleDataVo accessRuleDataVo:accessRuleDataVoList) {
            		if(accessRuleDataVo.getPrimaryKey()==accessRuleData.getPrimaryKey()) {
             			// set  
             			accessRuleData.setRecursiveBool(accessRuleDataVo.isRecursive());
            			accessRuleData.setState(accessRuleDataVo.getState());
            			allRules.add(accessRuleData);
            		}
            	}
        	}
     
          //  allRules.addAll(entry.getValue());
        }
        // Remove all access rules marked as UNUSED and replace the others
        for (AccessRuleData ar : allRules) {
            if (ar.getInternalState() == AccessRuleState.RULE_NOTUSED) {
                toRemove.add(ar);
            } else {
                toReplace.add(ar);
            }
        }
 			 removeAccessRules(role, toRemove, authenticationToken);
             replaceAccessRules(role, toReplace, authenticationToken);
     }
	
    /**
     * Method to remove an collection of access rules from a role.
     * 
     * @param accessrules a Collection of AccessRuleData containing accesss rules to remove.
     * @throws AuthorizationDeniedException when administrator is't authorized to edit this CA.
     * @throws RoleNotFoundException
     */
    public void removeAccessRules( RoleData role, Collection<AccessRuleData> accessRules,AuthenticationToken administrator) throws AuthorizationDeniedException, RoleNotFoundException {
         Collection<AccessRuleData> rulesToRemove = new ArrayList<AccessRuleData>();
        for(AccessRuleData rule : accessRules) {
            if(role.getAccessRules().containsKey(rule.getPrimaryKey())) {
                rulesToRemove.add(rule);
            }
        }
        roleManagementSession.removeAccessRulesFromRole(administrator, role, rulesToRemove);
     }

    /**
     * Method to replace an collection of access rules in a role.
     * 
     * @param rolename the name of the given role
     * @param accessrules a Collection of String containing accesssrules to replace.
     * @throws AuthorizationDeniedException when administrator is't authorized to edit this CA.
     * @throws RoleNotFoundException if role of given name wasn't found
     * @throws AccessRuleExistsException 
     */
    public void replaceAccessRules( RoleData role, Collection<AccessRuleData> accessRules,AuthenticationToken administrator) throws AuthorizationDeniedException, RoleNotFoundException, AccessRuleExistsException {
        roleManagementSession.replaceAccessRulesInRole(administrator, role, accessRules);
     }

    
    /** @return a cached list of all the available access rules */
    private Map<String, List<AccessRuleData>> getAccessRules( RoleData role,AuthenticationToken authenticationToken) {
     	 GlobalConfiguration globalconfiguration= (GlobalConfiguration) globalConfigSession.getCachedConfiguration(GlobalConfiguration.GLOBAL_CONFIGURATION_ID);
         Map<String, Set<String>> redactedRules = getRedactedAccessRules(AccessRulesConstants.CREATE_END_ENTITY, globalconfiguration, authenticationToken);
       	 Map<String, List<AccessRuleData>>    allRulesViewCache = getCategorizedRuleSet(role, redactedRules); 
         return allRulesViewCache;
    }
}
