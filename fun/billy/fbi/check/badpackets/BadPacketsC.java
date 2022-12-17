package fun.billy.fbi.check.checks.badpackets;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class BadPacketsC
        extends PacketCheck {

    public BadPacketsC() {
        super(CheckType.BADPACKETSC, "C", "BadPackets", Check.CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying && !playerData.isTeleporting()) {
            float pitch = ((PacketPlayInFlying) packet).e();
            if (Math.abs(pitch) > 90.0f)
                VL.getInstance().handleViolation(playerData, this, "pitch=" + pitch, false);
        }
    }
}

