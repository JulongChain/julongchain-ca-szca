
package org.bcia.javachain.ca.szca.common.bcca.core.ejb.keybind;

import java.util.ArrayList;
import java.util.List;

import org.cesecore.keybind.InternalKeyBindingMgmtSession;
import org.cesecore.keybind.InternalKeyBindingRules;
import org.springframework.beans.factory.annotation.Autowired;

import org.bcia.javachain.ca.szca.common.cesecore.authorization.rules.AccessRulePlugin;


public class InternalKeybindingRulesReference implements AccessRulePlugin {
 
 @Autowired
 InternalKeyBindingMgmtSession internalKeyBindingMgmtSession;
	
    @Override
    public List<String> getRules() {
        List<String> allRules = new ArrayList<String>();
        for (InternalKeyBindingRules rule : InternalKeyBindingRules.values()) {
            allRules.add(rule.resource());

        }
          
        for (String type : internalKeyBindingMgmtSession.getAvailableTypesAndProperties().keySet()) {
            for (int id : internalKeyBindingMgmtSession.getInternalKeyBindingIds(type)) {
                for (InternalKeyBindingRules rule : InternalKeyBindingRules.values()) {
                    if (rule != InternalKeyBindingRules.BASE) {
                        allRules.add(rule.resource() + "/" + id);
                    }
                }
            }
        }
        return allRules;
    }

    @Override
    public String getCategory() {
        return "INTERNALKEYBINDINGRULES";
    }

}
