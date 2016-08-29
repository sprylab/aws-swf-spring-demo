/*
 * Copyright (C) 2016 sprylab technologies GmbH.
 */
package com.sprylab.aws.swf.workflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.amazonaws.services.simpleworkflow.flow.core.Promises;
import com.sprylab.aws.swf.activities.GreetingActivitiesClient;

/**
 * author: andreas.pasch@sprylab.com
 * date: 25.08.16 16:32
 */
public class GreetingWorkflowImpl implements GreetingWorkflow {

    @Autowired
    private GreetingActivitiesClient greetingActivitiesClient;

    @Override
    public void start() {
        // get random list of attendees
        final Promise<List<String>> attendees = greetingActivitiesClient.getAttendees();
        // create a greeting message for each
        final Promise<List<String>> greetings = greetEach(attendees);
        // print all greeting messages when they have been created by the activity
        printGreetings(greetings);
    }

    @Asynchronous
    private Promise<List<String>> greetEach(final Promise<List<String>> attendees) {
        List<Promise<String>> greetings = new ArrayList<>();
        attendees.get().stream()
                 .forEach(attendee -> {
                     Promise<String> greet = greetingActivitiesClient.greet(attendee);
                     greetings.add(greet);
                 });
        return Promises.listOfPromisesToPromise(greetings);
    }

    @Asynchronous
    private void printGreetings(Promise<List<String>> greetings) {
        greetings.get().forEach(System.out::println);
    }
}
