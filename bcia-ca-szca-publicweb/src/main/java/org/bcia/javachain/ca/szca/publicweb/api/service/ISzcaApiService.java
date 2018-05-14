package org.bcia.javachain.ca.szca.publicweb.api.service;

 
import java.util.List;

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
 
	public List<String> revoke(AuthenticationToken admin,String caName,String serial,int reason,String name,String  aki) throws Exception;
	public String addEndEntity(AuthenticationToken admin,EndEntityInformation entity) throws Exception;
//	public String enroll(String entityId,String passwd) throws Exception;
	public List<CAData> getCaList() throws Exception ;
	public void apiCallLog(CallLogData callLog);
	public String randomPwd(int length) ;
	public CallConfigData getCallConfigByIP(String ip) ;
	public List<java.security.cert.Certificate>  findTCert(AuthenticationToken authenticationToken, String caName,int certCount,int validityPeriod,boolean isEncrypt,String attr_names);
}
