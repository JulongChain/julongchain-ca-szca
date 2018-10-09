
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

import java.io.Serializable;
import java.security.cert.Certificate;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.bcia.javachain.ca.szca.admin.common.WebLanguages;
import org.cesecore.certificates.ca.CAConstants;
import org.cesecore.certificates.ca.CAInfo;
import org.cesecore.certificates.ca.CVCCAInfo;
import org.cesecore.certificates.ca.X509CAInfo;
import org.cesecore.certificates.ca.catoken.CAToken;
import org.cesecore.util.SimpleTime;
import org.ejbca.core.model.SecConst;
//import org.ejbca.ui.web.admin.configuration.EjbcaWebBean;
import org.ejbca.util.HTMLTools;


public class CAInfoView implements Serializable, Cloneable {

	private static final long serialVersionUID = -5154282635821412670L;
    // Public constants.

   public static final int NAME                    = 0;  
   public static final int SUBJECTDN               = 1;   
   public static final int SUBJECTALTNAME          = 2;
   public static final int CATYPE                  = 3;
   
   private static final int SECTION_CA             = 4;
   
   public static final int EXPIRETIME              = 5;
   public static final int STATUS                  = 6;
   @Deprecated
   public static final int CATOKEN_STATUS          = 7;
   public static final int DESCRIPTION             = 8;
   
   private static final int SECTION_CRL            = 9;
   
   public static final int CRLPERIOD               = 10;
   public static final int CRLISSUEINTERVAL        = 11;
   public static final int CRLOVERLAPTIME          = 12;
   public static final int DELTACRLPERIOD          = 13;
   public static final int CRLPUBLISHERS           = 14;
   
   private static final int SECTION_SERVICE        = 15;
   
   public static final int OCSP                    = 16;
  
    
   /** A info text strings must contain:
    * CANAME, CERT_SUBJECTDN, EXT_ABBR_SUBJECTALTNAME, CATYPE, EXPIRES, STATUS, DESCRIPTION, CRL_CA_CRLPERIOD, CRL_CA_ISSUEINTERVAL, CRL_CA_OVERLAPTIME, CRL_CA_DELTACRLPERIOD
    * It must also have CADATA in position n° 4 (CA data) 
    * It must also have CRLSPECIFICDATA in position n° 9 (CRL Specific Data) 
    * It must also have SERVICES in position n° 15 (Services), if exists 
    */
   public static String[] X509CA_CAINFODATATEXTS = {"CANAME","CERT_SUBJECTDN","EXT_ABBR_SUBJECTALTNAME","CATYPE",
       "CADATA",               /* CA data */
       "EXPIRES","STATUS",/*"CATOKENSTATUS"*/ "","DESCRIPTION",
       "CRLSPECIFICDATA",      /* CRL Specific Data */
       "CRL_CA_CRLPERIOD","CRL_CA_ISSUEINTERVAL","CRL_CA_OVERLAPTIME","CRL_CA_DELTACRLPERIOD","PUBLISHERS",
       "SERVICES",             /* Services */
       "OCSPSERVICE"};

public static String[] CVCCA_CAINFODATATEXTS = {"NAME","CERT_SUBJECTDN","","CATYPE",
      "CADATA",                /* CA data */
      "EXPIRES","STATUS",/*"CATOKENSTATUS"*/ "","DESCRIPTION",
      "CRLSPECIFICDATA",       /* CRL Specific Data */
      "CRL_CA_CRLPERIOD","CRL_CA_ISSUEINTERVAL","CRL_CA_OVERLAPTIME","CRL_CA_DELTACRLPERIOD"};

   private String[] cainfodata = null;
   private String[] cainfodatatexts = null;
   
   private CAInfo          cainfo   = null;
   private WebLanguages webLanguages = null;
   public CAInfoView(CAInfo cainfo,  Map<Integer, String> publishersidtonamemap){
 //   public CAInfoView(CAInfo cainfo, EjbcaWebBean ejbcawebbean, Map<Integer, String> publishersidtonamemap){
      this.cainfo = cainfo;  
      this.webLanguages = WebLanguages.getInstance(); 
      if (cainfo instanceof X509CAInfo) {
        setupGeneralInfo(X509CA_CAINFODATATEXTS, cainfo);

        cainfodata[SUBJECTALTNAME] = HTMLTools.htmlescape(((X509CAInfo) cainfo).getSubjectAltName());

		cainfodata[CRLPUBLISHERS] = "";
        Iterator<Integer> publisherIds = ((X509CAInfo) cainfo).getCRLPublishers().iterator();
        if(publisherIds.hasNext()) {
        	cainfodata[CRLPUBLISHERS] = publishersidtonamemap.get(publisherIds.next()); 
        } else {
        	cainfodata[CRLPUBLISHERS] = webLanguages.getText("NONE");
        }
        
        while(publisherIds.hasNext()) {
			cainfodata[CRLPUBLISHERS] = cainfodata[CRLPUBLISHERS] + ", " + (String) publishersidtonamemap.get(publisherIds.next());
        }
        
		cainfodata[SECTION_SERVICE]          = "&nbsp;"; // Section row
        
      } else if (cainfo instanceof CVCCAInfo) {
          setupGeneralInfo(CVCCA_CAINFODATATEXTS, cainfo);          
      }
   }

   private void setupGeneralInfo(String[] strings, CAInfo cainfo) {
	//private void setupGeneralInfo(String[] strings, CAInfo cainfo, EjbcaWebBean ejbcawebbean) {
		cainfodatatexts = new String[strings.length];
        cainfodata = new String[strings.length];  
        
        for(int i=0; i < strings.length; i++){
          if(strings[i].equals("")) {
              cainfodatatexts[i]="&nbsp;";
          } else {
              cainfodatatexts[i] = WebLanguages.getInstance().getText(strings[i]);
          }
        }
        
        cainfodata[SUBJECTDN]  = HTMLTools.htmlescape(cainfo.getSubjectDN());
        cainfodata[NAME]       = HTMLTools.htmlescape(cainfo.getName());
        int catype = cainfo.getCAType();
        if (catype == CAInfo.CATYPE_CVC) {
            cainfodata[CATYPE]     = webLanguages.getText("CVCCA");        	
        } else {
            cainfodata[CATYPE]     = webLanguages.getText("X509");        	
        }
        cainfodata[SECTION_CA]          = "&nbsp;"; // Section row
        if(cainfo.getExpireTime() == null) {
		  cainfodata[EXPIRETIME] = "";
        } else {
          cainfodata[EXPIRETIME] = webLanguages.formatAsISO8601(cainfo.getExpireTime());
        }
        
        switch(cainfo.getStatus()){
            case CAConstants.CA_ACTIVE :
              cainfodata[STATUS]     = webLanguages.getText("ACTIVE");     
              break;
            case CAConstants.CA_EXPIRED :
              cainfodata[STATUS]     = webLanguages.getText("EXPIRED");
              break;
            case CAConstants.CA_OFFLINE :
              cainfodata[STATUS]     = webLanguages.getText("OFFLINE");
              break;
            case CAConstants.CA_REVOKED :
              cainfodata[STATUS]     = webLanguages.getText("CAREVOKED") + "<br>&nbsp;&nbsp;" + 
            		  webLanguages.getText("REASON") + " : <br>&nbsp;&nbsp;&nbsp;&nbsp;" + 
            		  webLanguages.getText(SecConst.reasontexts[cainfo.getRevocationReason()]) + "<br>&nbsp;&nbsp;" +
            		  webLanguages.getText("CRL_ENTRY_REVOCATIONDATE") + "<br>&nbsp;&nbsp;&nbsp;&nbsp;" + 
            		  webLanguages.formatAsISO8601(cainfo.getRevocationDate());
              break;
            case CAConstants.CA_WAITING_CERTIFICATE_RESPONSE :
              cainfodata[STATUS]     = webLanguages.getText("WAITINGFORCERTRESPONSE");
              break;              
            case CAConstants.CA_EXTERNAL :
                cainfodata[STATUS]     = webLanguages.getText("EXTERNALCA");
                break;              
        } 
        
        cainfodata[DESCRIPTION] = HTMLTools.htmlescape(cainfo.getDescription());
        
		cainfodata[SECTION_CRL]          = "&nbsp;"; // Section row

        cainfodata[CRLPERIOD] = SimpleTime.getInstance(cainfo.getCRLPeriod()).toString(SimpleTime.TYPE_MINUTES);
        cainfodata[CRLISSUEINTERVAL] = SimpleTime.getInstance(cainfo.getCRLIssueInterval()).toString(SimpleTime.TYPE_MINUTES);
        cainfodata[CRLOVERLAPTIME] = SimpleTime.getInstance(cainfo.getCRLOverlapTime()).toString(SimpleTime.TYPE_MINUTES);
        cainfodata[DELTACRLPERIOD] = SimpleTime.getInstance(cainfo.getDeltaCRLPeriod()).toString(SimpleTime.TYPE_MINUTES);
	}

   public String[] getCAInfoData(){ return cainfodata;}
   public String[] getCAInfoDataText(){ return cainfodatatexts;} 

   public CAInfo getCAInfo() { return cainfo;}
   public CAToken getCAToken() { return cainfo.getCAToken(); }
   public Collection<Certificate> getCertificateChain() { return cainfo.getCertificateChain(); }
}
