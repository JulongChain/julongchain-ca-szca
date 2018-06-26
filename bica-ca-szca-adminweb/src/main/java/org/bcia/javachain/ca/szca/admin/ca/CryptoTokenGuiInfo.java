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

import org.cesecore.keys.token.CryptoTokenInfo;
import org.cesecore.keys.token.PKCS11CryptoToken;
//import org.ejbca.ui.web.admin.configuration.EjbcaJSFHelper;

public class CryptoTokenGuiInfo {

    private final CryptoTokenInfo cryptoTokenInfo;
    private final String p11LibraryAlias;
    private final boolean allowedActivation;
    private final boolean allowedDeactivation;
    private String authenticationCode;
    private final boolean referenced;
    
    CryptoTokenGuiInfo(CryptoTokenInfo cryptoTokenInfo, String p11LibraryAlias, boolean allowedActivation, boolean allowedDectivation, boolean referenced) {
        this.cryptoTokenInfo = cryptoTokenInfo;
        this.p11LibraryAlias = p11LibraryAlias;
        this.allowedActivation = allowedActivation;
        this.allowedDeactivation = allowedDectivation;
        this.referenced = referenced;
    }
    
    public String getStatusImg() {
        return isActive()?"status-ca-active.png":"status-ca-offline.png" ;
    }
    public String getAutoActivationYesImg() {
        return "status-ca-active.png";
    }
    public Integer getCryptoTokenId() { return cryptoTokenInfo.getCryptoTokenId(); }
    public String getTokenName() { return cryptoTokenInfo.getName(); }
    public boolean isActive() { return cryptoTokenInfo.isActive(); }
    public boolean isAutoActivation() { return cryptoTokenInfo.isAutoActivation(); }
    public String getTokenType() { return cryptoTokenInfo.getType(); }
    
    /**
     * @return A string representing slot:index:label for a P11 slot
     */
    public String getP11Slot() { return cryptoTokenInfo.getP11Slot(); }
    public String getP11SlotLabelType() { return cryptoTokenInfo.getP11SlotLabelType(); }
    public String getP11SlotLabelTypeText() {
        if (!isP11SlotType()) {
            return "";
        }
        //return EjbcaJSFHelper.getBean().getText().get("CRYPTOTOKEN_LABEL_TYPE_" + cryptoTokenInfo.getP11SlotLabelType());
        return  "CRYPTOTOKEN_LABEL_TYPE_" + cryptoTokenInfo.getP11SlotLabelType() ;
    }
    public String getP11LibraryAlias() { return p11LibraryAlias; }
    public String getAuthenticationCode() { return authenticationCode; }
    public void setAuthenticationCode(String authenticationCode) { this.authenticationCode = authenticationCode; }
    public boolean isAllowedActivation() { return allowedActivation; }
    public boolean isAllowedDeactivation() { return allowedDeactivation; }
    public boolean isReferenced() { return referenced; }
    public boolean isP11SlotType() { return PKCS11CryptoToken.class.getSimpleName().equals(cryptoTokenInfo.getType()); }
    //public long getCrtTime() {return cryptoTokenInfo.get}

	public CryptoTokenInfo getCryptoTokenInfo() {
		return cryptoTokenInfo;
	}
    
}
