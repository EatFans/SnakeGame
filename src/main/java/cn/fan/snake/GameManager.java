package cn.fan.snake;

import cn.fan.snake.engine.Drawer;
import cn.fan.snake.engine.Logger;
import cn.fan.snake.ui.Map;
import cn.fan.snake.engine.ansi.BackColor;
import cn.fan.snake.engine.ansi.ForeColor;
import cn.fan.snake.engine.ansi.Terminal;
import cn.fan.snake.ui.Menu;
import cn.fan.snake.ui.UI;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏管理类
 *
 * @author Fan
 */
public class GameManager {
    private final int ROW_COUNT = 10;
    // 地图每个格子的坐标
    private int row;
    private int col;
    private int score;  // 当前得分
    private int length; // 当前蛇的长度
    private boolean isRunning;

    private final Drawer drawer;
    private final List<UI> uis = new ArrayList<>();

    public GameManager(int row, int col){
        this.row = row;
        this.col = col;

        drawer = new Drawer();

        uis.add(new Map(this.row,this.col));
        uis.add(new Menu(this.row+ROW_COUNT,this.col,ROW_COUNT));
    }

    public void run(){
        // 游戏初始化
        init();

        // 游戏主循环
        while (isRunning){

            drawer.draw(8,10,ForeColor.GREEN, BackColor.GREEN, "　");
            render();

        }
        // 游戏结束
        if (!isRunning){

        }
    }
    /**
     * 游戏初始化
     */
    private void init(){
        this.score = 0;
        this.length = 2;
        this.isRunning = true;

        initUI();

        // 初始化食物的位置

        // 初始化蛇的位置

    }

    /**
     * 初始化UI
     */
    private void initUI(){
        for (UI ui : uis){
            ui.setDrawer(this.drawer);
        }

        Map map = null;
        Menu menu = null;
        for (UI ui : uis){
            if (ui instanceof Map)
                map =(Map) ui;
            if (ui instanceof Menu)
                menu = (Menu) ui;
        }

        Terminal.cleanScreen();
        Terminal.hideCursor();

        if (map == null)
            Logger.error("【错误】：地图UI初始化失败！");
        if (menu == null)
            Logger.error("【错误】：菜单UI初始化失败！");

        map.draw();
        menu.draw();
    }

    /**
     * 更新逻辑
     */
    public void update(){

    }

    /**
     * 处理输入
     */
    public void processInput(){

    }

    /**
     * 渲染逻辑
     */
    public void render(){
        // 得分、长度、fps数值动态渲染
        String score = String.format("%d",this.score);
        drawer.drawText(row+3,25*2,ForeColor.WHITE, score);
    }
}
