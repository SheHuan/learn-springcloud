package com.sn.openfeign;

import com.sn.IHelloService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// 指定服务名称
@FeignClient("provider")
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
