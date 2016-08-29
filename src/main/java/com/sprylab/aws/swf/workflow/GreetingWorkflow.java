/*
 * Copyright (C) 2016 sprylab technologies GmbH.
 */
package com.sprylab.aws.swf.workflow;

import com.amazonaws.services.simpleworkflow.flow.annotations.Execute;
import com.amazonaws.services.simpleworkflow.flow.annotations.Workflow;
import com.amazonaws.services.simpleworkflow.flow.annotations.WorkflowRegistrationOptions;

/**
 * author: andreas.pasch@sprylab.com
 * date: 25.08.16 16:31
 */
@Workflow
@WorkflowRegistrationOptions(defaultExecutionStartToCloseTimeoutSeconds = 3600)
public interface GreetingWorkflow {

    @Execute(version = "1.0")
    void start();
}
