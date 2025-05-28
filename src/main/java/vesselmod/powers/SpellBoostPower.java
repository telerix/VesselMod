package vesselmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vesselmod.misc.CustomTags;

import static vesselmod.VesselMod.makeID;

public class SpellBoostPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("SpellBoost");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public SpellBoostPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.amount = amount;

    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (card.hasTag(CustomTags.SPELL)) {
            return (damage * (1 + this.amount * 0.1f));
        }
        else return damage;
    }//effect

    public void updateDescription(){
        this.description = DESCRIPTIONS[0] + this.amount * 10 + DESCRIPTIONS[1];;
    }

    @Override
    public AbstractPower makeCopy() {
        return new SpellBoostPower(owner, amount);
    }
}
