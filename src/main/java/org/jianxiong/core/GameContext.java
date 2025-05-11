package org.jianxiong.core;

/**
 * @ClassName: GameContext
 * @Description: 封装当前游戏的全局上下文信息（场景、天气、回合数）
 * @Author BsKPLu
 * @Date 2025/5/11
 * @Version 1.1
 */
public class GameContext {
    public SceneType currentScene;
    public WeatherSystem weatherSystem;
    public int currentTurn = 1;// 当前回合数

    public GameContext(SceneType scene, WeatherSystem weather) {
        this.currentScene = scene;
        this.weatherSystem = weather;
    }

    public SceneType getCurrentScene() {
        return currentScene;
    }

    public WeatherType getCurrentWeather() {
        return weatherSystem.getCurrentWeather();
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void nextTurn() {
        this.currentTurn++;
        System.out.println("\n--- 进入回合 " + this.currentTurn + " ---");
    }
}
