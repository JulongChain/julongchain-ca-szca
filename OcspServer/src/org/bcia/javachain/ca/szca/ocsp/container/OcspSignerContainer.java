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

package org.bcia.javachain.ca.szca.ocsp.container;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bcia.javachain.ca.szca.ocsp.object.OcspSigner;
import org.bcia.javachain.ca.szca.ocsp.utils.FileUtils;
import org.bcia.javachain.ca.szca.ocsp.utils.ProUtils;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.x509.extension.X509ExtensionUtil;
/**
 * OCSP签名证书容器
 * @author zhenght
 *
 */
public class OcspSignerContainer {
	private static final Logger log = Logger.getLogger(OcspSignerContainer.class);
	private static OcspSignerContainer container;
	private static Map<String, OcspSigner> ocspSigners;
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	static{
		ocspSigners = new HashMap<String, OcspSigner>();
	}
	private OcspSignerContainer(){}
	
	public static OcspSignerContainer getInstance() throws Exception{
		if(container == null || container.getOcspSigners().size() < 1){
			container = new OcspSignerContainer();
			loadOcspSignCert();
		}
		return container;
	}
	
	/**
	 * 加载OCSP签名证书,通过配置文件中配置ocspSignerPath路径下,每个文件夹为一个证书,里面包含.key和.cer2个文件文件,对应私钥和证书
	 * @throws Exception
	 */
	private static void loadOcspSignCert() throws Exception{
		log.info("loadOcspSignCert begin .............");
		String certPath = ProUtils.getKeyValue("ocspSignerPath");
		File signFile = new File(certPath);
		File[] dirs = signFile.listFiles();
		if(dirs.length < 1) throw new Exception(certPath + "目录下未配置OCSP签名证书,请检查!");
		for(int i=0;i<dirs.length;i++){
			if(dirs[i].isDirectory()){
				File[] files = dirs[i].listFiles();
				if(files.length == 2){
					OcspSigner os =loadSignInfo(files);
					if(os.getCert() != null && os.getPriKey() != null && os.getIssuerFinger() != null){
						if(os.getCert().getNotAfter().after(new Date()))
							ocspSigners.put(os.getIssuerFinger(), os);
						else
							log.info("OCSP签名证书已过期，请检查:" + os.getIssuerDN());
					}
				}
			}
		}
		
		log.info("loadOcspSignCert end. OcspSigner size:" + ocspSigners.size());
		if(ocspSigners.size() < 1)
			throw new Exception(certPath + "目录下未配置OCSP签名证书,请检查!");
	}
	
	//将读取私钥和证书文件，生成OCSP签名证书对象
	private static OcspSigner loadSignInfo(File[] files) throws Exception{
		OcspSigner os =  new OcspSigner();
		for(int i=0;i<files.length;i++){
			if(files[i].getName().indexOf(".key") != -1){
				PrivateKey pk = getPrivateKey(new String(FileUtils.readFile(files[i])));
				os.setPriKey(pk);
			}
			if(files[i].getName().indexOf(".cer") != -1){
				X509Certificate cert = getX509Certificate(new String(FileUtils.readFile(files[i])));
				os.setCert(cert);
				os.setIssuerFinger(getIssuerKeyHash(cert));
				os.setIssuerDN(cert.getIssuerDN().getName());
			}
		}
        return os;
	}
	
	//获取证书的颁发者公钥HASH
	private static String getIssuerKeyHash(X509Certificate cert) throws IOException{
		byte[] extVal = cert.getExtensionValue(Extension.authorityKeyIdentifier.toString());
		AuthorityKeyIdentifier aki = AuthorityKeyIdentifier.getInstance(X509ExtensionUtil.fromExtensionValue(extVal));
	    byte [] keyIdentifier = aki.getKeyIdentifier();
	    return Hex.toHexString(keyIdentifier);
	}
	
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	/*//私钥字符串转对象
	private static PrivateKey getPrivateKey(String privateKey) throws Exception{
		KeyFactory keyf = null;  
		if(privateKey != null && privateKey.indexOf("EC") != -1)
			keyf = KeyFactory.getInstance("EC");  
		else if(privateKey != null && privateKey.indexOf("RSA") != -1)
			keyf = KeyFactory.getInstance("RSA");  
		else{ 
			log.error("私钥格式异常!");
			return null;
		}
    	privateKey = replaceHeadAndEnd(privateKey);
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey) );    
        PrivateKey priKey = keyf.generatePrivate(priPKCS8);  
        return priKey;
    }*/
	//私钥字符串转对象
	private static PrivateKey getPrivateKey(String privateKey) throws Exception{
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
			System.out.println("私钥格式异常!");
			return null;
		}
		privateKey = replaceHeadAndEnd(privateKey);
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey) );  
		PrivateKey priKey = keyf.generatePrivate(priPKCS8);  
		return priKey;
	}
	
	//证书字符串转对象
	private static X509Certificate getX509Certificate(String cert) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
    	CertificateFactory cf = CertificateFactory.getInstance("X.509","BC");
    	X509Certificate x509cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(cert.getBytes()));
    	return x509cert;
    }
	
	private static String replaceHeadAndEnd(String content) throws Exception{
		StringReader in=new StringReader(content);  
		String tmp = "";
		BufferedReader bf = new BufferedReader(in);
		String b;
		while((b= bf.readLine())!= null){
			if(b.indexOf("-----") == -1){
				tmp += b;
			}
		}
		return tmp;
	}
	
	public static Map<String, OcspSigner> getOcspSigners() {
		return ocspSigners;
	}

	public static void setOcspSigners(Map<String, OcspSigner> ocspSigners) {
		OcspSignerContainer.ocspSigners = ocspSigners;
	}

	public static void main(String[] args) throws Exception{
		OcspSignerContainer.getInstance();
	}
}
