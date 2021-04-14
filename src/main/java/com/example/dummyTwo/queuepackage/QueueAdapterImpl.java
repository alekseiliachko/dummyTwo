package com.example.dummyTwo.queuepackage;

import com.example.dummyTwo.queuepackage.config.QueueConfig;
import com.example.dummyTwo.queuepackage.dto.InitRequest;
import com.example.dummyTwo.queuepackage.excpetion.QueueMessageBrokerException;
import com.example.dummyTwo.queuepackage.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Component
public class QueueAdapterImpl implements QueueAdapter{

    private final RestTemplate restTemplate;

    // imagine value here
    static String REMOTE_NAME = "http://localhost:8002";

    // imagine value here
    static String REMOTE_PASS = "password";

    // imagine value here
    static String REMOTE_KEY = "secretKey";

    static String TOKEN = null;

    private static URI URI_REG;
    private static URI URI_SUB;
    private static URI URI_MES;

    static {
        try {
            URI_REG = new URI(QueueConfig.QUEUE_BASE_URL_PATH + QueueConfig.QUEUE_REGISTER_PATH);
            URI_MES = new URI(QueueConfig.QUEUE_BASE_URL_PATH + QueueConfig.QUEUE_MESSAGE_PATH);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public QueueAdapterImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.registerWithKey(REMOTE_KEY);
    };

    @Override
    public void registerWithKey(String key) {
        try {
            InitRequest initRequest = new InitRequest();
            initRequest.setRemoteName(REMOTE_NAME);
            initRequest.setRemotePass(REMOTE_PASS);
            initRequest.setRemoteKey(REMOTE_KEY);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(URI_REG, initRequest, String.class);

            if (!HttpStatus.OK.equals(responseEntity.getStatusCode()))
                throw new QueueMessageBrokerException();

            TOKEN = responseEntity.getBody();

            log.info("logged in with and assigned token: " + TOKEN);
        } catch (Exception e) {
            log.error("FAILED TO INSTANTIATE QUEUE ADAPTER");
//            throw new QueueMessageBrokerException();
        }
    }

    @Override
    public void subscribe(String topic) {
        try {
            URI_SUB = new URI(QueueConfig.QUEUE_BASE_URL_PATH + QueueConfig.QUEUE_SUBSCRIPTION_PATH + topic + "/");
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(URI_REG, new Object(), String.class);
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
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(URI_MES, message, String.class);
            if (!HttpStatus.OK.equals(responseEntity.getStatusCode()))
                throw new QueueMessageBrokerException();

            log.info("send: " + message);
        } catch (Exception e) {
            throw new QueueMessageBrokerException();
        }
    }
}
