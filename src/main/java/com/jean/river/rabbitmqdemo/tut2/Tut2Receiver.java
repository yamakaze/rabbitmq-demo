package com.jean.river.rabbitmqdemo.tut2;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/**
 * @Author: JeanRiver
 * @Description: autoAck = true 自动确认 或者 手动确认
 * 自动确认会在消息发送给消费者后立即确认，但存在丢失消息的可能，如果消费端消费逻辑抛出异常，也就是消费端没有处理成功这条消息，那么就相当于丢失了消息
 * spring amqp 会调用channel.basicReject(deliveryTag, requeue) 帮助重新入队
 * 当抛出的异常是AmqpRejectAndDontRequeueException异常的时候，则消息会被拒绝，且requeue=false
 * 消费者显示调用channel.basicAck()通知rabbitMQ消息已成功消费，mq将删除此消息
 * 否则MQ将重新将消息重新入队
 * <p>
 * 先将消息进行应答，此时消息队列会删除该条消息，同时我们再次发送该消息到消息队列，异常消息就放在了消息队列尾部，这样既保证消息不会丢失，又保证了正常业务的进行。
 * 但这种方法并没有解决根本问题，错误消息还是会时不时报错，后面优化设置了消息重试次数，达到了重试上限以后，手动确认，队列删除此消息，并将消息持久化入MySQL并推送报警，进行人工处理和定时任务做补偿。
 * <p>
 * RabbitMQ 不保证消息不重复，如果你的业务需要保证严格的不重复消息，需要你自己在业务端去重。
 * @Date Created at 11:07 2021/5/10
 * @Modified By:
 */
@RabbitListener(queues = "hello")
public class Tut2Receiver {

    private final int instance;

    public Tut2Receiver(int i) {
        this.instance = i;
    }

    @RabbitHandler
    public void receive(String in) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("instance " + this.instance +
                " [x] Received '" + in + "'");
        doWork(in);
        watch.stop();
        System.out.println("instance " + this.instance +
                " [x] Done in " + watch.getTotalTimeSeconds() + "s");
    }

    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
