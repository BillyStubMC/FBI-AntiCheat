package fun.billy.fbi.check.checks.aimassist;

import fun.billy.fbi.check.type.AimCheck;
import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;

public class AimC
        extends AimCheck {
    private double vl;

    public AimC() {
        super(Check.CheckType.AIM_ASSISTC, "C", "AimAssist", Check.CheckVersion.RELEASE);
        this.violations = -5.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        if (Math.abs(to.getYaw() - from.getYaw()) != 0.0f && Math.abs(to.getYaw() - from.getYaw()) == Math.abs(to.getPitch() - from.getPitch())) {
            if (this.vl < 10.0) {
                ++this.vl;
            }
            if (this.vl > 4.0) {
                VL.getInstance().handleViolation(playerData, this, "Rounded", false);
            }
        } else {
            if (this.vl > 0.0) {
                this.vl -= 0.2;
            }
            this.violations -= Math.min(this.violations + 5.0, 0.005);
        }
    }
}
