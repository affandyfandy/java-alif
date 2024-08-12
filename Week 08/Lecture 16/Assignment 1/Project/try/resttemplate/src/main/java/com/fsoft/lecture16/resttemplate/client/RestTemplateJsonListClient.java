package com.fsoft.lecture16.resttemplate.client;

import com.fsoft.lecture16.resttemplate.service.UserConsumerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RestTemplateJsonListClient {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.example");
        context.refresh();

        UserConsumerService userConsumerService = context.getBean(UserConsumerService.class);
        userConsumerService.getUserNames().forEach(System.out::println);

        context.close();
    }
}
