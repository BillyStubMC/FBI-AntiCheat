package fun.billy.fbi.check.checks.speed;

import fun.billy.fbi.check.type.MovementCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;
import fun.billy.fbi.util.utils.BukkitUtils;
import fun.billy.fbi.util.utils.CollisionUtil;
import fun.billy.fbi.util.utils.MathUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;

public class SpeedD
        extends MovementCheck {
    private int iceSlimeTicks, underBlockTicks, sprintTicks, nonSprintTicks, vl;

    public SpeedD() {
        super(CheckType.SPEEDD, "D", "Speed", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation playerLocation, PlayerLocation playerLocation2, long timestamp) {
        if (playerData.isOnGround() && !playerData.getPlayer().getAllowFlight() && playerData.getPlayer().getGameMode().equals(GameMode.SURVIVAL) && !playerData.isTeleporting()) {

            double hypot = MathUtils.hypot(playerLocation2.getX() - playerLocation.getX(), playerLocation2.getZ() - playerLocation.getZ());
            double limit = BukkitUtils.getBaseSpeed(playerData.getPlayer());
            final boolean onIce = CollisionUtil.isOnChosenBlock(playerData.getPlayer(), -0.5001, Material.ICE, Material.PACKED_ICE, Material.SLIME_BLOCK);
            final boolean underBlock = CollisionUtil.blockNearHead(playerData.getLocation(), playerData.getPlayer());
            final boolean sprint = playerData.getEntityPlayer().isSprinting();

            if (hypot == 0.24) {
                VL.getInstance().handleViolation(playerData, this, hypot + " > " + limit, false);
            }
        }
    }
}
