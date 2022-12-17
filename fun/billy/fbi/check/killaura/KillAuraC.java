package fun.billy.fbi.check.checks.killaura;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.MovementCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;
import fun.billy.fbi.util.utils.BukkitUtils;
import fun.billy.fbi.util.utils.MathUtils;
import org.bukkit.GameMode;
import org.bukkit.potion.PotionEffectType;

public class KillAuraC
        extends MovementCheck {
    private int threshold;
    private boolean lastOnGround;
    private Double lastValue;

    public KillAuraC() {
        super(Check.CheckType.KILL_AURAC, "C", "KillAura", Check.CheckVersion.RELEASE);
        this.violations = -2.5;
        this.threshold = 0;
        this.lastOnGround = false;
        this.lastValue = null;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation playerLocation, PlayerLocation playerLocation2, long timestamp) {
        if (playerLocation.getOnGround() && playerLocation2.getOnGround() && this.lastOnGround && playerData.getSprinting() != null && playerData.getSprinting() && playerData.getPlayer().getGameMode() != GameMode.CREATIVE) {
            double hypot = MathUtils.hypot(playerLocation2.getX() - playerLocation.getX(), playerLocation2.getZ() - playerLocation.getZ());
            if (this.lastValue != null && playerData.getLastAttackTicks() <= 1 && playerData.getLastAttacked() != null) {
                double n2 = 0.2325 + BukkitUtils.getPotionLevel(playerData.getPlayer(), PotionEffectType.SPEED) * 0.02;
                if (playerData.getPlayer().getWalkSpeed() > 0.2) {
                    n2 *= playerData.getPlayer().getWalkSpeed() / 0.2;
                }
                if (hypot >= this.lastValue * 0.99 && hypot > n2) {
                    if (this.threshold++ > 3) {
                        VL.getInstance().handleViolation(playerData, this, "H " + (hypot - n2), false);
                        this.threshold = 0;
                    }
                } else {
                    this.threshold = 0;
                    this.decreaseVL(0.05);
                }
            }
            this.lastValue = hypot;
        } else {
            this.lastValue = null;
        }
        this.lastOnGround = playerLocation.getOnGround();
    }
}

