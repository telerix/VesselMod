package vesselmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.ExplosivePotion;

@SpirePatch2(clz = ExplosivePotion.class, method = "use")
public class ExplosivePotPatch {
    @SpireInsertPatch(rloc = 9, localvars = {"potency"})
    public static SpireReturn<Void> Insert(int potency) {
        fixDamageType(potency);
        return SpireReturn.Return();
    }

    private static void fixDamageType(int potency) {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(potency, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
    }
}
