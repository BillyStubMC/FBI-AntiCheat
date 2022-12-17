package fun.billy.fbi.check.checks.timer;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;

import java.util.concurrent.TimeUnit;

public class TimerA
        extends PacketCheck {
    private final long CHECK_TIME_LINEAR = toNanos(60L);
    private long lastFlag;
    private Long lastPacket;
    private long offset;

    private long fromNanos(final long n) {
        return TimeUnit.NANOSECONDS.toMillis(n);
    }

    private long toNanos(final long n) {
        return TimeUnit.MILLISECONDS.toNanos(n);
    }

    public TimerA() {
        super(Check.CheckType.TIMERA, "A", "Timer", Check.CheckVersion.RELEASE);
        this.violations = -1.0;
        this.lastPacket = null;
        this.offset = -100L;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying) {
            final long nanoTime = System.nanoTime();
            if (this.lastPacket != null) {
                this.offset += toNanos(50L) - (nanoTime - this.lastPacket);
                if (this.offset > this.CHECK_TIME_LINEAR) {
                    if (fromNanos(nanoTime - this.lastFlag) > 1L && playerData.getTotalTicks() > 400 && !playerData.isTeleporting() && !playerData.getPlayer().isInsideVehicle()) {
                        VL.getInstance().handleViolation(playerData, this, "BAL " + fromNanos(this.offset), false);
                    }
                    this.lastFlag = nanoTime;
                    this.offset = 0L;
                } else {
                    this.decreaseVL(0.0025);
                }
            }
            this.lastPacket = nanoTime;
        } else if (packet instanceof PacketPlayOutPosition) {
            this.offset -= toNanos(50L);
        }
    }
}