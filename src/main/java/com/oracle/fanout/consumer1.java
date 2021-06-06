package com.oracle.fanout;

import com.oracle.utils.RabbitMqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class consumer1 {
    public static void main(String[] args) throws IOException {
        Connection conn = RabbitMqUtil.getConn();
        final Channel channel = conn.createChannel();
        //声明一个交换机   需要和提供者保持一致
        channel.exchangeDeclare("logs","fanout");
        //创建临时队列   获取临时队列名
        String queue = channel.queueDeclare().getQueue();
        //将临时队列绑定到交换机上
        channel.queueBind(queue,"logs","");
        //处理消息
        channel.basicConsume(queue,false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1："+new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });

    }
}
