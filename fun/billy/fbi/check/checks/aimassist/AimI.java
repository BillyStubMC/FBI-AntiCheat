package fun.billy.fbi.check.checks.aimassist;

import fun.billy.fbi.check.type.AimCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;

public class AimI
        extends AimCheck {
    private int vl;
    private float lastYawRate;
    private float lastPitchRate;

    public AimI() {
        super(CheckType.AIM_ASSISTI, "I", "AimAssist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        if (playerData.getLastAttackTicks() > 200) {
            return;
        }
        float diffYaw = Math.abs(to.getYaw() - from.getYaw());
        float diffPitch = Math.abs(to.getPitch() - from.getPitch());
        float diffPitchRate = Math.abs(this.lastPitchRate - diffPitch);
        float diffYawRate = Math.abs(this.lastYawRate - diffYaw);
        float diffPitchRatePitch = Math.abs(diffPitchRate - diffPitch);
        float diffYawRateYaw = Math.abs(diffYawRate - diffYaw);
        if ((double)diffPitch < 0.009 && (double)diffPitch > 0.001 && (double)diffPitchRate > 1.0 && (double)diffYawRate > 1.0 && (double)diffYaw > 3.0 && (double)this.lastYawRate > 1.5 && (diffPitchRatePitch > 1.0f || diffYawRateYaw > 1.0f)) {
            //VL.getInstance().handleViolation(playerData, this, "" + diffPitch + " " + diffYaw + "/" + lastYawRate, false);
            VL.getInstance().handleViolation(playerData, this, "Small Rate", false);
        }
        this.lastYawRate = diffYaw;
        this.lastPitchRate = diffPitch;
    }
}
