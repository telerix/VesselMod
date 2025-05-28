package vesselmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.powers.InfectionPower;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class ChanneledBeam extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ChanneledBeam", //Card ID
            2, //base cost [-1 = X, -2 = unplayable]
            CardType.ATTACK, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);

    public static final String ID = makeID(cardInfo.baseId);

    public ChanneledBeam() {
        super(cardInfo);
        setDamage(14, 6);
        setMagic(1,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(InfectionPower.POWER_ID)) {
            this.addToBot(new ReducePowerAction(p, p, InfectionPower.POWER_ID, p.getPower(InfectionPower.POWER_ID).amount));
        }
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.LIGHTNING));
    }
    @Override
    public AbstractCard makeCopy() {
        return new ChanneledBeam();
    }
}
