package vesselmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import vesselmod.actions.SoulChangeAction;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.misc.CustomTags;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;
import static vesselmod.misc.SoulMechanics.soulCount;

public class Empower extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Empower", //Card ID
            3, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.RARE, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public Empower() {
        super(cardInfo);
        setExhaust(true, true);
        setSoulCost(soulCount, 0);
        tags.add(CustomTags.COST_SOUL);
        setCostUpgrade(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int currentSoul = soulCount;
        this.addToBot(new SoulChangeAction(p, currentSoul, this.freeSoulCost()));;
        int applyAmount = (int)Math.floor(currentSoul / 3f) * 2;
        if (applyAmount > 0) {
            this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, applyAmount), applyAmount));
            this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, applyAmount), applyAmount));
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new Empower();
    }
}