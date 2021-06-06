package com.oracle.provider;

import com.oracle.utils.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider {

    @Test
    public void directTest() throws IOException, TimeoutException {
       /* //创建连接mq的连接工厂对象
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置rabbitmq的主机
        connectionFactory.setHost("192.168.25.141");
        //设置端口号
        connectionFactory.setPort(5672);
        //设置连接哪个虚拟主机   虚拟主机名前必须要带 /
        connectionFactory.setVirtualHost("/ems");
        //设置用户名和密码
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");

        //获取连接对象
        Connection connection = connectionFactory.newConnection();*/

        Connection connection = RabbitMqUtil.getConn();

        //获取连接中的通道
        Channel channel = connection.createChannel();
        //通道绑定对应消息队列
        //参数1：queue 队列名称，如果队列不存在则创建队列
        //参数2：durable 定义队列特性，是否要持久化（若持久化在关闭后会写入磁盘中）
        //参数3：exclusive 是否独占队列
        //参数4：autoDelete  在消费完成后是否自动删除队列   为true时是在消费者彻底与队列断开连接后，删除队列
        //参数5：额外附加参数
        channel.queueDeclare("hello",false,false,false,null);

        //发布消息
        //参数1：交换机名称
        //参数2：队列名称
        //参数3：传递消息额外设置(可以设置队列中消息持久化)
        //参数4：消息内容，需要转为字节数组
        channel.basicPublish("","hello",MessageProperties.PERSISTENT_TEXT_PLAIN,"hello rabbitmq!".getBytes());

        //关闭通道和连接
        /*channel.close();
        connection.close();*/
        RabbitMqUtil.closeConnAndChannel(channel,connection);
    }
}
