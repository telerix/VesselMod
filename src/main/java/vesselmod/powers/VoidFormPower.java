package vesselmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vesselmod.actions.CardBecomesEtherealAction;
import vesselmod.cards.BaseCard;

import static vesselmod.VesselMod.makeID;

public class VoidFormPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("VoidForm");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public VoidFormPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner);
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (!card.isEthereal && card.type == AbstractCard.CardType.ATTACK) {
            this.flash();
            this.addToBot(new CardBecomesEtherealAction(card));
        }
        if (card.type == AbstractCard.CardType.ATTACK && card instanceof BaseCard) {
            if (((BaseCard) card).soulCost > 0) {
                ((BaseCard) card).soulCost = 0;
                ((BaseCard) card).isSoulCostModified = true;
            }
        }
    }

    public void updateDescription(){
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new VoidFormPower(owner);
    }
}
