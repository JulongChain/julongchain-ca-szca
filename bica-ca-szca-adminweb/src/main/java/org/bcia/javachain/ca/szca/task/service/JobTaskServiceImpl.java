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

package org.bcia.javachain.ca.szca.task.service;
 


import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.bcia.javachain.ca.szca.task.QuartzJobFactory;
import org.bcia.javachain.ca.szca.task.QuartzJobFactoryDisallowConcurrentExecution;
import org.bcia.javachain.ca.szca.task.dao.ScheduleJobDao;
import org.bcia.javachain.ca.szca.task.entity.ScheduleJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;


/**
* Description: 接口实现类
* ClassName: JobTaskServiceImpl
* Date:2018年4月18日 下午3:18:38
* @author power
* @version 1.0
*/
@Service
public class JobTaskServiceImpl implements JobTaskService{
	public final Logger log = Logger.getLogger(this.getClass());
	public static final String START = "start";
	public static final String STOP = "stop";
	
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private ScheduleJobDao scheduleJobDao;

 
	public List<ScheduleJob> getAllTask() {
		return scheduleJobDao.getAll();
	}
 
	public void addTask(ScheduleJob job) throws SchedulerException {
  		addJob(job);
	}
 
	public ScheduleJob getTaskById(Long jobId) {
		return scheduleJobDao.selectByPrimaryKey(jobId);
	}

	 
	public void changeStatus(Long jobId, String cmd) throws SchedulerException {
		ScheduleJob job = getTaskById(jobId);
		if (job == null) {
			return;
		}
		if (STOP.equals(cmd)) {
			deleteJob(job);
			job.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
		} else if (START.equals(cmd)) {
			job.setJobStatus(ScheduleJob.STATUS_RUNNING);
			addJob(job);
		}
		scheduleJobDao.updateByPrimaryKeySelective(job);
	}

	 
	public void updateCron(Long jobId, String cron) throws SchedulerException {
		ScheduleJob job = getTaskById(jobId);
		if (job == null) {
			return;
		}
		job.setCronExpression(cron);
		job.setJobStatus(ScheduleJob.STATUS_RUNNING);
		job.setUpdateTime(new Date());
		if (ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
			updateJobCron(job);
		}
		scheduleJobDao.updateByPrimaryKeySelective(job);
	}

	 
	public void addJob(ScheduleJob job) throws SchedulerException {
		if (job == null || !ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
			return;
		}

		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		// 不存在，创建一个
		if (null == trigger) {
			Class clazz = ScheduleJob.CONCURRENT_IS.equals(job.getConcurrentStatus()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();
			jobDetail.getJobDataMap().put("scheduleJob", job);
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}

	@PostConstruct
	public void init() throws Exception {
   		// 这里获取任务信息数据
		List<ScheduleJob> jobList = scheduleJobDao.getAll();
 		for (ScheduleJob job : jobList) {
			addJob(job);
		}
	}

 	/**
	* Description: 暂停一个job
	* @param scheduleJob
	* @throws SchedulerException
	* Date: 2018年4月18日 下午3:45:13
	* Author:power
	* Version: 1.0
	*/
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

 
	/**
	* Description:恢复一个job
	* @param scheduleJob
	* @throws SchedulerException
	* Date: 2018年4月18日 下午3:45:05
	* Author:power
	* Version: 1.0
	*/
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}

 
	/**
	* Description:删除一个job
	* @param scheduleJob
	* @throws SchedulerException
	* Date: 2018年4月18日 下午3:31:59
	* Author:power
	* Version: 1.0
	*/
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);
 	}

 
	/**
	* Description:立即执行job
	* @param scheduleJob
	* @throws SchedulerException
	* Date: 2018年4月18日 下午3:44:43
	* Author:power
	* Version: 1.0
	*/
	public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}

 
	/**
	* Description: 更新job时间表达式
	* @param scheduleJob
	* @throws SchedulerException
	* Date: 2018年4月18日 下午3:36:53
	* Author:power
	* Version: 1.0
	*/
	public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
 		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
 		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
 		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
 		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
 		scheduler.rescheduleJob(triggerKey, trigger);
	}

 
}
