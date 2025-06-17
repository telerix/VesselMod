package vesselmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import vesselmod.powers.InfectionPower;
import vesselmod.relics.GlowingWomb;

public class BlightedSlashAction extends AbstractGameAction {
    private DamageInfo info;
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    private AbstractMonster m;

    public BlightedSlashAction(AbstractMonster target, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);
        this.m = target;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.FIRE;
        this.duration = 0.01F;
    }

    public void update() {
        if (this.target == null) {
            this.isDone = true;
        } else {
            if (this.m.hasPower(InfectionPower.POWER_ID) || AbstractDungeon.player.hasPower(InfectionPower.POWER_ID) || AbstractDungeon.player.hasRelic(GlowingWomb.ID)) {
                if (this.duration == 0.01F && this.target != null && this.target.currentHealth > 0) {
                    if (this.info.type != DamageInfo.DamageType.THORNS && this.info.owner.isDying) {
                        this.isDone = true;
                        return;
                    }

                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
                }

                this.tickDuration();
                if (this.isDone && this.target != null && this.target.currentHealth > 0) {
                    if (AbstractDungeon.player.hasRelic(GlowingWomb.ID)) {
                        AbstractRelic relic = AbstractDungeon.player.getRelic(GlowingWomb.ID);
                        relic.flash();
                        this.addToTop(new RelicAboveCreatureAction(target, relic));
                    }
                    this.target.damage(this.info);
                    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                        AbstractDungeon.actionManager.clearPostCombatActions();
                    }

                    this.addToTop(new WaitAction(0.1F));
                }
            } else {
                this.isDone = true;
            }

        }
    }
}
