package me.devupdates.millenaireReborn.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.devupdates.millenaireReborn.MillenaireReborn;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Millénaire Konfigurationssystem
 */
public class MillConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(
        FabricLoader.getInstance().getConfigDir().toFile(), 
        "millenaire-reborn.json"
    );
    
    // Haupt-Konfigurationswerte (aus dem alten Mod adaptiert)
    public static boolean generateVillages = true;
    public static int villageSpawnRadius = 700;
    public static double villageSpawnDensity = 1.0;
    public static boolean displayNames = true;
    public static boolean enableVillagerSounds = true;
    public static boolean enableDebugMode = false;
    public static int maxVillagersPerVillage = 20;
    public static boolean allowVillageConstruction = true;
    
    /**
     * Lädt die Konfiguration aus der Datei
     */
    public static void load() {
        MillenaireReborn.LOGGER.info("Loading Millénaire configuration...");
        
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                ConfigData data = GSON.fromJson(reader, ConfigData.class);
                if (data != null) {
                    applyConfig(data);
                    MillenaireReborn.LOGGER.info("Configuration loaded successfully");
                } else {
                    MillenaireReborn.LOGGER.warn("Config file is empty or invalid, using defaults");
                    save(); // Erstelle korrekte Config
                }
            } catch (IOException e) {
                MillenaireReborn.LOGGER.error("Failed to load config file", e);
                save(); // Erstelle neue Config bei Fehler
            }
        } else {
            MillenaireReborn.LOGGER.info("No config file found, creating default configuration");
            save(); // Erstelle Default-Config
        }
    }
    
    /**
     * Speichert die aktuelle Konfiguration in die Datei
     */
    public static void save() {
        ConfigData data = new ConfigData();
        data.generateVillages = generateVillages;
        data.villageSpawnRadius = villageSpawnRadius;
        data.villageSpawnDensity = villageSpawnDensity;
        data.displayNames = displayNames;
        data.enableVillagerSounds = enableVillagerSounds;
        data.enableDebugMode = enableDebugMode;
        data.maxVillagersPerVillage = maxVillagersPerVillage;
        data.allowVillageConstruction = allowVillageConstruction;
        
        try {
            // Stelle sicher dass das Config-Verzeichnis existiert
            CONFIG_FILE.getParentFile().mkdirs();
            
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(data, writer);
                MillenaireReborn.LOGGER.info("Configuration saved to {}", CONFIG_FILE.getPath());
            }
        } catch (IOException e) {
            MillenaireReborn.LOGGER.error("Failed to save config file", e);
        }
    }
    
    /**
     * Wendet die geladenen Konfigurationsdaten an
     */
    private static void applyConfig(ConfigData data) {
        generateVillages = data.generateVillages;
        villageSpawnRadius = data.villageSpawnRadius;
        villageSpawnDensity = data.villageSpawnDensity;
        displayNames = data.displayNames;
        enableVillagerSounds = data.enableVillagerSounds;
        enableDebugMode = data.enableDebugMode;
        maxVillagersPerVillage = data.maxVillagersPerVillage;
        allowVillageConstruction = data.allowVillageConstruction;
    }
    
    /**
     * Interne Klasse für JSON-Serialisierung
     */
    private static class ConfigData {
        boolean generateVillages = true;
        int villageSpawnRadius = 700;
        double villageSpawnDensity = 1.0;
        boolean displayNames = true;
        boolean enableVillagerSounds = true;
        boolean enableDebugMode = false;
        int maxVillagersPerVillage = 20;
        boolean allowVillageConstruction = true;
    }
}