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

package org.bcia.javachain.ca.szca.task;

import org.apache.log4j.Logger;
import org.bcia.javachain.ca.szca.task.entity.ScheduleJob;
import org.bcia.javachain.ca.szca.util.TaskUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
  
/**
* Description:无状态的，主要实现可配置定时 类的方法
* ClassName: QuartzJobFactory
* Date:2018年4月18日 下午2:57:08
* @author power
* @version 1.0
*/
public class QuartzJobFactory implements Job {
	public final Logger log = Logger.getLogger(this.getClass());
 	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
 		TaskUtils.invokMethod(scheduleJob);
 	}
}