package vesselmod.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vesselmod.character.Vessel;

import static vesselmod.VesselMod.makeID;
@AutoAdd.Seen
public class SoulEater extends BaseRelic{
    public static final String NAME = "SoulEater";
    public static final String ID = makeID(NAME);
    public static final int soulGain = 1;

    public SoulEater() {
        super(ID, NAME, Vessel.Enums.CARD_COLOR, RelicTier.BOSS, LandingSound.CLINK);
        UnlockTracker.markRelicAsSeen(this.relicId);
    }
    //effect in SoulMechanics

    public AbstractRelic makeCopy() {
        return new SoulEater();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + soulGain + DESCRIPTIONS[1];
    }
}
