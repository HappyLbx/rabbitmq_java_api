package gupaoedu.vip.rabbitmq.dxl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  使用延时插件实现的消息投递-生产者
 *  必须要在服务端安装rabbitmq-delayed-message-exchange插件，安装步骤见README.MD
 *  先启动消费者
 */
public class DelayPluginProducer {
    public static void main(String[] args) throws Exception {
        // 连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@127.0.0.1:5672");
        Connection conn = factory.newConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        // 定义发送消息的信息
        //设置延时1分钟，原理是记录当前时间，在将当前时间加1分钟，最后拿加的延时时间减去记录的当前时间
        Date now = new Date();
        // 使用calendar 创建实例后 ，对现在的时间进行加一分钟
        Calendar calendar =  Calendar.getInstance();
        calendar.add(Calendar.MINUTE,+1 ); // 1分钟后投递
        Date delayTime = calendar.getTime();
        // 可以设置定时投递
        // 定时投递，把这个值替换delayTime即可???  每个月一号投递如何设置呢
        // Date exactDealyTime = new Date("2019/01/14,22:30:00");

        //设置消息的内容
        // 组装消息的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String msg = "发送时间为：【" + sdf.format(now) + "】,需要投递时间为：【" + sdf.format(delayTime) + "】";
        // 设置消息的属性
        Map<String,Object> headers = new HashMap<>();
        headers.put("x-delay", delayTime.getTime() - now .getTime()); // 作差，获取延时的毫秒值
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().headers(headers).build();
        // 发送消息
        channel.basicPublish("DELAY_EXCHANGE", "DELAY_KEY", properties, msg.getBytes());

        // 关闭
        channel.close();
        conn.close();


    }
}
