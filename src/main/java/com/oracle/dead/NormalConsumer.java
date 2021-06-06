package com.oracle.dead;

import com.oracle.utils.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NormalConsumer {
    private static final String EXCHANGE_NAME="normal-exchange";
    private static final String QUEUE_NAME="normal-queue";
    public static void main(String[] args) throws Exception {
        Connection conn = RabbitMqUtil.getConn();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        Map<String,Object> map = new HashMap<>();
        map.put("x-message-ttl",10000);
        map.put("x-dead-letter-exchange","dead-exchange");
        map.put("x-dead-letter-routing-key","#");
        channel.queueDeclare(QUEUE_NAME,false,false,false,map);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"abc");

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        channel.basicConsume(QUEUE_NAME,true,queueingConsumer);
        QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
        System.out.println("处理正常的消息------>"+new String(delivery.getBody()));

        RabbitMqUtil.closeConnAndChannel(channel,conn);
    }
}
