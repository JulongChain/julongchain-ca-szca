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

package org.bcia.javachain.ca.szca.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bcia.javachain.ca.szca.admin.crl.service.CrlServiceIpl;
import org.bcia.javachain.ca.szca.task.entity.ScheduleJob;

public class TaskUtils {
	public final static Logger log = Logger.getLogger(TaskUtils.class);

 	/**
	* Description:过反射调用scheduleJob中定义的方法
	* @param scheduleJob
	* Date: 2018年4月18日 下午3:50:25
	* Author:power
	* Version: 1.0
	*/
	public static void invokMethod(ScheduleJob scheduleJob) {
		Object object = null;
		Class clazz = null;
		if (StringUtils.isNotBlank(scheduleJob.getSpringBeanId())) {
			object = SpringUtils.getBean(scheduleJob.getSpringBeanId());
		} else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
			try {
				clazz = Class.forName(scheduleJob.getBeanClass());
				object = clazz.newInstance();
			} catch (Exception e) {
 				log.error("反射异常", e);
			}

		}
		if (object == null) {
			log.error("任务名称 = [" + scheduleJob.getJobName()+ "]---------------未启动成功，请检查是否配置正确！！！");
			return;
		}
		clazz = object.getClass();
		Method method = null;
		try {
			if(CrlServiceIpl.TASK_METHODNAME.equals(scheduleJob.getMethodName())) {
				Class[] types = {ScheduleJob.class};
				method = clazz.getDeclaredMethod(scheduleJob.getMethodName(),types);
 			}else {
				method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
			}
 		} catch (NoSuchMethodException e) {
			log.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，方法名设置错误！！！");
		} catch (SecurityException e) {
 			e.printStackTrace();
		}
		if (method != null) {
			try {
				if(CrlServiceIpl.TASK_METHODNAME.equals(scheduleJob.getMethodName())) {
 					method.invoke(object,scheduleJob);
	 			}else {
	 				method.invoke(object);
				}
			
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		log.info("任务名称 = ["+scheduleJob.getJobName() + "]----------启动成功");
	}
}
