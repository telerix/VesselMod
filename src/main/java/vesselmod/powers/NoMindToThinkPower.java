package vesselmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static vesselmod.VesselMod.makeID;

public class NoMindToThinkPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("NoMindToThink");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public NoMindToThinkPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void noMindToThinkEffect() { //triggered in SoulChangeAction
        this.flash();
        this.addToTop(new GainBlockAction(this.owner, amount, Settings.FAST_MODE));
    }

    public void updateDescription(){
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new NoMindToThinkPower(owner, amount);
    }
}
