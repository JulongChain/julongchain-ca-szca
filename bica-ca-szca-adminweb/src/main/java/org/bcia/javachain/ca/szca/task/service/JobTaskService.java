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

import java.util.List;

import org.bcia.javachain.ca.szca.task.entity.ScheduleJob;
import org.quartz.SchedulerException;


 
 
/**
* Description:
* ClassName: JobTaskService
* Date:2018年4月18日 下午3:14:02
* @author power
* @version 1.0
*/
public interface JobTaskService {
 
    /**
    * Description:获取索引任务
    * @return
    * Date: 2018年4月18日 下午3:15:42
    * Author:power
    * Version: 1.0
    */
    public List<ScheduleJob> getAllTask();
    /**
    * Description:加入任务
    * @param job
    * Date: 2018年4月18日 下午3:15:50
    * Author:power
    * Version: 1.0
    */
    public void addTask(ScheduleJob job) throws SchedulerException ;
    /**
    * Description:
    * @param jobId
    * @return
    * Date: 2018年4月18日 下午3:16:15
    * Author:power
    * Version: 1.0
    */
    public ScheduleJob getTaskById(Long jobId) ;
    /**
    * Description:
    * @param jobId
    * @param cmd
    * @throws SchedulerException
    * Date: 2018年4月18日 下午3:16:18
    * Author:power
    * Version: 1.0
    */
    public void changeStatus(Long jobId, String cmd) throws SchedulerException;
    /**
    * Description:修改任务执行周期时间
    * @param jobId
    * @param cron 格式：eg 0/5 * * * * ? 
    * @throws SchedulerException
    * Date: 2018年4月18日 下午3:16:30
    * Author:power
    * Version: 1.0
    */
    public void updateCron(Long jobId, String cron) throws SchedulerException;
}
