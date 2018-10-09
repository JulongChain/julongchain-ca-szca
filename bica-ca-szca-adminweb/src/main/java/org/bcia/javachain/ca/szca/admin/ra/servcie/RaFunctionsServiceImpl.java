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

package org.bcia.javachain.ca.szca.admin.ra.servcie;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.ejb.FinderException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.bcia.javachain.ca.result.Result;
import org.bcia.javachain.ca.szca.admin.common.WebLanguages;
import org.bcia.javachain.ca.szca.admin.ra.EditEndEntityBean;
import org.bcia.javachain.ca.szca.admin.ra.UserView;
import org.bcia.javachain.ca.szca.admin.ra.UsersView;
import org.bcia.javachain.ca.szca.admin.ra.vo.CaAndCertificateprofileRel;
import org.bcia.javachain.ca.szca.admin.ra.vo.CaDataVo;
import org.bcia.javachain.ca.szca.admin.ra.vo.EndEntityInfoVo;
import org.bcia.javachain.ca.szca.admin.ra.vo.EndEntityInformationVo;
import org.bcia.javachain.ca.szca.admin.ra.vo.OtherSubjectVo;
import org.bcia.javachain.ca.szca.admin.ra.vo.SubjectDnVo;
import org.bcia.javachain.ca.szca.admin.ra.vo.ViewcertificateVo;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authorization.AuthorizationDeniedException;
import org.cesecore.certificates.ca.CADoesntExistsException;
import org.cesecore.certificates.ca.CAInfo;
import org.cesecore.certificates.certificate.CertificateConstants;
import org.cesecore.certificates.certificate.CertificateDataWrapper;
import org.cesecore.certificates.certificateprofile.CertificateProfile;
import org.cesecore.certificates.endentity.EndEntityConstants;
import org.cesecore.certificates.endentity.EndEntityInformation;
import org.cesecore.certificates.endentity.EndEntityType;
import org.cesecore.certificates.endentity.EndEntityTypes;
import org.cesecore.certificates.endentity.ExtendedInformation;
import org.cesecore.certificates.util.DNFieldExtractor;
import org.cesecore.certificates.util.DnComponents;
import org.cesecore.config.AvailableExtendedKeyUsagesConfiguration;
import org.cesecore.util.ValidityDate;
import org.ejbca.config.GlobalConfiguration;
import org.ejbca.core.EjbcaException;
import org.ejbca.core.ejb.ra.EndEntityExistsException;
import org.ejbca.core.model.SecConst;
import org.ejbca.core.model.approval.ApprovalException;
import org.ejbca.core.model.approval.WaitingForApprovalException;
import org.ejbca.core.model.ca.store.CertReqHistory;
import org.ejbca.core.model.ra.AlreadyRevokedException;
import org.ejbca.core.model.ra.raadmin.EndEntityProfile;
import org.ejbca.core.model.ra.raadmin.UserDoesntFullfillEndEntityProfile;
import org.ejbca.core.model.ra.raadmin.validators.RegexFieldValidator;
import org.ejbca.ui.web.CertificateView;
import org.ejbca.util.query.IllegalQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.szca.common.LoginUser;
import com.szca.wfs.common.BaseForm;

import org.bcia.javachain.ca.szca.common.bcca.config.WebConfiguration;
import org.bcia.javachain.ca.szca.common.bcca.core.ejb.ca.store.CertReqHistorySessionLocal;
//import org.bcia.javachain.ca.szca.common.bcca.core.ejb.hardtoken.HardTokenSession;
import org.bcia.javachain.ca.szca.common.bcca.core.ejb.ra.EndEntityAccessSessionLocal;
import org.bcia.javachain.ca.szca.common.bcca.core.ejb.ra.EndEntityManagementSessionLocal;
import org.bcia.javachain.ca.szca.common.bcca.core.ejb.ra.raadmin.EndEntityProfileSessionLocal;
import org.bcia.javachain.ca.szca.common.bcca.core.model.authorization.AccessRulesConstants;
import org.bcia.javachain.ca.szca.common.cesecore.authorization.control.AccessControlSessionLocal;
import org.bcia.javachain.ca.szca.common.cesecore.certificates.ca.CaSessionLocal;
import org.bcia.javachain.ca.szca.common.cesecore.certificates.certificate.CertificateStoreSession;
import org.bcia.javachain.ca.szca.common.cesecore.certificates.certificateprofile.CertificateProfileSessionLocal;
import org.bcia.javachain.ca.szca.common.cesecore.configuration.GlobalConfigurationSessionLocal;

@Service
public class RaFunctionsServiceImpl implements RaFunctionsService{
	private static Logger log = Logger.getLogger(RaFunctionsServiceImpl.class);


	@Autowired
	private EndEntityManagementSessionLocal endEntityManagementSessionBean;


    @Autowired
    private EndEntityProfileSessionLocal endEntityProfileSession;

    @Autowired
    private CaSessionLocal casession;

    //@Autowired
    //private HardTokenSession hardtokensession;

    @Autowired
    private AccessControlSessionLocal authorizationsession;

    @Autowired
    private CertReqHistorySessionLocal certreqhistorysession;

    @Autowired
    private CertificateStoreSession certificatesession;


    @Autowired
    private EndEntityAccessSessionLocal endEntityAccessSession;
    @Autowired
    GlobalConfigurationSessionLocal globalConfigurationSessionLocal;

    GlobalConfiguration globalconfiguration =new GlobalConfiguration();
    private TreeMap<String, Integer> profilenamesendentity = null;
    private HashMap<Integer, String> certificateprofilenamestore     = new HashMap<Integer, String>();
    private TreeMap<String, Integer> hardtokenprofiles = null;
    // Public Constants.
    public static final int AUTHORIZED_RA_VIEW_RIGHTS = 0;
    public static final int AUTHORIZED_RA_EDIT_RIGHTS = 1;
    public static final int AUTHORIZED_RA_CREATE_RIGHTS = 2;
    public static final int AUTHORIZED_RA_DELETE_RIGHTS = 3;
    public static final int AUTHORIZED_RA_REVOKE_RIGHTS = 4;
    public static final int AUTHORIZED_RA_HISTORY_RIGHTS = 5;
    public static final int AUTHORIZED_HARDTOKEN_VIEW_RIGHTS = 6;
    public static final int AUTHORIZED_CA_VIEW_CERT = 7;
    public static final int AUTHORIZED_RA_KEYRECOVERY_RIGHTS = 8;    private final Boolean[] raauthorized = new Boolean[AUTHORIZED_RA_RESOURCES.length];
    private static final String[] AUTHORIZED_RA_RESOURCES = { AccessRulesConstants.REGULAR_VIEWENDENTITY, AccessRulesConstants.REGULAR_EDITENDENTITY,
            AccessRulesConstants.REGULAR_CREATEENDENTITY, AccessRulesConstants.REGULAR_DELETEENDENTITY, AccessRulesConstants.REGULAR_REVOKEENDENTITY,
            AccessRulesConstants.REGULAR_VIEWENDENTITYHISTORY, AccessRulesConstants.REGULAR_VIEWHARDTOKENS,
            AccessRulesConstants.REGULAR_VIEWCERTIFICATE, AccessRulesConstants.REGULAR_KEYRECOVERY };
    private final TimeZone timeZone = ValidityDate.TIMEZONE_SERVER;


     @Autowired
    private  CertificateProfileSessionLocal  certificateProfileSession;
	@Override
	public void addUser(UserView userdata,AuthenticationToken administrator) throws EndEntityExistsException, CADoesntExistsException,
			AuthorizationDeniedException, WaitingForApprovalException, EjbcaException,
			UserDoesntFullfillEndEntityProfile, WaitingForApprovalException {

 	}

	@Override
    public TreeMap<String, Integer> getAuthorizedEndEntityProfileNames(final String endentityAccessRule,AuthenticationToken administrator){
    		TreeMap<String, Integer> authprofilenames = new TreeMap<String, Integer>();
    		final Map<Integer, String> idtonamemap = endEntityProfileSession.getEndEntityProfileIdToNameMap();
    		for (final Integer id : endEntityProfileSession.getAuthorizedEndEntityProfileIds(administrator, endentityAccessRule)) {
    			authprofilenames.put(idtonamemap.get(id), id);
    		}
    	return authprofilenames;
    }

	@Override
	public Map<Integer, List<Integer>> getCasAvailableToEndEntity(int endentityprofileid, String endentityAccessRule,
			AuthenticationToken administrator) {
	    Map<Integer, HashMap<Integer, List<Integer>>> endentityavailablecas  = new HashMap<Integer, HashMap<Integer, List<Integer>>>();
        //Create a TreeMap to get a sorted list.
        TreeMap<CAInfo, Integer> sortedMap = new TreeMap<CAInfo, Integer>(new Comparator<CAInfo>() {
            @Override
            public int compare(CAInfo o1, CAInfo o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        // 1. Retrieve a list of all CA's the current user is authorized to
        for(CAInfo caInfo : casession.getAuthorizedAndNonExternalCaInfos(administrator)) {
            sortedMap.put(caInfo, caInfo.getCAId());
        }
        Collection<Integer> authorizedCas = sortedMap.values();
        //Cache certificate profiles to save on database transactions
        HashMap<Integer, CertificateProfile> certificateProfiles = new HashMap<Integer, CertificateProfile>();
        // 2. Retrieve a list of all authorized end entity profile IDs
        for (Integer nextendentityprofileid : endEntityProfileSession.getAuthorizedEndEntityProfileIds(administrator, endentityAccessRule)) {
            EndEntityProfile endentityprofile = endEntityProfileSession.getEndEntityProfile(nextendentityprofileid.intValue());
            // 3. Retrieve the list of CA's available to the current end entity profile
            String[] availableCAs = endentityprofile.getValue(EndEntityProfile.AVAILCAS, 0).split(EndEntityProfile.SPLITCHAR);
            List<Integer> casDefineInEndEntityProfile = new ArrayList<Integer>();
            for (String profileId : availableCAs) {
                casDefineInEndEntityProfile.add(Integer.valueOf(profileId));
            }
            boolean allCasDefineInEndEntityProfile = false;
            if (casDefineInEndEntityProfile.contains(Integer.valueOf(SecConst.ALLCAS))) {
                allCasDefineInEndEntityProfile = true;
            }
            // 4. Next retrieve all certificate profiles defined in the end entity profile
            String[] availableCertificateProfiles = endentityprofile.getValue(EndEntityProfile.AVAILCERTPROFILES, 0).split(EndEntityProfile.SPLITCHAR);
            HashMap<Integer, List<Integer>> certificateProfileMap = new HashMap<Integer, List<Integer>>();
            for (String certificateProfileIdString : availableCertificateProfiles) {
                Integer certificateProfileId = Integer.valueOf(certificateProfileIdString);
                CertificateProfile certprofile = (CertificateProfile) certificateProfiles.get(certificateProfileId);
                if (certprofile == null) {
                    certprofile = certificateProfileSession.getCertificateProfile(certificateProfileId.intValue());
                    //Cache the profile for repeated use
                    certificateProfiles.put(certificateProfileId, certprofile);
                }
                // 5. Retrieve all CAs defined in the current certificate profile
                final Collection<Integer> casDefinedInCertificateProfile;
                if(certprofile != null) {
                    casDefinedInCertificateProfile = certprofile.getAvailableCAs();
                } else {
                    casDefinedInCertificateProfile = new ArrayList<Integer>();
                }
                // First make a clone of the full list of available CAs
                List<Integer> authorizedCasClone = new ArrayList<Integer>(authorizedCas);
                if (!casDefinedInCertificateProfile.contains(Integer.valueOf(CertificateProfile.ANYCA))) {
                    //If ANYCA wasn't defined among the list from the cert profile, only keep the intersection
                    authorizedCasClone.retainAll(casDefinedInCertificateProfile);
                }
                if (!allCasDefineInEndEntityProfile) {
                    //If ALL wasn't defined in the EE profile, only keep the intersection
                    authorizedCasClone.retainAll(casDefineInEndEntityProfile);
                }
                certificateProfileMap.put(certificateProfileId, authorizedCasClone);
            }
            endentityavailablecas.put(nextendentityprofileid, certificateProfileMap);
        }
        return endentityavailablecas.get(Integer.valueOf(endentityprofileid));

    }




    public String[] getCertificateProfileNames(AuthenticationToken administrator){
        String[] dummy = {""};
        Collection<String> certprofilenames = this.getAuthorizedEndEntityCertificateProfileNames(administrator).keySet();
        if(certprofilenames == null) {
            return new String[0];
        }
        return (String[]) certprofilenames.toArray(dummy);
    }

    public int getCertificateProfileId(String certificateprofilename) {
    	return certificateProfileSession.getCertificateProfileId(certificateprofilename);
    }



    /**
     * Returns authorized end entity  profile names as a treemap of name (String) -> id (Integer)
     */
    public TreeMap<String, Integer> getAuthorizedEndEntityCertificateProfileNames(AuthenticationToken administrator) {
        return this.getAuthorizedEndEntityCertificateProfileNames(globalconfiguration.getIssueHardwareTokens(), administrator);
    }



    public TreeMap<String, Integer> getAuthorizedEndEntityCertificateProfileNames(boolean usehardtokenprofiles,AuthenticationToken administrator){
        if(profilenamesendentity==null){
          profilenamesendentity = new TreeMap<String, Integer>();
          Iterator<Integer> iter = null;
          if(usehardtokenprofiles) {
            iter = certificateProfileSession.getAuthorizedCertificateProfileIds(administrator, CertificateConstants.CERTTYPE_HARDTOKEN).iterator();
          } else {
  		  iter = certificateProfileSession.getAuthorizedCertificateProfileIds(administrator, CertificateConstants.CERTTYPE_ENDENTITY).iterator();
          }
          Map<Integer, String> idtonamemap = certificateProfileSession.getCertificateProfileIdToNameMap();
          while(iter.hasNext()){
            Integer id = (Integer) iter.next();
            profilenamesendentity.put(idtonamemap.get(id),id);
          }
        }
        return profilenamesendentity;
      }

    public String getCertificateProfileName(int certificateprofileid) {

        String returnval = null;
        // Check if name is in hashmap
        returnval = (String) certificateprofilenamestore.get(Integer.valueOf(certificateprofileid));

        if(returnval==null){
          // Retreive profilename
          returnval = certificateProfileSession.getCertificateProfileName(certificateprofileid);
          if(returnval != null) {
            certificateprofilenamestore.put(Integer.valueOf(certificateprofileid),returnval);
          }
        }
        return returnval;
      }


	public TreeMap<String, Integer> getHardTokenProfiles(AuthenticationToken administrator){
		  if(hardtokenprofiles==null){
			hardtokenprofiles = new TreeMap<String, Integer>();
			/*for(Integer id :  hardtokensession.getAuthorizedHardTokenProfileIds(administrator)){
			  String name = hardtokensession.getHardTokenProfileName(id.intValue());
			  hardtokenprofiles.put(name, id);
			}*/
		  }
		  return hardtokenprofiles;
		}

	@Override
	public Result addendentity(HttpServletRequest request, Integer profileid,ModelAndView view) {
		Result result=new Result();
		AuthenticationToken administrator= getAuthenticationToken(request);
 	    TreeMap<String, Integer> profileNames=getAuthorizedEndEntityProfileNames(AccessRulesConstants.CREATE_END_ENTITY, administrator);
	    if(null==profileid) {
	    	profileid=profileNames.firstEntry().getValue();
	    }else {//验证
	      	if(!profileNames.containsValue(profileid)) {
	      		result.setMsg("非法访问！");
 	      		return result;
	      	}
	    }
 	    EndEntityProfile  endEntityProfile =  endEntityProfileSession.getEndEntityProfile(profileid);
	    view.addObject("profileNames", profileNames);
	    setAddEndentityToView(null,profileid,view, endEntityProfile,administrator);
 		result.setSuccess(true);
         return result;

	}

	private void setAddEndentityToView(UserView userdata,Integer profileid,ModelAndView view,EndEntityProfile  endEntityProfile,AuthenticationToken administrator) {
	    boolean usehardtokenissuers = false;
	    boolean usekeyrecovery = false;
	    usekeyrecovery = endEntityProfile.getUse(EndEntityProfile.KEYRECOVERABLE, 0);
	    usehardtokenissuers = new GlobalConfiguration().getIssueHardwareTokens() && endEntityProfile.getUse(EndEntityProfile.AVAILTOKENISSUER, 0);
	    view.addObject("usekeyrecovery", usekeyrecovery);
	    view.addObject("KEYRECOVERABLE",  WebLanguages.getInstance().getText("KEYRECOVERABLE"));
	    view.addObject("keyecoverableValue", endEntityProfile.getValue(EndEntityProfile.KEYRECOVERABLE,0).equals(EndEntityProfile.TRUE));
	    view.addObject("isRequiredUsekeyrecovery", endEntityProfile.isRequired(EndEntityProfile.KEYRECOVERABLE,0));
 		view.addObject("usehardtokenissuers", usehardtokenissuers);
	    view.addObject("endEntityProfile", endEntityProfile);
	    boolean isModifyableUsername=  endEntityProfile.isModifyable(EndEntityProfile.USERNAME,0);
	    view.addObject("useNameFields",endEntityProfile.getUse(EndEntityProfile.USERNAME,0));
	    view.addObject("isRequiredUsername", endEntityProfile.isRequired(EndEntityProfile.USERNAME,0));
	    view.addObject("isModifyableUsername", isModifyableUsername);
	    view.addObject("nameFieldsValue",  endEntityProfile.getValue(EndEntityProfile.USERNAME,0));
 	    if(!isModifyableUsername) {
 	    	 String[] options = endEntityProfile.getValue(EndEntityProfile.USERNAME, 0).split(EndEntityProfile.SPLITCHAR);
 		     view.addObject("nameoptions", options);
  	    }

 	    if(userdata!=null) {
 	    	if(userdata.getEndEntityProfileId() != 0) {
				  view.addObject("ENDENTITYPROFILE",  endEntityProfileSession.getEndEntityProfileName(userdata.getEndEntityProfileId()));
			  }else {
				  view.addObject("ENDENTITYPROFILE",  WebLanguages.getInstance().getText("NOENDENTITYPROFILEDEFINED"));
				  }
		    view.addObject("USERNAME", userdata.getUsername());
			view.addObject("CLEARTEXTPASSWORD",userdata.getClearTextPassword());
	         if(userdata.getEmail() != null && !userdata.getEmail().equals("")){
	  		    view.addObject("emailname", userdata.getEmail().substring(0,userdata.getEmail().indexOf('@')));
	  		    view.addObject("emaildomain", userdata.getEmail().substring(userdata.getEmail().indexOf('@')+1));
	         }
	 	    view.addObject("CERT_SUBJECTDN_TITLE", WebLanguages.getInstance().getText("CERT_SUBJECTDN"));
	 		view.addObject("caId", userdata.getCAId());
			view.addObject("tokenType", userdata.getTokenType());
			view.addObject("keyRecoverabl", userdata.getKeyRecoverable());
	   		view.addObject("endEntityProfileId", userdata.getEndEntityProfileId());

  	    }
	    view.addObject("usePasswordFields", endEntityProfile.getUse(EndEntityProfile.PASSWORD,0));
	    view.addObject("isRequiredPassword", endEntityProfile.isRequired(EndEntityProfile.PASSWORD,0));
 	    view.addObject("isModifyablePassword", endEntityProfile.isModifyable(EndEntityProfile.PASSWORD,0));
	    view.addObject("passwordFieldsValue",  endEntityProfile.getValue(EndEntityProfile.PASSWORD,0));
	    view.addObject("useMaxFailedLogins", endEntityProfile.getUse(EndEntityProfile.MAXFAILEDLOGINS,0));
	    int maxLoginAttempts = -1;
		try {
			maxLoginAttempts = Integer.parseInt(endEntityProfile.getValue(EndEntityProfile.MAXFAILEDLOGINS, 0));
		} catch(NumberFormatException ignored) {

		}
		view.addObject("maxLoginAttempts",maxLoginAttempts);

	    view.addObject("isRequiredMaxLoginAttempts", endEntityProfile.isRequired(EndEntityProfile.MAXFAILEDLOGINS,0));
  	    view.addObject("isModifyMaxLoginAttempts", endEntityProfile.isModifyable(EndEntityProfile.MAXFAILEDLOGINS,0));

	    view.addObject("useMaxFailedLoginsFields", endEntityProfile.getUse(EndEntityProfile.MAXFAILEDLOGINS,0));
	    view.addObject("isModifyableMaxFailedLogins", endEntityProfile.isModifyable(EndEntityProfile.MAXFAILEDLOGINS,0));


	    view.addObject("useCleartextPasswordFields", endEntityProfile.getUse(EndEntityProfile.CLEARTEXTPASSWORD,0));
	    view.addObject("isRequiredCleartextPassword", endEntityProfile.isRequired(EndEntityProfile.CLEARTEXTPASSWORD,0));
	    view.addObject("cleartextPasswordValue", endEntityProfile.getValue(EndEntityProfile.CLEARTEXTPASSWORD,0));

	    view.addObject("useEmailFields", endEntityProfile.getUse(EndEntityProfile.EMAIL,0));
	    view.addObject("isRequiredEmail", endEntityProfile.isRequired(EndEntityProfile.EMAIL,0));
	    boolean isModifyableEmail=  endEntityProfile.isModifyable(EndEntityProfile.EMAIL,0);
	    view.addObject("isModifyableEmail",isModifyableEmail);
	    view.addObject("emailValue", endEntityProfile.getValue(EndEntityProfile.EMAIL,0));

	    if(!isModifyableEmail) {
	    	 String[] options = endEntityProfile.getValue(EndEntityProfile.EMAIL, 0).split(EndEntityProfile.SPLITCHAR);
		     view.addObject("emailoptions", options);
 	    }

	    //SUBJECT DN
	    int[] fielddata = null;
	    List<SubjectDnVo> subjectDnList=new ArrayList<SubjectDnVo>();
	    int numberofsubjectdnfields = endEntityProfile.getSubjectDNFieldOrderLength();
        for(int i=0; i < numberofsubjectdnfields; i++){
              fielddata = endEntityProfile.getSubjectDNFieldsInOrder(i);
              SubjectDnVo subjectDnVo=new SubjectDnVo();
              subjectDnVo.setTitle(WebLanguages.getInstance().getText(DnComponents.getLanguageConstantFromProfileId(fielddata[EndEntityProfile.FIELDTYPE])));
              subjectDnVo.setFieldOfType(EndEntityProfile.isFieldOfType(fielddata[EndEntityProfile.FIELDTYPE], DnComponents.DNEMAILADDRESS));
              boolean isModifyable=endEntityProfile.isModifyable(fielddata[EndEntityProfile.FIELDTYPE],fielddata[EndEntityProfile.NUMBER]);
              subjectDnVo.setModifyable(isModifyable);
              if(!isModifyable) {
                  String[] options = endEntityProfile.getValue(fielddata[EndEntityProfile.FIELDTYPE],fielddata[EndEntityProfile.NUMBER]).split(EndEntityProfile.SPLITCHAR);
                  subjectDnVo.setOptions(options);
              }else {
            	   Map<String,Serializable> validation = endEntityProfile.getValidation(fielddata[EndEntityProfile.FIELDTYPE],fielddata[EndEntityProfile.NUMBER]);
                   String regex = (validation != null ? (String)validation.get(RegexFieldValidator.class.getName()) : null);
                   subjectDnVo.setRegex(regex);
                   subjectDnVo.setValue(endEntityProfile.getValue(fielddata[EndEntityProfile.FIELDTYPE],fielddata[EndEntityProfile.NUMBER]));
              }
              subjectDnVo.setRequired(endEntityProfile.isRequired(fielddata[EndEntityProfile.FIELDTYPE],fielddata[EndEntityProfile.NUMBER]));
              if(userdata!=null) {
              subjectDnVo.setSubjectDNField(userdata.getSubjectDNField(DnComponents.profileIdToDnId(fielddata[EndEntityProfile.FIELDTYPE]),fielddata[EndEntityProfile.NUMBER])) ;
              }
              subjectDnList.add(subjectDnVo);
           }
     	  view.addObject("subjectDnList", subjectDnList);


     	  //Other subject attributes


     	int numberofsubjectaltnamefields = endEntityProfile.getSubjectAltNameFieldOrderLength();
 		int numberofsubjectdirattrfields = endEntityProfile.getSubjectDirAttrFieldOrderLength();
 	    view.addObject("OTHERSUBJECTATTR",WebLanguages.getInstance().getText("OTHERSUBJECTATTR"));
 	    view.addObject("SUBJECTALTNAME",WebLanguages.getInstance().getText("EXT_PKIX_SUBJECTALTNAME"));
 		view.addObject("SUBJECTDIRATTRS",WebLanguages.getInstance().getText("EXT_PKIX_SUBJECTDIRATTRS"));
 		view.addObject("numberofsubjectaltnamefields", numberofsubjectaltnamefields);
 		view.addObject("numberofsubjectdirattrfields", numberofsubjectdirattrfields);
	    List<OtherSubjectVo> otherSubjectVoList=new ArrayList<OtherSubjectVo>();
  		for(int i=0; i < numberofsubjectaltnamefields; i++){
            fielddata = endEntityProfile.getSubjectAltNameFieldsInOrder(i);
            int fieldtype = fielddata[EndEntityProfile.FIELDTYPE];
          	OtherSubjectVo otherSubjectVo=new OtherSubjectVo();
            boolean fieldImplemented=EndEntityProfile.isFieldImplemented(fieldtype);
     	    boolean modifyable = endEntityProfile.isModifyable(fielddata[EndEntityProfile.FIELDTYPE],fielddata[EndEntityProfile.NUMBER]);
    	    boolean required= endEntityProfile.isRequired(fielddata[EndEntityProfile.FIELDTYPE],fielddata[EndEntityProfile.NUMBER]);
             otherSubjectVo.setFieldImplemented(fieldImplemented);
             otherSubjectVo.setModifyable(modifyable);
             if(fieldImplemented) {
                 otherSubjectVo.setTitle(WebLanguages.getInstance().getText(DnComponents.getLanguageConstantFromProfileId(fielddata[EndEntityProfile.FIELDTYPE])));
                 boolean tFieldOfTypeRFC822=EndEntityProfile.isFieldOfType(fielddata[EndEntityProfile.FIELDTYPE], DnComponents.RFC822NAME );
                 otherSubjectVo.setFieldOfTypeRFC822(tFieldOfTypeRFC822);
                 if(tFieldOfTypeRFC822) {
                 boolean use=endEntityProfile.getUse(fielddata[EndEntityProfile.FIELDTYPE],fielddata[EndEntityProfile.NUMBER]) ;
                 otherSubjectVo.setUse(use);
                 otherSubjectVo.setRequired(required);
                 otherSubjectVo.setValue(endEntityProfile.getValue(fielddata[EndEntityProfile.FIELDTYPE],fielddata[EndEntityProfile.NUMBER]));
                  if(!use) {
                		String rfc822NameString = endEntityProfile.getValue(fielddata[EndEntityProfile.FIELDTYPE],fielddata[EndEntityProfile.NUMBER]);
                		String[] rfc822NameArray = new String[2];
                		if ( rfc822NameString.indexOf("@") != -1 ) {
                			rfc822NameArray = rfc822NameString.split("@");
                		} else {
    	            		rfc822NameArray[0] = "";
                			rfc822NameArray[1] = rfc822NameString;
                		}
                		otherSubjectVo.setRfc822NameStringc(!(!modifyable && rfc822NameString.contains("@")));
                		otherSubjectVo.setRfc822NameArray(rfc822NameArray);
                		otherSubjectVo.setRfc822NameOptions(rfc822NameString.split(EndEntityProfile.SPLITCHAR));

                 }
                 }else {
  					otherSubjectVo.setFieldOfTypeUPN(EndEntityProfile.isFieldOfType(fielddata[EndEntityProfile.FIELDTYPE], DnComponents.UPN));
                 	 if(!modifyable) {
                			String[] options = endEntityProfile.getValue(fielddata[EndEntityProfile.FIELDTYPE],fielddata[EndEntityProfile.NUMBER]).split(EndEntityProfile.SPLITCHAR);
         					otherSubjectVo.setNonRFC822NAMEOptions(options);

                	 }else {
                	     final Map<String,Serializable> validation = endEntityProfile.getValidation(fielddata[EndEntityProfile.FIELDTYPE],fielddata[EndEntityProfile.NUMBER]);
                         final String regex = (validation != null ? (String)validation.get(RegexFieldValidator.class.getName()) : null);
                         otherSubjectVo.setValue(endEntityProfile.getValue(fielddata[EndEntityProfile.FIELDTYPE],fielddata[EndEntityProfile.NUMBER]));
                         otherSubjectVo.setRegex(regex);
                	 }

                  }
                 if(userdata!=null) {
                 otherSubjectVo.setSubjectDNField(userdata.getSubjectDNField(DnComponents.profileIdToDnId(fielddata[EndEntityProfile.FIELDTYPE]),fielddata[EndEntityProfile.NUMBER])) ;
                 }
                 otherSubjectVoList.add(otherSubjectVo);

            	}
            }
  		view.addObject("otherSubjectVoList", otherSubjectVoList);
	    List<OtherSubjectVo> dirAttrList=new ArrayList<OtherSubjectVo>();
  		for(int i=0; i < numberofsubjectdirattrfields; i++){
            fielddata = endEntityProfile.getSubjectDirAttrFieldsInOrder(i);
           	OtherSubjectVo otherSubjectVo=new OtherSubjectVo();
            otherSubjectVo.setTitle(WebLanguages.getInstance().getText(DnComponents.getLanguageConstantFromProfileId(fielddata[EndEntityProfile.FIELDTYPE])));
    	    boolean modifyable = endEntityProfile.isModifyable(fielddata[EndEntityProfile.FIELDTYPE],fielddata[EndEntityProfile.NUMBER]);
    	    boolean required= endEntityProfile.isRequired(fielddata[EndEntityProfile.FIELDTYPE],fielddata[EndEntityProfile.NUMBER]);
    	    otherSubjectVo.setModifyable(modifyable);
            otherSubjectVo.setRequired(required);
           if(!modifyable) {
     			String[] options = endEntityProfile.getValue(fielddata[EndEntityProfile.FIELDTYPE],fielddata[EndEntityProfile.NUMBER]).split(EndEntityProfile.SPLITCHAR);
					otherSubjectVo.setNonRFC822NAMEOptions(options);
      	 }else {
               otherSubjectVo.setValue(endEntityProfile.getValue(fielddata[EndEntityProfile.FIELDTYPE],fielddata[EndEntityProfile.NUMBER]));
      	 }
           if(userdata!=null) {
           otherSubjectVo.setSubjectDNField(userdata.getSubjectDNField(DnComponents.profileIdToDnId(fielddata[EndEntityProfile.FIELDTYPE]),fielddata[EndEntityProfile.NUMBER])) ;
      	    }
           dirAttrList.add(otherSubjectVo);
 		}
  		view.addObject("dirAttrList", dirAttrList);



  		//Main certificate data

  		view.addObject("maincertificaText", WebLanguages.getInstance().getText("MAINCERTIFICATEDATA"));
  		view.addObject("certificateprofileText", WebLanguages.getInstance().getText("CERTIFICATEPROFILE"));

  		 String[] availablecertprofiles = endEntityProfile.getValue(EndEntityProfile.AVAILCERTPROFILES, 0).split(EndEntityProfile.SPLITCHAR);
         String  selectedcertificateprofile= endEntityProfile.getValue(EndEntityProfile.DEFAULTCERTPROFILE,0);
         Map<String,String> profileMap=new HashMap<String,String>();
         if( availablecertprofiles != null){
           for(int i =0; i< availablecertprofiles.length;i++){
         	   profileMap.put(availablecertprofiles[i], getCertificateProfileName(Integer.parseInt(availablecertprofiles[i])));
        	   }
           }
         view.addObject("selectedcertificateprofile", selectedcertificateprofile);
         view.addObject("profileMap",profileMap);


   		view.addObject("maincertificaCAText", WebLanguages.getInstance().getText("CA"));
   		Map<Integer, List<Integer>>  mapCa= getCasAvailableToEndEntity(profileid, AccessRulesConstants.CREATE_END_ENTITY, administrator);
   		view.addObject("profileid", profileid);


      	String[] availabletokens = endEntityProfile.getValue(EndEntityProfile.AVAILKEYSTORE, 0).split(EndEntityProfile.SPLITCHAR);
      	String  lastselectedtoken= endEntityProfile.getValue(EndEntityProfile.DEFKEYSTORE,0);


        String[] tokentexts = SecConst.TOKENTEXTS;
        int[] tokenids =  SecConst.TOKENIDS;

        if (new GlobalConfiguration().getIssueHardwareTokens()) {
            TreeMap hardtokenprofiles = getHardTokenProfiles(administrator);

            tokentexts = new String[tokentexts.length + hardtokenprofiles.keySet().size()];
            tokenids = new int[tokentexts.length];
            for (int i = 0; i < tokentexts.length; i++) {
             tokentexts[i] = tokentexts[i];
             tokenids[i] = tokenids[i];
            }

            Iterator iter = hardtokenprofiles.keySet().iterator();
            int index = 0;
			while (iter.hasNext()) {
				String name = (String) iter.next();
				tokentexts[index + tokentexts.length] = name;
				tokenids[index + tokentexts.length] = ((Integer) hardtokenprofiles.get(name)).intValue();
				index++;
			}
        }
    	view.addObject("lastselectedtoken", lastselectedtoken);

    	Map<String,String>tokensMap=new HashMap<String,String>();
		if (null != availabletokens) {
			for (int i = 0; i < availabletokens.length; i++) {
				for (int j = 0; j < tokentexts.length; j++) {
					if (tokenids[j] == Integer.parseInt(availabletokens[i])) {
						if (tokenids[j] > SecConst.TOKEN_SOFT) {
							tokensMap.put(tokentexts[j], availabletokens[i]);
						} else {
							tokensMap.put(WebLanguages.getInstance().getText(tokentexts[j]), availabletokens[i]);
						}
					}

				}

			}
		}
		view.addObject("tokensMap", tokensMap);
		view.addObject("tokenText", WebLanguages.getInstance().getText("TOKEN"));

 		HashMap<Integer,String> caidtonamemap=casession.getCAIdToNameMap();
		Iterator iter = mapCa.keySet().iterator();
		List<CaAndCertificateprofileRel> caAndCertificateprofileRelList=new ArrayList<CaAndCertificateprofileRel>();
		List<CaDataVo> cadata=new ArrayList<CaDataVo>();
		while (iter.hasNext()) {
			cadata.clear();
			Integer next = (Integer) iter.next();
			Collection nextcaset = (Collection) mapCa.get(next);
			CaAndCertificateprofileRel caAndCertificateprofileRel = new CaAndCertificateprofileRel();
  			caAndCertificateprofileRel.setCertificateprofileId(next.intValue());
 			Iterator iter2 = nextcaset.iterator();
			while (iter2.hasNext()) {
				CaDataVo caDataVo=new CaDataVo();
				Integer nextca = (Integer) iter2.next();
				caDataVo.setCaname(caidtonamemap.get(nextca));
				caDataVo.setCaid(nextca.intValue());
				cadata.add(caDataVo);
 			}
			caAndCertificateprofileRel.setCadata(cadata);
			caAndCertificateprofileRelList.add(caAndCertificateprofileRel);
		}
		Gson gson = new Gson();
		String jsonStr=gson.toJson(caAndCertificateprofileRelList);
		view.addObject("caAndCertificateprofileRel", jsonStr);
	}

	@Override
	public Result saveEndentity(HttpServletRequest request,EndEntityInformationVo endEntityInformationVo) {
		Result  result=new Result();
		EndEntityInformation endEntityInformation=new EndEntityInformation(endEntityInformationVo.getUsername(),
				endEntityInformationVo.getSubjectDN(), endEntityInformationVo.getCaid(),
				endEntityInformationVo.getSubjectAltName(), endEntityInformationVo.getSubjectEmail(),
				new EndEntityType(endEntityInformationVo.isKeyrecoverable()?EndEntityTypes.KEYRECOVERABLE:EndEntityTypes.ENDUSER), endEntityInformationVo.getEndentityprofileid(), endEntityInformationVo.getCertificateprofileid(), endEntityInformationVo.getTokentype(), endEntityInformationVo.getHardtokenissuerid(), null);
 		endEntityInformation.setPassword(endEntityInformationVo.getPassword());
	    Map<Integer, String> caidtonamemap=new  HashMap<Integer, String>();
 		UserView userdata=new UserView(endEntityInformation, caidtonamemap)	  ;
  	    LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
		AuthenticationToken administrator = user.getAuthenticationToken();
		boolean userexists=endEntityManagementSessionBean.existsUser(userdata.getUsername());
		if(userexists) {
			result.setSuccess(false);
			result.setMsg("user already exists");
			return result;
		}
        if (userdata.getEndEntityProfileId() != 0) {

         	String value="";
         	String serialnumber="";
        	 int[] fielddata = null;
      	    EndEntityProfile  endEntityProfile =  endEntityProfileSession.getEndEntityProfile(endEntityInformationVo.getEndentityprofileid());
      	    int numberofsubjectdnfields = endEntityProfile.getSubjectDNFieldOrderLength();
            String subjectdn = "";
            for(int i=0; i < numberofsubjectdnfields; i++){
            value = null;
           fielddata = endEntityProfile.getSubjectDNFieldsInOrder(i);

           if (!EndEntityProfile.isFieldOfType(fielddata[EndEntityProfile.FIELDTYPE], DnComponents.DNEMAILADDRESS)) {
               value = request.getParameter("textfieldsubjectdn"+ i);
           } else {
               if (endEntityProfile.isRequired(fielddata[EndEntityProfile.FIELDTYPE], fielddata[EndEntityProfile.NUMBER])
                       || (request.getParameter("selectsubjectdn"+ i) != null && request.getParameter("checkboxsubjectdn"+ i)
                               .equals("true")))
                   value = userdata.getEmail();
           }
           if (value != null) {
               value = value.trim();
               if (EndEntityProfile.isFieldOfType(fielddata[EndEntityProfile.FIELDTYPE], DnComponents.DNSERIALNUMBER)) {
                   serialnumber = value;
               }
               endEntityProfile.setValue(fielddata[EndEntityProfile.FIELDTYPE], fielddata[EndEntityProfile.NUMBER], value);
               final String field = DNFieldExtractor.getFieldComponent(
                       DnComponents.profileIdToDnId(fielddata[EndEntityProfile.FIELDTYPE]), DNFieldExtractor.TYPE_SUBJECTDN)
                       + value;
               final String dnPart;
               if (field.charAt(field.length() - 1) != '=') {
                   dnPart = org.ietf.ldap.LDAPDN.escapeRDN(field);
               } else {
                   dnPart = field;
               }
               if (subjectdn.equals(""))
                   subjectdn = dnPart;
               else
                   subjectdn += ", " + dnPart;
           }

           value = request.getParameter("selectsubjectdn"+ i);
           if (value != null) {
               value = value.trim();
               if (!value.equals("")) {
                   value = org.ietf.ldap.LDAPDN.escapeRDN(DNFieldExtractor.getFieldComponent(
                           DnComponents.profileIdToDnId(fielddata[EndEntityProfile.FIELDTYPE]), DNFieldExtractor.TYPE_SUBJECTDN)
                           + value);
                   if (subjectdn.equals(""))
                       subjectdn = value;
                   else
                       subjectdn += ", " + value;
               }
           }
       }
            userdata.setSubjectDN(subjectdn);
            userdata.setKeyRecoverable(endEntityInformationVo.isKeyrecoverable());
            userdata.setClearTextPassword(endEntityInformationVo.isCleartextpwd());
            EndEntityInformation uservo = new EndEntityInformation(userdata.getUsername(), userdata.getSubjectDN(), userdata.getCAId(), userdata.getSubjectAltName(),
        		userdata.getEmail(), EndEntityConstants.STATUS_NEW, userdata.getType(), userdata.getEndEntityProfileId(), userdata.getCertificateProfileId(),
        		null,null, userdata.getTokenType(), userdata.getHardTokenIssuerId(), null);
            uservo.setPassword(userdata.getPassword());
            uservo.setExtendedinformation(userdata.getExtendedInformation());
            uservo.setCardNumber(userdata.getCardNumber());
            try {
				endEntityManagementSessionBean.addUser(administrator, uservo, userdata.getClearTextPassword());
				result.setSuccess(true);
 				return result;
			}   catch (Exception e) {
 				e.printStackTrace();
 				result.setMsg(e.getMessage());
			}
         } else {
 			result.setMsg("profile id not set, user not created");
         }
 	    return result;
 	    }

	@Override
	public void findAllUsers(HttpServletRequest request,BaseForm baseForm,ModelAndView view,EndEntityInformationVo endEntityInformationVo) throws IllegalQueryException {
		   UsersView  usersView = new UsersView();
		   AuthenticationToken administrator= getAuthenticationToken(request);
		   int total=  0;
 		   if(null!=endEntityInformationVo&&StringUtils.isNoneEmpty(endEntityInformationVo.getUsername())) {
   		    	EndEntityInformation[] userarray = new EndEntityInformation[1];
 		    	EndEntityInformation user = null;
 		    	try {
 		    		user = endEntityAccessSession.findUser(administrator, endEntityInformationVo.getUsername());
 		    	} catch(AuthorizationDeniedException e) {
 		    	}
 		    	if (user != null) {
 		    		userarray[0]=user;
 		    		total=1;
 		    		usersView.setUsers(userarray, casession.getCAIdToNameMap());
 		    	} else {
 		    		usersView.setUsers((EndEntityInformation[]) null, casession.getCAIdToNameMap());
 		    	}
  		   }else {//  else if  TODO
  			   total=   endEntityManagementSessionBean.findAllUsersTotal(administrator);
  	  		   usersView.setUsers(endEntityManagementSessionBean.findAllUsers(administrator,baseForm.getCurrentPage(),baseForm.getRowsPerPage()), casession.getCAIdToNameMap());
   		   }
 		   view.addObject("totalRowsCount", total);
		   view.addObject("currentPage", baseForm.getCurrentPage());
		   view.addObject("rowsPerPage", baseForm.getRowsPerPage());

	       UserView[] uv=  usersView.getUsers(0,usersView.size());
	       List<EndEntityInfoVo> list=new ArrayList<EndEntityInfoVo>();
	       for(int i=0;i<uv.length;i++) {
	    	   EndEntityInfoVo endEntityInfoVo=new EndEntityInfoVo();
	    	   try {
				endEntityInfoVo.setUsername(java.net.URLEncoder.encode(uv[i].getUsername(),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
 				e.printStackTrace();
 				endEntityInfoVo.setUsername(uv[i].getUsername());
 				log.error("Username encode  error!",e);
			}
	    	   endEntityInfoVo.setCaName(uv[i].getCAName());
	    	   endEntityInfoVo.setCommonName(uv[i].getCommonName());
	    	   endEntityInfoVo.setStatus(getStatus(uv[i].getStatus()));
 			   endEntityInfoVo.setViewendentity((authorizedToEditUser(uv[i].getEndEntityProfileId(),administrator) || !globalconfiguration.getEnableEndEntityProfileLimitations())
				   && isAuthorizedNoLog(administrator,AUTHORIZED_RA_EDIT_RIGHTS));
               endEntityInfoVo.setAuthorizedCaView_cert(isAuthorizedNoLog(administrator,AUTHORIZED_CA_VIEW_CERT));
               endEntityInfoVo.setAuthorizedHardtokenViewRights(globalconfiguration.getIssueHardwareTokens() && (authorizedToViewHardToken(administrator,uv[i].getEndEntityProfileId()) || !globalconfiguration.getEnableEndEntityProfileLimitations())
                       && isAuthorizedNoLog(administrator,AUTHORIZED_HARDTOKEN_VIEW_RIGHTS));
               endEntityInfoVo.setAuthorizedRaHistoryRights((authorizedToViewHistory(administrator,uv[i].getEndEntityProfileId()) || !globalconfiguration.getEnableEndEntityProfileLimitations()) &&isAuthorizedNoLog(administrator,AUTHORIZED_RA_HISTORY_RIGHTS));
               endEntityInfoVo.setSubjectDNFieldO(uv[i].getSubjectDNField(DNFieldExtractor.OU,0));
	    	   endEntityInfoVo.setSubjectDNFieldO(uv[i].getSubjectDNField(DNFieldExtractor.O,0));
	    	   list.add(endEntityInfoVo);
              }
			view.addObject("VIEWENDENTITY", WebLanguages.getInstance().getText("VIEWENDENTITY"));
			view.addObject("EDITENDENTITY", WebLanguages.getInstance().getText("EDITENDENTITY"));
			view.addObject("VIEWCERTIFICATES", WebLanguages.getInstance().getText("VIEWCERTIFICATES"));
			view.addObject("VIEWHARDTOKENS", WebLanguages.getInstance().getText("VIEWHARDTOKENS"));
			view.addObject("VIEWHISTORY", WebLanguages.getInstance().getText("VIEWHISTORY"));
			view.addObject("endEntityInformationVo", endEntityInformationVo);
			view.addObject("list", list);

 	    }


	private String getStatus(int status) {
	String statusStr="";
 	   switch(status){
        case EndEntityConstants.STATUS_NEW :
        	statusStr=  WebLanguages.getInstance().getText("STATUSNEW");
         break;
        case EndEntityConstants.STATUS_FAILED :
        	statusStr=  WebLanguages.getInstance().getText("STATUSFAILED");
          break;
        case EndEntityConstants.STATUS_INITIALIZED :
        	statusStr=  WebLanguages.getInstance().getText("STATUSINITIALIZED");
           break;
        case EndEntityConstants.STATUS_INPROCESS :
        	statusStr=  WebLanguages.getInstance().getText("STATUSINPROCESS");
           break;
        case EndEntityConstants.STATUS_GENERATED :
        	statusStr=  WebLanguages.getInstance().getText("STATUSGENERATED");
          break;
        case EndEntityConstants.STATUS_REVOKED :
        	statusStr=  WebLanguages.getInstance().getText("STATUSREVOKED");
           break;
        case EndEntityConstants.STATUS_HISTORICAL :
        	statusStr=  WebLanguages.getInstance().getText("STATUSHISTORICAL");
           break;
        case EndEntityConstants.STATUS_KEYRECOVERY :
        	statusStr=  WebLanguages.getInstance().getText("STATUSKEYRECOVERY");
           break;
           }
 	   return statusStr;
	}


    public boolean authorizedToEditUser(int profileid,AuthenticationToken administrator) {
    	return endEntityAuthorization(administrator, profileid, AccessRulesConstants.EDIT_END_ENTITY, false);
    }
    /** Help function used to check end entity profile authorization. */
    public boolean endEntityAuthorization(AuthenticationToken admin, int profileid, String rights, boolean log) {
    	boolean returnval = false;
    	if (log) {
    		returnval = authorizationsession.isAuthorized(admin, AccessRulesConstants.ENDENTITYPROFILEPREFIX + Integer.toString(profileid) + rights,
    		        AccessRulesConstants.REGULAR_RAFUNCTIONALITY + rights);
    	} else {
    		returnval = authorizationsession.isAuthorizedNoLogging(admin, AccessRulesConstants.ENDENTITYPROFILEPREFIX + Integer.toString(profileid)
    				+ rights, AccessRulesConstants.REGULAR_RAFUNCTIONALITY + rights);
    	}
    	return returnval;
    }

    /**
     * Checks if the admin have authorization to view the resource without performing any logging. Used by menu page Does not return false if not
     * authorized, instead throws an AuthorizationDeniedException.
     *
     * TODO: don't use as is in a new admin GUI, refactor to return true or false instead (if we re-use this class at all)
     *
     * @return true if is authorized to resource, throws AuthorizationDeniedException if not authorized, never returns false.
     * @throws AuthorizationDeniedException is not authorized to resource
     */
    public boolean isAuthorizedNoLog(AuthenticationToken administrator,String... resources) throws AuthorizationDeniedException {
        if (!authorizationsession.isAuthorizedNoLogging(administrator, resources)) {
            throw new AuthorizationDeniedException("Not authorized to " + Arrays.toString(resources));
        }
        return true;
    }

    /**
     * A more optimized authorization version to check if the admin have authorization to view the url without performing any logging. AUTHORIZED_RA..
     * constants should be used. Does not return false if not authorized, instead throws an AuthorizationDeniedException.
     *
     * TODO: don't use as is in a new admin GUI, refactor to return true or false instead (if we re-use this class at all)
     *
     * @return true is authorized to resource, never return false instead throws AuthorizationDeniedException.
     * @throws AuthorizationDeniedException is not authorized to resource
     */
    public boolean isAuthorizedNoLog(AuthenticationToken administrator,int resource)     {
        if (raauthorized[resource] == null) {
            raauthorized[resource] = Boolean.valueOf(authorizationsession.isAuthorizedNoLogging(administrator, AUTHORIZED_RA_RESOURCES[resource]));
        }
        final boolean returnval = raauthorized[resource].booleanValue();
        if (!returnval) {
 		   log.error("Not authorized to " + resource);
 		   return false;
         }
        return returnval;
    }



    public boolean authorizedToViewHardToken(AuthenticationToken administrator,String username) throws AuthorizationDeniedException {
    	int profileid = endEntityAccessSession.findUser(administrator, username).getEndEntityProfileId();
    	if (!endEntityAuthorization(administrator, profileid, AccessRulesConstants.HARDTOKEN_RIGHTS, false)) {
    		throw new AuthorizationDeniedException();
    	}
    	if (!WebConfiguration.getHardTokenDiplaySensitiveInfo()) {
    		return false;
    	}
    	return endEntityAuthorization(administrator, profileid, AccessRulesConstants.HARDTOKEN_PUKDATA_RIGHTS, false);
    }

    public boolean authorizedToViewHardToken(AuthenticationToken administrator,int profileid) {
    	return endEntityAuthorization(administrator, profileid, AccessRulesConstants.HARDTOKEN_RIGHTS, false);
    }

    public boolean authorizedToViewHistory(AuthenticationToken administrator,int profileid) {
    	return endEntityAuthorization(administrator, profileid, AccessRulesConstants.VIEW_END_ENTITY_HISTORY, false);
    }

	@Override
	public Result viewendentity(HttpServletRequest request, String username, ModelAndView view) {
 		try {
			UserView userView = findUser(username, getAuthenticationToken(request));
			return	setEntityToModelAndView(userView, username, view);
		} catch (Exception e) {
 			e.printStackTrace();
 			log.error("viewendentity error"+e.getStackTrace());
   			return null;
		}

	}




    private Result setEntityToModelAndView(UserView currentuser, String username, ModelAndView view) {
        Result  result=new  Result();
        try {
            int currentexists = 0;
            if(currentuser != null){
                currentexists = 1;
            }
            List<CertReqHistory> hist =getCertReqUserDatas(username);
            UserView[]  userdatas = new UserView[hist.size() +currentexists];
            if(currentuser != null){
                userdatas[0] = currentuser;
            }
            for(int i=0; i< hist.size();i++){
                CertReqHistory next = ((CertReqHistory) hist.get(i));
                userdatas[i+currentexists] = new UserView(next.getEndEntityInformation(),casession.getCAIdToNameMap());
            }
            UserView   userdata =null;
            if ( (userdatas != null) && (userdatas.length > 0) ) {
                userdata = userdatas[0];
                if(userdata!=null) {
                    EndEntityProfile endentityprofile = endEntityProfileSession.getEndEntityProfile(userdata.getEndEntityProfileId());
                    view.addObject("endentityprofile", endentityprofile);
                    view.addObject("USERNAME_TITLE", WebLanguages.getInstance().getText("USERNAME"));
                    view.addObject("USERNAME", userdata.getUsername());
                    view.addObject("STATUST_ITLE", WebLanguages.getInstance().getText("STATUS"));
                    view.addObject("STATUST", getStatus(userdata.getStatus()));
                    view.addObject("CREATED", formatAsISO8601(userdata.getTimeCreated()));
                    view.addObject("CREATED_TITLE", WebLanguages.getInstance().getText("CREATED"));
                    view.addObject("MODIFIED_TITLE", WebLanguages.getInstance().getText("MODIFIED"));
                    view.addObject("MODIFIED", formatAsISO8601(userdata.getTimeModified()));
                    view.addObject("ENDENTITYPROFILE_TITLE", WebLanguages.getInstance().getText("ENDENTITYPROFILE"));
                    if(userdata.getEndEntityProfileId() != 0) {
                        view.addObject("ENDENTITYPROFILE",  endEntityProfileSession.getEndEntityProfileName(userdata.getEndEntityProfileId()));
                    }else {
                        view.addObject("ENDENTITYPROFILE",  WebLanguages.getInstance().getText("NOENDENTITYPROFILEDEFINED"));
                    }
                    boolean  cleartextpassword= endentityprofile.getUse(EndEntityProfile.CLEARTEXTPASSWORD,0);
                    view.addObject("USE_CLEARTEXTPASSWORD",cleartextpassword);
                    if(cleartextpassword) {
                        view.addObject("USEINBATCH_ABBR_TITLE", WebLanguages.getInstance().getText("USEINBATCH_ABBR"));
                        view.addObject("CLEARTEXTPASSWORD",userdata.getClearTextPassword()?WebLanguages.getInstance().getText("YES"):WebLanguages.getInstance().getText("NO"));
                    }
                    boolean  email= endentityprofile.getUse(EndEntityProfile.EMAIL,0);
                    view.addObject("USE_EMAIL",email);
                    if(email) {
                        view.addObject("EMAIL_TITLE", WebLanguages.getInstance().getText("EMAIL"));
                        view.addObject("EMAIL",userdata.getEmail());
                    }

                    view.addObject("CERT_SUBJECTDN_TITLE", WebLanguages.getInstance().getText("CERT_SUBJECTDN"));
                    //LinkedHashMap<String ,String> subjectMap=new LinkedHashMap<String ,String>();
                    List<SubjectDnVo> subjectList = new LinkedList<SubjectDnVo>();
                    int subjectfieldsize = endentityprofile.getSubjectDNFieldOrderLength();
                    for(int i = 0; i < subjectfieldsize; i++){
                        int[]  fielddata = endentityprofile.getSubjectDNFieldsInOrder(i);
                        SubjectDnVo subjectDnVo=new SubjectDnVo();
                        subjectDnVo.setTitle(WebLanguages.getInstance().getText(DnComponents.getLanguageConstantFromProfileId(fielddata[EndEntityProfile.FIELDTYPE])));
                        subjectDnVo.setValue(userdata.getSubjectDNField(DnComponents.profileIdToDnId(fielddata[EndEntityProfile.FIELDTYPE]),fielddata[EndEntityProfile.NUMBER]));
                        subjectList.add(subjectDnVo);
                        //subjectMap.put(WebLanguages.getInstance().getText(DnComponents.getLanguageConstantFromProfileId(fielddata[EndEntityProfile.FIELDTYPE])), userdata.getSubjectDNField(DnComponents.profileIdToDnId(fielddata[EndEntityProfile.FIELDTYPE]),fielddata[EndEntityProfile.NUMBER])) ;
                    }

                    view.addObject("subjectList",subjectList);

                    int subjectAltNameFieldOrderLength=endentityprofile.getSubjectAltNameFieldOrderLength();
                    int subjectDirAttrFieldOrderLength=endentityprofile.getSubjectDirAttrFieldOrderLength();
                    view.addObject("OTHERSUBJECTATTR_TITLE", WebLanguages.getInstance().getText("OTHERSUBJECTATTR"));
                    view.addObject("subjectAltNameFieldOrderLength", subjectAltNameFieldOrderLength);
                    view.addObject("subjectDirAttrFieldOrderLength", subjectDirAttrFieldOrderLength);
                    view.addObject("EXT_ABBR_SUBJECTALTNAME_TITLE", WebLanguages.getInstance().getText("EXT_ABBR_SUBJECTALTNAME"));
                    //LinkedHashMap<String ,String> subjectAltNameFieldsInOrderMap=new LinkedHashMap<String ,String>();
                    List<OtherSubjectVo> otherSubjectVoList=new ArrayList<OtherSubjectVo>();
                    for(int i = 0; i < subjectAltNameFieldOrderLength; i++){
                        int[] fielddata =  endentityprofile.getSubjectAltNameFieldsInOrder(i);
                        int fieldtype =  fielddata[EndEntityProfile.FIELDTYPE];
                        if(EndEntityProfile.isFieldImplemented(fieldtype)){
                            OtherSubjectVo osv = new OtherSubjectVo();
                            osv.setTitle(WebLanguages.getInstance().getText(DnComponents.getLanguageConstantFromProfileId(fielddata[EndEntityProfile.FIELDTYPE])));
                            osv.setValue(userdata.getSubjectAltNameField(DnComponents.profileIdToDnId(fielddata[EndEntityProfile.FIELDTYPE]),fielddata[EndEntityProfile.NUMBER]));
                            otherSubjectVoList.add(osv);
                            //subjectAltNameFieldsInOrderMap.put(WebLanguages.getInstance().getText(DnComponents.getLanguageConstantFromProfileId(fielddata[EndEntityProfile.FIELDTYPE])), userdata.getSubjectAltNameField(DnComponents.profileIdToDnId(fielddata[EndEntityProfile.FIELDTYPE]),fielddata[EndEntityProfile.NUMBER]));
                        }
                    }

                    view.addObject("otherSubjectVoList",otherSubjectVoList);
                    view.addObject("EXT_ABBR_SUBJECTDIRATTRS_TITLE", WebLanguages.getInstance().getText("EXT_ABBR_SUBJECTDIRATTRS"));
                    LinkedHashMap<String ,String> subjectDirAttrFieldOrderMap=new LinkedHashMap<String ,String>();
                    for(int i = 0; i < subjectDirAttrFieldOrderLength; i++){
                        int[] fielddata = endentityprofile.getSubjectDirAttrFieldsInOrder(i);
                        subjectDirAttrFieldOrderMap.put(WebLanguages.getInstance().getText((DnComponents.getLanguageConstantFromProfileId(fielddata[EndEntityProfile.FIELDTYPE]))),  userdata.getSubjectDirAttributeField(DnComponents.profileIdToDnId(fielddata[EndEntityProfile.FIELDTYPE]),fielddata[EndEntityProfile.NUMBER]));
                    }
                    view.addObject("subjectDirAttrFieldOrderMap",subjectDirAttrFieldOrderMap);
                    view.addObject("MAINCERTIFICATEDATA_TITLE", WebLanguages.getInstance().getText("MAINCERTIFICATEDATA"));
                    view.addObject("CERTIFICATEPROFILE_TITLE", WebLanguages.getInstance().getText("CERTIFICATEPROFILE"));
                    if(userdata.getCertificateProfileId() != 0){
                        view.addObject("CERTIFICATEPROFILE",  getCertificateProfileName(userdata.getCertificateProfileId()));
                    }else {
                        view.addObject("CERTIFICATEPROFILE", WebLanguages.getInstance().getText("NOCERTIFICATEPROFILEDEFINED"));
                    }
                    view.addObject("CA_TITLE", WebLanguages.getInstance().getText("CA"));
                    view.addObject("CA", userdata.getCAName());
                    view.addObject("TOKEN_TITLE", WebLanguages.getInstance().getText("TOKEN"));
                    String[] tokentexts = SecConst.TOKENTEXTS;
                    int[] tokenids =  SecConst.TOKENIDS;
                    for(int i=0; i <tokentexts.length;i++){
                        if(tokenids[i] == userdata.getTokenType()) {
                            if(tokenids[i] > SecConst.TOKEN_SOFT) {
                                view.addObject("TOKEN",tokentexts[i]);
                            } else {
                                view.addObject("TOKEN",WebLanguages.getInstance().getText(tokentexts[i]));
                            }
                            break;
                        }
                    }
                    boolean issueHardwareTokens=  globalconfiguration.getIssueHardwareTokens();
                    view.addObject("issueHardwareTokens",issueHardwareTokens);
                    if(issueHardwareTokens){
                        view.addObject("HARDTOKENISSUER_TITLE", WebLanguages.getInstance().getText("HARDTOKENISSUER"));
                        if(userdata.getHardTokenIssuerId() == SecConst.NO_HARDTOKENISSUER) {
                            view.addObject("HARDTOKENISSUER",WebLanguages.getInstance().getText("NONE"));
                        }  else {
                            //view.addObject("HARDTOKENISSUER",hardtokensession.getHardTokenIssuerAlias(userdata.getHardTokenIssuerId()));
                        }
                    }
                    boolean  enableKeyRecovery= endentityprofile.getUse(EndEntityProfile.KEYRECOVERABLE,0) ;
                    view.addObject("enableKeyRecovery",enableKeyRecovery);
                    if(enableKeyRecovery){
                        view.addObject("KEYRECOVERABLE_TITLE",WebLanguages.getInstance().getText("KEYRECOVERABLE"));
                        view.addObject("KEYRECOVERABLE",userdata.getKeyRecoverable()?WebLanguages.getInstance().getText("YES"):WebLanguages.getInstance().getText("NO"));
                    }
                }
            }
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("System error "+e);
        }

        return result;

    }


    public List<CertReqHistory> getCertReqUserDatas(String username){
		List<CertReqHistory> history = this.certreqhistorysession.retrieveCertReqHistory(username);
		// Sort it by timestamp, newest first;
		Collections.sort(history, new CertReqUserCreateComparator());
		return history;
	}
	/** Class used to sort CertReq History by users modified time, with latest first*/
	private class CertReqUserCreateComparator implements Comparator<CertReqHistory> {
		@Override
		public int compare(CertReqHistory o1, CertReqHistory o2) {
			return 0 - (o1.getEndEntityInformation().getTimeModified().compareTo(o2.getEndEntityInformation().getTimeModified()));
		}
	}

	  /** @return a more user friendly representation of a Date. */
    public String formatAsISO8601(final Date date) {
        return ValidityDate.formatAsISO8601(date, timeZone);
    }


    /** Method to retrieve a user from the database without inserting it into users data, used by 'edituser.jsp' and page*/
    public UserView findUserForEdit(AuthenticationToken administrator,String username) throws AuthorizationDeniedException {
    	UserView userview = null;
    	EndEntityInformation user = endEntityAccessSession.findUser(administrator, username);
    	if (user != null) {
    	    if (globalconfiguration.getEnableEndEntityProfileLimitations()) {
    	        if (!endEntityAuthorization(administrator, user.getEndEntityProfileId(),AccessRulesConstants.EDIT_END_ENTITY, false)) {
    	            throw new AuthorizationDeniedException("Not authorized to edit user.");
    	        }
    	    }
    	    userview = new UserView(user, casession.getCAIdToNameMap());
    	}
    	return userview;
    }

    public UserView findUser(String username,AuthenticationToken administrator ) throws Exception{
   	if (log.isTraceEnabled()) {
   		log.trace(">findUser(" + username + ")");
   	}
   	EndEntityInformation user = endEntityAccessSession.findUser(administrator, username);
   	UserView userview = null;
   	if (user != null) {
   		userview = new UserView(user,casession.getCAIdToNameMap());
   	}
   	if (log.isTraceEnabled()) {
   		log.trace("<findUser(" + username + "): " + userview);
   	}
   	return userview;
   }




	@Override
	public Result editendentity(HttpServletRequest request, String username, ModelAndView view) {
		Result result=new Result();
		try { AuthenticationToken administrator= getAuthenticationToken(request);
 	    UserView userdata=findUserForEdit(administrator, username);//检查是否编辑的权限
		if (null == userdata) {
				result.setMsg("user is not found!");
				return result;
			}
		EndEntityProfile endentityprofile = endEntityProfileSession.getEndEntityProfile(userdata.getEndEntityProfileId());
        setAddEndentityToView(userdata,userdata.getEndEntityProfileId(), view, endentityprofile, administrator);
        result.setSuccess(true);
 		} catch (AuthorizationDeniedException e) {
 			e.printStackTrace();
 			result.setMsg(e.getMessage());
		}
  		return result;
	}


	private  AuthenticationToken getAuthenticationToken(HttpServletRequest request) {
		    LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken administrator = user.getAuthenticationToken();
			return administrator;
	}
    @Transactional
	@Override
	public Result changeUserData(HttpServletRequest request, EndEntityInformationVo endEntityInformationVo,String primevalUsername) throws AuthorizationDeniedException, EndEntityExistsException, CADoesntExistsException, UserDoesntFullfillEndEntityProfile, WaitingForApprovalException, EjbcaException {
		    Result result=new Result();
 			log.trace(">changeUserData()");
  			AuthenticationToken administrator= getAuthenticationToken(request);
			UserView  userdata=findUserForEdit(administrator, primevalUsername);
			if(null!=userdata) {
			    UserView newuser = new UserView();
			    newuser.setEndEntityProfileId(endEntityInformationVo.getEndentityprofileid());
			    if(!primevalUsername.equals(endEntityInformationVo.getUsername())) {
			    	boolean res=endEntityManagementSessionBean.renameEndEntity(administrator, primevalUsername, endEntityInformationVo.getUsername());
			    	if(!res) {
			    		result.setMsg(WebLanguages.getInstance().getText("ENDENTITYDOESNTEXIST"));
			    		return result;
			    	}
			    }




			    String value="";
	         	String serialnumber="";
	        	 int[] fielddata = null;
	      	    EndEntityProfile  endEntityProfile =  endEntityProfileSession.getEndEntityProfile(endEntityInformationVo.getEndentityprofileid());
	      	    int numberofsubjectdnfields = endEntityProfile.getSubjectDNFieldOrderLength();
	            String subjectdn = "";
	            for(int i=0; i < numberofsubjectdnfields; i++){
	            value = null;
	           fielddata = endEntityProfile.getSubjectDNFieldsInOrder(i);

	           if (!EndEntityProfile.isFieldOfType(fielddata[EndEntityProfile.FIELDTYPE], DnComponents.DNEMAILADDRESS)) {
	               value = request.getParameter("textfieldsubjectdn"+ i);
	           } else {
	               if (endEntityProfile.isRequired(fielddata[EndEntityProfile.FIELDTYPE], fielddata[EndEntityProfile.NUMBER])
	                       || (request.getParameter("selectsubjectdn"+ i) != null && request.getParameter("checkboxsubjectdn"+ i)
	                               .equals("true")))
	                   value = endEntityInformationVo.getSubjectEmail();
	           }
	           if (value != null) {
	               value = value.trim();
	               if (EndEntityProfile.isFieldOfType(fielddata[EndEntityProfile.FIELDTYPE], DnComponents.DNSERIALNUMBER)) {
	                   serialnumber = value;
	               }
	               endEntityProfile.setValue(fielddata[EndEntityProfile.FIELDTYPE], fielddata[EndEntityProfile.NUMBER], value);
	               final String field = DNFieldExtractor.getFieldComponent(
	                       DnComponents.profileIdToDnId(fielddata[EndEntityProfile.FIELDTYPE]), DNFieldExtractor.TYPE_SUBJECTDN)
	                       + value;
	               final String dnPart;
	               if (field.charAt(field.length() - 1) != '=') {
	                   dnPart = org.ietf.ldap.LDAPDN.escapeRDN(field);
	               } else {
	                   dnPart = field;
	               }
	               if (subjectdn.equals(""))
	                   subjectdn = dnPart;
	               else
	                   subjectdn += ", " + dnPart;
	           }

	           value = request.getParameter("selectsubjectdn"+ i);
	           if (value != null) {
	               value = value.trim();
	               if (!value.equals("")) {
	                   value = org.ietf.ldap.LDAPDN.escapeRDN(DNFieldExtractor.getFieldComponent(
	                           DnComponents.profileIdToDnId(fielddata[EndEntityProfile.FIELDTYPE]), DNFieldExtractor.TYPE_SUBJECTDN)
	                           + value);
	                   if (subjectdn.equals(""))
	                       subjectdn = value;
	                   else
	                       subjectdn += ", " + value;
	               }
	           }
	       }

			    newuser.setUsername(endEntityInformationVo.getUsername());
			    if(StringUtils.isNotEmpty(endEntityInformationVo.getPassword())) {
			    	newuser.setPassword(endEntityInformationVo.getPassword());
			    }

			    ExtendedInformation ei = newuser.getExtendedInformation();
			    if (ei == null) {
			        ei = new ExtendedInformation();
			    }
			    EditEndEntityBean editEndEntityBean=new EditEndEntityBean();
			    editEndEntityBean.setExtendedInformation(ei);
			    ExtendedInformation userei = userdata.getExtendedInformation();
			    if (userei != null) {
			        ei.setRemainingLoginAttempts(userei.getRemainingLoginAttempts());
			    }

			    if (StringUtils.isNotEmpty(endEntityInformationVo.getMaxLoginAttempts())) {
			        ei.setMaxLoginAttempts(Integer.parseInt(endEntityInformationVo.getMaxLoginAttempts()));
			        newuser.setExtendedInformation(ei);
			    }

			    if(userdata.getPassword() != null && userdata.getPassword().trim().equals("")) {
			    	userdata.setPassword(null);
			    }
			    EndEntityInformation uservo = new EndEntityInformation(endEntityInformationVo.getUsername(), subjectdn, endEntityInformationVo.getCaid(), endEntityInformationVo.getSubjectAltName(),
			    		endEntityInformationVo.getSubjectEmail(), endEntityInformationVo.getStatus(), new EndEntityType(endEntityInformationVo.isKeyrecoverable()?EndEntityTypes.KEYRECOVERABLE:EndEntityTypes.ENDUSER), endEntityInformationVo.getEndentityprofileid(), endEntityInformationVo.getCertificateprofileid(),
						null,null, endEntityInformationVo.getTokentype(), endEntityInformationVo.getHardtokenissuerid(), null);
				uservo.setPassword(endEntityInformationVo.getPassword());
				uservo.setExtendedinformation(userdata.getExtendedInformation());
				uservo.setCardNumber(userdata.getCardNumber());
				endEntityManagementSessionBean.changeUser(getAuthenticationToken(request), uservo, userdata.getClearTextPassword());
				result.setSuccess(true);
			}else {
			 	result.setMsg(WebLanguages.getInstance().getText("ENDENTITYDOESNTEXIST"));
				return result;
			}

			log.trace("<changeUserData()");

        return result;
	}


    public Result viewcertificate(HttpServletRequest request, String username, int index, ModelAndView view) {
        Result result=new Result();
        AuthenticationToken administrator= getAuthenticationToken(request);
        List<CertificateDataWrapper> list=certificatesession.getCertificateDataByUsername(username, false, null);
        if (!list.isEmpty()) {
            CertificateView certificateView = new CertificateView(list.get(index));
            ViewcertificateVo viewcertificateVo = new ViewcertificateVo();
            viewcertificateVo.setUsername(WebLanguages.getInstance().getText("USERNAME"));
            viewcertificateVo.setUsernameVal(certificateView.getUsername());
            viewcertificateVo.setCertificatenr(WebLanguages.getInstance().getText("CERTIFICATENR"));
            viewcertificateVo.setCertificatenrVal((index + 1) + WebLanguages.getInstance().getText("OF") + list.size());
            viewcertificateVo.setCert_typeversion(WebLanguages.getInstance().getText("CERT_TYPEVERSION"));
            viewcertificateVo.setCert_typeversionVal(certificateView.getType()+ WebLanguages.getInstance().getText("VER") + certificateView.getVersion());
            viewcertificateVo.setCert_serialnumber(WebLanguages.getInstance().getText("CERT_SERIALNUMBER"));
            viewcertificateVo.setCert_serialnumberVal(getFormatedCertSN(certificateView));
            viewcertificateVo.setCert_issuerdn(WebLanguages.getInstance().getText("CERT_ISSUERDN"));
            viewcertificateVo.setCert_issuerdnVal(certificateView.getIssuerDN());
            viewcertificateVo.setCert_validto(WebLanguages.getInstance().getText("CERT_VALIDTO"));
            viewcertificateVo.setCert_validtoVal(certificateView.getValidToString());
            viewcertificateVo.setCert_validfrom(WebLanguages.getInstance().getText("CERT_VALIDFROM"));
            viewcertificateVo.setCert_validfromVal(certificateView.getValidFromString());
            viewcertificateVo.setCert_validto(WebLanguages.getInstance().getText("CERT_VALIDTO"));
            viewcertificateVo.setCert_validtoVal(certificateView.getValidToString());
            viewcertificateVo.setCert_subjectdn(WebLanguages.getInstance().getText("CERT_SUBJECTDN"));
            viewcertificateVo.setCert_subjectdnVal(certificateView.getSubjectDN());
            viewcertificateVo.setSignaturealgorithm(WebLanguages.getInstance().getText("SIGNATUREALGORITHM"));
            viewcertificateVo.setSignaturealgorithmVal(certificateView.getSignatureAlgoritm());
            boolean type=!certificateView.getType().equalsIgnoreCase("CVC");
            view.addObject("type", type);
            if (type) {
                viewcertificateVo.setExt_abbr_subjectaltname(WebLanguages.getInstance().getText("EXT_ABBR_SUBJECTALTNAME"));
                if(certificateView.getSubjectAltName() == null) {
                    viewcertificateVo.setExt_abbr_subjectaltnameVal((WebLanguages.getInstance().getText("ALT_NONE")));
                }else {
                    viewcertificateVo.setExt_abbr_subjectaltnameVal(certificateView.getSubjectAltName());
                }

                viewcertificateVo.setExt_abbr_subjectdirattrs(WebLanguages.getInstance().getText("EXT_ABBR_SUBJECTDIRATTRS"));
                if(certificateView.getSubjectDirAttr()== null) {
                    viewcertificateVo.setExt_abbr_subjectdirattrsVal((WebLanguages.getInstance().getText("SDA_NONE")));
                }else {
                    viewcertificateVo.setExt_abbr_subjectdirattrsVal(certificateView.getSubjectDirAttr());
                }


                viewcertificateVo.setExt_abbr_keyusage(WebLanguages.getInstance().getText("EXT_ABBR_KEYUSAGE"));
                boolean first= true;
                boolean none = true;
                StringBuilder keyUsageSb=new StringBuilder();
                if(certificateView.getKeyUsage(CertificateConstants.DIGITALSIGNATURE)){
                    keyUsageSb.append(WebLanguages.getInstance().getText("KU_DIGITALSIGNATURE"));
                    first=false;
                    none =false;
                }
                if(certificateView.getKeyUsage(CertificateConstants.NONREPUDIATION)){
                    if(!first) {
                        keyUsageSb.append(", ");
                    }
                    first=false;
                    none =false;
                    keyUsageSb.append(WebLanguages.getInstance().getText("KU_NONREPUDIATION"));
                }
                if(certificateView.getKeyUsage(CertificateConstants.KEYENCIPHERMENT)){
                    if(!first) {
                        keyUsageSb.append(", ");
                    }
                    first=false;
                    none =false;
                    keyUsageSb.append(WebLanguages.getInstance().getText("KU_KEYENCIPHERMENT"));
                }
                if(certificateView.getKeyUsage(CertificateConstants.DATAENCIPHERMENT)){
                    if(!first) {
                        keyUsageSb.append(", ");
                    }
                    first=false;
                    none =false;
                    keyUsageSb.append(WebLanguages.getInstance().getText("KU_DATAENCIPHERMENT"));
                }
                if(certificateView.getKeyUsage(CertificateConstants.KEYAGREEMENT)){
                    if(!first) {
                        keyUsageSb.append(", ");
                    }
                    first=false;
                    none =false;
                    keyUsageSb.append(WebLanguages.getInstance().getText("KU_KEYAGREEMENT"));
                }
                if(certificateView.getKeyUsage(CertificateConstants.KEYCERTSIGN)){
                    if(!first) {
                        keyUsageSb.append(", ");
                    }
                    first=false;
                    none =false;
                    keyUsageSb.append(WebLanguages.getInstance().getText("KU_KEYCERTSIGN"));
                }
                if(certificateView.getKeyUsage(CertificateConstants.CRLSIGN)){
                    if(!first) {
                        keyUsageSb.append(", ");
                    }
                    first=false;
                    none =false;
                    keyUsageSb.append(WebLanguages.getInstance().getText("KU_CRLSIGN"));
                }
                if(certificateView.getKeyUsage(CertificateConstants.ENCIPHERONLY)){
                    if(!first) {
                        keyUsageSb.append(", ");
                    }
                    first=false;
                    none =false;
                    keyUsageSb.append(WebLanguages.getInstance().getText("KU_ENCIPHERONLY"));
                }
                if(certificateView.getKeyUsage(CertificateConstants.DECIPHERONLY)){
                    if(!first) {
                        keyUsageSb.append(", ");
                    }
                    first=false;
                    none =false;
                    keyUsageSb.append(WebLanguages.getInstance().getText("KU_DECIPHERONLY"));
                }
                if(none){
                    keyUsageSb.append(WebLanguages.getInstance().getText("KU_NONE"));
                }
                viewcertificateVo.setExt_abbr_keyusageVal(keyUsageSb.toString());
            }

            viewcertificateVo.setCert_publickey(WebLanguages.getInstance().getText("CERT_PUBLICKEY"));
            StringBuilder sb=new StringBuilder();


            sb.append(certificateView.getPublicKeyAlgorithm());
            if("EC".equals(certificateView.getCertificate().getPublicKey().getAlgorithm()))
                sb.append("(sm2p256v1) ");
            else
                sb.append(certificateView.getKeySpec(WebLanguages.getInstance().getText("BITS")));
            if(certificateView.getPublicKeyModulus() != null) {
                sb.append(certificateView.getPublicKeyModulus());
            }
            viewcertificateVo.setCert_publickeyVal(sb.toString());

            viewcertificateVo.setExt_abbr_basicconstraints(WebLanguages.getInstance().getText("EXT_ABBR_BASICCONSTRAINTS"));
            viewcertificateVo.setExt_abbr_basicconstraintsVal(certificateView.getBasicConstraints(WebLanguages.getInstance().getText("EXT_UNUSED"), WebLanguages.getInstance().getText("EXT_PKIX_BC_CANOLIMIT"), WebLanguages.getInstance().getText("EXT_PKIX_BC_ENDENTITY"), WebLanguages.getInstance().getText("EXT_PKIX_BC_CAPATHLENGTH")));
            viewcertificateVo.setExt_abbr_extendedkeyusage(WebLanguages.getInstance().getText("EXT_ABBR_EXTENDEDKEYUSAGE"));

            String[] extendedkeyusage = certificateView.getExtendedKeyUsageAsTexts((AvailableExtendedKeyUsagesConfiguration) globalConfigurationSessionLocal.getCachedConfiguration(AvailableExtendedKeyUsagesConfiguration.CONFIGURATION_ID));
            sb.delete(0, sb.length());
            for(int i=0; i<extendedkeyusage.length; i++){
                if(sb.length()>0) {
                    sb.append(", ").append(WebLanguages.getInstance().getText(extendedkeyusage[i]));
                }else {
                    sb.append(WebLanguages.getInstance().getText(extendedkeyusage[i]));
                }
            }
            if(extendedkeyusage == null || extendedkeyusage.length == 0) {
                sb.append(WebLanguages.getInstance().getText("EKU_NONE"));
            }
            viewcertificateVo.setExt_abbr_extendedkeyusageVal(sb.toString());
            viewcertificateVo.setExt_abbr_nameconstraints(WebLanguages.getInstance().getText("EXT_ABBR_NAMECONSTRAINTS"));
            viewcertificateVo.setExt_abbr_nameconstraintsVal(certificateView.hasNameConstraints()?WebLanguages.getInstance().getText("YES"):WebLanguages.getInstance().getText("NO"));

            viewcertificateVo.setExt_abbr_qcstatements(WebLanguages.getInstance().getText("EXT_ABBR_QCSTATEMENTS"));
            viewcertificateVo.setExt_abbr_qcstatementsVal(certificateView.hasQcStatement()?WebLanguages.getInstance().getText("YES"):WebLanguages.getInstance().getText("NO"));

            viewcertificateVo.setExt_certificate_transparency_scts(WebLanguages.getInstance().getText("EXT_CERTIFICATE_TRANSPARENCY_SCTS"));
            viewcertificateVo.setExt_certificate_transparency_sctsVal(certificateView.hasCertificateTransparencySCTs()?WebLanguages.getInstance().getText("YES"):WebLanguages.getInstance().getText("NO"));

            viewcertificateVo.setFingerprint_sha256(WebLanguages.getInstance().getText("FINGERPRINT_SHA256"));
            viewcertificateVo.setFingerprint_sha256Val( certificateView.getSHA256Fingerprint());

            viewcertificateVo.setFingerprint_sha1(WebLanguages.getInstance().getText("FINGERPRINT_SHA1"));
            viewcertificateVo.setFingerprint_sha1Val( certificateView.getSHA1Fingerprint());

            viewcertificateVo.setRevoked(WebLanguages.getInstance().getText("REVOKED"));
            sb.delete(0, sb.length());
            boolean isRevoked= certificateView.isRevoked();
            view.addObject("isRevoked", isRevoked);
            if(isRevoked){
                sb.append(WebLanguages.getInstance().getText("YES"));
                viewcertificateVo.setRevocationdate((WebLanguages.getInstance().getText("CRL_ENTRY_REVOCATIONDATE")));
                viewcertificateVo.setRevocationdateVal(formatAsISO8601(certificateView.getRevocationDate()));
                viewcertificateVo.setRevocationreasons(WebLanguages.getInstance().getText("REVOCATIONREASONS"));
                viewcertificateVo.setRevocationreasonsVal(certificateView.getRevocationReason()==null?"":WebLanguages.getInstance().getText(certificateView.getRevocationReason()));
            } else {
                sb.append(WebLanguages.getInstance().getText("NO"));
            }
            viewcertificateVo.setRevokedVal(sb.toString());

            try {
                if(authorizedToRevokeCert(certificateView.getUsername(),administrator) && isAuthorizedNoLog(administrator,AUTHORIZED_RA_REVOKE_RIGHTS)){
                    if ( !certificateView.isRevoked() || certificateView.isRevokedAndOnHold() ){
                        Map<String,Integer> revokeMap=new HashMap<String,Integer>();
                        for(int i=0; i < SecConst.reasontexts.length; i++){
                            if(i!= 7){
                                revokeMap.put(WebLanguages.getInstance().getText(SecConst.reasontexts[i]), i);
                            }
                        }
                        viewcertificateVo.setRevoke(WebLanguages.getInstance().getText("REVOKE"));
                        view.addObject("revokeMap", revokeMap);//
                    }
                }


                view.addObject("viewcertificateVo",viewcertificateVo);//
                view.addObject("isRevokedAndOnHold", certificateView.isRevokedAndOnHold());//
                view.addObject("UNREVOKE", WebLanguages.getInstance().getText("UNREVOKE"));//
                view.addObject("certindex", index);//
                view.addObject("certsize", list.size());//

            } catch (AuthorizationDeniedException e) {
                e.printStackTrace();
            }
            result.setSuccess(true);
            return result;
        }else {
            result.setMsg(WebLanguages.getInstance().getText("CERTIFICATEDOESNTEXIST"));
            return result;
        }
    }




    public boolean authorizedToRevokeCert(String username, AuthenticationToken administrator) throws AuthorizationDeniedException{
    	boolean returnval=false;
    	EndEntityInformation data = endEntityAccessSession.findUser(administrator, username);
    	if (data == null) {
    		return false;
    	}
    	int profileid = data.getEndEntityProfileId();
    	if (globalconfiguration.getEnableEndEntityProfileLimitations()) {
    		returnval= endEntityAuthorization(administrator, profileid, AccessRulesConstants.REVOKE_END_ENTITY, false);
    	} else {
    		returnval=true;
    	}
    	return returnval;
    }

    public String getFormatedCertSN(CertificateView certificateData) {

    	String serialnumber = certificateData.getSerialNumber();
    	if(StringUtils.equals(certificateData.getType(), "X.509")) {
    		if((serialnumber.length()%2) != 0) {
    			serialnumber = "0" + serialnumber;
    		}

    		int octetChar = serialnumber.charAt(0) - '0';
    		if(octetChar > 7) {
    			serialnumber = "00" + serialnumber;
    		}

    	}
    	return serialnumber;

    }

    @Transactional
    public Result revoke(HttpServletRequest request, String username,int reason, String sha1Finger) {
        AuthenticationToken administrator= getAuthenticationToken(request);
        Result result=new Result();
        List<Integer> excludeStatus = new ArrayList<Integer>();
        excludeStatus.add(CertificateConstants.CERT_REVOKED);
        List<CertificateDataWrapper> list=certificatesession.getCertificateDataByUsername(username, false, excludeStatus);
        if(list.size()<=0) {
            result.setMsg(WebLanguages.getInstance().getText("CERTIFICATEDOESNTEXIST"));
            return result;
        }else if(list.size() == 1){
            try {
                log.info("The User has only one cert with status no_revoked!");
                CertificateView  c = new CertificateView(list.get(0));
                if(c.getSHA1Fingerprint().equals(sha1Finger)) {
                    endEntityManagementSessionBean.revokeUser(administrator, username, reason);
                    result.setSuccess(true);
                    result.setMsg("OK");
                    return result;
                }else {
                    log.error("The sha1Finger of the certificate to revoke was not found!");
                    result.setMsg("The certificate had been revoked!");
                }
            } catch (ApprovalException | AlreadyRevokedException | AuthorizationDeniedException | FinderException
                    | WaitingForApprovalException e) {
                log.error(e);
                result.setMsg(e.getMessage());
            }
        }else{
            CertificateView  certificatedata = null;
            for(int i=0;i<list.size();i++) {
                CertificateView  c = new CertificateView(list.get(i));
                if(c.getSHA1Fingerprint().equals(sha1Finger)) {
                    certificatedata = c;
                    break;
                }
            }
            if(certificatedata != null) {
                try {
                    if(authorizedToRevokeCert(certificatedata.getUsername(),administrator) && isAuthorizedNoLog(administrator,AUTHORIZED_RA_REVOKE_RIGHTS) && (!certificatedata.isRevoked())) {
                        endEntityManagementSessionBean.revokeCert(administrator, certificatedata.getSerialNumberBigInt(), certificatedata.getIssuerDNUnEscaped(), reason);
                        result.setSuccess(true);
                        result.setMsg("OK");
                        return result;
                    }
                } catch (ApprovalException e) {
                    log.error(e);
                    result.setMsg("ApprovalException ");
                } catch (AlreadyRevokedException e) {
                    log.error(e);
                    result.setMsg("AlreadyRevokedException");
                } catch (AuthorizationDeniedException e) {
                    log.error(e);
                    result.setMsg("AuthorizationDeniedException");
                } catch (FinderException e) {
                    result.setMsg("FinderException");
                    log.error(e);
                } catch (WaitingForApprovalException e) {
                    result.setMsg("WaitingForApprovalException");
                    log.error(e);
                }
            }
        }
        return result;
    }

    @Transactional
    public Result revokeUser(HttpServletRequest request, String username,int reason) {
        AuthenticationToken administrator= getAuthenticationToken(request);
        Result result=new Result();
        List<Integer> excludeStatus = new ArrayList<Integer>();
        excludeStatus.add(CertificateConstants.CERT_REVOKED);
        List<CertificateDataWrapper> list=certificatesession.getCertificateDataByUsername(username, false, excludeStatus);
        if(list.size()<=0) {
            result.setMsg(WebLanguages.getInstance().getText("CERTIFICATEDOESNTEXIST"));
            return result;
        }else{
            try {
                log.info("The User has only one cert with status no_revoked!");
                endEntityManagementSessionBean.revokeUser(administrator, username, reason);
                result.setSuccess(true);
                result.setMsg("OK");
                return result;
            } catch (ApprovalException | AlreadyRevokedException | AuthorizationDeniedException | FinderException
                    | WaitingForApprovalException e) {
                log.error(e);
                result.setMsg(e.getMessage());
            }
        }
        return result;
    }


}
