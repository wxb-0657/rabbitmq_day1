package com.oracle.dead;

import com.oracle.utils.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class producer {
    private static final String EXCHANGE_NAME="normal-exchange";
    private static final String QUEUE_NAME="normal-queue";
    public static void main(String[] args) throws IOException {
        Connection conn = RabbitMqUtil.getConn();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        Map<String,Object> map = new HashMap<>();
        map.put("x-message-ttl",10000);
        map.put("x-dead-letter-exchange","dead-exchange");
        map.put("x-dead-letter-routing-key","#");
        channel.queueDeclare(QUEUE_NAME,false,false,false,map);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"abc");
        channel.basicPublish(EXCHANGE_NAME,"abc",null,"提供者提供一个正常的消息".getBytes());

        RabbitMqUtil.closeConnAndChannel(channel,conn);
    }
}
