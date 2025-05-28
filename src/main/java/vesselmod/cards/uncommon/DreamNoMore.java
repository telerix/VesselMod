package vesselmod.cards.uncommon;

import basemod.cardmods.InnateMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.powers.DreamNoMorePower;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class DreamNoMore extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "DreamNoMore", //Card ID
            1, //base cost [-1 = X, -2 = unplayable]
            CardType.POWER, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);

    public static final String ID = makeID(cardInfo.baseId);

    public DreamNoMore() {
        super(cardInfo);
        setMagic(1, 0);
        this.cardsToPreview = new VoidCard();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            CardModifierManager.addModifier(this, new InnateMod());
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new DreamNoMorePower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy(){
        return new DreamNoMore();
    }
}
