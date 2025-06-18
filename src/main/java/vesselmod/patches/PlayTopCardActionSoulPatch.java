package vesselmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import vesselmod.misc.SoulMechanics;

@SpirePatch2(clz = PlayTopCardAction.class, method = "update")
public class PlayTopCardActionSoulPatch {
    @SpireInsertPatch(rloc = 26, localvars={"card"})
    public static void patch(AbstractCard ___card) {
        SoulMechanics.tempSetFreeSoul(___card);
    }
}
