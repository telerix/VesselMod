package vesselmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vesselmod.actions.SoulChangeAction;

import static vesselmod.VesselMod.makeID;

public class SoulAbsorptionPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("SoulAbsorption");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public SoulAbsorptionPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        this.flash();
        this.addToBot(new SoulChangeAction(AbstractDungeon.player, this.amount));
    }//effect

    public void updateDescription(){
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SoulAbsorptionPower(owner, amount);
    }
}
