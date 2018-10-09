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

package org.bcia.javachain.ca.szca.admin.controller;

public class CreateNewCaForm {
//	private String caName;
//	private String caType;
//	private String algorithm;
//
//	private String subjectAlternativeName;
//	private String cryptoTokenId;
//	private String subjectDn;
// 	private String issuerCaId;
//
//	private String certPolicyId;
//	private String akid;
//	private String crlNum;
//	private String alias;
//	private String password;



	private String  caName  ;
	private String signatureAlgorithm   ;//= requestMap.get(HIDDEN_CASIGNALGO);
	private String signKeySpec  ;//= requestMap.get(SELECT_KEYSIZE);
	private String keySequenceFormatParam  ;//= requestMap.get(SELECT_KEY_SEQUENCE_FORMAT);
	private String keySequence  ;//= requestMap.get(TEXTFIELD_KEYSEQUENCE);
	//1-x509,2-CVC
	private int caType =1 ;// = Integer.parseInt(requestMap.get(HIDDEN_CATYPE));
	private String subjectDn  ;//= requestMap.get(TEXTFIELD_SUBJECTDN);
	private String certificateProfileId  ;//= requestMap.get(SELECT_CERTIFICATEPROFILE);
	private String signedBy  ;//= requestMap.get(SELECT_SIGNEDBY);
	private String description ;// = requestMap.get(TEXTFIELD_DESCRIPTION);
	private String validityString  ;//= requestMap.get(TEXTFIELD_VALIDITY);
	private String approvalSettingValues  ;//= requestMap.get(SELECT_APPROVALSETTINGS);//request.getParameterValues(SELECT_APPROVALSETTINGS);
	private String numOfReqApprovalsParam  ;//= requestMap.get(SELECT_NUMOFREQUIREDAPPROVALS);
	private boolean finishUser = false;//CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_FINISHUSER));
	private boolean isDoEnforceUniquePublicKeys;// CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_DOENFORCEUNIQUEPUBLICKEYS));
	private boolean isDoEnforceUniqueDistinguishedName;// CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_DOENFORCEUNIQUEDN));
	private boolean isDoEnforceUniqueSubjectDNSerialnumber;// CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_DOENFORCEUNIQUESUBJECTDNSERIALNUMBER));
	private boolean useCertReqHistory =false;// CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_USECERTREQHISTORY));
	//useUserStorage 若为false，新增entity时不保存在数据库，只保存在内存中
	private boolean useUserStorage;//CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_USEUSERSTORAGE));
	private boolean useCertificateStorage;//CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_USECERTIFICATESTORAGE));
	private String subjectAltName ;//= requestMap.get(TEXTFIELD_SUBJECTALTNAME);
	private String policyId  ;//= requestMap.get(TEXTFIELD_POLICYID);
	private boolean useAuthorityKeyIdentifier;//CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_AUTHORITYKEYIDENTIFIER));
	private boolean authorityKeyIdentifierCritical;//CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_AUTHORITYKEYIDENTIFIERCRITICAL));
	// CRL periods and publishers is specific for X509 CAs
	private long crlPeriod  ;//= CombineTime.getInstance(requestMap.get(TEXTFIELD_CRLPERIOD), "1"+CombineTime.TYPE_DAYS).getLong();
	private long crlIssueInterval  ;//= CombineTime.getInstance(requestMap.get(TEXTFIELD_CRLISSUEINTERVAL), "0"+CombineTime.TYPE_MINUTES).getLong();
	private long crlOverlapTime  ;//= CombineTime.getInstance(requestMap.get(TEXTFIELD_CRLOVERLAPTIME), "10"+CombineTime.TYPE_MINUTES).getLong();
	private long deltaCrlPeriod  ;//= CombineTime.getInstance(requestMap.get(TEXTFIELD_DELTACRLPERIOD), "0"+CombineTime.TYPE_MINUTES).getLong();
	private String availablePublisherValues  ;//= requestMap.get(SELECT_AVAILABLECRLPUBLISHERS);//request.getParameterValues(SELECT_AVAILABLECRLPUBLISHERS);
	private boolean useCrlNumber;//CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_USECRLNUMBER));
	private boolean crlNumberCritical;//CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_CRLNUMBERCRITICAL));
	private String defaultCrlDistpoint  ;//= requestMap.get(TEXTFIELD_DEFAULTCRLDISTPOINT);
	private String defaultCrlIssuer  ;//= requestMap.get(TEXTFIELD_DEFAULTCRLISSUER);
	private String defaultOcspLocator  ;// = requestMap.get(TEXTFIELD_DEFAULTOCSPLOCATOR);
	private String authorityInformationAccess  ;//= requestMap.get(TEXTFIELD_AUTHORITYINFORMATIONACCESS);
	private String nameConstraintsPermitted  ;//= requestMap.get(TEXTFIELD_NAMECONSTRAINTSPERMITTED);
	private String nameConstraintsExcluded ;// = requestMap.get(TEXTFIELD_NAMECONSTRAINTSEXCLUDED);
	private String caDefinedFreshestCrl  ;//= requestMap.get(TEXTFIELD_CADEFINEDFRESHESTCRL);
	private boolean useUtf8PolicyText;//CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_USEUTF8POLICYTEXT));
	private boolean usePrintableStringSubjectDn;//CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_USEPRINTABLESTRINGSUBJECTDN));
	private boolean useLdapDnOrder;// CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_USELDAPDNORDER));
	private boolean useCrlDistpointonCrl  = false;//CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_USECRLDISTRIBUTIONPOINTONCRL));
	private boolean crlDistpointonCrlCritical = false;//CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_CRLDISTRIBUTIONPOINTONCRLCRITICAL));
	private boolean includeInHealthCheck = false;//CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_INCLUDEINHEALTHCHECK));
	private boolean serviceOcspActive = false;//CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_ACTIVATEOCSPSERVICE));
	private boolean serviceCmsActive = false;//CHECKBOX_VALUE.equals(requestMap.get(CHECKBOX_ACTIVATECMSSERVICE));
	private String sharedCmpRaSecret  ;//= requestMap.get(TEXTFIELD_SHAREDCMPRASECRET);
	private int cryptoTokenId  ;//= requestMap.get(HIDDEN_CACRYPTOTOKEN); //requestMap.get(SELECT_CRYPTOTOKEN);
	private String keyAliasCertSignKey  ;//= requestMap.get(SELECT_CRYPTOTOKEN_CERTSIGNKEY);
	private String keyAliasCrlSignKey = keyAliasCertSignKey;//requestMap.get(SELECT_CRYPTOTOKEN_CRLSIGNKEY);
	private String keyAliasDefaultKey  ;//= requestMap.get(SELECT_CRYPTOTOKEN_DEFAULTKEY);
	private String keyAliasHardTokenEncryptKey  ;//= requestMap.get(SELECT_CRYPTOTOKEN_HARDTOKENENCRYPTKEY);
	private String keyAliasKeyEncryptKey  ;//= requestMap.get(SELECT_CRYPTOTOKEN_KEYENCRYPTKEY);
	private String keyAliasKeyTestKey  ;//= requestMap.get(SELECT_CRYPTOTOKEN_KEYTESTKEY);
	public String getCaName() {
		return caName;
	}
	public void setCaName(String caName) {
		this.caName = caName;
	}
	public String getSignatureAlgorithm () {
		return signatureAlgorithm;
	}
	public void setSignatureAlgorithm (String signatureAlgorithm) {
		this.signatureAlgorithm = signatureAlgorithm;
	}
	public String getSignKeySpec() {
		return signKeySpec;
	}
	public void setSignKeySpec(String signKeySpec) {
		this.signKeySpec = signKeySpec;
	}
	public String getKeySequenceFormatParam() {
		return keySequenceFormatParam;
	}
	public void setKeySequenceFormatParam(String keySequenceFormatParam) {
		this.keySequenceFormatParam = keySequenceFormatParam;
	}
	public String getKeySequence() {
		return keySequence;
	}
	public void setKeySequence(String keySequence) {
		this.keySequence = keySequence;
	}
	public int getCaType() {
		return caType;
	}
	public void setCaType(int caType) {
		this.caType = caType;
	}
	public String getSubjectDn() {
		return subjectDn;
	}
	public void setSubjectDn(String subjectDn) {
		this.subjectDn = subjectDn;
	}
	public String getCertificateProfileId() {
		return certificateProfileId;
	}
	public void setCertificateProfileId(String certificateProfileIdString) {
		this.certificateProfileId = certificateProfileIdString;
	}
	public String getSignedBy() {
		return signedBy;
	}
	public void setSignedBy(String signedByString) {
		this.signedBy = signedByString;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getValidityString() {
		return validityString;
	}
	public void setValidityString(String validityString) {
		this.validityString = validityString;
	}
	public String getApprovalSettingValues() {
		return approvalSettingValues;
	}
	public void setApprovalSettingValues(String approvalSettingValues) {
		this.approvalSettingValues = approvalSettingValues;
	}
	public String getNumOfReqApprovalsParam() {
		return numOfReqApprovalsParam;
	}
	public void setNumOfReqApprovalsParam(String numofReqApprovalsParam) {
		this.numOfReqApprovalsParam = numofReqApprovalsParam;
	}
	public boolean isFinishUser() {
		return finishUser;
	}
	public void setFinishUser(boolean finishUser) {
		this.finishUser = finishUser;
	}
	public boolean isDoEnforceUniquePublicKeys() {
		return isDoEnforceUniquePublicKeys;
	}
	public void setIsDoEnforceUniquePublicKeys(boolean isDoEnforceUniquePublicKeys) {
		this.isDoEnforceUniquePublicKeys = isDoEnforceUniquePublicKeys;
	}
	public boolean isDoEnforceUniqueDistinguishedName() {
		return isDoEnforceUniqueDistinguishedName;
	}
	public void setIsDoEnforceUniqueDistinguishedName(boolean isDoEnforceUniqueDistinguishedName) {
		this.isDoEnforceUniqueDistinguishedName = isDoEnforceUniqueDistinguishedName;
	}
	public boolean isDoEnforceUniqueSubjectDNSerialnumber() {
		return isDoEnforceUniqueSubjectDNSerialnumber;
	}
	public void setIsDoEnforceUniqueSubjectDNSerialnumber(boolean isDoEnforceUniqueSubjectDNSerialnumber) {
		this.isDoEnforceUniqueSubjectDNSerialnumber = isDoEnforceUniqueSubjectDNSerialnumber;
	}
	public boolean isUseCertReqHistory() {
		return useCertReqHistory;
	}
	public void setUseCertReqHistory(boolean useCertReqHistory) {
		this.useCertReqHistory = useCertReqHistory;
	}
	public boolean isUseUserStorage() {
		return useUserStorage;
	}
	public void setUseUserStorage(boolean useUserStorage) {
		this.useUserStorage = useUserStorage;
	}
	public boolean isUseCertificateStorage() {
		return useCertificateStorage;
	}
	public void setUseCertificateStorage(boolean useCertificateStorage) {
		this.useCertificateStorage = useCertificateStorage;
	}
	public String getSubjectAltName() {
		return subjectAltName;
	}
	public void setSubjectAltName(String subjectaltname) {
		this.subjectAltName = subjectaltname;
	}
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public long getCrlIssueInterval() {
		return crlIssueInterval;
	}
	public void setCrlIssueInterval(long crlIssueInterval) {
		this.crlIssueInterval = crlIssueInterval;
	}
	public long getCrlOverlapTime() {
		return crlOverlapTime;
	}
	public void setCrlOverlapTime(long crlOverlapTime) {
		this.crlOverlapTime = crlOverlapTime;
	}

	public String getAvailablePublisherValues() {
		return availablePublisherValues;
	}
	public void setAvailablePublisherValues(String availablePublisherValues) {
		this.availablePublisherValues = availablePublisherValues;
	}

	public String getAuthorityInformationAccess() {
		return authorityInformationAccess;
	}
	public void setAuthorityInformationAccess(String authorityInformationAccess) {
		this.authorityInformationAccess = authorityInformationAccess;
	}
	public String getNameConstraintsPermitted() {
		return nameConstraintsPermitted;
	}
	public void setNameConstraintsPermitted(String nameConstraintsPermitted) {
		this.nameConstraintsPermitted = nameConstraintsPermitted;
	}
	public String getNameConstraintsExcluded() {
		return nameConstraintsExcluded;
	}
	public void setNameConstraintsExcluded(String nameConstraintsExcluded) {
		this.nameConstraintsExcluded = nameConstraintsExcluded;
	}
	public String getCaDefinedFreshestCrl() {
		return caDefinedFreshestCrl;
	}
	public void setCaDefinedFreshestCrl(String caDefinedFreshestCrl) {
		this.caDefinedFreshestCrl = caDefinedFreshestCrl;
	}

	public boolean isUseCrlDistpointonCrl() {
		return useCrlDistpointonCrl;
	}
	public void setUseCrlDistpointonCrl(boolean useCrlDistpointonCrl) {
		this.useCrlDistpointonCrl = useCrlDistpointonCrl;
	}

	public boolean isIncludeInHealthCheck() {
		return includeInHealthCheck;
	}
	public void setIncludeInHealthCheck(boolean includeInHealthCheck) {
		this.includeInHealthCheck = includeInHealthCheck;
	}
	public boolean isServiceOcspActive() {
		return serviceOcspActive;
	}
	public void setServiceOcspActive(boolean serviceOcspActive) {
		this.serviceOcspActive = serviceOcspActive;
	}
	public boolean isServiceCmsActive() {
		return serviceCmsActive;
	}
	public void setServiceCmsActive(boolean serviceCmsActive) {
		this.serviceCmsActive = serviceCmsActive;
	}
	public String getSharedCmpRaSecret() {
		return sharedCmpRaSecret;
	}
	public void setSharedCmpRaSecret(String sharedCmpRaSecret) {
		this.sharedCmpRaSecret = sharedCmpRaSecret;
	}
	public int getCryptoTokenId() {
		return cryptoTokenId;
	}
	public void setCryptoTokenId(int cryptoTokenId) {
		this.cryptoTokenId = cryptoTokenId;
	}
	public String getKeyAliasCertSignKey() {
		return keyAliasCertSignKey;
	}
	public void setKeyAliasCertSignKey(String keyAliasCertSignKey) {
		this.keyAliasCertSignKey = keyAliasCertSignKey;
	}
	public String getKeyAliasCrlSignKey() {
		return keyAliasCrlSignKey;
	}
	public void setKeyAliasCrlSignKey(String keyAliasCrlSignKey) {
		this.keyAliasCrlSignKey = keyAliasCrlSignKey;
	}
	public String getKeyAliasDefaultKey() {
		return keyAliasDefaultKey;
	}
	public void setKeyAliasDefaultKey(String keyAliasDefaultKey) {
		this.keyAliasDefaultKey = keyAliasDefaultKey;
	}
	public String getKeyAliasHardTokenEncryptKey() {
		return keyAliasHardTokenEncryptKey;
	}
	public void setKeyAliasHardTokenEncryptKey(String keyAliasHardTokenEncryptKey) {
		this.keyAliasHardTokenEncryptKey = keyAliasHardTokenEncryptKey;
	}
	public String getKeyAliasKeyEncryptKey() {
		return keyAliasKeyEncryptKey;
	}
	public void setKeyAliasKeyEncryptKey(String keyAliasKeyEncryptKey) {
		this.keyAliasKeyEncryptKey = keyAliasKeyEncryptKey;
	}
	public String getKeyAliasKeyTestKey() {
		return keyAliasKeyTestKey;
	}
	public void setKeyAliasKeyTestKey(String keyAliasKeyTestKey) {
		this.keyAliasKeyTestKey = keyAliasKeyTestKey;
	}
	public boolean isUseAuthorityKeyIdentifier() {
		return useAuthorityKeyIdentifier;
	}
	public void setUseAuthorityKeyIdentifier(boolean useAuthorityKeyIdentifier) {
		this.useAuthorityKeyIdentifier = useAuthorityKeyIdentifier;
	}
	public boolean isAuthorityKeyIdentifierCritical() {
		return authorityKeyIdentifierCritical;
	}
	public void setAuthorityKeyIdentifierCritical(boolean authorityKeyIdentifierCritical) {
		this.authorityKeyIdentifierCritical = authorityKeyIdentifierCritical;
	}
	public long getCrlPeriod() {
		return crlPeriod;
	}
	public void setCrlPeriod(long crlPeriod) {
		this.crlPeriod = crlPeriod;
	}
	public long getDeltaCrlPeriod() {
		return deltaCrlPeriod;
	}
	public void setDeltaCrlPeriod(long deltaCrlPeriod) {
		this.deltaCrlPeriod = deltaCrlPeriod;
	}
	public boolean isUseCrlNumber() {
		return useCrlNumber;
	}
	public void setUseCrlNumber(boolean useCrlNumber) {
		this.useCrlNumber = useCrlNumber;
	}
	public boolean isCrlNumberCritical() {
		return crlNumberCritical;
	}
	public void setCrlNumberCritical(boolean crlNumberCritical) {
		this.crlNumberCritical = crlNumberCritical;
	}
	public String getDefaultCrlDistpoint() {
		return defaultCrlDistpoint;
	}
	public void setDefaultCrlDistpoint(String defaultCrlDistpoint) {
		this.defaultCrlDistpoint = defaultCrlDistpoint;
	}
	public String getDefaultCrlIssuer() {
		return defaultCrlIssuer;
	}
	public void setDefaultCrlIssuer(String defaultCrlIssuer) {
		this.defaultCrlIssuer = defaultCrlIssuer;
	}
	public String getDefaultOcspLocator() {
		return defaultOcspLocator;
	}
	public void setDefaultOcspLocator(String defaultOcspLocator) {
		this.defaultOcspLocator = defaultOcspLocator;
	}
	public boolean isUseUtf8PolicyText() {
		return useUtf8PolicyText;
	}
	public void setUseUtf8PolicyText(boolean useUtf8PolicyText) {
		this.useUtf8PolicyText = useUtf8PolicyText;
	}
	public boolean isUsePrintableStringSubjectDn() {
		return usePrintableStringSubjectDn;
	}
	public void setUsePrintableStringSubjectDn(boolean usePrintableStringSubjectDn) {
		this.usePrintableStringSubjectDn = usePrintableStringSubjectDn;
	}
	public boolean isUseLdapDnOrder() {
		return useLdapDnOrder;
	}
	public void setUseLdapDnOrder(boolean useLdapDnOrder) {
		this.useLdapDnOrder = useLdapDnOrder;
	}
	public boolean isCrlDistpointonCrlCritical() {
		return crlDistpointonCrlCritical;
	}
	public void setCrlDistpointonCrlCritical(boolean crlDistpointonCrlCritical) {
		this.crlDistpointonCrlCritical = crlDistpointonCrlCritical;
	}


//	public String getCaName() {
//		return caName;
//	}
//	public void setCaName(String caName) {
//		this.caName = caName;
//	}
//	public String getCaType() {
//		return caType;
//	}
//	public void setCaType(String caType) {
//		this.caType = caType;
//	}
//	public String getAlgorithm() {
//		return algorithm;
//	}
//	public void setAlgorithm(String algorithm) {
//		this.algorithm = algorithm;
//	}
//	public String getSubjectAlternativeName() {
//		return subjectAlternativeName;
//	}
//	public void setSubjectAlternativeName(String subjectAlternativeName) {
//		this.subjectAlternativeName = subjectAlternativeName;
//	}
//	public String getCryptoTokenId() {
//		return cryptoTokenId;
//	}
//	public void setCryptoTokenId(String cryptoTokenId) {
//		this.cryptoTokenId = cryptoTokenId;
//	}
//	public String getSubjectDn() {
//		return subjectDn;
//	}
//	public void setSubjectDn(String subjectDn) {
//		this.subjectDn = subjectDn;
//	}
//	public String getIssuerCaId() {
//		return issuerCaId;
//	}
//	public void setIssuerCaId(String issuerCaId) {
//		this.issuerCaId = issuerCaId;
//	}
//	public String getCertPolicyId() {
//		return certPolicyId;
//	}
//	public void setCertPolicyId(String certPolicyId) {
//		this.certPolicyId = certPolicyId;
//	}
//	public String getAkid() {
//		return akid;
//	}
//	public void setAkid(String akid) {
//		this.akid = akid;
//	}
//	public String getCrlNum() {
//		return crlNum;
//	}
//	public void setCrlNum(String crlNum) {
//		this.crlNum = crlNum;
//	}
//	public String getAlias() {
//		return (alias==null || "".equals(alias) )?   "signkey":alias;
//	}
//	public void setAlias(String alias) {
//		this.alias = alias;
//	}
//	public String getPassword() {
//		return (password==null || "".equals(password) )?   "foo123":password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}


}
