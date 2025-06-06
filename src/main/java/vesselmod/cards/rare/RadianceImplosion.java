package vesselmod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.powers.InfectionPower;
import vesselmod.util.CardInfo;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static vesselmod.VesselMod.makeID;

public class RadianceImplosion extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RadianceImplosion", //Card ID
            1, //base cost [-1 = X, -2 = unplayable]
            CardType.ATTACK, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.RARE, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);
    private static final String SFX_ID = makeID("RadBoom");

    public RadianceImplosion() {
        super(cardInfo);
        setExhaust(true, true);
        this.isMultiDamage = true;
        setMagic(2, 1); //x times target's infection
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = this.magicNumber;
        if (player.hasRelic(ChemicalX.ID)) {
            effect += ChemicalX.BOOST;
            player.getRelic(ChemicalX.ID).flash();
        }
        if (effect > 0 && m.hasPower(InfectionPower.POWER_ID)) {
            this.addToBot(new SFXAction(SFX_ID));
            this.addToBot(new DamageAllEnemiesAction(player, effect * m.getPower(InfectionPower.POWER_ID).amount, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int infectOnTarget = 0;
        if (mo.hasPower(InfectionPower.POWER_ID)) {
            infectOnTarget = mo.getPower(InfectionPower.POWER_ID).amount;
        }
        if (infectOnTarget > 0) {
            this.baseDamage = this.magicNumber * infectOnTarget;
            super.calculateCardDamage(mo);
            this.rawDescription = this.cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.rawDescription = this.cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy(){
        return new RadianceImplosion();
    }
}