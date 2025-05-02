package cn.fan.snake.entity;

import cn.fan.snake.Direction;
import cn.fan.snake.Position;
import cn.fan.snake.engine.ansi.BackColor;
import cn.fan.snake.engine.ansi.ForeColor;

import java.util.ArrayList;
import java.util.List;

/**
 * 蛇实体类
 *
 * @author Fan
 */
public class Snake extends Entity {
    private final int DEFAULT_LENGTH = 3;
    private int length;
    private Direction direction;
    private List<Position> body = new ArrayList<>(); // 这里面的第一个坐标就是头的位置
    private Position oldTail; // 记录旧尾巴位置

    public Snake(){
        this.length = DEFAULT_LENGTH;
        this.direction = Direction.RIGHT;
        this.oldTail = null;
    }

    /**
     * 初始化蛇，
     * @param x 游戏二维坐标的x位置
     * @param y 游戏二维坐标的y位置
     */
    public void init(int x, int y){
        // 清除body之前的数据
        body.clear();

        // 添加蛇头位置数据
        Position head = new Position(x, y);
        body.add(head);

        // 根据默认的方向去添加蛇身数据
        for (int i = 1; i < length; i++){
            switch (direction){
                case UP -> {
                    body.add(new Position(x, y + i));
                }
                case DOWN -> {
                    body.add(new Position(x, y - i));
                }
                case LEFT -> {
                    body.add(new Position(x + i, y));
                }
                case RIGHT -> {
                    body.add(new Position(x - i, y));
                }
            }
        }

    }

    /**
     * 渲染蛇
     */
    public void draw(){
        checkDrawer();

        // 清除旧的尾巴（如果存在）
        if (oldTail != null){
            drawer.draw(oldTail.getRow(),oldTail.getCol(),"　");
            oldTail = null;
        }

        if (body != null){
            if (!body.isEmpty()){
                // 渲染蛇头
                Position head = body.get(0);
                drawer.draw(head.getRow(),head.getCol(), ForeColor.LIGHT_GREEN, BackColor.LIGHT_GREEN,"　");

                // 渲染蛇身
                for (int i = 1; i < body.size(); i++){
                    Position segment = body.get(i);
                    drawer.draw(segment.getRow(),segment.getCol(),ForeColor.GREEN,BackColor.GREEN,"　");
                }
            }

        }
    }

    /**
     * 移动，根据方向去移动
     */
    public void move(){
        // 移动前记录尾巴位置
        oldTail = getTail();

        // 获取当前蛇头位置
        Position head = getHead();
        Position newHead = null;
        switch (direction){
            case UP -> {
                newHead = new Position(head.getX(),head.getY() - 1);
            }
            case DOWN -> {
                newHead = new Position(head.getX(),head.getY() + 1);
            }
            case LEFT -> {
                newHead = new Position(head.getX() - 1, head.getY());
            }
            case RIGHT -> {
                newHead = new Position(head.getX() + 1, head.getY());
            }
        }
        setNewHead(newHead);
    }

    /**
     * 设置方向
     * @param direction 方向
     */
    public void setDirection(Direction direction){
        this.direction = direction;
    }

    /**
     * 获取蛇身体节点的数据
     * @return 蛇身体节点的数据
     */
    public List<Position> getBody(){
        return body;
    }

    /**
     * 获取蛇头
     * @return 蛇头坐标信息
     */
    public Position getHead(){
        return body.get(0);
    }


    /**
     * 设置新的蛇头位置，保持蛇长度不变
     * @param head 新蛇头位置
     */
    public void setNewHead(Position head){
        body.add(0,head);
        if (body.size() > length){
            body.remove(body.size() - 1);
        }
    }

    /**
     * 获取蛇尾
     * @return 蛇尾坐标信息
     */
    public Position getTail(){
        if (body.isEmpty()){
            return null;
        }

        return body.get(body.size() - 1);
    }

    /**
     * 获取蛇的长度
     * @return 长度
     */
    public int getLength(){
        return length;
    }

    /**
     * 检查蛇是否撞到自己
     * @return 如果撞到自己就返回true，否则就返回false
     */
    public boolean checkCollisionWhiSelf(){
        Position head = getHead();

        // 从第二节点开始检查（跳过头部）
        for (int i = 1; i < body.size(); i++){
            Position segment = body.get(i);
            if (head.getX() == segment.getX() && head.getY() == segment.getY())
                return true;
        }
        return false;
    }

    /**
     * 获取蛇的方向
     * @return 方向
     */
    public Direction getDirection(){
        return direction;
    }
}
