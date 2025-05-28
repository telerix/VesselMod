package vesselmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.misc.CustomTags;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class DashSlash extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "DashSlash", //Card ID
            2, //base cost [-1 = X, -2 = unplayable]
            CardType.ATTACK, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ALL_ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);

    public static final String ID = makeID(cardInfo.baseId);

    public DashSlash() {
        super(cardInfo);
        setDamage(13, 2);
        setMagic(1,1);
        tags.add(CustomTags.SLASH);
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("ATTACK_HEAVY"));
        this.addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        this.addToBot(new DrawCardAction(this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DashSlash();
    }
}
