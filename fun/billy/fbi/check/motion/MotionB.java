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

public class MotionB
        extends MovementCheck {
    private int slimeTicks;

    public MotionB() {
        super(CheckType.MOTIONB, "B", "Motion", Check.CheckVersion.RELEASE);
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        if (!playerData.getPlayer().getAllowFlight() && playerData.getPlayer().getGameMode().equals(GameMode.SURVIVAL) && !playerData.isTeleporting()) {

            float expectedJumpMotion = 0.42F;
            final boolean jumped = CollisionUtil.isOnGround(from, playerData.getPlayer(), -0.00001) &&
                    !CollisionUtil.isOnGround(to, playerData.getPlayer(), -0.00001) &&
                    playerData.getDeltaY() > 0;

            if (CollisionUtil.isOnChosenBlock(playerData.getPlayer(), -0.5001, Material.ICE, Material.PACKED_ICE, Material.SLIME_BLOCK))
                this.slimeTicks = 0;

            if (BukkitUtils.getPotionLevel(playerData.getPlayer(), PotionEffectType.JUMP) > 0)
                expectedJumpMotion += BukkitUtils.getPotionLevel(playerData.getPlayer(), PotionEffectType.JUMP) * 0.1F;

            final boolean valid = jumped && !CollisionUtil.isCollidingWithClimbable(playerData.getPlayer()) &&
                    from.getY() % 1 == 0 &&
                    !CollisionUtil.blockNearHead(to, playerData.getPlayer()) && ++this.slimeTicks > 20 &&
                    playerData.getKbticks() > ((playerData.getPlayer().getMaximumNoDamageTicks() < 15) ? 20 : 10);

            if (playerData.getDeltaY() < expectedJumpMotion && valid)
                VL.getInstance().handleViolation(playerData, this, "d=" + playerData.getDeltaY(), false);
        }
    }
}
