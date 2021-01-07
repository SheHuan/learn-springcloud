package com.sn.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class DataBatchService {
    @Autowired
    RestTemplate restTemplate;

    public List<String> getDataByIds(List<Integer> ids) {
        String[] objects = restTemplate.getForObject("http://provider/data/{ids}", String[].class, StringUtils.join(ids, ","));
        return Arrays.asList(objects);
    }

    /********************************如下是通过注解的方式实现请求合并**************************************/

    /**
     * 使用Command相关类继承方式实现请求合并太复杂，使用@HystrixCollapser就简单了
     *
     * @param id
     * @return
     */
    @HystrixCollapser(batchMethod = "getDataByIds2",
            collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds", value = "500"),
                    @HystrixProperty(name = "maxRequestsInBatch", value = "10")})
    public Future<String> getDataById(Integer id) {
        return null;
    }

    @HystrixCommand
    public List<String> getDataByIds2(List<Integer> ids) {
        String[] objects = restTemplate.getForObject("http://provider/data/{ids}", String[].class, StringUtils.join(ids, ","));
        return Arrays.asList(objects);
    }
}
