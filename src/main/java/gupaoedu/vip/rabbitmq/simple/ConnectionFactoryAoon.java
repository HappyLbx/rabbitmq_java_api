package gupaoedu.vip.rabbitmq.simple;

import java.lang.annotation.*;

/**
 * 想写一个注解接口，实现rabbitMq连接，好像还没有找到合适的方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConnectionFactoryAoon {
    String value() default "";
}
