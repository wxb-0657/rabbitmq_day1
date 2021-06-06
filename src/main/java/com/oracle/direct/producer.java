package com.oracle.direct;

import com.oracle.utils.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class producer {

    private static final String EXCHANGE_NAME = "direct-exchange-test";
    private static final String QUEUE_NAME = "direct-queue-test";
    public static void main(String[] args) throws Exception {
        Connection conn = RabbitMqUtil.getConn();
        Channel channel = conn.createChannel();
        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        //声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //绑定队列
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"wxb");

        for (int i=1;i<=100;i++){
            //消息
            String message = "这是direct方式发送的消息" + i;
            //发送消息
            channel.basicPublish(EXCHANGE_NAME,"wxb",null,message.getBytes());
        }

        System.out.println("发送完毕");

        RabbitMqUtil.closeConnAndChannel(channel,conn);

    }
}
