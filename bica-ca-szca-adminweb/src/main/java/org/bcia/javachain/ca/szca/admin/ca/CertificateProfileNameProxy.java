
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

package org.bcia.javachain.ca.szca.admin.ca;


import java.util.HashMap;

import org.cesecore.certificates.certificateprofile.CertificateProfileSession;


public class CertificateProfileNameProxy implements java.io.Serializable {
    
    private static final long serialVersionUID = -8362730983248532144L;
    private HashMap<Integer, String> certificateprofilenamestore;
    private CertificateProfileSession certificateProfileSession;

    /** Creates a new instance of ProfileNameProxy */
    public CertificateProfileNameProxy(CertificateProfileSession certificateProfileSession){
      this.certificateProfileSession = certificateProfileSession;
      certificateprofilenamestore = new HashMap<Integer, String>(); 
    }
    
    /**
     * Method that first tries to find certificateprofile name in local hashmap and if it does not exist looks it up over RMI.
     *
     * @param certificateprofileid the certificateprofile id number to look up.
     * @return the certificateprofilename or null if no certificateprofilename is relatied to the given id
     */
    public String getCertificateProfileName(int certificateprofileid)  {
      String returnval = null;  
      // Check if name is in hashmap
      returnval = (String) certificateprofilenamestore.get(Integer.valueOf(certificateprofileid));
      
      if(returnval==null){
        // Retreive profilename 
        returnval = certificateProfileSession.getCertificateProfileName(certificateprofileid);
        if(returnval != null) {
          certificateprofilenamestore.put(Integer.valueOf(certificateprofileid),returnval);
        }
      }    
      return returnval;
    }
}
