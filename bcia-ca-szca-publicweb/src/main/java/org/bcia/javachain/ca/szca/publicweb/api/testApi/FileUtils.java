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

package org.bcia.javachain.ca.szca.publicweb.api.testApi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.LogFactory;

import sun.rmi.runtime.Log;

/**
 * 文件工具�?
 * @author zhenght
 *
 */
public class FileUtils {
	

	
	private static int BUFFER_SIZE = 16 * 1024;
	
	
	/**
	 * 拷贝文件
	 * @param src 源文�?
	 * @param target 目标文件
	 * @return 是否拷贝成功
	 * @throws Exception 
	 * @throws SystemRunException
	 */
	public static boolean copyFile(File src, File target) throws Exception {
		if (src == null || !src.exists() || src.length() == 0 || !src.canRead()){
			return false;
		}
		if (target == null){
			return false;
		}
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		try {
			if (!target.exists()){
				if (!target.createNewFile()){
					return false;
				}
			}
			if (!target.canWrite()){
				return false;
			}
			bis = new BufferedInputStream(new FileInputStream(src));
			bos = new BufferedOutputStream(new FileOutputStream(target));
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = -1;
			while((len = bis.read(buffer)) != -1){
				bos.write(buffer, 0, len);
			}
		} catch (IOException e) {
			throw new Exception("拷贝文件出错");
		}finally{
			try {
				if (bis != null){
					bis.close();
				}
				if (bos != null){
					bos.close();
				}
			} catch (IOException e) {
			}
		}
		return target.exists();
	}
	
	/**
	 * 拷贝文件
	 * @param inFile 源文�?
	 * @param outFilePath 输出文件路径
	 * @param outFileName 输出文件名称
	 * @return
	 * @throws Exception
	 */
	public static boolean copyFile(File inFile,String outFilePath,String outFileName) throws Exception{
		File file = newFile(outFilePath, outFileName);   
		
		return copyFile(inFile, file);
	}
	
	/**
	 * 拷贝文件 
	 * @param inFilePath 输入文件路径
	 * @param inFileName 输入文件各称
	 * @param outFilePath 输出文件路径
	 * @param outFileName 输出文件名称
	 * @return
	 * @throws Exception
	 */
	public static boolean copyFile(String inFilePath,String inFileName,String outFilePath,String outFileName) throws Exception{
		File file = newFile(outFilePath, outFileName);   
		
		return copyFile(new File(inFilePath + inFileName), file);
	}
	
	/**
	 * 把文件内容全部读取为byte[]，文件内容最好不要太�?
	 * @param file
	 * @param deleteAfterRead 是否删除原文�?
	 * @return
	 * @throws Exception
	 */
	public static byte[] readFile(File file, boolean deleteAfterRead) throws Exception{

		FileInputStream fis = null;
		byte[] bFile = null;
		try {
			fis = new FileInputStream(file);
			bFile = new byte[fis.available()];
			fis.read(bFile);
		} catch (Exception e) {
			throw new Exception("读取文件错误");
		} finally{
			if (fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					
				}
				fis = null;
				if (deleteAfterRead){
					file.delete();
				}
			}
		}
		return bFile;
	}

	/**
	 * 把文件内容全部读取为byte[]，读取完后不删除原文件�?�文件内容最好不要太�?
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static byte[] readFile(File file) throws Exception{
		return readFile(file, false);
	}
	
	public static String[] readAsStringArray(File file) throws Exception{
		List<String> ltArr = new ArrayList<String>();
		if (file == null || !file.exists()){
			return new String[]{};
		}

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			while(br.ready()){
				ltArr.add(br.readLine());
			}
		} catch (Exception e) {
			throw new Exception("读取文件错误");
		}finally{
			if (br != null){
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
		return ltArr.toArray(new String[]{});
	}

	/**
	 * 把byte[]文件流，指定路径和文件名保存
	 * @param bfile �?要保存的文件�?
	 * @param filePath 保存路径
	 * @param fileName 保存文件�?
	 * @throws IOException 
	 */
	public static void saveFile(byte[] bfile, String filePath,String fileName) throws IOException {   
        BufferedOutputStream bos = null;   
        FileOutputStream fos = null;   
        try {   
        	File file = newFile(filePath, fileName);   
              
            fos = new FileOutputStream(file);   
            bos = new BufferedOutputStream(fos);   
            bos.write(bfile);   
        } catch (IOException e) { 
            throw e;   
        } finally {   
            if (bos != null) {   
                try {   
                    bos.close();   
                } catch (IOException e) {   
                }   
            }   
            if (fos != null) {   
                try {   
                    fos.close();   
                } catch (IOException e) {   
                }   
            }   
        }   
    }  
	public static void saveFile(InputStream inStream, String filePath,String fileName) throws IOException {   
		try {
			File file = newFile(filePath, fileName);   
            
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			inStream.close();
		} catch (IOException e) {
			
		}
    }  
	private static File newFile(String filePath,String fileName){
		File file = null;   
		File dir = new File(filePath);   
        if(!(dir.exists()&&dir.isDirectory())){//判断文件目录是否存在   
            dir.mkdirs();   
        }   
        if(filePath.lastIndexOf("/") == filePath.length()-1 || filePath.lastIndexOf("\\") == filePath.length()-1){
        	file = new File(filePath+fileName); 
        }else{
        	file = new File(filePath+"/"+fileName); 
        }
        return file;
	}
	

	 /** 
	   * 传入文件名以及字符串, 将字符串信息保存到文件中 
	   *  
	   * @param strFilename 
	   * @param strBuffer 
	   */  
	  public static void TextToFile(final String strFilename, final String strBuffer)  
	  {  
	    try  
	    {      
	      // 创建文件对象  
	      File fileText = new File(strFilename);  
	      // 向文件写入对象写入信�?  
	      FileWriter fileWriter = new FileWriter(fileText);  
	  
	      // 写文�?        
	      fileWriter.write(strBuffer);  
	      // 关闭  
	      fileWriter.close();  
	    }  
	    catch (IOException e)  
	    {  
	      //  
	      e.printStackTrace();  
	    }  
	  }  
	 
	
}
