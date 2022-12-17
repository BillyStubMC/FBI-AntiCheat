package fun.billy.fbi.check.checks.hitboxes;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.ClientCheck;
import fun.billy.fbi.data.ClientData;
import fun.billy.fbi.data.DistanceData;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;

public class HitBoxesA
        extends ClientCheck {
    private int vl;

    public HitBoxesA() {
        super(CheckType.HITBOXESA, "A", "HitBoxes", Check.CheckVersion.RELEASE);
        this.violations = -2.0;
    }

    @Override
    public void handle(PlayerData playerData, ClientData clientData, long timestamp) {
        DistanceData distanceData = clientData.getDistanceData();
        double hitboxX = distanceData.getX() / distanceData.getDist() * clientData.getExtra();
        double hitboxZ = distanceData.getZ() / distanceData.getDist() * clientData.getExtra();
        if (Math.max(Math.abs(hitboxX), Math.abs(hitboxZ)) > 0.5 && !playerData.getPlayer().isInsideVehicle()) {
            if (vl++ > 2 + playerData.getPingTicks() / 3) {
                VL.getInstance().handleViolation(playerData, this, "X " + hitboxX + " Z " + hitboxZ, false);
            }
        } else {
            this.violations -= Math.min(this.violations + 2.0, 0.1);
            vl = 0;
        }
    }
}

