package cn.fan.snake.entity;

import cn.fan.snake.Position;
import cn.fan.snake.engine.ansi.BackColor;
import cn.fan.snake.engine.ansi.ForeColor;

/**
 * 食物实体类
 *
 * @author Fan
 */
public class Food extends Entity{
    private Position position;

    public Food(){
        position = new Position(5,10);
    }

    /**
     * 绘制食物
     */
    public void draw(){
        checkDrawer();
        drawer.draw(position.getRow(),position.getCol(), ForeColor.RED, BackColor.RED, "　");
    }

    /**
     * 获取食物的位置
     * @return 食物位置
     */
    public Position getPosition(){
        return position;
    }

    /**
     * 设置食物的位置
     * @param x x位置
     * @param y y位置
     */
    public void setPosition(int x, int y){
        position = new Position(x, y);
    }

    /**
     * 设置食物位置
     * @param position 位置
     */
    public void setPosition(Position position){
        this.position = position;
    }

    /**
     * 检查食物是否在指定位置
     * @param x 要检查的x坐标
     * @param y 要检查的y坐标
     * @return 如果食物在指定位置返回true，否则返回false
     */
    public boolean isAt(int x, int y){
        return position.getX() == x && position.getY() == y;
    }

    /**
     * 检查食物是否在指定位置
     * @param position 要检查的位置
     * @return 如果食物在指定位置返回true，否则返回false
     */
    public boolean isAt(Position position){
        return this.position.equals(position);
    }

}
