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

import org.cesecore.certificates.ca.CAConstants;

public class CaActivationGuiInfo {

    private final int status;
    private final String name;
    private final int caId;
    private boolean monitored;
    private boolean monitoredNewState;
    private boolean newState;
    
     CaActivationGuiInfo(int status, boolean monitored, String name, int caId) {
        this.status = status;
        this.newState = isActive();
        this.monitored = monitored;
        this.monitoredNewState = monitored;
        this.name = name;
        this.caId = caId;
    }

    public boolean isActive() { return status == CAConstants.CA_ACTIVE; }
    public boolean isExpired() { return status == CAConstants.CA_EXPIRED; }
    public boolean isRevoked() { return status == CAConstants.CA_REVOKED; }
    public boolean isExternal() { return status == CAConstants.CA_EXTERNAL; }
    public boolean isWaiting() { return status == CAConstants.CA_WAITING_CERTIFICATE_RESPONSE; }
    public boolean isUnableToChangeState() { return isRevoked() || isExpired() || isExternal() || isWaiting(); }
    public boolean isOffline() { return !isActive() && !isExpired() && !isRevoked(); }

    public boolean isMonitoredNewState() { return monitoredNewState; }
    public void setMonitoredNewState(boolean monitoredNewState) { this.monitoredNewState = monitoredNewState; }
    public boolean isMonitored() { return monitored; }
    public int getStatus() { return status; }
    public String getName() { return name; }
    public int getCaId() { return caId; }
    public boolean isNewState() { return newState; }
    public void setNewState(boolean newState) { this.newState = newState; }

}
