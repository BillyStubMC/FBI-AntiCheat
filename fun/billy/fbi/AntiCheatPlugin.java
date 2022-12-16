package fun.billy.fbi;

import fun.billy.fbi.data.gui.impl.VlSettings;
import fun.billy.fbi.data.manager.Checks;
import fun.billy.fbi.data.manager.Gui;
import fun.billy.fbi.data.manager.AntiCheatPlayer;
import fun.billy.fbi.data.manager.Settings;
import fun.billy.fbi.data.manager.violations.SettingsVL;
import fun.billy.fbi.listener.PlayerListener;
import fun.billy.fbi.util.license.Loader;
import fun.billy.fbi.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import fun.billy.fbi.libs.CommandFramework;
import org.bukkit.Bukkit;
import java.util.*;

public class AntiCheatPlugin
        extends JavaPlugin implements Listener {

    public boolean enabled;
    public static AntiCheatPlugin instance;
    private PlayerListener dataListener;
    private InfoCommand infoCommand;
    private VlSettings vlSettings;
    private Loader typeLoader;

    public String Version = "1.0";
    public List<Player> inGui = new ArrayList<>();
    public Map<UUID, String> lastCheck = new HashMap<>();
    public Map<Player, String> checked = new HashMap<>();

    public void onEnable() {
        long currentTimeMillis = System.currentTimeMillis();
        this.registerAnticheat();
        this.registerListeners();
        this.registerCommand();
        this.getLogger().info("FBI launched sucessfully in " + (System.currentTimeMillis() - currentTimeMillis) + "ms)");
    }

    public void onDisable() {
        this.unregisterListeners();
        this.unregisterAnticheat();
        HandlerList.unregisterAll((Plugin) this);
        Bukkit.getScheduler().cancelTasks(this);
    }

    public static AntiCheatPlugin getInstance() {
        return instance;
    }

    public void registerAnticheat() {
        this.typeLoader = new Loader();
        instance = this;
        Settings.getInstance().enable();
        Checks.getInstance().enable();
        SettingsVL.getInstance().enable();
        Gui.getInstance().enable();
        AntiCheatPlayer.enable(this);
    }

    public void registerListeners() {
        this.dataListener = new PlayerListener();
        this.vlSettings = new VlSettings();
        this.infoCommand = new InfoCommand(this);
        this.getServer().getPluginManager().registerEvents(this.dataListener, this);
        this.getServer().getPluginManager().registerEvents(this.vlSettings, this);
        this.getServer().getPluginManager().registerEvents(this.infoCommand, this);
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    public void registerCommand() {
        this.setupCommand(
                new FBICommand(),
                new AlertsCommand(),
                new DevAlertsCommand(),
                new LogsCommand(),
                new InfoCommand(this)
        );
    }

    public void setupCommand(Object... objects) {
        CommandFramework commandFramework = new CommandFramework(this);
        Arrays.stream(objects).forEach(commandFramework::registerCommands);
    }

    public void unregisterListeners() {
        HandlerList.unregisterAll(dataListener);
        HandlerList.unregisterAll(vlSettings);
        HandlerList.unregisterAll(infoCommand);
        HandlerList.unregisterAll((Plugin) this);
    }

    public void unregisterAnticheat() {
        AntiCheatPlayer.disable();
        Settings.getInstance().disable();
        Checks.getInstance().disable();
        SettingsVL.getInstance().disable();
        this.typeLoader.setCheckClasses(null);
    }

    public Loader getTypeLoader() {
        return this.typeLoader;
    }

    public static AntiCheatPlugin getPlugin() {
        return instance;
    }
}

