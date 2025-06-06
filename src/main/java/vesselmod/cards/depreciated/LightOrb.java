package vesselmod.cards.depreciated;

import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.actions.depreciated.LightOrbAction;
import vesselmod.actions.SoulChangeAction;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.misc.CustomTags;
import vesselmod.modifiers.SpellDamage;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class LightOrb extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "LightOrb", //Card ID
            0, //base cost [-1 = X, -2 = unplayable]
            CardType.ATTACK, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public LightOrb() {
        super(cardInfo);
        this.misc = 4; //base amt
        this.baseDamage = this.misc;
        setMagic(2, 1); //stacking
        setSoulCost(2,0);
        tags.add(CustomTags.SPELL);
        tags.add(CustomTags.COST_SOUL);
        setExhaust(true,true);
        DamageModifierManager.addModifier(this, new SpellDamage());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SoulChangeAction(p, this.soulCost, this.freeSoulCost()));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void onLoadedMisc() {
        this.baseDamage = this.misc; //meta scaling on game load fix
    }


    @Override
    public void triggerOnExhaust() {
        this.addToBot(new LightOrbAction(this.uuid, this.magicNumber));
    }

    @Override
    public void applyPowers() {
        this.baseBlock = this.misc;
        super.applyPowers();
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new LightOrb();
    }
}
