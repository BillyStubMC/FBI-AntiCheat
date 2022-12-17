package fun.billy.fbi.check.checks.aimassist;

import fun.billy.fbi.check.type.AimCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;

public class AimJ
        extends AimCheck {
    private float avgYaw = 0.0f;
    private float avgPitch = 0.0f;
    private boolean pitchLast;

    public AimJ() {
        super(CheckType.AIM_ASSISTJ, "J", "AimAssist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        boolean pitchDiff;
        boolean bl = pitchDiff = from.getPitch() > to.getPitch();
        if (playerData.getLastAttackTicks() <= 50) {
            float yawChange = Math.abs(from.getYaw() - to.getYaw());
            float pitchChange = Math.abs(from.getPitch() - to.getPitch());
            this.avgYaw = (yawChange + this.avgYaw * 2.0f) / 3.0f;
            if ((double)pitchChange > 0.001 && (double)pitchChange < 0.01 && (double)yawChange > 0.01 && pitchDiff != this.pitchLast) {
                //VL.getInstance().handleViolation(playerData, this, "" + pitchChange + " " + yawChange + " " + pitchDiff, false);
                VL.getInstance().handleViolation(playerData, this, "Average", false);
            } else {
                this.violations -= Math.min(this.violations + 1.5, 0.001);
            }
            this.avgPitch = (pitchChange + this.avgPitch * 2.0f) / 3.0f;
        }
        this.pitchLast = pitchDiff;
    }
}
