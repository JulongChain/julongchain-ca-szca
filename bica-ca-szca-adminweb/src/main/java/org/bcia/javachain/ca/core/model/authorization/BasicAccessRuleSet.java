
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

package org.bcia.javachain.ca.core.model.authorization;

import java.io.Serializable;


public class BasicAccessRuleSet implements Serializable {

    private static final long serialVersionUID = 7733775825127915269L;

    public static final int ENDENTITY_VIEW             = 2;
    public static final int ENDENTITY_VIEWHISTORY      = 4;
    public static final int ENDENTITY_VIEWHARDTOKENS   = 8;    
    public static final int ENDENTITY_CREATE           = 16;    
    public static final int ENDENTITY_EDIT             = 32;
    public static final int ENDENTITY_DELETE           = 64;
    public static final int ENDENTITY_REVOKE           = 128;
    public static final int ENDENTITY_KEYRECOVER       = 256;
    public static final int ENDENTITY_APPROVE          = 512;
    public static final int ENDENTITY_VIEWPUK          = 1024;
    
    public static final int ENDENTITYPROFILE_ALL  = 0;
    
    public static final int CA_ALL  = 0;
    
    public static final int OTHER_VIEWLOG = 1;
    public static final int OTHER_ISSUEHARDTOKENS = 2;
    
    public static final String[]  ENDENTITYRULETEXTS =  {"VIEWENDENTITYRULE","VIEWHISTORYRULE","VIEWHARDTOKENRULE","VIEWPUKENDENTITYRULE",
        "CREATEENDENTITYRULE","EDITENDENTITYRULE","DELETEENDENTITYRULE",
		  "REVOKEENDENTITYRULE", "KEYRECOVERENDENTITYRULE",
		  "APPROVEENDENTITYRULE"};
    		
    public static final String[]  OTHERTEXTS = {"","VIEWAUDITLOG","ISSUEHARDTOKENS"};
        
   /**
     * This class should not be able to be instantiated.
     */
    private BasicAccessRuleSet(){}
    
    public static String getEndEntityRuleText(int endentityrule){
    	String returnval = "";
    	
    	switch(endentityrule){
    	   case BasicAccessRuleSet.ENDENTITY_VIEW:
    	   	  returnval = ENDENTITYRULETEXTS[0];
    	   	  break;
    	   case BasicAccessRuleSet.ENDENTITY_VIEWHISTORY:
    	   	  returnval = ENDENTITYRULETEXTS[1];
    	   	  break;
    	   case BasicAccessRuleSet.ENDENTITY_VIEWHARDTOKENS:
    	      returnval = ENDENTITYRULETEXTS[2];
    	      break;
    	   case BasicAccessRuleSet.ENDENTITY_VIEWPUK:
     	      returnval = ENDENTITYRULETEXTS[3];
     	      break;
    	   case BasicAccessRuleSet.ENDENTITY_CREATE:
    	   	  returnval = ENDENTITYRULETEXTS[4];
    	   	  break;
    	   case BasicAccessRuleSet.ENDENTITY_EDIT:
    	   	  returnval = ENDENTITYRULETEXTS[5];
    	   	  break;
    	   case BasicAccessRuleSet.ENDENTITY_DELETE:
    	   	returnval = ENDENTITYRULETEXTS[6];
    	   	break;
    	   case BasicAccessRuleSet.ENDENTITY_REVOKE:
    	   	  returnval = ENDENTITYRULETEXTS[7];
    	   	  break;
    	   case BasicAccessRuleSet.ENDENTITY_KEYRECOVER:
    	   	returnval = ENDENTITYRULETEXTS[8];
    	   	break;
    	   case BasicAccessRuleSet.ENDENTITY_APPROVE:
       	   	returnval = ENDENTITYRULETEXTS[9];
       	   	break;
    	}
    	return returnval;
    }
    
   
}
