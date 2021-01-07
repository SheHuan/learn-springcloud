package com.sn;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IHelloService {
    @GetMapping("/api/hello")
    String api_hello();

    @GetMapping("/api/data/{ids}")
    List<String> api_getDataByIds(@PathVariable(value = "ids") String ids);

    @GetMapping("/api/hello2")
    String api_hello2(@RequestParam(value = "name") String name);

    @GetMapping("/api/hello3")
    String api_hello3(@RequestHeader(value = "name") String name) throws UnsupportedEncodingException;
}
