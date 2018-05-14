package org.bcia.javachain.ca.szca.publicweb.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.szca.common.exception.JspViewNotFoundException;
import com.szca.common.web.ControllerExceptionHandler;

@Controller
public class IndexController {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@RequestMapping("index")
	public ModelAndView index(String cmd, String format, String issuer) {
		return new ModelAndView("index");
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             <br>
	 *             Note:<br>
	 *             有些简单的功能不用编写单独的Controller，则在此直接跳转到对应的jsp,<br>
	 *             比如/test/testObj.html -> /views/test/testObj.jsp
	 *             /test/a/b/c/d.html -> /views/test/a/b/c/d.jsp
	 */
	@RequestMapping(value = "/**", method = { RequestMethod.POST, RequestMethod.GET })
	public String doDefault(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("doDefault:" + request.getRequestURI());
		String jspView = ControllerExceptionHandler.getView(request);
		if (ControllerExceptionHandler.checkJSP(request))
			return jspView;
		else
			// throw exception , ControllerExceptionHandler would catch this
			// exception
			throw new JspViewNotFoundException(String.format("View[%s] not found.", jspView));
	}

}
