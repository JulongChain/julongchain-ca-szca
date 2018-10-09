/**
 *
 * E-MICE SOLUTIONS (GZ) LTD. Copyright c 2003-2013. All rights reserved.
 * 
 * This source code is the property of E-MICE SOLUTIONS (GZ) LTD. It is intended 
 * only for the use of E-MICE application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source 
 * is not permitted without written permission of the E-MICE SOLUTIONS (GZ) LTD.
 * 
 */
package com.szca.common;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

/**
 * Project Name : ROOT<br>
 * Package Name : cn.emice.ihealth.common<br>
 * File Name : CertUtil.java<br>
 * Type Name : CertUtil<br>
 * Created on : 2013-5-16 上午10:58:27<br>
 * Created by : JackyLuo <br>
 * Note:<br>
 * 
 * 
 */
public class CertUtil {

	static Logger logger = LoggerFactory.getLogger(CertUtil.class);

	private static final String pubKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkkd2cLZOfTlQEXMrc9QW4v8ey2hbzzONt5zT9FP4dBIm1r7xQYvgf14HBBVoGQrSoHiNs1L8DgjuSIcBickZVKH7Wwah7vDCh6tOO3AAZg1tb+YeaJjXUT98fOsHavG8bjpCPe22ruJyrc23ARs3Wyp6zumPZJHtBBx5VJ+hgrZ19LTkTtrnfu5dy05PpdiikQE2wcqMYwDtEX7iW0h2ygyoqJZ/XgWtwp7oc7PXzDZ0MFopTOTbZbPPxk5c/twvzay8v6V0M7yOSxlmtumzQWL7GUC+xEMWFx3bDmE12L+yaMwFwbZm8llpVJDi3yNrLaK6dQhMbJY20deaRwQaJQIDAQAB";
	private static final String priKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCSR3Zwtk59OVARcytz1Bbi/x7LaFvPM423nNP0U/h0EibWvvFBi+B/XgcEFWgZCtKgeI2zUvwOCO5IhwGJyRlUoftbBqHu8MKHq047cABmDW1v5h5omNdRP3x86wdq8bxuOkI97bau4nKtzbcBGzdbKnrO6Y9kke0EHHlUn6GCtnX0tORO2ud+7l3LTk+l2KKRATbByoxjAO0RfuJbSHbKDKioln9eBa3Cnuhzs9fMNnQwWilM5Ntls8/GTlz+3C/NrLy/pXQzvI5LGWa26bNBYvsZQL7EQxYXHdsOYTXYv7JozAXBtmbyWWlUkOLfI2storp1CExsljbR15pHBBolAgMBAAECggEAJKJLEk1F+OyKleQvBLIgDTdxYeW/4HN70XkwR+djuMX2/h8R0qWSUrOctDQcPvFL8K5gZoGo6GbqLjXJd9VqR5zNk21kzq9zOID5db9GCkjgmH6cLPviKNHqfmpqECl8VGq4vkOzVbcWm1qCs386bNWFrh4mMDyvjDEJXd0gmNhyDk/4V1AaIlCD8ge3ofzr45d/1OQ5OpAlc3yc+tbKNzLmKAjUN37ud4FbcvaQ3joiXQLrwFLj335YIkqK7WW4VOy6L92DBpUn5j1Cu2TmQIQsePGYIPh5CNywJiykZZX5kTFa6kg3x142sUvXQO16BvFFZ095dgcnvJpQhDX1/QKBgQDSIHukY7XoqJzuOxviQ7ksg5lqGEH14iMDALJzLvdwV3imezzKEeQ10TsXiuby5r6wEgf7wdjGrq7ObeEb7/xi+RgNHaF3BCNEib9crwZwI+ImQ5IjPRhQxFQwRSv9oDiGh/Clfvuif+KvqOAX4TB2a8U9Psb5fftUGoPqFgB6UwKBgQCyNqyJEN4hKRlT8oc4T1nAIckC62KHRJ+2McU8kXAgIvudzxh52cSyIUbSOuufpaypyatmEHUQW3WfzhU8EWATydlQFvA2lTaElR1xmlZgXpWjc5bKB9YPr8Cm041d/n/6hoybhVQOvnRShGR3YMNCjPBHUFn8q66+qKIkKly6pwKBgQCrijUmCPiyjSRAICUtXFhJjM2rnU4L66blhqYn/kmmfuJSFv2TgXOqs7roB09vMQyvNKkr0B6mZGj0wSMq6YpDSlNmMbBlNw2LoGCGy6QX3FN9JWCiP/gfx4drOK0nCqvauBOvJ7FKAdkPym/XTtF/e4+z5hIOD9kj2dmjOn79XQKBgAopdfMm5BOt/US9Kpq6Y5rCEm05MNgXwKep9N48SNF8TS61dWbVVRL3NMueEnt6/fp8hmp+e75gCLgBD1Lqhrj6JIsdk7r5DS/INPKaY1yBaDT2zV1dzwX79pdMrt3yOif17iScGKvyjj16USRBf0DXOohGe6sduwwiH4TCaaE3AoGBAMAbQBn98gA+HDTmmikOO4Dob/pLGOEJlRpRi7rkdWxNqtyrSyoK5KbBsFdiuaX5UPsbYzjN6gN9maBbNHQ5i+G+IscvLHG7x78veLxtl7MgBhxzboaJcGvAj2frc4qTKXf6XpobMV6grliTSw14RwVo7RdQ6yWQfsARCg+s9/On";

	public final static String ENCODE_TYPE = "UTF-8";
	private static Base64 base64 = new Base64();
	// private static X509Certificate certificate=null;
	private static PublicKey publicKey = null;
	private static PrivateKey privateKey = null;
	public final static String SIGN_ALG_NAME = "SHA256withRSA";//"SHA1withRSA";
	private static boolean isInit = false;

	public static void init() {
		if (!isInit) {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			try {
				publicKey = CertUtil.getPubilcKey(pubKeyStr);
				privateKey = CertUtil.getPrivateKey(priKeyStr);
			} catch (Exception e) {
				logger.error("Init cert fail:" + e.getMessage());
				e.printStackTrace();
			}
			isInit = true;
		}
	}

	/**
	 * Package Name : com.yima.yuming.common<br>
	 * File Name : CertUtil.java<br>
	 * Type Name : CertUtil<br>
	 * Method Name : CertUtil <br>
	 * Modified By : @author Jacky Luo<br>
	 * Modified On : 2012-12-27 下午3:27:44<br>
	 * <br>
	 * Note:<br>
	 * 
	 */
	private void CertUtil() {
	}

	public static void main(String[] args) throws Exception {
		System.out.println(CertUtil.genUserLoginPassword("a16029", "1"));

	}

	public static void main2(String[] args) throws Exception {

		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		String p12 = "C:\\rootca\\test01.p12";
		String pwd = "12345678", signAlgName = null;
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream fis = new FileInputStream(p12);
		keyStore.load(fis, pwd.toCharArray());
		fis.close();
		X509Certificate certificate = null;
		// privateKey = null;
		// publicKey
		Enumeration<String> aliasEmum = keyStore.aliases();
		while (aliasEmum.hasMoreElements()) {
			String alias = aliasEmum.nextElement();
			privateKey = (PrivateKey) keyStore.getKey(alias, pwd.toCharArray());
			certificate = (X509Certificate) keyStore.getCertificate(alias);
			signAlgName = certificate.getSigAlgName();
			System.out.println("signAlgName=" + signAlgName);
			publicKey = certificate.getPublicKey();
		}
		KeyPair keyPair = new KeyPair(certificate.getPublicKey(), privateKey);
		publicKey = certificate.getPublicKey();
		Base64 base64 = new Base64();
		System.out.println("certificate:");
		System.out.println(new String(base64.encode(certificate.getEncoded())));
		System.out.println("publicKey:");
		System.out.println(new String(base64.encode(publicKey.getEncoded())));
		System.out.println("privateKey:");
		System.out.println(new String(base64.encode(privateKey.getEncoded())));

		String src = "a36226|1|44";
		String signed = CertUtil.signPkcs1(src);
		System.out.println("signed=" + signed);

		boolean v = CertUtil.verifyPkcs1(signed, src);
		System.out.println("verifyPkcs1=" + v);

		String s = CertUtil.genPaymentValidCode(12312, "ss");
		System.out.println(s);

	}

	public static String genUserLoginPassword(String userId, String password) {
		String tmp = password + "|" + userId;
		// return md5(tmp);
		// return pki(tmp);
		return md5_32enc(tmp);
	}

	/**
	 * Package Name : com.yima.yuming.common<br>
	 * File Name : CertUtil.java<br>
	 * Type Name : CertUtil<br>
	 * Method Name : md5 <br>
	 * Modified By : @author JackyLuo<br>
	 * Modified On : 2013-3-22 下午5:49:58<br>
	 * 
	 * @param str
	 * @return<br> Note:<br>
	 *             MD5加密Base64
	 */
	private static String md5(String str) {
		byte[] pb = null;
		byte[] binaryData = null;
		String pwd;
		try {
			binaryData = (str).getBytes(ENCODE_TYPE);
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(binaryData);
			pb = md.digest();
		} catch (Exception e) {
			e.printStackTrace();
			binaryData = (str).getBytes();
			pb = Base64.encodeBase64(binaryData);
		} finally {
			pwd = new String(Base64.encodeBase64(pb));
		}
		return pwd;
	}

	/**
	 * Package Name : com.yuming.service.cn22<br>
	 * File Name : Cn22Param.java<br>
	 * Type Name : Cn22Param<br>
	 * Method Name : md32 <br>
	 * Modified By : @author vm<br>
	 * Modified On : 2013-3-20 下午3:51:34<br>
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 * <br>
	 *             Note:<br>
	 *             MD5的32位加密（md5加密结果的16进制表示）
	 */
	private static String md5_32enc(String src) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			byte[] b = md.digest(src.getBytes(ENCODE_TYPE));
			// byte[] b = md.digest(s.getBytes());
			StringBuffer buf = new StringBuffer("");
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			String re_md5 = buf.toString();
			// System.out.println(src+"="+re_md5);
			return re_md5;
		} catch (Exception e) {
			String b64 = new String(Base64.encodeBase64(src.getBytes()));
			return b64;
		}
	}

	public static String generalValidCode(String str, int length) {
		String s = md5_32enc(str);
		return s.substring(0, length);
	}

	public static String genDomainTransferValidCode(long transferId, String userId, String dn) {
		String tmp = userId + "|" + dn + "|" + transferId + "|" + System.currentTimeMillis();
		// String s = md5(tmp);
		// return base64ValidCode(s,24);
		// return s.substring(0, 24);

		String s = md5_32enc(tmp);
		return s;
	}

	public static String genPaymentValidCode(long paymentId, String userId) {
		String tmp = userId + "|" + paymentId + "|" + System.currentTimeMillis();
		// String s = md5(tmp);
		// return base64ValidCode(s,8);
		String s = md5_32enc(tmp);
		long l = Long.parseLong(s.substring(0, 8), 16);
		s = Long.toString(l, 10);
		return s.substring(0, 6);

	}

	protected static String base64ValidCode(String md5, int length) {
		String b64 = new String(Base64.encodeBase64(md5.getBytes()));
		if (length <= 0 || b64.length() <= length)
			return b64;
		else
			return b64.substring(0, length);

	}

	private static PublicKey getPubilcKey(String keyData) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		// String keyData =
		// "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAhGaktjcNKypj6m2HwI5ZVkJ0ijr86EAyWeQeiTHY2c/CqxhDZ/udW+RH+JqP+Gk6XaASgbxIecopUtNxWxDl+ZV/eOE4WsQ9+PUwQje4BUk7XRCyy4P1tGaCLOo980ei6MdMwMykQQbjWRGyLUQpMfSUn9GhdVJSQbdgX8DuB4Mzwx9vhcylZWET36JS0tI9BLFWwhhdhoHf89ldhDf4JetRetOyb/S4N1U2kbEdhkeNNWd2MT2x/NOBBZd9Ox0HGE6H2RMiJbiiCIaPu2iUbg11r26NChGpn7hfpGKAeTmKO3qERmIwdhEsj0M7LyepK93DrKXONFeXN09VS5U1xudZlVN75paH2egCjtsU6QstgpODT2yDFNrQlE/W1k1bEIYZSVUn/NUDKvBDBCytv8cQTgfwVw/kjae3IRra+hvLA6jDqJvYnWX0cR68DNrnneQQeHaStNnOfwO7xQAMXYu4iJ7Os3t0c1xpfEAzbOW3ODyooqzfhf5Gmnn4U1z9QBXFq+J52wPKPBlRu/shDJOc6Z+tjqO+h7eE+bRyyQhP+W83wIejhEqp1otAU+gc1RaR2qI5gYp+yjf0DXM/3DwjJIFy+EPOOB44cqeqWEDxBVq7Y/imq1uZZs4a8qP5I5m4WdFil35fSHmL+N7Ld1DMlyGa7EUrGJ7Rmqa04JsCAwEAAQ==";
		X509EncodedKeySpec spec = new X509EncodedKeySpec(base64.decode(keyData.getBytes()));
		// RSAPublicKeySpec spec = new RSAPublicKeySpec();

		KeyFactory kfac = KeyFactory.getInstance("RSA");
		publicKey = kfac.generatePublic(spec);
		logger.info("Load public key:");
		logger.info(publicKey.toString());
		return publicKey;

	}

	/*
	 * private static X509Certificate getCert(String base64Cert) throws
	 * Exception { X509Certificate cert = null;
	 * 
	 * sun.misc.BASE64Decoder bd = new sun.misc.BASE64Decoder(); byte[] data =
	 * bd.decodeBuffer(base64Cert); ByteArrayInputStream bis = new
	 * ByteArrayInputStream(data);
	 * 
	 * CertificateFactory cf = CertificateFactory.getInstance("X.509"); cert =
	 * (X509Certificate) cf.generateCertificate(bis);
	 * logger.info("Load certificate:"); logger.info(cert); return cert; }
	 */
	/**
	 * Package Name : com.yima.yuming.common<br>
	 * File Name : CertUtil.java<br>
	 * Type Name : CertUtil<br>
	 * Method Name : getPrivateKey <br>
	 * Modified By : @author Jacky Luo<br>
	 * Modified On : 2012-12-27 下午2:47:14<br>
	 * 
	 * @param base64Key
	 * @return
	 * @throws Exception
	 * <br>
	 *             Note:<br>
	 *             get private key from base64 string
	 */
	private static PrivateKey getPrivateKey(String base64Key) throws Exception {

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(base64.decode(base64Key.getBytes()));
		KeyFactory kfac = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = kfac.generatePrivate(spec);
		// System.out.println(privateKey);
		logger.info("Load private key:");
		logger.info(privateKey.toString());
		return privateKey;
	}

	/**
	 * Package Name : com.yima.yuming.common<br>
	 * File Name : CertUtil.java<br>
	 * Type Name : CertUtil<br>
	 * Method Name : signPkcs1 <br>
	 * Modified By : @author Jacky Luo<br>
	 * Modified On : 2012-12-27 下午4:18:46<br>
	 * 
	 * @param srcData
	 * @return
	 * @throws Exception
	 * <br>
	 *             Note:<br>
	 * 
	 */
	public static String signPkcs1(String srcData) throws Exception {

		Signature signature = Signature.getInstance(SIGN_ALG_NAME, "BC");
		signature.initSign(privateKey);
		signature.update(srcData.getBytes(ENCODE_TYPE));
		byte[] signedData = signature.sign();
		String s = new String(base64.encode(signedData));
		logger.info("pkcs1 signed:" + srcData + "\r\n" + s);
		return s;
	}

	// public static ValidateMessage signPkcs1(ValidateMessage message) throws
	// Exception {
	// String src = message.getValidText();
	// String signed = signPkcs1(src);
	// message.setSignedCode(signed);
	// return message;
	//
	// }

	/**
	 * Package Name : com.yima.yuming.common<br>
	 * File Name : CertUtil.java<br>
	 * Type Name : CertUtil<br>
	 * Method Name : verifyPkcs1 <br>
	 * Modified By : @author Jacky Luo<br>
	 * Modified On : 2012-12-27 下午4:18:49<br>
	 * 
	 * @param signedData
	 * @param srcData
	 * @return
	 * @throws Exception
	 * <br>
	 *             Note:<br>
	 * 
	 */
	public static boolean verifyPkcs1(String signedData, String srcData) throws Exception {
		Signature signature = Signature.getInstance(SIGN_ALG_NAME);
		signature.initVerify(publicKey);
		signature.update(srcData.getBytes(ENCODE_TYPE));
		boolean result = signature.verify(base64.decode(signedData.getBytes(ENCODE_TYPE)));
		logger.info("pkcs1 verified:" + srcData + "\r\n" + result);

		return result;
	}
	/*
	 * public static boolean verifyPkcs1(ValidateMessage message) throws
	 * Exception { String signedData = message.getValidText(); String srcData =
	 * message.getSignedCode(); return verifyPkcs1(signedData,srcData); }
	 */
}
