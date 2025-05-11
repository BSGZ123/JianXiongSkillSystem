package org.jianxiong.simulation;

import org.jianxiong.core.*;
import org.jianxiong.entity.*;
import org.jianxiong.skill.*;

/**
 * @ClassName: GameSimulationJianXiong
 * @Description: 演示和测试 "奸雄" 技能在不同场景、天气下的效果
 * @Author BsKPLu
 * @Date 2025/5/11
 * @Version 1.1
 */
public class GameSimulationJianXiong {
    public static void main(String[] args) {
        // 初始化
        WeatherSystem weatherSystem = new WeatherSystem();
        GameContext largeBattlefieldContext = new GameContext(SceneType.LARGE_BATTLEFIELD, weatherSystem);

        // 创建武将与敌人
        Wujiang caoCao = new Wujiang("曹操", 100.0, largeBattlefieldContext);
        Wujiang enemyGeneral = new Wujiang("敌将", 100.0, largeBattlefieldContext);
        ArmyUnit caoCaoArmy = new ArmyUnit("曹军虎豹骑", caoCao, 500.0, largeBattlefieldContext);

        // 使用工厂给曹操赋予“奸雄”技能
        Skill jianXiong = SkillFactory.createSkill("奸雄");
        caoCao.addSkill(jianXiong);

        // 创建策略
        Stratagem fireStratagem = new Stratagem("火计", 30.0);

        System.out.println("--- 场景1: 计策攻击曹操的军队 (大战场, 晴天) ---");
        weatherSystem.setCurrentWeather(WeatherType.SUNNY);
        largeBattlefieldContext.currentScene = SceneType.LARGE_BATTLEFIELD;
        fireStratagem.execute(enemyGeneral, caoCaoArmy, largeBattlefieldContext);
        printStatus(caoCao, enemyGeneral, caoCaoArmy);

        caoCao = new Wujiang("曹操", 100.0, largeBattlefieldContext);
        caoCao.addSkill(SkillFactory.createSkill("奸雄"));
        enemyGeneral = new Wujiang("敌将", 100.0, largeBattlefieldContext);
        caoCaoArmy = new ArmyUnit("曹军虎豹骑", caoCao, 500.0, largeBattlefieldContext);

        System.out.println("\n--- 场景2: 计策攻击曹操本人 (大战场, 晴天) ---");
        weatherSystem.setCurrentWeather(WeatherType.SUNNY);
        largeBattlefieldContext.currentScene = SceneType.LARGE_BATTLEFIELD;
        fireStratagem.execute(enemyGeneral, caoCao, largeBattlefieldContext);
        printStatus(caoCao, enemyGeneral, caoCaoArmy);

        caoCao = new Wujiang("曹操", 100.0, largeBattlefieldContext);
        caoCao.addSkill(SkillFactory.createSkill("奸雄"));
        enemyGeneral = new Wujiang("敌将", 100.0, largeBattlefieldContext);
        caoCaoArmy = new ArmyUnit("曹军虎豹骑", caoCao, 500.0, largeBattlefieldContext);

        System.out.println("\n--- 场景3: 计策攻击曹操的军队 (大战场, 雾天 - 奸雄失效) ---");
        weatherSystem.setCurrentWeather(WeatherType.FOGGY);
        largeBattlefieldContext.currentScene = SceneType.LARGE_BATTLEFIELD;
        fireStratagem.execute(enemyGeneral, caoCaoArmy, largeBattlefieldContext);
        printStatus(caoCao, enemyGeneral, caoCaoArmy);

        System.out.println("\n--- 场景4: 计策攻击曹操的军队 (小战场 - 奸雄不生效) ---");
        weatherSystem.setCurrentWeather(WeatherType.SUNNY);
        largeBattlefieldContext.currentScene = SceneType.SMALL_BATTLEFIELD;
        fireStratagem.execute(enemyGeneral, caoCaoArmy, largeBattlefieldContext);
        printStatus(caoCao, enemyGeneral, caoCaoArmy);
    }

    public static void printStatus(Wujiang w1, Wujiang w2, ArmyUnit a1) {
        System.out.println(String.format("  %s 生命: %.1f", w1.getName(), w1.getHealth()));
        System.out.println(String.format("  %s 生命: %.1f", w2.getName(), w2.getHealth()));
        if (a1 != null) {
            System.out.println(String.format("  %s 兵力: %.1f", a1.getId(), a1.getUnitStrength()));
        }
    }
}
