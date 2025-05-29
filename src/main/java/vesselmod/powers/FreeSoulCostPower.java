package vesselmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vesselmod.cards.BaseCard;
import vesselmod.misc.CustomTags;

import static vesselmod.VesselMod.makeID;

public class FreeSoulCostPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("FreeSoulCost");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public FreeSoulCostPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }

    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && card.hasTag(CustomTags.COST_SOUL)) {
            if (((BaseCard) card).soulCost > 0 && this.amount > 0) {
                this.flash();
                --this.amount;
                this.updateDescription();
                if (this.amount == 0) {
                    this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
                }
            }
        }

    }


    @Override
    public AbstractPower makeCopy() {
        return new FreeSoulCostPower(owner, amount);
    }
}
