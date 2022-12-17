package fun.billy.fbi.check.checks.speed;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.MovementCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;
import fun.billy.fbi.util.utils.BukkitUtils;
import fun.billy.fbi.util.utils.CollisionUtil;
import fun.billy.fbi.util.utils.MathUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;

public class SpeedB
        extends MovementCheck {
    private int iceSlimeTicks, underBlockTicks, sprintTicks, nonSprintTicks, vl;

    public SpeedB() {
        super(Check.CheckType.SPEEDB, "B", "Speed", Check.CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation playerLocation, PlayerLocation playerLocation2, long timestamp) {
        if (!playerData.getPlayer().getAllowFlight() && playerData.getPlayer().getGameMode().equals(GameMode.SURVIVAL) && !playerData.isTeleporting()) {

            double hypot = MathUtils.hypot(playerLocation2.getX() - playerLocation.getX(), playerLocation2.getZ() - playerLocation.getZ());
            double limit = BukkitUtils.getBaseSpeed(playerData.getPlayer());
            final boolean onIce = CollisionUtil.isOnChosenBlock(playerData.getPlayer(), -0.5001, Material.ICE, Material.PACKED_ICE, Material.SLIME_BLOCK);
            final boolean underBlock = CollisionUtil.blockNearHead(playerData.getLocation(), playerData.getPlayer());
            final boolean sprint = playerData.getEntityPlayer().isSprinting();

            if (onIce)
                iceSlimeTicks = 0;
            if (underBlock)
                underBlockTicks = 0;
            if (++iceSlimeTicks < 40)
                limit += 0.34;
            if (++underBlockTicks < 40)
                limit += 0.7;

            if (hypot > limit && playerData.getKbticks() > ((playerData.getPlayer().getMaximumNoDamageTicks() < 15) ? 20 : 10)) {
                if (++this.vl >= playerData.getPingTicks() + 4) // was originally 7, but will false on lag.
                        VL.getInstance().handleViolation(playerData, this, hypot + " > " + limit, false);
            } else {
                this.vl = 0;
            }

        }
    }
}