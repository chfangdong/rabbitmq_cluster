package com.example.consumer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
@RabbitListener(queues = "ak_queue")
public class TopicReceiver implements ChannelAwareMessageListener {

  @Override
  public void onMessage(Message message, Channel channel) throws Exception {
    long deliveryTag = message.getMessageProperties().getDeliveryTag();
    try {
      String msg = message.toString();
      String[] msgArray = msg.split("'");
      Map<String, String> msgMap = mapStringToMap(msgArray[1].trim());
      String messageId = msgMap.get("messageId");
      String messageData = msgMap.get("messageData");
      String createTime = msgMap.get("createTime");
      System.out.println(
          "messageId:" + messageId + "  messageData:" + messageData + "  createTime:" + createTime);
      channel.basicAck(deliveryTag, true);
    } catch (Exception e) {
      channel.basicReject(deliveryTag, true);//为true会重新放回队列
      e.printStackTrace();
    }
  }

  private Map<String, String> mapStringToMap(String str) {
    str = str.substring(1, str.length() - 1);
    String[] strs = str.split(",");
    Map<String, String> map = new HashMap<>();
    for (String string : strs) {
      String key = string.split("=")[0].trim();
      String value = string.split("=")[1];
      map.put(key, value);
    }
    return map;
  }
}
