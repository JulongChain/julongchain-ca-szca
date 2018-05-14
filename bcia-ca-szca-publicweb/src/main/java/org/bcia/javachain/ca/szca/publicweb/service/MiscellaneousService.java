package org.bcia.javachain.ca.szca.publicweb.service;

import java.security.KeyPair;

import org.bcia.javachain.ca.szca.publicweb.CertInfo;
import org.bouncycastle.asn1.x500.X500Name;

public interface MiscellaneousService {

	
	/**
	 * 生成密钥
	 * @param keyType 密钥类型：RSA1024/RSA2048/RSA4096/EC(SM2)
	 * @return
	 */
	public KeyPair genKeyPair(String keyType);
	
	/**
	 * 获取base64格式私钥
	 * @param kp 密钥对
	 * @return
	 */
	public String getPemPrivateKey(KeyPair kp) ;
	
	/**
	 * 生成CSR
	 * @param kp 			密钥对
	 * @param subjectDn		CSR主题信息
	 * @param keyType		密钥类型
	 * @return
	 */
	public String genCSR(KeyPair kp, X500Name subjectDn, String keyType);
	
	/**
	 * 将SubjectDN各项信息拼凑
	 */
	public X500Name getSubjectDn(String cn, String o, String ou, String l, String s, String c, String email);
	
	/**
	 * 解析CSR
	 * @param csr base64格式CSR
	 * @return
	 */
	public CertInfo readCSR(String csr);
	
	/**
	 * 验证证书和私钥是否匹配
	 * @param privateKey 私钥(base64)
	 * @param cert		   证书(base64)
	 * @return
	 */
	public boolean validateKeyAndCert(String privateKey, String cert);

}
