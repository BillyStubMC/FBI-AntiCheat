package fun.billy.fbi.check.checks.aimassist;

import fun.billy.fbi.check.type.AimCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;

public class AimG
        extends AimCheck {
    private int vl;


    public AimG() {
        super(CheckType.AIM_ASSISTG, "G", "AimAssist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        if (playerData.getLastAttackTicks() <= 100) {
            float yawChange = Math.abs(to.getYaw() - from.getYaw());
            float pitchChange = Math.abs(to.getPitch() - from.getPitch());
            if (yawChange > 0.0f && (double)yawChange < 0.01 && (double)pitchChange > 0.2) {
                //VL.getInstance().handleViolation(playerData, this, "PC " + pitchChange + "YC " + yawChange, false);
                VL.getInstance().handleViolation(playerData, this, "Small Change", false);
            }
        } else {
            this.decreaseVL(1.0E-4);
            vl = 0;
        }
    }
}