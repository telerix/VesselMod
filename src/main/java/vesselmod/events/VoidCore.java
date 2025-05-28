package vesselmod.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import vesselmod.cards.basic.VengefulSpirit;
import vesselmod.cards.event.AbyssShriek;
import vesselmod.cards.event.DescendingDark;
import vesselmod.cards.event.ShadeSoul;
import vesselmod.cards.rare.HowlingWraiths;
import vesselmod.cards.uncommon.DesolateDive;
import vesselmod.relics.VoidtouchedFocus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static vesselmod.VesselMod.makeID;

public class VoidCore extends AbstractImageEvent {
    public static final String ID = makeID("VoidCore");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private final List<String> newVoidCards;
    private CurScreen screen;


    public VoidCore() {
        super(title, DESCRIPTIONS[0], "vesselmod/events/VoidCore.jpg");
        this.newVoidCards = new ArrayList<>();
        boolean hasHowlingWraiths = hasHowlingWraiths();
        boolean hasNormalSpells = (MysteriousDevice.hasVengefulSpirit() || Crystallized.hasDesolateDive() || hasHowlingWraiths());

        this.screen = VoidCore.CurScreen.INTRO;
        String VengefulSpirit = (new VengefulSpirit()).name;
        String DesolateDive = (new DesolateDive()).name;
        String HowlingWraiths = (new HowlingWraiths()).name;
        String AbyssShriek = (new AbyssShriek()).name;
        if (hasHowlingWraiths) {
            this.imageEventText.setDialogOption(OPTIONS[0] + HowlingWraiths + OPTIONS[1] + AbyssShriek + OPTIONS[2], new AbyssShriek());
        } else {
            this.imageEventText.setDialogOption(OPTIONS[7], true);
        }
        if (hasNormalSpells) {
            this.imageEventText.setDialogOption(OPTIONS[3], new VoidtouchedFocus());
        } else {
            this.imageEventText.setDialogOption(OPTIONS[8] + VengefulSpirit + OPTIONS[4] + DesolateDive + OPTIONS[5] + HowlingWraiths + OPTIONS[6], true);
        }
        this.imageEventText.setDialogOption(OPTIONS[9]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        getAbyssShriek();
                        this.imageEventText.updateDialogOption(0, OPTIONS[10]);
                        this.imageEventText.clearRemainingOptions();
                        this.screen = VoidCore.CurScreen.RESULT;
                        return;

                    case 1:
                        voidCorruptSpells();
                        this.imageEventText.updateDialogOption(0, OPTIONS[10]);
                        this.imageEventText.clearRemainingOptions();
                        this.screen = VoidCore.CurScreen.RESULT;
                        return;

                    default:
                        ignore();
                        this.imageEventText.updateDialogOption(0, OPTIONS[10]);
                        this.imageEventText.clearRemainingOptions();
                        this.screen = VoidCore.CurScreen.RESULT;
                        return;

                }
            default:
                this.imageEventText.updateDialogOption(0, OPTIONS[10]);
                this.imageEventText.clearRemainingOptions();
                this.openMap();
        }
    }

    protected void getAbyssShriek(){
        this.replaceHowlingWraiths();
        logMetricObtainCards(title, OPTIONS[11], this.newVoidCards);
        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
    }

    protected void voidCorruptSpells(){
        this.voidCorruptAllSpells();
        logMetricObtainCards(title, OPTIONS[12], this.newVoidCards);
        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
    }

    protected void ignore() {
        logMetricIgnored(title);
        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
    }


    private void replaceHowlingWraiths() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;

        int i;
        for(i = masterDeck.size() - 1; i >= 0; --i) {
            AbstractCard card = masterDeck.get(i);
            if (Objects.equals(card.cardID, HowlingWraiths.ID)) {
                AbstractDungeon.player.masterDeck.removeCard(card);
                AbstractCard c = new AbyssShriek();
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                this.newVoidCards.add(c.cardID);
            }
        }
    }

    private void voidCorruptAllSpells() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, RelicLibrary.getRelic(VoidtouchedFocus.ID).makeCopy());
        int i;
        boolean upgrade;
        for(i = masterDeck.size() - 1; i >= 0; --i) {
            AbstractCard card = masterDeck.get(i);
            if (Objects.equals(card.cardID, VengefulSpirit.ID)) {
                upgrade = card.upgraded;
                AbstractDungeon.player.masterDeck.removeCard(card);
                AbstractCard c = new ShadeSoul();
                if (upgrade) {
                    c.upgrade();
                }
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH * 0.32F, (float)Settings.HEIGHT / 2.0F));
                this.newVoidCards.add(c.cardID);
            }
            if (Objects.equals(card.cardID, DesolateDive.ID)) {
                upgrade = card.upgraded;
                AbstractDungeon.player.masterDeck.removeCard(card);
                AbstractCard c = new DescendingDark();
                if (upgrade) {
                    c.upgrade();
                }
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH * 0.5F, (float)Settings.HEIGHT / 2.0F));
                this.newVoidCards.add(c.cardID);
            }
            if (Objects.equals(card.cardID, HowlingWraiths.ID)) {
                upgrade = card.upgraded;
                AbstractDungeon.player.masterDeck.removeCard(card);
                AbstractCard c = new AbyssShriek();
                if (upgrade) {
                    c.upgrade();
                }
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH * 0.68F, (float)Settings.HEIGHT / 2.0F));
                this.newVoidCards.add(c.cardID);
            }
        }
    }
    public static boolean hasHowlingWraiths() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;
        for(int i = masterDeck.size() - 1; i >= 0; --i) {
            AbstractCard card = masterDeck.get(i);
            if (Objects.equals(card.cardID, HowlingWraiths.ID)) {
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
