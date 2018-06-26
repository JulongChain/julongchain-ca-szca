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

package org.bcia.javachain.ca.szca.ocsp.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.bcia.javachain.ca.szca.ocsp.utils.FileUtils;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x509.AccessDescription;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.ocsp.BasicOCSPResp;
import org.bouncycastle.cert.ocsp.CertificateID;
import org.bouncycastle.cert.ocsp.CertificateStatus;
import org.bouncycastle.cert.ocsp.OCSPException;
import org.bouncycastle.cert.ocsp.OCSPReq;
import org.bouncycastle.cert.ocsp.OCSPReqBuilder;
import org.bouncycastle.cert.ocsp.OCSPResp;
import org.bouncycastle.cert.ocsp.RevokedStatus;
import org.bouncycastle.cert.ocsp.SingleResp;
import org.bouncycastle.cert.ocsp.UnknownStatus;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
/**
 * 客户端调用OCSP实现
 * @author zhenght
 *
 */
public class OcspClient {
	private static final Logger log = Logger.getLogger(OcspClient.class);
	public static final int HTTP_TYPE_GET = 1;
	public static final int HTTP_TYPE_POST = 2;
	//public static final String OCSP_URL = "http://127.0.0.1:8080/OcspServer/ocsp";
	public static final String OCSP_URL = "http://127.0.0.1:80/OcspServer/ocsp";
	private static final Charset ASCII = Charset.forName("US-ASCII");
	private static final String ocspUrlOid = "1.3.6.1.5.5.7.48.1";

	/**
	 * 查询证书状态(从证书读取OCSP地址，HTTP请求方式默认为POST)
	 * @param base64UserCert 查询状态的证书
	 * @param base64IssuerCert 查询状态证书的颁发者证书
	 * @return
	 */
	public static SingleOcspResp checkCertStatus(String base64UserCert,String base64IssuerCert) throws Exception {
		return checkCertStatus(base64UserCert, base64IssuerCert, getCertAccessUrl(getX509Certificate(base64UserCert), ocspUrlOid), HTTP_TYPE_POST);
	}
	
	
	/**
	 * 查询证书状态(从证书读取OCSP地址)
	 * @param base64UserCert 查询状态的证书
	 * @param base64IssuerCert 查询状态证书的颁发者证书
	 * @param httpType  HTTP请求类型 1:get 2:post
	 * @return
	 */
	public static SingleOcspResp checkCertStatus(String base64UserCert,String base64IssuerCert, int httpType) throws Exception {
		return checkCertStatus(base64UserCert, base64IssuerCert, getCertAccessUrl(getX509Certificate(base64UserCert), ocspUrlOid), httpType);
	}
	
	/**
	 * 查询证书状态(默认POST方式)
	 * @param base64UserCert 查询状态的证书
	 * @param base64IssuerCert 查询状态证书的颁发者证书
	 * @param ocspUrl  OCSP地址
	 * @return
	 */
	public static SingleOcspResp checkCertStatus(String base64UserCert,String base64IssuerCert, String ocspUrl) throws Exception {
		return checkCertStatus(base64UserCert, base64IssuerCert, ocspUrl, HTTP_TYPE_POST);
	}
	
	/**
	 * 查询证书状态
	 * @param base64UserCert 查询状态的证书
	 * @param base64IssuerCert 查询状态证书的颁发者证书
	 * @param ocspUrl  OCSP地址
	 * @param httpType  HTTP请求类型 1:get 2:post
	 * @return
	 */
	public static SingleOcspResp checkCertStatus(String base64UserCert,String base64IssuerCert, String ocspUrl, int httpType) {
		if(ocspUrl == null || ocspUrl.length() < 1){
			return SingleOcspResp.getInstance(base64UserCert, true, SingleOcspResp.CERT_STATUS_UNKNOWN, null, SingleOcspResp.HTTP_REQ_RESULT_FAIL, "无法获取OCSP地址!");
		}
		if(base64UserCert == null || base64UserCert.length() < 1){
			return SingleOcspResp.getInstance(base64UserCert, true, SingleOcspResp.CERT_STATUS_UNKNOWN, null, SingleOcspResp.HTTP_REQ_RESULT_FAIL, "请输入正确的证书!");
		}
		try {
			X509Certificate userCert = getX509Certificate(base64UserCert);
			if(userCert == null){
				return SingleOcspResp.getInstance(base64UserCert, true, SingleOcspResp.CERT_STATUS_UNKNOWN, null, SingleOcspResp.HTTP_REQ_RESULT_FAIL, "请输入正确的证书!");
			}
			X509Certificate issuerCert = getX509Certificate(base64IssuerCert);
			return checkRevocationStatus(userCert, issuerCert, ocspUrl, httpType);
		} catch (Exception e) {
			e.printStackTrace();
			return SingleOcspResp.getInstance(base64UserCert, true, SingleOcspResp.CERT_STATUS_UNKNOWN, null, SingleOcspResp.HTTP_REQ_RESULT_FAIL, "系统异常：" + e.getMessage());
		}
	}
	
	/**
	 * 证书字符串转对象
	 * @param cert base64证书字符串
	 * @return
	 * @throws Exception
	 */
	private static X509Certificate getX509Certificate(String cert) throws Exception {
    	CertificateFactory cf = CertificateFactory.getInstance("X.509","BC");
        //cf = CertificateFactory.getInstance("X.509");
    	
    	X509Certificate x509cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(cert.getBytes()));
    	return x509cert;
    }
	
	/**
	 * 生成OCSP请求内容
	 * @param issuerCert 查询状态证书的颁发者证书
	 * @param serialNumber 查询状态证书的序列号
	 * @return
	 * @throws Exception
	 */
	private static OCSPReq generateOCSPRequest(X509Certificate issuerCert, BigInteger serialNumber) throws Exception {

		try {
			CertificateID id = new CertificateID(
					(new BcDigestCalculatorProvider())
							.get(CertificateID.HASH_SHA1),
					new X509CertificateHolder(issuerCert.getEncoded()),
					serialNumber);

			OCSPReqBuilder generator = new OCSPReqBuilder();
			generator.addRequest(id);

			return generator.build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new OCSPException("生成OCSP请求异常", e);
		}
	}
	/**
	 * 获取Http Post OCSP响应
	 * @param serviceUrl OCSP地址
	 * @param request OCSP请求内容
	 * @return
	 * @throws Exception
	 */
	private static OCSPResp getPostOCSPResponce(String serviceUrl, OCSPReq request)
			throws Exception {

		try {
			byte[] array = request.getEncoded();

			if (serviceUrl.startsWith("http")) {
				byte[] respBytes = null;
				CloseableHttpClient httpclient = HttpClients.createDefault();
				HttpPost httpPost = new HttpPost(serviceUrl);
				ByteArrayEntity byteArrayEntity = new ByteArrayEntity(array,
						ContentType.create("application/ocsp-request"));
				httpPost.setEntity(byteArrayEntity);
				CloseableHttpResponse response = httpclient.execute(httpPost);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream instream = entity.getContent();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int bytesRead = 0;
					while ((bytesRead = instream.read(buffer, 0, buffer.length)) >= 0) {
						baos.write(buffer, 0, bytesRead);
					}
					instream.close();
					respBytes = baos.toByteArray();
				} else {
					throw new Exception("获取OCSP响应失败: "+ serviceUrl);
				}

				OCSPResp ocspResponse = new OCSPResp(respBytes);
				return ocspResponse;
			} else {
				throw new Exception("OCSP请求只支持http方式!");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("获取OCSP响应失败: " + serviceUrl, e);
		}
	}
	
	/**
	 * 获取Http Get方式OCSP响应
	 * @param serviceUrl OCSP地址
	 * @param request OCSP请求内容
	 * @return
	 * @throws Exception
	 */
	private static OCSPResp getGetOCSPResponce(String serviceUrl, OCSPReq request)throws Exception {
		String endUrl = null;
		try {
			byte[] array = request.getEncoded();
			endUrl = parseHttpGetUrl(serviceUrl, array);
			if (serviceUrl.startsWith("http")) {
				byte[] respBytes = null;
				CloseableHttpClient httpclient = HttpClients.createDefault();
				System.out.println("GET URL:\r\n"+endUrl);
				HttpGet httpGet = new HttpGet(endUrl);
				CloseableHttpResponse response = httpclient.execute(httpGet);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream instream = entity.getContent();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int bytesRead = 0;
					while ((bytesRead = instream.read(buffer, 0, buffer.length)) >= 0) {
						baos.write(buffer, 0, bytesRead);
					}
					instream.close();
					respBytes = baos.toByteArray();
				} else {
					throw new Exception("获取OCSP响应失败: "+ endUrl);
				}
		
				OCSPResp ocspResponse = new OCSPResp(respBytes);
				return ocspResponse;
			} else {
				throw new Exception("OCSP请求只支持http方式!");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("获取OCSP响应失败: " + endUrl, e);
		}
	}
	
	/**
	 * 将OCSP请求转换成get方式请求Url
	 * @param serverUrl OCSP地址
	 * @param request OCSP请求
	 * @return
	 */
	private static String parseHttpGetUrl(String serverUrl, byte[] request){
		byte[] base64 = Base64.encode(request);
        String ret = new String(base64, ASCII);
        try{
        	ret = URLEncoder.encode(ret, ASCII.name());
        } catch (UnsupportedEncodingException e){
        	throw new RuntimeException("US-ASCII转换异常!", e);
        }
        if (serverUrl.endsWith("/"))
            ret = serverUrl + ret;
        else
            ret = serverUrl + "/" + ret;
    
        if (ret.length() > 255)
            return null;
        return ret;
	}
	
	/**
	 * 客户端发起OCSP请求，并解析OCSP响应
	 * @param userCert 查询状态的证书
	 * @param issuerCert 查询状态证书的颁发者证书
	 * @param serviceUrl  OCSP地址
	 * @param httpType	请求类型. 1:get 2:post	
	 * @return
	 */
	private static SingleOcspResp checkRevocationStatus(X509Certificate userCert, X509Certificate issuerCert, String serviceUrl, int httpType) {
		int res = SingleOcspResp.CERT_STATUS_UNKNOWN;
		SingleOcspResp gdcaCertResp = null;
		try {
			gdcaCertResp = SingleOcspResp.getInstance(new String(Base64.encode(userCert.getEncoded())), true, 0, null, 0, null);
			OCSPReq request = generateOCSPRequest(issuerCert, userCert.getSerialNumber());
			OCSPResp ocspResponse = null;
			if(httpType == 1)
				ocspResponse = getGetOCSPResponce(serviceUrl, request);
			if(httpType == 2)
				ocspResponse = getPostOCSPResponce(serviceUrl, request);
			if (OCSPResp.SUCCESSFUL != ocspResponse.getStatus()) 
				return SingleOcspResp.getInstance(new String(Base64.encode(userCert.getEncoded())), true, res, null, SingleOcspResp.HTTP_REQ_RESULT_CONN_FAIL, "连接请求失败，请检查网络!");
			

			BasicOCSPResp basicResponse = (BasicOCSPResp) ocspResponse.getResponseObject();
			X509Certificate ocspSignCert = new JcaX509CertificateConverter().setProvider( "BC" ).getCertificate(basicResponse.getCerts()[0]);
			//验证OCSP签名证证及OCSP响应的签名值
			ocspSignCert.verify(issuerCert.getPublicKey(),"BC");
			if(!basicResponse.isSignatureValid(new JcaContentVerifierProviderBuilder().setProvider("BC").build(ocspSignCert.getPublicKey()))){
				return SingleOcspResp.getInstance(new String(Base64.encode(userCert.getEncoded())), true, res, null, SingleOcspResp.HTTP_REQ_RESULT_FAIL, "OCSP响应签名验签失败!");
			}
			//解析OCSP响应
			SingleResp[] responses = (basicResponse == null) ? null : basicResponse.getResponses();	
			if (responses != null && responses.length == 1) {
				SingleResp resp = responses[0];
				CertificateStatus status = resp.getCertStatus();
				if (status == CertificateStatus.GOOD) {
					res = SingleOcspResp.CERT_STATUS_GOOD;
					log.info("证书状态正常!");
				} else {
					if (status instanceof RevokedStatus) {
						RevokedStatus revokeStatus = (RevokedStatus) status;
						gdcaCertResp.setRevokeTime(revokeStatus.getRevocationTime());
						log.info("证书已吊销,吊销时间：" + revokeStatus.getRevocationTime() + ",吊销原因：" + revokeStatus.getRevocationReason());
						res = SingleOcspResp.CERT_STATUS_REVOKED;
					} else if (status instanceof UnknownStatus) {
						log.info("证书不存在!");
						res = SingleOcspResp.CERT_STATUS_UNKNOWN;
					}
				}
			}
			gdcaCertResp.setStatus(res);
			gdcaCertResp.setHttpResult(SingleOcspResp.HTTP_REQ_RESULT_SUCCESS);
			return gdcaCertResp;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("OCSP请求失败：" + e.getMessage());
			return null;
		} 
		
	}
	
	/**
	 * 获取证书中AIA扩展项的URL.  
	 * OCSP Url:1.3.6.1.5.5.7.48.1
	 * caCert Url:1.3.6.1.5.5.7.48.2
	 */
	private static String getCertAccessUrl(X509Certificate certificate, String oid) throws Exception{
		String access_url = null;
		byte[] aiaExtensionDER = certificate.getExtensionValue("1.3.6.1.5.5.7.1.1");
		ASN1InputStream asn1InputStream = null;

		if (aiaExtensionDER == null) throw new Exception("证书不存在AIA扩展项!");

		try{
		    ASN1InputStream aiaExtensionSeq = new ASN1InputStream(new ByteArrayInputStream(aiaExtensionDER));
		    DEROctetString derObjectString = (DEROctetString) aiaExtensionSeq.readObject();
		    aiaExtensionSeq.close();
		    asn1InputStream = new ASN1InputStream(derObjectString.getOctets());
		    ASN1Sequence asn1Sequence = (ASN1Sequence) asn1InputStream.readObject();
		    asn1InputStream.close();
		    AccessDescription accessDescription = null;

		    for (int i = 0; i < asn1Sequence.size(); i++){
		    	accessDescription = AccessDescription.getInstance((ASN1Sequence) asn1Sequence.getObjectAt(i).toASN1Primitive());
		    	if (accessDescription.getAccessMethod().toString().equalsIgnoreCase(oid) == true){
		    		GeneralName gn = accessDescription.getAccessLocation();
		    		access_url = gn.getName().toString();
		    		break;
		    	}
		    }

		    if (access_url == null){
		    	throw new Exception("证书扩展项未写入OCSP地址!");
		    }

		    return (access_url);
		}catch (IOException exc){
			throw new Exception("获取证书扩展项中URL时异常.\n" + exc.toString());
		}finally{
			if (asn1InputStream != null){
				try{
					asn1InputStream.close();
				}
				catch (IOException excIO){
					log.error("关闭IO异常!");
				}
			}
		}
     }
	 public static void main(String[] args) throws Exception{
		Security.addProvider(new BouncyCastleProvider());
		String SUB_DIR = "E:/sm2/sub/";
		String EE_DIR = "E:/sm2/entity/";
		String OCSP_DIR = "E:/sm2/ocsp/";
		
		 SUB_DIR = "d:/cert/sub/";
		 EE_DIR = "d:/cert/entity/";
		 OCSP_DIR = "d:/cert/ocsp/";
		 
		//測試RSA證書
		 String base64UserCert = new String(FileUtils.readFile(new File(EE_DIR + "20180509151833030rm0nm8vg9vq3no9_rsa.cer")));
		 String base64IssuerCert = new String(FileUtils.readFile(new File(SUB_DIR + "subca_rsa.cer")));
		System.out.println("測試RSA證書"+HTTP_TYPE_POST);
		OcspClient.checkCertStatus(base64UserCert, base64IssuerCert, OCSP_URL, HTTP_TYPE_POST);
		System.out.println("測試RSA證書"+HTTP_TYPE_GET);
		OcspClient.checkCertStatus(base64UserCert, base64IssuerCert, OCSP_URL, HTTP_TYPE_GET);
		
		 //測試SM2證書
 		  base64UserCert = new String(FileUtils.readFile(new File(EE_DIR + "20180509151813273lm8ml02q7ll9az8_ec.cer")));
 		  base64IssuerCert = new String(FileUtils.readFile(new File(SUB_DIR + "subca_ec.cer")));
 		 System.out.println("測試SM2證書"+HTTP_TYPE_POST);
 		OcspClient.checkCertStatus(base64UserCert, base64IssuerCert, OCSP_URL, HTTP_TYPE_POST);
 		System.out.println("測試SM2證書"+HTTP_TYPE_GET);
		OcspClient.checkCertStatus(base64UserCert, base64IssuerCert, OCSP_URL, HTTP_TYPE_GET);
		
	}
}
