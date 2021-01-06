package com.sn.hystrix;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

/**
 * 自定义请求指令
 */
public class HelloCommand extends HystrixCommand<String> {
    private RestTemplate restTemplate;

    protected HelloCommand(Setter setter, RestTemplate restTemplate) {
        super(setter);
        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() throws Exception {
        // 异常测试
        int i = 1 / 0;
        String result = restTemplate.getForObject("http://provider/hello", String.class);
        return result;
    }

    /**
     * 容错处理方法
     *
     * @return
     */
    @Override
    protected String getFallback() {
        // 打印具体的异常信息
        return "error: " + getExecutionException().getMessage();
    }
}
