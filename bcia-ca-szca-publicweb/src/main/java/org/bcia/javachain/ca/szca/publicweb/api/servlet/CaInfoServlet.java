package org.bcia.javachain.ca.szca.publicweb.api.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.BorderUIResource.EmptyBorderUIResource;

import org.apache.commons.lang.StringUtils;
import org.bcia.javachain.ca.szca.publicweb.api.ApiCaInfo;
import org.bcia.javachain.ca.szca.publicweb.api.ApiError;
import org.bcia.javachain.ca.szca.publicweb.api.ApiMessage;
import org.bcia.javachain.ca.szca.publicweb.api.ApiResult;
import org.bcia.javachain.ca.szca.publicweb.api.service.ISzcaApiService;
import org.cesecore.audit.impl.integrityprotected.AuditRecordData;
import org.cesecore.certificates.ca.CAData;
import org.cesecore.util.Base64;
import org.cesecore.util.CertTools;
import org.cesecore.util.StringTools;
import org.ejbca.core.ejb.ca.sign.SignSessionLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

 
import cn.net.bcia.cesecore.certificates.ca.CaSessionBean;
import cn.net.bcia.cesecore.certificates.ca.CaSessionLocal;

public class CaInfoServlet extends BaseApiServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(CaInfoServlet.class);

	

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		log.info("==init CaInfoServlet for getcacert ==");

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		doGet(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException {
		try {
			 //CaSessionLocal bean = this.getBean(req, CaSessionLocal.class);
		//ISzcaApiService   bean = this.getBean(req, ISzcaApiService.class);
		}catch(Exception e) {
			e.printStackTrace();
		}
		 
		// List<Integer> ids = bean.getAllCaIds();
		// res.getWriter().println(ids);
		String caName, format;
		JsonObject json = this.getPostParam(req);
		if(json.get("caname")!=null)
			caName =  json.get("caname").getAsString();
		else
			caName = req.getParameter("caname");
		
		if(json.get("format")!=null)
			format =  json.get("format").getAsString();
		else
			format = req.getParameter("format");
		
		ApiResult apiResult = new ApiResult();
		ApiCaInfo  apiCaInfo = new ApiCaInfo();
		apiCaInfo.setCaname(caName);
		ApiMessage apiMessage = new ApiMessage();
		ApiError apiError = new ApiError(); 
		try {
			ISzcaApiService   bean = this.getBean(req, ISzcaApiService.class);
			apiCaInfo.setCachain(bean.getCertificateChain4Base64(caName, format));		
			apiResult.setSuccess(true);
			
		}catch(Exception e) {
			e.printStackTrace();
			apiResult.setSuccess(false);
			apiError.setCode(-1);
			apiError.setMessage( e.getMessage());
		}
		apiResult.setResult(apiCaInfo.toJsonObject());
		apiResult.addMessage(apiMessage.toJsonObject());
		apiResult.addError(apiError.toJsonObject());
		
		byte[] ret = apiResult.toString().getBytes();
		res.setContentType("application/json");
		res.setContentLength(ret.length );
		res.getOutputStream().write(ret);
		log.debug("Sent CA certificate chain to client, len=" +ret.length + ".");

	}

	private JsonObject getPostParam(HttpServletRequest req) {
		JsonObject obj = new JsonObject();
		try {
			// byte[] buff = new byte[1024];
			// InputStream is = req.getInputStream();
			// is.read(buff);
			// InputStreamReader is
			StringBuffer buff = new StringBuffer();
			BufferedReader read = new BufferedReader(new InputStreamReader(req.getInputStream()));
			String str = null;
			while ((str = read.readLine()) != null) {
				buff.append(str);
			}
			obj = new JsonParser().parse(buff.toString().trim()).getAsJsonObject();
		} catch (Exception e) {
			log.warn("无法获取POST参数。");
		}
		return obj;

	}

	
}
