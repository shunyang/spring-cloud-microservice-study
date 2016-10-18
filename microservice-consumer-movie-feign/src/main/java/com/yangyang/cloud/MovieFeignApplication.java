package com.yangyang.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by chenshunyang on 2016/10/17.
 * 使用@EnableFeignClients开启Feign
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MovieFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieFeignApplication.class,args);
    }
}
