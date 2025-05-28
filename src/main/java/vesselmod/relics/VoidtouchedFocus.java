package vesselmod.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vesselmod.cards.basic.VengefulSpirit;
import vesselmod.cards.event.AbyssShriek;
import vesselmod.cards.event.DescendingDark;
import vesselmod.cards.event.ShadeSoul;
import vesselmod.cards.rare.HowlingWraiths;
import vesselmod.cards.uncommon.DesolateDive;
import vesselmod.character.Vessel;
import vesselmod.misc.CustomTags;

import java.util.Objects;

import static vesselmod.VesselMod.makeID;

@AutoAdd.Seen
public class VoidtouchedFocus extends BaseRelic{
    public static final String NAME = "VoidtouchedFocus";
    public static final String ID = makeID(NAME);
    private static final int spellLimit = 1;
    private int spellCount;
    public VoidtouchedFocus() {
        super(ID, NAME, Vessel.Enums.CARD_COLOR, RelicTier.SPECIAL, LandingSound.CLINK);
        UnlockTracker.markRelicAsSeen(this.relicId);
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.hasTag(CustomTags.SPELL)) {
            this.flash();
            ++this.spellCount;
        }
    }

    public boolean canPlay(AbstractCard card) {
        if (this.spellCount >= spellLimit && card.hasTag(CustomTags.SPELL)) {
            card.cantUseMessage = this.DESCRIPTIONS[2] + spellLimit + this.DESCRIPTIONS[3];
            return false;
        } else {
            return true;
        }
    }

    public void atBattleStart() {
        this.spellCount = 0;
    }

    public void atTurnStart() {
        this.spellCount = 0;
    }

    public void onPreviewObtainCard(AbstractCard c) {
        boolean isUpgraded;

        if (Objects.equals(c.cardID, VengefulSpirit.ID)) {
            this.flash();
            isUpgraded = c.upgraded;
            c = new ShadeSoul();
            if (isUpgraded) {
                c.upgrade();
            }
        } //this shouldnt get used because theres no way to get VS in regular card rewards

        if (Objects.equals(c.cardID, DesolateDive.ID)) {
            this.flash();
            isUpgraded = c.upgraded;
            c = new DescendingDark();
            if (isUpgraded) {
                c.upgrade();
            }
        }

        if (Objects.equals(c.cardID, HowlingWraiths.ID)) {
            this.flash();
            isUpgraded = c.upgraded;
            c = new AbyssShriek();
            if (isUpgraded) {
                c.upgrade();
            }
        }
    }

    public AbstractRelic makeCopy() {
        return new VoidtouchedFocus();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + spellLimit + DESCRIPTIONS[1];
    }
}
