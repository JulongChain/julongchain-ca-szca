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

package org.bcia.javachain.ca.szca.publicweb.api.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.bcia.javachain.ca.szca.publicweb.BciaGenerateToken;
import org.bcia.javachain.ca.szca.publicweb.BciaRequestHelper;
import org.bcia.javachain.ca.szca.publicweb.controller.EnrollCertForm;
import org.cesecore.ErrorCode;
import org.cesecore.authentication.tokens.AlwaysAllowLocalAuthenticationToken;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authentication.tokens.PublicWebPrincipal;
import org.cesecore.authentication.tokens.UsernamePrincipal;
import org.cesecore.certificates.ca.CADoesntExistsException;
import org.cesecore.certificates.ca.CAInfo;
import org.cesecore.certificates.certificateprofile.CertificateProfile;
//import org.cesecore.certificates.certificateprofile.CertificateProfileSessionLocal;
import org.cesecore.certificates.endentity.EndEntityConstants;
import org.cesecore.certificates.endentity.EndEntityInformation;
import org.cesecore.certificates.util.AlgorithmConstants;
import org.cesecore.keys.util.KeyTools;
import org.cesecore.util.Base64;
import org.cesecore.util.CertTools;
import org.cesecore.util.FileTools;
import org.cesecore.util.StringTools;
import org.ejbca.config.GlobalConfiguration;
import org.ejbca.config.WebConfiguration;
import org.ejbca.core.EjbcaException;
//import org.ejbca.core.ejb.ca.auth.EndEntityAuthenticationSessionLocal;
//import org.ejbca.core.ejb.ca.sign.SignSessionLocal;
//import org.ejbca.core.ejb.keyrecovery.KeyRecoverySessionLocal;
//import org.ejbca.core.ejb.ra.EndEntityAccessSessionLocal;
//import org.ejbca.core.ejb.ra.EndEntityManagementSessionLocal;
//import org.ejbca.core.ejb.ra.raadmin.EndEntityProfileSessionLocal;
import org.ejbca.core.model.InternalEjbcaResources;
import org.ejbca.core.model.SecConst;
import org.ejbca.core.model.ra.raadmin.EndEntityProfile;
import org.ejbca.ui.web.CertificateRequestResponse;
import org.ejbca.ui.web.CertificateResponseType;
import org.ejbca.ui.web.RequestHelper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

 

 

@Repository
public class BciaRequestInstance {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(BciaRequestInstance.class);
    private static final InternalEjbcaResources intres = InternalEjbcaResources.getInstance();
	
    /** Max size of request parameters that we will receive */
    private static final int REQUEST_MAX_SIZE = 10000;
    
    private class IncomatibleTokenTypeException extends EjbcaException {
        private static final long serialVersionUID = 5435852400591856793L;

        public IncomatibleTokenTypeException() {
            super(ErrorCode.BAD_USER_TOKEN_TYPE);
        }
    }
 
	@Autowired
	private org.bcia.javachain.ca.szca.common.bcca.core.ejb.ca.auth.EndEntityAuthenticationSessionLocal authenticationSession;
	@Autowired
	private org.bcia.javachain.ca.szca.common.cesecore.certificates.ca.CaSessionLocal caSession;
	@Autowired
	private org.bcia.javachain.ca.szca.common.cesecore.certificates.certificateprofile.CertificateProfileSessionLocal certificateProfileSession;
	@Autowired
	private org.bcia.javachain.ca.szca.common.bcca.core.ejb.ra.raadmin.EndEntityProfileSessionLocal endEntityProfileSession;
	@Autowired
	private org.bcia.javachain.ca.szca.common.bcca.core.ejb.keyrecovery.KeyRecoverySessionLocal keyRecoverySession;
	@Autowired
	private org.bcia.javachain.ca.szca.common.bcca.core.ejb.ca.sign.SignSessionLocal signSession;
	@Autowired
	private org.bcia.javachain.ca.szca.common.bcca.core.ejb.ra.EndEntityManagementSessionLocal endEntityManagementSession;
	@Autowired
	private org.bcia.javachain.ca.szca.common.cesecore.configuration.GlobalConfigurationSession globalConfigurationSession;
	@Autowired
	private org.bcia.javachain.ca.szca.common.bcca.core.ejb.ra.EndEntityAccessSessionLocal endEntityAccessSession;
	 
    @Transactional
    public BciaRequestResult doPost(HttpServletRequest request,EnrollCertForm form) throws IOException, ServletException {
 		//ServletDebug debug = new ServletDebug(request, response);
		//Logger  debug = null;
    	BciaRequestResult result = new BciaRequestResult();
    	String  message=null;
		boolean success =false;
		int tokentype = SecConst.TOKEN_SOFT_BROWSERGEN;
	 
    	byte[] resultByte = null;
    	
    	boolean usekeyrecovery = false;

		
		
		 String password=form.getPassword();
		 String username=form.getUsername();
//		 String openvpn=null;
		 String certProfile=form.getCertProfile();
 		 String keylength="2048";
		 String keyalg=AlgorithmConstants.KEYALGORITHM_RSA;
		// Possibility to override by code and ignore parameters
	//	 String keylengthstring=null;
//		 String keyalgstring=null;
		
		 
		
//		RequestHelper.setDefaultCharacterEncoding(request);
//		String iErrorMessage = null;
//		Map params = null;
		try {
//			setParameters(request);

            /*********************************************************************
             ** If parameters are not set by Set... they must be
             ** provided by request
             *********************************************************************/
//			params = this.setParameters(request);
			
			//若pkcs10有内容，则统一返回p7，否则返回p12
			byte[] pkcs10 = null;
			if(form.getCsrFile()!=null)
				pkcs10 = form.getCsrFile().getBytes();
			if((pkcs10==null || pkcs10.length<1 )&& form.getCsr()!=null)
				pkcs10 = form.getCsr().getBytes();
			 
 
            final AuthenticationToken administrator = new AlwaysAllowLocalAuthenticationToken(new PublicWebPrincipal("RequestInstance", request.getRemoteAddr()));

 			BciaRequestHelper helper = new BciaRequestHelper(administrator);

			log.info(intres.getLocalizedMessage("certreq.receivedcertreq", username, request.getRemoteAddr()));
//			debug.print("Username: " + HTMLTools.htmlescape(username));

			// Check user
	

			usekeyrecovery = ((GlobalConfiguration) globalConfigurationSession.getCachedConfiguration(GlobalConfiguration.GLOBAL_CONFIGURATION_ID)).getEnableKeyRecovery();

			EndEntityInformation data = endEntityAccessSession.findUser(administrator, username);

			if (data == null) {
				//throw new ObjectNotFoundException(String.format("指定实体[%s]不存在。",username));
				throw new Exception(String.format("指定实体[%s]不存在。",username));
			}

			boolean savekeys = data.getKeyRecoverable() && usekeyrecovery &&  (data.getStatus() != EndEntityConstants.STATUS_KEYRECOVERY);
			boolean loadkeys = (data.getStatus() == EndEntityConstants.STATUS_KEYRECOVERY) && usekeyrecovery;

			int endEntityProfileId = data.getEndEntityProfileId();
			int certificateProfileId = data.getCertificateProfileId();
			EndEntityProfile endEntityProfile = endEntityProfileSession.getEndEntityProfile(endEntityProfileId);
			boolean reusecertificate = endEntityProfile.getReUseKeyRecoveredCertificate();
			// Set a new certificate profile, if we have requested one specific
			if (StringUtils.isNotEmpty(certProfile)) {
				boolean clearpwd = StringUtils.isNotEmpty(data.getPassword());
				int id = certificateProfileSession.getCertificateProfileId(certProfile);
				// Change the value if there exists a certprofile with the requested name, and it is not the same as 
				// the one already registered to be used by default
                if (id > 0) {
					if (id != certificateProfileId) {
						// Check if it is in allowed profiles in the entity profile
						Collection<String> c = endEntityProfile.getAvailableCertificateProfileIds();
						if (c.contains(String.valueOf(id))) {
							data.setCertificateProfileId(certificateProfileId);
							// This admin can be the public web user, which may not be allowed to change status,
							// this is a bit ugly, but what can a man do...
							AuthenticationToken tempadmin = new AlwaysAllowLocalAuthenticationToken(new UsernamePrincipal("RequestInstance"+request.getRemoteAddr()));
							endEntityManagementSession.changeUser(tempadmin, data, clearpwd);            		            			
						} else {
							String defaultCertificateProfileName = certificateProfileSession.getCertificateProfileName(certificateProfileId);
							log.info(intres.getLocalizedMessage("certreq.badcertprofile", certProfile, defaultCertificateProfileName));
						}
					}
				} else {
					String defaultCertificateProfileName = certificateProfileSession.getCertificateProfileName(certificateProfileId);
					log.info(intres.getLocalizedMessage("certreq.nosuchcertprofile", certProfile, defaultCertificateProfileName));
				}
			}

			// get users Token Type.
			tokentype = data.getTokenType();
			//GenerateToken tgen = new GenerateToken(authenticationSession, endEntityAccessSession, endEntityManagementSession, caSession, keyRecoverySession, signSession);
			BciaGenerateToken tgen = new BciaGenerateToken(authenticationSession, endEntityAccessSession, endEntityManagementSession, caSession, keyRecoverySession, signSession);
			
			//不再根据数据库来决定输出格式，若有CSR内容，优先使用
			if(pkcs10!=null && pkcs10.length>1)
				tokentype = SecConst.TOKEN_SOFT_BROWSERGEN;
			else
				tokentype = form.getTokenType();
			
			if(tokentype == SecConst.TOKEN_SOFT_P12){
			    // If the user is configured for a server generated token, but submitted a CSR, it is most likely an administrative error.
			    // The RA admin should probably have set token type usergenerated instead.
//			    if (hasCSRInRequest(params)) {
//			        throw new IncomatibleTokenTypeException();
//			    }
				KeyStore ks = tgen.generateOrKeyRecoverToken(administrator, username, password, data.getCAId(), keylength, keyalg, false, loadkeys, savekeys, reusecertificate, endEntityProfileId);
//				if (StringUtils.equals(openvpn, "on")) {            	  
//					sendOpenVPNToken(ks, username, password, response);
//				} else {
//					sendP12Token(ks, username, password, response);
//				}
				 
					ByteArrayOutputStream buffer = new ByteArrayOutputStream();
					ks.store(buffer, password.toCharArray());
					resultByte =buffer.toByteArray();
			}
			if(tokentype == SecConst.TOKEN_SOFT_JKS){
                // If the user is configured for a server generated token, but submitted a CSR, it is most likely an administrative error.
                // The RA admin should probably have set token type usergenerated instead.
//                if (hasCSRInRequest(params)) {
//                    throw new IncomatibleTokenTypeException();
//                }
				KeyStore ks = tgen.generateOrKeyRecoverToken(administrator, username, password, data.getCAId(), keylength, keyalg, true, loadkeys, savekeys, reusecertificate, endEntityProfileId);
				//sendJKSToken(ks, username, password, response);
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				ks.store(buffer, password.toCharArray());
				resultByte =buffer.toByteArray();
			}
			if(tokentype == SecConst.TOKEN_SOFT_PEM){
                // If the user is configured for a server generated token, but submitted a CSR, it is most likely an administrative error.
                // The RA admin should probably have set token type usergenerated instead.
//                if (hasCSRInRequest(params)) {
//                    throw new IncomatibleTokenTypeException();
//                }
				KeyStore ks = tgen.generateOrKeyRecoverToken(administrator, username, password, data.getCAId(), keylength, keyalg, false, loadkeys, savekeys, reusecertificate, endEntityProfileId);
				//sendPEMTokens(ks, username, password, response);
				resultByte = KeyTools.getSinglePemFromKeyStore(ks, password.toCharArray());
				 
			}
			if(tokentype == SecConst.TOKEN_SOFT_BROWSERGEN){
				//根据CSR生成Certificate
				byte[] reqBytes = pkcs10;//getParameter(params,"pkcs10").getBytes();
				if(reqBytes==null||reqBytes.length<1)
					throw new Exception("无效的CSR");
				//resultByte=helper.pkcs10CertRequest(signSession, caSession, reqBytes, username, password, CertificateResponseType.ENCODED_PKCS7).getEncoded();
				//byte[] b64cert=helper.pkcs10CertRequest(signSession, caSession, reqBytes, username, password, CertificateResponseType.ENCODED_PKCS7).getEncoded();
				CertificateRequestResponse resp = helper.pkcs10CertRequest(signSession, caSession, reqBytes, username, password, CertificateResponseType.ENCODED_PKCS7);
				//P7b格式的证书链
				//resultByte = resp.getEncoded();
				
				//证书
				resultByte = Base64.encode(resp.getCertificate().getEncoded(), true);
				// CertificateFactory cf = CertificateFactory.getInstance("X.509");
				//将文件以文件流的形式读入证书类Certificate中
				 // Certificate cert = cf.generateCertificate(bin);
				  
				// System.out.println(">>>>>>>>>>>>"+cert);
				 
			}
			success  =true;
		}catch (Exception e) {
			e.printStackTrace();
			success  =false;
			message =e.getClass().toString()+":"+ e.getMessage();
		}
		/*
		} catch (AuthStatusException ase) {
			iErrorMessage = intres.getLocalizedMessage("certreq.wrongstatus");
        } catch (ObjectNotFoundException oe) {
            // Same error message for user not found and wrong password
            iErrorMessage = intres.getLocalizedMessage("ra.wrongusernameorpassword");
            // But debug log the real issue if needed
            if (log.isDebugEnabled()) {
                log.debug(intres.getLocalizedMessage("ra.errorentitynotexist", username));
            }
		} catch (AuthLoginException ale) {
			iErrorMessage = intres.getLocalizedMessage("ra.wrongusernameorpassword");
			log.info(iErrorMessage + " - username: " + username);
        } catch (IncomatibleTokenTypeException re) {
            iErrorMessage = intres.getLocalizedMessage("certreq.csrreceivedforservergentoken");
		} catch (SignRequestException re) {
			iErrorMessage = intres.getLocalizedMessage("certreq.invalidreq", re.getMessage());
		} catch (SignRequestSignatureException se) {
			String iMsg = intres.getLocalizedMessage("certreq.invalidsign");
			log.error(iMsg, se);
//			debug.printMessage(iMsg);
//			debug.printDebugInfo();
//			return;
		} catch (ArrayIndexOutOfBoundsException ae) {
			iErrorMessage = intres.getLocalizedMessage("certreq.invalidreq");
		} catch (DecoderException de) {
			iErrorMessage = intres.getLocalizedMessage("certreq.invalidreq");
		} catch (IllegalKeyException e) {
			iErrorMessage = intres.getLocalizedMessage("certreq.invalidkey", e.getMessage());
		} catch (CryptoTokenOfflineException ctoe) {
		    String ctoeMsg = ctoe.getMessage();
		    for (Throwable e = ctoe; e != null; e = e.getCause()) {
		        //We can not do "if (e instanceof sun.security.pkcs11.wrapper.PKCS11Exception)" here because these classes are not guaranteed to exist on all platforms
                if (e.getClass().getName().contains("PKCS11")) {
		            ctoeMsg = "PKCS11 error "+e.getMessage();
		            break;
		        }
		    }
            iErrorMessage = intres.getLocalizedMessage("certreq.catokenoffline", ctoeMsg);
		} catch (AuthorizationDeniedException e) {
		    iErrorMessage = intres.getLocalizedMessage("certreq.authorizationdenied") + e.getLocalizedMessage();
		} catch(CertificateCreateException e) {
		    if (e.getErrorCode()!=null && e.getErrorCode().equals(ErrorCode.CERTIFICATE_WITH_THIS_SUBJECTDN_ALREADY_EXISTS_FOR_ANOTHER_USER)) {
		        iErrorMessage = e.getLocalizedMessage();
		    } else {
//		        debug.takeCareOfException(e);
//	            debug.printDebugInfo();
		    }
		} catch (Exception e) {
			e.printStackTrace();
			Throwable e1 = e.getCause();
			if (e1 instanceof CryptoTokenOfflineException) {
				String iMsg = intres.getLocalizedMessage("certreq.catokenoffline", e1.getMessage());
				// this is already logged as an error, so no need to log it one more time
//				debug.printMessage(iMsg);
//				debug.printDebugInfo();
//				return;				
			} else {
				if (e1 == null) { e1 = e; }
                String iMsg = intres.getLocalizedMessage("certreq.errorgeneral", e1.getMessage());
                if (log.isDebugEnabled()) {
                    log.debug(iMsg, e);
                }
				iMsg = intres.getLocalizedMessage("certreq.parameters", e1.getMessage());
//				debug.print(iMsg + ":\n");
				@SuppressWarnings("unchecked")
                Set<String> paramNames = params.keySet();
				for(String name : paramNames) {
					String parameter = getParameter(params,name);
                    if (!StringUtils.equals(name, "password")) {
//                        debug.print(HTMLTools.htmlescape(name) + ": '" + HTMLTools.htmlescape(parameter) + "'\n");
                    } else {
//                        debug.print(HTMLTools.htmlescape(name) + ": <hidden>\n");
                    }
				}
//				debug.takeCareOfException(e);
//				debug.printDebugInfo();
			}
		}
		if (iErrorMessage != null) {
            if (log.isDebugEnabled()) {
                log.debug(iErrorMessage);
            }
//			debug.printMessage(iErrorMessage);
//			debug.printDebugInfo();
//			return;
		}*/
		result.setResultData(resultByte);
		result.setMessage(message);
		result.setSuccess(success);
		result.setTokenType(tokentype); 
		return result;
	}

    /** Check is a request to this servlet contains a Certificate Signing Request (CSR) in any
     * format that we support.
     */
    private boolean hasCSRInRequest(Map params) {
        if ((getParameter(params,"cvcreq") != null) || (getParameter(params,"cvcreqfile") != null)
                || (getParameter(params,"pkcs10req") != null) || (getParameter(params,"pkcs10file") != null)
                || (getParameter(params,"pkcs10") != null) || (getParameter(params,"PKCS10") != null)
                || (getParameter(params,"iidPkcs10") != null)
                || (getParameter(params,"keygen") != null)) {
            return true;
        }
        return false;
    }
    
    /**
     * Determines whether the issuer of a certificate is a "throw away" CA, i.e.
     * if it does not store certificates it issues in the database.
     * 
     * @param certbytes DER encoded certificate.
     * @throws CertificateException
     */
    private boolean isCertIssuerThrowAwayCA(final byte[] certbytes, final String username) throws CADoesntExistsException, CertificateException {
        Certificate cert = CertTools.getCertfromByteArray(certbytes, Certificate.class);
        String issuerDN = CertTools.getIssuerDN(cert);
        int caid = issuerDN.hashCode();
        CAInfo caInfo = caSession.getCAInfoInternal(caid);
        EndEntityInformation endEntityInformation = endEntityAccessSession.findUser(username);
        CertificateProfile certificateProfile = certificateProfileSession.getCertificateProfile(endEntityInformation.getCertificateProfileId());
        return (!caInfo.isUseCertificateStorage() && !certificateProfile.getUseCertificateStorage()) || !certificateProfile.getStoreCertificateData();
    }
    
	/**
	 * Method creating a Map of request values, designed to handle both
	 * regular x-encoded forms and multipart encoded upload forms.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @throws FileUploadException
	 *             if multipart request can not be parsed
	 * @throws IOException
	 *             If input stream of uploaded object can not be read
	 */
	@SuppressWarnings("unchecked")
    private Map setParameters(HttpServletRequest request) throws FileUploadException, IOException {
		 // private Map params = null;
		Map params = null;
		if (ServletFileUpload.isMultipartContent(request)) {
			params = new HashMap<String, String>();
            final DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            diskFileItemFactory.setSizeThreshold(9999);
            ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
			upload.setSizeMax(REQUEST_MAX_SIZE);
            List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
                FileItem item = iter.next();
				if (item.isFormField()) {
					params.put(item.getFieldName(), item.getString());
				} else {
					InputStream is = item.getInputStream();
					byte[] bytes = FileTools.readInputStreamtoBuffer(is);
					params.put(item.getFieldName(), new String(Base64.encode(bytes)));
				}
			}
		} else {
			params = request.getParameterMap();
		}
		return params;
	}

	private String getParameter(Map params,String param) {
		if(params==null)
			return null;
		 
		String ret = null;
		Object o = params.get(param);
		if (o != null) {
			if (o instanceof String) {
			    if ( ((String)o).length() > REQUEST_MAX_SIZE ) {
			        if (log.isDebugEnabled()) {
			            log.debug("Parameter '"+param+"' exceed size limit of "+REQUEST_MAX_SIZE);
			        }
			    } else {
			        ret = (String) o;
			    }
			} else if (o instanceof String[]) { // keygen is of this type
			    // for some reason...
			    String[] str = (String[]) o;
			    if ((str != null) && (str.length > 0)) {
			        if ( str[0].length() > REQUEST_MAX_SIZE ) {
			            if (log.isDebugEnabled()) {
			                log.debug("Parameter (first in list) '"+param+"' exceed size limit of "+REQUEST_MAX_SIZE);
			            }	                    
			        } else {
			            ret = str[0];
			        }
			    }
			} else {
				log.debug("Can not cast object of type: " + o.getClass().getName());
			}
		}
		return ret;
	}

	private void pkcs10Req(Map params,HttpServletRequest request, HttpServletResponse response, String username, String password, CertificateResponseType resulttype,
			BciaRequestHelper helper, byte[] reqBytes) throws Exception, IOException {
        if (log.isDebugEnabled()) {
            log.debug("Received PKCS10 request: " + new String(reqBytes));
        }
		CertificateRequestResponse result = helper.pkcs10CertRequest(signSession, caSession, reqBytes, username, password, resulttype);
		byte[] b64data = result.getEncoded(); // PEM cert, cert-chain or PKCS7
		byte[] b64subject = result.getCertificate().getEncoded(); // always a PEM cert of the subject
		if (Boolean.valueOf(getParameter(params,"showResultPage")) && !isCertIssuerThrowAwayCA(b64subject, username)) {
            RequestHelper.sendResultPage(b64subject, response, "true".equals(getParameter(params,"hidemenu")), resulttype);
        } else {
            switch (resulttype) {
            case ENCODED_PKCS7:
                RequestHelper.sendNewB64File(b64data, response, username + ".pem", RequestHelper.BEGIN_PKCS7_WITH_NL, RequestHelper.END_PKCS7_WITH_NL);
                break;
            case ENCODED_CERTIFICATE:
                RequestHelper.sendNewB64File(b64data, response, username + ".pem", RequestHelper.BEGIN_CERTIFICATE_WITH_NL,
                        RequestHelper.END_CERTIFICATE_WITH_NL);
                break;
            case ENCODED_CERTIFICATE_CHAIN:
                //Begin/end keys have already been set in the serialized object 
                RequestHelper.sendNewB64File(b64data, response, username + ".pem", "", "");
                break;
            default:
                log.warn("Unknown resulttype requested from pkcs10 request.");
                break;
            }
        }
	}

	/**
	 * method to create an install package for OpenVPN including keys and
	 * send to user. Contributed by: Jon Bendtsen,
	 * jon.bendtsen(at)laerdal.dk
	 */
	private void sendOpenVPNToken(KeyStore ks, String username, String kspassword, HttpServletResponse out) throws Exception {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ks.store(buffer, kspassword.toCharArray());

		String tempDirectory = System.getProperty("java.io.tmpdir");
		File fout = new File(tempDirectory + System.getProperty("file.separator") + username + ".p12");
		FileOutputStream certfile = new FileOutputStream(fout);

		Enumeration<String> en = ks.aliases();
		String alias = en.nextElement();
		// Then get the certificates
		Certificate[] certs = KeyTools.getCertChain(ks, alias);
		// The first one (certs[0]) is the users cert and the last
		// one (certs [certs.lenght-1]) is the CA-cert
		X509Certificate x509cert = (X509Certificate) certs[0];
		String IssuerDN = x509cert.getIssuerDN().toString();
		String SubjectDN = x509cert.getSubjectDN().toString();

		// export the users certificate to file
		buffer.writeTo(certfile);
		buffer.flush();
		buffer.close();
		certfile.close();

		// run shell script, which will also remove the created files
		// parameters are the username, IssuerDN and SubjectDN
		// IssuerDN and SubjectDN will be used to select the right
		// openvpn configuration file
		// they have to be written to stdin of the script to support
		// spaces in the username, IssuerDN or SubjectDN
		Runtime rt = Runtime.getRuntime();
		if (rt == null) {
			log.error(intres.getLocalizedMessage("certreq.ovpntnoruntime"));
		} else {
			final String script = WebConfiguration.getOpenVPNCreateInstallerScript();
			Process p = rt.exec(script);
			if (p == null) {
				log.error(intres.getLocalizedMessage("certreq.ovpntfailedexec", script));
			} else {
				OutputStream pstdin = p.getOutputStream();
				PrintStream stdoutp = new PrintStream(pstdin);
				stdoutp.println(username);
				stdoutp.println(IssuerDN);
				stdoutp.println(SubjectDN);
				stdoutp.flush();
				stdoutp.close();
				pstdin.close();
				int exitVal = p.waitFor();
				if (exitVal != 0) {
					log.error(intres.getLocalizedMessage("certreq.ovpntexiterror", exitVal));
				} else {
                    if (log.isDebugEnabled()) {
                        log.debug(intres.getLocalizedMessage("certreq.ovpntexiterror", exitVal));
                    }
				}
			}
		}

		// we ought to check if the script was okay or not, but in a little
		// while we will look for the openvpn-gui-install-$username.exe
		// and fail there if the script failed. Also, one could question
		// what to do if it did fail, serve the user the certificate?

		// sending the OpenVPN windows installer
		String filename = "openvpn-gui-install-" + username + ".exe";
		File fin = new File(tempDirectory + System.getProperty("file.separator") + filename);
		FileInputStream vpnfile = new FileInputStream(fin);
		out.setContentType("application/x-msdos-program");
		out.setHeader("Content-disposition", "filename=\"" + StringTools.stripFilename(filename) + "\"");
		out.setContentLength((int)fin.length());
		OutputStream os = out.getOutputStream();
		byte[] buf = new byte[4096];
		int bytes = 0;
		while ((bytes = vpnfile.read(buf)) != -1) {
			os.write(buf, 0, bytes);
		}
		vpnfile.close();
		// delete OpenVPN windows installer, the script will delete cert.
		fin.delete();
		out.flushBuffer();
	} // sendOpenVPNToken

	private void sendP12Token(KeyStore ks, String username, String kspassword, HttpServletResponse out) throws Exception {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ks.store(buffer, kspassword.toCharArray());

		out.setContentType("application/x-pkcs12");
		out.setHeader("Content-disposition", "filename=\"" + StringTools.stripFilename(username + ".p12") + "\"");
		out.setContentLength(buffer.size());
		buffer.writeTo(out.getOutputStream());
		out.flushBuffer();
		buffer.close();
	}

	private void sendJKSToken(KeyStore ks, String username, String kspassword, HttpServletResponse out) throws Exception {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ks.store(buffer, kspassword.toCharArray());

		out.setContentType("application/octet-stream");
		out.setHeader("Content-disposition", "filename=\"" + StringTools.stripFilename(username + ".jks") + "\"");
		out.setContentLength(buffer.size());
		buffer.writeTo(out.getOutputStream());
		out.flushBuffer();
		buffer.close();
	}

	private void sendPEMTokens(KeyStore ks, String username, String kspassword, HttpServletResponse out) throws Exception {
		out.setContentType("application/octet-stream");
		out.setHeader("Content-disposition", " attachment; filename=\"" + StringTools.stripFilename(username + ".pem") + "\"");
		out.getOutputStream().write(KeyTools.getSinglePemFromKeyStore(ks, kspassword.toCharArray()));
		out.flushBuffer();
	}
}

