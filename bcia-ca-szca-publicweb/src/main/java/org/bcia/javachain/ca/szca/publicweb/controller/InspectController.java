package org.bcia.javachain.ca.szca.publicweb.controller;

 
import org.bcia.javachain.ca.szca.publicweb.CertAndRequestDumpBean;
import org.bcia.javachain.ca.szca.publicweb.service.InspectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

 

@Controller
public class InspectController {

	Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private InspectService service;

	@RequestMapping("/inspect/inspect")
	public ModelAndView inspect(MultipartFile reqfile) {
		logger.info("inspect for req");
		ModelAndView view = new ModelAndView("/inspect/inspect");

		CertAndRequestDumpBean dump = null;
		try {
			if (reqfile != null && reqfile.getBytes().length > 0) {
				logger.info(new String(reqfile.getBytes()));
				dump = service.inspectReqFile(reqfile.getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		view.addObject("dump", dump);

		return view;

	}

}