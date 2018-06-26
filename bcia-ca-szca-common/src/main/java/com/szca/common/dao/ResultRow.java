
package com.szca.common.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.JsonObject;

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
public class ResultRow extends HashMap<String ,Object>{
	 
//	private Map<Integer ,String> keySort=new HashMap<Integer,String >();
//	
//	public String getKey(int index){
//		Integer key =new Integer(index);
//		if(keySort.containsKey(key))
//			return keySort.get(key);
//		else
//			return null;
//	}
	private static final long serialVersionUID = -7136695478886109862L;
	
	public ResultRow(){
		super();
	}
	
	public static final ResultRow copy(Map<String ,Object> map){
		ResultRow row = new ResultRow();
		if(map==null)
			return null;
		 
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			Object o = map.get(key);
			row.put(key, o);
		}
		return row;
	}
	
	public Integer getInt(String key)throws SQLException{
		Long l = this.getLong(key);
		if(l!=null)
			return l.intValue();
		else
			return 0;
	}
	public Long getLong(String key) throws SQLException{
		if(!this.containsKey(key))
			throw new SQLException(String.format("No such key[%s] in the row.",key));
		Object o = this.get(key);
		if(o==null)
			return 0L;
		
		if(o instanceof java.math.BigDecimal)
			return ((java.math.BigDecimal)o).longValue();
		else if(o instanceof Long)
			return ((Long)o).longValue();
		else if(o instanceof Integer)
			return ((Integer)o).longValue();
		else 
			throw new SQLException(String.format("Datatype[%S] could not convert to long.",o.getClass().getName()));	 				
	}
	public String getString(String key) throws SQLException{
		if(!this.containsKey(key))
			throw new SQLException(String.format("No such key[%s] in the row.",key));
		Object o = this.get(key);
		if(o!=null)
			return o.toString();
		else
			return null;
	}
	public Timestamp getTimestamp(String key) throws SQLException{
		if(!this.containsKey(key))
			throw new SQLException(String.format("No such key[%s] in the row.",key));
		Object o = this.get(key);
		if(o==null)
			return null;
		
		if(o instanceof Timestamp)
			return (Timestamp)o;
		if(o instanceof Long)
			return new Timestamp((Long)o);
		if(o instanceof Integer)
			return new Timestamp((Integer)o);
		if(o instanceof java.sql.Date)
			return new Timestamp( ((java.sql.Date)o).getTime());
		if(o instanceof java.util.Date)
			return new Timestamp( ((java.util.Date)o).getTime());
		
		return null;
	}
	public java.sql.Date getDate(String key) throws SQLException{
		if(!this.containsKey(key))
			throw new SQLException(String.format("No such key[%s] in the row.",key));
		Object o = this.get(key);
		if(o==null)
			return null;
		
		if(o instanceof java.sql.Date)
			return (java.sql.Date)o;
		if(o instanceof Long)
			return new java.sql.Date((Long)o);
		if(o instanceof Integer)
			return new java.sql.Date((Integer)o);
		if(o instanceof Timestamp)
			return new java.sql.Date( ((Timestamp)o).getTime());
		if(o instanceof java.util.Date)
			return new java.sql.Date( ((java.util.Date)o).getTime());
		
		return null;
	}
//	public Date getDate(String key) throws Exception{
//		if(!this.containsKey(key))
//			throw new Exception(String.format("No such key[%s] in the row.",key));
//		Object o = this.get(key);
//		return o.toString(); 		 	 				
//	}
//	public String getHello(){
//		return "Hello--";
//	}
//	public String getHello(String msg){
//		return "Hello,"+msg;
//	}
	public JsonObject toJson(){
		JsonObject json = new JsonObject();
		Iterator<String> keyIt = super.keySet().iterator();
		while(keyIt.hasNext()){
			String key = keyIt.next();
			Object val = super.get(key);
			json.addProperty(key, val==null?"":val.toString());
		}
		return json;
	}
}
