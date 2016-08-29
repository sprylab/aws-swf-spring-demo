/*
 * Copyright (C) 2016 sprylab technologies GmbH.
 */
package com.sprylab.aws.swf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Starts the spring boot application.
 * <p>
 *     the initialization of workflow and activity workers is automatically done by Spring during autowire of the beans
 *     defined in {@link AppConfiguration}.
 * </p>
 *
 * author: andreas.pasch@sprylab.com
 * date: 25.08.16 16:25
 *
 * @see AppConfiguration
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws InterruptedException {
        final SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }
}
