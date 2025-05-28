package vesselmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.actions.SoulChangeAction;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class Rejuvenate extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Rejuvenate", //Card ID
            0, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.RARE, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public Rejuvenate() {
        super(cardInfo);
        setExhaust(true, true);
        setMagic(3,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SoulChangeAction(p, this.magicNumber));
        this.addToBot(new DrawCardAction(p,3));
    }
    @Override
    public AbstractCard makeCopy() {
        return new Rejuvenate();
    }
}
