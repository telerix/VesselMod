package vesselmod.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.ui.FtueTip;
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;

import static vesselmod.VesselMod.makeID;
import static vesselmod.VesselMod.resourcePath;

public class SoulTutorial extends FtueTip {
    public static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString(makeID("SoulTutorial"));
    public static final String[] TEXT = tutorialStrings.TEXT;
    public static final String[] LABEL = tutorialStrings.LABEL;

    private final Color screen = Color.valueOf("1c262a00");
    private float x, targetX, startX;
    private float scrollTimer = 0f;
    private static final float SCROLL_TIME = 0.3f;
    private int currentSlot = 0;

    private static final TextureRegion imgp1 = new TextureRegion(new Texture(resourcePath("tutorial/img_p1.png")));
    private static final TextureRegion imgp2 = new TextureRegion(new Texture(resourcePath("tutorial/img_p2.png")));
    private static final TextureRegion imgp3 = new TextureRegion(new Texture(resourcePath("tutorial/img_p3.png")));
    private static final String textp1 = TEXT[0];
    private static final String textp2 = TEXT[1];
    private static final String textp3 = TEXT[2];
    private static final String labelnext = LABEL[0];
    private static final String labelend = LABEL[1];
    private static final String labelpage = LABEL[2];
    private static final String labelpageend = LABEL[3];
    private static final String labeltitle = LABEL[4];

    private static final int tutorialPage = 3; //adjust this to add pages

    public SoulTutorial() {
        AbstractDungeon.player.releaseCard();
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.FTUE;
        AbstractDungeon.overlayMenu.showBlackScreen();
        x = 0f;
        AbstractDungeon.overlayMenu.proceedButton.show();
        AbstractDungeon.overlayMenu.proceedButton.setLabel(labelnext);
    }

    @Override
    public void update() {
        if (screen.a != 0.8f) {
            screen.a += Gdx.graphics.getDeltaTime();
            if (screen.a > 0.8f) {
                screen.a = 0.8f;
            }
        }

        if (AbstractDungeon.overlayMenu.proceedButton.isHovered && InputHelper.justClickedLeft
                || CInputActionSet.proceed.isJustPressed()) {
            CInputActionSet.proceed.unpress();
            if (currentSlot == -tutorialPage + 1) {
                CardCrawlGame.sound.play("DECK_CLOSE");
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.overlayMenu.proceedButton.hide();
                AbstractDungeon.effectList.clear();
                AbstractDungeon.topLevelEffects.add(new BattleStartEffect(false));
                return;
            }
            AbstractDungeon.overlayMenu.proceedButton.hideInstantly();
            AbstractDungeon.overlayMenu.proceedButton.show();
            CardCrawlGame.sound.play("DECK_CLOSE");
            currentSlot--;
            startX = x;
            targetX = currentSlot * Settings.WIDTH;
            scrollTimer = SCROLL_TIME;

            if (currentSlot == -tutorialPage + 1) {
                AbstractDungeon.overlayMenu.proceedButton.setLabel(labelend);
            }
        }

        if (scrollTimer != 0f) {
            scrollTimer -= Gdx.graphics.getDeltaTime();
            if (scrollTimer < 0f) {
                scrollTimer = 0f;
            }
        }

        x = Interpolation.fade.apply(targetX, startX, scrollTimer / SCROLL_TIME);
    }

    @Override
    public void render(SpriteBatch sb) {
        float x1 = 567f * Settings.scale;
        float x2 = x1 + Settings.WIDTH;
        float x3 = x2 + Settings.WIDTH;
        sb.setColor(screen);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0f, 0f, Settings.WIDTH, Settings.HEIGHT);

        sb.setColor(Color.WHITE);
        sb.draw(imgp1, x + x1 - imgp1.getRegionWidth() / 2f, Settings.HEIGHT / 2f - imgp1.getRegionHeight() / 2f, imgp1.getRegionWidth() / 2f, imgp1.getRegionHeight() / 2f, imgp1.getRegionWidth(), imgp1.getRegionHeight(), Settings.scale, Settings.scale, 0);
        sb.draw(imgp2, x + x2 - imgp2.getRegionWidth() / 2f, Settings.HEIGHT / 2f - imgp2.getRegionHeight() / 2f, imgp2.getRegionWidth() / 2f, imgp2.getRegionHeight() / 2f, imgp2.getRegionWidth(), imgp2.getRegionHeight(), Settings.scale, Settings.scale, 0);
        sb.draw(imgp3, x + x3 - imgp3.getRegionWidth() / 2f, Settings.HEIGHT / 2f - imgp3.getRegionHeight() / 2f, imgp3.getRegionWidth() / 2f, imgp3.getRegionHeight() / 2f, imgp3.getRegionWidth(), imgp3.getRegionHeight(), Settings.scale, Settings.scale, 0);


        float offsetY = 0f;
        if (Settings.BIG_TEXT_MODE) {
            offsetY = 110f * Settings.scale;
        }
        //p1
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, textp1, x + x1 + 350f * Settings.scale, Settings.HEIGHT / 2f - FontHelper.getSmartHeight(FontHelper.panelNameFont, textp1, 700f * Settings.scale, 40f * Settings.scale) / 2f + offsetY, 700f * Settings.scale, 40f * Settings.scale, Settings.CREAM_COLOR);

        //p2
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, textp2, x + x2 + 480f * Settings.scale, Settings.HEIGHT / 2f - FontHelper.getSmartHeight(FontHelper.panelNameFont, textp2, 700f * Settings.scale, 40f * Settings.scale) / 2f + offsetY, 700f * Settings.scale, 40f * Settings.scale, Settings.CREAM_COLOR);

        //p3
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, textp3, x + x3 + 350f * Settings.scale, Settings.HEIGHT / 2f - FontHelper.getSmartHeight(FontHelper.panelNameFont, textp3, 700f * Settings.scale, 40f * Settings.scale) / 2f + offsetY, 700f * Settings.scale, 40f * Settings.scale, Settings.CREAM_COLOR);

        FontHelper.renderFontCenteredWidth(sb, FontHelper.tipBodyFont, labelpage + Math.abs(currentSlot - 1) + labelpageend, Settings.WIDTH / 2f, Settings.HEIGHT / 2f - 400f * Settings.scale, Settings.CREAM_COLOR);

        FontHelper.renderFontCenteredWidth(sb, FontHelper.panelNameFont, labeltitle, Settings.WIDTH * 0.5f, Settings.HEIGHT / 2f - 360f * Settings.scale, Settings.GOLD_COLOR);
        AbstractDungeon.overlayMenu.proceedButton.render(sb);

    }




}
