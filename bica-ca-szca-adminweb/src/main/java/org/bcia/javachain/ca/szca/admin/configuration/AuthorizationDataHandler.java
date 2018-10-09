

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

package org.bcia.javachain.ca.szca.admin.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authorization.AuthorizationDeniedException;
import org.cesecore.authorization.control.AccessControlSessionLocal;
import org.cesecore.authorization.control.StandardRules;
import org.cesecore.authorization.rules.AccessRuleData;
import org.cesecore.authorization.rules.AccessRuleNotFoundException;
import org.cesecore.authorization.user.AccessUserAspectData;
import org.cesecore.roles.RoleData;
import org.cesecore.roles.RoleExistsException;
import org.cesecore.roles.RoleNotFoundException;
import org.cesecore.roles.access.RoleAccessSession;
import org.cesecore.roles.management.RoleManagementSession;

import org.bcia.javachain.ca.szca.common.bcca.core.model.InternalEjbcaResources;

public class AuthorizationDataHandler implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Internal localization of logs and errors */
    private static final InternalEjbcaResources intres = InternalEjbcaResources.getInstance();

    private AccessControlSessionLocal authorizationsession;
    private RoleAccessSession roleAccessSession;
    private RoleManagementSession roleManagementSession;
    private AuthenticationToken administrator;
    private Collection<RoleData> authorizedRoles;

    /** Creates a new instance of ProfileDataHandler */
    public AuthorizationDataHandler(AuthenticationToken administrator, RoleAccessSession roleAccessSession,
            RoleManagementSession roleManagementSession, AccessControlSessionLocal authorizationsession) {
        this.roleManagementSession = roleManagementSession;
        this.roleAccessSession = roleAccessSession;
        this.authorizationsession = authorizationsession;
        this.administrator = administrator;
    }

    /**
     * Method to check if a admin is authorized to a resource
     * 
     * @param admin information about the administrator to be authorized.
     * @param resource the resource to look up.
     * @return true if authorizes
     * @throws AuthorizationDeniedException when authorization is denied.
     */
    public boolean isAuthorized(AuthenticationToken admin, String... resources) {
        return authorizationsession.isAuthorized(admin, resources);
    }

    /**
     * Method to check if a admin is authorized to a resource without performing any logging.
     * 
     * @param admin information about the administrator to be authorized.
     * @param resource the resource to look up.
     * @return true if authorizes
     * @throws AuthorizationDeniedException when authorization is denied.
     */
    public boolean isAuthorizedNoLog(AuthenticationToken admin, String... resources) {
        return authorizationsession.isAuthorizedNoLogging(admin, resources);
    }

    /**
     * Method to add a new role to the administrator privileges data.
     * 
     * @throws RoleExistsException
     */
    public void addRole(String name) throws AuthorizationDeniedException, RoleExistsException {
        // Authorized to edit administrative privileges
        if (!authorizationsession.isAuthorized(administrator, StandardRules.EDITROLES.resource())) {
            final String msg = intres.getLocalizedMessage("authorization.notuathorizedtoresource",
                    StandardRules.EDITROLES.resource(), null);
            throw new AuthorizationDeniedException(msg);
        }
        roleManagementSession.create(administrator, name);
         this.authorizedRoles = null;
    }

    /**
     * Method to remove a role.
     * 
     * @throws RoleNotFoundException
     */
    public void removeRole(String name) throws AuthorizationDeniedException, RoleNotFoundException {
        roleManagementSession.remove(administrator, name);
         this.authorizedRoles = null;
    }

    /**
     * Method to rename a role.
     * 
     * @throws RoleExistsException
     */
    public void renameRole(String oldname, String newname) throws AuthorizationDeniedException, RoleExistsException {
        roleManagementSession.renameRole(administrator, oldname, newname);
         this.authorizedRoles = null;
    }

    /**
     * Method returning a Collection of authorized roles. Only the fields role name and CA id is filled in these objects.
     */
    public Collection<RoleData> getRoles() {
        if (this.authorizedRoles == null) {
            this.authorizedRoles = roleManagementSession.getAllRolesAuthorizedToEdit(administrator);
        }
        return this.authorizedRoles;
    }

    /**
     * @return the given role with it's authorization data
     * 
     */
    public RoleData getRole(String roleName) {
        return roleAccessSession.findRole(roleName);
    }

    /**
     * Method to add a Collection of AccessRule to an role.
     * 
     * @throws AuthorizationDeniedException when administrator is't authorized to edit this CA or when administrator tries to add access rules she
     *             isn't authorized to.
     * @throws RoleNotFoundException
     * @throws AccessRuleNotFoundException
     */
    public void addAccessRules(String roleName, Collection<AccessRuleData> accessRules) throws AuthorizationDeniedException,
            AccessRuleNotFoundException, RoleNotFoundException {
        roleManagementSession.addAccessRulesToRole(administrator, roleAccessSession.findRole(roleName), accessRules);
     }

    /**
     * Method to remove an collection of access rules from a role.
     * 
     * @param accessrules a Collection of AccessRuleData containing accesss rules to remove.
     * @throws AuthorizationDeniedException when administrator is't authorized to edit this CA.
     * @throws RoleNotFoundException
     */
    public void removeAccessRules(String roleName, Collection<AccessRuleData> accessRules) throws AuthorizationDeniedException, RoleNotFoundException {
        RoleData role = roleAccessSession.findRole(roleName);
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
     */
    public void replaceAccessRules(String rolename, Collection<AccessRuleData> accessRules) throws AuthorizationDeniedException, RoleNotFoundException {
        roleManagementSession.replaceAccessRulesInRole(administrator, roleAccessSession.findRole(rolename), accessRules);
     }

//    /**
//     * Method returning all the available access rules authorized to administrator to manage.
//     * 
//     * @returns a map of sets of strings with available access rules, sorted by category
//     */
//    public Map<String, Set<String>> getAvailableAccessRules(final String endentityAccessRule) {
//        return this.informationmemory.getAuthorizedAccessRules(endentityAccessRule);
//    }
//    
//    /**
//     * Method returning all the available access rules authorized to administrator to manage.
//     * 
//     * @returns a map of sets of strings with available access rules, sorted by category
//     */
//    public Map<String, Set<String>> getRedactedAccessRules(final String endentityAccessRule) {
//        return this.informationmemory.getRedactedAccessRules(endentityAccessRule);
//    }
//
//
//    /**
//     * Method returning all the available access rules authorized to administrator to manage.
//     * 
//     * @returns a Collection of strings with available access rules.
//     */
//    public Set<String> getAvailableAccessRulesUncategorized(final String endentityAccessRule) {
//        return this.informationmemory.getAuthorizedAccessRulesUncategorized(endentityAccessRule);
//    }
    
    /**
     * Method to add a Collection of AdminEntity to an role.
     * 
     * @throws AuthorizationDeniedException if administrator isn't authorized to edit CAs administrative privileges.
     * @throws RoleNotFoundException 
     */
    public void addAdminEntities(RoleData role, Collection<AccessUserAspectData> subjects) throws AuthorizationDeniedException, RoleNotFoundException {
        roleManagementSession.addSubjectsToRole(administrator, role, subjects);
     }

    /**
     * Method to remove a Collection of AdminEntity from an role.
     * 
     * @throws AuthorizationDeniedException if administrator isn't authorized to edit CAs administrative privileges.
     * @throws RoleNotFoundException 
     */
    public void removeAdminEntities(RoleData role, Collection<AccessUserAspectData> subjects) throws AuthorizationDeniedException, RoleNotFoundException {
        roleManagementSession.removeSubjectsFromRole(administrator, role, subjects);
     }

}
