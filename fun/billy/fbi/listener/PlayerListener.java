package fun.billy.fbi.listener;

import fun.billy.fbi.AntiCheatPlugin;
import fun.billy.fbi.data.manager.AntiCheatPlayer;
import fun.billy.fbi.data.manager.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerListener
        implements Listener {
    private final AntiCheatPlayer playerManager = AntiCheatPlayer.getInstance();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if (player.hasPermission(Settings.getInstance().getModPermission()))
            player.setMetadata("FBI_ALERTS", new FixedMetadataValue(AntiCheatPlugin.getPlugin(), true));
        Bukkit.getScheduler().runTaskAsynchronously(AntiCheatPlugin.getPlugin(), () -> this.playerManager.inject(player));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        this.playerManager.uninject(player);
        if (player.hasPermission("admin.use"))
            player.removeMetadata("FBI_DEBUG", AntiCheatPlugin.getPlugin());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        this.playerManager.getPlayer(event.getPlayer()).handle(event);
    }
}