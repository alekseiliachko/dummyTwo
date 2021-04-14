package com.example.dummyTwo.queuepackage.excpetion;

public class QueueMessageBrokerException extends RuntimeException {
    public QueueMessageBrokerException() {
        super("queue message broker error");
    }
}
