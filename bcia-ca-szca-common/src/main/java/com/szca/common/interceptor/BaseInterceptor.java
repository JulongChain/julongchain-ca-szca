package com.szca.common.interceptor;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.szca.common.LoginUser;

public abstract class BaseInterceptor extends HandlerInterceptorAdapter {

	protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	protected LoginUser getLoginUser(HttpServletRequest request){
		//String url = request.getRequestURL().toString();
		HttpSession session = request.getSession();
		Object loginObj = session.getAttribute(LoginUser.LOGIN_USER);
		if(loginObj!=null && loginObj instanceof LoginUser)
			return (LoginUser)loginObj;
		else
			return null;
	}
	
	/**
	 * 判断是否是ajax请求
	 * 普通请求与ajax请求的报文头不一样,如果requestType能拿到值，并且值为XMLHttpRequest,表示客户端的请求为异步请求
	 * ，即是ajax请求了，反之如果为null,则是普通的请求
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected boolean isAjax(HttpServletRequest request) throws Exception {
		String requestType = request.getHeader("X-Requested-With");
		return requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest");
	}

	/**
	 * 检查处理器是否包含对应的注释，包含方法注释、类注释、包注释
	 * 
	 * @param handler
	 * @param annotationType
	 * @return
	 * @throws Exception
	 */
	protected boolean hasAnnotation(Object handler, Class annotationType) throws Exception {
		if (handler == null)
			return false;
		// 判断处理器是HandlerMethod，即在springMVC-servlet.xml中使用org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
		if (handler.getClass().isAssignableFrom(HandlerMethod.class) )
			return this.isHandlerMethodAnnotation((HandlerMethod) handler, annotationType);
		//若使用org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping，则handler的类直接是XXXController，无法定位是哪个方法来处理当前的请求
		else
			return isHandlerBeanAnnotation(handler, annotationType);

	}
	/**
	 * 获得处理器包含指定的注释，包含方法注释、类注释、包注释，优先级方法注释-》类注释-》包注释
	 * 
	 * @param handler
	 * @param annotationType
	 * @return
	 * @throws Exception
	 */
	protected Annotation getAnnotation(Object handler, Class annotationType) throws Exception {
		if(!this.hasAnnotation(handler, annotationType))
			return null;
		
				
		Class hdlClass = handler.getClass();
		// 判断处理器是HandlerMethod，即在springMVC-servlet.xml中使用org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
		if (hdlClass.isAssignableFrom(HandlerMethod.class) ) 
			return this.getHandlerMethodAnnotation((HandlerMethod)handler, annotationType);
		else
			return this.getHandlerBeanAnnotation(handler, annotationType);
 
	}
	private boolean isHandlerBeanAnnotation(Object handler, Class annotationType) throws Exception {
		if (handler == null)
			return false;
		// 判断处理器是HandlerMethod，即在springMVC-servlet.xml中使用org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
		if (handler.getClass().isAnnotationPresent(annotationType))
			// 对应的方法上有相关注释，直接返回
			return true;
		else
			// 再查看对应的包是否有相关的注释
			return handler.getClass().getPackage().isAnnotationPresent(annotationType);

	}
	private Annotation getHandlerBeanAnnotation(Object handler, Class annotationType) throws Exception {
		if (handler == null)
			return null;
		// 判断处理器是HandlerMethod，即在springMVC-servlet.xml中使用org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
		if (handler.getClass().isAnnotationPresent(annotationType))
			// 对应的方法上有相关注释，直接返回
			return handler.getClass().getAnnotation(annotationType);
		else{
			// 再查看对应的包是否有相关的注释
			if(handler.getClass().getPackage().isAnnotationPresent(annotationType))
				return handler.getClass().getPackage().getAnnotation(annotationType);
			else
				return null;
		}

	}
	 
	private boolean isHandlerMethodAnnotation(HandlerMethod handler, Class annotationType) throws Exception {
		if (handler == null)
			return false;
		// 判断处理器是HandlerMethod，即在springMVC-servlet.xml中使用org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
		if (handler.hasMethodAnnotation(annotationType))  
			// 对应的方法上有相关注释，直接返回
			return true;
		 
		// 对应的方法上没有相关注释，再查看对应的类
		Class beanClass = ((HandlerMethod) handler).getBeanType();
		if (beanClass.isAnnotationPresent(annotationType))
			return true;
		else
			// 再查看对应的包是否有相关的注释
			return beanClass.getPackage().isAnnotationPresent(annotationType);

	}

	private Annotation getHandlerMethodAnnotation(HandlerMethod handler, Class annotationType) throws Exception {
		if (handler == null)
			return null;
		// 判断处理器是HandlerMethod，即在springMVC-servlet.xml中使用org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
		if (handler.hasMethodAnnotation(annotationType)) 
			// 对应的方法上有相关注释，直接返回
			return handler.getMethodAnnotation(annotationType);
		
		// 对应的方法上没有相关注释，再查看对应的类
		Class beanClass = ((HandlerMethod) handler).getBeanType();
		if (beanClass.isAnnotationPresent(annotationType))
			return beanClass.getAnnotation(annotationType);
		else{
			// 再查看对应的包是否有相关的注释
			if(beanClass.getPackage().isAnnotationPresent(annotationType))
				return beanClass.getPackage().getAnnotation(annotationType);
			else 
				return null;
		}

	}
	

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}
}
