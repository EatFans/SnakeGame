package cn.fan.snake.engine;

import cn.fan.snake.engine.ansi.Terminal;

/**
 * 渲染类
 *
 * @author Fan
 */
public class Drawer {
    public Drawer(){

    }

    /**
     * 绘制内容，在绘制到该格结束重置终端样式
     * @param row 行
     * @param col 列
     * @param color 颜色
     * @param bgColor 背景颜色
     * @param content 内容
     */
    public void draw(int row, int col,String color,String bgColor, String content){
        Terminal.moveTo(row,col);
        System.out.print(color + bgColor + content);
        Terminal.reset();
    }

    /**
     * 绘制内容
     * @param row 行
     * @param col 列
     * @param content 内容
     */
    public void draw(int row, int col,String content){
        Terminal.moveTo(row,col);
        System.out.print(content);
        Terminal.reset();
    }

    /**
     * 安全绘制，保证不会破坏已经绘制好的内容
     * @param row 行
     * @param col 列
     * @param color 前景色
     * @param bgColor 背景色
     * @param content 内容
     */
    public void safeDraw(int row, int col, String color, String bgColor, String content) {
        Terminal.moveTo(row,col);
        System.out.print(color + bgColor + content);
    }

    /**
     * 绘制文本
     * @param row 行
     * @param col 列
     * @param color 颜色
     * @param text 文本
     */
    public void drawText(int row, int col, String color, String text){
        Terminal.moveTo(row,col);
        System.out.print(color + text);
        Terminal.reset();
    }
}
