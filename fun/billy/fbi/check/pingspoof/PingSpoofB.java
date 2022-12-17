package fun.billy.fbi.check.checks.pingspoof;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInKeepAlive;

public class PingSpoofB
        extends PacketCheck {
    private Integer lastKeepAlive;

    public PingSpoofB() {
        super(CheckType.PINGSPOOFB, "B", "PingSpoof", CheckVersion.RELEASE);
        this.lastKeepAlive = null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if(playerData.getAveragePing() >= 1000) {
            VL.getInstance().handleViolation(playerData, this, "" + (playerData.getAveragePing() + "ms"), false);
        }
    }
}

