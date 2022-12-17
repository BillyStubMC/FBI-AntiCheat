package fun.billy.fbi.data;

import fun.billy.fbi.util.Cuboid;
import lombok.Getter;

@Getter
public class DistanceData {
    private final Cuboid hitbox;
    private final double x, z, y, dist;

    public DistanceData(Cuboid hitbox, double x, double z, double y, double dist) {
        this.hitbox = hitbox;
        this.x = x;
        this.z = z;
        this.y = y;
        this.dist = dist;
    }
}