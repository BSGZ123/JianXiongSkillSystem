package org.jianxiong.skill;

/**
 * @ClassName: SkillFactory
 * @Description: 技能工厂类，根据名称创建技能实例
 * @Author BsKPLu
 * @Date 2025/5/11
 * @Version 1.1
 */
public class SkillFactory {
    public static Skill createSkill(String skillName) {
        if (skillName == null) return null;
        return switch (skillName.toLowerCase()) {
            case "jianxiong", "奸雄" -> new JianXiongSkill();
            default -> {
                System.out.println("警告: 未知的技能名称无法创建 - " + skillName);
                yield null;
            }
        };
    }
}
