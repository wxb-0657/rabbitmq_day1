package com.oracle.workqueue;

import com.oracle.utils.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

public class provider {
    public static void main(String[] args) {
        Connection conn = null;
        Channel channel = null;
        try {
            conn = RabbitMqUtil.getConn();
            channel = conn.createChannel();
            channel.queueDeclare("work",true,false,false,null);
            for (int i=1;i<=20;i++){
                channel.basicPublish("","work",MessageProperties.PERSISTENT_TEXT_PLAIN,(i+"hello work queue!").getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            RabbitMqUtil.closeConnAndChannel(channel,conn);
        }
    }
}
