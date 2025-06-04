package vesselmod.util;

import basemod.Pair;
import vesselmod.VesselMod;

public class SFX extends VesselMod {
    private static String sfxPath(String fileName) {
        logger.debug("Attempting to load file at \"" + VesselMod.resourcePath("sfx/" + fileName + "\"."));
        return resourcePath("sfx/" + fileName);
    }
    private static String sfxID(String Key) {
        return modID + ":" + Key;
    }

    //starter
    public static final Pair<String, String> Fireball = new Pair<>(sfxID("Fireball"), sfxPath("Fireball.ogg"));

    //common
    public static final Pair<String, String> Parry = new Pair<>(sfxID("Parry"), sfxPath("Parry.ogg"));

    //uncommon
    public static final Pair<String, String> Cyclone = new Pair<>(sfxID("Cyclone"), sfxPath("Cyclone.ogg"));
    public static final Pair<String, String> DDive = new Pair<>(sfxID("DDive"), sfxPath("DDive.ogg"));
    public static final Pair<String, String> DreamNail = new Pair<>(sfxID("DreamNail"), sfxPath("DreamNailSlash.ogg"));
    public static final Pair<String, String> GreatSlash = new Pair<>(sfxID("GreatSlash"), sfxPath("GreatSlash.ogg"));
    public static final Pair<String, String> Wings = new Pair<>(sfxID("Wings"), sfxPath("Wings.ogg"));

    //rare
    public static final Pair<String, String> Wraiths = new Pair<>(sfxID("Wraiths"), sfxPath("Wraiths.ogg"));

    //event
    public static final Pair<String, String> DDark = new Pair<>(sfxID("DDark"), sfxPath("DDark.ogg"));
    public static final Pair<String, String> Shriek = new Pair<>(sfxID("Shriek"), sfxPath("Shriek.ogg"));

}
