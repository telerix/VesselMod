package vesselmod.cards.rare;

import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.powers.CorruptedNailPower;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class CorruptedNail extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "CorruptedNail", //Card ID
            2, //base cost [-1 = X, -2 = unplayable]
            CardType.POWER, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.RARE, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);

    public static final String ID = makeID(cardInfo.baseId);

    public CorruptedNail() {
        super(cardInfo);
        setMagic(1, 0);
        CardModifierManager.addModifier(this, new EtherealMod());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            CardModifierManager.removeModifiersById(this, EtherealMod.ID, true);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("ATTACK_FIRE"));
        this.addToBot(new VFXAction(p, new BorderLongFlashEffect(Color.YELLOW), 0.0F, true));
        this.addToBot(new ApplyPowerAction(p, p, new CorruptedNailPower(p, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy(){
        return new CorruptedNail();
    }
}
