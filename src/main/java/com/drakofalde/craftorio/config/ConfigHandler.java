package com.drakofalde.craftorio.config;

import com.drakofalde.craftorio.CraftorioMod;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
    public static final ConfigHandler INSTANCE = new ConfigHandler();

    public static double primitiveLabSpeed = 0.75D;
    public static double labSpeed = 1.0D;
    public static boolean autoFilterDefaults = true;
    public static String energyConsumption = "NONE";
    public static String energyType = "RF";
    public static int labEnergyPerTick = 0;
    public static int primitiveLabEnergyPerTick = 0;
    public static boolean serverUtilitiesTeams = false;
    public static boolean pollution = false;

    private static Configuration cfg;

    public static void init(File file) {
        cfg = new Configuration(file);
        sync();
    }

    public static void sync() {
        String cat = Configuration.CATEGORY_GENERAL;

        primitiveLabSpeed  = cfg.getFloat("primitive_lab_speed", cat, 0.75F, 0.1F, 64F, "Speed multiplier for the Primitive Laboratory.");
        labSpeed           = cfg.getFloat("lab_speed", cat, 1.0F, 0.1F, 64F, "Speed multiplier for the Laboratory.");
        autoFilterDefaults = cfg.getBoolean("auto_filter_defaults", cat, true, "Auto-configure slot filters when the Laboratory multiblock is assembled.");
        energyConsumption  = cfg.getString("energy_consumption", cat, "NONE", "Which machines consume energy: LAB, PRIMITIVE_LAB, BOTH, NONE.", new String[]{"LAB", "PRIMITIVE_LAB", "BOTH", "NONE"});
        energyType         = cfg.getString("energy_type", cat, "RF", "Energy type (RF or HE).", new String[]{"RF", "HE"});
        labEnergyPerTick           = cfg.getInt("lab_energy_per_tick", cat, 0, 0, Integer.MAX_VALUE, "RF/HE per tick for the Laboratory.");
        primitiveLabEnergyPerTick  = cfg.getInt("primitive_lab_energy_per_tick", cat, 0, 0, Integer.MAX_VALUE, "RF/HE per tick for the Primitive Laboratory.");
        serverUtilitiesTeams = cfg.getBoolean("serverutilities_teams", cat, false, "Sync research progress across ServerUtilities teams.");
        pollution            = cfg.getBoolean("pollution", cat, false, "Produce pollution (requires HBM NTM).");

        if (cfg.hasChanged()) cfg.save();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (CraftorioMod.MODID.equals(event.modID)) {
            sync();
        }
    }
}