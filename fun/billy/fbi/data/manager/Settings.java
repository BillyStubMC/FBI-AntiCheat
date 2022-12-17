package fun.billy.fbi.data.manager;

import fun.billy.fbi.AntiCheatPlugin;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Collections;
import java.util.List;

@Getter
public class Settings {
    public static Settings instance;
    public static File file;
    public static File fileCheck;
    public static YamlConfiguration configuration;
    public static YamlConfiguration configurationCheck;
    public static String HEADER = "FBI AntiCheat.\nStop the server before editing the config!.\n";
    public String liscence;
    public String anticheatName;
    public String logsPlayer;
    public String logsResult;
    public boolean alertNoSpam;
    public String alertCertainty;
    public String logsSeparated;
    public String alertExperimental;
    public String alertMessage;
    public String alertHover;
    public String alertHoverCommand;
    public String alertEnabled;
    public String alertDisabled;
    public List<String> banCommands;
    public String timerBanMessage;
    public String timerBanHover;
    public String timerBanCancel;
    public Integer timer;
    public String banMessage;
    public String banSeparated;
    public boolean autoBan;
    public boolean banAnnouncement;
    public boolean bypassEnabled;
    public String bypassPermission;
    public String modPermission;
    public String logPermission;
    public boolean async;
    public boolean packetEnabled;
    public Integer packetlimiterban;
    public Integer packetlimiterlog;
    public boolean rollback;
    public boolean alertInformation;
    public Integer maxCps;
    public String miscAcCommand;
    public List<String> miscListCommand;
    public String nocrashingAlerts;
    public String nocrashingHover;
    public String errorPerm;
    public String errorOnlyPlayer;
    public String errorOnlyOp;
    public String errorPlayerNotFound;
    public String errorCannotFindLog;
    public String errorUsageLog;
    public String errorUsageInfo;
    public String errorUsageACCancel;

    public Settings() {
        configuration = new YamlConfiguration();
        configurationCheck = new YamlConfiguration();
    }

    public void enable() {
        this.setupConfig();
        this.setupConfigCheck();
        this.readConfig();
    }

    public void disable() {
        saveConfig();
    }

    public void readConfig() {
        this.liscence = configuration.getString("liscence", "tethpvp");
        this.anticheatName = configuration.getString("prefix", "&7[&e⚠&7]");
        this.logPermission = configuration.getString("logs.permission", "FBI.alerts");
        this.logsPlayer = configuration.getString("logs.playerlogs", "&7FBI AC logs for &e{player}&7:");
        this.logsResult = configuration.getString("logs.result", " &ex{vl}&7 - &e{check}");
        this.logsSeparated = configuration.getString("logs.separated", "&f&m----------------------------------");
        this.alertNoSpam = configuration.getBoolean("alerts.no-spam", false);
        this.alertInformation = configuration.getBoolean("alerts.information", true);
        this.alertCertainty = configuration.getString("alerts.certainty", "&7failed");
        this.alertExperimental = configuration.getString("alerts.experimental", "&7failed");
        this.alertMessage = configuration.getString("alerts.message", "{prefix} &e{player} {certainty} &c{cheat} {type}&7 {ping}ms {data} &7[&ex{vl}&7]");
        this.alertHover = configuration.getString("alerts.hover.message", "&7Click to teleport!");
        this.alertHoverCommand = configuration.getString("alerts.hover.command", "tp {player}");
        this.modPermission = configuration.getString("alerts.permission", "FBI.alerts");
        this.alertEnabled = configuration.getString("alerts.enabled", "{prefix} &aEnabling alerts.");
        this.alertDisabled = configuration.getString("alerts.disabled", "{prefix} &cDisabling alerts.");
        this.rollback = configuration.getBoolean("rollback.enabled", false);
        this.timer = configuration.getInt("bans.timer.time", 1);
        this.timerBanMessage = configuration.getString("bans.timer.message", "&7[&e⚠&7] &e%s &7will now be auto-banned");
        this.timerBanHover = configuration.getString("bans.timer.hover", "&7Click to cancel.");
        this.timerBanCancel = configuration.getString("bans.timer.cancel", "&aThe auto-ban was sucessfully cancelled.");
        this.banCommands = this.readList(configuration.get("bans.commands", new String[]{"banip %s 1d [FBI] Cheating -S"}));
        this.banMessage = configuration.getString("bans.message", "&7[&e⚠&7] &e%s &7was autobanned for &eCheating&7.");
        this.banSeparated = configuration.getString("bans.separated", " ");
        this.autoBan = configuration.getBoolean("bans.enabled", true);
        this.banAnnouncement = configuration.getBoolean("bans.announce.enabled", true);
        this.bypassEnabled = configuration.getBoolean("bans.bypass.enabled", true);
        this.bypassPermission = configuration.getString("bans.bypass.permission", "FBI.exempt");
        this.async = configuration.getBoolean("optimization.async.detection", false);
        this.async = configuration.getBoolean("optimization.async.commands", false);
        this.async = configuration.getBoolean("optimization.async.packet", false);
        this.async = configuration.getBoolean("optimization.async.ban", false);
        this.maxCps = configuration.getInt("clicks-per-seconds.max", 20);
        this.packetEnabled = configuration.getBoolean("packet-limiter.enabled", true);
        this.packetlimiterban = configuration.getInt("packet-limiter.packets-per-second.ban", 240);
        this.packetlimiterlog = configuration.getInt("packet-limiter.packets-per-second.log", 275);
        this.miscAcCommand = configuration.getString("misc.anticheat", "{prefix} &7This server is running &eFBI&7 by &cBillyStub.");
        this.miscListCommand = this.readList(configuration.get("misc.anticheat-help", new String[]{" &7- &e/anticheat gui &7- Manage the anticheat.", " &7- &e/alerts &7- Toggle the alerts for yourself.", " &7- &e/info &7- View the informations of a player.", " &7- &e/logs &7- View the logs of a player."}));
        this.nocrashingAlerts = configuration.getString("anticrash.alerts", "{prefix} &c{player} &7trying to crash.");
        this.nocrashingHover = configuration.getString("anticrash.hover.message", "&7(Blocked in &4{ms}&7ms - &4{reason}&7)");
        this.errorPerm = configuration.getString("error.permission", "{prefix} &cYou do not have the permission.");
        this.errorOnlyOp = configuration.getString("error.only-op", "{prefix} &cOnly players op can use this command.");
        this.errorOnlyPlayer = configuration.getString("error.only-player", "{prefix} &cOnly players can use this command.");
        this.errorPlayerNotFound = configuration.getString("error.player-not-found", "{prefix} &cPlayer not found.");
        this.errorCannotFindLog = configuration.getString("error.flag-not-found", "{prefix} &cCannot find flags for {player}.");
        this.errorUsageLog = configuration.getString("error.usage-log", "{prefix} &cUsage: /logs <player>");
        this.errorUsageInfo = configuration.getString("error.usage-info", "{prefix} &cUsage: /info <player>");
        this.errorUsageACCancel = configuration.getString("error.usage-autobancancel", "{prefix} &cUsage: /autobancancel <player>");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> List<T> readList(Object o) {
        return (List<T>) ((o instanceof List) ? ((List) o) : Collections.singletonList(o));
    }

    public void setupConfig() {
        AntiCheatPlugin plugin = AntiCheatPlugin.getPlugin();
        file = new File(plugin.getDataFolder(), "FBI-Config.yml");
        this.loadConfig();
        configuration.options().header(HEADER);
        configuration.options().copyDefaults(true).copyHeader(true);
        configuration.addDefault("liscence", "tethpvp");
        configuration.addDefault("prefix", "&7[&e⚠&7]");
        configuration.addDefault("logs.permission", "FBI.alerts");
        configuration.addDefault("logs.playerlogs", "&7FBI AC logs for &e{player}&7:");
        configuration.addDefault("logs.result", " &ex{vl}&7 - &e{check}");
        configuration.addDefault("logs.separated", "&f&m----------------------------------");
        configuration.addDefault("alerts.no-spam", false);
        configuration.addDefault("alerts.information", true);
        configuration.addDefault("alerts.certainty", "&7failed");
        configuration.addDefault("alerts.experimental", "&7failed");
        configuration.addDefault("alerts.message", "{prefix} &e{player} {certainty} &c{cheat} {type}&7 {ping}ms {data} &7[&ex{vl}&7]");
        configuration.addDefault("alerts.hover.message", "&7Click to teleport!");
        configuration.addDefault("alerts.hover.command", "tp {player}");
        configuration.addDefault("alerts.permission", "FBI.alerts");
        configuration.addDefault("alerts.enabled", "{prefix} &aEnabling alerts.");
        configuration.addDefault("alerts.disabled", "{prefix} &cDisabling alerts.");
        configuration.addDefault("rollback.enabled", false);
        configuration.addDefault("bans.timer.time", 1);
        configuration.addDefault("bans.timer.message", "&7[&e⚠&7] &e%s &7will now be auto-banned");
        configuration.addDefault("bans.timer.hover", "&7Click to cancel.");
        configuration.addDefault("bans.timer.cancel", "&aThe auto-ban was sucessfully cancelled.");
        configuration.addDefault("bans.commands", new String[]{"ban %s 1d [FBI] Cheating -s"});
        configuration.addDefault("bans.message", "&7[&e⚠&7] &e%s &7was autobanned for &eCheating&7.");
        configuration.addDefault("bans.separated", " ");
        configuration.addDefault("bans.enabled", true);
        configuration.addDefault("bans.announce.enabled", true);
        configuration.addDefault("bans.bypass.enabled", true);
        configuration.addDefault("bans.bypass.permission", "FBI.exempt");
        configuration.addDefault("optimization.async.detection", false);
        configuration.addDefault("optimization.async.commands", false);
        configuration.addDefault("optimization.async.packet", false);
        configuration.addDefault("optimization.async.ban", false);
        configuration.addDefault("optimization.ticks.fly", 1);
        configuration.addDefault("optimization.ticks.speed", 1);
        configuration.addDefault("clicks-per-seconds.max", 20);
        configuration.addDefault("packet-limiter.enabled", true);
        configuration.addDefault("packet-limiter.packets-per-second.log", 240);
        configuration.addDefault("packet-limiter.packets-per-second.ban", 275);
        configuration.addDefault("misc.anticheat", "{prefix} &7This server is running &eFBI&7 by &cBillyStub.");
        configuration.addDefault("misc.anticheat-help", new String[]{" &7- &e/anticheat gui &7- Manage the anticheat.", " &7- &e/alerts &7- Toggle the alerts for yourself.", " &7- &e/info &7- View the informations of a player.", " &7- &e/logs &7- View the logs of a player."});
        configuration.addDefault("anticrash.alerts", "{prefix} &c{player} &7trying to crash.");
        configuration.addDefault("anticrash.hover.message", "&7(Blocked in &4{ms}&7ms - &4{reason}&7)");
        configuration.addDefault("error.permission", "{prefix} &cYou do not have the permission.");
        configuration.addDefault("error.only-op", "{prefix} &cOnly players op can use this command.");
        configuration.addDefault("error.only-player", "{prefix} &cOnly players can use this command.");
        configuration.addDefault("error.player-not-found", "{prefix} &cPlayer not found.");
        configuration.addDefault("error.flag-not-found", "{prefix} &cCannot find flags for {player}.");
        configuration.addDefault("error.usage-log", "{prefix} &cUsage: /logs <player>");
        configuration.addDefault("error.usage-info", "{prefix} &cUsage: /info <player>");
        configuration.addDefault("error.usage-autobancancel", "{prefix} &cUsage: /autobancancel <player>");
        saveConfig();
    }

    public void setupConfigCheck() {
        AntiCheatPlugin plugin = AntiCheatPlugin.getPlugin();
        fileCheck = new File(plugin.getDataFolder(), "FBI-Checks.yml");
        this.loadConfigCheck();
        configurationCheck.options().header(HEADER);
        configurationCheck.options().copyDefaults(true).copyHeader(true);
        configurationCheck.addDefault("checktypes.AimAssist A.enabled", true);
        configurationCheck.addDefault("checktypes.AimAssist A.max-vl", 5);
        configurationCheck.addDefault("checktypes.AimAssist B.enabled", true);
        configurationCheck.addDefault("checktypes.AimAssist B.max-vl", 5);
        configurationCheck.addDefault("checktypes.AimAssist C.enabled", true);
        configurationCheck.addDefault("checktypes.AimAssist C.max-vl", 5);
        configurationCheck.addDefault("checktypes.AimAssist D.enabled", true);
        configurationCheck.addDefault("checktypes.AimAssist D.max-vl", 5);
        configurationCheck.addDefault("checktypes.AimAssist E.enabled", true);
        configurationCheck.addDefault("checktypes.AimAssist F.max-vl", 5);
        configurationCheck.addDefault("checktypes.AimAssist G.enabled", true);
        configurationCheck.addDefault("checktypes.AimAssist G.max-vl", 5);
        configurationCheck.addDefault("checktypes.Aim Combined.enabled", true);
        configurationCheck.addDefault("checktypes.Aim Combined.max-vl", 10);
        configurationCheck.addDefault("checktypes.Aim GCD.enabled", true);
        configurationCheck.addDefault("checktypes.Aim GCD.max-vl", 10);
        configurationCheck.addDefault("checktypes.AutoClicker A.enabled", true);
        configurationCheck.addDefault("checktypes.AutoClicker A.max-vl", 5);
        configurationCheck.addDefault("checktypes.AutoClicker B.enabled", true);
        configurationCheck.addDefault("checktypes.AutoClicker B.max-vl", 5);
        configurationCheck.addDefault("checktypes.AutoClicker C.enabled", true);
        configurationCheck.addDefault("checktypes.AutoClicker C.max-vl", 5);
        configurationCheck.addDefault("checktypes.AutoClicker D.enabled", true);
        configurationCheck.addDefault("checktypes.AutoClicker D.max-vl", 5);
        configurationCheck.addDefault("checktypes.AutoClicker E.enabled", true);
        configurationCheck.addDefault("checktypes.AutoClicker E.max-vl", 5);
        configurationCheck.addDefault("checktypes.AutoClicker F.enabled", true);
        configurationCheck.addDefault("checktypes.AutoClicker F.max-vl", 5);
        configurationCheck.addDefault("checktypes.AutoClicker G.enabled", true);
        configurationCheck.addDefault("checktypes.AutoClicker G.max-vl", 5);
        configurationCheck.addDefault("checktypes.AutoClicker H.enabled", true);
        configurationCheck.addDefault("checktypes.AutoClicker H.max-vl", 5);
        configurationCheck.addDefault("checktypes.AutoClicker BadPackets.enabled", true);
        configurationCheck.addDefault("checktypes.AutoClicker BadPackets.max-vl", 5);
        configurationCheck.addDefault("checktypes.BadPackets.enabled", true);
        configurationCheck.addDefault("checktypes.Fly A.enabled", true);
        configurationCheck.addDefault("checktypes.Fly A.max-vl", 2);
        configurationCheck.addDefault("checktypes.Motion A.enabled", true);
        configurationCheck.addDefault("checktypes.Motion A.max-vl", 5);
        configurationCheck.addDefault("checktypes.Motion B.enabled", true);
        configurationCheck.addDefault("checktypes.Motion B.max-vl", 5);
        configurationCheck.addDefault("checktypes.Motion C.enabled", true);
        configurationCheck.addDefault("checktypes.Motion C.max-vl", 5);
        configurationCheck.addDefault("checktypes.Motion D.enabled", true);
        configurationCheck.addDefault("checktypes.Motion D.max-vl", 5);
        configurationCheck.addDefault("checktypes.Inventory.enabled", true);
        configurationCheck.addDefault("checktypes.KillAura A.enabled", true);
        configurationCheck.addDefault("checktypes.KillAura A.max-vl", 5);
        configurationCheck.addDefault("checktypes.KillAura B.enabled", true);
        configurationCheck.addDefault("checktypes.KillAura B.max-vl", 5);
        configurationCheck.addDefault("checktypes.KillAura C.enabled", true);
        configurationCheck.addDefault("checktypes.KillAura C.max-vl", 5);
        configurationCheck.addDefault("checktypes.KillAura D.enabled", true);
        configurationCheck.addDefault("checktypes.KillAura D.max-vl", 5);
        configurationCheck.addDefault("checktypes.KillAura E.enabled", true);
        configurationCheck.addDefault("checktypes.KillAura E.max-vl", 5);
        configurationCheck.addDefault("checktypes.KillAura BadPackets.enabled", true);
        configurationCheck.addDefault("checktypes.KillAura BadPackets.max-vl", 5);
        configurationCheck.addDefault("checktypes.HitBoxes A.enabled", true);
        configurationCheck.addDefault("checktypes.HitBoxes A.max-vl", 5);
        configurationCheck.addDefault("checktypes.HitBoxes B.enabled", true);
        configurationCheck.addDefault("checktypes.HitBoxes B.max-vl", 2);
        configurationCheck.addDefault("checktypes.Reach A.enabled", true);
        configurationCheck.addDefault("checktypes.Reach A.max-vl", 5);
        configurationCheck.addDefault("checktypes.Reach B.enabled", true);
        configurationCheck.addDefault("checktypes.Reach B.max-vl", 25);
        configurationCheck.addDefault("checktypes.Reach C.enabled", true);
        configurationCheck.addDefault("checktypes.Reach C.max-vl", 25);
        configurationCheck.addDefault("checktypes.Speed A.enabled", true);
        configurationCheck.addDefault("checktypes.Speed A.max-vl", 5);
        configurationCheck.addDefault("checktypes.Speed B.enabled", true);
        configurationCheck.addDefault("checktypes.Speed B.max-vl", 2);
        configurationCheck.addDefault("checktypes.NoFall A.enabled", true);
        configurationCheck.addDefault("checktypes.NoFall A.max-vl", 5);
        configurationCheck.addDefault("checktypes.NoFall B.enabled", true);
        configurationCheck.addDefault("checktypes.NoFall B.max-vl", 5);
        configurationCheck.addDefault("checktypes.Timer A.enabled", true);
        configurationCheck.addDefault("checktypes.Timer A.max-vl", 5);
        configurationCheck.addDefault("checktypes.Timer B.enabled", true);
        configurationCheck.addDefault("checktypes.Timer B.max-vl", 5);
        configurationCheck.addDefault("checktypes.Timer C.enabled", true);
        configurationCheck.addDefault("checktypes.Timer C.max-vl", 2);
        configurationCheck.addDefault("checktypes.Velocity A.enabled", true);
        configurationCheck.addDefault("checktypes.Velocity A.max-vl", 2);
        configurationCheck.addDefault("checktypes.Velocity B.enabled", true);
        configurationCheck.addDefault("checktypes.Velocity B.max-vl", 2);
        configurationCheck.addDefault("checktypes.Scaffold A.enabled", true);
        configurationCheck.addDefault("checktypes.Scaffold A.max-vl", 2);
        configurationCheck.addDefault("checktypes.InvalidInventory A.enabled", true);
        configurationCheck.addDefault("checktypes.InvalidInventory A.max-vl", 5);
        configurationCheck.addDefault("checktypes.InvalidInventory B.enabled", true);
        configurationCheck.addDefault("checktypes.InvalidInventory B.max-vl", 5);
        configurationCheck.addDefault("checktypes.InvalidInventory C.enabled", true);
        configurationCheck.addDefault("checktypes.InvalidInventory C.max-vl", 5);
        configurationCheck.addDefault("checktypes.BadPackets A.enabled", true);
        configurationCheck.addDefault("checktypes.BadPackets A.max-vl", 2);
        configurationCheck.addDefault("checktypes.BadPackets B.enabled", true);
        configurationCheck.addDefault("checktypes.BadPackets B.max-vl", 5);
        configurationCheck.addDefault("checktypes.BadPackets C.enabled", true);
        configurationCheck.addDefault("checktypes.BadPackets C.max-vl", 2);
        configurationCheck.addDefault("checktypes.BadPackets E.enabled", true);
        configurationCheck.addDefault("checktypes.BadPackets E.max-vl", 5);
        configurationCheck.addDefault("checktypes.BadPackets D.enabled", true);
        configurationCheck.addDefault("checktypes.BadPackets D.max-vl", 5);
        configurationCheck.addDefault("checktypes.BadPackets F.enabled", true);
        configurationCheck.addDefault("checktypes.BadPackets F.max-vl", 5);
        configurationCheck.addDefault("checktypes.PingSpoof A.enabled", true);
        configurationCheck.addDefault("checktypes.PingSpoof A.max-vl", 2);
        saveConfigCheck();

    }

    public void loadConfig() {
        try {
            configuration.load(file);
        } catch (Exception exception) {
            // empty catch block
        }
    }

    public void loadConfigCheck() {
        try {
            configurationCheck.load(fileCheck);
        } catch (Exception exception) {
            // empty catch block
        }
    }

    public static void saveConfig() {
        try {
            Settings.configuration.save(Settings.file);
        } catch (Exception exception) {
            // empty catch block
        }
    }

    public static void saveConfigCheck() {
        try {
            Settings.configurationCheck.save(Settings.fileCheck);
        } catch (Exception exception) {
            // empty catch block
        }
    }

    public static Settings getInstance() {
        return instance == null ? (instance = new Settings()) : instance;
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    public YamlConfiguration getConfigurationCheck() {
        return configurationCheck;
    }
}

