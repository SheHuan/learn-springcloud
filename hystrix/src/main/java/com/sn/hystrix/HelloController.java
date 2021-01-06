package com.sn.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class HelloController {
    @Autowired
    HelloService helloService;

    @Autowired
    DataBatchService dataBatchService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello() {
        return helloService.hello();
    }

    @GetMapping("/hello2")
    public String hello2() {
        try {
            return helloService.hello2().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @GetMapping("/hello3")
    public String hello3() {
        return helloService.hello3();
    }

    @GetMapping("/hello4")
    public String hello4() {
        return helloService.hello4();
    }

    @GetMapping("/hello5")
    public String hello5() {
        HelloCommand helloCommand = new HelloCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("xxx")), restTemplate);
        String result = helloCommand.execute();

//        try {
//            result = helloCommand.queue().get();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return result;
    }

    @GetMapping("/hello6")
    public String hello6() {
        // 使用缓存时需要先执行如下操作
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();

        String result = "";
        result = helloService.hello5("xxx");

        // 移除缓存，一般可以在删除持久化数据后进行该操作，防止库里的数据已经删了，缓存里还有
        helloService.delete("xxx");

        result = helloService.hello5("xxx");

        // 释放缓存
        hystrixRequestContext.close();

        return result;
    }

    @GetMapping("/hello7")
    public void hello7() throws ExecutionException, InterruptedException {
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();

        DataCollapseCommand command1 = new DataCollapseCommand(dataBatchService, 1);
        DataCollapseCommand command2 = new DataCollapseCommand(dataBatchService, 2);
        DataCollapseCommand command3 = new DataCollapseCommand(dataBatchService, 3);
        DataCollapseCommand command4 = new DataCollapseCommand(dataBatchService, 4);

        Future<String> future1 = command1.queue();
        Future<String> future2 = command2.queue();
        Future<String> future3 = command3.queue();
        Future<String> future4 = command4.queue();

        String s1 = future1.get();
        String s2 = future2.get();
        String s3 = future3.get();
        String s4 = future4.get();

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);

        // 发送第请求前休眠1s，大于设置的500毫秒间隔，则下次请求不会被合并
        Thread.sleep(1000);

        DataCollapseCommand command5 = new DataCollapseCommand(dataBatchService, 5);
        Future<String> future5 = command5.queue();
        String s5 = future5.get();
        System.out.println(s5);

        hystrixRequestContext.close();
    }

    @GetMapping("/hello8")
    public void hello8() throws ExecutionException, InterruptedException {
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();

        Future<String> future1 = dataBatchService.getDataById(1);
        Future<String> future2 = dataBatchService.getDataById(2);
        Future<String> future3 = dataBatchService.getDataById(3);
        Future<String> future4 = dataBatchService.getDataById(4);

        String s1 = future1.get();
        String s2 = future2.get();
        String s3 = future3.get();
        String s4 = future4.get();

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);

        // 发送第请求前休眠1s，大于设置的500毫秒间隔，则下次请求不会被合并
        Thread.sleep(1000);

        Future<String> future5 = dataBatchService.getDataById(5);
        String s5 = future5.get();
        System.out.println(s5);

        hystrixRequestContext.close();
    }
}
