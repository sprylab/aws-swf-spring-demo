/*
 * Copyright (C) 2016 sprylab technologies GmbH.
 */
package com.sprylab.aws.swf.activities;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;

/**
 * author: andreas.pasch@sprylab.com
 * date: 25.08.16 16:25
 */
@ActivityRegistrationOptions(
        defaultTaskScheduleToStartTimeoutSeconds = 300,
        defaultTaskStartToCloseTimeoutSeconds = 300)
@Activities(version = "1.0")
public interface GreetingActivities {

    List<String> getAttendees();

    String greet(String name);

}
