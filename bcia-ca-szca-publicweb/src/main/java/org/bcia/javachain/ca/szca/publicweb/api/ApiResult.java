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
