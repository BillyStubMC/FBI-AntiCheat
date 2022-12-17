package fun.billy.fbi.util.reflection;

import com.google.common.base.Joiner;
import fun.billy.fbi.data.VelocityData;
import fun.billy.fbi.util.PlayerLocation;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.util.Vector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class SafeReflection {
    static Field PacketPlayOutPosition_a = SafeReflection.access(PacketPlayOutPosition.class, "a", "x");
    static Field PacketPlayOutPosition_b = SafeReflection.access(PacketPlayOutPosition.class, "b", "y");
    static Field PacketPlayOutPosition_c = SafeReflection.access(PacketPlayOutPosition.class, "c", "z");
    static Field PacketPlayOutPosition_d = SafeReflection.access(PacketPlayOutPosition.class, "d", "yaw");
    static Field PacketPlayOutPosition_e = SafeReflection.access(PacketPlayOutPosition.class, "e", "pitch");
    static Field PacketPlayOutKeepAlive_a = SafeReflection.access(PacketPlayOutKeepAlive.class, "a");
    static Field PacketPlayOutTransaction_b = SafeReflection.access(PacketPlayOutTransaction.class, "b");
    static Field PacketPlayInUseEntity_a = SafeReflection.access(PacketPlayInUseEntity.class, "a");
    static Field PlayerConnection_e = SafeReflection.access(PlayerConnection.class, "e");
    static Field PacketPlayOutEntityVelocity_a = SafeReflection.access(PacketPlayOutEntityVelocity.class, "a", "id");
    static Field PacketPlayOutEntityVelocity_b = SafeReflection.access(PacketPlayOutEntityVelocity.class, "b", "x");
    static Field PacketPlayOutEntityVelocity_c = SafeReflection.access(PacketPlayOutEntityVelocity.class, "c", "y");
    static Field PacketPlayOutEntityVelocity_d = SafeReflection.access(PacketPlayOutEntityVelocity.class, "d", "z");
    static Field PacketPlayOutEntity_a = SafeReflection.access(PacketPlayOutEntity.class, "a");
    static Field PacketPlayOutEntity_b = SafeReflection.access(PacketPlayOutEntity.class, "b");
    static Field PacketPlayOutEntity_c = SafeReflection.access(PacketPlayOutEntity.class, "c");
    static Field PacketPlayOutEntity_d = SafeReflection.access(PacketPlayOutEntity.class, "d");
    static Field PacketPlayOutEntity_g = SafeReflection.access(PacketPlayOutEntity.class, "g");
    static Field PacketPlayOutEntityTeleport_a = SafeReflection.access(PacketPlayOutEntityTeleport.class, "a");
    static Field PacketPlayOutEntityTeleport_b = SafeReflection.access(PacketPlayOutEntityTeleport.class, "b");
    static Field PacketPlayOutEntityTeleport_c = SafeReflection.access(PacketPlayOutEntityTeleport.class, "c");
    static Field PacketPlayOutEntityTeleport_d = SafeReflection.access(PacketPlayOutEntityTeleport.class, "d");
    static Field PacketPlayOutEntityTeleport_e = SafeReflection.access(PacketPlayOutEntityTeleport.class, "e");
    static Field PacketPlayOutEntityTeleport_f = SafeReflection.access(PacketPlayOutEntityTeleport.class, "f");
    static Field PacketPlayOutEntityTeleport_g = SafeReflection.access(PacketPlayOutEntityTeleport.class, "g");
    static Field PacketPlayOutEntityDestroy_a = SafeReflection.access(PacketPlayOutEntityDestroy.class, "a");
    static Field PacketPlayInWindowClick_slot = SafeReflection.access(PacketPlayInWindowClick.class, "slot");

    public static PlayerLocation getLocation(long timestamp, int totalTicks, PacketPlayOutPosition packet) {
        return new PlayerLocation(timestamp, totalTicks, SafeReflection.fetch(PacketPlayOutPosition_a, packet), SafeReflection.fetch(PacketPlayOutPosition_b, packet), SafeReflection.fetch(PacketPlayOutPosition_c, packet), SafeReflection.fetch(PacketPlayOutPosition_d, packet), SafeReflection.fetch(PacketPlayOutPosition_e, packet), null);
    }

    public static int getKeepAliveId(PacketPlayOutKeepAlive packet) {
        return (Integer) SafeReflection.fetch(PacketPlayOutKeepAlive_a, packet);
    }

    public static short getTransactionId(PacketPlayOutTransaction packet) {
        return (Short) SafeReflection.fetch(PacketPlayOutTransaction_b, packet);
    }

    public static int getAttackedEntity(PacketPlayInUseEntity packet) {
        return (Integer) SafeReflection.fetch(PacketPlayInUseEntity_a, packet);
    }

    public static int getNextKeepAliveTime(PlayerConnection playerConnection) {
        return (Integer) SafeReflection.fetch(PlayerConnection_e, playerConnection);
    }

    public static void setNextKeepAliveTime(PlayerConnection playerConnection, int e) {
        SafeReflection.set(PlayerConnection_e, playerConnection, e);
    }

    public static VelocityData getVelocity(PacketPlayOutEntityVelocity packet) {
        int a = SafeReflection.fetch(PacketPlayOutEntityVelocity_a, packet);
        int b = SafeReflection.fetch(PacketPlayOutEntityVelocity_b, packet);
        int c = SafeReflection.fetch(PacketPlayOutEntityVelocity_c, packet);
        int d = SafeReflection.fetch(PacketPlayOutEntityVelocity_d, packet);
        return new VelocityData(a, b, c, d);
    }

    public static void setOnGround(PacketPlayOutEntity packet, boolean onGround) {
        SafeReflection.set(PacketPlayOutEntity_g, packet, onGround);
    }

    public static int getEntityId(PacketPlayOutEntity packet) {
        return (Integer) SafeReflection.fetch(PacketPlayOutEntity_a, packet);
    }

    public static Vector getMovement(PacketPlayOutEntity packet) {
        return new Vector((double) (Byte) SafeReflection.fetch(PacketPlayOutEntity_b, packet) / 32.0, (double) (Byte) SafeReflection.fetch(PacketPlayOutEntity_c, packet) / 32.0, (double) (Byte) SafeReflection.fetch(PacketPlayOutEntity_d, packet) / 32.0);
    }

    public static void setOnGround(PacketPlayOutEntityTeleport packet, boolean onGround) {
        SafeReflection.set(PacketPlayOutEntityTeleport_g, packet, onGround);
    }

    public static int getEntityId(PacketPlayOutEntityTeleport packet) {
        return (Integer) SafeReflection.fetch(PacketPlayOutEntityTeleport_a, packet);
    }

    public static PlayerLocation getLocation(long timestamp, int tickTime, PacketPlayOutEntityTeleport packet) {
        return new PlayerLocation(timestamp, tickTime, (double) (Integer) SafeReflection.fetch(PacketPlayOutEntityTeleport_b, packet) / 32.0, (double) (Integer) SafeReflection.fetch(PacketPlayOutEntityTeleport_c, packet) / 32.0, (double) (Integer) SafeReflection.fetch(PacketPlayOutEntityTeleport_d, packet) / 32.0, (float) (Byte) SafeReflection.fetch(PacketPlayOutEntityTeleport_e, packet) * 360.0f / 256.0f, (float) (Byte) SafeReflection.fetch(PacketPlayOutEntityTeleport_f, packet) * 360.0f / 256.0f, SafeReflection.fetch(PacketPlayOutEntityTeleport_g, packet));
    }

    public static int[] getEntities(PacketPlayOutEntityDestroy packet) {
        return (int[]) SafeReflection.fetch(PacketPlayOutEntityDestroy_a, packet);
    }

    public static int getSlot(PacketPlayInWindowClick packet) {
        return (Integer) SafeReflection.fetch(PacketPlayInWindowClick_slot, packet);
    }

    @SuppressWarnings("rawtypes")
    static Field access(Class clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException var3) {
            throw new IllegalArgumentException(clazz.getSimpleName() + ":" + fieldName, var3);
        }
    }

    @SuppressWarnings("rawtypes")
    static /* varargs */ Field access(Class clazz, String... fieldNames) {
        for (String fieldName : fieldNames) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException ignored) {
            }
        }
        throw new IllegalArgumentException(clazz.getSimpleName() + ":" + Joiner.on(",").join(fieldNames));
    }

    @SuppressWarnings({"unused", "rawtypes"})
    static /* varargs */ <T> Constructor<T> constructor(Class<T> clazz, Class... o) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(o);
            constructor.setAccessible(true);
            return constructor;
        } catch (NoSuchMethodException var3) {
            throw new IllegalArgumentException(var3);
        }
    }

    @SuppressWarnings("unused")
    static /* varargs */ <T> T fetchConstructor(Constructor<T> constructor, Object... o) {
        try {
            return constructor.newInstance(o);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException var3) {
            throw new IllegalArgumentException(var3);
        }
    }

    @SuppressWarnings("unchecked")
    static <T> T fetch(Field field, Object object) {
        try {
            return (T) field.get(object);
        } catch (IllegalAccessException var3) {
            throw new IllegalArgumentException(var3);
        }
    }

    static <T> void set(Field field, Object object, T value) {
        try {
            field.set(object, value);
        } catch (IllegalAccessException var4) {
            throw new IllegalArgumentException(var4);
        }
    }
}

