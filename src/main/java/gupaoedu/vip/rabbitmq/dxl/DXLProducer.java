package gupaoedu.vip.rabbitmq.dxl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * 死信队列的生产者
 */
public class DXLProducer {
    public static void main(String[] args) throws Exception {
        //连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@127.0.0.1:5672");
        Connection conn = factory.newConnection();
        // 创建通道
        Channel channel = conn.createChannel();
        // 生产者不需要声明交换机，谁使用，谁声明
//        channel.exchangeDeclare("", "TEST_DLX_QUEUE",false ,false , false, null);

        System.out.println("开始发送消息....");
        String msg = "死信队列生产者发送消息了";
        //可以设置生产者的属性,如何设置生产者的属性呢?可以调用AMQP.BasicProperties内部类：Builder
        // 这里是可以设置消息的持久化和格式和ttl等属性，还有其他属性不常用可以参考：
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)  // 持久化？持久化是什么意思
                .contentEncoding("UTF-8") //编码格式
                .expiration("10000") // TTL
                .build();

        // 不指定交换机默认使用的AMQP这个交换机，绑定键直接就是填队列的名称，这时候，默认的交换机机会直接把消息发送到对列上去
        channel.basicPublish("", "TEST_DLX_QUEUE", properties, msg.getBytes());
        channel.close();
        conn.close();


    }
}
