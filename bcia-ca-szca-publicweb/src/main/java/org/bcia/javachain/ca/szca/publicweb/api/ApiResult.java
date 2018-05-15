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

package org.bcia.javachain.ca.szca.publicweb.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ApiResult {
	private com.google.gson.JsonObject retObj = new JsonObject();
	private com.google.gson.JsonObject result = new JsonObject();
	private com.google.gson.JsonArray errors = new JsonArray();
	private com.google.gson.JsonArray messages = new JsonArray();

	private boolean success = false;
	private String secret = null;

	public String toString() {
		if(secret!=null && !"".equals(secret.trim())) {
			this.result.addProperty("secret", secret);
			this.result.addProperty("credentials", secret);
		}
		
		retObj.addProperty("success", success);
		// retObj.addProperty("CAName", caname);
		retObj.add("result", result);
		retObj.add("errors", errors);
		retObj.add("messages", messages);
		return retObj.toString();
	}

	public void addError(JsonObject errObj) {
		errors.add(errObj);
	}

	public void addMessage(JsonObject msgObj) {
		messages.add(msgObj);
	}

	public void setResult(JsonObject resultObj) {
		this.result = resultObj;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setSecret(String secret) {
		// messages.add(msgObj);
		this.secret = secret;
	}
	// public String getCaname() {
	// return caname;
	// }
	//
	// public void setCaname(String caname) {
	// this.caname = caname;
	// }
}
