package fun.billy.fbi.check.checks.fly;

import fun.billy.fbi.check.type.MovementCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;
import fun.billy.fbi.util.utils.CollisionUtil;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;

public class FlyC
        extends MovementCheck {
    private boolean lastOnGround;
    private boolean lastLastOnGround;
    private double lastDistY;
    private int vl;

    public FlyC() {
        super(CheckType.FLYC, "C", "Fly", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    public boolean isRoughlyEqual(final double d1, final double d2) {
        return Math.abs(d1 - d2) < 0.001;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        final double distY = to.getY() - from.getY();
        final double lastDistY = this.lastDistY;
        this.lastDistY = distY;
        final double predictedDist = (lastDistY - 0.08) * 0.9800000190734863;
        final boolean onGround = to.getOnGround();
        final boolean lastOnGround = this.lastOnGround;
        this.lastOnGround = onGround;
        final boolean lastLastOnGround = this.lastLastOnGround;
        this.lastLastOnGround = lastOnGround;
        if (playerData.getLastAttackTicks() >= 8) {
            if (!onGround && !lastOnGround && !lastLastOnGround && Math.abs(predictedDist) >= 0.005) {
                if (!this.isRoughlyEqual(distY, predictedDist) && !playerData.isSpawnedIn() && !playerData.isTeleporting()) {
                    VL.getInstance().handleViolation(playerData, this, "Y " + distY, false);
                }
            }
            if (playerData.getLastAttackTicks() <= 7) {
            }
        }
    }

    public boolean isNearGround(final Location location) {
        for (double expand = 0.3, x = -expand; x <= expand; x += expand) {
            for (double z = -expand; z <= expand; z += expand) {
                if (location.clone().add(x, -0.5001, z).getBlock().getType() != Material.AIR) {
                    return true;
                }
            }
        }
        return false;
    }
}