/*
 * Copyright (C) 2016 sprylab technologies GmbH.
 */
package com.sprylab.aws.swf;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClient;
import com.amazonaws.services.simpleworkflow.flow.spring.SpringActivityWorker;
import com.amazonaws.services.simpleworkflow.flow.spring.SpringWorkflowWorker;
import com.amazonaws.services.simpleworkflow.flow.spring.WorkflowScope;
import com.google.common.collect.Lists;
import com.sprylab.aws.swf.activities.GreetingActivitiesClientImpl;
import com.sprylab.aws.swf.activities.GreetingActivitiesImpl;
import com.sprylab.aws.swf.workflow.GreetingWorkflowClientExternalFactoryImpl;
import com.sprylab.aws.swf.workflow.GreetingWorkflowImpl;

/**
 * author: andreas.pasch@sprylab.com
 * date: 25.08.16 16:42
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class AppConfiguration implements EnvironmentAware {

    private String accessKey;

    private String secretKey;

    private Region region;

    private String domain;

    private String workflowTaskList;

    private String activitiesTaskList;

    private int maxWorkflowThreads;

    private int maxActivityThreads;

    @Bean
    public BasicAWSCredentials awsCredentials() {
        return new BasicAWSCredentials(accessKey,
                                       secretKey);
    }

    @Bean
    public SpringWorkflowWorker workflowWorker(final AmazonSimpleWorkflow amazonSimpleWorkflow, final GreetingWorkflowImpl greetingWorkflow) {
        try {
            SpringWorkflowWorker workflowWorker = new SpringWorkflowWorker(amazonSimpleWorkflow, domain, workflowTaskList);
            workflowWorker.setWorkflowImplementations(Lists.newArrayList(greetingWorkflow));
            workflowWorker.setPollThreadCount(maxWorkflowThreads);
            workflowWorker.setDomainRetentionPeriodInDays(1);
            workflowWorker.setRegisterDomain(true);
//            workflowWorker.setDisableServiceShutdownOnStop(true);
            return workflowWorker;
        } catch (Exception e) {
            throw new RuntimeException("unable to initialize workflow worker");
        }
    }

    @Bean
    public SpringActivityWorker activityWorker(final AmazonSimpleWorkflow amazonSimpleWorkflow,
                                               final GreetingActivitiesImpl greetingActivities) {
        try {
            SpringActivityWorker renderingActivitiesWorker = new SpringActivityWorker(amazonSimpleWorkflow, domain, activitiesTaskList);
            renderingActivitiesWorker.addActivitiesImplementation(greetingActivities);
            renderingActivitiesWorker.setTaskExecutorThreadPoolSize(maxActivityThreads);
            renderingActivitiesWorker.setDomainRetentionPeriodInDays(1);
            renderingActivitiesWorker.setRegisterDomain(true);
//            renderingActivitiesWorker.setDisableServiceShutdownOnStop(true);
            return renderingActivitiesWorker;
        } catch (Exception e) {
            throw new RuntimeException("unable to initialize activity worker");
        }
    }

    @Bean(destroyMethod = "shutdown")
    public AmazonSimpleWorkflow amazonSimpleWorkflow() {
        final AmazonSimpleWorkflow service = new AmazonSimpleWorkflowClient(awsCredentials(), new ClientConfiguration().withConnectionTimeout(70*1000));
        service.setRegion(region);
        return service;
    }

    @Bean
    public GreetingWorkflowClientExternalFactoryImpl greetingWorkflowClientExternalFactory() {
        return new GreetingWorkflowClientExternalFactoryImpl(amazonSimpleWorkflow(), domain);
    }

    @Bean
    @Scope(value = WorkflowScope.NAME)
    public GreetingWorkflowImpl greetingWorkflow() {
        return new GreetingWorkflowImpl();
    }

    @Bean
    @Scope(value = WorkflowScope.NAME)
    public GreetingActivitiesClientImpl greetingActivitiesClient() {
        return new GreetingActivitiesClientImpl();
    }

    @Bean
    public GreetingActivitiesImpl greetingActivities() {
        return new GreetingActivitiesImpl();
    }

    @Bean
    public static CustomScopeConfigurer customScopeConfigurer() {
        final CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.addScope(WorkflowScope.NAME, new WorkflowScope());
        return configurer;
    }

    @Override
    public void setEnvironment(Environment environment) {
        final PropertyResolver awsPropertyResolver = new RelaxedPropertyResolver(environment, "aws.");
        final PropertyResolver swfPropertyResolver = new RelaxedPropertyResolver(environment, "aws.swf.");
        region = Region.getRegion(Regions.fromName(awsPropertyResolver.getProperty("region")));
        accessKey = awsPropertyResolver.getProperty("accessKey");
        secretKey = awsPropertyResolver.getProperty("secretKey");
        domain = swfPropertyResolver.getProperty("domain");
        workflowTaskList= swfPropertyResolver.getProperty("workflowTaskList");
        activitiesTaskList= swfPropertyResolver.getProperty("activityTaskList");
        maxWorkflowThreads = swfPropertyResolver.getProperty("maxWorkflowThreads", Integer.class, 1);
        maxActivityThreads = swfPropertyResolver.getProperty("maxActivityThreads", Integer.class, 5);
    }
}
