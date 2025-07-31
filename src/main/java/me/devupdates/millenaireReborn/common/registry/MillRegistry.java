package me.devupdates.millenaireReborn.common.registry;

import me.devupdates.millenaireReborn.MillenaireReborn;

public class MillRegistry {
    
    public static void init() {
        MillenaireReborn.LOGGER.info("Registering Millénaire content...");
        
        MillBlocks.init();
        MillItems.init();
        MillCreativeTabs.init();
        MillEntities.init();
        MillSounds.init();
        
        MillenaireReborn.LOGGER.info("Content registration complete!");
    }
}
