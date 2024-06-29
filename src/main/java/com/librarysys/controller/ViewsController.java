package com.librarysys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewsController
{
    @RequestMapping("/")
    public String index(){
        return "index.html";
    }

    @RequestMapping("/log-in")
    public String login(){
        return "loginpage.html";
    }

    @RequestMapping("/test")
    public String test(){
        return "testpage.html";
    }
}
