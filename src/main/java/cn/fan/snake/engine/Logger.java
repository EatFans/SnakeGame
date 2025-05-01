package cn.fan.snake.engine;

import cn.fan.snake.engine.ansi.ForeColor;
import cn.fan.snake.engine.ansi.Terminal;

/**
 * 游戏日志工具类
 *
 * @author Fan
 */
public class Logger {
    private final static int row = 3;
    private final static int col = 65;

    /**
     * 打印正常消息
     * @param message 消息
     */
    public static void info(String message){
        Terminal.moveTo(row,col);
        System.out.print(message);
        Terminal.reset();
    }

    /**
     * 打印警告消息
     * @param message 消息
     */
    public static void warn(String message){
        Terminal.moveTo(row,col);
        System.out.print(ForeColor.YELLOW+message);
        Terminal.reset();
    }

    /**
     * 打印错误消息
     * @param message 消息
     */
    public static void error(String message){
        Terminal.moveTo(row,col);
        System.out.print(ForeColor.RED+message);
        Terminal.cleanScreen();
    }
}
