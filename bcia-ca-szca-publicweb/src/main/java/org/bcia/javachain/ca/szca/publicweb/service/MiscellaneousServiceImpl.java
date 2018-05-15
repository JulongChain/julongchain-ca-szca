/*
 * **
 *  *
 *  * Copyright © 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 *  * Copyright © 2018  SZCA. All Rights Reserved.
 *  * <p>
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * <p>
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  * <p>
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 *
 */

package org.bcia.javachain.ca.szca.publicweb.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;

import org.apache.commons.lang.StringUtils;
import org.bcia.javachain.ca.szca.publicweb.CertInfo;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequest;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.cesecore.keys.util.KeyTools;
import org.cesecore.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


@Repository
public class MiscellaneousServiceImpl implements MiscellaneousService{
	Logger logger = LoggerFactory.getLogger(getClass());
	private static final String KEY_TYPE_RSA = "RSA";
	private static final String KEY_TYPE_SM2 = "EC";
	private static final String SIG_ALG_RSA = "SHA256withRSA";
	private static final String SIG_ALG_SM2 = "SM3withSM2";
	
	
	public KeyPair genKeyPair(String keyType){
		try{
			if(keyType != null) {
				KeyPairGenerator g = null;
				if(keyType.indexOf(KEY_TYPE_RSA) != -1) {
					g = KeyPairGenerator.getInstance(KEY_TYPE_RSA, "BC");
					g.initialize(Integer.parseInt(keyType.replaceAll(KEY_TYPE_RSA, "")));
				}else if(keyType.indexOf(KEY_TYPE_SM2) != -1) {
					return KeyTools.genKeys("sm2p256v1", "ECDSA");
				}else
					throw new Exception("无效的密钥类型：" + keyType);
		        return g.generateKeyPair();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getPemPrivateKey(KeyPair kp) {
		StringBuffer sb = new StringBuffer();
		sb.append("-----BEGIN PRIVATE KEY-----\n");
		sb.append(new String(Base64.encode(kp.getPrivate().getEncoded())));
		sb.append("\n-----END PRIVATE KEY-----\n");
	    return sb.toString();
	}
	
	public String genCSR(KeyPair kp, X500Name subjectDn, String keyType){
		try{
			String sigAlg = "";
			if(keyType.indexOf(KEY_TYPE_RSA) != -1) 
				sigAlg = SIG_ALG_RSA;
			else if(keyType.indexOf(KEY_TYPE_SM2) != -1) 
				sigAlg = SIG_ALG_SM2;
			
			if(sigAlg == null || sigAlg.length() < 1)
				throw new Exception("生成CSR签名算法异常:" + sigAlg);
	        PKCS10CertificationRequestBuilder crb = new JcaPKCS10CertificationRequestBuilder(subjectDn, kp.getPublic());
	        JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder(sigAlg);
	        ContentSigner signer = csBuilder.build(kp.getPrivate());
	        PKCS10CertificationRequest p10 =  crb.build(signer);
	        byte[] der = p10.getEncoded();  
	        StringBuffer sb = new StringBuffer();
	        
	        sb.append("-----BEGIN CERTIFICATE REQUEST-----\n");  
	        sb.append(new String(Base64.encode(der)));  
	        sb.append("\n-----END CERTIFICATE REQUEST-----\n");  
		    return sb.toString();
		}catch (Exception e) {
			logger.error("生成CSR异常：" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	} 
	
	public CertInfo readCSR(String csr){
		try{
			csr = replaceHeadAndEnd(csr);
			JcaPKCS10CertificationRequest jcaReq = new JcaPKCS10CertificationRequest(Base64.decode(csr.getBytes())).setProvider("BC");
			if(jcaReq == null) throw new Exception("转换CSR对象异常！");
			if(!jcaReq.isSignatureValid(new JcaContentVerifierProviderBuilder().setProvider("BC").build(jcaReq.getPublicKey())))  throw new Exception("CSR验签异常！");
				
			CertInfo certInfo = new CertInfo();
			certInfo = setSubjectDn(jcaReq.getSubject(), certInfo);
			certInfo = setKeySize(jcaReq.getPublicKey(), certInfo);
			certInfo.setSignAlg(getAlgName(jcaReq.getSignatureAlgorithm().getAlgorithm().getId()));
		    return certInfo;
		}catch (Exception e) {
			logger.error("解析CSR异常：" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	} 
	
	public X500Name getSubjectDn(String cn, String o, String ou, String l, String s, String c, String email){
		StringBuilder sb = new StringBuilder();
		if(!StringUtils.isEmpty(cn))
			sb.append("CN=").append(cn).append(",");
		if(!StringUtils.isEmpty(o))
			sb.append("O=").append(o).append(",");
		if(!StringUtils.isEmpty(ou))
			sb.append("OU=").append(ou).append(",");
		if(!StringUtils.isEmpty(l))
			sb.append("L=").append(l).append(",");
		if(!StringUtils.isEmpty(s))
			sb.append("ST=").append(s).append(",");
		if(!StringUtils.isEmpty(c))
			sb.append("C=").append(c).append(",");
		if(!StringUtils.isEmpty(email))
			sb.append("E=").append(email).append(",");
		if(sb.toString().length() > 0){
			return new X500Name(sb.toString().substring(0,sb.toString().length() - 1));
		}else{
			return null;
		}
	}
	
	private static String replaceHeadAndEnd(String content) throws Exception{
		StringReader in=new StringReader(content);  
		String tmp = "";
		BufferedReader bf = new BufferedReader(in);
		String b;
		while((b= bf.readLine())!= null){
			if(b.indexOf("---") == -1){
				tmp += b;
			}
		}
		return tmp;
	}
	
	
	private CertInfo setKeySize(PublicKey publicKey, CertInfo certInfo) throws Exception{
		if(KEY_TYPE_RSA.equals(publicKey.getAlgorithm())){
			KeyFactory keyFact = KeyFactory.getInstance(publicKey.getAlgorithm());
			RSAPublicKeySpec keySpec = (RSAPublicKeySpec)keyFact.getKeySpec(publicKey, RSAPublicKeySpec.class); 
			BigInteger prime = keySpec.getModulus();
			int len = prime.toString(2).length(); 
			certInfo.setKeySize("RSA " + len);
		}
		if(KEY_TYPE_SM2.equals(publicKey.getAlgorithm()))
			certInfo.setKeySize("sm2p256v1");
		
		return certInfo;
	}
	
	private String getAlgName(String oid) {
		if("1.2.840.113549.1.1.5".equals(oid))
			return "SHA1withRSA";
		else if("1.2.840.113549.1.1.11".equals(oid))
			return "SHA256withRSA";
		else if("1.2.840.113549.1.1.12".equals(oid))
			return "SHA384withRSA";
		else if("1.2.156.10197.1.501".equals(oid))
			return "SM3withSM2";
		else return null;
	}
	
	
	private CertInfo setSubjectDn(X500Name subject, CertInfo certInfo){
		certInfo.setCn(getOIDValue(BCStyle.CN, subject));
		certInfo.setO(getOIDValue(BCStyle.O, subject));
		certInfo.setOu(getOIDValue(BCStyle.OU, subject));
		certInfo.setL(getOIDValue(BCStyle.L, subject));
		certInfo.setS(getOIDValue(BCStyle.ST, subject));
		certInfo.setC(getOIDValue(BCStyle.C, subject));
		return certInfo;
	}
	
	private String getOIDValue(ASN1ObjectIdentifier aoi, X500Name subject){
		if(subject.getRDNs(aoi) != null && subject.getRDNs(aoi).length > 0) {
			RDN r = subject.getRDNs(aoi)[0];
			if(r != null)
				return IETFUtils.valueToString(r.getFirst().getValue());
			else return null;
		}
		else return null;
	}
	
	public boolean validateKeyAndCert(String privateKey, String cert) {
		try {
			String content =  "123";
			String sigAlg = "";
			//获取私钥类型，指定签名算法，只支持RSA与SM2
			PrivateKey pk = getPrivateKey(privateKey);
			if(KEY_TYPE_RSA.equals(pk.getAlgorithm()))
				sigAlg = SIG_ALG_RSA;
			else if(KEY_TYPE_SM2.equals(pk.getAlgorithm()))
				sigAlg = SIG_ALG_SM2;
			else
				throw new Exception("无法获取私钥的类型!");
			//签名
			Signature signature = Signature.getInstance(sigAlg, "BC");  
	        signature.initSign(getPrivateKey(privateKey));  
	        signature.update( content.getBytes());  
	        byte[] signed = signature.sign();  
	        
	        //验签
	        Signature ver = Signature.getInstance(sigAlg, "BC");
	        ver.initVerify(getX509Certificate(cert));
	        ver.update(content.getBytes());
	        boolean flag = ver.verify(signed);
	        return flag;
		}catch (Exception e) {
			logger.error("验证密钥与证书匹配异常：" + e.getMessage());
			return false;
		}
	}
	
	//证书字符串转对象
	private static X509Certificate getX509Certificate(String cert) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
	    CertificateFactory cf = CertificateFactory.getInstance("X.509","BC");
	    X509Certificate x509cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(cert.getBytes()));
	    return x509cert;
	}
	
	//私钥字符串转对象
	private PrivateKey getPrivateKey(String privateKey) throws Exception{
		StringReader in=new StringReader(privateKey);  
		String tmp = "";
		BufferedReader bf = new BufferedReader(in);
		String b = bf.readLine();
				
		KeyFactory keyf = null;  
		if(b!= null && b.indexOf("---") != -1 && b.indexOf("EC") != -1) {
			keyf = KeyFactory.getInstance("EC");  
		}else if(b!= null && b.indexOf("---") != -1 && b.indexOf("RSA") != -1) {
			keyf = KeyFactory.getInstance("RSA");  
		}else{ 
			throw new Exception("私钥格式异常!");
		}
		privateKey = replaceHeadAndEnd(privateKey);
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey.getBytes()) );  
		PrivateKey priKey = keyf.generatePrivate(priPKCS8);  
		return priKey;
	}
}
