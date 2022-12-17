package fun.billy.fbi.data.gui.runnable;

import fun.billy.fbi.AntiCheatPlugin;
import fun.billy.fbi.data.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InfoRunnable extends BukkitRunnable {
    private final AntiCheatPlugin main;
    private final Inventory inv;
    private final Player player;
    private final Player target;
    private final PlayerData targetData;

    public InfoRunnable(AntiCheatPlugin main, Inventory inv, Player player, Player target, PlayerData targetData) {
        this.main = main;
        this.inv = inv;
        this.player = player;
        this.target = target;
        this.targetData = targetData;
    }

    public void run() {
        if (this.main.inGui.contains(this.player)) {
            if (this.target.isOnline()) {

                ItemStack skull = new ItemStack(Material.SKULL_ITEM);
                skull.setDurability((short) 3);
                SkullMeta sm = (SkullMeta) skull.getItemMeta();
                sm.setOwner(target.getName());
                sm.setDisplayName(ChatColor.DARK_RED + ChatColor.BOLD.toString() + target.getName());

                sm.setLore(Arrays.asList("", ChatColor.RED + "Ping" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + targetData.getPing(), ChatColor.RED + "Average Ping" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + targetData.getAveragePing(), ChatColor.RED + "Lagging" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + targetData.hasLag(), "", ChatColor.RED + "Version" + ChatColor.GRAY + " \u279f " + ChatColor.WHITE + targetData.getVersion(), ChatColor.RED + "Mods" + ChatColor.GRAY + " \u279f " + "" + ChatColor.WHITE + targetData.getForgeMods()));
                skull.setItemMeta(sm);
                inv.setItem(11, skull);

                String cps = ChatColor.GRAY + " cps";

                ItemStack item5 = new ItemStack(Material.WEB);
                ItemMeta itemm5 = item5.getItemMeta();
                itemm5.setDisplayName(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Combat");
                List<String> cpsLore = new ArrayList<>();
                int[] lastCPS = targetData.getCps1();
                cpsLore.add("");
                cpsLore.add(ChatColor.GRAY + " * " + ChatColor.RED + lastCPS[0] + cps);
                cpsLore.add(ChatColor.GRAY + " * " + ChatColor.RED + lastCPS[1] + cps);
                cpsLore.add(ChatColor.GRAY + " * " + ChatColor.RED + lastCPS[2] + cps);
                cpsLore.add(ChatColor.GRAY + " * " + ChatColor.RED + lastCPS[3] + cps);
                cpsLore.add(ChatColor.GRAY + " * " + ChatColor.RED + lastCPS[4] + cps);
                itemm5.setLore(cpsLore);
                item5.setItemMeta(itemm5);
                inv.setItem(12, item5);

                this.player.updateInventory();
            } else {
                this.player.closeInventory();
                this.cancel();
            }
        } else {
            this.cancel();
        }
    }
}