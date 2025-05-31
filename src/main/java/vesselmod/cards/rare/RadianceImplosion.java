package vesselmod.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import vesselmod.actions.RadianceImplosionAction;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.powers.InfectionPower;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class RadianceImplosion extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RadianceImplosion", //Card ID
            -1, //base cost [-1 = X, -2 = unplayable]
            CardType.ATTACK, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.RARE, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public RadianceImplosion() {
        super(cardInfo);
        setExhaust(true, true);
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new RadianceImplosionAction(m, this.upgraded, this.energyOnUse, this.freeToPlayOnce));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int infectOnTarget = 0;
        String jank = "";
        if (mo.hasPower(InfectionPower.POWER_ID)) {
            infectOnTarget = mo.getPower(InfectionPower.POWER_ID).amount;
        }
        if (infectOnTarget > 0) {
            int xAmt = EnergyPanel.getCurrentEnergy();
            if (this.upgraded) {
                ++xAmt;
                jank = "+1";
            }
            if (AbstractDungeon.player.hasRelic(ChemicalX.ID)) {
                xAmt += ChemicalX.BOOST;
                AbstractDungeon.player.getRelic(ChemicalX.ID).flash();
            }
            this.baseDamage = xAmt * infectOnTarget;
            super.calculateCardDamage(mo);
            this.rawDescription = this.cardStrings.EXTENDED_DESCRIPTION[0] + jank + this.cardStrings.EXTENDED_DESCRIPTION[1];
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