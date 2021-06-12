/**
 * 
 */
package com.hikvision.sc.devops.logviewer.controller;

import com.hikvision.sc.devops.logviewer.utils.ViewUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class RootController {

	@RequestMapping("/")
	public ModelAndView frontend(Model model, HttpServletRequest request) {
		return ViewUtils.createModelView("login", "errorMsg", "");
	}

}
