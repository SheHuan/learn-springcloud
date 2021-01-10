package com.sn.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
// 开启网关代理
@EnableZuulProxy
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
    /*
     * http://localhost:1114/hello 直接访问服务
     * http://localhost:2021/provider/hello 通过zuul网关代理访问
     * http://localhost:2021/hello 配置了路由规则也可以这样访问
     * http://localhost:2021/login?username=shehuan&password=1234656 测试过滤器
     */
}
