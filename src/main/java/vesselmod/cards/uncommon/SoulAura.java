package vesselmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.misc.CustomTags;
import vesselmod.util.CardInfo;

import java.util.Iterator;

import static vesselmod.VesselMod.makeID;

public class SoulAura extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SoulAura", //Card ID
            3, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public SoulAura() {
        super(cardInfo);
        setBlock(13,4);
    }

    public void configureCostsOnNewCard() {
        Iterator var1 = AbstractDungeon.actionManager.cardsPlayedThisCombat.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if (c.hasTag(CustomTags.SPELL)) {
                this.updateCost(-1);
            }
        }
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard c) {
        if (c.hasTag(CustomTags.SPELL)) {
            this.updateCost(-1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
    }


    @Override
    public AbstractCard makeCopy(){
        return new SoulAura();
    }
}