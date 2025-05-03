package cn.fan.snake.engine;

/**
 * 坐标类，来标识二维坐标
 *
 * @author Fan
 */
public class Position {
    private int x;
    private int y;
    public Position(int x,int y){
        this.x = x;
        this.y = y;
    }
    public void setX(int x){
        this.x = x;
    }

    /**
     * 获取列，这里是将x位置转换为列位置
     * @return 列位置
     */
    public int getCol(){
        return x * 2 - 1;
    }

    public void setY(int y){
        this.y = y;
    }

    /**
     * 获取行，这里是将坐标系的y转换成实际行
     * @return 行位置
     */
    public int getRow(){
        return y;
    }

    /**
     * 获取游戏二维x位置
     * @return x位置
     */
    public int getX() {
        return x;
    }

    /**
     * 获取游戏二维y位置
     * @return y位置
     */
    public int getY() {
        return y;
    }
}
