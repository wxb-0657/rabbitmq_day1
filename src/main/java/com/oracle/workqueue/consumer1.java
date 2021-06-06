package com.oracle.workqueue;

import com.oracle.utils.RabbitMqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class consumer1 {
    public static void main(String[] args) {
        try {
            Connection conn = RabbitMqUtil.getConn();
            final Channel channel = conn.createChannel();
            channel.queueDeclare("work",true,false,false,null);
            //每次值消费一条消息
            channel.basicQos(1);
            channel.basicConsume("work",false,new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("consumer1=======>" + new String(body));
                    //参数2：是否开启多消息确认
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
