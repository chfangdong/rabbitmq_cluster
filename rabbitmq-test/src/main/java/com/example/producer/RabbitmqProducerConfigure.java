package com.example.producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqProducerConfigure {
  public static final String NORMAL_EXCHANGE = "test";
  public static final String NO_EXIST_EXCHANGE = "no_exist_test";
  public static final String ALONE_EXCHANGE = "aloneExchange";
  public static final String NORMAL_ROUTE_KEY = "demo.topic";

  // @Autowired
  // private RabbitProperties properties;

  @Bean
  public TopicExchange createExchange() {
    return new TopicExchange(NORMAL_EXCHANGE);
  }

  @Bean
  public TopicExchange createAloneExchange() {
    return new TopicExchange(ALONE_EXCHANGE);
  }

  @Bean
  public Queue createQueue() {
    return new Queue("demo_queue");
  }

  @Bean
  public Queue createAckQueue() {
    return new Queue("ak_queue");
  }

  @Bean
  public Binding bindExchangeQueue() {
    return BindingBuilder.bind(createQueue()).to(createExchange()).with("demo.*");
  }

  @Bean
  public Binding bindExchangeAckQueue() {
    return BindingBuilder.bind(createAckQueue()).to(createExchange()).with("demo.*");
  }

  @Bean
  public RabbitTemplate createRabbitmqTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate();
    rabbitTemplate.setConnectionFactory(connectionFactory);
    rabbitTemplate.setMandatory(true);

    rabbitTemplate.setConfirmCallback(new ConfirmCallback() {
      @Override
      public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("ConfirmCallback:     " + "相关数据：" + correlationData);
        System.out.println("ConfirmCallback:     " + "确认情况：" + ack);
        System.out.println("ConfirmCallback:     " + "原因：" + cause);
      }
    });

    rabbitTemplate.setReturnCallback(new ReturnCallback() {
      @Override
      public void returnedMessage(Message message, int replyCode, String replyText, String exchange,
          String routingKey) {
        System.out.println("ReturnCallback:     " + "消息：" + message);
        System.out.println("ReturnCallback:     " + "回应码：" + replyCode);
        System.out.println("ReturnCallback:     " + "回应信息：" + replyText);
        System.out.println("ReturnCallback:     " + "交换机：" + exchange);
        System.out.println("ReturnCallback:     " + "路由键：" + routingKey);
      }
    });

    return rabbitTemplate;
  }

}
