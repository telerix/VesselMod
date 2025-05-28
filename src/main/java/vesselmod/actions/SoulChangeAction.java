package vesselmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vesselmod.misc.SoulMechanics;
import vesselmod.powers.NoMindToThinkPower;

public class SoulChangeAction extends AbstractGameAction {
    private final AbstractPlayer player;
    private final int amount;
    private boolean freeToPlayOnce;

    public SoulChangeAction(AbstractPlayer player, int soulLoss, boolean freeToPlayOnce) { //for soul uses
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.player = player;
        this.amount = -soulLoss; //auto negs the soul cost
        this.freeToPlayOnce = freeToPlayOnce;
    }

    public SoulChangeAction(AbstractPlayer player, int soulGain) { //for soul gains
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.player = player;
        this.amount = soulGain;
    }

    @Override
    public void update() {
        if (this.amount > 0) { //soul gain
            int soulBeforeAdd = SoulMechanics.soulCount;
            boolean isMaxSoul = (soulBeforeAdd == SoulMechanics.soulLimit);
            SoulMechanics.soulCount += this.amount;
            if (SoulMechanics.soulCount > SoulMechanics.soulLimit) {
                SoulMechanics.soulCount = SoulMechanics.soulLimit;
            }

            AbstractPower power = player.getPower(NoMindToThinkPower.POWER_ID); //check for no mind to think
            if (power != null && !isMaxSoul) {
                ((NoMindToThinkPower)power).noMindToThinkEffect();
                power.flash();
            }

            this.isDone = true;
        } else if (this.amount < 0 && !this.freeToPlayOnce) {
            SoulMechanics.soulCount += this.amount;
            if (SoulMechanics.soulCount < 0) {
                SoulMechanics.soulCount = 0;
            }

            this.isDone = true;
        } else { //if soulcost set to 0 or free to play once
            this.isDone = true;
        }
    }
}
