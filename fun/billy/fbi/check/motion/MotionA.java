package fun.billy.fbi.check.checks.motion;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.MovementCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.utils.CollisionUtil;
import fun.billy.fbi.util.PlayerLocation;
import fun.billy.fbi.util.utils.BukkitUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class MotionA
        extends MovementCheck {
    private int slimeTicks, inVehicleTicks;

    public MotionA() {
        super(CheckType.MOTIONA, "A", "Motion", Check.CheckVersion.RELEASE);
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        if (!playerData.getPlayer().getAllowFlight() && playerData.getPlayer().getGameMode().equals(GameMode.SURVIVAL) && !playerData.isTeleporting()) {

            float expectedJumpMotion = 0.6F;
            final boolean jumped = CollisionUtil.isOnGround(from, playerData.getPlayer(), -0.00001) &&
                    !CollisionUtil.isOnGround(to, playerData.getPlayer(), -0.00001) &&
                    playerData.getDeltaY() > 0;

            if (playerData.getPlayer().isInsideVehicle()) {
                this.inVehicleTicks = 0;
            } else {
                this.inVehicleTicks++;
            }

            if (CollisionUtil.isOnChosenBlock(playerData.getPlayer(), -0.5001, Material.SLIME_BLOCK))
                this.slimeTicks = 0;

            if (BukkitUtils.getPotionLevel(playerData.getPlayer(), PotionEffectType.JUMP) > 0)
                expectedJumpMotion += BukkitUtils.getPotionLevel(playerData.getPlayer(), PotionEffectType.JUMP) * 0.1F;

            if (playerData.getDeltaY() > expectedJumpMotion && ++this.slimeTicks > 20 && jumped && this.inVehicleTicks > 20)
                VL.getInstance().handleViolation(playerData, this, "d=" + playerData.getDeltaY(), false);
        }
    }
}

