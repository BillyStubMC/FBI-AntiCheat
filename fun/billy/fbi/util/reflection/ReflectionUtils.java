package fun.billy.fbi.util.reflection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

//Credits to funkemunky.
public class ReflectionUtils {

    static String serverVersion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
    public static Class<?> EntityPlayer = getNMSClass("EntityPlayer");
    static Class<?> Entity = getNMSClass("Entity");
    static Class<?> CraftPlayer = getCBClass("entity.CraftPlayer");
    static Class<?> CraftWorld = getCBClass("CraftWorld");
    static Class<?> World = getNMSClass("World");
    static Method getBlocks = getMethod(World, "a", getNMSClass("AxisAlignedBB"));
    static Method getBlocks1_12 = getMethod(World, "getCubes", getNMSClass("Entity"), getNMSClass("AxisAlignedBB"));

    public static Method getMethod(Class<?> object, String method, Class<?>... args) {
        try {
            Method methodObject = object.getMethod(method, args);

            methodObject.setAccessible(true);

            return methodObject;

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getInvokedMethod(Method method, Object object, Object... args) {
        try {
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Field getField(Class<?> object, String field) {
        try {
            Field fieldObject = object.getField(field);
            fieldObject.setAccessible(true);
            return fieldObject;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getInvokedField(Field field, Object object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> getNMSClass(String string) {
        return getClass("net.minecraft.server." + serverVersion + "." + string);
    }

    public static boolean isBukkitVerison(String version) {
        return serverVersion.contains(version);
    }

    public static boolean isNewVersion() {
        return isBukkitVerison("1_9") || isBukkitVerison("1_1");
    }

    public static Class<?> getCBClass(String string) {
        return getClass("org.bukkit.craftbukkit." + serverVersion + "." + string);
    }

    public static Class<?> getClass(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Collection<?> getCollidingBlocks(Player player, Object axisAlignedBB) {
        Object world = getInvokedMethod(getMethod(CraftWorld, "getHandle"), player.getWorld());
        return (Collection<?>) (isNewVersion()
                ? getInvokedMethod(getBlocks1_12, world, null, axisAlignedBB)
                : getInvokedMethod(getBlocks, world, axisAlignedBB));
    }

    public static Object getBoundingBox(Player player) {
        return isBukkitVerison("1_7") ? getInvokedField(getField(Entity, "boundingBox"), getEntityPlayer(player)) : getInvokedMethod(getMethod(EntityPlayer, "getBoundingBox"), getEntityPlayer(player));
    }

    public static Object modifyBoundingBox(Object box, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        double newminX = (double) getInvokedField(getField(box.getClass(), "a"), box) + minX;
        double newminY = (double) getInvokedField(getField(box.getClass(), "b"), box) + minY;
        double newminZ = (double) getInvokedField(getField(box.getClass(), "c"), box) + minZ;
        double newmaxX = (double) getInvokedField(getField(box.getClass(), "d"), box) + maxX;
        double newmaxY = (double) getInvokedField(getField(box.getClass(), "e"), box) + maxY;
        double newmaxZ = (double) getInvokedField(getField(box.getClass(), "f"), box) + maxZ;

        try {
            return getNMSClass("AxisAlignedBB").getConstructor(double.class, double.class, double.class, double.class, double.class, double.class).newInstance(newminX, newminY, newminZ, newmaxX, newmaxY, newmaxZ);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getEntityPlayer(Player player) {
        return getInvokedMethod(getMethod(CraftPlayer, "getHandle"), player);
    }

}