package vesselmod.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vesselmod.actions.SoulChangeAction;
import vesselmod.character.Vessel;

import static vesselmod.VesselMod.makeID;
@AutoAdd.Seen
public class BrokenVessel extends BaseRelic{
    public static final String NAME = "BrokenVessel";
    public static final String ID = makeID(NAME);
    private final int soulStart = 2;
    public BrokenVessel() {
        super(ID, NAME, Vessel.Enums.CARD_COLOR, RelicTier.STARTER, LandingSound.CLINK);
        UnlockTracker.markRelicAsSeen(this.relicId);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToBot(new SoulChangeAction(AbstractDungeon.player, this.soulStart));
    }

    public AbstractRelic makeCopy() {
        return new BrokenVessel();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + soulStart + DESCRIPTIONS[1];
    }
}
