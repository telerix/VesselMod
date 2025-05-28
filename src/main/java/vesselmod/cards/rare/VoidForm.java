package vesselmod.cards.rare;

import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.powers.SoulDrainPower;
import vesselmod.powers.VoidFormPower;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class VoidForm extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "VoidForm", //Card ID
            3, //base cost [-1 = X, -2 = unplayable]
            CardType.POWER, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.RARE, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);

    public static final String ID = makeID(cardInfo.baseId);

    public VoidForm() {
        super(cardInfo);
        setMagic(2,0);
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
        boolean powerExists = false;

        for (AbstractPower pow : p.powers) {
            if (pow.ID.equals(VoidFormPower.POWER_ID)) {
                powerExists = true;
                break;
            }
        }
        if (!powerExists) {
            this.addToBot(new ApplyPowerAction(p, p, new VoidFormPower(p)));
        }
        this.addToBot(new ApplyPowerAction(p, p, new SoulDrainPower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy(){
        return new VoidForm();
    }
}
