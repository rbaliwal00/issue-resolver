package com.rbaliwal00.todoappusingjsp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
public class SayHelloController {
    @RequestMapping("/")
    @ResponseBody
    public String sayHello(){
        return "Hello World!!";
    }

    @RequestMapping("say-html")
    @ResponseBody
    public String sayHelloHtml(){
        StringBuffer sb = new StringBuffer();
        sb.append("<h1>Hello World. Welcome to our Html Page</h1>");
        return sb.toString();
    }

    @RequestMapping("say-jsp")
    public String sayHelloJsp(){
        return "sayHello";
    }
}
