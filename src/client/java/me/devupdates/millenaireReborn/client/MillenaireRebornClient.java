package me.devupdates.millenaireReborn.client;

import me.devupdates.millenaireReborn.MillenaireReborn;
import me.devupdates.millenaireReborn.common.network.MillNetworking;
import net.fabricmc.api.ClientModInitializer;

public class MillenaireRebornClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        MillenaireReborn.LOGGER.info("Initializing Millénaire Reborn Client");
        
        // Client-spezifische Network-Handler
        MillNetworking.registerClientPackets();
        
        // TODO: Item/Block Model-Registrierung wird hier später hinzugefügt
        
        MillenaireReborn.LOGGER.info("Millénaire Reborn Client initialization complete!");
    }
}