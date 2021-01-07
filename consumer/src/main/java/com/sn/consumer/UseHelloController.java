package com.sn.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@RestController
public class UseHelloController {
    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    @Qualifier("restTemplate")
    RestTemplate restTemplate;

    @Autowired
    @Qualifier("restTemplate2")
    RestTemplate restTemplate2;

    @GetMapping("/hello")
    public String hello() {
        List<ServiceInstance> providers = discoveryClient.getInstances("provider");
        ServiceInstance serviceInstance = providers.get(0);
        String scheme = serviceInstance.getScheme();
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();

        StringBuilder sb = new StringBuilder();
        sb.append(scheme).append("://")
                .append(host).append(":")
                .append(port)
                .append("/hello");

        try {
            URL url = new URL(sb.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder resultSb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    resultSb.append(line);
                }
                return resultSb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    int count;

    @GetMapping("/hello2")
    public String hello2() {
        List<ServiceInstance> providers = discoveryClient.getInstances("provider");
        // 模拟负载均衡
        ServiceInstance serviceInstance = providers.get((count++) % providers.size());
        String scheme = serviceInstance.getScheme();
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();

        StringBuilder sb = new StringBuilder();
        sb.append(scheme).append("://")
                .append(host).append(":")
                .append(port)
                .append("/hello");

        try {
            URL url = new URL(sb.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder resultSb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    resultSb.append(line);
                }
                return resultSb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @GetMapping("/hello3")
    public String hello3() {
        List<ServiceInstance> providers = discoveryClient.getInstances("provider");
        // 模拟负载均衡
        ServiceInstance serviceInstance = providers.get((count++) % providers.size());
        String scheme = serviceInstance.getScheme();
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();

        StringBuilder sb = new StringBuilder();
        sb.append(scheme).append("://")
                .append(host).append(":")
                .append(port)
                .append("/hello");

        String result = restTemplate.getForObject(sb.toString(), String.class);
        return result;
    }

    @GetMapping("/hello4")
    public String hello4() {
        String url = "http://provider/hello";
        String result = restTemplate2.getForObject(url, String.class);
        return result;
    }
}
