package fun.billy.fbi.check.checks.autoclicker;

import com.google.common.collect.Lists;
import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.Pair;
import fun.billy.fbi.util.utils.MathUtils;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

import java.util.Deque;
import java.util.List;

public class AutoClickerC
        extends PacketCheck {
    private int movements = 0;
    private double buffer = 0.0d;
    private final Deque<Integer> samples = Lists.newLinkedList();

    public AutoClickerC() {
        super(Check.CheckType.AUTO_CLICKERC, "C", "AutoClicker", Check.CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInArmAnimation) {

            if (playerData.getVersion() == 1.9) {
                return;
            }

            boolean valid = movements < 4 && !playerData.isDigging();

            // If the movements is smaller than 4 and the player isn't digging
            if (valid) samples.add(movements);

            // Once the samples size is equal to 15
            if (samples.size() == 15) {
                Pair<List<Double>, List<Double>> outlierPair = MathUtils.getOutliers(samples);

                // Get the deviation outliers the the cps from the math util
                double deviation = MathUtils.getStandardDeviation(samples);
                double outliers = outlierPair.getX().size() + outlierPair.getY().size();
                double cps = MathUtils.getCps(samples);

                // If the deviation is relatively low along with the outliers and the cps is rounded
                if (deviation < 0.3 && outliers < 2 && cps % 1.0 == 0.0) {
                    buffer += 0.25;

                    if (buffer > 0.75) {
                        //VL.getInstance().handleViolation(playerData, this, "D " + deviation + ", O " + outliers + ", CPS " + cps, false);
                        VL.getInstance().handleViolation(playerData, this, "Outliers", false);
                    }
                    VL.getInstance().handleDebug(playerData, this, "D " + deviation + ", O" + outliers + ", CPS " + cps);
                } else {
                    buffer = Math.max(buffer - 0.2, 0);
                }

                // Clear the samples
                samples.clear();
            }

            // Reset the movements
            movements = 0;
        }
    }
}