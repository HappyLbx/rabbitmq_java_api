package gupaoedu.vip.rabbitmq.simple;

import com.rabbitmq.client.*;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 * 1.为什么创建连接都是在消费者这边呢？
 * 因为遵循一个谁使用谁管理的原则
 * 2.
 */
@Async
public class MyComsumer {
    public final static String EXCHANG_NAME = "SIMPLE_EXCHANG";
    public final static String QUEUE_NAME = "SIMPLE_QUEUE";

    public static void main(String[] args) throws Exception {
//        //连接
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("127.0.0.1");
//        //监听端口
//        factory.setPort(5672);
//        //虚拟机
//        factory.setVirtualHost("/");
//        // 设置访问用户
//        factory.setUsername("guest");
//        factory.setPassword("guest");
//        // 建立连接
//        Connection conn = factory.newConnection();
//        // 创建消息通道
//        Channel channel = conn.createChannel();
//        System.out.println(factory.getHost()+factory.getVirtualHost() + factory.getUsername() + factory.getPassword());
//
//        // 声明交换机
//        //String exchange, String type ,boolean autoDelete, Map<String,Object> Arguments
//        channel.exchangeDeclare(EXCHANG_NAME, "direct", false,false,null);
//        System.out.println("Waiting for message....");
//
//        //创建队列
//        //String queue , boolean durable ,boolean exclusive ,boolean autoDelete ,Map<String,String> Arguments
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//
//        //绑定交换机和队列
//        channel.queueBind(QUEUE_NAME, EXCHANG_NAME, "gupao.best");
//
//        // 创建消费者
//        Consumer consumer = new DefaultConsumer(channel){
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//
//               String msg = new String(body,"UTF-8");
//                System.out.println("Eeceived message : '" + msg +"'");
//                System.out.println("consumerTag : " + consumerTag);
//                System.out.println("deliveryTag : " + envelope.getDeliveryTag());
//
//            }
//        };
//        // 开始获取消息
//        // String queue , boolean autoAck ,Consumer callback
//        channel.basicConsume(QUEUE_NAME, true,consumer);
//
//        // 消费者为什么不去释呢，因为它要持续地监听消息
//        //channel.close();
//        //conn.close();
        Thread thread = new Thread(){
            @Override
            public void run() {
                System.out.println("2");
            }
        };
        thread.sleep(1000);
        thread.run();
        System.out.println("1");
    }


}
class B extends MyComsumer{
    public String a="111";

}
class C extends B{
    public static void main(String[] args) {
        System.out.println(new C().EXCHANG_NAME);

    }
}
