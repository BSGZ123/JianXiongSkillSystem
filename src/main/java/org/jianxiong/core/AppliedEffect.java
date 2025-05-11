package org.jianxiong.core;

import org.jianxiong.entity.ArmyUnit;
import org.jianxiong.entity.Wujiang;

/**
 * @ClassName: AppliedEffect
 * @Description: 抽象类，表示附加在角色实体上的各种效果（如buff、debuff）
 * @Author BsKPLu
 * @Date 2025/5/11
 * @Version 1.1
 */
public abstract class AppliedEffect {
    protected String name;
    protected Object target;
    protected int remainingDurationTurns;

    public AppliedEffect(String name, Object target, int duration) {
        this.name = name;
        this.target = target;
        this.remainingDurationTurns = duration;
    }

    public String getName() { return name; }

    /**
     * 在每个回合更新时调用，减少效果的剩余持续回合数，并检查效果是否到期。
     * @return
     */
    public boolean onTurnUpdate() {
        if (remainingDurationTurns > 0) {
            remainingDurationTurns--;
            if (remainingDurationTurns == 0) return true;
        }
        return false;
    }
    public abstract void onApply();
    public abstract void onExpire();

    protected String getTargetName() {
        if (target instanceof Wujiang) return ((Wujiang) target).getName();
        if (target instanceof ArmyUnit) return ((ArmyUnit) target).getId();
        return "未知目标";
    }
}
