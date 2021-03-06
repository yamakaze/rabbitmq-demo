package com.jean.river.rabbitmqdemo.tut1;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @Author: JeanRiver
 * @Description:
 * @Date Created at 9:21 2021/5/10
 * @Modified By:
 */
@Profile({"tut1","hello-world"})
@Configuration
public class Tut1Config {

    //定义一个bean返回一个name为hello的Queue对象
    @Bean
    public Queue hello(){
        return new Queue("hello");
    }

    @Profile("receiver")
    @Bean
    public Tut1Receiver receiver() {
        return new Tut1Receiver();
    }

    @Profile("sender")
    @Bean
    public Tut1Sender sender() {
        return new Tut1Sender();
    }
}
