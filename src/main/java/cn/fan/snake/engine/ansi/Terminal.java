package cn.fan.snake.engine.ansi;

/**
 * Terminal终端控制台类
 *
 * @author Fan
 */
public class Terminal {

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
     * 将光标向下移动
     * @param row 行
     */
    public static void moveDown(int row){
        String position = String.format("\u001B[%dB",row);
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

    /**
     * 重置样式
     */
    public static void reset(){
        System.out.print("\u001B[0m");
    }
}
