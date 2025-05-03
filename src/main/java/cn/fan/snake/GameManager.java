package cn.fan.snake;

import cn.fan.snake.engine.Drawer;
import cn.fan.snake.engine.Logger;
import cn.fan.snake.engine.ansi.BackColor;
import cn.fan.snake.entity.Food;
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
import java.util.Random;

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
    private int speed; // 速度
    private boolean isRunning;
    private GameStatus gameStatus; // 游戏状态
    private Button startGameButton; // 开始游戏按钮
    private Button restartGameButton; // 开始游戏按钮
    private int currentSelectButton;
    private Snake snake; // 蛇
    private Food food; // 食物
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
        // 创建蛇对象
        food = new Food();
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

            processInput();
            update();
            render();

        }
        // 游戏循环结束
        stop();
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
        this.speed = 100;
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
        food.setDrawer(this.drawer);

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
        length = 3;
        snake.clear();
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

    /**
     * 初始化食物
     */
    private void initFood(){
        food.draw();
    }

    /**
     * 更新逻辑
     */
    public void update(){
        length = snake.getLength();

        if (gameStatus == GameStatus.STARTING){
            // 获取蛇头的当前位置
            Position head = snake.getHead();
            Position nextHead = getNextHeadPosition(head);

            // 在移动前检查碰撞
            if (checkCollisionWithBoundary(nextHead)) {
                // 游戏结束
                gameOver();
                return;
            }

            // 检查是否撞到自己
            if (snake.checkCollisionWhiSelf()) {
                gameOver();
                return;
            }
            snake.move();

            if (checkSnakeEatFood()){
                eatFood();
            }
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
                        if (gameStatus == GameStatus.STARTING)
                            if (snake.getDirection() != Direction.DOWN)
                                snake.setDirection(Direction.UP);
                        break;
                    case 's':
                        drawer.drawText(row+6,25*2,ForeColor.WHITE, "S");
                        if (gameStatus == GameStatus.STARTING)
                            if (snake.getDirection() != Direction.UP)
                                snake.setDirection(Direction.DOWN);
                        break;
                    case 'a':
                        drawer.drawText(row+6,25*2,ForeColor.WHITE, "A");
                        if (gameStatus == GameStatus.STARTING)
                            if (snake.getDirection() != Direction.RIGHT)
                                snake.setDirection(Direction.LEFT);
                        break;
                    case 'd':
                        drawer.drawText(row+6,25*2,ForeColor.WHITE, "D");
                        if (gameStatus == GameStatus.STARTING)
                            if (snake.getDirection() != Direction.LEFT)
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
                    case 13: // 回车键
                        drawer.drawText(row+6,25*2,ForeColor.WHITE, "↵");
                        Logger.warn("当前选择的按钮为 "+currentSelectButton);
                        if (gameStatus == GameStatus.MENU || gameStatus == GameStatus.GAME_OVER){
                            if (currentSelectButton == 1){
                                // 开始游戏被选择确定后，把游戏状态设置为开始状态
                                gameStatus = GameStatus.STARTING;
                            } else if (currentSelectButton == 2) {
                                Logger.info("重新开始游戏");
                                restartGame();
                            }
                        }
                        break;
                    case 27:
                        if (gameStatus == GameStatus.MENU || gameStatus == GameStatus.GAME_OVER){
                            isRunning = false;
                        }
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
        String score = String.format("%3d",this.score);
        drawer.drawText(row+2,25*2,ForeColor.WHITE, score);

        String length = String.format("%3d",this.length);
        drawer.drawText(row+4, 25*2,ForeColor.WHITE,length);

        String fps = String.format("%3d",this.speed);
        drawer.drawText(row+8,25*2,ForeColor.WHITE,fps);

        // 如果游戏状态在菜单，去动态实时渲染按钮
        if (gameStatus == GameStatus.MENU || gameStatus == GameStatus.GAME_OVER){
            startGameButton.draw();
            restartGameButton.draw();
        }

        if (gameStatus == GameStatus.STARTING){

            // 渲染蛇
            // 如果是蛇头位置处于边界上就不渲染
            Position head = snake.getHead();
            if (!isOnBorder(head))
                snake.draw();

            // 渲染食物
            food.draw();

        }


    }

    /**
     * 游戏循环结束
     */
    private void stop(){
        // 清理终端
        Terminal.cleanScreen();
        // 显示光标
        Terminal.showCursor();

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
     * 吃到食物
     */
    private void eatFood(){
        score += 10; // 吃到就加10分
        // 删除就的食物
        Position oldFoodPosition = food.getPosition();
        drawer.draw(oldFoodPosition.getX(),oldFoodPosition.getY(),"　");
        // 生成新的食物
        generateFood();
        snake.grow(); // 蛇生长一节
    }

    /**
     * 生成食物
     */
    private void generateFood(){
        Random random = new Random();
        int foodX, foodY;
        boolean validPosition;
        do {
            // 生成食物随机位置
            // x是 3 ~ 29 之间生成
            // y是 2 ～ 27 之间生成  (PS：在底部边框刷新食物，可能出现bug，懒得修，直接在2～27以上生成）
            foodX = random.nextInt( 29 - 3 + 1) + 3;
            foodY = random.nextInt(27 - 2 + 1) + 2;

            // 检查位置是否有效（不在蛇身上）
            validPosition = true;
            for (Position segment : snake.getBody()) {
                if (segment.getX() == foodX && segment.getY() == foodY) {
                    validPosition = false;
                    break;
                }
            }
        } while (!validPosition);
        // 设置食物位置
        food.setPosition(foodX, foodY);
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
     * 获取蛇头的下一个位置
     */
    private Position getNextHeadPosition(Position head) {
        Direction direction = snake.getDirection();
        int x = head.getX();
        int y = head.getY();

        return switch (direction) {
            case UP -> new Position(x - 1, y);
            case DOWN -> new Position(x + 1, y);
            case LEFT -> new Position(x, y - 1);
            case RIGHT -> new Position(x, y + 1);
            default -> head;
        };
    }

    /**
     * 检查是否吃到食物
     * @return 如果吃到食物就返回true，否则就返回false
     */
    private boolean checkSnakeEatFood(){
        Position foodPosition = food.getPosition();
        Position head = snake.getHead();

        return head.getX() == foodPosition.getX() && head.getY() == foodPosition.getY();
    }

    /**
     * 检查指定位置是否撞到地图边界
     */
    private boolean checkCollisionWithBoundary(Position position) {
        int row = position.getRow();
        int col = position.getCol();

        // 确保边界检测准确
        return row <= 0 || row >= this.row - 1 || col <= 0 || col >= this.col - 1;
    }

    /**
     * 游戏结束
     */
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
        this.length = 3;
        Map map = null;
        Menu menu = null;
        for (UI ui : uis){
            if (ui instanceof Map)
                map =(Map) ui;
            if (ui instanceof Menu)
                menu = (Menu) ui;
        }
        // 为防止UI被破坏，重新再渲染绘制一遍UI
        map.draw();
        menu.draw();
        // 清理游戏区域渲染
        drawer.clearDraw(2,3,this.row-1,this.col-2);
        // 初始化蛇、生成食物
        initSnake();
        generateFood();
        // 将游戏状态设置为STARTING
        gameStatus = GameStatus.STARTING;


    }

}