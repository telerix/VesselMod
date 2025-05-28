package vesselmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vesselmod.actions.InfectionLoseHpAction;

import static vesselmod.VesselMod.makeID;

public class InfectionPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("Infection");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public InfectionPower(AbstractCreature owner, AbstractCreature source, int infectionAmt) {
        super(POWER_ID, TYPE, TURN_BASED, owner);
        this.source = source;
        this.amount = infectionAmt;
        if (this.amount > 9999) {
            this.amount = 9999;
        }
    }

    @Override
    public void onInitialApplication() {
        this.addToTop(new InfectionLoseHpAction(this.owner, this.source, this.amount, AbstractGameAction.AttackEffect.FIRE));
    }
    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount > 0) {
        this.addToTop(new InfectionLoseHpAction(this.owner, this.source, this.amount, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (owner.hasPower(InfectionPower.POWER_ID)) {
            --this.amount;

            if (this.amount == 0) {
                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, InfectionPower.POWER_ID));
            }
        }
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_POISON", 0.1F);
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new InfectionPower(owner, source, amount);
    }
}
