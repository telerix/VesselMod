package vesselmod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.util.CardInfo;

import java.util.ArrayList;

import static vesselmod.VesselMod.makeID;

public class VoidLash extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "VoidLash", //Card ID
            2, //base cost [-1 = X, -2 = unplayable]
            CardType.ATTACK, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.COMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);


    public VoidLash() {
        super(cardInfo);
        setDamage(16, 0);
        setMagic(2,1);
    }

    private int additionalDamage() {
        AbstractPlayer p = AbstractDungeon.player;
        int cardCount = 0;
        if (!p.exhaustPile.isEmpty()) cardCount += p.exhaustPile.size();
        ArrayList<AbstractCard> activeCards = new ArrayList<>();
        activeCards.addAll(p.hand.group);
        activeCards.addAll(p.drawPile.group);
        activeCards.addAll(p.discardPile.group);
        for (AbstractCard card : activeCards) {
            if (card.isEthereal) ++cardCount;
        }
        return cardCount * this.magicNumber;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += additionalDamage();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += additionalDamage();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), this.vfxAttackHeaviness()));
    }

    @Override
    public AbstractCard makeCopy(){
        return new VoidLash();
    }

    private AbstractGameAction.AttackEffect vfxAttackHeaviness() {
        if (this.additionalDamage() > 10) return AbstractGameAction.AttackEffect.BLUNT_HEAVY;
        else return AbstractGameAction.AttackEffect.BLUNT_LIGHT;
    }
}