package fun.billy.fbi.check.checks.nofall;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.MovementCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.Cuboid;
import fun.billy.fbi.util.CustomList;
import fun.billy.fbi.util.PlayerLocation;
import org.bukkit.GameMode;

public class NoFallA
        extends MovementCheck {

    public NoFallA() {
        super(CheckType.NOFALLA, "A", "NoFall", Check.CheckVersion.RELEASE);
        this.violations = -2.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        if (!playerData.getPlayer().getAllowFlight() && playerData.getPlayer().getGameMode().equals(GameMode.SURVIVAL) && !playerData.isTeleporting() && !playerData.isPlace()) {

            final double yDiff = from.getY() - to.getY();

            if (from.getOnGround() && yDiff > 0.079 && Math.abs(from.getY() % 0.5 - 0.015555072702202466) > 1.0E-12) {
                final Cuboid cuboid = new Cuboid(playerData.getLocation()).add(new Cuboid(-0.5, 0.5, -1.0, 1.5, -0.5, 0.5));

                this.run(() -> {
                    if (cuboid.checkBlocks(playerData.getPlayer().getWorld(), material -> !CustomList.BAD_VELOCITY.contains(material) && !CustomList.INVALID_SHAPE.contains(material)))
                        VL.getInstance().handleViolation(playerData, this, "D " + yDiff, false);
                });
                return;
            }
            this.decreaseVL(0.005);
        }
    }
}

