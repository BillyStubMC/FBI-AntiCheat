package fun.billy.fbi.check.checks.scaffold;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.CustomLocation;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class ScaffoldC
        extends PacketCheck {
    private long lastPlace;
    private boolean place;

    public ScaffoldC() {
        super(CheckType.MISCE, "C", "Scaffold", CheckVersion.RELEASE);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInBlockPlace && !playerData.isTeleporting()) {
            final CustomLocation lastMovePacket = playerData.getLastMovePacket();
            if (lastMovePacket == null) {
                return;
            }
            final long delay = System.currentTimeMillis() - lastMovePacket.getTimestamp();
            if (delay <= 25.0) {
                this.lastPlace = System.currentTimeMillis();
                this.place = true;
            } else if (packet instanceof PacketPlayInFlying && this.place) {
                final long time = System.currentTimeMillis() - this.lastPlace;
                if (time >= 25L) {
                    VL.getInstance().handleViolation(playerData, this, "T " + time + " D " + delay, false);
                    this.place = false;
                }
            }
        }
    }
}