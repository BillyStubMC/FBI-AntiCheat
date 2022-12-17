package fun.billy.fbi.check.checks.killaura;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class KillAuraB
        extends PacketCheck {
    private Float lastYaw = null;
    private float yawSpeed = 360.0f;

    public KillAuraB() {
        super(Check.CheckType.KILL_AURAB, "B", "KillAura", CheckVersion.EXPERIMENTAL);
        this.violations = -2.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying.PacketPlayInLook) {
            PacketPlayInFlying.PacketPlayInLook packetPlayInLook = (PacketPlayInFlying.PacketPlayInLook) packet;
            if (this.lastYaw != null && timestamp - playerData.getLastPosition() > 3500L) {
                float changeYaw = Math.abs(this.lastYaw - packetPlayInLook.d());
                if (this.yawSpeed < 45.0f && playerData.getTeleportTicks() > 5) {
                    if (changeYaw > 345.0f && changeYaw < 375.0f) {
                        float nearValue2 = Math.abs(360.0f - (changeYaw + Math.abs(180.0f - Math.abs(packetPlayInLook.d() % 180.0f - this.lastYaw % 180.0f))));
                        if ((double) Math.abs(nearValue2) == 0.0) {
                            VL.getInstance().handleViolation(playerData, this, "y=" + changeYaw, false);
                        }
                    } else if ((double) changeYaw > 172.5 && (double) changeYaw < 187.5 && (double) Math.abs(Math.abs(180.0f - (changeYaw + Math.abs(90.0f - Math.abs(packetPlayInLook.d() % 90.0f - this.lastYaw % 90.0f))))) == 0.0) {
                        VL.getInstance().handleViolation(playerData, this, "Y " + changeYaw, false);
                    }
                    this.violations -= Math.min(this.violations + 2.0, 0.1);
                }
                this.yawSpeed *= 3.0f;
                this.yawSpeed += changeYaw;
                this.yawSpeed /= 4.0f;
            }
            this.lastYaw = packetPlayInLook.d();
        }
    }
}

