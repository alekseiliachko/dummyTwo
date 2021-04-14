package com.example.dummyTwo.queuepackage;

import com.example.dummyTwo.queuepackage.dto.Message;

public interface QueueAdapter {

    /**
     * This method registers instance of this app with secret key provided in running QUEUE instance
     *
     *
     */
    public void registerWithKey();

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
