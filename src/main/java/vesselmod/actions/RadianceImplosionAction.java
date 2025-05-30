package vesselmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import vesselmod.powers.InfectionPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class RadianceImplosionAction extends AbstractGameAction {
    private final boolean isUpgraded;
    private final int energyOnUse;
    private final boolean freeToPlayOnce;


    public RadianceImplosionAction(AbstractCreature target, boolean isUpgraded, int energyOnUse, boolean freeToPlayOnce) {
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FAST;
        this.isUpgraded = isUpgraded;
        this.target = target;
        this.energyOnUse = energyOnUse;
        this.freeToPlayOnce = freeToPlayOnce;
    }

    public void update() {
        if (this.target != null && this.target.hasPower(InfectionPower.POWER_ID)) {
            int infectionStacks = this.target.getPower(InfectionPower.POWER_ID).amount;
            int effect = EnergyPanel.totalCount;
            if (this.energyOnUse != -1) {
                effect = this.energyOnUse;
            }

            if (isUpgraded) {
                ++effect;
            }

            if (player.hasRelic(ChemicalX.ID)) {
                effect += ChemicalX.BOOST;
                player.getRelic(ChemicalX.ID).flash();
            }

            if (effect > 0) {
                this.addToBot(new DamageAllEnemiesAction(player, effect * infectionStacks, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
            }

            if (!this.freeToPlayOnce) {
                player.energy.use(EnergyPanel.totalCount);
            }
        }
        this.isDone = true;
    }
}