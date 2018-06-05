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

package org.bcia.javachain.ca.szca.admin.ra.vo;

import java.io.Serializable;

public class ViewcertificateVo implements  Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -942856053913000175L;
	private String username;
	private String usernameVal;
	private String revoke;
	private String revokeVal;
	private String certificatenr;
	private String certificatenrVal;
	private String cert_typeversion;
	private String cert_typeversionVal;
	private String cert_serialnumber;
	private String cert_serialnumberVal;
	private String cert_issuerdn;
	private String cert_issuerdnVal;
	private String cert_validfrom;
	private String cert_validfromVal;
	private String cert_validto;
	private String cert_validtoVal;
	private String cert_subjectdn;
	private String cert_subjectdnVal;
	private String ext_abbr_subjectaltname;
	private String ext_abbr_subjectaltnameVal;
	private String ext_abbr_subjectdirattrs;
	private String ext_abbr_subjectdirattrsVal;
	private String cert_publickey;
	private String cert_publickeyVal;
	private String ext_abbr_basicconstraints;
	private String ext_abbr_basicconstraintsVal;
	private String ext_abbr_keyusage;
	private String ext_abbr_keyusageVal;
	private String ext_abbr_extendedkeyusage;
	private String ext_abbr_extendedkeyusageVal;
	private String ext_abbr_nameconstraints;
	private String ext_abbr_nameconstraintsVal;
	private String ext_abbr_qcstatements;
	private String ext_abbr_qcstatementsVal;
	private String ext_certificate_transparency_scts;
	private String ext_certificate_transparency_sctsVal;
	private String signaturealgorithm;
	private String signaturealgorithmVal;
	private String fingerprint_sha256;
	private String  fingerprint_sha256Val;
	private String fingerprint_sha1;
	private String  fingerprint_sha1Val;
	private String revoked;
	private String revokedVal;
	private String revocationdate;
	private String revocationdateVal;
	private String revocationreasons;
	private String revocationreasonsVal;

 	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsernameVal() {
		return usernameVal;
	}
	public void setUsernameVal(String usernameVal) {
		this.usernameVal = usernameVal;
	}
	public String getRevoke() {
		return revoke;
	}
	public void setRevoke(String revoke) {
		this.revoke = revoke;
	}
	public String getRevokeVal() {
		return revokeVal;
	}
	public void setRevokeVal(String revokeVal) {
		this.revokeVal = revokeVal;
	}
	public String getCertificatenr() {
		return certificatenr;
	}
	public void setCertificatenr(String certificatenr) {
		this.certificatenr = certificatenr;
	}
	public String getCertificatenrVal() {
		return certificatenrVal;
	}
	public void setCertificatenrVal(String certificatenrVal) {
		this.certificatenrVal = certificatenrVal;
	}
	public String getCert_typeversion() {
		return cert_typeversion;
	}
	public void setCert_typeversion(String cert_typeversion) {
		this.cert_typeversion = cert_typeversion;
	}
	public String getCert_typeversionVal() {
		return cert_typeversionVal;
	}
	public void setCert_typeversionVal(String cert_typeversionVal) {
		this.cert_typeversionVal = cert_typeversionVal;
	}
	public String getCert_serialnumber() {
		return cert_serialnumber;
	}
	public void setCert_serialnumber(String cert_serialnumber) {
		this.cert_serialnumber = cert_serialnumber;
	}
	public String getCert_serialnumberVal() {
		return cert_serialnumberVal;
	}
	public void setCert_serialnumberVal(String cert_serialnumberVal) {
		this.cert_serialnumberVal = cert_serialnumberVal;
	}
	public String getCert_issuerdn() {
		return cert_issuerdn;
	}
	public void setCert_issuerdn(String cert_issuerdn) {
		this.cert_issuerdn = cert_issuerdn;
	}
	public String getCert_issuerdnVal() {
		return cert_issuerdnVal;
	}
	public void setCert_issuerdnVal(String cert_issuerdnVal) {
		this.cert_issuerdnVal = cert_issuerdnVal;
	}
	public String getCert_validfrom() {
		return cert_validfrom;
	}
	public void setCert_validfrom(String cert_validfrom) {
		this.cert_validfrom = cert_validfrom;
	}
	public String getCert_validfromVal() {
		return cert_validfromVal;
	}
	public void setCert_validfromVal(String cert_validfromVal) {
		this.cert_validfromVal = cert_validfromVal;
	}
	public String getCert_validto() {
		return cert_validto;
	}
	public void setCert_validto(String cert_validto) {
		this.cert_validto = cert_validto;
	}
	public String getCert_validtoVal() {
		return cert_validtoVal;
	}
	public void setCert_validtoVal(String cert_validtoVal) {
		this.cert_validtoVal = cert_validtoVal;
	}
	public String getCert_subjectdn() {
		return cert_subjectdn;
	}
	public void setCert_subjectdn(String cert_subjectdn) {
		this.cert_subjectdn = cert_subjectdn;
	}
	public String getCert_subjectdnVal() {
		return cert_subjectdnVal;
	}
	public void setCert_subjectdnVal(String cert_subjectdnVal) {
		this.cert_subjectdnVal = cert_subjectdnVal;
	}
	public String getExt_abbr_subjectaltname() {
		return ext_abbr_subjectaltname;
	}
	public void setExt_abbr_subjectaltname(String ext_abbr_subjectaltname) {
		this.ext_abbr_subjectaltname = ext_abbr_subjectaltname;
	}
	public String getExt_abbr_subjectaltnameVal() {
		return ext_abbr_subjectaltnameVal;
	}
	public void setExt_abbr_subjectaltnameVal(String ext_abbr_subjectaltnameVal) {
		this.ext_abbr_subjectaltnameVal = ext_abbr_subjectaltnameVal;
	}
	public String getExt_abbr_subjectdirattrs() {
		return ext_abbr_subjectdirattrs;
	}
	public void setExt_abbr_subjectdirattrs(String ext_abbr_subjectdirattrs) {
		this.ext_abbr_subjectdirattrs = ext_abbr_subjectdirattrs;
	}
	public String getExt_abbr_subjectdirattrsVal() {
		return ext_abbr_subjectdirattrsVal;
	}
	public void setExt_abbr_subjectdirattrsVal(String ext_abbr_subjectdirattrsVal) {
		this.ext_abbr_subjectdirattrsVal = ext_abbr_subjectdirattrsVal;
	}
	public String getCert_publickey() {
		return cert_publickey;
	}
	public void setCert_publickey(String cert_publickey) {
		this.cert_publickey = cert_publickey;
	}
	public String getCert_publickeyVal() {
		return cert_publickeyVal;
	}
	public void setCert_publickeyVal(String cert_publickeyVal) {
		this.cert_publickeyVal = cert_publickeyVal;
	}
	public String getExt_abbr_basicconstraints() {
		return ext_abbr_basicconstraints;
	}
	public void setExt_abbr_basicconstraints(String ext_abbr_basicconstraints) {
		this.ext_abbr_basicconstraints = ext_abbr_basicconstraints;
	}
	public String getExt_abbr_basicconstraintsVal() {
		return ext_abbr_basicconstraintsVal;
	}
	public void setExt_abbr_basicconstraintsVal(String ext_abbr_basicconstraintsVal) {
		this.ext_abbr_basicconstraintsVal = ext_abbr_basicconstraintsVal;
	}
	public String getExt_abbr_keyusage() {
		return ext_abbr_keyusage;
	}
	public void setExt_abbr_keyusage(String ext_abbr_keyusage) {
		this.ext_abbr_keyusage = ext_abbr_keyusage;
	}
	public String getExt_abbr_keyusageVal() {
		return ext_abbr_keyusageVal;
	}
	public void setExt_abbr_keyusageVal(String ext_abbr_keyusageVal) {
		this.ext_abbr_keyusageVal = ext_abbr_keyusageVal;
	}
	public String getExt_abbr_extendedkeyusage() {
		return ext_abbr_extendedkeyusage;
	}
	public void setExt_abbr_extendedkeyusage(String ext_abbr_extendedkeyusage) {
		this.ext_abbr_extendedkeyusage = ext_abbr_extendedkeyusage;
	}
	public String getExt_abbr_extendedkeyusageVal() {
		return ext_abbr_extendedkeyusageVal;
	}
	public void setExt_abbr_extendedkeyusageVal(String ext_abbr_extendedkeyusageVal) {
		this.ext_abbr_extendedkeyusageVal = ext_abbr_extendedkeyusageVal;
	}
	public String getExt_abbr_nameconstraints() {
		return ext_abbr_nameconstraints;
	}
	public void setExt_abbr_nameconstraints(String ext_abbr_nameconstraints) {
		this.ext_abbr_nameconstraints = ext_abbr_nameconstraints;
	}
	public String getExt_abbr_nameconstraintsVal() {
		return ext_abbr_nameconstraintsVal;
	}
	public void setExt_abbr_nameconstraintsVal(String ext_abbr_nameconstraintsVal) {
		this.ext_abbr_nameconstraintsVal = ext_abbr_nameconstraintsVal;
	}
	public String getExt_abbr_qcstatements() {
		return ext_abbr_qcstatements;
	}
	public void setExt_abbr_qcstatements(String ext_abbr_qcstatements) {
		this.ext_abbr_qcstatements = ext_abbr_qcstatements;
	}
	public String getExt_abbr_qcstatementsVal() {
		return ext_abbr_qcstatementsVal;
	}
	public void setExt_abbr_qcstatementsVal(String ext_abbr_qcstatementsVal) {
		this.ext_abbr_qcstatementsVal = ext_abbr_qcstatementsVal;
	}
	public String getExt_certificate_transparency_scts() {
		return ext_certificate_transparency_scts;
	}
	public void setExt_certificate_transparency_scts(String ext_certificate_transparency_scts) {
		this.ext_certificate_transparency_scts = ext_certificate_transparency_scts;
	}
	public String getExt_certificate_transparency_sctsVal() {
		return ext_certificate_transparency_sctsVal;
	}
	public void setExt_certificate_transparency_sctsVal(String ext_certificate_transparency_sctsVal) {
		this.ext_certificate_transparency_sctsVal = ext_certificate_transparency_sctsVal;
	}
	public String getSignaturealgorithm() {
		return signaturealgorithm;
	}
	public void setSignaturealgorithm(String signaturealgorithm) {
		this.signaturealgorithm = signaturealgorithm;
	}
	public String getSignaturealgorithmVal() {
		return signaturealgorithmVal;
	}
	public void setSignaturealgorithmVal(String signaturealgorithmVal) {
		this.signaturealgorithmVal = signaturealgorithmVal;
	}
	public String getFingerprint_sha256() {
		return fingerprint_sha256;
	}
	public void setFingerprint_sha256(String fingerprint_sha256) {
		this.fingerprint_sha256 = fingerprint_sha256;
	}
	public String getFingerprint_sha256Val() {
		return fingerprint_sha256Val;
	}
	public void setFingerprint_sha256Val(String fingerprint_sha256Val) {
		this.fingerprint_sha256Val = fingerprint_sha256Val;
	}
	public String getFingerprint_sha1() {
		return fingerprint_sha1;
	}
	public void setFingerprint_sha1(String fingerprint_sha1) {
		this.fingerprint_sha1 = fingerprint_sha1;
	}
	public String getFingerprint_sha1Val() {
		return fingerprint_sha1Val;
	}
	public void setFingerprint_sha1Val(String fingerprint_sha1Val) {
		this.fingerprint_sha1Val = fingerprint_sha1Val;
	}
	public String getRevoked() {
		return revoked;
	}
	public void setRevoked(String revoked) {
		this.revoked = revoked;
	}
	public String getRevokedVal() {
		return revokedVal;
	}
	public void setRevokedVal(String revokedVal) {
		this.revokedVal = revokedVal;
	}
	public String getRevocationdate() {
		return revocationdate;
	}
	public void setRevocationdate(String revocationdate) {
		this.revocationdate = revocationdate;
	}
	public String getRevocationreasons() {
		return revocationreasons;
	}
	public void setRevocationreasons(String revocationreasons) {
		this.revocationreasons = revocationreasons;
	}
	public String getRevocationdateVal() {
		return revocationdateVal;
	}
	public void setRevocationdateVal(String revocationdateVal) {
		this.revocationdateVal = revocationdateVal;
	}
	public String getRevocationreasonsVal() {
		return revocationreasonsVal;
	}
	public void setRevocationreasonsVal(String revocationreasonsVal) {
		this.revocationreasonsVal = revocationreasonsVal;
	}
	
	
 
}
