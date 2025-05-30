package vesselmod.cards.uncommon;

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
import vesselmod.modifiers.SpellDamage;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class SoulBoomerang extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SoulBoomerang", //Card ID
            0, //base cost [-1 = X, -2 = unplayable]
            CardType.ATTACK, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);

    public static final String ID = makeID(cardInfo.baseId);

    public SoulBoomerang() {
        super(cardInfo);
        setDamage(4, 0);
        setSoulCost(2,0);
        setMagic(2,1);
        tags.add(CustomTags.SPELL);
        tags.add(CustomTags.COST_SOUL);
        DamageModifierManager.addModifier(this, new SpellDamage());
        this.shuffleBackIntoDrawPile = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SoulChangeAction(p, this.soulCost, this.freeSoulCost()));
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        //this.addToBot(new ModifyDamageAction(this.uuid, this.magicNumber));

    }

    @Override
    public AbstractCard makeCopy() {
        return new SoulBoomerang();
    }
}
