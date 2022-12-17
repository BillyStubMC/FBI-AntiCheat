package fun.billy.fbi.check.checks.motion;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.MovementCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;
import fun.billy.fbi.util.utils.MathUtils;
import org.bukkit.GameMode;

public class MotionD
        extends MovementCheck {
    private int vl;

    public MotionD() {
        super(CheckType.MOTIOND, "D", "Motion", Check.CheckVersion.RELEASE);
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        if (!playerData.getPlayer().getAllowFlight() && playerData.getPlayer().getGameMode().equals(GameMode.SURVIVAL) && !playerData.isTeleporting()) {
            if (MathUtils.isScientificNotation(Math.abs(playerData.getDeltaY()))) {
                if (++this.vl > 3)
                    VL.getInstance().handleViolation(playerData, this, "d=" + playerData.getDeltaY(), false);
            } else {
                this.vl = Math.max(0, this.vl - 1);
            }
        }
    }
}