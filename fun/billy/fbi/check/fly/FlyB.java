package fun.billy.fbi.check.checks.fly;

import fun.billy.fbi.check.type.MovementCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;
import fun.billy.fbi.util.utils.CollisionUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FlyB
        extends MovementCheck {
    private double lastDeltaY;
    private int vl;

    public FlyB() {
        super(CheckType.FLYB, "B", "Fly", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        if (Bukkit.getAllowFlight() && !to.getOnGround()) {
            final double offsetH = Math.hypot(to.getX() - from.getX(),to.getZ() - from.getZ());
            final double offsetY = to.getY() - from.getY();
            if (offsetH > 0.0 && offsetY == 0.0) {
                if (++vl >= 10) {
                    VL.getInstance().handleViolation(playerData, this, "H " + offsetH + " P " + offsetY, false);
                }
            }
            else {
                vl = 0;
            }
        }
        else {
            vl = 0;
        }
    }
}