package cn.fan.snake;

import cn.fan.snake.engine.Drawer;
import cn.fan.snake.engine.Logger;
import cn.fan.snake.engine.ansi.BackColor;
import cn.fan.snake.entity.Snake;
import cn.fan.snake.ui.Button;
import cn.fan.snake.ui.Map;
import cn.fan.snake.engine.ansi.ForeColor;
import cn.fan.snake.engine.ansi.Terminal;
import cn.fan.snake.ui.Menu;
import cn.fan.snake.ui.UI;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

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
    private final int SNAKE_INIT_POSITION_X = 8;
    private final int SNAKE_INIT_POSITION_Y = 16;
    private int currentFPS; // 当前fps
    private boolean isRunning;
    private GameStatus gameStatus; // 游戏状态
    private Button startGameButton; // 开始游戏按钮
    private Button restartGameButton; // 开始游戏按钮
    private int currentSelectButton;
    private Snake snake; // 蛇

    private Drawer drawer;
    private final List<UI> uis = new ArrayList<>();

    // jline库中的输入控制的类
    private org.jline.terminal.Terminal terminal;
    private NonBlockingReader reader;

    public GameManager(int row, int col){
        this.row = row;
        this.col = col;


        // 渲染器对象
        drawer = new Drawer();
        // 创建UI对象
        uis.add(new Map(this.row,this.col));
        uis.add(new Menu(this.row+ROW_COUNT,this.col,ROW_COUNT));
        // 按钮创建对象
        startGameButton = new Button(this.row+6,3*2,"开始游戏");
        restartGameButton = new Button(this.row+6,13*2,"重新开始");
        // 创建蛇的对象
        snake = new Snake();
        try {
            // 初始化终端
            terminal = TerminalBuilder.builder()
                    .system(true)
                    .jansi(true)
                    .build();
            reader = terminal.reader();
            terminal.enterRawMode();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void run(){
        // 游戏初始化
        init();

        // 游戏主循环
        while (isRunning){

            update();
            processInput();
            render();

        }
        // 游戏结束
        if (!isRunning){

        }
        cleanup();
    }

    /**
     * 游戏初始化
     */
    private void init(){
        initData();
        initUI();
        // 初始化蛇的位置
        initSnake();
        // 初始化食物的位置
        initFood();
    }


    private void initData(){
        this.score = 0;
        this.length = snake.getLength();
        this.isRunning = true; // 游戏循环是否运行
        this.currentFPS = 0;
        this.gameStatus = GameStatus.MENU; // 初始化游戏状态
        // 默认选择开始游戏按钮
        currentSelectButton = 1;
        startGameButton.onSelect();
        // 禁用重新开始按钮
        restartGameButton.setEnable(false);
    }

    /**
     * 初始化UI
     */
    private void initUI(){
        for (UI ui : uis){
            ui.setDrawer(this.drawer);
        }
        startGameButton.setDrawer(this.drawer);
        restartGameButton.setDrawer(this.drawer);
        snake.setDrawer(this.drawer);

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

        startGameButton.draw();
        restartGameButton.draw();
    }

    /**
     * 初始化蛇
     */
    private void initSnake(){
        snake.init(SNAKE_INIT_POSITION_X,SNAKE_INIT_POSITION_Y);
        // 渲染蛇
        List<Position> body = snake.getBody();
        if (body != null && !body.isEmpty()) {
            // 渲染蛇头
            Position head = body.get(0);
            drawer.draw(head.getRow(), head.getCol(), ForeColor.LIGHT_GREEN, BackColor.LIGHT_GREEN, "　");

            // 渲染蛇身
            for (int i = 1; i < body.size(); i++) {
                Position segment = body.get(i);
                drawer.draw(segment.getRow(), segment.getCol(), ForeColor.GREEN, BackColor.GREEN, "　");
            }
        }

    }

    private void initFood(){

    }

    /**
     * 更新逻辑
     */
    public void update(){
        // 检查蛇是否撞到边界
        if (checkCollisionWithBoundary()){
            gameOver();
        }


        if (gameStatus == GameStatus.STARTING){
            snake.move();
        }

        // 检查蛇是否撞到自己
        if (snake.checkCollisionWhiSelf()){
            gameOver();
        }


    }


    /**
     * 处理输入
     */
    public void processInput(){
        try {
            int input = reader.read(1);
            if (input != -1){
                char c = (char) input;
                switch (Character.toLowerCase(c)){
                    case 'w':
                        drawer.drawText(row+6,25*2,ForeColor.WHITE, "W");
                        snake.setDirection(Direction.UP);
                        break;
                    case 's':
                        drawer.drawText(row+6,25*2,ForeColor.WHITE, "S");
                        snake.setDirection(Direction.DOWN);
                        break;
                    case 'a':
                        drawer.drawText(row+6,25*2,ForeColor.WHITE, "A");
                        snake.setDirection(Direction.LEFT);
                        break;
                    case 'd':
                        drawer.drawText(row+6,25*2,ForeColor.WHITE, "D");
                        snake.setDirection(Direction.RIGHT);
                        break;
                    case '1':
                        drawer.drawText(row+6,25*2,ForeColor.WHITE, "1");
                        if (gameStatus == GameStatus.MENU ){
                            // 检查startGameButton是否启用
                            if (startGameButton.isEnable()){
                                currentSelectButton = 1;
                                startGameButton.onSelect();
                                restartGameButton.onDeselect();
                            } else {
                                currentSelectButton = 2;
                                restartGameButton.onSelect();
                                startGameButton.onDeselect();
                            }

                        }
                        break;
                    case '2':
                        drawer.drawText(row+6,25*2,ForeColor.WHITE,"2");
                        if (gameStatus == GameStatus.MENU || gameStatus == GameStatus.GAME_OVER){
                            // 检查restartGameButton是否启用
                            if (restartGameButton.isEnable()){
                                currentSelectButton = 2;
                                startGameButton.onDeselect();
                                restartGameButton.onSelect();
                            } else {
                                currentSelectButton = 1;
                                startGameButton.onSelect();
                                restartGameButton.onDeselect();
                            }

                        }
                        break;
                    case 13:
                        drawer.drawText(row+6,25*2,ForeColor.WHITE, "↵");
                        Logger.warn("当前选择的按钮为 "+currentSelectButton);
                        if (currentSelectButton == 1){
                            // 开始游戏被选择确定后，把游戏状态设置为开始状态
                            gameStatus = GameStatus.STARTING;
                        } else if (currentSelectButton == 2) {

                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 渲染逻辑
     */
    public void render(){
        // 得分、长度、fps数值动态渲染
        String score = String.format("%d",this.score);
        drawer.drawText(row+2,25*2,ForeColor.WHITE, score);

        String length = String.format("%d",this.length);
        drawer.drawText(row+4, 25*2,ForeColor.WHITE,length);

        String fps = String.format("%d",this.currentFPS);
        drawer.drawText(row+8,25*2,ForeColor.WHITE,fps);

        // 如果游戏状态在菜单，去动态实时渲染按钮
        if (gameStatus == GameStatus.MENU || gameStatus == GameStatus.GAME_OVER){
            startGameButton.draw();
            restartGameButton.draw();
        }

        // 渲染蛇
        if (gameStatus == GameStatus.STARTING){
            // 如果是蛇头位置处于边界上就不渲染
            Position head = snake.getHead();
            if (!isOnBorder(head))
                snake.draw();
        }
    }



    /**
     * 清理终端输入
     */
    public void cleanup() {
        try {
            // 关闭终端
            terminal.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否在边界上
     * @param position 位置
     * @return 如果是就返回true，否则就返回false
     */
    private boolean isOnBorder(Position position){
        return position.getRow() == 1 || position.getRow() == this.row || position.getCol() == 1 || position.getCol() == this.col;
    }

    /**
     * 检查蛇是否撞到地图边界
     * @return 如果撞到边界就返回true，否则就返回false
     */
    private boolean checkCollisionWithBoundary(){
        Position head = snake.getHead();
        int row = head.getRow();
        int col = head.getCol();
        return row < 1 || row >= this.row || col < 2 || col >= this.col;
    }

    private void gameOver(){
        // 游戏状态设置为游戏结束
        gameStatus = GameStatus.GAME_OVER;
        restartGameButton.setEnable(true);
        startGameButton.setEnable(false);
        currentSelectButton = 2;
        restartGameButton.onSelect();
//        Logger.error("游戏结束，当前选择的按钮为" + currentSelectButton);
    }

    /**
     * 重新游戏
     */
    private void restartGame(){
        this.score = 0;

    }
}
