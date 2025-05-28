package vesselmod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.red.Exhume;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import vesselmod.cards.rare.Reconstruct;

import java.util.ArrayList;
import java.util.Iterator;

import static vesselmod.VesselMod.makeID;

public class ReconstructAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final String ACTION_ID = makeID("ReconstructAction");
    private final ArrayList<AbstractCard> unreconstructables = new ArrayList<>();

    public ReconstructAction() {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        Iterator<AbstractCard> it;
        AbstractCard cards;

        if (this.duration == Settings.ACTION_DUR_FAST) { //select cards
            if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
                AbstractDungeon.player.createHandIsFullDialog();
                this.isDone = true;
            } else if (this.p.exhaustPile.isEmpty()) {
                this.isDone = true;

            } else if (this.p.exhaustPile.size() == 1) {
                if (!(this.p.exhaustPile.group.get(0)).cardID.equals(Exhume.ID) && (!(this.p.exhaustPile.group.get(0)).cardID.equals(Reconstruct.ID))) {
                    AbstractCard c = this.p.exhaustPile.getTopCard();
                    c.unfadeOut();
                    this.addToBot(new MakeTempCardInHandAction(c));

                    c.unhover();
                    c.fadingOut = false;
                }
                this.isDone = true;
            } else {
                it = this.p.exhaustPile.group.iterator();

                while (it.hasNext()) { //removes all exhumes and unreconstructables into
                    cards = it.next();
                    cards.stopGlowing();
                    cards.unhover();
                    cards.unfadeOut();
                    if (cards.cardID.equals(Exhume.ID) || cards.cardID.equals(Reconstruct.ID)) {
                        it.remove();
                        this.unreconstructables.add(cards);
                    }
                }

                if (this.p.exhaustPile.isEmpty()) { //only reconstruct/exhumes in exhaust pile
                    this.p.exhaustPile.group.addAll(this.unreconstructables);
                    this.unreconstructables.clear();
                    this.isDone = true;
                } else {
                    AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, 1, TEXT[0], false);
                    this.tickDuration();
                }
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for(it = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); it.hasNext(); cards.unhover()) {
                    cards = it.next();
                    this.addToBot(new MakeTempCardInHandAction(cards));
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
                this.p.exhaustPile.group.addAll(this.unreconstructables);
                this.unreconstructables.clear();

                for (it = this.p.exhaustPile.group.iterator(); it.hasNext(); cards.target_y = 0.0F) {
                    cards = it.next();
                    cards.unhover();
                    cards.target_x = (float) CardGroup.DISCARD_PILE_X;
                }
            }
            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ACTION_ID);
        TEXT = uiStrings.TEXT;
    }
}
