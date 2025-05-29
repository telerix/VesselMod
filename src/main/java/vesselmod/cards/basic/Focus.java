package vesselmod.cards.basic;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.actions.SoulChangeAction;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.misc.CustomTags;
import vesselmod.misc.SoulMechanics;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class Focus extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Focus", //Card ID
            0, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.BASIC, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public Focus() {
        super(cardInfo);
        setMagic(1, 1);
        setExhaust(true,true);
        setSoulCost(2, 0);
        tags.add(CustomTags.COST_SOUL);
        tags.add(CustomTags.FOCUS);
    }

    @Override
    public void use(AbstractPlayer p,AbstractMonster m) {
        this.addToBot(new SoulChangeAction(p, this.soulCost, this.freeSoulCost()));
        this.addToBot(new GainEnergyAction(2));
        this.addToBot(new DrawCardAction(p, this.magicNumber));
    }
    @Override
    public AbstractCard makeCopy() {
        return new Focus();
    }

    
}