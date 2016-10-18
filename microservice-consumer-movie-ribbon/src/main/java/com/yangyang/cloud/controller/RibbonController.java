package com.yangyang.cloud.controller;

import com.yangyang.cloud.domain.User;
import com.yangyang.cloud.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenshunyang on 2016/10/16.
 */
@RestController
public class RibbonController {

    @Autowired
    private RibbonService ribbonService;


    @GetMapping("/ribbon/{id}")
    public User findById(@PathVariable Long id) {
        return this.ribbonService.findById(id);
    }
}
