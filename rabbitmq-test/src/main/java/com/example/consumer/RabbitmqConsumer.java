package com.example.consumer;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "demo_queue")
public class RabbitmqConsumer {
  
  @RabbitHandler
  public void process(Map<?, ?> message){
    System.out.println("TopicTotalReceiver消费者收到消息  : " + message.toString());
  }
}
