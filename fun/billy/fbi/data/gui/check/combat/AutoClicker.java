package fun.billy.fbi.data.gui.check.combat;

import com.google.common.collect.Maps;
import fun.billy.fbi.data.gui.ClickData;
import fun.billy.fbi.data.gui.impl.VlSettings;
import fun.billy.fbi.data.manager.Checks;
import fun.billy.fbi.data.manager.Gui;
import fun.billy.fbi.data.manager.violations.SettingsVL;
import fun.billy.fbi.check.type.Check;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Map;

public class AutoClicker
        extends fun.billy.fbi.data.gui.Gui {
    Map<Integer, Check.CheckType> checksById = Maps.newConcurrentMap();

    public AutoClicker() {
        super(ChatColor.RED + "AutoClicker", 27);
        int total = 0;
        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta checkMeta1 = back.getItemMeta();
        checkMeta1.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Back");
        checkMeta1.setLore(Arrays.asList("", ChatColor.GRAY + "Click to go back."));
        back.setItemMeta(checkMeta1);
        this.inventory.setItem(17, back);
        for (Check.CheckType type : Check.CheckType.values()) {
            if (type.getName().contains("AutoClicker")) {
                ItemStack checkStack = new ItemStack(Checks.getInstance().enabled(type) ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
                ItemMeta checkMeta = checkStack.getItemMeta();
                checkMeta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD.toString() + type.getName());
                checkMeta.setLore(Arrays.asList(
                        ChatColor.GRAY + "\u27a5 " + type.getDescription(),
                        "",
                        ChatColor.DARK_RED + "Enabled" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + Checks.getInstance().enabled(type),
                        ChatColor.DARK_RED + "Max-VL" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + SettingsVL.getInstance().vl_to_ban(type),
                        ""));
                checkStack.setItemMeta(checkMeta);
                this.inventory.setItem(total, checkStack);
                this.checksById.put(total, type);
                ++total;
            }
        }
    }

    @Override
    public void onClick(ClickData clickData) {
        Integer slot = clickData.getSlot();
        if (slot <= this.getInventory().getSize() && slot >= 0) {
            ItemStack checkStack = clickData.getItemStack();
            ItemMeta checkMeta = checkStack.getItemMeta();
            Check.CheckType type = this.checksById.get(slot);
            if (checkStack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Back")) {
                clickData.getPlayer().closeInventory();
                Gui.getInstance().getCheckGui().openGui(clickData.getPlayer());
            } else if (clickData.getClickType().isLeftClick() && checkStack.getItemMeta().getDisplayName().equalsIgnoreCase("§c§l" + type.getName())) {
                boolean enabled = Checks.getInstance().enabled(type);
                checkStack.setType(enabled ? Material.REDSTONE_BLOCK : Material.EMERALD_BLOCK);
                if (enabled) {
                    Checks.getInstance().disableType(type, clickData.getPlayer());
                } else {
                    Checks.getInstance().enableType(type, clickData.getPlayer());
                }
                checkMeta.setLore(Arrays.asList(
                        ChatColor.GRAY + "\u27a5 " + type.getDescription(),
                        "",
                        ChatColor.DARK_RED + "Enabled" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + Checks.getInstance().enabled(type),
                        ChatColor.DARK_RED + "Max-VL" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + SettingsVL.getInstance().vl_to_ban(type),
                        ""));
                checkStack.setItemMeta(checkMeta);
                this.inventory.setItem(slot, checkStack);
            } else if (clickData.getClickType().isRightClick() && checkStack.getItemMeta().getDisplayName().equalsIgnoreCase("§c§l" + type.getName())) {
                clickData.getPlayer().closeInventory();
                VlSettings.vlGui(clickData.getPlayer(), type, this, slot, checkStack);
            }
        }
    }
}

