package fun.billy.fbi.check.checks.reach;

import fun.billy.fbi.check.type.ClientCheck;
import fun.billy.fbi.data.ClientData;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;

import java.text.DecimalFormat;

public class ReachE
        extends ClientCheck {
    private static final ThreadLocal<DecimalFormat> REACH_FORMAT = ThreadLocal.withInitial(() -> new DecimalFormat("#.###"));

    public ReachE() {
        super(CheckType.REACHE, "E", "Reach", CheckVersion.EXPERIMENTAL);
    }

    @Override
    public void handle(PlayerData playerData, ClientData clientData, long timestamp) {
        double range = clientData.getReach();
        double movement = clientData.getMovement();
        if (movement == 0) {
            if (range > 3.0) {
                DecimalFormat decimalFormat = REACH_FORMAT.get();
                double extra = clientData.getExtra();
                double horizontal = clientData.getHorizontal();
                VL.getInstance().handleViolation(playerData, this, String.valueOf(decimalFormat.format(range)) + " H " + decimalFormat.format(horizontal) + " E " + decimalFormat.format(extra) + " M " + decimalFormat.format(movement), false);
            } else {
                this.violations -= Math.min(this.violations + 1.5, 0.01);
            }
        }
    }
}