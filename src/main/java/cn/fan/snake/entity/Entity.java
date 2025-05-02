package cn.fan.snake.entity;

import cn.fan.snake.engine.Drawer;
import cn.fan.snake.exception.DrawerIsNullException;

/**
 * 实体类
 *
 * @author Fan
 */
public class Entity {
    protected Drawer drawer;

    public void setDrawer(Drawer drawer){
        this.drawer = drawer;
    }

    public void checkDrawer(){
        if (drawer == null)
            throw new DrawerIsNullException();
    }
}
