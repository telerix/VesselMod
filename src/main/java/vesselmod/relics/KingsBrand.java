package vesselmod.relics;

import basemod.AutoAdd;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vesselmod.character.Vessel;
import vesselmod.powers.InfectionPower;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static vesselmod.VesselMod.makeID;

@AutoAdd.Seen
public class KingsBrand extends BaseRelic{
    public static final String NAME = "KingsBrand";
    public static final String ID = makeID(NAME);
    public static final int EFFECT = 8;
    HashMap<String, Integer> stats = new HashMap<>();
    private final String DMG_MIT = DESCRIPTIONS[2];
    private final String PER_COMBAT_STRING = DESCRIPTIONS[3];

    public KingsBrand() {
        super(ID, NAME, Vessel.Enums.CARD_COLOR, RelicTier.RARE, LandingSound.CLINK);
        UnlockTracker.markRelicAsSeen(this.relicId);
    }
    //effect is set in InfectionLoseHpAction

    @Override
    public void flash() { //hijacking this to track activation from InfectionLoseHpAction
        super.flash();
        AbstractPlayer p = AbstractDungeon.player;
        if (AbstractDungeon.player != null &&
                AbstractDungeon.currMapNode != null &&
                AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && //makes sure game doesnt null pointer exception out of combat
                p.hasPower(InfectionPower.POWER_ID)) {
            this.addToBot(new RelicAboveCreatureAction(p, this));
            int dmgMitigated = p.getPower(InfectionPower.POWER_ID).amount - EFFECT;
            stats.put(DMG_MIT, stats.get(DMG_MIT) + dmgMitigated);
        }
    }

    public AbstractRelic makeCopy() {
        KingsBrand tmp = new KingsBrand();
        tmp.stats = this.stats;
        return tmp;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + EFFECT + DESCRIPTIONS[1];
    }

    public String getStatsDescription() {
        return DMG_MIT + stats.get(DMG_MIT);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        // You would just return getStatsDescription() if you don't want to display per-combat and per-turn stats
        StringBuilder builder = new StringBuilder();
        builder.append(getStatsDescription());
        float stat = (float)stats.get(DMG_MIT);
        // Relic Stats truncates these extended stats to 3 decimal places, so we do the same
        DecimalFormat perTurnFormat = new DecimalFormat("#.###");
        builder.append(PER_COMBAT_STRING);
        builder.append(perTurnFormat.format(stat / Math.max(totalCombats, 1)));
        return builder.toString();
    }

    public void resetStats() {
        stats.put(DMG_MIT, 0);
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<Integer> statsToSave = new ArrayList<>();
        statsToSave.add(stats.get(DMG_MIT));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(DMG_MIT, jsonArray.get(0).getAsInt());
        } else {
            resetStats();
        }
    }
}