package fun.billy.fbi.data.gui.impl.category;

import fun.billy.fbi.data.gui.ClickData;
import fun.billy.fbi.data.manager.Gui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Mouvement
        extends fun.billy.fbi.data.gui.Gui {

    public Mouvement() {
        super(ChatColor.RED + "Movement", 18);
        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta checkMeta1 = back.getItemMeta();
        checkMeta1.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Back");
        checkMeta1.setLore(Arrays.asList("", ChatColor.GRAY + "Click to go back."));
        back.setItemMeta(checkMeta1);
        this.inventory.setItem(17, back);
        ItemStack Aim = new ItemStack(Material.BOOK);
        ItemMeta AimD = Aim.getItemMeta();
        AimD.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Fly");
        AimD.setLore(Arrays.asList("", ChatColor.GRAY + "Click to configure."));
        Aim.setItemMeta(AimD);
        this.inventory.setItem(0, Aim);
        ItemStack Auto = new ItemStack(Material.BOOK);
        ItemMeta AutoD = Auto.getItemMeta();
        AutoD.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Speed");
        AutoD.setLore(Arrays.asList("", ChatColor.GRAY + "Click to configure."));
        Auto.setItemMeta(AutoD);
        this.inventory.setItem(1, Auto);
        ItemStack Reach = new ItemStack(Material.BOOK);
        ItemMeta ReachD = Reach.getItemMeta();
        ReachD.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Velocity");
        ReachD.setLore(Arrays.asList("", ChatColor.GRAY + "Click to configure."));
        Reach.setItemMeta(ReachD);
        this.inventory.setItem(2, Reach);
        ItemStack Interact = new ItemStack(Material.BOOK);
        ItemMeta InteractD = Interact.getItemMeta();
        InteractD.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Scaffold");
        InteractD.setLore(Arrays.asList("", ChatColor.GRAY + "Click to configure."));
        Interact.setItemMeta(InteractD);
        this.inventory.setItem(3, Interact);
        ItemStack Motion = new ItemStack(Material.BOOK);
        ItemMeta MotionD = Motion.getItemMeta();
        MotionD.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Motion");
        MotionD.setLore(Arrays.asList("", ChatColor.GRAY + "Click to configure."));
        Motion.setItemMeta(MotionD);
        this.inventory.setItem(4, Motion);
        ItemStack NoFall = new ItemStack(Material.BOOK);
        ItemMeta NoFallD = NoFall.getItemMeta();
        NoFallD.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "NoFall");
        NoFallD.setLore(Arrays.asList("", ChatColor.GRAY + "Click to configure."));
        NoFall.setItemMeta(NoFallD);
        this.inventory.setItem(5, NoFall);
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
                Gui.getInstance().getFlyGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 1) {
                Gui.getInstance().getSpeedGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 2) {
                Gui.getInstance().getVelocityGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 3) {
                Gui.getInstance().getScaffoldGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 4) {
                Gui.getInstance().getMotionGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 5) {
                Gui.getInstance().getNoFallGui().openGui(clickData.getPlayer());
            }
        }
    }
}

