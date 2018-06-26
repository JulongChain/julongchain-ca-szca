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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.cesecore.keys.token.CryptoTokenInfo;
import org.cesecore.keys.token.NullCryptoToken;

public class TokenAndCaActivationGuiInfo {

    private final CryptoTokenInfo cryptoTokenInfo;
    private final List<CaActivationGuiInfo> caActivationGuiInfos = new ArrayList<CaActivationGuiInfo>();
    private final boolean allowedActivation;
    private final boolean allowedDeactivation;
    private boolean cryptoTokenNewState;

    TokenAndCaActivationGuiInfo(CryptoTokenInfo cryptoTokenInfo, boolean allowedActivation, boolean allowedDeactivation) {
        this.cryptoTokenInfo = cryptoTokenInfo;
        this.cryptoTokenNewState = cryptoTokenInfo.isActive();
        this.allowedActivation = allowedActivation;
        this.allowedDeactivation = allowedDeactivation;
    }

    public TokenAndCaActivationGuiInfo(Integer cryptoTokenId) {
        this.cryptoTokenInfo = new CryptoTokenInfo(cryptoTokenId, "CryptoToken id " + cryptoTokenId, false, false, NullCryptoToken.class,
                new Properties());
        this.cryptoTokenNewState = false;
        this.allowedActivation = false;
        this.allowedDeactivation = false;
    }

    public void add(CaActivationGuiInfo caActivationGuiInfo) {
        caActivationGuiInfos.add(caActivationGuiInfo);
    }

    public List<CaActivationGuiInfo> getCas() { return caActivationGuiInfos; }
    
    public int getCryptoTokenId() { return cryptoTokenInfo.getCryptoTokenId(); }
    public String getCryptoTokenName() { return cryptoTokenInfo.getName(); }
    public boolean isExisting() { return !"NullCryptoToken".equals(cryptoTokenInfo.getType()); }
    public boolean isCryptoTokenActive() { return cryptoTokenInfo.isActive(); }
    public boolean isAutoActivated() { return cryptoTokenInfo.isAutoActivation(); }
    public boolean isStateChangeDisabled() { return isAutoActivated() || (isCryptoTokenActive() && !allowedDeactivation) || (!isCryptoTokenActive() && !allowedActivation);}
    public boolean isCryptoTokenNewState() { return cryptoTokenNewState; }
    public void setCryptoTokenNewState(boolean cryptoTokenNewState) { this.cryptoTokenNewState = cryptoTokenNewState; }

}
