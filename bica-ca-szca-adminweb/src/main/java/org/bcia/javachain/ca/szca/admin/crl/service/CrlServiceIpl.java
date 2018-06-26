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

package org.bcia.javachain.ca.szca.admin.crl.service;


import java.util.Date;

import org.bcia.javachain.ca.result.Result;
import org.bcia.javachain.ca.szca.admin.crl.dao.CrlDao;
import org.bcia.javachain.ca.szca.admin.crl.entity.CrlTaskEntity;
import org.bcia.javachain.ca.szca.constants.Constants;
import org.bcia.javachain.ca.szca.task.dao.ScheduleJobDao;
import org.bcia.javachain.ca.szca.task.entity.ScheduleJob;
import org.bcia.javachain.ca.szca.task.service.JobTaskService;
import org.bcia.javachain.ca.szca.task.service.JobTaskServiceImpl;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CrlServiceIpl implements CrlService{
 	private static final String CRL_BEAN_ID = "crlTask";
	public static final String TASK_METHODNAME = "updateCrl";
 	@Autowired
	private	CrlDao crlDao;
	
	@Autowired
	JobTaskService jobTaskService;
	@Autowired
	ScheduleJobDao scheduleJobDao;
	
	@Override
	@Transactional
	public Result startUpdateCrl(int caid, String cronExpression) throws SchedulerException {
		//对某个CA 开启其CRL 定时生产任务
		//验证CA ID  是否正确  TODO
		//获取该CA 是否已经已经建立了关系
		Result result=new Result();
		CrlTaskEntity crlTaskEntity =crlDao.getCrlTaskEntityById(caid);
		if(null==crlTaskEntity) {//不存在
			//新建定时任务
 			StringBuffer sb=new StringBuffer();
			sb.append(CRL_BEAN_ID).append(caid).append("###").append(cronExpression);
			Date date=new Date();
			String desc=sb.toString();
			ScheduleJob scheduleJob=new ScheduleJob();
			scheduleJob.setBeanClass("");
			scheduleJob.setConcurrentStatus(ScheduleJob.CONCURRENT_NOT);
			scheduleJob.setCreateTime(date);
			scheduleJob.setCronExpression(cronExpression);
			scheduleJob.setDescription(desc);
			scheduleJob.setJobGroup(Constants.DEFAULE_JOb_GROUP);
			scheduleJob.setJobName(CRL_BEAN_ID);
			scheduleJob.setJobStatus(ScheduleJob.STATUS_RUNNING);
			scheduleJob.setMethodName(TASK_METHODNAME);
			scheduleJob.setSpringBeanId(CRL_BEAN_ID);
		    long jobId=scheduleJobDao.insert(scheduleJob);
		    crlTaskEntity=new CrlTaskEntity();
		    crlTaskEntity.setCaId(caid);
		    crlTaskEntity.setJobId(jobId);
		    crlTaskEntity.setCreateTime(date);
		    crlDao.saveCrlTaskEntity(crlTaskEntity);//保存关系
		    jobTaskService.addTask(scheduleJob);// 加入执行
		    result.setSuccess(true);
			return result;
		}else {
  			try {
				jobTaskService.updateCron(crlTaskEntity.getJobId(), cronExpression);
				result.setSuccess(true);
				return result;
			} catch (SchedulerException e) {
 				e.printStackTrace();
 				result.setSuccess(false);
 				result.setMsg(e.getMessage());
				return result;
			}
		}
 		
	}

	@Override
	public Result updateCrlCronExpression(int caid, String cronExpression) {
		CrlTaskEntity crlTaskEntity =crlDao.getCrlTaskEntityById(caid);
		Result result=new Result();
		if(null==crlTaskEntity) {//不存在
			result.setMsg(Constants.ERROR_CID_TASK_NOT_FINDED);
			return result;
		}
		try {
			jobTaskService.updateCron(crlTaskEntity.getJobId(), cronExpression);
			result.setSuccess(true);
			return result;
		} catch (SchedulerException e) {
				e.printStackTrace();
				result.setSuccess(false);
				result.setMsg(e.getMessage());
			return result;
		}
 		 
	}

	@Override
	public Result stopUpdateCrl(int caid) {
		CrlTaskEntity crlTaskEntity =crlDao.getCrlTaskEntityById(caid);
		Result result=new Result();
		if(null==crlTaskEntity) {//不存在
			result.setMsg(Constants.ERROR_CID_TASK_NOT_FINDED);
			return result;
		}
		try {
			jobTaskService.changeStatus(crlTaskEntity.getJobId(), JobTaskServiceImpl.STOP);
			result.setSuccess(true);
			return result;
		} catch (SchedulerException e) {
				e.printStackTrace();
				result.setSuccess(false);
				result.setMsg(e.getMessage());
			return result;
		}
 		 
	} }
