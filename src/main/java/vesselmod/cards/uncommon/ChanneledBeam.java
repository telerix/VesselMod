package vesselmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.misc.CustomTags;
import vesselmod.powers.InfectionPower;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class ChanneledBeam extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ChanneledBeam", //Card ID
            2, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ALL_ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);

    public static final String ID = makeID(cardInfo.baseId);

    public ChanneledBeam() {
        super(cardInfo);
        setMagic(3,1); //#times infection is applied
        tags.add(CustomTags.INFECT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(InfectionPower.POWER_ID)) {
            this.addToBot(new RemoveSpecificPowerAction(p, p, InfectionPower.POWER_ID));
        }
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!monster.isDead && !monster.isDying) {
                for (int i = 0; i < this.magicNumber; i++) {
                    this.addToBot(new ApplyPowerAction(monster, p, new InfectionPower(monster, p, 1), 1, true));
                }
            }
        }
    }
    @Override
    public AbstractCard makeCopy() {
        return new ChanneledBeam();
    }
}
