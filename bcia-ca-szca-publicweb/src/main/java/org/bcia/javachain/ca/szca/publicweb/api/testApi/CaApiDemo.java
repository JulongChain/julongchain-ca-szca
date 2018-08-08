/*
 * Copyright ? 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 * Copyright ? 2018  SZCA. All Rights Reserved.
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
 */

package org.bcia.javachain.ca.szca.publicweb.api.testApi;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.bouncycastle.util.encoders.Base64;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CaApiDemo {

    public static String SZCA_API_APPLY_URL = "https://localhost:8444/enrollOrUpdate.html";
    public static String SZCA_API_REVOKE_URL = "https://localhost:8444/revoke.html";
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        //证书申请或更新
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName", "test0155");  //用户名 必填
        jsonObject.addProperty("password", "123456");   //用户密码  必填
        jsonObject.addProperty("CN", "test0155"); //通用名CN，必填
        jsonObject.addProperty("O", "SZCA"); //可选
        jsonObject.addProperty("OU", "技术中心"); //可选
        jsonObject.addProperty("L", "广州市"); //可选
        jsonObject.addProperty("S", "广东省"); //可选
        jsonObject.addProperty("C", "CN"); //可选
        jsonObject.addProperty("processId", 7); //实体证书流程，对应后面管理-RA功能-实体证书流程配置 中的流程ID
        jsonObject.addProperty("certType", 2); //证书类型 1:Certificate  2:PKCS12  3:JKS
        jsonObject.addProperty("reqType", 2); //请求类型  1：证书新增申请   2：证书更新申请
        //jsonObject.addProperty("keyType", 2);  //密钥类型  当certType等于2或3时使用，可选，默认是RSA2048，当keyType=2时，为SM2
        //jsonObject.addProperty("CSR", genCSR("CN=test0155")); //证书请求CSR，当certType等于1时必填
        sendEnrollOrUpdateRequest(jsonObject);
		/*JsonObject jsonObject = new JsonObject();
		X509Certificate cert = getX509Certificate(new String(FileUtils.readFile(new File("d:/test0145.cer"))));
		jsonObject.addProperty("userName", "test0145"); //用户名
		//jsonObject.addProperty("serialNo", cert.getSerialNumber().toString(16).toUpperCase());   //撤销证书的序列号序列号，当reqType=2时必填，16进制字符串
		//jsonObject.addProperty("serialNo", "2B6048F392EF3E62");

		jsonObject.addProperty("reqType", 1); //请求类型  1：撤销用户  2：撤销单个证书
		jsonObject.addProperty("revokeReason", 5); //撤销原因
		sendRevokeRequest(jsonObject);*/
    }

    public static CloseableHttpClient initClient()  throws Exception{
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(new FileInputStream(new File("d:/administrator.p12")), "szca1234".toCharArray());
        KeyStore trustStore = KeyStore.getInstance("JKS");
        trustStore.load(new FileInputStream(new File("d:/truststore.jks")), "123456".toCharArray());
        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(trustStore)
                .loadKeyMaterial(keyStore, "szca1234".toCharArray())
                .build();
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
    }

    public static JsonObject sendRequest(String url, JsonObject jsonObject) throws Exception{
        CloseableHttpClient httpclient = initClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(jsonObject.toString(),Charset.forName("UTF-8")));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        JsonObject resultObject = new JsonParser().parse(responseContent.trim()).getAsJsonObject();
        response.close();
        httpclient.close();
        return resultObject;
    }

    public static void sendEnrollOrUpdateRequest(JsonObject jsonObject) throws Exception {
        JsonObject resultObject = sendRequest(SZCA_API_APPLY_URL, jsonObject);
        if(resultObject.get("resultCode").getAsString().equals("001")) {
            if(jsonObject.get("certType").getAsInt() == 1) {
                saveCert(new String(Base64.decode(resultObject.get("cert").getAsString())), "d:/", jsonObject.get("userName").getAsString() + ".cer");
            }
            if(jsonObject.get("certType").getAsInt() == 2) {
                FileUtils.saveFile(Base64.decode(resultObject.get("cert").getAsString()), "d:/", jsonObject.get("userName").getAsString() + ".pfx");
            }
            if(jsonObject.get("certType").getAsInt() == 3) {
                FileUtils.saveFile(Base64.decode(resultObject.get("cert").getAsString()), "d:/", jsonObject.get("userName").getAsString() + ".jks");
            }
        }else {
            System.out.println("request error:" + resultObject.get("resultCode").getAsString());
        }
    }

    public static void sendRevokeRequest(JsonObject jsonObject) throws Exception {
        JsonObject resultObject = sendRequest(SZCA_API_REVOKE_URL, jsonObject);
        if(resultObject.get("resultCode").getAsString().equals("001")) {
            System.out.println("revoke success!");
        }else {
            System.out.println("errorCode=" + resultObject.get("resultCode").getAsString() + ", errorMsg:" + resultObject.get("errorMsg").getAsString());
        }
    }

    //读取X509证书
    private static X509Certificate getX509Certificate(String cert) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        CertificateFactory cf = CertificateFactory.getInstance("X.509","BC");
        X509Certificate x509cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(cert.getBytes()));
        return x509cert;
    }

    public static String genCSR(String subjectDn){
        try{
            KeyPairGenerator g = KeyPairGenerator.getInstance("EC", "BC");
            g.initialize(new ECNamedCurveGenParameterSpec("sm2p256v1"));
            KeyPair kp = g.generateKeyPair();

            PKCS10CertificationRequestBuilder crb = new JcaPKCS10CertificationRequestBuilder(new X500Name(subjectDn), kp.getPublic());
            JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SM3withSM2");
            ContentSigner signer = csBuilder.build(kp.getPrivate());
            PKCS10CertificationRequest p10 =  crb.build(signer);
            System.out.println(p10.getSignatureAlgorithm().getAlgorithm().getId());
            byte[] der = p10.getEncoded();
            String code = "-----BEGIN CERTIFICATE REQUEST-----\n";
            code += new String(Base64.encode(der)) + "\n";
            code += "-----END CERTIFICATE REQUEST-----";
            String priStr = "-----BEGIN PRIVATE KEY-----\n";
            priStr += new String(Base64.encode(kp.getPrivate().getEncoded())) + "\n";
            priStr += "-----END PRIVATE KEY-----";
            System.out.println(code);
            System.out.println(priStr);
            return code;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //保存证书
    private static void saveCert(String cert, String filePath, String fileName) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("-----BEGIN CERTIFICATE-----\n")
                    .append(cert)
                    .append("\n-----END CERTIFICATE-----");
            FileUtils.saveFile(sb.toString().getBytes(), filePath, fileName);
        } catch (Exception e) {
            System.out.println("保存证书异常：" + e.getMessage());
        }
    }
}
