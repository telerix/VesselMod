package vesselmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vesselmod.actions.SoulChangeAction;
import vesselmod.misc.CustomTags;

import static vesselmod.VesselMod.makeID;

public class SpellTwisterPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("SpellTwister");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public SpellTwisterPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.amount = amount;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(CustomTags.SPELL)) {
            this.flash();
            this.addToBot(new SoulChangeAction(AbstractDungeon.player, this.amount));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SpellTwisterPower(owner, this.amount);
    }
}
