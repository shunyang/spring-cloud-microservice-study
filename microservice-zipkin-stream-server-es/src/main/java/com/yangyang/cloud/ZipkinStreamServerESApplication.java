package com.yangyang.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

/**
 * Created by chenshunyang on 2017/5/24.
 */
@EnableZipkinStreamServer// //使用Stream方式启动ZipkinServer
@SpringBootApplication
public class ZipkinStreamServerESApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZipkinStreamServerESApplication.class,args);
    }
}
