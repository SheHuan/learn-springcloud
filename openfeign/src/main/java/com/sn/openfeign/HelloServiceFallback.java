package com.sn.openfeign;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 整合 Hystrix 后，微服务接口调用失败后对应的服务降级处理方法
 */
@Component
// 下边的注解是为了防止请求接口地址重复定义，因为HelloService已经定义了请求接口地址，这里继承它相当于又定义了一遍
// 如果不想使用这个注解，感觉不优雅，可以重新定义一个FallbackFactory类，来代替这个类
@RequestMapping("/sn")
public class HelloServiceFallback implements HelloService {
    @Override
    public String hello() {
        return "error";
    }

    @Override
    public List<String> getDataByIds(String ids) {
        return new ArrayList<>();
    }

    @Override
    public String hello2(String name) {
        return "error";
    }

    @Override
    public String hello3(String name) {
        return "error";
    }

    @Override
    public String api_hello() {
        return null;
    }

    @Override
    public List<String> api_getDataByIds(String ids) {
        return null;
    }

    @Override
    public String api_hello2(String name) {
        return null;
    }

    @Override
    public String api_hello3(String name) throws UnsupportedEncodingException {
        return null;
    }
}
