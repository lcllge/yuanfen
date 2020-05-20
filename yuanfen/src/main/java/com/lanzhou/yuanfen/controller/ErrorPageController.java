package com.lanzhou.yuanfen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/error/")
public class ErrorPageController {


    @RequestMapping("404")
    public String page404(@PathVariable String url) {
        return "error/".concat(url);
    }



}
