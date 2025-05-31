package vesselmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;

import static vesselmod.VesselMod.makeID;

public class CardInHandToEtherealAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private boolean isRandom;
    private static final String ACTION_ID = makeID("CardInHandToEtherealAction");
    private int amount;
    private final ArrayList<AbstractCard> etherealCards = new ArrayList<>();

    public CardInHandToEtherealAction(int amount, boolean isRandom) {
        this.p = AbstractDungeon.player;
        this.isRandom = isRandom;
        this.actionType = ActionType.SPECIAL;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.p.hand.isEmpty()) {
            this.isDone = true;
            return;
        }

        Iterator<AbstractCard> var1 = this.p.hand.group.iterator();
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            while (var1.hasNext()) {
                c = var1.next();
                if (c.isEthereal) {
                    this.etherealCards.add(c);
                }
            }

            if (this.etherealCards.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }

            this.p.hand.group.removeAll(this.etherealCards);

            if (this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                this.isRandom = true;
            }

            if (!isRandom) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false, false);
                this.tickDuration();
                return;

            } else {
                for (int i = 0; i < this.amount; ++i) {
                    AbstractCard randomCardSelected = this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                    randomCardSelected.superFlash();
                    this.addToBot(new CardBecomesEtherealAction(randomCardSelected));
                    this.returnCards();
                    this.isDone = true;
                }
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while (var1.hasNext()) {
                c = var1.next();
                c.superFlash();
                this.addToBot(new CardBecomesEtherealAction(c));
                this.p.hand.addToTop(c);
            }
            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }


        this.tickDuration();

    }

    private void returnCards() {

        for (AbstractCard c : this.etherealCards) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
        TEXT = uiStrings.TEXT;
    }
}
