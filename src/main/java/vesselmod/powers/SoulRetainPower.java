package vesselmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static vesselmod.VesselMod.makeID;
import static vesselmod.misc.SoulMechanics.soulRetain;

public class SoulRetainPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("SoulRetain");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    @Override
    public void onVictory() {
        soulRetain += this.amount;
    }

    public SoulRetainPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);

    }

    public void updateDescription(){
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SoulRetainPower(owner, amount);
    }
}
