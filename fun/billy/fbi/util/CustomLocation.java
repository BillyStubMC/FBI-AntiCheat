package fun.billy.fbi.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomLocation {
    private long timestamp;
    private double x, y, z;
    private float yaw, pitch;

    public CustomLocation(double x, double y, double z, float yaw, float pitch) {
        this.timestamp = System.currentTimeMillis();
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
}
