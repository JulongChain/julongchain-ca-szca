
package com.szca.common.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * Project Name : SZCA-COMMON<br>
 * Package Name : cn.szca.common.dao<br>
 * File Name    : ResultRow.java<br>
 * Type Name    : ResultRow<br>
 * Created on   : 2017-2-10 上午9:49:27<br>
 * Created by   : JackyLuo <br>
 * Note:<br>
 *
 * 
 */
public class ResultMap extends HashMap<String ,Object>{
	 
	 
	private static final long serialVersionUID = -7136695478886109862L;
	
	 
	
	public ResultMap(){
		super();
	}

	public void addRowList(String key,List<ResultRow> list){
		put(key, list);
	}
	
	public List<ResultRow> getRowList(String key){
		if(containsKey(key) && get(key) instanceof List) 			
			return (List<ResultRow>) get(key);		 
		else
			return null;
	}
	
	public void addRow(String key,ResultRow row){
		put(key, row);
	}
	
	public ResultRow getRow(String key){
		if(containsKey(key) && get(key) instanceof ResultRow) 			
			return (ResultRow) get(key);		 
		else
			return null;
	}
	
//	public void addProp(String key,Object value){
//		put(  key,  value);
//	}
//	
//	public Object getProp(String key){
//		if(containsKey(key)  ) 			
//			return   get(key);
//		else
//			return null;
//	}
}
