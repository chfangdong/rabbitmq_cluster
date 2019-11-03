package com.example.producer;

import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqProducer {
  @Autowired
  protected RabbitTemplate rabbitTemplate;
  
  public void sendMessage(String exchanage, String routeKey, Map<?, ?> message){
    rabbitTemplate.convertAndSend(exchanage, routeKey, message);
  }
}
