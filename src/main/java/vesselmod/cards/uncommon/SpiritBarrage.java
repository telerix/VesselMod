package vesselmod.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import vesselmod.actions.SoulChangeAction;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.misc.CustomTags;
import vesselmod.modifiers.SpellDamage;
import vesselmod.powers.VoidFormPower;
import vesselmod.util.CardInfo;

import static vesselmod.VesselMod.makeID;
import static vesselmod.misc.SoulMechanics.soulCount;

public class SpiritBarrage extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SpiritBarrage", //Card ID
            1, //base cost [-1 = X, -2 = unplayable]
            CardType.ATTACK, //[ATTACK/SKILL/POWER/CURSE/STATUS]
            CardTarget.ENEMY, //[ENEMY/ALL_ENEMY]
            CardRarity.UNCOMMON, //[BASIC/COMMON/UNCOMMON/RARE/SPECIAL(event)/CURSE]
            Vessel.Enums.CARD_COLOR);
    public static final String ID = makeID(cardInfo.baseId);
    private boolean inVoidForm = false; //setting this to false s.t. it doesnt do funny stuff when added to hand directly

    public SpiritBarrage() {
        super(cardInfo);
        setExhaust(true, true);
        setDamage(5,2);
        setSoulCost(soulCount, 0);
        tags.add(CustomTags.COST_SOUL);
        tags.add(CustomTags.SPELL);
        DamageModifierManager.addModifier(this, new SpellDamage());
    }

    public void triggerWhenDrawn() {
        this.inVoidForm = AbstractDungeon.player.hasPower(VoidFormPower.POWER_ID);
    }

    public void applyPowers() {
        super.applyPowers();
        if (this.inVoidForm) {
            this.soulCost = 0;
        } else {
            this.soulCost = soulCount;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int currentSoul = soulCount;
        if (this.soulCost > 0) {
            this.addToBot(new SoulChangeAction(p, currentSoul, this.freeSoulCost()));
        }

        if (p.hasRelic(ChemicalX.ID)) {
            currentSoul += ChemicalX.BOOST;
            p.getRelic(ChemicalX.ID).flash();
        }

        for (int i = 0; i < currentSoul; i++) {
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT, true));
        }
    }

    @Override
    public AbstractCard makeCopy(){
        return new SpiritBarrage();
    }
}