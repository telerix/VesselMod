package vesselmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import vesselmod.actions.VoidUnityAction;

import static vesselmod.VesselMod.makeID;

public class VoidUnityPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("VoidUnity");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static boolean DrawAtEndOfTurn = false;

    public VoidUnityPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner);
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        DrawAtEndOfTurn = true;
        if (isPlayer && AbstractDungeon.player.hand.group.stream().anyMatch(card -> (card.isEthereal && card.cost != -2))) {
            this.addToBot(new VFXAction(AbstractDungeon.player, new BorderLongFlashEffect(Color.DARK_GRAY), 0.0F, true));
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card.isEthereal && card.cost != -2) {
                    this.flash();
                    this.addToBot(new VoidUnityAction(card));
                    this.addToBot(new WaitAction(0.1f));
                }
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            DrawAtEndOfTurn = false;
        }
    }

    @Override
    public void onCardDraw(AbstractCard card) { //retain all ethereal cards drawn from autoplay
        if (DrawAtEndOfTurn) {
            card.retain = true;
        }
    }

    public void updateDescription(){
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new VoidUnityPower(owner);
    }
}
