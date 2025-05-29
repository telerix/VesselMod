package vesselmod.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vesselmod.character.Vessel;

import static vesselmod.VesselMod.makeID;

@AutoAdd.Seen
public class KingsBrand extends BaseRelic{
    public static final String NAME = "KingsBrand";
    public static final String ID = makeID(NAME);
    public static final int EFFECT = 8;

    public KingsBrand() {
        super(ID, NAME, Vessel.Enums.CARD_COLOR, RelicTier.RARE, LandingSound.CLINK);
        UnlockTracker.markRelicAsSeen(this.relicId);
    }
    //effect is set in InfectionPower

    public AbstractRelic makeCopy() {
        return new KingsBrand();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + EFFECT + DESCRIPTIONS[1];
    }
}
