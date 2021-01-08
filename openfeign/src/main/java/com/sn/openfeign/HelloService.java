package com.sn.openfeign;

import com.sn.IHelloService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// value 指定要调用的服务名称
// fallback 指定Hystrix对应的服务降级处理类
// fallbackFactory 和 fallback 的用途一样，定义一个即可
@FeignClient(value = "provider", /*fallback = HelloServiceFallback.class, */ fallbackFactory = HelloServiceFallbackFactory.class)
public interface HelloService extends IHelloService {
    // 指定目标服务的接口，方法名可任意指定
    @GetMapping("/hello")
    String hello();

    @GetMapping("/data/{ids}")
    List<String> getDataByIds(@PathVariable("ids") String ids);

    // key-value格式传递数据时，需要添加@RequestParam
    @GetMapping("/hello2")
    String hello2(@RequestParam("name") String name);

    // 使用@RequestHeader传递中文需要编码，接收服务则需要转码
    @GetMapping("/hello3")
    String hello3(@RequestHeader("name") String name);
}
