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

package org.bcia.javachain.ca.szca.admin.ra.servcie;

import java.util.List;

import javax.persistence.EntityManager;

import org.bcia.javachain.ca.szca.admin.ra.entity.CertProcessData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional 
public class CertProcessServiceImpl implements CertProcessService{

	
	public List<CertProcessData> getAllCertProccessList(EntityManager entityManager) {
		return entityManager.createQuery("from CertProcessData").getResultList();
	}

	
	public void addCertProcess(EntityManager entityManager, CertProcessData cpd) {
		entityManager.persist(cpd);
		entityManager.flush();
	}


	public CertProcessData getCertProcess(EntityManager entityManager, long id) {
		return entityManager.find(CertProcessData.class, id);
	}


	@Override
	public void updateCertProcess(EntityManager entityManager, CertProcessData cpd) {
		entityManager.merge(cpd);
	}


	@Override
	public void updateCertProfileName(EntityManager entityManager, String oldName, String newName) {
		entityManager.createQuery("update CertProcessData set certProfileName=:newName where certProfileName=:oldName")
		.setParameter("newName", newName)
		.setParameter("oldName", oldName)
		.executeUpdate();
	}


	@Override
	public void updateEndEntityProfileName(EntityManager entityManager, String oldName, String newName) {
		entityManager.createQuery("update CertProcessData set endEntityProfileName=:newName where endEntityProfileName=:oldName")
		.setParameter("newName", newName)
		.setParameter("oldName", oldName)
		.executeUpdate();
	}


	@Override
	public void delCertProcess(EntityManager entityManager, long id) {
		CertProcessData cpd = entityManager.find(CertProcessData.class, id);
		entityManager.remove(cpd);
	}

}
