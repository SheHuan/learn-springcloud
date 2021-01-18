package com.sn.zipkin01;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Zipkin01Controller {
    private static final Log log = LogFactory.getLog(Zipkin01Controller.class);

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/hello")
    public String hello() {
        log.info("zipkin01");
        String result = restTemplate.getForObject("http://localhost:5302/hello?name={1}", String.class, "2021");
        return result;
    }
}
