package vesselmod.relics;

import basemod.AutoAdd;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vesselmod.character.Vessel;
import vesselmod.powers.OverloadedFocusPower;

import java.text.DecimalFormat;
import java.util.HashMap;

import static vesselmod.VesselMod.makeID;

@AutoAdd.Seen
public class TyrantsSoul extends BaseRelic{
    public static final String NAME = "TyrantsSoul";
    public static final String ID = makeID(NAME);
    private static final int ENERGY_GAIN = 1;
    private static final int CARD_DRAW = 1;
    private final String[] SOULCOST_DESC = DESCRIPTIONS;
    private int[] SOUL_COST_STATS = new int[4]; //[soul 1, soul 2, soul 3, discount sum]
    private boolean triggeredThisTurn = false;
    private HashMap<String, Integer> stats;

    public TyrantsSoul() {
        super(ID, NAME, Vessel.Enums.CARD_COLOR, RelicTier.BOSS, LandingSound.MAGICAL);
        UnlockTracker.markRelicAsSeen(this.relicId);
        resetStats();
    }

    public void atPreBattle() {
        this.flash();
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new OverloadedFocusPower(AbstractDungeon.player)));
    }

    @Override
    public void atTurnStart() {
        this.triggeredThisTurn = false;
    }

    public void onTrigger() {
        if (!this.triggeredThisTurn) {
            this.triggeredThisTurn = true;
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new GainEnergyAction(ENERGY_GAIN));
            this.addToBot(new DrawCardAction(CARD_DRAW));
        }
    }

    public void appendStats(int baseCost, int newCost) {
        SOUL_COST_STATS[newCost - 1] += 1;
        SOUL_COST_STATS[3] += baseCost - newCost;
    }

    public AbstractRelic makeCopy() {
        TyrantsSoul tmp = new TyrantsSoul();
        tmp.SOUL_COST_STATS = this.SOUL_COST_STATS;
        return tmp;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + CARD_DRAW + DESCRIPTIONS[1];
    }

    public String getStatsDescription() {
        StringBuilder toDisplay = new StringBuilder();
        int total_cards = 0;
        for(int i = 0; i < 3; i++) {
            toDisplay.append(SOULCOST_DESC[i + 2]); //first 2 lines are actual desc
            toDisplay.append(SOUL_COST_STATS[i]);
            total_cards += SOUL_COST_STATS[i];
        }
        toDisplay.append(SOULCOST_DESC[5]);
        if (total_cards == 0) {
            total_cards = 1;
        }
        toDisplay.append(new DecimalFormat("#.###").format((float) (SOUL_COST_STATS[3]) / total_cards));
        return toDisplay.toString();
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        return getStatsDescription();
    }


    public void resetStats() {
        SOUL_COST_STATS = new int[4];
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        return gson.toJsonTree(SOUL_COST_STATS);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 0; i < 4; i++) {
                if (i >= jsonArray.size()) {
                    SOUL_COST_STATS[i] = 0;
                } else {
                    SOUL_COST_STATS[i] = jsonArray.get(i).getAsInt();
                }
            }
        } else {
            resetStats();
        }
    }
}
