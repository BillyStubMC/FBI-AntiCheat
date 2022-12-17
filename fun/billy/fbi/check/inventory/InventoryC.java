package fun.billy.fbi.check.checks.inventory;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInCloseWindow;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class InventoryC
        extends PacketCheck {
    private boolean sent;

    public InventoryC() {
        super(Check.CheckType.INVENTORYC, "C", "InvalidInventory", CheckVersion.EXPERIMENTAL);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying) {
            this.sent = false;
        } else if (packet instanceof PacketPlayInCloseWindow) {
            if (this.sent && !playerData.hasLag()) VL.getInstance().handleViolation(playerData, this, "I ", false);
        } else if (packet instanceof PacketPlayInClientCommand && ((PacketPlayInClientCommand) packet).a() == PacketPlayInClientCommand.EnumClientCommand.OPEN_INVENTORY_ACHIEVEMENT) {
            this.sent = true;
        }
    }
}

