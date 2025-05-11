package org.jianxiong.skill;

import org.jianxiong.core.GameContext;
import org.jianxiong.entity.Wujiang;

/**
 * @InterfaceName: SkillState
 * @Description: 实现技能的状态模式（如准备、冷却等）
 * @Author BsKPLu
 * @Date 2025/5/11
 * @Version 1.1
 */
public interface SkillState {
    boolean canActivate(Skill skill, Wujiang caster, Object target, GameContext context);

    void activate(Skill skill, Wujiang caster, Object target, GameContext context);

    void onTurnEnd(Skill skill, GameContext context);

    String getStatus();
}
