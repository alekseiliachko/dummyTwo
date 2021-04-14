package com.example.dummyTwo.queuepackage;

import com.example.dummyTwo.queuepackage.config.QueueConfig;
import com.example.dummyTwo.queuepackage.dto.InitRequest;
import com.example.dummyTwo.queuepackage.dto.Message;
import com.example.dummyTwo.queuepackage.excpetion.QueueMessageBrokerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static com.example.dummyTwo.queuepackage.config.QueueConfig.*;

@Slf4j
@Component
public class QueueAdapterImpl implements QueueAdapter{

    private final RestTemplate restTemplate;

    private String TOKEN = null;

    private final ObjectMapper objectMapper;

    public QueueAdapterImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    };

    @Override
    public void registerWithKey() {
        try {
            InitRequest initRequest = new InitRequest();
            initRequest.setRemoteName(THIS_BASE_URL_PATH);
            initRequest.setRemotePass(REMOTE_PASS);
            initRequest.setRemoteKey(REMOTE_KEY);

            URI URI_REG = new URI(QueueConfig.QUEUE_BASE_URL_PATH + QueueConfig.QUEUE_REGISTER_PATH);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(URI_REG, initRequest, String.class);

            if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                throw new QueueMessageBrokerException();
            }

            TOKEN = responseEntity.getBody();

            log.info(" ... successfully logged in ... ");
        } catch (Exception e) {
            log.error(" ... failed to log in ... ");
//            throw new QueueMessageBrokerException();
        }
    }

    @Override
    public void subscribe(String topic) {
        try {
            URI URI_SUB = new URI(QueueConfig.QUEUE_BASE_URL_PATH + QueueConfig.QUEUE_SUBSCRIPTION_PATH + topic + "/");
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(URI_SUB, getEntity(), String.class);
            if (!HttpStatus.OK.equals(responseEntity.getStatusCode()))
                throw new QueueMessageBrokerException();

            log.info("subbed to: " + topic);
        } catch (Exception e) {
            throw new QueueMessageBrokerException();
        }
    }

    @Override
    public void sendMessage(Message message) {
        try {
            URI URI_MES = new URI(QueueConfig.QUEUE_BASE_URL_PATH + QueueConfig.QUEUE_MESSAGE_PATH);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(URI_MES, getEntity(message), String.class);
            if (!HttpStatus.OK.equals(responseEntity.getStatusCode()))
                throw new QueueMessageBrokerException();

            log.info("send: " + message);
        } catch (Exception e) {
            throw new QueueMessageBrokerException();
        }
    }

    private HttpEntity<String> getEntity(Message message) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+ TOKEN);

        String json = objectMapper.writeValueAsString(message);

        return new HttpEntity<String>(json,headers);
    }

    private HttpEntity<String> getEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+ TOKEN);

        return new HttpEntity<String>(headers);
    }
}
