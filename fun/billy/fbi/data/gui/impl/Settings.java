package fun.billy.fbi.data.gui.impl;

import fun.billy.fbi.data.gui.ClickData;
import fun.billy.fbi.data.manager.Gui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Settings
        extends fun.billy.fbi.data.gui.Gui {
    private ItemStack checkStack;
    private ItemStack checkStack2;
    private ItemStack checkStack3;
    private ItemStack checkStack4;
    private ItemStack checkStack10;
    private ItemStack checkStack12;
    private ItemStack checkStack13;

    public Settings() {
        super(ChatColor.RED + "Settings", 18);

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta checkMeta1 = back.getItemMeta();
        checkMeta1.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Back");
        checkMeta1.setLore(Arrays.asList("", ChatColor.GRAY + "Click to go back."));
        back.setItemMeta(checkMeta1);
        this.inventory.setItem(17, back);

        ItemStack boolan = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta boolanMeta = boolan.getItemMeta();
        boolanMeta.setDisplayName(ChatColor.GRAY + "Boolean Settings");
        boolan.setItemMeta(boolanMeta);
        this.inventory.setItem(0, boolan);

        this.checkStack = new ItemStack(fun.billy.fbi.data.manager.Settings.getInstance().isAlertNoSpam() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
        ItemMeta checkMeta = this.checkStack.getItemMeta();
        checkMeta.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "No-Spam Alert");
        checkMeta.setLore(Arrays.asList(ChatColor.GRAY + "\u27a5 Activate this to avoid spam alert.", "", ChatColor.LIGHT_PURPLE + "Enabled" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + fun.billy.fbi.data.manager.Settings.getInstance().isAlertNoSpam(), "", ChatColor.GRAY + "Click to change."));
        this.checkStack.setItemMeta(checkMeta);
        this.inventory.setItem(1, this.checkStack);
        this.checkStack2 = new ItemStack(fun.billy.fbi.data.manager.Settings.getInstance().isAutoBan() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
        ItemMeta checkMeta2 = this.checkStack2.getItemMeta();
        checkMeta2.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Auto-Ban");
        checkMeta2.setLore(Arrays.asList(ChatColor.GRAY + "\u27a5 Activate this to ban a player when he's cheating.", "", ChatColor.LIGHT_PURPLE + "Enabled" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + fun.billy.fbi.data.manager.Settings.getInstance().isAutoBan(), "", ChatColor.GRAY + "Click to change."));
        this.checkStack2.setItemMeta(checkMeta2);
        this.inventory.setItem(2, this.checkStack2);
        this.checkStack3 = new ItemStack(fun.billy.fbi.data.manager.Settings.getInstance().isBanAnnouncement() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
        ItemMeta checkMeta3 = this.checkStack3.getItemMeta();
        checkMeta3.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Auto-Ban Broadcast");
        checkMeta3.setLore(Arrays.asList(ChatColor.GRAY + "\u27a5 Activate this to send a broadcast for the auto-ban.", "", ChatColor.LIGHT_PURPLE + "Enabled" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + fun.billy.fbi.data.manager.Settings.getInstance().isBanAnnouncement(), "", ChatColor.GRAY + "Click to change."));
        this.checkStack3.setItemMeta(checkMeta3);
        this.inventory.setItem(3, this.checkStack3);
        this.checkStack4 = new ItemStack(fun.billy.fbi.data.manager.Settings.getInstance().isBypassEnabled() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
        ItemMeta checkMeta4 = this.checkStack4.getItemMeta();
        checkMeta4.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Auto-Ban Bypass");
        checkMeta4.setLore(Arrays.asList(ChatColor.GRAY + "\u27a5 Activate this to bypass auto-ban with a permission.", "", ChatColor.LIGHT_PURPLE + "Enabled" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + fun.billy.fbi.data.manager.Settings.getInstance().isBypassEnabled(), "", ChatColor.GRAY + "Click to change."));
        this.checkStack4.setItemMeta(checkMeta4);
        this.inventory.setItem(4, this.checkStack4);
        this.checkStack10 = new ItemStack(fun.billy.fbi.data.manager.Settings.getInstance().isPacketEnabled() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
        ItemMeta checkMeta10 = this.checkStack10.getItemMeta();
        checkMeta10.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Packet-Limiter");
        checkMeta10.setLore(Arrays.asList(ChatColor.GRAY + "\u27a5 Activate this to fix exploit / crasher.", "", ChatColor.DARK_RED + "Enabled" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + fun.billy.fbi.data.manager.Settings.getInstance().isPacketEnabled(), "", ChatColor.GRAY + "Click to change."));
        this.checkStack10.setItemMeta(checkMeta10);
        this.inventory.setItem(5, this.checkStack10);
        this.checkStack12 = new ItemStack(fun.billy.fbi.data.manager.Settings.getInstance().isRollback() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
        ItemMeta checkMeta12 = this.checkStack12.getItemMeta();
        checkMeta12.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Rollback");
        checkMeta12.setLore(Arrays.asList(ChatColor.GRAY + "\u27a5 Activate this to rollback player when he's flagging.", "", ChatColor.LIGHT_PURPLE + "Enabled" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + fun.billy.fbi.data.manager.Settings.getInstance().isRollback(), "", ChatColor.GRAY + "Click to change."));
        this.checkStack12.setItemMeta(checkMeta12);
        this.inventory.setItem(6, this.checkStack12);
        this.checkStack13 = new ItemStack(fun.billy.fbi.data.manager.Settings.getInstance().isAlertInformation() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
        ItemMeta checkMeta13 = this.checkStack13.getItemMeta();
        checkMeta13.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Alert Information");
        checkMeta13.setLore(Arrays.asList(ChatColor.GRAY + "\u27a5 Activate to have information when player is flagging.", "", ChatColor.LIGHT_PURPLE + "Enabled" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + fun.billy.fbi.data.manager.Settings.getInstance().isAlertInformation(), "", ChatColor.GRAY + "Click to change."));
        this.checkStack13.setItemMeta(checkMeta13);
        this.inventory.setItem(7, this.checkStack13);
    }

    @Override
    public void onClick(ClickData clickData) {
        if (clickData.getSlot() == 17) {
            clickData.getPlayer().closeInventory();
            Gui.getInstance().getAcGui().openGui(clickData.getPlayer());
        }
        if (clickData.getSlot() == 1) {
            if (!fun.billy.fbi.data.manager.Settings.getInstance().isAlertNoSpam()) {
                fun.billy.fbi.data.manager.Settings.getInstance().getConfiguration().set("alerts.no-spam", true);
                fun.billy.fbi.data.manager.Settings.getInstance().alertNoSpam = true;
            } else {
                fun.billy.fbi.data.manager.Settings.getInstance().getConfiguration().set("alerts.no-spam", false);
                fun.billy.fbi.data.manager.Settings.getInstance().alertNoSpam = false;
            }
            this.checkStack = new ItemStack(fun.billy.fbi.data.manager.Settings.getInstance().isAlertNoSpam() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
            ItemMeta checkMeta = this.checkStack.getItemMeta();
            checkMeta.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "No-Spam Alert");
            checkMeta.setLore(Arrays.asList(ChatColor.GRAY + "\u27a5 Activate this to avoid spam alert.", "", ChatColor.DARK_RED + "Enabled" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + fun.billy.fbi.data.manager.Settings.getInstance().isAlertNoSpam(), "", ChatColor.GRAY + "Click to change."));
            this.checkStack.setItemMeta(checkMeta);
            this.inventory.setItem(1, this.checkStack);
            fun.billy.fbi.data.manager.Settings.saveConfig();
        }
        if (clickData.getSlot() == 2) {
            if (!fun.billy.fbi.data.manager.Settings.getInstance().isAutoBan()) {
                fun.billy.fbi.data.manager.Settings.getInstance().getConfiguration().set("bans.enabled", true);
                fun.billy.fbi.data.manager.Settings.getInstance().autoBan = true;
            } else {
                fun.billy.fbi.data.manager.Settings.getInstance().getConfiguration().set("bans.enabled", false);
                fun.billy.fbi.data.manager.Settings.getInstance().autoBan = false;
            }
            this.checkStack2 = new ItemStack(fun.billy.fbi.data.manager.Settings.getInstance().isAutoBan() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
            ItemMeta checkMeta2 = this.checkStack2.getItemMeta();
            checkMeta2.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Auto-Ban");
            checkMeta2.setLore(Arrays.asList(ChatColor.GRAY + "\u27a5 Activate this to ban a player when he's cheating.", "", ChatColor.DARK_RED + "Enabled" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + fun.billy.fbi.data.manager.Settings.getInstance().isAutoBan(), "", ChatColor.GRAY + "Click to change."));
            this.checkStack2.setItemMeta(checkMeta2);
            this.inventory.setItem(2, this.checkStack2);
            fun.billy.fbi.data.manager.Settings.saveConfig();
        }
        if (clickData.getSlot() == 3) {
            if (!fun.billy.fbi.data.manager.Settings.getInstance().isBanAnnouncement()) {
                fun.billy.fbi.data.manager.Settings.getInstance().getConfiguration().set("bans.announce.enabled", true);
                fun.billy.fbi.data.manager.Settings.getInstance().banAnnouncement = true;
            } else {
                fun.billy.fbi.data.manager.Settings.getInstance().getConfiguration().set("bans.announce.enabled", false);
                fun.billy.fbi.data.manager.Settings.getInstance().banAnnouncement = false;
            }
            this.checkStack3 = new ItemStack(fun.billy.fbi.data.manager.Settings.getInstance().isBanAnnouncement() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
            ItemMeta checkMeta3 = this.checkStack3.getItemMeta();
            checkMeta3.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Auto-Ban Broadcast");
            checkMeta3.setLore(Arrays.asList(ChatColor.GRAY + "\u27a5 Activate this to send a broadcast for the auto-ban.", "", ChatColor.DARK_RED + "Enabled" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + fun.billy.fbi.data.manager.Settings.getInstance().isBanAnnouncement(), "", ChatColor.GRAY + "Click to change."));
            this.checkStack3.setItemMeta(checkMeta3);
            this.inventory.setItem(3, this.checkStack3);
            fun.billy.fbi.data.manager.Settings.saveConfig();
        }
        if (clickData.getSlot() == 4) {
            if (!fun.billy.fbi.data.manager.Settings.getInstance().isBypassEnabled()) {
                fun.billy.fbi.data.manager.Settings.getInstance().getConfiguration().set("bans.bypass.enabled", true);
                fun.billy.fbi.data.manager.Settings.getInstance().bypassEnabled = true;
            } else {
                fun.billy.fbi.data.manager.Settings.getInstance().getConfiguration().set("bans.bypass.enabled", false);
                fun.billy.fbi.data.manager.Settings.getInstance().bypassEnabled = false;
            }
            this.checkStack4 = new ItemStack(fun.billy.fbi.data.manager.Settings.getInstance().isBypassEnabled() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
            ItemMeta checkMeta4 = this.checkStack4.getItemMeta();
            checkMeta4.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Auto-Ban Bypass");
            checkMeta4.setLore(Arrays.asList(ChatColor.GRAY + "\u27a5 Activate this to bypass auto-ban with a permission.", "", ChatColor.DARK_RED + "Enabled" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + fun.billy.fbi.data.manager.Settings.getInstance().isBypassEnabled(), "", ChatColor.GRAY + "Click to change."));
            this.checkStack4.setItemMeta(checkMeta4);
            this.inventory.setItem(4, this.checkStack4);
            fun.billy.fbi.data.manager.Settings.saveConfig();
        }
        if (clickData.getSlot() == 5) {
            if (!fun.billy.fbi.data.manager.Settings.getInstance().isPacketEnabled()) {
                fun.billy.fbi.data.manager.Settings.getInstance().getConfiguration().set("packet-limiter.enabled", true);
                fun.billy.fbi.data.manager.Settings.getInstance().packetEnabled = true;
            } else {
                fun.billy.fbi.data.manager.Settings.getInstance().getConfiguration().set("packet-limiter.enabled", false);
                fun.billy.fbi.data.manager.Settings.getInstance().packetEnabled = false;
            }
            this.checkStack10 = new ItemStack(fun.billy.fbi.data.manager.Settings.getInstance().isPacketEnabled() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
            ItemMeta checkMeta10 = this.checkStack10.getItemMeta();
            checkMeta10.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Packet-Limiter");
            checkMeta10.setLore(Arrays.asList(ChatColor.GRAY + "\u27a5 Activate this to fix exploit / crasher.", "", ChatColor.DARK_RED + "Enabled" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + fun.billy.fbi.data.manager.Settings.getInstance().isPacketEnabled(), "", ChatColor.GRAY + "Click to change."));
            this.checkStack10.setItemMeta(checkMeta10);
            this.inventory.setItem(5, this.checkStack10);
            fun.billy.fbi.data.manager.Settings.saveConfig();
        }
        if (clickData.getSlot() == 6) {
            if (!fun.billy.fbi.data.manager.Settings.getInstance().isRollback()) {
                fun.billy.fbi.data.manager.Settings.getInstance().getConfiguration().set("rollback.enabled", true);
                fun.billy.fbi.data.manager.Settings.getInstance().rollback = true;
            } else {
                fun.billy.fbi.data.manager.Settings.getInstance().getConfiguration().set("rollback.enabled", false);
                fun.billy.fbi.data.manager.Settings.getInstance().rollback = false;
            }
            this.checkStack12 = new ItemStack(fun.billy.fbi.data.manager.Settings.getInstance().isRollback() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
            ItemMeta checkMeta12 = this.checkStack12.getItemMeta();
            checkMeta12.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Rollback");
            checkMeta12.setLore(Arrays.asList(ChatColor.GRAY + "\u27a5 Activate this to rollback player when he's flagging.", "", ChatColor.DARK_RED + "Enabled" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + fun.billy.fbi.data.manager.Settings.getInstance().isRollback(), "", ChatColor.GRAY + "Click to change."));
            this.checkStack12.setItemMeta(checkMeta12);
            this.inventory.setItem(6, this.checkStack12);
            fun.billy.fbi.data.manager.Settings.saveConfig();
        }
        if (clickData.getSlot() == 7) {
            if (!fun.billy.fbi.data.manager.Settings.getInstance().isAlertInformation()) {
                fun.billy.fbi.data.manager.Settings.getInstance().getConfiguration().set("alerts.information", true);
                fun.billy.fbi.data.manager.Settings.getInstance().alertInformation = true;
            } else {
                fun.billy.fbi.data.manager.Settings.getInstance().getConfiguration().set("alerts.information", false);
                fun.billy.fbi.data.manager.Settings.getInstance().alertInformation = false;
            }
            this.checkStack13 = new ItemStack(fun.billy.fbi.data.manager.Settings.getInstance().isAlertInformation() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
            ItemMeta checkMeta13 = this.checkStack13.getItemMeta();
            checkMeta13.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Alert Information");
            checkMeta13.setLore(Arrays.asList(ChatColor.GRAY + "\u27a5 Activate to have information when player is flagging.", "", ChatColor.DARK_RED + "Enabled" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + fun.billy.fbi.data.manager.Settings.getInstance().isAlertInformation(), "", ChatColor.GRAY + "Click to change."));
            this.checkStack13.setItemMeta(checkMeta13);
            this.inventory.setItem(7, this.checkStack13);
            fun.billy.fbi.data.manager.Settings.saveConfig();
        }
    }
}

