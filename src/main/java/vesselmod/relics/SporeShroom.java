package vesselmod.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vesselmod.character.Vessel;
import vesselmod.misc.CustomTags;

import static vesselmod.VesselMod.makeID;
@AutoAdd.Seen
public class SporeShroom extends BaseRelic{
    public static final String NAME = "SporeShroom";
    public static final String ID = makeID(NAME);
    public static final int EFFECT = 6;
    public SporeShroom() {
        super(ID, NAME, Vessel.Enums.CARD_COLOR, RelicTier.COMMON, LandingSound.FLAT);
        UnlockTracker.markRelicAsSeen(this.relicId);
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.hasTag(CustomTags.FOCUS)) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(EFFECT, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.POISON));
        }
    }

    public AbstractRelic makeCopy() {
        return new SporeShroom();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + EFFECT + DESCRIPTIONS[1];
    }
}
