package fun.billy.fbi.command;

import fun.billy.fbi.AntiCheatPlugin;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.AntiCheatPlayer;
import fun.billy.fbi.data.manager.Settings;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import fun.billy.fbi.libs.Command;
import fun.billy.fbi.libs.CommandArgs;

public class AlertsCommand {
    @Command(name = "alerts", permission = "FBI.alerts", inGameOnly = true)
    public void alerts(CommandArgs args) {
        final Player player = args.getPlayer();
        final PlayerData playerData = AntiCheatPlayer.getInstance().getPlayer(player);
        if (playerData != null) {
            playerData.setAlerts(!playerData.isAlerts());
            if (playerData.isAlerts()) {
                player.sendMessage(Settings.getInstance().getAlertEnabled().replace('&', '§').replace("{prefix}", Settings.getInstance().getAnticheatName().replace("&", "§")));
                player.setMetadata("FBI_ALERTS", new FixedMetadataValue(AntiCheatPlugin.getPlugin(), true));
            } else {
                player.sendMessage(Settings.getInstance().getAlertDisabled().replace('&', '§').replace("{prefix}", Settings.getInstance().getAnticheatName().replace("&", "§")));
                player.removeMetadata("FBI_ALERTS", AntiCheatPlugin.getPlugin());
            }
        }
    }
}

