package fun.billy.fbi.check.checks.speed;

import fun.billy.fbi.check.type.MovementCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;
import fun.billy.fbi.util.utils.BukkitUtils;
import fun.billy.fbi.util.utils.CollisionUtil;
import fun.billy.fbi.util.utils.MathUtils;
import net.minecraft.server.v1_8_R3.BlockPosition;
import org.bukkit.GameMode;
import org.bukkit.potion.PotionEffectType;

public class SpeedE
        extends MovementCheck {
    private double lastOffsetH, blockFriction = 0.91f;

    public SpeedE() {
        super(CheckType.SPEEDE, "E", "Speed", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        if (!playerData.getPlayer().getAllowFlight() && playerData.getPlayer().getGameMode().equals(GameMode.SURVIVAL) && !playerData.isTeleporting() && !CollisionUtil.isOnGround(to, playerData.getPlayer(), -0.00001)) {

            final double distanceXZ = MathUtils.hypot(to.getX() - from.getX(), to.getZ() - from.getZ());
            final double deltaY = playerData.getDeltaY();
            final boolean onGround = from.getOnGround();
            final double jumpHeight = 0.48 + BukkitUtils.getPotionLevel(playerData.getPlayer(), PotionEffectType.JUMP) * 0.1;

            double movementSpeed = playerData.getMovementSpeed();


            if (onGround) {
                movementSpeed *= 1.2;

                if (MathUtils.getMoveAngle(from, to) > 90)
                    movementSpeed /= 1.3;

                movementSpeed *= 0.4 / Math.pow(blockFriction, 3);

                if (deltaY > 0.00001 && deltaY < jumpHeight) {
                    movementSpeed += 0.4; // jump speed boost
                }
            } else {
                movementSpeed = 0.03F;
                this.blockFriction = 0.95f;
            }

            double speedup = (distanceXZ - lastOffsetH) / movementSpeed;

            if (playerData.isOnGround() && playerData.getKbticks() > ((playerData.getPlayer().getMaximumNoDamageTicks() < 15) ? 20 : 10)) {
                //VL.getInstance().handleViolation(playerData, this,  "MS " + movementSpeed, false);
            } else {
                this.decreaseVL(0.025);
            }

            BlockPosition blockPosition = new BlockPosition(
                    Math.floor(to.getX()), Math.floor(to.getY() - 1), Math.floor(to.getZ()));

            this.lastOffsetH = Math.max(distanceXZ, 0.25) * this.blockFriction;
            this.blockFriction = playerData.getEntityPlayer().world.getType(blockPosition).getBlock().frictionFactor * 0.91f;
        }
    }
}