package vesselmod.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vesselmod.actions.SoulChangeAction;
import vesselmod.character.Vessel;

import static vesselmod.VesselMod.makeID;

@AutoAdd.Seen
public class SalubrasBlessing extends BaseRelic{
    public static final String NAME = "SalubrasBlessing";
    public static final String ID = makeID(NAME);
    public static final int soulGain = 2;
    public SalubrasBlessing() {
        super(ID, NAME, Vessel.Enums.CARD_COLOR, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        UnlockTracker.markRelicAsSeen(this.relicId);
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.type == AbstractCard.CardType.POWER) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new SoulChangeAction(AbstractDungeon.player, soulGain));
        }
    }

    public AbstractRelic makeCopy() {
        return new SalubrasBlessing();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + soulGain + DESCRIPTIONS[1];
    }
}
