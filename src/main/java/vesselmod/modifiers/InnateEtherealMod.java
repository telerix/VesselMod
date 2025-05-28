package vesselmod.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static vesselmod.VesselMod.makeID;

public class InnateEtherealMod extends AbstractCardModifier {
    public static final String ID = makeID("InnateEtherealModifier");
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    private boolean inherent;


    public InnateEtherealMod() {
        this(false);
    }

    public InnateEtherealMod(boolean isInherent) {
        this.inherent = isInherent;
    }

    public boolean shouldApply(AbstractCard card) {
        return !card.isEthereal && !card.isInnate;
    }

    public void onInitialApplication(AbstractCard card) {
        card.isEthereal = true;
        card.isInnate = true;
    }

    public void onRemove(AbstractCard card) {
        card.isEthereal = false;
        card.isInnate = false;
    }

    public String identifier(AbstractCard card) {
        return ID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return TEXT[0] + rawDescription;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new InnateEtherealMod(inherent);
    }
}
