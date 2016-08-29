/*
 * Copyright (C) 2016 sprylab technologies GmbH.
 */
package com.sprylab.aws.swf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * author: andreas.pasch@sprylab.com
 * date: 26.08.16 15:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingServiceTest {

    @Autowired
    private GreetingService greetingService;

    @Test
    public void testGreetAttendees() throws InterruptedException {
        System.out.println("\nStart greeting workflow\n");

        greetingService.greetAttendees();

        Thread.sleep(4000);

        System.out.println("\nWorkflow done.\n");

        // Exceptions during shutdown are annoying but benign.
        // They are caused by long poll being interrupted. The only
        // way to shutdown workers without long poll interruption
        // is to wait for a poll to return (up to a minute),
        // stop polling loop and then close the socket.
        // You can eliminate the occurrence of these exceptions
        // by setting "disableServiceShutdownOnStop" to "true" on the workers
        // but it takes longer to shut down.
    }
}
