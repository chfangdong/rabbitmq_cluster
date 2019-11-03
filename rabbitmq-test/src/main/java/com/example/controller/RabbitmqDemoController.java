package com.example.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.producer.RabbitmqProducer;
import com.example.producer.RabbitmqProducerConfigure;

@RestController
@RequestMapping("/demo")
public class RabbitmqDemoController {

  @Autowired
  RabbitmqProducer rabbitmqProducer;
  
  @PostMapping("/test")
  public String sendMessageToRabbitmq(){
    String messageId = String.valueOf(UUID.randomUUID());
    String messageData = "test message, hello!";
    String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Map<String,Object> map=new HashMap<>();
    map.put("messageId",messageId);
    map.put("messageData",messageData);
    map.put("createTime", createTime);
    System.out.println("send message: " + map);
    rabbitmqProducer.sendMessage(RabbitmqProducerConfigure.NORMAL_EXCHANGE, RabbitmqProducerConfigure.NORMAL_ROUTE_KEY, map);
    return "OK";
  }
  
  @PostMapping("/noExchange")
  public String sendMessageToRabbitmqNoExchange(){
    String messageId = String.valueOf(UUID.randomUUID());
    String messageData = "test message hello!";
    String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Map<String,Object> map=new HashMap<>();
    map.put("messageId",messageId);
    map.put("messageData",messageData);
    map.put("createTime", createTime);
    System.out.println("send message: " + map);
    rabbitmqProducer.sendMessage(RabbitmqProducerConfigure.NO_EXIST_EXCHANGE, RabbitmqProducerConfigure.NORMAL_ROUTE_KEY, map);
    return "OK";
  }
  
  @PostMapping("/noQueue")
  public String sendMessageToRabbitmqNoQueue(){
    String messageId = String.valueOf(UUID.randomUUID());
    String messageData = "test message, hello!";
    String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Map<String,Object> map=new HashMap<>();
    map.put("messageId",messageId);
    map.put("messageData",messageData);
    map.put("createTime", createTime);
    System.out.println("send message: " + map);
    rabbitmqProducer.sendMessage(RabbitmqProducerConfigure.ALONE_EXCHANGE, RabbitmqProducerConfigure.NORMAL_ROUTE_KEY, map);
    return "OK";
  }
  
}
