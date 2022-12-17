package fun.billy.fbi.data.gui.impl.category;

import fun.billy.fbi.data.gui.ClickData;
import fun.billy.fbi.data.manager.Gui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Combat
        extends fun.billy.fbi.data.gui.Gui {

    public Combat() {
        super(ChatColor.LIGHT_PURPLE + "Combat", 18);
        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta checkMeta1 = back.getItemMeta();
        checkMeta1.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Back");
        checkMeta1.setLore(Arrays.asList("", ChatColor.GRAY + "Click to go back."));
        back.setItemMeta(checkMeta1);
        this.inventory.setItem(17, back);
        ItemStack Aim = new ItemStack(Material.BOOK);
        ItemMeta AimD = Aim.getItemMeta();
        AimD.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Aim");
        AimD.setLore(Arrays.asList("", ChatColor.GRAY + "Click to configure."));
        Aim.setItemMeta(AimD);
        this.inventory.setItem(0, Aim);
        ItemStack Auto = new ItemStack(Material.BOOK);
        ItemMeta AutoD = Auto.getItemMeta();
        AutoD.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "AutoClicker");
        AutoD.setLore(Arrays.asList("", ChatColor.GRAY + "Click to configure."));
        Auto.setItemMeta(AutoD);
        this.inventory.setItem(1, Auto);
        ItemStack Ka = new ItemStack(Material.BOOK);
        ItemMeta KaD = Ka.getItemMeta();
        KaD.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "KillAura");
        KaD.setLore(Arrays.asList("", ChatColor.GRAY + "Click to configure."));
        Ka.setItemMeta(KaD);
        this.inventory.setItem(2, Ka);
        ItemStack Reach = new ItemStack(Material.BOOK);
        ItemMeta ReachD = Reach.getItemMeta();
        ReachD.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Reach");
        ReachD.setLore(Arrays.asList("", ChatColor.GRAY + "Click to configure."));
        Reach.setItemMeta(ReachD);
        this.inventory.setItem(3, Reach);
        ItemStack Inventory = new ItemStack(Material.BOOK);
        ItemMeta InventoryD = Inventory.getItemMeta();
        InventoryD.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "InvalidInventory");
        InventoryD.setLore(Arrays.asList("", ChatColor.GRAY + "Click to configure."));
        Inventory.setItemMeta(InventoryD);
        this.inventory.setItem(4, Inventory);
        ItemStack HitBoxes = new ItemStack(Material.BOOK);
        ItemMeta HitBoxesD = HitBoxes.getItemMeta();
        HitBoxesD.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "HitBoxes");
        HitBoxesD.setLore(Arrays.asList("", ChatColor.GRAY + "Click to configure."));
        HitBoxes.setItemMeta(HitBoxesD);
        this.inventory.setItem(5, HitBoxes);
    }

    @Override
    public void onClick(ClickData clickData) {
        Integer slot = clickData.getSlot();
        if (slot <= this.getInventory().getSize() && slot >= 0) {
            ItemStack checkStack = clickData.getItemStack();
            if (checkStack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Back")) {
                clickData.getPlayer().closeInventory();
                Gui.getInstance().getMainGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 0) {
                Gui.getInstance().getAimGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 1) {
                Gui.getInstance().getAutoGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 2) {
                Gui.getInstance().getKaGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 3) {
                Gui.getInstance().getReachGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 4) {
                Gui.getInstance().getInvinvGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 5) {
                Gui.getInstance().getHitBoxesGui().openGui(clickData.getPlayer());
            }
        }
    }
}

