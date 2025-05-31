package vesselmod.actions;

import basemod.cardmods.EtherealMod;
import basemod.cardmods.InnateMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import vesselmod.modifiers.InnateEtherealMod;

public class CardBecomesEtherealAction extends AbstractGameAction {

    private final AbstractCard card;

    public CardBecomesEtherealAction(AbstractCard card) {
        this.actionType = ActionType.SPECIAL;
        this.card = card;
    }

    public void update() {
        if (!this.card.isEthereal && !this.card.retain && !card.selfRetain) {
            if (!this.card.isInnate) {
                CardModifierManager.addModifier(this.card, new EtherealMod());
            } else if (CardModifierManager.hasModifier(this.card, InnateMod.ID)) {
                CardModifierManager.removeModifiersById(this.card, InnateMod.ID, true);
                CardModifierManager.addModifier(this.card, new InnateEtherealMod());
            } else { //failsafe
                CardModifierManager.addModifier(this.card, new EtherealMod());
            }
            if (this.card.type.equals(AbstractCard.CardType.CURSE) || this.card.type.equals(AbstractCard.CardType.STATUS)) { //make autoplay burns exhaust
                this.card.exhaust = true;
            }

            this.card.isEthereal = true; //failsafe
        }
        this.isDone = true;
    }
}
