package fun.billy.fbi.command;

import fun.billy.fbi.data.manager.Gui;
import fun.billy.fbi.data.manager.Settings;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import fun.billy.fbi.libs.Command;
import fun.billy.fbi.libs.CommandArgs;

public class FBICommand {
    @Command(name = "FBI", permission = "FBI.*", inGameOnly = true)
    public void info(CommandArgs args) {
        final Player p = (Player) args.getSender();
        final String firstArg = (args.getArgs().length >= 1) ? args.getArgs()[0] : null;
        if (p.isOp()) {
            if (firstArg == null) {
                this.sendCommandMessage(p);
            } else {
                switch (args.getArgs()[0].toLowerCase()) {
                    case "gui":
                        Gui.getInstance().getAcGui().openGui(p);
                        break;
                    case "dev":
                        p.sendMessage(ChatColor.GREEN + "Developer mode is now enabled.");
                        break;
                }
            }
        }
    }

    private void sendCommandMessage(Player player) {
        player.sendMessage("");
        player.sendMessage(Settings.getInstance().getMiscAcCommand().replace('&', '§').replace("{prefix}", Settings.getInstance().getAnticheatName().replace("&", "§")));
        player.sendMessage("");
        for (String s : Settings.getInstance().getMiscListCommand()) {
            player.sendMessage(s.replace('&', '§').replace("{prefix}", Settings.getInstance().getAnticheatName().replace("&", "§")));
        }
        player.sendMessage("");
    }
}
