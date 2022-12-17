package fun.billy.fbi.check.checks.aimassist;

import fun.billy.fbi.check.type.AimCheck;
import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;
import fun.billy.fbi.util.utils.MathUtils;

public class AimD
        extends AimCheck {
    private double vl;

    public AimD() {
        super(Check.CheckType.AIM_ASSISTD, "D", "AimAssist", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        float diffYaw = (float) MathUtils.getDistanceBetweenAngles(to.getYaw(), from.getYaw());
        if (from.getPitch() == to.getPitch() && diffYaw >= 3.0f && from.getPitch() != 90.0f && to.getPitch() != 90.0f) {
            if ((vl += 0.9) >= 6.3) {
                //VL.getInstance().handleViolation(playerData, this, "ydiff: " + diffYaw, false);
                VL.getInstance().handleViolation(playerData, this, "Change", false);
            }
        } else {
            vl -= 1.6;
        }
    }
}

