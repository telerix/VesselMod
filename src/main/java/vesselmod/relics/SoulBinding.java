package vesselmod.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vesselmod.character.Vessel;
import vesselmod.misc.SoulMechanics;

import static vesselmod.VesselMod.makeID;
@AutoAdd.Seen
public class SoulBinding extends BaseRelic{
    public static final String NAME = "SoulBinding";
    public static final String ID = makeID(NAME);
    private final int soulLimitDecrease = 3;
    public SoulBinding() {
        super(ID, NAME, Vessel.Enums.CARD_COLOR, RelicTier.BOSS, LandingSound.CLINK);
        UnlockTracker.markRelicAsSeen(this.relicId);
    }

    public void atBattleStart() {
        this.flash();
        SoulMechanics.soulLimit -= soulLimitDecrease;
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    public AbstractRelic makeCopy() {
        return new SoulBinding();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + soulLimitDecrease + DESCRIPTIONS[1];
    }
}
