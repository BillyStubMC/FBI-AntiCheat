package fun.billy.fbi.check.checks.velocity;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.MovementCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.Cuboid;
import fun.billy.fbi.util.PlayerLocation;
import fun.billy.fbi.util.utils.MathUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class VelocityB
        extends MovementCheck {
    private int lastBypassTicks = 0;
    private double total = 0.0;

    public VelocityB() {
        super(Check.CheckType.VELOCITYB, "B", "Velocity", Check.CheckVersion.RELEASE);
        this.violations = -1.5;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        if (playerData.getVelX() != 0.0 && playerData.getVelZ() != 0.0 && playerData.getHorizontalVelocityTicks() > playerData.getMoveTicks() && (to.getY() - from.getY()) > 0.0) {
            double velocity = MathUtils.hypot(playerData.getVelX(), playerData.getVelZ());
            if (playerData.getLastLastLocation().getOnGround() && from.getOnGround() && !to.getOnGround() && MathUtils.onGround(from.getY()) && !MathUtils.onGround(to.getY()) && velocity > 0.0 && playerData.getTotalTicks() - this.lastBypassTicks > 10) {
                Vector vector = new Vector(playerData.getLastLastLocation().getX(), playerData.getLastLastLocation().getY(), playerData.getLastLastLocation().getZ());
                vector.subtract(new Vector(from.getX(), from.getY(), from.getZ()));
                PlayerLocation newTo = to.add(vector.getX(), vector.getY(), vector.getZ());
                double distance = MathUtils.hypot(to.getX() - from.getX(), to.getZ() - from.getZ());
                double properDistance = MathUtils.hypot(newTo.getX() - from.getX(), newTo.getZ() - from.getZ());
                double percentage = Math.max(distance, properDistance) / velocity;
                this.violations -= Math.min(this.violations, 0.01);
                if (percentage < 1.0) {
                    World world = playerData.getPlayer().getWorld();
                    Cuboid cuboid = new Cuboid(to).add(new Cuboid(-1.0, 1.0, 0.0, 2.05, -1.0, 1.0));
                    int totalTicks = playerData.getTotalTicks();
                    this.run(() -> {
                        if (cuboid.checkBlocks(world, type -> type == Material.AIR)) {
                            this.total += 1.0 - percentage;
                            if (this.total > 2.0) {
                                this.total = 0.0;
                                VL.getInstance().handleViolation(playerData, this, "t=" + distance + ", p=:" + properDistance, false);
                            }
                        } else {
                            this.total -= Math.min(this.total, 0.03);
                            this.lastBypassTicks = totalTicks;
                            this.violations -= Math.min(this.violations + 1.5, 0.01);
                        }
                    });
                } else {
                    this.total -= Math.min(this.total, 0.03);
                    this.violations -= Math.min(this.violations + 1.5, 0.01);
                }
            }
            playerData.setVelX(0.0);
            playerData.setVelZ(0.0);
        }
    }
}

