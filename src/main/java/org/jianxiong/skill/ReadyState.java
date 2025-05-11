package org.jianxiong.skill;

import org.jianxiong.core.GameContext;
import org.jianxiong.entity.Wujiang;

/**
 * @ClassName: ReadyState
 * @Description: 被动技能或冷却结束后技能的默认状态
 * @Author BsKPLu
 * @Date 2025/5/11
 * @Version 1.1
 */
public class ReadyState implements SkillState {
    @Override
    public boolean canActivate(Skill skill, Wujiang caster, Object target, GameContext context) {
        return true;
    }

    @Override
    public void activate(Skill skill, Wujiang caster, Object target, GameContext context) {
        skill.executeEffect(caster, target, context);
    }

    @Override
    public void onTurnEnd(Skill skill, GameContext context) {

    }

    @Override
    public String getStatus() {
        return "准备就绪 (被动)";
    }
}
