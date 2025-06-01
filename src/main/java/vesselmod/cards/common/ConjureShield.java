package vesselmod.cards.common;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.actions.SoulChangeAction;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.misc.CustomTags;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;
import static vesselmod.misc.SoulMechanics.soulCount;

public class ConjureShield extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ConjureShield", //Card ID
            0, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.COMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public ConjureShield() {
        super(cardInfo);
        setBlock(7,3);
        setSoulCost(1,0);
        setMagic(2,0); //soul increase when soulcount = 0
        tags.add(CustomTags.COST_SOUL);
        tags.add(CustomTags.COST_SOUL_ALT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (soulCount == 0) {
            this.addToBot(new SoulChangeAction(p, this.magicNumber));
        }
        else {
            this.addToBot(new SoulChangeAction(p, this.soulCost, this.freeSoulCost()));
            addToBot(new GainBlockAction(p ,p ,this.block));
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new ConjureShield();
    }
}