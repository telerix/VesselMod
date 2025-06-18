package vesselmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.DuplicationPower;
import vesselmod.misc.SoulMechanics;

@SpirePatch2(clz = DuplicationPower.class, method = "onUseCard")
public class DupePotSoulPatch {
    @SpireInsertPatch(rloc = 15, localvars={"tmp"})
    public static void patch(AbstractCard ___tmp) {
        SoulMechanics.tempSetFreeSoul(___tmp);
    }
}
