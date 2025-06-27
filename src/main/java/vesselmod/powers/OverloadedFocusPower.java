package vesselmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vesselmod.cards.BaseCard;
import vesselmod.misc.CustomTags;
import vesselmod.relics.TyrantsSoul;

import static vesselmod.VesselMod.makeID;

public class OverloadedFocusPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("OverloadedFocus");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public OverloadedFocusPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner);
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card instanceof BaseCard &&
                card.hasTag(CustomTags.COST_SOUL) && !card.hasTag(CustomTags.COST_SOUL_X)) {
            if (!(AbstractDungeon.player.hasPower(VoidFormPower.POWER_ID) && card.type.equals(AbstractCard.CardType.ATTACK))) {
                BaseCard c = (BaseCard) card;
                int origSoulCost = c.baseSoulCost;
                int newSoulCost = AbstractDungeon.cardRandomRng.random(1, 3);

                if (origSoulCost != newSoulCost) {
                    c.soulCost = newSoulCost;
                    c.isSoulCostModified = true;
                }
                if (AbstractDungeon.player.hasRelic(TyrantsSoul.ID)) {
                    ((TyrantsSoul) AbstractDungeon.player.getRelic(TyrantsSoul.ID)).appendStats(origSoulCost, newSoulCost);
                }
            }
        }
    }

    public void updateDescription(){
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new OverloadedFocusPower(owner);
    }
}
