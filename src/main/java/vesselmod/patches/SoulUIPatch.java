package vesselmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static vesselmod.misc.SoulMechanics.*;

public class SoulUIPatch {
    private static boolean Rendering = false;
    @SpirePatch2(clz = EnergyPanel.class, method = "renderOrb", paramtypes = { "com.badlogic.gdx.graphics.g2d.SpriteBatch"})
    public static class RenderPatch{
        public static void Prefix(EnergyPanel __instance, SpriteBatch sb){
            if(loadSoulUI()){
                renderSoulUI(sb, __instance.current_x);
            }
        }
    }

    @SpirePatch(clz = EnergyPanel.class, method = "update")
    public static class UpdatePatch{
        public static void Prefix(EnergyPanel __instance){
            if (loadSoulUI()) {
                updateSoulUI();
            }
        }
    }
}
