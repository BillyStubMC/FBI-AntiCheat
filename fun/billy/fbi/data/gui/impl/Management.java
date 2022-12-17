package fun.billy.fbi.data.gui.impl;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.data.gui.ClickData;
import fun.billy.fbi.data.manager.Gui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;

public class Management
        extends fun.billy.fbi.data.gui.Gui {

    public Management() {
        super(ChatColor.RED + "Choose a category", 18);
        ArrayList<Object> combat = new ArrayList<>();
        ArrayList<Object> move = new ArrayList<>();
        ArrayList<Object> other = new ArrayList<>();
        for (Check.CheckType type : Check.CheckType.values()) {
            if (type.getSuffix().contains("Combat")) {
                combat.add(type);
            }
            if (type.getSuffix().contains("Mouvement")) {
                move.add(type);
            }
            if (type.getSuffix().contains("Other")) {
                other.add(type);
            }
        }
        ItemStack checkStack = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta checkMeta = checkStack.getItemMeta();
        checkMeta.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Combat" + ChatColor.RED + " (x" + combat.size() + ")");
        checkMeta.setLore(Collections.singletonList(ChatColor.GRAY + "\u27a5 Click to open combat checks."));
        checkStack.setItemMeta(checkMeta);
        this.inventory.setItem(2, checkStack);
        ItemStack checkStack1 = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta checkMeta1 = checkStack1.getItemMeta();
        checkMeta1.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Movement" + ChatColor.RED + " (x" + move.size() + ")");
        checkMeta1.setLore(Collections.singletonList(ChatColor.GRAY + "\u27a5 Click to open Movement checks."));
        checkStack1.setItemMeta(checkMeta1);
        this.inventory.setItem(4, checkStack1);
        ItemStack checkStack2 = new ItemStack(Material.REDSTONE);
        ItemMeta checkMeta2 = checkStack2.getItemMeta();
        checkMeta2.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Other" + ChatColor.RED + " (x" + other.size() + ")");
        checkMeta2.setLore(Collections.singletonList(ChatColor.GRAY + "\u27a5 Click to open Other checks."));
        checkStack2.setItemMeta(checkMeta2);
        this.inventory.setItem(6, checkStack2);
        ItemStack back1 = new ItemStack(Material.ARROW);
        ItemMeta back = back1.getItemMeta();
        back.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Back");
        back.setLore(Collections.singletonList(ChatColor.GRAY + "Click to go back."));
        back1.setItemMeta(back);
        this.inventory.setItem(17, back1);
    }

    @Override
    public void onClick(ClickData clickData) {
        if (clickData.getSlot() == 2) {
            clickData.getPlayer().closeInventory();
            Gui.getInstance().getCheckGui().openGui(clickData.getPlayer());
        }
        if (clickData.getSlot() == 4) {
            clickData.getPlayer().closeInventory();
            Gui.getInstance().getMovGui().openGui(clickData.getPlayer());
        }
        if (clickData.getSlot() == 6) {
            clickData.getPlayer().closeInventory();
            Gui.getInstance().getOtherGui().openGui(clickData.getPlayer());
        }
        if (clickData.getSlot() == 17) {
            clickData.getPlayer().closeInventory();
            Gui.getInstance().getAcGui().openGui(clickData.getPlayer());
        }
    }
}

