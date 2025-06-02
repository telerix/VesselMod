package vesselmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;
import static vesselmod.misc.SoulMechanics.soulCount;

public class SoulShield extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SoulShield", //Card ID
            1, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public SoulShield() {
        super(cardInfo);
        setBlock(3, 1);
    }

    @Override
    public void use(AbstractPlayer p,AbstractMonster m) {
        for(int i = 0; i < soulCount; ++i) {
            this.addToBot(new GainBlockAction(p, p, this.block));
        }
    }
    @Override
    public AbstractCard makeCopy() {
        return new SoulShield();
    }
}