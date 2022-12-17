package fun.billy.fbi.command;

import fun.billy.fbi.AntiCheatPlugin;
import fun.billy.fbi.data.manager.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import fun.billy.fbi.libs.Command;
import fun.billy.fbi.libs.CommandArgs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class LogsCommand {
    @SuppressWarnings("deprecation")
    @Command(name = "logs", permission = "FBI.logs", inGameOnly = true)
    public void logs(CommandArgs args) {
        final Player player = args.getPlayer();
        if (args.getArgs().length == 0) {
            player.sendMessage(Settings.getInstance().getErrorUsageLog().replace('&', '§').replace("{prefix}", Settings.getInstance().getAnticheatName().replace("&", "§")));
            return;
        }
        if (args.getArgs().length == 1) {
            final String playerCheck = args.getArgs()[0].toLowerCase();
            final String latestLogs = this.getFromFile("plugins/FBI/data/" + playerCheck + ".log");
            if (latestLogs.equals("-1")) {
                player.sendMessage(Settings.getInstance().getErrorCannotFindLog().replace('&', '§').replace("{prefix}", Settings.getInstance().getAnticheatName().replace("&", "§")).replace("{player}", playerCheck));
            } else {
                Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(AntiCheatPlugin.getPlugin(), () -> {
                    player.sendMessage(Settings.getInstance().getLogsSeparated().replace('&', '§'));
                    player.sendMessage("");
                    player.sendMessage(Settings.getInstance().getLogsPlayer().replace('&', '§').replace("{player}", Bukkit.getOfflinePlayer(playerCheck).getName()));
                    player.sendMessage("");
                    HashMap<String, Integer> flags = new HashMap<>();
                    String[] split;
                    for (int length = (split = latestLogs.split("@")).length, i = 0; i < length; ++i) {
                        String s1 = split[i];
                        String hackName = s1.split(",")[1];
                        if (flags.containsKey(hackName)) {
                            flags.replace(hackName, flags.get(hackName) + 1);
                        } else {
                            flags.put(hackName, 1);
                        }
                    }
                    String displayed = "";
                    for (HashMap<String, Integer> highest = LogsCommand.this.getHighestEntry(flags, displayed); highest != null; highest = LogsCommand.this.getHighestEntry(flags, displayed)) {
                        for (String s2 : highest.keySet()) {
                            player.sendMessage(Settings.getInstance().getLogsResult().replace('&', '§').replace("{check}", s2).replace("{vl}", highest.get(s2).toString()));
                            displayed = displayed + s2;
                        }
                    }
                    player.sendMessage("");
                    player.sendMessage(Settings.getInstance().getLogsSeparated().replace('&', '§'));
                });
            }
        }
    }

    private HashMap<String, Integer> getHighestEntry(HashMap<String, Integer> entries, String exclusions) {
        HashMap<String, Integer> returnEntry = new HashMap<>();
        int highestEntry = 0;
        String entry = null;
        for (String s : entries.keySet()) {
            if (!exclusions.contains(s) && entries.get(s) > highestEntry) {
                highestEntry = entries.get(s);
                entry = s;
            }
        }
        if (entry == null || highestEntry == 0) {
            return null;
        }
        returnEntry.put(entry, highestEntry);
        return returnEntry;
    }

    private String getFromFile(String fileName) {
        try {
            StringBuilder getHotKey = new StringBuilder();
            FileReader getFile = new FileReader(fileName);
            BufferedReader bufferReader = new BufferedReader(getFile);
            String line;
            while ((line = bufferReader.readLine()) != null) {
                getHotKey.append(line);
            }
            bufferReader.close();
            return getHotKey.toString();
        } catch (Exception e) {
            return "-1";
        }
    }
}

