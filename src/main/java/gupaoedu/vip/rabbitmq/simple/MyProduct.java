package gupaoedu.vip.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import static gupaoedu.vip.rabbitmq.simple.MyComsumer.EXCHANG_NAME;


public class MyProduct {
    public static void main(String[] args) throws Exception {
        //连接
        ConnectionFactory factory = new ConnectionFactory();
        //连接IP
        factory.setHost("127.0.0.1");
        //连接端口
        factory.setPort(5672);
        //虚拟机
        factory.setVirtualHost("/");
        // 用户
        factory.setUsername("guest");
        factory.setPassword("guest");
        // 建立连接
        Connection conn = factory.newConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        // 发现消息
        String msg = "Hello World,Rabbit MQ";

        // String exchange , String routingKey , BasicProperises props , byte[] boby
        channel.basicPublish(EXCHANG_NAME, "gupao.best", null, msg.getBytes());

        channel.close();
        conn.close();
    }
}
