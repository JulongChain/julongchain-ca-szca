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

package org.bcia.javachain.ca.szca.admin.crl.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.bcia.javachain.ca.szca.admin.crl.entity.CrlTaskEntity;
import org.bcia.javachain.ca.szca.admin.crl.service.CrlServiceIpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CrlDaoImpl implements CrlDao{
	private static Logger logger = LoggerFactory.getLogger(CrlServiceIpl.class);

	
	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public CrlTaskEntity getCrlTaskEntityById(int id) {
 		return entityManager.find(CrlTaskEntity.class, id);
	}

	@Override
	public int saveCrlTaskEntity(CrlTaskEntity crlTaskEntity) {
		 try {
	            entityManager.persist(crlTaskEntity);
	            entityManager.flush();
	            return crlTaskEntity.getCaId();
	        }catch (Exception e){
	        	logger.error("新增任务失败", e);
				return -1;
	        }
	}

	@Override
	public CrlTaskEntity getCrlTaskEntityByJobId(long id) {
		String hql="from CrlTaskEntity y where y.jobId=:jobId";
	    Query query = entityManager.createQuery(hql);
	    CrlTaskEntity crlTaskEntity=(CrlTaskEntity) query.setParameter("jobId",id).getSingleResult();
 		return crlTaskEntity;
		
	}

}
