package org.jianxiong.skill;

import org.jianxiong.core.GameContext;
import org.jianxiong.entity.Wujiang;

/**
 * @ClassName: Skill
 * @Description: 所有具体技能的父类，统一管理技能的触发、冷却、描述等
 * @Author BsKPLu
 * @Date 2025/5/11
 * @Version 1.1
 */
public abstract class Skill {
    protected String name;
    protected String description;
    protected SkillState currentState;
    protected int maxCooldown;

    public Skill(String name, String description, int maxCooldown) {
        this.name = name;
        this.description = description;
        this.maxCooldown = maxCooldown;
        this.currentState = new ReadyState();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setCurrentState(SkillState state) {
        this.currentState = state;
    }

    public abstract double handleStratagemImpact(
            Wujiang skillOwner,
            Object actualTargetOfStratagem,
            Wujiang stratagemCaster,
            double incomingDamage,
            GameContext context
    );

    public void tryActivate(Wujiang caster, Object target, GameContext context) {
        if (currentState.canActivate(this, caster, target, context)) {
            currentState.activate(this, caster, target, context);
        } else {
            System.out.println("技能 [" + name + "] 无法主动激活: " + currentState.getStatus());
        }
    }

    protected abstract void executeEffect(Wujiang caster, Object target, GameContext context);

    public void onTurnEndUpdate(GameContext context) {
        currentState.onTurnEnd(this, context);
    }

    public String getStatus() {
        return currentState.getStatus();
    }
}
