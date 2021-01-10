package com.sn.resilience4j;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
//@Retry(name = "retryA")
@CircuitBreaker(name = "cbA", fallbackMethod = "error")
public class HelloService {
    @Autowired
    RestTemplate restTemplate;

    public String hello() {
        return restTemplate.getForObject("http://provider/hello4", String.class);
    }

    public String hello2() {
        // 连续请求10次，测试服务端限流
        for (int i = 0; i < 10; i++) {
            restTemplate.getForObject("http://provider/hello5", String.class);
        }
        return "success";
    }

    public String error(Throwable t) {
        return "error: " + t.getMessage();
    }
}
