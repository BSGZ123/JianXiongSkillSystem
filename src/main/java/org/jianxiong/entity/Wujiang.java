package org.jianxiong.entity;

import org.jianxiong.core.GameContext;
import org.jianxiong.simulation.Stratagem;
import org.jianxiong.skill.Skill;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Wujiang
 * @Description: 武将类，表示游戏中的武将角色，拥有生命值、技能、可带领军队，能处理计策伤害。
 * @Author BsKPLu
 * @Date 2025/5/11
 * @Version 1.1
 */
public class Wujiang extends GameEntity {
    private String name;
    private double health;
    private List<Skill> skills;
    private ArmyUnit leadingArmy;

    public Wujiang(String name, double initialHealth, GameContext context) {
        this.name = name;
        this.health = initialHealth;
        this.skills = new ArrayList<>();
        this.setGameContext(context);
    }

    @Override
    public String getEntityName() {
        return this.name;
    }

    public String getName() {
        return name;
    }

    public double getHealth() {
        return health;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void addSkill(Skill skill) {
        if (skill != null) this.skills.add(skill);
    }

    public ArmyUnit getLeadingArmy() {
        return leadingArmy;
    }

    public void setLeadingArmy(ArmyUnit army) {
        this.leadingArmy = army;
    }

    public void takeDamage(double amount, String source) {
        this.health -= amount;
        System.out.println("武将 [" + this.name + "] 受到来自 [" + source + "] 的伤害: " + amount + "。剩余生命: " + String.format("%.1f", this.health));
        if (this.health <= 0) {
            this.health = 0;
            System.out.println("武将 [" + this.name + "] 已被击败！");
        }
    }

    public void processIncomingStratagem(Wujiang stratagemCaster, Stratagem stratagem, double rawDamage, Object actualTargetOfStratagem) {
        System.out.println("武将 [" + this.name + "] (作为计策目标) 正在处理来自 [" + stratagemCaster.getName() + "] 的计策 [" + stratagem.getName() + "]，原始伤害: " + rawDamage);
        double damageToApply = rawDamage;

        for (Skill skill : this.skills) {
            damageToApply = skill.handleStratagemImpact(this, actualTargetOfStratagem, stratagemCaster, damageToApply, this.gameContext);
        }

        if (damageToApply > 0) {
            this.takeDamage(damageToApply, "计策：" + stratagem.getName());
        } else {
            System.out.println("武将 [" + this.name + "] 在技能影响后未受到计策 [" + stratagem.getName() + "] 的伤害。");
        }
    }

    public void updateSkillsAndEffectsOnTurnEnd() {
        for (Skill skill : skills) {
            skill.onTurnEndUpdate(this.gameContext);
        }
        updateAppliedEffects();
    }
}
