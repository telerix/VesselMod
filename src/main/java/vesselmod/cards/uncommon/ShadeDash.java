package vesselmod.cards.uncommon;

import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class ShadeDash extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ShadeDash", //Card ID
            1, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public ShadeDash() {
        super(cardInfo);
        setMagic(2, 1);
        CardModifierManager.addModifier(this, new EtherealMod());
    }

    @Override
    public void use(AbstractPlayer p,AbstractMonster m) {
        this.addToBot(new DrawCardAction(p, this.magicNumber));
        if (AbstractDungeon.player.exhaustPile.size() > 0) {
            this.addToBot(new GainBlockAction(p, p, AbstractDungeon.player.exhaustPile.size()));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ShadeDash();
    }

}