package com.example.dummyTwo.queuepackage.listener;

import com.example.dummyTwo.queuepackage.config.QueueConfig;
import com.example.dummyTwo.queuepackage.dto.Message;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface QueueHttpListener {

    @PostMapping(QueueConfig.THIS_LISTENER_PATH)
    public ResponseEntity<String> receiver(@RequestHeader HttpHeaders headers, @RequestBody Message message);
}
