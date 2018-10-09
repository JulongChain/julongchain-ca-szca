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

import org.bcia.javachain.ca.szca.task.entity.ScheduleJob;

/**
* Description:定时任务持久接口
* ClassName: ScheduleJobDao
* Date:2018年4月18日 下午3:09:42
* @author power
* @version 1.0
*/
public interface ScheduleJobDao {

	/**
	* Description: 删除一个任务 TODO 支持后续在前端界面
	* @param job_id
	* @return
	* Date: 2018年4月18日 下午3:10:57
	* Author:power
	* Version: 1.0
	*/
	boolean deleteByPrimaryKey(Long job_id);

	/**
	* Description: 新增一个任务
	* @param record
	* @return
	* Date: 2018年4月18日 下午3:11:03
	* Author:power
	* Version: 1.0
	*/
	long insert(ScheduleJob record);

    /**
    * Description: 查询实体
    * @param job_id
    * @return ScheduleJob
    * Date: 2018年4月18日 下午3:11:06
    * Author:power
    * Version: 1.0
    */
    ScheduleJob selectByPrimaryKey(Long job_id);

    /**
    * Description: 更新
    * @param record
    * @return
    * Date: 2018年4月18日 下午3:11:11
    * Author:power
    * Version: 1.0
    */
    boolean updateByPrimaryKeySelective(ScheduleJob record);
     
    /**
    * Description: 获取所有任务，用于初始加载启动任务
    * @return
    * Date: 2018年4月18日 下午3:11:14
    * Author:power
    * Version: 1.0
    */
    List<ScheduleJob> getAll();

}
