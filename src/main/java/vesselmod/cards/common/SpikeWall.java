package vesselmod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.powers.SpikeWallPower;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class SpikeWall extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SpikeWall", //Card ID
            1, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.COMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public SpikeWall() {
        super(cardInfo);
        setBlock(7,3);
        setMagic(2,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p,p,block));
        this.addToBot(new ApplyPowerAction(p, p, new SpikeWallPower(p, this.magicNumber), this.magicNumber));
    }
    @Override
    public AbstractCard makeCopy() {
        return new SpikeWall();
    }
}
