/*
 * Copyright ? 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 * Copyright ? 2018  SZCA. All Rights Reserved.
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
 */
package org.bcia.javachain.ca.szca.publicweb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bcia.javachain.ca.szca.common.bcca.common.BaseCodeData;
import org.bcia.javachain.ca.szca.common.bcca.common.BaseCodeService;

/**
 * 系统初始化
 * @author Jacky Luo
 *
 */
@Service
public class InitGlobalConfig {
	private Logger logger = LoggerFactory.getLogger(InitGlobalConfig.class);
	
	@Autowired
	BaseCodeService service;
	
	@PostConstruct
	public void initSysConfig() {
		logger.info("====InitGlobalConfig, initializing system global config...... ");
		BaseCodeData bc = service.getBasecode("URL", "ADMIN_URL");
		if(bc!=null)
			PublicWebConstants.ADMIN_URL =bc.getValue(); 
	}
	
	@PreDestroy
	public void destroySysConfig() {
		logger.info("====InitGlobalConfig, destroy system global config...... ");
	}
}
