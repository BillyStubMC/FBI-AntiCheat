package fun.billy.fbi.check.type;

import fun.billy.fbi.data.PlayerData;
import net.minecraft.server.v1_8_R3.Packet;

public abstract class PacketCheck
        extends Check {
    public PacketCheck(Check.CheckType subType, String type, String friendlyName, Check.CheckVersion checkVersion) {
        super(subType, type, friendlyName, checkVersion);
    }

    public abstract void handle(PlayerData playerData, Packet packet, long timeStamp);
}

