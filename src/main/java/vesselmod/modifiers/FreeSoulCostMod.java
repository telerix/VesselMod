package vesselmod.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import vesselmod.cards.BaseCard;

import static vesselmod.VesselMod.makeID;

public class FreeSoulCostMod extends AbstractCardModifier {
    public static final String ID = makeID("FreeSoulCostOnPlay");
    private final boolean inherent;
    private int origSoulCost;

    public FreeSoulCostMod() {
        this(false);
    }

    public FreeSoulCostMod(boolean isInherent) {
        this.inherent = isInherent;
    }

    public void onInitialApplication(AbstractCard c) {
        if (c instanceof BaseCard) {
            BaseCard card = (BaseCard) c;
            origSoulCost = card.soulCost;
            card.soulCost = 0;
        }
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ((BaseCard) card).soulCost = origSoulCost;
                this.isDone = true;
            }
        });
        CardModifierManager.removeModifiersById(card, ID, true);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new FreeSoulCostMod(inherent);
    }
}
