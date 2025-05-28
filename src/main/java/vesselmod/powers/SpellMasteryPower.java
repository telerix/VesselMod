package vesselmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vesselmod.misc.CustomTags;

import static vesselmod.VesselMod.makeID;

public class SpellMasteryPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("SpellMastery");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public SpellMasteryPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.amount = amount;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(CustomTags.SPELL)) {
            this.addToBot(new ApplyPowerAction(owner, owner, new SpellBoostPower(owner, this.amount), this.amount));
        }
    }

    public void updateDescription(){
        this.description = DESCRIPTIONS[0] + this.amount * 10 + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SpellMasteryPower(owner, amount);
    }
}
