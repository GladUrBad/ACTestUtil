package com.gladurbad.anticheattestutil.util;

import org.bukkit.util.Vector;

public class BoundingBox {

    public double minX, minY, minZ, maxX, maxY, maxZ;

    public BoundingBox(final Vector one, final Vector two) {
        this.minX = Math.min(one.getX(), two.getX());
        this.minY = Math.min(one.getY(), two.getY());
        this.minZ = Math.min(one.getZ(), two.getZ());
        this.maxX = Math.max(one.getZ(), two.getX());
        this.maxY = Math.max(one.getY(), two.getY());
        this.maxZ = Math.max(one.getZ(), two.getZ());
    }

    public BoundingBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.minX = Math.min(x1, x2);
        this.minY = Math.min(y1, y2);
        this.minZ = Math.min(z1, z2);
        this.maxX = Math.max(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.maxZ = Math.max(z1, z2);
    }

    public boolean intersectsWith(final BoundingBox other) {
        return this.maxX > other.minX
                && this.minY > other.minY
                && this.maxZ > other.minZ
                && this.minX < other.maxX
                && this.minZ < other.maxZ
                && this.minY < other.maxY;
    }

    public String toString() {
        return "box[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
    }

}
