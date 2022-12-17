package fun.billy.fbi.data.gui.impl.category;

import fun.billy.fbi.data.gui.ClickData;
import fun.billy.fbi.data.manager.Gui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Other
        extends fun.billy.fbi.data.gui.Gui {

    public Other() {
        super(ChatColor.RED + "Other", 18);
        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta checkMeta1 = back.getItemMeta();
        checkMeta1.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Back");
        checkMeta1.setLore(Arrays.asList("", ChatColor.GRAY + "Click to go back."));
        back.setItemMeta(checkMeta1);
        this.inventory.setItem(17, back);
        ItemStack NoSlow = new ItemStack(Material.BOOK);
        ItemMeta NoSlowD = NoSlow.getItemMeta();
        NoSlowD.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Timer");
        NoSlowD.setLore(Arrays.asList("", ChatColor.GRAY + "Click to configure."));
        NoSlow.setItemMeta(NoSlowD);
        this.inventory.setItem(0, NoSlow);
        ItemStack Inventory = new ItemStack(Material.BOOK);
        ItemMeta InventoryD = Inventory.getItemMeta();
        InventoryD.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "BadPackets");
        InventoryD.setLore(Arrays.asList("", ChatColor.GRAY + "Click to configure."));
        Inventory.setItemMeta(InventoryD);
        this.inventory.setItem(1, Inventory);
        ItemStack AutoSneak = new ItemStack(Material.BOOK);
        ItemMeta AutoSneakD = AutoSneak.getItemMeta();
        AutoSneakD.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "PingSpoof");
        AutoSneakD.setLore(Arrays.asList("", ChatColor.GRAY + "Click to configure."));
        AutoSneak.setItemMeta(AutoSneakD);
        this.inventory.setItem(2, AutoSneak);
    }

    @Override
    public void onClick(ClickData clickData) {
        Integer slot = clickData.getSlot();
        if (slot <= this.getInventory().getSize() && slot >= 0) {
            ItemStack checkStack = clickData.getItemStack();
            if (checkStack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Back")) {
                clickData.getPlayer().closeInventory();
                Gui.getInstance().getMainGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 0) {
                Gui.getInstance().getPacketsGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 1) {
                Gui.getInstance().getBadPacketsGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 2) {
                Gui.getInstance().getPingSpoofGui().openGui(clickData.getPlayer());
            }
        }
    }
}

