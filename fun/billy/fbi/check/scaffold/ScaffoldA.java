package fun.billy.fbi.check.checks.scaffold;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInHeldItemSlot;

import java.util.Objects;

public class ScaffoldA
        extends PacketCheck {
    private Integer lastSlot;

    public ScaffoldA() {
        super(Check.CheckType.MISCC, "A", "Scaffold", CheckVersion.RELEASE);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInHeldItemSlot) {
            PacketPlayInHeldItemSlot vPacketPlayInHeldItemSlot = (PacketPlayInHeldItemSlot) packet;
            if (this.lastSlot != null && Objects.equals(vPacketPlayInHeldItemSlot.a(), this.lastSlot) && playerData.getTotalTicks() > 200) {
                VL.getInstance().handleViolation(playerData, this, "s=" + this.lastSlot, false);
            } else {
                this.decreaseVL(0.25);
            }
            this.lastSlot = vPacketPlayInHeldItemSlot.a();
        }
    }
}

