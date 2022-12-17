package fun.billy.fbi.check.checks.aimassist.gcd;

import fun.billy.fbi.check.type.AimCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;
import fun.billy.fbi.util.utils.MathUtils;

public class GcdA
        extends AimCheck {
    double last, result, previous, vl;

    public GcdA() {
        super(CheckType.AIM_GCDA, "N", "AimAssist", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        double pitchChange = Math.abs(from.getPitch() - to.getPitch());

        double gcd = MathUtils.gcd(0x4000, (pitchChange * Math.pow(2.0, 24.0)), (previous * Math.pow(2.0, 24.0)));

        if (Math.min(last, Math.atan(to.getPitch())) == result && gcd < 0x20000 && gcd > 0) {
            if (vl < 15) {
                vl++;
            }
            if (vl > 1) VL.getInstance().handleViolation(playerData, this, "GCD" + gcd, false);
        } else {
            if (vl > 0) {
                vl -= 0.5;
            }
        }

        previous = pitchChange;
        result = Math.min(last, Math.atan(to.getPitch()));
        last = Math.atan(to.getPitch());
    }
}
