package fun.billy.fbi.check.checks.killaura;

import fun.billy.fbi.check.type.AimCheck;
import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;
import fun.billy.fbi.util.utils.MathUtils;

public class KillAuraA
        extends AimCheck {
    private int number = 0;

    public KillAuraA() {
        super(Check.CheckType.KILL_AURAA, "A", "KillAura", Check.CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        if (playerData.getLastAttacked() != null && playerData.getLastAttackTicks() <= 6 && playerData.getTotalTicks() > 100) {
            PlayerLocation playerLocation = playerData.getLocation();
            PlayerLocation targetLocation = playerData.getLastAttacked().getLocation(playerData.getPingTicks());
            if (targetLocation != null) {
                double angle = MathUtils.getDistanceBetweenAngles(playerLocation.getYaw(), MathUtils.getRotationFromPosition(playerLocation, targetLocation)[0]);
                if ((angle = Math.min(180.0 - angle, angle)) < 15.0) {
                    ++this.number;
                } else {
                    if (angle > 30.0 && Math.abs(from.getYaw() - to.getYaw()) > 30.0f && this.number > 6 && playerData.getLastAttackTicks() <= 3) {
                        VL.getInstance().handleViolation(playerData, this, "A " + angle, false);
                    } else {
                        this.violations -= Math.min(this.violations + 1.0, 0.05);
                    }
                    this.number = 0;
                }
            }
        }
    }
}

