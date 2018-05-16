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

package org.bcia.javachain.ca.szca.ocsp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.bcia.javachain.ca.szca.ocsp.service.OCSPParam;


public class JDBCUtil {
	private static final Logger log = Logger.getLogger(JDBCUtil.class);
	/**定义一个Connection 用来连接数据库*/  
    private static Connection conn=null;   
    /**连接数据库的URL*/  
    //private final static String url="jdbc:mysql://127.0.0.1:3306/ejbca";   
    /**指定数据的用户名和密码*/   
    //private final static String username="root";   
    //private final static String password="123456";   

    
    /**  
     * 建立数据的连接  
     * @throws Exception 
     * @exception SQLException, ClassNotFoundException  
     */  
    @SuppressWarnings("finally")   
    public static Connection getConn() throws Exception{
    	if(conn == null){   
    		try{
	    		Class.forName("com.mysql.jdbc.Driver");   
	    		conn=DriverManager.getConnection(ProUtils.getKeyValue("DB_URL"),ProUtils.getKeyValue("DB_USERNAME"),ProUtils.getKeyValue("DB_PASSWORD"));   
    		}catch (Exception e) {
				throw new Exception("创建数据库连接异常:" + e.getMessage());
			}
        }  
        return conn; 
    }   
    
    /**关闭连接*/  
    public boolean close(Connection conn,PreparedStatement pstmt,ResultSet resultSet){   
        boolean isColes = false;   
        if(resultSet!=null){   
            try {   
                resultSet.close();   
                resultSet=null;   
                isColes=true;   
            } catch (SQLException e) {   
                isColes=false;   
                e.printStackTrace();   
                log.error("关闭结果集发生错误");   
            }   
        }   
        if(pstmt!=null){   
            try {   
                pstmt.close();   
                pstmt=null;   
                isColes=true;   
            } catch (SQLException e) {   
                isColes=false;   
                e.printStackTrace();   
                log.error("关闭pstmt发生异常");   
            }   
        }   
        if(conn!=null){   
            try{   
                conn.close();   
                conn=null;   
                isColes=true;   
            }catch (Exception e) {   
                isColes=false;   
                e.printStackTrace();   
                log.error("关闭conn发生异常");   
            }   
        }   
        return isColes;   
    }   

}
