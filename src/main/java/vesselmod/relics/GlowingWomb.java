package vesselmod.relics;

import basemod.AutoAdd;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vesselmod.actions.SoulChangeAction;
import vesselmod.character.Vessel;
import vesselmod.powers.InfectionPower;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static vesselmod.VesselMod.makeID;

@AutoAdd.Seen
public class GlowingWomb extends BaseRelic implements OnApplyPowerRelic {
    public static final String NAME = "GlowingWomb";
    public static final String ID = makeID(NAME);
    private static final int extraInfection = 1;
    HashMap<String, Integer> stats = new HashMap<>();
    private final String ADD_INFECT = DESCRIPTIONS[2];
    private final String PER_TURN_STRING = DESCRIPTIONS[3];
    private final String PER_COMBAT_STRING = DESCRIPTIONS[4];

    public GlowingWomb() {
        super(ID, NAME, Vessel.Enums.CARD_COLOR, RelicTier.UNCOMMON, LandingSound.CLINK);
        UnlockTracker.markRelicAsSeen(this.relicId);
        resetStats();
    }

    @Override
    public int onApplyPowerStacks(AbstractPower p, AbstractCreature target, AbstractCreature source, int stackAmt) {
        if (p instanceof InfectionPower && target != AbstractDungeon.player) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(target, this));
            stats.put(ADD_INFECT, stats.get(ADD_INFECT) + extraInfection);
            return stackAmt + extraInfection;
        } else return stackAmt;
    }

    @Override
    public boolean onApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        return true;
    }

    public void wasHPLost(int damageAmount) {
        if (damageAmount > 0) {
            this.flash();
            this.addToTop(new SoulChangeAction(AbstractDungeon.player, extraInfection));
            stats.put(ADD_INFECT, stats.get(ADD_INFECT) + extraInfection);
        }
    }

    public AbstractRelic makeCopy() {
        GlowingWomb tmp = new GlowingWomb();
        tmp.stats = this.stats;
        return tmp;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + extraInfection + DESCRIPTIONS[1];
    }

    public String getStatsDescription() {
        return ADD_INFECT + stats.getOrDefault(ADD_INFECT, 0);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        // You would just return getStatsDescription() if you don't want to display per-combat and per-turn stats
        StringBuilder builder = new StringBuilder();
        builder.append(getStatsDescription());
        float stat = (float)stats.getOrDefault(ADD_INFECT, 0);
        // Relic Stats truncates these extended stats to 3 decimal places, so we do the same
        DecimalFormat perTurnFormat = new DecimalFormat("#.###");
        builder.append(PER_TURN_STRING);
        builder.append(perTurnFormat.format(stat / Math.max(totalTurns, 1)));
        builder.append(PER_COMBAT_STRING);
        builder.append(perTurnFormat.format(stat / Math.max(totalCombats, 1)));
        return builder.toString();
    }

    public void resetStats() {
        stats.put(ADD_INFECT, 0);
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<Integer> statsToSave = new ArrayList<>();
        statsToSave.add(stats.getOrDefault(ADD_INFECT, 0));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(ADD_INFECT, jsonArray.get(0).getAsInt());
        } else {
            resetStats();
        }
    }
}
