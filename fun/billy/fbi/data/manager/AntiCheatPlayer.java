package fun.billy.fbi.data.manager;

import com.google.common.collect.Maps;
import fun.billy.fbi.AntiCheatPlugin;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.netty.PacketIn;
import fun.billy.fbi.netty.PacketOut;
import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

//import us.myles.ViaVersion.ViaVersionPlugin;

public class AntiCheatPlayer {
    private static AntiCheatPlayer instance;
    private final AntiCheatPlugin plugin;
    private final Map<UUID, PlayerData> players = Maps.newConcurrentMap();

    public PlayerData getPlayer(Player player) {
        return this.players.get(player.getUniqueId());
    }

    public void inject(Player player) {
        final PlayerData playerData = new PlayerData(player, this.plugin.getTypeLoader().loadChecks());
        final Channel channel = playerData.getEntityPlayer().playerConnection.networkManager.channel;
        this.players.put(player.getUniqueId(), playerData);
        if (channel != null) {
            channel.pipeline().addBefore("packet_handler", "FBI-encoder", new PacketOut(playerData));
            channel.pipeline().addBefore("packet_handler", "FBI-decoder", new PacketIn(playerData));
        }
        this.handShake(playerData);
    }

    public void uninject(Player player) {
        final PlayerData playerData = this.players.remove(player.getUniqueId());
        if (playerData != null) {
            playerData.setEnabled(false);
            final PlayerConnection playerConnection = playerData.getEntityPlayer().playerConnection;
            if (playerConnection != null && !playerConnection.isDisconnected()) {
                final Channel channel = playerConnection.networkManager.channel;
                try {
                    channel.pipeline().remove("FBI-encoder");
                    channel.pipeline().remove("FBI-decoder");
                } catch (Throwable throwable) {
                    // empty catch block
                }
            }
        }
    }

    private void handShake(PlayerData playerData) {
        //int versioningNumber = ViaVersionPlugin.getInstance().getApi().getPlayerVersion(playerData.getPlayer());
        int versioningNumber = 47;
        if (versioningNumber == 4 || versioningNumber == 5) {
            playerData.setVersion(1.7);
        } else if (versioningNumber == 47) {
            playerData.setVersion(1.8);
        } else if (versioningNumber > 47) {
            playerData.setVersion(1.9);
        }
    }

    public static AntiCheatPlayer getInstance() {
        return instance;
    }

    public static void enable(AntiCheatPlugin plugin) {
        instance = new AntiCheatPlayer(plugin);
        for (Player player : Bukkit.getOnlinePlayers()) {
            instance.inject(player);
        }
    }

    public static void disable() {
        for (Player player : Bukkit.getOnlinePlayers()) instance.uninject(player);
        instance = null;
    }

    public AntiCheatPlayer(AntiCheatPlugin plugin) {
        this.plugin = plugin;
    }

    public AntiCheatPlugin getPlugin() {
        return this.plugin;
    }

    public Map<UUID, PlayerData> getPlayers() {
        return this.players;
    }

    public Collection<PlayerData> getPlayerData() {
        return this.players.values();
    }
}

