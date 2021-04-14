package com.example.dummyTwo.controller;

import com.example.dummyTwo.queuepackage.QueueAdapter;
import com.example.dummyTwo.queuepackage.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {

    @Autowired
    QueueAdapter queueAdapter;

    @PostMapping("message")
    public void sendMessage(@RequestParam String payload, @RequestParam String topic) {
        queueAdapter.sendMessage(new Message(payload, topic));
    }

    @PostMapping("topic")
    public void subscribe(@RequestParam String topic) {
        queueAdapter.subscribe(topic);
    }
}
