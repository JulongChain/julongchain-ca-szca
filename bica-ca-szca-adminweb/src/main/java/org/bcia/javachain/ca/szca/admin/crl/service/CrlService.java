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

import org.bcia.javachain.ca.result.Result;
import org.quartz.SchedulerException;

/**
* Description:CRL 接口
* ClassName: CrlService
* Date:2018年4月19日 下午2:26:34
* @author power
* @version 1.0
*/
public interface CrlService {
	
 
	/**
	* Description:
	* @param caid
	* @param cronExpression eg 0 0/1 * * * ?
	* @return
	* Date: 2018年5月16日 上午10:18:03
	* Author:power
	* Version: 1.0
	*/
	public Result  startUpdateCrl(int caid,String cronExpression)throws SchedulerException;
	
	/**
	* Description:
	* @param caid 更新定时更新CRL 的时间，在编辑CA 有设置CRL更新的时候请求
	* @param cronExpression
	* @return
	* Date: 2018年5月16日 上午10:23:20
	* Author:power
	* Version: 1.0
	*/
	public Result  updateCrlCronExpression(int caid,String cronExpression);
	
	/**
	* Description:
	* @param caid 停止 定时更新某个CA的  CRL  移除CRL 定时更新的业务 时，请求
	* @return
	* Date: 2018年5月16日 上午10:24:13
	* Author:power
	* Version: 1.0
	*/
	public Result  stopUpdateCrl(int caid);

}
