package cn.fan.snake.ui;

import cn.fan.snake.engine.ansi.ForeColor;

/**
 * 按钮类
 *
 * @author Fan
 */
public class Button extends UI{
    private boolean isEnable; // 按钮是否可用
    private boolean isSelected; // 按钮是否被选择中
    private String text;
    public Button(int row, int col, String text){
        super(row,col);
        this.text = text;
        this.isEnable = true;
        this.isSelected = false;
    }

    /**
     * 渲染绘制
     */
    @Override
    public void draw(){
        checkDrawer();
        // 按钮不同状态有不同的渲染效果

        // 检查按钮启用
        if (isEnable) {
            // 检查按钮被选择中
            if (isSelected){
                drawer.drawText(row,col,ForeColor.GREEN,text);
            } else {
                drawer.drawText(row, col, ForeColor.WHITE, text);
            }
        } else {
            drawer.drawText(row, col, ForeColor.CYAN, text);
        }
    }


    /**
     * 选择按钮
     */
    public void onSelect(){
        this.isSelected = true;
    }

    /**
     * 取消选择
     */
    public void onDeselect(){
        this.isSelected = false;
    }

    /**
     * 按钮是否启用
     * @return boolean
     */
    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean flag){
        this.isEnable = flag;
    }

    public void setSelected(boolean flag){
        this.isSelected = flag;
    }

}
