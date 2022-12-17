package fun.billy.fbi.check.checks.badpackets;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;

public class BadPacketsD
        extends PacketCheck {

    private int count = 0;

    public BadPacketsD() {
        super(CheckType.BADPACKETSD, "D", "BadPackets", Check.CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInBlockDig) {
            final PacketPlayInBlockDig packetPlayInBlockDig = (PacketPlayInBlockDig) packet;

            if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.RELEASE_USE_ITEM) {
                final boolean invalid = ++count > 1;

                if (invalid)
                    VL.getInstance().handleViolation(playerData, this, "Invalid block dig", false);
            }
        } else {
            count = 0;
        }
    }
}

