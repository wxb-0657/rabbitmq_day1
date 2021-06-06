package com.oracle.fanout;

import com.oracle.utils.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class provider {
    public static void main(String[] args) throws IOException {
        Connection conn = RabbitMqUtil.getConn();
        Channel channel = conn.createChannel();
        //参数1：声明交换机的名称   参数2：交换机的类型
        channel.exchangeDeclare("logs","fanout");
        //发布消息给交换机
        //参数1：交换机名   参数2：路由key    参数4：消息内容
        channel.basicPublish("logs","",null,"hello fanout message!".getBytes());
    }
}
