package fun.billy.fbi.check.checks.autoclicker;

import com.google.common.collect.Lists;
import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.utils.MathUtils;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

import java.util.Deque;

public class AutoClickerB
        extends PacketCheck {
    private final Deque<Integer> samples = Lists.newLinkedList();
    private int movements = 0, streak = 0;

    private double lastKurtosis = 0.0d, lastSkewness = 0.0d, lastDeviation = 0.0d;

    public AutoClickerB() {
        super(Check.CheckType.AUTO_CLICKERB, "B", "AutoClicker", CheckVersion.RELEASE);
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

            // If the movements are lower than 4 and the player isn;t digging
            if (valid) samples.add(movements);

            if (samples.size() == 10) {
                // Get the standard deviation skewness and kurtosis from math utils
                double deviation = MathUtils.getStandardDeviation(samples);
                double skewness = MathUtils.getSkewness(samples);
                double kurtosis = MathUtils.getKurtosis(samples);

                // If the statistic values are the same for two sample rotations, flag
                if (deviation == lastDeviation && skewness == lastSkewness && kurtosis == lastKurtosis && playerData.getCps1()[0] > 9) {
                    if (++streak > 2) {
                        VL.getInstance().handleViolation(playerData, this, "Kurtosis", false);
                        //VL.getInstance().handleViolation(playerData, this, "D " + deviation + ", K" + kurtosis, false);
                    }
                    VL.getInstance().handleDebug(playerData, this, "D " + deviation + ", K" + kurtosis);
                } else {
                    streak = 0;
                }

                // Parse values to the last values and clear the list
                lastDeviation = deviation;
                lastKurtosis = kurtosis;
                lastSkewness = skewness;
                samples.clear();
            }

            // Reset the movements
            movements = 0;
        } else if (packet instanceof PacketPlayInFlying) {
            ++movements;
        }
    }
}

