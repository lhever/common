/**
 * 
 */
package com.lhever.sc.devops.logviewer.controller;

import com.lhever.sc.devops.logviewer.constant.LogViewerConst;
import com.lhever.sc.devops.logviewer.dto.LoginCache;
import com.lhever.sc.devops.logviewer.dto.LoginForm;
import com.lhever.sc.devops.logviewer.utils.CommonUtils;
import com.lhever.sc.devops.logviewer.utils.ViewUtils;
import com.lhever.sc.devops.core.utils.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author dengyishi
 * <p>
 * 2017年11月6日 下午9:04:24
 */
@RestController
public class LoginController {

	private LoginCache loginCache = new LoginCache();

	@Value("${log.user.name}")
	private String loginUser;

	@Value("${log.user.pwd}")
	private String loginPwd;

	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public ModelAndView loginPage() {
		loginCache.removeExpire();
		return ViewUtils.createModelView("login", "errorMsg", "");
	}

	@PostMapping("/login")
	public ModelAndView login(LoginForm loginForm, HttpServletRequest request) {
		String errorMsg = null;
		synchronized (loginCache) {
			errorMsg = checkPwd(request, loginForm.getUsername(), loginForm.getPassword());
		}
		if (StringUtils.isBlank(errorMsg)) {
			HttpSession session = request.getSession(true);
			session.setAttribute(LogViewerConst.CURRENT_USER, loginForm.getUsername());
			return ViewUtils.createModelView("list", "content", getContent());
		} else {
			String tpl = "<h2 class=\"form-signin-heading\" align=\"center\"><font color=\"red\">{}</font></h2>";
			return ViewUtils.createModelView("login", "errorMsg", ParseUtils.parseArgs(tpl, errorMsg));
		}
	}



	public String checkPwd(HttpServletRequest request, String userName, String password) {
		String ip = request.getRemoteAddr();
		String loginCountKey = getLoginCountKey(ip);

		int loginCount = loginCache.getLoginCount(loginCountKey);
		if (isLocked(loginCount)) {
			return "登录被锁定15分钟!";
		}
		if (!loginUser.equals(userName) || StringUtils.isBlank(password)) {
			loginCache.incLoginCount(loginCountKey, loginCount);
			return "用户名或密码错误!";
		}

		String base64 = Base64Utils.encode("123456" + loginPwd);
		String md5 = Md5Utils.md5Quitely(base64 + "123!@#");


		if (StringUtils.equals(md5, password)) {
			loginCache.clear(loginCountKey);
			return "";
		} else {
			loginCache.incLoginCount(loginCountKey, loginCount);
			return "用户名或密码错误!";
		}
	}

	private String getLoginCountKey(String clientIp) {
		return "clientIp:loginFail:" + clientIp;
	}

	private boolean isLocked(int loginFailCount) {
		return loginFailCount >= 5;
	}









	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return ViewUtils.createModelView("login", "errorMsg", "");
	}


	private String getContent() {
		Map<String, Object> yamlMap = CommonUtils.getYamlMap();
		Map<String, Object> fileMap = OgnlUtils.getValue("log.files", yamlMap);
		if (fileMap == null) {
			return "";
		}
		Set<String> keys = fileMap.keySet();
		if (keys == null || keys.size() == 0) {
			return "";
		}

		List<String> list = new ArrayList<>();

		String liTemplate = "<li class=\"list-group-item\"><a href=\"{}\">{}</a></li>";

		String host = OgnlUtils.getValue("server.host", yamlMap);
		Integer port = OgnlUtils.getValue("server.port", yamlMap);
		Map<String, Object> servletMap = OgnlUtils.getValue("server.servlet", yamlMap);
		String context  = CollectionUtils.getValueSafely(servletMap, "context-path", String.class);

		for (String key : keys) {
			Map<String, Object>  value = OgnlUtils.getValue(key, fileMap);
			String fileName = CollectionUtils.getValueSafely(value, LogViewerConst.ROLLING_FILE_NAME, String.class);
			String desc = CollectionUtils.getValueSafely(value, LogViewerConst.FILE_DESCRIPTION, String.class);
			String tip = null;
			if (StringUtils.isNotBlank(desc)) {
				tip = desc;
			} else {
				tip = key + "服务的日志文件:" + fileName;
			}

			String url = "http://" + host.trim() + ":" + port + FileUtils.trimTail(context) + "/index?serviceName=" + key;


			String li = ParseUtils.parseArgs(liTemplate, url, tip);
			list.add(li);
		}

		String ulStart = "<ul class=\"list-group\">";
		String join = StringUtils.join(list, "\r\n");
		String ulEnd = "</ul>";
		return ulStart + join + ulEnd;
	}


}
