package cn.fan.snake.engine.ansi;

/**
 * ANSI工具类
 *
 * @author Fan
 */
public class AnsiTool {

    // 重置样式
    public static final String RESET = "\u001B[0m";

    /**
     * 将光标移动到指定行列
     * @param row 行
     * @param col 列
     */
    public static void moveTo(int row, int col){
        String position = String.format("\u001B[%d;%dH",row,col);
        System.out.print(position);
    }

    /**
     * 清除
     */
    public static void cleanScreen(){
        System.out.print("\033[2J");
    }

    /**
     * 隐藏光标
     */
    public static void hideCursor(){
        System.out.print("\033[?25l");
    }

    /**
     * 显示光标
     */
    public static void showCursor(){
        System.out.print("\033[?25h");
    }
}
