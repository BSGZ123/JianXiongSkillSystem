package org.jianxiong.skill;

import org.jianxiong.core.GameContext;
import org.jianxiong.entity.Wujiang;

/**
 * @ClassName: CooldownState
 * @Description: 主动技能冷却时的状态，防止激活。
 * @Author BsKPLu
 * @Date 2025/5/11
 * @Version 1.1
 */
public class CooldownState implements SkillState {
    private int cooldownTurnsRemaining;

    public CooldownState(int totalCooldown) {
        this.cooldownTurnsRemaining = totalCooldown;
    }

    @Override
    public boolean canActivate(Skill skill, Wujiang caster, Object target, GameContext context) {
        return false;
    }

    @Override
    public void activate(Skill skill, Wujiang caster, Object target, GameContext context) {
        System.out.println("技能 [" + skill.getName() + "] 无法激活：正在冷却中。");
    }

    @Override
    public void onTurnEnd(Skill skill, GameContext context) {
        if (cooldownTurnsRemaining > 0) {
            cooldownTurnsRemaining--;
            if (cooldownTurnsRemaining == 0) {
                skill.setCurrentState(new ReadyState());
                System.out.println("技能 [" + skill.getName() + "] 冷却完毕 (若曾进入冷却)。");
            }
        }
    }

    @Override
    public String getStatus() {
        return "冷却中 (" + cooldownTurnsRemaining + "回合)";
    }
}
