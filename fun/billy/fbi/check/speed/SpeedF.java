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

public class SpeedF
        extends MovementCheck {
    private int iceSlimeTicks, underBlockTicks, sprintTicks, nonSprintTicks, vl;

    public SpeedF() {
        super(CheckType.SPEEDF, "F", "Speed", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation playerLocation, PlayerLocation playerLocation2, long timestamp) {
        if (!playerData.isTeleporting()) {

            double hypot = MathUtils.hypot(playerLocation2.getX() - playerLocation.getX(), playerLocation2.getZ() - playerLocation.getZ());
            double patch = BukkitUtils.valuePatch1(playerData.getPlayer());
            if (hypot == patch) {
                VL.getInstance().handleViolation(playerData, this, "S " + patch, false);
            }
        }
    }
}
