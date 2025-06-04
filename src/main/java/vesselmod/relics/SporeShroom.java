package vesselmod.relics;

import basemod.AutoAdd;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vesselmod.character.Vessel;
import vesselmod.misc.CustomTags;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static vesselmod.VesselMod.makeID;
@AutoAdd.Seen
public class SporeShroom extends BaseRelic{
    public static final String NAME = "SporeShroom";
    public static final String ID = makeID(NAME);
    public static final int EFFECT = 6;
    HashMap<String, Integer> stats = new HashMap<>();
    private final String DMG = DESCRIPTIONS[2];
    private final String PER_COMBAT_STRING = DESCRIPTIONS[3];
    public SporeShroom() {
        super(ID, NAME, Vessel.Enums.CARD_COLOR, RelicTier.COMMON, LandingSound.FLAT);
        UnlockTracker.markRelicAsSeen(this.relicId);
        resetStats();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.hasTag(CustomTags.FOCUS)) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));

            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDeadOrEscaped()) {
                    stats.put(DMG, stats.getOrDefault(DMG, 0) + EFFECT);
                }
            }

            this.addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(EFFECT, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.POISON));
        }
    }

    public AbstractRelic makeCopy() {
        SporeShroom tmp = new SporeShroom();
        tmp.stats = this.stats;
        return tmp;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + EFFECT + DESCRIPTIONS[1];
    }

    public String getStatsDescription() {
        return DMG + stats.getOrDefault(DMG, 0);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        // You would just return getStatsDescription() if you don't want to display per-combat and per-turn stats
        StringBuilder builder = new StringBuilder();
        builder.append(getStatsDescription());
        float stat = (float)stats.getOrDefault(DMG, 0);
        // Relic Stats truncates these extended stats to 3 decimal places, so we do the same
        DecimalFormat perTurnFormat = new DecimalFormat("#.###");
        builder.append(PER_COMBAT_STRING);
        builder.append(perTurnFormat.format(stat / Math.max(totalCombats, 1)));
        return builder.toString();
    }

    public void resetStats() {
        stats.put(DMG, 0);
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<Integer> statsToSave = new ArrayList<>();
        statsToSave.add(stats.getOrDefault(DMG, 0));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(DMG, jsonArray.get(0).getAsInt());
        } else {
            resetStats();
        }
    }
}
