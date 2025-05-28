package vesselmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.powers.SpellMasteryPower;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class SpellMastery extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SpellMastery", //Card ID
            2, //base cost [-1 = X, -2 = unplayable]
            CardType.POWER, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.RARE, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);

    public static final String ID = makeID(cardInfo.baseId);

    public SpellMastery() {
        super(cardInfo);
        setMagic(30,20);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new SpellMasteryPower(p, this.magicNumber/10)));
    }

    @Override
    public AbstractCard makeCopy(){
        return new SpellMastery();
    }
}
