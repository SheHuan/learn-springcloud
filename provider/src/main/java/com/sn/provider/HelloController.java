package com.sn.provider;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class HelloController {
    @Value("${server.port}")
    Integer port;

    @GetMapping("/hello")
    public String hello() {
        System.out.println(System.currentTimeMillis());
        return "hello world" + "#" + port;
    }

    /**
     * @param ids 格式为1,2,3,4
     * @return
     */
    @GetMapping("/data/{ids}")
    public List<String> getDataByIds(@PathVariable String ids) {
        System.out.println(ids);
        String[] idArr = ids.split(",");
        ArrayList<String> data = new ArrayList<>();
        for (String id : idArr) {
            data.add("data" + id);
        }
        return data;
    }

    @GetMapping("/hello2")
    public String hello2(String name) {
        System.out.println(System.currentTimeMillis());
        return name + "#" + port;
    }

    @GetMapping("/hello3")
    public String hello3(@RequestHeader String name) throws UnsupportedEncodingException {
        System.out.println(System.currentTimeMillis());
        return URLDecoder.decode(name, "utf-8") + "#" + port;
    }

    @GetMapping("/hello4")
    public String hello4() {
        System.out.println("hello4#" + port);
        int i = 1 / 0;
        return "hello world" + "#" + port;
    }

    @GetMapping("/hello5")
    // 限流配置
    @RateLimiter(name = "ratelimiterA")
    public String hello5() {
        System.out.println(new Date());
        return "hello world" + "#" + port;
    }

    @GetMapping("/login")
    public String login(String username, String password) {
        return "login" + "#" + port;
    }
}
