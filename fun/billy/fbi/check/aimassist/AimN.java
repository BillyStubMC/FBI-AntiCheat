package fun.billy.fbi.check.checks.aimassist;

import fun.billy.fbi.check.type.AimCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;
import org.bukkit.entity.Player;

public class AimN
        extends AimCheck {

    public AimN() {
        super(CheckType.AIM_ASSISTM, "M", "AimAssist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        final Player p = playerData.getPlayer();
        if ((from.getPitch() == 0.0f && to.getPitch() == 0.0f) || (to.getPitch() == 0.0f && from.getPitch() == 0.0f && from.getPitch() != 90.0f && to.getPitch() != 90.0f)) {
            VL.getInstance().handleViolation(playerData, this, "INVALID ", false);
        }
        if ((from.getPitch() == 1.0f && to.getPitch() == 1.0f) || (to.getPitch() == -1.0f && from.getPitch() == -1.0f && from.getPitch() != 90.0f && to.getPitch() != 90.0f)) {
            VL.getInstance().handleViolation(playerData, this, "INVALID ", false);
        }
    }
}
