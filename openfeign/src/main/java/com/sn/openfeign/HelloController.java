package com.sn.openfeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
public class HelloController {
    @Autowired
    HelloService helloService;

    @GetMapping("/hello")
    String hello() {
        return helloService.hello();
    }

    @GetMapping("/data")
    List<String> getDataByIds() {
        return helloService.getDataByIds("1,2,3,4");
    }

    @GetMapping("/hello2")
    String hello2() {
        return helloService.hello2("ice peak");
    }

    @GetMapping("/hello3")
    String hello3() throws UnsupportedEncodingException {
        // 中文需要编码
        return helloService.hello3(URLEncoder.encode("西安", "utf-8"));
    }

    @GetMapping("/hello-api")
    void helloApi() throws UnsupportedEncodingException {
        System.out.println(helloService.api_hello());
        System.out.println(helloService.api_getDataByIds("1,2,3,4"));
        System.out.println(helloService.api_hello2("ice peak"));
        System.out.println(helloService.api_hello3(URLEncoder.encode("西安", "utf-8")));
    }
}
