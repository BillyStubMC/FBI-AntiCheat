package fun.billy.fbi.util;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;

@Getter
@Setter
public class PlayerLocation {
    private long timestamp;
    private int tickTime;
    private double x, y, z;
    private float yaw, pitch;
    private Boolean onGround;

    public PlayerLocation add(double x, double y, double z) {
        return new PlayerLocation(this.timestamp, this.tickTime, this.x + x, this.y + y, this.z + z, this.yaw, this.pitch, this.onGround);
    }

    public Location toLocation(final World world) {
        return new Location(world, this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public double distanceXZSquared(PlayerLocation playerLocation) {
        return Math.pow(this.x - playerLocation.x, 2.0) + Math.pow(this.z - playerLocation.z, 2.0);
    }

    public PlayerLocation clone() {
        return new PlayerLocation(this.timestamp, this.tickTime, this.x, this.y, this.z, this.yaw, this.pitch, this.onGround);
    }

    public Cuboid hitbox() {
        return new Cuboid(this.x, this.y, this.z).add(new Cuboid(-0.3, 0.3, 0.0, 1.8, -0.3, 0.3));
    }

    public Cuboid to(PlayerLocation playerLocation) {
        return new Cuboid(Math.min(this.x, playerLocation.x), Math.max(this.x, playerLocation.x), Math.min(this.y, playerLocation.y), Math.max(this.y, playerLocation.y), Math.min(this.z, playerLocation.z), Math.max(this.z, playerLocation.z));
    }

    public boolean sameLocation(PlayerLocation playerLocation) {
        return this.x == playerLocation.x && this.y == playerLocation.y && this.z == playerLocation.z;
    }

    public boolean sameDirection(PlayerLocation playerLocation) {
        return this.yaw == playerLocation.yaw && this.pitch == playerLocation.pitch;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && this.getClass() == o.getClass()) {
            PlayerLocation location = (PlayerLocation) o;
            if (this.timestamp != location.timestamp) {
                return false;
            }
            if (this.tickTime != location.tickTime) {
                return false;
            }
            if (Double.compare(location.x, this.x) != 0) {
                return false;
            }
            if (Double.compare(location.y, this.y) != 0) {
                return false;
            }
            if (Double.compare(location.z, this.z) != 0) {
                return false;
            }
            if (Float.compare(location.yaw, this.yaw) != 0) {
                return false;
            }
            return Float.compare(location.pitch, this.pitch) == 0;
        }
        return false;
    }

    public PlayerLocation(long timestamp, int tickTime, double x, double y, double z, float yaw, float pitch, Boolean onGround) {
        this.timestamp = timestamp;
        this.tickTime = tickTime;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }
}

