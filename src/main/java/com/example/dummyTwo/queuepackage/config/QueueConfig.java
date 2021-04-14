package com.example.dummyTwo.queuepackage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class QueueConfig {
    public static final String THIS_LISTENER_PATH = "queueMessageBroker/";

    public static final String QUEUE_BASE_URL_PATH = "http://localhost:8080/";
    public static final String QUEUE_MESSAGE_PATH = "messageReceived/";
    public static final String QUEUE_REGISTER_PATH = "registerRemote/";
    public static final String QUEUE_SUBSCRIPTION_PATH = "topic/";

    private static class QueueTemplateConfig {
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }
}
