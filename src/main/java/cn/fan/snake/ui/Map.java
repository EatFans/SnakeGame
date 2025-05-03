package cn.fan.snake.ui;

import cn.fan.snake.engine.ansi.BackColor;
import cn.fan.snake.engine.ansi.ForeColor;

public class Map extends UI {


    public Map(int row,int col){
        super(row,col);
    }


    /**
     * 绘制地图
     */
    @Override
    public void draw(){
        //检查渲染器， 如果drawer渲染器为空，抛出DrawerIsNull异常
        checkDrawer();

        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                // 判断是否在边框位置
                if (i == 1 || i == row || j == 1 || j == col) {
                    drawer.draw(i, j, ForeColor.WHITE, BackColor.WHITE, "　");
                }
            }
        }
//        Logger.info("x ： "+ col / 2);
//        Logger.info("y : " + row);
    }

}
