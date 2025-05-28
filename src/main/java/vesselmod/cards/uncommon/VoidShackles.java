package vesselmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class VoidShackles extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "VoidShackles", //Card ID
            1, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public VoidShackles() {
        super(cardInfo);
        setMagic(8,0);
        setExhaust(true, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -this.magicNumber), -this.magicNumber));
        if (m != null && !m.hasPower("Artifact")) {
            this.addToBot(new ApplyPowerAction(m, p, new GainStrengthPower(m, this.magicNumber), this.magicNumber));
        }
        if (upgraded) {
            this.addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -2), -2));
        }
    }
    @Override
    public AbstractCard makeCopy() {
        return new VoidShackles();
    }
}
