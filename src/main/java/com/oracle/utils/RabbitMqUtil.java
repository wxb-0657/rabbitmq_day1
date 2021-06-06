package com.oracle.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqUtil {
    private static ConnectionFactory connectionFactory;

    static{
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.25.142");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
    }

    public static Connection getConn(){
        try {
            return connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void closeConnAndChannel(Channel channel,Connection conn){
        try {
            if(channel!=null) channel.close();
            if (conn!=null) conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
