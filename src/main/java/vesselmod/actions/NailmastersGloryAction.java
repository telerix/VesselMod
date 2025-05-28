package vesselmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vesselmod.misc.CustomTags;

import java.util.Iterator;

public class NailmastersGloryAction extends AbstractGameAction {
    public NailmastersGloryAction() {
        this.actionType = ActionType.POWER;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        LowerAllSlashCost(p.hand);
        LowerAllSlashCost(p.drawPile);
        LowerAllSlashCost(p.discardPile);
        LowerAllSlashCost(p.exhaustPile);
        this.isDone = true;
    }

    private static void LowerAllSlashCost(CardGroup cardGroup) {
        Iterator<AbstractCard> var1 = cardGroup.group.iterator();
        AbstractCard c;
        while(var1.hasNext()) {
            c = var1.next();
            if (c.hasTag(CustomTags.SLASH) && (c.cost > 0)) {
                c.cost -= 1;
                if (c.costForTurn > 0) {
                    c.costForTurn -= 1;
                }
                c.isCostModified = true;
                c.isCostModifiedForTurn = true;
            }
        }
    }
}
