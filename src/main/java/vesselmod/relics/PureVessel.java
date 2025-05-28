package vesselmod.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vesselmod.actions.SoulChangeAction;
import vesselmod.character.Vessel;

import static vesselmod.VesselMod.makeID;
import static vesselmod.misc.SoulMechanics.soulLimit;
@AutoAdd.Seen
public class PureVessel extends BaseRelic{
    public static final String NAME = "PureVessel";
    public static final String ID = makeID(NAME);
    private static final int soulStart = 5;
    private static final int soulLimitBoost = 3;

    private final float START_X = 64.0F * Settings.scale;
    private final float START_Y = Settings.isMobile ? (float)Settings.HEIGHT - 132.0F * Settings.scale : (float)Settings.HEIGHT - 102.0F * Settings.scale;
    private final float PAD_X = 72.0F * Settings.scale;
    public PureVessel() {
        super(ID, NAME, Vessel.Enums.CARD_COLOR, RelicTier.BOSS, LandingSound.CLINK);
        UnlockTracker.markRelicAsSeen(this.relicId);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        soulLimit += soulLimitBoost;
        this.addToBot(new SoulChangeAction(AbstractDungeon.player, soulStart));
    }

    public void onVictory() {
        soulLimit -= soulLimitBoost;
    }

    @Override
    public void obtain() {
        this.hb.hovered = false;
        this.targetX = START_X;
        this.targetY = START_Y;
        AbstractDungeon.player.relics.set(0, this);
        this.relicTip();
        UnlockTracker.markRelicAsSeen(this.relicId);

    }

    public AbstractRelic makeCopy() {
        return new PureVessel();
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(BrokenVessel.ID);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + soulLimitBoost + DESCRIPTIONS[1] + soulStart + DESCRIPTIONS[2];
    }
}
