package vesselmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.misc.SoulMechanics;


@SpirePatch2(clz = AbstractMonster.class, method = "damage")
public class SoulGainOnAttackPatch {
    @SpireInsertPatch(rloc = 16, localvars={"damageAmount"})
    public static void patch(DamageInfo info, int damageAmount) {
        SoulMechanics.soulOnAttack(info,damageAmount);
    }
}
