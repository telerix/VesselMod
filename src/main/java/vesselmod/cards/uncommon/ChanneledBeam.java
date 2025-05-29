package vesselmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.powers.InfectionPower;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class ChanneledBeam extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ChanneledBeam", //Card ID
            1, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ALL_ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);

    public static final String ID = makeID(cardInfo.baseId);

    public ChanneledBeam() {
        super(cardInfo);
        setCostUpgrade(0);
        setExhaust(true,true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int playerInfection = 0;
        if (p.hasPower(InfectionPower.POWER_ID)) {
            playerInfection = p.getPower(InfectionPower.POWER_ID).amount;
            this.addToBot(new ReducePowerAction(p, p, InfectionPower.POWER_ID, p.getPower(InfectionPower.POWER_ID).amount));
        }
        if (playerInfection > 0) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDead && !monster.isDying) {
                    this.addToBot(new ApplyPowerAction(monster, p, new InfectionPower(monster, p, playerInfection), playerInfection, true));
                }
            }
        }
    }
    @Override
    public AbstractCard makeCopy() {
        return new ChanneledBeam();
    }
}
