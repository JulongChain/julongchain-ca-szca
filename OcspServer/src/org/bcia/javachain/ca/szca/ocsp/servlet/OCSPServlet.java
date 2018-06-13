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

package org.bcia.javachain.ca.szca.ocsp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.bcia.javachain.ca.szca.ocsp.service.OCSPParam;
import org.bcia.javachain.ca.szca.ocsp.service.OCSPService;
import org.bcia.javachain.ca.szca.ocsp.utils.ProUtils;
import org.bouncycastle.util.encoders.Base64;

public class OCSPServlet extends HttpServlet{
	private static final Logger log = Logger.getLogger(OCSPServlet.class);

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		byte[] enc = null;
		try{
			StringBuffer url = req.getRequestURL();
			if (url.length() <= Integer.parseInt(ProUtils.getKeyValue("maxRequestSize"))) {
	            String decodedRequest = parseHttpGetParam(url, req);
	            byte[] ret = Base64.decode(decodedRequest.getBytes());
	            enc = OCSPService.checkCertStatus(ret, resp);
	            writeData(enc, resp);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("查询证书状态异常:" +e.getMessage());
			enc = OCSPParam.getBadResponse(OCSPParam.INTERNAL_ERROR);
			writeData(enc, resp);
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		byte[] enc = null;
		try{
			if (req.getContentType()==null||!req.getContentType().equals("application/ocsp-request")) {
				log.error("非OCSP查询请求!");
				resp.setContentType("text/html");
				PrintWriter out=resp.getWriter();
				out.println("非法OCSP查询请求!");
				return;
			}
			
			int len=req.getContentLength();
			if (len < 1 || len > Integer.parseInt(ProUtils.getKeyValue("maxRequestSize"))) {
				log.error("请求长度异常!");
				enc = OCSPParam.getBadResponse(OCSPParam.MALFORMED_REQUEST);
				writeData(enc, resp);
				return;
			}
			
			ServletInputStream reader = req.getInputStream();
			byte [] ocspreqdata = new byte [len];
			int offset = 0;
			int bytes_read;
			while ((bytes_read = reader.read(ocspreqdata,offset,len-offset)) != -1) {
				offset += bytes_read;
				if(offset == len)
					break;
			}
			
			enc = OCSPService.checkCertStatus(ocspreqdata, resp);
			writeData(enc, resp);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("查询证书状态异常:" +e.getMessage());
			enc = OCSPParam.getBadResponse(OCSPParam.INTERNAL_ERROR);
			writeData(enc, resp);
		}
	}
	
	//解析HTTP Get请求参数
	private String parseHttpGetParam(StringBuffer url, HttpServletRequest req) throws UnsupportedEncodingException{
		String fullServletpath = req.getContextPath() + req.getServletPath();
        int paramIx = Math.max(url.indexOf(fullServletpath), 0) + fullServletpath.length() + 1;
        String requestString = paramIx < url.length() ? url.substring(paramIx) : "";
        return URLDecoder.decode(requestString, "UTF-8").replaceAll(" ", "+");
	}

	//OCSP响应返回给客户端
	private void writeData(byte[] respdata,HttpServletResponse resp) throws IOException {
		resp.setContentType("application/ocsp-response");
		resp.setContentLength(respdata.length);
		ServletOutputStream so=resp.getOutputStream();
		so.write(respdata);
	}
	
}
