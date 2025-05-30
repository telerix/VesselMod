package vesselmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static vesselmod.VesselMod.makeID;

public class NextTurnInfectPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("NextTurnInfect");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;
    private final int infAmt;

    public NextTurnInfectPower(AbstractCreature owner, int infAmt) {
        super(POWER_ID, TYPE, TURN_BASED, owner, infAmt);
        this.infAmt = infAmt;
        this.owner = owner;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void atStartOfTurn() {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new InfectionPower(this.owner, this.owner, this.infAmt), this.infAmt));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }


    @Override
    public AbstractPower makeCopy() {
        return new NextTurnInfectPower(owner, amount);
    }
}
