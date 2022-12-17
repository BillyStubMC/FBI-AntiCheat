package fun.billy.fbi.check.checks.fly;

import fun.billy.fbi.check.type.MovementCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;
import fun.billy.fbi.util.utils.MathUtils;

public class FlyF
        extends MovementCheck {
    private boolean lastOnGround;
    private boolean lastLastOnGround;
    private double lastDistY;
    private int vl;

    public FlyF() {
        super(CheckType.FLYF, "F", "Fly", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        final boolean cground = to.getOnGround();
        final boolean sground = MathUtils.onGround(playerData.getLocation().getY());
        if(cground != sground){
            VL.getInstance().handleViolation(playerData, this, "SPOOF", false);
        }
    }
}