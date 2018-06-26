
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

package org.bcia.javachain.ca.szca.admin.ca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authorization.AuthorizationDeniedException;
import org.cesecore.authorization.control.CryptoTokenRules;
import org.cesecore.certificates.ca.CAConstants;
import org.cesecore.certificates.ca.CADoesntExistsException;
import org.cesecore.certificates.ca.CAInfo;
import org.cesecore.keys.token.CryptoTokenAuthenticationFailedException;
import org.cesecore.keys.token.CryptoTokenInfo;
import org.cesecore.keys.token.CryptoTokenOfflineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.bcia.javachain.ca.szca.common.bcca.core.ejb.ca.caadmin.CAAdminSession;
import org.bcia.javachain.ca.szca.common.cesecore.certificates.ca.CaSessionLocal;
import org.bcia.javachain.ca.szca.common.cesecore.keys.token.CryptoTokenManagementSessionLocal;
 

@Repository
public class CaFunctionBean implements Serializable {

    /** Backing object for main page list of CA and CRL statuses. */
    public class CaCrlStatusInfo {
        final private String caName;
        final private boolean caService;
        final private boolean crlStatus;
        private CaCrlStatusInfo(final String caName, final boolean caService, final boolean crlStatus) {
            this.caName = caName;
            this.caService = caService;
            this.crlStatus = crlStatus;
        }
        public String getCaName() { return caName; }
        public boolean isCaService() { return caService; }
        public boolean isCrlStatus() { return crlStatus; }
    }

	private static final long serialVersionUID = 3L;
	private static final Logger log = Logger.getLogger(CaFunctionBean.class);
	
    public static final int CATOKENTYPE_P12          = 1;
    public static final int CATOKENTYPE_HSM          = 2;
	public static final int CATOKENTYPE_NULL         = 3;
 
	@Autowired
	CaSessionLocal  caSession;
  
	@Autowired
    private org.bcia.javachain.ca.szca.common.cesecore.certificates.crl.CrlStoreSession crlStoreSession;
//	@Autowired
//    private PublishingCrlSessionLocal publishingCrlSession;
 
	//WebAuthenticationProviderSessionLocal authenticationSession = ejbLocalHelper.getWebAuthenticationProviderSession();
	
	@Autowired
	 private  org.bcia.javachain.ca.szca.common.cesecore.authorization.control.AccessControlSessionLocal accessControlSession ;
	
	@Autowired
	 private  CryptoTokenManagementSessionLocal cryptoTokenManagementSession ;
	
	@Autowired
	 private  CAAdminSession caAdminSession ;
	
//	@Autowired
//	WebAuthenticationProviderSessionLocal authenticationSession;
  //  private boolean initialized;
//     private AuthenticationToken authenticationToken;
  
	
 
	/** Creates a new instance of CaInterfaceBean */
    public CaFunctionBean() { }
    

    public List<TokenAndCaActivationGuiComboInfo> getAuthorizedTokensAndCas(AuthenticationToken authenticationToken) {
    	//List<TokenAndCaActivationGuiComboInfo> authorizedTokensAndCas = null;
        final Map<Integer,TokenAndCaActivationGuiInfo> sortMap = new HashMap<Integer,TokenAndCaActivationGuiInfo>();
        for (final CAInfo caInfo : caSession.getAuthorizedAndEnabledCaInfos(authenticationToken)) {
                final Integer cryptoTokenId = Integer.valueOf(caInfo.getCAToken().getCryptoTokenId());
                if (sortMap.get(cryptoTokenId)==null) {
                    // Perhaps not authorized to view the CryptoToken used by the CA, but we implicitly
                    // allow this in the current context since we are authorized to the CA.
                    final CryptoTokenInfo cryptoTokenInfo = cryptoTokenManagementSession.getCryptoTokenInfo(cryptoTokenId.intValue());
                    if (cryptoTokenInfo==null) {
                        sortMap.put(cryptoTokenId, new TokenAndCaActivationGuiInfo(cryptoTokenId));
                    } else {
                        final boolean allowedActivation = accessControlSession.isAuthorizedNoLogging(authenticationToken, CryptoTokenRules.ACTIVATE.resource() + '/' + cryptoTokenId);
                        final boolean allowedDeactivation = accessControlSession.isAuthorizedNoLogging(authenticationToken, CryptoTokenRules.DEACTIVATE.resource() + '/' + cryptoTokenId);
                        sortMap.put(cryptoTokenId, new TokenAndCaActivationGuiInfo(cryptoTokenInfo, allowedActivation, allowedDeactivation));
                    }
                }
                sortMap.get(cryptoTokenId).add(new CaActivationGuiInfo(caInfo.getStatus(), caInfo.getIncludeInHealthCheck(), caInfo.getName(), caInfo.getCAId()));
        }
        final TokenAndCaActivationGuiInfo[] tokenAndCasArray = sortMap.values().toArray(new TokenAndCaActivationGuiInfo[0]);
        // Sort array by CryptoToken name
        Arrays.sort(tokenAndCasArray, new Comparator<TokenAndCaActivationGuiInfo>() {
            @Override
            public int compare(TokenAndCaActivationGuiInfo o1, TokenAndCaActivationGuiInfo o2) {
                return o1.getCryptoTokenName().compareToIgnoreCase(o2.getCryptoTokenName());
            }
        });
        final List<TokenAndCaActivationGuiComboInfo> retValues = new ArrayList<TokenAndCaActivationGuiComboInfo>();
        for (final TokenAndCaActivationGuiInfo value : tokenAndCasArray) {
            boolean first = true;
            final CaActivationGuiInfo[] casArray = value.getCas().toArray(new CaActivationGuiInfo[0]);
            // Sort array by CA name
            Arrays.sort(casArray, new Comparator<CaActivationGuiInfo>() {
                @Override
                public int compare(CaActivationGuiInfo o1, CaActivationGuiInfo o2) {
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            });
            for (final CaActivationGuiInfo value2 : casArray) {
                retValues.add(new TokenAndCaActivationGuiComboInfo(value, value2, first));
                first = false;
            }
        }
       // authorizedTokensAndCas = retValues;
        return retValues;
    }
 
    
    @org.springframework.transaction.annotation.Transactional
    public void applyChanges(int caid,String cryptoTokenName ,AuthenticationToken authenticationToken,String authenticationcode,List<TokenAndCaActivationGuiComboInfo> authorizedTokensAndCas  ,boolean caNewState,boolean cryptoTokenNewState,boolean monitoredNewState  ) {
	    if (authorizedTokensAndCas==null) {
	        return;
	    }
	    for (final TokenAndCaActivationGuiComboInfo tokenAndCaCombo : authorizedTokensAndCas) {
	    	
          // if (tokenAndCaCombo.isFirst()) {
                TokenAndCaActivationGuiInfo tokenAndCa = tokenAndCaCombo.getCryptoToken();
                if(tokenAndCaCombo.getCa().getCaId()!=caid)
    	    		continue;
                
                if (log.isDebugEnabled()) {
                    log.debug("isCryptoTokenActive(): " + tokenAndCa.isCryptoTokenActive() + " isCryptoTokenNewState(): " + tokenAndCa.isCryptoTokenNewState());
                }
                //for　test
                tokenAndCa.setCryptoTokenNewState(cryptoTokenNewState);
	           if (tokenAndCa.isCryptoTokenActive() != tokenAndCa.isCryptoTokenNewState()) {
	                if (tokenAndCa.isCryptoTokenNewState()) {
	                    // Assert that authcode is present
	                    if (authenticationcode != null && authenticationcode.length()>0) {
	                        // Activate CA's CryptoToken
	                        try {
	                            cryptoTokenManagementSession.activate(authenticationToken, tokenAndCa.getCryptoTokenId(), authenticationcode.toCharArray());
	                            log.info(authenticationToken.toString() + " activated CryptoToken " + tokenAndCa.getCryptoTokenId());
	                        } catch (CryptoTokenAuthenticationFailedException e) {
	                        	e.printStackTrace();
	        	            	//   super.addNonTranslatedErrorMessage("Bad authentication code.");
	                        } catch (CryptoTokenOfflineException e) {
	                        	e.printStackTrace();
	        	            	//   super.addNonTranslatedErrorMessage("Crypto Token is offline and cannot be activated.");
	                        } catch (AuthorizationDeniedException e) {
	                        	e.printStackTrace();
	        	            	//   super.addNonTranslatedErrorMessage(e.getMessage());
	                        }
	                    } else {
	                    	//super.addNonTranslatedErrorMessage("Authentication code required.");
	                    	System.out.println("==========Authentication code required.");
	                    }
	                } else {
	                    // Deactivate CA's CryptoToken
	                    try {
	                        cryptoTokenManagementSession.deactivate(authenticationToken, tokenAndCa.getCryptoTokenId());
	                        log.info(authenticationToken.toString() + " deactivated CryptoToken " + tokenAndCa.getCryptoTokenId());
	                    } catch (AuthorizationDeniedException e) {
	                    	e.printStackTrace();
	    	            	// super.addNonTranslatedErrorMessage(e.getMessage());
	                    }
	                }
	            }
	        //}
	        CaActivationGuiInfo ca = tokenAndCaCombo.getCa();
	        ca.setNewState(caNewState);
	        if (ca.isActive() != ca.isNewState()) {
	            // Valid transition 1: Currently offline, become active
	            if (ca.isNewState() && ca.getStatus()==CAConstants.CA_OFFLINE) {
	                try {
	                    caAdminSession.activateCAService(authenticationToken, ca.getCaId());
	                } catch (Exception e) {
	                	e.printStackTrace();
		            	//  super.addNonTranslatedErrorMessage(e.getMessage());
	                }
	            } 
	            // Valid transition 2: Currently online, become offline
	            if (!ca.isNewState() && ca.getStatus()==CAConstants.CA_ACTIVE) {
	                try {
	                    caAdminSession.deactivateCAService(authenticationToken, ca.getCaId());
	                } catch (Exception e) {
	                	e.printStackTrace();
		            	//  super.addNonTranslatedErrorMessage(e.getMessage());
	                }
	            }
	        }
	        
	        ca.setMonitoredNewState(monitoredNewState);
	        if (ca.isMonitored() != ca.isMonitoredNewState()) {
	            // Only persist changes if there are any
	            try {
	                final CAInfo caInfo = caSession.getCAInfoInternal(ca.getCaId(), null, false);
	                caInfo.setIncludeInHealthCheck(ca.isMonitoredNewState());
	                caAdminSession.editCA(authenticationToken, caInfo);
	            } catch (CADoesntExistsException e) {
	               e.printStackTrace();
	            	// super.addNonTranslatedErrorMessage(e.getMessage());
	            } catch (AuthorizationDeniedException e) {
	            	e.printStackTrace();
	            	//  super.addNonTranslatedErrorMessage(e.getMessage());
	            }
	        }
	        if (log.isDebugEnabled()) {
	            log.debug("caId: " + ca.getCaId() + " monitored: " + ca.isMonitored() + " newCaStatus: " + ca.isNewState());
	        }
	    }
	}

}
