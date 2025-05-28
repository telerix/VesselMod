package vesselmod.cards.common;

import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.actions.SoulChangeAction;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.misc.CustomTags;
import vesselmod.misc.SoulMechanics;
import vesselmod.modifiers.SpellDamage;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;


public class SoulFlukeswarm extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SoulFlukeswarm", //Card ID
            1, //base cost [-1 = X, -2 = unplayable]
            CardType.ATTACK, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.COMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public SoulFlukeswarm() {
        super(cardInfo);
        setDamage(3, 0);
        setMagic(4,1);
        tags.add(CustomTags.SPELL);
        tags.add(CustomTags.COST_SOUL);
        DamageModifierManager.addModifier(this, new SpellDamage());
        setSoulCost(2, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SoulChangeAction(p, this.soulCost, this.freeToPlayOnce));
        for(int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SoulFlukeswarm();
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (canUse && SoulMechanics.soulCount < this.soulCost) {
            this.cantUseMessage = SoulMechanics.noSoulMessage;
            return false;
        } else {
            return canUse;
        }
    }
}