package com.sn.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

@Service
public class HelloService {
    @Autowired
    RestTemplate restTemplate;

    /**
     * @return
     * @HystrixCommand 的 fallbackMethod 属性表示目标微服务的方法调用失败时的临时容错处理替代法（服务降级）
     * <p>
     * hello()方法如果有参数，则error()方法需要声明同样的参数
     */
    @HystrixCommand(fallbackMethod = "error")
    public String hello() {
        String url = "http://provider/hello";
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }

    /**
     * 请求异步调用
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "error")
    public Future<String> hello2() {
        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                return restTemplate.getForObject("http://provider/hello", String.class);
            }
        };
    }

    /**
     * 不是微服务调用出错，而是其它地方出错的异常，同样会进入到容错处理方法，进行服务降级处理
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "error2")
    public String hello3() {
        int i = 1 / 0;
        return restTemplate.getForObject("http://provider/hello", String.class);
    }

    /**
     * ignoreExceptions 指定忽略那些异常，不进行服务降级处理，直接抛出异常
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "error2", ignoreExceptions = {ArithmeticException.class})
    public String hello4() {
        int i = 1 / 0;
        return restTemplate.getForObject("http://provider/hello", String.class);
    }

    /**
     * @CacheResult 注解表示返回的结果会被缓存，默认情况下，缓存的key是方法的参数（多个参数则会拼接），缓存的value是方法的返回结果
     */
    @HystrixCommand(fallbackMethod = "error3")
    @CacheResult
    public String hello5(String param) {
        return restTemplate.getForObject("http://provider/hello", String.class);
    }

    @HystrixCommand(fallbackMethod = "error3")
    // 删除缓存，commandKey是进行缓存操作的方法名
    @CacheRemove(commandKey = "hello5")
    public String delete(String param) {
        return "";
    }

    public String error() {
        return "error";
    }

    public String error2(Throwable throwable) {
        return "error: " + throwable.getMessage();
    }

    public String error3(String param) {
        return "error: " + param;
    }
}
