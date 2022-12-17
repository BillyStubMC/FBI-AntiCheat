package fun.billy.fbi.check.checks.aimassist;

import fun.billy.fbi.check.type.AimCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;

public class AimE
        extends AimCheck {
    private int vl;

    public AimE() {
        super(CheckType.AIM_ASSISTE, "E", "AimAssist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        float abs = Math.abs(from.getYaw() - to.getYaw());
        float abs2 = Math.abs(from.getPitch() - to.getPitch());
        if (playerData.getLastAttackTicks() < 3 && abs > 0.0f && abs < 0.8 && abs2 > 0.279 && abs2 < 0.28090858) {
            if (++vl > 2) {
                vl = 0;
                //VL.getInstance().handleViolation(playerData, this, "ydiff:" + abs + ",pdiff:" + abs2, false);
                VL.getInstance().handleViolation(playerData, this, "ABS", false);
            }
        } else {
            this.decreaseVL(1.0E-4);
            vl = 0;
        }
    }
}
