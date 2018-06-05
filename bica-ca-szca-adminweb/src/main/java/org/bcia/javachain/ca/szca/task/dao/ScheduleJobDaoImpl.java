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

package org.bcia.javachain.ca.szca.task.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.bcia.javachain.ca.szca.task.entity.ScheduleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;



@Repository
public class ScheduleJobDaoImpl implements ScheduleJobDao{
	private static Logger logger = LoggerFactory.getLogger(ScheduleJobDaoImpl.class);

 	
	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public boolean deleteByPrimaryKey(Long job_id) {
		 ScheduleJob scheduleJob = entityManager.find(ScheduleJob.class, job_id);
		 if(null==scheduleJob) {
			 return false;
		 }else{
			try {
				entityManager.remove(scheduleJob);
				return true;
			} catch (Exception e) {
  				logger.error("删除任务失败", e);
 				return false;
			}
		 }
 	}

	@Override
	public long insert(ScheduleJob record) {
		 try {
	            entityManager.persist(record);
	            entityManager.flush();
	            return record.getJobId();
	        }catch (Exception e){
	        	logger.error("新增任务失败", e);
 				return -1;
	        }
	}



	@Override
	public ScheduleJob selectByPrimaryKey(Long job_id) {
 		return entityManager.find(ScheduleJob.class, job_id);
	}

	@Override
	public boolean updateByPrimaryKeySelective(ScheduleJob record) {
         try {
            entityManager.merge(record);
            return true;
        } catch (Exception e) {
        	logger.error("更新任务失败", e);
        	 return false;
        }
 	}

 
	@Override
	public List<ScheduleJob> getAll() {
		String hql = "from ScheduleJob";
		final Query query = entityManager.createQuery(hql);
		List<ScheduleJob> list= query.getResultList();
		entityManager.close();
        return list;
	}

}
