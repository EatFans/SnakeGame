package cn.fan.snake.ui;

import cn.fan.snake.engine.Drawer;
import cn.fan.snake.engine.ansi.BackColor;
import cn.fan.snake.engine.ansi.ForeColor;
import cn.fan.snake.exception.DrawerIsNullException;

/**
 * UI基础类
 *
 * @author Fan
 */
public class UI {
    protected int row; // 行
    protected int col; // 列

    protected Drawer drawer; // 渲染器

    public UI(int row, int col){
        this.row = row;
        this.col = col;
    }

    /**
     * 设置渲染器
     * @param drawer 渲染器
     */
    public void setDrawer(Drawer drawer){
        this.drawer = drawer;
    }

    /**
     * 检查渲染器是否正常
     */
    protected void checkDrawer(){
        if (drawer == null)
            throw new DrawerIsNullException();
    }

    /**
     * 绘制UI方框区域
     */
    public void draw(){
        checkDrawer();
        for (int i = 1; i <= row; i++){
            for (int j = 1; j <= col; j++){
                drawer.draw(i,j, ForeColor.WHITE, BackColor.WHITE,"　");
            }
        }
    }
}
