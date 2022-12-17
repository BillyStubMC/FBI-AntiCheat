package fun.billy.fbi.util;

import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.AntiCheatPlayer;

public class API {

    public static int[] getCps(org.bukkit.entity.Player player) {
        PlayerData playerData = AntiCheatPlayer.getInstance().getPlayer(player);
        return playerData.getCps1();
    }

    public static double[] getReach(org.bukkit.entity.Player player) {
        PlayerData playerData = AntiCheatPlayer.getInstance().getPlayer(player);
        return playerData.getReach1();
    }
}
