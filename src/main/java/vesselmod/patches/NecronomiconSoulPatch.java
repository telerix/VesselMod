package vesselmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.Necronomicon;
import vesselmod.misc.SoulMechanics;

@SpirePatch2(clz = Necronomicon.class, method = "onUseCard")
public class NecronomiconSoulPatch {
    @SpireInsertPatch(rloc = 16, localvars={"tmp"})
    public static void patch(AbstractCard ___tmp) {
        SoulMechanics.tempSetFreeSoul(___tmp);
    }
}
