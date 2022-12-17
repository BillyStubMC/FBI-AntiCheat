package fun.billy.fbi.check.checks.timer;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;

public class TimerB
        extends PacketCheck {
    private long lastPacket;
    private int count;

    public TimerB() {
        super(Check.CheckType.TIMERB, "B", "Timer", CheckVersion.RELEASE);
        this.violations = -1.0;
        this.lastPacket = 0L;
        this.count = 0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying) {
            if (playerData.getTeleportTicks() > playerData.getPingTicks() && !playerData.hasLag() && playerData.getVersion() != 1.9) {
                long delay = timestamp - this.lastPacket;
                if (delay > 90L) {
                    if (this.count++ > 2) {
                        VL.getInstance().handleViolation(playerData, this, "D " + delay + ", T" + this.count, false);
                        this.count = 0;
                    }
                } else {
                    this.count = 0;
                }
                this.lastPacket = timestamp;
            }
        } else if (packet instanceof PacketPlayOutPosition) {
            this.count = 0;
        }
    }
}
