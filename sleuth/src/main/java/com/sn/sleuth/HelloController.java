package com.sn.sleuth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {
    private static final Log log = LogFactory.getLog(HelloController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HelloService helloService;

    @RequestMapping("/hello")
    public String hello() {
        log.info("hello spring cloud sleuth");
        return "hello spring cloud sleuth";
    }

    @RequestMapping("/hello2")
    public String hello2() {
        log.info("hello2");
        return restTemplate.getForObject("http://localhost:5300/hello3", String.class);
    }

    @RequestMapping("/hello3")
    public String hello3() {
        log.info("hello3");
        return "hello3";
    }

    @RequestMapping("/hello4")
    public String hello4() {
        log.info("hello4");
        return helloService.backgroundFun();
    }

    @RequestMapping("/hello5")
    public void hello5() {
        helloService.task();
    }
}
