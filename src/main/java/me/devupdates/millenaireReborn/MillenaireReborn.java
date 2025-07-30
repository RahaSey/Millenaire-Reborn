package me.devupdates.millenaireReborn;

import me.devupdates.millenaireReborn.common.config.MillConfig;
import me.devupdates.millenaireReborn.common.network.MillNetworking;
import me.devupdates.millenaireReborn.common.registry.MillRegistry;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MillenaireReborn implements ModInitializer {
    public static final String MOD_ID = "millenaire-reborn";
    public static final Logger LOGGER = LoggerFactory.getLogger("Millénaire Reborn");
    
    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Millénaire Reborn for MC 1.21.8");
        
        // Registry-Initialisierung
        MillRegistry.init();
        
        // Config laden
        MillConfig.load();
        
        // Network-Handler
        MillNetworking.registerServerPackets();
        
        LOGGER.info("Millénaire Reborn initialization complete!");
    }
}
