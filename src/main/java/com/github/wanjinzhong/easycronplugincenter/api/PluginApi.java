package com.github.wanjinzhong.easycronplugincenter.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("public/api")
@RestController
public class PluginApi {

    @GetMapping("plugins")
    public String getPlugins() {
        return "OK";
    }
}
