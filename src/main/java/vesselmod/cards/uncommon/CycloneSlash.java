package vesselmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.unique.SkewerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.misc.CustomTags;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class CycloneSlash extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "CycloneSlash", //Card ID
            -1, //base cost [-1 = X, -2 = unplayable]
            CardType.ATTACK, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);

    public static final String ID = makeID(cardInfo.baseId);
    private static final String SFX_ID = makeID("Cyclone");

    public CycloneSlash() {
        super(cardInfo);
        setDamage(7, 3);
        tags.add(CustomTags.SLASH);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energyOnUse > 0 && !p.hasRelic(ChemicalX.ID)) {
            this.addToBot(new SFXAction(SFX_ID));
        }
        this.addToBot(new SkewerAction(p, m, damage, damageTypeForTurn, freeToPlayOnce, energyOnUse));
    }
    @Override
    public AbstractCard makeCopy() {
        return new CycloneSlash();
    }
}
