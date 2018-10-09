package com.szca.common.interceptor;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

public class LanguageInterceptor extends BaseInterceptor {

	 
	public LanguageInterceptor() {
		logger.debug("LanguageInterceptor ......");

	}
 
	/**
     * Default name of the locale specification parameter: "locale".
     */
    public static final String DEFAULT_PARAM_NAME = "locale";
    private String paramName = DEFAULT_PARAM_NAME;
    
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
    public String getParamName() {
        return this.paramName;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Locale newLocale = getLocale(request.getParameter(getParamName()));
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        
        if (localeResolver == null) {
            throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
        }
        
        localeResolver.setLocale(request, response, newLocale);
        
        return true;
    }
    

    //根据language 获取Locale
    public static Locale getLocale(String language){
        Locale locale = new Locale("zh", "CN");
        if(language!=null && language.equals("en")){
            locale = new Locale("en", "US");
        }
        
        return locale;
    }

}
