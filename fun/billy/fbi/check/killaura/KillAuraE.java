package fun.billy.fbi.check.checks.killaura;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.utils.MathUtils;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KillAuraE
        extends PacketCheck {
    Queue<Double> pitchList;
    private Float lastPitch;

    public KillAuraE() {
        super(Check.CheckType.KILL_AURAE, "E", "KillAura", Check.CheckVersion.RELEASE);
        this.violations = -1.0;
        this.pitchList = new ConcurrentLinkedQueue<>();
        this.lastPitch = null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying) {
            PacketPlayInFlying packetPlayInFlying = (PacketPlayInFlying) packet;
            if (packetPlayInFlying.h()) {
                if (this.lastPitch != null && playerData.getLastAttackTicks() <= 3 && playerData.getLastAttacked() != null) {
                    this.pitchList.add((double) Math.abs(packetPlayInFlying.e() - this.lastPitch));
                    if (this.pitchList.size() >= 20) {
                        double deviation = MathUtils.deviation(this.pitchList);
                        double average = MathUtils.average(this.pitchList);
                        if ((average > 17.5 && deviation > 15.0) || (average > 22.5 && deviation > 12.5)) {
                            VL.getInstance().handleViolation(playerData, this, "A " + average + ", D" + deviation, false);
                        } else {
                            this.decreaseVL(0.025);
                        }
                        this.pitchList.clear();
                    }
                }
                this.lastPitch = packetPlayInFlying.e();
            }
        }
    }
}


