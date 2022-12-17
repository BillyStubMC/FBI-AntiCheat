package fun.billy.fbi.check.checks.badpackets;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;

public class BadPacketsA
        extends PacketCheck {
    private boolean sent;

    public BadPacketsA() {
        super(CheckType.BADPACKETSA, "A", "BadPackets", Check.CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (playerData.getVersion() != 1.9) {
            if (packet instanceof PacketPlayInFlying) {
                this.sent = false;
            } else if (packet instanceof PacketPlayInEntityAction) {
                final PacketPlayInEntityAction packetPlayInEntityAction = (PacketPlayInEntityAction) packet;
                if (packetPlayInEntityAction.b() == PacketPlayInEntityAction.EnumPlayerAction.START_SPRINTING
                        || packetPlayInEntityAction.b() == PacketPlayInEntityAction.EnumPlayerAction.STOP_SPRINTING
                        || packetPlayInEntityAction.b() == PacketPlayInEntityAction.EnumPlayerAction.START_SNEAKING
                        || packetPlayInEntityAction.b() == PacketPlayInEntityAction.EnumPlayerAction.STOP_SNEAKING) {
                    this.sent = true;
                }
            } else if (packet instanceof PacketPlayInUseEntity) {
                final PacketPlayInUseEntity packetPlayInUseEntity = (PacketPlayInUseEntity) packet;
                if (packetPlayInUseEntity.a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK && sent)
                    VL.getInstance().handleViolation(playerData, this, "Send sprint or sneak same in the last tick", false);
            }
        }
    }
}

