package gupaoedu.vip.rabbitmq.ttl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import gupaoedu.vip.rabbitmq.util.ResourceUtil;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class TTLProducer {
    public static void main(String[] args) throws Exception, URISyntaxException {
        //连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@127.0.0.1:5672");
        Connection conn =factory.newConnection();
        // 创建通道
        Channel channel = conn.createChannel();
        String msg = "你好，rabbitMQ，DLX_MSG";
        // 通过声明队列属性设置消息过期时间
        Map<String,Object> argss = new HashMap<>();
        argss.put("x-message-ttl", 6000);
        //声明队列
        channel.queueDeclare("TEST_TTL_QUEUE", false, false, false, argss);

        // 对每条信息消息设置过期时间
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2) //持久化消息
                .contentEncoding("UTF-8")
                .expiration("1000") // TTL
                .build();

        // 此处两种方式设置消息的过期时间的方式都使用了，将以较小的数值为准
        //发送消息
        channel.basicPublish("", "TEST_TTL_QUEUE", properties, msg.getBytes());
        channel.close();
        conn.close();

    }
}

