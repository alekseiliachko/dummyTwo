package com.example.dummyTwo.queuepackage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    String payload;
    String topic;
}
