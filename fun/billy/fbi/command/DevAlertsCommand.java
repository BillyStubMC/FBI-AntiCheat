package fun.billy.fbi.command;

import fun.billy.fbi.AntiCheatPlugin;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.AntiCheatPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import fun.billy.fbi.libs.Command;
import fun.billy.fbi.libs.CommandArgs;

public class DevAlertsCommand {
    @Command(name = "devalerts", permission = "FBI.devalerts", inGameOnly = true)
    public void devalerts(CommandArgs args) {
        final Player player = args.getPlayer();
        final PlayerData playerData = AntiCheatPlayer.getInstance().getPlayer(player);
        if (playerData != null) {
            playerData.setDebug(!playerData.isDebug());
            if (playerData.isDebug()) {
                player.sendMessage(ChatColor.GREEN + "You have enabled your developer alerts.");
                player.setMetadata("FBI_DEBUG", new FixedMetadataValue(AntiCheatPlugin.getPlugin(), true));
            } else {
                player.sendMessage(ChatColor.RED + "You have disabled your developer alerts.");
                player.removeMetadata("FBI_DEBUG", AntiCheatPlugin.getPlugin());
            }
        }
    }
}

