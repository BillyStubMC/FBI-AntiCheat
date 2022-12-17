package fun.billy.fbi.check.checks.inventory;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInHeldItemSlot;

public class InventoryA
        extends PacketCheck {
    private int stage = 0;

    public InventoryA() {
        super(CheckType.INVENTORYA, "A", "InvalidInventory", Check.CheckVersion.RELEASE);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying) {
            this.stage = 0;
        } else if (packet instanceof PacketPlayInBlockPlace) {
            if (this.stage == 1) {
                this.stage = 2;
            }
        } else if (packet instanceof PacketPlayInHeldItemSlot) {
            if (this.stage == 2) {
                VL.getInstance().handleViolation(playerData, this, "HeldItemSlot", false);
            }
            this.stage = 1;
        }
    }
}

