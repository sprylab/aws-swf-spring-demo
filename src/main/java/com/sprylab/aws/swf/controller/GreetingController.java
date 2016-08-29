/*
 * Copyright (C) 2016 sprylab technologies GmbH.
 */
package com.sprylab.aws.swf.controller;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sprylab.aws.swf.GreetingService;

/**
 * author: andreas.pasch@sprylab.com
 * date: 25.08.16 17:11
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class GreetingController {

    @Autowired
    private GreetingService greetingService;

    @RequestMapping(value = "/greet", method = RequestMethod.GET)
    public Callable<ResponseEntity<Void>> greet() {
        return () -> {
            greetingService.greetAttendees();
            return ResponseEntity.ok().build();
        };
    }
}
