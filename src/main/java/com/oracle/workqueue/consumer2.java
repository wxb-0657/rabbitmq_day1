package com.oracle.workqueue;

import com.oracle.utils.RabbitMqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class consumer2 {
    public static void main(String[] args) {
        try {
            Connection conn = RabbitMqUtil.getConn();
            final Channel channel = conn.createChannel();
            channel.queueDeclare("work",true,false,false,null);
            channel.basicQos(1);
            channel.basicConsume("work",false,new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("consumer2=======>" + new String(body));
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
