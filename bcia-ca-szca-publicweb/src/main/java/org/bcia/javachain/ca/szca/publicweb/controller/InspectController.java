/*
 * **
 *  *
 *  * Copyright © 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 *  * Copyright © 2018  SZCA. All Rights Reserved.
 *  * <p>
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * <p>
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  * <p>
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 *
 */

package org.bcia.javachain.ca.szca.publicweb.controller;

 
import org.bcia.javachain.ca.szca.publicweb.CertAndRequestDumpBean;
import org.bcia.javachain.ca.szca.publicweb.service.InspectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

 

@Controller
public class InspectController {

	Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private InspectService service;

	@RequestMapping("/inspect/inspect")
	public ModelAndView inspect(MultipartFile reqfile) {
		logger.info("inspect for req");
		ModelAndView view = new ModelAndView("/inspect/inspect");

		CertAndRequestDumpBean dump = null;
		try {
			if (reqfile != null && reqfile.getBytes().length > 0) {
				logger.info(new String(reqfile.getBytes()));
				dump = service.inspectReqFile(reqfile.getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		view.addObject("dump", dump);

		return view;

	}

}