package cn.fan.snake.listener;

import cn.fan.snake.GameManager;
import cn.fan.snake.GameStatus;
import cn.fan.snake.engine.annotation.KeyHandler;
import cn.fan.snake.engine.ansi.ForeColor;
import cn.fan.snake.engine.keybord.KeyListener;

/**
 * 游戏菜单按键监听器
 *
 * @author Fan
 */
public class MenuKeyListener implements KeyListener {
    private final GameManager gameManager;
    public MenuKeyListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    /**
     * 处理1按键
     */
    @KeyHandler(key = '1')
    public void hande1Key(){
        gameManager.getDrawer().drawText(gameManager.getRow()+6,25*2,ForeColor.WHITE,"1");
        if (gameManager.getGameStatus() == GameStatus.MENU){
            // 检查startGameButton是否启用
            if (gameManager.getStartGameButton().isEnable()){
                gameManager.setCurrentSelectButton(1);
                gameManager.getStartGameButton().onSelect();
                gameManager.getRestartGameButton().onDeselect();
            } else {
                gameManager.setCurrentSelectButton(2);
                gameManager.getRestartGameButton().onSelect();
                gameManager.getStartGameButton().onDeselect();
            }
        }
    }

    /**
     * 处理2按键
     */
    @KeyHandler(key = '2')
    public void handle2Key(){
        gameManager.getDrawer().drawText(gameManager.getRow()+6,25*2,ForeColor.WHITE,"2" );
        if (gameManager.getGameStatus() == GameStatus.MENU || gameManager.getGameStatus() == GameStatus.GAME_OVER){
            // 检查restartGameButton是否启用
            if(gameManager.getRestartGameButton().isEnable()){
                gameManager.setCurrentSelectButton(2);
                gameManager.getStartGameButton().onDeselect();
                gameManager.getRestartGameButton().onSelect();
            } else {
                gameManager.setCurrentSelectButton(1);
                gameManager.getStartGameButton().onSelect();
                gameManager.getRestartGameButton().onDeselect();
            }
        }
    }

    /**
     * 处理enter按键
     */
    @KeyHandler(key = 13)
    public void handleEnterKey(){
        gameManager.getDrawer().drawText(gameManager.getRow()+6,25*2, ForeColor.WHITE, "↵");
        if (gameManager.getGameStatus() == GameStatus.MENU || gameManager.getGameStatus() == GameStatus.GAME_OVER){
            if (gameManager.getCurrentSelectButton() == 1){
                gameManager.setGameStatus(GameStatus.STARTING);
            } else if (gameManager.getCurrentSelectButton() == 2){
                gameManager.restartGame();
            }
        }
    }
}
