package cn.fan.snake.engine.keybord;

import cn.fan.snake.engine.annotation.KeyHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 输入管理器
 *
 * @author Fan
 */
public class InputManager {
    private final Map<String,KeyListener> keyListeners = new HashMap<>();
    private final Map<Character, Method> keyHandlerMethods = new HashMap<>();
    private final Map<Character, KeyListener> keyHandlerInstances = new HashMap<>();

    public InputManager(){

    }

    /**
     * 注册监听器
     * @param name 名字
     * @param keyListener 监听器
     */
    public void register(String name,KeyListener keyListener){
        keyListeners.put(name,keyListener);

        // 扫描监听器中带有@KeyHandler注解的方法
        Method[] methods = keyListener.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(KeyHandler.class)) {
                KeyHandler annotation = method.getAnnotation(KeyHandler.class);
                char key = annotation.key();
                method.setAccessible(true);
                keyHandlerMethods.put(key, method);
                keyHandlerInstances.put(key, keyListener);
            }
        }
    }

    /**
     * 处理按键输入
     * @param input 输入字符
     */
    public void handler(char input){
        Method method = keyHandlerMethods.get(input);
        KeyListener instance = keyHandlerInstances.get(input);

        if (method != null && instance != null){
            try {
                method.invoke(instance);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 清除数据
     */
    public void clean(){
        keyListeners.clear();
        keyHandlerInstances.clear();
        keyHandlerMethods.clear();
    }
}
