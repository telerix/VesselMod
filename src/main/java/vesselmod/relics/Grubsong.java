package vesselmod.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vesselmod.actions.SoulChangeAction;
import vesselmod.character.Vessel;

import static vesselmod.VesselMod.makeID;

@AutoAdd.Seen
public class Grubsong extends BaseRelic{
    public static final String NAME = "Grubsong";
    public static final String ID = makeID(NAME);
    public static final int soulGain = 1;
    public Grubsong() {
        super(ID, NAME, Vessel.Enums.CARD_COLOR, RelicTier.UNCOMMON, LandingSound.CLINK);
        UnlockTracker.markRelicAsSeen(this.relicId);
    }

    public void wasHPLost(int damageAmount) {
        if (damageAmount > 0) {
            this.flash();
            this.addToBot(new SoulChangeAction(AbstractDungeon.player, soulGain));
        }
    }

    public AbstractRelic makeCopy() {
        return new Grubsong();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + soulGain + DESCRIPTIONS[1];
    }
}
