package vesselmod;

import basemod.*;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.CardBorderGlowManager;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;
import vesselmod.cards.BaseCard;
import vesselmod.character.Vessel;
import vesselmod.events.Crystallized;
import vesselmod.events.MysteriousDevice;
import vesselmod.events.VoidCore;
import vesselmod.misc.CustomTags;
import vesselmod.misc.SoulCostVariable;
import vesselmod.misc.SoulMechanics;
import vesselmod.misc.SoulTutorial;
import vesselmod.relics.BaseRelic;
import vesselmod.util.GeneralUtils;
import vesselmod.util.KeywordInfo;
import vesselmod.util.SFX;
import vesselmod.util.TextureLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static vesselmod.character.Vessel.Enums.VESSEL;
import static vesselmod.misc.SoulMechanics.*;

@SpireInitializer
public class VesselMod implements
        PostDeathSubscriber,
        OnStartBattleSubscriber,
        PostBattleSubscriber,
        StartGameSubscriber,
        EditRelicsSubscriber,
        EditCardsSubscriber,
        EditCharactersSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber,
        AddAudioSubscriber{
    public static ModInfo info;
    public static String modID;

    static {
        loadModInfo();
    }

    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.
    private static final String resourcesFolder = "vesselmod";
    private static final String BG_ATTACK = characterPath("cardback/bg_attack.png");
    private static final String BG_ATTACK_P = characterPath("cardback/bg_attack_p.png");
    private static final String BG_SKILL = characterPath("cardback/bg_skill.png");
    private static final String BG_SKILL_P = characterPath("cardback/bg_skill_p.png");
    private static final String BG_POWER = characterPath("cardback/bg_power.png");
    private static final String BG_POWER_P = characterPath("cardback/bg_power_p.png");
    private static final String ENERGY_ORB = characterPath("cardback/energy_orb.png");
    private static final String ENERGY_ORB_P = characterPath("cardback/energy_orb_p.png");
    private static final String SMALL_ORB = characterPath("cardback/small_orb.png");
    private static final String CHAR_SELECT_BUTTON = characterPath("select/button.png");
    private static final String CHAR_SELECT_PORTRAIT = characterPath("select/portrait.png");
    private static final Color cardColor = new Color(250f / 255f, 250f / 255f, 255f / 255f, 1f);

    public static String makeID(String id) {
        return modID + ":" + id;
    }

    public static Properties defaultSettings = new Properties();
    public static final String SKIP_TUTORIALS_SETTING = "Skip Tutorial";
    public static Boolean skipTutorialsPlaceholder = true; // The boolean we'll be setting on/off (true/false)
    public static ModLabeledToggleButton skipTutorials;


    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        new VesselMod();
        BaseMod.addColor(Vessel.Enums.CARD_COLOR, cardColor,
                BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                SMALL_ORB);
    }

    public VesselMod() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");
        defaultSettings.setProperty(SKIP_TUTORIALS_SETTING, "FALSE");
        try {
            SpireConfig config = new SpireConfig(modID, makeID("Config"), defaultSettings); //set tutorial on for default
            skipTutorialsPlaceholder = config.getBool(SKIP_TUTORIALS_SETTING);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receivePostInitialize() {
        BaseMod.addEvent(new AddEventParams.Builder(MysteriousDevice.ID, MysteriousDevice.class)
                .playerClass(Vessel.Enums.VESSEL)
                .dungeonIDs(Exordium.ID, TheCity.ID)
                .eventType(EventUtils.EventType.NORMAL)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Crystallized.ID, Crystallized.class)
                .playerClass(Vessel.Enums.VESSEL)
                .dungeonIDs(TheCity.ID, TheBeyond.ID)
                .eventType(EventUtils.EventType.NORMAL)
                .bonusCondition(Crystallized::hasDesolateDive)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(VoidCore.ID, VoidCore.class)
                .playerClass(Vessel.Enums.VESSEL)
                .dungeonID(TheBeyond.ID)
                .eventType(EventUtils.EventType.NORMAL)
                .create());

        CardBorderGlowManager.addGlowInfo(new CardBorderGlowManager.GlowInfo() {
            private final String ID = makeID("SoulUseBorder");

            @Override
            public boolean test(AbstractCard card) {
                if (card instanceof BaseCard) {
                    return (SoulMechanics.soulCount >= ((BaseCard) card).soulCost || ((BaseCard) card).freeSoulCost()) &&
                            card.hasTag(CustomTags.COST_SOUL);
                } else return false;
            }

            @Override
            public Color getColor(AbstractCard card) {
                return Color.WHITE.cpy();
            }

            @Override
            public String glowID() {
                return ID;
            }
        });
        ModPanel settingsPanel = new ModPanel();

        skipTutorials = new ModLabeledToggleButton("Skip Tutorial",
                350.0f, 750.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                skipTutorialsPlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:

                    skipTutorialsPlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
                    try {
                        // And based on that boolean, set the settings and save them
                        SpireConfig config = new SpireConfig(modID, makeID("Config"), defaultSettings);
                        config.setBool(SKIP_TUTORIALS_SETTING, skipTutorialsPlaceholder);
                        config.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        settingsPanel.addUIElement(skipTutorials); // Add the button to the settings panel. Button is a go.

        Texture badgeTexture = TextureLoader.getTexture(resourcePath("badge.png"));
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, settingsPanel);
    }


    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
    private static String getLangString() {
        return Settings.language.name().toLowerCase();
    }

    private static final String defaultLanguage = "eng";

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage);
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            }
            catch(Exception e) {
                loadLocalization(defaultLanguage);
            }
        }
    }

    private void loadLocalization(String lang) {
        //While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        //Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
        BaseMod.loadCustomStringsFile(TutorialStrings.class,
                localizationPath(lang, "TutorialStrings.json"));
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    registerKeyword(keyword);
                }
            } catch (Exception e) {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String resourcePath(String file) {
        return resourcesFolder + "/" + file;
    }

    public static String characterPath(String file) {
        return resourcesFolder + "/character/" + file;
    }

    public static String powerPath(String file) {
        return resourcesFolder + "/powers/" + file;
    }

    public static String relicPath(String file) {
        return resourcesFolder + "/relics/" + file;
    }


    //This determines the mod's ID based on information stored by ModTheSpire.
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo) -> {
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(VesselMod.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        } else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Vessel(),
                CHAR_SELECT_BUTTON, CHAR_SELECT_PORTRAIT, VESSEL);
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new SoulCostVariable());

        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseCard.class) //In the same package as this class
                .setDefaultSeen(true) //And marks them as seen in the compendium
                .notPackageFilter("vesselmod.cards.depreciated")
                .cards(); //Adds the cards
    }

    @Override
    public void receiveEditRelics() { //somewhere in the class
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseRelic.class) //In the same package as this class
                .any(BaseRelic.class, (info, relic) -> { //Run this code for any classes that extend this class
                    if (relic.pool != null)
                        BaseMod.addRelicToCustomPool(relic, relic.pool); //Register a custom character specific relic
                    else
                        BaseMod.addRelic(relic, relic.relicType); //Register a shared or base game character specific relic

                    if (info.seen) { //If the class is annotated with @AutoAdd.Seen, it will be marked as seen, making it visible in the relic library.
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveStartGame() {
        if (AbstractDungeon.player.chosenClass == VESSEL) {
            soulVesselLimit(6);
        }
        soulCount = 0;
    }//when game starts/loads (use for class check)

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        soulEndOfCombat();
        if (AbstractDungeon.player.chosenClass == VESSEL) {
            soulVesselLimit(6);
        } else {
            soulVesselLimit(0);
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        if (!skipTutorials.toggle.enabled && AbstractDungeon.player.chosenClass == VESSEL) {
            AbstractDungeon.ftue = new SoulTutorial();
            skipTutorials.toggle.toggle();
        }
        //when battle starts
    }
    @Override
    public void receivePostDeath () {
        soulCount = 0;
        soulLimit = 0;
        soulRetain = 0;
    } //backup

    @Override
    public void receiveAddAudio() {
        addAudio(SFX.Fireball);
        addAudio(SFX.Parry);

        addAudio(SFX.Cyclone);
        addAudio(SFX.Dash);
        addAudio(SFX.DDive);
        addAudio(SFX.DreamNail);
        addAudio(SFX.GreatSlash);
        addAudio(SFX.Wings);

        addAudio(SFX.RadianceExplode);
        addAudio(SFX.RadianceLaser);
        addAudio(SFX.Stun);
        addAudio(SFX.Wraiths);

        addAudio(SFX.DDark);
        addAudio(SFX.Shriek);
    }

    protected void addAudio(Pair<String, String> audio) {
        BaseMod.addAudio(audio.getKey(), audio.getValue());
    }
}