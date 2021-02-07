package com.dimasta.learn.statisticsMicroservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @RequestMapping("/test")
    public String test() {
        return "StatisticsMicroservice works correctly!";
    }
}
