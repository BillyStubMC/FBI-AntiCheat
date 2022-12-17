package fun.billy.fbi.command;

import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.AntiCheatPlayer;
import fun.billy.fbi.data.manager.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import fun.billy.fbi.libs.Command;
import fun.billy.fbi.libs.CommandArgs;

public class AutobanCommand {
    @Command(name = "autoban", permission = "FBI.autoban", inGameOnly = true)
    public void autoban(CommandArgs args) {
        final Player player = args.getPlayer();
        if (args.getArgs().length == 0) {
            player.sendMessage(Settings.getInstance().getErrorUsageACCancel().replace('&', '§').replace("{prefix}", Settings.getInstance().getAnticheatName().replace("&", "§")));
        } else if (args.getArgs().length == 1) {
            final Player t = player.getServer().getPlayer(args.getArgs()[0]);
            if (t == null) {
                player.sendMessage(Settings.getInstance().getErrorPlayerNotFound().replace('&', '§').replace("{prefix}", Settings.getInstance().getAnticheatName().replace("&", "§")));
                return;
            }
            final PlayerData tData = AntiCheatPlayer.getInstance().getPlayer(t.getPlayer());
            if (tData.isBanned()) {
                tData.setBanned(false);
                player.sendMessage(Settings.getInstance().getTimerBanCancel().replace('&', '§'));
                Bukkit.getScheduler().cancelTask(tData.getBanticks());
            } else {
                player.sendMessage(Settings.getInstance().getErrorPlayerNotFound().replace('&', '§').replace("{prefix}", Settings.getInstance().getAnticheatName().replace("&", "§")));
            }
        }
    }
}