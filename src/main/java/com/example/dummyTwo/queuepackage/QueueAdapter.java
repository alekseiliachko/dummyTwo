package com.example.dummyTwo.queuepackage;

import com.example.dummyTwo.queuepackage.dto.Message;
import org.springframework.stereotype.Component;

public interface QueueAdapter {

    /**
     * vars
     */
    static String REMOTE_NAME = null;

    static String REMOTE_PASS = null;

    static String REMOTE_KEY = null;

    /**
     * This method registers instance of this app with secret key provided in running QUEUE instance
     *
     * @param key
     */
    public void registerWithKey(String key);

    /**
     * This method subscribes instance of this app to specified topics API
     * @param topic
     */
    public void subscribe(String topic);


    /**
     * This method subscribes instance of this app to specified topics API
     * @param message
     */
    public void sendMessage(Message message);
}
