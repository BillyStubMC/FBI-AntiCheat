package fun.billy.fbi.data.gui.impl;

import fun.billy.fbi.AntiCheatPlugin;
import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.data.gui.ClickData;
import fun.billy.fbi.data.manager.Gui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainGui
        extends fun.billy.fbi.data.gui.Gui {

    public MainGui() {
        super(ChatColor.LIGHT_PURPLE + "FBI Anticheat", 27);
        ArrayList<Object> all = new ArrayList<>(Arrays.asList(Check.CheckType.values()));
        ItemStack check = new ItemStack(Material.BOOK);
        ItemMeta checkMeta = check.getItemMeta();
        checkMeta.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Checks");
        checkMeta.setLore(Arrays.asList(ChatColor.GRAY + "\u27a5 Click to manage the checks.", "", ChatColor.WHITE + "Total checks " + ChatColor.GRAY + "\u279f" + ChatColor.WHITE + " " + all.size(), "", ChatColor.GRAY + "Version " + AntiCheatPlugin.getPlugin().Version + "."));
        check.setItemMeta(checkMeta);
        this.inventory.setItem(12, check);
        ItemStack settings = new ItemStack(Material.REDSTONE_COMPARATOR);
        ItemMeta settingsM = settings.getItemMeta();
        settingsM.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Settings");
        settingsM.setLore(Collections.singletonList(ChatColor.GRAY + "\u27a5 Click to configure FBI."));
        settings.setItemMeta(settingsM);
        this.inventory.setItem(14, settings);
        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta glassM = glass.getItemMeta();
        glassM.setDisplayName(" ");
        glass.setItemMeta(glassM);
        List<Integer> slotsGlass = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26);
        for (Integer slot : slotsGlass) {
            this.inventory.setItem(slot, glass);
        }
    }

    @Override
    public void onClick(ClickData clickData) {
        if (clickData.getSlot() == 12) {
            clickData.getPlayer().closeInventory();
            Gui.getInstance().getMainGui().openGui(clickData.getPlayer());
        }
        if (clickData.getSlot() == 14) {
            clickData.getPlayer().closeInventory();
            Gui.getInstance().getSettingsGui().openGui(clickData.getPlayer());
        }
    }
}

