package fun.billy.fbi.check.checks.aimassist;

import fun.billy.fbi.check.type.AimCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;

public class AimL
        extends AimCheck {

    public AimL() {
        super(CheckType.AIM_ASSISTL, "L", "AimAssist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        final float deltaYaw = Math.abs(to.getYaw() - from.getYaw());
        final float deltaPitch = Math.abs(to.getPitch() - from.getPitch());
        if (deltaYaw != 0.0f && Math.round(deltaYaw) == deltaYaw) {
            //VL.getInstance().handleViolation(playerData, this, "D " + deltaYaw, false);
            VL.getInstance().handleViolation(playerData, this, "Rounded [DEV]", false);
        }
        if (deltaPitch != 0.0f && Math.round(deltaPitch) == deltaPitch) {
            VL.getInstance().handleViolation(playerData, this, "Rounded [DEV]", false);
        }
    }
}
