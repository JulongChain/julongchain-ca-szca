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

package org.bcia.javachain.ca.szca.publicweb.api.service;

 
import java.util.List;

import org.bcia.javachain.ca.szca.publicweb.api.ApiAdminUserData;
import org.bcia.javachain.ca.szca.publicweb.api.CallConfigData;
import org.bcia.javachain.ca.szca.publicweb.api.CallLogData;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.certificates.ca.CAData;
import org.cesecore.certificates.endentity.EndEntityInformation;

public interface ISzcaApiService {
	public String getCertificateChain4Base64(String caName, String format) throws Exception ;
	public String getDefaultCaName() ;
	public CAData getCaByName(String caName);
	public CAData getCaByUser(String user);
 
	public List<String> revoke(AuthenticationToken admin,String caName,String serial,String certSubjectDn, int reason,String entityUserName,String  aki) throws Exception;
	public String addEndEntity(AuthenticationToken admin,EndEntityInformation entity) throws Exception;
	public void addEndEntityWithPassword(AuthenticationToken admin,EndEntityInformation entity) throws Exception;
//	public String enroll(String entityId,String passwd) throws Exception;
	public List<CAData> getCaList() throws Exception ;
	public void apiCallLog(CallLogData callLog);
	public String randomPwd(int length) ;
	public CallConfigData getCallConfigByIP(String ip) ;
	public ApiAdminUserData getApiAdminUserData(String adminName) ;
	public List<java.security.cert.Certificate>  findTCert(AuthenticationToken authenticationToken, String caName,int certCount,int validityPeriod,boolean isEncrypt,String attr_names);
}
