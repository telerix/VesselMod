package vesselmod.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vesselmod.actions.SoulChangeAction;
import vesselmod.character.Vessel;

import static vesselmod.VesselMod.makeID;
@AutoAdd.Seen
public class Kingsoul extends BaseRelic{
    public static final String NAME = "Kingsoul";
    public static final String ID = makeID(NAME);
    private final int soulGain = 1;

    public Kingsoul() {
        super(ID, NAME, Vessel.Enums.CARD_COLOR, RelicTier.RARE, LandingSound.CLINK);
        UnlockTracker.markRelicAsSeen(this.relicId);
    }

    public void atTurnStart() {
        this.flash();
        this.addToBot(new SoulChangeAction(AbstractDungeon.player, soulGain));
    }

    public AbstractRelic makeCopy() {
        return new Kingsoul();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + this.soulGain + DESCRIPTIONS[1];
    }
}
