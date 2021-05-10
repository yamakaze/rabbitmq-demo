package com.jean.river.rabbitmqdemo.tut3;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @Author: JeanRiver
 * @Description: Publish/Subscribe
 * Sending messages to many consumers at once
 * 消息广播给所有消费者 Essentially, published messages are going to be broadcast to all the receivers.
 * @Date Created at 14:41 2021/5/10
 * @Modified By:
 */
@Profile({"tut3", "pub-sub", "publish-subscribe"})
@Configuration
public class Tut3Config {

    /**
     * fanout exchange
     * used for fanning out our message
     * @return
     */
    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("tut.fanout");
    }

    @Profile("receiver")
    private static class ReceiverConfig {

        /**
         * 并没有给队列命名
         * ①当消费者连接上rabbitMQ时，获取一个空的新队列
         * ②当消费者与MQ断开连接时，删除队列
         * @return
         */
        @Bean
        public Queue autoDeleteQueue1() {
            //AnonymousQueue, a non-durable, exclusive, auto-delete queue with a generated name:
            //queue name for example: spring.gen-1Rx9HOqvTAaHeeZrQWu8Pg
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }

        //将队列绑定至exchange "fanout"
        //如果没有队列绑定至exchange,消息会被丢弃 discard
        @Bean
        public Binding binding1(FanoutExchange fanout,
                                Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(fanout);
        }

        @Bean
        public Binding binding2(FanoutExchange fanout,
                                Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2).to(fanout);
        }

        @Bean
        public Tut3Receiver receiver() {
            return new Tut3Receiver();
        }
    }

    @Profile("sender")
    @Bean
    public Tut3Sender sender() {
        return new Tut3Sender();
    }
}
