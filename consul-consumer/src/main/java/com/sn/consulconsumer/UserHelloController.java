package com.sn.consulconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserHelloController {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @GetMapping("/hello")
    public String hello() {
        ServiceInstance choose = loadBalancerClient.choose("consul-provider");
        String result = restTemplate.getForObject(choose.getUri() + "/hello", String.class);
        return result;
    }
}
