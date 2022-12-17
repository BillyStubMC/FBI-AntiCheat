package fun.billy.fbi.check.checks.nofall;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.MovementCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.utils.CollisionUtil;
import fun.billy.fbi.util.PlayerLocation;
import fun.billy.fbi.util.utils.MathUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;

public class NoFallB
        extends MovementCheck {
    private int vl, ticksSinceInVehicle;

    public NoFallB() {
        super(CheckType.NOFALLB, "B", "NoFall", Check.CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        if (!playerData.getPlayer().getAllowFlight() && playerData.getPlayer().getGameMode().equals(GameMode.SURVIVAL) && !playerData.isTeleporting() && !playerData.hasLag()) {

            final boolean serverGround = from.getOnGround() && to.getOnGround();
            final boolean clientGround = MathUtils.onGround(playerData.getPlayer().getLocation().getY());
            final boolean onBlock = CollisionUtil.isInLiquid(playerData.getPlayer()) && CollisionUtil.isCollidingWithClimbable(playerData.getPlayer());

            if (CollisionUtil.isOnChosenBlock(playerData.getPlayer(), 2.0, Material.LADDER))
                return;

            if (playerData.getPlayer().isInsideVehicle()) {
                ticksSinceInVehicle = 0;
            } else {
                ++ticksSinceInVehicle;
            }

            if (!onBlock && serverGround != clientGround && ticksSinceInVehicle > 10) {
                if (++this.vl > 5)
                    VL.getInstance().handleViolation(playerData, this, "S " + serverGround + " C " + clientGround, false);
            } else {
                this.vl = 0;
            }
        }
    }
}