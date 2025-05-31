package vesselmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.actions.CardBecomesEtherealAction;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class VoidShield extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "VoidShield", //Card ID
            1, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public VoidShield() {
        super(cardInfo);
        setBlock(5, 2);
    }

    @Override
    public void use(AbstractPlayer p,AbstractMonster m) {
        for (AbstractCard card : p.hand.group) {
            if (card.type != CardType.ATTACK && !card.isEthereal && !card.selfRetain && !card.retain) {
                this.addToBot(new CardBecomesEtherealAction(card));
                card.superFlash();
            }
        }
        for (AbstractCard card : p.hand.group) {
            if (card.isEthereal || card.type != CardType.ATTACK) {
                this.addToBot(new GainBlockAction(p, p, block));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new VoidShield();
    }
}