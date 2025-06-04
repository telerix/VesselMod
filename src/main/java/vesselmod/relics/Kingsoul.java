package vesselmod.relics;

import basemod.AutoAdd;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vesselmod.actions.SoulChangeAction;
import vesselmod.character.Vessel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static vesselmod.VesselMod.makeID;
@AutoAdd.Seen
public class Kingsoul extends BaseRelic{
    public static final String NAME = "Kingsoul";
    public static final String ID = makeID(NAME);
    private final int soulGain = 1;
    HashMap<String, Integer> stats = new HashMap<>();
    private final String SOUL_GAIN = DESCRIPTIONS[2];
    private final String PER_COMBAT_STRING = DESCRIPTIONS[3];

    public Kingsoul() {
        super(ID, NAME, Vessel.Enums.CARD_COLOR, RelicTier.RARE, LandingSound.CLINK);
        UnlockTracker.markRelicAsSeen(this.relicId);
    }

    public void atTurnStart() {
        this.flash();
        this.addToBot(new SoulChangeAction(AbstractDungeon.player, soulGain));
        stats.put(SOUL_GAIN, stats.get(SOUL_GAIN) + soulGain);
    }

    public AbstractRelic makeCopy() {
        Kingsoul tmp = new Kingsoul();
        tmp.stats = this.stats;
        return tmp;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + this.soulGain + DESCRIPTIONS[1];
    }

    public String getStatsDescription() {
        return SOUL_GAIN + stats.get(SOUL_GAIN);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        // You would just return getStatsDescription() if you don't want to display per-combat and per-turn stats
        StringBuilder builder = new StringBuilder();
        builder.append(getStatsDescription());
        float stat = (float)stats.get(SOUL_GAIN);
        // Relic Stats truncates these extended stats to 3 decimal places, so we do the same
        DecimalFormat perTurnFormat = new DecimalFormat("#.###");
        builder.append(PER_COMBAT_STRING);
        builder.append(perTurnFormat.format(stat / Math.max(totalCombats, 1)));
        return builder.toString();
    }

    public void resetStats() {
        stats.put(SOUL_GAIN, 0);
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<Integer> statsToSave = new ArrayList<>();
        statsToSave.add(stats.get(SOUL_GAIN));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(SOUL_GAIN, jsonArray.get(0).getAsInt());
        } else {
            resetStats();
        }
    }
}
