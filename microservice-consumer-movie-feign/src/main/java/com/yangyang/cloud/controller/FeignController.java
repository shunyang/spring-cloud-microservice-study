package com.yangyang.cloud.controller;

import com.yangyang.cloud.domain.User;
import com.yangyang.cloud.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenshunyang on 2016/10/17.
 */
@RestController
public class FeignController {
    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("feign/{id}")
    public User findByIdFeign(@PathVariable Long id){
        User user = this.userFeignClient.findByIdFeign(id);
        return user;
    }
}
