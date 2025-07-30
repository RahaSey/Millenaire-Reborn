package me.devupdates.millenaireReborn.common.registry;

import me.devupdates.millenaireReborn.MillenaireReborn;

/**
 * Zentrale Registry-Klasse die alle anderen Registry-Initialisierungen koordiniert
 */
public class MillRegistry {
    
    public static void init() {
        MillenaireReborn.LOGGER.info("Registering Millénaire content...");
        
        // Reihenfolge ist wichtig: Blocks vor Items (wegen BlockItems)
        MillBlocks.init();
        MillItems.init();
        MillCreativeTabs.init();  // Creative Tabs nach Items registrieren
        MillEntities.init();
        MillSounds.init();
        
        MillenaireReborn.LOGGER.info("Content registration complete!");
    }
}