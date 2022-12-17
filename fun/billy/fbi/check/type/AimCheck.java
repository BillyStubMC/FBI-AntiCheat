package fun.billy.fbi.check.type;

import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.util.PlayerLocation;

public abstract class AimCheck
        extends Check {
    public AimCheck(Check.CheckType subType, String type, String friendlyName, Check.CheckVersion checkVersion) {
        super(subType, type, friendlyName, checkVersion);
    }

    public abstract void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp);
}

