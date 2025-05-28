package vesselmod.cards.uncommon;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.modifiers.InnateEtherealMod;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class PreEmptiveStrike extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "PreEmptiveStrike", //Card ID
            0, //base cost [-1 = X, -2 = unplayable]
            CardType.ATTACK, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);

    public static final String ID = makeID(cardInfo.baseId);

    public PreEmptiveStrike() {
        super(cardInfo);
        CardModifierManager.addModifier(this, new InnateEtherealMod(true));
        setDamage(5, 2);
        setMagic(2,0);
        setExhaust(true, true);
        tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new PreEmptiveStrike();
    }
}
