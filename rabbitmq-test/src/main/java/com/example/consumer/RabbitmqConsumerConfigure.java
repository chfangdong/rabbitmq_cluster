package com.example.consumer;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitmqConsumerConfigure {
  @Autowired
  private CachingConnectionFactory cachingConnectionFactory;
  
  @Autowired
  private TopicReceiver topicReceiver;
  
  @Bean
  public SimpleMessageListenerContainer ListeneSimpleMessageContainer (){
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cachingConnectionFactory);
    container.setConcurrentConsumers(1);
    container.setMaxConcurrentConsumers(1);
    container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
    container.setQueueNames("ak_queue");
    container.setMessageListener(topicReceiver);
    return container;
  }
  
}
