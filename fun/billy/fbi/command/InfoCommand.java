package fun.billy.fbi.command;

import fun.billy.fbi.AntiCheatPlugin;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.gui.runnable.InfoRunnable;
import fun.billy.fbi.data.manager.AntiCheatPlayer;
import fun.billy.fbi.data.manager.Settings;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import fun.billy.fbi.libs.Command;
import fun.billy.fbi.libs.CommandArgs;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class InfoCommand implements Listener {
    private final AntiCheatPlugin main;

    @Command(name = "info", permission = "FBI.info", inGameOnly = true)
    public void info(CommandArgs args) {
        final Player player = args.getPlayer();
        if (args.getArgs().length == 0) {
            player.sendMessage(Settings.getInstance().getErrorUsageInfo().replace('&', '§').replace("{prefix}", Settings.getInstance().getAnticheatName().replace("&", "§")));
        } else if (args.getArgs().length == 1) {
            final Player t = player.getServer().getPlayer(args.getArgs()[0]);
            if (t == null) {
                player.sendMessage(Settings.getInstance().getErrorPlayerNotFound().replace('&', '§').replace("{prefix}", Settings.getInstance().getAnticheatName().replace("&", "§")));
                return;
            }
            final PlayerData tData = AntiCheatPlayer.getInstance().getPlayer(t.getPlayer());
            this.openInfoGui(player, t, tData);
        }
    }

    public void openInfoGui(Player playerToOpen, Player targetToInfo, PlayerData targetData) {
        this.main.inGui.add(playerToOpen);
        this.main.checked.put(playerToOpen, targetToInfo.getName());

        final Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GRAY + targetToInfo.getName() + (targetToInfo.getName().endsWith("s") ? "" : "'s") + " info (thx Johannes)");

        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta glassM = glass.getItemMeta();
        glassM.setDisplayName(" ");
        glass.setItemMeta(glassM);
        List<Integer> slotsGlass = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26);
        for (Integer slot : slotsGlass) {
            inv.setItem(slot, glass);
        }

        ItemStack logs = new ItemStack(Material.ARROW);
        ItemMeta logsM = logs.getItemMeta();
        logsM.setDisplayName(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Logs");
        logsM.setLore(Collections.singletonList(ChatColor.GRAY + "\u27a5 Click to see " + targetToInfo.getName() + (targetToInfo.getName().endsWith("s") ? "" : "'s") + " logs."));
        logs.setItemMeta(logsM);
        inv.setItem(15, logs);

        new InfoRunnable(this.main, inv, playerToOpen, targetToInfo, targetData).runTaskTimer(this.main, 0L, 20L);

        playerToOpen.openInventory(inv);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        final Player player = (Player)event.getPlayer();
        this.main.inGui.remove(player);
        this.main.checked.remove(player);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        if (this.main.inGui.contains(player) && event.getCurrentItem() != null && event.getCurrentItem().getType() != null && event.getCurrentItem().getType() != Material.AIR && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null && event.getClickedInventory().getName().contains("info")) {
            event.setCancelled(true);
            if (event.getSlot() == 15) {
                player.performCommand("logs " + this.main.checked.get(player));
                player.closeInventory();
            }
        }
    }
}
