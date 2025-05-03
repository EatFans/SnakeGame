package cn.fan.snake.listener;

import cn.fan.snake.GameManager;
import cn.fan.snake.GameStatus;
import cn.fan.snake.engine.Direction;
import cn.fan.snake.engine.annotation.KeyHandler;
import cn.fan.snake.engine.ansi.ForeColor;
import cn.fan.snake.engine.keybord.KeyListener;

/**
 * 游戏移动按键处理监听器
 * @author Fan
 */
public class MoveKeyListener implements KeyListener {
    private final GameManager gameManager;
    public MoveKeyListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    /**
     * 处理w按键
     */
    @KeyHandler(key = 'w')
    public void handlerWKey(){
        gameManager.getDrawer().drawText(gameManager.getRow()+6,25*2, ForeColor.WHITE, "W");
        if (gameManager.getGameStatus() == GameStatus.STARTING){
            if (gameManager.getSnake().getDirection() != Direction.DOWN)
                gameManager.getSnake().setDirection(Direction.UP);
        }
    }

    /**
     * 处理s按键
     */
    @KeyHandler(key = 's')
    public void handlerSKey(){
        gameManager.getDrawer().drawText(gameManager.getRow()+6,25*2, ForeColor.WHITE, "S");
        if (gameManager.getGameStatus() == GameStatus.STARTING){
            if (gameManager.getSnake().getDirection() != Direction.UP)
                gameManager.getSnake().setDirection(Direction.DOWN);
        }
    }

    /**
     * 处理a按键
     */
    @KeyHandler(key = 'a')
    public void handlerAKey(){
        gameManager.getDrawer().drawText(gameManager.getRow()+6,25*2, ForeColor.WHITE, "A");
        if (gameManager.getGameStatus() == GameStatus.STARTING){
            if (gameManager.getSnake().getDirection() != Direction.RIGHT)
                gameManager.getSnake().setDirection(Direction.LEFT);
        }
    }

    /**
     * 处理d按键
     */
    @KeyHandler(key = 'd')
    public void handlerDKey(){
        gameManager.getDrawer().drawText(gameManager.getRow()+6,25*2, ForeColor.WHITE, "D");
        if (gameManager.getGameStatus() == GameStatus.STARTING){
            if (gameManager.getSnake().getDirection() != Direction.LEFT)
                gameManager.getSnake().setDirection(Direction.RIGHT);
        }
    }


}
