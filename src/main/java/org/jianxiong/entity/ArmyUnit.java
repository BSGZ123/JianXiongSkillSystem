package org.jianxiong.entity;

import org.jianxiong.core.GameContext;
import org.jianxiong.simulation.Stratagem;
import org.jianxiong.skill.Skill;

/**
 * @ClassName: ArmyUnit
 * @Description: 军队单位类，表示由武将带领的军队,拥有兵力、指挥官，能处理计策伤害，技能效果由领导武将决定。
 * @Author BsKPLu
 * @Date 2025/5/11
 * @Version 1.1
 */
public class ArmyUnit extends GameEntity {
    private String id;
    private Wujiang leader;
    private double unitStrength;

    public ArmyUnit(String id, Wujiang leader, double initialStrength, GameContext context) {
        this.id = id;
        this.leader = leader;
        this.unitStrength = initialStrength;
        this.setGameContext(context);
        if (leader != null) {
            leader.setLeadingArmy(this);
        }
    }

    @Override
    public String getEntityName() {
        return this.id;
    }

    public String getId() {
        return id;
    }

    public Wujiang getLeader() {
        return leader;
    }

    public double getUnitStrength() {
        return unitStrength;
    }

    public void takeDamage(double amount, String source) {
        this.unitStrength -= amount;
        System.out.println("军队 [" + this.id + "] 受到来自 [" + source + "] 的伤害: " + amount + "。剩余兵力: " + String.format("%.1f", this.unitStrength));
        if (this.unitStrength <= 0) {
            this.unitStrength = 0;
            System.out.println("军队 [" + this.id + "] 已被击溃！");
        }
    }

    /**
     * 处理针对此军队单位的技能效果，技能效果来自领导武将
     * @param stratagemCaster
     * @param stratagem
     * @param rawDamage
     * @param actualTargetOfStratagem
     */
    public void processIncomingStratagem(Wujiang stratagemCaster, Stratagem stratagem, double rawDamage, Object actualTargetOfStratagem) {
        System.out.println("军队单位 [" + this.id + "] (指挥官: " + (leader != null ? leader.getName() : "无") + ") 正在处理来自 [" + stratagemCaster.getName() + "] 的计策 [" + stratagem.getName() + "]，原始伤害: " + rawDamage);
        double damageToApply = rawDamage;

        if (this.leader != null) {
            for (Skill skill : this.leader.getSkills()) {
                damageToApply = skill.handleStratagemImpact(this.leader, this, stratagemCaster, damageToApply, this.gameContext);
            }
        }

        if (damageToApply > 0) {
            this.takeDamage(damageToApply, "计策：" + stratagem.getName());
        } else {
            System.out.println("军队单位 [" + this.id + "] 在技能影响后未受到计策 [" + stratagem.getName() + "] 的伤害。");
        }
    }

    public void updateEffectsOnTurnEnd() {
        updateAppliedEffects();
    }
}
