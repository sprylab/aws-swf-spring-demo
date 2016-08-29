/*
 * Copyright (C) 2016 sprylab technologies GmbH.
 */
package com.sprylab.aws.swf.activities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * author: andreas.pasch@sprylab.com
 * date: 25.08.16 16:27
 */
public class GreetingActivitiesImpl implements GreetingActivities {

    private static final String[] ATTENDEES = new String[]{
            "Tom", "Mark", "Bernard", "Andy", "Susan", "Therese", "Christie"
    };

    @Override
    public String greet(String name) {
        return "Hello " + name + ", how are you?";
    }

    @Override
    public List<String> getAttendees() {
        List<String> attendees = Arrays.stream(ATTENDEES)
                     .limit(Math.max(1, new Random().nextInt(ATTENDEES.length)))
                     .collect(Collectors.toList());
        Collections.shuffle(attendees);
        return attendees;
    }
}
