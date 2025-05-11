package org.jianxiong.entity;

import org.jianxiong.core.AppliedEffect;
import org.jianxiong.core.GameContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName: GameEntity
 * @Description: 游戏角色实体抽象基类，统一管理角色的buff/debuff效果和上下文
 * @Author BsKPLu
 * @Date 2025/5/11
 * @Version 1.1
 */
public abstract class GameEntity {
    protected List<AppliedEffect> activeEffects = new ArrayList<>();
    protected GameContext gameContext;

    public void setGameContext(GameContext context) {
        this.gameContext = context;
    }

    public void addAppliedEffect(AppliedEffect effect) {
        effect.onApply();
        this.activeEffects.add(effect);
    }

    /**
     * 更新角色身上所有效果的状态，并在效果到期时移除
     * 通常在每个游戏回合调用，以维护效果的生命周期
     */
    public void updateAppliedEffects() {
        Iterator<AppliedEffect> iterator = activeEffects.iterator();
        while (iterator.hasNext()) {
            AppliedEffect effect = iterator.next();
            if (effect.onTurnUpdate()) {
                effect.onExpire();
                iterator.remove();
            }
        }
    }

    public abstract String getEntityName();
}
