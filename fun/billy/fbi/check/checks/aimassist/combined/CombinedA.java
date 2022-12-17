package fun.billy.fbi.check.checks.aimassist.combined;

import fun.billy.fbi.check.type.AimCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.PlayerLocation;
import org.bukkit.util.Vector;

public class CombinedA
        extends AimCheck {
    double multiplier = Math.pow(2.0, 24.0);
    long[] gcdLog = new long[10];
    float lastPitch = -1, deltaYaw, deltaPitch, lastDeltaYaw, lastDeltaPitch, pitchDifference, yawDifference;
    private int current = 0;

    public CombinedA() {
        super(CheckType.AIM_COMBINEDA, "O", "AimAssist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
        if (playerData.getLastAttackTicks() <= 50) {
            float yawdiff = this.getDistanceBetweenAngles(to.getYaw(), from.getYaw());
            float pitchdiff = this.getDistanceBetweenAngles(to.getPitch(), from.getPitch());
            yawDifference = Math.abs(yawdiff - deltaYaw);
            pitchDifference = Math.abs(pitchdiff - deltaPitch);
            lastDeltaYaw = deltaYaw;
            lastDeltaPitch = deltaPitch;
            deltaYaw = yawdiff;
            deltaPitch = pitchdiff;

            Vector first = new Vector(deltaYaw, 0, deltaPitch);
            Vector second = new Vector(lastDeltaYaw, 0, lastDeltaPitch);

            double angle = Math.pow(first.angle(second) * 180, 2);
            long deviation = getDeviation(deltaPitch);

            gcdLog[current % gcdLog.length] = deviation;
            current++;

            if (to.getPitch() > -20 && to.getPitch() < 20
                    && deltaPitch > 0
                    && deltaYaw > 1
                    && deltaYaw < 10
                    && lastDeltaYaw <= deltaYaw
                    && yawDifference != 0
                    && yawDifference < 1
                    && angle > 2500) {

                if (current > gcdLog.length) {
                    long maxDeviation = 0;
                    for (long l : gcdLog)
                        if (deviation != 0 && l != 0)
                            maxDeviation = Math.max(Math.max(l, deviation) % Math.min(l, deviation), maxDeviation);
                }

                if (deviation > 0) {
                    VL.getInstance().handleViolation(playerData, this, "KillAura", false);
                    //VL.getInstance().handleViolation(playerData, this, "deltaY:" + deltaYaw + ",diffY:" + yawDifference + ",angle:" + angle, false);
                    reset();
                }

            }
        }
    }

    private float getDistanceBetweenAngles(float angle1, float angle2) {
        float distance = Math.abs(angle1 - angle2) % 360.0f;
        if (distance > 180.0f) {
            distance = 360.0f - distance;
        }
        return distance;
    }

    public long getDeviation(float pitchChange) {
        if (lastPitch != -1) {
            try {
                long current = (long) (pitchChange * multiplier);
                long last = (long) (lastPitch * multiplier);
                long value = convert(current, last);

                if (value < 0x20000) {
                    return value;
                }
            } catch (Exception ignored) {
            }
        }
        lastPitch = pitchChange;
        return -1;
    }

    public void reset() {
        lastPitch = -1;
    }

    private long convert(long current, long last) {
        if (last <= 16384) return current;
        return convert(last, current % last);
    }
}

