package com.sn.provider;

import com.sn.IHelloService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController2 implements IHelloService {
    @Value("${server.port}")
    Integer port;

    @Override
    public String api_hello() {
        System.out.println(System.currentTimeMillis());
        return "hello world" + "#" + port;
    }

    @Override
    public List<String> api_getDataByIds(String ids) {
        System.out.println(ids);
        String[] idArr = ids.split(",");
        ArrayList<String> data = new ArrayList<>();
        for (String id : idArr) {
            data.add("data" + id);
        }
        return data;
    }

    @Override
    public String api_hello2(String name) {
        System.out.println(System.currentTimeMillis());
        return name + "#" + port;
    }

    @Override
    public String api_hello3(String name) throws UnsupportedEncodingException {
        System.out.println(System.currentTimeMillis());
        return URLDecoder.decode(name, "utf-8") + "#" + port;
    }
}
