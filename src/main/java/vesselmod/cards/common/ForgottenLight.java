package vesselmod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.misc.CustomTags;
import vesselmod.powers.InfectionPower;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class ForgottenLight extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ForgottenLight", //Card ID
            1, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.COMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public ForgottenLight() {
        super(cardInfo);
        setMagic(2,1);
        tags.add(CustomTags.INFECT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, p, new InfectionPower(m, p, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(m, p, new InfectionPower(m, p, this.magicNumber), this.magicNumber));
    }
    @Override
    public AbstractCard makeCopy() {
        return new ForgottenLight();
    }
}
