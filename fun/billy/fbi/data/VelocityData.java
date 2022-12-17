package fun.billy.fbi.data;

import lombok.Getter;

@Getter
public class VelocityData {
    private final int entityId, x, y, z;

    public VelocityData(int entityId, int x, int y, int z) {
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

