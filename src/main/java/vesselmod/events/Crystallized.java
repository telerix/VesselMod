package vesselmod.events;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import vesselmod.cards.event.DescendingDark;
import vesselmod.cards.uncommon.DesolateDive;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static vesselmod.VesselMod.makeID;

public class Crystallized extends AbstractImageEvent {
    public static final String ID = makeID("Crystallized");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private final List<String> descendingDark;
    private final int hpLoss;
    private CurScreen screen;


    public Crystallized() {
        super(title, DESCRIPTIONS[0], "vesselmod/events/Crystallized.jpg");
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.hpLoss = MathUtils.ceil((float) AbstractDungeon.player.maxHealth * 0.20F);
        } else {
            this.hpLoss = MathUtils.ceil((float) AbstractDungeon.player.maxHealth * 0.15F);
        }
        this.descendingDark = new ArrayList<>();
        boolean hasDesolateDive = hasDesolateDive();

        this.screen = Crystallized.CurScreen.INTRO;
        if (hasDesolateDive) {
            String DesolateDive = (new DesolateDive()).name;
            String DescendingDark = (new DescendingDark()).name;
            this.imageEventText.setDialogOption(OPTIONS[0] + DesolateDive + OPTIONS[1] + DescendingDark + OPTIONS [2] + hpLoss + OPTIONS[3], new DescendingDark());
        } else {
            this.imageEventText.setDialogOption(OPTIONS[4], true);
        }
        this.imageEventText.setDialogOption(OPTIONS[5]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        getDescendingDark();
                        this.imageEventText.updateDialogOption(0, OPTIONS[6]);
                        this.imageEventText.clearRemainingOptions();
                        this.screen = Crystallized.CurScreen.RESULT;
                        return;

                    default:
                        ignore();
                        this.imageEventText.updateDialogOption(0, OPTIONS[6]);
                        this.imageEventText.clearRemainingOptions();
                        this.screen = Crystallized.CurScreen.RESULT;
                        return;

                }
            default:
                this.imageEventText.updateDialogOption(0, OPTIONS[6]);
                this.imageEventText.clearRemainingOptions();
                this.openMap();
        }
    }

    protected void getDescendingDark(){
        AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, this.hpLoss));
        AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractGameAction.AttackEffect.SMASH));
        this.replaceDesolateDive();
        logMetricObtainCardsLoseMapHP(Crystallized.title, OPTIONS[7], this.descendingDark, this.hpLoss);
        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
    }
    protected void ignore() {
        logMetricIgnored(Crystallized.title);
        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
    }


    private void replaceDesolateDive() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;

        int i;
        for(i = masterDeck.size() - 1; i >= 0; --i) {
            AbstractCard card = masterDeck.get(i);
            if (Objects.equals(card.cardID, DesolateDive.ID)) {
                AbstractDungeon.player.masterDeck.removeCard(card);
                AbstractCard c = new DescendingDark();
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                this.descendingDark.add(c.cardID);
            }
        }
    }

    public static boolean hasDesolateDive() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;
        int i;
        for(i = masterDeck.size() - 1; i >= 0; --i) {
            AbstractCard card = masterDeck.get(i);
            if (Objects.equals(card.cardID, DesolateDive.ID)) {
                return true;
            }
        }
        return false;
    }

    private enum CurScreen {
        INTRO,
        RESULT;

        CurScreen() {
        }
    }
}
