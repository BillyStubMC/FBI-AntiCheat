package fun.billy.fbi.check.checks.badpackets;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.GameMode;

public class BadPacketsB
        extends PacketCheck {
    private Long lastFlying = null;
    private boolean placed;

    public BadPacketsB() {
        super(CheckType.BADPACKETSB, "B", "BadPackets", Check.CheckVersion.RELEASE);
        this.violations = -2.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.lastFlying != null) {
                if (timestamp - this.lastFlying > 40L && timestamp - this.lastFlying < 80L) {
                    VL.getInstance().handleViolation(playerData, this, "post=" + (timestamp - this.lastFlying) * 2L, false);
                }
                this.lastFlying = null;
                if (placed) this.placed = false;
            }
        } else if (packet instanceof PacketPlayInUseEntity && ((PacketPlayInUseEntity) packet).a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
            long lastFlying = playerData.getLastFlying();
            if (timestamp - lastFlying < 10L) {
                this.lastFlying = lastFlying;
            } else {
                this.decreaseVL(0.05);
            }
        } else if (packet instanceof PacketPlayInBlockPlace && playerData.getPlayer().getGameMode() == GameMode.SURVIVAL) {
            long lastFlying = playerData.getLastFlying();
            if (timestamp - lastFlying < 5L) {
                this.lastFlying = lastFlying;
                this.placed = true;
            } else {
                this.decreaseVL(0.1);
            }
        }
    }
}
