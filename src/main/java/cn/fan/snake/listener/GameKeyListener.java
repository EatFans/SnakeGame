package cn.fan.snake.listener;

import cn.fan.snake.GameManager;
import cn.fan.snake.GameStatus;
import cn.fan.snake.engine.annotation.KeyHandler;
import cn.fan.snake.engine.keybord.KeyListener;

/**
 * 游戏按键监听器
 *
 * @author Fan
 */
public class GameKeyListener implements KeyListener {
    private final GameManager gameManager;
    public GameKeyListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    /**
     * 处理Esc按键
     */
    @KeyHandler(key = 27)
    public void handleEscKey(){
        if (gameManager.getGameStatus() == GameStatus.STARTING) {
            gameManager.gameOver();
        } else if (gameManager.getGameStatus() == GameStatus.MENU ||
                gameManager.getGameStatus() == GameStatus.GAME_OVER){
            gameManager.setRunning(false);
        }

    }
}
