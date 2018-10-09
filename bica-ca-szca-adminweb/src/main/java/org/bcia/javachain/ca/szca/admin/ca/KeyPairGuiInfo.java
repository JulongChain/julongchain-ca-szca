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

import org.cesecore.certificates.util.AlgorithmConstants;
import org.cesecore.certificates.util.AlgorithmTools;
import org.cesecore.keys.token.CryptoToken;
import org.cesecore.keys.token.KeyPairInfo;
import org.cesecore.keys.util.KeyTools;

public class KeyPairGuiInfo {

    private final String alias;
    private final String keyAlgorithm;
    private final String keySpecification; // to be displayed in GUI
    private final String rawKeySpec; // to be used for key generation
    private final String subjectKeyID;
    private final boolean placeholder;
    private boolean selected = false;
    
     KeyPairGuiInfo(KeyPairInfo keyPairInfo) {
        alias = keyPairInfo.getAlias();
        keyAlgorithm = keyPairInfo.getKeyAlgorithm();
        rawKeySpec = keyPairInfo.getKeySpecification();
        if (AlgorithmConstants.KEYALGORITHM_ECDSA.equals(keyPairInfo.getKeyAlgorithm())) {
            keySpecification = getEcKeySpecAliases(rawKeySpec);
        } else {
            keySpecification = rawKeySpec;
        }
        subjectKeyID = keyPairInfo.getSubjectKeyID();
        placeholder = false;
    }
    
    /**
     * Creates a placeholder with a template string, in the form of "alias;keyspec".
     * Placeholders are created in CryptoTokens that are imported from Statedump. 
     */
     KeyPairGuiInfo(String templateString) {
        String[] pieces = templateString.split("["+CryptoToken.KEYPLACEHOLDERS_INNER_SEPARATOR+"]");
        alias = pieces[0];
        keyAlgorithm = KeyTools.keyspecToKeyalg(pieces[1]);
        rawKeySpec = KeyTools.shortenKeySpec(pieces[1]);
        if (AlgorithmConstants.KEYALGORITHM_ECDSA.equals(keyAlgorithm)) {
            keySpecification = getEcKeySpecAliases(rawKeySpec);
        } else {
            keySpecification = rawKeySpec;
        }
        subjectKeyID = "";
        placeholder = true;
    }
    
    public String getAlias() { return alias; }
    public String getKeyAlgorithm() { return keyAlgorithm; }
    public String getKeySpecification() { return keySpecification; }
    public String getRawKeySpec() { return rawKeySpec; }
    public String getSubjectKeyID() { return subjectKeyID; }
    public boolean isPlaceholder() { return placeholder; }
    
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }
    private String getEcKeySpecAliases(final String ecKeySpec) {
        StringBuilder ret = new StringBuilder();
        for (final String alias : AlgorithmTools.getEcKeySpecAliases(ecKeySpec)) {
            if (ret.length()!=0) {
                ret.append(" / ");
            }
            ret.append(alias);
        }
        return ret.toString();
    }
}
