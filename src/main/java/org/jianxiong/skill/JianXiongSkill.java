package org.jianxiong.skill;

import org.jianxiong.core.GameContext;
import org.jianxiong.core.SceneType;
import org.jianxiong.core.WeatherType;
import org.jianxiong.entity.ArmyUnit;
import org.jianxiong.entity.Wujiang;

/**
 * @ClassName: JianXiongSkill
 * @Description: 当武将或其军队被计策攻击时，根据条件反弹伤害
 * @Author BsKPLu
 * @Date 2025/5/11
 * @Version 1.1
 */
public class JianXiongSkill extends Skill {
    public JianXiongSkill() {
        super("奸雄", "我方军队单位被敌方计策命中后反弹自身受到伤害的50%，单一针对具备奸雄技能的武将个体的计策伤害反弹100%（大战场生效，雾天失效）。", 0);
    }

    @Override
    public double handleStratagemImpact(Wujiang skillOwner, Object actualTargetOfStratagem, Wujiang stratagemCaster, double incomingDamage, GameContext context) {
        // 检查激活条件（天气及场景）
        if (context.getCurrentScene() != SceneType.LARGE_BATTLEFIELD) {
            return incomingDamage;
        }
        if (context.getCurrentWeather() == WeatherType.FOGGY) {
            System.out.println("奸雄技能: 雾天，失效。");
            return incomingDamage;
        }

        System.out.println("武将 [" + skillOwner.getName() + "] 的【奸雄】技能被触发！计策来源: [" + stratagemCaster.getName() + "] 对目标 [" + (actualTargetOfStratagem instanceof Wujiang ? ((Wujiang) actualTargetOfStratagem).getName() : ((ArmyUnit) actualTargetOfStratagem).getId()) + "]，原始伤害: " + incomingDamage);

        double reflectedDamagePercentage = 0;
        double damageTakenByTargetPercentage = 1.0;

        // 根据计谋的目标对象来确定效果
        boolean effectTriggered = false;
        if (actualTargetOfStratagem instanceof Wujiang && actualTargetOfStratagem == skillOwner) {
            System.out.println("奸雄效果: 计策针对武将个体 [" + skillOwner.getName() + "]。");
            reflectedDamagePercentage = 1.0;
            damageTakenByTargetPercentage = 0.0;
            effectTriggered = true;
        } else if (actualTargetOfStratagem instanceof ArmyUnit && ((ArmyUnit) actualTargetOfStratagem).getLeader() == skillOwner) {
            ArmyUnit targetedArmy = (ArmyUnit) actualTargetOfStratagem;
            System.out.println("奸雄效果: 计策针对 [" + skillOwner.getName() + "] 带领的军队单位 [" + targetedArmy.getId() + "]。");
            reflectedDamagePercentage = 0.5;
            damageTakenByTargetPercentage = 0.5;
            effectTriggered = true;
        }

        // 如果计谋命中了其他目标（例如：一个不由此武将领导的军队单位，或者一个不同的武将）
        // 或者技能拥有者与计谋的实际目标在定义的方式上没有直接关联
        if (!effectTriggered) {
            return incomingDamage;
        }

        double damageToReflect = incomingDamage * reflectedDamagePercentage;
        if (damageToReflect > 0 && stratagemCaster != null) {
            System.out.println("向计策施法者 [" + stratagemCaster.getName() + "] 反弹伤害: " + damageToReflect);
            stratagemCaster.takeDamage(damageToReflect, "奸雄技能反弹");
        }

        double actualDamageToSkillOwnerOrTheirUnit = incomingDamage * damageTakenByTargetPercentage;
        System.out.println("奸雄结算后，目标实际受到伤害: " + actualDamageToSkillOwnerOrTheirUnit);
        return actualDamageToSkillOwnerOrTheirUnit;
    }

    @Override
    protected void executeEffect(Wujiang caster, Object target, GameContext context) {
        System.out.println("奸雄技能为被动触发，没有主动执行的效果。");
    }
}
