package fun.billy.fbi.check.checks.reach;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.check.type.ClientCheck;
import fun.billy.fbi.data.ClientData;
import fun.billy.fbi.data.manager.violations.VL;

import java.text.DecimalFormat;

public class ReachA
        extends ClientCheck {
    ThreadLocal<DecimalFormat> REACH_FORMAT = ThreadLocal.withInitial(() -> new DecimalFormat("#.##"));

    public ReachA() {
        super(Check.CheckType.REACHA, "A", "Reach", Check.CheckVersion.RELEASE);
    }

    @Override
    public void handle(PlayerData playerData, ClientData clientData, long timestamp) {
        double reach = clientData.getReach();
        double extra = clientData.getExtra();
        double movement = clientData.getMovement();
        if (reach > 4.2d && extra < 6.0 && movement < 6.0){
            VL.getInstance().handleViolation(playerData, this, "r=" + reach, false);
        }
    }
}