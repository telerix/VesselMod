package vesselmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.powers.InfectionPower;
import vesselmod.relics.GlowingWomb;
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
        setBlock(20,8);
        setExhaust(true, true);
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.player.hasPower(InfectionPower.POWER_ID) || AbstractDungeon.player.hasRelic(GlowingWomb.ID)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, this.block));

        if (p.hasPower(InfectionPower.POWER_ID) || p.hasRelic(GlowingWomb.ID)) {
            if (p.hasRelic(GlowingWomb.ID)) {
                AbstractRelic tmp = p.getRelic(GlowingWomb.ID);
                tmp.flash();
                this.addToBot(new RelicAboveCreatureAction(p, tmp));
            }
            this.addToBot(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, this.block), this.block));
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new InfectedWall();
    }
}