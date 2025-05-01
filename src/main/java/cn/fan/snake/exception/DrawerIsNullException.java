package cn.fan.snake.exception;

/**
 * 渲染器为空异常
 */
public class DrawerIsNullException extends RuntimeException{
    public DrawerIsNullException(){
        super("渲染器为null");
    }
}
