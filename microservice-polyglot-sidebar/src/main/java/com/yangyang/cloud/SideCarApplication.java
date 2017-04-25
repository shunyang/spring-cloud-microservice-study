package com.yangyang.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

/**
 * @EnableSidecar 这个注解包含了@EnableZuulProxy，所以呢，它也支持软
 负载均衡，如果启动多个服务，通过gateway来调用这个接口，多次调用我们会发现，请求会落在不同的服务上
 * Created by chenshunyang on 2016/10/23.
 */
@EnableSidecar
@SpringBootApplication
public class SideCarApplication {
    public static void main(String[] args) {
        SpringApplication.run(SideCarApplication.class,args);
    }
}
