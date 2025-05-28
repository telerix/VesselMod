package vesselmod.cards.common;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.actions.SoulChangeAction;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class SoulJar extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SoulJar", //Card ID
            0, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.COMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public SoulJar() {
        super(cardInfo);
        setExhaust(true, true);
        setRetain(true, true);
        setMagic(4,2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SoulChangeAction(p, this.magicNumber));
    }
    @Override
    public AbstractCard makeCopy() {
        return new SoulJar();
    }
}
