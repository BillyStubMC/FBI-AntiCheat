package fun.billy.fbi.check.checks.motion;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.MovementCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.utils.CollisionUtil;
import fun.billy.fbi.util.PlayerLocation;
import org.bukkit.GameMode;

public class MotionC
        extends MovementCheck {

    public MotionC() {
        super(CheckType.MOTIONC, "C", "Motion", Check.CheckVersion.RELEASE);
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        if (!playerData.getPlayer().getAllowFlight() && playerData.getPlayer().getGameMode().equals(GameMode.SURVIVAL) && !playerData.isTeleporting()) {
            final boolean colliding = CollisionUtil.isOnGround(from, playerData.getPlayer(), -0.5001)
                    && CollisionUtil.isOnGround(playerData.getLocation(), playerData.getPlayer(), -0.5001);

            if (colliding) {
                if (playerData.getDeltaY() > 0.6F)
                    VL.getInstance().handleViolation(playerData, this, "d=" + playerData.getDeltaY(), false);
            }
        }
    }
}
