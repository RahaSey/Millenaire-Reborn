package me.devupdates.millenaireReborn.common.network;

import me.devupdates.millenaireReborn.MillenaireReborn;

/**
 * Netzwerk-System für Client-Server-Kommunikation
 */
public class MillNetworking {
    
    /**
     * Registriert Server-seitige Packet-Handler
     */
    public static void registerServerPackets() {
        MillenaireReborn.LOGGER.info("Server networking initialized (placeholder)");
        // TODO: Packet-Handler für Server werden hier registriert
        // Beispiele: Village-Updates, Villager-Commands, Quest-Sync, etc.
    }
    
    /**
     * Registriert Client-seitige Packet-Handler
     */
    public static void registerClientPackets() {
        MillenaireReborn.LOGGER.info("Client networking initialized (placeholder)");
        // TODO: Packet-Handler für Client werden hier registriert
        // Beispiele: GUI-Updates, Particle-Effects, Sound-Sync, etc.
    }
}