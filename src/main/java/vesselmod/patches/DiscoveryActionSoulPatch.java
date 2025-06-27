package vesselmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.unique.DiscoveryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import vesselmod.misc.SoulMechanics;

import java.util.ArrayList;

@SpirePatch2(clz = DiscoveryAction.class, method = "update")
public class DiscoveryActionSoulPatch {
    @SpireInsertPatch(rloc = 22, localvars={"disCard", "disCard2"})
    public static void patch(AbstractCard ___disCard, AbstractCard ___disCard2) {
        SoulMechanics.tempSetFreeSoul(___disCard);
        SoulMechanics.tempSetFreeSoul(___disCard2);
    }
    @SpireInsertPatch(rloc = 9, localvars = {"generatedCards"})
    public static void patch(ArrayList<AbstractCard> ___generatedCards) {
        for (AbstractCard card : ___generatedCards) {
        SoulMechanics.tempSetFreeSoul(card);
    }
        }
}
