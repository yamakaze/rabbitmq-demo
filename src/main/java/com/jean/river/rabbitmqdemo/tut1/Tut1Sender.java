package com.jean.river.rabbitmqdemo.tut1;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Author: JeanRiver
 * @Description:
 * @Date Created at 10:18 2021/5/10
 * @Modified By:
 */
public class Tut1Sender {

    @Autowired
    private RabbitTemplate template;

    //注入Queue
    @Autowired
    private Queue queue;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        String message = "Hello World";
        this.template.convertAndSend(queue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
