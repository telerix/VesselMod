package vesselmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.powers.MantisTrainingPower;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class MantisTraining extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "MantisTraining", //Card ID
            1, //base cost [-1 = X, -2 = unplayable]
            CardType.POWER, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);

    public static final String ID = makeID(cardInfo.baseId);

    public MantisTraining() {
        super(cardInfo);
        setMagic(4, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new MantisTrainingPower(p, this.magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy(){
        return new MantisTraining();
    }
}
