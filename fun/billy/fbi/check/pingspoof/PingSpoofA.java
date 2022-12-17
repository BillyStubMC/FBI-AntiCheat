package fun.billy.fbi.check.checks.pingspoof;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInKeepAlive;

public class PingSpoofA
        extends PacketCheck {
    private Integer lastKeepAlive;

    public PingSpoofA() {
        super(CheckType.PINGSPOOFA, "A", "PingSpoof", Check.CheckVersion.RELEASE);
        this.lastKeepAlive = null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInKeepAlive) {
            PacketPlayInKeepAlive packetPlayInKeepAlive = (PacketPlayInKeepAlive) packet;
            if (this.lastKeepAlive != null && this.lastKeepAlive > packetPlayInKeepAlive.a() && playerData.getTotalTicks() > 160) {
                VL.getInstance().handleViolation(playerData, this, "bp=" + (this.lastKeepAlive - packetPlayInKeepAlive.a()), false);
            }
            this.lastKeepAlive = packetPlayInKeepAlive.a();
        }
    }
}

