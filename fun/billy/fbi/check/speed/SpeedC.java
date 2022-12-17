package fun.billy.fbi.check.checks.speed;

import fun.billy.fbi.check.type.MovementCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.MathUtils;
import fun.billy.fbi.util.PlayerLocation;
import fun.billy.fbi.util.utils.BukkitUtils;
import fun.billy.fbi.util.utils.CollisionUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class SpeedC
        extends MovementCheck {
    private int iceSlimeTicks, underBlockTicks, sprintTicks, nonSprintTicks, vl;

    public SpeedC() {
        super(CheckType.SPEEDC, "C", "Speed", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    private PlayerLocation lastLastLocation = null;
    private final Set<Integer> DIRECTIONS = new HashSet<>(Arrays.asList(45, 90, 135, 180));
    private double threshold;

    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        if (!playerData.isTeleporting()) {
            return;
        }

        float yawDelta = Math.abs(to.getYaw() - from.getYaw());
        float yawDeltaClamped = (float) MathUtils.yawTo180D(yawDelta);

        PlayerLocation lastLocation = null;
        if (lastLastLocation != null) {
            float moveAngle = MathUtils.getMoveAngle(lastLastLocation, lastLocation);

            double deltaX = to.getX() - from.getX();
            double deltaZ = to.getZ() - from.getZ();

            double deltaXZ = Math.hypot(deltaX, deltaZ);

            if ((double) yawDeltaClamped > 0.001 && (double) yawDeltaClamped <= 360) {
                if (deltaXZ > 0.01 && !playerData.isOnGround()) {
                    DIRECTIONS.forEach(direction -> {
                        double change = Math.abs(direction - moveAngle);

                        if (change < 0.0001F) {
                            if (threshold++ > 4) {
                                VL.getInstance().handleViolation(playerData, this, "R", false);
                            }
                        } else {
                            threshold -= Math.min(threshold, 0.02f);
                        }
                    });
                }

            }
        }

        lastLastLocation = lastLocation;
    }
}