package vesselmod.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import vesselmod.relics.KingsBrand;

public class InfectionLoseHpAction extends AbstractGameAction {
    private final AbstractCreature target;

    public InfectionLoseHpAction(AbstractCreature target, AbstractCreature source, int amount, AbstractGameAction.AttackEffect effect) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = 0.33F;
        this.target = target;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            this.isDone = true;
        } else {
            if (this.duration == 0.33F && this.target.currentHealth > 0) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
            }
            this.tickDuration();

            if (this.isDone) {
                if (this.target.currentHealth > 0) {
                    this.target.tint.color = Color.CHARTREUSE.cpy();
                    this.target.tint.changeColor(Color.WHITE.cpy());
                    if (this.target == AbstractDungeon.player && this.amount > KingsBrand.EFFECT && AbstractDungeon.player.hasRelic(KingsBrand.ID)) {
                        AbstractDungeon.player.getRelic(KingsBrand.ID).flash();
                        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, AbstractDungeon.player.getRelic(KingsBrand.ID)));
                        this.target.damage(new DamageInfo(this.source, KingsBrand.EFFECT, DamageInfo.DamageType.THORNS));
                    } else {
                        this.target.damage(new DamageInfo(this.source, this.amount, DamageInfo.DamageType.THORNS));
                    }
                }

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }

                this.addToTop(new WaitAction(0.05F));
            }

        }
    }
}