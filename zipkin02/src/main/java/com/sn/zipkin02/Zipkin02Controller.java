package com.sn.zipkin02;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Zipkin02Controller {
    private static final Log log = LogFactory.getLog(Zipkin02Controller.class);

    @RequestMapping("/hello")
    public String hello(String name) {
        log.info("zipkin02");
        return "hello " + name;
    }
}
