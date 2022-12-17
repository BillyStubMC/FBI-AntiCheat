package fun.billy.fbi.check.checks.fly;

import fun.billy.fbi.check.type.MovementCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;
import org.bukkit.Location;
import org.bukkit.Material;

public class FlyE
        extends MovementCheck {
    private boolean lastOnGround;
    private boolean lastLastOnGround;
    private double lastDistY;
    private int vl;

    public FlyE() {
        super(CheckType.FLYE, "E", "Fly", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        final double diffY = Math.abs(from.getY() - to.getY());
        final double lastDiffY = from.getY();
        final double finalDifference = Math.abs(diffY - lastDiffY);
        if (finalDifference < 0.08 && from.getY() < to.getY() && !to.getOnGround()) {
            VL.getInstance().handleViolation(playerData, this, "FINAL " + finalDifference, false);
        }
    }
}