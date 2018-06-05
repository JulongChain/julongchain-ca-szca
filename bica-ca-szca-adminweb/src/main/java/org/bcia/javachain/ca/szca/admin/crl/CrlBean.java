
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

package org.bcia.javachain.ca.szca.admin.crl;

import java.io.Serializable;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.bcia.javachain.ca.result.Result;
import org.bcia.javachain.ca.szca.admin.controller.CaInfoHolder;
import org.bcia.javachain.ca.szca.admin.crl.service.CrlService;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authorization.AuthorizationDeniedException;
import org.cesecore.certificates.ca.CA;
import org.cesecore.certificates.ca.CADoesntExistsException;
import org.cesecore.certificates.ca.CAInfo;
import org.cesecore.certificates.ca.CAOfflineException;
import org.cesecore.certificates.crl.CRLInfo;
import org.cesecore.keys.token.CryptoTokenOfflineException;
import org.quartz.SchedulerException;
//import org.ejbca.core.ejb.crl.PublishingCrlSessionLocal;
//import org.ejbca.core.model.util.EjbLocalHelper;
//import org.ejbca.ui.web.admin.configuration.EjbcaWebBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.szca.common.LoginUser;

import cn.net.bcia.bcca.core.ejb.authentication.web.WebAuthenticationProviderSessionLocal;
import cn.net.bcia.bcca.core.ejb.crl.PublishingCrlSessionLocal;
import cn.net.bcia.cesecore.certificates.ca.CaSessionLocal;
import cn.net.bcia.cesecore.util.CertTools;
 
 
@Service
public class CrlBean implements Serializable {

    /** Backing object for main page list of CA and CRL statuses. */
    public class CaCrlStatusInfo {
        final private String caName;
        final private boolean caService;
        final private boolean crlStatus;
        private CaCrlStatusInfo(final String caName, final boolean caService, final boolean crlStatus) {
            this.caName = caName;
            this.caService = caService;
            this.crlStatus = crlStatus;
        }
        public String getCaName() { return caName; }
        public boolean isCaService() { return caService; }
        public boolean isCrlStatus() { return crlStatus; }
    }

	private static final long serialVersionUID = 3L;
	private static final Logger log = Logger.getLogger(CrlBean.class);
	
    public static final int CATOKENTYPE_P12          = 1;
    public static final int CATOKENTYPE_HSM          = 2;
	public static final int CATOKENTYPE_NULL         = 3;
	
	@PersistenceContext 
	private EntityManager entityManager;
	
	@Autowired
	private CrlService crlTaskService;
	
	
	@Autowired
	CaSessionLocal  caSession;
  
	@Autowired
    private cn.net.bcia.cesecore.certificates.crl.CrlStoreSession crlStoreSession;
	@Autowired
    private PublishingCrlSessionLocal publishingCrlSession;
 
	//WebAuthenticationProviderSessionLocal authenticationSession = ejbLocalHelper.getWebAuthenticationProviderSession();
	
	@Autowired
	WebAuthenticationProviderSessionLocal authenticationSession;
  //  private boolean initialized;
  //  private AuthenticationToken authenticationToken;
  
 
 
	/** Creates a new instance of CaInterfaceBean */
    public CrlBean() { }
   
    // Public methods
//    public void initialize(HttpServletRequest   request)  throws Exception{
//        
//        	final X509Certificate[] certificates = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
//            if (certificates == null || certificates.length == 0) {
//                throw new AuthenticationFailedException("Client certificate required.");
//            } else {
//                final Set<X509Certificate> credentials = new HashSet<X509Certificate>();
//                credentials.add(certificates[0]);
//                final AuthenticationSubject subject = new AuthenticationSubject(null, credentials);
//       
////          authenticationToken = ejbcawebbean.getAdminObject();
//        	this.authenticationToken = authenticationSession.authenticate(subject);
//          if (authenticationToken == null) {
//              throw new AuthenticationFailedException("Authentication failed for certificate: " + CertTools.getSubjectDN(certificates[0]));
//          }
//          
//    }
//  }

    public java.util.List<CaInfoHolder>getAllCa(AuthenticationToken authenticationToken){
    	java.util.List<Integer> ids = caSession.getAllCaIds();
    	java.util.List<CaInfoHolder> cas = new ArrayList<CaInfoHolder>();
    	for(Integer caid:ids){
    		CA ca = null;
    		try{
    			ca=	caSession.getCA(authenticationToken , caid);
    		}catch(Exception e){
    		e.printStackTrace();
    		}
    		if(ca!=null){
    			CaInfoHolder holder = new CaInfoHolder();
    			
    			CAInfo caInfo =ca.getCAInfo();
    			 
    		//public CRLInfo getLastCRLInfo(CAInfo caInfo, boolean deltaCRL) {
    			final String issuerdn;// use issuer DN from CA certificate. Might differ from DN in CAInfo.
    			{
    				final Collection<Certificate> certs = caInfo.getCertificateChain();
    				final Certificate cacert = !certs.isEmpty() ? (Certificate)certs.iterator().next(): null;
    				issuerdn = cacert!=null ? CertTools.getSubjectDN(cacert) : null;
    			}
//    			return crlStoreSession.getLastCRLInfo(issuerdn, deltaCRL);          
    		//}
    		 CRLInfo crlinfo = crlStoreSession.getLastCRLInfo(issuerdn, false);
    		 holder.setCaInfo(caInfo);
    		 holder.setCrlInfo(crlinfo);
    		 cas.add(holder);
    		}
    	}
    	return cas;
    }
       
	public void createCRL(int caid, AuthenticationToken authenticationToken)
			throws CryptoTokenOfflineException, CAOfflineException {
		try {
			publishingCrlSession.forceCRL(authenticationToken, caid);
		} catch (CADoesntExistsException e) {
			throw new RuntimeException(e);
		} catch (AuthorizationDeniedException e) {
			throw new RuntimeException(e);
		}
	}

    public void createDeltaCRL(int caid,AuthenticationToken authenticationToken) throws CryptoTokenOfflineException, CAOfflineException {      
        try {
            publishingCrlSession.forceDeltaCRL(authenticationToken, caid);
        } catch (CADoesntExistsException e) {
            throw new RuntimeException(e);
        } catch (AuthorizationDeniedException e) {
            throw new RuntimeException(e);
		}
    }

    public int getLastCRLNumber(String  issuerdn) {
    	return crlStoreSession.getLastCRLNumber(issuerdn, false);      
    }

    /**
     * @param caInfo of the CA that has issued the CRL.
     * @param deltaCRL false for complete CRL info, true for delta CRLInfo
     * @return CRLInfo of last CRL by CA or null if no CRL exists.
     */
	public CRLInfo getLastCRLInfo(CAInfo caInfo, boolean deltaCRL) {
		final String issuerdn;// use issuer DN from CA certificate. Might differ from DN in CAInfo.
		{
			final Collection<Certificate> certs = caInfo.getCertificateChain();
			final Certificate cacert = !certs.isEmpty() ? (Certificate)certs.iterator().next(): null;
			issuerdn = cacert!=null ? CertTools.getSubjectDN(cacert) : null;
		}
		return crlStoreSession.getLastCRLInfo(issuerdn, deltaCRL);          
	}
   
	
	
	public List<CrlStrategyData> getAllCrlStrategyData() {
		String hql = "SELECT a FROM CrlStrategyData a ORDER BY a.caid";
		Query query = entityManager.createQuery(hql);
		return (List<CrlStrategyData>)query.getResultList();
	}
 
	
	public CrlStrategyData getCrlStrategyDataById(int caid) {
		return (CrlStrategyData)entityManager.find(CrlStrategyData.class, caid);
	}
 
	@Transactional
	public Result udpateCrlStrategyData(int caid,String cronExpression,LoginUser  user) throws SchedulerException {
		CrlStrategyData strategy = this.getCrlStrategyDataById(caid);
		Result result = null;
		String msg = "";
		if(strategy==null) {
			result =	crlTaskService.startUpdateCrl(caid, cronExpression);
			strategy = new CrlStrategyData();
			strategy.setCaid(caid);
			strategy.setCronExpr(cronExpression);
			strategy.setCrttime(System.currentTimeMillis());
			strategy.setCrtuser(user.getUsername());
			strategy.setRunning(result.isSuccess());
			strategy.setTaskresult(result.toString());
			entityManager.persist(strategy);
			msg = "已经创建CA[%s]的CRL生成策略";
		}
		else {
			result = crlTaskService.updateCrlCronExpression(caid, cronExpression);
			strategy.setCronExpr(cronExpression);
			strategy.setModtime(System.currentTimeMillis());
			strategy.setModuser(user.getUsername());
			strategy.setRunning(result.isSuccess());
			strategy.setTaskresult(result.toString());
			entityManager.persist(strategy);
			msg = "已经更新CA[%s]的CRL生成策略";
		}
		msg= String.format(msg, caid);
		result.setMsg(msg);
		return result;
	}
	
	@Transactional
	public Result switchCrlStrategyData(int caid,LoginUser  user) throws Exception{
		CrlStrategyData strategy = this.getCrlStrategyDataById(caid);
		if(strategy==null)
			throw new Exception("此CA尚未配置CRL生成策略，请先配置CRL生成策略。");
		Result result = null;
		String msg = "";
		if(strategy.isRunning()) {
			result = crlTaskService.stopUpdateCrl(caid);
			msg = "已经停止CA[%s]的CRL生成策略";
		}
		else {
			result = crlTaskService.updateCrlCronExpression(caid, strategy.getCronExpr());
			msg = "已经启动CA[%s]的CRL生成策略";
		}
		
		strategy.setModtime(System.currentTimeMillis());
		strategy.setModuser(user.getUsername());
		if(result.isSuccess())
			strategy.setRunning(!strategy.isRunning());
		strategy.setTaskresult(result.toString());
		entityManager.persist(strategy); 
		
		msg= String.format(msg, caid);
		result.setMsg(msg);
		
		return result;
	}
}
