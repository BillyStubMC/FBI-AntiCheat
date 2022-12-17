package fun.billy.fbi.util;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;

import java.util.Set;

public class CustomList {
    public static Set<Object> INVALID_SHAPE = ImmutableSet.of(Material.ACACIA_STAIRS, Material.BIRCH_WOOD_STAIRS, Material.BRICK_STAIRS, Material.COBBLESTONE_STAIRS, Material.DARK_OAK_STAIRS, Material.JUNGLE_WOOD_STAIRS, new Material[]{Material.NETHER_BRICK_STAIRS, Material.QUARTZ_STAIRS, Material.SANDSTONE_STAIRS, Material.RED_SANDSTONE_STAIRS, Material.SMOOTH_STAIRS, Material.SPRUCE_WOOD_STAIRS, Material.WOOD_STAIRS, Material.SNOW, Material.STONE_SLAB2, Material.STEP, Material.WOOD_STEP, Material.CARPET, Material.CHEST, Material.ENDER_CHEST, Material.TRAPPED_CHEST, Material.ENDER_PORTAL_FRAME, Material.TRAP_DOOR, Material.SLIME_BLOCK, Material.WATER_LILY, Material.REDSTONE_COMPARATOR, Material.TRAP_DOOR, Material.CAULDRON, Material.STATIONARY_WATER, Material.FENCE, Material.HOPPER, Material.REDSTONE_COMPARATOR});
    public static Set<Object> BAD_VELOCITY = ImmutableSet.of(Material.WATER, Material.STATIONARY_WATER, Material.LAVA, Material.STATIONARY_LAVA, Material.WEB, Material.SLIME_BLOCK, new Material[]{Material.LADDER, Material.VINE, Material.PISTON_EXTENSION, Material.PISTON_MOVING_PIECE, Material.SNOW, Material.FENCE, Material.STONE_SLAB2, Material.SOUL_SAND, Material.CHEST});
    public static Set<Object> ICE = ImmutableSet.of(Material.PACKED_ICE, Material.ICE);
    public static Set<Object> SLABS = ImmutableSet.of(Material.STONE_SLAB2, Material.STEP, Material.WOOD_STEP);
    public static Set<Object> STAIRS = ImmutableSet.of(Material.ACACIA_STAIRS, Material.BIRCH_WOOD_STAIRS, Material.BRICK_STAIRS, Material.COBBLESTONE_STAIRS, Material.DARK_OAK_STAIRS, Material.JUNGLE_WOOD_STAIRS, Material.NETHER_BRICK_STAIRS, Material.QUARTZ_STAIRS, Material.SANDSTONE_STAIRS, Material.RED_SANDSTONE_STAIRS, Material.SMOOTH_STAIRS, Material.SPRUCE_WOOD_STAIRS, Material.WOOD_STAIRS, Material.SMOOTH_STAIRS);

}

