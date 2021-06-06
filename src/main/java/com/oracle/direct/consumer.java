package com.oracle.direct;

import com.oracle.utils.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

public class consumer {
    private static final String EXCHANGE_NAME = "direct-exchange-test";
    private static final String QUEUE_NAME = "direct-queue-test";
    public static void main(String[] args) throws Exception {
        Connection conn = RabbitMqUtil.getConn();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"wxb");
        //消费消息
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME,true,queueingConsumer);
        QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
        byte[] body = delivery.getBody();
        System.out.println(new String(body));
        RabbitMqUtil.closeConnAndChannel(channel,conn);
    }
}
