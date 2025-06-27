package vesselmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.QuickSlash;
import vesselmod.misc.CustomTags;

import java.util.ArrayList;

@SpirePatch2(clz = QuickSlash.class, method = "<ctor>")
public class QuickSlashTagPatch {
    @SpireInsertPatch(rloc = 2, localvars={"tags"})
    public static void patch(ArrayList<AbstractCard.CardTags> ___tags) {
        ___tags.add(CustomTags.SLASH);
    }
}
