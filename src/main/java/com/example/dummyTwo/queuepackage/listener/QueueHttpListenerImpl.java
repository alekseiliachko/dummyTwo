package com.example.dummyTwo.queuepackage.listener;

import com.example.dummyTwo.queuepackage.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class QueueHttpListenerImpl implements QueueHttpListener{

    @Override
    public ResponseEntity<String> receiver(HttpHeaders headers, Message message) {
        log.info("RECEIVED: " + message);
        return ResponseEntity.ok().build();
    }
}
