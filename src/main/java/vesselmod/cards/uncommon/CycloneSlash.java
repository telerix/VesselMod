package vesselmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import vesselmod.actions.SoulChangeAction;
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
        setMagic(1,0); //soul gain per hit
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int effect = EnergyPanel.totalCount;
                if (energyOnUse != -1) {
                    effect = energyOnUse;
                }
                if (p.hasRelic(ChemicalX.ID)) {
                    effect += 2;
                }

                if (effect > 0) {
                    this.addToBot(new SFXAction(SFX_ID));
                    for(int i = 0; i < effect; ++i) {
                        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AttackEffect.BLUNT_LIGHT));
                    }
                    this.addToBot(new SoulChangeAction(p, effect));

                    if (!freeToPlayOnce) {
                        p.energy.use(EnergyPanel.totalCount);
                    }
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public AbstractCard makeCopy() {
        return new CycloneSlash();
    }
}
