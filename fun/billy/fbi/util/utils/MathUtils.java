package fun.billy.fbi.util.utils;

import com.google.common.collect.Lists;
import fun.billy.fbi.util.Pair;
import fun.billy.fbi.util.PlayerLocation;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.*;

@UtilityClass
public class MathUtils {
    public float field_180189_a;
    public float[] SIN_TABLE_FAST;
    public boolean fastMath;
    public float[] SIN_TABLE;

    public float sqrt_float(float p_76129_0_) {
        return (float) Math.sqrt(p_76129_0_);
    }

    public float sqrt_double(double p_76133_0_) {
        return (float) Math.sqrt(p_76133_0_);
    }

    public float abs(float p_76135_0_) {
        return p_76135_0_ >= 0.0f ? p_76135_0_ : -p_76135_0_;
    }

    static {
        int i;
        field_180189_a = MathUtils.sqrt_float(2.0f);
        SIN_TABLE_FAST = new float[4096];
        fastMath = false;
        SIN_TABLE = new float[65536];
        for (i = 0; i < 65536; ++i) {
            MathUtils.SIN_TABLE[i] = (float) Math.sin((double) i * 3.141592653589793 * 2.0 / 65536.0);
        }
        for (i = 0; i < 4096; ++i) {
            MathUtils.SIN_TABLE_FAST[i] = (float) Math.sin(((float) i + 0.5f) / 4096.0f * 6.2831855f);
        }
        for (i = 0; i < 360; i += 90) {
            MathUtils.SIN_TABLE_FAST[(int) ((float) i * 11.377778f) & 4095] = (float) Math.sin((float) i * 0.017453292f);
        }
    }

    public boolean isScientificNotation(double d) {
        return Double.toString(d).contains("E");
    }

    public int floor(double var0) {
        int var2 = (int) var0;
        return var0 < var2 ? var2 - 1 : var2;
    }

    public <T> T firstNonNull(@Nullable final T t, @Nullable final T t2) {
        return (t != null) ? t : t2;
    }

    public double gcd(double limit, double a, double b) {
        return b <= limit ? a : MathUtils.gcd(limit, b, a % b);
    }

    public double getStandardDeviation(final Collection<? extends Number> data) {
        final double variance = getVariance(data);

        // The standard deviation is the square root of variance. (sqrt(s^2))
        return Math.sqrt(variance);
    }

    public double getVariance(final Collection<? extends Number> data) {
        int count = 0;

        double sum = 0.0;
        double variance = 0.0;

        double average;

        // Increase the sum and the count to find the average and the standard deviation
        for (final Number number : data) {
            sum += number.doubleValue();
            ++count;
        }

        average = sum / count;

        // Run the standard deviation formula
        for (final Number number : data) {
            variance += Math.pow(number.doubleValue() - average, 2.0);
        }

        return variance;
    }

    public double getCps(final Collection<? extends Number> data) {
        final double average = data.stream().mapToDouble(Number::doubleValue).average().orElse(0.0);

        return 20 / average;
    }


    public Pair<List<Double>, List<Double>> getOutliers(final Collection<? extends Number> collection) {
        final List<Double> values = new ArrayList<>();

        for (final Number number : collection) {
            values.add(number.doubleValue());
        }

        final double q1 = getMedian(values.subList(0, values.size() / 2));
        final double q3 = getMedian(values.subList(values.size() / 2, values.size()));

        final double iqr = Math.abs(q1 - q3);
        final double lowThreshold = q1 - 1.5 * iqr, highThreshold = q3 + 1.5 * iqr;

        final Pair<List<Double>, List<Double>> tuple = new Pair<>(new ArrayList<>(), new ArrayList<>());

        for (final Double value : values) {
            if (value < lowThreshold) {
                tuple.getX().add(value);
            } else if (value > highThreshold) {
                tuple.getY().add(value);
            }
        }

        return tuple;
    }

    public double getKurtosis(final Collection<? extends Number> data) {
        double sum = 0.0;
        int count = 0;

        for (Number number : data) {
            sum += number.doubleValue();
            ++count;
        }

        if (count < 3.0) {
            return 0.0;
        }

        final double efficiencyFirst = count * (count + 1.0) / ((count - 1.0) * (count - 2.0) * (count - 3.0));
        final double efficiencySecond = 3.0 * Math.pow(count - 1.0, 2.0) / ((count - 2.0) * (count - 3.0));
        final double average = sum / count;

        double variance = 0.0;
        double varianceSquared = 0.0;

        for (final Number number : data) {
            variance += Math.pow(average - number.doubleValue(), 2.0);
            varianceSquared += Math.pow(average - number.doubleValue(), 4.0);
        }

        return efficiencyFirst * (varianceSquared / Math.pow(variance / sum, 2.0)) - efficiencySecond;
    }

    public double getSkewness(final Collection<? extends Number> data) {
        double sum = 0;
        int count = 0;

        final List<Double> numbers = Lists.newArrayList();

        // Get the sum of all the data and the amount via looping
        for (final Number number : data) {
            sum += number.doubleValue();
            ++count;

            numbers.add(number.doubleValue());
        }

        // Sort the numbers to run the calculations in the next part
        Collections.sort(numbers);

        // Run the formula to get skewness
        final double mean = sum / count;
        final double median = (count % 2 != 0) ? numbers.get(count / 2) : (numbers.get((count - 1) / 2) + numbers.get(count / 2)) / 2;
        final double variance = getVariance(data);

        return 3 * (mean - median) / variance;
    }

    private double getMedian(final List<Double> data) {
        if (data.size() % 2 == 0) {
            return (data.get(data.size() / 2) + data.get(data.size() / 2 - 1)) / 2;
        } else {
            return data.get(data.size() / 2);
        }
    }

    public double highest(final Iterable<? extends Number> iterable) {
        Double value = null;
        for (final Number n : iterable) {
            if (value != null && n.doubleValue() <= value) {
                continue;
            }
            value = n.doubleValue();
        }
        return firstNonNull(value, 0.0);
    }

    public double total(Iterable<? extends Number> numbers) {
        double total = 0.0;
        for (Number number : numbers) {
            total += number.doubleValue();
        }
        return total;
    }

    public double average(Iterable<? extends Number> numbers) {
        double total = 0.0;
        int i = 0;
        for (Number number : numbers) {
            total += number.doubleValue();
            ++i;
        }
        return total / (double) i;
    }

    public double deviationSquared(Iterable<? extends Number> numbers) {
        double total = 0.0;
        int i = 0;
        for (Number number : numbers) {
            total += number.doubleValue();
            ++i;
        }
        double average = total / (double) i;
        double deviation = 0.0;
        for (Number number : numbers) {
            deviation += Math.pow(number.doubleValue() - average, 2.0);
        }
        return deviation / (double) (i - 1);
    }

    public double deviation(Iterable<? extends Number> numbers) {
        return Math.sqrt(MathUtils.deviationSquared(numbers));
    }

    public double varianceSquared(Number value, Iterable<? extends Number> numbers) {
        double variance = 0.0;
        int i = 0;
        for (Number number : numbers) {
            variance += Math.pow(number.doubleValue() - value.doubleValue(), 2.0);
            ++i;
        }
        return variance / (double) (i - 1);
    }

    public double variance(Number value, Iterable<? extends Number> numbers) {
        return Math.sqrt(MathUtils.varianceSquared(value, numbers));
    }

    public /* varargs */ double hypot(double... values) {
        return Math.sqrt(MathUtils.hypotSquared(values));
    }

    public /* varargs */ double hypotSquared(double... values) {
        double total = 0.0;
        for (double value : values) {
            total += Math.pow(value, 2.0);
        }
        return total;
    }

    public /* varargs */ Double getMinimumAngle(PlayerLocation playerLocation, PlayerLocation... targetLocations) {
        Double angle = null;
        for (PlayerLocation targetLocation : targetLocations) {
            double xDiff = targetLocation.getX() - playerLocation.getX();
            double zDiff = targetLocation.getZ() - playerLocation.getZ();
            float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
            double yawDiff = MathUtils.getDistanceBetweenAngles360(playerLocation.getYaw(), yaw);
            if (angle != null && angle <= yawDiff) continue;
            angle = yawDiff;
        }
        return angle;
    }

    public float[] getRotationFromPosition(PlayerLocation playerLocation, PlayerLocation targetLocation) {
        double xDiff = targetLocation.getX() - playerLocation.getX();
        double zDiff = targetLocation.getZ() - playerLocation.getZ();
        double yDiff = targetLocation.getY() + 0.81 - playerLocation.getY() - 1.2;
        float dist = (float) Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float) (-Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793);
        return new float[]{yaw, pitch};
    }

    public double getDistanceBetweenAngles(float angle1, float angle2) {
        float distance = Math.abs(angle1 - angle2) % 360.0f;
        if (distance > 180.0f) {
            distance = 360.0f - distance;
        }
        return distance;
    }

    public double getDistanceBetweenAngles360(double angle1, double angle2) {
        double distance = Math.abs(angle1 % 360.0 - angle2 % 360.0);
        distance = Math.min(360.0 - distance, distance);
        return Math.abs(distance);
    }

    public double getMoveAngle(PlayerLocation from, PlayerLocation to) {
        double dx = to.getX() - from.getX();
        double dz = to.getZ() - from.getZ();

        double moveAngle = Math.toDegrees(Math.atan2(dz, dx)) - 90D; // have to subtract by 90 because minecraft does it

        return Math.abs(wrapAngleTo180_double(moveAngle - to.getYaw()));
    }

    public double wrapAngleTo180_double(double value) {
        value %= 360D;

        if (value >= 180D)
            value -= 360D;

        if (value < -180D)
            value += 360D;

        return value;
    }

    public boolean onGround(double y) {
        return y % 0.015625 == 0.0;
    }
}

