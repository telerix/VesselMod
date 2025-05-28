package vesselmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static vesselmod.VesselMod.makeID;

public class FeintPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("Feint");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public FeintPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.flash();
        this.addToBot(new GainBlockAction(player, player, this.amount, true));
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, FeintPower.POWER_ID));
    }

    public void updateDescription(){
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new FeintPower(owner, amount);
    }
}
