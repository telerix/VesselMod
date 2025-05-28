package vesselmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.powers.FeintPower;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class Feint extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Feint", //Card ID
            1, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);

    public static final String ID = makeID(cardInfo.baseId);

    public Feint() {
        super(cardInfo);
        setMagic(5, 0);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new FeintPower(p, this.magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy(){
        return new Feint();
    }
}
