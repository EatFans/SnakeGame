package cn.fan.snake.engine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 按键监听处理器注解，用于在Listener类中的处理按键的方法上
 *
 * @author Fan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface KeyHandler {
    char key();
}
