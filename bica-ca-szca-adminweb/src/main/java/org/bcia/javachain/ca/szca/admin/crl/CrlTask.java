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

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.bcia.javachain.ca.szca.admin.crl.dao.CrlDao;
import org.bcia.javachain.ca.szca.admin.crl.entity.CrlTaskEntity;
import org.bcia.javachain.ca.szca.config.PropertiesConfig;
import org.bcia.javachain.ca.szca.task.entity.ScheduleJob;
import org.cesecore.authentication.tokens.AuthenticationSubject;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.util.CertTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.net.bcia.bcca.core.ejb.authentication.web.WebAuthenticationProviderSessionLocal;

@Component
public class CrlTask {
	public final Logger log = Logger.getLogger(this.getClass());
	@Autowired
	CrlBean crlBean;
	@Autowired
	WebAuthenticationProviderSessionLocal authenticationSession;
	@Autowired
	CrlDao crlDao;
	
	public void updateCrl(ScheduleJob scheduleJob) {
		//读取证书
 		try {

			String classPath=CrlTask.class.getClassLoader().getResource("/").getPath();
			if (System.getProperty("os.name").startsWith("Windows")) {
				classPath = classPath.replaceAll("%20", " ");
			}
 			KeyStore ks = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(PropertiesConfig.getProperty("threadsCert"));
 			char[] nPassword = PropertiesConfig.getProperty("threadsCertPassword").toCharArray();
			ks.load(fis, nPassword);
			fis.close();
			Enumeration enumas = ks.aliases();
			String keyAlias = null;
			while (enumas.hasMoreElements())// we are readin just one certificate.
			{
				keyAlias = (String) enumas.nextElement();
 			}
			// Now once we know the alias, we could get the keys.
		    Certificate cert = ks.getCertificate(keyAlias);
		    final CertificateFactory cf = CertTools.getCertificateFactory();
			final X509Certificate x509cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(cert.getEncoded()));
			final Set<X509Certificate> credentials = new HashSet<X509Certificate>();
			credentials.add(x509cert);
			// 设置一个 后端线程的证书 给予足够的权限
			final AuthenticationSubject subject = new AuthenticationSubject(null, credentials);
			AuthenticationToken authenticationToken = authenticationSession.authenticate(subject);
			CrlTaskEntity crlTaskEntity=crlDao.getCrlTaskEntityByJobId(scheduleJob.getJobId());
 		    crlBean.createCRL(crlTaskEntity.getCaId(),authenticationToken);// 生产CRL到数据库
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	}
  	 
}