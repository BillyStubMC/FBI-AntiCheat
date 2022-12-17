package fun.billy.fbi.check.checks.aimassist;

import fun.billy.fbi.check.type.AimCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;

public class AimF
        extends AimCheck {
    private int vl;

    public AimF() { //Valux AimAssist A
        super(CheckType.AIM_ASSISTF, "F", "AimAssist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        float pitchChange;
        float yawChange = Math.abs(to.getYaw() - from.getYaw());
        float abs = Math.abs(from.getYaw() - to.getYaw());
        float abs2 = Math.abs(from.getPitch() - to.getPitch());
        if (yawChange >= 1.0f && yawChange % 0.1f == 0.0f) {
            boolean badYaw;
            boolean bl = badYaw = yawChange % 1.0f == 0.0f || yawChange % 10.0f == 0.0f || yawChange % 30.0f == 0.0f;
            if (badYaw) {
                this.violations += 1.0;
                //VL.getInstance().handleViolation(playerData, this, "YAW:" + yawChange, false);
                VL.getInstance().handleViolation(playerData, this, "Pitch", false);
            }
            if ((pitchChange = Math.abs(to.getPitch() - from.getPitch())) >= 1.0f && pitchChange % 0.1f == 0.0f) {
                if (pitchChange % 1.0f == 0.0f) {
                    this.violations += 1.0;
                }
                if (pitchChange % 10.0f == 0.0f) {
                    this.violations += 1.0;
                }
                if (pitchChange % 30.0f == 0.0f) {
                    this.violations += 1.0;
                }
                VL.getInstance().handleViolation(playerData, this, "Pitch:" + pitchChange, false);
            }
        } else {
            this.decreaseVL(1.0E-4);
            vl = 0;
        }
    }
}