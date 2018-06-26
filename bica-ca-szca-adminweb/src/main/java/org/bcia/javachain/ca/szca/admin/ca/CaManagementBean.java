

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

package org.bcia.javachain.ca.szca.admin.ca;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.security.KeyStore;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJBException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.bcia.javachain.ca.szca.admin.common.WebLanguages;
import org.bcia.javachain.ca.szca.admin.controller.CreateNewCaForm;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.certificates.ca.CAConstants;
import org.cesecore.certificates.ca.CAData;
import org.cesecore.certificates.ca.CAInfo;
import org.cesecore.certificates.ca.CVCCAInfo;
import org.cesecore.certificates.ca.X509CAInfo;
import org.cesecore.certificates.ca.catoken.CAToken;
import org.cesecore.certificates.ca.catoken.CATokenConstants;
import org.cesecore.certificates.ca.extendedservices.ExtendedCAServiceInfo;
import org.cesecore.certificates.certificate.CertificateConstants;
import org.cesecore.certificates.certificate.certextensions.CertificateExtensionException;
import org.cesecore.certificates.certificate.certextensions.standard.NameConstraint;
import org.cesecore.certificates.certificateprofile.CertificatePolicy;
import org.cesecore.certificates.certificateprofile.CertificateProfile;
import org.cesecore.certificates.util.AlgorithmConstants;
import org.cesecore.certificates.util.AlgorithmTools;
import org.cesecore.certificates.util.DNFieldExtractor;
import org.cesecore.config.CesecoreConfiguration;
import org.cesecore.keys.token.CryptoToken;
import org.cesecore.keys.token.CryptoTokenData;
import org.cesecore.keys.token.CryptoTokenNameInUseException;
import org.cesecore.keys.token.CryptoTokenOfflineException;
import org.cesecore.keys.token.SoftCryptoToken;
import org.cesecore.util.CertTools;
import org.cesecore.util.StringTools;
import org.cesecore.util.ValidityDate;
import org.ejbca.core.model.ca.caadmin.extendedcaservices.CmsCAServiceInfo;
import org.ejbca.core.model.ca.caadmin.extendedcaservices.HardTokenEncryptCAServiceInfo;
import org.ejbca.core.model.ca.caadmin.extendedcaservices.KeyRecoveryCAServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.net.bcia.bcca.core.ejb.ca.caadmin.CAAdminSession;
import cn.net.bcia.cesecore.certificates.ca.CaSessionLocal;
import cn.net.bcia.cesecore.certificates.certificateprofile.CertificateProfileSessionLocal;
import cn.net.bcia.cesecore.keys.token.CryptoTokenManagementSessionLocal;


@Repository
public class CaManagementBean implements Serializable {

	private static final long serialVersionUID = 3L;
	private static final Logger log = Logger.getLogger(CaManagementBean.class);

	public static final int CATOKENTYPE_P12 = 1;
	public static final int CATOKENTYPE_HSM = 2;
	public static final int CATOKENTYPE_NULL = 3;

	@Autowired
	CaSessionLocal caSession;

	@PersistenceContext // (unitName = CesecoreConfiguration.PERSISTENCE_UNIT)
	private EntityManager entityManager;
//	@Autowired
//	CADataHandler  cadatahandler;
	// @Autowired
	// private cn.net.bcia.cesecore.certificates.crl.CrlStoreSession
	// crlStoreSession;
	// @Autowired
	// private PublishingCrlSessionLocal publishingCrlSession;

	// WebAuthenticationProviderSessionLocal authenticationSession =
	// ejbLocalHelper.getWebAuthenticationProviderSession();
	
	@Autowired
	CADataHandlerBean   caDataHandlerBean;
	
	
	@Autowired
	CertificateProfileSessionLocal  certificateProfileSession;
	
	@Autowired
	private cn.net.bcia.cesecore.authorization.control.AccessControlSessionLocal accessControlSession;

	  @Autowired
	  private CryptoTokenManagementSessionLocal cryptoTokenManagementSession;

	@Autowired
	private CAAdminSession caAdminSession;

	// @Autowired
	// WebAuthenticationProviderSessionLocal authenticationSession;
	// private boolean initialized;
	// private AuthenticationToken authenticationToken;

	/** Creates a new instance of CaInterfaceBean */
	public CaManagementBean() {
	}

	public List<CAInfo> getCaList(AuthenticationToken authenticationToken) {
		// caAdminSession.
		List<Integer> caids = caSession.getAuthorizedCaIds(authenticationToken);
		List<CAInfo> caList = new ArrayList<CAInfo>();
		for (Integer caid : caids) {
			try {
				CAInfo ca = caSession.getCAInfo(authenticationToken, caid);
				caList.add(ca);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return caList;

	}

	public CAInfo getCaById(AuthenticationToken authenticationToken, int caid) throws Exception {
		// caAdminSession.

		return caSession.getCAInfo(authenticationToken, caid);

	}

	public void updateCaName(AuthenticationToken authenticationToken, int caid, String name) throws Exception {
		// caAdminSession.

		// caSession.getCAInfo(authenticationToken, caid);
		entityManager.find(CAData.class, caid);

	}

	@Transactional
	public void importCAFromKeyStore(AuthenticationToken authenticationToken, String caname, byte[] p12file,
			String keystorepass, String privateSignatureKeyAlias, String privateEncryptionKeyAlias) throws Exception {
		final KeyStore ks = KeyStore.getInstance("PKCS12", BouncyCastleProvider.PROVIDER_NAME);
		ks.load(new ByteArrayInputStream(p12file), keystorepass.toCharArray());
		if (privateSignatureKeyAlias.equals("")) {
			Enumeration<String> aliases = ks.aliases();
			if (aliases == null || !aliases.hasMoreElements()) {
				throw new Exception("This file does not contain any aliases.");
			}
			privateSignatureKeyAlias = (String) aliases.nextElement();
			if (aliases.hasMoreElements()) {
				while (aliases.hasMoreElements()) {
					privateSignatureKeyAlias += " " + (String) aliases.nextElement();
				}
				throw new Exception("You have to specify any of the following aliases: " + privateSignatureKeyAlias);
			}
		}
		if (privateEncryptionKeyAlias.equals("")) {
			privateEncryptionKeyAlias = null;
		}
		caAdminSession.importCAFromKeyStore(authenticationToken, caname, p12file, keystorepass, keystorepass,
				privateSignatureKeyAlias, privateEncryptionKeyAlias);
		// info.cAsEdited();
	}

	public List<CryptoTokenData> getCryptoTokenList() {
		final Query query = entityManager.createQuery("SELECT a FROM CryptoTokenData a ");
		// query.setParameter("name", name);
		List<CryptoTokenData> list = query.getResultList();
		// return (CAData) QueryResultWrapper.getSingleResult(query);
		return list;

	}

	public List<CAData> getCaList() {
		final Query query = entityManager.createQuery("SELECT a FROM CAData a ");
		List<CAData> list = query.getResultList();
		return list;

	}

//	public int createNewCa(CreateNewCaForm createNewCaForm) throws Exception {
//		int caid = 0;
//		// final Query query = entityManager.createQuery("SELECT a FROM CAData a WHERE
//		// a.name=:name");
//		// query.setParameter("name", name);
//		// return (CAData) QueryResultWrapper.getSingleResult(query);
//
//		CryptoTokenData cryptoTokenData = entityManager.find(CryptoTokenData.class,
//				Integer.parseInt(createNewCaForm.getCryptoTokenId()));
//
//		/*
//		 * Entity类org.cesecore.keys.token.CryptoTokenData映射到表cryptotokendata，
//		 * 其中tokenData保存了Base64格式的keystore，是PKCS12格式，默认密码是foo123。
//		 */
//		String password = "foo123";
//		// byte[] ksData = cryptoTokenData.getTokenDataAsBytes();
//		KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
//		keyStore.load(new ByteArrayInputStream(cryptoTokenData.getTokenDataAsBytes()),
//				createNewCaForm.getPassword().toCharArray());
//
//		PrivateKey privateKey = (PrivateKey) keyStore.getKey(createNewCaForm.getAlias(),
//				createNewCaForm.getPassword().toCharArray());
//
//		// Get the public key from the private key
//		RSAPrivateCrtKey privk = (RSAPrivateCrtKey) privateKey;
//		RSAPublicKeySpec publicKeySpec = new java.security.spec.RSAPublicKeySpec(privk.getModulus(),
//				privk.getPublicExponent());
//		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
//
//		org.bouncycastle.jce.PKCS10CertificationRequest p10 = this.genCsr(createNewCaForm.getAlgorithm(), publicKey,
//				privateKey);
//
//		X509Certificate genX509Cert = this.genX509Cert(createNewCaForm.getAlgorithm(), publicKey, privateKey);
//
//		return caid;
//	}
//
//  
//	private AuthorityKeyIdentifier createAuthorityKeyId(PublicKey pubKey) {
//		AuthorityKeyIdentifier authKeyId = null;
//		try {
//
//			authKeyId = new AuthorityKeyIdentifier(pubKey.getEncoded());
//		} catch (Exception e) {
//			System.err.println("Error generating SubjectKeyIdentifier:  " + e.toString());
//			System.exit(1);
//		}
//		return authKeyId;
//	}
//
//	private SubjectKeyIdentifier createSubjectKeyId(PublicKey pubKey) {
//		SubjectKeyIdentifier authKeyId = null;
//		try {
//
//			authKeyId = new SubjectKeyIdentifier(pubKey.getEncoded());
//		} catch (Exception e) {
//			System.err.println("Error generating SubjectKeyIdentifier:  " + e.toString());
//			System.exit(1);
//		}
//		return authKeyId;
//	}

	@Transactional
	public boolean actionCreateCaMakeRequest(AuthenticationToken authenticationToken, CreateNewCaForm form) throws Exception {
//		 String caName = form.getCaName(); String signatureAlgorithm = form.getSignatureAlgorithmParam();
//         String extendedServiceSignatureKeySpec;
//         String keySequenceFormat; String keySequence; int catype; String subjectdn;
//         String certificateProfileIdString; String signedByString; String description; String validityString;
//         String approvalSettingValues; String numofReqApprovalsParam, boolean finishUser; boolean isDoEnforceUniquePublicKeys;
//         boolean isDoEnforceUniqueDistinguishedName; boolean isDoEnforceUniqueSubjectDNSerialnumber;
//         boolean useCertReqHistory, boolean useUserStorage, boolean useCertificateStorage, String subjectaltname;
//         String policyid, boolean useauthoritykeyidentifier, boolean authoritykeyidentifiercritical,
//         long crlperiod, long crlIssueInterval, long crlOverlapTime, long deltacrlperiod,
//         String availablePublisherValues, boolean usecrlnumber, boolean crlnumbercritical,
//         String defaultcrldistpoint, String defaultcrlissuer, String defaultocsplocator,
//         String authorityInformationAccessString, String nameConstraintsPermittedString, String nameConstraintsExcludedString;
//         String caDefinedFreshestCrlString, boolean useutf8policytext;
//         boolean useprintablestringsubjectdn, boolean useldapdnorder; boolean usecrldistpointoncrl;
//         boolean crldistpointoncrlcritical, boolean includeInHealthCheck; boolean serviceOcspActive;
//         boolean serviceCmsActive, String sharedCmpRaSecret, boolean buttonCreateCa; boolean buttonMakeRequest;
//         String cryptoTokenIdString, String keyAliasCertSignKey, String keyAliasCrlSignKey; String keyAliasDefaultKey;
//         String keyAliasHardTokenEncryptKey; String keyAliasKeyEncryptKey; String keyAliasKeyTestKey;
//         byte[] fileBuffer;
		String caName = form.getCaName();
	        int cryptoTokenId =  0;
	        try {
	        	  
	        	String keyAliasDefaultKey = form.getKeyAliasDefaultKey();
	        	String keyAliasCertSignKey = form.getKeyAliasDefaultKey();//form.getKeyAliasCertSignKey() ;//"signKey";
	        	String keyAliasCrlSignKey = form.getKeyAliasDefaultKey();// form.getKeyAliasCertSignKey();
	        	String keyAliasHardTokenEncryptKey = form.getKeyAliasHardTokenEncryptKey(); //"";"";
	        	String keyAliasKeyEncryptKey = form.getKeyAliasKeyEncryptKey(); //"";
	        	String keyAliasKeyTestKey =form.getKeyAliasKeyTestKey(); //""; "testKey";
	        	
	        	String extendedServiceSignatureKeySpec = form.getSignKeySpec();
	            if (form.getCryptoTokenId() ==0) {
	                // The admin has requested a quick setup and wants to generate a soft keystore with some usable keys
	                keyAliasDefaultKey = "defaultKey";
	                keyAliasCertSignKey = "signKey";
	                keyAliasCrlSignKey = keyAliasCertSignKey;
	                keyAliasHardTokenEncryptKey = "";
	                keyAliasKeyEncryptKey = "";
	                keyAliasKeyTestKey = "testKey";
	                // First create a new soft auto-activated CryptoToken with the same name as the CA
	                final Properties cryptoTokenProperties = new Properties();
	                cryptoTokenProperties.setProperty(CryptoToken.AUTOACTIVATE_PIN_PROPERTY, CesecoreConfiguration.getCaKeyStorePass());
	                try {
	                    cryptoTokenId = cryptoTokenManagementSession.createCryptoToken(authenticationToken, caName, SoftCryptoToken.class.getName(),
	                            cryptoTokenProperties, null, null);
	                } catch (CryptoTokenNameInUseException e) {
	                    // If the name was already in use we simply add a timestamp to the name to manke it unique
	                    final String postfix = "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	                    cryptoTokenId = cryptoTokenManagementSession.createCryptoToken(authenticationToken, caName+postfix, SoftCryptoToken.class.getName(),
	                            cryptoTokenProperties, null, null);
	                }
	                // Next generate recommended RSA key pairs for decryption and test
	                cryptoTokenManagementSession.createKeyPair(authenticationToken, cryptoTokenId, keyAliasDefaultKey, AlgorithmConstants.KEYALGORITHM_RSA + "2048");
	                cryptoTokenManagementSession.createKeyPair(authenticationToken, cryptoTokenId, keyAliasKeyTestKey, AlgorithmConstants.KEYALGORITHM_RSA + "1024");
	                // Next, create a CA signing key
	                final String caSignKeyAlgo = AlgorithmTools.getKeyAlgorithmFromSigAlg(form.getSignatureAlgorithm());
	                String caSignKeySpec = AlgorithmConstants.KEYALGORITHM_RSA + "2048";
	                extendedServiceSignatureKeySpec = "2048";
	                if (AlgorithmConstants.KEYALGORITHM_DSA.equals(caSignKeyAlgo)) {
	                    caSignKeySpec = AlgorithmConstants.KEYALGORITHM_DSA + "1024";
	                    extendedServiceSignatureKeySpec = caSignKeySpec;
	                } else if (AlgorithmConstants.KEYALGORITHM_ECDSA.equals(caSignKeyAlgo)) {
	                    caSignKeySpec = "prime256v1";
	                    extendedServiceSignatureKeySpec = caSignKeySpec;
	                } else if (AlgorithmTools.isGost3410Enabled() && AlgorithmConstants.KEYALGORITHM_ECGOST3410.equals(caSignKeyAlgo)) {
	                    caSignKeySpec = CesecoreConfiguration.getExtraAlgSubAlgName("gost3410", "B");
	                    extendedServiceSignatureKeySpec = caSignKeySpec;
	                } else if (AlgorithmTools.isDstu4145Enabled() && AlgorithmConstants.KEYALGORITHM_DSTU4145.equals(caSignKeyAlgo)) {
	                    caSignKeySpec = CesecoreConfiguration.getExtraAlgSubAlgName("dstu4145", "233");
	                    extendedServiceSignatureKeySpec = caSignKeySpec;
	                }
	                cryptoTokenManagementSession.createKeyPair(authenticationToken, cryptoTokenId, keyAliasCertSignKey, caSignKeySpec);
	            }
//	            return actionCreateCaMakeRequestInternal(caName, signatureAlgorithm, extendedServiceSignatureKeySpec,
//	                    keySequenceFormat, keySequence, catype, subjectdn, certificateProfileIdString, signedByString,
//	                    description, validityString, approvalSettingValues, numofReqApprovalsParam, finishUser,
//	                    isDoEnforceUniquePublicKeys, isDoEnforceUniqueDistinguishedName, isDoEnforceUniqueSubjectDNSerialnumber,
//	                    useCertReqHistory, useUserStorage, useCertificateStorage, subjectaltname, policyid,
//	                    useauthoritykeyidentifier, authoritykeyidentifiercritical, crlperiod, crlIssueInterval,
//	                    crlOverlapTime, deltacrlperiod, availablePublisherValues, usecrlnumber, crlnumbercritical,
//	                    defaultcrldistpoint, defaultcrlissuer, defaultocsplocator, authorityInformationAccessString,
//	                    nameConstraintsPermittedString, nameConstraintsExcludedString,
//	                    caDefinedFreshestCrlString, useutf8policytext, useprintablestringsubjectdn, useldapdnorder,
//	                    usecrldistpointoncrl, crldistpointoncrlcritical, includeInHealthCheck, serviceOcspActive,
//	                    serviceCmsActive, sharedCmpRaSecret, buttonCreateCa, buttonMakeRequest, cryptoTokenId,
//	                    keyAliasCertSignKey, keyAliasCrlSignKey, keyAliasDefaultKey, keyAliasHardTokenEncryptKey,
//	                    keyAliasKeyEncryptKey, keyAliasKeyTestKey, fileBuffer);
	            
	            return actionCreateCaMakeRequestInternal(authenticationToken,caName, form.getSignatureAlgorithm(), extendedServiceSignatureKeySpec,
	                    form.getKeySequenceFormatParam(), form.getKeySequence(), form.getCaType(), form.getSubjectDn(), form.getCertificateProfileId(), form.getSignedBy(),
	                    form.getDescription(), form.getValidityString(), form.getApprovalSettingValues(),form.getNumOfReqApprovalsParam(), form.isFinishUser() ,
	                    form.isDoEnforceUniquePublicKeys(), form.isDoEnforceUniqueDistinguishedName(), form.isDoEnforceUniqueSubjectDNSerialnumber() ,
	                    form.isUseCertReqHistory() , form.isUseUserStorage(), form.isUseCertificateStorage(), form.getSubjectAltName(),form.getPolicyId() ,
	                    form.isUseAuthorityKeyIdentifier(),   form.isAuthorityKeyIdentifierCritical(), form.getCrlPeriod() , form.getCrlIssueInterval() ,
	                    form.getCrlOverlapTime() , form.getDeltaCrlPeriod() , form.getAvailablePublisherValues() , form.isUseCrlNumber() , form.isCrlNumberCritical(),
	                    form.getDefaultCrlDistpoint() , form.getDefaultCrlIssuer() , form.getDefaultOcspLocator() , form.getAuthorityInformationAccess(),
	                    form.getNameConstraintsPermitted(), form.getNameConstraintsExcluded(),
	                    form.getCaDefinedFreshestCrl(), form.isUseUtf8PolicyText(), form.isUsePrintableStringSubjectDn(), form.isUseLdapDnOrder(),
	                    form.isUseCrlDistpointonCrl(), form.isCrlDistpointonCrlCritical(), form.isIncludeInHealthCheck() , form.isServiceOcspActive(),
	                    form.isServiceCmsActive(),  form.getSharedCmpRaSecret() , true,false /*buttonCreateCa, buttonMakeRequest*/, form.getCryptoTokenId()==0?cryptoTokenId:form.getCryptoTokenId(),
	                    keyAliasCertSignKey, keyAliasCrlSignKey, keyAliasDefaultKey, keyAliasHardTokenEncryptKey,
	                    keyAliasKeyEncryptKey, keyAliasKeyTestKey, null);
	        } catch (Exception e) {
	            // If we failed during the creation we manually roll back any created soft CryptoToken
	            // The more proper way of doing it would be to implement a CaAdminSession call for one-shot
	            // CryptoToken and CA creation, but this would currently push a lot of GUI specific code
	            // to the business logic. Until we have a new GUI this is probably the best way of doing it.
	            if (cryptoTokenId != 0 &&  form.getCryptoTokenId()==0) {
	                cryptoTokenManagementSession.deleteCryptoToken(authenticationToken, cryptoTokenId);
	            }
	            throw e;
	        }
	    }

	private boolean actionCreateCaMakeRequestInternal(AuthenticationToken authenticationToken,String caName, String signatureAlgorithm,
			String extendedServiceSignatureKeySpec, String keySequenceFormat, String keySequence, int catype,
			String subjectdn, String certificateProfileIdString, String signedByString, String description,
			String validityString, String approvalSettingValues, String numofReqApprovalsParam, boolean finishUser,
			boolean isDoEnforceUniquePublicKeys, boolean isDoEnforceUniqueDistinguishedName,
			boolean isDoEnforceUniqueSubjectDNSerialnumber, boolean useCertReqHistory, boolean useUserStorage,
			boolean useCertificateStorage, String subjectaltname, String policyid, boolean useauthoritykeyidentifier,
			boolean authoritykeyidentifiercritical, long crlperiod, long crlIssueInterval, long crlOverlapTime,
			long deltacrlperiod, String availablePublisherValues, boolean usecrlnumber, boolean crlnumbercritical,
			String defaultcrldistpoint, String defaultcrlissuer, String defaultocsplocator,
			String authorityInformationAccessString, String nameConstraintsPermittedString,
			String nameConstraintsExcludedString, String caDefinedFreshestCrlString, boolean useutf8policytext,
			boolean useprintablestringsubjectdn, boolean useldapdnorder, boolean usecrldistpointoncrl,
			boolean crldistpointoncrlcritical, boolean includeInHealthCheck, boolean serviceOcspActive,
			boolean serviceCmsActive, String sharedCmpRaSecret, boolean buttonCreateCa, boolean buttonMakeRequest,
			int cryptoTokenId, String keyAliasCertSignKey, String keyAliasCrlSignKey, String keyAliasDefaultKey,
			String keyAliasHardTokenEncryptKey, String keyAliasKeyEncryptKey, String keyAliasKeyTestKey,
			byte[] fileBuffer) throws Exception {

		boolean illegaldnoraltname = false;
		CAInfo caInfo = null;
		byte[]  request = null;
		final List<String> keyPairAliases = cryptoTokenManagementSession.getKeyPairAliases(authenticationToken,
				cryptoTokenId);
		if (!keyPairAliases.contains(keyAliasDefaultKey)) {
			log.info(authenticationToken.toString()
					+ " attempted to createa a CA with a non-existing defaultKey alias: " + keyAliasDefaultKey);
			throw new Exception("Invalid default key alias!");
		}
		final String[] suppliedAliases = { keyAliasCertSignKey, keyAliasCrlSignKey, keyAliasHardTokenEncryptKey,
				keyAliasHardTokenEncryptKey, keyAliasKeyEncryptKey, keyAliasKeyTestKey };
		for (final String currentSuppliedAlias : suppliedAliases) {
			if (currentSuppliedAlias.length() > 0 && !keyPairAliases.contains(currentSuppliedAlias)) {
				log.info(authenticationToken.toString() + " attempted to create a CA with a non-existing key alias: "
						+ currentSuppliedAlias);
				throw new Exception("Invalid key alias!");
			}
		}
		final Properties caTokenProperties = new Properties();
		caTokenProperties.setProperty(CATokenConstants.CAKEYPURPOSE_DEFAULT_STRING, keyAliasDefaultKey);
		if (keyAliasCertSignKey.length() > 0) {
			caTokenProperties.setProperty(CATokenConstants.CAKEYPURPOSE_CERTSIGN_STRING, keyAliasCertSignKey);
		}
		if (keyAliasCrlSignKey.length() > 0) {
			caTokenProperties.setProperty(CATokenConstants.CAKEYPURPOSE_CRLSIGN_STRING, keyAliasCrlSignKey);
		}
		if (keyAliasHardTokenEncryptKey.length() > 0) {
			caTokenProperties.setProperty(CATokenConstants.CAKEYPURPOSE_HARDTOKENENCRYPT_STRING,
					keyAliasHardTokenEncryptKey);
		}
		if (keyAliasKeyEncryptKey.length() > 0) {
			caTokenProperties.setProperty(CATokenConstants.CAKEYPURPOSE_KEYENCRYPT_STRING, keyAliasKeyEncryptKey);
		}
		if (keyAliasKeyTestKey.length() > 0) {
			caTokenProperties.setProperty(CATokenConstants.CAKEYPURPOSE_TESTKEY_STRING, keyAliasKeyTestKey);
		}
		final CAToken catoken = new CAToken(cryptoTokenId, caTokenProperties);
		if (signatureAlgorithm == null) {
			throw new Exception("No signature algorithm supplied!");
		}
		catoken.setSignatureAlgorithm(signatureAlgorithm);
		catoken.setEncryptionAlgorithm(AlgorithmTools.getEncSigAlgFromSigAlg(signatureAlgorithm));

		if (extendedServiceSignatureKeySpec == null || extendedServiceSignatureKeySpec.length() == 0) {
			throw new Exception("No key specification supplied.");
		}
		if (keySequenceFormat == null) {
			catoken.setKeySequenceFormat(StringTools.KEY_SEQUENCE_FORMAT_NUMERIC);
		} else {
			catoken.setKeySequenceFormat(Integer.parseInt(keySequenceFormat));
		}
		if (keySequence == null) {
			catoken.setKeySequence(CAToken.DEFAULT_KEYSEQUENCE);
		} else {
			catoken.setKeySequence(keySequence);
		}
		try {
			CertTools.stringToBcX500Name(subjectdn);
		} catch (Exception e) {
			illegaldnoraltname = true;
		}
		int certprofileid = (certificateProfileIdString == null ? 0 : Integer.parseInt(certificateProfileIdString));
		int signedby = (signedByString == null ? 0 : Integer.parseInt(signedByString));

		if (description == null) {
			description = "";
		}

		final long validity;
		if (buttonMakeRequest) {
			validity = 0; // not applicable
		} else {
			validity = ValidityDate.encode(validityString);
			if (validity < 0) {
				throw new Exception(WebLanguages.getInstance().getText("INVALIDVALIDITYORCERTEND") );
			}
		}

		if (catoken != null && catype != 0 && subjectdn != null && caName != null && signedby != 0) {
			// Approvals is generic for all types of CAs
			final ArrayList<Integer> approvalsettings = new ArrayList<Integer>();
			if (approvalSettingValues != null) {
				for (int i = 0; i < approvalSettingValues.split(";").length; i++) {
					approvalsettings.add(Integer.valueOf(approvalSettingValues.split(";")[i]));
				}
			}
			final int numofreqapprovals = (numofReqApprovalsParam == null ? 1
					: Integer.parseInt(numofReqApprovalsParam));

			if (catype == CAInfo.CATYPE_X509) {
				// Create a X509 CA
				if (subjectaltname == null) {
					subjectaltname = "";
				}
				if (!checkSubjectAltName(subjectaltname)) {
					illegaldnoraltname = true;
				}
				/* Process certificate policies. */
				final List<CertificatePolicy> policies = parsePolicies(policyid);
				// Certificate policies from the CA and the CertificateProfile will be merged
				// for cert creation in the CAAdminSession.createCA call

				final ArrayList<Integer> crlpublishers = new ArrayList<Integer>();
				if (availablePublisherValues != null) {
					for (final String availablePublisherId : availablePublisherValues.split(";")) {
						crlpublishers.add(Integer.valueOf(availablePublisherId));
					}
				}
				final List<String> authorityInformationAccess = new ArrayList<String>();
				authorityInformationAccess.add(authorityInformationAccessString);
				String cadefinedfreshestcrl = "";
				if (caDefinedFreshestCrlString != null) {
					cadefinedfreshestcrl = caDefinedFreshestCrlString;
				}

				final List<String> nameConstraintsPermitted = parseNameConstraintsInput(nameConstraintsPermittedString);
				final List<String> nameConstraintsExcluded = parseNameConstraintsInput(nameConstraintsExcludedString);
				final boolean hasNameConstraints = !nameConstraintsPermitted.isEmpty()
						|| !nameConstraintsExcluded.isEmpty();
				if (hasNameConstraints && !isNameConstraintAllowedInProfile(certprofileid)) {
					throw new Exception(WebLanguages.getInstance().getText("NAMECONSTRAINTSNOTENABLED") );
				}

				if (crlperiod != 0 && !illegaldnoraltname) {
					if (buttonCreateCa) {
						List<ExtendedCAServiceInfo> extendedcaservices = makeExtendedServicesInfos(
								extendedServiceSignatureKeySpec, subjectdn, serviceCmsActive);
						X509CAInfo x509cainfo = new X509CAInfo(subjectdn, caName, CAConstants.CA_ACTIVE, new Date(),
								subjectaltname, certprofileid, validity, null, catype, signedby, null, catoken,
								description, -1, null, policies, crlperiod, crlIssueInterval, crlOverlapTime,
								deltacrlperiod, crlpublishers, useauthoritykeyidentifier,
								authoritykeyidentifiercritical, usecrlnumber, crlnumbercritical, defaultcrldistpoint,
								defaultcrlissuer, defaultocsplocator, authorityInformationAccess,
								nameConstraintsPermitted, nameConstraintsExcluded, cadefinedfreshestcrl, finishUser,
								extendedcaservices, useutf8policytext, approvalsettings, numofreqapprovals,
								useprintablestringsubjectdn, useldapdnorder, usecrldistpointoncrl,
								crldistpointoncrlcritical, includeInHealthCheck, isDoEnforceUniquePublicKeys,
								isDoEnforceUniqueDistinguishedName, isDoEnforceUniqueSubjectDNSerialnumber,
								useCertReqHistory, useUserStorage, useCertificateStorage, sharedCmpRaSecret);
						try {
							caDataHandlerBean.createCA(authenticationToken,(CAInfo) x509cainfo);
						} catch (EJBException e) {
							if (e.getCausedByException() instanceof IllegalArgumentException) {
								// Couldn't create CA from the given parameters
								illegaldnoraltname = true;
							} else {
								throw e;
							}
						}
					}

					if (buttonMakeRequest) {
						List<ExtendedCAServiceInfo> extendedcaservices = makeExtendedServicesInfos(
								extendedServiceSignatureKeySpec, subjectdn, serviceCmsActive);
						X509CAInfo x509cainfo = new X509CAInfo(subjectdn, caName, CAConstants.CA_ACTIVE, new Date(),
								subjectaltname, certprofileid, validity, null, catype, CAInfo.SIGNEDBYEXTERNALCA, null,
								catoken, description, -1, null, policies, crlperiod, crlIssueInterval, crlOverlapTime,
								deltacrlperiod, crlpublishers, useauthoritykeyidentifier,
								authoritykeyidentifiercritical, usecrlnumber, crlnumbercritical, defaultcrldistpoint,
								defaultcrlissuer, defaultocsplocator, authorityInformationAccess,
								nameConstraintsPermitted, nameConstraintsExcluded, cadefinedfreshestcrl, finishUser,
								extendedcaservices, useutf8policytext, approvalsettings, numofreqapprovals,
								useprintablestringsubjectdn, useldapdnorder, usecrldistpointoncrl,
								crldistpointoncrlcritical, false, // Do not automatically include new CAs in
																	// health-check
								isDoEnforceUniquePublicKeys, isDoEnforceUniqueDistinguishedName,
								isDoEnforceUniqueSubjectDNSerialnumber, useCertReqHistory, useUserStorage,
								useCertificateStorage, null);
						//saveRequestInfo(x509cainfo);
						caInfo = x509cainfo;
					}
				}
			}

			if (catype == CAInfo.CATYPE_CVC) {
				// Only default values for these that are not used
				crlperiod = 2400;
				crlIssueInterval = 0;
				crlOverlapTime = 0;
				deltacrlperiod = 0;
				List<Integer> crlpublishers = new ArrayList<Integer>();
				if (crlperiod != 0 && !illegaldnoraltname) {
					// A CVC CA does not have any of the external services OCSP, CMS
					List<ExtendedCAServiceInfo> extendedcaservices = new ArrayList<ExtendedCAServiceInfo>();
					if (buttonMakeRequest) {
						signedby = CAInfo.SIGNEDBYEXTERNALCA;
					}
					// Create the CAInfo to be used for either generating the whole CA or making a
					// request
					CVCCAInfo cvccainfo = new CVCCAInfo(subjectdn, caName, CAConstants.CA_ACTIVE, new Date(),
							certprofileid, validity, null, catype, signedby, null, catoken, description, -1, null,
							crlperiod, crlIssueInterval, crlOverlapTime, deltacrlperiod, crlpublishers, finishUser,
							extendedcaservices, approvalsettings, numofreqapprovals, false, // Do not automatically
																							// include new CAs in
																							// health-check
							isDoEnforceUniquePublicKeys, isDoEnforceUniqueDistinguishedName,
							isDoEnforceUniqueSubjectDNSerialnumber, useCertReqHistory, useUserStorage,
							useCertificateStorage);
					if (buttonCreateCa) {
						caDataHandlerBean.createCA(authenticationToken,cvccainfo);
					} else if (buttonMakeRequest) {
						//saveRequestInfo(cvccainfo);
						caInfo = cvccainfo;
					}
				}
			}
		}
		if (buttonMakeRequest && !illegaldnoraltname) {
//			CAInfo cainfo = getRequestInfo();
			CAInfo cainfo = caInfo;
			caDataHandlerBean.createCA(authenticationToken,cainfo);
			int caid = cainfo.getCAId();
			try {
				byte[] certreq = caDataHandlerBean.makeRequest(authenticationToken,caid, fileBuffer,
						catoken.getAliasFromPurpose(CATokenConstants.CAKEYPURPOSE_CERTSIGN));
				//saveRequestData(certreq);
				request = certreq;
			} catch (CryptoTokenOfflineException e) {
				caDataHandlerBean.removeCA(authenticationToken,caid);
			}
		}
		return illegaldnoraltname;
	}
	 private boolean checkSubjectAltName(String subjectaltname) {
	        if (subjectaltname != null && !subjectaltname.trim().equals("")) {
	            final DNFieldExtractor subtest = new DNFieldExtractor(subjectaltname,DNFieldExtractor.TYPE_SUBJECTALTNAME);                   
	            if (subtest.isIllegal() || subtest.existsOther()) {
	                return false;
	            }
	        }
	        return true;
	    }
	 private List<CertificatePolicy> parsePolicies(String policyid) {
	        final ArrayList<CertificatePolicy> policies = new ArrayList<CertificatePolicy>();
	        if (!(policyid == null || policyid.trim().equals(""))) {
	            final String[] str = policyid.split("\\s+");
	            if (str.length > 1) {
	                policies.add(new CertificatePolicy(str[0], CertificatePolicy.id_qt_cps, str[1]));
	            } else {
	                policies.add(new CertificatePolicy((policyid.trim()),null,null));
	            }
	        }
	        return policies;
	    }
	 
	 private List<String> parseNameConstraintsInput(String input) throws Exception {
	        try {
	            return NameConstraint.parseNameConstraintsList(input);
	        } catch (CertificateExtensionException e) {
	            throw new Exception(MessageFormat.format(WebLanguages.getInstance().getText("INVALIDNAMECONSTRAINT"), e.getMessage()));
	        }
	    }
	    
	    private boolean isNameConstraintAllowedInProfile(int certProfileId) {
	        final CertificateProfile certProfile = certificateProfileSession.getCertificateProfile(certProfileId);
	        
	        final boolean isCA = (certProfile.getType() == CertificateConstants.CERTTYPE_SUBCA ||
	                certProfile.getType() == CertificateConstants.CERTTYPE_ROOTCA);
	        
	        return isCA && certProfile.getUseNameConstraints();
	    }
	    private List<ExtendedCAServiceInfo> makeExtendedServicesInfos(String keySpec, String subjectdn, boolean serviceCmsActive) {
		    String keyType = AlgorithmConstants.KEYALGORITHM_RSA;
	        try {
	            Integer.parseInt(keySpec);
	        } catch (NumberFormatException e) {
	            if (keySpec.startsWith("DSA")) {
	                keyType = AlgorithmConstants.KEYALGORITHM_DSA;
	            } else if (keySpec.startsWith(AlgorithmConstants.KEYSPECPREFIX_ECGOST3410)) {
	                keyType = AlgorithmConstants.KEYALGORITHM_ECGOST3410;
	            } else if (AlgorithmTools.isDstu4145Enabled() && keySpec.startsWith(CesecoreConfiguration.getOidDstu4145())) {
	                keyType = AlgorithmConstants.KEYALGORITHM_DSTU4145;
	            } else {
	                keyType = AlgorithmConstants.KEYALGORITHM_ECDSA;
	            }
	        }
	        
	        final int cmsactive = serviceCmsActive ? ExtendedCAServiceInfo.STATUS_ACTIVE : ExtendedCAServiceInfo.STATUS_INACTIVE;
		    
	        List<ExtendedCAServiceInfo> extendedcaservices = new ArrayList<ExtendedCAServiceInfo>();
	        // Create and active External CA Services.
	        extendedcaservices.add(
	                new CmsCAServiceInfo(cmsactive,
	                        "CN=CMSCertificate, " + subjectdn, "",
	                        keySpec, keyType));
	        extendedcaservices.add(new HardTokenEncryptCAServiceInfo(ExtendedCAServiceInfo.STATUS_ACTIVE));
	        extendedcaservices.add(new KeyRecoveryCAServiceInfo(ExtendedCAServiceInfo.STATUS_ACTIVE));
	        return extendedcaservices;
	    }
}
