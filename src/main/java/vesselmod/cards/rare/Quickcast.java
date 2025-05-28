package vesselmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.powers.QuickcastPower;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class Quickcast extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Quickcast", //Card ID
            1, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.RARE, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public Quickcast() {
        super(cardInfo);
        setMagic(1,0);
        setExhaust(true, false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new QuickcastPower(p, this.magicNumber), this.magicNumber));
    }
    @Override
    public AbstractCard makeCopy() {
        return new Quickcast();
    }
}
