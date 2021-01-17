package com.sn.zipkin02;

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

    @RequestMapping("/hello")
    public void hello() {
        String result = restTemplate.getForObject("http://localhost:5301/hello?name={1}", String.class, "shehuan");
        log.info("zipkin02: " + result);
    }
}
