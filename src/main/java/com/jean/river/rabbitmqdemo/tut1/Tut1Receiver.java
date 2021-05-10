package com.jean.river.rabbitmqdemo.tut1;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @Author: JeanRiver
 * @Description: 指定一个名为hello的队列，从中获取信息
 * @Date Created at 10:20 2021/5/10
 * @Modified By:
 */
@RabbitListener(queues = "hello")
public class Tut1Receiver {

    @RabbitHandler
    public void receive(String in) {
        System.out.println(" [x] Received '" + in + "'");
    }
}
