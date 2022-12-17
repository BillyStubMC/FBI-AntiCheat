package fun.billy.fbi.check.type;

import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.util.PlayerLocation;

public abstract class MovementCheck
        extends Check {
    public MovementCheck(Check.CheckType subType, String type, String friendlyName, Check.CheckVersion checkVersion) {
        super(subType, type, friendlyName, checkVersion);
    }

    public abstract void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp);
}

