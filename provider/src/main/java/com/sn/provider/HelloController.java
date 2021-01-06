package com.sn.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {
    @Value("${server.port}")
    Integer port;

    @GetMapping("/hello")
    public String hello() {
        System.out.println(System.currentTimeMillis());
        return "hello world" + "\n" + port;
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
}
