package fun.billy.fbi.check.checks.aimassist;

import fun.billy.fbi.check.type.AimCheck;
import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;

public class AimB
        extends AimCheck {
    private boolean lastYaw, lastPitch;
    private double lastPitchAbs, lastYawAbs;
    private int vl;

    public AimB() {
        super(Check.CheckType.AIM_ASSISTB, "B", "AimAssist", Check.CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        if (to.getYaw() == from.getYaw() && !lastYaw && lastYawAbs > 1 || to.getPitch() == from.getPitch() && !lastPitch && lastPitchAbs > 1) {
            if (vl > 5) {
                vl = 0;
                //VL.getInstance().handleViolation(playerData, this, "ABS" + lastYawAbs + " " + lastPitchAbs, false);
                VL.getInstance().handleViolation(playerData, this, "Same", false);
            }
        } else {
            violations -= Math.min(violations + 6.0, 0.05);
            vl = 0;
        }
        lastYaw = (playerData.getLocation().getYaw() == playerData.getLastLastLocation().getYaw());
        lastPitch = (playerData.getLocation().getPitch() == playerData.getLastLastLocation().getPitch());
        lastPitchAbs = Math.abs(playerData.getLocation().getPitch() - playerData.getLastLastLocation().getPitch());
        lastYawAbs = Math.abs(playerData.getLocation().getYaw() - playerData.getLastLastLocation().getYaw());
    }
}


