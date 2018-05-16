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

package org.bcia.javachain.ca.szca.ocsp.service;

import org.apache.log4j.Logger;
import org.bouncycastle.cert.ocsp.OCSPResp;
import org.bouncycastle.cert.ocsp.OCSPRespBuilder;

/**
 * OCSP响应异常情况
 * @author zhenght
 *
 */
public class OCSPParam {
	private static final Logger log = Logger.getLogger(OCSPParam.class);
	public static final int MALFORMED_REQUEST=0;
	public static final int SIGREQUIRED=1;
	public static final int UNAUTHORIZED=2;
	public static final int TRY_LATER=3;
	public static final int INTERNAL_ERROR=4;
	public static final int CERT_STATUS_REVOKED = 40;
	private static byte [][] badresponses=new byte[5][];
	
	//OCSP异常返回
	public static byte [] getBadResponse(int status){
		try{
			OCSPRespBuilder rg=new OCSPRespBuilder();
			badresponses[OCSPParam.MALFORMED_REQUEST]=rg.build(OCSPResp.MALFORMED_REQUEST,null).getEncoded();
			badresponses[OCSPParam.SIGREQUIRED]=rg.build(OCSPResp.SIG_REQUIRED,null).getEncoded();
			badresponses[OCSPParam.UNAUTHORIZED]=rg.build(OCSPResp.UNAUTHORIZED,null).getEncoded();
			badresponses[OCSPParam.TRY_LATER]=rg.build(OCSPResp.TRY_LATER,null).getEncoded();
			badresponses[OCSPParam.INTERNAL_ERROR]=rg.build(OCSPResp.INTERNAL_ERROR,null).getEncoded();
			return badresponses[status];
		}catch (Exception e) {
			log.info("生成返回信息异常：" + e.getMessage());
			return null;
		}
	}
}
