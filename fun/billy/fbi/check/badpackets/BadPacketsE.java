package fun.billy.fbi.check.checks.badpackets;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class BadPacketsE
        extends PacketCheck {

    private int count = 0;
    private PacketPlayInEntityAction.EnumPlayerAction lastAction;

    public BadPacketsE() {
        super(CheckType.BADPACKETSE, "E", "BadPackets", Check.CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInEntityAction && !playerData.isTeleporting()) {
            final PacketPlayInEntityAction packetPlayInEntityAction = (PacketPlayInEntityAction) packet;

            final boolean invalid = ++this.count > 1 && packetPlayInEntityAction.b() == lastAction;

            if (invalid)
                VL.getInstance().handleViolation(playerData, this, "Send same action in one tick", false);

            this.lastAction = packetPlayInEntityAction.b();
        } else if (packet instanceof PacketPlayInFlying) {
            this.count = 0;
        }
    }
}

