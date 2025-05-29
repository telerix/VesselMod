package vesselmod.cards.event;

import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.actions.SoulChangeAction;
import vesselmod.cards.BaseCard;
import vesselmod.misc.CustomTags;
import vesselmod.misc.SoulMechanics;
import vesselmod.modifiers.SpellDamage;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class ShadeSoul extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ShadeSoul", //Card ID
            1, //base cost [-1 = X, -2 = unplayable]
            CardType.ATTACK, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ALL_ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.SPECIAL, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            CardColor.COLORLESS);
    public static final String ID = makeID(cardInfo.baseId);

    public ShadeSoul() {
        super(cardInfo);
        setDamage(14, 5);
        tags.add(CustomTags.SPELL);
        tags.add(CustomTags.COST_SOUL);
        setSoulCost(3, 0);
        isMultiDamage = true;
        DamageModifierManager.addModifier(this, new SpellDamage());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SoulChangeAction(p, this.soulCost, this.freeSoulCost()));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ShadeSoul();
    }

    
}