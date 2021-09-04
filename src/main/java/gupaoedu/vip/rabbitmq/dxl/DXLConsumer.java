package gupaoedu.vip.rabbitmq.dxl;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 学习目标：死信队列的消费者
 * 1.什么是死信，死信队列，死信交换机
 * 2.消息什么时候变成死信
 * 3.死信队列如何使用
 * 2.如何处理rabbitMQ的死信
 *
 *
 */

public class DXLConsumer {
    public static void main(String[] args) throws Exception, URISyntaxException {
        //连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@127.0.0.1:5672");
        Connection conn =factory.newConnection();
        // 创建通道
        Channel channel = conn.createChannel();
        //指定队列的死信交换机
        Map<String,Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "DLX_EXCHANGE");
        arguments.put("x-epires", "9000");//设置队列的TTL
        arguments.put("x-max-length", 4);//如果设置队列最大长度，超过长度时，先入队的消息会被发送到DLX
        //声明队列（默认交换机AMQP default , Direct）
        // TEST_DLX_QUEUE 只是普通的队列，要测试它转到死信队列中
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare("TEST_DLX_QUEUE", false, false, false, arguments);
        // 声明死信交换机
        channel.exchangeDeclare("DLX_EXCHANGE", "topic",false , false, false,null );

        // 声明死信队列
        channel.queueDeclare("DLX_QUEUE", false, false, false, null);

        // 绑定，此处 Dead  letter routing key 设置为 #
        channel.queueBind("DLX_QUEUE","DLX_EXCHANGE","#");
        System.out.println(" Waiting for message....");
        // 创建消费者
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"UTF-8");
                System.out.println("Received message : '" + msg + "'");
            }
        };

        // 开始获取消息
        // String queue, boolean autoAck, Consumer callback
        // channel.basicConsume("TEST_DLX_QUEUE", true, consumer);

    }
}
