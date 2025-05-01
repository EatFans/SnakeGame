package cn.fan.snake.ui;

import cn.fan.snake.engine.ansi.BackColor;
import cn.fan.snake.engine.ansi.ForeColor;

/**
 * 菜单UI类
 *
 * @author Fan
 */
public class Menu extends UI{
    private int rowCount; // 菜单的行数高度数量

    public Menu(int row, int col, int rowCount){
        super(row,col);
        this.rowCount = rowCount;
    }

    /**
     * 渲染绘制菜单
     */
    @Override
    public void draw(){
        checkDrawer();
        // 菜单边框绘制
        int realRow = row - rowCount;
        for (int i = realRow; i <= row; i++){
            for(int j = 1; j <= col; j++){
                if (i == row || j == 1 || j == 40 || j == col){
                    drawer.draw(i,j, ForeColor.WHITE, BackColor.WHITE,"　");
                }
            }
        }
        // 菜单文本绘制
        drawer.drawText(realRow+2,3*2,ForeColor.WHITE,"使用w、s、a、d控制，1、2选择按钮");
        drawer.drawText(realRow+3,3*2,ForeColor.WHITE,"enter确定选择");
        drawer.drawText(realRow+3,22*2,ForeColor.YELLOW,"得分：");
        drawer.drawText(realRow+5,22*2,ForeColor.GREEN,"长度：");
        drawer.drawText(realRow+7,22*2,ForeColor.RED,"FPS：");
    }


}
