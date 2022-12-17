package fun.billy.fbi.data;

import fun.billy.fbi.util.PlayerLocation;
import lombok.Getter;

@Getter
public class ClientData {
    private final PlayerData playerData;
    private final PlayerLocation location;
    private final DistanceData distanceData;
    private final double movement, horizontal, extra, vertical, reach;

    public ClientData(PlayerData playerData, PlayerLocation location, DistanceData distanceData, double movement, double horizontal, double extra, double vertical, double reach) {
        this.playerData = playerData;
        this.location = location;
        this.distanceData = distanceData;
        this.movement = movement;
        this.horizontal = horizontal;
        this.extra = extra;
        this.vertical = vertical;
        this.reach = reach;
    }
}

