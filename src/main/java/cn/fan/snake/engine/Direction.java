package cn.fan.snake.engine;

/**
 * 方向枚举
 *
 * @author Fan
 */
public enum Direction {
    UP("w"),
    DOWN("s"),
    LEFT("a"),
    RIGHT("d");

    private final String code;
    Direction(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
