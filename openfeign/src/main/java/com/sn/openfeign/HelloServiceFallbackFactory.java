package com.sn.openfeign;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 和 HelloServiceFallback 的作用一样
 */
@Component
public class HelloServiceFallbackFactory implements FallbackFactory<HelloService> {
    @Override
    public HelloService create(Throwable throwable) {
        return new HelloService() {
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
                return null;
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
        };
    }
}
