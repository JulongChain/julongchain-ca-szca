
package com.szca.common;


import com.google.gson.JsonObject;

/** 
 * Project Name : SZCA-COMMON<br>
 * Package Name : cn.szca.common<br>
 * File Name    : AjaxActionResult.java<br>
 * Type Name    : AjaxActionResult<br>
 * Created on   : 2017-2-8 下午5:03:54<br>
 * Created by   : JackyLuo <br>
 * Note:<br>
 *
 * 
 */
public class AjaxActionResult extends ActionResult {
	
	public final static String KEY_SUCCESS = "success";
	public final static String KEY_MESSAGE = "message";
	public final static String KEY_RESULT_CODE = "resultCode";
	public final static String KEY_EXCEPTION = "exception";

	public String getJSONString() {
		JsonObject jo = new JsonObject();
		//JSONObject jo = new JSONObject();
		try {
			jo.addProperty(KEY_SUCCESS, super.success);
			jo.addProperty(KEY_MESSAGE, super.message);
			jo.addProperty(KEY_RESULT_CODE, super.resultCode);
			if(super.exception!=null)
				jo.addProperty(KEY_EXCEPTION, super.exception.toString());
			else
				jo.addProperty(KEY_EXCEPTION, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String r = jo.toString();
		logger.debug(r);
		return r;
	}

	

}
