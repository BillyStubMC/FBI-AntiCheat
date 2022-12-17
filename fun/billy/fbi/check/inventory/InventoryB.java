package fun.billy.fbi.check.checks.inventory;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class InventoryB
        extends PacketCheck {
    private int ticks;

    public InventoryB() {
        super(CheckType.INVENTORYB, "B", "InvalidInventory", CheckVersion.RELEASE);
        this.ticks = 0;
        this.violations = -4.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInClientCommand && ((PacketPlayInClientCommand) packet).a().equals(PacketPlayInClientCommand.EnumClientCommand.OPEN_INVENTORY_ACHIEVEMENT)) {
            this.ticks = 0;
        } else if (packet instanceof PacketPlayInFlying && !playerData.hasLag() && playerData.hasFast()) {
            if (this.ticks++ > 30) {
                playerData.setInventoryOpen(false);
            } else if (++this.ticks >= 4 && playerData.isInventoryOpen() && playerData.getPlayer().isSprinting() && playerData.isOnGround()) {
                VL.getInstance().handleViolation(playerData, this, "T " + this.ticks, false);
            }
        }
    }
}
