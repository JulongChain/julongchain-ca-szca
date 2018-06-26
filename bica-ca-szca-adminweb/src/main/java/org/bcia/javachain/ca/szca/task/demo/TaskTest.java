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

package org.bcia.javachain.ca.szca.task.demo;

import org.apache.log4j.Logger;
import org.bcia.javachain.ca.szca.task.dao.ScheduleJobDao;
import org.bcia.javachain.ca.szca.util.DatetimeHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* Description: 任务类示例，  如果注解@Component 可以写 SPRING的BEAN ID，  例如这里可以写：springBeanid=taskTest  （注：注解默认是首字母小写）
* ClassName: TaskTest
* Date:2018年4月18日 下午3:57:28
* @author power
* @version 1.0
*/
@Component
public class TaskTest {
	public final Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private ScheduleJobDao scheduleJobDao;  //有依赖注入 建议配置 springBeanid字段  使用包名+类+方法的话 可以手动获取 所需的BEAN
	
	public void run() {
		System.out.println("########## run...." + DatetimeHandle.formatCurrentDate()+"##"+scheduleJobDao);
         
	}

	public void run1() {
 		System.out.println("########## run1.." +DatetimeHandle.formatCurrentDate()+"##");

	}

}
 