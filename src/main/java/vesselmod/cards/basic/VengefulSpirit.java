package vesselmod.cards.basic;

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


public class VengefulSpirit extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "VengefulSpirit", //Card ID
            1, //base cost [-1 = X, -2 = unplayable]
            CardType.ATTACK, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.BASIC, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public VengefulSpirit() {
        super(cardInfo);
        setDamage(15, 5);
        tags.add(CustomTags.SPELL);
        tags.add(CustomTags.COST_SOUL);
        DamageModifierManager.addModifier(this, new SpellDamage());
        setSoulCost(3, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SoulChangeAction(p, this.soulCost, this.freeToPlayOnce));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public AbstractCard makeCopy() {
        return new VengefulSpirit();
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