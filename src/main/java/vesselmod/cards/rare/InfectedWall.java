package vesselmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.powers.InfectionPower;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class InfectedWall extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "InfectedWall", //Card ID
            2, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.RARE, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public InfectedWall() {
        super(cardInfo);
        setBlock(24,10);
        setExhaust(true, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, this.block));

        if (p.hasPower(InfectionPower.POWER_ID)) {
        int infectionStacks = p.getPower(InfectionPower.POWER_ID).amount;
        this.addToBot(new ApplyPowerAction(p, p, new MetallicizePower(p, 2 * infectionStacks)));

        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new InfectedWall();
    }
}