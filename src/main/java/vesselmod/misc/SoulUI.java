package vesselmod.misc;

import basemod.BaseMod;
import basemod.ClickableUIElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;

import static vesselmod.VesselMod.resourcePath;

public class SoulUI extends ClickableUIElement {
    public Hitbox hb;
    private FrameBuffer fbo;
    private static final float hb_w = 130.0F * Settings.scale;
    private static final float hb_h = 130.0F * Settings.scale;
    private static final float baseX = 30.0F * Settings.scale;
    private static final float baseY = 195.0F * Settings.scale;
    private final float x = baseX;
    private final float y = baseY;
    private static final int IMG_DIM = 256;
    private static int rotation;
    public static float fontScale = 1.0F;
    public static Texture SoulVesselBackground;
    private static final Texture BrokenVessel = ImageMaster.loadImage(resourcePath("misc/soul_meter_broken.png"));
    private static final Texture PureVessel = ImageMaster.loadImage(resourcePath("misc/soul_meter_gm.png"));


    private static Texture loadVessel() {
        if (AbstractDungeon.player.hasRelic(vesselmod.relics.PureVessel.ID)) {
           return PureVessel;
        } else {
            return BrokenVessel;
        }
    }

    public SoulUI(Texture image){
        super(image, baseX, baseY , hb_w, hb_h);
        this.image = image;
        SoulVesselBackground = loadVessel();
        hb = new Hitbox(x, y, hb_w, hb_h); //square hitbox for the soul vessel, honestly no idea what the x y does here
        //this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, IMG_DIM, IMG_DIM, false, false);
        this.setClickable(false);
    }
    public void render(SpriteBatch sb, float current_x){
        SoulVesselBackground = loadVessel();

        if (SoulMechanics.soulLimit > 0) {
            sb.draw(
                    SoulVesselBackground,
                    this.x + 55f * Settings.scale - SoulVesselBackground.getWidth() / 2F,
                    this.y + 50f * Settings.scale - SoulVesselBackground.getHeight() / 2F,
                    SoulVesselBackground.getWidth() / 2F,
                    SoulVesselBackground.getHeight() / 2F,
                    (float) SoulVesselBackground.getWidth(),
                    (float) SoulVesselBackground.getHeight(),
                    Settings.scale,
                    Settings.scale,
                    0,
                    0,
                    0,
                    SoulVesselBackground.getWidth(),
                    SoulVesselBackground.getHeight(),
                    false,
                    false); //the vessel image

            FontHelper.renderFontCentered(
                    sb,
                    FontHelper.energyNumFontBlue,
                    SoulMechanics.soulCount + "/" + SoulMechanics.soulLimit,
                    x + 55f * Settings.scale,
                    y + 50f * Settings.scale,
                    Color.WHITE,
                    fontScale); //the soul count

        }
    }

    /*private void updateHitboxPosition(float x, float y){
        hb.translate(x - 150f * Settings.scale, y - 130f * Settings.scale);
    }
     */

    @Override
    protected void onHover() {
        TipHelper.renderGenericTip(x - Settings.xScale * 20f, y + Settings.yScale * 250f, BaseMod.getKeywordTitle("vesselmod:soul_vessel"), BaseMod.getKeywordDescription("vesselmod:soul_vessel")); //popup text
    }

    @Override
    protected void onUnhover() {
    }

    @Override
    protected void onClick() {
    }
}
