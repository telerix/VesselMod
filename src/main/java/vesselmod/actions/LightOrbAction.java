package vesselmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.Iterator;
import java.util.UUID;

public class LightOrbAction extends AbstractGameAction {
    private final int miscIncrease;
    private final UUID uuid;

    public LightOrbAction(UUID targetUUID, int miscIncrease) {
        this.miscIncrease = miscIncrease;
        this.uuid = targetUUID;
    }

    public void update() {
        //damage increase crap
        Iterator<AbstractCard> var1 = AbstractDungeon.player.masterDeck.group.iterator();

        AbstractCard c;
        while (var1.hasNext()) {
            c = var1.next();
            if (c.uuid.equals(this.uuid)) {
                c.misc += this.miscIncrease;
                c.applyPowers();
                c.baseDamage = c.misc;
                c.isDamageModified = false;
            }
        }

        for (var1 = GetAllInBattleInstances.get(this.uuid).iterator(); var1.hasNext(); c.baseDamage = c.misc) {
            c = var1.next();
            c.misc += this.miscIncrease;
            c.applyPowers();
        }

        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
        }
        this.isDone = true;
    }
}
