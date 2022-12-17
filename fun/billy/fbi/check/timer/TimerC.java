package fun.billy.fbi.check.checks.timer;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class TimerC
        extends PacketCheck {
    public TimerC() {
        super(Check.CheckType.TIMERC, "C", "Timer", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying && playerData.getPackets() > 20 && !playerData.isTeleporting()) {
            VL.getInstance().handleViolation(playerData, this, "S " + playerData.getPackets(), false);
        }
    }
}

