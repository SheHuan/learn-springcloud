package com.sn.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * 编码方式路由
     *
     * @param builder
     * @return
     */
//    @Bean
//    RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        // http://localhost:2022/get
//        return builder.routes()
//                // 将/get路由到http://httpbin.org/get
//                .route("my_route", r -> r.path("/get").uri("http://httpbin.org/get"))
//                .build();
//    }

    /*
     * 整合微服务后，调用服务接口时，服务名要使用Eureka注册中心里的服务名
     * （项目配置文件里的服务名小写，但到注册中心变大写了）http://localhost:2022/PROVIDER/hello
     */
}
