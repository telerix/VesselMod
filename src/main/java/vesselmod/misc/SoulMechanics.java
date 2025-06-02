package vesselmod.misc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import vesselmod.actions.SoulChangeAction;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.modifiers.SpellDamage;
import vesselmod.relics.SoulEater;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static vesselmod.VesselMod.makeID;

public class SoulMechanics extends Vessel{
    public static int soulLimit = 0;
    public static int soulCount = 0;
    public static int soulRetain = 0; //unused
    public static SoulUI soulUI;
    private static final String ID = makeID("SoulMechanics");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String noSoulMessage = uiStrings.TEXT[0];
    private static boolean renderSoulUI = false;

    public static void soulVesselLimit(int limit) {
        SoulMechanics.soulLimit = limit;
    }
    public static void soulEndOfCombat() {
        if (soulRetain < soulCount) {
            soulCount = soulRetain;
        }
        soulRetain = 0; //not used, too janky.
        
    }

    public static void soulOnAttack(DamageInfo info, int attackDamage) {
        if (player.chosenClass == Enums.VESSEL && attackDamage > 0 && info.type == DamageInfo.DamageType.NORMAL && DamageModifierManager.getDamageMods(info).stream().noneMatch((mod)->mod instanceof SpellDamage)) {
            if (AbstractDungeon.player.hasRelic(SoulEater.ID)) {
                player.getRelic(SoulEater.ID).flash();
                AbstractDungeon.actionManager.addToTop(new SoulChangeAction(AbstractDungeon.player, 1 + SoulEater.soulGain));
            } else {
                AbstractDungeon.actionManager.addToTop(new SoulChangeAction(AbstractDungeon.player, 1));
            }
        }
    }

    public static boolean loadSoulUI(){
        if (CardCrawlGame.dungeon != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (renderSoulUI) {
                if (soulUI == null) {
                    soulUI = new SoulUI(ImageMaster.loadImage("vesselmod/misc/soul_meter.png"));
                }
                return true;
            } else if (soulLimit > 0 || player.chosenClass == Vessel.Enums.VESSEL) {
                renderSoulUI = true;
                if (soulUI == null) {
                    soulUI = new SoulUI(ImageMaster.loadImage("vesselmod/misc/soul_meter.png"));
                }
                return true;
            }
        }
        return false;
    }

    public static void duplicationSoulCostFix(AbstractCard dupeCard) {
        if (dupeCard instanceof BaseCard) {
            ((BaseCard) dupeCard).soulCost = 0;
        }
    }

    public static void renderSoulUI(SpriteBatch sb, float current_x){
        soulUI.render(sb, current_x);
    }

    public static void updateSoulUI()
    {
        soulUI.update();
    }
}
