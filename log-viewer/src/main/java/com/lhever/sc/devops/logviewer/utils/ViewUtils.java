package com.lhever.sc.devops.logviewer.utils;

import com.lhever.sc.devops.core.utils.StringUtils;
import org.springframework.web.servlet.ModelAndView;

public class ViewUtils {


    public static ModelAndView createModelView(String name, String key, Object obj) {
        ModelAndView view = new ModelAndView();
        view.setViewName(name);
        if (StringUtils.isNotBlank(key) && obj != null) {
            view.addObject(key, obj);
        }
        return view;
    }

    public static ModelAndView createModelView(String name, Object... obj) {
        ModelAndView view = new ModelAndView();
        view.setViewName(name);
        if (obj != null && obj.length > 0) {
            for (int i = 0; i < obj.length / 2; i ++) {
                String key = (String)obj[2 * i];
                Object value = obj[2 * i + 1];
                if (StringUtils.isBlank(key) || value == null) {
                    continue;
                }
                view.addObject(key, value);
            }
        }
        return view;
    }

}
