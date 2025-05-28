package vesselmod.events;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import vesselmod.cards.basic.VengefulSpirit;
import vesselmod.cards.event.ShadeSoul;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static vesselmod.VesselMod.makeID;

public class MysteriousDevice extends AbstractImageEvent {
    public static final String ID = makeID("MysteriousDevice");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private final List<String> shadeSoul;
    private int maxHpLoss;
    private CurScreen screen;


    public MysteriousDevice() {
        super(title, DESCRIPTIONS[0], "vesselmod/events/MysteriousDevice.jpg");
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.maxHpLoss = MathUtils.ceil((float) AbstractDungeon.player.maxHealth * 0.15F);
        } else {
            this.maxHpLoss = MathUtils.ceil((float) AbstractDungeon.player.maxHealth * 0.10F);
        }
        if (maxHpLoss >= AbstractDungeon.player.maxHealth) {
            maxHpLoss = AbstractDungeon.player.maxHealth - 1;
        }
        this.shadeSoul = new ArrayList<>();
        boolean hasVengefulSpirit = hasVengefulSpirit();

        this.screen = MysteriousDevice.CurScreen.INTRO;
        if (hasVengefulSpirit) {
            String VengefulSpirit = (new VengefulSpirit()).name;
            String ShadeSoul = (new ShadeSoul()).name;
            this.imageEventText.setDialogOption(OPTIONS[0] + VengefulSpirit + OPTIONS[5] + ShadeSoul + OPTIONS [6] + maxHpLoss + OPTIONS[1], new ShadeSoul());
        } else {
            this.imageEventText.setDialogOption(OPTIONS[2], true);
        }
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        getShadeSoul();
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        this.screen = MysteriousDevice.CurScreen.RESULT;
                        return;

                    default:
                        ignore();
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        this.screen = MysteriousDevice.CurScreen.RESULT;
                        return;

                }
            default:
                this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                this.imageEventText.clearRemainingOptions();
                this.openMap();
        }
    }

    protected void getShadeSoul(){
        AbstractDungeon.player.decreaseMaxHealth(this.maxHpLoss);
        this.replaceVengefulSpirit();
        logMetricObtainCardsLoseMapHP(title, OPTIONS[7], this.shadeSoul, this.maxHpLoss);
        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
    }
    protected void ignore() {
        logMetricIgnored(title);
        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
    }


    private void replaceVengefulSpirit() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;

        int i;
        for(i = masterDeck.size() - 1; i >= 0; --i) {
            AbstractCard card = masterDeck.get(i);
            if (Objects.equals(card.cardID, VengefulSpirit.ID)) {
                AbstractDungeon.player.masterDeck.removeCard(card);
                AbstractCard c = new ShadeSoul();
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                this.shadeSoul.add(c.cardID);
            }
        }
    }

    public static boolean hasVengefulSpirit() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;
        for(int i = masterDeck.size() - 1; i >= 0; --i) {
            AbstractCard card = masterDeck.get(i);
            if (Objects.equals(card.cardID, VengefulSpirit.ID)) {
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
