package vesselmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.powers.EmanatingGlowPower;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class EmanatingGlow extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "EmanatingGlow", //Card ID
            1, //base cost [-1 = X, -2 = unplayable]
            CardType.POWER, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);

    public static final String ID = makeID(cardInfo.baseId);

    public EmanatingGlow() {
        super(cardInfo);
        setMagic(1, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new EmanatingGlowPower(p, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy(){
        return new EmanatingGlow();
    }
}
