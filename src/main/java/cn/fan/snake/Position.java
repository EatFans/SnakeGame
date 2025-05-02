package cn.fan.snake;

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
}
