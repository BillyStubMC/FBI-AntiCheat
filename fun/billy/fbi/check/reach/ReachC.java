package fun.billy.fbi.check.checks.reach;

import fun.billy.fbi.check.type.ClientCheck;
import fun.billy.fbi.data.ClientData;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;

import java.text.DecimalFormat;

public class ReachC
        extends ClientCheck {
    private static final ThreadLocal<DecimalFormat> REACH_FORMAT = ThreadLocal.withInitial(() -> new DecimalFormat("#.###"));

    public ReachC() {
        super(CheckType.REACHC, "C", "Reach", CheckVersion.EXPERIMENTAL);
    }

    @Override
    public void handle(PlayerData playerData, ClientData clientData, long timestamp) {
        double reach = clientData.getReach();
        if (reach > 3.41) {
            DecimalFormat decimalFormat = REACH_FORMAT.get();
            double extra = clientData.getExtra();
            double movement = clientData.getMovement();
            double horizontal = clientData.getHorizontal();
            VL.getInstance().handleViolation(playerData, this, " D " + reach, false);
        }
    }
}