package org.jianxiong.core;

/**
 * @ClassName: WeatherSystem
 * @Description: 天气系统，管理和记录当前天气
 * @Author BsKPLu
 * @Date 2025/5/11
 * @Version 1.1
 */
public class WeatherSystem {
    private WeatherType currentWeather = WeatherType.SUNNY;

    public WeatherType getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(WeatherType weather) {
        this.currentWeather = weather;
        System.out.println("天气变为: " + weather);
    }
}
