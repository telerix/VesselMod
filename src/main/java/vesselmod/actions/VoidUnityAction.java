package vesselmod.actions;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import vesselmod.cards.BaseCard;
import vesselmod.misc.CustomTags;
import vesselmod.modifiers.FreeSoulCostMod;

public class VoidUnityAction extends AbstractGameAction {
    private final AbstractCard card;
    public VoidUnityAction (AbstractCard card) {
        this.card = card;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if (card.hasTag(CustomTags.COST_SOUL)) {
            if (((BaseCard)card).soulCost > 0) {
                CardModifierManager.addModifier(this.card, new FreeSoulCostMod());
            }
        }
        card.exhaust = true;
        this.addToBot(new NewQueueCardAction(card, true, false, true));
        this.isDone = true;
    }
}
