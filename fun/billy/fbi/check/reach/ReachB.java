package fun.billy.fbi.check.checks.reach;

import fun.billy.fbi.check.type.ClientCheck;
import fun.billy.fbi.data.ClientData;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;

import java.text.DecimalFormat;

public class ReachB
        extends ClientCheck {
    ThreadLocal<DecimalFormat> REACH_FORMAT = ThreadLocal.withInitial(() -> new DecimalFormat("#.##"));

    public ReachB() {
        super(CheckType.REACHB, "B", "Reach", CheckVersion.RELEASE);
    }

    @Override
    public void handle(PlayerData playerData, ClientData clientData, long timestamp) {
        double reach = clientData.getReach();
        double extra = clientData.getExtra();
        double movement = clientData.getMovement();
        if (reach > 3.6d && extra < 6.0 && movement < 6.0){
            VL.getInstance().handleViolation(playerData, this, "r=" + reach, false);
        }
    }
}