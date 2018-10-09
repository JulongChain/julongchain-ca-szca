
package com.szca.common.dao;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Project Name : SZCA-COMMON<br>
 * Package Name : cn.szca.common.dao<br>
 * File Name : BaseDao.java<br>
 * Type Name : BaseDao<br>
 * Created on : 2017-2-7 下午6:08:21<br>
 * Created by : JackyLuo <br>
 * Note:<br>
 * 
 * 
 */
 
public abstract class AbstractJdbcDao extends JdbcTemplate {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	//@Autowired
	private DataSource dataSource;
	  
	abstract public DataSource getDataSource() ;

	abstract public void setDataSource(DataSource dataSource);

 
	  
	public  <T> T getObject(Class<T> objType,String sql, Object... args) throws SQLException {
		  List<T> list =  getObjList(objType, sql, args);
		  if(list==null ||  list.isEmpty())
			  return null;
		  else
			  return list.get(0);
		  
	}
	
	
	public  <T> List<T> getObjList(Class<T> objType,String sql, Object... args) throws SQLException {
		return  this.getList(objType, sql, args);
	}
	public  <T> List<T> getList(Class<T> objType,String sql, Object... args) throws SQLException {
		//return null;
	//}
	//public List<T> getList(String sql,Class<T> obj, Object... args) throws SQLException {
		List<T> objList = null;
		List<ResultRow> rows = getRowList( sql,  args) ;
		if(rows==null||rows.isEmpty())
			return null;
		ResultRow row = rows.get(0);
		//=====method and key mapping
		Set<String> keySet = row.keySet();
		Iterator<String> keyIt = keySet.iterator();
		Map<String,String> keyMethodMap = new HashMap<String ,String>();
		Map<String,Class[]> keyMethodParamTypeMap = new HashMap<String ,Class[]>();
		String underLine = "_",minus="-";
		while(keyIt.hasNext()){
			String key = keyIt.next();
			String keyUnderLine=null,keyMinus = null;
//			boolean hasSetter=false,hasGetter=false;
			Method[] methods = objType.getMethods();
			if(key.indexOf(underLine)>=0)
				keyUnderLine = key.replaceAll(underLine, "");
			if(key.indexOf(minus)>=0)
				keyMinus = key.replaceAll(minus, "");
			for(Method m:methods){
				String methodName = m.getName();
				if(!methodName.startsWith("get") && !methodName.startsWith("set"))
					continue;
				
				if(methodName.equalsIgnoreCase("get"+key))
					keyMethodMap.put("GET"+key, methodName);
				if(keyUnderLine!=null && methodName.equalsIgnoreCase("get"+keyUnderLine))
					keyMethodMap.put("GET"+underLine+key, methodName);
				if(keyMinus!=null && methodName.equalsIgnoreCase("get"+keyMinus))
					keyMethodMap.put("GET"+minus+key, methodName);
				
			 
				if(methodName.equalsIgnoreCase("set"+key)){
					keyMethodMap.put("SET"+key, methodName);
					keyMethodParamTypeMap.put("SET"+key, m.getParameterTypes());
				}
				if(keyUnderLine!=null && methodName.equalsIgnoreCase("set"+keyUnderLine)){
					keyMethodMap.put("SET"+underLine+key, methodName);
					keyMethodParamTypeMap.put("SET"+underLine+key, m.getParameterTypes());
				}
				if(keyMinus!=null && methodName.equalsIgnoreCase("set"+keyMinus)){
					keyMethodMap.put("SET"+minus+key, methodName);
					keyMethodParamTypeMap.put("SET"+minus+key, m.getParameterTypes());
				}
					 
			}
		}
		//========ROW to Object
		objList = new ArrayList<T>();
		for(ResultRow dataRow:rows){
			try{
				Object obj = objType.newInstance();
				//dataRow.
				//Set<String> keySet = row.keySet();
				keyIt = keySet.iterator();
				while(keyIt.hasNext()){
					String key = keyIt.next();
					String method = this.getSetterMethodFromMap(keyMethodMap, key);
					if(method==null)
						continue;
					//TODO: call setter
					Object data = dataRow.get(key);
					Class[] paramType = this.getSetterMethodParamTypeFromMap(keyMethodParamTypeMap, key);
					//Method setter = objType.getMethod(method, new Class[] {int.class,String.class});
					Method setter = objType.getMethod(method, paramType);
				      //获得参数Object
				      Object[] setterArgs = new Object[paramType.length];//new Object[]{data};
				      //setter常用参数类型
				      Long objLong = 1l;
				      Integer objInteger = 1;
				      String objStr = "";
				      Date objDate = new Date(0);
				      Timestamp objStamp = new Timestamp(0);
				      for(int i=0;i<setterArgs.length;i++){
				    	  Class pt = paramType[i];
				    	  if(pt.isInstance(data))
				    		  setterArgs[i] = data;
				    	  else{
				    		  if(pt.isInstance(objLong)){
				    			  //TODO: transfer data into long
				    			 // try{
				    				  setterArgs[i] = dataRow.getLong(key);   
				    			 // }catch(Exception e){
				    			//	  e.printStackTrace();
				    			//	  logger.error(e.getMessage());
				    			 // }				    			  
				    		  }else if(pt.isInstance(objInteger)){
				    			  //TODO: transfer data into Integer
				    			  setterArgs[i] = dataRow.getInt(key);
				    		  }else if(pt.isInstance(objStr)){
				    			  //TODO: transfer data into String 
				    			  setterArgs[i] = dataRow.getString(key);
				    		  }else if(pt.isInstance(objDate)){
				    			  //TODO: transfer data into Date 
				    			  setterArgs[i] = (Date)dataRow.get(key);
				    		  }else if(pt.isInstance(objStamp)){
				    			  //TODO: transfer data into Timestamp 
				    			  setterArgs[i] = dataRow.getTimestamp(key);
				    		  }else if(pt.isInstance(new java.sql.Date(0))){
				    			  //TODO: transfer data into java.sql.Date 
				    			  setterArgs[i] = dataRow.getDate(key);
				    		  }else
				    			  setterArgs[i] = null;
				    	  }
				      }				      
				      //执行方法
				      setter.invoke(obj, setterArgs);
				      
				}
				objList.add((T)obj);
			}catch(Exception e){
				String msg = String.format("Create instance for class[%s] failed:%s",objType,e.getMessage());
				logger.error(msg);
				e.printStackTrace();
				throw new SQLException(msg);
			}
			
			
		}
		
		return objList;
	}
	
	private String getSetterMethodFromMap(final Map<String,String> keyMethodMap,String key){
		if(keyMethodMap==null)
			return null;
		String mname = null;
		if(keyMethodMap.containsKey("SET"+key))
			mname = keyMethodMap.get("SET"+key);
		if(mname==null && keyMethodMap.containsKey("SET_"+key))
			mname = keyMethodMap.get("SET_"+key);
		if(mname==null && keyMethodMap.containsKey("SET-"+key))
			mname = keyMethodMap.get("SET-"+key);
		return mname;
	}
	private Class[] getSetterMethodParamTypeFromMap(final Map<String,Class[]> keyMethodMap,String key){
		if(keyMethodMap==null)
			return null;
		Class[] paramType  = null;
		if(keyMethodMap.containsKey("SET"+key))
			paramType = keyMethodMap.get("SET"+key);
		if(paramType==null && keyMethodMap.containsKey("SET_"+key))
			paramType = keyMethodMap.get("SET_"+key);
		if(paramType==null && keyMethodMap.containsKey("SET-"+key))
			paramType = keyMethodMap.get("SET-"+key);
		return paramType;
	}
	
	public List<ResultRow> getRowList(String sql, Object... args) throws SQLException {
		List<ResultRow> rowList = null;
		try {
			List<Map<String, Object>> list = null;
			if(args==null || args.length==0)
				list = super.queryForList(sql);
			else
				list = super.queryForList(sql, args);
			
			if (!list.isEmpty()) {
				rowList = new ArrayList<ResultRow>();
				Iterator<Map<String, Object>> it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> map = it.next();
					ResultRow row = ResultRow.copy(map);
					rowList.add(row);
				}

			}
		} catch (BadSqlGrammarException sqle) {
			throw new SQLException(sqle);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("");
		}
		return rowList;
	}

	public ResultRow getSingleRow(String sql, Object... args) throws SQLException {
		ResultRow row = null;
		try {
			List<Map<String, Object>> list = null;
			if(args==null || args.length==0)
				list = super.queryForList(sql);
			else
				list = super.queryForList(sql, args);
			if (!list.isEmpty()) {
				Map<String, Object> map = list.get(0);
				row = ResultRow.copy(map);
			}
		} catch (BadSqlGrammarException sqle) {
			throw new SQLException(sqle);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("");
		}
		return row;
	}

	public long getSingleLong(String sql, Object... args) throws SQLException {
		Object o = this.getSingleObject(sql,  args);
		if (o == null)
			return 0;
		if (o instanceof java.math.BigDecimal)
			return ((java.math.BigDecimal) o).longValue();
		else if (o instanceof Long)
			return ((Long) o).longValue();
		else if (o instanceof Integer)
			return ((Integer) o).longValue();
		else
			throw new SQLException(String.format("Datatype[%S] could not convert to long", o.getClass().getName()));

	}
	public int getSingleInt(String sql, Object... args) throws SQLException {
		Object o = this.getSingleObject(sql,  args);
		if (o == null)
			return 0;
		if (o instanceof java.math.BigDecimal)
			return ((java.math.BigDecimal) o).intValue();
		else if (o instanceof Long)
			return ((Long) o).intValue();
		else if (o instanceof Integer)
			return ((Integer) o).intValue();
		else
			throw new SQLException(String.format("Datatype[%S] could not convert to int", o.getClass().getName()));

	}

	public String getSingleStr(String sql,   Object... args) throws SQLException {
		Object o = this.getSingleObject(sql,  args);
		if (o == null)
			return null;
		if (o instanceof java.math.BigDecimal)
			return Long.toString(((java.math.BigDecimal) o).longValue());
		else if (o instanceof Long)
			return Long.toString(((Long) o).longValue());
		else if (o instanceof Integer)
			return Integer.toString(((Integer) o).intValue());
		else if (o instanceof String)
			return  (String) o;
		else
			return o.toString();
			//throw new SQLException(String.format("Datatype[%S] could not convert to int", o.getClass().getName()));

	}
	public Object getSingleObject(String sql,   Object... args) throws SQLException {
		Object obj = null;
		try {

			Map<String, Object> map = this.getSingleRow(sql, args);
			/*if (map != null && (key == null || "".equalsIgnoreCase(key.trim()))) {
				Iterator<String> it = map.keySet().iterator();
				if (it.hasNext())
					key = it.next();
			}

			if (map != null && map.containsKey(key))
				obj = map.get(key);
*/			
			if(map!=null){
				Iterator it=  map.values().iterator();
				if(it.hasNext())
					obj =  it.next();
			}
			
		} catch (BadSqlGrammarException sqle) {
			throw new SQLException(sqle);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("");
		}
		return obj;
	}
}
