package vesselmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vesselmod.actions.CardBecomesEtherealAction;

import java.util.Objects;

import static vesselmod.VesselMod.makeID;

public class DreamNoMorePower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("DreamNoMore");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public DreamNoMorePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE) {
            this.flash();
            this.addToBot(new CardBecomesEtherealAction(card));
            if (Objects.equals(card.cardID, VoidCard.ID)) {
                this.addToBot(new GainEnergyAction(1));
            }
            this.addToBot(new DrawCardAction(amount));
        }
    }//effect


    public void updateDescription(){
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
        else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new DreamNoMorePower(owner, amount);
    }
}
