package com.github.wanjinzhong.easycronplugincenter.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

    @RequestMapping(value = {
            "/login",
            // "/**"
    })
    public ModelAndView openIndexPage() {
        return new ModelAndView("/");
    }
}
