package gupaoedu.vip.rabbitmq.dxl;


import com.rabbitmq.client.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 延时交换机的消费者
 *  使用延时插件实现的消息投递-消费者
 *  必须要在服务端安装rabbitmq-delayed-message-exchange插件，安装步骤见README.MD
 *  先启动消费者
 */
public class DelayPluginConsumer {
    public static void main(String[] args) throws Exception {
        // 连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@127.0.0.1:5672");
        Connection conn = factory.newConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();
        // 声明x-delayed-message类型的exchange
        Map<String, Object> argss = new HashMap<>();
        argss.put("x-delayed-type", "direct");
        channel.exchangeDeclare("DELAY_EXCHANGE", "x-delayed-message", false, false, false, argss);
        // 声明队列
        channel.queueDeclare("DELAY_QUEUE", false, false, false, null);
        // 同过DELAY_KEY绑定队列和交换机
        channel.queueBind("DELAY_QUEUE", "DELAY_EXCHANGE", "DELAY_KEY");
        // 创建一个默认的消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            // 重写handleDelivery的方法
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                //创建一个简单日期格式
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                System.out.println("延迟队列接受的信息：" + msg + "接受时间为：【" + sdf.format(new Date()) +"】");
            }
        };
        // 开始获取消息
        // String queue, boolean autoAck, Consumer callback
        System.out.println("开始接受消息....");
        channel.basicConsume("DELAY_QUEUE", true, consumer);

    }
}
