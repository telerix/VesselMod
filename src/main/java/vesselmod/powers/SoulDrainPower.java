package vesselmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vesselmod.actions.SoulChangeAction;

import static vesselmod.VesselMod.makeID;

public class SoulDrainPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("SoulDrain");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public SoulDrainPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.flash();
            this.addToBot(new SoulChangeAction(AbstractDungeon.player, this.amount, false));
        }
    }//effect

    public void updateDescription(){
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SoulDrainPower(owner, amount);
    }
}
