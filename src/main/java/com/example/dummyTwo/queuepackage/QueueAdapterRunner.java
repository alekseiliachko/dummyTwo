package com.example.dummyTwo.queuepackage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QueueAdapterRunner implements
        ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    QueueAdapter queueAdapter;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info(" ... Attempting to connect to Queue client .... ");
        queueAdapter.registerWithKey();
    }
}
