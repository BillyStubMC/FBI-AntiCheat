package fun.billy.fbi.check.checks.reach;

import fun.billy.fbi.check.type.ClientCheck;
import fun.billy.fbi.data.ClientData;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;

import java.text.DecimalFormat;

public class ReachF
        extends ClientCheck {
    private static final ThreadLocal<DecimalFormat> REACH_FORMAT = ThreadLocal.withInitial(() -> new DecimalFormat("#.###"));
    private int vl;

    public ReachF() {
        super(CheckType.REACHF, "F", "Reach", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, ClientData clientData, long timestamp) {
        double range = clientData.getReach();
        double vertical = clientData.getVertical();
        double movement = clientData.getMovement();
        if (range > 3.0) {
            DecimalFormat decimalFormat = REACH_FORMAT.get();
            if (++this.vl > 5)
                VL.getInstance().handleViolation(playerData, this, "R" + range, false);
            if (++this.vl >= 5)
                VL.getInstance().handleDebug(playerData, this, "R " + range);
        } else {
            this.violations -= Math.min(this.violations - 1.5, 0.01);
        }
    }
}