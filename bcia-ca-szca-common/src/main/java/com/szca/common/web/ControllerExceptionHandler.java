package com.szca.common.web;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.handler.HandlerExceptionResolverComposite;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.util.WebUtils;

import com.szca.common.exception.JspViewNotFoundException;

 

/**
 * Project Name : SZCA-COMMON<br>
 * Package Name : cn.szca.common.exception<br>
 * File Name : ExceptionHandler.java<br>
 * Type Name : ExceptionHandler<br>
 * Created on : 2017-2-9 上午9:43:19<br>
 * Created by : JackyLuo <br>
 * Note:<br>
 * 
 * 
 */
@Repository
public class ControllerExceptionHandler extends HandlerExceptionResolverComposite {
	Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	/**
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @return<br>
	 * @see HandlerExceptionResolverComposite#resolveException(HttpServletRequest,
	 *      HttpServletResponse, Object,
	 *      Exception)<br>
	 *      Note:<br>
	 *      全局异常处理,可以捕获所有控制器抛出的异常，并将它映射到配置的视图中,在Controller中发生了异常，都会由此方法处理
	 */
	@Override
	@org.springframework.web.bind.annotation.ExceptionHandler
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		if(ex!=null){
			logger.warn("resolveException:"+ ex.getMessage());
			ex.printStackTrace();
		}
		logger.error(String.format("Handler:[%s], Exception:[%s],Message:[%s]", handler.getClass().getName(), ex.getClass(), ex.getMessage()));
		ex.printStackTrace();

		// TODO: 根据不同异常类型，跳转到不同页面
		ModelAndView modelAndView = super.resolveException(request, response, handler, ex);

		int statusCode = 500;

		if (ex instanceof NoSuchRequestHandlingMethodException) {
			statusCode = HttpServletResponse.SC_NOT_FOUND;
			// 与DefaultControllers的配合，一定要设置为@RequestMapping(value =
			// "/*"),先把无法精确匹配的请求引导到此Controller，避免出现No mapping found for HTTP
			// request with URI [xxx]的情况 ，
			// 当发生下面No mapping found for HTTP request with URI [xxxx] in
			// DispatcherServlet with name
			// 'springMVC'的情况时，先判断是否有对应的JSP文件，如果有，直接打开此jsp，否则再报错.
			// [WARN ][2017-02-09
			// 11:53:29,470][org.springframework.web.servlet.DispatcherServlet->DispatcherServlet.java:1161]:
			// No mapping found for HTTP request with URI [/test/login.html] in
			// DispatcherServlet with name 'springMVC'

			// DefaultControllers只能拦截/*.html,对于/*/*/.../*.html,则会抛出NoSuchRequestHandlingMethodException异常
			try {
				String viewName = ControllerExceptionHandler.getView(request);
				if (modelAndView == null)
					modelAndView = new ModelAndView();
				if (ControllerExceptionHandler.checkJSP(request))
					modelAndView.setViewName(viewName);
				else
					modelAndView.setViewName("error");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (ex instanceof HttpRequestMethodNotSupportedException) {
			statusCode = HttpServletResponse.SC_METHOD_NOT_ALLOWED;
		} else if (ex instanceof HttpMediaTypeNotSupportedException) {
			statusCode = HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
		} else if (ex instanceof HttpMediaTypeNotAcceptableException) {
			statusCode = HttpServletResponse.SC_NOT_ACCEPTABLE;
		} else if (ex instanceof MissingServletRequestParameterException) {
			statusCode = HttpServletResponse.SC_BAD_REQUEST;
		} else if (ex instanceof ServletRequestBindingException) {
			statusCode = HttpServletResponse.SC_BAD_REQUEST;
		} else if (ex instanceof ConversionNotSupportedException) {
			statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		} else if (ex instanceof TypeMismatchException) {
			statusCode = HttpServletResponse.SC_BAD_REQUEST;
		} else if (ex instanceof HttpMessageNotReadableException) {
			statusCode = HttpServletResponse.SC_BAD_REQUEST;
		} else if (ex instanceof HttpMessageNotWritableException) {
			statusCode = HttpServletResponse.SC_BAD_REQUEST;
		} else if (ex instanceof MethodArgumentNotValidException) {
			statusCode = HttpServletResponse.SC_BAD_REQUEST;
		} else if (ex instanceof MissingServletRequestPartException) {
			statusCode = HttpServletResponse.SC_BAD_REQUEST;
		} else if (ex instanceof BindException) {
			statusCode = HttpServletResponse.SC_BAD_REQUEST;
		} else if (ex instanceof NoHandlerFoundException) {
			statusCode = HttpServletResponse.SC_NOT_FOUND;
		} else if (ex instanceof JspViewNotFoundException) {
	 
			statusCode = HttpServletResponse.SC_NOT_FOUND;
		} else{
			statusCode = 999;
		}

		// String url = WebUtils.getPathWithinApplication(request);
		// String url = "test.html";
		// logger.error("controller error.url=" + url,statusCode, ex);
		if (modelAndView == null) {
			modelAndView = new ModelAndView("error");
			modelAndView.addObject("error.");
		}
		StackTraceElement[] ste = ex.getStackTrace();	
		StringBuffer buff = new StringBuffer();
		for(StackTraceElement s:ste){
			buff.append(s.toString());
			buff.append("\r\n");
		}
		String backUrl = "javascript:history.back()";
//		if(ex instanceof AuthLoginException)
//			backUrl =request.getContextPath()+ "/login.html";
		
		modelAndView.addObject("backUrl", backUrl);
		modelAndView.addObject("ste", buff.toString());
		modelAndView.addObject("exception", ex.getClass());
		modelAndView.addObject("errMsg", ex.getMessage());
		return modelAndView;
	}

	public static boolean checkJSP(HttpServletRequest request) throws Exception {
		 
		String root = WebUtils.getRealPath(request.getSession().getServletContext(), "/");
		String jspName = getView(request);// uri.replaceFirst(path, "");
		String jspFullName = root + "/WEB-INF/views" + jspName + ".jsp";
		File jspFile = new File(jspFullName);
		return jspFile.exists();

	}

	public static String getView(HttpServletRequest request) throws Exception {
		 
		String uri = request.getRequestURI();
		 
		String path = request.getContextPath();
		 
		String jspName = uri.replaceFirst(path, "");

		String viewName = jspName;
		if (viewName.endsWith(".html"))
			viewName = viewName.substring(0, viewName.length() - 5);

		return viewName;

	}
}
