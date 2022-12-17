package fun.billy.fbi.check.checks.aimassist;

import fun.billy.fbi.check.type.AimCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;

public class AimH
        extends AimCheck {
    private int vl;
    private float lastYawRate;
    private float lastPitchRate;

    public AimH() {
        super(CheckType.AIM_ASSISTG, "H", "AimAssist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        if (playerData.getLastAttackTicks() > 200) {
            return;
        }
        float diffYaw = Math.abs(to.getYaw() - from.getYaw());
        float diffPitch = Math.abs(to.getPitch() - from.getPitch());
        float diffYawPitch = Math.abs(diffYaw - diffPitch);
        float diffPitchRate = Math.abs(this.lastPitchRate - diffPitch);
        float diffYawRate = Math.abs(this.lastYawRate - diffYaw);
        float diffPitchRatePitch = Math.abs(diffPitchRate - diffPitch);
        float diffYawRateYaw = Math.abs(diffYawRate - diffYaw);
        if (diffYaw > 0.05f && (double)diffPitch > 0.05 && ((double)diffPitchRate > 1.0 || (double)diffYawRate > 1.0) && (diffPitchRatePitch > 1.0f || diffYawRateYaw > 1.0f) && diffYawPitch < 0.009f && diffYawPitch > 0.001f) {
            //VL.getInstance().handleViolation(playerData, this, "" + diffPitch + " " + diffPitchRate + " " + diffYawPitch + "", false);
            VL.getInstance().handleViolation(playerData, this, "Rate", false);
        }
        this.lastYawRate = diffYaw;
        this.lastPitchRate = diffPitch;
    }
}
