package fun.billy.fbi.check.checks.aimassist;

import fun.billy.fbi.check.type.AimCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;

public class AimA
        extends AimCheck {
    private double lastYaw, lastPitch, vl;

    public AimA() {
        super(CheckType.AIM_ASSISTA, "A", "AimAssist", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        double pitchDiff = Math.abs(to.getPitch() - from.getPitch());
        double yawDiff = Math.abs(to.getYaw() - from.getYaw());
        if (pitchDiff == lastPitch && pitchDiff > 0.4 || yawDiff == lastYaw && pitchDiff > 0.4) {
            if (++vl > 4) {
                vl = 0;
                //VL.getInstance().handleViolation(playerData, this, "PD " + pitchDiff + " LP " + lastPitch, false);
                VL.getInstance().handleViolation(playerData, this, "Constant", false);
            }
        } else {
            violations -= Math.min(violations + 1.0, 0.005);
            vl = 0;
        }
        lastYaw = yawDiff;
        lastPitch = pitchDiff;
    }
}
