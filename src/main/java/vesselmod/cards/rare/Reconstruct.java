package vesselmod.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.actions.ReconstructAction;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;


public class Reconstruct extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Reconstruct", //Card ID
            1, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.RARE, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public Reconstruct() {
        super(cardInfo);
        this.exhaust = true;
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ReconstructAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new Reconstruct();
    }
}