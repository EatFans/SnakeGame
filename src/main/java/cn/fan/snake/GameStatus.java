package cn.fan.snake;

/**
 * 游戏状态
 */
public enum GameStatus {
    MENU(2),    // 还未开始在菜单选择中
    RUNNING(1), // 游戏进行中
    GAME_OVER(0); // 游戏结束

    private final int status;

    GameStatus(int status){
        this.status = status;
    }

    public int getStatus(){
        return status;
    }
}
