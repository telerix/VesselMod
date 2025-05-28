package vesselmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vesselmod.misc.CustomTags;

import static vesselmod.VesselMod.makeID;

public class MantisTrainingPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("MantisTraining");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public MantisTrainingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        return (card.hasTag(AbstractCard.CardTags.STRIKE) || card.hasTag(CustomTags.SLASH)) ? damage + this.amount : damage;
    }//effect

    public void updateDescription(){
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new MantisTrainingPower(owner, amount);
    }
}
