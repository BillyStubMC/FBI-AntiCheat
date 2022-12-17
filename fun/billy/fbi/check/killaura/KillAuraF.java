package fun.billy.fbi.check.checks.killaura;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class KillAuraF
        extends PacketCheck {
    private Float lastYaw;
    private float yawSpeed = 360.0f;

    public KillAuraF() {
        super(CheckType.KILL_AURAA, "F", "KillAura", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying.PacketPlayInLook) {
            PacketPlayInFlying.PacketPlayInLook look = (PacketPlayInFlying.PacketPlayInLook) ((Object) packet);
            float yawChange = Math.abs(this.lastYaw.floatValue() - look.d());
            if (this.yawSpeed < 45.0f && playerData.getTeleportTicks() > 5) {
                if (yawChange > 345.0f && yawChange < 375.0f) {
                    if ((double) Math.abs(Math.abs(360.0f - (yawChange + Math.abs(180.0f - Math.abs(look.d() % 180.0f - this.lastYaw.floatValue() % 180.0f))))) == 0.0) {
                        VL.getInstance().handleViolation(playerData, this, "c=" + yawChange, false);
                    }
                } else if ((double) yawChange > 172.5 && (double) yawChange < 187.5 && (double) Math.abs(Math.abs(180.0f - (yawChange + Math.abs(90.0f - Math.abs(look.d() % 90.0f - this.lastYaw.floatValue() % 90.0f))))) == 0.0) {
                    VL.getInstance().handleViolation(playerData, this, "C " + yawChange, false);
                }
                this.violations -= Math.min(this.violations + 2.0, 0.01);
            }
            this.yawSpeed *= 3.0f;
            this.yawSpeed += yawChange;
            this.yawSpeed /= 4.0f;
        }
    }
}