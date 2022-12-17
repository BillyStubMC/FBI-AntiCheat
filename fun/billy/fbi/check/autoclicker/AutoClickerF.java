package fun.billy.fbi.check.checks.autoclicker;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.Settings;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import org.bukkit.GameMode;

public class AutoClickerF
        extends PacketCheck {
    private int acTypeAClicks, done;
    private boolean place;

    public AutoClickerF() {
        super(Check.CheckType.AUTO_CLICKERF, "F", "AutoClicker", Check.CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInArmAnimation && !this.place) {
            ++acTypeAClicks;
        } else if (packet instanceof PacketPlayInFlying) {
            if (++done > 19) {
                if (!playerData.hasLag()) {
                    if (acTypeAClicks >= Settings.getInstance().getMaxCps() && !playerData.isDigging() && playerData.getPlayer().getGameMode() != GameMode.CREATIVE)
                        //VL.getInstance().handleViolation(playerData, this, "CPS " + acTypeAClicks, true);
                        VL.getInstance().handleViolation(playerData, this, "High CPS", true);
                }
                playerData.cps1[4] = playerData.cps1[3];
                playerData.cps1[3] = playerData.cps1[2];
                playerData.cps1[2] = playerData.cps1[1];
                playerData.cps1[1] = playerData.cps1[0];
                playerData.cps1[0] = acTypeAClicks;
                acTypeAClicks = 0;
                done = 0;
                if (place) {
                    place = false;
                }
            }
        } else if (packet instanceof PacketPlayInBlockPlace) {
            place = true;
        }
    }
}