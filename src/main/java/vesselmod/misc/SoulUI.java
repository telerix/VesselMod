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

public class SoulUI extends ClickableUIElement {
    public Hitbox hb;
    private FrameBuffer fbo;
    private static float hb_w = 130.0F * Settings.scale;
    private static float hb_h = 130.0F * Settings.scale;
    private static float baseX = 60.0F * Settings.scale;
    private static float baseY = 240.0F * Settings.scale;
    private float x = baseX;
    private float y = baseY;
    private static int IMG_DIM = 256;
    private static int rotation;
    public static float fontScale = 1.0F;
    public static Texture SoulVesselBackground;
    private static final Texture BrokenVessel = ImageMaster.loadImage("vesselmod/misc/soul_meter_broken.png");
    private static final Texture PureVessel = ImageMaster.loadImage("vesselmod/misc/soul_meter_gm.png");


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
        hb = new Hitbox(x * 0.5f, y * 0.2f, hb_w, hb_h); //y this no worky
        //this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, IMG_DIM, IMG_DIM, false, false);
        this.setClickable(false);
    }
    public void render(SpriteBatch sb, float current_x){
        //rotation += 1;
        //updateHitboxPosition(x, y);
        /*sb.setBlendFunction(770, 1);
        if (SoulMechanics.soulLimit > 0 ){
            sb.draw(image, this.x - (float) image.getWidth() / 2.0F, this.y - (float) image.getWidth() / 2.5F, (float) image.getWidth() / 2.0F, (float)image.getHeight() / 2.0F,
                    (float)image.getHeight(), (float) image.getHeight(), 2.0f * Settings.scale, 2.0f * Settings.scale, rotation,0,0,45,46,false,false);
        }*/
        sb.setBlendFunction(770, 771);
        SoulVesselBackground = loadVessel();

        if (SoulMechanics.soulLimit > 0) {
            sb.draw(SoulVesselBackground, this.x - (float) image.getWidth(), this.y - (float) image.getHeight() / 1.2f, (float) SoulVesselBackground.getWidth() / 2.0F, (float) SoulVesselBackground.getHeight() / 2.0F,
                    (float) SoulVesselBackground.getWidth(), (float) SoulVesselBackground.getHeight(), 0.9f * Settings.scale, 0.9f * Settings.scale, 0, 0, 0, 504, 200, false, false);
            FontHelper.renderFontCentered(sb, FontHelper.energyNumFontBlue, SoulMechanics.soulCount + "/" + SoulMechanics.soulLimit,  current_x + x - baseX * 2.78f, y * Settings.scale * 1.185f, Color.WHITE, fontScale);

            /*FontHelper.renderFontCentered(sb, FontHelper.energyNumFontBlue, "" + SoulMechanics.soulCount, current_x + (x-image.getWidth()/5.5f) - baseX * 1.82f, y * Settings.scale * 1.04f, Color.WHITE, fontScale);
            FontHelper.renderFontCentered(sb, FontHelper.energyNumFontBlue, "" + SoulMechanics.soulLimit, current_x + (x+image.getWidth()/5.5f) - baseX * 1.82f, y * Settings.scale * 1.04f, Color.WHITE, fontScale); */

        }
    }

    /*private void updateHitboxPosition(float x, float y){
        hb.translate(x * 0.4f, y * 0.3f);
    } */
    @Override
    protected void onHover() {
        TipHelper.renderGenericTip(x - Settings.scale * 50f, y + Settings.scale * 175f, BaseMod.getKeywordTitle("vesselmod:soul_vessel"), BaseMod.getKeywordDescription("vesselmod:soul_vessel"));
    }

    @Override
    protected void onUnhover() {
    }

    @Override
    protected void onClick() {
    }
}
