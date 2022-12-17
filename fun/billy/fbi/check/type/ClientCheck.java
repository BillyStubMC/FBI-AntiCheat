package fun.billy.fbi.check.type;

import fun.billy.fbi.data.ClientData;
import fun.billy.fbi.data.PlayerData;


public abstract class ClientCheck
        extends Check {
    public ClientCheck(Check.CheckType subType, String type, String friendlyName, Check.CheckVersion checkVersion) {
        super(subType, type, friendlyName, checkVersion);
    }

    public abstract void handle(PlayerData playerData, ClientData clientData, long timeStamp);
}

