package me.devupdates.millenaireReborn.common.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * 3D-Punkt-Klasse für Millénaire (portiert vom alten Mod)
 * Vereinfacht mathematische Operationen und Konvertierungen
 */
public class Point {
    public final int x;
    public final int y; 
    public final int z;
    
    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Point(BlockPos pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }
    
    public Point(Vec3d vec) {
        this((int) Math.floor(vec.x), (int) Math.floor(vec.y), (int) Math.floor(vec.z));
    }
    
    /**
     * Konvertiert zu BlockPos für Minecraft-API-Calls
     */
    public BlockPos toBlockPos() {
        return new BlockPos(x, y, z);
    }
    
    /**
     * Konvertiert zu Vec3d für Berechnungen
     */
    public Vec3d toVec3d() {
        return new Vec3d(x + 0.5, y + 0.5, z + 0.5); // Zentriert in Block
    }
    
    /**
     * Berechnet die Euklidische Distanz zu einem anderen Punkt
     */
    public double distanceTo(Point other) {
        return Math.sqrt(distanceSquaredTo(other));
    }
    
    /**
     * Berechnet die quadrierte Distanz (effizienter für Vergleiche)
     */
    public double distanceSquaredTo(Point other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double dz = this.z - other.z;
        return dx * dx + dy * dy + dz * dz;
    }
    
    /**
     * Berechnet die Manhattan-Distanz (nur orthogonale Bewegungen)
     */
    public int manhattanDistanceTo(Point other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z);
    }
    
    /**
     * Erstellt einen neuen Punkt mit Offset
     */
    public Point offset(int dx, int dy, int dz) {
        return new Point(x + dx, y + dy, z + dz);
    }
    
    /**
     * Erstellt einen neuen Punkt mit Offset nur in X-Z-Ebene
     */
    public Point offsetXZ(int dx, int dz) {
        return new Point(x + dx, y, z + dz);
    }
    
    /**
     * Prüft ob dieser Punkt innerhalb eines Bereichs liegt
     */
    public boolean isWithinBounds(Point min, Point max) {
        return x >= min.x && x <= max.x &&
               y >= min.y && y <= max.y &&
               z >= min.z && z <= max.z;
    }
    
    /**
     * Berechnet den Mittelpunkt zwischen zwei Punkten
     */
    public Point midpoint(Point other) {
        return new Point((x + other.x) / 2, (y + other.y) / 2, (z + other.z) / 2);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Point)) return false;
        Point other = (Point) obj;
        return x == other.x && y == other.y && z == other.z;
    }
    
    @Override
    public int hashCode() {
        // Gleiche Hash-Funktion wie BlockPos für Konsistenz
        return x + z * 31 + y * 961;
    }
    
    @Override
    public String toString() {
        return String.format("Point[%d,%d,%d]", x, y, z);
    }
}