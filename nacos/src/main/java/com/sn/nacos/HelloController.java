package com.sn.nacos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope
public class HelloController {
    @Value("${name}")
    String name;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello() {
        return name;
    }

    @GetMapping("/hello2")
    public String hello2() {
        return restTemplate.getForObject("http://nacos-2/hello", String.class);
    }
}
