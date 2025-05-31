package vesselmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vesselmod.actions.SoulChangeAction;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.misc.CustomTags;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;

public class DeepFocus extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "DeepFocus", //Card ID
            0, //base cost [-1 = X, -2 = unplayable]
            CardType.SKILL, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.SELF, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);

    public DeepFocus() {
        super(cardInfo);
        setMagic(7,0);
        setExhaust(true,true);
        setSoulCost(4, -1);
        tags.add(CardTags.HEALING);
        tags.add(CustomTags.COST_SOUL);
        tags.add(CustomTags.FOCUS);
    }

    @Override
    public void use(AbstractPlayer p,AbstractMonster m) {
        this.addToBot(new SoulChangeAction(p, this.soulCost, this.freeSoulCost()));
        this.addToBot(new HealAction(p, p, this.magicNumber));
    }
    @Override
    public AbstractCard makeCopy() {
        return new DeepFocus();
    }

    
}