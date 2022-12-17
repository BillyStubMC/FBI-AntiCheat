package fun.billy.fbi.data.gui.impl;

import fun.billy.fbi.AntiCheatPlugin;
import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.data.manager.Checks;
import fun.billy.fbi.data.manager.Gui;
import fun.billy.fbi.data.manager.violations.SettingsVL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class VlSettings implements Listener {
    static Map<Player, Check.CheckType> check = new HashMap<>();
    static Map<Player, fun.billy.fbi.data.gui.Gui> lastGui = new HashMap<>();
    static Map<Player, Integer> lastSlot = new HashMap<>();
    static Map<Player, ItemStack> lastItemStack = new HashMap<>();

    public static void vlGui(Player p, Check.CheckType type, fun.billy.fbi.data.gui.Gui gui, Integer slot, ItemStack stack) {
        Inventory inventory = Bukkit.createInventory(null, 18, String.valueOf(type.getName()));
        check.put(p, type);
        lastGui.put(p, gui);
        lastSlot.put(p, slot);
        lastItemStack.put(p, stack);
        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta checkMeta1 = back.getItemMeta();
        checkMeta1.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Back");
        checkMeta1.setLore(Arrays.asList("", ChatColor.GRAY + "Click to go back."));
        back.setItemMeta(checkMeta1);
        inventory.setItem(17, back);
        ItemStack Ka = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta KaD = Ka.getItemMeta();
        KaD.setDisplayName(ChatColor.RED + "- 1");
        KaD.setLore(Arrays.asList("", ChatColor.GRAY + "Click to remove 1 to Max-VL."));
        Ka.setItemMeta(KaD);
        inventory.setItem(2, Ka);
        ItemStack Interact = new ItemStack(Material.BOOK);
        ItemMeta InteractD = Interact.getItemMeta();
        InteractD.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + type.getName());
        InteractD.setLore(Arrays.asList("", ChatColor.LIGHT_PURPLE + "Max-VL", ChatColor.WHITE + String.valueOf(SettingsVL.getInstance().vl_to_ban(type))));
        Interact.setItemMeta(InteractD);
        inventory.setItem(4, Interact);
        ItemStack NoSlow = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
        ItemMeta NoSlowD = NoSlow.getItemMeta();
        NoSlowD.setDisplayName(ChatColor.LIGHT_PURPLE + "+ 1");
        NoSlowD.setLore(Arrays.asList("", ChatColor.GRAY + "Click to add 1 to Max-VL."));
        NoSlow.setItemMeta(NoSlowD);
        inventory.setItem(6, NoSlow);
        p.openInventory(inventory);
    }

    @EventHandler
    private void onCloseInventory(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (check.containsKey(p)) {
            Check.CheckType type = check.get(p);
            Checks.getInstance().reloadCheck(type);
            new BukkitRunnable() {
                public void run() {
                    Gui.getInstance().getGui(lastGui.get(p)).openGui(p);
                }
            }.runTaskLater(AntiCheatPlugin.getPlugin(), 1L);
            this.updateItem(p, lastItemStack.get(p), lastItemStack.get(p).getItemMeta(), lastSlot.get(p), type);
            this.clearMap(p);
        }
    }

    private void updateItem(Player p, ItemStack checkStack, ItemMeta checkMeta, Integer slot, Check.CheckType type) {
        new BukkitRunnable() {
            public void run() {
                checkMeta.setLore(Arrays.asList(
                        "",
                        ChatColor.LIGHT_PURPLE + "Enabled" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + Checks.getInstance().enabled(type),
                        ChatColor.LIGHT_PURPLE + "Max-VL" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + SettingsVL.getInstance().vl_to_ban(type),
                        "",
                        ChatColor.GRAY + "Left-click to enable / disable.",
                        ChatColor.GRAY + "Right-click to change Max-VL."));
                checkStack.setItemMeta(checkMeta);
                p.getOpenInventory().setItem(slot, checkStack);
            }
        }.runTaskLater(AntiCheatPlugin.getPlugin(), 2L);
    }

    private void clearMap(Player p) {
        new BukkitRunnable() {
            public void run() {
                check.remove(p);
                lastGui.remove(p);
                lastSlot.remove(p);
                lastItemStack.remove(p);
            }
        }.runTaskLaterAsynchronously(AntiCheatPlugin.getPlugin(), 3L);
    }

    @EventHandler
    private void onClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            if (check.containsKey(p)) {
                String name = check.get(p).getName();
                if (e.getInventory().getName().equalsIgnoreCase(name)) {
                    e.setCancelled(true);
                    if (e.getWhoClicked() instanceof Player) {
                        if (e.getCurrentItem() != null && e.getCurrentItem().getType() != null && e.getCurrentItem().getType() != Material.AIR && e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
                            Check.CheckType type = check.get(p);
                            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "- 1")) {
                                if (SettingsVL.getInstance().vl_to_ban(type) == 0) {
                                    p.sendMessage(ChatColor.LIGHT_PURPLE + "You can't set negative Max-VL.");
                                    return;
                                }
                                SettingsVL.getInstance().removeVl(type, p, SettingsVL.getInstance().vl_to_ban(type) - 1);
                                ItemStack Interact = new ItemStack(Material.BOOK);
                                ItemMeta InteractD = Interact.getItemMeta();
                                InteractD.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + type.getName());
                                InteractD.setLore(Arrays.asList("", ChatColor.LIGHT_PURPLE + "Max-Vl", ChatColor.WHITE + String.valueOf(SettingsVL.getInstance().vl_to_ban(type))));
                                Interact.setItemMeta(InteractD);
                                p.getOpenInventory().setItem(4, Interact);
                                p.sendMessage(ChatColor.LIGHT_PURPLE + "Removed 1 to " + type.getName() + " Max-VL.");
                            }
                            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "+ 1")) {
                                SettingsVL.getInstance().addVl(type, p, SettingsVL.getInstance().vl_to_ban(type) + 1);
                                ItemStack Interact = new ItemStack(Material.BOOK);
                                ItemMeta InteractD = Interact.getItemMeta();
                                InteractD.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + type.getName());
                                InteractD.setLore(Arrays.asList("", ChatColor.LIGHT_PURPLE + "Max-Vl", ChatColor.WHITE + String.valueOf(SettingsVL.getInstance().vl_to_ban(type))));
                                Interact.setItemMeta(InteractD);
                                p.getOpenInventory().setItem(4, Interact);
                                p.sendMessage(ChatColor.LIGHT_PURPLE + "Added 1 to " + type.getName() + " Max-VL.");
                            }
                            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + ChatColor.BOLD.toString() + "Back")) {
                                p.closeInventory();
                            }
                        }
                    }
                }
            }
        }
    }
}

