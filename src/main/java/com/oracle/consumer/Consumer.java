package com.oracle.consumer;

import com.oracle.utils.RabbitMqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMqUtil.getConn();
        Channel channel = connection.createChannel();
        //消费者绑定通道时需要和提供者保持一致
        channel.queueDeclare("hello",false,false,false,null);

        //消费消息的方法
        //参数1：消费哪个队列的消息   -----》队列名称
        //参数2：开始消息的自动确认机制
        //参数3：消费时的回调接口(Consumer)  可以使用内部类的形式，直接给定回调后执行的函数
        channel.basicConsume("hello",true,new DefaultConsumer(channel){
            //body是消息的内容
            //handleDelivery类似于回调函数
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        });
    }
}
