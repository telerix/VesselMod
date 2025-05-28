package vesselmod.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.actions.RadianceImplosionAction;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class RadianceImplosion extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RadianceImplosion", //Card ID
            -1, //base cost [-1 = X, -2 = unplayable]
            CardType.ATTACK, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.RARE, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public RadianceImplosion() {
        super(cardInfo);
        setExhaust(true, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RadianceImplosionAction(m, upgraded, this.energyOnUse, false));
    }

    @Override
    public AbstractCard makeCopy(){
        return new RadianceImplosion();
    }
}