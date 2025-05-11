package org.jianxiong.simulation;

import org.jianxiong.core.GameContext;
import org.jianxiong.entity.ArmyUnit;
import org.jianxiong.entity.Wujiang;

/**
 * @ClassName: Stratagem
 * @Description: 计策类，表示可以对目标造成伤害的特殊行动,可被武将使用，对武将或军队造成伤害，触发技能判定。
 * @Author BsKPLu
 * @Date 2025/5/11
 * @Version 1.1
 */
public class Stratagem {
    private String name;
    private double baseDamage;

    public Stratagem(String name, double baseDamage) {
        this.name = name;
        this.baseDamage = baseDamage;
    }

    public String getName() {
        return name;
    }

    public void execute(Wujiang caster, Object targetEntity, GameContext context) {
        System.out.println("\n武将 [" + caster.getName() + "] 对 " +
                (targetEntity instanceof Wujiang ? "武将 [" + ((Wujiang) targetEntity).getName() + "]" : "军队单位 [" + ((ArmyUnit) targetEntity).getId() + "]") +
                " 使用计策 [" + this.name + "]");

        // 可能因为施法者的属性、目标的防御等因素的影响而变动
        double currentDamage = this.baseDamage;

        if (targetEntity instanceof Wujiang) {
            ((Wujiang) targetEntity).processIncomingStratagem(caster, this, currentDamage, targetEntity);
        } else if (targetEntity instanceof ArmyUnit) {
            ((ArmyUnit) targetEntity).processIncomingStratagem(caster, this, currentDamage, targetEntity);
        } else {
            System.out.println("计策目标类型未知: " + targetEntity);
        }
    }
}
