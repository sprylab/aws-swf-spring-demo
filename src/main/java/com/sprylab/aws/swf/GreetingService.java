/*
 * Copyright (C) 2016 sprylab technologies GmbH.
 */
package com.sprylab.aws.swf;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprylab.aws.swf.workflow.GreetingWorkflowClientExternal;
import com.sprylab.aws.swf.workflow.GreetingWorkflowClientExternalFactory;

/**
 * author: andreas.pasch@sprylab.com
 * date: 25.08.16 17:21
 */
@Service
public class GreetingService {

    @Autowired
    private GreetingWorkflowClientExternalFactory workflowClientFactory;

    public void greetAttendees() {
        // obtain the workflow client instance
        GreetingWorkflowClientExternal greetingWorkflowClient = workflowClientFactory.getClient(UUID.randomUUID().toString());
        // ...and start the workflow
        greetingWorkflowClient.start();
    }
}
