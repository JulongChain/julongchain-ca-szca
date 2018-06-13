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

package org.bcia.javachain.ca.szca.ocsp.service;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.bcia.javachain.ca.szca.ocsp.container.OcspSignerContainer;
import org.bcia.javachain.ca.szca.ocsp.object.OcspSigner;
import org.bcia.javachain.ca.szca.ocsp.utils.JDBCUtil;
import org.bcia.javachain.ca.szca.ocsp.utils.ProUtils;
import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.ocsp.BasicOCSPResp;
import org.bouncycastle.cert.ocsp.BasicOCSPRespBuilder;
import org.bouncycastle.cert.ocsp.CertificateStatus;
import org.bouncycastle.cert.ocsp.OCSPException;
import org.bouncycastle.cert.ocsp.OCSPReq;
import org.bouncycastle.cert.ocsp.OCSPRespBuilder;
import org.bouncycastle.cert.ocsp.Req;
import org.bouncycastle.cert.ocsp.RespID;
import org.bouncycastle.cert.ocsp.RevokedStatus;
import org.bouncycastle.cert.ocsp.UnknownStatus;
import org.bouncycastle.cert.ocsp.jcajce.JcaBasicOCSPRespBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.encoders.Hex;
/**
 * OCSP查询服务实现
 * @author zhenght
 *
 */
public class OCSPService {
	private static final Logger log = Logger.getLogger(OCSPService.class);
	private static final String BC = "BC";

	
	public static byte[] checkCertStatus(byte[] ocspreqdata, HttpServletResponse resp) throws Exception{
		byte[] enc = null;
		OCSPReq ocspReq = new OCSPReq(ocspreqdata);
		Req[] requests = ocspReq.getRequestList();
		if (requests == null){
			enc = OCSPParam.getBadResponse(OCSPParam.MALFORMED_REQUEST);
			return enc;
		}
		//获取查询状态的证书的序列号及颁发者公钥HASH
		String issuerKeyHash = Hex.toHexString(requests[0].getCertID().getIssuerKeyHash());
		String sn = String.valueOf(requests[0].getCertID().getSerialNumber());
		log.info("Check Certificate SerialNo:" + sn);
		//查询证书状态
		CertificateStatus cs = getCertificateStatus(sn);
		//获取OCSP响应签名证书
		OcspSigner os = OcspSignerContainer.getInstance().getOcspSigners().get(issuerKeyHash);
		DigestCalculatorProvider digCalcProv = new JcaDigestCalculatorProviderBuilder().setProvider(BC).build();
		BasicOCSPRespBuilder respGen = new JcaBasicOCSPRespBuilder(os.getCert().getPublicKey(), digCalcProv.get(RespID.HASH_SHA1));		
		//如果OCSP请求中包含随机数扩展项，OCSP响应中加入对应扩展项
		addNonceExtention(ocspReq, respGen);
		if(Integer.parseInt(ProUtils.getKeyValue("isCache")) == 1){
			//OCSP响应加入nextUpdate字段，用于缓存
			Date now = new Date();
			Date next = getFrontDay(now, Integer.parseInt(ProUtils.getKeyValue("nextUpdate")));				
			respGen.addResponse(requests[0].getCertID(), cs, now, next);
		}else{
			respGen.addResponse(requests[0].getCertID(), cs);
		}
		
		X509CertificateHolder holder = new X509CertificateHolder(os.getCert().getEncoded());
	    X509CertificateHolder [] chain = new X509CertificateHolder[]{holder};	 
	    //生成OCSP响应
	    BasicOCSPResp ocspResp = respGen.build(new JcaContentSignerBuilder(os.getCert().getSigAlgName()).setProvider(BC).build(os.getPriKey()), chain, new Date());
	    OCSPRespBuilder rGen = new OCSPRespBuilder();
	    
	    enc = rGen.build(OCSPRespBuilder.SUCCESSFUL, ocspResp).getEncoded();
	    addRfc5019CacheHeaders(resp,enc);
	   return enc;
	}
	
	//OCSP响应加入随机数
	public static void addNonceExtention(OCSPReq ocspReq, BasicOCSPRespBuilder respGen){
		Extension extValue = ocspReq.getExtension(OCSPObjectIdentifiers.id_pkix_ocsp_nonce);
		if(extValue != null){
			ExtensionsGenerator extGen = new ExtensionsGenerator();
	        extGen.addExtension(extValue);
			respGen.setResponseExtensions(extGen.generate());
		}
	}
	
	//查询数据库中证书状态
	public static CertificateStatus getCertificateStatus(String serialNum) throws Exception{
		CertificateStatus cs = new UnknownStatus();
		String sql = "SELECT status,revocationDate,revocationReason from certificatedata where serialNumber=?";
		PreparedStatement pstmt = JDBCUtil.getConn().prepareStatement(sql);
		pstmt.setString(1, serialNum);
		ResultSet rs = pstmt.executeQuery();
		rs.last(); 
		int rowCount = rs.getRow(); 
		log.info("check db certificate count:" + rowCount);
		if(rowCount != 1)
			return cs;
		rs.beforeFirst();
		
		while(rs.next()){
			int status = rs.getInt(1);
			log.info("Check certificate status in db. Result:count:" + rowCount + " and status:" + status);
			if(OCSPParam.CERT_STATUS_REVOKED == status){
				Date revocationDate = new Date(rs.getLong(2));
				int revocationReason = rs.getInt(3);
				cs = new RevokedStatus(revocationDate,revocationReason);
			}else
				cs = CertificateStatus.GOOD;
		}
		return cs;
	}
	
	private static Date getFrontDay(Date start, int day){
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		c.add(Calendar.DATE, day);
		return c.getTime();
	}
	
	
	
	//RFC 5019请求头部分
	public static void addRfc5019CacheHeaders(HttpServletResponse response, byte[] ocspResponse)  throws IOException, OCSPException, NoSuchAlgorithmException, NoSuchProviderException {
		if (Integer.parseInt(ProUtils.getKeyValue("isCache")) == 0) {
			return;
		} 
		Date d = new Date();
		long now = d.getTime();
		response.setDateHeader("Date", now);
		response.setDateHeader("Last-Modified", now);	
		response.setDateHeader("Expires", getFrontDay(d, Integer.parseInt(ProUtils.getKeyValue("nextUpdate"))).getTime()); 
		String responseHeader = new String(Hex.encode(MessageDigest.getInstance("SHA-1", BouncyCastleProvider.PROVIDER_NAME).digest(ocspResponse)));
		response.setHeader("ETag", "\"" + responseHeader + "\"");
		response.setHeader("Cache-Control", "max-age=" + Integer.parseInt(ProUtils.getKeyValue("maxAge")) + ",public,no-transform,must-revalidate");
	}
}
