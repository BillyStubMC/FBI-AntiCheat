package fun.billy.fbi.data.manager.violations;

import fun.billy.fbi.AntiCheatPlugin;
import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.Checks;
import fun.billy.fbi.data.manager.AntiCheatPlayer;
import fun.billy.fbi.data.manager.Settings;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VL {
    public static VL instance;
    public static String NAME;
    public static String CERTAINTY;
    public static String CERTAINTYEXP;
    public static String ALERT;
    public static String DEBUG;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static ArrayList<org.bukkit.entity.Player> cooldown;

    private VL() {
        VL.cooldown = new ArrayList<>();
    }

    static {
        NAME = Settings.getInstance().getAnticheatName();
        CERTAINTY = Settings.getInstance().getAlertCertainty();
        CERTAINTYEXP = Settings.getInstance().getAlertExperimental();
        ALERT = Settings.getInstance().getAlertMessage();
        DEBUG = ChatColor.GRAY + "[" + ChatColor.DARK_RED + ChatColor.BOLD + "DEV" + ChatColor.GRAY + "] " + ChatColor.RED + "%s" + ChatColor.GRAY + " x " + ChatColor.RED + "%s" + ChatColor.GRAY + " x " + ChatColor.RED + "%s";
    }

    public String getFormattedAlert(String player, String check, String checkType, String data, String vl, Integer ping) {
        return ChatColor.translateAlternateColorCodes('&', ALERT.replace("{prefix}", NAME).replace("{player}", player).replace("{certainty}", CERTAINTY).replace("{cheat}", check).replace("{type}", checkType).replace("{vl}", vl).replace("{ping}", ping.toString()).replace("{data}", data));
    }

    public String getFormattedExpir(String player, String check, String checkType, String data, String vl, Integer ping) {
        return ChatColor.translateAlternateColorCodes('&', ALERT.replace("{prefix}", NAME).replace("{player}", player).replace("{certainty}", CERTAINTYEXP).replace("{cheat}", check).replace("{type}", checkType).replace("{vl}", vl).replace("{ping}", ping.toString()).replace("{data}", data));
    }

    public void handleViolation(PlayerData playerData, Check check, String data, boolean info) {
        this.handleViolation(playerData, check, data, 1.0, info);
    }

    public void handleViolation(PlayerData playerData, Check check, String data, double vl, boolean info) {
        this.Violation(playerData, check, data, vl, info);
    }

    public void Violation(PlayerData playerData, Check check, String data, double vl, boolean info) {
        AntiCheatPlugin.getPlugin().lastCheck.put(playerData.getPlayer().getUniqueId(), check.getFriendlyName() + " " + check.getSubType());
        this.executorService.submit(() -> this._handleViolation(playerData, check, data, vl, info));
    }

    private void _handleViolation(PlayerData playerData, Check check, String data, double vl, boolean info) {
        if (playerData.isEnabled() && !playerData.isBanned()) {
            check.setViolations(check.getViolations() + vl);
            int currentViolation = (int) Math.floor(check.getViolations());
            if (check.getViolations() > 0.0) {
                if (Checks.getInstance().enabled(check.getType())) {
                    this.handleDebug(playerData, check, data);
                }
            }
            if (currentViolation > 0) {
                this.handleAlert(playerData, check, data, currentViolation, info);
                if (check.getViolations() >= (double) check.getMaxViolation()) {
                    this.handleBan(playerData, check);
                }
            }
        }
    }

    public void handleAlert(PlayerData playerData, Check check, String data, int vl, boolean info) {
        boolean information = Settings.getInstance().isAlertInformation();
        String format = this.getFormattedAlert(playerData.getPlayer().getName(), check.getFriendlyName(), (information ? check.getSubType() : ""), data, String.valueOf(vl), playerData.getPing());
        String formatExpir = this.getFormattedExpir(playerData.getPlayer().getName(), check.getFriendlyName(), (information ? check.getSubType() : ""), data, String.valueOf(vl), playerData.getPing());
        if (Checks.getInstance().enabled(check.getType())) {
            this.log(playerData.getPlayer(), "[" + new SimpleDateFormat("dd/MM/yy hh:mm:ss").format(new Date()) + "], " + check.getFriendlyName() + " (Type " + check.getSubType() + ")@");
            if (Settings.getInstance().isAlertNoSpam() && VL.cooldown.contains(playerData.getPlayer()) && !check.getFriendlyName().contains("Reach") || VL.cooldown.contains(playerData.getPlayer()) && !check.getFriendlyName().contains("KillAura") || VL.cooldown.contains(playerData.getPlayer()) && !check.getFriendlyName().contains("Auto Clicker") || VL.cooldown.contains(playerData.getPlayer()) && !check.getFriendlyName().contains("Velocity") || VL.cooldown.contains(playerData.getPlayer())) {
                return;
            }
            AntiCheatPlayer.getInstance().getPlayers().values().stream().filter(PlayerData::isAlerts).map(PlayerData::getPlayer).forEach(player -> this.sendCommandMsg(player, (check.getCheckVersion() == Check.CheckVersion.RELEASE ? format : formatExpir), playerData.getPlayer(), info, data));
            if (Settings.getInstance().isAlertNoSpam()) {
                VL.cooldown.add(playerData.getPlayer());
                new BukkitRunnable() {
                    public void run() {
                        VL.cooldown.remove(playerData.getPlayer());
                    }
                }.runTaskLaterAsynchronously(AntiCheatPlugin.getPlugin(), 15L);
            }
        }
    }

    private void log(org.bukkit.entity.Player player, String log) {
        try {
            try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("plugins/FBI/data/" + player.getName().toLowerCase() + ".log", true)))) {
                pw.println(log);
            }
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("plugins/FBI/data/" + player.getName().toLowerCase() + ".log", true)));
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Please make a folder named 'data' in plugins/FBI/");
        }
    }

    public void sendCommandMsg(org.bukkit.entity.Player p, String msg, org.bukkit.entity.Player player, boolean info, String data) {
        TextComponent message = new TextComponent(msg);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + Settings.getInstance().getAlertHoverCommand().replace("{player}", player.getName())));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder((info ? ChatColor.GRAY + " * " + ChatColor.WHITE + data + "\n" + "\n" : "") + Settings.getInstance().getAlertHover().replace('&', '§')).create()));
        p.spigot().sendMessage(message);
    }

    public void handleDebug(PlayerData playerData, Check check, String data) {
        AntiCheatPlayer.getInstance().getPlayers().values().stream().filter(PlayerData::isDebug).map(PlayerData::getPlayer).forEach(player -> {
            Object[] arrobject = new Object[5];
            arrobject[0] = playerData.getPlayer().getName();
            arrobject[1] = check.getType().getName();
            arrobject[2] = data;
            player.sendMessage(String.format(DEBUG, arrobject));
        });
    }

    public void handleBan(PlayerData playerData, Check check) {
        if (!(!Checks.getInstance().enabled(check.getType()) || !Settings.getInstance().isAutoBan() || check.getCheckVersion() != Check.CheckVersion.RELEASE || playerData.isBanned() || playerData.isDebug() || Settings.getInstance().isBypassEnabled() && playerData.getPlayer().hasPermission(Settings.getInstance().getBypassPermission()))) {
            this._handleBan(playerData, check);
        }
    }

    public void _handleBan(PlayerData playerData, Check check) {
        this.insertBan(playerData, check);
    }

    public void insertBan(PlayerData playerData, Check check) {
        playerData.setBanned(true);
        String name = playerData.getPlayer().getName();
        this.launchBan(playerData, name, check);
    }

    private void launchBan(PlayerData playerData, String name, Check check) {
        int seconds = Settings.getInstance().getTimer() * 20;
        new BukkitRunnable() {
            int n = 0;

            public void run() {
                ++this.n;
                if (this.n == 1) {
                    playerData.setBanticks(this.getTaskId());
                    check.setViolations(0.0);
                    AntiCheatPlayer.getInstance().getPlayers().values().stream().filter(PlayerData::isAlerts).map(PlayerData::getPlayer).forEach(player -> this.sendCommandMsg(player, ChatColor.translateAlternateColorCodes('&', Settings.getInstance().getTimerBanMessage().replace("%s", name).replace("{time}", String.valueOf(Settings.getInstance().getTimer()))), playerData.getPlayer()));
                } else if (this.n == 2) {
                    if (Settings.getInstance().isBanAnnouncement()) {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Settings.getInstance().getBanSeparated()));
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', String.format(Settings.getInstance().getBanMessage().replace("{check}", check.getFriendlyName()), name)));
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Settings.getInstance().getBanSeparated()));
                    }
                    for (String banCommand : Settings.getInstance().getBanCommands()) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(banCommand, name).replace("{check}", check.getFriendlyName()));
                    }
                    this.cancel();
                }
            }

            public void sendCommandMsg(org.bukkit.entity.Player p, String msg, org.bukkit.entity.Player target) {
                TextComponent message = new TextComponent(msg);
                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/autobancancel " + target.getName()));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', Settings.getInstance().getTimerBanHover())).create()));
                p.spigot().sendMessage(message);
            }
        }.runTaskTimer(AntiCheatPlugin.getPlugin(), 0L, seconds);
    }

    public static VL getInstance() {
        return instance == null ? (instance = new VL()) : instance;
    }
}

