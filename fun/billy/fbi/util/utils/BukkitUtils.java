package fun.billy.fbi.util.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Iterator;

@UtilityClass
public class BukkitUtils {

    static int[] MATERIAL_FLAGS;

    public boolean checkFlag(Material material, int flag) {
        return (BukkitUtils.MATERIAL_FLAGS[material.getId()] & flag) == flag;
    }

    static {
        MATERIAL_FLAGS = new int[256];
        for (int i = 0; i < BukkitUtils.MATERIAL_FLAGS.length; ++i) {
            Material material = Material.values()[i];
            if (material.isSolid()) {
                BukkitUtils.MATERIAL_FLAGS[i] |= 0x1;
            }
            if (material.name().endsWith("_STAIRS")) {
                BukkitUtils.MATERIAL_FLAGS[i] |= 0x10;
            }
        }
        BukkitUtils.MATERIAL_FLAGS[Material.SIGN_POST.getId()] = 0;
        BukkitUtils.MATERIAL_FLAGS[Material.WALL_SIGN.getId()] = 0;
        BukkitUtils.MATERIAL_FLAGS[Material.DIODE_BLOCK_OFF.getId()] = 1;
        BukkitUtils.MATERIAL_FLAGS[Material.DIODE_BLOCK_ON.getId()] = 1;
        BukkitUtils.MATERIAL_FLAGS[Material.CARPET.getId()] = 1;
        BukkitUtils.MATERIAL_FLAGS[Material.SNOW.getId()] = 1;
        BukkitUtils.MATERIAL_FLAGS[Material.ANVIL.getId()] = 1;
        int id = Material.WATER.getId();
        BukkitUtils.MATERIAL_FLAGS[id] |= 0x2;
        int id2 = Material.STATIONARY_WATER.getId();
        BukkitUtils.MATERIAL_FLAGS[id2] |= 0x2;
        int id3 = Material.LAVA.getId();
        BukkitUtils.MATERIAL_FLAGS[id3] |= 0x2;
        int id4 = Material.STATIONARY_LAVA.getId();
        BukkitUtils.MATERIAL_FLAGS[id4] |= 0x2;
        int id5 = Material.LADDER.getId();
        BukkitUtils.MATERIAL_FLAGS[id5] |= 0x5;
        int id6 = Material.VINE.getId();
        BukkitUtils.MATERIAL_FLAGS[id6] |= 0x5;
        int id7 = Material.FENCE.getId();
        BukkitUtils.MATERIAL_FLAGS[id7] |= 0x8;
        int id8 = Material.FENCE_GATE.getId();
        BukkitUtils.MATERIAL_FLAGS[id8] |= 0x8;
        int id9 = Material.COBBLE_WALL.getId();
        BukkitUtils.MATERIAL_FLAGS[id9] |= 0x8;
        int id10 = Material.NETHER_FENCE.getId();
        BukkitUtils.MATERIAL_FLAGS[id10] |= 0x8;
    }

    public boolean hasEnchantment(final Player player, final int n) {
        boolean has = false;
        ItemStack[] armorContents;
        for (int length = (armorContents = player.getInventory().getArmorContents()).length, i = 0; i < length; ++i) {
            final ItemStack itemStack = armorContents[i];
            if (itemStack != null) {
                if (!itemStack.getEnchantments().isEmpty()) {
                    has = true;
                    break;
                }
            }
        }
        return has;
    }

    @SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
    public int getPotionLevel(Player player, PotionEffectType type) {
        PotionEffect potionEffect;
        ArrayList potionEffectList = new ArrayList(player.getActivePotionEffects());
        Iterator var3 = potionEffectList.iterator();
        do {
            if (var3.hasNext()) continue;
            return 0;
        } while ((potionEffect = (PotionEffect) var3.next()).getType().getId() != type.getId());
        return potionEffect.getAmplifier() + 1;
    }

    public float getBaseSpeed(Player player) {
        return 0.35f + (BukkitUtils.getPotionLevel(player, PotionEffectType.SPEED) * 0.062f) + ((player.getWalkSpeed() - 0.2f) * 1.6f);
    }

    public float getBaseSpeed2(Player player) {
        return 0.35f + (BukkitUtils.getPotionLevel(player, PotionEffectType.SPEED) * 0.062f) + ((player.getWalkSpeed() - 0.2f) * 1.6f);
    }

    public float valuePatch1(Player player) {
        return 0.249999999999923f + (BukkitUtils.getPotionLevel(player, PotionEffectType.SPEED) * 0.062f) + ((player.getWalkSpeed() - 0.2f) * 1.6f);
    }

    public boolean hasEffect(Player player, int n) {
        return player.getActivePotionEffects().stream().map(PotionEffect::getType).map(PotionEffectType::getId).anyMatch(n2 -> n == n2);
    }
}

