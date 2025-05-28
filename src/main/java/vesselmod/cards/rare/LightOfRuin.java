package vesselmod.cards.rare;

import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import vesselmod.actions.SoulChangeAction;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.misc.CustomTags;
import vesselmod.misc.SoulMechanics;
import vesselmod.modifiers.SpellDamage;
import vesselmod.powers.InfectionPower;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;


public class LightOfRuin extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "LightOfRuin", //Card ID
            2, //base cost [-1 = X, -2 = unplayable]
            CardType.ATTACK, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.RARE, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public LightOfRuin() {
        super(cardInfo);
        setDamage(15, 0);
        setMagic(10,6);
        tags.add(CustomTags.COST_SOUL);
        setSoulCost(3, 0);
        tags.add(CustomTags.INFECT);
        tags.add(CustomTags.SPELL);
        DamageModifierManager.addModifier(this, new SpellDamage());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SoulChangeAction(p, this.soulCost, this.freeToPlayOnce));
        this.addToTop(new VFXAction(new LightningEffect(m.drawX, m.drawY)));
        this.addToTop(new VFXAction(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, AbstractGameAction.AttackEffect.NONE)));
        this.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        this.addToBot(new ApplyPowerAction(m, p, new InfectionPower(m, p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new LightOfRuin();
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