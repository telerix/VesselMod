package vesselmod.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vesselmod.actions.SoulChangeAction;
import vesselmod.character.Vessel;
import vesselmod.misc.CustomTags;

import static vesselmod.VesselMod.makeID;

@AutoAdd.Seen
public class SpellTwister extends BaseRelic{
    public static final String NAME = "SpellTwister";
    public static final String ID = makeID(NAME);
    public static final int soulGain = 1;
    public SpellTwister() {
        super(ID, NAME, Vessel.Enums.CARD_COLOR, RelicTier.RARE, LandingSound.MAGICAL);
        UnlockTracker.markRelicAsSeen(this.relicId);
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.hasTag(CustomTags.SPELL)) {
            this.flash();
            this.addToBot(new SoulChangeAction(AbstractDungeon.player, soulGain));
        }
    }

    public AbstractRelic makeCopy() {
        return new SpellTwister();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + soulGain + DESCRIPTIONS[1];
    }
}
