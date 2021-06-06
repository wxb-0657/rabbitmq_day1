package com.oracle.dead;

import com.oracle.utils.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

public class DeadConsumer {

    private static final String EXCHANGE_NAME="dead-exchange";
    private static final String QUEUE_NAME="dead-queue";

    public static void main(String[] args) throws Exception {
        Connection conn = RabbitMqUtil.getConn();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"#");

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        channel.basicConsume(QUEUE_NAME,true,queueingConsumer);
        QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
        System.out.println("死信队列处理正常的消息------>"+new String(delivery.getBody()));

        RabbitMqUtil.closeConnAndChannel(channel,conn);

    }
}
