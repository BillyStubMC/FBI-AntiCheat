package fun.billy.fbi.check.checks.velocity;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;
import fun.billy.fbi.util.utils.MathUtils;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class VelocityC
        extends PacketCheck {
    private int lastBypassTicks = 0;
    private double total = 0.0;

    public VelocityC() {
        super(CheckType.VELOCITYC, "C", "Velocity", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, Packet packet, long timeStamp) {
        if (packet instanceof PacketPlayInFlying) {
            PlayerLocation previous = playerData.getLastLastLocation();
            PlayerLocation current = playerData.getLocation();
            if (playerData.getVerticalVelocityTicks() > playerData.getMoveTicks() && playerData.getLastVelY() > 0.0) {
                double diffY = current.getY() - previous.getY();
                if (diffY > 0.0) {
                    double ratio = diffY / playerData.getLastVelY();
                    if (Math.ceil(diffY * 8000.0) / 8000.0 < (double) 0.42f && previous.getOnGround().booleanValue() && !current.getOnGround().booleanValue() && MathUtils.onGround(previous.getY()) && !MathUtils.onGround(current.getY()) && ratio < 0.995) {
                        VL.getInstance().handleViolation(playerData, this, "diff=" + diffY, false);
                    }
                    playerData.setLastVelY(0.0);
                    this.violations -= Math.min(this.violations, 0.01);
                }
            }
        }
    }
}