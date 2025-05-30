package vesselmod.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vesselmod.character.Vessel;
import vesselmod.powers.SpellBoostPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static vesselmod.VesselMod.makeID;
@AutoAdd.Seen
public class ShamanStone extends BaseRelic{
    public static final String NAME = "ShamanStone";
    public static final String ID = makeID(NAME);
    public static final int EFFECT = 50;
    public ShamanStone() {
        super(ID, NAME, Vessel.Enums.CARD_COLOR, RelicTier.SHOP, LandingSound.CLINK);
        UnlockTracker.markRelicAsSeen(this.relicId);
    }

    public void atBattleStart() {
        this.flash();
        this.addToBot(new ApplyPowerAction(player, player, new SpellBoostPower(player, EFFECT/10), EFFECT/10));
    }

    public AbstractRelic makeCopy() {
        return new ShamanStone();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + EFFECT + DESCRIPTIONS[1];
    }
}
