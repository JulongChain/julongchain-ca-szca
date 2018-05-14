package org.bcia.javachain.ca.szca.publicweb.api.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.bcia.javachain.ca.szca.publicweb.api.CallConfigData;
import org.bcia.javachain.ca.szca.publicweb.api.CallLogData;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.certificates.ca.CAData;
import org.cesecore.certificates.endentity.EndEntityInformation;
import org.cesecore.util.Base64;
import org.cesecore.util.CertTools;
import org.ejbca.core.ejb.ra.UserData;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.net.bcia.bcca.core.ejb.ra.EndEntityManagementSession;
import cn.net.bcia.cesecore.certificates.certificate.CertificateStoreSession;

@Repository
public class SzcaApiService implements ISzcaApiService {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(SzcaApiService.class);
	   
	
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	EndEntityManagementSession endEntityManagementSession;
	@Autowired
	CertificateStoreSession  certificateStoreSessionBean;
//	@Autowired
//	EndEntityManagementSessionLocal endEntityManagementSession;
	
	public String getDefaultCaName()   {

		String hql = "SELECT a FROM CAData a ";
		Query query = entityManager.createQuery(hql);
		List caList = query.getResultList();
		if (caList != null) {
			CAData caData = (CAData) caList.get(0);

			return caData.getCA().getName();// .toArray(new Certificate[1]);
		} else
			return null;

	}

	public CAData getCaByName(String caName)   {

		String hql = "SELECT a FROM CAData a WHERE a.name=:name";
		Query query = entityManager.createQuery(hql);
		query.setParameter("name", caName);
		List caList = query.getResultList();
		if (caList != null) {
			CAData caData = (CAData) caList.get(0);

			return caData;// .toArray(new Certificate[1]);
		} else
			return null;

	}
	
	public String getCertificateChain4Base64(String caName, String format) throws Exception {

		Collection<Certificate> chain = this.getCertificateChain(caName);
		byte[] outbytes = new byte[0];
		// Encode and send back
		if ((format == null) || StringUtils.equalsIgnoreCase(format, "pem")) {
		 	outbytes = getPemFromCertificateChain(chain);
			//outbytes = getBase64FromCertificateChain(chain);
			//outbytes ="MIIFZTCCA02gAwIBAgIIAwIJrv8NcegwDQYJKoZIhvcNAQELBQAwQDENMAsGA1UECgwEQkNJQTESMBAGA1UECwwJamF2YWNoYWluMRswGQYDVQQDDBJCQ0lBIE1hbmFnZW1lbnQgQ0EwHhcNMTgwNTAxMTA1NjUzWhcNMjgwNDI4MTA1NjUzWjBAMQ0wCwYDVQQKDARCQ0lBMRIwEAYDVQQLDAlqYXZhY2hhaW4xGzAZBgNVBAMMEkJDSUEgTWFuYWdlbWVudCBDQTCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBALZxM6GO0UNAJdyXwsTyO5d22RA4qreX3w8wGMXwCDPli1GixzReYYu4CrmKYaz6aE3gpZYrOG2B0vOftgeI5/6f7Q8Deg7+lr4Fqc/MUz2aFQVLk/ogItoYMbVkaQZscPRsN8EPuPx7U7zWs9GML4HjRRQdeZ3O0mjbbpiIInnsmkT1x1ogZWKxpLAJP9/XbZR8szJGGgRNeB2CZN0uOQ5WqUXTDPBNh3ys1YhijYZeYL/SjGbTnw21RIidhbINzQLfie5rtY2hJebGN1USFKRbhPkP5vxWo3UBNjfoNdGs9j5hVUUec+IrgfnP4cv7Iq4Di0+0oBBhPbhFrzxhLh/kgf6bCx1ElXh5FEzUSr29f+CdEHj3o3ZAGE3QTb1yXs+29m4SlZI7HD1AQltK3BUPrvqSESytThLm0LOsFT8aTPzhim8vOl/q/Pz1kNe3EwP4M1M6rXSVUIoQso0PN6CiuweauhDhz5q/wUBmfbKVTEnMxCBON1tR4zKVJA79gcPn0vpdhWIqZf/DiQwignzJSTRf6Tc6wk76mC9SVoUeoBcymdswBgiwddfh1+w/lSAkd1PXeLZTsIEVmZNISwtmbUFyhfwxYuf1rp4ukSlpKDiTuPNbcAdDSBWfNmTbfJ7HMeQpUhGKJ/B7hUUHxuJmbSFDWt+1J6OZkJnwQd9jAgMBAAGjYzBhMA8GA1UdEwEB/wQFMAMBAf8wHwYDVR0jBBgwFoAU0NOmFKV7cbfLqeByHqfykiageaEwHQYDVR0OBBYEFNDTphSle3G3y6ngch6n8pImoHmhMA4GA1UdDwEB/wQEAwIBhjANBgkqhkiG9w0BAQsFAAOCAgEAWFfS+2k61AWyFQ3VHG2yvw0Ze6HZJhJCh3qKt+FII0g99rapCmDB0nKeQJ7mCbaqOjv6GdTIs0zavNzuI8fWSx6Zf+4fHneAwthGCNqFUZmPcPjPcBC62cXr2dOCM2W+4jbZYsLQpeiSy+WLC6HCOg3ZD1V2cQrpLjH3TwHRVzcOomZlC86lfcnj4/gWr9EJsFSI6t88lLImr0nlN+EmN4NnXHOtbN9NuKHe9OYk2n72/5E3ry/rGsd43e0oeIzI3NyQNvQhDhME9gtBF8DLVnVvNx+muSIsWGwkAwFC6c9xIjAfovWbIv1BJmsz6Kv/qs8Wkfo5Nz+ti0zhsdOEgs3OKG+KtNzphSNDl76mBg/eJTMy+TkNeS/5FOGvPjCSvT41xOBqvpt7nTQ8qVudOEtW1FQEHOqMSNqZ681tnGR6azrMOiHdfehiOoqNpsZEVJXktFicJ+wJp8xoqLkBnO7hNSK2+MjdsASkLhA7P8hwmwdZ189SyBsG/Frnx294nxnHWuzaQJ+d4Wz3xC/bGuMCUmNT91RiEgsicLHtCCQ8BVRzNZR9LwjOP+MDUmxRbe8bYpn5fwZwT7+JHt94M/C7DYPUMR5lRSWY9mn/grQmgMxSMbpPslvd8N7YCeRq1V+J6J9C/MVnXNNOi0IEDk0LvQ0AOeN8vN8Q00Kqi/s=".getBytes();
		} else {
			// Create a JKS truststore with the CA certificates in
			final KeyStore store = KeyStore.getInstance("JKS");
			store.load(null, null);
			Certificate[] chainCerts = (Certificate[]) chain.toArray(new Certificate[1]);
			for (int i = 0; i < chainCerts.length; i++) {
				String cadn = CertTools.getSubjectDN(chainCerts[i]);
				String alias = CertTools.getPartFromDN(cadn, "CN");
				if (alias == null) {
					alias = CertTools.getPartFromDN(cadn, "O");
				}
				if (alias == null) {
					alias = "cacert" + i;
				}
				alias = StringUtils.replaceChars(alias, ' ', '_');
				alias = StringUtils.substring(alias, 0, 15);
				store.setCertificateEntry(alias, chainCerts[i]);
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				store.store(out, "changeit".toCharArray());
				out.close();
				outbytes = out.toByteArray();
			}
		}
		return new String(outbytes);
	}

	public byte[] getPemFromCertificateChain(Collection<Certificate> certs) throws CertificateEncodingException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 
		try (final PrintStream printStream = new PrintStream(baos)) {
			for (final Certificate certificate : certs) {
				// printStream.println("Subject: " + CertTools.getSubjectDN(certificate));
				// printStream.println("Issuer: " + CertTools.getIssuerDN(certificate));
				  printStream.println("-----BEGIN CERTIFICATE-----");
				printStream.println(new String(Base64.encode(certificate.getEncoded())));
				  printStream.println("-----END CERTIFICATE-----");
			}
		} 
		 
		//return baos.toByteArray();
		return org.apache.commons.codec.binary.Base64.encodeBase64(baos.toByteArray());
	}
	
	public byte[] getBase64FromCertificateChain___(Collection<Certificate> certs) throws CertificateEncodingException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 
		for (final Certificate certificate : certs) {
			try {
				//使用org.cesecore.util.Base64会自动格式化，即每64个字符自动回车换行\r\n
			//baos.write(org.cesecore.util.Base64.encode(certificate.getEncoded()));
				//使用org.apache.commons.codec.binary.Base64不会自动格式化
			//	baos.write(org.apache.commons.codec.binary.Base64.encodeBase64(certificate.getEncoded()));
				baos.write(org.apache.commons.codec.binary.Base64.encodeBase64(org.cesecore.util.Base64.encode(certificate.getEncoded())));
			}catch(Exception e) {
				new CertificateEncodingException(e.getMessage());
			}
		}
		byte[] base64certs =   baos.toByteArray();
		System.out.println(new String(base64certs));
		return base64certs;
	}

	private Collection<Certificate> getCertificateChain(String caName) {

		Collection<Certificate> chain = null;
		String cname;
		if (caName == null || "".equals(caName.trim()))
			cname = this.getDefaultCaName();
		else
			cname = caName;
		String hql = "SELECT a FROM CAData a WHERE a.name=:name";
		Query query = entityManager.createQuery(hql);
		query.setParameter("name", cname);
		CAData caData = (CAData) query.getSingleResult();

		if (caData != null)
			chain = caData.getCA().getCertificateChain();// .toArray(new Certificate[1]);

		return chain;
	}

	@Override
	@Transactional
	public List<String> revoke(AuthenticationToken admin,String caName, String serno, int reason, String name, String aki) throws Exception {
		// TODO Auto-generated method stub
		List<String> snList = new ArrayList<String>();
		Collection<Certificate> chain = this.getCertificateChain(caName);
		Certificate[] certs =  (Certificate[])chain.toArray(new Certificate[1]);
		String issuerdn=  ((X509Certificate)certs[0]).getIssuerDN().getName();
		   BigInteger certserno = null;
        try {  certserno = new BigInteger(serno);}catch(Exception e1) {try {  certserno = new BigInteger(serno,16);}catch(Exception e2) {}}
		
		endEntityManagementSession.revokeCert(admin, certserno, issuerdn, reason);
		if(certserno!=null)
			snList.add(certserno.toString(10));
		 return snList;
	}

	@Override
	@Transactional
	public String addEndEntity(AuthenticationToken admin, EndEntityInformation entity) throws Exception{
		// TODO Auto-generated method stub
		String passwd = this.randomPwd(12);
		entity.setPassword(passwd);
		endEntityManagementSession.addUser(admin, entity, false);
		
		return passwd;
	}
	
	@Override
	public String randomPwd(int length) {
		String s="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";//+"!@#$%&*()<>?";		 
		char[] sarray = s.toCharArray();
		StringBuffer buff = new StringBuffer();
		 for(int i=0;i<length;i++){
			 int idx = (int)(Math.random()*s.length());
			 buff.append(sarray[idx]);
		 }	 
		 return buff.toString();
	}

	@Override
	public List<CAData> getCaList()  {
		 
		String hql = "SELECT a FROM CAData a WHERE a.status=1 ";
		Query query = entityManager.createQuery(hql);
	 
		List<CAData> caList  =  query.getResultList() ;
		return caList;
	}

	@Override
	@Transactional
	public void apiCallLog(CallLogData callLog) {
		// TODO Auto-generated method stub
		 
		callLog.setLogTime(System.currentTimeMillis());
		entityManager.persist(callLog);
	}

	@Override
	public CAData getCaByUser(String username) {
		// TODO Auto-generated method stub
		String hql = "SELECT a FROM UserData a WHERE a.username=:username ";
		Query query = entityManager.createQuery(hql);
		query.setParameter("username", username);
		List<UserData> userList  =  query.getResultList() ;
		int caId = 0;
		if(userList!=null && !userList.isEmpty()) {
			UserData userData = userList.get(0);
			caId = userData.getCaId();
		}
		CAData caData = null;
		if(caId!=0)
		{
			hql = "SELECT a FROM CAData a WHERE a.caId=:caId ";
			query = entityManager.createQuery(hql);
			query.setParameter("caId", caId);
			//caData = (CAData)query.getSingleResult();
			List<CAData> caList  =  query.getResultList() ;
			if(caList!=null && !caList.isEmpty()) {
				caData = caList.get(0);				 
			}
		}
		 
		return caData;
	}

	@Override
	public CallConfigData getCallConfigByIP(String ip) {
		String hql = "SELECT a FROM CallConfigData a WHERE a.ip=:ip ";
		Query query = entityManager.createQuery(hql);
		query.setParameter("ip", ip);
		List<CallConfigData> userList  =  query.getResultList() ;
		int caId = 0;
		CallConfigData callConfigData = null;
		if(userList!=null && !userList.isEmpty()) {
			callConfigData = userList.get(0);		 
		}
		return callConfigData;
	}

	@Override
	public List<Certificate> findTCert(AuthenticationToken authenticationToken, String caName, int certCount,
			int validityPeriod, boolean isEncrypt, String attr_names) {
		// TODO Auto-generated method stub
		return null;
	}

 
 
}
